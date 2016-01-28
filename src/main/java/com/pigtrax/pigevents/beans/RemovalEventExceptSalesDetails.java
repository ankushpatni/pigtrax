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
	private Integer premiseId;
	private Date lastUpdated;
	private String userUpdated;
	private Integer destPremiseId;
	private String remarks;
	private Integer mortalityReasonId;
	
	private Integer transportJourneyId;
	private TransportJourney transportJourney;
	private Integer companyId;
	private BigDecimal revenueUsd;
	private Integer roomId;
	
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
	public Integer getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
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
	public Integer getTransportJourneyId() {
		return transportJourneyId;
	}
	public void setTransportJourneyId(Integer transportJourneyId) {
		this.transportJourneyId = transportJourneyId;
	}
	public TransportJourney getTransportJourney() {
		return transportJourney;
	}
	public void setTransportJourney(TransportJourney transportJourney) {
		this.transportJourney = transportJourney;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getDestPremiseId() {
		return destPremiseId;
	}
	public void setDestPremiseId(Integer destPremiseId) {
		this.destPremiseId = destPremiseId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getMortalityReasonId() {
		return mortalityReasonId;
	}
	public void setMortalityReasonId(Integer mortalityReasonId) {
		this.mortalityReasonId = mortalityReasonId;
	}
	public BigDecimal getRevenueUsd() {
		return revenueUsd;
	}
	public void setRevenueUsd(BigDecimal revenueUsd) {
		this.revenueUsd = revenueUsd;
	}
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	
}
