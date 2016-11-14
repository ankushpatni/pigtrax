package com.pigtrax.usermanagement.enums;

public enum GroupEventActionType {
	
	Add(1),
	Removal(2),
	Sold(3),
	Transferred(4);
	
	private final int type;
	
	GroupEventActionType(int type) {
		this.type = type;
	}
	
	public int getTypeCode(){
		return type;
	}

}
