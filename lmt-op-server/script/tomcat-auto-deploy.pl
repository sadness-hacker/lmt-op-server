#!/usr/bin/perl -w
use strict;
use DBI;
use LWP;
use LWP::Simple;
use Digest::MD5 qw/md5 md5_hex md5_base64/;
use Digest::MD5::File;
use POSIX qw/strftime/;
use JSON;
use Cwd;

#获取当前目录
my $pwd = getcwd();

#加载配置文件
my $config = $ARGV[0];
if(!defined $config){
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 缺少配置文件参数：命令格式 perl tomcat-auto-deploy.pl config.properties\n";
	exit 1;
}
my %conf = ();
open(CONF,"$config");
while(my $line = <CONF>){
	chomp($line);
	$line =~ s/(^\s+|\s+$)//g;
	if(index($line,'#') == 0 || $line eq ''){
		next;
	}
	my $i = index($line,'=');
	if($i > -1){
		my $k = substr($line,0,$i);
		my $v = substr($line,$i+1);
		$conf{$k}=$v;
	}
}
close(CONF);

#检查参数是否合法
my $center_url = $conf{'center.url'};
if(!defined $center_url){
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 发布中心服务器配置未找到，脚本将退出！center.url=\n";
	exit 1;
}
my $server_sign = $conf{'server.sign'};
if(!defined $server_sign){
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 服务标识配置未找到(需要全局唯一)，脚本将退出！server.sign=\n";
	exit 1;
}
my $server_name = $conf{'server.name'};
if(!defined $server_name){
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 服务器名配置未找到，脚本将退出！server.name=\n";
	exit 1;
}
my $app_name = $conf{'app.name'};
if(!defined $app_name){
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 应用名配置未找到，脚本将退出！app.name=\n";
	exit 1;
}
my $app_env_type = $conf{'app.env.type'};
if(!defined $app_env_type){
	$app_env_type = 'product';
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 应用环境中配置未找到，将使用默认环境(生产环境)app.env.type=product\n";
}
my $server_description = $conf{'server.description'};
if(!defined $server_description){
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 服务描述配置未找到，将使用默认值空字符串server.description=\n";
	$server_description = '';
}
my $tomcat_base_path = $conf{'tomcat.base.path'};
if(!defined $tomcat_base_path){
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " tomcat根目录配置未找到，脚本将退出！tomcat.base.path=\n";
	exit 1;
}
my $tomcat_port = $conf{'tomcat.port'};
if(!defined $tomcat_port){
	$tomcat_port = 8080;
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " tomcat端口配置未找到，将使用默认值8080！tomcat.port=8080\n";
}
my $useKillCommand = $conf{'useKillCommand'};
if(!defined $useKillCommand){
	$useKillCommand = 'true';
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 是否使用kill命令结束tomcat配置未找到，将使用默认值true！useKillCommand=true\n";
}
my $tomcat_backup_path = $conf{'tomcat.bak.path'};
if(!defined $tomcat_backup_path){
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " tomcat备份目录配置未找到，脚本将退出！tomcat.bak.path=\n";
	exit 1;
}
my $diedAutoRestart = $conf{'diedAutoRestart'};
if(!defined $diedAutoRestart){
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " tomcat进程意外中止后自动重启配置未找到，将使用默认值true(会自动重启),diedAutoRestart=true";
	$diedAutoRestart = 'true';
}

my $ua = LWP::UserAgent -> new();
my $url = "$center_url/op/center/regist?" . 
			"sign=$server_sign" . 
			"&serverName=$server_name" .
			"&appName=$app_name" . 
			"&envType=$app_env_type" . 
			"&basePath=$tomcat_base_path" . 
			"&port=$tomcat_port" . 
			"&description=$server_description";
#注册服务
my $response = $ua -> get($url);
my $success = $response->is_success();
if($success){
	my $content = $response -> content();
	my $json = decode_json($content);
	if($json->{'success'} && $json->{'code'} == 200){
		print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 服务注册成功！\n";
	}else{
		print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 服务注册失败！" . $content . "\n";
		exit 1;
	}
}else{
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 服务注册失败（请求失败）！" . $response->status_line() . " $url\n";
	exit 1;
}

while(1){
	my $cmd = keepAlive();
	if($cmd eq 'restart'){
		restart();
	}elsif($cmd eq 'died'){
		my $json = report($server_sign,2,"服务已停止，线程不存在");
		if($json->{'success'} && $json->{'msg'} eq 'execute'){
			doCmd($json->{'data'});
		}
	}
	sleep(3);
}

#检测tomcat存活,获取当前版本号
sub getCurrVersion{
	my $url = "http://127.0.0.1:$tomcat_port/version.jsp";
	my $version = '';
	my $response = $ua -> get($url);
	my $success = $response->is_success();
	if($success){
		$version = $response -> content();
	}else{
		$version = '';
		print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 检测存活失败！$tomcat_base_path " . $response->status_line() . " $url\n";
	}
	return $version;
}

