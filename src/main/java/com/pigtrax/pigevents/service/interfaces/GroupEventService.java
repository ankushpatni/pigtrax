package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.GroupEvent;

public interface GroupEventService {
	
GroupEvent getGroupEventByGroupId(final String groupId) throws PigTraxException;
	
	int addGroupEvent(final GroupEvent groupEvent) throws PigTraxException;
	
	int updateGroupEvent(final GroupEvent groupEvent) throws SQLException;
	
	int updateGroupEventStatus( final String groupI, final Boolean groupStatus ) throws SQLException;

}
