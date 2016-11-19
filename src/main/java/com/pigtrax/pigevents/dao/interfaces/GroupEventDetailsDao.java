package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pigtrax.pigevents.beans.GroupEventDetails;

public interface GroupEventDetailsDao {
	
	List<GroupEventDetails> groupEventDetailsListByGroupId(final int groupId);
	
	GroupEventDetails groupEventDetailsListById(final Integer id) ;
	   
	int updateGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException;
	   
	int addGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException;
	 
	void deleteGroupEventDetailsByGroupId(final Integer id) throws SQLException;
	
	void deleteGroupEventDetailsByPigletEvent(final Integer pigletStatusEventId) throws SQLException;

	GroupEventDetails groupEventDetailsListByIdAndSalesId(Integer id,
			Integer salesId);

	GroupEventDetails groupEventDetailsListByIdAndRemovalId(Integer id,
			Integer removalId);
	
	List<GroupEventDetails> getAllTransfers( final int groupEventId) throws SQLException;
	
	Map<Integer, Integer> getInventoryCntByWeek(final Integer groupId, final Date ServDateSTART, final Date ServDateEND) throws Exception;
}
