package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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

import com.pigtrax.pigevents.beans.FeedEventDetail;
import com.pigtrax.pigevents.dao.interfaces.FeedEventDetailDao;
import com.pigtrax.util.DateUtil;
import com.pigtrax.util.UserUtil;

@Repository
public class FeedEventDetailDaoImpl implements FeedEventDetailDao {

private static final Logger logger = Logger.getLogger(FeedEventDetailDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public FeedEventDetail getFeedEventDetailById(final int id)
			throws SQLException {
		String qry = "select FED.\"id\", FED.\"feedEventDate\", FED.\"weightInKgs\", FED.\"remarks\", FED.\"id_FeedEvent\", "
		   		+ "FED.\"id_Silo\", FED.\"id_GroupEvent\", FED.\"id_FeedEventType\",FED.\"lastUpdated\", FED.\"userUpdated\",GE.\"groupId\",FED.\"feedMill\" "+
		   		"from pigtrax.\"FeedEventDetails\" FED LEFT JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\" where FED.\"id\" = ? ";
			
			List<FeedEventDetail> feedEventDetailList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, id);
				}}, new FeedEventDetailMapper());

			if(feedEventDetailList != null && feedEventDetailList.size() > 0){
				return feedEventDetailList.get(0);
			}
			return null;
	}

	@Override
	public List<FeedEventDetail> getFeedEventDetailByFeedEventId(final int feedEventid)
			throws SQLException {
		String qry = "select FED.\"id\", FED.\"feedEventDate\", FED.\"weightInKgs\", FED.\"remarks\", FED.\"id_FeedEvent\", "
		   		+ "FED.\"id_Silo\", FED.\"id_GroupEvent\", FED.\"id_FeedEventType\",FED.\"lastUpdated\", FED.\"userUpdated\",GE.\"groupId\",FED.\"feedMill\" "+
		   		"from pigtrax.\"FeedEventDetails\" FED LEFT JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\" where FED.\"id_FeedEvent\" = ? ";
			
			List<FeedEventDetail> feedEventDetailList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, feedEventid);
				}}, new FeedEventDetailMapper());

			if(feedEventDetailList != null && feedEventDetailList.size() > 0){
				return feedEventDetailList;
			}
			return null;
	}

	@Override
	public int addFeedEventDetail(final FeedEventDetail feedEventDetail)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"FeedEventDetails\"(\"feedEventDate\", \"weightInKgs\", \"remarks\", \"id_FeedEvent\", "
		   		+ "\"id_Silo\", \"id_GroupEvent\", \"id_FeedEventType\",\"lastUpdated\", \"userUpdated\",\"feedMill\") "
				+ "values(?,?,?,?,?,?,?,current_timestamp,?,?)";	
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				if( null != feedEventDetail.getFeedEventDate())
				{
				ps.setDate(1, new java.sql.Date(feedEventDetail.getFeedEventDate().getTime()));
				}
				else
				{
					ps.setNull(1, java.sql.Types.DATE);
				}
				
				if( null != feedEventDetail.getWeightInKgs())
				{
					ps.setBigDecimal(2, feedEventDetail.getWeightInKgs());
				}
				else
				{
					ps.setNull(2, java.sql.Types.INTEGER);
				}
				
				ps.setString(3, feedEventDetail.getRemarks() );
				ps.setInt(4, feedEventDetail.getFeedEventId());
				ps.setInt(5, feedEventDetail.getSiloId());
				if( null != feedEventDetail.getGroupEventId() && feedEventDetail.getGroupEventId()>0)
				{
					ps.setInt(6, feedEventDetail.getGroupEventId());
				}
				else
				{
					ps.setNull(6, java.sql.Types.INTEGER);
				}
				ps.setInt(7, feedEventDetail.getFeedEventTypeId());
				ps.setString(8, UserUtil.getLoggedInUser());
				ps.setString(9,  feedEventDetail.getFeedMill());
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}

	@Override
	public int updateFeedEventDetail(final FeedEventDetail feedEventDetail)
			throws SQLException {
		String query = "update pigtrax.\"FeedEventDetails\" SET \"feedEventDate\"=?, \"weightInKgs\"=?, \"remarks\"=?,"
				+" \"id_Silo\"=? ,\"id_GroupEvent\" =? , \"id_FeedEventType\" = ?, \"lastUpdated\"=current_timestamp,"+
				" \"userUpdated\"=?,\"feedMill\"=?  where \"id\" = ? ";
		
			return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					if( null != feedEventDetail.getFeedEventDate())
					{
					ps.setDate(1, new java.sql.Date(feedEventDetail.getFeedEventDate().getTime()));
					}
					else
					{
						ps.setNull(1, java.sql.Types.DATE);
					}
					if( null != feedEventDetail.getWeightInKgs())
					{
						ps.setBigDecimal(2, feedEventDetail.getWeightInKgs());
					}
					else
					{
						ps.setNull(2, java.sql.Types.INTEGER);
					}
					//ps.setBigDecimal(2, feedEventDetail.getWeightInKgs());
					ps.setString(3, feedEventDetail.getRemarks() );
					ps.setInt(4, feedEventDetail.getSiloId());
					if( null != feedEventDetail.getGroupEventId() && feedEventDetail.getGroupEventId()>0)
					{
						ps.setInt(5, feedEventDetail.getGroupEventId());
					}
					else
					{
						ps.setNull(5, java.sql.Types.INTEGER);
					}
					ps.setInt(6, feedEventDetail.getFeedEventTypeId());
					ps.setString(7, UserUtil.getLoggedInUser());
					ps.setString(8, feedEventDetail.getFeedMill());
					ps.setInt(9, feedEventDetail.getId());
				}
			});	
	}
	
	private static final class FeedEventDetailMapper implements RowMapper<FeedEventDetail> {
		public FeedEventDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			FeedEventDetail feedEventDetail = new FeedEventDetail();
			feedEventDetail.setId(rs.getInt("id"));
			feedEventDetail.setFeedEventDate(rs.getDate("feedEventDate"));
			try {
				feedEventDetail.setFeedEventDateStr(DateUtil.convertToFormatString(feedEventDetail.getFeedEventDate(), "MM/dd/yyyy"));
			} catch (ParseException e) {
				feedEventDetail.setFeedEventDateStr(null);
			}
			feedEventDetail.setWeightInKgs(rs.getBigDecimal("weightInKgs"));
			feedEventDetail.setRemarks(rs.getString("remarks"));
			feedEventDetail.setFeedEventId(rs.getInt("id_FeedEvent"));
			feedEventDetail.setSiloId(rs.getInt("id_Silo"));
			feedEventDetail.setGroupEventId(rs.getInt("id_GroupEvent"));
			feedEventDetail.setFeedEventTypeId(rs.getInt("id_FeedEventType"));
			feedEventDetail.setLastUpdated(rs.getDate("lastUpdated"));
			feedEventDetail.setUserUpdated(rs.getString("userUpdated"));
			feedEventDetail.setGroupEventGroupId(rs.getString("groupId"));
			feedEventDetail.setFeedMill(rs.getString("feedMill"));
			return feedEventDetail;
		}	
	}

}
