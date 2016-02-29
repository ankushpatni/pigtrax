package com.pigtrax.report.bean;

import java.util.Date;

public class ProdEventLogBean {
	
	private String premiseId;
	private String barnId;
	private String roomId;
	private Integer groupId;
	private Date eventDate;
	private Integer logEventType;
	private String remark;	
	
	public String getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(String premiseId) {
		this.premiseId = premiseId;
	}
	public String getBarnId() {
		return barnId;
	}
	public void setBarnId(String barnId) {
		if(barnId  == null) barnId = "";
		this.barnId = barnId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		if(roomId  == null) roomId = "";
		this.roomId = roomId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Integer getLogEventType() {
		return logEventType;
	}
	public void setLogEventType(Integer logEventType) {
		this.logEventType = logEventType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
