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

import com.pigtrax.pigevents.beans.MatingDetails;
import com.pigtrax.pigevents.dao.interfaces.MatingDetailsDao;

@Repository
@Transactional
public class MatingDetailsDaoImpl implements MatingDetailsDao {

	private static final Logger logger = Logger.getLogger(BreedingEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public List<MatingDetails> getMatingDetails(final Integer breedingEventId) {
		String qry = "select \"id\", \"id_BreedingEvent\", \"semenId\", \"matingDate\", \"matingQuality\", "
				+ "\"id_EmployeeGroup\", \"lastUpdated\", \"userUpdated\" from pigtrax.\"MatingDetails\" where \"id_BreedingEvent\" = ?";
		
		List<MatingDetails> matingDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, breedingEventId);
			}}, new MatingEventWrapper());
		
		return matingDetailsList;
	}
	
	
	private static final class MatingEventWrapper implements RowMapper<MatingDetails> {
		public MatingDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			MatingDetails matingDetails = new MatingDetails();
			matingDetails.setMatingDetailId(rs.getInt("id"));
			matingDetails.setBreedingEventId(rs.getInt("id_BreedingEvent"));
			matingDetails.setEmployeeGroupId(rs.getInt("id_EmployeeGroup"));
			matingDetails.setMatingDate(rs.getDate("matingDate"));
			matingDetails.setSemenId(rs.getString("semenId"));
			matingDetails.setMatingQuality(rs.getInt("matingQuality"));
			matingDetails.setLastUpdated(rs.getDate("lastUpdated"));
			matingDetails.setUserUpdated(rs.getString("userUpdated"));
			return matingDetails;
		}
	}
	
	@Override
	public int addMatingDetails(final MatingDetails matingDetails) { 
		final String qry = "insert into pigtrax.\"MatingDetails\" (\"matingDate\", \"semenId\", \"matingQuality\", "
				+ "\"id_BreedingEvent\", \"id_EmployeeGroup\", \"lastUpdated\", \"userUpdated\") "
				+ " values (?,?,?,?,?,current_timestamp, ?)";
		
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(qry, new String[] {"id"});
	    	            
	    				ps.setDate(1, new java.sql.Date(matingDetails.getMatingDate().getTime()));
	    				ps.setObject(2, matingDetails.getSemenId());
	    				ps.setObject(3, matingDetails.getMatingQuality(), java.sql.Types.INTEGER);
	    				ps.setObject(4, matingDetails.getBreedingEventId(), java.sql.Types.INTEGER);
	    				ps.setObject(5, matingDetails.getEmployeeGroupId(), java.sql.Types.INTEGER);
	    				ps.setString(6, matingDetails.getUserUpdated());	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return 0;
	}
	
	@Override
	public int deleteMatingDetails(final Integer matingDetailsId) {
		final String qry = "delete from pigtrax.\"MatingDetails\" where \"id\" = ?";
		
		return this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, matingDetailsId);
			}
		});
	}
	
}
