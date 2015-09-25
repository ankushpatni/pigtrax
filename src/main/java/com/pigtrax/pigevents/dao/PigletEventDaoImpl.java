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

import com.pigtrax.pigevents.beans.PigletEvent;
import com.pigtrax.pigevents.dao.interfaces.PigletEventDao;

@Repository
@Transactional
public class PigletEventDaoImpl implements PigletEventDao {
	

	private static final Logger logger = Logger.getLogger(PigletEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int addPigletEventDetails(final PigletEvent pigletEvent)
			throws SQLException, DuplicateKeyException{
		final String Qry = "insert into pigtrax.\"IndividualPigletStatus\"(\"tattooId\", \"weightAtBirth\", \"weightAtWeaning\", \"lastUpdated\","
				+ " \"userUpdated\", \"id_FarrowEvent\") "
				+ "values(?,?,?,current_timestamp,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setString(1, pigletEvent.getTattooId());
	    	            ps.setObject(2, pigletEvent.getWeightAtBirth(), java.sql.Types.DOUBLE);
	    				ps.setObject(3, pigletEvent.getWeightAtWeaning(), java.sql.Types.DOUBLE);
	    				ps.setString(4, pigletEvent.getUserUpdated());
	    				ps.setObject(5, pigletEvent.getFarrowEventId(), java.sql.Types.INTEGER);
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}	
   
   
   /**
	 * Retrieves the Piglet Event information for a given Pig Id/Tattoo Id/Piglet Id
	 */
   @Override
  public List<PigletEvent> getPigletEvents(String searchText, String option, final Integer companyId) throws SQLException{	     
	   List<PigletEvent> pigletEventList = null;	   
	   if(option ==null) option = "pigId";
	   else if("PIGID".equalsIgnoreCase(option))
		   pigletEventList = getPigletEventsByPigId(searchText, companyId);
	   else if("PIGLETTATTOOID".equalsIgnoreCase(option))
		   pigletEventList = getPigletEventsByPigletTattooId(searchText, companyId);
		return pigletEventList;
	} 
   
   
   
   /**
	 * Retrieves the Piglet Event information for a given Pig Id
	 */
  private List<PigletEvent> getPigletEventsByPigId(final String pigId, final Integer companyId) throws SQLException{
	   String qry = "select IPS.\"id\", IPS.\"tattooId\", IPS.\"weightAtBirth\", IPS.\"weightAtWeaning\", "
	   		+ "IPS.\"lastUpdated\", IPS.\"userUpdated\", IPS.\"id_FarrowEvent\" from pigtrax.\"IndividualPigletStatus\" IPS "
	   		+ "JOIN pigtrax.\"FarrowEvent\" FE ON IPS.\"id_FarrowEvent\" = FE.\"id\" "
	   		+ "JOIN pigtrax.\"PregnancyEvent\" PE on FE.\"id_PregnancyEvent\" = PE.\"id\" "
	   		+ "JOIN pigtrax.\"PigInfo\" PI on PE.\"id_PigInfo\" = PI.\"id\" "
	   		+ " WHERE PI.\"pigId\" = ? and PI.\"id_Company\" = ? ";
		
		List<PigletEvent> pigletEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, pigId);
				ps.setInt(2, companyId);
			}}, new PigletEventMapper());

		return pigletEventList; 
	}
  
  
  /**
 	 * Retrieves the Pregnancy Event information for a given tattoo Id 
 	 */
   private List<PigletEvent> getPigletEventsByPigletTattooId(final String tattoo, final Integer companyId) throws SQLException{
	   String qry = "select IPS.\"id\", IPS.\"tattooId\", IPS.\"weightAtBirth\", IPS.\"weightAtWeaning\", "
		   		+ "IPS.\"lastUpdated\", IPS.\"userUpdated\", IPS.\"id_FarrowEvent\" from pigtrax.\"IndividualPigletStatus\" IPS "
		   		+ "JOIN pigtrax.\"FarrowEvent\" FE ON IPS.\"id_FarrowEvent\" = FE.\"id\" "
		   		+ "JOIN pigtrax.\"PregnancyEvent\" PE on FE.\"id_PregnancyEvent\" = PE.\"id\" "
		   		+ "JOIN pigtrax.\"PigInfo\" PI on PE.\"id_PigInfo\" = PI.\"id\" "
		   		+ " WHERE IPS.\"tattooId\" = ? and PI.\"id_Company\" = ? ";
 		
 		List<PigletEvent> pigletEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, tattoo);
 				ps.setInt(2, companyId);
 			}}, new PigletEventMapper());

 		return pigletEventList;
 	}
   
    
    @Override
	public int updatePigletEventDetails(final PigletEvent pigletEvent)
			throws SQLException, DuplicateKeyException {
		String Qry = "update pigtrax.\"IndividualPigletStatus\" set \"tattooId\" = ?, \"weightAtBirth\" = ?, \"weightAtWeaning\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"id_FarrowEvent\" = ? where \"id\" = ? ";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setString(1, pigletEvent.getTattooId());
				ps.setObject(2, pigletEvent.getWeightAtBirth(), java.sql.Types.DOUBLE);
				ps.setObject(3, pigletEvent.getWeightAtWeaning(), java.sql.Types.DOUBLE);				
				ps.setString(4, pigletEvent.getUserUpdated());	
				ps.setObject(5, pigletEvent.getFarrowEventId(), java.sql.Types.INTEGER);
				ps.setInt(6, pigletEvent.getId());
			}
		});
	}
   
   
   private static final class PigletEventMapper implements RowMapper<PigletEvent> {
		public PigletEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			PigletEvent pigletEvent = new PigletEvent();
			pigletEvent.setId(rs.getInt("id"));
			pigletEvent.setTattooId(rs.getString("tattooId"));
			pigletEvent.setWeightAtBirth(rs.getDouble("weightAtBirth"));
			pigletEvent.setWeightAtWeaning(rs.getDouble("weightAtWeaning"));
			pigletEvent.setFarrowEventId(rs.getInt("id_FarrowEvent"));
			pigletEvent.setLastUpdated(rs.getDate("lastUpdated"));
			pigletEvent.setUserUpdated(rs.getString("userUpdated"));
			return pigletEvent;
		}
	}
   
 
   /**
    * Delete a piglet event based on the primary key id
    */
   @Override
	public void deletePigletEvent(final Integer pigletEventId)
			throws SQLException {
	   final String qry = "delete from pigtrax.\"IndividualPigletStatus\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigletEventId);
			}
		});
	}
}
