package com.pigtrax.master.dto;

import java.util.Date;

public class Premises {

	public int id;
	public String permiseId;
	public int companyId;
	public String name;
	public String address;
	public String city;
	public String state;
	public String zipcode;
	private boolean isActive;
	private Date lastUpdated;
	private String userUpdated;
	private String gpsLatittude;
	private String gpsLongitude;
	private String premiseType;
	private Integer premiseTypeId;
	private String language;
	private String sowSource;
	private String otherCity;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPermiseId() {
		return permiseId;
	}
	public void setPermiseId(String permiseId) {
		this.permiseId = permiseId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
	public String getGpsLatittude() {
		return gpsLatittude;
	}
	public void setGpsLatittude(String gpsLatittude) {
		this.gpsLatittude = gpsLatittude;
	}
	public String getGpsLongitude() {
		return gpsLongitude;
	}
	public void setGpsLongitude(String gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}
	public String getPremiseType() {
		return premiseType;
	}
	public void setPremiseType(String premiseType) {
		this.premiseType = premiseType;
	}
	public Integer getPremiseTypeId() {
		return premiseTypeId;
	}
	public void setPremiseTypeId(Integer premiseTypeId) {
		this.premiseTypeId = premiseTypeId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSowSource() {
		return sowSource;
	}
	public void setSowSource(String sowSource) {
		this.sowSource = sowSource;
	}
	public String getOtherCity() {
		return otherCity;
	}
	public void setOtherCity(String otherCity) {
		this.otherCity = otherCity;
	}
	
	
	
}
