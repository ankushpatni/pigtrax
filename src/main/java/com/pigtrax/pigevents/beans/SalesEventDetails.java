package com.pigtrax.pigevents.beans;

import java.math.BigDecimal;
import java.util.Date;

public class SalesEventDetails {
	
	private Integer id;
	private String invoiceId;
	private String ticketNumber;
	private Integer numberOfPigs;
	private BigDecimal revenueUsd;
	private BigDecimal weightInKgs;
	private Date salesDateTime;
	private Integer pigInfoId;
	private Integer groupEventId;
	private String soldTo;
	private Integer removalEventId;
	private Date lastUpdated;
	private String userUpdated;
	private Integer transportJourneyId;
	private TransportJourney transportJourney;
	private Integer companyId;
	private String remarks;
	private Integer[] salesTypes;
	private Integer[] salesReasons;
	private String salesTypesAsString;
	
	private String salesReasonsAsString;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public Integer getNumberOfPigs() {
		return numberOfPigs;
	}
	public void setNumberOfPigs(Integer numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}
	public BigDecimal getRevenueUsd() {
		return revenueUsd;
	}
	public void setRevenueUsd(BigDecimal revenueUsd) {
		this.revenueUsd = revenueUsd;
	}
	public BigDecimal getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(BigDecimal weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public Date getSalesDateTime() {
		return salesDateTime;
	}
	public void setSalesDateTime(Date salesDateTime) {
		this.salesDateTime = salesDateTime;
	}
	public Integer getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	public Integer getGroupEventId() {
		return groupEventId;
	}
	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}
	public String getSoldTo() {
		return soldTo;
	}
	public void setSoldTo(String soldTo) {
		this.soldTo = soldTo;
	}
	public Integer getRemovalEventId() {
		return removalEventId;
	}
	public void setRemovalEventId(Integer removalEventId) {
		this.removalEventId = removalEventId;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getUserUpdated() {
		return userUpdated;
	}
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}
	public Integer getTransportJourneyId() {
		return transportJourneyId;
	}
	public void setTransportJourneyId(Integer transportJourneyId) {
		this.transportJourneyId = transportJourneyId;
	}
	public TransportJourney getTransportJourney() {
		return transportJourney;
	}
	public void setTransportJourney(TransportJourney transportJourney) {
		this.transportJourney = transportJourney;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer[] getSalesTypes() {
		return salesTypes;
	}
	public void setSalesTypes(Integer[] salesTypes) {
		this.salesTypes = salesTypes;
	}
	public Integer[] getSalesReasons() {
		return salesReasons;
	}
	public void setSalesReasons(Integer[] salesReasons) {
		this.salesReasons = salesReasons;		
	}
	
	public void setSalesTypesAsString(String salesTypesAsString) {
		this.salesTypesAsString = salesTypesAsString;
		if(this.salesTypesAsString != null)
		{
			String[] types = this.salesTypesAsString.split(",");
			if(types != null && 0<types.length)
				salesTypes = new Integer[types.length];
			int i = 0;
			for(String s : types)
			{
				try{
					salesTypes[i] = Integer.parseInt(s);
				}catch(Exception e)
				{
					
				}
				i++;
			}
		}
		
	}
	public String getSalesReasonsAsString() {
		this.salesReasonsAsString = "";
		if(this.salesReasons != null && 0< this.salesReasons.length)
		{
			int i = 0;
			for(Integer in : salesReasons)
			{
				i++;
				this.salesReasonsAsString += in;
				if(i<this.salesReasons.length)
					this.salesReasonsAsString+=",";
			}
		}
		return this.salesReasonsAsString;
	}
	public void setSalesReasonsAsString(String salesReasonsAsString) {
		this.salesReasonsAsString = salesReasonsAsString;
		if(this.salesTypesAsString != null)
		{
			String[] reasons = this.salesReasonsAsString.split(",");
			if(reasons != null && 0<reasons.length)
				salesReasons = new Integer[reasons.length];
			int i = 0;
			for(String s : reasons)
			{				
				try{
					salesReasons[i] = Integer.parseInt(s);
				}catch(Exception e)
				{
					
				}
				i++;
			}
		}
	}
	
	public String getSalesTypesAsString() {
		this.salesTypesAsString = "";
		if(this.salesTypes != null && 0< this.salesTypes.length)
		{
			int i = 0;
			for(Integer in : salesTypes)
			{
				i++;
				this.salesTypesAsString += in;
				if(i<this.salesTypes.length)
					this.salesTypesAsString+=",";
			}
		}
		return this.salesTypesAsString;
	}
	
}
