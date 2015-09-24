package com.pigtrax.master.dto;

import java.util.Date;

public class TransportTruck {
	
	public int id;
	public String transportTruckId;
	private int companyId;
	private Date lastUpdated;
	private String userUpdated;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTransportTruckId() {
		return transportTruckId;
	}
	public void setTransportTruckId(String transportTruckId) {
		this.transportTruckId = transportTruckId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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
	@Override
	public String toString() {
		return "TransportTruck [id=" + id + ", truckId=" + transportTruckId
				+ ", companyId=" + companyId + ", lastUpdated=" + lastUpdated
				+ ", userUpdated=" + userUpdated + "]";
	}
	
	

}
