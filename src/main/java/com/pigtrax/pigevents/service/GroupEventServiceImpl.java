package com.pigtrax.pigevents.service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.RoomPK;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.beans.GroupEventPhaseChange;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventPhaseChangeDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventRoomDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.util.DateUtil;

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
	
	@Autowired
	GroupEventPhaseChangeDao groupEventPhaseChangeDao;
	
	@Autowired
	GroupEventRoomDao groupEventRoomDao;

	@Override
	public GroupEvent getGroupEventByGroupId(String groupId, int companyId, Integer premiseId)
			throws PigTraxException {
		try 
		{
			return groupEventDao.getGroupEventByGroupId(groupId,companyId, premiseId);
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
	public List getGroupEventAndDetailByGroupId(String groupId, int companyId, Integer premiseId, Integer phaseOfTypeId)
			throws PigTraxException {
		try  
		{
			List phaseType = new ArrayList();
			GroupEvent groupEvent =  null;
			
			if(phaseOfTypeId == null)
				 groupEvent = groupEventDao.getGroupEventByGroupId(groupId,companyId, premiseId);
			else
				groupEvent = groupEventDao.getGroupEventByGroupId(groupId,companyId, premiseId, phaseOfTypeId);
			
			if(groupEvent != null)
			{
				//Pull the phase change details
				
				List<GroupEventPhaseChange> phaseChangeList = groupEventPhaseChangeDao.getPhaseChangeDetails(groupEvent.getId());
				groupEvent.setPhaseChangeList(phaseChangeList);
				
				List<RoomPK> roomIds = groupEventRoomDao.getGroupEventRooms(groupEvent.getId());
				groupEvent.setRoomIds(roomIds);
				
				
				if(null != groupEvent)
				{
					phaseType.add(groupEvent);
					List<GroupEventDetails> groupEventDetailsList = groupEventDetailsDao.groupEventDetailsListByGroupId(groupEvent.getId());
					if(null != groupEventDetailsList)
					{
						phaseType.add(groupEventDetailsList);
					}
					if(null != groupEvent)
					{
						phaseType.add(groupEventDao.getListoFFollowerId(groupEvent.getGroupId()));
					}
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
			groupEvent.setId(generatedId);
			
			GroupEventPhaseChange groupEventPhaseChange = new GroupEventPhaseChange();
			groupEventPhaseChange.setGroupEventId(generatedId);
			groupEventPhaseChange.setPhaseOfProductionTypeId(groupEvent.getPhaseOfProductionTypeId());
			groupEventPhaseChange.setUserUpdated(groupEvent.getUserUpdated());
			groupEventPhaseChange.setPremiseId(groupEvent.getPremiseId());
			
			groupEventPhaseChangeDao.addGroupPhaseChange(groupEventPhaseChange);
			
			groupEventRoomDao.addGroupEventRooms(groupEvent);
			
			PigTraxEventMaster master = new PigTraxEventMaster();
			master.setUserUpdated(groupEvent.getUserUpdated());
			master.setEventTime(groupEvent.getGroupStartDateTime());
			master.setGroupEventId(generatedId);
			master.setLastUpdated( new java.sql.Date(System.currentTimeMillis()));
			eventMasterDao.insertEntryEventDetails(master);
			groupEvent.setId(generatedId);
			
			if(groupEvent.isFromMove())
			{
				GroupEvent groupEventUpdate = groupEventDao.getGroupEventByGroupId(groupEvent.getPreviousGroupId(), groupEvent.getCompanyId(), groupEvent.getPremiseId());
				if(null != groupEventUpdate )
				{
					groupEventUpdate.setCurrentInventory(groupEventUpdate.getCurrentInventory() - groupEvent.getCurrentInventory());
					groupEventDao.updateGroupEventCurrentInventory(groupEventUpdate);
				}
				GroupEventDetails groupEventDetails = getGroupeventDetailsFromgroupEvent(groupEvent);
				if(null!=groupEventDetails)
				{
					groupEventDetailsDao.addGroupEventDetails(groupEventDetails);
				}
				
			}
			
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
			int generatedId =0;
			if(null!=groupEvent && groupEvent.isFromMove())
			{
				//Update the current group Inventory
				GroupEvent currentGroup = groupEventDao.getGroupEventByGroupId(groupEvent.getGroupId(), groupEvent.getCompanyId(), groupEvent.getPremiseId());
				if(null != currentGroup )
				{
					currentGroup.setCurrentInventory(currentGroup.getCurrentInventory() - groupEvent.getTransferredPigNum());
					groupEventDao.updateGroupEventCurrentInventory(currentGroup);
				}
				
				
				//Add new entries to the transferred group
				GroupEventDetails newGroupEventDetails = getGroupeventDetailsFromgroupEvent(groupEvent);
				if(null!=newGroupEventDetails)
				{
					groupEventDetailsDao.addGroupEventDetails(newGroupEventDetails);
				}
				
				GroupEvent newGroupEvent = groupEventDao.getGroupEventByGeneratedGroupId(groupEvent.getTransferredToGroupId(), groupEvent.getCompanyId());
				newGroupEvent.setCurrentInventory(groupEvent.getTransferredPigNum()+newGroupEvent.getCurrentInventory());
				generatedId = groupEventDao.updateGroupEventCurrentInventory(newGroupEvent);
				
			}
			else
			{
				if( null != groupEvent.getInventoryAdjustment() && groupEvent.getInventoryAdjustment() >0)
				{
					groupEvent.setCurrentInventory(groupEvent.getCurrentInventory()-groupEvent.getInventoryAdjustment());
				}
				generatedId = groupEventDao.updateGroupEvent(groupEvent);
				
				groupEventRoomDao.deleteGroupEventRooms(groupEvent.getId());
				groupEventRoomDao.addGroupEventRooms(groupEvent);
				
				Integer currentPhaseId = groupEventPhaseChangeDao.getCurrentPhase(groupEvent.getId());
				if(currentPhaseId != groupEvent.getPhaseOfProductionTypeId())
				{
					groupEventPhaseChangeDao.endDateGroupEventPhase(groupEvent.getId());
					
					GroupEventPhaseChange groupEventPhaseChange = new GroupEventPhaseChange();
					groupEventPhaseChange.setGroupEventId(groupEvent.getId());
					groupEventPhaseChange.setPhaseOfProductionTypeId(groupEvent.getPhaseOfProductionTypeId());
					groupEventPhaseChange.setUserUpdated(groupEvent.getUserUpdated());
					groupEventPhaseChange.setPremiseId(groupEvent.getPremiseId());
					
					groupEventPhaseChangeDao.addGroupPhaseChange(groupEventPhaseChange);
				}
				
			}
			return generatedId;
		} 
		catch (SQLException sqlEx) {
			
				throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());			
		} 
	}

	@Override
	public int updateGroupEventStatus(GroupEvent groupEvent)
			throws PigTraxException {
		
		try {
			if(!groupEvent.isActive())
			{
				return groupEventDao.updateGroupEventStatusWithCloseDate(groupEvent);
			}
			else 
			{
				return groupEventDao.updateGroupEventStatus(groupEvent);
			}
		} 
		catch (SQLException sqlEx) {
			
				throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());			
		} 
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
	
	public GroupEventDetails getGroupeventDetailsFromgroupEvent(GroupEvent groupEvent)
	{
		GroupEventDetails groupEventDetails = new GroupEventDetails();
		groupEventDetails.setGroupId(groupEvent.getTransferredToGroupId());
		groupEventDetails.setNumberOfPigs(groupEvent.getTransferredPigNum());
		groupEventDetails.setDateOfEntry(DateUtil.getToday());
		groupEventDetails.setWeightInKgs(groupEvent.getTransferredPigWt());
		groupEventDetails.setUserUpdated(groupEvent.getUserUpdated());
		groupEventDetails.setFromGroupId(groupEvent.getId());
		return groupEventDetails;
	}
	
	public String getListoFFollowerId(final String groupId) throws PigTraxException 
	{
		try
		{
			return  groupEventDao.getListoFFollowerId(groupId);			
		} 
		catch (SQLException sqlEx)
		{
			throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());			
		} 
	}
	
	public Map<Integer, GroupEvent> getGroupEventByCompanyId(
			int companyId)  {
		Map<Integer, GroupEvent> groupIdMap = new LinkedHashMap<Integer, GroupEvent>();
		try
		{
			List<GroupEvent> groupEventList = groupEventDao.getGroupEventByCompanyId(companyId);
			if (null != groupEventList && groupEventList.size() > 0) 
			{
				for (GroupEvent groupEvent : groupEventList) {
					groupIdMap.put(groupEvent.getId(), groupEvent);
				}
			}
		} 
		catch (SQLException sqlEx)
		{
			logger.info("No GroupEvent found for given company is : " + companyId + "/"
					+ sqlEx.getCause());			
		} 
		return groupIdMap;
	}

	
}
