package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.GroupEventDetails;

public interface GroupEventDetailsDao {
	
	GroupEventDetails groupEventDetailsListByGroupId(final String groupId);
	
	GroupEventDetails groupEventDetailsListById(final Integer id) ;
	   
	int updateGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException;
	   
	int addGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException;
	 
	void deleteGroupEventDetailsByGroupId(final Integer id) throws SQLException;
	
}
