package com.pigtrax.master.dto;

import java.util.Date;

public class MasterRationDto {
	private Integer id;
	private String rationValue;
	private Integer feedTypeId;
	private String feedType;
	private String userUpdated;
	private Date lastUpdated;
	private String rationDescription;
	private Integer rationTypeId;
	private String rationType; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRationValue() {
		return rationValue;
	}

	public void setRationValue(String rationValue) {
		this.rationValue = rationValue;
	}

	public Integer getFeedTypeId() {
		return feedTypeId;
	}

	public void setFeedTypeId(Integer feedTypeId) {
		this.feedTypeId = feedTypeId;
	}

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public String getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getRationDescription() {
		return rationDescription;
	}

	public void setRationDescription(String rationDescription) {
		this.rationDescription = rationDescription;
	}

	public Integer getRationTypeId() {
		return rationTypeId;
	}

	public void setRationTypeId(Integer rationTypeId) {
		this.rationTypeId = rationTypeId;
	}

	public String getRationType() {
		return rationType;
	}

	public void setRationType(String rationType) {
		this.rationType = rationType;
	}
	
	

}
