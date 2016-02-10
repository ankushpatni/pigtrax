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
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.util.DateUtil;
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
	public GroupEvent getGroupEventByGroupId(final String groupId, final int companyId, final Integer premiseId)
			throws SQLException {
		
		String qry = "select \"id\", \"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\", "
		   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\", \"id_Company\", \"currentInventory\",\"previousGroupId\", \"id_PhaseOfProductionType\",\"id_Premise\" "+
				"from pigtrax.\"GroupEvent\" where \"groupId\" = ? and \"id_Company\" = ? ";
		
			if(premiseId != null && premiseId > 0)
				qry += "and \"id_Premise\" = ?";
			
			List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, groupId.toUpperCase());
					ps.setInt(2, companyId); 
					if(premiseId != null && premiseId > 0)
						ps.setInt(3, premiseId);
				}}, new GroupEventMapper());

			if(groupEventList != null && groupEventList.size() > 0){
				return groupEventList.get(0);
			}
			return null;
	}
	
	
	@Override
		public GroupEvent getGroupEventByGroupId(final String groupId, final int companyId,	final Integer premiseId, final Integer phaseOfProductionTypeId)
				throws SQLException {
		String qry = "select \"id\", \"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\", "
		   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\", \"id_Company\", \"currentInventory\",\"previousGroupId\", \"id_PhaseOfProductionType\",\"id_Premise\" "+
				"from pigtrax.\"GroupEvent\" where \"groupId\" = ? and \"id_Company\" = ? and \"id_Premise\" = ? and \"id_PhaseOfProductionType\" = ?";
			
			List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, groupId.toUpperCase());
					ps.setInt(2, companyId);
					ps.setInt(3, premiseId);
					ps.setInt(4, phaseOfProductionTypeId);
				}}, new GroupEventMapper());

			if(groupEventList != null && groupEventList.size() > 0){
				return groupEventList.get(0);
			}
			return null;
		}
	
	public GroupEvent getGroupEventByGeneratedGroupId(final int groupId, final int companyId)
	{
		String qry = "select \"id\", \"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\", "
		   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\",\"id_Company\",  \"currentInventory\",\"previousGroupId\", \"id_PhaseOfProductionType\", \"id_Premise\" "+
				"from pigtrax.\"GroupEvent\" where  \"id\" = ? and \"id_Company\" = ?";
			
			List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
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
				+ " \"remarks\", \"lastUpdated\",\"userUpdated\", \"id_Company\" ,\"currentInventory\"  , \"previousGroupId\", \"id_PhaseOfProductionType\", \"id_Premise\") "
				+ "values(?,?,?,?,?,current_timestamp,?,?,?,?,?,?)";

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				ps.setString(1, groupEvent.getGroupId().toUpperCase());
				ps.setDate(2, new java.sql.Date(groupEvent.getGroupStartDateTime().getTime()));
				if( null != groupEvent.getGroupCloseDateTime())
				{
				ps.setDate(3, new java.sql.Date(groupEvent.getGroupCloseDateTime().getTime()));
				}
				else
				{
					ps.setNull(3, java.sql.Types.DATE);
				}
				
				ps.setBoolean(4, true);
				ps.setString(5, groupEvent.getRemarks());
				ps.setString(6, groupEvent.getUserUpdated());
				ps.setInt(7, groupEvent.getCompanyId());
				if(null != groupEvent.getCurrentInventory())
				{
					ps.setInt(8, groupEvent.getCurrentInventory());
				}
				else
				{
					ps.setNull(8, java.sql.Types.INTEGER);
				}
				ps.setString(9, groupEvent.getPreviousGroupId());
				if(null != groupEvent.getPhaseOfProductionTypeId())
				{
					ps.setInt(10, groupEvent.getPhaseOfProductionTypeId());
				}
				else
				{
					ps.setNull(10, java.sql.Types.INTEGER);
				}
				
								
				if(null != groupEvent.getPremiseId() && groupEvent.getPremiseId() != 0)
				{
					ps.setInt(11, groupEvent.getPremiseId());
				}
				else
				{
					ps.setNull(11, java.sql.Types.INTEGER);
				}
				
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
				" \"userUpdated\"=? , \"currentInventory\"=? ,\"previousGroupId\" =? , \"id_PhaseOfProductionType\" = ?, \"id_Premise\"=?  where \"id\" = ? ";
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
					if( null != groupEvent.getGroupCloseDateTime())
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
					ps.setString(6, groupEvent.getUserUpdated());
					
					if(null != groupEvent.getCurrentInventory())
					{
						ps.setInt(7, groupEvent.getCurrentInventory());
					}
					else
					{
						ps.setNull(7, java.sql.Types.INTEGER);
					}
					ps.setString(8, groupEvent.getPreviousGroupId());
					if(null != groupEvent.getPhaseOfProductionTypeId())
					{
						ps.setInt(9, groupEvent.getPhaseOfProductionTypeId());
					}
					else
					{
						ps.setNull(9, java.sql.Types.INTEGER);
					}
					
					if(null != groupEvent.getPremiseId() && groupEvent.getPremiseId() != 0)
					{
						ps.setInt(10, groupEvent.getPremiseId());
					}
					else
					{
						ps.setNull(10, java.sql.Types.INTEGER);
					}
					
					ps.setInt(11, groupEvent.getId());
				}
			});	
	}

	@Override
	public int updateGroupEventStatus(final GroupEvent groupEvent)
			throws SQLException {
		String query = "update pigtrax.\"GroupEvent\" SET \"isActive\"=?  WHERE \"id\"=?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, groupEvent.isActive());
				ps.setInt(2, groupEvent.getId());
			}
		});
	}
	
	@Override
	public int updateGroupEventStatusWithCloseDate(final GroupEvent groupEvent)
			throws SQLException {
		String query = "update pigtrax.\"GroupEvent\"SET \"isActive\"=?, \"groupCloseDateTime\"=?  WHERE \"id\"=?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, groupEvent.isActive());
				if(groupEvent.getGroupCloseDateTime() != null)
					ps.setDate(2, new java.sql.Date(groupEvent.getGroupCloseDateTime().getTime()));
				else
					ps.setNull(2, java.sql.Types.DATE);
				ps.setInt(3, groupEvent.getId());
			}
		});
	}
	
	
	public int updateGroupEventCurrentInventory(final GroupEvent groupEvent) throws SQLException{
		
		String query = "update pigtrax.\"GroupEvent\" SET \"currentInventory\"=?  where \"id\" = ? and \"id_Company\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, groupEvent.getCurrentInventory());				
				ps.setInt(2,groupEvent.getId());
				ps.setInt(3, groupEvent.getCompanyId());
			}
		});
		
	}
	
