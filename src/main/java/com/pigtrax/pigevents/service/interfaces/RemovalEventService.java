package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.RemovalEvent;

public interface RemovalEventService {
	
		
	public RemovalEvent getRemovalEventById(final int id)
			throws SQLException ;
	
	RemovalEvent getFeedEventByRemovalId(final String removalId) throws SQLException;
	
	/*FeedEvent getFeedEventByGeneratedFeedId(final int generatedFeedId) throws SQLException;
	*/
	int addRemovalEvent(final RemovalEvent removalEvent) throws PigTraxException;
	
	int updateRemovalEvent(final RemovalEvent removalEvent) throws PigTraxException;
	
	public List getRemovalEventAndDetailByRemovalId( final String ticketNumber) throws PigTraxException;

}
