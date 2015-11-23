package com.pigtrax.usermanagement.beans;

import java.util.Date;

public class Employee {
	private int id;
	private String employeeId;
	private int companyId;
	private String name;
	private String ptPassword;
	private String isActiveR;
	private String isPortR;
	private boolean active;
	private Date lastUpdated;
	private String userUpdated;
	private int userRoleId;
	private boolean portalUser;
	private String email;
	private int portalId;
	private String companyIdValue;
	private String companyName;
	private Integer functionTypeId;
	private Integer jobFunctionRoleId;
	private String phoneNumber;
	private Date functionStartDate;
	private Date functionEndDate;
	private String empFunctionName;

	
	public int getPortalId() {
		return portalId;
	}

	public void setPortalId(int portalId) {
		this.portalId = portalId;
	}

	public String getIsActiveR() {
		return isActiveR;
	}

	public void setIsActiveR(String isActiveR) {
		this.isActiveR = isActiveR;
	}

	public String getIsPortR() {
		return isPortR;
	}

	public void setIsPortR(String isPortR) {
		this.isPortR = isPortR;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		this.enabled = this.active;
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
		return portalUser;
	}

	public void setPortalUser(boolean portalUser) {
		this.portalUser = portalUser;
	}
	
	

	public String getCompanyIdValue() {
		return companyIdValue;
	}

	public void setCompanyIdValue(String companyIdValue) {
		this.companyIdValue = companyIdValue;
	}
	
	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

	public Integer getFunctionTypeId() {
		return functionTypeId;
	}

	public void setFunctionTypeId(Integer functionTypeId) {
		this.functionTypeId = functionTypeId;
	}
	
	

	public Integer getJobFunctionRoleId() {
		return jobFunctionRoleId;
	}

	public void setJobFunctionRoleId(Integer jobFunctionRoleId) {
		this.jobFunctionRoleId = jobFunctionRoleId;
	}

	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

	public Date getFunctionStartDate() {
		return functionStartDate;
	}

	public void setFunctionStartDate(Date functionStartDate) {
		this.functionStartDate = functionStartDate;
	}

	public Date getFunctionEndDate() {
		return functionEndDate;
	}

	public void setFunctionEndDate(Date functionEndDate) {
		this.functionEndDate = functionEndDate;
	}
	
	

	public String getEmpFunctionName() {
		return empFunctionName;
	}

	public void setEmpFunctionName(String empFunctionName) {
		this.empFunctionName = empFunctionName;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeId=" + employeeId
				+ ", companyId=" + companyId + ", name=" + name + ",email="
				+ email + ", ptPassword=" + ptPassword + ", active="
				+ active + ", lastUpdated=" + lastUpdated + ", userUpdated="
				+ userUpdated + ", userRoleId=" + userRoleId
				+ ", portalUser=" + portalUser + ", enabled=" + enabled
				+ ",portalId="+portalId+"]";
	}

}
