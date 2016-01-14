package com.pigtrax.pigevents.beans;

import java.util.Date;
import java.util.List;

import com.pigtrax.master.dto.RoomPK;

public class GroupEventPhaseChange {
    private Integer id;
    private Integer groupEventId;
    private Integer phaseOfProductionTypeId;
    private Date phaseStartDate;
    private String phaseStartTimeStr;
    private Date phaseEndDate;
    private String phaseEndTimeStr;
    private Integer premiseId;
    private String userUpdated;
    private Date lastUpdated; 
    private String premise;

	private List<RoomPK> roomIds;
	
	private List<String> rooms;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}
	public Integer getPhaseOfProductionTypeId() {
		return phaseOfProductionTypeId;
	}
	public void setPhaseOfProductionTypeId(Integer phaseOfProductionTypeId) {
		this.phaseOfProductionTypeId = phaseOfProductionTypeId;
	}
	public Date getPhaseStartDate() {
		return phaseStartDate;
	}
	public void setPhaseStartDate(Date phaseStartDate) {
		this.phaseStartDate = phaseStartDate;
	}
	public Date getPhaseEndDate() {
		return phaseEndDate;
	}
	public void setPhaseEndDate(Date phaseEndDate) {
		this.phaseEndDate = phaseEndDate;
	}
	public Integer getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}
	
	public String getUserUpdated() {
		return userUpdated;
	}
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getPremise() {
		return premise;
	}
	public void setPremise(String premise) {
		this.premise = premise;
	}
	public String getPhaseStartTimeStr() {
		return phaseStartTimeStr;
	}
	public void setPhaseStartTimeStr(String phaseStartTimeStr) {
		this.phaseStartTimeStr = phaseStartTimeStr;
	}
	public String getPhaseEndTimeStr() {
		return phaseEndTimeStr;
	}
	public void setPhaseEndTimeStr(String phaseEndTimeStr) {
		this.phaseEndTimeStr = phaseEndTimeStr;
	}
	public List<RoomPK> getRoomIds() {
		return roomIds;
	}
	public void setRoomIds(List<RoomPK> roomIds) {
		this.roomIds = roomIds;
	}
	public List<String> getRooms() {
		return rooms;
	}
	public void setRooms(List<String> rooms) {
		this.rooms = rooms;
	}
	
	
    
}
