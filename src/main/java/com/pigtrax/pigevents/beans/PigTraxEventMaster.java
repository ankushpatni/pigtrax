package com.pigtrax.pigevents.beans;

import java.util.Date;

public class PigTraxEventMaster {
     private Integer id;
     private Date eventTime;
     private Integer groupEventId;
     private Integer breedingEventId;
     private Integer pregnancyEventId;
     private Integer farrowEventId;
     private Integer pigletStatusId;
     private Date lastUpdated;
     private String userUpdated;
     private Integer saleEventId;
     private Integer pigInfoId;
     private Integer feedEventId;
     private Integer removalEventId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	public Integer getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}
	public Integer getBreedingEventId() {
		return breedingEventId;
	}
	public void setBreedingEventId(Integer breedingEventId) {
		this.breedingEventId = breedingEventId;
	}
	public Integer getPregnancyEventId() {
		return pregnancyEventId;
	}
	public void setPregnancyEventId(Integer pregnancyEventId) {
		this.pregnancyEventId = pregnancyEventId;
	}
	public Integer getFarrowEventId() {
		return farrowEventId;
	}
	public void setFarrowEventId(Integer farrowEventId) {
		this.farrowEventId = farrowEventId;
	}
	public Integer getPigletStatusId() {
		return pigletStatusId;
	}
	public void setPigletStatusId(Integer pigletStatusId) {
		this.pigletStatusId = pigletStatusId;
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
	public Integer getSaleEventId() {
		return saleEventId;
	}
	public void setSaleEventId(Integer saleEventId) {
		this.saleEventId = saleEventId;
	}
	public Integer getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	public Integer getFeedEventId() {
		return feedEventId;
	}
	public void setFeedEventId(Integer feedEventId) {
		this.feedEventId = feedEventId;
	}
	public Integer getRemovalEventId() {
		return removalEventId;
	}
	public void setRemovalEventId(Integer removalEventId) {
		this.removalEventId = removalEventId;
	}
     
}
