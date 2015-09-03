package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.FeedEvent;
import com.pigtrax.pigevents.dao.interfaces.FeedEventDao;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class FeedEventDaoImpl implements FeedEventDao
{
	
	private static final Logger logger = Logger.getLogger(FeedEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<FeedEvent> getFeedEventById(final int id)
			throws SQLException {
		
		String qry = "select \"id\", \"ticketNumber\", \"feedContentId\", \"initialFeedEntryDateTime\", \"batchId\", "
		   		+ "\"initialFeedQuantityKgs\", \"feedCost\", \"feedMedication\", \"id_TransportJourney\",\"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"FeedEvent\" where \"id\" = ? ";
		
			
			List<FeedEvent> feedEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, id);
				}}, new FeedEventMapper());

			if(feedEventList != null && feedEventList.size() > 0){
				return feedEventList;
			}
			return null;
	}

	@Override
	public FeedEvent getFeedEventByTicketNumber(final String ticketNumber) throws SQLException {
		String qry = "select \"id\", \"ticketNumber\", \"feedContentId\", \"initialFeedEntryDateTime\", \"batchId\", "
		   		+ "\"initialFeedQuantityKgs\", \"feedCost\", \"feedMedication\", \"id_TransportJourney\",\"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"FeedEvent\" where \"ticketNumber\" = ? ";
			
			List<FeedEvent> feedEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setString(1, ticketNumber);
				}}, new FeedEventMapper());

			if(feedEventList != null && feedEventList.size() > 0){
				return feedEventList.get(0);
			}
			return null;
	}

	/*@Override
	public FeedEvent getFeedEventByGeneratedFeedId(final int generatedFeedId)
			throws SQLException {
		String qry = "select \"id\", \"ticketNumber\", \"feedId\", \"feedDateTime\", \"id_Silo\", "
		   		+ "\"id_TransportJourney\", \"id_GroupEvent\", \"id_FeedEventType\", \"batchId\", "
				+"\"feedQuantityKgs\", \"feedCost\", \"feedMedication\", \"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"FeedEvent\" where \"feedId\" = ? ";
			
			List<FeedEvent> feedEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, generatedFeedId);
				}}, new FeedEventMapper());

			if(feedEventList != null && feedEventList.size() > 0){
				return feedEventList.get(0);
			}
			return null;
	}*/

	@Override
	public int addFeedEvent(final FeedEvent feedEvent) throws SQLException {
		final String Qry = "insert into pigtrax.\"FeedEvent\"(\"ticketNumber\", \"feedContentId\", \"initialFeedEntryDateTime\", \"batchId\","
				+ " \"initialFeedQuantityKgs\",\"feedCost\", \"feedMedication\", \"id_TransportJourney\",  \"lastUpdated\",\"userUpdated\") "
				+ "values(?,?,?,?,?,?,?,?,current_timestamp,?)";	
		

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				ps.setString(1, feedEvent.getTicketNumber());
				ps.setString(2, feedEvent.getFeedContentId());
				ps.setDate(3, new java.sql.Date(feedEvent
						.getInitialFeedEntryDateTime().getTime()));
				ps.setString(4, feedEvent.getBatchId());
				ps.setInt(5, feedEvent.getInitialFeedQuantityKgs());
				ps.setBigDecimal(6, feedEvent.getFeedCost());
				ps.setString(7, feedEvent.getFeedMedication());
				if(null != feedEvent.getTransportJourneyId() && feedEvent.getTransportJourneyId()>0)
					ps.setInt(8, feedEvent.getTransportJourneyId());
				else
					ps.setNull(8,  java.sql.Types.INTEGER);
				ps.setString(9, UserUtil.getLoggedInUser());				
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}

	@Override
	public int updateFeedEvent(final FeedEvent feedEvent) throws SQLException {
		String query = "update pigtrax.\"FeedEvent\" SET \"ticketNumber\"=?, \"initialFeedEntryDateTime\"=?, \"batchId\"=?,"
				+" \"initialFeedQuantityKgs\"=? ,\"feedCost\" =? , \"feedMedication\" = ?, \"id_TransportJourney\"=?, \"lastUpdated\"=current_timestamp,"+
				" \"userUpdated\"=?  where \"id\" = ? ";
		
			return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, feedEvent.getTicketNumber());
					if(null!=feedEvent
							.getInitialFeedEntryDateTime())
					{
					ps.setDate(2, new java.sql.Date(feedEvent
							.getInitialFeedEntryDateTime().getTime()));
					}
					else
					{
						ps.setNull(2, java.sql.Types.DATE);
					}
					ps.setString(3, feedEvent.getBatchId());
					
					if(feedEvent.getInitialFeedQuantityKgs() != null )
						ps.setInt(4, feedEvent.getInitialFeedQuantityKgs());
					else
						ps.setNull(4, java.sql.Types.INTEGER);
					
					if(feedEvent.getFeedCost() != null )
						ps.setBigDecimal(5, feedEvent.getFeedCost());
					else
						ps.setNull(5, java.sql.Types.DECIMAL);
					
					ps.setString(6, feedEvent.getFeedMedication());
					
					if(feedEvent.getTransportJourneyId() != null  && feedEvent.getTransportJourneyId()>0)
						ps.setInt(7, feedEvent.getTransportJourneyId());
					else
						ps.setNull(7, java.sql.Types.INTEGER);
					
					ps.setString(8, UserUtil.getLoggedInUser());
					ps.setInt(9, feedEvent.getId());
				}
			});	
	}
	
	private static final class FeedEventMapper implements RowMapper<FeedEvent> {
		public FeedEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			FeedEvent feedEvent = new FeedEvent();
			feedEvent.setId(rs.getInt("id"));
			feedEvent.setTicketNumber(rs.getString("ticketNumber"));
			feedEvent.setFeedContentId(rs.getString("feedContentId"));
			feedEvent.setInitialFeedEntryDateTime(rs.getDate("initialFeedEntryDateTime"));
			feedEvent.setBatchId(rs.getString("batchId"));
			feedEvent.setInitialFeedQuantityKgs(rs.getInt("initialFeedQuantityKgs"));
			feedEvent.setFeedCost(rs.getBigDecimal("feedCost"));
			feedEvent.setFeedMedication(rs.getString("feedMedication"));			
			feedEvent.setFeedCost(rs.getBigDecimal("feedCost"));
			feedEvent.setFeedMedication(rs.getString("feedMedication"));
			feedEvent.setTransportJourneyId(rs.getInt("id_TransportJourney"));
			feedEvent.setLastUpdated(rs.getDate("lastUpdated"));
			feedEvent.setUserUpdated(rs.getString("userUpdated"));
			return feedEvent;
		}		
	}


}
