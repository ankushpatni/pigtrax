package com.pigtrax.application.exception;

public class PigTraxException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sqlState;
	
	private boolean duplicateStatus;

	public PigTraxException(String message, String sqlState) {
		super(message);
		this.sqlState = sqlState;
	}
	
	public PigTraxException(String message, String sqlState, boolean duplicateStatus) {
		super(message);
		this.sqlState = sqlState;
		this.duplicateStatus = true;
	}

	public String getSqlState() {
		return this.sqlState;
	}

	public boolean isDuplicateStatus() {
		return duplicateStatus;
	}

	public void setDuplicateStatus(boolean duplicateStatus) {
		this.duplicateStatus = duplicateStatus;
	}

	public void setSqlState(String sqlState) {
		this.sqlState = sqlState;
	}
	
	
}
