package com.pigtrax.pigevents.dto;

import java.util.Date;

import com.pigtrax.master.dto.EmployeeGroupDto;

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
    private EmployeeGroupDto employeeGroup;
	private String searchText;
	private String searchOption;
	private String pregnancyEventType;
	private String language;
	private int breedingEventId;
	private BreedingEventDto breedingEventDto;
	private String breedingServiceId;
	private Integer premiseId;
	private Integer selectedPremise;
	private String resultDateStr;

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

	public EmployeeGroupDto getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(EmployeeGroupDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public String getPregnancyEventType() {
		return pregnancyEventType;
	}

	public void setPregnancyEventType(String pregnancyEventType) {
		this.pregnancyEventType = pregnancyEventType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getBreedingEventId() {
		return breedingEventId;
	}

	public void setBreedingEventId(int breedingEventId) {
		this.breedingEventId = breedingEventId;
	}

	public BreedingEventDto getBreedingEventDto() {
		return breedingEventDto;
	}

	public void setBreedingEventDto(BreedingEventDto breedingEventDto) {
		this.breedingEventDto = breedingEventDto;
	}

	public String getBreedingServiceId() {
		return breedingServiceId;
	}

	public void setBreedingServiceId(String breedingServiceId) {
		this.breedingServiceId = breedingServiceId;
	}

	public Integer getPremiseId() {
		return premiseId;
	}

	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}

	public Integer getSelectedPremise() {
		return selectedPremise;
	}

	public void setSelectedPremise(Integer selectedPremise) {
		this.selectedPremise = selectedPremise;
	}

	public String getResultDateStr() {
		return resultDateStr;
	}

	public void setResultDateStr(String resultDateStr) {
		this.resultDateStr = resultDateStr;
	}
	
	
	
}
