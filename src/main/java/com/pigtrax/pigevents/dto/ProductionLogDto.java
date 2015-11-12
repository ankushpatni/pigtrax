package com.pigtrax.pigevents.dto;

import java.util.Date;

public class ProductionLogDto {
	private Integer id;
	private String observation;
	private Integer companyId;
	private Date lastUpdated;
	private String userUpdated;
	private Date startDate;
	private Date endDate;
	private Integer roomId;
	private Integer logEventTypeId;
	private String eventId;
	private Date observationDate;
	private String logEventType;
	private String language;
	private String room;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	

	public Integer getLogEventTypeId() {
		return logEventTypeId;
	}

	public void setLogEventTypeId(Integer logEventTypeId) {
		this.logEventTypeId = logEventTypeId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Date getObservationDate() {
		return observationDate;
	}

	public void setObservationDate(Date observationDate) {
		this.observationDate = observationDate;
	}

	public String getLogEventType() {
		return logEventType;
	}

	public void setLogEventType(String logEventType) {
		this.logEventType = logEventType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	

}
