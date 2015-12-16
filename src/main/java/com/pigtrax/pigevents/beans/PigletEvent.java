package com.pigtrax.pigevents.beans;

import java.util.Date;

public class PigletEvent {

	private Integer id;
	private String tattooId;
	private double weightAtBirth;
	private double weightAtWeaning;
	private Date lastUpdated;
	private String userUpdated;
	private Integer farrowEventId;
	private Integer premiseId;
	private Integer litterId;
	private Integer pigInfoId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTattooId() {
		return tattooId;
	}
	public void setTattooId(String tattooId) {
		this.tattooId = tattooId;
	}
	public double getWeightAtBirth() {
		return weightAtBirth;
	}
	public void setWeightAtBirth(double weightAtBirth) {
		this.weightAtBirth = weightAtBirth;
	}
	public double getWeightAtWeaning() {
		return weightAtWeaning;
	}
	public void setWeightAtWeaning(double weightAtWeaning) {
		this.weightAtWeaning = weightAtWeaning;
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
	public Integer getFarrowEventId() {
		return farrowEventId;
	}
	public void setFarrowEventId(Integer farrowEventId) {
		this.farrowEventId = farrowEventId;
	}
	public Integer getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}
	public Integer getLitterId() {
		return litterId;
	}
	public void setLitterId(Integer litterId) {
		this.litterId = litterId;
	}
	public Integer getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	
	
}
