package com.pigtrax.pigevents.dto;

import java.util.Date;

public class PregnancyEventDto {
	private Integer id;
	private Integer pigInfoId;
	private String pigId;
	private Integer employeeGroupId;
	private Integer pregnancyEventTypeId;
	private Integer pregnancyExamResultTypeId;
	private Date examDate;
	private Date resultDate;
	private Integer sowCondition;
	private Date lastUpdated;
	private String userUpdated;
	private int companyId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPigInfoId() {
		return pigInfoId;
	}

	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	
	

	public String getPigId() {
		return pigId;
	}

	public void setPigId(String pigId) {
		this.pigId = pigId;
	}

	public Integer getEmployeeGroupId() {
		return employeeGroupId;
	}

	public void setEmployeeGroupId(Integer employeeGroupId) {
		this.employeeGroupId = employeeGroupId;
	}

	public Integer getPregnancyEventTypeId() {
		return pregnancyEventTypeId;
	}

	public void setPregnancyEventTypeId(Integer pregnancyEventTypeId) {
		this.pregnancyEventTypeId = pregnancyEventTypeId;
	}

	public Integer getPregnancyExamResultTypeId() {
		return pregnancyExamResultTypeId;
	}

	public void setPregnancyExamResultTypeId(Integer pregnancyExamResultTypeId) {
		this.pregnancyExamResultTypeId = pregnancyExamResultTypeId;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	public Integer getSowCondition() {
		return sowCondition;
	}

	public void setSowCondition(Integer sowCondition) {
		this.sowCondition = sowCondition;
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	
	
}
