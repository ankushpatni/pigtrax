package com.pigtrax.master.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.TransportTruck;

public interface TransportTruckService {
	
	/**
	 * To get the list of TransportTruck
	 * @return List<TransportTruck>
	 */
	public List<TransportTruck> getTransportTruckList( int generatedCompanyId );
	
	
	/**
	 * To Insert TransportTruck record
	 * @return int (Success with int>0)
	 */
	
	public int insertTransportTruck(TransportTruck transportTruck) throws SQLException;
	
	/**
	 * To update TransportTruck record
	 * @return TransportTruck 
	 */
	
	public int updateTransportTruck(TransportTruck transportTruck) throws SQLException;
	
	/**
	 * Load the TransportTruck information based on auto generated generatedTransportTruckId
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	
	public TransportTruck findByTransportTruckByAutoGeneratedId(final int generatedTransportTruckId) throws SQLException;


}
