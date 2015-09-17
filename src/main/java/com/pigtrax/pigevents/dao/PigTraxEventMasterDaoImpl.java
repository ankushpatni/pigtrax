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
		String Qry = "insert into pigtrax.\"PigTraxEventMaster\"( \"eventTime\", \"id_PigInfo\", \"lastUpdated\", \"userUpdated\", \"id_GroupEvent\", \"id_BreedingEvent\", \"id_PregnancyEvent\", \"id_FarrowEvent\", \"id_PigletStatus\", \"id_FeedEvent\", \"id_RemovalEvent\") "
				+ "values(?,?,current_timestamp,?,?,?,?,?,?,?,?)";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
		
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setDate(1, new java.sql.Date(master.getEventTime().getTime()));
				if(null != master.getPigInfoId())
					ps.setInt(2, master.getPigInfoId());
				else
					ps.setNull(2, java.sql.Types.INTEGER);
				ps.setString(3, master.getUserUpdated());
				ps.setObject(4, (master.getGroupEventId() != null)?master.getGroupEventId():null);
				ps.setObject(5, (master.getBreedingEventId() != null)?master.getBreedingEventId():null);
				ps.setObject(6, (master.getPregnancyEventId() != null)?master.getPregnancyEventId():null);
				ps.setObject(7, (master.getFarrowEventId() != null)?master.getFarrowEventId():null);
				ps.setObject(8, (master.getPigletStatusId() != null)?master.getPigletStatusId():null);
				ps.setObject(9, (master.getFeedEventId() != null)?master.getFeedEventId():null);
				ps.setObject(10, (master.getRemovalEventId() != null)?master.getRemovalEventId():null);
			}
		});
		
	}
	
	
	/**
	 * Insert the entry event details of a pig info
	 * @param master
	 * @return
	 * @throws SQLException
	 */
	public int updatePigletStatusEventMasterDetails(final PigTraxEventMaster master) throws SQLException {
		String Qry = "update pigtrax.\"PigTraxEventMaster\" set \"eventTime\" = ?, \"id_PigInfo\" = ?, \"lastUpdated\"= current_timestamp, "
				+ "\"userUpdated\" = ? where \"id_PigletStatus\" = ?";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
		
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setDate(1, new java.sql.Date(master.getEventTime().getTime()));
				ps.setInt(2, master.getPigInfoId());
				ps.setString(3, master.getUserUpdated());
				ps.setObject(4, (master.getPigletStatusId() != null)?master.getPigletStatusId():null);
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
	
	
	public List<PigTraxEventMaster> getEventMasterRecords(final Integer pigInfoKey) throws Exception
	{
		String qry = "select \"id\", \"eventTime\", \"id_GroupEvent\", \"id_BreedingEvent\", \"id_PregnancyEvent\", "
				+ "\"id_FarrowEvent\", \"id_PigletStatus\", \"lastUpdated\", \"userUpdated\", \"id_PigInfo\", \"id_FeedEvent\", \"id_RemovalEvent\" "
				+ " from pigtrax.\"PigTraxEventMaster\" where \"id_PigInfo\" = ? order by \"id\" desc ";
		
		List<PigTraxEventMaster> eventMasterList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoKey);
			}}, new PigTraxEventMasterMapper());

		return eventMasterList;
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
			eventMaster.setPigInfoId(rs.getInt("id_PigInfo"));
			eventMaster.setFeedEventId(rs.getInt("id_FeedEvent"));
			eventMaster.setRemovalEventId(rs.getInt("id_RemovalEvent"));
			return eventMaster;
		}
	}
	
	/**
	 * Delete the piglet status event entries for a given farrow event id
	 * @param farrowEventId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int deletePigletStatusEvents(final Integer farrowEventId)
			throws SQLException {
		final String qry = "delete from pigtrax.\"PigTraxEventMaster\" where \"id_PigletStatus\" IN "
				+ "(select \"id\" from pigtrax.\"PigletStatus\" where \"id_FarrowEvent\" = ? or \"id_fosterFarrowEvent\" = ?)";
				
		int rowsDeleted = this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, farrowEventId);
				ps.setInt(2, farrowEventId);
			}
		});
		return rowsDeleted;
	}
	
	 /**
	    * Delete a farrow events from master table based on the primary key id
	    */
	   @Override
		public void deleteFarrowEvent(final Integer farrowEventId)
				throws SQLException {
		   final String qry = "delete from pigtrax.\"PigTraxEventMaster\" where \"id_FarrowEvent\" = ? and \"id_PigletStatus\" is null";
			
			this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, farrowEventId);
				}
			});
		}
	   
	   @Override
		public void deletePregnancyEvent(final Integer pregnancyEventId)
				throws SQLException {
		   final String qry = "delete from pigtrax.\"PigTraxEventMaster\" where \"id_PregnancyEvent\" = ?";
			
			this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, pregnancyEventId);
				}
			});
			
		}
	   
	   @Override
	public void deleteBreedingEvent(final Integer breedingEventId)
			throws SQLException {
		   final String qry = "delete from pigtrax.\"PigTraxEventMaster\" where \"id_BreedingEvent\" = ?";
			
			this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, breedingEventId);
				}
			});
	}
	
}
