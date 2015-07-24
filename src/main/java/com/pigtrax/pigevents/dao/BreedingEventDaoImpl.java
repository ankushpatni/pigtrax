package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;

@Repository
@Transactional
public class BreedingEventDaoImpl implements BreedingEventDao {
	private static final Logger logger = Logger.getLogger(BreedingEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int addBreedingEventInformation(final BreedingEvent breedingEvent)
			throws SQLException, DuplicateKeyException {
		final String Qry = "insert into pigtrax.\"BreedingEvent\"(\"serviceId\", \"id_EmployeeGroup\", \"id_PigInfo\", \"id_BreedingServiceType\", \"brgrId\", \"breedingDate\", \"semenId\", \"remarks\", \"mateQuality\", \"sowCondition\", \"lastUpdated\", \"userUpdated\") "
				+ "values(?,?,?,?,?,?,?,?,?,?,current_timestamp,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setString(1, breedingEvent.getServiceId());
	    				ps.setInt(2, breedingEvent.getEmployeeGroupId());
	    				ps.setInt(3, breedingEvent.getPigInfoKey());
	    				ps.setInt(4, breedingEvent.getBreedingServiceTypeId());
	    				ps.setString(5, breedingEvent.getBreedingGroupId());
	    				ps.setDate(6,  new java.sql.Date(breedingEvent.getBreedingDate().getTime()));
	    				ps.setString(7, breedingEvent.getSemenId());
	    				ps.setString(8, breedingEvent.getRemarks());
	    				ps.setInt(9, breedingEvent.getMateQuality());
	    				ps.setInt(10, breedingEvent.getSowCondition());
	    				ps.setString(11, breedingEvent.getUserUpdated());
	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}
	
	
	@Override
	public int updateBreedingEventInformation(final BreedingEvent breedingEvent)
			throws SQLException, DuplicateKeyException {
		String Qry = "update pigtrax.\"BreedingEvent\" set \"serviceId\"=?, \"id_EmployeeGroup\" = ?, \"id_PigInfo\" = ?, \"id_BreedingServiceType\"= ?, \"brgrId\"= ?, \"breedingDate\" = ?, \"semenId\" = ?, \"remarks\" = ?, \"mateQuality\" = ?, \"sowCondition\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ? where \"id\" = ? ";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setString(1, breedingEvent.getServiceId());
				ps.setInt(2, breedingEvent.getEmployeeGroupId());
				ps.setInt(3, breedingEvent.getPigInfoKey());
				ps.setInt(4, breedingEvent.getBreedingServiceTypeId());
				ps.setString(5, breedingEvent.getBreedingGroupId());
				ps.setDate(6,  new java.sql.Date(breedingEvent.getBreedingDate().getTime()));
				ps.setString(7, breedingEvent.getSemenId());
				ps.setString(8, breedingEvent.getRemarks());
				ps.setInt(9, breedingEvent.getMateQuality());
				ps.setInt(10, breedingEvent.getSowCondition());
				ps.setString(11, breedingEvent.getUserUpdated());				
				ps.setInt(12, breedingEvent.getId());
			}
		});
	}
	
	
	@Override
	public BreedingEvent getBreedingEventInformation(final String serviceId, final Integer companyId)
			throws SQLException {
		String qry = "Select PI.\"id_Company\", BE.\"id\", BE.\"serviceId\", BE.\"id_EmployeeGroup\", BE.\"id_PigInfo\",BE.\"id_BreedingServiceType\", BE.\"brgrId\", BE.\"breedingDate\", BE.\"semenId\",BE.\"remarks\",BE.\"mateQuality\", BE.\"sowCondition\", BE.\"lastUpdated\", BE.\"userUpdated\" from pigtrax.\"BreedingEvent\" BE join pigtrax.\"PigInfo\" PI on BE.\"id_PigInfo\" = PI.\"id\"   where BE.\"serviceId\" = ? and PI.\"id_Company\" = ?";
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, serviceId);
				ps.setInt(2, companyId);
			}}, new BreedingEventMapper());

		logger.info("breedingEventList size : "+breedingEventList.size());
		if(breedingEventList != null && breedingEventList.size() > 0){
			return breedingEventList.get(0);
		}
		return null;
	}
	
	private static final class BreedingEventMapper implements RowMapper<BreedingEvent> {
		public BreedingEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			BreedingEvent breedingEvent = new BreedingEvent();
			breedingEvent.setId(rs.getInt("id"));
			breedingEvent.setServiceId(rs.getString("serviceId"));
			breedingEvent.setPigInfoKey(rs.getInt("id_PigInfo"));
			breedingEvent.setEmployeeGroupId(rs.getInt("id_EmployeeGroup"));
			breedingEvent.setBreedingServiceTypeId(rs.getInt("id_BreedingServiceType"));
			breedingEvent.setBreedingGroupId(rs.getString("brgrId"));
			breedingEvent.setBreedingDate(rs.getDate("breedingDate"));
			breedingEvent.setSemenId(rs.getString("semenId"));
			breedingEvent.setRemarks(rs.getString("remarks"));
			breedingEvent.setMateQuality(rs.getInt("mateQuality"));
			breedingEvent.setSowCondition(rs.getInt("sowCondition"));
			breedingEvent.setLastUpdated(rs.getDate("lastUpdated"));
			breedingEvent.setUserUpdated(rs.getString("userUpdated"));
			return breedingEvent;
		}
	}
	
	
	public void deleteBreedingEventInfo(final Integer id) throws SQLException {
		final String qry = "delete from pigtrax.\"BreedingEvent\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
	}
}

