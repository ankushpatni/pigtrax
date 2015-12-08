package com.pigtrax.pigevents.dto;

import java.util.Date;

import com.pigtrax.master.dto.EmployeeGroupDto;

public class MatingDetailsDto {
	private Integer matingDetailId;
	private Date matingDate;
	private String semenId;
	private Integer matingQuality;
	private Integer employeeGroupId;
	private Date lastUpdated;
	private String userUpdated;
	private Integer breedingEventId;	
	private EmployeeGroupDto employeeGroup;
	private Date semenDate;
	

	public Integer getMatingDetailId() {
		return matingDetailId;
	}

	public void setMatingDetailId(Integer matingDetailId) {
		this.matingDetailId = matingDetailId;
	}

	public Date getMatingDate() {
		return matingDate;
	}

	public void setMatingDate(Date matingDate) {
		this.matingDate = matingDate;
	}

	public String getSemenId() {
		return semenId;
	}

	public void setSemenId(String semenId) {
		this.semenId = semenId;
	}

	public Integer getMatingQuality() {
		return matingQuality;
	}

	public void setMatingQuality(Integer matingQuality) {
		this.matingQuality = matingQuality;
	}

	public Integer getEmployeeGroupId() {
		return employeeGroupId;
	}

	public void setEmployeeGroupId(Integer employeeGroupId) {
		this.employeeGroupId = employeeGroupId;
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

	public Integer getBreedingEventId() {
		return breedingEventId;
	}

	public void setBreedingEventId(Integer breedingEventId) {
		this.breedingEventId = breedingEventId;
	}

	public EmployeeGroupDto getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(EmployeeGroupDto employeeGroupDto) {
		this.employeeGroup = employeeGroupDto;
	}

	public Date getSemenDate() {
		return semenDate;
	}

	public void setSemenDate(Date semenDate) {
		this.semenDate = semenDate;
	}
	
	

}
