package com.pigtrax.pigevents.dto;

import java.util.Date;

public class PigletStatusEventDto implements Comparable<PigletStatusEventDto>{
	private Integer id;
	private Integer weanId;
	private Integer fosterId;
	private Integer fosterinId;
	private Integer deathId;
	private String pigId;
	private Integer pigInfoId;
	private Integer fosterFrom;
	private Integer fosterTo;
	private Date fosterToDateTime;
	private Integer pigletStatusEventTypeId;
	private Date eventDateTime;
	private Integer numberOfPigs;
	private Double weightInKgs;
	private String eventReason;
	private String remarks;
	private Integer sowCondition;
	private String weanGroupId;
	private Date lastUpdated;
	private String userUpdated;
    private Integer farrowEventId;
    private FarrowEventDto farrowEventDto;
    private Integer weanPigNum;
    private Double weanPigWt;
    private Integer fosterPigNum;
    private Double fosterPigWt;
    private String fosterToPigId;
    private Integer fosterFarrowId;
	private Integer deathPigNum;
    private Double deathPigWt;
    private String language;
    private Integer companyId;
    private String searchText;
    private String searchOption;
    private String pigletStatusEventType;
    private Integer totalfoserInCnt;
    private String fosterFromPigId; 
    private Integer fosterFarrowEventId;
    private Date weanEventDateTime;
    private Date fosterEventDateTime;
    private Date deathEventDateTime;
    private Integer groupEventId;
    private String groupId;
    private Integer mortalityReasonTypeId;
    private Integer penId;
    private Integer premiseId;
    private Integer selectedPremise;
    private String deathEventDateStr;
    private String fosterEventDateStr;
    private String weanEventDateStr;
    private Integer numberOfPigsLW;
    
    
    
    public Integer getFosterFarrowEventId() {
		return fosterFarrowEventId;
	}

	public void setFosterFarrowEventId(Integer fosterFarrowEventId) {
		this.fosterFarrowEventId = fosterFarrowEventId;
	}

	public Integer getTotalfoserInCnt() {
		return totalfoserInCnt;
	}

	public void setTotalfoserInCnt(Integer totalfoserInCnt) {
		this.totalfoserInCnt = totalfoserInCnt;
	}

	public Integer getFosterFarrowId() {
		return fosterFarrowId;
	}

	public void setFosterFarrowId(Integer fosterFarrowId) {
		this.fosterFarrowId = fosterFarrowId;
	}
	
	public Integer getWeanId() {
		return weanId;
	}

	public void setWeanId(Integer weanId) {
		this.weanId = weanId;
	}

	public Integer getFosterId() {
		return fosterId;
	}

	public void setFosterId(Integer fosterId) {
		this.fosterId = fosterId;
	}

	public Integer getFosterinId() {
		return fosterinId;
	}

	public void setFosterinId(Integer fosterinId) {
		this.fosterinId = fosterinId;
	}

	public Integer getDeathId() {
		return deathId;
	}

	public void setDeathId(Integer deathId) {
		this.deathId = deathId;
	}

    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPigInfoId() {
		return pigInfoId;
	}

	public void setPigInfoId(Integer pigInfoId) {
		this.pigInfoId = pigInfoId;
	}

	public Integer getPigletStatusEventTypeId() {
		return pigletStatusEventTypeId;
	}

	public void setPigletStatusEventTypeId(Integer pigletStatusEventTypeId) {
		this.pigletStatusEventTypeId = pigletStatusEventTypeId;
	}

	public Date getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public Integer getNumberOfPigs() {
		return numberOfPigs;
	}

	public void setNumberOfPigs(Integer numberOfPigs) {
		this.numberOfPigs = numberOfPigs;
	}

	public Double getWeightInKgs() {
		return weightInKgs;
	}

	public void setWeightInKgs(Double weightInKgs) {
		this.weightInKgs = weightInKgs;
	}

	public String getEventReason() {
		return eventReason;
	}

