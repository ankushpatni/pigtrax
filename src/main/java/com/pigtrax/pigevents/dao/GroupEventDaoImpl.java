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

import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class GroupEventDaoImpl implements GroupEventDao{
	
private static final Logger logger = Logger.getLogger(GroupEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public GroupEvent getGroupEventByGroupId(final String groupId, final int companyId)
			throws SQLException {
		
		String qry = "select \"id\", \"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\", "
		   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\", \"id_Company\" from pigtrax.\"GroupEvent\" where \"groupId\" = ? and \"id_Company\" = ?";
			
			List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, groupId);
					ps.setInt(2, companyId);
				}}, new GroupEventMapper());

			if(groupEventList != null && groupEventList.size() > 0){
				return groupEventList.get(0);
			}
			return null;
	}

	@Override
	public int addGroupEvent(final GroupEvent groupEvent) throws SQLException {

		final String Qry = "insert into pigtrax.\"GroupEvent\"(\"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\","
				+ " \"remarks\", \"lastUpdated\",\"userUpdated\", \"id_Company\") "
				+ "values(?,?,?,?,?,current_timestamp,?,?)";

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
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
		logger.info("Key generated = " + keyVal);

		return keyVal;
	}
	
	@Override
	public int updateGroupEvent(final GroupEvent groupEvent) throws SQLException
	{
		String query = "update pigtrax.\"GroupEvent\" SET \"groupStartDateTime\"=?, \"groupCloseDateTime\"=?, \"isActive\"=?, \"remarks\"=?, \"lastUpdated\"=?,"+
				" \"userUpdated\"=?  WHERE \"groupId\"=?";
			return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					if( null != groupEvent.getGroupStartDateTime())
					{
						ps.setDate(1, new java.sql.Date(groupEvent
							.getGroupStartDateTime().getTime()));
					}
					else
					{
						ps.setNull(1, java.sql.Types.DATE);
					}
					if( null != groupEvent.getGroupStartDateTime())
					{
					ps.setDate(2, new java.sql.Date(groupEvent
							.getGroupCloseDateTime().getTime()));
					}
					else
					{
						ps.setNull(2, java.sql.Types.DATE);
					}
					
					ps.setBoolean(3, groupEvent.isActive());
					ps.setString(4, groupEvent.getRemarks());
					ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
					ps.setString(6, UserUtil.getLoggedInUser());
					ps.setString(7, groupEvent.getGroupId());
				}
			});	
	}

	@Override
	public int updateGroupEventStatus(final String groupId, final Boolean groupStatus)
			throws SQLException {
		String query = "update pigtrax.\"GroupEvent\" SET \"isActive\"=?  WHERE \"groupId\"=?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, !groupStatus);
				ps.setString(2, groupId.toUpperCase());
			}
		});
	}
	
	 private static final class GroupEventMapper implements RowMapper<GroupEvent> {
			public GroupEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
				GroupEvent groupEvent = new GroupEvent();
				groupEvent.setId(rs.getInt("id"));
				groupEvent.setGroupId(rs.getString("groupId"));
				groupEvent.setGroupStartDateTime(rs.getDate("groupStartDateTime"));
				groupEvent.setGroupCloseDateTime(rs.getDate("groupCloseDateTime"));
				groupEvent.setActive(rs.getBoolean("isActive"));
				groupEvent.setRemarks(rs.getString("remarks"));
				groupEvent.setLastUpdated(rs.getDate("lastUpdated"));
				groupEvent.setUserUpdated(rs.getString("userUpdated"));
				groupEvent.setCompanyId(rs.getInt("id_Company"));
				return groupEvent;
			}
		}

}
