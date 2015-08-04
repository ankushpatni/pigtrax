package com.pigtrax.master.dto;

import java.util.Date;

public class TransportTrailer {
	
	public int id;
	public String transportTrailerId;
	private int trailerTypeId;
	private int companyId;
	private Date lastUpdated;
	private String userUpdated;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the trailerId
	 */
	public String getTransportTrailerId() {
		return transportTrailerId;
	}
	/**
	 * @param trailerId the trailerId to set
	 */
	public void setTransportTrailerId(String transportTrailerId) {
		this.transportTrailerId = transportTrailerId;
	}
	/**
	 * @return the trailerTypeId
	 */
	public int getTrailerTypeId() {
		return trailerTypeId;
	}
	/**
	 * @param trailerTypeId the trailerTypeId to set
	 */
	public void setTrailerTypeId(int trailerTypeId) {
		this.trailerTypeId = trailerTypeId;
	}
	/**
	 * @return the companyId
	 */
	public int getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the lastUpdated
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}
	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	/**
	 * @return the userUpdated
	 */
	public String getUserUpdated() {
		return userUpdated;
	}
	/**
	 * @param userUpdated the userUpdated to set
	 */
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TransportTrailer [id=" + id + ", trailerId=" + transportTrailerId
				+ ", trailerTypeId=" + trailerTypeId + ", companyId="
				+ companyId + ", lastUpdated=" + lastUpdated + ", userUpdated="
				+ userUpdated + "]";
	}
	
	

}
