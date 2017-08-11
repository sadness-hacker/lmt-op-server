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
@Table(name="lmt_version")
public class Version implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="version_num")
	private String versionNum;
	
	@Column(name="version_date")
	private String versionDate;
	
	@Column(name="app_id")
	private Integer appId;
	
	@Column(name="app_name")
	private String appName;
	
	@Column(name="type")
	private String type;
	
	@Column(name="file_path")
	private String filePath;
	
	@Column(name="file_md5")
	private String fileMd5;
	
	@Column(name="file_size")
	private long fileSize;
	
	@Column(name="description")
	private String description;
	
	@Column(name="add_time")
	private Date addTime;
	
	@Column(name="add_user")
	private String addUser;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="deploy_time")
	private Date deployTime;
	
	@Column(name="deploy_user")
	private String deployUser;

	@Column(name="deploy_status")
	private Integer deployStatus;
	
	@Column(name="deploy_mark")
	private String deployMark;
	
	@Column(name="restart")
	private Integer restart;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(Date deployTime) {
		this.deployTime = deployTime;
	}

	public String getDeployUser() {
		return deployUser;
	}

	public void setDeployUser(String deployUser) {
		this.deployUser = deployUser;
	}

	public Integer getDeployStatus() {
		return deployStatus;
	}

	public void setDeployStatus(Integer deployStatus) {
		this.deployStatus = deployStatus;
	}

	public String getDeployMark() {
		return deployMark;
	}

	public void setDeployMark(String deployMark) {
		this.deployMark = deployMark;
	}

	public Integer getRestart() {
		return restart;
	}

	public void setRestart(Integer restart) {
		this.restart = restart;
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

	public String getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(String versionDate) {
		this.versionDate = versionDate;
	}
	
}
