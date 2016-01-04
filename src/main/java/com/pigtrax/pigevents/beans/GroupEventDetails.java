package com.pigtrax.pigevents.beans;

import java.util.Date;

public class GroupEventDetails {
	
	private Integer id;	
	private Integer barnId;
	private Date dateOfEntry;
	private Integer numberOfPigs;
	private Double weightInKgs;
	private Integer inventoryAdjustment;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	private Integer roomId;
	private Integer employeeGroupId;
	private int groupId;
	private Integer transportDestination;
	private Integer premiseId;
	private Integer sowSourceId;
	private Integer pigletStatusEventId;
	private Integer fromGroupId;
	
	public Date getDateOfEntry() {
		return dateOfEntry;
	}
	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public Integer getBarnId() {
		return barnId;
	}
	public void setBarnId(Integer barnId) {
		this.barnId = barnId;
	}
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public Integer getEmployeeGroupId() {
		return employeeGroupId;
	}
	public void setEmployeeGroupId(Integer employeeGroupId) {
		this.employeeGroupId = employeeGroupId;
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
	public Integer getInventoryAdjustment() {
		return inventoryAdjustment;
	}
	public void setInventoryAdjustment(Integer inventoryAdjustment) {
		this.inventoryAdjustment = inventoryAdjustment;
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
	public Integer getTransportDestination() {
		return transportDestination;
	}
	public void setTransportDestination(Integer transportDestination) {
		this.transportDestination = transportDestination;
	}
	public Integer getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}
	public Integer getSowSourceId() {
		return sowSourceId;
	}
	public void setSowSourceId(Integer sowSourceId) {
		this.sowSourceId = sowSourceId;
	}
	public Integer getPigletStatusEventId() {
		return pigletStatusEventId;
	}
	public void setPigletStatusEventId(Integer pigletStatusEventId) {
		this.pigletStatusEventId = pigletStatusEventId;
	}
	public Integer getFromGroupId() {
		return fromGroupId;
	}
	public void setFromGroupId(Integer fromGroupId) {
		this.fromGroupId = fromGroupId;
	}
	
	
}
