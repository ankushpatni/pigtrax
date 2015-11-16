package com.pigtrax.master.dto;

import java.util.Date;

public class Room {
	
	
	public int id;
	public String roomId;
	public int barnId;
	public String location;
	private boolean isActive;
	private Date lastUpdated;
	private String userUpdated;
	private Integer roomPositionId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
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
		return "Room [id=" + id + ", roomId=" + roomId + ", barnId=" + barnId
				+ ", location=" + location + ", isActive=" + isActive
				+ ", lastUpdated=" + lastUpdated + ", userUpdated="
				+ userUpdated + "]";
	}
	public Integer getRoomPositionId() {
		return roomPositionId;
	}
	public void setRoomPositionId(Integer roomPositionId) {
		this.roomPositionId = roomPositionId;
	}
	
	

}
