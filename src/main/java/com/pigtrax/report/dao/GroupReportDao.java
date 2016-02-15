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

}
