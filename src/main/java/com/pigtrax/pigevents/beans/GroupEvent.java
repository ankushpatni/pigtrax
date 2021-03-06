package com.pigtrax.pigevents.beans;

import java.util.Date;
import java.util.List;

import com.pigtrax.master.dto.RoomPK;

public class GroupEvent {
	
	private Integer id;
	private String groupId;
	private Integer companyId;
	private Date groupStartDateTime;
	private Date groupCloseDateTime;
	private boolean isActive;
	private String remarks;
	private Date lastUpdated;
	private String userUpdated;
	private Integer currentInventory;
	private String previousGroupId;
	private Integer phaseOfProductionTypeId;
	private boolean fromMove; // added to get flag from UI for move
	private Double weightInKgs; 
	private Integer inventoryAdjustment;
	private String groupStartDateStr;
	private String groupCloseDateStr;
	private Integer premiseId;
	private List<RoomPK> roomIds;
	private Integer transferredPigNum;
	private Double transferredPigWt;
	private Integer transferredFromGroupId;
	private Integer transferredToGroupId;
	private String roomId;
	private List<GroupEventPhaseChange> phaseChangeList;
	private Date removalDateTime;
	private Integer roomIdPk;
	private String barnId;
	private String premiseIdStr;
	private Integer roomSpace;
	
	
	
	public Date getRemovalDateTime() {
		return removalDateTime;
	}
	public void setRemovalDateTime(Date removalDateTime) {
		this.removalDateTime = removalDateTime;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Date getGroupStartDateTime() {
		return groupStartDateTime;
	}
	public void setGroupStartDateTime(Date groupStartDateTime) {
		this.groupStartDateTime = groupStartDateTime;
	}
	public Date getGroupCloseDateTime() {
		return groupCloseDateTime;
	}
	public void setGroupCloseDateTime(Date groupCloseDateTime) {
		this.groupCloseDateTime = groupCloseDateTime;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
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
	public Integer getCurrentInventory() {
		return currentInventory;
	}
	public void setCurrentInventory(Integer currentInventory) {
		this.currentInventory = currentInventory;
	}
	public String getPreviousGroupId() {
		return previousGroupId;
	}
	public void setPreviousGroupId(String previousGroupId) {
		this.previousGroupId = previousGroupId;
	}
	public Integer getPhaseOfProductionTypeId() {
		return phaseOfProductionTypeId;
	}
	public void setPhaseOfProductionTypeId(Integer phaseOfProductionTypeId) {
		this.phaseOfProductionTypeId = phaseOfProductionTypeId;
	}
	public boolean isFromMove() {
		return fromMove;
	}
	public void setFromMove(boolean fromMove) {
		this.fromMove = fromMove;
	}
	public Double getWeightInKgs() {
		return weightInKgs;
	}
	public void setWeightInKgs(Double weightInKgs) {
		this.weightInKgs = weightInKgs;
	}
	public Integer getInventoryAdjustment() {
		return inventoryAdjustment;
	}
	public void setInventoryAdjustment(Integer inventoryAdjustment) {
		this.inventoryAdjustment = inventoryAdjustment;
	}
	public String getGroupStartDateStr() {
		return groupStartDateStr;
	}
	public void setGroupStartDateStr(String groupStartDateStr) {
		this.groupStartDateStr = groupStartDateStr;
	}
	public String getGroupCloseDateStr() {
		return groupCloseDateStr;
	}
	public void setGroupCloseDateStr(String groupCloseDateStr) {
		this.groupCloseDateStr = groupCloseDateStr;
	}
	public Integer getPremiseId() {
		return premiseId;
	}
	public void setPremiseId(Integer premiseId) {
		this.premiseId = premiseId;
	}
	public List<RoomPK> getRoomIds() {
		return roomIds;
	}
	public void setRoomIds(List<RoomPK> roomIds) {
		this.roomIds = roomIds;
	}
	public List<GroupEventPhaseChange> getPhaseChangeList() {
		return phaseChangeList;
	}
	public void setPhaseChangeList(List<GroupEventPhaseChange> phaseChangeList) {
		this.phaseChangeList = phaseChangeList;
	}
	public Integer getTransferredPigNum() {
		return transferredPigNum;
	}
	public void setTransferredPigNum(Integer transferredPigNum) {
		this.transferredPigNum = transferredPigNum;
	}
	public Integer getTransferredFromGroupId() {
		return transferredFromGroupId;
	}
	public void setTransferredFromGroupId(Integer transferredFromGroupId) {
		this.transferredFromGroupId = transferredFromGroupId;
	}
	public Integer getTransferredToGroupId() {
		return transferredToGroupId;
	}
	public void setTransferredToGroupId(Integer transferredToGroupId) {
		this.transferredToGroupId = transferredToGroupId;
	}
	public Double getTransferredPigWt() {
		return transferredPigWt;
	}
	public void setTransferredPigWt(Double transferredPigWt) {
		this.transferredPigWt = transferredPigWt;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Integer getRoomIdPk() {
		return roomIdPk;
	}
	public void setRoomIdPk(Integer roomIdPk) {
		this.roomIdPk = roomIdPk;
	}
	public String getBarnId() {
		return barnId;
	}
	public void setBarnId(String barnId) {
		this.barnId = barnId;
	}
	public String getPremiseIdStr() {
		return premiseIdStr;
	}
	public void setPremiseIdStr(String premiseIdStr) {
		this.premiseIdStr = premiseIdStr;
	}
	public Integer getRoomSpace() {
		return roomSpace;
	}
	public void setRoomSpace(Integer roomSpace) {
		this.roomSpace = roomSpace;
	}
	
	
	
}
