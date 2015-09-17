package com.pigtrax.pigevents.beans;

import java.util.Date;

public class RemovalEvent {
	
	private Integer id;
	private String removalId;
	private Integer removalTypeId;
	//private Integer transportJourneyId;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	//private TransportJourney transportJourney;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRemovalId() {
		return removalId;
	}
	public void setRemovalId(String removalId) {
		this.removalId = removalId;
	}
	public Integer getRemovalTypeId() {
		return removalTypeId;
	}
	public void setRemovalTypeId(Integer removalTypeId) {
		this.removalTypeId = removalTypeId;
	}
	/*public Integer getTransportJourneyId() {
		return transportJourneyId;
	}
	public void setTransportJourneyId(Integer transportJourneyId) {
		this.transportJourneyId = transportJourneyId;
	}*/
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	/*public TransportJourney getTransportJourney() {
		return transportJourney;
	}
	public void setTransportJourney(TransportJourney transportJourney) {
		this.transportJourney = transportJourney;
	}*/
		
}