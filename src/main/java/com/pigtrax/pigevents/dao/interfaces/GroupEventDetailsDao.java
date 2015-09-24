package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.GroupEventDetails;

public interface GroupEventDetailsDao {
	
	List<GroupEventDetails> groupEventDetailsListByGroupId(final int groupId);
	
	GroupEventDetails groupEventDetailsListById(final Integer id) ;
	   
	int updateGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException;
	   
	int addGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException;
	 
	void deleteGroupEventDetailsByGroupId(final Integer id) throws SQLException;
	
}
