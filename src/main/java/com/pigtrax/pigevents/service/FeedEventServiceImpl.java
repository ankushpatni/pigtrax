package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.FeedEvent;
import com.pigtrax.pigevents.beans.FeedEventDetail;
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
	public int addFeedEvent(FeedEvent feedEvent) throws PigTraxException
	{
		int returnValue = 0;
		try
		{
			returnValue = feedEventDao.addFeedEvent(feedEvent);
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		return returnValue;
	}

	@Override
	public int updateFeedEvent(FeedEvent feedEvent) throws PigTraxException {
		int returnValue = 0;
		try
		{
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
