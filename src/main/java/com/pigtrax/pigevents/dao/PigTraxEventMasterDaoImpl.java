package com.pigtrax.pigevents.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;

@Repository
@Transactional
public class PigTraxEventMasterDaoImpl implements PigTraxEventMasterDao {
    
	private static final Logger logger = Logger.getLogger(PigInfoDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * Insert the entry event details of a pig info
	 * @param master
	 * @return
	 * @throws SQLException
	 */
	public int insertEntryEventDetails(final PigTraxEventMaster master) throws SQLException {
		String Qry = "insert into pigtrax.\"PigTraxEventMaster\"( \"eventTime\", \"id_PigInfo\", \"lastUpdated\", \"userUpdated\") "
				+ "values(current_timestamp,?,current_timestamp,?)";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
		
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, master.getPigInfoId());
				ps.setString(2, master.getUserUpdated());
			}
		});
		
	}
	
	/**
	 * update the breeding event details of a given pigInfoId
	 * @param breedingEvent
	 * @return
	 * @throws SQLException
	 */
	public int updateBreedingEventDetails(final BreedingEvent breedingEvent)
			throws SQLException {
		String qry = "update pigtrax.\"PigTraxEventMaster\" set \"eventTime\" = current_timestamp, \"id_BreedingEvent\" = ?, \"lastUpdated\"=current_timestamp where \"id_PigInfo\" = ?";
		return this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, breedingEvent.getId());
				ps.setInt(2, breedingEvent.getPigInfoKey());
			}
		});
	}
	
	/**
	 * update the pregnancy event details of a given pigInfoId
	 * @param PregnancyEvent
	 * @return
	 * @throws SQLException
	 */
	public int updatePregnancyEventDetails(final PregnancyEvent pregnancyEvent)
			throws SQLException {
		String qry = "update pigtrax.\"PigTraxEventMaster\" set \"eventTime\" = current_timestamp, \"id_PregnancyEvent\" = ?, \"lastUpdated\"=current_timestamp where \"id_PigInfo\" = ?";
		return this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, pregnancyEvent.getId());
				ps.setInt(2, pregnancyEvent.getPigInfoId());
			}
		});
	}
	
	
	
	
	public PigTraxEventMaster getEventMasterRecord(final Integer pigInfoKey) throws Exception
	{
		String qry = "select \"id\", \"eventTime\", \"id_GroupEvent\", \"id_BreedingEvent\", \"id_PregnancyEvent\", "
				+ "\"id_FarrowEvent\", \"id_PigletStatus\", \"lastUpdated\", \"userUpdated\", \"id_SaleEvent\", \"id_PigInfo\", \"id_FeedEvent\", \"id_RemovalEvent\" "
				+ " from pigtrax.\"PigTraxEventMaster\" where \"id_PigInfo\" = ?";
		
		List<PigTraxEventMaster> eventMasterList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoKey);
			}}, new PigTraxEventMasterMapper());

		if(eventMasterList != null && eventMasterList.size() > 0){
			return eventMasterList.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @author user
	 *
	 */
	private static final class PigTraxEventMasterMapper implements RowMapper<PigTraxEventMaster> {
		public PigTraxEventMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
			PigTraxEventMaster eventMaster = new PigTraxEventMaster();
			eventMaster.setId(rs.getInt("id"));
			eventMaster.setEventTime(rs.getDate("eventTime"));
			eventMaster.setGroupEventId(rs.getInt("id_GroupEvent"));
			eventMaster.setBreedingEventId(rs.getInt("id_BreedingEvent"));
			eventMaster.setPregnancyEventId(rs.getInt("id_PregnancyEvent"));
			eventMaster.setFarrowEventId(rs.getInt("id_FarrowEvent"));
			eventMaster.setPigletStatusId(rs.getInt("id_PigletStatus"));
			eventMaster.setLastUpdated(rs.getDate("lastUpdated"));
			eventMaster.setUserUpdated(rs.getString("userUpdated"));
			eventMaster.setSaleEventId(rs.getInt("id_SaleEvent"));
			eventMaster.setPigInfoId(rs.getInt("id_PigInfo"));
			eventMaster.setFeedEventId(rs.getInt("id_FeedEvent"));
			eventMaster.setRemovalEventId(rs.getInt("id_RemovalEvent"));
			return eventMaster;
		}
	}
}
