package com.pigtrax.pigevents.beans;

import java.util.Date;

public class BreedingEvent {
    private Integer id;
    private String serviceId;
    private Integer employeeGroupId;
    private String pigInfoId;
    private  Integer breedingServiceTypeId;
    private String breedingGroupId;
    private Date breedingDate;
    private String semenId;
    private String remarks;
    private Integer mateQuality;
    private Integer sowCondition;
    private Date lastUpdated;
    private String userUpdated;
    private Integer pigInfoKey;
    private String breedingServiceType;
    private boolean gestationRecord;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getEmployeeGroupId() {
		return employeeGroupId;
	}
	public void setEmployeeGroupId(Integer employeeGroupId) {
		this.employeeGroupId = employeeGroupId;
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
	public Date getBreedingDate() {
		return breedingDate;
	}
	public void setBreedingDate(Date breedingDate) {
		this.breedingDate = breedingDate;
	}
	public String getSemenId() {
		return semenId;
	}
	public void setSemenId(String semenId) {
		this.semenId = semenId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getMateQuality() {
		return mateQuality;
	}
	public void setMateQuality(Integer mateQuality) {
		this.mateQuality = mateQuality;
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
	public Integer getPigInfoKey() {
		return pigInfoKey;
	}
	public void setPigInfoKey(Integer pigInfoKey) {
		this.pigInfoKey = pigInfoKey;
	}
	public String getBreedingServiceType() {
		return breedingServiceType;
	}
	public void setBreedingServiceType(String breedingServiceType) {
		this.breedingServiceType = breedingServiceType;
	}
	public boolean isGestationRecord() {
		return gestationRecord;
	}
	public void setGestationRecord(boolean gestationRecord) {
		this.gestationRecord = gestationRecord;
	}
    
    
}
