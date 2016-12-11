package com.pigtrax.jobs.dto;

import java.io.StringReader;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class GroupPerformanceReportDataDto {
	private Integer premiseId;
	private String premise;
	private Integer groupEventId;
	private String groupId;
	private boolean isActive;
	private Object performanceData;
	private Date groupStartDate;
	private Date groupEndDate;
	
	private GroupPerformanceAttribute performanceAttribute;

	public Integer getPremiseId() {
		return premiseId;
	}

	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}

	public String getPremise() {
		return premise;
	}

	public void setPremise(String premise) {
		this.premise = premise;
	}

	public Integer getGroupEventId() {
		return groupEventId;
	}

	public void setGroupEventId(Integer groupEventId) {
		this.groupEventId = groupEventId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Object getPerformanceData() {
		return performanceData;
	}

	public void setPerformanceData(Object performanceData) {
		this.performanceData = performanceData;
		try{
		if(this.performanceData != null)
		{
			StringReader reader = new StringReader(this.performanceData.toString());
			JAXBContext jaxbContext = JAXBContext
					.newInstance(GroupPerformanceAttribute.class);			
 			Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
 			this.performanceAttribute = (GroupPerformanceAttribute) jaxbMarshaller.unmarshal(reader);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public Date getGroupStartDate() {
		return groupStartDate;
	}

	public void setGroupStartDate(Date groupStartDate) {
		this.groupStartDate = groupStartDate;
	}

	public Date getGroupEndDate() {
		return groupEndDate;
	}

	public void setGroupEndDate(Date groupEndDate) {
		this.groupEndDate = groupEndDate;
	}

	public GroupPerformanceAttribute getPerformanceAttribute() {
		return performanceAttribute;
	}

	public void setPerformanceAttribute(
			GroupPerformanceAttribute performanceAttribute) {
		this.performanceAttribute = performanceAttribute;
	}
	
	

}
