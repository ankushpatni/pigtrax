package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.pigtrax.pigevents.beans.FarrowEvent;
import com.pigtrax.pigevents.dao.interfaces.FarrowEventDao;

@Repository
@Transactional
public class FarrowEventDaoImpl implements FarrowEventDao {
	

	private static final Logger logger = Logger.getLogger(FarrowEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int addFarrowEventDetails(final FarrowEvent farrowEvent)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"FarrowEvent\"(\"farrowDateTime\", \"id_Pen\", \"liveBorns\", "
				+ "\"stillBorns\", \"mummies\", \"maleBorns\", \"femaleBorns\", \"weightInKgs\", \"inducedBirth\", "
				+ "\"assistedBirth\", \"remarks\", \"sowCondition\", \"lastUpdated\", \"userUpdated\", \"id_EmployeeGroup\", "
				+ "\"id_PigInfo\", \"id_PregnancyEvent\",\"teats\",\"id_PigletCondition\", \"weakBorns\" ) values(?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            if(farrowEvent.getFarrowDateTime() != null){
		    	            ps.setDate(1, new java.sql.Date(farrowEvent.getFarrowDateTime().getTime()));
	    	            }
	    	            else{
	    	            	ps.setNull(1,  java.sql.Types.DATE);
	    	            }
	    	            
	    	            if(farrowEvent.getPenId() != null){
	    	            	ps.setInt(2, farrowEvent.getPenId());
	    	            }
	    	            else{
	    	            	ps.setNull(2, java.sql.Types.INTEGER);
	    	            }
	    				ps.setObject(3, farrowEvent.getLiveBorns(), java.sql.Types.INTEGER);
	    				ps.setObject(4, farrowEvent.getStillBorns(), java.sql.Types.INTEGER);
	    				ps.setObject(5, farrowEvent.getMummies(), java.sql.Types.INTEGER);
	    				ps.setObject(6, farrowEvent.getMaleBorns(), java.sql.Types.INTEGER);
	    				ps.setObject(7, farrowEvent.getFemaleBorns(), java.sql.Types.INTEGER);
	    				ps.setObject(8, farrowEvent.getWeightInKgs(), java.sql.Types.DOUBLE);
	    				ps.setBoolean(9, farrowEvent.isInducedBirth());
	    				ps.setBoolean(10, farrowEvent.isAssistedBirth());
	    				ps.setString(11, farrowEvent.getRemarks());
	    				
	    				if(farrowEvent.getSowCondition() != null){
	    					ps.setInt(12, farrowEvent.getSowCondition());
	    				}
	    				else{
	    					ps.setNull(12, java.sql.Types.INTEGER);
	    				}
	    				ps.setString(13, farrowEvent.getUserUpdated());
	    				
	    				if(farrowEvent.getEmployeeGroupId() != null && farrowEvent.getEmployeeGroupId() != 0){
	    					ps.setInt(14, farrowEvent.getEmployeeGroupId());
	    				}
	    				else{
	    					ps.setNull(14, java.sql.Types.INTEGER);
	    				}
	    				
	    				if(farrowEvent.getPigInfoId() != null){
	    					ps.setInt(15, farrowEvent.getPigInfoId());
	    				}
	    				else{
	    					ps.setNull(15, java.sql.Types.INTEGER);
	    				}
	    				
	    				if( farrowEvent.getPregnancyEventId() != null){
	    					ps.setInt(16, farrowEvent.getPregnancyEventId());
	    				}
	    				else{
	    					ps.setNull(16, java.sql.Types.INTEGER);
	    				}
	    				
	    				ps.setInt(17,  farrowEvent.getTeats());
	    				if(farrowEvent.getPigletConditionId() != null && farrowEvent.getPigletConditionId() != 0){
	    					ps.setInt(18, farrowEvent.getPigletConditionId());
	    				}
	    				else{
	    					ps.setNull(18, java.sql.Types.INTEGER);
	    				}
	    				
