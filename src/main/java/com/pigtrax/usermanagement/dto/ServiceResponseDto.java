package com.pigtrax.usermanagement.dto;

import java.io.Serializable;
/**
 * Response DTO designed to handle all service call responses
 * @author Vidya
 *
 */
public class ServiceResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String statusMessage;
	private Object payload;
	private boolean duplicateRecord;

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}
	
   
	public boolean isDuplicateRecord() {
		return duplicateRecord;
	}

	public void setDuplicateRecord(boolean duplicateRecord) {
		this.duplicateRecord = duplicateRecord;
	}

	/**
	 * checks the statusMessage attribute
	 * and correspondingly set the error as true/false
	 * @return
	 */
	public Boolean getError() {
		Boolean rc = false;
		if (null != this.statusMessage && this.statusMessage.startsWith("ERR")) {
			rc = true;
		}
		return rc;
	}
}
