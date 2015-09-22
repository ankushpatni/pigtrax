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

import com.pigtrax.pigevents.beans.RemovalEvent;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventDao;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class RemovalEventDaoImpl implements RemovalEventDao
{
	
private static final Logger logger = Logger.getLogger(FeedEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public RemovalEvent getRemovalEventById(final int id) throws SQLException 
	{
		String qry = "select \"id\", \"removalId\", \"id_RemovalType\", \"remarks\", \"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"RemovalEvent\" where \"id\" = ? ";
				
			List<RemovalEvent> removalEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, id);
				}}, new RemovalEventMapper());

			if(removalEventList != null && removalEventList.size() > 0){
				return removalEventList.get(0);
			}
			return null;
	}

	@Override
	public RemovalEvent getRemovalEventByRemovalId(final String removalId)
			throws SQLException {
		String qry = "select \"id\", \"removalId\", \"id_RemovalType\", \"remarks\", \"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"RemovalEvent\" where \"removalId\" = ? ";
				
			List<RemovalEvent> removalEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setString(1, removalId.toUpperCase());
				}}, new RemovalEventMapper());

			if(removalEventList != null && removalEventList.size() > 0){
				return removalEventList.get(0);
			}
			return null;
	}
	
	@Override
	public List<RemovalEvent> getRemovalEventByGroupId(final int groupId)
			throws SQLException {
		String qry = "select \"id\", \"removalId\", \"id_RemovalType\", \"remarks\", \"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"RemovalEvent\" where \"id\" in (SELECT \"id_RemovalEvent\"  FROM pigtrax.\"RemovalEventExceptSalesDetails\" where \"id_GroupEvent\" = ?)";
				
			List<RemovalEvent> removalEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, groupId);
				}}, new RemovalEventMapper());

			if(removalEventList != null && removalEventList.size() > 0){
				return removalEventList;
			}
			return null;
	}
	
	@Override
	public List<RemovalEvent> getRemovalEventByPigId(final int pigId)
			throws SQLException {
		String qry = "select \"id\", \"removalId\", \"id_RemovalType\", \"remarks\", \"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"RemovalEvent\" where \"id\" in (SELECT \"id_RemovalEvent\"  FROM pigtrax.\"RemovalEventExceptSalesDetails\" where \"id_PigInfo\" = ?)";
				
			List<RemovalEvent> removalEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, pigId);
				}}, new RemovalEventMapper());

			if(removalEventList != null && removalEventList.size() > 0){
				return removalEventList;
			}
			return null;
	}
	public RemovalEvent getRemovalEventByGroupId(final String groupId) throws SQLException
	{
		String qry = "select \"id\", \"removalId\", \"id_RemovalType\", \"remarks\", \"lastUpdated\", \"userUpdated\" "+
		   		"from pigtrax.\"RemovalEvent\" where \"removalId\" = ? ";
				
			List<RemovalEvent> removalEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setString(1, groupId.toUpperCase());
				}}, new RemovalEventMapper());

			if(removalEventList != null && removalEventList.size() > 0){
				return removalEventList.get(0);
			}
			return null;
	}

	@Override
	public int addRemovalEvent(final RemovalEvent removalEvent) throws SQLException 
	{
		final String Qry = "insert into pigtrax.\"RemovalEvent\"(\"removalId\", \"id_RemovalType\","
				+"\"remarks\",  \"lastUpdated\",\"userUpdated\") "
				+ "values(?,?,?,current_timestamp,?)";

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				ps.setString(1, removalEvent.getRemovalId().toUpperCase());
				ps.setInt(2, removalEvent.getRemovalTypeId());
				ps.setString(3, removalEvent.getRemarks());
				/*if(null != removalEvent.getTransportJourneyId())
					ps.setInt(4, removalEvent.getTransportJourneyId());
				else
					ps.setNull(4, java.sql.Types.INTEGER);*/
				ps.setString(4, UserUtil.getLoggedInUser());				
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}

	@Override
	public int updateRemovalEvent(final RemovalEvent removalEvent)
			throws SQLException {
		
		String query = "update pigtrax.\"RemovalEvent\" SET  \"remarks\"=?, \"lastUpdated\"=current_timestamp,"+
				" \"userUpdated\"=?  where \"id\" = ? ";
		
			return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
										
					ps.setString(1, removalEvent.getRemarks());
					ps.setString(2, UserUtil.getLoggedInUser());
					ps.setInt(3, removalEvent.getId());
				}
			});	
	}
	
	private static final class RemovalEventMapper implements RowMapper<RemovalEvent> {
		public RemovalEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			RemovalEvent removalEvent = new RemovalEvent();
			removalEvent.setId(rs.getInt("id"));
			removalEvent.setRemovalId(rs.getString("removalId"));
			removalEvent.setRemovalTypeId(rs.getInt("id_RemovalType"));
			//removalEvent.setTransportJourneyId(rs.getInt("id_TransportJourney"));
			removalEvent.setRemarks(rs.getString("remarks"));
			removalEvent.setLastUpdated(rs.getDate("lastUpdated"));
			removalEvent.setUserUpdated(rs.getString("userUpdated"));
			return removalEvent;
		}		
	}

}
