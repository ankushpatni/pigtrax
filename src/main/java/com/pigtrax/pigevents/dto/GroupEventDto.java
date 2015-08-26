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
	private Double weightInKgs;
	private Integer inventoryAdjustment;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	private EmployeeGroupDto employeeGroup;
	private Integer companyId;
	
	//groupevent fields	
	
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
	
	public EmployeeGroupDto getEmployeeGroup() {
		return employeeGroup;
	}
	public void setEmployeeGroup(EmployeeGroupDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}
