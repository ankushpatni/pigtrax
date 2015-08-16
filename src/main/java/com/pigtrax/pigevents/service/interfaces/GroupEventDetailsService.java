package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;

import com.pigtrax.pigevents.beans.GroupEventDetails;

public interface GroupEventDetailsService {

	GroupEventDetails groupEventDetailsListByGroupId(final String groupId);
	
	GroupEventDetails groupEventDetailsListById(final Integer id) ;
	   
	int updateGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException;
	   
	int addGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException;
	 
	void deleteGroupEventDetailsByGroupId(final Integer id) throws SQLException;
}
