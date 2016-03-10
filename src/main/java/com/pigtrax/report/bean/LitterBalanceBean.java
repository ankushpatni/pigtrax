package com.pigtrax.report.bean;

import java.util.Date;

public class LitterBalanceBean {
	
	private String pigId;
	private Date weanDate;		
	private String penId;
	private Integer liveBorn;
	private Integer death;
	private Integer transfer;
	private Integer wean;
	private Integer balance;
	private Integer fosterInNum;
	
	public String getPigId() {
		return pigId;
	}
	public void setPigId(String pigId) {
		this.pigId = pigId;
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
	public Integer getLiveBorn() {
		return liveBorn;
	}
	public void setLiveBorn(Integer liveBorn) {
		this.liveBorn = liveBorn;
	}
	public Integer getDeath() {
		return death;
	}
	public void setDeath(Integer death) {
		this.death = death;
	}
	public Integer getWean() {
		return wean;
	}
	public void setWean(Integer wean) {
		this.wean = wean;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getTransfer() {
		return transfer;
	}
	public void setTransfer(Integer transfer) {
		this.transfer = transfer;
	}
	public Integer getFosterInNum() {
		return fosterInNum;
	}
	public void setFosterInNum(Integer fosterInNum) {
		this.fosterInNum = fosterInNum;
	}	

	
}
