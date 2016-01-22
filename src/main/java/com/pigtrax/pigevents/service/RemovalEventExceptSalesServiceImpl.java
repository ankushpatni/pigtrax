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
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.SowMovement;
import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.SowMovementDao;
import com.pigtrax.pigevents.dao.interfaces.TransportJourneyDao;
import com.pigtrax.pigevents.service.interfaces.RemovalEventExceptSalesService;
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
					//Add a negative transaction in the group event details
					GroupEventDetails groupEventDetails = new GroupEventDetails();
					groupEventDetails.setGroupId(groupEventUpdate.getId());
					groupEventDetails.setDateOfEntry(DateUtil.getToday());
					groupEventDetails.setNumberOfPigs(-1*removalEventExceptSalesDetails.getNumberOfPigs());
					groupEventDetails.setWeightInKgs(removalEventExceptSalesDetails.getWeightInKgs().doubleValue());
					groupEventDetails.setUserUpdated(removalEventExceptSalesDetails.getUserUpdated());
					groupEventDetails.setRemarks("Removed through Pig Movement");
					groupEventDetailsDao.addGroupEventDetails(groupEventDetails);
					
					groupEventUpdate.setCurrentInventory(groupEventUpdate.getCurrentInventory() - removalEventExceptSalesDetails.getNumberOfPigs());
					groupEventDao.updateGroupEventCurrentInventory(groupEventUpdate);
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
					pigInfo.setPremiseId(removalEventExceptSalesDetails.getDestPremiseId());
					pigInfo.setRoomId(null);					
					pigInfoDao.updatePigInformation(pigInfo);
					
				   SowMovement sowMovement = new SowMovement();
				   sowMovement.setPigInfoId(pigInfo.getId());
				   sowMovement.setPremiseId(pigInfo.getPremiseId());
				   sowMovement.setRoomId(pigInfo.getRoomId());
				   sowMovement.setUserUpdated(pigInfo.getUserUpdated());
				   sowMovement.setCompanyId(pigInfo.getCompanyId());
				   sowMovementDao.addSowMovement(sowMovement);
					
				}
			}
			returnValue = removalEventExceptSalesDetailsDao.addRemovalEventExceptSalesDetails(removalEventExceptSalesDetails);
			
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
				}
			}
			
			if(null != removalEventExceptSalesDetails.getPigInfoId() && removalEventExceptSalesDetails.getPigInfoId() !=0)
			{	
				PigInfo pigInfo = pigInfoDao.getPigInformationById(removalEventExceptSalesDetails.getPigInfoId());
				if(null != pigInfo)
				{
					pigInfoDao.updatePigInfoStatus(removalEventExceptSalesDetails.getPigInfoId(), true);
				}
			}
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
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsByGroupId( final String groupId, final int companyId) throws PigTraxException
	{
		List<RemovalEventExceptSalesDetails> removalEventExceptSalesDetails = null;
		
		try 
		{
			GroupEvent groupEvent = groupEventDao.getGroupEventByGroupId(
					groupId, companyId, 1);

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
				removalEventExceptSalesDetails = removalEventExceptSalesDetailsDao.getRemovalEventExceptSalesDetailsByPigInfoId(pigInfo.getId());
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
