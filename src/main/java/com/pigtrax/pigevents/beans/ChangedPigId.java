package com.pigtrax.pigevents.beans;

import java.util.Date;

public class ChangedPigId {
	
	private Integer id;
	private String oldSowId;
	private String changedSowId;
	private Date changeDateTime;
	private Integer companyId;
	private Date lastUpdated;
	private String userUpdated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOldSowId() {
		return oldSowId;
	}
	public void setOldSowId(String oldSowId) {
		this.oldSowId = oldSowId;
	}
	public String getChangedSowId() {
		return changedSowId;
	}
	public void setChangedSowId(String changedSowId) {
		this.changedSowId = changedSowId;
	}
	public Date getChangeDateTime() {
		return changeDateTime;
	}
	public void setChangeDateTime(Date changeDateTime) {
		this.changeDateTime = changeDateTime;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getUserUpdated() {
		return userUpdated;
	}
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}
	
	
	
}
