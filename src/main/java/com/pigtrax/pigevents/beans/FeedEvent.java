package com.pigtrax.pigevents.beans;

import java.math.BigDecimal;
import java.util.Date;

public class FeedEvent {
	
	private Integer id;
	private String ticketNumber;
	private String feedContentId;
	private Date initialFeedEntryDateTime;
	private String batchId;
	private Integer initialFeedQuantityKgs;
	private BigDecimal feedCost;
	private String feedMedication;
	private Integer transportJourneyId;
	private Date lastUpdated;
	private String userUpdated;
	private TransportJourney transportJourney; 
	
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
	public String getFeedContentId() {
		return feedContentId;
	}
	public void setFeedContentId(String feedContentId) {
		this.feedContentId = feedContentId;
	}
	public Date getInitialFeedEntryDateTime() {
		return initialFeedEntryDateTime;
	}
	public void setInitialFeedEntryDateTime(Date initialFeedEntryDateTime) {
		this.initialFeedEntryDateTime = initialFeedEntryDateTime;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public Integer getInitialFeedQuantityKgs() {
		return initialFeedQuantityKgs;
	}
	public void setInitialFeedQuantityKgs(Integer initialFeedQuantityKgs) {
		this.initialFeedQuantityKgs = initialFeedQuantityKgs;
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
	public Integer getTransportJourneyId() {
		return transportJourneyId;
	}
	public void setTransportJourneyId(Integer transportJourneyId) {
		this.transportJourneyId = transportJourneyId;
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
	public TransportJourney getTransportJourney() {
		return transportJourney;
	}
	public void setTransportJourney(TransportJourney transportJourney) {
		this.transportJourney = transportJourney;
	}
	
}
