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

import com.pigtrax.master.dao.BarnDaoImpl;
import com.pigtrax.report.bean.SowReportBean;

@Repository
@Transactional
public class SowReportDao {
	
	private static final Logger logger = Logger.getLogger(BarnDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	public List<SowReportBean> getSowList(final int sowId) {
		
		String query = "select PM.\"id\",PM.\"eventTime\", PM.\"id_PigInfo\",PI.\"id_Premise\",PI.\"id_Barn\",PI.\"id_Room\", "+
				"PM.\"id_BreedingEvent\", BE.\"id_Premise\" as BE_PREMISES ,BE.\"id_Pen\" as BE_PEN,"+
				" PM.\"id_PregnancyEvent\", PE.\"id_Premise\" as PE_PREMISES, "+
				" PM.\"id_FarrowEvent\" , FE.\"id_Premise\" as FE_PREMISES , FE.\"id_Pen\" as FE_PEN,"+
				" PM.\"id_PigletStatus\" , PS.\"id_Premise\" as PS_PREMISES , PS.\"id_Pen\" as PS_PEN,"+
				"PM.\"id_RemovalEventExceptSalesDetails\" , RES.\"id_DestPremise\" as RES_PREMISES , RES.\"id_Room\" as RES_ROOM,"+
				"PM.\"id_SalesEventDetails\"  "+
				",PI.remarks as PIRemarks, FE.remarks as FERemarks, PS.remarks as PSRemarks,RES.remarks as RESRemarks, SE.remarks as SERemarks "+


				"from pigtrax.\"PigTraxEventMaster\" PM "+

				"join  pigtrax.\"PigInfo\" PI ON PM.\"id_PigInfo\" = PI.\"id\" "+

				"left join  pigtrax.\"BreedingEvent\" BE ON PM.\"id_BreedingEvent\" = BE.\"id\" "+

				"left join  pigtrax.\"PregnancyEvent\" PE ON PM.\"id_PregnancyEvent\" = PE.\"id\" "+

				"left join  pigtrax.\"FarrowEvent\" FE ON PM.\"id_FarrowEvent\" = FE.\"id\" "+

				"left join pigtrax.\"PigletStatus\" PS ON PM.\"id_PigletStatus\" = PS.\"id\" "+

				"left join pigtrax.\"RemovalEventExceptSalesDetails\" RES ON PM.\"id_RemovalEventExceptSalesDetails\" = RES.\"id\" "+

				"left join pigtrax.\"SalesEventDetails\" SE ON PM.\"id_SalesEventDetails\" = SE.\"id\" "+
				
				"where PM.\"id_PigInfo\" = ? order by PM.\"eventTime\" ";


		List<SowReportBean> sowReportBeanList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, sowId);
			}}, new SowReportMapper());
		
		return sowReportBeanList;
	}
	

	private static final class SowReportMapper implements RowMapper<SowReportBean> {
		public SowReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			SowReportBean sowReportBean = new SowReportBean();
			sowReportBean.setMasterEventId(rs.getInt("id"));
			sowReportBean.setEventDate(rs.getDate("eventTime"));
			sowReportBean.setPigInfoId(rs.getInt("id_PigInfo"));
			sowReportBean.setPigInfoPremisesId(rs.getInt("id_Premise"));
			sowReportBean.setPigInfoBarn(rs.getInt("id_Barn"));
			sowReportBean.setPigInfoRoom(rs.getInt("id_Room"));
			sowReportBean.setBreedingEventId(rs.getInt("id_BreedingEvent"));
			sowReportBean.setBreedingEventPremisesId(rs.getInt("BE_PREMISES"));
			sowReportBean.setBreedingEventPenId(rs.getInt("BE_PEN"));
			sowReportBean.setPregnancyEventId(rs.getInt("id_PregnancyEvent"));
			sowReportBean.setPregnancyEventPremisesId(rs.getInt("PE_PREMISES"));
			sowReportBean.setFarrowEventId(rs.getInt("id_FarrowEvent"));
			sowReportBean.setFarrowEventPremisesId(rs.getInt("FE_PREMISES"));
			sowReportBean.setFarrowEventPenId(rs.getInt("FE_PEN"));
			sowReportBean.setPigletStatusId(rs.getInt("id_PigletStatus"));
			sowReportBean.setPigletStatusPremisesId(rs.getInt("PS_PREMISES"));
			sowReportBean.setPigletStatusPenId(rs.getInt("PS_PEN"));
			sowReportBean.setRemovalEventExceptSalesDetailsId(rs.getInt("id_RemovalEventExceptSalesDetails"));
			sowReportBean.setRemovalEventExceptSalesDetailsPremisesId(rs.getInt("RES_PREMISES"));
			sowReportBean.setRemovalEventExceptSalesDetailsRoomId(rs.getInt("RES_ROOM"));
			sowReportBean.setSalesEventDetailsId(rs.getInt("id_SalesEventDetails"));
			sowReportBean.setPigInfoRemarks(rs.getString("PIRemarks"));
			sowReportBean.setFarrowEventRemarks(rs.getString("FERemarks"));
			sowReportBean.setPigletStatusRemarks(rs.getString("PSRemarks"));
			sowReportBean.setRemovalRemarks(rs.getString("RESRemarks"));
			sowReportBean.setSalesRemarks(rs.getString("SERemarks"));
			return sowReportBean;
		}
	}
}
