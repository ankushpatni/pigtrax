package com.pigtrax.usermanagement.enums;

public enum PigletStatusEventType {
	Wean(1),
	FosterIn(2),
	FosterOut(3),
	Death(4);
	
	private final int role;
	
	PigletStatusEventType(int role) {
		this.role = role;
	}
	
	public int getTypeCode(){
		return role;
	}
}
