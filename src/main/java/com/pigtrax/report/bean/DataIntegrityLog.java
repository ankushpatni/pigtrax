package com.pigtrax.report.bean;

import java.util.Date;

public class DataIntegrityLog {
	private String eventType;
	private String relevantField;
	private String errorType;
	private Date eventDate;
	private String errorDescription;
	private Integer companyId;
	private String userId;
	private Integer premiseId;
	private String premise;
	
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRelevantField() {
		return relevantField;
	}

	public void setRelevantField(String relevantField) {
		this.relevantField = relevantField;
	}

	public Integer getPremiseId() {
		return premiseId;
	}

	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}

	public String getPremise() {
		return premise;
	}

	public void setPremise(String premise) {
		this.premise = premise;
	}

	
	
}
