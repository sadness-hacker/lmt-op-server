CREATE TABLE lmt_server(
	id int(11) unsigned PRIMARY KEY AUTO_INCREMENT,
	name varchar(50) NOT NULL COMMENT '服务器名',
	inner_ip varchar(30) not null default '' comment '服务器内网ip',
	out_ip varchar(30) not null default '' comment '服务器外网ip',
	online_time datetime default null comment '上线时间',
	end_time datetime default null comment '到期时间',
	config varchar(1024) not null default '' comment '服务器配置',
	c_desc varchar(1024) not null default '' comment '服务描述',
	status tinyint(4) not null default 1 comment '服务器状态',
	UNIQUE KEY name (name) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;


CREATE TABLE lmt_application(
	id int(11) unsigned PRIMARY KEY AUTO_INCREMENT,
	name varchar(50) NOT NULL COMMENT '应用名',
	type varchar(20) NOT NULL COMMENT '应用类型',
	description varchar(1024) not null default '' comment '应用描述',
	status tinyint(4) not null default 1 comment '应用状态',
	UNIQUE KEY name (name) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;


CREATE TABLE lmt_config(
	id int(11) unsigned PRIMARY KEY AUTO_INCREMENT,
	name varchar(50) NOT NULL COMMENT '配置名',
	app_id int not null comment '应用id',
	app_name varchar(50) not null default '' comment '应用名',
	env_type varchar(20) default null comment '环境类型',
	path varchar(128) default null comment '保存路径',
	content text comment '配置内容',
	md5 char(32) default '' comment '配置md5值',
	update_time date comment '更新时间',
	update_user varchar(20) default '' comment '更新人',
	add_time date comment '添加时间',
	add_user varchar(20) default '' comment '添加人',
	status tinyint(4) not null default 1 comment '配置状态',
	UNIQUE KEY name_app_id_env_type_path (app_id,env_type,path) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

CREATE TABLE lmt_config_histroy(
	id int(11) unsigned PRIMARY KEY AUTO_INCREMENT,
	config_id int not null comment '配置记录id',
	name varchar(50) NOT NULL COMMENT '配置名',
	app_id int not null comment '应用id',
	app_name varchar(50) not null default '' comment '应用名',
	env_type varchar(20) default null comment '环境类型',
	path varchar(128) default null comment '保存路径',
	content text comment '配置内容',
	md5 char(32) default '' comment '配置md5值',
	update_time date comment '更新时间',
	update_user varchar(20) default '' comment '更新人',
	add_time date comment '添加时间',
	add_user varchar(20) default '' comment '添加人',
	status tinyint(4) not null default 1 comment '配置状态',
	histroy_add_time datetime not null comment '记录添加时间',
	KEY config_id (config_id) USING BTREE,
	KEY app_id (app_id) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;


CREATE TABLE lmt_version(
	id int(11) unsigned PRIMARY KEY AUTO_INCREMENT,
	version_num varchar(20) not null comment '版本号',
	version_date varchar(10) not null comment '版本日期',
	app_id int not null comment '应用id',
	app_name varchar(50) comment '应用名',
	type varchar(20) NOT NULL COMMENT '版本类型',
	file_path varchar(128) not null comment '文件路径',
	file_md5 char(32) not null default '' comment '文件md5值',
	file_size bigint default null comment '文件大小',
	description varchar(1024) default null comment '版本描述',
	add_time datetime comment '添加日期',
	add_user varchar(20) default '' comment '添加人',
	deploy_time date comment '发布时间',
	deploy_user varchar(20) default '' comment '发布人',
	deploy_status tinyint comment '发布状态',
	deploy_mark varchar(1024) default '' comment '发布备注',
	status tinyint(4) not null default 1 comment '配置状态',
	restart tinyint(4) not null comment '重启标识',
	key app_id (app_id) using btree
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

CREATE TABLE `lmt_server_application` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sign` varchar(30) NOT NULL COMMENT '服务标识',
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `server_name` varchar(30) NOT NULL COMMENT '服务器名',
  `env_type` varchar(20) NOT NULL default '' COMMENT '环境类型',
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `app_name` varchar(50) DEFAULT NULL COMMENT '应用名',
  app_type varchar(20) default null default '' comment '应用类型',
  `add_time` datetime DEFAULT NULL COMMENT '添加日期',
  `update_time` datetime DEFAULT NULL COMMENT '添加日期',
  `description` varchar(1024) NOT NULL default '' COMMENT '描述',
  base_path varchar(128) not null default '' comment '服务基础路径',
  port varchar(20) not null default '' comment '服务端口号',
  mark varchar(1024) not null default '' comment '备注',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '配置状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sign` (`sign`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;


CREATE TABLE lmt_deploy_log(
	id int(11) unsigned PRIMARY KEY AUTO_INCREMENT,
	version_id int(11) not null comment '版本id',
	version_num varchar(30) not null comment '版本号',
	version_type varchar(20) not null comment '版本类型',
	sign varchar(50) not null comment '服务唯一标识',
	server_id int not null comment '服务器id',
	server_name varchar(30) not null comment '服务器名',
	app_id int not null comment '应用id',
	app_name varchar(50) not null comment '应用名',
	app_type varchar(20) not null comment '应用类型',
	add_time datetime comment '添加日期',
	restart tinyint not null comment '是否重启',
	deploy_time datetime comment '发布时间',
	deploy_user varchar(20) comment '发布人',
	rollback_time datetime comment '回滚时间',
	rollback_status tinyint comment '回滚状态',
	rollback_user varchar(20) comment '回滚人',
	description varchar(1024) not null comment '发布描述',
	status tinyint(4) not null default 1 comment '发布状态',
	update_time datetime comment '更新时间',
	bak_version varchar(50) comment '备份版本号',
	cmd varchar(30) comment '执行的操作',
	key sign (sign) using btree
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

alter table lmt_deploy_log modify column bak_version varchar(50);

alter table lmt_deploy_log modify column description varchar(10240);

CREATE TABLE lmt_user(
	id int(11) unsigned PRIMARY KEY AUTO_INCREMENT,
	username varchar(30) not null comment '用户名',
	password char(32) not null comment '密码',
	salt varchar(6) not null default '' comment 'salt',
	email varchar(50) not null comment '邮箱',
	phone varchar(20) not null comment '手机号',
	head_img_url varchar(128) not null comment '头像',
	add_time datetime comment '添加日期',
	realname varchar(20) comment '真实姓名',
	last_login_time datetime comment '回滚时间',
	last_login_ip varchar(20) comment '最后登录ip',
	brief varchar(1024) not null comment '简介',
	status tinyint(4) not null default 1 comment '状态',
	update_time datetime comment '更新时间',
	add_user varchar(30) comment '添加人',
	unique key username (username) using btree
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

alter table lmt_user modify column password char(32);

alter table lmt_user add column privileges varchar(1024);
