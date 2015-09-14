package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.TransportJourneyDao;
import com.pigtrax.pigevents.service.interfaces.RemovalEventExceptSalesService;

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
					groupEventUpdate.setCurrentInventory(groupEventUpdate.getCurrentInventory() - removalEventExceptSalesDetails.getNumberOfPigs());
					groupEventDao.updateGroupEventCurrentInventory(groupEventUpdate);
				}
			}
			
			if(null != removalEventExceptSalesDetails.getPigInfoId() && removalEventExceptSalesDetails.getPigInfoId() !=0)
			{
				PigInfo pigInfo = pigInfoDao.getPigInformationById(removalEventExceptSalesDetails.getPigInfoId());
				if(null != pigInfo)
				{
					pigInfoDao.updatePigInfoStatus(removalEventExceptSalesDetails.getPigInfoId(), false);
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

}
