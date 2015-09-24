package com.pigtrax.usermanagement.enums;

public enum RoleType {
	PigTraxSuperAdmin("ROLE_1"),
	PigTraxDataConfigMgr("ROLE_2"),
	PigFarmSuperAdmin("ROLE_3"),
	PigFarmDataConfigMgr("ROLE_4");
	
	private final String role;
	
	RoleType(String role) {
		this.role = role;
	}
	
	public String getRoleValue(){
		return role;
	}
}
