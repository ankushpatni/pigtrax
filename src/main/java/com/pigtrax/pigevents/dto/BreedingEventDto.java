package com.pigtrax.pigevents.dto;

import java.util.Date;
import java.util.List;

public class BreedingEventDto {
    private Integer id;
    private String pigInfoId;
    private  Integer breedingServiceTypeId;
    private String breedingGroupId;
    private Integer sowCondition;
    private Integer penId;
    private Date serviceStartDate;
    private Date lastUpdated;
    private String userUpdated;
    private Integer companyId;
    private Integer pigInfoKey;
    private Date pigBirthDate;
	private String searchText;
	private String searchOption;
	private String breedingServiceType;
	private String language;
	private Double weightInKgs;
    private List<MatingDetailsDto> matingDetailsList;
    private Integer premiseId;
    private Integer selectedPremise;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(String pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	public Integer getBreedingServiceTypeId() {
		return breedingServiceTypeId;
	}
	public void setBreedingServiceTypeId(Integer breedingServiceTypeId) {
		this.breedingServiceTypeId = breedingServiceTypeId;
	}
	public String getBreedingGroupId() {
		return breedingGroupId;
	}
	public void setBreedingGroupId(String breedingGroupId) {
		this.breedingGroupId = breedingGroupId;
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
    
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
		
	public Integer getPigInfoKey() {
		return pigInfoKey;
	}
	public void setPigInfoKey(Integer pigInfoKey) {
		this.pigInfoKey = pigInfoKey;
	}
		
	public Date getPigBirthDate() {
		return pigBirthDate;
	}
	public void setPigBirthDate(Date pigBirthDate) {
		this.pigBirthDate = pigBirthDate;
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
	public String getBreedingServiceType() {
		return breedingServiceType;
	}
	public void setBreedingServiceType(String breedingServiceType) {
		this.breedingServiceType = breedingServiceType;
	}
		
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Integer getPenId() {
		return penId;
	}
	public void setPenId(Integer penId) {
		this.penId = penId;
	}
	public Date getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	
	public Double getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(Double weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public List<MatingDetailsDto> getMatingDetailsList() {
		return matingDetailsList;
	}
	public void setMatingDetailsList(List<MatingDetailsDto> matingDetailsList) {
		this.matingDetailsList = matingDetailsList;
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
	@Override
    public String toString() {
    	String str = " BreedingEventDto "
    			+ " [ id : "+this.id
    			+", pigInfoId : "+this.pigInfoId
    			+", breedingServiceTypeId :" +this.breedingServiceTypeId
    			+", breedingGroupId  : "+this.breedingGroupId
    			+", sowCondition : "+this.sowCondition
    			+", lastUpdated : "+this.lastUpdated
    			+", userUpdated : "+this.userUpdated
    			+", companyId : "+this.companyId+"]";
    	return str;
    };
}
