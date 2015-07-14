package com.pigtrax.pigevents.dto;

import java.util.Date;

public class BarnDto {
    private Integer id;
    private String barnId;
    private Integer premiseId;
    private Integer phaseTypeId;
    private String location;
    private Integer area;
    private Integer feederCount;
    private Integer waterAccessCount;
    private boolean isActive;
    private Date lastUpdated;
    private String userUpdated;
    private Integer ventilationTypeId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBarnId() {
		return barnId;
	}
	public void setBarnId(String barnId) {
		this.barnId = barnId;
	}
	public Integer getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}
	public Integer getPhaseTypeId() {
		return phaseTypeId;
	}
	public void setPhaseTypeId(Integer phaseTypeId) {
		this.phaseTypeId = phaseTypeId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Integer getFeederCount() {
		return feederCount;
	}
	public void setFeederCount(Integer feederCount) {
		this.feederCount = feederCount;
	}
	public Integer getWaterAccessCount() {
		return waterAccessCount;
	}
	public void setWaterAccessCount(Integer waterAccessCount) {
		this.waterAccessCount = waterAccessCount;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
	public Integer getVentilationTypeId() {
		return ventilationTypeId;
	}
	public void setVentilationTypeId(Integer ventilationTypeId) {
		this.ventilationTypeId = ventilationTypeId;
	}
    
    
    
}
