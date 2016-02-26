package com.pigtrax.report.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class LactationLengthBean {
	private Integer numberOfPigs;
	private Integer totalPigCount;
	private Integer lactationLength;
	private Double percentage;
	private Date eventTime;
	public Integer getNumberOfPigs() {
		return numberOfPigs;
	}
	public void setNumberOfPigs(Integer numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}
	public Integer getTotalPigCount() {
		return totalPigCount;
	}
	public void setTotalPigCount(Integer totalPigCount) {
		this.totalPigCount = totalPigCount;
	}
	public Integer getLactationLength() {
		return lactationLength;
	}
	public void setLactationLength(Integer lactationLength) {
		this.lactationLength = lactationLength;
	}
	public Double getPercentage() {
		if(percentage != null)
		{
			 BigDecimal bd = new BigDecimal(percentage);
		     bd = bd.setScale(2, RoundingMode.HALF_UP); //set the precision to 2 decimal places
		     return bd.doubleValue();
		}
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	
	

}
