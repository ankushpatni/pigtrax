package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		
		
		String qry = "SELECT MR.\"rationValue\" as \"rationId\", R.\"actualTons\" as \"actualTonsUsed\", R.\"tons\" as \"targetTonsUsed\", (R.\"actualTons\" - R.\"tons\")/R.\"tons\" as \"deviationTonsUsed\", "
				+ "	(R.\"actualTons\"*1000/R.\"pigNum\")/"+durationDays+" as \"actualKg\",R.\"kg/day\" as \"targetKg\", (((R.\"actualTons\"*1000/R.\"pigNum\")/"+durationDays+") - R.\"kg/day\")/R.\"kg/day\" as \"deviationKg\", "
				+ " R.\"actualCost\" as \"actualFeedCost\", R.\"feedCostTarget\" as \"targetFeedCost\", (R.\"actualCost\"-R.\"feedCostTarget\")/R.\"feedCostTarget\" as \"deviationFeedCost\" "
				+" FROM " 
				+" ( "
				+"   SELECT T.\"batchId\", (T.feedInWt-T.feedOutWt+T.feedOutWt)/1000 as \"actualTons\", T.\"tons\", "  
				+"		T.\"kg/day\",  (T.feedInCost-T.feedOutCost+T.feedAdjCost)/T.\"pigNum\" as \"actualCost\", T.\"feedCostTarget\", T.\"pigNum\" "
				+"    FROM ( "		
				+"     SELECT distinct FE.\"batchId\", coalesce(A.feedInWt,0) as feedInWt ,  coalesce(B.feedOutWt,0) as feedOutWt ,  coalesce(C.feedAdjWt,0) as feedAdjWt , "
				+"		coalesce(a.feedInCost,0) as feedInCost, " 
				+"		coalesce(b.feedOutCost,0) as feedOutCost,  coalesce(c.feedAdjCost,0) as feedAdjCost,  coalesce(a.num1,1) as \"pigNum\"  "
				+" ,  kg.\"kg/day\",tons.\"tons\", feedCostTarget.\"feedCostTarget\" "
				+" FROM " 
				+" pigtrax.\"FeedEventDetails\" FED " 
				+" JOIN pigtrax.\"FeedEvent\" FE On FED.\"id_FeedEvent\" = FE.\"id\" "
				+" JOIN pigtrax.\"Premise\" P ON P.\"id\" = FE.\"id_Premise\" "
				+" LEFT JOIN ( "
				+"  SELECT CT.\"id_Premise\", CAST(coalesce(CT.\"targetValue\", '0') AS integer) as \"kg/day\", 'Kg/Day' as ind from pigtrax.\"CompanyTarget\"  CT "
				+"  JOIN pigtraxrefdata.\"TargetType\" TT ON CT.\"id_TargetType\" = TT.\"id\" and TT.\"fieldDescription\" = 'Feed_kg/pig/day' "
				+" ) kg  ON P.\"id\" = kg.\"id_Premise\"  "		
				+" LEFT JOIN ( "
				+" select CT.\"id_Premise\",  CAST(coalesce(CT.\"targetValue\", '0') AS integer) as \"tons\", 'tonsUsed' as ind from pigtrax.\"CompanyTarget\"  CT "
				+" JOIN pigtraxrefdata.\"TargetType\" TT ON CT.\"id_TargetType\" = TT.\"id\" and TT.\"fieldDescription\" = 'Feed_tons used' "
				+" ) tons ON  P.\"id\" = tons.\"id_Premise\" "
				+" LEFT JOIN ( "
				+"   select CT.\"id_Premise\",  CAST(coalesce(CT.\"targetValue\", '0') AS integer) as \"feedCostTarget\", 'feedCostTarget' as ind from pigtrax.\"CompanyTarget\"  CT "
				+"	JOIN pigtraxrefdata.\"TargetType\" TT ON CT.\"id_TargetType\" = TT.\"id\" and TT.\"fieldDescription\" = 'Feed_Feed cost/pig' "
				+"	) feedCostTarget ON  P.\"id\" = tons.\"id_Premise\" "
				+"		LEFT JOIN " 
				+"(	"
				+"	SELECT sum(GE.\"currentInventory\") as num1, a.\"batchId\", sum(a.feedInWt) as feedInWt, sum(a.feedInCost) as feedInCost  from " 
				+"	pigtrax.\"GroupEvent\" GE " 
				+" JOIN( "
				+" select FED.\"id_GroupEvent\" ,FE.\"batchId\", sum(FED.\"weightInKgs\") as feedInWt,'FeedOut', sum(FED.\"feedCost\") as feedInCost from pigtrax.\"FeedEventDetails\" FED " 
				+" JOIN pigtrax.\"FeedEvent\"  FE on FED.\"id_FeedEvent\" = FE.\"id\" " 
				+" where FED.\"id_FeedEventType\"  = ? and FED.\"feedEventDate\" between ? and ? and FE.\"id_Premise\" = ? and FED.\"id_GroupEvent\" = ? "
				+" Group by  FED.\"id_GroupEvent\",FE.\"batchId\" ) a ON a.\"id_GroupEvent\" = GE.\"id\" "
				+" group by a.\"batchId\" "    
				+" ) A ON FE.\"batchId\" =  A.\"batchId\" "
				+" LEFT JOIN ( "
					+" select sum(GE.\"currentInventory\") as num2, a.\"batchId\", sum(a.feedOutWt) as feedOutWt, sum(a.feedOutCost) as feedOutCost  from "  
					+" pigtrax.\"GroupEvent\" GE "
					+" JOIN( "
					+" select FED.\"id_GroupEvent\" ,FE.\"batchId\", sum(FED.\"weightInKgs\") as feedOutWt,'FeedOut', sum(FED.\"feedCost\") as feedOutCost from pigtrax.\"FeedEventDetails\" FED " 
					+" JOIN pigtrax.\"FeedEvent\"  FE on FED.\"id_FeedEvent\" = FE.\"id\" " 
					+" where FED.\"id_FeedEventType\"  = ? and FED.\"feedEventDate\" between ? and ? and FE.\"id_Premise\" = ?  and FED.\"id_GroupEvent\" = ?"
					+" Group by  FED.\"id_GroupEvent\",FE.\"batchId\" ) a ON a.\"id_GroupEvent\" = GE.\"id\" "
					+" group by a.\"batchId\" "
					+" ) B ON FE.\"batchId\" = B.\"batchId\" "
					+" LEFT JOIN ( "
					+" select sum(GE.\"currentInventory\") as num3, a.\"batchId\", sum(a.feedAdjWt) as feedAdjWt, sum(a.feedAdjCost) as feedAdjCost  from " 
					+" pigtrax.\"GroupEvent\" GE "
					+" JOIN( "
					+" select FED.\"id_GroupEvent\" ,FE.\"batchId\", sum(FED.\"weightInKgs\") as feedAdjWt,'FeedOut', sum(FED.\"feedCost\") as feedAdjCost from pigtrax.\"FeedEventDetails\" FED " 
					+" JOIN pigtrax.\"FeedEvent\"  FE on FED.\"id_FeedEvent\" = FE.\"id\" " 
					+" where FED.\"id_FeedEventType\"  = ? and FED.\"feedEventDate\" between ? and ? and FE.\"id_Premise\" = ?  and FED.\"id_GroupEvent\" = ?"
					+" Group by  FED.\"id_GroupEvent\",FE.\"batchId\" ) a ON a.\"id_GroupEvent\" = GE.\"id\" "
					+" group by a.\"batchId\" "    
					+" ) C ON FE.\"batchId\" = C.\"batchId\" "
					+" WHERE  (A.\"batchId\" IS NOT NULL OR  B.\"batchId\" IS NOT NULL OR C.\"batchId\" IS NOT NULL)) T )R JOIN pigtrax.\"MasterRation\" MR ON MR.\"id\" = R.\"batchId\" ";
		
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
				
				ps.setInt(11, 4);
				ps.setDate(12, new java.sql.Date(startDate.getTime()));
				ps.setDate(13, new java.sql.Date(endDate.getTime()));
				ps.setInt(14, premiseId);
				ps.setInt(15, groupId);
				
			}}, new RationReportMapper());
		
		return rationReportList;
	}
	
	
	private static final class RationReportMapper implements RowMapper<RationReportBean> {
		public RationReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			RationReportBean rationReportBean = new RationReportBean();	
			rationReportBean.setRationId(rs.getString("rationId"));
			rationReportBean.setActualTonsUsed(rs.getDouble("actualTonsUsed"));
			rationReportBean.setTargetTonsUsed(rs.getDouble("targetTonsUsed"));
			rationReportBean.setDeviationTonsUsed(rs.getDouble("deviationTonsUsed"));
			rationReportBean.setActualKg(rs.getDouble("actualKg"));
			rationReportBean.setTargetKg(rs.getDouble("targetKg"));
			rationReportBean.setDeviationKg(rs.getDouble("deviationKg"));
			rationReportBean.setActualFeedCost(rs.getDouble("actualFeedCost"));
			rationReportBean.setTargetFeedCost(rs.getDouble("targetFeedCost"));
			rationReportBean.setDeviationFeedCost(rs.getDouble("deviationFeedCost"));
			return rationReportBean;
		}
	}
}