public int updateGroupEventCurrentInventorywithStatus(final GroupEvent groupEvent) throws SQLException{
		
		String query = "update pigtrax.\"GroupEvent\" SET \"currentInventory\"=?,\"isActive\"=?  where \"id\" = ? and \"id_Company\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, groupEvent.getCurrentInventory());	
				ps.setBoolean(2, groupEvent.isActive());	
				ps.setInt(3,groupEvent.getId());
				ps.setInt(4, groupEvent.getCompanyId());
			}
		});
		
	}
	
	
	@Override
	public String getListoFFollowerId(final String groupId)
			throws SQLException {
		
		String returnString = null;
		String qry = "Select string_agg(\"groupId\", ', ')  from pigtrax.\"GroupEvent\" where \"previousGroupId\" = ?";
		PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(qry);
		ps.setString(1, groupId);

		ResultSet result = ps.executeQuery();
		if(null !=result)
		while(result.next()) {
			returnString = result.getString(1);
		}
		return returnString;
	}
	
	
	public List<GroupEvent> getGroupEventByCompanyId( final int companyId) throws SQLException
	{
		String qry = "select \"id\", \"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\", "
		   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\",\"id_Company\",  \"currentInventory\",\"previousGroupId\", \"id_PhaseOfProductionType\",\"id_Premise\" "+
				"from pigtrax.\"GroupEvent\" where  \"id_Company\" = ?";
			
			List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, companyId);
				}}, new GroupEventMapper());

			if(groupEventList != null && groupEventList.size() > 0){
				return groupEventList;
			}
			return null;
	}
	
	public List<GroupEvent> getGroupEventByPremise( final int premiseId) throws SQLException
	{
		String qry = "select \"id\", \"groupId\", \"groupStartDateTime\", \"groupCloseDateTime\", \"isActive\", "
		   		+ "\"remarks\", \"lastUpdated\", \"userUpdated\",\"id_Company\",  \"currentInventory\",\"previousGroupId\", \"id_PhaseOfProductionType\",\"id_Premise\" "+
				"from pigtrax.\"GroupEvent\" where  \"id_Premise\" = ? and \"isActive\" is true";
			
			List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, premiseId);
				}}, new GroupEventMapper());

			if(groupEventList != null && groupEventList.size() > 0){
				return groupEventList;
			}
			return null;
	}
	
	 private static final class GroupEventMapper implements RowMapper<GroupEvent> {
			public GroupEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
				GroupEvent groupEvent = new GroupEvent();
				groupEvent.setId(rs.getInt("id"));
				groupEvent.setGroupId(rs.getString("groupId"));
				groupEvent.setGroupStartDateTime(rs.getDate("groupStartDateTime"));
				try {
					groupEvent.setGroupStartDateStr(DateUtil.convertToFormatString(groupEvent.getGroupStartDateTime(), "MM/dd/yyyy"));
				} catch (ParseException e) {
					groupEvent.setGroupStartDateStr(null);
				}
				
				groupEvent.setGroupCloseDateTime(rs.getDate("groupCloseDateTime"));
				try {
					groupEvent.setGroupCloseDateStr(DateUtil.convertToFormatString(groupEvent.getGroupCloseDateTime(), "MM/dd/yyyy"));
				} catch (ParseException e) {
					groupEvent.setGroupCloseDateStr(null);
				}
				groupEvent.setActive(rs.getBoolean("isActive"));
				groupEvent.setRemarks(rs.getString("remarks"));
				groupEvent.setLastUpdated(rs.getDate("lastUpdated"));
				groupEvent.setUserUpdated(rs.getString("userUpdated"));
				groupEvent.setCompanyId(rs.getInt("id_Company"));
				groupEvent.setCurrentInventory(rs.getInt("currentInventory"));
				groupEvent.setPreviousGroupId(rs.getString("previousGroupId"));
				groupEvent.setPhaseOfProductionTypeId(rs.getInt("id_PhaseOfProductionType"));
				groupEvent.setPremiseId(rs.getInt("id_Premise"));
				return groupEvent;
			}
		}
	 
	 

}
