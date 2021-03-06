package com.pigtrax.report.dao;

import java.sql.Date;
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

import com.pigtrax.master.dao.BarnDaoImpl;
import com.pigtrax.report.bean.RemovalReportBean;

@Repository
@Transactional
public class RemovalReportDao {
	
	private static final Logger logger = Logger.getLogger(RemovalReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<RemovalReportBean> getRemovalList(final int premisesId, final int pigId,final int groupId, final Date start, final Date end) {
		
		String parentQuery = " Select T.\"name\", T.\"groupId\", T.\"pigId\", T.\"removalDateTime\", T.\"numberOfPigs\", T.\"averageDOF\", T.\"averageWeight\", T.\"parity\", "
				+ " T.\"mortalityReason\", T.\"Last Phase\", T.\"penId\", T.\"barnId\", T.\"roomId\" from ( ";
		
		String query = "( select P.\"name\",GE.\"groupId\", PI.\"pigId\", RES.\"removalDateTime\", RES.\"numberOfPigs\", "
				 +"  case when( RES.\"id_GroupEvent\" is not null ) THEN  DATE_PART('day', RES.\"removalDateTime\"::timestamp - GE.\"groupStartDateTime\"::timestamp) else DATE_PART('day', RES.\"removalDateTime\"::timestamp - PI.\"entryDate\"::timestamp) END as \"averageDOF\", "
				 +"  case when( RES.\"id_GroupEvent\" is not null ) THEN  RES.\"weightInKgs\"/RES.\"numberOfPigs\" else RES.\"weightInKgs\" END as \"averageWeight\", "
				 +"  case when( RES.\"id_PigInfo\" is not null ) THEN  PI.\"parity\" else 0 END as \"parity\", "
				 +"  case when( RES.\"id_MortalityReason\" is not null ) THEN  MR.\"fieldDescription\" else '' END as \"mortalityReason\", "
				 +" CU_PH.\"Last Phase\", pen.\"penId\",BA.\"barnId\", RGR.\"roomId\"  "
				
				 +"  from pigtrax.\"RemovalEventExceptSalesDetails\" RES  "
				 +"  left JOIN pigtrax.\"GroupEvent\" GE ON RES.\"id_GroupEvent\" = GE.\"id\" "
				 +"  left JOIN pigtrax.\"PigInfo\" PI ON RES.\"id_PigInfo\" = PI.\"id\" "
				 +"  left join pigtrax.\"Premise\" P  ON RES.\"id_Premise\" = P.\"id\" "
				 +"  left join pigtrax.\"Premise\" P1  ON RES.\"id_DestPremise\" = P1.\"id\" "
				 +"  left join pigtrax.\"Room\" R ON RES.\"id_Room\" = R.\"id\" "
				 +"  left join pigtraxrefdata.\"MortalityReasonType\" MR ON RES.\"id_MortalityReason\" = MR.\"id\" "
				 +" left join (SELECT \"id_PigInfo\", CASE WHEN \"id_FarrowEvent\" IS NOT NULL THEN \"id_FarrowEvent\" else NULL END as \"id_FarrowEvent\", "
				 +"    CASE WHEN "
				 +"         \"id_FarrowEvent\" IS NOT NULL THEN 'Farrow' "
				 +"        ELSE CASE WHEN \"id_PregnancyEvent\" IS NOT NULL THEN 'Pregnancy'  "
				 +"        ELSE CASE WHEN \"id_BreedingEvent\" IS NOT NULL THEN 'Service'  "
				 +"        ELSE 'Entry' "
				 +"    END "
				 +"    END "
				 +"    END as \"Last Phase\" "
				 +" FROM ( "
				 +"   SELECT "
				 +"     ROW_NUMBER() OVER (PARTITION BY \"id_PigInfo\" ORDER BY \"id\" desc) AS rowNum, "
				 +"     PEM.* "
				 +"   FROM "
				 +"     pigtrax.\"PigTraxEventMaster\" PEM) events "
				 +" WHERE "
				 +"   events.rowNum = 2) CU_PH ON RES.\"id_PigInfo\" = CU_PH.\"id_PigInfo\" "
				 +"   left join pigtrax.\"FarrowEvent\" FE ON CU_PH.\"id_FarrowEvent\" = FE.\"id\" "
				 +"   left join pigtrax.\"Pen\" Pen ON FE.\"id_Pen\" = Pen.\"id\" "
				 + " left join (SELECT \"barnserialid\" as \"id\", \"penserialid\" as \"id_Pen\" from pigtrax.\"CompPremBarnRoomPenVw\" where \"barnId\" != '') BA_RN ON FE.\"id_Pen\" = BA_RN.\"id_Pen\" "
				 + "  left join pigtrax.\"Barn\" BA ON BA_RN.\"id\" = BA.\"id\" "
				 +  " left join (select GEPC.\"id_GroupEvent\", GER.\"id_Room\" from pigtrax.\"GroupEventRoom\" GER  "
				 +" left JOIN pigtrax.\"GroupEventPhaseChange\" GEPC ON GER.\"id_GroupEventPhaseChange\" = GEPC.\"id\" "
				 +" where GEPC.\"phaseEndDate\" is null) GR_ROOM ON RES.\"id_GroupEvent\" = GR_ROOM.\"id_GroupEvent\"  "
				 + "left join pigtrax.\"Room\" RGR ON GR_ROOM.\"id_Room\" =  RGR.\"id\" " 
				 +"  where (RES.\"id_RemovalEvent\" = 8 OR RES.\"id_RemovalEvent\" = 2 OR RES.\"id_RemovalEvent\" = 7) and RES.\"id_Premise\" = ? ";
		if(pigId != 0)
			query = query +"  and RES.\"id_PigInfo\" =  " +pigId ;
		if(groupId != 0)
			query = query +"  and RES.\"id_GroupEvent\" =  "+groupId ;
		if(start != null)
			query = query +"  and RES.\"removalDateTime\" >=  '"+ start+"'";
		if(end != null)
			query = query + " and RES.\"removalDateTime\" <= '"+ end+"'";
		
		query = query+")";
		
		String secondaryQuery =  "( select P.\"name\",'' as \"groupId\", PI.\"pigId\", PS.\"eventDateTime\" as \"removalDateTime\", PS.\"numberOfPigs\", "
				 + " case when( PS.\"id_GroupEvent\" is not null ) THEN  DATE_PART('day', PS.\"eventDateTime\"::timestamp - GE.\"groupStartDateTime\"::timestamp) else DATE_PART('day', PS.\"eventDateTime\"::timestamp - PI.\"entryDate\"::timestamp) END as \"averageDOF\", "
				 + " PS.\"weightInKgs\" as \"averageWeight\", "
				 + " case when( PS.\"id_PigInfo\" is not null ) THEN  PI.\"parity\" else 0 END as \"parity\", "
				 + " case when( PS.\"id_MortalityReasonType\" is not null ) THEN  MR.\"fieldDescription\" else '' END as \"mortalityReason\", "
				 + " case when( PS.\"id_MortalityReasonType\" is not null ) THEN  'Piglet Death' else (case when( PS.\"id_GroupEvent\" is not null) THEN 'Piglet Transfer' else (case when( PS.\"fosterTo\" is not null) THEN 'Piglet Wean' else '' END) END) END as \"Last Phase\", "
				 + " PEN.\"penId\", "
				 + " case when (PS.\"id_Pen\" is not null) THEN BA.\"barnId\" else '' END as \"barnId\", '' as \"roomId\" "
				 
				 + " from pigtrax.\"PigletStatus\" PS "
				 + " left JOIN pigtrax.\"GroupEvent\" GE ON PS.\"id_GroupEvent\" = GE.\"id\"    " 
				 + " left JOIN pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\"  "
				 + " left join pigtrax.\"Premise\" P  ON PS.\"id_Premise\" = P.\"id\" "
				 + " left join pigtrax.\"Pen\" PEN  ON PS.\"id_Pen\" = PEN.\"id\" "
				 + " left join (SELECT \"barnserialid\" as \"id\", \"penserialid\" as \"id_Pen\" from pigtrax.\"CompPremBarnRoomPenVw\" where \"barnId\" != '') BA_RN ON PS.\"id_Pen\" = BA_RN.\"id_Pen\" "
				 + " left join pigtrax.\"Barn\" BA ON BA_RN.\"id\" = BA.\"id\" "
				 + " left join pigtraxrefdata.\"MortalityReasonType\" MR ON PS.\"id_MortalityReasonType\" = MR.\"id\" WHERE PS.\"id_Premise\" = ?";
		if(pigId != 0)
			secondaryQuery = secondaryQuery +"  and PS.\"id_PigInfo\" =  " +pigId ;
		if(start != null)
			secondaryQuery = secondaryQuery +"  and PS.\"eventDateTime\" >=  '"+ start+"'";
		if(end != null)
			secondaryQuery = secondaryQuery + " and PS.\"eventDateTime\" <= '"+ end+"'";
		
		secondaryQuery = secondaryQuery+" ) ";
		
		parentQuery = parentQuery +query + " UNION "+  secondaryQuery + " ) T order by T.\"removalDateTime\"::date asc ";
		
		List<RemovalReportBean> sowReportBeanList = jdbcTemplate.query(parentQuery, new PreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premisesId);
				ps.setInt(2, premisesId);
			}}, new RemovalReportMapper());
		
		return sowReportBeanList;
	
	}
	
	private static final class RemovalReportMapper implements RowMapper<RemovalReportBean> {
		public RemovalReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			RemovalReportBean removalReportBean = new RemovalReportBean();
			
			removalReportBean.setPremise(rs.getString("name"));
			removalReportBean.setGroupID(rs.getString("groupId"));
			removalReportBean.setPigID(rs.getString("pigId"));
			removalReportBean.setRemovalDate(rs.getDate("removalDateTime"));
			removalReportBean.setNumberPigsRemoved(rs.getInt("numberOfPigs"));
			removalReportBean.setAveDOF(rs.getInt("averageDOF"));
			removalReportBean.setAveWOF(rs.getInt("averageDOF")/7);
			removalReportBean.setAveWeight(rs.getFloat("averageWeight"));
			removalReportBean.setParity(rs.getInt("parity"));
			removalReportBean.setMortalityReason(rs.getString("mortalityReason"));
			removalReportBean.setpStatus(rs.getString("Last Phase"));
			removalReportBean.setPenID(rs.getString("penId"));
			removalReportBean.setBarnID(rs.getString("barnId"));
			removalReportBean.setRoomID(rs.getString("roomId"));
			
			
			return removalReportBean;
		}
	}
	
	
	private static final class RemovalReportMortalityReasonMapper implements RowMapper<RemovalReportBean> {
		public RemovalReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			RemovalReportBean removalReportBean = new RemovalReportBean();
			
			removalReportBean.setNumberPigsRemoved(rs.getInt("sum"));
			removalReportBean.setMortalityReason(rs.getString("mortalityReason"));
			
			return removalReportBean;
		}
	}
	
	
	public List<RemovalReportBean> getRemovalMortalityReasonList(final int premisesId, final Date start, final Date end, String animalType) {
		
		String query = null;
		String groupQuery =	" select sum(RES.\"numberOfPigs\"), case when( RES.\"id_MortalityReason\" is not null ) THEN  MR.\"fieldDescription\" else 'Unclassified' END as \"mortalityReason\"  "
				 + " from pigtrax.\"RemovalEventExceptSalesDetails\" RES LEFT OUTER JOIN pigtraxrefdata.\"MortalityReasonType\" MR ON RES.\"id_MortalityReason\" = MR.\"id\"  "
				 + " where  RES.\"id_GroupEvent\" is not null and  (RES.\"id_RemovalEvent\" = 2 or RES.\"id_RemovalEvent\" = 7 or RES.\"id_RemovalEvent\" = 8 )  and RES.\"id_Premise\" = ?" ;
		
		String pigQuery =	" select sum(RES.\"numberOfPigs\"), case when( RES.\"id_MortalityReason\" is not null ) THEN  MR.\"fieldDescription\" else 'UnClassified' END as \"mortalityReason\"  "
				 + " from pigtrax.\"RemovalEventExceptSalesDetails\" RES LEFT OUTER JOIN pigtraxrefdata.\"MortalityReasonType\" MR ON RES.\"id_MortalityReason\" = MR.\"id\"  "
				 + " where  RES.\"id_PigInfo\" is not null and  (RES.\"id_RemovalEvent\" = 2 or RES.\"id_RemovalEvent\" = 7 or RES.\"id_RemovalEvent\" = 8 )  and RES.\"id_Premise\" = ? " ;
		
		String pigLetStatusQuery = " select case when( RES.\"id_MortalityReasonType\" is not null ) THEN  MR.\"fieldDescription\" else 'Unclassified' END as \"mortalityReason\"  , "
									+" sum(RES.\"numberOfPigs\") from pigtrax.\"PigletStatus\" RES LEFT OUTER JOIN pigtraxrefdata.\"MortalityReasonType\" MR ON RES.\"id_MortalityReasonType\" = MR.\"id\"  "
									+" where RES.\"id_PigletStatusEventType\" = 4 and  RES.\"id_Premise\" = ?";
				 
		
		if(animalType.equalsIgnoreCase("group"))
		{
			query = groupQuery;
			if(start != null)
				query = query +"  and RES.\"removalDateTime\" >=  '"+ start+"'";
			if(end != null)
				query = query + " and RES.\"removalDateTime\" <= '"+ end+"'";
		}
		else if(animalType.equalsIgnoreCase("pig"))
		{
			query = pigQuery;
			if(start != null)
				query = query +"  and RES.\"removalDateTime\" >=  '"+ start+"'";
			if(end != null)
				query = query + " and RES.\"removalDateTime\" <= '"+ end+"'";
		}
		else
		{
			query = pigLetStatusQuery;
			
			if(start != null)
				query = query +"  and RES.\"eventDateTime\" >=  '"+ start+"'";
			if(end != null)
				query = query + " and RES.\"eventDateTime\" <= '"+ end+"'";
			
		}
		
		query = query + " group by \"mortalityReason\" order by \"mortalityReason\" " ;
		
		List<RemovalReportBean> sowReportBeanList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premisesId);
			}}, new RemovalReportMortalityReasonMapper());
		
		return sowReportBeanList;
	}

}
