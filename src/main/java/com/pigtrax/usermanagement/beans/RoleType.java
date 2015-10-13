package com.pigtrax.usermanagement.beans;

import java.util.Date;

public class RoleType {
	private int id;
	private int fieldCode;
	private String fieldDescription;
	private Date lastUpdated;
	private String userUpdated;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(int fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getFieldDescription() {
		return fieldDescription;
	}
	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
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
