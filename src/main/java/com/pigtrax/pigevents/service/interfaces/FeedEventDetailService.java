package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.FeedEvent;
import com.pigtrax.pigevents.beans.FeedEventDetail;

public interface FeedEventDetailService {
	
	public FeedEventDetail getFeedEventDetailById(final int id)
			throws PigTraxException ;
	
	public FeedEventDetail getFeedEventDetailByFeedEventId(final String feedEventId) throws PigTraxException;
	
	/*FeedEvent getFeedEventByGeneratedFeedId(final int generatedFeedId) throws SQLException;
	*/
	int addFeedEventDetail(final FeedEventDetail feedEventDetail) throws PigTraxException;
	
	int updateFeedEventDetail(final FeedEventDetail feedEventDetail) throws PigTraxException;

}
