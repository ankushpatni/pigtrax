package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
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
		final String Qry = "insert into pigtrax.\"BreedingEvent\"(\"id_PigInfo\", \"id_BreedingServiceType\", "
				+ "\"serviceGroupId\", \"id_Pen\", \"sowCondition\",  \"weightInKgs\",\"lastUpdated\", \"userUpdated\",\"id_Premise\") "
				+ "values(?,?,?,?,?,?,current_timestamp,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            
	    				ps.setInt(1, breedingEvent.getPigInfoId());
	    				
	    				if(breedingEvent.getBreedingServiceTypeId() != null){
	    					ps.setInt(2, breedingEvent.getBreedingServiceTypeId());
	    				}
	    				else{
	    	            	ps.setNull(2, java.sql.Types.INTEGER);
	    				}
	    				ps.setString(3, breedingEvent.getBreedingGroupId());
	    				if(breedingEvent.getPenId() != null && breedingEvent.getPenId() != 0){
	    					ps.setInt(4, breedingEvent.getPenId());
	    				}
	    				else{
	    	            	ps.setNull(4, java.sql.Types.INTEGER);
	    				}
	    				
	    				if(breedingEvent.getSowCondition() != null ){
	    					ps.setInt(5, breedingEvent.getSowCondition());
	    				}
	    				else{
	    					ps.setNull(5, java.sql.Types.INTEGER);
	    				}
	    				ps.setObject(6, breedingEvent.getWeightInKgs(), java.sql.Types.DOUBLE); 
	    				
	    				ps.setString(7, breedingEvent.getUserUpdated());
	    				if(breedingEvent.getPremiseId() != null && breedingEvent.getPremiseId() != 0){
	    					ps.setInt(8, breedingEvent.getPremiseId());
	    				}
	    				else{
	    					ps.setNull(8, java.sql.Types.INTEGER);
	    				}
	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}
	
	/**
	 * Update the Breeding event details for a given pig Id
	 */
	@Override 
	public int updateBreedingEventInformation(final BreedingEvent breedingEvent)
			throws SQLException, DuplicateKeyException {
		String Qry = "update pigtrax.\"BreedingEvent\" set  \"id_PigInfo\" = ?, \"id_BreedingServiceType\"= ?, \"serviceGroupId\"= ?, \"id_Pen\" = ?,  "
				+ "\"sowCondition\" = ?, \"weightInKgs\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"id_Premise\" = ? where \"id\" = ? ";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, breedingEvent.getPigInfoId());
				
				if(breedingEvent.getBreedingServiceTypeId() != null && breedingEvent.getBreedingServiceTypeId() != 0){
					ps.setInt(2, breedingEvent.getBreedingServiceTypeId());
				}
				else{
					ps.setNull(2, java.sql.Types.INTEGER);
				}
				
				ps.setString(3, breedingEvent.getBreedingGroupId());
				
							
				if(breedingEvent.getPenId() != null && breedingEvent.getPenId() != 0){
					ps.setInt(4, breedingEvent.getPenId());
				}
				else{
					ps.setNull(4, java.sql.Types.INTEGER);
				}
				
				if(breedingEvent.getSowCondition() != null){
					ps.setInt(5, breedingEvent.getSowCondition());
				}
				else{
					ps.setNull(5, java.sql.Types.INTEGER);
				}
				
				ps.setObject(6, breedingEvent.getWeightInKgs(), java.sql.Types.DOUBLE);
				
				ps.setString(7, breedingEvent.getUserUpdated());	
				
				if(breedingEvent.getPremiseId() != null && breedingEvent.getPremiseId() !=0){
					ps.setInt(8, breedingEvent.getPremiseId());
				}
				else{
					ps.setNull(8, java.sql.Types.INTEGER);
				}
				
				ps.setInt(9, breedingEvent.getId());
			}
		});
	}	
	
	
	/**
	 * Retrive the breeding event information for a given pig Id and company Id
	 */
	@Override
	public List<BreedingEvent> getBreedingEventInformationByPigId(final String pigId, final Integer companyId)
			throws SQLException {
		String qry = "Select PI.\"id_Company\", BE.\"id\",BE.\"id_PigInfo\",BE.\"id_BreedingServiceType\", "
				+ "BE.\"serviceGroupId\", BE.\"id_Pen\", BE.\"serviceStartDate\", BE.\"sowCondition\", BE.\"weightInKgs\", BE.\"lastUpdated\", "
				+ "BE.\"userUpdated\",BE.\"id_Premise\" from pigtrax.\"BreedingEvent\" BE join pigtrax.\"PigInfo\" PI on BE.\"id_PigInfo\" = PI.\"id\"  "
				+ " where PI.\"pigId\" = ? and PI.\"id_Company\" = ? order by BE.\"id\" desc";
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, pigId);
				ps.setInt(2, companyId);
			}}, new BreedingEventMapper());
		
		return breedingEventList;
	}
	
	/**
	 * Retrive the breeding event information for a given pig Id and company Id
	 */
	@Override
	public List<BreedingEvent> getBreedingEventInformationByPigId(final String pigId, final Integer companyId, final Integer premiseId)
			throws SQLException {
		String qry = "Select PI.\"id_Company\", BE.\"id\",BE.\"id_PigInfo\",BE.\"id_BreedingServiceType\", "
				+ "BE.\"serviceGroupId\", BE.\"id_Pen\", BE.\"serviceStartDate\", BE.\"sowCondition\", BE.\"weightInKgs\", BE.\"lastUpdated\", "
				+ "BE.\"userUpdated\",BE.\"id_Premise\" from pigtrax.\"BreedingEvent\" BE join pigtrax.\"PigInfo\" PI on BE.\"id_PigInfo\" = PI.\"id\"  "
				+ " where PI.\"pigId\" = ? and PI.\"id_Company\" = ? and PI.\"id_Premise\" = ? order by BE.\"id\" desc";
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, pigId);
				ps.setInt(2, companyId);
				ps.setInt(3, premiseId);
			}}, new BreedingEventMapper());
		
		return breedingEventList;
	}
	
	/**
	 * Retrive the breeding event information based on the given tattoo and company Id
	 */
	
	@Override 
	public List<BreedingEvent> getBreedingEventInformationByTattoo(final String tattoo, final Integer companyId)
			throws SQLException {
		
		String qry = "Select PI.\"id_Company\", BE.\"id\",BE.\"id_PigInfo\",BE.\"id_BreedingServiceType\", "
				+ "BE.\"serviceGroupId\", BE.\"id_Pen\", BE.\"serviceStartDate\", BE.\"sowCondition\", BE.\"weightInKgs\", BE.\"lastUpdated\", "
				+ "BE.\"userUpdated\",BE.\"id_Premise\" from pigtrax.\"BreedingEvent\" BE join pigtrax.\"PigInfo\" PI on BE.\"id_PigInfo\" = PI.\"id\"  "
				+ " where PI.\"tattoo\" = ? and PI.\"id_Company\" = ? order by BE.\"id\" desc";		
		
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tattoo);
				ps.setInt(2, companyId);
			}}, new BreedingEventMapper()); 

		return breedingEventList;
	}
	
	
	/**
	 * Retrive the breeding event information based on the given tattoo and company Id
	 */
	
	@Override 
	public List<BreedingEvent> getBreedingEventInformationByTattoo(final String tattoo, final Integer companyId, final Integer premiseId)
			throws SQLException {
		
		String qry = "Select PI.\"id_Company\", BE.\"id\",BE.\"id_PigInfo\",BE.\"id_BreedingServiceType\", "
				+ "BE.\"serviceGroupId\", BE.\"id_Pen\", BE.\"serviceStartDate\", BE.\"sowCondition\", BE.\"weightInKgs\", BE.\"lastUpdated\", "
				+ "BE.\"userUpdated\",BE.\"id_Premise\" from pigtrax.\"BreedingEvent\" BE join pigtrax.\"PigInfo\" PI on BE.\"id_PigInfo\" = PI.\"id\"  "
				+ " where PI.\"tattoo\" = ? and PI.\"id_Company\" = ? and PI.\"id_Premise\" = ? order by BE.\"id\" desc";		
		
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tattoo);
				ps.setInt(2, companyId);
				ps.setInt(3, premiseId);
			}}, new BreedingEventMapper()); 

		return breedingEventList;
	}
	
	
	/**
	 * Retrive the breeding event information for a given breeding event id
	 */
	@Override
	public BreedingEvent getBreedingEventInformation(final Integer breedingEventId)
			throws SQLException {
		String qry = "Select BE.\"id\", BE.\"id_PigInfo\",BE.\"id_BreedingServiceType\", BE.\"serviceGroupId\","
				+ " BE.\"serviceStartDate\", BE.\"id_Pen\", BE.\"sowCondition\", BE.\"weightInKgs\", BE.\"lastUpdated\", BE.\"userUpdated\",BE.\"id_Premise\""
				+ " from pigtrax.\"BreedingEvent\" BE  where BE.\"id\" = ? ";
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, breedingEventId);
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
			breedingEvent.setPigInfoId(rs.getInt("id_PigInfo"));
			breedingEvent.setBreedingServiceTypeId(rs.getInt("id_BreedingServiceType"));
			breedingEvent.setBreedingGroupId(rs.getString("serviceGroupId"));
			breedingEvent.setSowCondition(rs.getInt("sowCondition"));
			breedingEvent.setWeightInKgs(rs.getDouble("weightInKgs"));
			breedingEvent.setPenId(rs.getInt("id_Pen"));
			breedingEvent.setServiceStartDate(rs.getDate("serviceStartDate"));
			breedingEvent.setLastUpdated(rs.getDate("lastUpdated"));
			breedingEvent.setUserUpdated(rs.getString("userUpdated"));
			breedingEvent.setPremiseId(rs.getInt("id_Premise"));
			return breedingEvent;
		}
	}
	
	/**
	 * Delete the breeding event information based for the given id
	 */	
	public void deleteBreedingEventInfo(final Integer id) throws SQLException {
		final String qry = "delete from pigtrax.\"BreedingEvent\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
	}
	
	@Override 
	public int updateServiceStartDate(final Date matingDate, final Integer breedingEventId) {
		String qry = "update pigtrax.\"BreedingEvent\" set \"serviceStartDate\" = ? where \"id\" = ?";
		return this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				if(matingDate != null)
					ps.setObject(1, new java.sql.Date(matingDate.getTime()));
				else
					ps.setObject(1, null);
				ps.setInt(2, breedingEventId);
			}
		});
	}
	
	
	/**
	 * Check if the breeding event exists with a given service Id for pig Id
	 */
	@Override
	public BreedingEvent checkForBreedingServiceId(final String pigId,final String serviceId,final int companyId){
		/*String qry = "Select PI.\"id_Company\", BE.\"id\", BE.\"serviceId\", BE.\"id_EmployeeGroup\", BE.\"id_PigInfo\",BE.\"id_BreedingServiceType\", "
				+ "BE.\"brgrId\", BE.\"breedingDate\", BE.\"semenId\",BE.\"remarks\",BE.\"mateQuality\", BE.\"sowCondition\", BE.\"lastUpdated\", "
				+ "BE.\"userUpdated\" from pigtrax.\"BreedingEvent\" BE join pigtrax.\"PigInfo\" PI on BE.\"id_PigInfo\" = PI.\"id\"   "
				+ "where BE.\"serviceId\" = ? and PI.\"id_Company\" = ? and PI.\"pigId\" = ?";
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, serviceId);
				ps.setInt(2, companyId); 
				ps.setString(3, pigId);
			}}, new BreedingEventMapper());

		logger.info("breedingEventList size : "+breedingEventList.size());
		if(breedingEventList != null && breedingEventList.size() > 0){
			return breedingEventList.get(0);
		}*/
		return new BreedingEvent();
	}
	
	  
	@Override
	public BreedingEvent getLatestServiceEvent(final Integer pigInfoId) {
		String qry = "Select BE.\"id\", BE.\"id_PigInfo\",BE.\"id_BreedingServiceType\", BE.\"serviceGroupId\","
				+ " BE.\"serviceStartDate\", BE.\"id_Pen\", BE.\"sowCondition\", BE.\"weightInKgs\", BE.\"lastUpdated\", BE.\"userUpdated\", BE.\"id_Premise\""
				+ " from pigtrax.\"BreedingEvent\" BE  where BE.\"id_PigInfo\" = ? order by BE.\"id\" desc";
		
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoId);
			}}, new BreedingEventMapper());

		logger.info("breedingEventList size : "+breedingEventList.size());
		if(breedingEventList != null && breedingEventList.size() > 0){
			return breedingEventList.get(0);
		}
		return null; 
	}
	
	@Override
	public List<BreedingEvent> getOpenServiceRecords(final Integer pigInfoId) {
		String qry = "select BE.* from pigtrax.\"BreedingEvent\" BE where BE.\"serviceStartDate\" "
				+ "	is not NULL and BE.\"id\" not in (select PE.\"id_BreedingEvent\" from pigtrax.\"PregnancyEvent\" PE "
				+ " JOIN pigtrax.\"BreedingEvent\" BE on PE.\"id_BreedingEvent\" = BE.\"id\" JOIN pigtrax.\"PigInfo\" PI "
				+ " on BE.\"id_PigInfo\" = PI.\"id\" where PI.\"id\" = ?) and  BE.\"id_PigInfo\" = ? and BE.\"serviceStartDate\" is not NULL order by BE.\"id\" desc";
		
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoId);
				ps.setInt(2, pigInfoId);
			}}, new BreedingEventMapper());
		
		return breedingEventList;
	}
	
	
	@Override
	public List<BreedingEvent> getPendingFarrowServiceRecords(final Integer pigInfoId) {
		String qry = "select BE.* from pigtrax.\"BreedingEvent\" BE where BE.\"serviceStartDate\" "
				+ "	is not NULL and BE.\"id\" not in (select FE.\"id_BreedingEvent\" from pigtrax.\"FarrowEvent\" FE "
				+ " JOIN pigtrax.\"BreedingEvent\" BE on FE.\"id_BreedingEvent\" = BE.\"id\"  JOIN pigtrax.\"PigInfo\" PI "
				+ " on BE.\"id_PigInfo\" = PI.\"id\" where PI.\"id\" = ?) and  BE.\"id_PigInfo\" = ? and BE.\"serviceStartDate\" is not NULL order by BE.\"id\" desc";
		
		List<BreedingEvent> breedingEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoId);
				ps.setInt(2, pigInfoId);
			}}, new BreedingEventMapper());
		
		return breedingEventList;
	}
	
	
	
	@Override
	public Date getServiceStartDate(final Integer breedingEventId){
		String qry = "Select  BE.\"serviceStartDate\" from pigtrax.\"BreedingEvent\" BE  where BE.\"id\" = ? ";
		Date serviceStartDate = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, breedingEventId);
			}}, new ResultSetExtractor<Date>() {
				public Date extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					if (resultSet.next()) {
						return resultSet.getDate(1);
					}
					return null;
				}
			});	

		return serviceStartDate;
	}

	
}