	public void setEventReason(String eventReason) {
		this.eventReason = eventReason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getSowCondition() {
		return sowCondition;
	}

	public void setSowCondition(Integer sowCondition) {
		this.sowCondition = sowCondition;
	}

	public String getWeanGroupId() {
		return weanGroupId;
	}

	public void setWeanGroupId(String weanGroupId) {
		this.weanGroupId = weanGroupId;
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

	public Integer getFosterFrom() {
		return fosterFrom; 	 
	}

	public void setFosterFrom(Integer fosterFrom) {
		this.fosterFrom = fosterFrom;
	}

	public Integer getFosterTo() {
		return fosterTo;
	}

	public void setFosterTo(Integer fosterTo) {
		this.fosterTo = fosterTo;
	}

	public Integer getFarrowEventId() {
		return farrowEventId;
	}

	public void setFarrowEventId(Integer farrowEventId) {
		this.farrowEventId = farrowEventId;
	}

	public FarrowEventDto getFarrowEventDto() {
		return farrowEventDto;
	}

	public void setFarrowEventDto(FarrowEventDto farrowEventDto) {
		this.farrowEventDto = farrowEventDto;
	}

	public Integer getWeanPigNum() {
		return weanPigNum;
	}

	public void setWeanPigNum(Integer weanPigNum) {
		this.weanPigNum = weanPigNum;
	}

	public Double getWeanPigWt() {
		return weanPigWt;
	}

	public void setWeanPigWt(Double weanPigWt) {
		this.weanPigWt = weanPigWt;
	}

	public Integer getFosterPigNum() {
		return fosterPigNum;
	}

	public void setFosterPigNum(Integer fosterPigNum) {
		this.fosterPigNum = fosterPigNum;
	}

	public Double getFosterPigWt() {
		return fosterPigWt;
	}

	public void setFosterPigWt(Double fosterPigWt) {
		this.fosterPigWt = fosterPigWt;
	}

	public String getFosterToPigId() {
		return fosterToPigId;
	}

	public void setFosterToPigId(String fosterToPigId) {
		this.fosterToPigId = fosterToPigId;
	}

	public Integer getDeathPigNum() {
		return deathPigNum;
	}

	public void setDeathPigNum(Integer deathPigNum) {
		this.deathPigNum = deathPigNum;
	}

	public Double getDeathPigWt() {
		return deathPigWt;
	}

	public void setDeathPigWt(Double deathPigWt) {
		this.deathPigWt = deathPigWt;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPigId() {
		return pigId;
	}

	public void setPigId(String pigId) {
		this.pigId = pigId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public String getPigletStatusEventType() {
		return pigletStatusEventType;
	}

	public void setPigletStatusEventType(String pigletStatusEventType) {
		this.pigletStatusEventType = pigletStatusEventType;
	}
	
	public Date getFosterToDateTime() {
		return fosterToDateTime;
	}

	public void setFosterToDateTime(Date fosterToDateTime) {
		this.fosterToDateTime = fosterToDateTime;
	}

	public String getFosterFromPigId() {
		return fosterFromPigId;
	}

	public void setFosterFromPigId(String fosterFromPigId) {
		this.fosterFromPigId = fosterFromPigId;
	}

	public Date getWeanEventDateTime() {
		return weanEventDateTime;
	}

	public void setWeanEventDateTime(Date weanEventDateTime) {
		this.weanEventDateTime = weanEventDateTime;
	}

	public Date getFosterEventDateTime() {
		return fosterEventDateTime;
	}

	public void setFosterEventDateTime(Date fosterEventDateTime) {
		this.fosterEventDateTime = fosterEventDateTime;
	}

	public Date getDeathEventDateTime() {
		return deathEventDateTime;
	}

	public void setDeathEventDateTime(Date deathEventDateTime) {
		this.deathEventDateTime = deathEventDateTime;
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

	public Integer getMortalityReasonTypeId() {
		return mortalityReasonTypeId;
	}

	public void setMortalityReasonTypeId(Integer mortalityReasonTypeId) {
		this.mortalityReasonTypeId = mortalityReasonTypeId;
	}

	public Integer getPenId() {
		return penId;
	}

	public void setPenId(Integer penId) {
		this.penId = penId;
	}

	public Integer getPremiseId() {
		return premiseId;
	}

	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}

	public Integer getSelectedPremise() {
		return selectedPremise;
	}

	public void setSelectedPremise(Integer selectedPremise) {
		this.selectedPremise = selectedPremise;
	}

	public String getDeathEventDateStr() {
		return deathEventDateStr;
	}

	public void setDeathEventDateStr(String deathEventDateStr) {
		this.deathEventDateStr = deathEventDateStr;
	}

	public String getFosterEventDateStr() {
		return fosterEventDateStr;
	}

	public void setFosterEventDateStr(String fosterEventDateStr) {
		this.fosterEventDateStr = fosterEventDateStr;
	}

	public String getWeanEventDateStr() {
		return weanEventDateStr;
	}

	public void setWeanEventDateStr(String weanEventDateStr) {
		this.weanEventDateStr = weanEventDateStr;
	}

	public Integer getNumberOfPigsLW() {
		return numberOfPigsLW;
	}

	public void setNumberOfPigsLW(Integer numberOfPigsLW) {
		this.numberOfPigsLW = numberOfPigsLW;
	}

	public int compareTo(PigletStatusEventDto o) {
		int value = 0;
		if (this.getFarrowEventDto() != null && o.getFarrowEventDto()!=null){
			if (value == 0){
				value = this.getFarrowEventDto().getFarrowDateTime().compareTo(o.getFarrowEventDto().getFarrowDateTime());
			}
			
			if (value == 0){
				Date thisDateObject=null;
				if (this.getFosterEventDateTime() != null){
					thisDateObject=this.getFosterEventDateTime();
				}else if (this.getWeanEventDateTime() != null){
					thisDateObject = this.getWeanEventDateTime();
				} else if (this.getDeathEventDateTime()!=null){
					thisDateObject=this.getDeathEventDateTime();
				}
				Date otherDateObject=null;
				if (o.getFosterEventDateTime() != null){
					otherDateObject=o.getFosterEventDateTime();
				}else if (o.getWeanEventDateTime() != null){
					otherDateObject = o.getWeanEventDateTime();
				} else if (o.getDeathEventDateTime()!=null){
					otherDateObject=o.getDeathEventDateTime();
				}
				if (thisDateObject!=null && otherDateObject!=null){
					value = thisDateObject.compareTo(otherDateObject);
				}
			}
			
			
		}
		return value;
	} 
	
	
	
}
