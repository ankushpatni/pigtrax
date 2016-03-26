package com.pigtrax.report.bean;

import java.util.Date;

public class SaleReportBean {
	
	private String premisesId;
	private String barnID;
	private String roomID;	
	private String groupID;	
	private Date salesEventDate;
	private String salesType;
	private Integer numberOfPigsSold;
	private String destination;
	private Integer totalWeight;
	private float weightPerPig;
	private String ticketNumber;
	private String invoiceNumber;
	private float revenue;
	private String truck;
	private String trailer;
	private String remarks;
	private String pigID;
	
	public String getPremisesId() {
		return premisesId;
	}
	public void setPremisesId(String premisesId) {
		this.premisesId = premisesId;
	}
	public String getBarnID() {
		return barnID;
	}
	public void setBarnID(String barnID) {
		this.barnID = barnID;
	}
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public Date getSalesEventDate() {
		return salesEventDate;
	}
	public void setSalesEventDate(Date salesEventDate) {
		this.salesEventDate = salesEventDate;
	}
	public String getSalesType() {
		return salesType;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	public Integer getNumberOfPigsSold() {
		return numberOfPigsSold;
	}
	public void setNumberOfPigsSold(Integer numberOfPigsSold) {
		this.numberOfPigsSold = numberOfPigsSold;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Integer getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Integer totalWeight) {
		this.totalWeight = totalWeight;
	}
	public float getWeightPerPig() {
		return weightPerPig;
	}
	public void setWeightPerPig(float weightPerPig) {
		this.weightPerPig = weightPerPig;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public float getRevenue() {
		return revenue;
	}
	public void setRevenue(float revenue) {
		this.revenue = revenue;
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
	public String getPigID() {
		return pigID;
	}
	public void setPigID(String pigID) {
		this.pigID = pigID;
	}
	
	
}
