package com.pigtrax.pigevents.dto;

import java.util.Date;

public class PigletEventDto {
	
	private Integer pigletId;
	private String tattooId;
	private double weightAtBirth;
	private double weightAtWeaning;
	private Date lastUpdated;
	private String userUpdated;
	private String pigId;
	private String searchText;
	private String searchOption;
	private Integer farrowEventId;
	private String farrowId;
	private Integer companyId;
	private String language;
	private FarrowEventDto farrowEventDto;
	private Integer premiseId;
	private Integer litterId;
	private Integer selectedPremise;
	private Integer pigInfoId;
	
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getPigletId() {
		return pigletId;
	}
	public void setPigletId(Integer pigletId) {
		this.pigletId = pigletId;
	}
	public Integer getFarrowEventId() {
		return farrowEventId;
	}
	public void setFarrowEventId(Integer farrowId) {
		this.farrowEventId = farrowId;
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
	public String getPigId() {
		return pigId;
	}
	public void setPigId(String pigId) {
		this.pigId = pigId;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getSearchOption() {
		return searchOption;
	}
	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
	public FarrowEventDto getFarrowEventDto() {
		return farrowEventDto;
	}
	public void setFarrowEventDto(FarrowEventDto farrowEventDto) {
		this.farrowEventDto = farrowEventDto;
	}
	public String getFarrowId() {
		return farrowId;
	}
	public void setFarrowId(String farrowId) {
		this.farrowId = farrowId;
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
	public Integer getSelectedPremise() {
		return selectedPremise;
	}
	public void setSelectedPremise(Integer selectedPremise) {
		this.selectedPremise = selectedPremise;
	}
	public Integer getPigInfoId() {
		return pigInfoId;
	}
	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}
	
	
	
}
