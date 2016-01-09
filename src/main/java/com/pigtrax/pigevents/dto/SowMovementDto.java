package com.pigtrax.pigevents.dto;

import java.util.Date;

public class SowMovementDto {
	
	 	private Integer id;
	    private Integer pigInfoId;
	    private Integer roomId;
	    private Integer premiseId;
	    private Date movementDate;
	    private Date lastUpdated;
	    private String userUpdated;
	    
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
		public Integer getRoomId() {
			return roomId;
		}
		public void setRoomId(Integer roomId) {
			this.roomId = roomId;
		}
		public Integer getPremiseId() {
			return premiseId;
		}
		public void setPremiseId(Integer premiseId) {
			this.premiseId = premiseId;
		}
		public Date getMovementDate() {
			return movementDate;
		}
		public void setMovementDate(Date movementDate) {
			this.movementDate = movementDate;
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
}
