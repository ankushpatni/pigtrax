package com.pigtrax.pigevents.beans;

import java.util.Date;

public class PigletEvent {

	private Integer id;
	private String tattooId;
	private double weightAtBirth;
	private double weightAtWeaning;
	private Date lastUpdated;
	private String userUpdated;
	private int farrowEventId;
	
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
	public int getFarrowEventId() {
		return farrowEventId;
	}
	public void setFarrowEventId(int farrowEventId) {
		this.farrowEventId = farrowEventId;
	}
	
}
