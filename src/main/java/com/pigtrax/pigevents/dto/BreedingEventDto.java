package com.pigtrax.pigevents.dto;

import java.util.Date;

import com.pigtrax.master.beans.EmployeeGroup;
import com.pigtrax.master.dto.EmployeeGroupDto;

public class BreedingEventDto {
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
    private Integer companyId;
    private Integer pigInfoKey;
    private EmployeeGroupDto employeeGroup;
    private Date pigBirthDate;
	private String searchText;
	private String searchOption;
	private String breedingServiceType;
	private String language;
	private boolean gestationRecord;
	private Date gestationRecordDate;
    
	
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
    
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public EmployeeGroupDto getEmployeeGroup() {
		return employeeGroup;
	}
	public void setEmployeeGroup(EmployeeGroupDto employeeGroup) {
		this.employeeGroup = employeeGroup;
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
	
	public boolean isGestationRecord() {
		return gestationRecord;
	}
	public void setGestationRecord(boolean gestationRecord) {
		this.gestationRecord = gestationRecord;
	}
	public Date getGestationRecordDate() {
		return gestationRecordDate;
	}
	public void setGestationRecordDate(Date gestationRecordDate) {
		this.gestationRecordDate = gestationRecordDate;
	}
	@Override
    public String toString() {
    	String str = " BreedingEventDto "
    			+ " [ id : "+this.id
    			+", serviceId : "+this.serviceId
    			+", EmployeeGroupId : "+this.employeeGroupId
    			+", pigInfoId : "+this.pigInfoId
    			+", breedingServiceTypeId :" +this.breedingServiceTypeId
    			+", breedingGroupId  : "+this.breedingGroupId
    			+", breedingDate : "+this.breedingDate
    			+", semenId : "+this.semenId
    			+", remarks : "+this.remarks
    			+", mateQuality : "+this.mateQuality
    			+", sowCondition : "+this.sowCondition
    			+", lastUpdated : "+this.lastUpdated
    			+", userUpdated : "+this.userUpdated
    			+", companyId : "+this.companyId+"]";
    	return str;
    };
}
