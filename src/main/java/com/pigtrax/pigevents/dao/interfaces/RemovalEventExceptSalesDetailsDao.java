package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;

public interface RemovalEventExceptSalesDetailsDao {
	
	public RemovalEventExceptSalesDetails getRemovalEventExceptSalesDetailsById(final int id)
			throws SQLException ;
	
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsListByRemovalId(final int removalId)
			throws SQLException ;
	
	int addRemovalEventExceptSalesDetails(final RemovalEventExceptSalesDetails salesEventDetails) throws SQLException;
	
	int updateRemovalEventExceptSalesDetails(final RemovalEventExceptSalesDetails salesEventDetails) throws SQLException;


}
