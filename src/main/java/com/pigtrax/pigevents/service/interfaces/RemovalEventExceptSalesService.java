package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;

public interface RemovalEventExceptSalesService {
	
	public RemovalEventExceptSalesDetails getRemovalEventExceptSalesDetailsById(final int id)
			throws PigTraxException ;
	
	int addRemovalEventExceptSalesDetails(final RemovalEventExceptSalesDetails salesEventDetails) throws PigTraxException;
	
	int updateFeedEventDeupdateRemovalEventExceptSalesDetailstail(final RemovalEventExceptSalesDetails salesEventDetails) throws PigTraxException;
	
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsListByRemovalId(final RemovalEventExceptSalesDetails salesEventDetails)
			throws SQLException ;
	
	public int deleteRemovalExceptSales(RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
			throws PigTraxException;

	List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsByPigInfoId(
			String pigInfoIdId, int companyId) throws PigTraxException;

	List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsByGroupId(
			String groupId, int companyId) throws PigTraxException;
}
