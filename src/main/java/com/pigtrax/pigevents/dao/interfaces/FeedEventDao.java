package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.FeedEvent;

public interface FeedEventDao {
	
	List<FeedEvent> getFeedEventByGroupId(final int groupEventId) throws SQLException;
	
	FeedEvent getFeedEventByFeedId(final String feedId) throws SQLException;
	
	FeedEvent getFeedEventByGeneratedFeedId(final int generatedFeedId) throws SQLException;
	
	int addFeedEvent(final FeedEvent groupEvent) throws SQLException;
	
	int updateFeedEvent(final FeedEvent groupEvent) throws SQLException;

}
