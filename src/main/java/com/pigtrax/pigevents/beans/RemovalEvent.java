package com.pigtrax.pigevents.beans;

import java.util.Date;

public class RemovalEvent {
	
	private Integer id;
	private String removalId;
	private Integer removalTypeId;
	//private Integer transportJourneyId;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	
	private String groupId;
	private String pigId;
	private int companyId;
	private int premisesId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRemovalId() {
		return removalId;
	}
	public void setRemovalId(String removalId) {
		this.removalId = removalId;
	}
	public Integer getRemovalTypeId() {
		return removalTypeId;
	}
	public void setRemovalTypeId(Integer removalTypeId) {
		this.removalTypeId = removalTypeId;
	}
	/*public Integer getTransportJourneyId() {
		return transportJourneyId;
	}
	public void setTransportJourneyId(Integer transportJourneyId) {
		this.transportJourneyId = transportJourneyId;
	}*/
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getPigId() {
		return pigId;
	}
	public void setPigId(String pigId) {
		this.pigId = pigId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getPremisesId() {
		return premisesId;
	}
	public void setPremisesId(int premisesId) {
		this.premisesId = premisesId;
	}
	
		
}