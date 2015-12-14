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
		final String Qry = "insert into pigtrax.\"PregnancyEvent\"(\"id_PigInfo\", \"id_BreedingEvent\", \"id_EmployeeGroup\", \"id_PregnancyEventType\", "
				+ "\"id_PregnancyExamResultType\", \"examDate\", \"resultDate\", \"sowCondition\", \"lastUpdated\", \"userUpdated\",\"id_Premise\") "
				+ "values(?,?,?,?,?,?,?,?,current_timestamp,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});	    	           
	    	            ps.setInt(1, pregnancyEvent.getPigInfoId());
	    	            ps.setInt(2, pregnancyEvent.getBreedingEventId());
	    	            if(pregnancyEvent.getEmployeeGroupId() != null && pregnancyEvent.getEmployeeGroupId() != 0)
	    	            	ps.setInt(3, pregnancyEvent.getEmployeeGroupId());
	    	            else
	    	            	ps.setNull(3, java.sql.Types.INTEGER);
	    	            if(pregnancyEvent.getPregnancyEventTypeId() != null && pregnancyEvent.getPregnancyEventTypeId() != 0)
	    	            	ps.setInt(4, pregnancyEvent.getPregnancyEventTypeId());
	    	            else 
	    	            	ps.setNull(4, java.sql.Types.INTEGER);
	    	            if(pregnancyEvent.getPregnancyExamResultTypeId() != null && pregnancyEvent.getPregnancyExamResultTypeId() != 0)
	    	            	ps.setObject(5, pregnancyEvent.getPregnancyExamResultTypeId());
	    	            else
	    	            	ps.setNull(5, java.sql.Types.INTEGER);
	    	            
	    	            if(pregnancyEvent.getExamDate() != null)
	    	            	ps.setDate(6, new java.sql.Date(pregnancyEvent.getExamDate().getTime()));
	    	            else
	    	            	ps.setNull(6, java.sql.Types.DATE);
	    	            
	    				ps.setDate(7,  new java.sql.Date(pregnancyEvent.getResultDate().getTime()));
	    				if(pregnancyEvent.getSowCondition() != null && pregnancyEvent.getSowCondition() != 0)
	    					ps.setInt(8, pregnancyEvent.getSowCondition());
	    				else
	    					ps.setNull(8, java.sql.Types.INTEGER);
	    				
	    				ps.setString(9, pregnancyEvent.getUserUpdated());
	    				
	    				if(pregnancyEvent.getPremiseId() != null && pregnancyEvent.getPremiseId() != 0)
	    					ps.setInt(10, pregnancyEvent.getPremiseId());
	    				else
	    					ps.setNull(10, java.sql.Types.INTEGER);
	    			
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
	   String qry = "select \"id\", \"id_PigInfo\", \"id_BreedingEvent\", \"id_EmployeeGroup\", \"id_PregnancyEventType\", "
	   		+ "\"id_PregnancyExamResultType\", \"examDate\", \"resultDate\", \"sowCondition\", "
	   		+ "\"lastUpdated\", \"userUpdated\",\"id_Premise\" from pigtrax.\"PregnancyEvent\" where \"id\" = ?";
		
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
   
   
   /**
	 * Retrieves the Pregnancy Event information for a given pig Id 
	 */
   @Override
  public List<PregnancyEvent> getPregnancyEvents(String searchText, String option, final Integer companyId) throws SQLException{	     
	   List<PregnancyEvent> pregnancyEventList = null;	   
	   if(option ==null) option = "pigId";
	   else if("PIGId".equalsIgnoreCase(option))
		   pregnancyEventList = getPregnancyEventsByPigId(searchText, companyId);
	   else if("TATTOO".equalsIgnoreCase(option))
		   pregnancyEventList = getPregnancyEventsByTattoo(searchText, companyId);
	    
		return pregnancyEventList;
	} 
   
   
   /**
  	 * Retrieves the Pregnancy Event information for a given pig Id 
  	 */
     @Override
    public List<PregnancyEvent> getPregnancyEvents(String searchText, String option, final Integer companyId, final Integer premiseId) throws SQLException{	     
  	   List<PregnancyEvent> pregnancyEventList = null;	   
  	   if(option ==null) option = "pigId";
  	   else if("PIGId".equalsIgnoreCase(option))
  		   pregnancyEventList = getPregnancyEventsByPigId(searchText, companyId, premiseId);
  	   else if("TATTOO".equalsIgnoreCase(option))
  		   pregnancyEventList = getPregnancyEventsByTattoo(searchText, companyId, premiseId);
  	    
  		return pregnancyEventList;
  	} 
     
   
   
   /**
	 * Retrieves the Pregnancy Event information for a given pig Id 
	 */
  private List<PregnancyEvent> getPregnancyEventsByPigId(final String pigId, final Integer companyId) throws SQLException{
	   String qry = "select PE.\"id\", PE.\"id_PigInfo\", PE.\"id_EmployeeGroup\", PE.\"id_PregnancyEventType\", "
	   		+ "PE.\"id_PregnancyExamResultType\", PE.\"examDate\", PE.\"resultDate\", PE.\"sowCondition\", "
	   		+ "PE.\"lastUpdated\", PE.\"userUpdated\", PE.\"id_BreedingEvent\",PE.\"id_Premise\" "
	   		+ "from pigtrax.\"PregnancyEvent\" PE JOIN pigtrax.\"PigInfo\" PI ON PE.\"id_PigInfo\" = PI.\"id\""
	   		+ " WHERE PI.\"pigId\" = ? and PI.\"id_Company\" = ? ";
		
		List<PregnancyEvent> pregnancyEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, pigId);
				ps.setInt(2, companyId);
			}}, new PregnancyEventMapper());

		return pregnancyEventList; 
	}
  
  /**
 	 * Retrieves the Pregnancy Event information for a given pig Id 
 	 */
   private List<PregnancyEvent> getPregnancyEventsByPigId(final String pigId, final Integer companyId, final Integer premiseId) throws SQLException{
 	   String qry = "select PE.\"id\", PE.\"id_PigInfo\", PE.\"id_EmployeeGroup\", PE.\"id_PregnancyEventType\", "
 	   		+ "PE.\"id_PregnancyExamResultType\", PE.\"examDate\", PE.\"resultDate\", PE.\"sowCondition\", "
 	   		+ "PE.\"lastUpdated\", PE.\"userUpdated\", PE.\"id_BreedingEvent\",PE.\"id_Premise\" "
 	   		+ "from pigtrax.\"PregnancyEvent\" PE JOIN pigtrax.\"PigInfo\" PI ON PE.\"id_PigInfo\" = PI.\"id\" and PE.\"id_Premise\" = ?"
 	   		+ " WHERE PI.\"pigId\" = ? and PI.\"id_Company\" = ? ";
 		
 		List<PregnancyEvent> pregnancyEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, pigId);
 				ps.setInt(2, companyId);
 				ps.setInt(3, premiseId);
 			}}, new PregnancyEventMapper());

 		return pregnancyEventList; 
 	}
  
  
  /**
 	 * Retrieves the Pregnancy Event information for a given pig Id 
 	 */
   private List<PregnancyEvent> getPregnancyEventsByTattoo(final String tattoo, final Integer companyId) throws SQLException{
 	   String qry = "select PE.\"id\", PE.\"id_PigInfo\", PE.\"id_EmployeeGroup\", PE.\"id_PregnancyEventType\", "
 	   		+ "PE.\"id_PregnancyExamResultType\", PE.\"examDate\", PE.\"resultDate\", PE.\"sowCondition\", "
 	   		+ "PE.\"lastUpdated\", PE.\"userUpdated\", PE.\"id_BreedingEvent\",\"id_Premise\" "
 	   		+ "from pigtrax.\"PregnancyEvent\" PE JOIN pigtrax.\"PigInfo\" PI ON PE.\"id_PigInfo\" = PI.\"id\""
 	   		+ " WHERE PI.\"tattoo\" = ? and PI.\"id_Company\" = ? ";
 		
 		List<PregnancyEvent> pregnancyEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, tattoo);
 				ps.setInt(2, companyId); 				
 			}}, new PregnancyEventMapper());

 		return pregnancyEventList;
 	}
   
   /**
	 * Retrieves the Pregnancy Event information for a given pig Id 
	 */
  private List<PregnancyEvent> getPregnancyEventsByTattoo(final String tattoo, final Integer companyId, final Integer premiseId) throws SQLException{
	   String qry = "select PE.\"id\", PE.\"id_PigInfo\", PE.\"id_EmployeeGroup\", PE.\"id_PregnancyEventType\", "
	   		+ "PE.\"id_PregnancyExamResultType\", PE.\"examDate\", PE.\"resultDate\", PE.\"sowCondition\", "
	   		+ "PE.\"lastUpdated\", PE.\"userUpdated\", PE.\"id_BreedingEvent\",\"id_Premise\" "
	   		+ "from pigtrax.\"PregnancyEvent\" PE JOIN pigtrax.\"PigInfo\" PI ON PE.\"id_PigInfo\" = PI.\"id\" and PE.\"id_Premise\" = ?"
	   		+ " WHERE PI.\"tattoo\" = ? and PI.\"id_Company\" = ? ";
		
		List<PregnancyEvent> pregnancyEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tattoo);
				ps.setInt(2, companyId);
				ps.setInt(3, premiseId);
			}}, new PregnancyEventMapper());

		return pregnancyEventList;
	}
   
    
    @Override
	public int updatePregnancyEventDetails(final PregnancyEvent pregnancyEvent)
			throws SQLException, DuplicateKeyException {
		String Qry = "update pigtrax.\"PregnancyEvent\" set \"id_PigInfo\" = ?, \"id_EmployeeGroup\" = ?, \"id_PregnancyEventType\" = ?, "
				+ "\"id_PregnancyExamResultType\"= ?, \"examDate\"= ?, \"resultDate\" = ?, \"sowCondition\" = ?, \"lastUpdated\" = current_timestamp, "
				+ "\"userUpdated\" = ?, \"id_BreedingEvent\" = ?,\"id_Premise\"= ? where \"id\" = ? ";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, pregnancyEvent.getPigInfoId());
				if(pregnancyEvent.getEmployeeGroupId() != null && pregnancyEvent.getEmployeeGroupId() != 0)
					ps.setInt(2, pregnancyEvent.getEmployeeGroupId());
				else
					ps.setNull(2, java.sql.Types.INTEGER);
				
				if(pregnancyEvent.getPregnancyEventTypeId() != null && pregnancyEvent.getPregnancyEventTypeId() != 0)
					ps.setInt(3, pregnancyEvent.getPregnancyEventTypeId());
				else
					ps.setNull(3, java.sql.Types.INTEGER);
				
				if(pregnancyEvent.getPregnancyEventTypeId() != null && pregnancyEvent.getPregnancyEventTypeId() != 0)
					ps.setInt(4, pregnancyEvent.getPregnancyExamResultTypeId());
				else
					ps.setNull(4, java.sql.Types.INTEGER);
				
				if(pregnancyEvent.getExamDate() != null)
					ps.setDate(5, new java.sql.Date(pregnancyEvent.getExamDate().getTime()));
				else
					ps.setNull(5, java.sql.Types.DATE);
				
				ps.setDate(6,new java.sql.Date(pregnancyEvent.getResultDate().getTime()));
				
				if(pregnancyEvent.getSowCondition() != null && pregnancyEvent.getSowCondition() != 0)
					ps.setInt(7, pregnancyEvent.getSowCondition());
				else
					ps.setNull(7, java.sql.Types.INTEGER);
				
				ps.setString(8, pregnancyEvent.getUserUpdated());	
				ps.setInt(9, pregnancyEvent.getBreedingEventId());
				if(pregnancyEvent.getPremiseId() != null && pregnancyEvent.getPremiseId() != 0)
					ps.setInt(10, pregnancyEvent.getPremiseId());
				else
					ps.setNull(10, java.sql.Types.INTEGER);
				ps.setInt(11, pregnancyEvent.getId());
			}
		});
	}
   
   
   private static final class PregnancyEventMapper implements RowMapper<PregnancyEvent> {
		public PregnancyEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			PregnancyEvent pregnancyEvent = new PregnancyEvent();
			pregnancyEvent.setId(rs.getInt("id"));
			pregnancyEvent.setPigInfoId(rs.getInt("id_PigInfo"));
			pregnancyEvent.setBreedingEventId(rs.getInt("id_BreedingEvent"));
			pregnancyEvent.setEmployeeGroupId(rs.getInt("id_EmployeeGroup"));
			pregnancyEvent.setPregnancyEventTypeId(rs.getInt("id_PregnancyEventType"));
			pregnancyEvent.setPregnancyExamResultTypeId(rs.getInt("id_PregnancyExamResultType"));
			pregnancyEvent.setExamDate(rs.getDate("examDate"));
			pregnancyEvent.setResultDate(rs.getDate("resultDate"));
			pregnancyEvent.setSowCondition(rs.getInt("sowCondition"));
			pregnancyEvent.setLastUpdated(rs.getDate("lastUpdated"));
			pregnancyEvent.setUserUpdated(rs.getString("userUpdated"));
			pregnancyEvent.setPremiseId(rs.getInt("id_Premise"));
			return pregnancyEvent;
		}
	}
   
 
   /**
    * Delete a pregnancy event based on the primary key id
    */
   @Override
	public void deletePregnancyEvent(final Integer pregnancyEventId)
			throws SQLException {
	   final String qry = "delete from pigtrax.\"PregnancyEvent\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pregnancyEventId);
			}
		});
	}
   
   
   /**
    * 
    * @param breedingEventId
    * @return 
    */
   public List<PregnancyEvent> getPregnancyEvents(final Integer breedingEventId)
   {
	String sql = "select * from pigtrax.\"PregnancyEvent\" where \"id_BreedingEvent\" = ?";

	List<PregnancyEvent> pregnancyEventList = jdbcTemplate.query(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {				
				ps.setInt(1, breedingEventId);
			}}, new PregnancyEventMapper());

		return pregnancyEventList;	
   }
   
   @Override
	public List<PregnancyEvent> getOpenPregnancyRecords(final Integer pigInfoId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select PE.* from pigtrax.\"PregnancyEvent\" PE where PE.\"id_PregnancyEventType\" = ? and PE.\"id_PregnancyExamResultType\" = ? "
				+ "and PE.\"id_PigInfo\" = ? and PE.\"id\" not in (select FE.\"id_PregnancyEvent\" from pigtrax.\"FarrowEvent\" FE where FE.\"id_PigInfo\" = ?) order by PE.\"id\" desc");
		List<PregnancyEvent> pregnancyEventList = jdbcTemplate.query(buffer.toString(), new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, 1);
				ps.setInt(2, 1);
				ps.setInt(3, pigInfoId);
				ps.setInt(4, pigInfoId);
			}}, new PregnancyEventMapper());
		
		return pregnancyEventList;
		
	}
  
}
