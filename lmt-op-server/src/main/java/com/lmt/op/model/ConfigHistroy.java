package com.lmt.op.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author ducx
 * @date 2017-07-30
 *
 */
@Entity
@Table(name="lmt_config_histroy")
public class ConfigHistroy extends Config {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConfigHistroy() {
		
	}
	
	public ConfigHistroy(Config conf){
		this.setAddTime(conf.getAddTime());
		this.setAddUser(conf.getAddUser());
		this.setAppId(conf.getId());
		this.setAppName(conf.getAppName());
		this.setConfigId(conf.getId());
		this.setContent(conf.getContent());
		this.setEnvType(conf.getEnvType());
		this.setHistroyAddTime(new Date());
		this.setMd5(conf.getMd5());
		this.setName(conf.getName());
		this.setPath(conf.getPath());
		this.setStatus(conf.getStatus());
		this.setUpdateTime(conf.getUpdateTime());
		this.setUpdateUser(conf.getUpdateUser());
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="config_id")
	private Integer configId;

	@Column(name="histroy_add_time")
	private Date histroyAddTime;

	public Date getHistroyAddTime() {
		return histroyAddTime;
	}

	public void setHistroyAddTime(Date histroyAddTime) {
		this.histroyAddTime = histroyAddTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	
}
