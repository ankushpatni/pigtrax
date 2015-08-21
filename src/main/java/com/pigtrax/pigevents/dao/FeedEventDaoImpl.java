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
	public List<FeedEvent> getFeedEventByGroupId(final int groupEventId)
			throws SQLException {
		
		String qry = "select \"id\", \"ticketNumber\", \"feedId\", \"feedDateTime\", \"id_Silo\", "
		   		+ "\"id_TransportJourney\", \"id_GroupEvent\", \"id_FeedEventType\", \"batchId\", "
				+"\"feedQuantityKgs\", \"feedCost\", \"feedMedication\", \"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"FeedEvent\" where \"id_GroupEvent\" = ? ";
			
			List<FeedEvent> feedEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, groupEventId);
				}}, new FeedEventMapper());

			if(feedEventList != null && feedEventList.size() > 0){
				return feedEventList;
			}
			return null;
	}

	@Override
	public FeedEvent getFeedEventByFeedId(final String feedId) throws SQLException {
		String qry = "select \"id\", \"ticketNumber\", \"feedId\", \"feedDateTime\", \"id_Silo\", "
		   		+ "\"id_TransportJourney\", \"id_GroupEvent\", \"id_FeedEventType\", \"batchId\", "
				+"\"feedQuantityKgs\", \"feedCost\", \"feedMedication\", \"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"FeedEvent\" where \"feedId\" = ? ";
			
			List<FeedEvent> feedEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setString(1, feedId);
				}}, new FeedEventMapper());

			if(feedEventList != null && feedEventList.size() > 0){
				return feedEventList.get(0);
			}
			return null;
	}

	@Override
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
	}

	@Override
	public int addFeedEvent(FeedEvent feedEvent) throws SQLException {
		final String Qry = "insert into pigtrax.\"GroupEvent\"(\"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\","
				+ " \"remarks\", \"lastUpdated\",\"userUpdated\", \"id_Company\") "
				+ "values(?,?,?,?,?,current_timestamp,?,?)";

		KeyHolder holder = new GeneratedKeyHolder();

		/*jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				ps.setString(1, groupEvent.getGroupId().toUpperCase());
				ps.setDate(2, new java.sql.Date(groupEvent
						.getGroupStartDateTime().getTime()));
				ps.setDate(3, new java.sql.Date(groupEvent
						.getGroupCloseDateTime().getTime()));
				ps.setBoolean(4, true);
				ps.setString(5, groupEvent.getRemarks());
				ps.setString(6, UserUtil.getLoggedInUser());
				ps.setInt(7, groupEvent.getCompanyId());
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);*/
		int keyVal =0;
		return keyVal;
	}

	@Override
	public int updateFeedEvent(FeedEvent feedEvent) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private static final class FeedEventMapper implements RowMapper<FeedEvent> {
		public FeedEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			FeedEvent feedEvent = new FeedEvent();
			feedEvent.setId(rs.getInt("id"));
			feedEvent.setTicketNumber(rs.getString("ticketNumber"));
			feedEvent.setFeedId(rs.getInt("feedId"));
			feedEvent.setFeedDateTime(rs.getDate("feedDateTime"));
			feedEvent.setSiloId(rs.getInt("id_Silo"));
			feedEvent.setTransportJourneyId(rs.getInt("id_TransportJourney"));
			feedEvent.setGroupEventId(rs.getInt("id_GroupEvent"));
			feedEvent.setFeedEventType(rs.getInt("id_FeedEventType"));
			feedEvent.setBatchId(rs.getString("batchId"));
			feedEvent.setFeedQuantity(rs.getInt("feedQuantityKgs"));
			feedEvent.setFeedCost(rs.getBigDecimal("feedCost"));
			feedEvent.setFeedMedication(rs.getString("feedMedication"));
			feedEvent.setLastUpdated(rs.getDate("lastUpdated"));
			feedEvent.setUserUpdated(rs.getString("userUpdated"));
			return feedEvent;
		}		
		
	}


}
