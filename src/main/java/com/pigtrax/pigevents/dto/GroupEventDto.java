package com.pigtrax.pigevents.dto;

import java.util.Date;

public class GroupEventDto {
	
	private Integer id;
	private String groupId;
	private String origin;
	private Date beginDateTime;
	private Integer roomId;
	private Integer employeeGroupId;
	private Integer numberOfPigs;
	private Integer weightInKgs;
	private Integer inventoryAdjustment;
	private Date groupCloseDateTime;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	private Integer phaseOfProductionTypeId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public Date getBeginDateTime() {
		return beginDateTime;
	}
	public void setBeginDateTime(Date beginDateTime) {
		this.beginDateTime = beginDateTime;
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
	public Integer getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(Integer weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public Integer getInventoryAdjustment() {
		return inventoryAdjustment;
	}
	public void setInventoryAdjustment(Integer inventoryAdjustment) {
		this.inventoryAdjustment = inventoryAdjustment;
	}
	public Date getGroupCloseDateTime() {
		return groupCloseDateTime;
	}
	public void setGroupCloseDateTime(Date groupCloseDateTime) {
		this.groupCloseDateTime = groupCloseDateTime;
	}
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
	public Integer getPhaseOfProductionTypeId() {
		return phaseOfProductionTypeId;
	}
	public void setPhaseOfProductionTypeId(Integer phaseOfProductionTypeId) {
		this.phaseOfProductionTypeId = phaseOfProductionTypeId;
	}
	
	

}
