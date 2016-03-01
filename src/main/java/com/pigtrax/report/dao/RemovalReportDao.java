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
	
	private static final Logger logger = Logger.getLogger(BarnDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<RemovalReportBean> getRemovalList(final int premisesId, final int pigId,final int groupId, final Date start, final Date end) {
		
		String query = " select P.\"name\",GE.\"groupId\", PI.\"pigId\", RES.\"removalDateTime\", RES.\"numberOfPigs\", "
				 +"  case when( RES.\"id_GroupEvent\" is not null ) THEN  DATE_PART('day', RES.\"removalDateTime\"::timestamp - GE.\"groupStartDateTime\"::timestamp) else DATE_PART('day', RES.\"removalDateTime\"::timestamp - PI.\"entryDate\"::timestamp) END as \"averageDOF\", "
				 +"  case when( RES.\"id_GroupEvent\" is not null ) THEN  RES.\"weightInKgs\"/RES.\"numberOfPigs\" else RES.\"weightInKgs\" END as \"averageWeight\", "
				 +"  case when( RES.\"id_PigInfo\" is not null ) THEN  PI.\"parity\" else 0 END as \"parity\", "
				 +"  case when( RES.\"id_MortalityReason\" is not null ) THEN  MR.\"fieldDescription\" else '' END as \"mortalityReason\" "
				
				 +"  from pigtrax.\"RemovalEventExceptSalesDetails\" RES  "
				 +"  left JOIN pigtrax.\"GroupEvent\" GE ON RES.\"id_GroupEvent\" = GE.\"id\" "
				 +"  left JOIN pigtrax.\"PigInfo\" PI ON RES.\"id_PigInfo\" = PI.\"id\" "
				 +"  left join pigtrax.\"Premise\" P  ON RES.\"id_Premise\" = P.\"id\" "
				 +"  left join pigtrax.\"Premise\" P1  ON RES.\"id_DestPremise\" = P1.\"id\" "
				 +"  left join pigtrax.\"Room\" R ON RES.\"id_Room\" = R.\"id\" "
				 +"  left join pigtraxrefdata.\"MortalityReasonType\" MR ON RES.\"id_MortalityReason\" = MR.\"id\" "
				 +"  where RES.\"id_RemovalEvent\" = 9 and RES.\"id_Premise\" = ? ";
		if(pigId != 0)
			query = query +"  and RES.\"id_PigInfo\" =  " +pigId ;
		if(groupId != 0)
			query = query +"  and RES.\"id_GroupEvent\" =  "+groupId ;
		if(start != null)
			query = query +"  and RES.\"removalDateTime\" >  "+ start;
		if(end != null)
			query = query + " and RES.\"removalDateTime\" < "+ end;
		
		List<RemovalReportBean> sowReportBeanList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premisesId);
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
			
			
			
			return removalReportBean;
		}
	}

}
