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
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;

@Repository
@Transactional
public class GroupEventDetailsDaoImpl implements GroupEventDetailsDao{

private static final Logger logger = Logger.getLogger(GroupEventDetailsDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public GroupEventDetails groupEventDetailsListByGroupId(final String groupId) {
		 String qry = "select \"id\", \"groupId\", \"origin\", \"dateOfEntry\", \"id_Room\", "
			   		+ "\"id_EmployeeGroup\", \"numberOfPigs\", \"weightInKgs\", \"inventoryAdjustment\", "
			   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\", \"id_PhaseOfProductionType\" from pigtrax.\"GroupEventDetails\" where \"groupId\" = ?";
				
				List<GroupEventDetails> groupEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, groupId);
					}}, new GroupEventDetailsMapper());

				if(groupEventDetailsList != null && groupEventDetailsList.size() > 0){
					return groupEventDetailsList.get(0);
				}
				return null;
	}
	

	@Override
	public GroupEventDetails groupEventDetailsListById(final Integer id) {
		 String qry = "select \"id\", \"groupId\", \"origin\", \"dateOfEntry\", \"id_Room\", "
			   		+ "\"id_EmployeeGroup\", \"numberOfPigs\", \"weightInKgs\", \"inventoryAdjustment\", "
			   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\", \"id_PhaseOfProductionType\" from pigtrax.\"GroupEventDetails\" where \"id\" = ?";
				
				List<GroupEventDetails> groupEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, id);
					}}, new GroupEventDetailsMapper());

				if(groupEventDetailsList != null && groupEventDetailsList.size() > 0){
					return groupEventDetailsList.get(0);
				}
				return null;
	}

	@Override
	public int updateGroupEventDetails(final GroupEventDetails groupEventDetails)
			throws SQLException {
		final String Qry = "update pigtrax.\"GroupEventDetails\" set \"origin\" = ?, \"dateOfEntry\" = ?, \"id_Room\" = ?, \"id_EmployeeGroup\"= ?," +
			"\"numberOfPigs\"= ?, \"weightInKgs\" = ?, \"inventoryAdjustment\" = ?, \"remarks\" = ?,  \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"id_PhaseOfProductionType\" = ? where \"id\" = ? ";

		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, groupEventDetails.getOrigin());
				ps.setDate(2, new java.sql.Date(groupEventDetails.getDateOfEntry()
						.getTime()));
				ps.setInt(3, groupEventDetails.getRoomId());
				ps.setInt(4, groupEventDetails.getEmployeeGroupId());
				ps.setInt(5, groupEventDetails.getNumberOfPigs());
				ps.setInt(6, groupEventDetails.getWeightInKgs());
				ps.setInt(7, groupEventDetails.getInventoryAdjustment());
				ps.setString(8, groupEventDetails.getRemarks());
				ps.setString(9, groupEventDetails.getUserUpdated());
				ps.setInt(10, groupEventDetails.getPhaseOfProductionTypeId());
				ps.setInt(11, groupEventDetails.getId());
			}
		});

	}

	@Override
	public int addGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException {
		final String Qry = "insert into pigtrax.\"GroupEventDetails\"(\"id_GroupEvent\", \"origin\", \"dateOfEntry\", \"id_Room\", \"id_EmployeeGroup\", \"numberOfPigs\","
					+"\"weightInKgs\", \"indeventoryAdjustment\", \"remarks\", \"lastUpdated\", \"userUpdated\", \"id_PhaseOfProductionType\") "
				+ "values(?,?,?,?,?,?,?,?,?,current_timestamp,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setInt(1, groupEventDetails.getGroupId());
	    	            ps.setString(2, groupEventDetails.getOrigin());
	    	            ps.setDate(3, new java.sql.Date(groupEventDetails.getDateOfEntry().getTime()));
	    	            
	    	            if(groupEventDetails.getRoomId() != null && groupEventDetails.getRoomId() != 0)
	    	            	ps.setInt(4, groupEventDetails.getRoomId());
	    	            else
	    	            	ps.setNull(4, java.sql.Types.INTEGER);
	    	            
	    	            if(groupEventDetails.getEmployeeGroupId() != null && groupEventDetails.getEmployeeGroupId() != 0)
	    	            	ps.setInt(5, groupEventDetails.getEmployeeGroupId());
	    	            else
	    	            	ps.setNull(5, java.sql.Types.INTEGER);
	    	            
	    	            ps.setInt(6, groupEventDetails.getNumberOfPigs());
	    	            ps.setInt(7, groupEventDetails.getWeightInKgs());
	    	            
	    	            if(groupEventDetails.getInventoryAdjustment() != null && groupEventDetails.getInventoryAdjustment() != 0)
	    	            	ps.setInt(8, groupEventDetails.getInventoryAdjustment());
	    	            else
	    	            	ps.setNull(8, java.sql.Types.INTEGER);
	    			
	    	            
	    	            ps.setString(9, groupEventDetails.getRemarks());
	    	            ps.setString(10, groupEventDetails.getUserUpdated());
	    	            
	    	            if(groupEventDetails.getPhaseOfProductionTypeId() != null && groupEventDetails.getPhaseOfProductionTypeId() != 0)
	    	            	ps.setInt(11, groupEventDetails.getPhaseOfProductionTypeId());
	    	            else
	    	            	ps.setNull(11, java.sql.Types.INTEGER);
	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}

	@Override
	public void deleteGroupEventDetailsByGroupId(final Integer id) throws SQLException {
		 final String qry = "delete from pigtrax.\"GroupEventDetails\" where \"id\" = ?";
			
			this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, id);
				}
			});
		
	}
	
	
	 private static final class GroupEventDetailsMapper implements RowMapper<GroupEventDetails> {
			public GroupEventDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
				GroupEventDetails groupEventDetails = new GroupEventDetails();
				groupEventDetails.setId(rs.getInt("id"));
				groupEventDetails.setGroupId(rs.getInt("groupId"));
				groupEventDetails.setOrigin(rs.getString("origin"));
				groupEventDetails.setDateOfEntry(rs.getDate("dateOfEntry"));
				groupEventDetails.setRoomId(rs.getInt("roomId"));
				groupEventDetails.setEmployeeGroupId(rs.getInt("employeeGroupId"));
				groupEventDetails.setNumberOfPigs(rs.getInt("numberOfPigs"));
				groupEventDetails.setWeightInKgs(rs.getInt("weightInKgs"));
				groupEventDetails.setInventoryAdjustment(rs.getInt("inventoryAdjustment"));
				groupEventDetails.setRemarks(rs.getString("remarks"));
				groupEventDetails.setLastUpdated(rs.getDate("lastUpdated"));
				groupEventDetails.setUserUpdated(rs.getString("userUpdated"));
				groupEventDetails.setPhaseOfProductionTypeId(rs.getInt("phaseOfProductionTypeId"));
				return groupEventDetails;
			}
		}
}