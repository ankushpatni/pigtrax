package com.pigtrax.usermanagement.enums;

public enum PigletStatusEventType {
	FosterIn(1),
	FosterOut(2),
	Wean(3),
	Death(4);
	
	private final int role;
	
	PigletStatusEventType(int role) {
		this.role = role;
	}
	
	public int getTypeCode(){
		return role;
	}
}
