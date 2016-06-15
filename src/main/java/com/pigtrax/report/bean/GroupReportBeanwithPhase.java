package com.pigtrax.report.bean;

import java.util.Date;

public class GroupReportBeanwithPhase {
	
	private String groupEventId;
	private Date eventDate;
	private String eventName;
	private String data;
	private String removalType;
	private String mortalityReason;
	private String ticketnumber;	
	private String salesTypes;
	private String phaseChange;
	private String additionalData;
	
	public String getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(String groupEventId) {
		this.groupEventId = groupEventId;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getRemovalType() {
		return removalType;
	}
	public void setRemovalType(String removalType) {
		this.removalType = removalType;
	}
	public String getMortalityReason() {
		return mortalityReason;
	}
	public void setMortalityReason(String mortalityReason) {
		this.mortalityReason = mortalityReason;
	}
	public String getTicketnumber() {
		return ticketnumber;
	}
	public void setTicketnumber(String ticketnumber) {
		this.ticketnumber = ticketnumber;
	}
	public String getSalesTypes() {
		return salesTypes;
	}
	public void setSalesTypes(String salesTypes) {
		this.salesTypes = salesTypes;
	}
	public String getPhaseChange() {
		return phaseChange;
	}
	public void setPhaseChange(String phaseChange) {
		this.phaseChange = phaseChange;
	}
	public String getAdditionalData() {
		return additionalData;
	}
	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}	
	
	
}
