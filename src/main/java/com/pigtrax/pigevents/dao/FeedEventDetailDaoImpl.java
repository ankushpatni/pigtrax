package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pigtrax.pigevents.beans.FeedEventDetail;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.FeedEventDetailDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.report.bean.RationReportBean;
import com.pigtrax.report.bean.RationReportFeedCostBean;
import com.pigtrax.util.DateUtil;

@Repository
public class FeedEventDetailDaoImpl implements FeedEventDetailDao {

private static final Logger logger = Logger.getLogger(FeedEventDetailDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public FeedEventDetail getFeedEventDetailById(final int id)
			throws SQLException {
		String qry = "select FED.\"id\", FED.\"feedEventDate\", FED.\"weightInKgs\", FED.\"remarks\", FED.\"id_FeedEvent\", "
		   		+ "FED.\"id_Silo\", FED.\"id_GroupEvent\", FED.\"id_FeedEventType\",FED.\"lastUpdated\", FED.\"userUpdated\",GE.\"groupId\",FED.\"feedMill\", FED.\"feedCost\" "+
		   		"from pigtrax.\"FeedEventDetails\" FED LEFT JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\" where FED.\"id\" = ? ";
			
			List<FeedEventDetail> feedEventDetailList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, id);
				}}, new FeedEventDetailMapper());

			if(feedEventDetailList != null && feedEventDetailList.size() > 0){
				return feedEventDetailList.get(0);
			}
			return null;
	}

	@Override
	public List<FeedEventDetail> getFeedEventDetailByFeedEventId(final int feedEventid)
			throws SQLException {
		String qry = "select FED.\"id\", FED.\"feedEventDate\", FED.\"weightInKgs\", FED.\"remarks\", FED.\"id_FeedEvent\", "
		   		+ "FED.\"id_Silo\", FED.\"id_GroupEvent\", FED.\"id_FeedEventType\",FED.\"lastUpdated\", FED.\"userUpdated\",GE.\"groupId\",FED.\"feedMill\", FED.\"feedCost\" "+
		   		"from pigtrax.\"FeedEventDetails\" FED LEFT JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\" where FED.\"id_FeedEvent\" = ? ";
			
			List<FeedEventDetail> feedEventDetailList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, feedEventid);
				}}, new FeedEventDetailMapper());

			if(feedEventDetailList != null && feedEventDetailList.size() > 0){
				return feedEventDetailList;
			}
			return null;
	}

	@Override
	public int addFeedEventDetail(final FeedEventDetail feedEventDetail)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"FeedEventDetails\"(\"feedEventDate\", \"weightInKgs\", \"remarks\", \"id_FeedEvent\", "
		   		+ "\"id_Silo\", \"id_GroupEvent\", \"id_FeedEventType\",\"lastUpdated\", \"userUpdated\",\"feedMill\",\"feedCost\") "
				+ "values(?,?,?,?,?,?,?,current_timestamp,?,?,?)";	
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				if( null != feedEventDetail.getFeedEventDate())
				{
				ps.setDate(1, new java.sql.Date(feedEventDetail.getFeedEventDate().getTime()));
				}
				else
				{
					ps.setNull(1, java.sql.Types.DATE);
				}
				
				if( null != feedEventDetail.getWeightInKgs())
				{
					ps.setBigDecimal(2, feedEventDetail.getWeightInKgs());
				}
				else
				{
					ps.setNull(2, java.sql.Types.INTEGER);
				}
				
				ps.setString(3, feedEventDetail.getRemarks() );
				ps.setInt(4, feedEventDetail.getFeedEventId());
				ps.setInt(5, feedEventDetail.getSiloId());
				if( null != feedEventDetail.getGroupEventId() && feedEventDetail.getGroupEventId()>0)
				{
					ps.setInt(6, feedEventDetail.getGroupEventId());
				}
				else
				{
					ps.setNull(6, java.sql.Types.INTEGER);
				}
				ps.setInt(7, feedEventDetail.getFeedEventTypeId());
				ps.setString(8, feedEventDetail.getUserUpdated());
				ps.setString(9,  feedEventDetail.getFeedMill());
				ps.setObject(10, feedEventDetail.getFeedCost(), java.sql.Types.DOUBLE);
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}

	@Override
	public int updateFeedEventDetail(final FeedEventDetail feedEventDetail)
			throws SQLException {
		String query = "update pigtrax.\"FeedEventDetails\" SET \"feedEventDate\"=?, \"weightInKgs\"=?, \"remarks\"=?,"
				+" \"id_Silo\"=? ,\"id_GroupEvent\" =? , \"id_FeedEventType\" = ?, \"lastUpdated\"=current_timestamp,"+
				" \"userUpdated\"=?,\"feedMill\"=?,\"feedCost\" = ?  where \"id\" = ? ";
		
			return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					if( null != feedEventDetail.getFeedEventDate())
					{
					ps.setDate(1, new java.sql.Date(feedEventDetail.getFeedEventDate().getTime()));
					}
					else
					{
						ps.setNull(1, java.sql.Types.DATE);
					}
					if( null != feedEventDetail.getWeightInKgs())
					{
						ps.setBigDecimal(2, feedEventDetail.getWeightInKgs());
					}
					else
					{
						ps.setNull(2, java.sql.Types.INTEGER);
					}
					//ps.setBigDecimal(2, feedEventDetail.getWeightInKgs());
					ps.setString(3, feedEventDetail.getRemarks() );
					ps.setInt(4, feedEventDetail.getSiloId());
					if( null != feedEventDetail.getGroupEventId() && feedEventDetail.getGroupEventId()>0)
					{
						ps.setInt(5, feedEventDetail.getGroupEventId());
					}
					else
					{
						ps.setNull(5, java.sql.Types.INTEGER);
					}
					ps.setInt(6, feedEventDetail.getFeedEventTypeId());
					ps.setString(7, feedEventDetail.getUserUpdated());
					ps.setString(8, feedEventDetail.getFeedMill());
					ps.setObject(9, feedEventDetail.getFeedCost(), java.sql.Types.DOUBLE);
					ps.setInt(10, feedEventDetail.getId());
				}
			});	
	}
	
	private static final class FeedEventDetailMapper implements RowMapper<FeedEventDetail> {
		public FeedEventDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			FeedEventDetail feedEventDetail = new FeedEventDetail();
			feedEventDetail.setId(rs.getInt("id"));
			feedEventDetail.setFeedEventDate(rs.getDate("feedEventDate"));
			try {
				feedEventDetail.setFeedEventDateStr(DateUtil.convertToFormatString(feedEventDetail.getFeedEventDate(), "dd/MM/yyyy"));
			} catch (ParseException e) {
				feedEventDetail.setFeedEventDateStr(null);
			}
			feedEventDetail.setWeightInKgs(rs.getBigDecimal("weightInKgs"));
			feedEventDetail.setRemarks(rs.getString("remarks"));
			feedEventDetail.setFeedEventId(rs.getInt("id_FeedEvent"));
			feedEventDetail.setSiloId(rs.getInt("id_Silo"));
			feedEventDetail.setGroupEventId(rs.getInt("id_GroupEvent"));
			feedEventDetail.setFeedEventTypeId(rs.getInt("id_FeedEventType"));
			feedEventDetail.setLastUpdated(rs.getDate("lastUpdated"));
			feedEventDetail.setUserUpdated(rs.getString("userUpdated"));
			feedEventDetail.setGroupEventGroupId(rs.getString("groupId"));
			feedEventDetail.setFeedMill(rs.getString("feedMill"));
			feedEventDetail.setFeedCost(rs.getBigDecimal("feedCost"));
			return feedEventDetail;
		}	
	}
	
	public Double getTotalFeedUsed(Integer groupId) {
		String qry = " select coalesce(sum(FED.\"weightInKgs\"),0) as total from pigtrax.\"FeedEventDetails\" FED "
				+ "where FED.\"id_GroupEvent\" = "+groupId+" and FED.\"id_FeedEventType\" = 1";
		
		
		@SuppressWarnings("unchecked")
		Double total  = (Double)jdbcTemplate.query(qry,
	        new ResultSetExtractor() {
	          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
	            if (resultSet.next()) {
	              return resultSet.getDouble(1);
	            }
	            return 0;
	          }
	        });
		
		return total;
		}
	
	
	
	public Double getTotalFeedBudgeted(final Integer groupId) {
		
//		final String qry = "select sum(\"weightInKgs\") from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\" = ?  and \"salesDateTime\" <=  ?";
/*		final String salesDateqry = "select max(\"salesDateTime\") from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\"= ? ";
		
		
		
		@SuppressWarnings("unchecked")
		Date lastSale  = (Date)jdbcTemplate.query(salesDateqry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					
				}
			}, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDate(1);
			            }
			            return 0;
			          }
			        });
*/		
		
		Double budgetedFeed = 0D;
		
		String qry= "select A.\"weightInKgs\",A.\"feedCost\",A.\"feedEventDate\",  A.\"batchId\", A.\"groupStartDateTime\",A.\"targetValue\" feednumberofdays, B.\"targetValue\" feedperkgperpigperday,"
				+ "C.\"targetValue\" feedcostperpig  from (select FED.\"weightInKgs\",FED.\"feedCost\",FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" from pigtrax.\"FeedEvent\" "
				+ "FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\"  "
				+ "LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" in (111)   "
				+ "where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = "+groupId+"  AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" from pigtrax.\"GroupEvent\" where \"id\" = "+groupId+") "
				+ "order by \"feedEventDate\") A LEFT JOIN (select FED.\"weightInKgs\",FED.\"feedCost\",FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" from pigtrax.\"FeedEvent\" "
				+ "FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\"  "
				+ "LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" in (112)   "
				+ "where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = "+groupId+"  AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" from pigtrax.\"GroupEvent\" where \"id\" = "+groupId+") "
				+ "order by \"feedEventDate\") B ON A.\"batchId\"=B.\"batchId\" LEFT JOIN (select FED.\"weightInKgs\",FED.\"feedCost\",FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" "
				+ "from pigtrax.\"FeedEvent\" FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  JOIN pigtrax.\"GroupEvent\" GE "
				+ "ON FED.\"id_GroupEvent\" = GE.\"id\"  LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" "
				+ "AND CT.\"id_TargetType\" in (113)   where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = "+groupId+"  AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" "
				+ "from pigtrax.\"GroupEvent\" where \"id\" = "+groupId+") order by \"feedEventDate\") C ON B.\"batchId\"=C.\"batchId\"";
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> budgetListFeedPerKgPerPig  = (List)jdbcTemplate.query(qry, new BudgetMapper());
		if(budgetListFeedPerKgPerPig != null && 0 <budgetListFeedPerKgPerPig.size())
		{
			for(Map<String, Object> entry : budgetListFeedPerKgPerPig)
			{
				final Date feedDate = entry.get("feedEventDate") != null? (Date)entry.get("feedEventDate"):null;
				Double feedNumberofDays = entry.get("feednumberofdays") != null ? Double.parseDouble((String)entry.get("feednumberofdays")) : 0D;
				Double feedPerKgPerPigPerDay = entry.get("feedperkgperpigperday") != null ? Double.parseDouble((String)entry.get("feedperkgperpigperday")) : 0D;
				Double feedCostPerPig = entry.get("feedcostperpig") != null ? Double.parseDouble((String)entry.get("feedcostperpig")) : 0D;
				Date groupStartDate = entry.get("groupStartDate") != null ? (Date)entry.get("groupStartDate") : null;
				if(feedDate != null)
				{
					String sql = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num from pigtrax.\"GroupEventDetails\" GED "
							+ "where GED.\"id_GroupEvent\" = "+groupId+" and GED.\"dateOfEntry\" <= ?";
					
					@SuppressWarnings("unchecked")
					Integer sowCount  = (Integer)jdbcTemplate.query(sql,new PreparedStatementSetter() {
						@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setDate(1, new java.sql.Date(feedDate.getTime()));
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
					if(sowCount == 0 && feedDate.getTime() <= groupStartDate.getTime())
					{
						Map<String, Object> detailsMap = groupEventDao.getStartWtAndHead(groupId);
						if(detailsMap != null && detailsMap.get("StartHd") != null)
						{
							sowCount = ((Long)detailsMap.get("StartHd")).intValue();
						}
					}
					budgetedFeed += sowCount*feedPerKgPerPigPerDay*feedNumberofDays;
				}
				
			}
		}
		
		return budgetedFeed;
		}
	
	
	private static final class BudgetMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Map<String, Object> budgetMap = new HashMap<String, Object>();
			
			budgetMap.put("weightInKgs",rs.getString(1));
			budgetMap.put("feedCost",rs.getString(2));
			budgetMap.put("feedEventDate", rs.getDate(3));
			budgetMap.put("batchId", rs.getInt(4));
			budgetMap.put("groupStartDateTime", rs.getDate(5));
			budgetMap.put("feednumberofdays", rs.getString(6));
			budgetMap.put("feedperkgperpigperday", rs.getString(7));
			budgetMap.put("feedcostperpig", rs.getString(8));
			return budgetMap;
		}	
	}
	
	public Double getTotalFeedCost(Integer groupId) {
		String qry = " select coalesce(sum(FED.\"feedCost\"),0) as total from pigtrax.\"FeedEventDetails\" FED "
				+ "where FED.\"id_GroupEvent\" = "+groupId+" and FED.\"id_FeedEventType\" = 1";
		
		
		@SuppressWarnings("unchecked")
		Double total  = (Double)jdbcTemplate.query(qry,
	        new ResultSetExtractor() {
	          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
	            if (resultSet.next()) {
	              return resultSet.getDouble(1);
	            }
	            return 0;
	          }
	        });
		
		return total;
		}
	
	
	public Double getTotalFeedBudgetedCost(final Integer groupId) {
/*		final String salesDateqry = "select max(\"salesDateTime\") from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\"= ? ";
		
		
		
		@SuppressWarnings("unchecked")
		Date lastSaleDate  = (Date)jdbcTemplate.query(salesDateqry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					
				}
			}, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDate(1);
			            }
			            return 0;
			          }
			        });
*/
		Double budgetedFeed = 0D;
		
		/*String qry= "select A.\"feedEventDate\",  A.\"batchId\", A.\"groupStartDateTime\",A.\"targetValue\" feednumberofdays, B.\"targetValue\" feedperkgperpigperday,"
				+ "C.\"targetValue\" feedcostperpig  from (select FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" from pigtrax.\"FeedEvent\" "
				+ "FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\"  "
				+ "LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" in (111)   "
				+ "where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = "+groupId+"  AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" from pigtrax.\"GroupEvent\" where \"id\" = 141) "
				+ "order by \"feedEventDate\") A LEFT JOIN (select FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" from pigtrax.\"FeedEvent\" "
				+ "FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\"  "
				+ "LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" in (112)   "
				+ "where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = "+groupId+"  AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" from pigtrax.\"GroupEvent\" where \"id\" = 141) "
				+ "order by \"feedEventDate\") B ON A.\"batchId\"=B.\"batchId\" LEFT JOIN (select FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" "
				+ "from pigtrax.\"FeedEvent\" FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  JOIN pigtrax.\"GroupEvent\" GE "
				+ "ON FED.\"id_GroupEvent\" = GE.\"id\"  LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" "
				+ "AND CT.\"id_TargetType\" in (113)   where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = "+groupId+"  AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" "
				+ "from pigtrax.\"GroupEvent\" where \"id\" = "+groupId+") order by \"feedEventDate\") C ON B.\"batchId\"=C.\"batchId\"";*/
		
		
		String qry = "select A.\"weightInKgs\",A.\"feedCost\",A.\"feedEventDate\",  A.\"batchId\", A.\"groupStartDateTime\",A.\"targetValue\" feednumberofdays, B.\"targetValue\" feedperkgperpigperday, "
				+ " C.\"targetValue\" feedcostperpig  from (select FED.\"weightInKgs\",FED.\"feedCost\",FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" from pigtrax.\"FeedEvent\"  "
				+ " FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\"  "
				+ " LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" in (111)  "
				+ " where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = " + groupId
				+ "  AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" from pigtrax.\"GroupEvent\" where \"id\" =  "
				+ groupId + ")  "
				+ "		order by \"feedEventDate\") A LEFT JOIN (select FED.\"weightInKgs\",FED.\"feedCost\",FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" from pigtrax.\"FeedEvent\"  "
				+ "		 FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\"    "
				+ "		 LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" in (112)   "
				+ "		 where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = " + groupId
				+ "  AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" from pigtrax.\"GroupEvent\" where \"id\" = "
				+ groupId + ")  "
				+ "		 order by \"feedEventDate\") B ON A.\"batchId\"=B.\"batchId\" LEFT JOIN (select FED.\"weightInKgs\",FED.\"feedCost\",FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\"  "
				+ "		 from pigtrax.\"FeedEvent\" FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  JOIN pigtrax.\"GroupEvent\" GE   "
				+ "		 ON FED.\"id_GroupEvent\" = GE.\"id\"  LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\"  "
				+ "		 AND CT.\"id_TargetType\" in (113)   where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = "
				+ groupId + "  AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\"  "
				+ "		 from pigtrax.\"GroupEvent\" where \"id\" = " + groupId
				+ ") order by \"feedEventDate\") C ON B.\"batchId\"=C.\"batchId\"";
		
/*		String feedPerKgPerPigPerDay= " select FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" from pigtrax.\"FeedEvent\" FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  "
				+" JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\" "
				+" LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" = 112  "  //target Type - 113 for Feed_Feed cost/pig
				+" where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = "+groupId+" AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" from pigtrax.\"GroupEvent\" where \"id\" = "+groupId+") "
						+ "order by FED.\"feedEventDate\"";
		
		String feedCostPerPig = " select FED.\"feedEventDate\", FD.\"batchId\", CT.\"targetValue\",GE.\"groupStartDateTime\" from pigtrax.\"FeedEvent\" FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  "
				+" JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\" "
				+" LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" = 113  "  //target Type - 113 for Feed_Feed cost/pig
				+" where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = "+groupId+" AND FED.\"feedEventDate\" >= (select \"groupStartDateTime\" from pigtrax.\"GroupEvent\" where \"id\" = "+groupId+") "
						+ "order by FED.\"feedEventDate\"";
*/		
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> budgetList  = (List)jdbcTemplate.query(qry, new BudgetMapper());
		if(budgetList != null && 0 <budgetList.size())
		{
			for(Map<String, Object> entry : budgetList)			
			{
				final Date feedDate = entry.get("feedEventDate") != null? (Date)entry.get("feedEventDate"):null;
				Double feedNumberofDays = entry.get("feednumberofdays") != null ? Double.parseDouble((String)entry.get("feednumberofdays")) : 0D;
				Double feedPerKgPerPigPerDay = entry.get("feedperkgperpigperday") != null ? Double.parseDouble((String)entry.get("feedperkgperpigperday")) : 0D;
				Double feedCostPerPig = entry.get("feedcostperpig") != null ? Double.parseDouble((String)entry.get("feedcostperpig")) : 0D;
				Double weight = entry.get("weightInKgs") != null ? Double.parseDouble((String)entry.get("weightInKgs")) : 0D;
				Double feedCost = entry.get("feedCost") != null ? Double.parseDouble((String)entry.get("feedCost")) : 0D;
/*				if (count<budgetList.size()){
					nextFeedDate = budgetList.get(count).get("feedEventDate") != null? (Date)budgetList.get(count).get("feedEventDate"):null;
					
				}
				else{
					nextFeedDate = lastSaleDate;
					
				}
*/				Date groupStartDate = entry.get("groupStartDate") != null ? (Date)entry.get("groupStartDate") : null;
				if(feedDate != null)
				{
					String sql = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num from pigtrax.\"GroupEventDetails\" GED "
							+ "where GED.\"id_GroupEvent\" = "+groupId+" and GED.\"dateOfEntry\" <= ?";
					
					@SuppressWarnings("unchecked")
					Integer sowCount  = (Integer)jdbcTemplate.query(sql,new PreparedStatementSetter() {
						@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setDate(1, new java.sql.Date(feedDate.getTime()));
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
					int duration = 0;
					if(sowCount == 0 && feedDate.getTime() <= groupStartDate.getTime())
					{
						Map<String, Object> detailsMap = groupEventDao.getStartWtAndHead(groupId);
						if(detailsMap != null && detailsMap.get("StartHd") != null)
						{
							sowCount = ((Long)detailsMap.get("StartHd")).intValue();
						}
					}
					budgetedFeed += sowCount*feedPerKgPerPigPerDay*feedNumberofDays*(feedCost/weight);
				}
			}
		}
		
		return budgetedFeed;
		}
	

	   
}
