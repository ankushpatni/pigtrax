package com.pigtrax.report.bean;

import java.util.Date;

public class RationReportBean {
	
	private String rationId;
	private Double actualTonsUsed;
	private Double targetTonsUsed;
	private Double deviationTonsUsed;
	private Double actualKg;
	private Double targetKg;
	private Double deviationKg; 
	private Double actualFeedCost;	
	private Double targetFeedCost;	
	private Double deviationFeedCost;
	private String ticketNum1;
	private String ticketNum2;
	private String ticketNum3;
	private Double totalWeight;
	private Date feedEvenDate;
	
	public String getRationId() {
		return rationId;
	}
	public void setRationId(String rationId) {
		this.rationId = rationId;
	}
	public Double getActualTonsUsed() {
		return actualTonsUsed;
	}
	public void setActualTonsUsed(Double actualTonsUsed) {
		this.actualTonsUsed = actualTonsUsed;
	}
	public Double getTargetTonsUsed() {
		return targetTonsUsed;
	}
	public void setTargetTonsUsed(Double targetTonsUsed) {
		this.targetTonsUsed = targetTonsUsed;
	}
	public Double getDeviationTonsUsed() {
		return deviationTonsUsed;
	}
	public void setDeviationTonsUsed(Double deviationTonsUsed) {
		this.deviationTonsUsed = deviationTonsUsed;
	}
	public Double getActualKg() {
		return actualKg;
	}
	public void setActualKg(Double actualKg) {
		this.actualKg = actualKg;
	}
	public Double getTargetKg() {
		return targetKg;
	}
	public void setTargetKg(Double targetKg) {
		this.targetKg = targetKg;
	}
	public Double getDeviationKg() {
		return deviationKg;
	}
	public void setDeviationKg(Double deviationKg) {
		this.deviationKg = deviationKg;
	}
	public Double getActualFeedCost() {
		return actualFeedCost;
	}
	public void setActualFeedCost(Double actualFeedCost) {
		this.actualFeedCost = actualFeedCost;
	}
	public Double getTargetFeedCost() {
		return targetFeedCost;
	}
	public void setTargetFeedCost(Double targetFeedCost) {
		this.targetFeedCost = targetFeedCost;
	}
	public Double getDeviationFeedCost() {
		return deviationFeedCost;
	}
	public void setDeviationFeedCost(Double deviationFeedCost) {
		this.deviationFeedCost = deviationFeedCost;
	}
	public String getTicketNum1() {
		return ticketNum1;
	}
	public void setTicketNum1(String ticketNum1) {
		this.ticketNum1 = ticketNum1;
	}
	public String getTicketNum2() {
		return ticketNum2;
	}
	public void setTicketNum2(String ticketNum2) {
		this.ticketNum2 = ticketNum2;
	}
	public String getTicketNum3() {
		return ticketNum3;
	}
	public void setTicketNum3(String ticketNum3) {
		this.ticketNum3 = ticketNum3;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Date getFeedEvenDate() {
		return feedEvenDate;
	}
	public void setFeedEvenDate(Date feedEvenDate) {
		this.feedEvenDate = feedEvenDate;
	}
	
	

}
