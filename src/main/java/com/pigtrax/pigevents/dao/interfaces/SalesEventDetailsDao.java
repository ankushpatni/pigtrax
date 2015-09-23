package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.SalesEventDetails;

public interface SalesEventDetailsDao {
	
	public SalesEventDetails getSalesEventDetailsById(final int id)
			throws SQLException ;
	
	public List<SalesEventDetails> getSalesEventDetailsListByRemovalId(final String removalId)
			throws SQLException ;
	
	int addSalesEventDetails(final SalesEventDetails salesEventDetails) throws SQLException;
	
	int updateSalesEventDetails(final SalesEventDetails salesEventDetails) throws SQLException;
	
	public void deleteSalesEventDetails(final Integer id) throws SQLException;

}
