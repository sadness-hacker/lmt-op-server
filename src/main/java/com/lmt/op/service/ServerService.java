package com.lmt.op.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lmt.op.cache.TrustIPCache;
import com.lmt.op.dao.IServerDao;
import com.lmt.op.dao.ServerDao;
import com.lmt.op.model.Server;
import com.lmt.orm.common.serivce.BaseService;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 *
 */
@Service
public class ServerService extends BaseService<ServerDao, Server, Integer> implements IServerService {
	
	private static final Logger log = LoggerFactory.getLogger(ServerService.class);
	
	@Resource
	private IServerDao serverDao;
	
	/**
	 * 刷新ip缓存
	 */
	@PostConstruct
	public void flushIpCache(){
		List<Server> list = serverDao.listAll();
		for(Server server : list){
			String ip = server.getInnerIp();
			if(!StringUtils.isBlank(ip)){
				String [] arr = ip.split(",");
				for(String s : arr){
					if(!StringUtils.isBlank(s)){
						TrustIPCache.addTrustIp(s.trim());
						log.info("添加信任ip->{}",s);
					}
				}
			}
			ip = server.getOutIp();
			if(!StringUtils.isBlank(ip)){
				String [] arr = ip.split(",");
				for(String s : arr){
					if(!StringUtils.isBlank(s)){
						TrustIPCache.addTrustIp(s.trim());
						log.info("添加信任ip->{}",s);
					}
				}
			}
		}
	}
}
