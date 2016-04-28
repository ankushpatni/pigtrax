package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.report.bean.ActionListReportBean;


@Repository
@Transactional
public class ActionListReportDao {
	
	private static final Logger logger = Logger.getLogger(ActionListReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ActionListReportBean> getActionList(final Integer premiseId) {
		
		String query = "SELECT  "
				+" T.\"id_PigInfo\"," 
				+" T.\"pigId\" as \"Sow Id\", " 
				+" T.\"parity\", " 
				+" CASE WHEN t.\"Sow Phase\" = 'EntryEvent' THEN T.\"Age\" ELSE 0 END as \"Age\", " 
				+" T.\"serviceGroupId\" as \"Service Group\",  "
				+" T.\"Sow Phase Date\", " 
				+" T.\"Sow Phase\", " 
				+" T.\"roomId\", " 
				+" T.\"penId\", " 
				+" T.\"currentServiceDate\" as \"Current Service Date\", " 
				+" T.\"fieldDescription\" as \"Pregnancy Event Type\", " 
				+" T.\"resultDate\" as \"Pregnancy Event Date\", "
				+" CASE WHEN (t.\"Sow Phase\" <> 'BreedingEvent' AND t.\"Sow Phase\" <> 'EntryEvent' AND t.\"Sow Phase\" <> 'PrengancyEvent') THEN T.\"ServNo\" ELSE 0 END as \"ServNo\", "
				+" CASE WHEN t.\"Sow Phase\" = 'BreedingEvent' OR t.\"Sow Phase\" = 'PregnancyEvent' " 
				+" THEN " 
				+" (CASE WHEN t.\"parity\" = 0 "  
				+" THEN (t.\"currentServiceDate\"::date+115*interval '1' day) " 
				+" ELSE (t.\"currentServiceDate\"::date + (T.\"lastGestationLength\"*interval '1' day)) " 
				+" END) " 
				+" ELSE NULL " 
				+" END as \"Due Date Anticipated\", "	 
				+" T.\"farrowDateTime\" as \"Farrow Date\", "
				+" CASE WHEN T.\"Sow Phase\" = 'EntryEvent' THEN (current_date-T.\"Sow Phase Date\"::date -3) ELSE "
				+ "CASE WHEN T.\"Sow Phase\"='BreedingEvent' THEN (current_date-T.\"Sow Phase Date\"::date - 115) ELSE "
				+ "CASE WHEN T.\"Sow Phase\" = 'PregnancyEvent' THEN (current_date-T.\"Sow Phase Date\"::date - 3) ELSE "
				+ "CASE WHEN T.\"Sow Phase\" = 'Farrow' THEN (current_date-T.\"Sow Phase Date\"::date - 30) ELSE "
				+ "CASE WHEN T.\"Sow Phase\" = 'Wean' OR T.\"Sow Phase\" = 'Transfer' OR T.\"Sow Phase\" = 'Mortality' THEN (current_date-T.\"Sow Phase Date\"::date - 5) ELSE (current_date-T.\"Sow Phase Date\"::date) END END END END END \"OverDue\", "
				+" T.\"Avg Gestlength\", "
				+" T.\"Lactating days\" "
				+" FROM " 
				+" (SELECT " 
				+" PEM.\"id_PigInfo\", "
				+" PI.\"pigId\", " 
				+" PI.\"parity\",current_date-(CASE WHEN \"birthDate\"IS NOT NULL THEN \"birthDate\" ELSE \"entryDate\" END)::date as \"Age\", "
				+" BE.\"serviceGroupId\" , "
				+" PEM.\"eventTime\" as \"Sow Phase Date\", "	
				+" CASE WHEN PEM.\"id_SalesEventDetails\" > 0 THEN 'SalesEvent' ELSE "
			    +" CASE WHEN PEM.\"id_RemovalEventExceptSalesDetails\" > 0 THEN 'RemovalEvent' "
			    +" ELSE "
			    +" CASE WHEN PEM.\"id_PigletStatus\" > 0 THEN "
			    + " CASE WHEN PS.\"id_PigletStatusEventType\" = 2 THEN 'Transfer' ELSE CASE WHEN PS.\"id_PigletStatusEventType\" = 3 THEN 'Wean' ELSE  CASE WHEN PS.\"id_PigletStatusEventType\" = 4 THEN 'Piglet Mortality' ELSE 'Foster In' END END END"
			    +" ELSE "
				+" CASE WHEN PEM.\"id_FarrowEvent\" > 0 THEN 'Farrow' " 
				+" ELSE  CASE WHEN PEM.\"id_PregnancyEvent\" > 0 THEN 'PregnancyEvent' "
				+" ELSE CASE WHEN PEM.\"id_BreedingEvent\" > 0 THEN 'BreedingEvent' " 
				+" ELSE 'EntryEvent' END END END END END END AS \"Sow Phase\", "
				+" R.\"roomId\", " 
				+" PI.\"id_Room\", " 
				+" PEN.\"penId\", "
				+" PET.\"fieldDescription\", "
				+" PE.\"resultDate\", "
				+" FE.\"farrowDateTime\", "	
				+" (CASE WHEN PEM.\"id_BreedingEvent\" >0 THEN BE.\"serviceStartDate\" ELSE (CASE WHEN PEM.\"id_PregnancyEvent\">0 THEN SUB_BE1.\"serviceStartDate\" ELSE SUB_BE.\"serviceStartDate\" END) END) as \"currentServiceDate\", "    
				+" SUB_BE.\"serviceStartDate\"as \"farrowServiceDate\", "
				+" CASE WHEN PI.\"lastGestationLength\" IS NOT NULL AND PI.\"lastGestationLength\" > 0 THEN  PI.\"lastGestationLength\" ELSE 115 END as \"lastGestationLength\", "
				+" CASE WHEN PEM.\"id_FarrowEvent\">0 THEN (FE.\"farrowDateTime\":: date - SUB_BE.\"serviceStartDate\" ::date) ELSE 0 END as \"Avg Gestlength\", "
				+" CASE WHEN PEM.\"id_FarrowEvent\">0 THEN (current_date - FE.\"farrowDateTime\" ::date) ELSE 0 END as \"Lactating days\", "
				+" BE_PAR.\"ServNo\" "	
				+" FROM " 
				+" pigtrax.\"PigTraxEventMaster\" PEM  "
				+" JOIN pigtrax.\"PigInfo\" PI ON PEM.\"id_PigInfo\" = PI.\"id\" "
				+" LEFT JOIN pigtrax.\"Room\" R on PI.\"id_Room\" = R.\"id\" "	
				+" LEFT JOIN pigtrax.\"PigletStatus\" PS on PEM.\"id_PigletStatus\" = PS.\"id\" "
				+" LEFT JOIN pigtrax.\"FarrowEvent\" FE on PEM.\"id_FarrowEvent\" = FE.\"id\" " 
				+" LEFT JOIN pigtrax.\"Pen\" PEN on FE.\"id_Pen\" = PEN.\"id\" "
				+" LEFT JOIN pigtrax.\"PregnancyEvent\" PE on PEM.\"id_PregnancyEvent\" = PE.\"id\" "
				+" LEFT JOIN pigtraxrefdata.\"PregnancyEventType\" PET on PE.\"id_PregnancyEventType\" = PET.\"id\" "
				+" LEFT JOIN pigtrax.\"BreedingEvent\" BE on PEM.\"id_BreedingEvent\" = BE.\"id\" "
				+" LEFT JOIN (SELECT \"id\", \"serviceStartDate\" from pigtrax.\"BreedingEvent\" where \"id\" in (select max(\"id\") from pigtrax.\"BreedingEvent\" group by \"id_PigInfo\")) SUB_BE on FE.\"id_BreedingEvent\" = SUB_BE.\"id\" "
				+" LEFT JOIN (SELECT \"id\", \"serviceStartDate\" from pigtrax.\"BreedingEvent\" where \"id\" in (select max(\"id\") from pigtrax.\"BreedingEvent\" group by \"id_PigInfo\")) SUB_BE1 on PE.\"id_BreedingEvent\" = SUB_BE1.\"id\" "
				+" LEFT JOIN (select \"id_PigInfo\", \"currentParity\", count(\"currentParity\") as \"ServNo\" "
				+ "		FROM pigtrax.\"BreedingEvent\" where \"serviceStartDate\" IS NOT NULL group by \"id_PigInfo\",\"currentParity\") BE_PAR ON PEM.\"id_PigInfo\" = BE_PAR.\"id_PigInfo\" and PI.\"parity\" = BE_PAR.\"currentParity\" "    
				+" WHERE "
				+" PEM.\"id\" in " 
				+" (select distinct on (PEM1.\"id_PigInfo\")  PEM1.\"id\" from pigtrax.\"PigTraxEventMaster\" PEM1 "
				+ "JOIN pigtrax.\"PigInfo\" PI ON PEM1.\"id_PigInfo\" = PI.\"id\" and PI.\"id_Premise\"=?  "
				+ " order by PEM1.\"id_PigInfo\", PEM1.\"eventTime\" desc, PEM1.\"id\" desc) " 
				+" order by PEM.\"id_PigInfo\") T ";


		List<ActionListReportBean> actionListReport = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premiseId);
			}}, new ActionListReportMapper());
		
		return actionListReport;
	}
	
	private static final class ActionListReportMapper implements RowMapper<ActionListReportBean> {
		public ActionListReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			ActionListReportBean actionListReportBean = new ActionListReportBean();
			actionListReportBean.setPigId(rs.getString("Sow Id"));
			actionListReportBean.setParity(rs.getInt("parity"));
			actionListReportBean.setAge(rs.getInt("Age"));
			actionListReportBean.setSowPhaseDate(rs.getDate("Sow Phase Date"));
			actionListReportBean.setSowPhase(rs.getString("Sow Phase"));
			actionListReportBean.setRoomId(rs.getString("roomId"));
			actionListReportBean.setPenId(rs.getString("penId"));
			actionListReportBean.setPregnancyEventType(rs.getString("Pregnancy Event Type"));
			actionListReportBean.setPregnancyEventDate(rs.getDate("Pregnancy Event Date"));
			actionListReportBean.setDueDateAnticipated(rs.getDate("Due Date Anticipated"));
			actionListReportBean.setGestationLength(rs.getInt("Avg Gestlength"));
			actionListReportBean.setLactatingDays(rs.getInt("Lactating days"));
			actionListReportBean.setOverDue(rs.getInt("OverDue"));
			actionListReportBean.setServiceGroupId(rs.getString("Service Group"));
			actionListReportBean.setServNum(rs.getInt("ServNo"));
			return actionListReportBean;
		}
	}

	
	public Map<String, String> getActionListTargets(final Integer premiseId)
	{
		String qry = "select TT.\"fieldDescription\",coalesce(CT.\"targetValue\",'0') as \"targetValue\" from pigtraxrefdata.\"TargetType\" TT  LEFT JOIN pigtrax.\"CompanyTarget\" CT"
				+ "   ON CT.\"id_TargetType\" = TT.\"id\" and  CT.\"id_Premise\" = ?   WHERE   TT.\"fieldDescription\" in "
				+ "('Arrival to 1st serv interval','Avg gest','Avg weaning age','Wean to 1st service interval')";
		@SuppressWarnings("unchecked")
		Map<String, String> actionListReport = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premiseId);
			}}, new ResultSetExtractor<Map>(){
			    @Override
			    public Map extractData(ResultSet rs) throws SQLException,DataAccessException {
			        HashMap<String,String> mapRet= new HashMap<String,String>();
			        while(rs.next()){
			        	
			        	String fieldDesc = rs.getString("fieldDescription");
			        	if("Arrival to 1st serv interval".equalsIgnoreCase(fieldDesc))
			        	{
			        		mapRet.put("EntryEvent",rs.getString("targetValue"));
			        	}
			        	else if("Avg gest".equalsIgnoreCase(fieldDesc))
			        	{
			        	    mapRet.put("BreedingEvent",rs.getString("targetValue"));
			        	}
			        	else if("Avg weaning age".equalsIgnoreCase(fieldDesc))
			        	{
			        		mapRet.put("Farrow",rs.getString("targetValue"));
			        	}
			        	else if("Wean to 1st service interval".equalsIgnoreCase(fieldDesc))
			        	{
			        		mapRet.put("Wean",rs.getString("targetValue"));
			        	}
			        	
			        }
			        return mapRet;
			    }
			});
		
		
		return actionListReport;
		
	}
	
	

}
