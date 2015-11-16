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
				+ "\"id_FarrowEvent\", \"id_fosterFarrowEvent\", \"id_GroupEvent\", \"id_MortalityReasonType\") "
				+ "values(?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?, ?,?,?)";
		 
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
	    	            ps.setObject(15,  pigletStatusEvent.getGroupEventId(), java.sql.Types.INTEGER);
	    	            ps.setObject(16,  pigletStatusEvent.getMortalityReasonTypeId(), java.sql.Types.INTEGER);
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
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\""
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\", "
		   		+ "PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\" "
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
		   		+ "PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\" "
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
	 * To get the list of PigletStatus events of a given tattooId and companyId
	 */

	private List<PigletStatusEvent> getPigletStatusEventsByTattooId(final String tattooId,
			final Integer companyId) {
		String qry = "select PSE.\"id\", PSE.\"id_PigInfo\", PSE.\"fosterFrom\", "
		   		+ "PSE.\"fosterTo\", PSE.\"id_PigletStatusEventType\", PSE.\"eventDateTime\", PSE.\"numberOfPigs\""
		   		+ ", PSE.\"weightInKgs\", PSE.\"eventReason\", PSE.\"remarks\", PSE.\"sowCondition\", PSE.\"weanGroupId\","
		   		+ " PSE.\"lastUpdated\",PSE.\"userUpdated\", PSE.\"id_FarrowEvent\", PSE.\"id_fosterFarrowEvent\", "
		   		+ "PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\" "
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
			pigletStatusEvent.setFosterFarrowEventId(rs.getInt("id_fosterFarrowEvent"));
			pigletStatusEvent.setGroupEventId(rs.getInt("id_GroupEvent"));
			pigletStatusEvent.setMortalityReasonTypeId(rs.getInt("id_MortalityReasonType"));
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
				"PS.\"id_FarrowEvent\", PS.\"id_fosterFarrowEvent\", PS.\"id_GroupEvent\", PS.\"id_MortalityReasonType\" "
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
		   		+ " PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\" "
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
		   		+ " PSE.\"id_GroupEvent\", PSE.\"id_MortalityReasonType\" "
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
			final Integer companyId) {
		//String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"id_Company\" = ? and \"id_PigletStatusEventType\" <> ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ?";
		
		String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"id_PigletStatusEventType\" = ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ?";

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				/*ps.setInt(1, companyId);
 				ps.setInt(2, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(3, start);
 				ps.setDate(4, end);*/
 				//ps.setInt(1, companyId);
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
	public Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(final Date start,final Date end,
			final Integer companyId) {
		//String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"id_Company\" = ? and \"id_PigletStatusEventType\" <> ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ? and \"numberOfPigs\" >12 ";
		String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where  \"id_PigletStatusEventType\" = ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ? and \"numberOfPigs\" >12 ";
 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				/*ps.setInt(1, companyId);
 				ps.setInt(2, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(3, start);
 				ps.setDate(4, end);*/
 				//ps.setInt(1, companyId);
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
	 * To get the total pigs weavned count companyId
	 */
	@Override
	public Integer getTotalPigsWeavend(final Date start,final Date end,
			final Integer companyId) {
		//String qry = "select sum(\"numberOfPigs\") from pigtrax.\"PigletStatus\" where \"id_Company\" = ? and \"id_PigletStatusEventType\" <> ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ?  ";
		String qry = "select sum(\"numberOfPigs\") from pigtrax.\"PigletStatus\" where \"id_PigletStatusEventType\" = ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ?  ";

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				/*ps.setInt(1, companyId);
 				ps.setInt(2, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(3, start);
 				ps.setDate(4, end);*/
 			//	ps.setInt(1, companyId);
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
			final Integer companyId) {
	//	String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"id_Company\" = ? and  \"eventDateTime\" >= ? and \"eventDateTime\" <= ? " 
	//			      +" and (\"id_PigletStatusEventType\" <> ? or \"id_PigletStatusEventType\" <> ? or \"id_PigletStatusEventType\" <> ?";

		String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"eventDateTime\" >= ? and \"eventDateTime\" <= ? " 
			      +" and (\"id_PigletStatusEventType\" = ? or \"id_PigletStatusEventType\" = ? or \"id_PigletStatusEventType\" = ?)";

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				/*ps.setInt(1, companyId); 				
 				ps.setDate(2, start);
 				ps.setDate(3, end);
 				ps.setInt(4, PigletStatusEventType.Wean.getTypeCode());
 				ps.setInt(5, PigletStatusEventType.FosterIn.getTypeCode());
 				ps.setInt(6, PigletStatusEventType.FosterOut.getTypeCode());*/
 			//	ps.setInt(1, companyId); 				
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
			final Integer companyId) {
		//String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"id_Company\" = ? and \"id_PigletStatusEventType\" <> ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ?";
		
		String qry = "select count(\"id_FarrowEvent\") from pigtrax.\"PigletStatus\" where \"id_PigletStatusEventType\" = ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ? and \"weightInKgs\" !=0 ";

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				/*ps.setInt(1, companyId);
 				ps.setInt(2, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(3, start);
 				ps.setDate(4, end);*/
 				//ps.setInt(1, companyId);
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
	 * To get the total pigs weavned count companyId with weight
	 */
	@Override
	public Integer getTotalPigsWeavendWithWeight(final Date start,final Date end,
			final Integer companyId) {
		//String qry = "select sum(\"numberOfPigs\") from pigtrax.\"PigletStatus\" where \"id_Company\" = ? and \"id_PigletStatusEventType\" <> ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ?  ";
		String qry = "select sum(\"numberOfPigs\") from pigtrax.\"PigletStatus\" where \"id_PigletStatusEventType\" = ? and \"eventDateTime\" >= ? and \"eventDateTime\" <= ? and \"weightInKgs\" !=0 ";

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				/*ps.setInt(1, companyId);
 				ps.setInt(2, PigletStatusEventType.Wean.getTypeCode());
 				ps.setDate(3, start);
 				ps.setDate(4, end);*/
 			//	ps.setInt(1, companyId);
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

	
}
