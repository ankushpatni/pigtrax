package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.GroupEvent;

public interface GroupEventService {
	
GroupEvent getGroupEventByGroupId(final String groupId, final int companyId) throws PigTraxException;
	
	int addGroupEvent(final GroupEvent groupEvent) throws PigTraxException;
	
	int updateGroupEvent(final GroupEvent groupEvent) throws PigTraxException;
	
	int updateGroupEventStatus( final GroupEvent groupEvent ) throws PigTraxException;
	
	List<Map<Integer,String>> getGroupEventAndDetailByGroupId(String groupId, int companyId)
			throws PigTraxException;
	
	public int updateGroupEventCurrentInventory(final GroupEvent groupEvent) throws PigTraxException;
	
	 GroupEvent getGroupEventByGeneratedGroupId(final int groupId,  final int companyId);
	 
	 public String getListoFFollowerId(final String groupId) throws PigTraxException ;
	 
	 Map<Integer,GroupEvent> getGroupEventByCompanyId( int companyId);
		

}
