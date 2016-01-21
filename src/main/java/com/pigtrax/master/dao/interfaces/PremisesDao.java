package com.pigtrax.master.dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pigtrax.master.dto.Premises;
import com.pigtrax.usermanagement.beans.Company;

public interface PremisesDao {
	
	/**
	 * To get the list of Premises
	 * @return List<Company>
	 */
	public List<Premises> getPremisesList( int generatedCompanyId, String premisesType );
	
	/**
	 * To get the list of Premises
	 * @return List<Company>
	 */
	public List<Premises> getPremisesListBySowSource( int generatedCompanyId );
	
	
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
	
	/**
	 * To get the Premises
	 * @return Premises
	 */
	
	public Premises findByPremisesByAutoGeneratedId(int generatedPremisesId)
			throws SQLException;
	
	/**
	 * To get the Premises based on companyId
	 * @return Premises
	 */
	
	public List<Premises> getPremisesListBasedOnCompanyId(int generatedCompanyId)
			throws SQLException;
	
	
	/**
	 * To delete premise data based on generated premise id
	 */
	int deletePremiseData(int generatedPremisesId);

	public List<Premises> getPremisesListNotInFilterPremisesType(
			int generatedCompanyId, String premisesType);

	List<Premises> getPremisesListBasedOnCompanyIdFromView(
			int generatedCompanyId) throws SQLException;

}
