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
import com.pigtrax.pigevents.beans.FeedEvent;
import com.pigtrax.pigevents.beans.FeedEventDetail;
import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.dao.interfaces.FeedEventDao;
import com.pigtrax.pigevents.dao.interfaces.FeedEventDetailDao;
import com.pigtrax.pigevents.dao.interfaces.TransportJourneyDao;
import com.pigtrax.pigevents.service.interfaces.FeedEventService;

@Repository
public class FeedEventServiceImpl implements FeedEventService
{

	private static final Logger logger = Logger.getLogger(FeedEventServiceImpl.class);
	
	@Autowired 
	FeedEventDao feedEventDao;
	
	@Autowired
	FeedEventDetailDao feedEventDetailDao;
	
	@Autowired
	TransportJourneyDao transportJourneyDao;

	@Override
	public List<FeedEvent> getFeedEventById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FeedEvent> getFeedEventByTicketNumber(String ticketNumber)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional("ptxJTransactionManager")
	public int addFeedEvent(FeedEvent feedEvent) throws PigTraxException
	{
		int returnValue = 0;
		try
		{
			if (null != feedEvent.getTransportJourney())
			{
				TransportJourney transportJourney = feedEvent
						.getTransportJourney();
				transportJourney.setUserUpdated(feedEvent.getUserUpdated());
				int transportJourneyId = transportJourneyDao.addTransportJourney(feedEvent
						.getTransportJourney());
				
				if(transportJourneyId !=0 )
				{
					feedEvent.setTransportJourneyId(transportJourneyId);					
				}
				else
				{
					throw new PigTraxException("Not able to create transport journey",null);
				}
				
			}
			
			returnValue = feedEventDao.addFeedEvent(feedEvent);
			
		} 
		catch (SQLException sqlEx)
		{
			sqlEx.printStackTrace();
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
			sqlExDup.printStackTrace();
			throw new PigTraxException("GroupId already exists",
						null, true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public int updateFeedEvent(FeedEvent feedEvent) throws PigTraxException {
		int returnValue = 0;
		try
		{
			if (null != feedEvent.getTransportJourney() && (feedEvent.getTransportJourney().getId() == null || feedEvent.getTransportJourney().getId() ==0) )
			{
				TransportJourney transportJourney = feedEvent
						.getTransportJourney();
				transportJourney.setUserUpdated(feedEvent.getUserUpdated());
				int transportJourneyId = transportJourneyDao.addTransportJourney(feedEvent
						.getTransportJourney());
				
				if(transportJourneyId !=0 )
				{
					feedEvent.setTransportJourneyId(transportJourneyId);					
				}
			}
			returnValue = feedEventDao.updateFeedEvent(feedEvent);
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		return returnValue;
	}

	@Override
	public List getFeedEventAndDetailByTicketNumber(String ticketNumber)
			throws PigTraxException {
		try 
		{
			List eventAndDetailList = new ArrayList();
			FeedEvent feedEvent =  feedEventDao.getFeedEventByTicketNumber(ticketNumber);
			if(null != feedEvent)
			{
				eventAndDetailList.add(feedEvent);
				List<FeedEventDetail> feedEventDetailsList = feedEventDetailDao.getFeedEventDetailByFeedEventId(feedEvent.getId());
				if(null != feedEventDetailsList)
				{
					eventAndDetailList.add(feedEventDetailsList);
				}
				else
				{
					eventAndDetailList.add(null);
				}
				if(null != feedEvent)
				{
					eventAndDetailList.add(transportJourneyDao.getTransportJourneyById(feedEvent.getTransportJourneyId()));
				}
			}
			return eventAndDetailList;
		}
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

}
