package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.usermanagement.enums.RemovalEventType;
import com.pigtrax.util.DateUtil;

@Repository
@Transactional
public class GroupStatusReportDao {

	private static final Logger logger = Logger.getLogger(GroupStatusReportDao.class);

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	GroupEventDao groupDao;
	
	
	@Autowired
	RefDataCache refDataCache;
	

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Map<String, Object>> getGroupStatusList(Integer premiseId, List<Map<String, Object>> rangeList, Integer groupId, String language)
	{	
		Date groupStartDate = getGroupStartDate(groupId);
		
		Integer pigSpaces = getPigSpaces(groupId);
		
		for(Map<String, Object> row : rangeList)
		{
			Date ServDateSTART = (Date)row.get("EventDateStart");
			Date ServDateEND = (Date)row.get("EventDateEnd");
			
			Map<String, Object> startWtAndHeadMap = getStartWtAndHead(ServDateSTART, ServDateEND, groupId);
			Long startHead  = 0L;
			if(startWtAndHeadMap != null)
			{
			row.put("StartWt", startWtAndHeadMap.get("StartWt"));
			startHead = (Long)startWtAndHeadMap.get("StartHd");
			row.put("StartHd", startHead);
			}
			
			Integer phaseId = getPhaseOfProduction(ServDateSTART, ServDateEND, groupId);
			
			if(phaseId != null && phaseId > 0)
				row.put("PhaseType",refDataCache.getPhaseOfProductionTypeMap(language).get(phaseId));
			else
				row.put("PhaseType", " ");
			
			
			Integer deadCount = getDeadsCount(ServDateSTART, ServDateEND, groupId);
			
			row.put("Deads", deadCount);
			
			Integer inventoryCnt = startHead.intValue() -  deadCount;
			
			row.put("Inventory", inventoryCnt);
			
			Double density = 0D;
			
			if(inventoryCnt >0 && pigSpaces >0)
				density = (double)(inventoryCnt/pigSpaces);
			row.put("Density", density);
			
			Integer salesCount = getSalesCount(ServDateSTART, ServDateEND, groupId);
			row.put("Sales", salesCount);
			
			Double mortalityPercentage = 0D;
			if(deadCount >0 && startHead > 0)
				mortalityPercentage = (double) ((deadCount*100)/startHead);
			row.put("Mortality%", mortalityPercentage);
			
			int durationDays = Days.daysBetween(new DateTime(groupStartDate).withTimeAtStartOfDay() , new DateTime(ServDateSTART).withTimeAtStartOfDay() ).getDays() ;
			
			Double WOF = 0D;
			
			if(durationDays > 0)
				WOF = (double)(durationDays/7);
			row.put("WOF", WOF);
			
			Date projectedSalesDate = null;
			if(phaseId == 1)
				projectedSalesDate = DateUtil.addDays(ServDateSTART, 49);
			else
				projectedSalesDate = DateUtil.addDays(ServDateSTART, 120);
			
			row.put("ProjectedSaleDate", projectedSalesDate);
			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(projectedSalesDate);
			int week = cal.get(Calendar.WEEK_OF_YEAR);
			row.put("SaleWk", week);
			
			
			Map<Integer, Integer> weekCntMap = getInventoryCntByWeek(groupId,ServDateSTART, ServDateEND);
			if(weekCntMap != null && weekCntMap.size() > 0)
			{
				int i = 1;
				while(i<=26)
				{
					row.put("W"+i,weekCntMap.get(i));
					i++;
				}
			}
			
		}
		
		return rangeList;
	}
	
	private Map<String, Object> getStartWtAndHead(final Date ServDateSTART, final Date ServDateEND, final Integer groupId)
	{
		final String qry = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num, coalesce(sum(GED.\"weightInKgs\"),0) as Wt from pigtrax.\"GroupEventDetails\" GED "
				+ "where GED.\"id_GroupEvent\" = ? and GED.\"dateOfEntry\"::date <= ?";
		
			
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> cnt  = (List<Map<String, Object>>)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setDate(2, new java.sql.Date(ServDateSTART.getTime()));
				}
			}, new DataMapper());
		
