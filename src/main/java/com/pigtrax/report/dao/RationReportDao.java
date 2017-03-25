package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.report.bean.RationReportBean;
import com.pigtrax.report.bean.RationReportFeedCostBean;

@Repository
@Transactional
public class RationReportDao {

	private static final Logger logger = Logger.getLogger(ProdEventLogDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<RationReportBean> getRationReportList(final Integer premiseId, final Date startDate, final Date endDate, final Integer groupId)
	{
		List<RationReportBean> rationReportList = new ArrayList<RationReportBean>();		
		
		int durationDays = 30;
		if(startDate != null && endDate != null)
		{
			DateTime startDateTime = new DateTime(startDate);
			DateTime endDateTime = new DateTime(endDate);		
			durationDays = Days.daysBetween(startDateTime.withTimeAtStartOfDay() , endDateTime.withTimeAtStartOfDay() ).getDays() ;
		}
		
		
		String qry = " SELECT distinct on (MR.\"rationValue\")"+
	" 		MR.\"rationValue\", RE.\"feedEventDate\",RE.\"batchId\", RE.\"actualTons\", RE.\"Target_Tons\", RE.\"deviationTons\",  "+
		" 	RE.\"actualKg\", RE.\"Target_Kg\", (RE.\"actualKg\"-RE.\"Target_Kg\") as \"deviationKg\", "+
			" RE.\"actualCost\", RE.\"feedCostTarget\",RE.\"deviationFeedCost\" , RE.\"pigNum\",  "+
			" RE.\"T1\", RE.\"T2\", RE.\"T3\", RE.\"TotalWt\"  "+
			" FROM ( "+
				" SELECT  "+
					" 	FR.\"feedEventDate\", FR.\"batchId\", FR.\"actualTons\", FR.\"Target_Tons\", (FR.\"actualTons\" - FR.\"Target_Tons\") as  \"deviationTons\",  "+
						" (FR.\"actualTons\"*1000/FR.\"pigNum\")/"+durationDays+" as \"actualKg\", FR.\"kg/day\"  as \"Target_Kg\",FR.\"actualCost\", FR.\"feedCostTarget\",  "+
						" (FR.\"actualCost\"-FR.\"feedCostTarget\") as \"deviationFeedCost\" , FR.\"pigNum\", FR.\"T1\",  FR.\"T2\", FR.\"T3\", FR.\"TotalWt\" "+
						" FROM (  "+
							" SELECT  "+ 
								" R.\"feedEventDate\" , R.\"batchId\", (R.\"feedInWt\"-R.\"feedOutWt\"+R.\"feedAdjWt\")/1000 as \"actualTons\", R.\"tons\" as \"Target_Tons\", R.\"kg/day\", (R.\"feedInCost\"-R.\"feedOutCost\"+R.\"feedAdjCost\")/R.\"pigNum\" as \"actualCost\"  "+
								" , R.\"feedCostTarget\"  "+
								" , R.\"pigNum\" ,R.\"T1\", R.\"T2\", R.\"T3\",  (R.\"feedInWt\"-R.\"feedOutWt\"+R.\"feedAdjWt\") as \"TotalWt\"  "+
							" FROM (  "+
								" SELECT  "+
									" 	T.\"feedEventDate\" , T.\"batchId\", T.\"feedInWt\", T.\"feedOutWt\", T.\"feedAdjWt\", T.\"feedInCost\",  T.\"feedOutCost\", T.\"feedAdjCost\", T.\"pigNum\", T.\"T1\", T.\"T2\", T.\"T3\",tons.\"tons\",  kg.\"kg/day\", feedCostTarget.\"feedCostTarget\"   "+
									" FROM   "+
									" ( "+
										" SELECT  "+
											" 	distinct FED.\"feedEventDate\" , FE.\"batchId\", coalesce(A.feedInWt,0) as \"feedInWt\"  "+
												" ,  coalesce(B.feedOutWt,0) as \"feedOutWt\" ,  "+
												" coalesce(C.feedAdjWt,0) as \"feedAdjWt\" ,  "+
												" coalesce(a.feedInCost,0) as \"feedInCost\",  "+
												" coalesce(b.feedOutCost,0) as \"feedOutCost\",   "+
												" coalesce(c.feedAdjCost,0) as \"feedAdjCost\",  "+
												" coalesce(a.num1,1) as \"pigNum\", A.\"T1\", B.\"T2\", C.\"T3\"	 "+			
										" FROM  "+
											" pigtrax.\"FeedEventDetails\" FED  "+
											" JOIN pigtrax.\"FeedEvent\" FE On FED.\"id_FeedEvent\" = FE.\"id\"  "+
											" JOIN pigtrax.\"Premise\" P ON P.\"id\" = FE.\"id_Premise\"  "+
										" LEFT JOIN  "+
										" (	 "+
											" SELECT sum(GE.\"currentInventory\") as num1, a.\"batchId\", sum(a.feedInWt) as feedInWt, sum(a.feedInCost) as feedInCost, a.\"T1\" as \"T1\" from  "+
											" pigtrax.\"GroupEvent\" GE  "+
											 " JOIN(  "+
											 " select FED.\"id_GroupEvent\" ,FE.\"batchId\", sum(FED.\"weightInKgs\") as  feedInWt,'FeedIn', sum(FED.\"feedCost\") as feedInCost, array_to_string(array_agg(FE.\"ticketNumber\"), ': ') as \"T1\" from  pigtrax.\"FeedEventDetails\" FED  "+
											 " JOIN pigtrax.\"FeedEvent\"  FE on FED.\"id_FeedEvent\" = FE.\"id\"  "+
											 " where FED.\"id_FeedEventType\"  = ? "
											 + "and FED.\"feedEventDate\" between ? and ? "
											 + " and FE.\"id_Premise\" = ? and FED.\"id_GroupEvent\" = ?  "+
											 " Group by  FED.\"id_GroupEvent\",FE.\"batchId\" ) a ON a.\"id_GroupEvent\" =  GE.\"id\"  "+
											 " group by a.\"batchId\", a.\"T1\"   "+
											 " ) A ON FE.\"batchId\" =  A.\"batchId\" "+
											 " LEFT JOIN (  "+
												 " select sum(GE.\"currentInventory\") as num2, a.\"batchId\", sum(a.feedOutWt)  as feedOutWt, sum(a.feedOutCost) as feedOutCost,  a.\"T2\" as \"T2\"  "+
												 " from    "+
												 " pigtrax.\"GroupEvent\" GE  "+
												 " JOIN( "+
												 " select FED.\"id_GroupEvent\" ,FE.\"batchId\", sum(FED.\"weightInKgs\") as feedOutWt,'FeedOut', sum(FED.\"feedCost\") as feedOutCost, array_to_string(array_agg(FE.\"ticketNumber\"), ': ') as \"T2\"  from pigtrax.\"FeedEventDetails\" FED  "+
												 " JOIN pigtrax.\"FeedEvent\"  FE on FED.\"id_FeedEvent\" = FE.\"id\"  "+
												 " where FED.\"id_FeedEventType\"  = ? "
												 + "and FED.\"feedEventDate\" between ? and ? "
												 + " and FE.\"id_Premise\" = ?  and FED.\"id_GroupEvent\" = ? "+
												 " Group by  FED.\"id_GroupEvent\",FE.\"batchId\" ) a ON a.\"id_GroupEvent\" = GE.\"id\"  "+
												 " group by a.\"batchId\" ,a.\"T2\"  "+
												 " ) B ON FE.\"batchId\" = B.\"batchId\"  "+
												" LEFT JOIN ( "+
												 " select sum(GE.\"currentInventory\") as num3, a.\"batchId\", sum(a.feedAdjWt) as feedAdjWt, sum(a.feedAdjCost) as feedAdjCost,  a.\"T3\" as \"T3\" from  "+
												 " pigtrax.\"GroupEvent\" GE  "+
												 " JOIN(  "+
												 " select FED.\"id_GroupEvent\" ,FE.\"batchId\", sum(FED.\"weightInKgs\") as feedAdjWt,'FeedOut', sum(FED.\"feedCost\") as feedAdjCost, array_to_string(array_agg(FE.\"ticketNumber\"), ':') as \"T3\"  from pigtrax.\"FeedEventDetails\" FED  "+
												 " JOIN pigtrax.\"FeedEvent\"  FE on FED.\"id_FeedEvent\" = FE.\"id\"  "+
												 " where FED.\"id_FeedEventType\"  = ? "
												 + "and FED.\"feedEventDate\" between ? and ? "
												 + " and FE.\"id_Premise\" = ?  and FED.\"id_GroupEvent\" = ? "+
												 " Group by  FED.\"id_GroupEvent\",FE.\"batchId\" ) a ON a.\"id_GroupEvent\" = GE.\"id\"  "+
												 " group by a.\"batchId\", a.\"T3\"   "+
												 " ) C ON FE.\"batchId\" = C.\"batchId\"  "+
											" WHERE P.\"id\" = ? and FED.\"id_GroupEvent\" = ? and  (A.\"batchId\" IS NOT NULL OR  B.\"batchId\" IS NOT NULL OR C.\"batchId\" IS NOT NULL)) T "+
										" LEFT JOIN (  "+
										 " select CT.\"id_Ration\" , CT.\"id_Premise\",  CAST(coalesce(CT.\"targetValue\", '0') AS decimal) as \"tons\", 'tonsUsed' as ind from pigtrax.\"CompanyTarget\"  CT  "+
										 " JOIN pigtraxrefdata.\"TargetType\" TT ON CT.\"id_TargetType\" = TT.\"id\" and TT.\"fieldDescription\" = 'ADFI(Average Daily Feed Intake)' and CT.\"id_Premise\" = ? "+
										 " ) tons ON  T.\"batchId\" = tons.\"id_Ration\" "+
										 " LEFT JOIN ( "+
										  " SELECT CT.\"id_Ration\", CT.\"id_Premise\", CAST(coalesce(CT.\"targetValue\", '0') AS decimal) as \"kg/day\", 'Kg/Day' as ind from pigtrax.\"CompanyTarget\"  CT  "+
										  " JOIN pigtraxrefdata.\"TargetType\" TT ON CT.\"id_TargetType\" = TT.\"id\" and TT.\"fieldDescription\" = 'DOF(Days on Feed)'  and CT.\"id_Premise\" = ? "+
										 " ) kg  ON T.\"batchId\" = kg.\"id_Ration\"  "+
										 " LEFT JOIN ( "+
										   " select CT.\"id_Ration\",CT.\"id_Premise\",  CAST(coalesce(CT.\"targetValue\", '0') AS decimal) as \"feedCostTarget\", 'feedCostTarget' as ind from pigtrax.\"CompanyTarget\"  CT  "+
											" JOIN pigtraxrefdata.\"TargetType\" TT ON CT.\"id_TargetType\" = TT.\"id\" and TT.\"fieldDescription\" = 'Feed_Feed cost/pig'   and CT.\"id_Premise\" = ? "+
											" ) feedCostTarget ON  T.\"batchId\" =feedCostTarget.\"id_Ration\" "+
									" ) R  "+
							" ) FR  "+
					" ) RE JOIN pigtrax.\"MasterRation\" MR ON MR.\"id\" = RE.\"batchId\"	"+
				" order by MR.\"rationValue\"  ";
		
		final int duration = durationDays;
		rationReportList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, 1);
				ps.setDate(2, new java.sql.Date(startDate.getTime()));
				ps.setDate(3, new java.sql.Date(endDate.getTime()));
				ps.setInt(4, premiseId);
				ps.setInt(5, groupId);
				
				ps.setInt(6, 2);
				ps.setDate(7, new java.sql.Date(startDate.getTime()));
				ps.setDate(8, new java.sql.Date(endDate.getTime()));
				ps.setInt(9, premiseId);
				ps.setInt(10, groupId);
				
				ps.setInt(11, 3);
				ps.setDate(12, new java.sql.Date(startDate.getTime()));
				ps.setDate(13, new java.sql.Date(endDate.getTime()));
				ps.setInt(14, premiseId);
				ps.setInt(15, groupId);
				
				ps.setInt(16, premiseId);
				ps.setInt(17, groupId);
				ps.setInt(18, premiseId);
				ps.setInt(19, premiseId);
				ps.setInt(20, premiseId);
				
				
				
			}}, new RationReportMapper());
		
		return rationReportList;
	}
	
	
	private static final class RationReportMapper implements RowMapper<RationReportBean> {
		public RationReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			RationReportBean rationReportBean = new RationReportBean();	
			rationReportBean.setRationId(rs.getString("rationValue"));
			rationReportBean.setActualTonsUsed(rs.getDouble("actualTons"));
			rationReportBean.setTargetTonsUsed(rs.getDouble("Target_Tons"));
			rationReportBean.setDeviationTonsUsed(rs.getDouble("deviationTons"));
			rationReportBean.setActualKg(rs.getDouble("actualKg"));
			rationReportBean.setTargetKg(rs.getDouble("Target_Kg"));
			rationReportBean.setDeviationKg(rs.getDouble("deviationKg"));
			rationReportBean.setActualFeedCost(rs.getDouble("actualCost"));
			rationReportBean.setTargetFeedCost(rs.getDouble("feedCostTarget"));
			rationReportBean.setDeviationFeedCost(rs.getDouble("deviationFeedCost"));
			rationReportBean.setTicketNum1(rs.getString("T1"));
			rationReportBean.setTicketNum2(rs.getString("T2"));
			rationReportBean.setTicketNum3(rs.getString("T3")); 
			rationReportBean.setTotalWeight(rs.getDouble("TotalWt"));
			rationReportBean.setFeedEvenDate(rs.getDate("feedEventDate"));
			return rationReportBean;
		}
	}


	private static class RationReportFeedCostMapper implements RowMapper<RationReportFeedCostBean>{

		public RationReportFeedCostBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			RationReportFeedCostBean bean = new RationReportFeedCostBean();
			bean.setWtdAvg(rs.getDouble(1));
			bean.setRationId(rs.getString(2));
			
			return bean;
		}
		
	}
	public List<RationReportFeedCostBean> getFeedCostAndWeight(final Integer groupId) {

		List<RationReportFeedCostBean> rationReportList = new ArrayList<RationReportFeedCostBean>();		
		
		String qry = "select avg(A.\"feedCost\"/A.\"weightInKgs\") wtdAvg,A.\"id_Ration\" "
				+ "from "
				+ "(select FED.\"feedCost\",FED.\"weightInKgs\",CT.\"id_Ration\",FED.\"feedEventDate\" "
				+ "from pigtrax.\"FeedEvent\" FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\" "
				+ " JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\"  "
				+ "LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" = 112   "
				+ "where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = ?  "
				+ "order by CT.\"id_Ration\" ) "
				+ "A group by A.\"id_Ration\" ";
		
/*		String qry = " select FED.\"weightInKgs\",FED.\"feedCost\",CT.\"id_Ration\",CT.\"id_Premise\",FED.\"feedEventDate\", FD.\"batchId\", "
				+ "CT.\"targetValue\",GE.\"groupStartDateTime\""
				+ " from pigtrax.\"FeedEvent\" FD Join  pigtrax.\"FeedEventDetails\" FED ON FED.\"id_FeedEvent\" = FD.\"id\"  "
				+ " JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\"  "
				+ " LEFT JOIN pigtrax.\"CompanyTarget\" CT On FD.\"id_Premise\" = CT.\"id_Premise\" and FD.\"batchId\" = CT.\"id_Ration\" AND CT.\"id_TargetType\" = 112  "
				+ " where \"id_FeedEventType\" = 1 and \"id_GroupEvent\" = ? "
				+ " order by \"feedEventDate\"";
*/		

		
		rationReportList = jdbcTemplate.query(qry, new PreparedStatementSetter(){ 
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, groupId);
			}}, new RationReportFeedCostMapper());
		
		return rationReportList;
	
	}

	public List<Map<String,Date>> getStartEndDates(Integer groupId) {
		// TODO Auto-generated method stub
		List<Map<String,Date>> startEndDateList = new ArrayList<Map<String,Date>>();
		String qry = "select min(\"dateOfEntry\") startDate,max(\"dateOfEntry\") endDate from pigtrax.\"GroupEventDetails\" where \"id_GroupEvent\"="+groupId;
		startEndDateList = (List<Map<String,Date>>)jdbcTemplate.query(qry, new PigGroupEventDatesMapper());
		return startEndDateList;
	}
	

	private static final class PigGroupEventDatesMapper implements RowMapper<Map<String, Date>>{

		public Map<String, Date> mapRow(ResultSet rs, int rowNum) throws SQLException {

			Map<String,Date> startEndDateMap = new HashMap<String,Date>();
			startEndDateMap.put("startDate",rs.getDate(1));
			startEndDateMap.put("endDate",rs.getDate(2));
			return startEndDateMap;
		}
		
	}
}
