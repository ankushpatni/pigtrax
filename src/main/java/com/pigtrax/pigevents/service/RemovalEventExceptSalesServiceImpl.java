package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.beans.GroupEventPhaseChange;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.SowMovement;
import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventPhaseChangeDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventRoomDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.SowMovementDao;
import com.pigtrax.pigevents.dao.interfaces.TransportJourneyDao;
import com.pigtrax.pigevents.service.interfaces.RemovalEventExceptSalesService;
import com.pigtrax.usermanagement.enums.RemovalEventType;
import com.pigtrax.util.DateUtil;

@Repository
public class RemovalEventExceptSalesServiceImpl implements RemovalEventExceptSalesService{

	@Autowired
	TransportJourneyDao transportJourneyDao;
	
	@Autowired 
	GroupEventDao groupEventDao;
	
	@Autowired
	RemovalEventExceptSalesDetailsDao removalEventExceptSalesDetailsDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	SowMovementDao sowMovementDao;
	
	@Autowired
	GroupEventDetailsDao groupEventDetailsDao;
	
	@Autowired
	GroupEventPhaseChangeDao groupEventPhaseChangeDao;
	
	@Autowired
	GroupEventRoomDao groupEventRoomDao;
	
	@Autowired 
	PigTraxEventMasterDao eventMasterDao;
	
