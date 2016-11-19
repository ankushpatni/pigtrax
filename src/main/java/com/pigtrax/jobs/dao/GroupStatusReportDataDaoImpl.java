package com.pigtrax.jobs.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.jobs.dao.interfaces.GroupStatusReportDataDao;
import com.pigtrax.jobs.dto.GroupStatusReportDataDto;
import com.pigtrax.report.dao.ActionListReportDao;

@Repository
@Transactional
public class GroupStatusReportDataDaoImpl implements GroupStatusReportDataDao {

	private static final Logger logger = Logger.getLogger(ActionListReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcTemplate.setQueryTimeout(10*60); 
	}
	
	/**
	 * Delete the data based on the type (I - Inventory, M - Mortality)
	 */
	public int delete(final Integer groupEventId,final String type) {
			final String qry = "delete from pigtrax.\"GroupStatusReportData\" where \"id_GroupEvent\" = ? and \"type\" = ?";
			
			return this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupEventId);
					ps.setString(2, type);
				}
			});
	}
	
	public void insert(final GroupStatusReportDataDto data) {
		insertInventoryData(data);
		insertMortalityData(data);
	}
	
	
	  

	/**
	 * Insert inventory data for the group
	 * @param data
	 */
   void insertInventoryData(final GroupStatusReportDataDto data) {
		
		data.setType("I");
		final String qry = "insert into pigtrax.\"GroupStatusReportData\"(\"calendarWk\", \"id_GroupEvent\", \"StartHd\", \"StartWt\", \"W1\", "
				+ "\"W2\",\"W3\",\"W4\",\"W5\",\"W6\",\"W7\",\"W8\",\"W9\",\"W10\",\"W11\",\"W12\",\"W13\",\"W14\",\"W15\","
				+ "\"W16\",\"W17\",\"W18\",\"W19\",\"W20\",\"W21\",\"W22\",\"W23\",\"W24\",\"W25\",\"W26\",\"type\","
				+ "\"sowSource\", \"id_PhaseType\", \"roomId\", \"barnId\", \"premiseId\", \"inventory\", "
				+ "\"deads\", \"mortalityPercentage\", \"density\", \"sales\", \"projectedSaleDate\", \"projectedSaleWk\",\"groupId\", \"eventStartDate\",\"eventCloseDate\", "
				+ " \"id_SowSource\",\"id_Premise\",\"lastUpdatedOn\") values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp)";
		
		 this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, data.getCalendarWk(), java.sql.Types.INTEGER);
				ps.setObject(2, data.getGroupEventId(), java.sql.Types.INTEGER);
				ps.setObject(3, data.getStartHd(), java.sql.Types.INTEGER);
				ps.setObject(4, data.getStartWt(), java.sql.Types.INTEGER);		
				int idx = 5;
				if(data.getInventoryCntMap() != null && data.getInventoryCntMap().size() > 0)
				{ 
					int i = 1;
					while(i<=26)
					{	
						ps.setObject(idx,  data.getInventoryCntMap().get(i), java.sql.Types.INTEGER);
						i++;
						idx++;
					}
				}
				ps.setObject(31, data.getType(), java.sql.Types.VARCHAR);
				ps.setObject(32, data.getSowSource(),java.sql.Types.VARCHAR);
				ps.setObject(33, data.getPhaseTypeId(),java.sql.Types.INTEGER);
				ps.setObject(34, data.getRoomId(),java.sql.Types.VARCHAR);
				ps.setObject(35, data.getBarnId(),java.sql.Types.VARCHAR);
				ps.setObject(36,  data.getPremiseId(),java.sql.Types.VARCHAR);
				ps.setObject(37, data.getInventory(), java.sql.Types.INTEGER);
				ps.setObject(38, data.getDeads(), java.sql.Types.INTEGER);
				ps.setObject(39,  data.getMortalityPercentage(), java.sql.Types.DOUBLE);
				ps.setObject(40, data.getDensity(), java.sql.Types.DOUBLE);
				ps.setObject(41,  data.getSales(), java.sql.Types.INTEGER);
				ps.setObject(42, new java.sql.Date(data.getProjectedSaleDate().getTime()), java.sql.Types.DATE);
				ps.setObject(43, data.getProjectedSaleWk(), java.sql.Types.INTEGER);
				ps.setObject(44,  data.getGroupId(),java.sql.Types.VARCHAR);
				ps.setObject(45,  new java.sql.Date(data.getGroupStartDate().getTime()),java.sql.Types.DATE);
				if(data.getGroupCloseDate() != null)
					ps.setObject(46,  new java.sql.Date(data.getGroupCloseDate().getTime()),java.sql.Types.DATE);
				else
					ps.setNull(46,  java.sql.Types.DATE);
				ps.setObject(47,  data.getSowSourceId(), java.sql.Types.INTEGER);
				ps.setObject(48,  data.getPremiseIdPk(), java.sql.Types.INTEGER);
			}
		});
	}
   
   
   /**
    * Insert Mortality data for the group
    * @param data
    */
   void insertMortalityData(final GroupStatusReportDataDto data) {
		
		data.setType("M");
		final String qry = "insert into pigtrax.\"GroupStatusReportData\"(\"calendarWk\", \"id_GroupEvent\", \"StartHd\", \"StartWt\", \"W1\", "
				+ "\"W2\",\"W3\",\"W4\",\"W5\",\"W6\",\"W7\",\"W8\",\"W9\",\"W10\",\"W11\",\"W12\",\"W13\",\"W14\",\"W15\","
				+ "\"W16\",\"W17\",\"W18\",\"W19\",\"W20\",\"W21\",\"W22\",\"W23\",\"W24\",\"W25\",\"W26\",\"type\","
				+ "\"sowSource\", \"id_PhaseType\", \"roomId\", \"barnId\", \"premiseId\", \"inventory\","
				+ "\"deads\", \"mortalityPercentage\", \"density\", \"sales\", \"projectedSaleDate\", \"projectedSaleWk\",\"groupId\", \"eventStartDate\",\"eventCloseDate\","
				+ " \"id_SowSource\",\"id_Premise\",\"lastUpdatedOn\") values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp)";
		
		 this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, data.getCalendarWk(), java.sql.Types.INTEGER);
				ps.setObject(2, data.getGroupEventId(), java.sql.Types.INTEGER);
				ps.setObject(3, data.getStartHd(), java.sql.Types.INTEGER);
				ps.setObject(4, data.getStartWt(), java.sql.Types.INTEGER);				
				int idx = 5;
				if(data.getMortalityCntMap() != null && data.getMortalityCntMap().size() > 0)
				{ 
					int i = 1;
					while(i<=26)
					{
						ps.setObject(idx,  data.getMortalityCntMap().get(i), java.sql.Types.INTEGER);
						i++;
						idx++;
					}
				}
				ps.setObject(31, data.getType(), java.sql.Types.VARCHAR);
				ps.setObject(32, data.getSowSource(),java.sql.Types.VARCHAR);
				ps.setObject(33, data.getPhaseTypeId(),java.sql.Types.INTEGER);
				ps.setObject(34, data.getRoomId(),java.sql.Types.VARCHAR);
				ps.setObject(35, data.getBarnId(),java.sql.Types.VARCHAR);
				ps.setObject(36,  data.getPremiseId(),java.sql.Types.VARCHAR);
				ps.setObject(37, data.getInventory(), java.sql.Types.INTEGER);
				ps.setObject(38, data.getDeads(), java.sql.Types.INTEGER);
				ps.setObject(39,  data.getMortalityPercentage(), java.sql.Types.DOUBLE);
				ps.setObject(40, data.getDensity(), java.sql.Types.DOUBLE);
				ps.setObject(41,  data.getSales(), java.sql.Types.INTEGER);
				ps.setObject(42, new java.sql.Date(data.getProjectedSaleDate().getTime()), java.sql.Types.DATE);
				ps.setObject(43, data.getProjectedSaleWk(), java.sql.Types.INTEGER);
				ps.setObject(44,  data.getGroupId(),java.sql.Types.VARCHAR);
				ps.setObject(45,  new java.sql.Date(data.getGroupStartDate().getTime()),java.sql.Types.DATE);
				if(data.getGroupCloseDate() != null)
					ps.setObject(46,  new java.sql.Date(data.getGroupCloseDate().getTime()),java.sql.Types.DATE);
				else
					ps.setNull(46,  java.sql.Types.DATE);
				ps.setObject(47,  data.getSowSourceId(), java.sql.Types.INTEGER);
				ps.setObject(48,  data.getPremiseIdPk(), java.sql.Types.INTEGER);
			}
		});
	}
   
   
   public int delete(Integer groupEventId) {
	   delete(groupEventId, "I");
	   delete(groupEventId, "M");
	   return 1;
   }	
	
}
