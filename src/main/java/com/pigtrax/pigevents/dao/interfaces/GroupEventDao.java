package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.pigevents.beans.GroupEvent;

public interface GroupEventDao {
	
	GroupEvent getGroupEventByGroupId(final String groupId, final int companyId) throws SQLException;
	
	int addGroupEvent(final GroupEvent groupEvent) throws SQLException;
	
	int updateGroupEvent(final GroupEvent groupEvent) throws SQLException;
	
	int updateGroupEventStatus( final String groupI, final Boolean groupStatus ) throws SQLException;
	
	int updateGroupEventCurrentInventory(final GroupEvent groupEvent) throws SQLException;
	
	GroupEvent getGroupEventByGeneratedGroupId(final int groupId, final int companyId);

}
