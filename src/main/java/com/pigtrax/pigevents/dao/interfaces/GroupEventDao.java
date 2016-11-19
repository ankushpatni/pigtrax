package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pigtrax.pigevents.beans.GroupEvent;

public interface GroupEventDao {
	
	GroupEvent getGroupEventByGroupId(final String groupId, final int companyId, Integer premiseId) throws SQLException;
	
	GroupEvent getGroupEventByGroupId(final String groupId, final int companyId, Integer premiseId, Integer phaseOfProductionTypeId) throws SQLException;
	
	int addGroupEvent(final GroupEvent groupEvent) throws SQLException;
	
	int updateGroupEvent(final GroupEvent groupEvent) throws SQLException;
	
	int updateGroupEventStatus( GroupEvent groupEvent ) throws SQLException;
	
	int updateGroupEventCurrentInventory(final GroupEvent groupEvent) throws SQLException;
	
	GroupEvent getGroupEventByGeneratedGroupId(final int groupId, final int companyId);
	
	String getListoFFollowerId(final String groupId) throws SQLException;
	
	public List<GroupEvent> getGroupEventByCompanyId( final int companyId) throws SQLException;
	
	public List<GroupEvent> getGroupEventByPremise( final int premiseId) throws SQLException;
	
	public int updateGroupEventStatusWithCloseDate(final GroupEvent groupEvent)
			throws SQLException;
	
	public int updateGroupEventCurrentInventorywithStatus(final GroupEvent groupEvent) throws SQLException;

	List<GroupEvent> getGroupEventByPremiseWithoutStatus(int premiseId) throws SQLException;
	
	List<GroupEvent> getAllGroups(final Integer premiseId);
	
	Map<String, Object> getStartWtAndHead(final Integer groupId);
	
	 Integer getInventoryCount(final Date endDate, final Integer groupId);
	 
	 Integer getPhaseOfProduction(final Integer groupId);

}
