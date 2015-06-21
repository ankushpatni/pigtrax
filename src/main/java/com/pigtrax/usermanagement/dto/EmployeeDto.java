package com.pigtrax.usermanagement.dto;

public class EmployeeDto {
     private Integer empId;
     private String name;
     private String email;
	public Integer getEmpId() {
		return empId;
	}
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
     
	@Override
	public String toString() {		
		return "Employee [ empId : "+this.empId+", name : "+this.name+", email : "+this.email+"]";
	}
}