		if(cnt != null && 0<cnt.size())
			return cnt.get(0);
		return null;
	}
	
	private Date getGroupStartDate(final Integer groupId)
	{
		final String qry = " select \"groupStartDateTime\" from pigtrax.\"GroupEvent\"  "
				+ "where \"id\" = ?";
		
			
		@SuppressWarnings("unchecked")
		Date startDate  = (Date)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
				}
			},new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDate(1);
			            }
			            return null;
			          }
			        });
		
		return startDate ;
	}
	
	private Integer getPigSpaces(final Integer groupId)
	{
		final String qry = " select \"pigSpaces\" from pigtrax.\"Room\" R "
				+ "JOIN pigtrax.\"GroupEventRoom\" GER ON GER.\"id_Room\" = R.\"id\" "
				+ "JOIN pigtrax.\"GroupEventPhaseChange\" GEPC ON GER.\"id_GroupEventPhaseChange\" = GEPC.\"id\" "
				+ "JOIN pigtrax.\"GroupEvent\" GE ON GEPC.\"id_GroupEvent\" = GE.\"id\"  where GE.\"id\" = ?";
		
			
		@SuppressWarnings("unchecked")
		Integer pigSpaces  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
				}
			},new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return null;
			          }
			        });
		
		return pigSpaces ;
	}
	
	private Integer getPhaseOfProduction(final Date ServDateSTART, final Date ServDateEND, final Integer groupId)
	{
		final String qry = "select \"id_PhaseOfProductionType\" from pigtrax.\"GroupEventPhaseChange\" where \"id\" = (select max(\"id\") from pigtrax.\"GroupEventPhaseChange\" where " 
			+" (\"phaseStartDate\" between ?  and ? or \"phaseStartDate\" < ?)   and \"id_GroupEvent\" = ?)";
		
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setDate(1, new java.sql.Date(ServDateSTART.getTime()));
					ps.setDate(2, new java.sql.Date(ServDateEND.getTime()));
					ps.setDate(3, new java.sql.Date(ServDateEND.getTime()));
					ps.setInt(4, groupId);
				}
			}, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return 0;
			          }
			        });
		
		return cnt ;
	}
	
	
	private Integer getDeadsCount(final Date ServDateSTART, final Date ServDateEND, final Integer groupId)
	{
		final String qry = "select sum(\"numberOfPigs\") from pigtrax.\"RemovalEventExceptSalesDetails\" where \"id_GroupEvent\" = ? and \"id_RemovalEvent\" = ? and \"removalDateTime\" between ? and  ?";
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setInt(2, RemovalEventType.Mortality.getTypeCode());
					ps.setDate(3, new java.sql.Date(ServDateSTART.getTime()));
					ps.setDate(4, new java.sql.Date(ServDateEND.getTime()));
					
				}
			}, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return 0;
			          }
			        });
		
		return (cnt != null)?cnt : 0 ;
	}
	
	
	private Integer getSalesCount(final Date ServDateSTART, final Date ServDateEND, final Integer groupId)
	{
		final String qry = "select sum(\"numberOfPigs\") from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\" = ?  and \"salesDateTime\" between ? and  ?";
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setDate(2, new java.sql.Date(ServDateSTART.getTime()));
					ps.setDate(3, new java.sql.Date(ServDateEND.getTime()));
					
				}
			}, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return 0;
			          }
			        });
		
		return (cnt != null)?cnt : 0 ;
	}
	
	
	
	private static final class DataMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("StartHd",rs.getLong("Num"));
			dataMap.put("StartWt",rs.getDouble("Wt"));
			return dataMap;
		}
	}
	
	
	private Map<Integer, Integer> getInventoryCntByWeek(final Integer groupId, final Date ServDateSTART, final Date ServDateEND)
	{
		Map<Integer, Integer> weekCntMap = new HashMap<Integer, Integer>();
		Integer remainingCnt = 0;
		for(int i =1 ;i <=26; i++)
		{
			final Date startDate = DateUtil.addDays(ServDateSTART, i*7);
			final Date endDate = DateUtil.addDays(ServDateEND, i*7);
			final int index = i;
			
			final String qry = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num from pigtrax.\"GroupEventDetails\" GED "
					+ "where GED.\"id_GroupEvent\" = ? and GED.\"dateOfEntry\" <= ?";
			
			
			
			@SuppressWarnings("unchecked")
			Integer sowCount  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
				@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, groupId);
						ps.setDate(2, new java.sql.Date(startDate.getTime()));
					}
				},
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getInt(1);
		            }
		            return 0;
		          }
		        });
			
			weekCntMap.put(index, sowCount);
		}
		
		return weekCntMap;
		
	}
	
}
