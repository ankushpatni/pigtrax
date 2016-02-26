package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.report.bean.LactationLengthBean;
import com.pigtrax.report.bean.PigletMortalityReportBean;

@Repository
@Transactional
public class PigletMortalityReportDao {

	private static final Logger logger = Logger.getLogger(PigletMortalityReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<PigletMortalityReportBean> getPigletMortalityList(final Integer premiseId, final Date startDate, final Date endDate)
	{
		List<PigletMortalityReportBean> pigletMortalityList = new ArrayList<PigletMortalityReportBean>();
		
		String qry=" SELECT BN.\"barnId\", R.\"roomId\", current_date-FE.\"farrowDateTime\":: date as \"lactationDays\", "
				 +"	SUM(PS.\"numberOfPigs\") as \"Number of Deaths\", MRT.\"fieldDescription\",PS.\"id_Pen\" " 
				 +" FROM pigtrax.\"PigletStatus\" PS "
				 +" JOIN pigtrax.\"FarrowEvent\" FE On PS.\"id_FarrowEvent\" = FE.\"id\" "
				 +" LEFT JOIN pigtrax.\"Pen\" PEN ON PS.\"id_Pen\" = PEN.\"id\" "
				 +" LEFT JOIN pigtrax.\"Room\" R ON PEN.\"id_Room\" = R.\"id\" "
				 +" LEFT JOIN pigtrax.\"Barn\" BN ON BN.\"id\" = R.\"id_Barn\" "				 
				 +" LEFT JOIN pigtraxrefdata.\"MortalityReasonType\" MRT ON PS.\"id_MortalityReasonType\" = MRT.\"id\" "
				 +" WHERE PS.\"id_PigletStatusEventType\" = 4 and PS.\"id_Premise\" = ? "
				 +" AND FE.\"farrowDateTime\"::date between ? and ? "
				 +" GROUP BY PS.\"id_Pen\" , MRT.\"fieldDescription\", \"lactationDays\", R.\"roomId\",BN.\"barnId\" ORDER BY \"lactationDays\" ";

		pigletMortalityList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premiseId);
				ps.setDate(2, new java.sql.Date(startDate.getTime()));
				ps.setDate(3, new java.sql.Date(endDate.getTime()));
			}}, new PigletMortalityReportMapper());
		
		return pigletMortalityList;
	}
	
	
	private static final class PigletMortalityReportMapper implements RowMapper<PigletMortalityReportBean> {
		public PigletMortalityReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			PigletMortalityReportBean pigletMortalityReportBean = new PigletMortalityReportBean();			
			pigletMortalityReportBean.setBarnId(rs.getString("barnId"));
			pigletMortalityReportBean.setRoomId(rs.getString("roomId"));
			pigletMortalityReportBean.setLactationDays(rs.getInt("lactationDays"));
			pigletMortalityReportBean.setNumberOfDeaths(rs.getInt("Number of Deaths"));
			pigletMortalityReportBean.setMortalityReason(rs.getString("fieldDescription"));
			
			return pigletMortalityReportBean;
		}
	}
}
