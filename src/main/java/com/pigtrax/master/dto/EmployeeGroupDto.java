package com.pigtrax.master.dto;

import java.util.Date;
import java.util.List;

import com.pigtrax.usermanagement.dto.EmployeeDto;
import java.util.List;

public class EmployeeGroupDto {
	private int id;
	private String groupId;
	private boolean isActive;
	private Date lastUpdated;
	private String userUpdated;
	private int employeeJobFunctionId;	
	private String employeeJobFunction;
	private List<EmployeeDto> employeeList;
	private int companyId;
	private boolean selected;

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
	
	

	public List<EmployeeDto> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeDto> employeeList) {
		this.employeeList = employeeList;
	}
	
	

	public String getEmployeeJobFunction() {
		return employeeJobFunction;
	}

	public void setEmployeeJobFunction(String employeeJobFunction) {
		this.employeeJobFunction = employeeJobFunction;
	}
	
	

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
    public String toString() {
    	String str = " EmployeeGroup "
    			+ " [ id : "+this.id
    			+", groupId : "+this.groupId
    			+", isActive : "+this.isActive
    			+", lastUpdated : "+this.lastUpdated
    			+", userUpdated : "+this.userUpdated
    			+", employeeJobFunctionId : "+this.employeeJobFunctionId+"]";
    	return str;
    };
}
