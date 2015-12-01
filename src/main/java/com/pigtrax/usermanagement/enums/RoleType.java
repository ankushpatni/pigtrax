package com.pigtrax.usermanagement.enums;

public enum RoleType {
	PigTraxSuperAdmin("ROLE_1"),
	PigTraxDataConfigMgr("ROLE_2"),
	DataManager("ROLE_3"),
	User("ROLE_4");
	
	private final String role;
	
	RoleType(String role) {
		this.role = role;
	}
	
	public String getRoleValue(){
		return role;
	}
}
