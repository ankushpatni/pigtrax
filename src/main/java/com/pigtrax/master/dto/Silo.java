package com.pigtrax.master.dto;

import java.util.Date;

public class Silo {
	
	
	public int id;
	public String siloId;
	public int barnId;
	public String location;
	private int siloTypeId;
	private Date lastUpdated;
	private String userUpdated;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSiloId() {
		return siloId;
	}
	public void setSiloId(String siloId) {
		this.siloId = siloId;
	}
	public int getBarnId() {
		return barnId;
	}
	public void setBarnId(int barnId) {
		this.barnId = barnId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	
	public int getSiloTypeId() {
		return siloTypeId;
	}
	public void setSiloTypeId(int siloTypeId) {
		this.siloTypeId = siloTypeId;
	}
	@Override
	public String toString() {
		return "Silo [id=" + id + ", siloId=" + siloId + ", barnId=" + barnId
				+ ", location=" + location + ", isActive=" + siloTypeId
				+ ", lastUpdated=" + lastUpdated + ", userUpdated="
				+ userUpdated + "]";
	}
	
	

}
