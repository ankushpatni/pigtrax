package com.pigtrax.pigevents.beans;

import java.math.BigDecimal;
import java.util.Date;

public class FeedEventDetail {
	
	private Integer id;
	private Date feedEventDate;
	private BigDecimal weightInKgs;
	private String remarks;
	private Integer feedEventId;
	private Integer siloId;
	private Integer groupEventId;
	private Integer feedEventTypeId;
	private Date lastUpdated;
	private String userUpdated;
	private String groupEventGroupId;
	private String feedEventDateStr;
	private String feedMill;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFeedEventDate() {
		return feedEventDate;
	}
	public void setFeedEventDate(Date feedEventDate) {
		this.feedEventDate = feedEventDate;
	}
	public BigDecimal getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(BigDecimal weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getFeedEventId() {
		return feedEventId;
	}
	public void setFeedEventId(Integer feedEventId) {
		this.feedEventId = feedEventId;
	}
	public Integer getSiloId() {
		return siloId;
	}
	public void setSiloId(Integer siloId) {
		this.siloId = siloId;
	}
	public Integer getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}
	public Integer getFeedEventTypeId() {
		return feedEventTypeId;
	}
	public void setFeedEventTypeId(Integer feedEventTypeId) {
		this.feedEventTypeId = feedEventTypeId;
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
	public String getGroupEventGroupId() {
		return groupEventGroupId;
	}
	public void setGroupEventGroupId(String groupEventGroupId) {
		this.groupEventGroupId = groupEventGroupId;
	}
	public String getFeedEventDateStr() {
		return feedEventDateStr;
	}
	public void setFeedEventDateStr(String feedEventDateStr) {
		this.feedEventDateStr = feedEventDateStr;
	}
	public String getFeedMill() {
		return feedMill;
	}
	public void setFeedMill(String feedMill) {
		this.feedMill = feedMill;
	}
	
	
	
}