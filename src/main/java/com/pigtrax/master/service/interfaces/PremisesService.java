package com.pigtrax.master.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.Premises;

public interface PremisesService {
	
	
	/**
	 * To get the list of Premises
	 * @return List<Premises>
	 */
	public List<Premises> getPremisesList( int generatedCompanyId );
	
	/**
	 * To update Premises Status
	 * @return 
	 */
	public int updatePremisesStatus(final String premisesID,final Boolean premisesStatus) throws SQLException;
	
	
	/**
	 * To Insert Premises record
	 * @return int (Success with int>0)
	 */
	public int insertPremisesRecord(Premises premises) throws SQLException;
	
	
	/**
	 * To Insert Update record
	 * @return int (Success with int>0)
	 */
	public int updatePremisesRecord(Premises premises) throws SQLException;
	
	/**
	 * Load the Premises information based on auto generated premisesId
	 * @param Premises
	 * @return
	 * @throws SQLException
	 */

	public Premises findByPremisesByAutoGeneratedId(int generatedPremisesId)throws SQLException;
}
