package com.pigtrax.master.dto;

import java.util.Date;

public class Pen {
	
	public int id;
	public String penId;
	public int roomId;
	public String location;
	private boolean isActive;
	private Date lastUpdated;
	private String userUpdated;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPenId() {
		return penId;
	}
	public void setPenId(String penId) {
		this.penId = penId;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	@Override
	public String toString() {
		return "Pen [id=" + id + ", penId=" + penId + ", roomId=" + roomId
				+ ", location=" + location + ", isActive=" + isActive
				+ ", lastUpdated=" + lastUpdated + ", userUpdated="
				+ userUpdated + "]";
	}
	
	

}
