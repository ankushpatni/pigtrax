package com.pigtrax.pigevents.beans;

import java.util.Date;

public class MatingDetails {
	private Integer matingDetailId;
	private Date matingDate;
	private String semenId;
	private Integer matingQuality;
	private Integer employeeGroupId;
	private Date lastUpdated;
	private String userUpdated;
	private Integer breedingEventId;

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

}
