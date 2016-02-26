package com.pigtrax.report.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class PigletMortalityReportBean {
	
	private String premise;
	private String barnId;
	private String roomId;
	private Integer lactationDays;
	private Integer numberOfDeaths;
	private String mortalityReason;
	public String getPremise() {
		return premise;
	}
	public void setPremise(String premise) {
		this.premise = premise;
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
	public Integer getLactationDays() {
		return lactationDays;
	}
	public void setLactationDays(Integer lactationDays) {
		this.lactationDays = lactationDays;
	}
	public Integer getNumberOfDeaths() {
		return numberOfDeaths;
	}
	public void setNumberOfDeaths(Integer numberOfDeaths) {
		this.numberOfDeaths = numberOfDeaths;
	}
	public String getMortalityReason() { 
		return mortalityReason;
	}
	public void setMortalityReason(String mortalityReason) {
		if(mortalityReason == null) mortalityReason = "";
		this.mortalityReason = mortalityReason;
	}
	

}
