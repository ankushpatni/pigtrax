package com.pigtrax.application.exception;

public class PigTraxException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sqlState;

	public PigTraxException(String message, String sqlState) {
		super(message);
		this.sqlState = sqlState;
	}

	public String getSqlState() {
		return this.sqlState;
	}
}
