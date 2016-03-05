package com.pigtrax.report.bean;

import java.util.Date;

public class FeedReportBean {
	
	private String GroupID;
	private String BarnID;
	private String SiloID;
	private String RationID;
	private Date feedEventDate;
	private String feedEventtype;
	private float  Weight;
	private float  feedcost;
	private String medication;
	private String truckTicket;
	private String feedMill;
	private String truck;
	private String trailer;
	private String remarks;
	public String getGroupID() {
		return GroupID;
	}
	public void setGroupID(String groupID) {
		GroupID = groupID;
	}
	public String getBarnID() {
		return BarnID;
	}
	public void setBarnID(String barnID) {
		BarnID = barnID;
	}
	public String getSiloID() {
		return SiloID;
	}
	public void setSiloID(String siloID) {
		SiloID = siloID;
	}
	public String getRationID() {
		return RationID;
	}
	public void setRationID(String rationID) {
		RationID = rationID;
	}
	public Date getFeedEventDate() {
		return feedEventDate;
	}
	public void setFeedEventDate(Date feedEventDate) {
		this.feedEventDate = feedEventDate;
	}
	public String getFeedEventtype() {
		return feedEventtype;
	}
	public void setFeedEventtype(String feedEventtype) {
		this.feedEventtype = feedEventtype;
	}
	public float getWeight() {
		return Weight;
	}
	public void setWeight(float weight) {
		Weight = weight;
	}
	public float getFeedcost() {
		return feedcost;
	}
	public void setFeedcost(float feedcost) {
		this.feedcost = feedcost;
	}
	public String getMedication() {
		return medication;
	}
	public void setMedication(String medication) {
		this.medication = medication;
	}
	public String getTruckTicket() {
		return truckTicket;
	}
	public void setTruckTicket(String truckTicket) {
		this.truckTicket = truckTicket;
	}
	public String getFeedMill() {
		return feedMill;
	}
	public void setFeedMill(String feedMill) {
		this.feedMill = feedMill;
	}
	public String getTruck() {
		return truck;
	}
	public void setTruck(String truck) {
		this.truck = truck;
	}
	public String getTrailer() {
		return trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
