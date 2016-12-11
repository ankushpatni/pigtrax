package com.pigtrax.pigevents.dao.interfaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;

public interface RemovalEventExceptSalesDetailsDao {
	
	public RemovalEventExceptSalesDetails getRemovalEventExceptSalesDetailsById(final int id)
			throws SQLException ;
	
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsListByRemovalId(final int removalId)
			throws SQLException ;
	
	int addRemovalEventExceptSalesDetails(final RemovalEventExceptSalesDetails salesEventDetails) throws SQLException;
	
	int updateRemovalEventExceptSalesDetails(final RemovalEventExceptSalesDetails salesEventDetails) throws SQLException;

	public void deleteRemovalEventExceptSalesDetails(final Integer id) throws SQLException;

	List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsByGroupId(
			int id) throws SQLException;

	List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsByPigInfoId(
			int id) throws SQLException;

	Integer getTotalPigsMortal(Date start, Date end, Integer companyId,Integer premisesId);
	
	Integer getDeadsCount(final java.util.Date endDate, final Integer groupId);
	
	Integer getDeadsCount(final java.util.Date endDate, final Integer groupId, final Integer mortalityReasonType);
	
	Integer getCount(final java.util.Date endDate, final Integer groupId, final Integer removalType);
	
	Map<Integer, Integer> getMortalityCntByWeek(final Integer groupId, final java.util.Date ServDateSTART, final java.util.Date ServDateEND) throws Exception;
}
