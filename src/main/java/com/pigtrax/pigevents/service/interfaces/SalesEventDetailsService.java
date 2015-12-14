package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.SalesEventDetails;

public interface SalesEventDetailsService {
	
	public SalesEventDetails getSalesEventDetailsById(final int id)
			throws PigTraxException ;
	
	int addSalesEventDetails(final SalesEventDetails salesEventDetails) throws PigTraxException;
	
	int updateFeedEventDeupdateSalesEventDetailstail(final SalesEventDetails salesEventDetails) throws PigTraxException;
	
	public List<SalesEventDetails> getSalesEventDetailsListByRemovalId(final SalesEventDetails salesEventDetails)
			throws SQLException ;

	int deleteSalesEventDetails(SalesEventDetails salesEventDetails)
			throws PigTraxException;

	List<SalesEventDetails> getSalesEventDetailsListByGroupId(String groupId,
			int companyId) throws PigTraxException;

	List<SalesEventDetails> getSalesEventDetailsListByPigId(String pigInfoIdId,
			int companyId, Integer premiseId) throws PigTraxException;
	
	List<SalesEventDetails> getSalesEventDetailsListByPigId(String pigInfoIdId,
			int companyId) throws PigTraxException;

}
