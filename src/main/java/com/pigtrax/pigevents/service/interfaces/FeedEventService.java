package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.FeedEvent;

public interface FeedEventService {
	
	public List<FeedEvent> getFeedEventById(final int id)
			throws SQLException ;
	
	List<FeedEvent> getFeedEventByTicketNumber(final String ticketNumber) throws SQLException;
	
	/*FeedEvent getFeedEventByGeneratedFeedId(final int generatedFeedId) throws SQLException;
	*/
	int addFeedEvent(final FeedEvent groupEvent) throws PigTraxException;
	
	int updateFeedEvent(final FeedEvent groupEvent) throws PigTraxException;
	
	public List getFeedEventAndDetailByTicketNumber( final String ticketNumber) throws PigTraxException;
	
	public List getFeedEventAndDetailByTicketNumber( final String ticketNumber, Integer selectedPremise) throws PigTraxException;

	List<FeedEvent> getFeedEventsByPremises(Integer selectedPremise) throws PigTraxException;

}
