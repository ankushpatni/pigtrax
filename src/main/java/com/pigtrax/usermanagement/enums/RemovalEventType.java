package com.pigtrax.usermanagement.enums;

public enum RemovalEventType {
	
	Death(1),
	AdjustInventory(2),
	Sales(3),
	DOA(4),
	Downer(5),
	Injury(6),
	Miscount(7),
	Mortality(8),
	Transferred(9);
	
	private final int role;
	
	RemovalEventType(int role) {
		this.role = role;
	}
	
	public int getTypeCode(){
		return role;
	}

}