	@Override
	public RemovalEventExceptSalesDetails getRemovalEventExceptSalesDetailsById(int removalId) throws PigTraxException {
		try 
		{
			RemovalEventExceptSalesDetails removalEventExceptSalesDetails =  removalEventExceptSalesDetailsDao.getRemovalEventExceptSalesDetailsById(removalId);
			if(null != removalEventExceptSalesDetails)
			{
				if(null != removalEventExceptSalesDetails.getTransportJourneyId())
				{
					removalEventExceptSalesDetails.setTransportJourney(transportJourneyDao.getTransportJourneyById(removalEventExceptSalesDetails.getTransportJourneyId()));
				}
			}
			return removalEventExceptSalesDetails;
		}
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

	@Override
	@Transactional("ptxJTransactionManager")
	public int addRemovalEventExceptSalesDetails(RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
			throws PigTraxException 
	{
		int returnValue = 0;
		GroupEventDetails groupEventDetails = null;
		try
		{
			if (null != removalEventExceptSalesDetails.getTransportJourney())
			{
				TransportJourney transportJourney = removalEventExceptSalesDetails
						.getTransportJourney();
				transportJourney.setUserUpdated(removalEventExceptSalesDetails.getUserUpdated());
				int transportJourneyId = transportJourneyDao.addTransportJourney(removalEventExceptSalesDetails
						.getTransportJourney());
				
				if(transportJourneyId !=0 )
				{
					removalEventExceptSalesDetails.setTransportJourneyId(transportJourneyId);					
				}
				else
				{
					throw new PigTraxException("Not able to create transport journey",null);
				}
				
			}	
			
			if(null != removalEventExceptSalesDetails.getGroupEventId() && removalEventExceptSalesDetails.getGroupEventId() !=0)
			{
				GroupEvent groupEventUpdate = groupEventDao.getGroupEventByGeneratedGroupId(removalEventExceptSalesDetails.getGroupEventId(), removalEventExceptSalesDetails.getCompanyId());
				if(null != groupEventUpdate )
				{
					if(removalEventExceptSalesDetails.getRemovalEventId() == RemovalEventType.Transferred.getTypeCode())
					{
						groupEventUpdate.setPremiseId(removalEventExceptSalesDetails.getDestPremiseId());
						groupEventDao.updateGroupEvent(groupEventUpdate);
						
						GroupEventPhaseChange currentPhase = groupEventPhaseChangeDao.getCurrentPhase(groupEventUpdate.getId());
						currentPhase.setPremiseId(removalEventExceptSalesDetails.getDestPremiseId());						
						groupEventPhaseChangeDao.updatePhaseDetails(currentPhase);						
						groupEventRoomDao.deleteGroupEventRooms(currentPhase.getId());
						groupEventRoomDao.addSingleGroupEventRooms(currentPhase.getId(), removalEventExceptSalesDetails.getRoomId());
						
					}
					else
					{
						//Add a negative transaction in the group event details
						groupEventDetails = new GroupEventDetails();
						groupEventDetails.setGroupId(groupEventUpdate.getId());
						groupEventDetails.setDateOfEntry(removalEventExceptSalesDetails.getRemovalDateTime());
						groupEventDetails.setNumberOfPigs(-1*removalEventExceptSalesDetails.getNumberOfPigs());
						groupEventDetails.setWeightInKgs(removalEventExceptSalesDetails.getWeightInKgs().doubleValue());
						groupEventDetails.setUserUpdated(removalEventExceptSalesDetails.getUserUpdated());
						groupEventDetails.setRemarks("Removed through Pig Movement");
						
						groupEventUpdate.setCurrentInventory(groupEventUpdate.getCurrentInventory() - removalEventExceptSalesDetails.getNumberOfPigs());
						groupEventDao.updateGroupEventCurrentInventory(groupEventUpdate);
					}
				}
			}
			
			if(null != removalEventExceptSalesDetails.getPigInfoId() && removalEventExceptSalesDetails.getPigInfoId() !=0)
			{
				PigInfo pigInfo = pigInfoDao.getPigInformationById(removalEventExceptSalesDetails.getPigInfoId());
				if(null != pigInfo)
				{
					//No need to change the pig status on transfer
					//pigInfoDao.updatePigInfoStatus(removalEventExceptSalesDetails.getPigInfoId(), false);
					//update the pig premise and room details.
					
					if(removalEventExceptSalesDetails.getRemovalEventId() == RemovalEventType.Transferred.getTypeCode())
					{
						pigInfo.setActive(true);
						pigInfo.setPremiseId(removalEventExceptSalesDetails.getDestPremiseId());
						pigInfo.setRoomId(removalEventExceptSalesDetails.getRoomId());
					}
					else
					{
						pigInfo.setActive(false);
					}
										
					pigInfoDao.updatePigInformation(pigInfo);
					
					if(removalEventExceptSalesDetails.getRemovalEventId() == RemovalEventType.Transferred.getTypeCode())
					{
					   SowMovement sowMovement = new SowMovement();
					   sowMovement.setPigInfoId(pigInfo.getId());
					   sowMovement.setPremiseId(pigInfo.getPremiseId());
					   sowMovement.setRoomId(pigInfo.getRoomId());
					   sowMovement.setUserUpdated(pigInfo.getUserUpdated());
					   sowMovement.setCompanyId(pigInfo.getCompanyId());
					   sowMovementDao.addSowMovement(sowMovement);
					}	
					
				}
			}
						
			returnValue = removalEventExceptSalesDetailsDao.addRemovalEventExceptSalesDetails(removalEventExceptSalesDetails);
			
			if(groupEventDetails!= null)
			{
				groupEventDetails.setRemovalId(returnValue);
				groupEventDetailsDao.addGroupEventDetails(groupEventDetails);
			}
			
			PigTraxEventMaster master = new PigTraxEventMaster();
			if(removalEventExceptSalesDetails.getPigInfoId() != null && removalEventExceptSalesDetails.getPigInfoId()!=0)
				master.setPigInfoId(removalEventExceptSalesDetails.getPigInfoId());
			if(removalEventExceptSalesDetails.getGroupEventId() != null && removalEventExceptSalesDetails.getGroupEventId()!=0)
				master.setGroupEventId(removalEventExceptSalesDetails.getGroupEventId());
			master.setUserUpdated(removalEventExceptSalesDetails.getUserUpdated());
			master.setEventTime(removalEventExceptSalesDetails.getRemovalDateTime());
			master.setRemovalEventExceptSalesDetails(returnValue);
			eventMasterDao.insertEntryEventDetails(master);
			
		} 
		catch (SQLException sqlEx)
		{
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
		catch (DuplicateKeyException sqlExDup)
		{
			throw new PigTraxException("GroupId already exists",
						null, true);
		}
		return returnValue;
	}

	@Override
	public int updateFeedEventDeupdateRemovalEventExceptSalesDetailstail(RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
			throws PigTraxException {
		int returnValue = 0;
		try
		{
			if (null != removalEventExceptSalesDetails.getTransportJourney() &&
					(removalEventExceptSalesDetails.getTransportJourney().getId() == null || removalEventExceptSalesDetails.getTransportJourney().getId() ==0) )
			{
				TransportJourney transportJourney = removalEventExceptSalesDetails
						.getTransportJourney();
				transportJourney.setUserUpdated(removalEventExceptSalesDetails.getUserUpdated());
				int transportJourneyId = transportJourneyDao.addTransportJourney(removalEventExceptSalesDetails
						.getTransportJourney());
				
				if(transportJourneyId !=0 )
				{
					removalEventExceptSalesDetails.setTransportJourneyId(transportJourneyId);					
				}
			}
			returnValue = removalEventExceptSalesDetailsDao.updateRemovalEventExceptSalesDetails(removalEventExceptSalesDetails);
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		return returnValue;
	}

	@Override
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsListByRemovalId(
			RemovalEventExceptSalesDetails salesEventDetails)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional("ptxJTransactionManager")
	public int deleteRemovalExceptSales(RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
			throws PigTraxException 
	{
		int returnValue = 0;
		try
		{
			if (null != removalEventExceptSalesDetails.getTransportJourneyId())
			{
				transportJourneyDao.deleteTransportJourney(removalEventExceptSalesDetails
						.getTransportJourneyId());				
			}	
			
			if(null != removalEventExceptSalesDetails.getGroupEventId() && removalEventExceptSalesDetails.getGroupEventId() !=0)
			{
				GroupEvent groupEventUpdate = groupEventDao.getGroupEventByGeneratedGroupId(removalEventExceptSalesDetails.getGroupEventId(), removalEventExceptSalesDetails.getCompanyId());
				if(null != groupEventUpdate )
				{
					if(!groupEventUpdate.isActive())
					{
						groupEventUpdate.setActive(true);
					}
					groupEventUpdate.setCurrentInventory(groupEventUpdate.getCurrentInventory() + removalEventExceptSalesDetails.getNumberOfPigs());
					groupEventDao.updateGroupEventCurrentInventorywithStatus(groupEventUpdate);
					
					if(removalEventExceptSalesDetails.getRemovalEventId() == RemovalEventType.Transferred.getTypeCode())
					{
						groupEventUpdate.setPremiseId(removalEventExceptSalesDetails.getPremiseId());
						groupEventDao.updateGroupEvent(groupEventUpdate);
						
						GroupEventPhaseChange currentPhase = groupEventPhaseChangeDao.getCurrentPhase(groupEventUpdate.getId());
						currentPhase.setPremiseId(removalEventExceptSalesDetails.getPremiseId());						
						groupEventPhaseChangeDao.updatePhaseDetails(currentPhase);						
						/*groupEventRoomDao.deleteGroupEventRooms(currentPhase.getId());
						groupEventRoomDao.addSingleGroupEventRooms(currentPhase.getId(), removalEventExceptSalesDetails.getRoomId());
						*/
					}
					else
					{
						//Add a negative transaction in the group event details
						GroupEventDetails groupEventDetails = groupEventDetailsDao.groupEventDetailsListByIdAndRemovalId(removalEventExceptSalesDetails.getGroupEventId(), removalEventExceptSalesDetails.getId());
						groupEventDetailsDao.deleteGroupEventDetailsByGroupId(groupEventDetails.getId());
					
					}
				
				}
			}
			
			if(null != removalEventExceptSalesDetails.getPigInfoId() && removalEventExceptSalesDetails.getPigInfoId() !=0)
			{	
				PigInfo pigInfo = pigInfoDao.getPigInformationById(removalEventExceptSalesDetails.getPigInfoId());
				if(null != pigInfo)
				{
					if(removalEventExceptSalesDetails.getRemovalEventId() == RemovalEventType.Transferred.getTypeCode() && 
							removalEventExceptSalesDetails.getDestPremiseId().intValue() == pigInfo.getPremiseId().intValue())
					{
						SowMovement sowMovement = new SowMovement();
						sowMovement.setPigInfoId(pigInfo.getId());
						
						List<SowMovement> sowMovementList = sowMovementDao.getSowMovementListByPigInfoId(pigInfo.getPigId(), pigInfo.getCompanyId(), pigInfo.getPremiseId());
						if(sowMovementList != null && !sowMovementList.isEmpty() && sowMovementList.size() == 1)
						{
							pigInfo.setPremiseId(removalEventExceptSalesDetails.getPremiseId());
							pigInfo.setRoomId(removalEventExceptSalesDetails.getRoomId());
							
							sowMovement.setPremiseId(removalEventExceptSalesDetails.getPremiseId());
							sowMovement.setRoomId(removalEventExceptSalesDetails.getRoomId());
						}
						else if(sowMovementList != null && !sowMovementList.isEmpty() && sowMovementList.size() > 0)
						{
							pigInfo.setPremiseId(sowMovementList.get(1).getPremiseId());
							pigInfo.setRoomId(sowMovementList.get(1).getRoomId());
							
							sowMovement.setPremiseId(sowMovementList.get(1).getPremiseId());
							sowMovement.setRoomId(sowMovementList.get(1).getRoomId());
						}
						sowMovement.setUserUpdated(pigInfo.getUserUpdated());
						sowMovement.setCompanyId(pigInfo.getCompanyId());
						sowMovementDao.addSowMovement(sowMovement);
					}
					pigInfo.setActive(true);
					pigInfoDao.updatePigInformation(pigInfo);
				}
				
			}
			
			eventMasterDao.deleteRemovalingEvent(removalEventExceptSalesDetails.getId());
			removalEventExceptSalesDetailsDao.deleteRemovalEventExceptSalesDetails(removalEventExceptSalesDetails.getId());			
			
		} 
		catch (SQLException sqlEx)
		{
			throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());
		}
		return returnValue;
	}
	
	@Override
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsByGroupId( final String groupId, final int companyId, final int premisesId) throws PigTraxException
	{
		List<RemovalEventExceptSalesDetails> removalEventExceptSalesDetails = null;
		
		try 
		{
			GroupEvent groupEvent = groupEventDao.getGroupEventByGroupId(
					groupId, companyId, premisesId);

			if (null != groupEvent) 
			{
				removalEventExceptSalesDetails = removalEventExceptSalesDetailsDao
						.getRemovalEventExceptSalesDetailsByGroupId(groupEvent
								.getId());
			}
		} 
		catch (SQLException sqlEx)
		{
			throw new PigTraxException("SqlException occured",
					sqlEx.getSQLState());
		}
		
		return removalEventExceptSalesDetails;
	}
	
	@Override
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsByPigInfoId( final String pigInfoIdId, final int companyId, Integer premiseId) throws PigTraxException
	{
		List<RemovalEventExceptSalesDetails> removalEventExceptSalesDetails = null;
		
		try
		{
			PigInfo pigInfo = pigInfoDao.getInactivePigInformationByPigId(pigInfoIdId,
					companyId, premiseId);

			if (null != pigInfo)
			{
				removalEventExceptSalesDetails = removalEventExceptSalesDetailsDao
						.getRemovalEventExceptSalesDetailsByPigInfoId(pigInfo
								.getId());
			}
			else
			{
				pigInfo = pigInfoDao.getPigInformationByPigId(pigInfoIdId,companyId, premiseId);
				if(pigInfo != null)
				{
					removalEventExceptSalesDetails = removalEventExceptSalesDetailsDao.getRemovalEventExceptSalesDetailsByPigInfoId(pigInfo.getId());
				}
			}
		} 
		catch (SQLException sqlEx)
		{
			throw new PigTraxException("SqlException occured",
					sqlEx.getSQLState());
		}
		
		
		return removalEventExceptSalesDetails;
	}

}
