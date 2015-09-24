package com.pigtrax.pigevents.beans;

import java.util.Date;

public class TransportJourney {
	
	private Integer id;
	private String trailerFunction;
	private Date journeyStartTime;
	private Date journeyEndTime;
	private Integer transportDestinationId;
	private Integer transportTruckId;
	private Integer transportTrailerId;
	private Date lastUpdated;
	private String userUpdated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTrailerFunction() {
		return trailerFunction;
	}
	public void setTrailerFunction(String trailerFunction) {
		this.trailerFunction = trailerFunction;
	}
	public Date getJourneyStartTime() {
		return journeyStartTime;
	}
	public void setJourneyStartTime(Date journeyStartTime) {
		this.journeyStartTime = journeyStartTime;
	}
	public Date getJourneyEndTime() {
		return journeyEndTime;
	}
	public void setJourneyEndTime(Date journeyEndTime) {
		this.journeyEndTime = journeyEndTime;
	}
	public Integer getTransportDestinationId() {
		return transportDestinationId;
	}
	public void setTransportDestinationId(Integer transportDestinationId) {
		this.transportDestinationId = transportDestinationId;
	}
	public Integer getTransportTruckId() {
		return transportTruckId;
	}
	public void setTransportTruckId(Integer transportTruckId) {
		this.transportTruckId = transportTruckId;
	}
	public Integer getTransportTrailerId() {
		return transportTrailerId;
	}
	public void setTransportTrailerId(Integer transportTrailer) {
		this.transportTrailerId = transportTrailer;
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
