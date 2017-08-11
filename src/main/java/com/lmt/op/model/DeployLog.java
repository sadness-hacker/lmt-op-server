package com.lmt.op.model;

import java.io.Serializable;
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
 * @date 2017-07-29
 *
 */
@Entity
@Table(name="lmt_deploy_log")
public class DeployLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="version_id")
	private Integer versionId;
	
	@Column(name="version_num")
	private String versionNum;
	
	@Column(name="version_type")
	private String versionType;
	
	@Column(name="sign")
	private String sign;
	
	@Column(name="server_id")
	private Integer serverId;
	
	@Column(name="server_name")
	private String serverName;
	
	@Column(name="app_id")
	private Integer appId;
	
	@Column(name="app_name")
	private String appName;
	
	@Column(name="app_type")
	private String appType;
	
	@Column(name="restart")
	private Integer restart;
	
	@Column(name="add_time")
	private Date addTime;
	
	@Column(name="deploy_time")
	private Date deployTime;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="bak_version")
	private String bakVersion;
	
	@Column(name="rollback_time")
	private Date rollbackTime;
	
	@Column(name="rollback_status")
	private Integer rollbackStatus;
	
	@Column(name="rollback_user")
	private String rollbackUser;
	
	@Column(name="description")
	private String description;
	
	private String cmd;
	
	@Column(name="update_time")
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Integer getRestart() {
		return restart;
	}

	public void setRestart(Integer restart) {
		this.restart = restart;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(Date deployTime) {
		this.deployTime = deployTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getRollbackTime() {
		return rollbackTime;
	}

	public void setRollbackTime(Date rollbackTime) {
		this.rollbackTime = rollbackTime;
	}

	public Integer getRollbackStatus() {
		return rollbackStatus;
	}

	public void setRollbackStatus(Integer rollbackStatus) {
		this.rollbackStatus = rollbackStatus;
	}

	public String getRollbackUser() {
		return rollbackUser;
	}

	public void setRollbackUser(String rollbackUser) {
		this.rollbackUser = rollbackUser;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBakVersion() {
		return bakVersion;
	}

	public void setBakVersion(String bakVersion) {
		this.bakVersion = bakVersion;
	}

}
