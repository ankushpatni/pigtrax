package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.FeedEvent;
import com.pigtrax.pigevents.beans.FeedEventDetail;
import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.dao.interfaces.FeedEventDetailDao;
import com.pigtrax.pigevents.service.interfaces.FeedEventDetailService;

@Repository
public class FeedEventDetailServiceImpl implements FeedEventDetailService{

	@Autowired
	FeedEventDetailDao feedEventDetailDao;
	
	@Override
	public FeedEventDetail getFeedEventDetailById(int id) throws PigTraxException {
		try
		{
			return( feedEventDetailDao.getFeedEventDetailById(id));
			
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

	@Override
	public FeedEventDetail getFeedEventDetailByFeedEventId(String ticketNumber)
			throws PigTraxException {
		return null;
	}

	@Override
	public int addFeedEventDetail(FeedEventDetail feedEventDetail) throws PigTraxException {
		int returnValue = 0;
		try
		{
			returnValue = feedEventDetailDao.addFeedEventDetail(feedEventDetail);
			
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		return returnValue;
	}

	@Override
	public int updateFeedEventDetail(final FeedEventDetail feedEventDetail) throws PigTraxException {
		// TODO Auto-generated method stub
		int returnValue = 0;
		try
		{
			returnValue = feedEventDetailDao.updateFeedEventDetail(feedEventDetail);
			
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		return returnValue;
	}

}
