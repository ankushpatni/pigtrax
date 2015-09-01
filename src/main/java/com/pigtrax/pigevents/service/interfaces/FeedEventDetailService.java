package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.FeedEvent;

public interface FeedEventDetailService {
	
	public List<FeedEvent> getFeedEventById(final int id)
			throws SQLException ;
	
	List<FeedEvent> getFeedEventByTicketNumber(final String ticketNumber) throws SQLException;
	
	/*FeedEvent getFeedEventByGeneratedFeedId(final int generatedFeedId) throws SQLException;
	*/
	int addFeedEvent(final FeedEvent groupEvent) throws SQLException;
	
	int updateFeedEvent(final FeedEvent groupEvent) throws SQLException;

}