#检测进程存活并获取进程号
sub checkProcess{
	my $process = `ps x | grep $tomcat_base_path`;
	$process =~ s/^ +//g;
	my $keyword = 'org.apache.catalina.startup.Bootstrap';
	my @arr = split(/\n/,$process);
	my $num = '';
	foreach my $line (@arr){
		if(index($line,$keyword) > -1){
			if($num eq ''){
				$num = substr($line,0,index($line,' '));
			}else{
				$num = $num . ' ' . substr($line,0,index($line,' '));
			}
		}
	}
	return $num;
}

#汇报状态到发布中心
sub keepAlive{
	my $process = '';
	for(my $i=0;$i<3;$i++){
		$process = checkProcess();
		last if ($process ne '');
		sleep(1);
	}
	
	if($process eq ''){
		if($diedAutoRestart eq 'true'){
			return 'restart';
		}else{
			return 'died';
		}
	}
	
	
	my $version = '';
	for(my $i=0;$i<10;$i++){
		$version = getCurrVersion();
		last if ($version ne '');
		sleep(1);
	}
	
	if($version eq ''){
		if($diedAutoRestart eq 'true'){
			return 'restart';
		}else{
			return 'died';
		}
	}
	
	my $json = report($server_sign,1,"服务正常，当前版本$version");
	if($json->{'success'} && $json->{'msg'} eq 'execute'){
		doCmd($json->{'data'});
	}
}

#执行更新操作
sub deploy{
	my ($json) = @_;
	my $id = $json->{'id'};
	my $vid = $json->{'versionId'};
	my $version = $json->{'versionNum'};
	my $restart = $json->{'restart'};
	#下载更新包
	my $time = strftime("%Y-%m-%d %H:%M:%S",localtime);
	my $url = "$center_url/op/deployLog/log?id=$id&status=2&log=$time下载更新包开始！";
	
	$ua -> get($url);
	$url = "$center_url/op/version/download?id=$vid";
	getstore($url,"$pwd/work/$version.zip") or die "不能下载...$url\n";
	
	$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
	$url = "$center_url/op/deployLog/log?id=$id&status=2&log=$time下载更新包结束！";
	$ua -> get($url);
	
	#比对文件指纹
	my $response = $ua->get("$center_url/op/version/md5?id=$vid");
	my $success = $response->is_success();
	if(!$success){
		$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
		$url = "$center_url/op/deployLog/log?id=$id&status=3&log=$time获取文件md5失败！";
		$ua -> get($url);
		return 0;
	}
	my $md5 = $response->content();
	my $ctx = Digest::MD5 -> new();
	$ctx -> addpath("$pwd/work/$version.zip");
	my $md5_local = $ctx -> hexdigest();
	
	if(index($md5_local,$md5) > -1){
		$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
		$url = "$center_url/op/deployLog/log?id=$id&status=2&log=$time 文件md5比对成功！";
		$ua -> get($url);
	}else{
		$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
		$url = "$center_url/op/deployLog/log?id=$id&status=3&log=$time 文件md5比对失败！";
		$ua -> get($url);
		return 1;
	}
	
	#备份文件
	my $cmd = "cd $tomcat_base_path/webapps/ROOT && tar -czvf $tomcat_backup_path/$version.pre.tar.gz ./* & cd $pwd";
	`$cmd`;
	$url = "$center_url/op/deployLog/bakVersion?id=$id&bakVersion=$version.pre.tar.gz";
	$ua -> get($url);
	
	$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
	$url = "$center_url/op/deployLog/log?id=$id&status=2&log=$time 文件备份成功！$version.pre.tar.gz";
	$ua -> get($url);
	#清空目录（如果需要）
	#rm -rf $tomcat_base_path/webapps/ROOT/*
	
	#解压缩
	$cmd = "unzip -o $pwd/work/$version.zip -d $tomcat_base_path/webapps/ROOT";
	`$cmd`;
	$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
	$url = "$center_url/op/deployLog/log?id=$id&status=2&log=$time 解压缩成功！";
	$ua -> get($url);
	#覆盖version.jsp
	my $jsp = '<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<%@ page trimDirectiveWhitespaces="true" %>
		<%
		out.print("' . $version . '");
		%>';
	open(JSP,">$tomcat_base_path/webapps/ROOT/version.jsp") or die $!;
	print (JSP $jsp);
	close(JSP);
	
	#重启应用（如果需要）
	if($restart == 1){
		#覆盖配置文件(如果需要)
		#下载配置文件
		$url = "$center_url/op/config/load?sign=$server_sign";
		$response = $ua->get($url);
		$success = $response->is_success();
		if(!$success){
			$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
			print "$time 下载配置文件失败 $url";
			$url = "$center_url/op/deployLog/log?id=$id&status=3&log=$time 下载配置文件失败！";
			$ua -> get($url);
			return 1;
		}
		my $new_conf = $response -> content();
		$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
		$url = "$center_url/op/deployLog/log?id=$id&status=2&log=$time 下载配置文件成功！";
		$ua -> get($url);
		my $j = decode_json($new_conf);
		my $a = $j->{'root'};
		my @arr = @$a;
		my $conf_file = '<br/>';
		foreach my $c (@arr){
			my $p = $c->{'path'};
			my $pp = "$tomcat_base_path/webapps/ROOT/$p";
			$pp = substr($pp,0,rindex($pp,'/'));
			`mkdir -p $pp`;
			$conf_file = $conf_file . $p . "<br/>";
			my $con = $c->{'content'};
			open(F,">$tomcat_base_path/webapps/ROOT/$p");
			print (F $con);
			close(F);
		}
		$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
		$url = "$center_url/op/deployLog/log?id=$id&status=2&log=$time 覆盖配置文件成功！$conf_file";
		$ua -> get($url);
		restart();
		$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
		$url = "$center_url/op/deployLog/log?id=$id&status=1&log=$time 重启成功！";
		$ua -> get($url);
	}
}

