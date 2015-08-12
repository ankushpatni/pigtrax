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
	public GroupEvent getGroupEventByGroupId(final String groupId) {
		 String qry = "select \"id\", \"groupId\", \"origin\", \"beginDateTime\", \"id_Room\", "
			   		+ "\"id_EmployeeGroup\", \"numberOfPigs\", \"weightInKgs\", \"inventoryAdjustment\", "
			   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\", \"id_PhaseOfProductionType\" from pigtrax.\"GroupEvent\" where \"groupId\" = ?";
				
				List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, groupId);
					}}, new GroupEventMapper());

				if(groupEventList != null && groupEventList.size() > 0){
					return groupEventList.get(0);
				}
				return null;
	}
	

	@Override
	public GroupEvent getGroupEventById(final Integer id) {
		 String qry = "select \"id\", \"groupId\", \"origin\", \"beginDateTime\", \"id_Room\", "
			   		+ "\"id_EmployeeGroup\", \"numberOfPigs\", \"weightInKgs\", \"inventoryAdjustment\", "
			   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\", \"id_PhaseOfProductionType\" from pigtrax.\"GroupEvent\" where \"id\" = ?";
				
				List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, id);
					}}, new GroupEventMapper());

				if(groupEventList != null && groupEventList.size() > 0){
					return groupEventList.get(0);
				}
				return null;
	}

	@Override
	public int updateGroupEventDetails(final GroupEvent groupEvent)
			throws SQLException {
		final String Qry = "update pigtrax.\"GroupEvent\" set \"origin\" = ?, \"beginDateTime\" = ?, \"id_Room\" = ?, \"id_EmployeeGroup\"= ?," +
			"\"numberOfPigs\"= ?, \"weightInKgs\" = ?, \"inventoryAdjustment\" = ?, \"remarks\" = ?,  \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"id_PhaseOfProductionType\" = ? where \"id\" = ? ";

		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, groupEvent.getOrigin());
				ps.setDate(2, new java.sql.Date(groupEvent.getBeginDateTime()
						.getTime()));
				ps.setInt(3, groupEvent.getRoomId());
				ps.setInt(4, groupEvent.getEmployeeGroupId());
				ps.setInt(5, groupEvent.getNumberOfPigs());
				ps.setInt(6, groupEvent.getWeightInKgs());
				ps.setInt(7, groupEvent.getInventoryAdjustment());
				ps.setString(8, groupEvent.getRemarks());
				ps.setString(9, groupEvent.getUserUpdated());
				ps.setInt(10, groupEvent.getPhaseOfProductionTypeId());
				ps.setInt(11, groupEvent.getId());
			}
		});

	}

	@Override
	public int addGroupEventDetails(final GroupEvent groupEvent) throws SQLException {
		final String Qry = "insert into pigtrax.\"GroupEvent\"(\"groupId\", \"origin\", \"beginDateTime\", \"id_Room\", \"id_EmployeeGroup\", \"numberOfPigs\","
					+"\"weightInKgs\", \"inventoryAdjustment\", \"remarks\", \"lastUpdated\", \"userUpdated\", \"id_PhaseOfProductionType\") "
				+ "values(?,?,?,?,?,?,?,?,current_timestamp,?,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setString(1, groupEvent.getGroupId());
	    	            ps.setString(2, groupEvent.getOrigin());
	    	            ps.setDate(3, new java.sql.Date(groupEvent.getBeginDateTime().getTime()));
	    	            ps.setInt(4, groupEvent.getRoomId());
	    	            ps.setInt(5, groupEvent.getEmployeeGroupId());
	    	            ps.setInt(6, groupEvent.getNumberOfPigs());
	    	            ps.setInt(7, groupEvent.getWeightInKgs());
	    	            ps.setInt(8, groupEvent.getInventoryAdjustment());
	    	            ps.setString(9, groupEvent.getRemarks());
	    	            ps.setString(10, groupEvent.getUserUpdated());
	    	            ps.setInt(11, groupEvent.getPhaseOfProductionTypeId());
	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}

	@Override
	public void deleteGroupEventByGroupId(final Integer id) throws SQLException {
		 final String qry = "delete from pigtrax.\"GroupEvent\" where \"id\" = ?";
			
			this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, id);
				}
			});
		
	}
	
	
	 private static final class GroupEventMapper implements RowMapper<GroupEvent> {
			public GroupEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
				GroupEvent groupEvent = new GroupEvent();
				groupEvent.setId(rs.getInt("id"));
				groupEvent.setGroupId(rs.getString("groupId"));
				groupEvent.setOrigin(rs.getString("origin"));
				groupEvent.setBeginDateTime(rs.getDate("beginDateTime"));
				groupEvent.setRoomId(rs.getInt("roomId"));
				groupEvent.setEmployeeGroupId(rs.getInt("employeeGroupId"));
				groupEvent.setNumberOfPigs(rs.getInt("numberOfPigs"));
				groupEvent.setWeightInKgs(rs.getInt("weightInKgs"));
				groupEvent.setInventoryAdjustment(rs.getInt("inventoryAdjustment"));
				groupEvent.setGroupCloseDateTime(rs.getDate("groupCloseDateTime"));
				groupEvent.setRemarks(rs.getString("remarks"));
				groupEvent.setLastUpdated(rs.getDate("lastUpdated"));
				groupEvent.setUserUpdated(rs.getString("userUpdated"));
				groupEvent.setPhaseOfProductionTypeId(rs.getInt("phaseOfProductionTypeId"));
				return groupEvent;
			}
		}
}
