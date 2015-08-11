package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.pigevents.beans.GroupEvent;

public interface GroupEventDao {
	
	GroupEvent getGroupEventByGroupId(String groupId);
	
	GroupEvent getGroupEventById(Integer id);
	   
	int updateGroupEventDetails(GroupEvent groupEvent) throws SQLException;
	   
	int addGroupEventDetails(GroupEvent groupEvent) throws SQLException;
	 
	void deleteGroupEventByGroupId(Integer id) throws SQLException;

}
