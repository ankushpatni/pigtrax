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
				+ "\"weanGroupId\", \"lastUpdated\", \"userUpdated\", \"fosterFrom\", \"fosterTo\", \"id_FarrowEvent\") "
				+ "values(?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?)";
		 
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
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}
	
	/**
	 * Update the details of pigletStatusEvent
	 */
	@Override
	public int updatePigletStatusEvent(final PigletStatusEvent pigletStatusEvent)
			throws SQLException, DuplicateKeyException {
		final String Qry = "update pigtrax.\"PigletStatus\" set  \"id_PigInfo\" = ?, \"id_PigletStatusEventType\" = ?, "
				+ "\"eventDateTime\" = ?, \"numberOfPigs\" = ?, \"weightInKgs\" = ?, \"eventReason\" = ?, \"remarks\" = ?, \"sowCondition\" = ?, "
				+ "\"weanGroupId\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"fosterFrom\" = ?, \"fosterTo\" = ?, \"id_FarrowEvent\" = ? "
				+ " where \"id\" = ?";
		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry);
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
	    	            ps.setInt(14, pigletStatusEvent.getId());
	    	            return ps;
	    	        }
	    	    });
		return 0;
	}
	
	/**
	 * To get the details of a given pigletEventStatusId
	 */
	
	@Override
	public PigletStatusEvent getPigletStatusEventInformation(
			final Integer pigletStatusEventId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\""
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\" "
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
	
	private List<PigletStatusEvent> getPigletStatusEventsByPigId(final String farrowId,
			final Integer companyId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\", "
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE "
		   		+ "JOIN pigtrax.\"FarrowEvent\" FE ON PSE.\"id_FarrowEvent\" = FE.\"id\" "
		   		+ "JOIN pigtrax.\"PregnancyEvent\" PE on FE.\"id_PregnancyEvent\" = PE.\"id\" "
		   		+ "JOIN pigtrax.\"PigInfo\" PI on PE.\"id_PigInfo\" = PI.\"id\" "
		   		+ " WHERE PI.\"pigId\" = ? and PI.\"id_Company\" = ? order by PSE.\"id_FarrowEvent\" ";
 		
 		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, farrowId);
 				ps.setInt(2, companyId);
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
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\" "
		   		+ " from pigtrax.\"PigletStatus\" PSE "
		   		+ "JOIN pigtrax.\"FarrowEvent\" FE ON PSE.\"id_FarrowEvent\" = FE.\"id\" "
		   		+ "JOIN pigtrax.\"PregnancyEvent\" PE on FE.\"id_PregnancyEvent\" = PE.\"id\" "
		   		+ "JOIN pigtrax.\"PigInfo\" PI on PE.\"id_PigInfo\" = PI.\"id\" "
		   		+ " WHERE PI.\"tattoo\" = ? and PI.\"id_Company\" = ? order by PSE.\"id_FarrowEvent\" ";
 		
 		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, tattooId);
 				ps.setInt(2, companyId);
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
			
			return pigletStatusEvent;
		}
	}
	
	/**
	 * To retrieve the fosterIn Events for a given pigInfoId
	 */
	@Override
	public List<PigletStatusEvent> getFosterInRecords(final String pigId, final Integer companyId) {
		// TODO Auto-generated method stub
		String qry = "SELECT PS.\"id\", PS.\"id_PigInfo\", PS.\"fosterFrom\", PS.\"fosterTo\", PS.\"id_PigletStatusEventType\", "+ 
				"PS.\"eventDateTime\", PS.\"numberOfPigs\", PS.\"weightInKgs\", PS.\"eventReason\", "+ 
				"PS.\"remarks\", PS.\"sowCondition\", PS.\"weanGroupId\", PS.\"lastUpdated\", PS.\"userUpdated\", "+ 
				"PS.\"id_FarrowEvent\"  FROM pigtrax.\"PigletStatus\" PS "
				+ "JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\" where PI.\"pigId\" = ? "
				+ "and PS.\"id_PigletStatusEventType\" = ? and PI.\"id_Company\" = ?";
		
		List<PigletStatusEvent> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setString(1, pigId);
 				ps.setInt(2, PigletStatusEventType.FosterIn.getTypeCode());
 				ps.setInt(3, companyId); 
 			}}, new PigletStatusEventMapper());

		return pigletStatusEventList;
	}
	
}
