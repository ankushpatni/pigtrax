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

import com.pigtrax.report.bean.FeedReportBean;

@Repository
@Transactional
public class FeedReportDao {
	
	private static final Logger logger = Logger.getLogger(FeedReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
public List<FeedReportBean> getFeedList(final int premisesId, final Integer groupId) {
		
		String query = " select GE.\"groupId\",B.\"barnId\",S.\"siloId\",MR.\"rationValue\",FED.\"feedEventDate\",FET.\"fieldDescription\" as \"eventType\", FED.\"weightInKgs\",FED.\"feedCost\", "
				 +"  FE.\"feedMedication\",FE.\"ticketNumber\",FED.\"feedMill\",TT.\"trailerId\", TAT.\"truckId\", FED.remarks "
				 +"  from pigtrax.\"FeedEventDetails\" FED "
				 +"  LEFT JOIN pigtrax.\"FeedEvent\" FE ON FED.\"id_FeedEvent\" = FE.\"id\" "
				 +"  LEFT JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\" "
				 +"  LEFT JOIN pigtrax.\"Silo\" S ON FED.\"id_Silo\" = S.\"id\" "
				 +"  LEFT JOIN pigtraxrefdata.\"FeedEventType\" FET ON FED.\"id_FeedEventType\" = FET.\"id\"  "
				 +"  LEFT JOIN pigtrax.\"MasterRation\" MR ON FE.\"batchId\" = MR.\"id\" "
				 +"  LEFT JOIN pigtrax.\"Barn\" B ON S.\"id_Barn\" = B.\"id\" "
				 +"  left JOIN pigtrax.\"TransportJourney\" TJ ON FE.\"id_TransportJourney\" = TJ.\"id\" "
				 +"  left JOIN pigtrax.\"TransportTrailer\" TT ON TJ.\"id_TransportTrailer\" = TT.\"id\" "
				 +"  left JOIN pigtrax.\"TransportTruck\" TAT ON TJ.\"id_TransportTruck\" = TAT.\"id\" "
				 +"  where FE.\"id_Premise\"=? ";
		if(groupId != null && groupId > 0)
			query += " and FE.\"id_GroupEvent\" = ? ";
		
		query+= " order by B.\"barnId\", FED.\"feedEventDate\" ";
		
		List<FeedReportBean> feedReportBeanList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, premisesId);
				if(groupId != null && groupId > 0)
					ps.setInt(2, groupId);
			}}, new FeedReportMapper());
		
		return feedReportBeanList;
	
	}
	
	private static final class FeedReportMapper implements RowMapper<FeedReportBean> {
		public FeedReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			FeedReportBean feedReportBean = new FeedReportBean();
			
			feedReportBean.setGroupID(rs.getString("groupId"));
			feedReportBean.setBarnID(rs.getString("barnId"));
			feedReportBean.setSiloID(rs.getString("siloId"));
			feedReportBean.setRationID(rs.getString("rationValue"));
			feedReportBean.setFeedEventDate(rs.getDate("feedEventDate"));
			feedReportBean.setFeedEventtype(rs.getString("eventType"));
			feedReportBean.setWeight(rs.getFloat("weightInKgs"));
			
			feedReportBean.setFeedcost(rs.getFloat("feedCost"));
			feedReportBean.setMedication(rs.getString("feedMedication"));
			feedReportBean.setTruckTicket(rs.getString("ticketNumber"));
			feedReportBean.setFeedMill(rs.getString("feedMill"));
			feedReportBean.setTrailer(rs.getString("trailerId"));
			feedReportBean.setTruck(rs.getString("truckId"));
			feedReportBean.setRemarks(rs.getString("remarks"));
			
			
			return feedReportBean;
		}
	}

}
