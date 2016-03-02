package com.pigtrax.report.bean;

import java.util.Date;

public class RemovalReportBean {
	
	private String premise;
	private String barnID;
	private Integer roomID;
	private String penID;
	private String groupID;
	private String pigID;
	private String pStatus;
	private Date removalDate;
	private Integer numberPigsRemoved;
	private float aveWeight;
	private Integer parity;
	private Integer aveDOF;
	private float aveWOF;
	private String mortalityReason;
	
	public String getPremise() {
		return premise;
	}
	public void setPremise(String premise) {
		this.premise = premise;
	}
	public String getBarnID() {
		return barnID;
	}
	public void setBarnID(String barnID) {
		this.barnID = barnID;
	}
	public Integer getRoomID() {
		return roomID;
	}
	public void setRoomID(Integer roomID) {
		this.roomID = roomID;
	}
	public String getPenID() {
		return penID;
	}
	public void setPenID(String penID) {
		this.penID = penID;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getPigID() {
		return pigID;
	}
	public void setPigID(String pigID) {
		this.pigID = pigID;
	}
	public String getpStatus() {
		return pStatus;
	}
	public void setpStatus(String pStatus) {
		this.pStatus = pStatus;
	}
	public Date getRemovalDate() {
		return removalDate;
	}
	public void setRemovalDate(Date removalDate) {
		this.removalDate = removalDate;
	}
	public Integer getNumberPigsRemoved() {
		return numberPigsRemoved;
	}
	public void setNumberPigsRemoved(Integer numberPigsRemoved) {
		this.numberPigsRemoved = numberPigsRemoved;
	}
	public float getAveWeight() {
		return aveWeight;
	}
	public void setAveWeight(float aveWeight) {
		this.aveWeight = aveWeight;
	}
	public Integer getParity() {
		return parity;
	}
	public void setParity(Integer parity) {
		this.parity = parity;
	}
	public Integer getAveDOF() {
		return aveDOF;
	}
	public void setAveDOF(Integer aveDOF) {
		this.aveDOF = aveDOF;
	}
	public float getAveWOF() {
		return aveWOF;
	}
	public void setAveWOF(float aveWOF) {
		this.aveWOF = aveWOF;
	}
	public String getMortalityReason() {
		return mortalityReason;
	}
	public void setMortalityReason(String mortalityReason) {
		this.mortalityReason = mortalityReason;
	}
}
