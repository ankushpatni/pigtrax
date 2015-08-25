package com.pigtrax.pigevents.service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.util.UserUtil;

@Repository
public class GroupEventServiceImpl implements GroupEventService{
	
	private static final Logger logger = Logger.getLogger(PregnancyEventServiceImpl.class);
		
	@Autowired 
	GroupEventDao groupEventDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired 
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired 
	GroupEventDetailsDao groupEventDetailsDao;
	

	@Override
	public GroupEvent getGroupEventByGroupId(String groupId, int companyId)
			throws PigTraxException {
		try 
		{
			return groupEventDao.getGroupEventByGroupId(groupId,companyId);
		}
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
	@Override
	public GroupEvent getGroupEventByGeneratedGroupId(final int groupId,  final int companyId) {
				return groupEventDao.getGroupEventByGeneratedGroupId(groupId,companyId);		
	}
	
	@Override
	public List getGroupEventAndDetailByGroupId(String groupId, int companyId)
			throws PigTraxException {
		try 
		{
			List phaseType = new ArrayList();
			GroupEvent groupEvent =  groupEventDao.getGroupEventByGroupId(groupId,companyId);
			if(null != groupEvent)
			{
				phaseType.add(groupEvent);
				List<GroupEventDetails> groupEventDetailsList = groupEventDetailsDao.groupEventDetailsListByGroupId(groupEvent.getId());
				if(null != groupEventDetailsList)
				{
					phaseType.add(groupEventDetailsList);
				}
			}
			return phaseType;
		}
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
	

	@Override
	@Transactional("ptxJTransactionManager")
	public int addGroupEvent(GroupEvent groupEvent) throws PigTraxException {
		try {
			int generatedId = groupEventDao.addGroupEvent(groupEvent);
			PigTraxEventMaster master = new PigTraxEventMaster();
			master.setUserUpdated(UserUtil.getLoggedInUser());
			master.setEventTime(groupEvent.getGroupStartDateTime());
			master.setGroupEventId(generatedId);
			master.setLastUpdated( new java.sql.Date(System.currentTimeMillis()));
			eventMasterDao.insertEntryEventDetails(master);
			
			return generatedId;
		} 
		catch (SQLException sqlEx) {
			if ("23505".equals(sqlEx.getSQLState()))
			{
				throw new PigTraxException("GroupId already exists",
						sqlEx.getSQLState(), true);
			} 
			else
			{
				throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());
			}
		} 
		catch (DuplicateKeyException sqlEx)
		{
			logger.info("DuplicateKeyException : " + sqlEx.getRootCause() + "/"
					+ sqlEx.getCause());
			throw new PigTraxException(
					"Group Id already Exist. Please check Group Id",
					"", true);
		}
	}

	@Override
	public int updateGroupEvent(GroupEvent groupEvent) throws PigTraxException {
		
		try {
			int generatedId = groupEventDao.updateGroupEvent(groupEvent);
			return generatedId;
		} 
		catch (SQLException sqlEx) {
			
				throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());			
		} 
	}

	@Override
	public int updateGroupEventStatus(String groupId, Boolean groupStatus)
			throws SQLException {
		return groupEventDao.updateGroupEventStatus(groupId, groupStatus);
	}
	
	public int updateGroupEventCurrentInventory(final GroupEvent groupEvent) throws PigTraxException
	{

		try {
			int generatedId = groupEventDao.updateGroupEventCurrentInventory(groupEvent);
			return generatedId;
		} 
		catch (SQLException sqlEx) {
			
				throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());			
		} 
	}

	
}
