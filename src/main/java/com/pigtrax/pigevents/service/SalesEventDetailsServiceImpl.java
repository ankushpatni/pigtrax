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
import com.pigtrax.pigevents.beans.SalesEventDetails;
import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.SalesEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.TransportJourneyDao;
import com.pigtrax.pigevents.service.interfaces.SalesEventDetailsService;

@Repository
public class SalesEventDetailsServiceImpl implements SalesEventDetailsService
{
	
	@Autowired
	TransportJourneyDao transportJourneyDao;
	
	@Autowired 
	GroupEventDao groupEventDao;
	
	@Autowired
	SalesEventDetailsDao salesEventDetailsDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	
	@Override
	public SalesEventDetails getSalesEventDetailsById(int removalId) throws PigTraxException {
		try 
		{
			SalesEventDetails salesEventDetails =  salesEventDetailsDao.getSalesEventDetailsById(removalId);
			if(null != salesEventDetails)
			{
				if(null != salesEventDetails.getTransportJourneyId())
				{
					salesEventDetails.setTransportJourney(transportJourneyDao.getTransportJourneyById(salesEventDetails.getTransportJourneyId()));
				}
			}
			return salesEventDetails;
		}
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

	@Override
	@Transactional("ptxJTransactionManager")
	public int addSalesEventDetails(SalesEventDetails salesEventDetails)
			throws PigTraxException 
	{
		int returnValue = 0;
		try
		{
			if (null != salesEventDetails.getTransportJourney())
			{
				TransportJourney transportJourney = salesEventDetails
						.getTransportJourney();
				transportJourney.setUserUpdated(salesEventDetails.getUserUpdated());
				int transportJourneyId = transportJourneyDao.addTransportJourney(salesEventDetails
						.getTransportJourney());
				
				if(transportJourneyId !=0 )
				{
					salesEventDetails.setTransportJourneyId(transportJourneyId);					
				}
				else
				{
					throw new PigTraxException("Not able to create transport journey",null);
				}
				
			}	
			
			if(null != salesEventDetails.getGroupEventId() && salesEventDetails.getGroupEventId() !=0)
			{
				GroupEvent groupEventUpdate = groupEventDao.getGroupEventByGeneratedGroupId(salesEventDetails.getGroupEventId(), salesEventDetails.getCompanyId());
				if(null != groupEventUpdate )
				{
					groupEventUpdate.setCurrentInventory(groupEventUpdate.getCurrentInventory() - salesEventDetails.getNumberOfPigs());
					groupEventDao.updateGroupEventCurrentInventory(groupEventUpdate);
				}
			}
			
			if(null != salesEventDetails.getPigInfoId() && salesEventDetails.getPigInfoId() !=0)
			{
				PigInfo pigInfo = pigInfoDao.getPigInformationById(salesEventDetails.getPigInfoId());
				if(null != pigInfo)
				{
					pigInfoDao.updatePigInfoStatus(salesEventDetails.getPigInfoId(), false);
				}
			}
			returnValue = salesEventDetailsDao.addSalesEventDetails(salesEventDetails);
			
		} 
		catch (SQLException sqlEx)
		{
			if ("23505".equals(sqlEx.getSQLState()))
			{
				throw new PigTraxException("SalesEventDetailsDao already exists",
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
			throw new PigTraxException("SalesEventDetails",
						null, true);
		}
		return returnValue;
	}

	@Override
	public int updateFeedEventDeupdateSalesEventDetailstail(SalesEventDetails salesEventDetails) throws PigTraxException
	{
		int returnValue = 0;
		try
		{
			if (null != salesEventDetails.getTransportJourney() &&
					(salesEventDetails.getTransportJourney().getId() == null || salesEventDetails.getTransportJourney().getId() ==0) )
			{
				TransportJourney transportJourney = salesEventDetails
						.getTransportJourney();
				transportJourney.setUserUpdated(salesEventDetails.getUserUpdated());
				int transportJourneyId = transportJourneyDao.addTransportJourney(salesEventDetails
						.getTransportJourney());
				
				if(transportJourneyId !=0 )
				{
					salesEventDetails.setTransportJourneyId(transportJourneyId);					
				}
			}
			returnValue = salesEventDetailsDao.updateSalesEventDetails(salesEventDetails);
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		return returnValue;
	}

		
	@Override
	@Transactional("ptxJTransactionManager")
	public int deleteSalesEventDetails(
			SalesEventDetails salesEventDetails)
			throws PigTraxException
	{
		int returnValue = 0;
		try
		{
			if (null != salesEventDetails.getTransportJourneyId())
			{
				transportJourneyDao.deleteTransportJourney(salesEventDetails
						.getTransportJourneyId());				
			}	
			
			if(null != salesEventDetails.getGroupEventId() && salesEventDetails.getGroupEventId() !=0)
			{
				GroupEvent groupEventUpdate = groupEventDao.getGroupEventByGeneratedGroupId(salesEventDetails.getGroupEventId(), salesEventDetails.getCompanyId());
				if(null != groupEventUpdate )
				{
					if(!groupEventUpdate.isActive())
					{
						groupEventUpdate.setActive(true);
					}
					groupEventUpdate.setCurrentInventory(groupEventUpdate.getCurrentInventory() + salesEventDetails.getNumberOfPigs());
					groupEventDao.updateGroupEventCurrentInventorywithStatus(groupEventUpdate);
				}
			}
			
			if(null != salesEventDetails.getPigInfoId() && salesEventDetails.getPigInfoId() !=0)
			{
				PigInfo pigInfo = pigInfoDao.getPigInformationById(salesEventDetails.getPigInfoId());
				if(null != pigInfo)
				{
					pigInfoDao.updatePigInfoStatus(salesEventDetails.getPigInfoId(), true);
				}
			}
			salesEventDetailsDao.deleteSalesEventDetails(salesEventDetails.getId());
			
		} 
		catch (SQLException sqlEx)
		{
			throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());
		}
		return returnValue;
	}

	

	
	@Override
	public List<SalesEventDetails> getSalesEventDetailsListByRemovalId(
			SalesEventDetails salesEventDetails) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<SalesEventDetails> getSalesEventDetailsListByGroupId(final String groupId, final int companyId) throws PigTraxException
	{
		List<SalesEventDetails> salesEventDetailsDetailsList = null;
		
		try 
		{
			GroupEvent groupEvent = groupEventDao.getGroupEventByGroupId( groupId, companyId);

			if (null != groupEvent) 
			{
				salesEventDetailsDetailsList = salesEventDetailsDao.getSalesEventDetailsListByGroupId(groupEvent.getId());
			}
		} 
		catch (SQLException sqlEx)
		{
			throw new PigTraxException("SqlException occured",
					sqlEx.getSQLState());
		}
		
		return salesEventDetailsDetailsList;
	}
	
	@Override
	public List<SalesEventDetails> getSalesEventDetailsListByPigId(final String pigInfoIdId, final int companyId) throws PigTraxException
	{
		List<SalesEventDetails> salesEventDetailsDetailsList = null;
		
		try
		{
			PigInfo pigInfo = pigInfoDao.getInactivePigInformationByPigId(pigInfoIdId,
					companyId);

			if (null != pigInfo)
			{
				salesEventDetailsDetailsList = salesEventDetailsDao.getSalesEventDetailsListByPigId(pigInfo.getId());
			}
		} 
		catch (SQLException sqlEx)
		{
			throw new PigTraxException("SqlException occured",
					sqlEx.getSQLState());
		}
		
		
		return salesEventDetailsDetailsList;
	}

	

}
