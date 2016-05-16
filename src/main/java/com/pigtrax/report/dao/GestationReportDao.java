package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.master.dao.interfaces.PenDao;
import com.pigtrax.master.dto.Pen;
import com.pigtrax.util.DateUtil;

@Repository
@Transactional
public class GestationReportDao {

	private static final Logger logger = Logger.getLogger(GestationReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	PenDao penDao;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Map<String, Object>> getGestationResultList(Integer premiseId, List<Map<String, Object>> rangeList)
	{
		Integer penCount = getPenCount(premiseId);
		
		
		for(Map<String, Object> row : rangeList)
		{
			Date ServDateSTART = (Date)row.get("ServDateSTART");
			Date ServDateEND = (Date)row.get("ServDateEND");
			
			Integer NumberServ = getNumberOfSowsByServiceDate(premiseId,ServDateSTART, ServDateEND);
			row.put("NumberServ", NumberServ);
			
			Map<Integer, Integer> weekCntMap = getNumberOfSowsByWeek(premiseId,ServDateSTART, ServDateEND);
			if(weekCntMap != null && weekCntMap.size() > 0)
			{
				int i = 1;
				while(i<=16)
				{
					row.put("W"+i,weekCntMap.get(i));
					i++;
				}
			}
			row.put("NumberServ", NumberServ);
			Integer farrowCnt = getFarrowCount(premiseId,ServDateSTART, ServDateEND);
			row.put("Farrows", farrowCnt);
			Date farrowDate = DateUtil.addDays(ServDateSTART, 115);
			try {
				row.put("FarrowDate", DateUtil.convertToFormatString(farrowDate, "dd/MM/yyyy"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Double farrowPercentage = 0D;
			if(NumberServ >0 && farrowCnt > 0)
				farrowPercentage = (double) ((farrowCnt*100)/NumberServ);
			row.put("%FR", farrowPercentage);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(farrowDate);
			int week = cal.get(Calendar.WEEK_OF_YEAR);
			row.put("FarrowWK", week);
			Double FACapacity = 0D;
			
			if(penCount > 0 && farrowCnt > 0)
			{
				FACapacity = (double)(farrowCnt/penCount)*100;
			row.put("FACapacity", FACapacity);
			}
			else
				row.put("FACapacity", 0);
		}
		
		return rangeList;
	}
	
	
	private Integer getNumberOfSowsByServiceDate(final Integer premiseId, final Date ServDateSTART, final Date ServDateEND)
	{
		final String qry = "Select count(BE.\"id_PigInfo\") from pigtrax.\"BreedingEvent\" BE where BE.\"id_Premise\" = ? and BE.\"serviceStartDate\" between ? and ? ";
		
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, premiseId);
					ps.setDate(2, new java.sql.Date(ServDateSTART.getTime()));
					ps.setDate(3, new java.sql.Date(ServDateEND.getTime()));
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
		
		return cnt ;
	}
	
	
	private Integer getFarrowCount(final Integer premiseId, final Date ServDateSTART, final Date ServDateEND)
	{
		final String qry = "Select count(FE.\"id_PigInfo\") from pigtrax.\"FarrowEvent\" FE JOIN pigtrax.\"BreedingEvent\" BE ON FE.\"id_BreedingEvent\" = BE.\"id\" "
				+ " where BE.\"id_Premise\" = ? and BE.\"serviceStartDate\" between ? and ? ";
		
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, premiseId);
					ps.setDate(2, new java.sql.Date(ServDateSTART.getTime()));
					ps.setDate(3, new java.sql.Date(ServDateEND.getTime()));
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
		
		return cnt ;
	}
	
	
	public Integer getAverageGestationTarget(final Integer premiseId)
	{
		final String qry = "SELECT CAST(coalesce(CT.\"targetValue\", '0') AS integer) as \"Avg gest\" from pigtrax.\"CompanyTarget\"  CT "
					+" JOIN pigtraxrefdata.\"TargetType\" TT ON CT.\"id_TargetType\" = TT.\"id\" and TT.\"fieldDescription\" = 'Avg gest' and CT.\"id_Premise\" = ?";
		
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, premiseId);
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
		
		return cnt ;
	}
	
	
	public Integer getPenCount(final Integer premiseId)
	{
		try {
			List<Pen> penList = penDao.getPenListByPremiseId(premiseId);
			return penList.size();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return 0;
	}
	
	
	private Map<Integer, Integer> getNumberOfSowsByWeek(final Integer premiseId, final Date ServDateSTART, final Date ServDateEND)
	{
		Map<Integer, Integer> weekCntMap = new HashMap<Integer, Integer>();
		Integer remainingCnt = 0;
		for(int i =1 ;i <=16; i++)
		{
			final Date startDate = DateUtil.addDays(ServDateSTART, i*7);
			final Date endDate = DateUtil.addDays(ServDateEND, i*7);
			final int index = i;
			
			final String qry = "Select count(BE.\"id_PigInfo\") from pigtrax.\"BreedingEvent\" BE where BE.\"id_Premise\" = ? and BE.\"serviceStartDate\"+interval '"+i*7+"' day between ? and ? ";
			
			
			@SuppressWarnings("unchecked")
			Integer sowCount  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
				@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, premiseId);
						ps.setDate(2, new java.sql.Date(startDate.getTime()));
						ps.setDate(3, new java.sql.Date(endDate.getTime()));
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
			/*
			final String qry1 = "select sum(T.cnt) as removalCnt from (select count(RE.\"id_PigInfo\") as cnt from pigtrax.\"RemovalEventExceptSalesDetails\" RE where "
					+ " \"id_PigInfo\" in ( select \"id_PigInfo\" from pigtrax.\"BreedingEvent\" where \"serviceStartDate\" between ? and ? and \"id_Premise\" = ? "
					+" ) and \"removalDateTime\" between ? and ? "
					+" UNION "
					+" select count( SE.\"id_PigInfo\") as cnt from pigtrax.\"SalesEventDetails\" SE where \"id_PigInfo\" in ( "
					+" select \"id_PigInfo\" from pigtrax.\"BreedingEvent\" where \"serviceStartDate\" between ? and ?  and \"id_Premise\" = ? "
					+" ) and \"salesDateTime\" between ? and ? ) T ";
			
			@SuppressWarnings("unchecked")
			Integer removalCnt  = (Integer)jdbcTemplate.query(qry1,new PreparedStatementSetter() {
				@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						
						ps.setDate(1, new java.sql.Date(ServDateSTART.getTime()));
						ps.setDate(2, new java.sql.Date(ServDateEND.getTime()));
						ps.setInt(3, premiseId);
						ps.setDate(4, new java.sql.Date(ServDateSTART.getTime()));
						ps.setDate(5, new java.sql.Date(endDate.getTime()));
						
						ps.setDate(6, new java.sql.Date(ServDateSTART.getTime()));
						ps.setDate(7, new java.sql.Date(ServDateEND.getTime()));
						ps.setInt(8, premiseId);
						ps.setDate(9, new java.sql.Date(ServDateSTART.getTime()));
						ps.setDate(10, new java.sql.Date(endDate.getTime()));
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
			
			remainingCnt = sowCount - removalCnt;
			weekCntMap.put(index, remainingCnt);*/
			weekCntMap.put(index, sowCount);
		}
		
		return weekCntMap;
		
	}
}
