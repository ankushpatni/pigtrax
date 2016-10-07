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
	private String dayLabel;
	private String sowIdString;
	public Integer getNumberOfPigs() {
		if(numberOfPigs == null) numberOfPigs = 0;
		return numberOfPigs;
	}
	public void setNumberOfPigs(Integer numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}
	public Integer getTotalPigCount() {
		if(totalPigCount == null) totalPigCount = 0;
		return totalPigCount;
	}
	public void setTotalPigCount(Integer totalPigCount) {
		this.totalPigCount = totalPigCount;
	}
	public Integer getLactationLength() {
		if(lactationLength == null) lactationLength =0;
		return lactationLength;
	}
	public void setLactationLength(Integer lactationLength) {
		this.lactationLength = lactationLength;
	}
	public Double getPercentage() {
//		percentage = (double) ((numberOfPigs*100)/totalPigCount);
//		if(percentage != null)
//		{
//			 BigDecimal bd = new BigDecimal(percentage);
//		     bd = bd.setScale(2, RoundingMode.HALF_UP); //set the precision to 2 decimal places
//		     return bd.doubleValue();
//		}
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
	public String getDayLabel() {
		return dayLabel;
	}
	public void setDayLabel(String dayLabel) {
		this.dayLabel = dayLabel;
	}
	public String getSowIdString() {
		return sowIdString;
	}
	public void setSowIdString(String sowIdString) {
		this.sowIdString = sowIdString;
	}
	
	

}
