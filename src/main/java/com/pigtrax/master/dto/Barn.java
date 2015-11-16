package com.pigtrax.master.dto;

import java.math.BigDecimal;
import java.util.Date;

public class Barn {
	
	public int id;
	public String barnId;
	public int premiseId;
	public int phaseTypeId;
	public String location;
	public BigDecimal area;
	public int feederCount;
	public int waterAccessCount;
	private boolean isActive;
	private int ventilationTypeId;
	private Date lastUpdated;
	private String userUpdated;
	private Integer barnOrientationId;
	private String barnOrientation;
	private Integer barnLocationId;
	private Integer waterTypeId;
	private Integer barnPositionId;
	private Integer feederTypeId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBarnId() {
		return barnId;
	}
	public void setBarnId(String barnId) {
		this.barnId = barnId;
	}
	public int getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(int premiseId) {
		this.premiseId = premiseId;
	}
	public int getPhaseTypeId() {
		return phaseTypeId;
	}
	public void setPhaseTypeId(int phaseTypeId) {
		this.phaseTypeId = phaseTypeId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
	}
	public int getFeederCount() {
		return feederCount;
	}
	public void setFeederCount(int feederCount) {
		this.feederCount = feederCount;
	}
	public int getWaterAccessCount() {
		return waterAccessCount;
	}
	public void setWaterAccessCount(int waterAccessCount) {
		this.waterAccessCount = waterAccessCount;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getVentilationTypeId() {
		return ventilationTypeId;
	}
	public void setVentilationTypeId(int ventilationTypeId) {
		this.ventilationTypeId = ventilationTypeId;
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
	public Integer getBarnOrientationId() {
		return barnOrientationId;
	}
	public void setBarnOrientationId(Integer barnOrientationId) {
		this.barnOrientationId = barnOrientationId;
	}
	public String getBarnOrientation() {
		return barnOrientation;
	}
	public void setBarnOrientation(String barnOrientation) {
		this.barnOrientation = barnOrientation;
	}
	public Integer getBarnLocationId() {
		return barnLocationId;
	}
	public void setBarnLocationId(Integer barnLocationId) {
		this.barnLocationId = barnLocationId;
	}
	public Integer getWaterTypeId() {
		return waterTypeId;
	}
	public void setWaterTypeId(Integer waterTypeId) {
		this.waterTypeId = waterTypeId;
	}
	public Integer getBarnPositionId() {
		return barnPositionId;
	}
	public void setBarnPositionId(Integer barnPositionId) {
		this.barnPositionId = barnPositionId;
	}
	public Integer getFeederTypeId() {
		return feederTypeId;
	}
	public void setFeederTypeId(Integer feederTypeId) {
		this.feederTypeId = feederTypeId;
	}
	
	
	
}
