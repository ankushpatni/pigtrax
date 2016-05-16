package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.report.bean.InventoryStatusBean;

@Repository
@Transactional
public class InventoryStatusDao {

	private static final Logger logger = Logger.getLogger(InventoryStatusDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<InventoryStatusBean> getInventoryList(final Integer premiseId)
	{
		List<InventoryStatusBean> inventoryStatusList = new ArrayList<InventoryStatusBean>();
		
		String qry="SELECT T.\"barnId\", T.\"Phase Type\", T.\"Group Id\", T.\"Animal Type\", T.\"Head\", T.\"DOF\" "
				+" FROM "
				+" (SELECT BN.\"barnId\" , " 
				+" CASE WHEN PEM.\"id_RemovalEventExceptSalesDetails\" > 0 THEN 'Farrow' ELSE "
				+" CASE WHEN PEM.\"id_PigletStatus\" > 0 THEN  "
				+ "  CASE WHEN PS.\"id_PigletStatusEventType\" = 2 THEN 'Farrow' ELSE CASE WHEN PS.\"id_PigletStatusEventType\" = 3 THEN 'Wean' ELSE CASE WHEN PS.\"id_PigletStatusEventType\" = 4 THEN 'Farrow' ELSE 'Farrow' END eND END "
				+" ELSE CASE WHEN PEM.\"id_FarrowEvent\" > 0 THEN 'Farrow' " 
				+" ELSE  CASE WHEN PEM.\"id_PregnancyEvent\" > 0 THEN 'BreedingEvent' "
				+" ELSE CASE WHEN PEM.\"id_BreedingEvent\" > 0 THEN 'BreedingEvent' " 
				+" ELSE 'EntryEvent' END END END END END AS \"Phase Type\", '' as \"Group Id\", "
				+" CASE WHEN PI.\"id_SexType\" = 2 THEN 'Sow' ELSE 'Boar' END as \"Animal Type\", count(1) as \"Head\", null as \"DOF\"	  "   
				+" FROM "
				+" pigtrax.\"PigTraxEventMaster\" PEM " 
				+" JOIN pigtrax.\"PigInfo\" PI ON PEM.\"id_PigInfo\" = PI.\"id\" "
				+" LEFT JOIN pigtrax.\"PigletStatus\" PS ON PEM.\"id_PigletStatus\" = PS.\"id\" "
				+" JOIN pigtrax.\"Premise\" P ON P.\"id\"=PI.\"id_Premise\" "
				+" LEFT JOIN pigtrax.\"Room\" R ON PI.\"id_Room\" = R.\"id\" "
				+" LEFT JOIN pigtrax.\"Barn\" BN ON BN.\"id\" = R.\"id_Barn\" "
				+" WHERE " 
				+" PEM.\"id\" in ( "
				+" SELECT max(PEM1.\"id\") " 
				+" FROM " 
				+" pigtrax.\"PigTraxEventMaster\" PEM1 " 
				+" JOIN pigtrax.\"PigInfo\" PI ON PEM1.\"id_PigInfo\" = PI.\"id\" and PI.\"id_Premise\"=? "
				+" group by PEM1.\"id_PigInfo\") " 	
				+" AND "	
				+" PI.\"isActive\" is true AND PI.\"id_Premise\" = ?" 
			//	+" GROUP BY PI.\"id_SexType\",  \"Sow Source\", \"Phase Type\", BN.\"barnId\" "
				+" GROUP BY PI.\"id_SexType\",   \"Phase Type\", BN.\"barnId\" "
				+" UNION ALL"
				+" SELECT  BN.\"barnId\" , PPT.\"fieldDescription\"  as \"Phase Type\", GE.\"groupId\" as \"Group Id\", "
					+ "'Pig' as \"Animal Type\", SUM(GED.\"numberOfPigs\") as \"Head\", current_date-GE.\"groupStartDateTime\"::date as \"DOF\" "
				+" FROM pigtrax.\"GroupEvent\" GE "
				+" JOIN pigtrax.\"GroupEventPhaseChange\" GEPC ON GE.\"id\" = GEPC.\"id_GroupEvent\" and GEPC.\"phaseEndDate\" is NULL "
				+" JOIN pigtraxrefdata.\"PhaseOfProductionType\" PPT ON GEPC.\"id_PhaseOfProductionType\" = PPT.\"id\" "
				+" LEFT JOIN pigtrax.\"GroupEventDetails\" GED ON GED.\"id_GroupEvent\" = GE.\"id\" "
				+" LEFT JOIN pigtrax.\"GroupEventRoom\" GER ON GER.\"id_GroupEventPhaseChange\" = GEPC.\"id\" "
				+" LEFT JOIN pigtrax.\"Room\" R ON GER.\"id_Room\" = R.\"id\" "
				+" LEFT JOIN pigtrax.\"Barn\" BN ON BN.\"id\" = R.\"id_Barn\"	 "
				+" LEFT JOIN pigtrax.\"Premise\" PR ON GED.\"id_SowSource\" = PR.\"id\" "	
				//+" LEFT JOIN (select FED.\"id_GroupEvent\", min(FED.\"feedEventDate\") as \"DOF\" from pigtrax.\"FeedEventDetails\" FED group by FED.\"id_GroupEvent\") FED ON FED.\"id_GroupEvent\" = GE.\"id\" "				
				+" WHERE " 
				+" GEPC.\"id_Premise\" = ? "
				+" GROUP BY \"Phase Type\", GE.\"groupId\", BN.\"barnId\" ,\"DOF\") T ORDER BY T.\"Animal Type\", T.\"Group Id\" ";

		 inventoryStatusList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premiseId);
				ps.setInt(2, premiseId);
				ps.setInt(3, premiseId);
			}}, new InventoryStatusReportMapper());
		
		return inventoryStatusList;
	}
	
	
	private static final class InventoryStatusReportMapper implements RowMapper<InventoryStatusBean> {
		public InventoryStatusBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			InventoryStatusBean inventoryStatusReportBean = new InventoryStatusBean();
			
		//	inventoryStatusReportBean.setSowSource(rs.getString("Sow Source"));
			inventoryStatusReportBean.setBarnId(rs.getString("barnId"));
			inventoryStatusReportBean.setPhaseType(rs.getString("Phase Type"));
			inventoryStatusReportBean.setGroupId(rs.getString("Group Id"));
			inventoryStatusReportBean.setAnimalType(rs.getString("Animal Type"));
			inventoryStatusReportBean.setHead(rs.getInt("Head"));
			inventoryStatusReportBean.setDateOfFeed(rs.getInt("DOF"));
			
			return inventoryStatusReportBean;
		}
	}
}
