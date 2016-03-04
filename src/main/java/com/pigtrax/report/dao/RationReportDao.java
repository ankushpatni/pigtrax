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
	
	public List<RationReportBean> getRationReportList(final Integer premiseId, final Date startDate, final Date endDate)
	{
		List<RationReportBean> rationReportList = new ArrayList<RationReportBean>();
		
		String qry="select \"rationValue\" as \"rationId\",1 as \"actualTonsUsed\","
					+"2 as \"targetTonsUsed\",3 as \"deviationTonsUsed\",4 as \"actualKg\",5 as \"targetKg\",6 as \"deviationKg\","
					+ "7 as \"actualFeedCost\",8 as \"targetFeedCost\" ,9 as \"deviationFeedCost\" from pigtrax.\"MasterRation\"";

		rationReportList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				//ps.setInt(1, premiseId);
				//ps.setDate(2, new java.sql.Date(startDate.getTime()));
				//ps.setDate(3, new java.sql.Date(endDate.getTime()));
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
