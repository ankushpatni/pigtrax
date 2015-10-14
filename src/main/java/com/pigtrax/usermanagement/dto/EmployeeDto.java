package com.pigtrax.usermanagement.dto;

public class EmployeeDto {
	private Integer id;
     private String employeeId;
     private String name;
     private String ptPassword;
     private Integer companyId;
     private Integer userRole;
     private boolean active;
     private boolean portalUser;
     private Integer userRoleId;
	  private boolean selected;
     private Integer employeeJobId;
     private String companyName;
	 private String otPassword;
     private String email;
     private int portalId;
     private String activeFlag;
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public int getPortalId() {
		return portalId;
	}
	public void setPortalId(int portalId) {
		this.portalId = portalId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {		
		return name != null?name : "";
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
	public String getEmail() {
		return email != null?email : "";
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	public String getEmployeeId() {
		return employeeId != null?employeeId : "";
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
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isPortalUser() {
		return portalUser;
	}
	public void setPortalUser(boolean portalUser) {
		this.portalUser = portalUser;
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
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getEmployeeJobId() {
		return employeeJobId;
	}
	public void setEmployeeJobId(Integer employeeJobId) {
		this.employeeJobId = employeeJobId;
	}
	
	public String getOtPassword() {
		return otPassword;
	}
	public void setOtPassword(String otPassword) {
		this.otPassword = otPassword;
	}
	@Override
	public String toString() {		
		return "Employee [id:"+this.id+" ,  empId : "+this.employeeId+", name : "+this.name+", email : "+this.email+", companyId : "+this.companyId+"  ,active:"+this.active+" , activeFlag : "+this.activeFlag+"]";
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