#执行回滚操作
sub rollback{
	my ($json) = @_;
	my $id = $json->{'id'};
	my $time = strftime("%Y-%m-%d %H:%M:%S",localtime);
	my $url = "$center_url/op/deployLog/log?id=$id&status=2&log=$time 回滚开始！";
	$ua -> get($url);
	
	my $bak_version = $json->{'bakVersion'};
	`rm -rf $tomcat_base_path/webapps/ROOT/*`;
	my $cmd = "tar -zxvf $tomcat_backup_path/$bak_version -C $tomcat_base_path/webapps/ROOT/";
	`$cmd`;
	restart();
	
	$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
	$url = "$center_url/op/deployLog/reportRollbackStatus?id=$id&rollbackStatus=1&log=$time 回滚成功！";
	$ua -> get($url);
}

#执行重启操作
sub restart{
	stop();
	my $cmd = "sh $tomcat_base_path/bin/startup.sh";
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 执行重启命令 $cmd\n";
	`$cmd`;
	print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 执行重启命令结束\n";
	my $num = '';
	for(my $i=0;$i<3;$i++){
		$num = checkProcess();
		if($num eq ''){
			`$cmd`;
		}else{
			last;
		}
	}
	#重启失败，进程不存在
	if($num eq ''){
		my $i = 0;
		while(1){
			$i++;
			my $num = checkProcess();
			print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 重启失败，进程$num\n";
			last if($num ne '');
			#报告重启状态
			print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 重启失败，进程不存在！ $i\n";
			my $json = report($server_sign,0,'重启失败，进程不存在！');
			if($json->{'success'} && $json->{'msg'} eq 'execute' && $i > 3){
				doCmd($json->{'data'});
			}
			sleep(1);
		}
	}
	
	my $i = 0;
	while(1){
		$i++;
		my $version = getCurrVersion();
		if($version ne ''){
			print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 启动成功！ $i\n";
			last;
		}
		#报告重启状态
		print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 重启失败，无法访问！ $i\n";
		my $json = report($server_sign,0,'重启失败，无法访问!');
		if($json->{'success'} && $json->{'msg'} eq 'execute' && $i > 3){
			doCmd($json->{'data'});
		}
		sleep(1);
	}
	return 1;
}

#执行停止操作
sub stop{
	my $num = checkProcess();
	if($num eq ''){
		return 1;
	}
	if($useKillCommand eq 'true'){
		`kill -9 $num`;
		return 1;
	}else{
		my $cmd = "sh $tomcat_base_path/bin/shutdown.sh";
		`$cmd`;
		for(my $i=0;$i<3;$i++){
			$num = checkProcess();
			if($num eq ''){
				return 1;
			}else{
				`$cmd`;
			}
		}
		$num = checkProcess();
		if($num ne ''){
			`kill -9 $num`;
			return 1;
		}
	}
	return 0;
}

#报告状态
sub report{
	my ($sign,$status,$mark) = @_;
	my $url = "$center_url/op/center/report?sign=$sign&status=$status&mark=$mark";
	my $response = $ua -> get($url);
	my $success = $response->is_success();
	if($success){
		my $content = $response -> content();
		my $json = decode_json($content);
		return $json;
	}else{
		print strftime("%Y-%m-%d %H:%M:%S",localtime) . " 状态报告失败（请求失败）！" . $response->status_line() . " $url\n";
		my $content = '{"success":false,"code":404,"msg":"请求失败"}';
		my $json = decode_json($content);
		return $json;
	}
}

#执行操作命令
sub doCmd{
	my ($json) = @_;
	my $cmd = $json->{'cmd'};
	my $id = $json->{'id'};
	my $time = strftime("%Y-%m-%d %H:%M:%S",localtime);
	my $url = "$center_url/op/deployLog/log?id=$id&status=2&log=$time开始执行$cmd";
	$ua -> get($url);
	if('restart' eq $cmd){
		restart();
	}elsif('deploy' eq $cmd){
		deploy($json);
	}elsif('rollback' eq $cmd){
		rollback($json);
	}
	$time = strftime("%Y-%m-%d %H:%M:%S",localtime);
	$url = "$center_url/op/deployLog/log?id=$id&status=1&log=$time执行$cmd完成！";
	$ua -> get($url);
}


1;
