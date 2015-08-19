package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.GroupEvent;

public interface GroupEventService {
	
GroupEvent getGroupEventByGroupId(final String groupId) throws PigTraxException;
	
	int addGroupEvent(final GroupEvent groupEvent) throws PigTraxException;
	
	int updateGroupEvent(final GroupEvent groupEvent) throws PigTraxException;
	
	int updateGroupEventStatus( final String groupI, final Boolean groupStatus ) throws SQLException;
	
	List<Map<Integer,String>> getGroupEventAndDetailByGroupId(String groupId)
			throws PigTraxException;

}
