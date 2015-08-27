package com.pigtrax.pigevents.dto;

import java.util.Date;

public class PigInfoDto {
	private Integer id;
	private String pigId;
	private String damId;
	private String sireId;
	private Date entryDate;
	private String origin;
	private String gline;
	private String gcompany;
	private Date birthDate;
	private String tattoo;
	private String alternateTattoo;
	private String remarks;
	private Integer sowCondition;
	private Integer companyId;
	private Integer penId;
	private Integer barnId;
	private Integer sexTypeId;
	private String userUpdated;
	private String searchText;
	private String searchOption;
	private Date  currentFarrowEventDate;
	private Integer farrowEventId;

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

	public String getGline() {
		return gline;
	}

	public void setGline(String gline) {
		this.gline = gline;
	}

	public String getGcompany() {
		return gcompany;
	}

	public void setGcompany(String gcompany) {
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

	public Integer getSowCondition() {
		return sowCondition;
	}

	public void setSowCondition(Integer sowCondition) {
		this.sowCondition = sowCondition;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getPenId() {
		return penId;
	}

	public void setPenId(Integer penId) {
		this.penId = penId;
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
		buffer.append(", "+"sowCondition : "+this.sowCondition);
		buffer.append(", "+"companyId : "+this.companyId);
		buffer.append(", "+"penId : "+this.penId);
		buffer.append(", "+"barnId : "+this.barnId);
		buffer.append(", "+"sexTypeId"+this.sexTypeId);
		buffer.append(" ] ");
		return buffer.toString();
	}

}
