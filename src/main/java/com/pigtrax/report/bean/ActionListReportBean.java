package com.pigtrax.report.bean;

import java.util.Date;

public class ActionListReportBean {
	private String pigId;
	private Integer parity;
	private Integer age;
	private Date sowPhaseDate;
	private String sowPhase;
	private String roomId;
	private String penId;
	private Date currentServiceDate;
	private String pregnancyEventType;
	private Date pregnancyEventDate;
	private Date dueDateAnticipated;
	private Date farrowDate;
	private Integer gestationLength;
	private Integer lactatingDays;
	private Integer overDue;

	public String getPigId() {
		return pigId;
	}

	public void setPigId(String pigId) {
		this.pigId = pigId;
	}

	public Integer getParity() {
		return parity;
	}

	public void setParity(Integer parity) {
		this.parity = parity;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getSowPhaseDate() {
		return sowPhaseDate;
	}

	public void setSowPhaseDate(Date sowPhaseDate) {
		this.sowPhaseDate = sowPhaseDate;
	}

	public String getSowPhase() {
		return sowPhase;
	}

	public void setSowPhase(String sowPhase) {
		if(sowPhase == null) sowPhase = "";
		this.sowPhase = sowPhase;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		if(roomId == null) roomId = "";
		this.roomId = roomId;
	}

	public String getPenId() {
		return penId;
	}

	public void setPenId(String penId) {
		if(penId == null) penId = "";
		this.penId = penId;
	}

	public Date getCurrentServiceDate() {
		return currentServiceDate;
	}

	public void setCurrentServiceDate(Date currentServiceDate) {
		this.currentServiceDate = currentServiceDate;
	}

	public String getPregnancyEventType() {
		return pregnancyEventType;
	}

	public void setPregnancyEventType(String pregnancyEventType) {
		if(pregnancyEventType == null) pregnancyEventType = "";
		this.pregnancyEventType = pregnancyEventType;
	}

	public Date getPregnancyEventDate() {
		return pregnancyEventDate;
	}

	public void setPregnancyEventDate(Date pregnancyEventDate) {
		this.pregnancyEventDate = pregnancyEventDate;
	}

	public Date getDueDateAnticipated() {
		return dueDateAnticipated;
	}

	public void setDueDateAnticipated(Date dueDateAnticipated) {
		this.dueDateAnticipated = dueDateAnticipated;
	}

	public Date getFarrowDate() {
		return farrowDate;
	}

	public void setFarrowDate(Date farrowDate) {
		this.farrowDate = farrowDate;
	}

	public Integer getGestationLength() {
		return gestationLength;
	}

	public void setGestationLength(Integer gestationLength) {
		if(gestationLength == null) gestationLength = 0;
		this.gestationLength = gestationLength;
	}

	public Integer getLactatingDays() {
		return lactatingDays;
	}

	public void setLactatingDays(Integer lactatingDays) {
		if(lactatingDays == null) lactatingDays = 0;
		this.lactatingDays = lactatingDays;
	}

	public Integer getOverDue() {
		return overDue;
	}

	public void setOverDue(Integer overDue) {
		this.overDue = overDue;
	}
	
	

}
