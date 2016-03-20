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

import com.pigtrax.report.bean.TargetReportBean;

@Repository
@Transactional
public class TargetReportDao {

	private static final Logger logger = Logger.getLogger(TargetReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<TargetReportBean> getTargetList(final Integer companyId,final Integer premiseId, final Date startDate)
	{
		List<TargetReportBean> prodEventLogList = new ArrayList<TargetReportBean>();
		
		String qry="select TT.\"fieldDescription\",CT.\"completionDate\" :: date,CT.\"targetValue\",CT.\"remarks\" "
				+ "from pigtrax.\"CompanyTarget\" as CT,pigtraxrefdata.\"TargetType\" as TT where CT.\"id_TargetType\"=TT.\"id\" "
				+"and CT.\"completionDate\" ::date >=? and CT.\"id_Company\" = ? and CT.\"id_Premise\" = ?";

		prodEventLogList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setDate(1, new java.sql.Date(startDate.getTime()));
				ps.setInt(2, companyId);
				ps.setInt(3, premiseId	);
			}}, new TargetReportMapper());
		
		return prodEventLogList;
	}
	
	
	private static final class TargetReportMapper implements RowMapper<TargetReportBean> {
		public TargetReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			TargetReportBean targetReportBean = new TargetReportBean();	
			targetReportBean.setParameter(rs.getString("fieldDescription"));
			targetReportBean.setStartDate(rs.getDate("completionDate"));
			targetReportBean.setTargetValue(rs.getString("targetValue"));
			targetReportBean.setRemark(rs.getString("remarks"));	
			return targetReportBean;
		}
	}
}
