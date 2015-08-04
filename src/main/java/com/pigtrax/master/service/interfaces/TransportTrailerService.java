package com.pigtrax.master.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.TransportTrailer;

public interface TransportTrailerService {
	
	/**
	 * To get the list of TransportTrailer
	 * @return List<TransportTrailer>
	 */
	public List<TransportTrailer> getTransportTrailerList( int generatedCompanyId );
	
	
	/**
	 * To Insert TransportTrailer record
	 * @return int (Success with int>0)
	 */
	
	public int insertTransportTrailer(TransportTrailer transportTrailer) throws SQLException;
	
	/**
	 * To update TransportTrailer record
	 * @return TransportTrailer 
	 */
	
	public int updateTransportTrailer(TransportTrailer transportTrailer) throws SQLException;
	
	/**
	 * Load the TransportTrailer information based on auto generated generatedTransportTrailerId
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	
	public TransportTrailer findByTransportTrailerByAutoGeneratedId(final int generatedTransportTrailerId) throws SQLException;


}
