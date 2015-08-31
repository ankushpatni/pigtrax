package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.FeedEvent;
import com.pigtrax.pigevents.beans.FeedEventDetail;

public interface FeedEventDetailDao {
	
	public List<FeedEventDetail> getFeedEventDetailById(final int id)
			throws SQLException ;
	
	public List<FeedEventDetail> getFeedEventDetailByFeedEventId(final int feedEventid)
			throws SQLException ;
	
	/*FeedEvent getFeedEventByGeneratedFeedId(final int generatedFeedId) throws SQLException;
	*/
	int addFeedEventDetail(final FeedEventDetail feedEventDetail) throws SQLException;
	
	int updateFeedEventDetail(final FeedEventDetail feedEventDetail) throws SQLException;

}
