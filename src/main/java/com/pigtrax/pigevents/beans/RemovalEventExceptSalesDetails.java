package com.pigtrax.pigevents.beans;

import java.math.BigDecimal;
import java.util.Date;

public class RemovalEventExceptSalesDetails {
	
	private Integer id;
	private Integer numberOfPigs;	
	private Date removalDateTime;
	private Integer pigInfoId;
	private Integer groupEventId;
	private BigDecimal weightInKgs;
	private Integer removalEventId;
	private Integer barnId;
	private Date lastUpdated;
	private String userUpdated;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNumberOfPigs() {
		return numberOfPigs;
	}
	public void setNumberOfPigs(Integer numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}
	public Date getRemovalDateTime() {
		return removalDateTime;
	}
	public void setRemovalDateTime(Date removalDateTime) {
		this.removalDateTime = removalDateTime;
	}
	public Integer getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	public Integer getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}
	public BigDecimal getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(BigDecimal weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public Integer getRemovalEventId() {
		return removalEventId;
	}
	public void setRemovalEventId(Integer removalEventId) {
		this.removalEventId = removalEventId;
	}
	public Integer getBarnId() {
		return barnId;
	}
	public void setBarnId(Integer barnId) {
		this.barnId = barnId;
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

}
