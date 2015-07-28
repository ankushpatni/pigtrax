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
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.pigevents.dao.interfaces.PregnancyEventDao;

@Repository
@Transactional
public class PregnancyEventDaoImpl implements PregnancyEventDao {
	

	private static final Logger logger = Logger.getLogger(PigInfoDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int addPregnancyEventdDetails(final PregnancyEvent pregnancyEvent)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"PregnancyEvent\"(\"id_PigInfo\", \"id_EmployeeGroup\", \"id_PregnancyEventType\", \"id_PregnancyExamResultType\", \"examDate\", \"resultDate\", \"sowCondition\", \"lastUpdated\", \"userUpdated\") "
				+ "values(?,?,?,?,?,?,?,current_timestamp,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setInt(1, pregnancyEvent.getPigInfoId());
	    				ps.setInt(2, pregnancyEvent.getEmployeeGroupId());
	    				ps.setInt(3, pregnancyEvent.getPregnancyEventTypeId());
	    				ps.setInt(4, pregnancyEvent.getPregnancyExamResultTypeId());
	    				ps.setDate(5, new java.sql.Date(pregnancyEvent.getExamDate().getTime()));
	    				ps.setDate(6,  new java.sql.Date(pregnancyEvent.getResultDate().getTime()));
	    				ps.setInt(7, pregnancyEvent.getSowCondition());
	    				ps.setString(8, pregnancyEvent.getUserUpdated());
	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}
	
	/**
	 * Retrieves the Pregnancy Event information for a given Id 
	 */
   public PregnancyEvent getPregnancyEvent(final Integer pregnancyEventId) {
	   String qry = "select \"id\", \"id_PigInfo\", \"id_EmployeeGroup\", \"id_PregnancyEventType\", "
	   		+ "\"id_PregnancyExamResultType\", \"examDate\", \"resultDate\", \"sowCondition\", "
	   		+ "\"lastUpdated\", \"userUpdated\" from pigtrax.\"PregnancyEvent\" where \"id\" = ?";
		
		List<PregnancyEvent> pregnancyEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pregnancyEventId);
			}}, new PregnancyEventMapper());

		if(pregnancyEventList != null && pregnancyEventList.size() > 0){
			return pregnancyEventList.get(0);
		}
		return null;
	}
   
    @Override
	public int updatePregnancyEventDetails(final PregnancyEvent pregnancyEvent)
			throws SQLException, DuplicateKeyException {
		String Qry = "update pigtrax.\"PregnancyEvent\" set \"id_PigInfo\" = ?, \"id_EmployeeGroup\" = ?, \"id_PregnancyEventType\" = ?, \"id_PregnancyExamResultType\"= ?, \"examDate\"= ?, \"resultDate\" = ?, \"sowCondition\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ? where \"id\" = ? ";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, pregnancyEvent.getPigInfoId());
				ps.setInt(2, pregnancyEvent.getEmployeeGroupId());
				ps.setInt(3, pregnancyEvent.getPregnancyEventTypeId());
				ps.setInt(4, pregnancyEvent.getPregnancyExamResultTypeId());
				ps.setDate(5, new java.sql.Date(pregnancyEvent.getExamDate().getTime()));
				ps.setDate(6,new java.sql.Date(pregnancyEvent.getResultDate().getTime()));
				ps.setInt(7, pregnancyEvent.getSowCondition());
				ps.setString(8, pregnancyEvent.getUserUpdated());				
				ps.setInt(9, pregnancyEvent.getId());
			}
		});
	}
   
   
   private static final class PregnancyEventMapper implements RowMapper<PregnancyEvent> {
		public PregnancyEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			PregnancyEvent pregnancyEvent = new PregnancyEvent();
			pregnancyEvent.setId(rs.getInt("id"));
			pregnancyEvent.setPigInfoId(rs.getInt("id_PigInfo"));
			pregnancyEvent.setEmployeeGroupId(rs.getInt("id_EmployeeGroup"));
			pregnancyEvent.setPregnancyEventTypeId(rs.getInt("id_PregnancyEventType"));
			pregnancyEvent.setPregnancyExamResultTypeId(rs.getInt("id_PregnancyExamResultType"));
			pregnancyEvent.setExamDate(rs.getDate("examDate"));
			pregnancyEvent.setResultDate(rs.getDate("resultDate"));
			pregnancyEvent.setSowCondition(rs.getInt("sowCondition"));
			pregnancyEvent.setLastUpdated(rs.getDate("lastUpdated"));
			pregnancyEvent.setUserUpdated(rs.getString("userUpdated"));
			return pregnancyEvent;
		}
	}
}
