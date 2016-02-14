package com.pigtrax.report.bean;

import java.util.Date;

public class GroupReportBean {
	
	private Integer masterEventId;
	private Date eventDate;
	private Integer groupEventId;
	private Integer groupEventPremiseId;
	private Integer groupPhaseOfProductionType;
	private Integer removalEventExceptSalesDetailsId;
	private Integer removalEventExceptSalesDetailsPremisesId;
	private Integer removalEventExceptSalesDetailsRoomId;
	private Integer salesEventDetailsId;
	private Integer groupEventNumberOfPigs;
	
	public Integer getMasterEventId() {
		return masterEventId;
	}
	public void setMasterEventId(Integer masterEventId) {
		this.masterEventId = masterEventId;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Integer getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}
	public Integer getGroupEventPremiseId() {
		return groupEventPremiseId;
	}
	public void setGroupEventPremiseId(Integer groupEventPremiseId) {
		this.groupEventPremiseId = groupEventPremiseId;
	}
	public Integer getGroupPhaseOfProductionType() {
		return groupPhaseOfProductionType;
	}
	public void setGroupPhaseOfProductionType(Integer groupPhaseOfProductionType) {
		this.groupPhaseOfProductionType = groupPhaseOfProductionType;
	}
	public Integer getRemovalEventExceptSalesDetailsId() {
		return removalEventExceptSalesDetailsId;
	}
	public void setRemovalEventExceptSalesDetailsId(
			Integer removalEventExceptSalesDetailsId) {
		this.removalEventExceptSalesDetailsId = removalEventExceptSalesDetailsId;
	}
	public Integer getRemovalEventExceptSalesDetailsPremisesId() {
		return removalEventExceptSalesDetailsPremisesId;
	}
	public void setRemovalEventExceptSalesDetailsPremisesId(
			Integer removalEventExceptSalesDetailsPremisesId) {
		this.removalEventExceptSalesDetailsPremisesId = removalEventExceptSalesDetailsPremisesId;
	}
	public Integer getRemovalEventExceptSalesDetailsRoomId() {
		return removalEventExceptSalesDetailsRoomId;
	}
	public void setRemovalEventExceptSalesDetailsRoomId(
			Integer removalEventExceptSalesDetailsRoomId) {
		this.removalEventExceptSalesDetailsRoomId = removalEventExceptSalesDetailsRoomId;
	}
	public Integer getSalesEventDetailsId() {
		return salesEventDetailsId;
	}
	public void setSalesEventDetailsId(Integer salesEventDetailsId) {
		this.salesEventDetailsId = salesEventDetailsId;
	}
	public Integer getGroupEventNumberOfPigs() {
		return groupEventNumberOfPigs;
	}
	public void setGroupEventNumberOfPigs(Integer groupEventNumberOfPigs) {
		this.groupEventNumberOfPigs = groupEventNumberOfPigs;
	}	
}
