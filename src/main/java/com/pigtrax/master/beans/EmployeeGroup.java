package com.pigtrax.master.beans;

import java.util.Date;

public class EmployeeGroup {
	private int id;
	private String groupId;
	private boolean isActive;
	private Date lastUpdated;
	private String userUpdated;
	private int employeeJobFunctionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public int getEmployeeJobFunctionId() {
		return employeeJobFunctionId;
	}

	public void setEmployeeJobFunctionId(int employeeJobFunctionId) {
		this.employeeJobFunctionId = employeeJobFunctionId;
	}

}