	    				ps.setObject(19, farrowEvent.getWeakBorns(), java.sql.Types.INTEGER);
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
   public FarrowEvent getFarrowEvent(final Integer farrowEventId) { 
	   
	   String qry = "select FE.\"id\", FE.\"farrowDateTime\", FE.\"id_Pen\", "
		   		+ "FE.\"liveBorns\", FE.\"stillBorns\", FE.\"mummies\", FE.\"maleBorns\", FE.\"femaleBorns\" "
		   		+ ", FE.\"weightInKgs\", FE.\"inducedBirth\", FE.\"assistedBirth\", FE.\"remarks\", FE.\"sowCondition\""
		   		+" , FE.\"lastUpdated\", FE.\"userUpdated\", FE.\"id_EmployeeGroup\", FE.\"id_PigInfo\", FE.\"id_PregnancyEvent\", "
		   		+ "FE.\"teats\", FE.\"id_PigletCondition\", FE.\"weakBorns\" "
		   		+ " from pigtrax.\"FarrowEvent\" FE "
		   		+ " WHERE FE.\"id\" = ? ";
		
		List<FarrowEvent> farrowEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, farrowEventId);
			}}, new FarrowEventMapper());

		if(farrowEventList != null && farrowEventList.size() > 0){
			return farrowEventList.get(0);
		}
		return null;
	}
   
   
   /**
	 * Retrieves the FarrowEvent Event information for a given Id  
	 */
  public FarrowEvent getFarrowEventByPregancyEvent(final Integer pregnancyEventId) { 
	   
	   String qry = "select FE.\"id\", FE.\"farrowDateTime\", FE.\"id_Pen\", "
		   		+ "FE.\"liveBorns\", FE.\"stillBorns\", FE.\"mummies\", FE.\"maleBorns\", FE.\"femaleBorns\" "
		   		+ ", FE.\"weightInKgs\", FE.\"inducedBirth\", FE.\"assistedBirth\", FE.\"remarks\", FE.\"sowCondition\""
		   		+" , FE.\"lastUpdated\", FE.\"userUpdated\", FE.\"id_EmployeeGroup\", FE.\"id_PigInfo\", FE.\"id_PregnancyEvent\", "
		   		+ "FE.\"teats\", FE.\"id_PigletCondition\", FE.\"weakBorns\" "
		   		+ " from pigtrax.\"FarrowEvent\" FE "
		   		+ " WHERE FE.\"id_PregnancyEvent\" = ? ";
		
		List<FarrowEvent> farrowEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pregnancyEventId);
			}}, new FarrowEventMapper());

		if(farrowEventList != null && farrowEventList.size() > 0){
			return farrowEventList.get(0);
		}
		return null;
	}   
 
   /**
	 * Retrieves the Pregnancy Event information for a given pig Id 
	 */
   @Override
  public List<FarrowEvent> getFarrowEvents(String searchText, String option, final Integer companyId) throws SQLException{	     
	   List<FarrowEvent> farrowEventList = null;	   
	   if(option ==null) option = "pigId";
	   else if("PIGId".equalsIgnoreCase(option))
		   farrowEventList = getFarrowEventsByPigId(searchText, companyId);
	   else if("TATTOO".equalsIgnoreCase(option))
		   farrowEventList = getFarrowEventsByTattoo(searchText, companyId);
	    
		return farrowEventList;
	} 
   
   
   
   /**
	 * Retrieves the Pregnancy Event information for a given pig Id 
	 */
  private List<FarrowEvent> getFarrowEventsByPigId(final String pigId, final Integer companyId) throws SQLException{
	   String qry = "select FE.\"id\", FE.\"farrowDateTime\", FE.\"id_Pen\", "
	   		+ "FE.\"liveBorns\", FE.\"stillBorns\", FE.\"mummies\", FE.\"maleBorns\", FE.\"femaleBorns\", "
	   		+ "FE.\"weightInKgs\", FE.\"inducedBirth\", FE.\"assistedBirth\", FE.\"remarks\", FE.\"sowCondition\""
	   		+" , FE.\"lastUpdated\", FE.\"userUpdated\", FE.\"id_EmployeeGroup\", FE.\"id_PigInfo\", FE.\"id_PregnancyEvent\", "
	   		+ "FE.\"teats\", FE.\"id_PigletCondition\", FE.\"weakBorns\" "
	   		+ " from pigtrax.\"FarrowEvent\" FE JOIN pigtrax.\"PigInfo\" PI ON FE.\"id_PigInfo\" = PI.\"id\""
	   		+ " WHERE PI.\"pigId\" = ? and PI.\"id_Company\" = ? ";
		
		List<FarrowEvent> farrowEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, pigId);
				ps.setInt(2, companyId);
			}}, new FarrowEventMapper());

		return farrowEventList; 
	}
  
  
  /**
 	 * Retrieves the Pregnancy Event information for a given pig Id 
 	 */
   private List<FarrowEvent> getFarrowEventsByTattoo(final String tattoo, final Integer companyId) throws SQLException{
	   String qry = "select FE.\"id\", FE.\"farrowDateTime\", FE.\"id_Pen\", "
		   		+ "FE.\"liveBorns\", FE.\"stillBorns\", FE.\"mummies\", FE.\"maleBorns\", FE.\"femaleBorns\", "
		   		+ "FE.\"weightInKgs\", FE.\"inducedBirth\", FE.\"assistedBirth\", FE.\"remarks\", FE.\"sowCondition\""
		   		+" , FE.\"lastUpdated\", FE.\"userUpdated\", FE.\"id_EmployeeGroup\", FE.\"id_PigInfo\", FE.\"id_PregnancyEvent\", "
		   		+ "FE.\"teats\", FE.\"id_PigletCondition\", FE.\"weakBorns\" "
		   		+ " from pigtrax.\"FarrowEvent\" FE JOIN pigtrax.\"PigInfo\" PI ON FE.\"id_PigInfo\" = PI.\"id\""
		   		+ " WHERE PI.\"tattoo\" = ? and PI.\"id_Company\" = ? ";
 		
	   List<FarrowEvent> farrowEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, tattoo);
 				ps.setInt(2, companyId);
 			}}, new FarrowEventMapper());

 		return farrowEventList;
 	}
   
    
    @Override
	public int updateFarrowEventDetails(final FarrowEvent farrowEvent)
			throws SQLException, DuplicateKeyException {
		String Qry = "update pigtrax.\"FarrowEvent\" set  \"id_Pen\" = ?, \"farrowDateTime\"= ?, \"liveBorns\"= ?"
				+ ", \"stillBorns\" = ?, \"mummies\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"maleBorns\" = ?, \"femaleBorns\" = ? "
				+ " , \"weightInKgs\" = ? , \"inducedBirth\" = ?, \"assistedBirth\" = ?, \"remarks\" = ? "
				+ ", \"sowCondition\" = ?, \"id_EmployeeGroup\" = ? , \"id_PigInfo\" = ?, \"id_PregnancyEvent\"=?, "
				+ "\"teats\" = ?,\"id_PigletCondition\"=?, \"weakBorns\"=? where \"id\" = ? ";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
						
				if(farrowEvent.getPenId() != null){
					ps.setInt(1, farrowEvent.getPenId());
				}
				else{
					ps.setNull(1, java.sql.Types.INTEGER);
				}
				
				if(farrowEvent.getFarrowDateTime() != null){
	    	        ps.setDate(2, new java.sql.Date(farrowEvent.getFarrowDateTime().getTime()));
 	            }
 	            else{
 	            	ps.setNull(2,  java.sql.Types.DATE);
 	            }
				
				if(farrowEvent.getLiveBorns() != null){
					ps.setInt(3, farrowEvent.getLiveBorns());
				}
				else{
					ps.setNull(3, java.sql.Types.INTEGER);
				}
				
				if(farrowEvent.getStillBorns() != null){
					ps.setInt(4, farrowEvent.getStillBorns());
				}
				else{
					ps.setNull(4, java.sql.Types.INTEGER);
				}
				
				if(farrowEvent.getMummies() != null){
					ps.setInt(5,farrowEvent.getMummies());
				}
				else{
					ps.setNull(5, java.sql.Types.INTEGER);
				}
				ps.setString(6, farrowEvent.getUserUpdated());
				
				if(farrowEvent.getMaleBorns() != null){
					ps.setInt(7, farrowEvent.getMaleBorns());
				}
				else{
					ps.setNull(7, java.sql.Types.INTEGER);
				}
				
				if(farrowEvent.getFemaleBorns() != null){
					ps.setInt(8, farrowEvent.getFemaleBorns());
				}
				else{
					ps.setNull(8, java.sql.Types.INTEGER);
				}
				
				ps.setObject(9, farrowEvent.getWeightInKgs(), java.sql.Types.DOUBLE);
				ps.setBoolean(10, farrowEvent.isInducedBirth());
				ps.setBoolean(11, farrowEvent.isAssistedBirth());
				ps.setString(12, farrowEvent.getRemarks());
				if(farrowEvent.getSowCondition() != null){
					ps.setInt(13, farrowEvent.getSowCondition());
				}
				else{
					ps.setNull(13, java.sql.Types.INTEGER);
				}
				
				if(farrowEvent.getEmployeeGroupId() != null && farrowEvent.getEmployeeGroupId() != 0){
					ps.setInt(14, farrowEvent.getEmployeeGroupId());
				}
				else{
					ps.setNull(14, java.sql.Types.INTEGER);
				}
				
				if(farrowEvent.getPigInfoId() != null){
					ps.setInt(15, farrowEvent.getPigInfoId());
				}
				else{
					ps.setNull(15, java.sql.Types.INTEGER);
				}
				
				if(farrowEvent.getPregnancyEventId() != null){
					ps.setInt(16, farrowEvent.getPregnancyEventId());
				}
				else{
					ps.setNull(16, java.sql.Types.INTEGER);
				}
				
				ps.setInt(17, farrowEvent.getTeats());
				
				if(farrowEvent.getPigletConditionId() != null){
					ps.setInt(18, farrowEvent.getPigletConditionId());
				}
				else{
					ps.setNull(18, java.sql.Types.INTEGER);
				}
				
				if(farrowEvent.getWeakBorns() != null){
					ps.setInt(19, farrowEvent.getWeakBorns());
				}
				else{
					ps.setNull(19, java.sql.Types.INTEGER);
				}
				
				if(farrowEvent.getId() != null){
					ps.setInt(20, farrowEvent.getId());
				}
				else{
					ps.setNull(20, java.sql.Types.INTEGER);
				}
			}
		});
	}
   
   
   private static final class FarrowEventMapper implements RowMapper<FarrowEvent> {
		public FarrowEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			FarrowEvent farrowEvent = new FarrowEvent();
			farrowEvent.setId(rs.getInt("id"));
			farrowEvent.setFarrowDateTime(rs.getDate("farrowDateTime"));
			farrowEvent.setPenId(rs.getObject("id_Pen") != null ? (Integer)rs.getObject("id_Pen") : null);
			farrowEvent.setLiveBorns(rs.getInt("liveBorns"));
			farrowEvent.setStillBorns(rs.getInt("stillBorns"));
			farrowEvent.setMummies(rs.getInt("mummies"));
			farrowEvent.setMaleBorns(rs.getInt("maleBorns"));
			farrowEvent.setFemaleBorns(rs.getInt("femaleBorns"));
			farrowEvent.setWeightInKgs(rs.getDouble("weightInKgs"));;
			farrowEvent.setInducedBirth(rs.getBoolean("inducedBirth"));
			farrowEvent.setAssistedBirth(rs.getBoolean("assistedBirth"));
			farrowEvent.setRemarks(rs.getString("remarks"));;
			farrowEvent.setLastUpdated(rs.getDate("lastUpdated"));
			farrowEvent.setUserUpdated(rs.getString("userUpdated"));;
			farrowEvent.setEmployeeGroupId(rs.getInt("id_EmployeeGroup"));
			farrowEvent.setPigInfoId(rs.getInt("id_PigInfo"));
			farrowEvent.setPregnancyEventId(rs.getInt("id_PregnancyEvent"));
			farrowEvent.setSowCondition(rs.getInt("sowCondition"));
			farrowEvent.setTeats(rs.getInt("teats"));
			farrowEvent.setPigletConditionId(rs.getInt("id_PigletCondition"));
			farrowEvent.setWeakBorns(rs.getInt("weakBorns"));
			return farrowEvent;
		}
	} 
   
 
   /**
    * Delete a pregnancy event based on the primary key id
    */
   @Override
	public void deleteFarrowEvent(final Integer farrowEventId)
			throws SQLException {
	   final String qry = "delete from pigtrax.\"FarrowEvent\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, farrowEventId);
			}
		});
	}
   
   @Override
   public void updateLitterId(final Integer farrowEventId, final Integer companyId) { 
	   final String qry = "update pigtrax.\"FarrowEvent\"  set \"litterId\" = (SELECT \"litterId\"+1 as litterId "
	   		+ "from pigtrax.\"Company\"  WHERE \"id\"=?) where id = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, companyId);
				ps.setInt(2, farrowEventId);
			}
		});		
   }
   
   
   @Override
	public boolean checkFarrowEventByBreedingEvent(final Integer breedingEventId) { 
		String sql = "select count(FE.\"id\") from pigtrax.\"FarrowEvent\" FE where \"id_PregnancyEvent\" "
				+ "in (select PE.\"id\" from pigtrax.\"PregnancyEvent\" PE where \"id_BreedingEvent\" = ?) ";
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(sql,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, breedingEventId);
				}
			},
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getInt(1);
		            }
		            return null;
		          }
		        });
		
		return cnt > 0? true : false;
	}
   
}
