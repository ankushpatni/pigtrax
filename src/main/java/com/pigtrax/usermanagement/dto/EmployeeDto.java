package com.pigtrax.usermanagement.dto;

public class EmployeeDto {
	private Integer id;
     private String employeeId;
     private String name;
     private String ptPassword;
     private Integer companyId;
     private Integer userRole;
     private boolean isActive;
     private boolean isPortalUser;
     private Integer userRoleId;
     private boolean selected;
     private Integer employeeJobId;
     
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getUserRole() {
		return userRole;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	public String getEmployeeId() {
		return employeeId;
	}
     
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	}
	public boolean isPortalUser() {
		return isPortalUser;
	}
	public void setPortalUser(boolean isPortalUser) {
		this.isPortalUser = isPortalUser;
	}
	public Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	
	public Integer getEmployeeJobId() {
		return employeeJobId;
	}
	public void setEmployeeJobId(Integer employeeJobId) {
		this.employeeJobId = employeeJobId;
	}
	@Override
	public String toString() {		
		return "Employee [ empId : "+this.employeeId+", name : "+this.name+"]";
	}
	
	@Override
	public boolean equals(Object obj) {
		EmployeeDto dto = (EmployeeDto) obj;
		int idVal1 = (this.id != null) ?this.id.intValue() : 0;
		int idVal2 = (dto.getId() != null) ?dto.getId().intValue() : 0;
		return (idVal1 == idVal2);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.id.hashCode();
	}
}
