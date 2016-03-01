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
import com.pigtrax.report.bean.ProdEventLogBean;

@Repository
@Transactional
public class ProdEventLogDao {

	private static final Logger logger = Logger.getLogger(ProdEventLogDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ProdEventLogBean> getProdEventLogList(final Integer premiseId, final Date startDate, final Date endDate)
	{
		List<ProdEventLogBean> prodEventLogList = new ArrayList<ProdEventLogBean>();
		
		String qry="select PRE.\"permiseId\",BN.\"barnId\", RM.\"roomId\",PL.\"groupId\",PL.\"observationDate\"::date as \"eventDate\","
				+"LET.\"fieldDescription\" as \"logEventType\",PL.\"observation\" as \"remark\" "
				+"from pigtrax.\"ProductionLog\" PL "
				+"JOIN pigtrax.\"Premise\" PRE ON PL.\"id_Premise\" = PRE.\"id\" "
				+"JOIN pigtrax.\"Room\" RM ON PL.\"id_Room\" = RM.\"id\" "
				+"JOIN pigtrax.\"Barn\" BN ON RM.\"id_Barn\" = BN.\"id\" "
				+"JOIN pigtraxrefdata.\"LogEventType\" LET ON PL.\"id_LogEventType\" = LET.\"id\" "
				+"WHERE  "
				+"PL.\"id_Premise\" = ? AND "
				+"PL.\"observationDate\"::date between ? and ? "
				+"ORDER BY PRE.\"permiseId\",PL.\"observationDate\" ";

		prodEventLogList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premiseId);
				ps.setDate(2, new java.sql.Date(startDate.getTime()));
				ps.setDate(3, new java.sql.Date(endDate.getTime()));
			}}, new ProdEventLogMapper());
		
		return prodEventLogList;
	}
	
	
	private static final class ProdEventLogMapper implements RowMapper<ProdEventLogBean> {
		public ProdEventLogBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProdEventLogBean prodEventLogBean = new ProdEventLogBean();	
			prodEventLogBean.setPremiseId(rs.getString("permiseId"));
			prodEventLogBean.setBarnId(rs.getString("barnId"));
			prodEventLogBean.setRoomId(rs.getString("roomId"));
			prodEventLogBean.setGroupId(rs.getInt("groupId"));
			prodEventLogBean.setLogEventType(rs.getString("logEventType"));
			prodEventLogBean.setEventDate(rs.getDate("eventDate"));
			prodEventLogBean.setRemark(rs.getString("remark"));	
			return prodEventLogBean;
		}
	}
}
