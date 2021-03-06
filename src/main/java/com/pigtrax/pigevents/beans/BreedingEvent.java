package com.pigtrax.pigevents.beans;

import java.util.Date;

public class BreedingEvent {
    private Integer id;
    private  Integer breedingServiceTypeId;
    private String breedingGroupId;
    private Integer sowCondition;
    private Integer penId;
    private Date lastUpdated;
    private String userUpdated;
    private Integer pigInfoId;
    private String breedingServiceType;
    private Double weightInKgs;
    private Date serviceStartDate;
    private Integer premiseId;
    private Integer currentParity;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public String getBreedingServiceType() {
		return breedingServiceType;
	}
	public void setBreedingServiceType(String breedingServiceType) {
		this.breedingServiceType = breedingServiceType;
	}
	public Integer getPenId() {
		return penId;
	}
	public void setPenId(Integer penId) {
		this.penId = penId;
	}
	public Integer getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	public Double getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(Double weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public Date getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	public Integer getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}
	public Integer getCurrentParity() {
		return currentParity;
	}
	public void setCurrentParity(Integer currentParity) {
		this.currentParity = currentParity;
	}
	
	
    
}
