package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.RemovalEvent;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.SalesEventDetails;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventDao;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.SalesEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.TransportJourneyDao;
import com.pigtrax.pigevents.service.interfaces.RemovalEventService;

@Repository
public class RemovalEventServiceImpl implements RemovalEventService{

	private static final Logger logger = Logger.getLogger(RemovalEventServiceImpl.class);
	
	@Autowired 
	RemovalEventDao removalEventDao;
	
	@Autowired
	SalesEventDetailsDao salesEventDetailsDao;
	
	@Autowired
	RemovalEventExceptSalesDetailsDao removalEventExceptSalesDetailsDao;
	
	@Autowired
	TransportJourneyDao transportJourneyDao;
	
	@Autowired 
	GroupEventDao groupEventDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Override
	public RemovalEvent getRemovalEventById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemovalEvent getFeedEventByRemovalId(String removalId)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional("ptxJTransactionManager")
	public int addRemovalEvent(RemovalEvent removalEvent)
			throws PigTraxException
	{
		int returnValue = 0;
		try
		{
			/*if (null != removalEvent.getTransportJourney())
			{
				TransportJourney transportJourney = removalEvent
						.getTransportJourney();
				transportJourney.setUserUpdated(removalEvent.getUserUpdated());
				int transportJourneyId = transportJourneyDao.addTransportJourney(removalEvent
						.getTransportJourney());
				
				if(transportJourneyId !=0 )
				{
					removalEvent.setTransportJourneyId(transportJourneyId);					
				}
				else
				{
					throw new PigTraxException("Not able to create transport journey",null);
				}
				
			}*/
			returnValue = removalEventDao.addRemovalEvent(removalEvent);
			
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
	public int updateRemovalEvent(RemovalEvent removalEvent)
			throws PigTraxException {
		int returnValue = 0;
		try
		{
			/*if (null != removalEvent.getTransportJourney() && (removalEvent.getTransportJourney().getId() == null || removalEvent.getTransportJourney().getId() ==0) )
			{
				TransportJourney transportJourney = removalEvent
						.getTransportJourney();
				transportJourney.setUserUpdated(removalEvent.getUserUpdated());
				int transportJourneyId = transportJourneyDao.addTransportJourney(removalEvent
						.getTransportJourney());
				
				if(transportJourneyId !=0 )
				{
					removalEvent.setTransportJourneyId(transportJourneyId);					
				}
			}*/
			returnValue = removalEventDao.updateRemovalEvent(removalEvent);
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		return returnValue;
	}

	@Override
	public List getRemovalEventAndDetailByRemovalId(String removalId)
			throws PigTraxException {
		try 
		{
			List eventAndDetailList = new ArrayList();
			RemovalEvent removalEvent =  removalEventDao.getRemovalEventByRemovalId(removalId);
			if(null != removalEvent)
			{
				eventAndDetailList.add(removalEvent);
				if(removalEvent.getRemovalTypeId() == 1 || removalEvent.getRemovalTypeId() == 2 || removalEvent.getRemovalTypeId() == 3)
				{
					List<RemovalEventExceptSalesDetails> removalEventExceptSalesDetailsList = removalEventExceptSalesDetailsDao.getRemovalEventExceptSalesDetailsListByRemovalId(removalEvent.getId());
				
					if(null != removalEventExceptSalesDetailsList && removalEventExceptSalesDetailsList.size()>0)
					{
						eventAndDetailList.add(removalEventExceptSalesDetailsList);
					}
					else
					{
						eventAndDetailList.add(null);
					}
				}
				else if(removalEvent.getRemovalTypeId() == 4)
				{
					List<SalesEventDetails> salesEventDetailsList = salesEventDetailsDao.getSalesEventDetailsListByRemovalId(removalEvent.getId());
					
					if(null != salesEventDetailsList && salesEventDetailsList.size()>0)
					{
						eventAndDetailList.add(salesEventDetailsList);
					}
					else
					{
						eventAndDetailList.add(null);
					}
				}
			}
			return eventAndDetailList;
		}
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	@Override
	public List<RemovalEvent> getRemovalEventListGroupOrPigInfo(RemovalEvent removalEvent)
			throws PigTraxException {
		try 
		{
			List<RemovalEvent> removalEventList = new ArrayList<RemovalEvent>();
			
				if(null != removalEvent && removalEvent.getGroupId()!=null)
				{
					GroupEvent groupEvent = groupEventDao.getGroupEventByGroupId(removalEvent.getGroupId(), removalEvent.getCompanyId());
					
					if(null != groupEvent)
					{
						removalEventList =  removalEventDao.getRemovalEventByGroupId(groupEvent.getId());
					}
				}
				else if(null != removalEvent )
				{
					PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(removalEvent.getPigId(), removalEvent.getCompanyId());
					if(null != pigInfo)
					{
						removalEventList =  removalEventDao.getRemovalEventByPigId(pigInfo.getId());
					}
				}
			
			return removalEventList;
		}
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

}
