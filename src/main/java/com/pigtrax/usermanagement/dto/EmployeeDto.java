package com.pigtrax.usermanagement.dto;

public class EmployeeDto {
     private Integer empId;
     private String name;
     private String email;
     private Integer companyId;
     private Integer userRole;
     
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Integer getEmpId() {
		return empId;
	}
     
	@Override
	public String toString() {		
		return "Employee [ empId : "+this.empId+", name : "+this.name+", email : "+this.email+"]";
	}
}
