package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.pigtrax.pigevents.beans.SalesEventDetails;

public interface SalesEventDetailsDao {
	
	public SalesEventDetails getSalesEventDetailsById(final int id)
			throws SQLException ;
	
	public List<SalesEventDetails> getSalesEventDetailsListByRemovalId(
			final int removalId) throws SQLException ;
	
	int addSalesEventDetails(final SalesEventDetails salesEventDetails) throws SQLException;
	
	int updateSalesEventDetails(final SalesEventDetails salesEventDetails) throws SQLException;
	
	public void deleteSalesEventDetails(final Integer id) throws SQLException;

	List<SalesEventDetails> getSalesEventDetailsListByGroupId(int groupId)
			throws SQLException;

	List<SalesEventDetails> getSalesEventDetailsListByPigId(int pigInfoIdId)
			throws SQLException;
	
	 Integer getSalesCount(final Date endDate, final Integer groupId);
	 
	 
	 Double getSalesWt(final Date endDate, final Integer groupId);
	 
	 Integer getSalesCount(final Date endDate, final Integer groupId, final Integer marketType);
	 
	 Double getSalesRevenue(final Integer groupId);

}
