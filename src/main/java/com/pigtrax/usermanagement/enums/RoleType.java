package com.pigtrax.usermanagement.enums;

public enum RoleType {
	PigTraxSuperAdmin(1),
	PigTraxDataConfigMgr(2),
	PigFarmSuperAdmin(3),
	PigFarmDataConfigMgr(4);
	
	private final Integer roleId;
	
	RoleType(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getIntegerValue(){
		return roleId;
	}
}
