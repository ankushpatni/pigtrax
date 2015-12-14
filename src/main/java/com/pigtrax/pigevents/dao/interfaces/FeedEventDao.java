package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.FeedEvent;

public interface FeedEventDao {
	
	public List<FeedEvent> getFeedEventById(final int id)
			throws SQLException ;
	
	FeedEvent getFeedEventByTicketNumber(final String ticketNumber) throws SQLException;
	
	FeedEvent getFeedEventByTicketNumber(final String ticketNumber, final Integer selectedPremise) throws SQLException;
	
	/*FeedEvent getFeedEventByGeneratedFeedId(final int generatedFeedId) throws SQLException;
	*/
	int addFeedEvent(final FeedEvent groupEvent) throws SQLException;
	
	int updateFeedEvent(final FeedEvent groupEvent) throws SQLException;

}
