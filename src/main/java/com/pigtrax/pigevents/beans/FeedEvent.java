package com.pigtrax.pigevents.beans;

import java.math.BigDecimal;
import java.util.Date;

public class FeedEvent {
	
	private Integer id;
	private String ticketNumber;
	private Integer feedId;
	private Date feedDateTime;
	private Integer siloId;
	private Integer transportJourneyId;
	private Integer groupEventId;
	private Integer feedEventType;
	private String batchId;
	private Integer feedQuantity;
	private BigDecimal feedCost;
	private String feedMedication;
	private Date lastUpdated;
	private String userUpdated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public Integer getFeedId() {
		return feedId;
	}
	public void setFeedId(Integer feedId) {
		this.feedId = feedId;
	}
	public Date getFeedDateTime() {
		return feedDateTime;
	}
	public void setFeedDateTime(Date feedDateTime) {
		this.feedDateTime = feedDateTime;
	}
	public Integer getSiloId() {
		return siloId;
	}
	public void setSiloId(Integer siloId) {
		this.siloId = siloId;
	}
	public Integer getTransportJourneyId() {
		return transportJourneyId;
	}
	public void setTransportJourneyId(Integer transportJourneyId) {
		this.transportJourneyId = transportJourneyId;
	}
	public Integer getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}
	public Integer getFeedEventType() {
		return feedEventType;
	}
	public void setFeedEventType(Integer feedEventType) {
		this.feedEventType = feedEventType;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public Integer getFeedQuantity() {
		return feedQuantity;
	}
	public void setFeedQuantity(Integer feedQuantity) {
		this.feedQuantity = feedQuantity;
	}
	public BigDecimal getFeedCost() {
		return feedCost;
	}
	public void setFeedCost(BigDecimal feedCost) {
		this.feedCost = feedCost;
	}
	public String getFeedMedication() {
		return feedMedication;
	}
	public void setFeedMedication(String feedMedication) {
		this.feedMedication = feedMedication;
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
