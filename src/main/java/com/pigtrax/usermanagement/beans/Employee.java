package com.pigtrax.usermanagement.beans;

import java.util.Date;

public class Employee {
	private int id;
	private String employeeId;
	private int companyId;
	private String name;
	private String ptPassword;
	private boolean isActive;
	private Date lastUpdated;
	private String userUpdated;
	private int userRoleId;
	private boolean isPortalUser;
	
	private boolean enabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getPtPassword() {
		return ptPassword;
	}

	public void setPtPassword(String ptPassword) {
		this.ptPassword = ptPassword;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		this.enabled = this.isActive;
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

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isPortalUser() {
		return isPortalUser;
	}

	public void setPortalUser(boolean isPortalUser) {
		this.isPortalUser = isPortalUser;
	}

	
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeId=" + employeeId + ", companyId=" + companyId + ", name=" + name + ", ptPassword=" + ptPassword + ", isActive=" + isActive + ", lastUpdated="
				+ lastUpdated + ", userUpdated=" + userUpdated + ", userRoleId=" + userRoleId + ", isPortalUser=" + isPortalUser + ", enabled=" + enabled + "]";
	}

	

}
