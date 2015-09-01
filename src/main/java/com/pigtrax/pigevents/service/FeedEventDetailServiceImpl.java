package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pigtrax.pigevents.beans.FeedEvent;
import com.pigtrax.pigevents.service.interfaces.FeedEventDetailService;

@Repository
public class FeedEventDetailServiceImpl implements FeedEventDetailService{

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
	public int addFeedEvent(FeedEvent groupEvent) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateFeedEvent(FeedEvent groupEvent) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
