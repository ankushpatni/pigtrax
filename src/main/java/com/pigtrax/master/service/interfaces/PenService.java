package com.pigtrax.master.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dto.Pen;

public interface PenService {
	
	/**
	 * To get the list of Pen
	 * @return List<Pen>
	 */
	public List<Pen> getPenList( int generatedRoomId );
	
	
	/**
	 * To update Pen Status
	 * @return int
	 */
	
	public int updatePenStatus(final String penID, final Boolean penStatus) throws SQLException;
	
	/**
	 * To Insert Pen record
	 * @return int (Success with int>0)
	 */
	
	public int insertPenRecord(Pen pen) throws SQLException;
	
	/**
	 * To update Pen record
	 * @return Pen 
	 */
	
	public int updatePenRecord(Pen pen) throws SQLException;
	
	List<Pen> getPenListByBarnId(Integer barnId) throws SQLException;
	
	List<Pen> getPenListByPremiseId(Integer premiseId) throws PigTraxException;
	
	/**
	 * Get the list of Pen for a given company
	 * @param companyId
	 * @return
	 * @throws SQLException
	 */
	List<Pen> getPenListByCompanyId(Integer companyId) throws PigTraxException;


}
