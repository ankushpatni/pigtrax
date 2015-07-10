package com.pigtrax.usermanagement.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.location.Premises;
import com.pigtrax.usermanagement.beans.Company;

public interface PremisesDao {
	
	/**
	 * To get the list of Premises
	 * @return List<Company>
	 */
	public List<Premises> getPremisesList( int generatedCompanyId );
	
	
	/**
	 * To update Premises Status
	 * @return int
	 */
	
	public int updatePremisesStatus(final String premisesID, final Boolean premisesStatus) throws SQLException;
	
	/**
	 * To Insert Premises record
	 * @return int (Success with int>0)
	 */
	
	public int insertPremisesRecord(Premises premises) throws SQLException;
	
	/**
	 * To update Premises record
	 * @return Premises 
	 */
	
	public int updatePremisesRecord(Premises premises) throws SQLException;

}
