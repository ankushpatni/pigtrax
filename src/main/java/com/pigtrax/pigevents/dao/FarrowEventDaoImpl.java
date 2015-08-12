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

import com.pigtrax.pigevents.beans.FarrowEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;
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
		final String Qry = "insert into pigtrax.\"FarrowEvent\"(\"farrowId\", \"farrowDateTime\", \"id_Pen\", \"liveBorns\", "
				+ "\"stillBorns\", \"mummies\", \"maleBorns\", \"femaleBorns\", \"weightInKgs\", \"inducedBirth\", "
				+ "\"assistedBirth\", \"remarks\", \"sowCondition\", \"lastUpdated\", \"userUpdated\", \"id_EmployeeGroup\", "
				+ "\"id_PigInfo\", \"id_PregnancyEvent\" ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setString(1, farrowEvent.getFarrowId());
	    	            ps.setDate(2, new java.sql.Date(farrowEvent.getFarrowDateTime().getTime()));
	    				ps.setInt(3, farrowEvent.getPenId());
	    				ps.setObject(4, farrowEvent.getLiveBorns());
	    				ps.setObject(5, farrowEvent.getStillBorns());
	    				ps.setObject(6, farrowEvent.getMummies());
	    				ps.setObject(7, farrowEvent.getMaleBorns());
	    				ps.setObject(8, farrowEvent.getFemaleBorns());
	    				ps.setDouble(9, farrowEvent.getWeightInKgs());
	    				ps.setBoolean(10, farrowEvent.isInducedBirth());
	    				ps.setBoolean(11, farrowEvent.isAssistedBirth());
	    				ps.setString(12, farrowEvent.getRemarks());
	    				ps.setInt(13, farrowEvent.getSowCondition());
	    				ps.setString(14, farrowEvent.getUserUpdated());
	    				ps.setInt(15, farrowEvent.getEmployeeGroupId());
	    				ps.setInt(16, farrowEvent.getPigInfoId());
	    				ps.setInt(17, farrowEvent.getPregnancyEventId());
	    			
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
	   
	   String qry = "select FE.\"id\", FE.\"farrowId\", FE.\"farrowDateTime\", FE.\"id_Pen\", "
		   		+ "FE.\"liveBorns\", FE.\"stillBorns\", FE.\"mummies\", FE.\"maleBorns\", FE.\"femaleBorns\" "
		   		+ "FE.\"weightInKgs\", FE.\"inducedBirth\", FE.\"assistedBirth\", FE.\"remarks\", FE.\"sowCondition\""
		   		+" , FE.\"lastUpdated\", FE.\"userUpdated\", FE.\"id_EmployeeGroup\", FE.\"id_PigInfo\", FE.\"id_PregnancyEvent\""
		   		+ " from pigtrax.\"FarrowEvent\" "
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
	   else if("FARROWID".equalsIgnoreCase(option))
		   farrowEventList = getFarrowEventsByFarrowId(searchText, companyId);
	    
		return farrowEventList;
	} 
   
   
   
   /**
	 * Retrieves the Pregnancy Event information for a given pig Id 
	 */
  private List<FarrowEvent> getFarrowEventsByPigId(final String pigId, final Integer companyId) throws SQLException{
	   String qry = "select FE.\"id\", FE.\"farrowId\", FE.\"farrowDateTime\", FE.\"id_Pen\", "
	   		+ "FE.\"liveBorns\", FE.\"stillBorns\", FE.\"mummies\", FE.\"maleBorns\", FE.\"femaleBorns\", "
	   		+ "FE.\"weightInKgs\", FE.\"inducedBirth\", FE.\"assistedBirth\", FE.\"remarks\", FE.\"sowCondition\""
	   		+" , FE.\"lastUpdated\", FE.\"userUpdated\", FE.\"id_EmployeeGroup\", FE.\"id_PigInfo\", FE.\"id_PregnancyEvent\""
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
	 * Retrieves the Pregnancy Event information for a given farrowId
	 */
private List<FarrowEvent> getFarrowEventsByFarrowId(final String farrowId, final Integer companyId) throws SQLException{
	   String qry = "select FE.\"id\", FE.\"farrowId\", FE.\"farrowDateTime\", FE.\"id_Pen\", "
	   		+ "FE.\"liveBorns\", FE.\"stillBorns\", FE.\"mummies\", FE.\"maleBorns\", FE.\"femaleBorns\", "
	   		+ "FE.\"weightInKgs\", FE.\"inducedBirth\", FE.\"assistedBirth\", FE.\"remarks\", FE.\"sowCondition\""
	   		+" , FE.\"lastUpdated\", FE.\"userUpdated\", FE.\"id_EmployeeGroup\", FE.\"id_PigInfo\", FE.\"id_PregnancyEvent\""
	   		+ " from pigtrax.\"FarrowEvent\" FE JOIN pigtrax.\"PigInfo\" PI ON FE.\"id_PigInfo\" = PI.\"id\""
	   		+ " WHERE FE.\"farrowId\" = ? and PI.\"id_Company\" = ? ";
		
		List<FarrowEvent> farrowEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, farrowId);
				ps.setInt(2, companyId);
			}}, new FarrowEventMapper());

		return farrowEventList; 
	}
  
  
  /**
 	 * Retrieves the Pregnancy Event information for a given pig Id 
 	 */
   private List<FarrowEvent> getFarrowEventsByTattoo(final String tattoo, final Integer companyId) throws SQLException{
	   String qry = "select FE.\"id\", FE.\"farrowId\", FE.\"farrowDateTime\", FE.\"id_Pen\", "
		   		+ "FE.\"liveBorns\", FE.\"stillBorns\", FE.\"mummies\", FE.\"maleBorns\", FE.\"femaleBorns\", "
		   		+ "FE.\"weightInKgs\", FE.\"inducedBirth\", FE.\"assistedBirth\", FE.\"remarks\", FE.\"sowCondition\""
		   		+" , FE.\"lastUpdated\", FE.\"userUpdated\", FE.\"id_EmployeeGroup\", FE.\"id_PigInfo\", FE.\"id_PregnancyEvent\""
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
		String Qry = "update pigtrax.\"FarrowEvent\" set \"farrowId\" = ?, \"id_Pen\" = ?, \"farrowDateTime\"= ?, \"liveBorns\"= ?"
				+ ", \"stillBorns\" = ?, \"mummies\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"maleBorns\" = ?, \"femaleBorns\" = ? "
				+ " , \"weightInKgs\" = ? , \"inducedBirth\" = ?, \"assistedBirth\" = ?, \"remarks\" = ? "
				+ ", \"sowCondition\" = ?, \"id_EmployeeGroup\" = ? , \"id_PigInfo\" = ?, \"id_PregnancyEvent\"=? where \"id\" = ? ";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setString(1, farrowEvent.getFarrowId());
				ps.setInt(2, farrowEvent.getPenId());
				ps.setDate(3, new java.sql.Date(farrowEvent.getFarrowDateTime().getTime()));
				ps.setInt(4, farrowEvent.getLiveBorns());
				ps.setInt(5, farrowEvent.getStillBorns());
				ps.setInt(6,farrowEvent.getMummies());
				ps.setString(7, farrowEvent.getUserUpdated());
				ps.setInt(8, farrowEvent.getMaleBorns());	
				ps.setInt(9, farrowEvent.getFemaleBorns());
				ps.setDouble(10, farrowEvent.getWeightInKgs());
				ps.setBoolean(11, farrowEvent.isInducedBirth());
				ps.setBoolean(12, farrowEvent.isAssistedBirth());
				ps.setString(13, farrowEvent.getRemarks());
				ps.setInt(14, farrowEvent.getSowCondition());
				ps.setInt(15, farrowEvent.getEmployeeGroupId());
				ps.setInt(16, farrowEvent.getPigInfoId());
				ps.setInt(17, farrowEvent.getPregnancyEventId());
				ps.setInt(18, farrowEvent.getId());				
			}
		});
	}
   
   
   private static final class FarrowEventMapper implements RowMapper<FarrowEvent> {
		public FarrowEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			FarrowEvent farrowEvent = new FarrowEvent();
			farrowEvent.setId(rs.getInt("id"));
			farrowEvent.setFarrowId(rs.getString("farrowId"));
			farrowEvent.setFarrowDateTime(rs.getDate("farrowDateTime"));
			farrowEvent.setPenId(rs.getInt("id_Pen"));
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
}
