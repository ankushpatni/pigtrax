package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;

import com.pigtrax.pigevents.beans.GroupEvent;

public interface GroupEventService {

	GroupEvent getGroupEventByGroupId(String groupId);
	
	GroupEvent getGroupEventById(Integer id);
	   
	int updateGroupEventDetails(GroupEvent groupEvent) throws SQLException;
	   
	int addGroupEventDetails(GroupEvent groupEvent) throws SQLException;
	 
	void deleteGroupEventByGroupId(Integer id) throws SQLException;

}
