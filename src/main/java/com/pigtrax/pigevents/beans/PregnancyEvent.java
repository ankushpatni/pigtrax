package com.pigtrax.pigevents.beans;

import java.util.Date;

public class PregnancyEvent {
	private Integer id;
	private Integer pigInfoId;
	private Integer employeeGroupId;
	private Integer pregnancyEventTypeId;
	private Integer pregnancyExamResultTypeId;
	private Date examDate;
	private Date resultDate;
	private Integer sowCondition;
	private Date lastUpdated;
	private String userUpdated;
	private int breedingEventId;

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

	public int getBreedingEventId() {
		return breedingEventId;
	}

	public void setBreedingEventId(int breedingEventId) {
		this.breedingEventId = breedingEventId;
	}

	
	
}
