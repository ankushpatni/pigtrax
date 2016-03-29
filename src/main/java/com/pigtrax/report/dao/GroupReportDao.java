package com.pigtrax.report.dao;

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

import com.pigtrax.report.bean.GroupReportBean;
import com.pigtrax.report.bean.GroupReportBeanwithPhase;


@Repository
@Transactional
public class GroupReportDao {
	
	private static final Logger logger = Logger.getLogger(GroupReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<GroupReportBean> getGroupList(final int groupId) {
		
		String query = "select PM.\"id\",PM.\"eventTime\", PM.\"id_GroupEvent\", GE.\"id_Premise\", GE.\"currentInventory\",GEPC.\"id_PhaseOfProductionType\","
				 +"PM.\"id_RemovalEventExceptSalesDetails\" , RES.\"id_DestPremise\" as RES_PREMISES , RES.\"id_Room\" as RES_ROOM,"
				 +"PM.\"id_SalesEventDetails\""
				 +"from pigtrax.\"PigTraxEventMaster\" PM "
				 +"left join  pigtrax.\"GroupEvent\" GE ON PM.\"id_GroupEvent\" = GE.\"id\""
				 +"left join  pigtrax.\"GroupEventPhaseChange\" GEPC ON PM.\"id_GroupEvent\" = GEPC.\"id_GroupEvent\""
				 +"left join pigtrax.\"RemovalEventExceptSalesDetails\" RES ON PM.\"id_RemovalEventExceptSalesDetails\" = RES.\"id\""
				 +"left join pigtrax.\"SalesEventDetails\" SE ON PM.\"id_SalesEventDetails\" = SE.\"id\""
				 +"where PM.\"id_GroupEvent\" = ? order by PM.\"eventTime\" ";


		List<GroupReportBean> groupReportGroupList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, groupId);
			}}, new GroupReportMapper());
		
		return groupReportGroupList;
	}
	
	private static final class GroupReportMapper implements RowMapper<GroupReportBean> {
		public GroupReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			GroupReportBean groupReportBean = new GroupReportBean();
			groupReportBean.setMasterEventId(rs.getInt("id"));
			groupReportBean.setEventDate(rs.getDate("eventTime"));
			groupReportBean.setGroupEventId(rs.getInt("id_GroupEvent"));
			groupReportBean.setGroupEventPremiseId(rs.getInt("id_Premise"));
			groupReportBean.setGroupPhaseOfProductionType(rs.getInt("id_PhaseOfProductionType"));
			groupReportBean.setRemovalEventExceptSalesDetailsId(rs.getInt("id_RemovalEventExceptSalesDetails"));
			groupReportBean.setRemovalEventExceptSalesDetailsPremisesId(rs.getInt("RES_PREMISES"));
			groupReportBean.setRemovalEventExceptSalesDetailsRoomId(rs.getInt("RES_ROOM"));
			groupReportBean.setSalesEventDetailsId(rs.getInt("id_SalesEventDetails"));
			groupReportBean.setGroupEventNumberOfPigs(rs.getInt("currentInventory"));
			return groupReportBean;
		}
	}
	
	
	public List<GroupReportBean> getNewGroupList(final int groupId) {
		
		String query = " select GED.\"dateOfEntry\", GED.\"numberOfPigs\", GED.\"weightInKgs\", GED.\"remarks\", RES.\"id_MortalityReason\",SE.\"id\" as salesId, "
				+" SE.\"ticketNumber\", SE.\"salesTypes\", SE.\"salesReasons\",RES.\"id\" as removalId, RES.\"id_RemovalEvent\",GE.\"groupStartDateTime\" "
				+" from pigtrax.\"GroupEventDetails\" GED  "
				+" left join pigtrax.\"RemovalEventExceptSalesDetails\" RES ON GED.\"id_RemovalEventExceptSalesDetails\" = RES.\"id\" "
				+" left join pigtrax.\"SalesEventDetails\" SE ON GED.\"id_SalesEventDetails\" = SE.\"id\" "
				+" left join pigtrax.\"GroupEvent\" GE  ON GED.\"id_GroupEvent\" = GE.\"id\"  "
				+" where GED.\"id_GroupEvent\" = ? order by GED.\"dateOfEntry\" asc ";


		List<GroupReportBean> groupReportGroupList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, groupId);
			}}, new GroupReportMapperNew());
		
		return groupReportGroupList;
	}
	
	private static final class GroupReportMapperNew implements RowMapper<GroupReportBean> {
		public GroupReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			GroupReportBean groupReportBean = new GroupReportBean();
			//groupReportBean.setMasterEventId(rs.getInt("id"));
			groupReportBean.setDateOfEntry(rs.getDate("dateOfEntry"));
			groupReportBean.setNumberOfPigs(rs.getInt("numberOfPigs"));
			groupReportBean.setWeightInKgs(rs.getDouble("weightInKgs"));
			groupReportBean.setRemarks(rs.getString("remarks"));
			groupReportBean.setMortalityReasonId(rs.getInt("id_MortalityReason"));
			groupReportBean.setSalesId(rs.getInt("salesId"));
			groupReportBean.setTicketNumber(rs.getString("ticketNumber"));
			groupReportBean.setSalesTypes(rs.getString("salesTypes"));
			groupReportBean.setSalesReasons(rs.getString("salesReasons"));
			groupReportBean.setGroupStartDateTime(rs.getDate("groupStartDateTime"));
			groupReportBean.setRemovalId(rs.getInt("removalId"));
			groupReportBean.setRemovalTypeId(rs.getInt("id_RemovalEvent"));
			return groupReportBean;
		}
	}
	
	public List<GroupReportBeanwithPhase> getGroupListWithPhaseDetails(final int groupId) {
	
	String query = 			" Select T.\"groupId\", T.\"Event Date\", T.\"Event Name\", T.\"Data\",T.\"RemovalType\",T.\"mortalityReason\", T.\"Ticketnumber\",T.\"salesTypes\",T.\"phaseChange\" from ( "
			+" (SELECT 1 as row, null, GE.\"groupId\" as \"groupId\", GE.\"groupStartDateTime\" as \"Event Date\", 'Begin Group' as \"Event Name\", 'Created' as \"Data\" "
			+" , '' as \"RemovalType\",'' as \"mortalityReason\", '' as \"Ticketnumber\", '' as \"salesTypes\",'' as \"phaseChange\" from pigtrax.\"GroupEvent\" GE where GE.\"id\"= ?) "
			+" UNION ALL "
			+" (SELECT  2 as row, RES.\"id_RemovalEvent\", GE.\"groupId\", GED.\"dateOfEntry\" as \"Event Date\",GED.\"remarks\" as \"Event Name\", "
			+" 'Start Hd : '|| GED.\"numberOfPigs\" || ' :: Weight :' || GED.\"weightInKgs\" as \"Data\", "
			+" case when( RES.\"id_RemovalEvent\" is not null ) THEN  RT.\"fieldDescription\" else '' END as \"RemovalType\", "
			+" case when( RES.\"id_MortalityReason\" is not null ) THEN  MR.\"fieldDescription\" else '' END as \"mortalityReason\", "
			+" case when( SED.\"ticketNumber\" is not null ) THEN  SED.\"ticketNumber\" else '' END as \"Ticketnumber\", "
			+" case when( SED.\"salesTypes\" is not null ) THEN  SED.\"salesTypes\" else '' END as \"salesTypes\", "
			+" '' as \"phaseChange\" "
			+" from pigtrax.\"GroupEvent\" GE   "
			 +" JOIN pigtrax.\"GroupEventDetails\" GED ON GE.\"id\" = GED.\"id_GroupEvent\" "
			 +" left join pigtrax.\"RemovalEventExceptSalesDetails\" RES ON GED.\"id_RemovalEventExceptSalesDetails\" = RES.\"id\"   "
			 +" left join pigtraxrefdata.\"RemovalType\" RT  ON RES.\"id_RemovalEvent\" = RT.\"id\"  "
			 +" left join pigtraxrefdata.\"MortalityReasonType\" MR ON RES.\"id_MortalityReason\" = MR.\"id\" "
			 +" left join pigtrax.\"SalesEventDetails\" SED ON GED.\"id_SalesEventDetails\" = SED.\"id\" "
			 +" where GE.\"id\" = ? and (RES.\"id_RemovalEvent\" IS NULL OR RES.\"id_RemovalEvent\" != 9) order by GED.\"dateOfEntry\" ) "
			 +" UNION ALL   "
			+" (SELECT  2 as row, null,  GE.\"groupId\", GEPC.\"phaseEndDate\" as \"Event Date\",'Phase Change' as \"Event Name\",  "
			 +" '' as \"Data\", '' as \"RemovalType\",'' as \"mortalityReason\", '' as \"Ticketnumber\", '' as \"salesTypes\", "
			 +" (select PPT.\"fieldDescription\" from pigtraxrefdata.\"PhaseOfProductionType\" PPT where GEPC.\"id_PhaseOfProductionType\" = PPT.\"id\") as \"phaseChange\" "
			 +" from pigtrax.\"GroupEvent\" GE   "
			 +" JOIN pigtrax.\"GroupEventPhaseChange\" GEPC ON GE.\"id\" = GEPC.\"id_GroupEvent\" "
			 +" and GEPC.\"phaseEndDate\" is NOT NULL  "
			  +" and GE.\"id\" = ?  order by GEPC.\"id\" ) "
			+" UNION ALL "
			+" ( select  2 as row,  RES.\"id_RemovalEvent\", GE.\"groupId\",  RES.\"removalDateTime\" as \"Event Date\", 'Transfer through Pig Movement' as \"Event Name\", "
			+" 'Start Hd : '|| RES.\"numberOfPigs\" || ' :: Weight :' || RES.\"weightInKgs\" || ' :: From Premises : ' || coalesce(p.\"name\",'') || ' :: To Premises : ' || coalesce(p1.\"name\",'') || ' :: To Room : ' || (case when( RES.\"id_Room\" is not null ) THEN  R.\"roomId\" else ''  END) as \"Data\", "
			 +" '' as \"RemovalType\",'' as \"mortalityReason\", '' as \"Ticketnumber\", '' as \"salesTypes\",'' as \"phaseChange\" "
			  +" from pigtrax.\"GroupEvent\" GE JOIN pigtrax.\"RemovalEventExceptSalesDetails\" RES ON GE.\"id\" = RES.\"id_GroupEvent\" and  "
			  +" GE.\"id\" = ? and RES.\"id_RemovalEvent\" = 9  "
			  +" left join pigtrax.\"Premise\" P  ON RES.\"id_Premise\" = P.\"id\" "
			  +" left join pigtrax.\"Premise\" P1  ON RES.\"id_DestPremise\" = P1.\"id\" "
			  +" left join pigtrax.\"Room\" R ON RES.\"id_Room\" = R.\"id\" ORDER BY RES.\"removalDateTime\" ) "
			+" ) T order by  T.\"Event Date\" asc ";
	
	List<GroupReportBeanwithPhase> groupReportGroupList = jdbcTemplate.query(query, new PreparedStatementSetter(){
		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, groupId);
			ps.setInt(2, groupId);
			ps.setInt(3, groupId);
			ps.setInt(4, groupId);
		}}, new GroupReportBeanwithPhaseMapper());
	
	return groupReportGroupList;
	
	}
	
	private static final class GroupReportBeanwithPhaseMapper implements RowMapper<GroupReportBeanwithPhase> {
		public GroupReportBeanwithPhase mapRow(ResultSet rs, int rowNum) throws SQLException {
			GroupReportBeanwithPhase groupReportBeanwithPhase = new GroupReportBeanwithPhase();
			groupReportBeanwithPhase.setGroupEventId(rs.getString("groupId"));
			groupReportBeanwithPhase.setEventDate(rs.getDate("Event Date"));
			groupReportBeanwithPhase.setEventName(rs.getString("Event Name"));
			groupReportBeanwithPhase.setData(rs.getString("Data"));
			groupReportBeanwithPhase.setRemovalType(rs.getString("RemovalType"));
			groupReportBeanwithPhase.setMortalityReason(rs.getString("mortalityReason"));
			groupReportBeanwithPhase.setTicketnumber(rs.getString("Ticketnumber"));
			groupReportBeanwithPhase.setSalesTypes(rs.getString("salesTypes"));
			groupReportBeanwithPhase.setPhaseChange(rs.getString("phaseChange"));
			
			return groupReportBeanwithPhase;
		}
	}
	

}
