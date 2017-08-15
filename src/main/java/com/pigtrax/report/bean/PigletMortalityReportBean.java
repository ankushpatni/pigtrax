package com.pigtrax.report.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class PigletMortalityReportBean {
	
	private String premise;
	private String penId;
	private String barnId;
	private String roomId;
	private Integer lactationDays;
	private Integer numberOfDeaths;
	private String mortalityReason;
	private Integer inventoryCount;
	private Date deathDate;
	private Integer count1;
	private Integer count2;
	private Integer count3;
	private Integer count4;
	private Integer count5;
	private Integer count6;
	private Integer count7;
	private Integer count8;
	private Integer count9;
	private Date farrowDate;
	private Integer roomPkId;
	private Date weanDate;
		
	public String getPremise() {
		if(premise == null) premise = "";
		return premise;
	}
	public void setPremise(String premise) {
		this.premise = premise;
	}
	public String getBarnId() {
		if(barnId == null) barnId = "";
		return barnId;
	}
	public void setBarnId(String barnId) {
		if(barnId  == null) barnId = "";
		this.barnId = barnId;
	}
	public String getRoomId() {
		if(roomId == null) roomId = "";
		return roomId;
	}
	public void setRoomId(String roomId) {
		if(roomId  == null) roomId = "";
		this.roomId = roomId;
	}
	public Integer getLactationDays() {
		if(lactationDays == null) lactationDays = 0;
		return lactationDays;
	}
	public void setLactationDays(Integer lactationDays) {
		this.lactationDays = lactationDays;
	}
	public Integer getNumberOfDeaths() {
		if(numberOfDeaths == null) numberOfDeaths = 0;
		return numberOfDeaths;
	}
	public void setNumberOfDeaths(Integer numberOfDeaths) {
		this.numberOfDeaths = numberOfDeaths;
	}
	public String getMortalityReason() {
		if(mortalityReason == null) mortalityReason = "";
		return mortalityReason;
	}
	public void setMortalityReason(String mortalityReason) {
		if(mortalityReason == null) mortalityReason = "";
		this.mortalityReason = mortalityReason;
	}
	public Integer getInventoryCount() {
		if(inventoryCount == null) inventoryCount = 0;
		return inventoryCount;
	}
	public void setInventoryCount(Integer inventoryCount) {
		this.inventoryCount = inventoryCount;
	}
	public Date getDeathDate() {
		return deathDate;
	}
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
	public Integer getCount1() {
		if(count1 == null) count1 = 0;
		return count1;
	}
	public void setCount1(Integer count1) {
		this.count1 = count1;
	}
	public Integer getCount2() {
		if(count2 == null) count2 = 0;
		return count2;
	}
	public void setCount2(Integer count2) {
		this.count2 = count2;
	}
	public Integer getCount3() {
		if(count3 == null) count3 = 0;
		return count3;
	}
	public void setCount3(Integer count3) {
		this.count3 = count3;
	}
	public Integer getCount4() {
		if(count4 == null) count4 = 0;
		return count4;
	}
	public void setCount4(Integer count4) {
		this.count4 = count4;
	}
	public Integer getCount5() {
		if(count5 == null) count5 = 0;
		return count5;
	}
	public void setCount5(Integer count5) {
		this.count5 = count5;
	}
	public Integer getCount6() {
		if(count6 == null) count6 = 0;
		return count6;
	}
	public void setCount6(Integer count6) {
		this.count6 = count6;
	}
	public Integer getCount7() {
		if(count7 == null) count7 = 0;
		return count7;
	}
	public void setCount7(Integer count7) {
		this.count7 = count7;
	}
	public Integer getCount8() {
		if(count8 == null) count8 = 0;
		return count8;
	}
	public void setCount8(Integer count8) {
		this.count8 = count8;
	}
	public Integer getCount9() {
		if(count9 == null) count9 = 0;
		return count9;
	}
	public void setCount9(Integer count9) {
		this.count9 = count9;
	}
	public Date getFarrowDate() {
		return farrowDate;
	}
	public void setFarrowDate(Date farrowDate) {
		this.farrowDate = farrowDate;
	}
	public Integer getRoomPkId() {
		return roomPkId;
	}
	public void setRoomPkId(Integer roomPkId) {
		this.roomPkId = roomPkId;
	}
	public Date getWeanDate() {
		return weanDate;
	}
	public void setWeanDate(Date weanDate) {
		this.weanDate = weanDate;
	}
	public String getPenId() {
		return penId;
	}
	public void setPenId(String penId) {
		this.penId = penId;
	}
	
    
}
