package com.pigtrax.report.bean;

import java.math.BigInteger;
import java.util.Date;

public class InventoryStatusBean {
	private String sowSource;
	private String barnId;
	private String phaseType;
	private String groupId;
	private String animalType;
	private Integer head;
	private Date dateOfFeed;

	public String getSowSource() {
		return sowSource;
	}

	public void setSowSource(String sowSource) {
		if(sowSource == null ) sowSource = "";
		this.sowSource = sowSource;
	}

	public String getBarnId() {
		return barnId;
	}

	public void setBarnId(String barnId) {
		if(barnId == null ) barnId = "";
		this.barnId = barnId;
	}

	public String getPhaseType() {
		return phaseType;
	}

	public void setPhaseType(String phaseType) {
		if(phaseType == null ) phaseType = "";
		this.phaseType = phaseType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		if(groupId == null ) groupId = "";
		this.groupId = groupId;
	}

	public String getAnimalType() {
		return animalType;
	}

	public void setAnimalType(String animalType) {
		if(animalType == null ) animalType = "";
		this.animalType = animalType;
	}

	public Integer getHead() {
		return head;
	}

	public void setHead(Integer head) {
		this.head = head;
	}

	public Date getDateOfFeed() {
		return dateOfFeed;
	}

	public void setDateOfFeed(Date dateOfFeed) {
		this.dateOfFeed = dateOfFeed;
	}

}
