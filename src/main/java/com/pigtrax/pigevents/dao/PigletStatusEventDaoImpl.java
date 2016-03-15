package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.Date;
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

import com.pigtrax.pigevents.beans.PigletStatusEvent;
import com.pigtrax.pigevents.dao.interfaces.PigletStatusEventDao;
import com.pigtrax.usermanagement.enums.PigletStatusEventType;


@Repository
@Transactional
public class PigletStatusEventDaoImpl implements PigletStatusEventDao {

    private static final Logger logger = Logger.getLogger(PigletStatusEventDaoImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { 
		this.jdbcTemplate = jdbcTemplate;
	} 
	
	/**
	 * To add PigletStatusEvent
	 */
	@Override
	public int addPigletStatusEvent(final PigletStatusEvent pigletStatusEvent)
			throws SQLException, DuplicateKeyException {
		final String Qry = "insert into pigtrax.\"PigletStatus\"(\"id_PigInfo\", \"id_PigletStatusEventType\", "
				+ "\"eventDateTime\", \"numberOfPigs\", \"weightInKgs\", \"eventReason\", \"remarks\", \"sowCondition\", "
				+ "\"weanGroupId\", \"lastUpdated\", \"userUpdated\", \"fosterFrom\", \"fosterTo\", "
				+ "\"id_FarrowEvent\", \"id_fosterFarrowEvent\", \"id_GroupEvent\", \"id_MortalityReasonType\",\"id_Pen\",\"id_Premise\") "
				+ "values(?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?, ?,?,?,?,?)";
		 
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setObject(1, pigletStatusEvent.getPigInfoId(), java.sql.Types.INTEGER);
	    	            ps.setObject(2, pigletStatusEvent.getPigletStatusEventTypeId(), java.sql.Types.INTEGER);
	    	            ps.setObject(3, new java.sql.Date(pigletStatusEvent.getEventDateTime().getTime()), java.sql.Types.DATE);
	    	            ps.setInt(4,  pigletStatusEvent.getNumberOfPigs());
	    	            ps.setObject(5,  pigletStatusEvent.getWeightInKgs(),java.sql.Types.DOUBLE);
	    	            ps.setString(6,  pigletStatusEvent.getEventReason());
	    	            ps.setString(7,  pigletStatusEvent.getRemarks());
	    	            ps.setObject(8,  pigletStatusEvent.getSowCondition(), java.sql.Types.INTEGER);
	    	            ps.setString(9, pigletStatusEvent.getWeanGroupId());
	    	            ps.setString(10, pigletStatusEvent.getUserUpdated());
	    	            ps.setObject(11, pigletStatusEvent.getFosterFrom(), java.sql.Types.INTEGER);
	    	            ps.setObject(12, pigletStatusEvent.getFosterTo(), java.sql.Types.INTEGER);
	    	            ps.setObject(13,  pigletStatusEvent.getFarrowEventId(), java.sql.Types.INTEGER);
	    	            ps.setObject(14,  pigletStatusEvent.getFosterFarrowEventId(), java.sql.Types.INTEGER);
	    	            if(pigletStatusEvent.getGroupEventId() != null && pigletStatusEvent.getGroupEventId() !=0)
	    	            	ps.setObject(15,  pigletStatusEvent.getGroupEventId(), java.sql.Types.INTEGER);
	    	            else
	    	            	ps.setNull(15, java.sql.Types.INTEGER);
	    	            if(pigletStatusEvent.getMortalityReasonTypeId() != null && pigletStatusEvent.getMortalityReasonTypeId() !=0)
	    	            	ps.setObject(16,  pigletStatusEvent.getMortalityReasonTypeId(), java.sql.Types.INTEGER);
	    	            else
	    	            	ps.setNull(16, java.sql.Types.INTEGER);
	    	            if(pigletStatusEvent.getPenId() != null && pigletStatusEvent.getPenId() !=0)
	    	            	ps.setObject(17,  pigletStatusEvent.getPenId(), java.sql.Types.INTEGER);
	    	            else
	    	            	ps.setNull(17, java.sql.Types.INTEGER);
	    	            
	    	            if(pigletStatusEvent.getPremiseId() != null && pigletStatusEvent.getPremiseId() !=0)
	    	            	ps.setObject(18, pigletStatusEvent.getPremiseId(), java.sql.Types.INTEGER);
	    	            else
	    	            	ps.setNull(18, java.sql.Types.INTEGER);
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}	
	
	
	/**
	 * To get the details of a given pigletEventStatusId
	 */
	
	@Override
	public PigletStatusEvent getPigletStatusEventInformation(
			final Integer pigletStatusEventId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\", "
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\", "
		   		+ "PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\", PSE.\"id_Pen\",\"id_Premise\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE where PSE.\"id\" = ? ";
		
		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, pigletStatusEventId);
 			}}, new PigletStatusEventMapper());

		if(pigletStatusEventList != null && pigletStatusEventList.size() > 0)
			 return pigletStatusEventList.get(0);
		return null;
	}
	
	/**
	 * To get the list of PigletStatus events of a given farrowId and companyId
	 */
	
	private List<PigletStatusEvent> getPigletStatusEventsByPigId(final String pigId,
			final Integer companyId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\", "
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\", "
		   		+ "PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\", PSE.\"id_Pen\",PSE.\"id_Premise\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE "
		   		+ "JOIN pigtrax.\"PigInfo\" PI on PSE.\"id_PigInfo\" = PI.\"id\" "
		   		+ " WHERE PI.\"pigId\" = ? and PI.\"id_Company\" = ? and PSE.\"id_PigletStatusEventType\" <> ? order by PSE.\"id_FarrowEvent\" ";
 		
 		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, pigId);
 				ps.setInt(2, companyId);
 				ps.setInt(3, PigletStatusEventType.FosterIn.getTypeCode());
 			}}, new PigletStatusEventMapper());

		return pigletStatusEventList;
	}
	
	
	/**
	 * To get the list of PigletStatus events of a given farrowId and companyId
	 */
	
	private List<PigletStatusEvent> getPigletStatusEventsByPigId(final String pigId,
			final Integer companyId, final Integer premiseId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\", "
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\", "
		   		+ "PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\", PSE.\"id_Pen\",PSE.\"id_Premise\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE "
		   		+ "JOIN pigtrax.\"PigInfo\" PI on PSE.\"id_PigInfo\" = PI.\"id\" "
		   		+ " WHERE PI.\"pigId\" = ? and PI.\"id_Company\" = ? and PSE.\"id_PigletStatusEventType\" <> ? and PSE.\"id_Premise\" = ? order by PSE.\"id_FarrowEvent\" ";
 		
 		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, pigId);
 				ps.setInt(2, companyId); 				
 				ps.setInt(3, PigletStatusEventType.FosterIn.getTypeCode());
 				ps.setInt(4, premiseId);
 			}}, new PigletStatusEventMapper());

		return pigletStatusEventList;
	}
	
	
	
	/**
	 * To get the list of PigletStatus events of a given tattooId and companyId
	 */

	private List<PigletStatusEvent> getPigletStatusEventsByTattooId(final String tattooId,
			final Integer companyId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\","
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\", "
		   		+ "PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\", PSE.\"id_Pen\",PSE.\"id_Premise\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE "
		   		+ "JOIN pigtrax.\"FarrowEvent\" FE ON PSE.\"id_FarrowEvent\" = FE.\"id\" "
		   		+ "JOIN pigtrax.\"PregnancyEvent\" PE on FE.\"id_PregnancyEvent\" = PE.\"id\" "
		   		+ "JOIN pigtrax.\"PigInfo\" PI on PE.\"id_PigInfo\" = PI.\"id\" "
		   		+ " WHERE PI.\"tattoo\" = ? and PI.\"id_Company\" = ? and PSE.\"id_PigletStatusEventType\" <> ?  order by PSE.\"id_FarrowEvent\" ";
 		
 		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, tattooId);
 				ps.setInt(2, companyId);
 				ps.setInt(3, PigletStatusEventType.FosterIn.getTypeCode());
 			}}, new PigletStatusEventMapper());

		return pigletStatusEventList;
	}
	
	/**
	 * To get the list of PigletStatus events of a given tattooId and companyId
	 */

	private List<PigletStatusEvent> getPigletStatusEventsByTattooId(final String tattooId,
			final Integer companyId, final Integer premiseId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\","
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\", "
		   		+ "PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\", PSE.\"id_Pen\",PSE.\"id_Premise\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE "
		   		+ "JOIN pigtrax.\"FarrowEvent\" FE ON PSE.\"id_FarrowEvent\" = FE.\"id\" "
		   		+ "JOIN pigtrax.\"PregnancyEvent\" PE on FE.\"id_PregnancyEvent\" = PE.\"id\" "
		   		+ "JOIN pigtrax.\"PigInfo\" PI on PE.\"id_PigInfo\" = PI.\"id\" "
		   		+ " WHERE PI.\"tattoo\" = ? and PI.\"id_Company\" = ? and PSE.\"id_PigletStatusEventType\" <> ? and PSE.\"id_Premise\" = ?  order by PSE.\"id_FarrowEvent\" ";
 		
 		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, tattooId);
 				ps.setInt(2, companyId);
 				ps.setInt(3, PigletStatusEventType.FosterIn.getTypeCode());
 				ps.setInt(4 , premiseId);
 			}}, new PigletStatusEventMapper());

		return pigletStatusEventList;
	}
	
	
	
	/**
	 * Delete the breeding event information based for the given id
	 */	
	@Override
	public void deletePigletStatusEvent(final Integer id) throws SQLException {
		final String qry = "delete from pigtrax.\"PigletStatus\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
	}
	 
	@Override
	public List<PigletStatusEvent> getPigletStatusEvents(String searchText, String searchOption,
			Integer companyId) {
		List<PigletStatusEvent> pigletStatusEventList = null;	   
		   if(searchOption ==null) searchOption = "pigId";
		   else if("PIGId".equalsIgnoreCase(searchOption))
			   pigletStatusEventList = getPigletStatusEventsByPigId(searchText, companyId);
		   else if("TATTOOID".equalsIgnoreCase(searchOption))
			   pigletStatusEventList = getPigletStatusEventsByTattooId(searchText, companyId);
		  return pigletStatusEventList; 
	}
	
	
	@Override
	public List<PigletStatusEvent> getPigletStatusEvents(String searchText, String searchOption,
			Integer companyId, Integer premiseId) {
		List<PigletStatusEvent> pigletStatusEventList = null;	   
		   if(searchOption ==null) searchOption = "pigId";
		   else if("PIGId".equalsIgnoreCase(searchOption))
			   pigletStatusEventList = getPigletStatusEventsByPigId(searchText, companyId, premiseId);
		   else if("TATTOOID".equalsIgnoreCase(searchOption)) 
			   pigletStatusEventList = getPigletStatusEventsByTattooId(searchText, companyId, premiseId);
		  return pigletStatusEventList; 
	}
	
	private static final class PigletStatusEventMapper implements RowMapper<PigletStatusEvent> {
		public PigletStatusEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			PigletStatusEvent pigletStatusEvent = new PigletStatusEvent();
			pigletStatusEvent.setId(rs.getInt("id"));
			pigletStatusEvent.setPigInfoId(rs.getInt("id_PigInfo"));
			pigletStatusEvent.setFosterFrom(rs.getInt("fosterFrom"));
			pigletStatusEvent.setFosterTo(rs.getInt("fosterTo"));
			pigletStatusEvent.setPigletStatusEventTypeId(rs.getInt("id_PigletStatusEventType")); 
			pigletStatusEvent.setEventDateTime(rs.getDate("eventDateTime"));
			pigletStatusEvent.setNumberOfPigs(rs.getInt("numberOfPigs"));
			pigletStatusEvent.setWeightInKgs(rs.getDouble("weightInKgs"));
			pigletStatusEvent.setEventReason(rs.getString("eventReason"));
			pigletStatusEvent.setRemarks(rs.getString("remarks"));
			pigletStatusEvent.setSowCondition(rs.getInt("sowCondition"));
			pigletStatusEvent.setWeanGroupId(rs.getString("weanGroupId"));
			pigletStatusEvent.setLastUpdated(rs.getDate("lastUpdated"));
			pigletStatusEvent.setUserUpdated(rs.getString("userUpdated"));
			pigletStatusEvent.setFarrowEventId(rs.getInt("id_FarrowEvent"));
			pigletStatusEvent.setFosterFarrowEventId(rs.getInt("id_fosterFarrowEvent"));
			pigletStatusEvent.setGroupEventId(rs.getInt("id_GroupEvent"));
			pigletStatusEvent.setMortalityReasonTypeId(rs.getInt("id_MortalityReasonType"));
			pigletStatusEvent.setPenId(rs.getInt("id_Pen"));
			pigletStatusEvent.setPremiseId(rs.getInt("id_Premise"));
			return pigletStatusEvent;
		}
	}

	
	
	/**
	 * To retrieve the fosterIn Events for a given pigInfoId
	 */ 
	@Override
	public List<PigletStatusEvent> getFosterInRecords(final String pigId, final Integer companyId, final Integer farrowEventId) { 
		// TODO Auto-generated method stub
		String qry = "SELECT PS.\"id\", PS.\"id_PigInfo\", PS.\"fosterFrom\", PS.\"fosterTo\", PS.\"id_PigletStatusEventType\", "+ 
				"PS.\"eventDateTime\", PS.\"numberOfPigs\", PS.\"weightInKgs\", PS.\"eventReason\", "+ 
				"PS.\"remarks\", PS.\"sowCondition\", PS.\"weanGroupId\", PS.\"lastUpdated\", PS.\"userUpdated\", "+ 
				"PS.\"id_FarrowEvent\", PS.\"id_fosterFarrowEvent\", PS.\"id_GroupEvent\", PS.\"id_MortalityReasonType\", PS.\"id_Pen\",PS.\"id_Premise\" "
				+ " FROM pigtrax.\"PigletStatus\" PS "
				+ "JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where PI.\"pigId\" = ? "
				+ "and PS.\"id_PigletStatusEventType\" = ? and PI.\"id_Company\" = ? and PS.\"id_FarrowEvent\" = ?";
		
		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, pigId);
 				ps.setInt(2, PigletStatusEventType.FosterIn.getTypeCode());
 				ps.setInt(3, companyId); 
 				ps.setInt(4, farrowEventId);
 			}}, new PigletStatusEventMapper());
		return pigletStatusEventList; 
	}
	
	
	/**
	 * To get the list of PigletStatus events of a given farrowEventId
	 */

	public List<PigletStatusEvent> getPigletStatusEventsByFarrowEventId(final Integer  farrowEventId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\","
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\","
		   		+ " PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\", PSE.\"id_Pen\",PSE.\"id_Premise\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE "
		   		+ " WHERE PSE.\"id_FarrowEvent\" = ? and PSE.\"id_PigletStatusEventType\" <> ? ";
 		
 		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, farrowEventId);
 				ps.setInt(2, PigletStatusEventType.FosterIn.getTypeCode());
 			}}, new PigletStatusEventMapper());

		return pigletStatusEventList;
	}
	
	
	/**
	 * To get the list of PigletStatus events of a given farrowEventId
	 */

	public List<PigletStatusEvent> getPigletStatusEventsByFarrowEventId(final Integer  farrowEventId, final Integer pigletStatusEventTypeId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\","
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\","
		   		+ " PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\", PSE.\"id_Pen\",PSE.\"id_Premise\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE "
		   		+ " WHERE PSE.\"id_FarrowEvent\" = ? and PSE.\"id_PigletStatusEventType\" = ? ";
 		
 		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, farrowEventId);
 				//ps.setInt(2, PigletStatusEventType.FosterIn.getTypeCode());
 				ps.setInt(2, pigletStatusEventTypeId);
 			}}, new PigletStatusEventMapper());

		return pigletStatusEventList;
	}
	
	/***
	 * Delete the piglet status events for a given farrow event Id
	 */
	@Override
	public void deletePigletStatusEventsByFarrowId(final Integer pigInfoId, final Integer farrowEventId)
			throws SQLException {
		//final String qry = "delete from pigtrax.\"PigletStatus\" where \"id_FarrowEvent\" = ? or \"id_fosterFarrowEvent\" = ?";
		
		final String qry = "delete from pigtrax.\"PigletStatus\" where "
				+ "(\"id_FarrowEvent\" = ? and \"id_PigletStatusEventType\" <> ?) or "
				+ "(\"id_fosterFarrowEvent\" = ? and \"id_PigletStatusEventType\" = ? and \"id_PigInfo\" <> ?)";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, farrowEventId);
				ps.setInt(2, PigletStatusEventType.FosterIn.getTypeCode());
				ps.setInt(3, farrowEventId); 
				ps.setInt(4, PigletStatusEventType.FosterIn.getTypeCode());
				ps.setInt(5, pigInfoId); 
			}
		});
	}
	
	@Override
	public PigletStatusEvent getFosterInRecord(final Integer farrowEventId)
			throws SQLException {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\","
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\","
		   		+ " PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\", PSE.\"id_Pen\",PSE.\"id_Premise\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE "
		   		+ " WHERE PSE.\"id_fosterFarrowEvent\" = ? ";
 		
 		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, farrowEventId);
 			}}, new PigletStatusEventMapper());
 		if(pigletStatusEventList != null && pigletStatusEventList.size() > 0)
 			return pigletStatusEventList.get(0);
 		return null;
	}
	
	/**
	 * To get the list of PigletStatus Ferrow ID count Weavn  farrowId and companyId
	 */
	@Override
	public Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRange(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		
		//String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"id_PigletStatusEventType\" = ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ?";
		
		String qry = "select count(PS.\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" PS JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" "+
		" where PS.\"id_PigletStatusEventType\" = ? and PS.\"eventDateTime\" >= ? and PS.\"eventDateTime\" <= ? and  PI.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 			
 				ps.setInt(1, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 				ps.setInt(4, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the list of PigletStatus Ferrow ID count Weavn  farrowId and companyId
	 */
	@Override
	public Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		//String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where  \"id_PigletStatusEventType\" = ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ? and \"numberOfPigs\" >12 ";
		String qry = "select count(PS.\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" PS JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" "+
				" where PS.\"id_PigletStatusEventType\" = ? and PS.\"eventDateTime\" >= ? and PS.\"eventDateTime\" <= ? and PS.\"numberOfPigs\" >12 and  PI.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 			
 				ps.setInt(1, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 				ps.setInt(4, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the total pigs weavned count companyId
	 */
	@Override
	public Integer getTotalPigsWeavend(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		//String qry = "select sum(\"numberOfPigs\") from pigtrax.\"PigletStatus\" where \"id_PigletStatusEventType\" = ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ?  ";
		
		String qry = "select sum(\"numberOfPigs\") from pigtrax.\"PigletStatus\" PS JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" "+
				" where PS.\"id_PigletStatusEventType\" = ? and PS.\"eventDateTime\" >= ? and PS.\"eventDateTime\" <= ? and PI.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}


 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(4, companyId);
 				ps.setInt(1, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the list of PigletStatus Ferrow ID count Weavn  farrowId and companyId
	 */
	@Override
	public Integer getPigletStatusEventsFerrowIdWeavnAndFosterInAndOut(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
	
	//	String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"eventDateTime\" >= ? and \"eventDateTime\" <= ? " 
	//		      +" and (\"id_PigletStatusEventType\" = ? or \"id_PigletStatusEventType\" = ? or \"id_PigletStatusEventType\" = ?)";
		
		String qry = "select count(PS.\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" PS JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where PS.\"eventDateTime\" >= ? and PS.\"eventDateTime\" <= ? " 
						      +" and (PS.\"id_PigletStatusEventType\" = ? or PS.\"id_PigletStatusEventType\" = ? or PS.\"id_PigletStatusEventType\" = ?) and PI.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				
 				ps.setInt(6, companyId); 				
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, PigletStatusEventType.Wean.getTypeCode());
 				ps.setInt(4, PigletStatusEventType.FosterIn.getTypeCode());
 				ps.setInt(5, PigletStatusEventType.FosterOut.getTypeCode());
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the list of PigletStatus Ferrow ID count Weavn  farrowId and companyId
	 */
	@Override
	public Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithWeight(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		
		//String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"id_PigletStatusEventType\" = ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ? and \"weightInKgs\" !=0 ";
		
		String qry = "select count(PS.\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" PS JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where PS.\"id_PigletStatusEventType\" = ? and PS.\"eventDateTime\" >= ? and PS.\"eventDateTime\" <= ? and PS.\"weightInKgs\" !=0 and PI.\"id_Company\" = ?";

		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				
 				ps.setInt(1, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 				ps.setInt(4, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the total pigs weavned count companyId with weight
	 */
	@Override
	public Integer getTotalPigsWeavendWithWeight(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		String qry = "select sum(PS.\"numberOfPigs\") from pigtrax.\"PigletStatus\" PS JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where PS.\"id_PigletStatusEventType\" = ? and PS.\"eventDateTime\" >= ? and PS.\"eventDateTime\" <= ? and PS.\"weightInKgs\" !=0 and PI.\"id_Company\" = ? ";

		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 				ps.setInt(4, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}

	/**
	 * To get the total pigs weavned weight
	 */
	@Override
	public Integer getTotalPigsWeight(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		//String qry = "select sum(\"numberOfPigs\") from pigtrax.\"PigletStatus\" where \"id_Company\" = ? and \"id_PigletStatusEventType\" <> ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ?  ";
		String qry = "select sum(PS.\"weightInKgs\") from pigtrax.\"PigletStatus\" PS JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where PS.\"id_PigletStatusEventType\" = ? and PS.\"eventDateTime\" >= ? and PS.\"eventDateTime\" <= ? and PI.\"id_Company\" = ? ";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				
 				ps.setInt(4, companyId);
 				ps.setInt(1, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the list of PigletStatus Ferrow ID count Weavn  farrowId and companyId
	 */
	@Override
	public Integer getPigletStatusEventsFerrowIdForWeavnAndDateRange(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		
		String qry = "select count(FE.\"id_PigInfo\") from pigtrax.\"FarrowEvent\" FE "  
				 + " JOIN pigtrax.\"PigletStatus\" PS ON FE.\"id_PigInfo\" = PS.\"id_PigInfo\" "
				 + " JOIN pigtrax.\"PigInfo\" PI ON FE.\"id_PigInfo\" = PI.\"id\" "
				 + "  where DATE_PART('day', ?::timestamp - FE.\"farrowDateTime\"::timestamp) < 17 "
				 + " and PS.\"eventDateTime\" :: date BETWEEN ? and ? "
				 + " and PI.\"id_Company\" = ? and PS.\"id_PigletStatusEventType\" = ? ";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(5, PigletStatusEventType.Wean.getTypeCode());
 				ps.setInt(4, companyId);
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 				ps.setDate(1, end);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the count of piginfo id from farrow with parity 1 in piginfo
	 */
	@Override
	public Integer getCountPifIngoIdFromFarrowWithParityOneInPigInfo(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = "SELECT count(PS.\"id_PigInfo\") FROM pigtrax.\"FarrowEvent\" PS " +
				 " JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where PI.\"parity\" = 1 " +
				" and PS.\"farrowDateTime\" >= ? and PS.\"farrowDateTime\" <= ? and PI.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	
	/**
	 * To get the sum of parity of piginfo id from piginfo for ferrow
	 */
	@Override
	public Integer getCountParityOfPigIngoIdFromFarrow(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = "SELECT sum(PI.\"parity\") FROM pigtrax.\"PigInfo\" PI "+
				 "JOIN pigtrax.\"FarrowEvent\" FE ON PI.\"id\" = FE.\"id_PigInfo\" "+
				" and FE.\"farrowDateTime\" >= ? and  FE.\"farrowDateTime\" <= ? and PI.\"id_Company\" = ?"; 
		
		if(premisesId !=0)
		{
			qry = qry+ " and FE.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the sum of parity of piginfo id from piginfo for ferrow
	 */
	@Override
	public Integer getSumOfDiffOfFerrowAndBreedingDate(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry =  "SELECT sum(DATE_PART('day', FE.\"farrowDateTime\"::timestamp - BE.\"serviceStartDate\"::timestamp )) FROM pigtrax.\"FarrowEvent\" FE " +
		 " JOIN pigtrax.\"BreedingEvent\" BE ON FE.\"id_PigInfo\" = BE.\"id_PigInfo\" and FE.\"farrowDateTime\" >= ? and  FE.\"farrowDateTime\" <= ? " +
				" and BE.\"serviceStartDate\" >= ? and BE.\"serviceStartDate\" <= ? ";
		
		if(premisesId !=0)
		{
			qry = qry+ " and FE.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setDate(3, start);
 				ps.setDate(4, end);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the count of pigid from ferrow
	 */
	@Override
	public Integer getPiGIdFromFerrow(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = "SELECT count(PS.\"id_PigInfo\") FROM pigtrax.\"FarrowEvent\" PS " +
				 " JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where " +
				" PS.\"farrowDateTime\" >= ? and PS.\"farrowDateTime\" <= ? and PI.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the count of pigid from breeding
	 */
	@Override
	public Integer getPiGIdFromBreeding(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = "SELECT count(PS.\"id_PigInfo\") FROM pigtrax.\"BreedingEvent\" PS " +
				 " JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where " +
				" PS.\"serviceStartDate\" >= ? and PS.\"serviceStartDate\" <= ? and PI.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the count of first service from breeding
	 */
	@Override
	public Integer getCountOfFirstService(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = "select count(PI.\"id\") from pigtrax.\"PigInfo\" PI "+
					" JOIN pigtrax.\"BreedingEvent\" BE ON PI.\"id\" = BE.\"id_PigInfo\" "+ 
				" where PI.\"parity\" = 1 and PI.\"id_Company\" = ? and BE.\"serviceStartDate\" >= ? and  BE.\"serviceStartDate\" <= ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and BE.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, companyId);
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the count repeate Service of pigid from breeding
	 */
	@Override
	public Integer getCountOfRepeateService(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = "select count(PI.\"id\") from pigtrax.\"PigInfo\" PI "+
				" JOIN pigtrax.\"BreedingEvent\" BE ON PI.\"id\" = BE.\"id_PigInfo\" "+ 
			" where PI.\"parity\" > 1 and PI.\"id_Company\" = ? and BE.\"serviceStartDate\" >= ? and  BE.\"serviceStartDate\" <= ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and BE.\"id_Premise\" = " + premisesId;
		}
	
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, companyId);
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the count of breeding with mating one
	 */
	@Override
	public Integer getCountOfServiceWithMatingGreaterThanOne(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = " select count(MD.\"id\"), BE.\"id\", BE.\"serviceStartDate\" from pigtrax.\"MatingDetails\" MD " +
						" JOIN pigtrax.\"BreedingEvent\" BE on BE.\"id\" = MD.\"id_BreedingEvent\" "+ 	
						" JOIN pigtrax.\"PigInfo\" PI ON BE.\"id_PigInfo\" = PI.\"id\"	" +
						" where PI.\"id_Company\" = ?  and BE.\"serviceStartDate\" :: date between ? and ? group by BE.\"id\" 	having count(MD.\"id\") > 1";
		
		if(premisesId !=0)
		{
			qry = qry+ " and BE.\"id_Premise\" = " + premisesId;
		}
	
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, companyId);
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});
 		
 		if(pigletStatusEventList!=null && !pigletStatusEventList.isEmpty())
 			return pigletStatusEventList.get(0);
 		else
 			return 0;
	}
	
	/**
	 * To get the count of breeding with mating one
	 */
	@Override
	public Integer getCountOfMating(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = " select count(MD.\"id\"), BE.\"id\", BE.\"serviceStartDate\" from pigtrax.\"MatingDetails\" MD " +
						" JOIN pigtrax.\"BreedingEvent\" BE on BE.\"id\" = MD.\"id_BreedingEvent\" "+
						" JOIN pigtrax.\"PigInfo\" PI ON BE.\"id_PigInfo\" = PI.\"id\"	" +
						" where BE.\"serviceStartDate\" :: date between ? and ? and PI.\"id_Company\" = ?  group by BE.\"id\" ";
	
		if(premisesId !=0)
		{
			qry = qry+ " and BE.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

 		if(pigletStatusEventList!=null && !pigletStatusEventList.isEmpty())
 			return pigletStatusEventList.get(0);
 		else
 			return 0;
	}
	
	/**
	 * To get the count of breeding with mating one
	 */
	@Override
	public Integer getCountOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = "select count(PI.\"id_PigInfo\") from pigtrax.\"PigletStatus\" PI "+ 
					 " JOIN pigtrax.\"BreedingEvent\" BE ON PI.\"id_PigInfo\" = BE.\"id_PigInfo\" "+
					 " JOIN pigtrax.\"PigInfo\" PS ON BE.\"id_PigInfo\" = PS.\"id\"	" +
					 " where DATE_PART('day', PI.\"eventDateTime\"::timestamp - BE.\"serviceStartDate\"::timestamp)<7 " +
					 " and BE.\"serviceStartDate\" :: date between ? and ? and PI.\"eventDateTime\" :: date between ? and ? and PS.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PI.\"id_Premise\" = " + premisesId;
		}
	
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setDate(3, start);
 				ps.setDate(4, end);
 				ps.setInt(5, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

 		if(pigletStatusEventList!=null && !pigletStatusEventList.isEmpty())
 			return pigletStatusEventList.get(0);
 		else
 			return 0;
	}
	
	
	/**
	 * To get the list of PigletStatus PigID ID count for Weavn and companyId
	 */
	@Override
	public Integer getPigletStatusEventsPigIdCountForWeavnAndDateRange(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		
		String qry = "select count(\"id_PigInfo\") from pigtrax.\"PigletStatus\" PS JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where  " +
					 " PS.\"id_PigletStatusEventType\" = ? and PS.\"eventDateTime\" >= ? and PS.\"eventDateTime\" <= ? and PI.\"id_Company\" = ? ";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}
				  
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				
 				ps.setInt(1, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 				ps.setInt(4, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	
	/**
	 * To get the list of PigletStatus PigID ID count for Weavn and companyId
	 */
	@Override
	public Integer getNumberOfDaysBetweenWeanAndServiceDate(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		
	/*	String qry = "select count(\"id_PigInfo\") from pigtrax.\"PigletStatus\" PS JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where  " +
					 " PS.\"id_PigletStatusEventType\" = ? and PS.\"eventDateTime\" >= ? and PS.\"eventDateTime\" <= ? and PI.\"id_Company\" = ? ";
		*/
		String qry = " select sum(DATE_PART('day', PS.\"eventDateTime\"::timestamp - BE.\"serviceStartDate\"::timestamp))  from "+
				" pigtrax.\"PigletStatus\" PS, pigtrax.\"BreedingEvent\" BE where PS.\"id_PigInfo\" = BE.\"id_PigInfo\" and "+ 
				" BE.\"id_PigInfo\" in (Select \"id\" from pigtrax.\"PigInfo\" where \"parity\" = 1 and \"id_Company\"=?) and "+
				" BE.\"serviceStartDate\" :: date between ? and ?"; 
		
		if(premisesId !=0)
		{
			qry = qry+ " and BE.\"id_Premise\" = " + premisesId;
		}
				  
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				
 				ps.setInt(1, companyId);
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 				
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	 
	/**
	 * To get the list of PigletStatus PigID ID count for Weavn and companyId
	 */
	@Override
	public Integer getPairtyOfServedFemals(final Date start,final Date end,
			final Integer companyId,Integer premisesId) {
		
		String qry = " select sum(\"parity\") from pigtrax.\"PigInfo\" where \"id\" " + 
					" in (select \"id_PigInfo\" from pigtrax.\"BreedingEvent\" where \"serviceStartDate\" :: date between ? and ?) and \"id_Company\"=? ";
		
		if(premisesId !=0)
		{
			qry = qry+ " and BE.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, companyId);
 				
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the count of piginfo id from piginfo with parity 1 in
	 */
	@Override
	public Integer getCountPifIngoIdWithParityOneInPigInfo(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = "SELECT count(PI.\"id\") FROM pigtrax.\"PigInfo\" PI where PI.\"parity\" = 1 " +
				" and PI.\"entryDate\" >= ? and PI.\"entryDate\" <= ? and PI.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PI.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	/**
	 * To get the sum of date difference in Service and Entry date
	 */
	@Override
	public Integer getSumOfDateDiffBetweenServiceAndEntryDate(final Date start,final Date end, final Integer companyId,Integer premisesId) {
		
		String qry = " select sum(DATE_PART('day', BE.\"serviceStartDate\"::timestamp - PI.\"entryDate\"::timestamp))  from "+ 
		" pigtrax.\"PigInfo\" PI, pigtrax.\"BreedingEvent\" BE where PI.\"id\" = BE.\"id_PigInfo\" and PI.\"parity\" = 0 and " + 
				" BE.\"serviceStartDate\" :: date between ? and ? and PI.\"id_Company\"=? ";
		
		
		if(premisesId !=0)
		{
			qry = qry+ " and BE.\"id_Premise\" = " + premisesId;
		}

		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	
	/***
	 * Delete the piglet status events for a given farrow event Id
	 */
	@Override
	public void deletePigletStatusEventsByFarrowId(final Integer pigInfoId, final Integer farrowEventId, final Integer pigletStatusEventType)
			throws SQLException {
		//final String qry = "delete from pigtrax.\"PigletStatus\" where \"id_FarrowEvent\" = ? or \"id_fosterFarrowEvent\" = ?";
		
		StringBuffer buffer = new StringBuffer();
	
		if(pigletStatusEventType == 3 || pigletStatusEventType == 4 || pigletStatusEventType == 2)
		{
			buffer.append("delete from pigtrax.\"PigletStatus\" where "
					+ "\"id_FarrowEvent\" = ? and \"id_PigletStatusEventType\" = ? and \"id_PigInfo\" = ?");
			
		}
		else if(pigletStatusEventType == 1)
		{
			buffer.append("delete from pigtrax.\"PigletStatus\" where "
					+ "\"id_fosterFarrowEvent\" = ? and \"id_PigletStatusEventType\" = ? and \"fosterFrom\" = ?");
		}
		
		
		final String qry = buffer.toString();
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, farrowEventId);
				ps.setInt(2, pigletStatusEventType);
				ps.setInt(3, pigInfoId); 
			}
		});
	}
	
	/**
	 * To get the count of pigid from ferrow
	 */
	@Override
	public Integer getTotalWeekBornPiglet(final Date start,final Date end, final Integer companyId, final Integer premisesId) {
		
		String qry = "SELECT sum(PS.\"weakBorns\") FROM pigtrax.\"FarrowEvent\" PS " +
				 " JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where " +
				" PS.\"farrowDateTime\" >= ? and PS.\"farrowDateTime\" <= ? and PI.\"id_Company\" = ?";
		
		if(premisesId !=0)
		{
			qry = qry+ " and PS.\"id_Premise\" = " + premisesId;
		}
		
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setDate(1, start);
 				ps.setDate(2, end);
 				ps.setInt(3, companyId);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}
	
	@Override
	public Integer getLittersWithWeightOfLiveBorn(final Date startDate,
			final Date endDate, final Integer companyId,final Integer premisesId)  {
		String qry = "select count(FE.\"id\") from pigtrax.\"FarrowEvent\" FE  JOIN pigtrax.\"PigInfo\" PI ON FE.\"id_PigInfo\" = PI.\"id\" where PI.\"id_Company\" = ? and " +
			" FE.\"farrowDateTime\" >= ? and FE.\"farrowDateTime\" <= ? and FE.\"weightInKgs\" >0 ";
		
		if(premisesId !=0)
		{
			qry = qry+ " and FE.\"id_Premise\" = " + premisesId;
		}

		List<Integer> eventMasterList = jdbcTemplate.query(qry,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setDate(2, startDate);
						ps.setDate(3, endDate);
						ps.setInt(1, companyId);
					}
				}, new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt(1);
					}
				});

		return eventMasterList.get(0);
	}
	
	 @Override
		public int getConceptionRateAtPresumedPregnant(final Date startDate,  final Date endDate,final Integer companyId, final Integer difference, final Integer premisesId)
		{
			
			String qry = "select count(FE.\"id_PigInfo\") from pigtrax.\"FarrowEvent\" FE "
					+"	JOIN pigtrax.\"BreedingEvent\" BE ON FE.\"id_PigInfo\" = BE.\"id_PigInfo\" "
					+"	JOIN pigtrax.\"PregnancyEvent\" PE ON FE.\"id_PigInfo\" = PE.\"id_PigInfo\" "
					+"	JOIN pigtrax.\"PigInfo\" PI ON FE.\"id_PigInfo\" = PI.\"id\"  "
					+"	where DATE_PART('day', FE.\"farrowDateTime\"::timestamp - BE.\"serviceStartDate\"::timestamp) = ? "
					+"	and FE.\"farrowDateTime\" :: date between ? and ? "
					+"	and PI.\"id_Company\" = ? ";
			
			if(premisesId !=0)
			{
				qry = qry+ " and FE.\"id_Premise\" = " + premisesId;
			}
			
			List<Integer> eventMasterList = jdbcTemplate.query(qry,
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, difference);
							ps.setInt(4, companyId);
							ps.setDate(2, startDate);
							ps.setDate(3, endDate);
						}
					}, new RowMapper<Integer>() {
						public Integer mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getInt(1);
						}
					});

			return eventMasterList.get(0);
		}
	 
	 /**
		 * To get the sum of parity of piginfo id from piginfo for ferrow
		 */
		@Override
		public Integer getSumOfDiffOfFerrowAndWeanDate(final Date start,final Date end, final Integer companyId, final Integer premisesId) {
			
			String qry =  "SELECT sum(DATE_PART('day',  PS.\"eventDateTime\"::timestamp - FE.\"farrowDateTime\"::timestamp )) FROM pigtrax.\"PigletStatus\" PS "
					+ "	JOIN pigtrax.\"FarrowEvent\" FE  ON PS.\"id_PigInfo\" = FE.\"id_PigInfo\"  "
					+ "	JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" "
					+ "	where PS.\"eventDateTime\" >= ? and  PS.\"eventDateTime\" <= ? and PI.\"id_Company\" = ? and PS.\"id_PigletStatusEventType\" = ? ";
			
			if(premisesId !=0)
			{
				qry = qry+ " and FE.\"id_Premise\" = " + premisesId;
			}
			
	 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
	 			@Override
	 			public void setValues(PreparedStatement ps) throws SQLException {
	 				ps.setDate(1, start);
	 				ps.setDate(2, end);
	 				ps.setInt(3, companyId);
	 				ps.setInt(4, PigletStatusEventType.Wean.getTypeCode());
	 				
	 			}}, new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt(1);
					}
				});

			return pigletStatusEventList.get(0);
		}
	
	
}
