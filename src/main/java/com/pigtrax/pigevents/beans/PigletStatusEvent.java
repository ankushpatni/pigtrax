package com.pigtrax.pigevents.beans;

import java.util.Date;

public class PigletStatusEvent {
	private Integer id;
	private Integer pigInfoId;
	private Integer fosterFrom;
	private Integer fosterTo;
	private Integer pigletStatusEventTypeId;
	private Date eventDateTime;
	private Integer numberOfPigs;
	private Double weightInKgs;
	private String eventReason;
	private String remarks;
	private Integer sowCondition;
	private String weanGroupId;
	private Date lastUpdated;
	private String userUpdated;
	private Integer farrowEventId;
	private Integer fosterFarrowEventId;
	private Integer groupEventId;
	private Integer mortalityReasonTypeId;
	private Integer penId;
	private Integer premiseId;
	private Integer numberOfPigsLW;
	
	private String pigId;

	public Integer getFosterFarrowEventId() {
		return fosterFarrowEventId;
	}

	public void setFosterFarrowEventId(Integer fosterFarrowEventId) {
		this.fosterFarrowEventId = fosterFarrowEventId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPigInfoId() {
		return pigInfoId;
	}

	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}

	public Integer getPigletStatusEventTypeId() {
		return pigletStatusEventTypeId;
	}

	public void setPigletStatusEventTypeId(Integer pigletStatusEventTypeId) {
		this.pigletStatusEventTypeId = pigletStatusEventTypeId;
	}

	public Date getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public Integer getNumberOfPigs() {
		return numberOfPigs;
	}

	public void setNumberOfPigs(Integer numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}

	public Double getWeightInKgs() {
		return weightInKgs;
	}

	public void setWeightInKgs(Double weightInKgs) {
		this.weightInKgs = weightInKgs;
	}

	public String getEventReason() {
		return eventReason;
	}

	public void setEventReason(String eventReason) {
		this.eventReason = eventReason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getSowCondition() {
		return sowCondition;
	}

	public void setSowCondition(Integer sowCondition) {
		this.sowCondition = sowCondition;
	}

	public String getWeanGroupId() {
		return weanGroupId;
	}

	public void setWeanGroupId(String weanGroupId) {
		this.weanGroupId = weanGroupId;
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

	public Integer getFosterFrom() {
		return fosterFrom;
	}

	public void setFosterFrom(Integer fosterFrom) {
		this.fosterFrom = fosterFrom;
	}

	public Integer getFosterTo() {
		return fosterTo;
	}

	public void setFosterTo(Integer fosterTo) {
		this.fosterTo = fosterTo;
	}

	public Integer getFarrowEventId() {
		return farrowEventId;
	}

	public void setFarrowEventId(Integer farrowEventId) {
		this.farrowEventId = farrowEventId;
	}

	public String getPigId() {
		return pigId;
	}

	public void setPigId(String pigId) {
		this.pigId = pigId;
	}

	public Integer getGroupEventId() {
		return groupEventId;
	}

	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}

	public Integer getMortalityReasonTypeId() {
		return mortalityReasonTypeId;
	}

	public void setMortalityReasonTypeId(Integer mortalityReasonTypeId) {
		this.mortalityReasonTypeId = mortalityReasonTypeId;
	}

	public Integer getPenId() {
		return penId;
	}

	public void setPenId(Integer penId) {
		this.penId = penId;
	}

	public Integer getPremiseId() {
		return premiseId;
	}

	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}

	public Integer getNumberOfPigsLW() {
		return numberOfPigsLW;
	}

	public void setNumberOfPigsLW(Integer numberOfPigsLW) {
		this.numberOfPigsLW = numberOfPigsLW;
	}
	
	
	

}
