package com.pigtrax.pigevents.dto;

import java.util.Date;

import com.pigtrax.master.dto.EmployeeGroupDto;

public class FarrowEventDto {
	
	private Integer id;
	private String farrowId;
	private Date farrowDateTime;
	private Integer penId;
	private Integer liveBorns;
	private Integer stillBorns;
	private Integer mummies;
	private Integer maleBorns;
	private Integer femaleBorns;
	private double weightInKgs;
	private boolean inducedBirth;
	private boolean assistedBirth;
	private String remarks;
	private Integer sowCondition;
	private Date lastUpdated;
	private String userUpdated;
	private Integer employeeGroupId;
	private Integer pigInfoId;
	private Integer pregnancyEventId;
	private String pregnancyEventType;
	private String pigId;
	private EmployeeGroupDto employeeGroup;
	private PregnancyEventDto pregnancyEventDto;
	private BreedingEventDto breedingEventDto;
	private String searchText;
	private String searchOption;
	private Integer companyId;
	private String language;
	private boolean pigletsAdded;
	private Integer teats;
	private Integer pigletConditionId;
	private Integer weakBorns;
	private Integer litterId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFarrowId() {
		return farrowId;
	}
	public void setFarrowId(String farrowId) {
		this.farrowId = farrowId;
	}
	public Date getFarrowDateTime() {
		return farrowDateTime;
	}
	public void setFarrowDateTime(Date farrowDateTime) {
		this.farrowDateTime = farrowDateTime;
	}
	public Integer getPenId() {
		return penId;
	}
	public void setPenId(Integer penId) {
		this.penId = penId;
	}
	public Integer getLiveBorns() {
		return liveBorns;
	}
	public void setLiveBorns(Integer liveBorns) {
		this.liveBorns = liveBorns;
	}
	public Integer getStillBorns() {
		return stillBorns;
	}
	public void setStillBorns(Integer stillBorns) {
		this.stillBorns = stillBorns;
	}
	public Integer getMummies() {
		return mummies;
	}
	public void setMummies(Integer mummies) {
		this.mummies = mummies;
	}
	public Integer getMaleBorns() {
		return maleBorns;
	}
	public void setMaleBorns(Integer maleBorns) {
		this.maleBorns = maleBorns;
	}
	public Integer getFemaleBorns() {
		return femaleBorns;
	}
	public void setFemaleBorns(Integer femaleBorns) {
		this.femaleBorns = femaleBorns;
	}
	public double getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(double weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public boolean isInducedBirth() {
		return inducedBirth;
	}
	public void setInducedBirth(boolean inducedBirth) {
		this.inducedBirth = inducedBirth;
	}
	public boolean isAssistedBirth() {
		return assistedBirth;
	}
	public void setAssistedBirth(boolean assistedBirth) {
		this.assistedBirth = assistedBirth;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public Integer getEmployeeGroupId() {
		return employeeGroupId;
	}
	public void setEmployeeGroupId(Integer employeeGroupId) {
		this.employeeGroupId = employeeGroupId;
	}
	public Integer getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	public Integer getPregnancyEventId() {
		return pregnancyEventId;
	}
	public void setPregnancyEventId(Integer pregnancyEventId) {
		this.pregnancyEventId = pregnancyEventId;
	}
	public String getPigId() {
		return pigId;
	}
	public void setPigId(String pigId) {
		this.pigId = pigId;
	}
	public EmployeeGroupDto getEmployeeGroup() {
		return employeeGroup;
	}
	public void setEmployeeGroup(EmployeeGroupDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}
	public PregnancyEventDto getPregnancyEventDto() {
		return pregnancyEventDto;
	}
	public void setPregnancyEventDto(PregnancyEventDto pregnancyEventDto) {
		this.pregnancyEventDto = pregnancyEventDto;
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
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public boolean isPigletsAdded() {
		return pigletsAdded;
	}
	public void setPigletsAdded(boolean pigletsAdded) {
		this.pigletsAdded = pigletsAdded;
	}
	public Integer getTeats() {
		return teats;
	}
	public void setTeats(Integer teats) {
		this.teats = teats;
	}
	public BreedingEventDto getBreedingEventDto() {
		return breedingEventDto;
	}
	public void setBreedingEventDto(BreedingEventDto breedingEventDto) {
		this.breedingEventDto = breedingEventDto;
	}
	public Integer getPigletConditionId() {
		return pigletConditionId;
	}
	public void setPigletConditionId(Integer pigletConditionId) {
		this.pigletConditionId = pigletConditionId;
	}
	public Integer getWeakBorns() {
		return weakBorns;
	}
	public void setWeakBorns(Integer weakBorns) {
		this.weakBorns = weakBorns;
	}
	public Integer getLitterId() {
		return litterId;
	}
	public void setLitterId(Integer litterId) {
		this.litterId = litterId;
	}
	
	 
	
}
