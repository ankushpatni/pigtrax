package com.pigtrax.pigevents.dto;

import java.util.Comparator;
import java.util.Date;

public class PigInfoDto implements Comparable<PigInfoDto>{
	private Integer id;
	private String pigId;
	private String damId;
	private String sireId;
	private Date entryDate;
	private String origin;
	private Integer gline;
	private Integer gcompany;
	private Date birthDate;
	private String tattoo;
	private String alternateTattoo;
	private String remarks;
	private Integer companyId;
	private Integer roomId;
	private Integer barnId;
	private Integer sexTypeId;
	private String userUpdated;
	private String searchText;
	private String searchOption;
	private Date  currentFarrowEventDate;
	private Integer farrowEventId;
	private boolean active;
	private Integer parity;
	private Integer gfunctionTypeId;
	private boolean enableChangeId;
	private String newPigId;
	private Date changePigIdDate;
	private Integer originId;
	private Integer premiseId;
	private Integer selectedPremise;
	private String entryDateStr;
	private String birthDateStr;
	private String changePigIdDateStr;
	private String transferFromGroup;
	private Integer transferFromGroupId;
	
	
	public Integer getFarrowEventId() {
		return farrowEventId;
	}

	public void setFarrowEventId(Integer farrowEventId) {
		this.farrowEventId = farrowEventId;
	}

	public Date getCurrentFarrowEventDate() {
		return currentFarrowEventDate;
	}

	public void setCurrentFarrowEventDate(Date currentFarrowEventDate) {
		this.currentFarrowEventDate = currentFarrowEventDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getPigId() {
		return pigId;
	}

	public void setPigId(String pigId) {
		this.pigId = pigId;
	}

	public String getDamId() {
		return damId;
	}

	public void setDamId(String damId) {
		this.damId = damId;
	}

	public String getSireId() {
		return sireId;
	}

	public void setSireId(String sireId) {
		this.sireId = sireId;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getGline() {
		return gline;
	}

	public void setGline(Integer gline) {
		this.gline = gline;
	}

	public Integer getGcompany() {
		return gcompany;
	}

	public void setGcompany(Integer gcompany) {
		this.gcompany = gcompany;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getTattoo() {
		return tattoo;
	}

	public void setTattoo(String tattoo) {
		this.tattoo = tattoo;
	}

	public String getAlternateTattoo() {
		return alternateTattoo;
	}

	public void setAlternateTattoo(String alternateTattoo) {
		this.alternateTattoo = alternateTattoo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getBarnId() {
		return barnId;
	}

	public void setBarnId(Integer barnId) {
		this.barnId = barnId;
	}

	public Integer getSexTypeId() {
		return sexTypeId;
	}

	public void setSexTypeId(Integer sexTypeId) {
		this.sexTypeId = sexTypeId;
	}
	
	
	
	public String getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	
	public Integer getParity() {
		return parity;
	}

	public void setParity(Integer parity) {
		this.parity = parity;
	}
	
	

	public Integer getGfunctionTypeId() {
		return gfunctionTypeId;
	}

	public void setGfunctionTypeId(Integer gfunctionTypeId) {
		this.gfunctionTypeId = gfunctionTypeId;
	}
	
	

	public boolean isEnableChangeId() {
		return enableChangeId;
	}

	public void setEnableChangeId(boolean enableChangeId) {
		this.enableChangeId = enableChangeId;
	}
	

	public String getNewPigId() {
		return newPigId;
	}

	public void setNewPigId(String newPigId) {
		this.newPigId = newPigId;
	}
	

	public Date getChangePigIdDate() {
		return changePigIdDate;
	}

	public void setChangePigIdDate(Date changePigIdDate) {
		this.changePigIdDate = changePigIdDate;
	}
	
	

	public Integer getOriginId() {
		return originId;
	}

	public void setOriginId(Integer originId) {
		this.originId = originId;
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
	
	public String getEntryDateStr() {
		return entryDateStr;
	}

	public void setEntryDateStr(String entryDateStr) {
		this.entryDateStr = entryDateStr;
	}

	public String getBirthDateStr() {
		return birthDateStr;
	}

	public void setBirthDateStr(String birthDateStr) {
		this.birthDateStr = birthDateStr;
	}

	
	public String getChangePigIdDateStr() {
		return changePigIdDateStr;
	}

	public void setChangePigIdDateStr(String changePigIdDateStr) {
		this.changePigIdDateStr = changePigIdDateStr;
	}

	public String getTransferFromGroup() {
		return transferFromGroup;
	}

	public void setTransferFromGroup(String transferFromGroup) {
		this.transferFromGroup = transferFromGroup;
	}
	
	public Integer getTransferFromGroupId() {
		return transferFromGroupId;
	}

	public void setTransferFromGroupId(Integer transferFromGroupId) {
		this.transferFromGroupId = transferFromGroupId;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("PigInfo [");
		buffer.append("id : "+this.id);
		buffer.append(", "+"pigId : "+this.pigId);
		buffer.append(", "+"sireId : "+this.sireId);
		buffer.append(", "+"damId : "+this.damId);
		buffer.append(", "+"entryDate : "+this.entryDate);
		buffer.append(", "+"origin : "+this.origin);
		buffer.append(", "+"gline : "+this.gline);
		buffer.append(", "+"gcompany : "+this.gcompany);
		buffer.append(", "+"birthDate : "+this.birthDate);
		buffer.append(", "+"tattoo : "+this.tattoo);
		buffer.append(", "+"alternateTattoo : "+this.alternateTattoo);
		buffer.append(", "+"remarks : "+this.remarks);
		buffer.append(", "+"companyId : "+this.companyId);
		buffer.append(", "+"penId : "+this.roomId);
		buffer.append(", "+"barnId : "+this.barnId);
		buffer.append(", "+"sexTypeId"+this.sexTypeId);
		buffer.append(", "+"active"+this.active);
		buffer.append(" ] ");
		return buffer.toString();
	}

	public int compareTo(PigInfoDto o) {
		return this.getId()-o.getId();
	}

}