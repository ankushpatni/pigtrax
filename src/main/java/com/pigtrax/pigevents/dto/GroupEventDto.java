package com.pigtrax.pigevents.dto;

import java.util.Date;

import com.pigtrax.master.dto.EmployeeGroupDto;

public class GroupEventDto {
	
	private Integer id;
	private int groupId;
	private String origin;
	private Date dateOfEntry;
	private Integer roomId;
	private Integer employeeGroupId;
	private Integer numberOfPigs;
	private Integer weightInKgs;
	private Integer inventoryAdjustment;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	private Integer phaseOfProductionTypeId;
	private EmployeeGroupDto employeeGroup;
	
	//groupevent fields
	
	private Date groupStartDateTime;
	private Date groupCloseDateTime;
	private boolean isActive;
	private String remarksGroupEvent;
	private Date lastUpdatedGroupEvent;
	private String userUpdatedGroupEvent;
	
	
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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public Date getDateOfEntry() {
		return dateOfEntry;
	}
	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
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
	public Date getGroupStartDateTime() {
		return groupStartDateTime;
	}
	public void setGroupStartDateTime(Date groupStartDateTime) {
		this.groupStartDateTime = groupStartDateTime;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getRemarksGroupEvent() {
		return remarksGroupEvent;
	}
	public void setRemarksGroupEvent(String remarksGroupEvent) {
		this.remarksGroupEvent = remarksGroupEvent;
	}
	public Date getLastUpdatedGroupEvent() {
		return lastUpdatedGroupEvent;
	}
	public void setLastUpdatedGroupEvent(Date lastUpdatedGroupEvent) {
		this.lastUpdatedGroupEvent = lastUpdatedGroupEvent;
	}
	public String getUserUpdatedGroupEvent() {
		return userUpdatedGroupEvent;
	}
	public void setUserUpdatedGroupEvent(String userUpdatedGroupEvent) {
		this.userUpdatedGroupEvent = userUpdatedGroupEvent;
	}
	public EmployeeGroupDto getEmployeeGroup() {
		return employeeGroup;
	}
	public void setEmployeeGroup(EmployeeGroupDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}
	

}
