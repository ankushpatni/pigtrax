package com.pigtrax.master.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.TransportDestination;

public interface TransportDestinationService {
	
	/**
	 * To get the list of TransportDestination
	 * @return List<TransportDestination>
	 */
	public List<TransportDestination> getTransportDestinationList( int generatedCompanyId );
	
	
	/**
	 * To Insert TransportDestination record
	 * @return int (Success with int>0)
	 */
	
	public int insertTransportDestinationRecord(TransportDestination transportDestination) throws SQLException;
	
	/**
	 * To update TransportDestination record
	 * @return TransportDestination 
	 */
	
	public int updateTransportDestinationRecord(TransportDestination transportDestination) throws SQLException;
	
	/**
	 * Load the TransportDestination information based on auto generated generatedBarnId
	 * @param Barn
	 * @return
	 * @throws SQLException
	 */
	
	public TransportDestination findByTransportDestinationByAutoGeneratedId(final int generatedTransportDestinationId) throws SQLException;
	


}
