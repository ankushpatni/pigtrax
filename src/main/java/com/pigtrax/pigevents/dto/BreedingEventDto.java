package com.pigtrax.pigevents.dto;

import java.util.Date;

import com.pigtrax.master.beans.EmployeeGroup;

public class BreedingEventDto {
    private Integer id;
    private String serviceId;
    private Integer employeeGroupId;
    private Integer pigInfoId;
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
    private EmployeeGroup employeeGroup;
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
	public Integer getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(Integer pigInfoId) {
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
	
	public EmployeeGroup getEmployeeGroup() {
		return employeeGroup;
	}
	public void setEmployeeGroup(EmployeeGroup employeeGroup) {
		this.employeeGroup = employeeGroup;
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
