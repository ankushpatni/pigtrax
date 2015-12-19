package com.pigtrax.pigevents.dto;

import java.util.Date;

public class CompanyTargetDto {
	private Integer id;
	private Integer targetId;
	private String targetValue;
	private Date completionDate;
	private String remarks;
	private Integer companyId;
	private Date lastUpdated;
	private String userUpdated;
	private String language;
	private String targetName;
	private String completionDateStr;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getCompletionDateStr() {
		return completionDateStr;
	}

	public void setCompletionDateStr(String completionDateStr) {
		this.completionDateStr = completionDateStr;
	}
	
	

}
