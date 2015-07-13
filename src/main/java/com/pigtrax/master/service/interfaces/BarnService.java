package com.pigtrax.master.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.Barn;

public interface BarnService {
	

	/**
	 * To get the list of Barn
	 * @return List<Barn>
	 */
	public List<Barn> getBarnList( int generatedPremisesId );
	
	
	/**
	 * To update Barn Status
	 * @return int
	 */
	
	public int updateBarnStatus(final String barnID, final Boolean barnStatus) throws SQLException;
	
	/**
	 * To Insert Barn record
	 * @return int (Success with int>0)
	 */
	
	public int insertBarnRecord(Barn barn) throws SQLException;
	
	/**
	 * To update Barn record
	 * @return Barn 
	 */
	
	public int updateBarnRecord(Barn barn) throws SQLException;
}
