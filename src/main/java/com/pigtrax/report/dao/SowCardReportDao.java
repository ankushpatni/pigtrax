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

import com.pigtrax.report.bean.SowCardReportBean;

@Repository
@Transactional
public class SowCardReportDao {
	
	private static final Logger logger = Logger.getLogger(SowCardReportDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<SowCardReportBean> getSowCardList(final int pigId) {
		
		String query = " select BE.\"currentParity\",FE.\"farrowDateTime\", (FE.\"liveBorns\"+FE.\"stillBorns\"+FE.\"mummies\") as totalBorn, FE.\"liveBorns\",FE.\"stillBorns\",FE.\"mummies\", "
				 + " DATE_PART('day',FE.\"farrowDateTime\" - BE.\"serviceStartDate\") as genLength, "
				 + " FE.\"weightInKgs\" as \"birthWeight\", PS.\"eventDateTime\" weanDate, PS.\"numberOfPigs\",  "
				 + " DATE_PART('day',PS.\"eventDateTime\" - FE.\"farrowDateTime\") as \"weanAge\", PS.\"weightInKgs\"/PS.\"numberOfPigs\" as \"avgWeanWeight\", M.\"ServNo\" as \"totalService\",  "
				 + " LAST_SERV.\"LastServDate\", BE.\"serviceStartDate\" as \"firstServiceDate\" from pigtrax.\"FarrowEvent\" FE "
				 + "  join pigtrax.\"BreedingEvent\" BE ON FE.\"id_BreedingEvent\" = BE.\"id\"  "
				 + "  left join pigtrax.\"PigletStatus\" PS ON PS.\"id_FarrowEvent\" = FE.\"id\" and PS.\"id_PigletStatusEventType\" = 3 "
				 + "  join (select \"id_BreedingEvent\" as \"serviceId\", count(*) as \"ServNo\" from pigtrax.\"MatingDetails\" group by \"id_BreedingEvent\") M ON M.\"serviceId\" = BE.\"id\" "
				 + "  JOIN (select \"id_BreedingEvent\", \"matingDate\"  as \"LastServDate\" from  pigtrax.\"MatingDetails\" where \"id\" in (select max(\"id\") from pigtrax.\"MatingDetails\" group by \"id_BreedingEvent\")) LAST_SERV ON LAST_SERV.\"id_BreedingEvent\" = BE.\"id\" "
				 + "  where FE.\"id_PigInfo\" = ? order by  FE.\"farrowDateTime\" ";


		List<SowCardReportBean> sowCardList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigId);
			}}, new SowCardReportMapper());
		
		return sowCardList;
	}
	
	private static final class SowCardReportMapper implements RowMapper<SowCardReportBean> {
		public SowCardReportBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			SowCardReportBean sowCardReportBean = new SowCardReportBean();
			
			sowCardReportBean.setCurrentParity(rs.getInt("currentParity"));			
			sowCardReportBean.setFarrowDateTime(rs.getDate("farrowDateTime"));
			sowCardReportBean.setTotalBorn(rs.getInt("totalBorn"));
			sowCardReportBean.setLiveBorns(rs.getInt("liveBorns"));
			sowCardReportBean.setStillBorns(rs.getInt("stillBorns"));
			sowCardReportBean.setMummies(rs.getInt("mummies"));
			sowCardReportBean.setGenLength(rs.getDouble("genLength"));
			sowCardReportBean.setBirthWeight(rs.getDouble("birthWeight"));
			sowCardReportBean.setWeanDate(rs.getDate("weanDate"));
			sowCardReportBean.setNumberOfPigs(rs.getInt("numberOfPigs"));			
			sowCardReportBean.setWeanAge(rs.getDouble("weanAge"));
			sowCardReportBean.setTotalService(rs.getInt("totalService"));
			sowCardReportBean.setAvgWeanWeight(rs.getDouble("avgWeanWeight"));
			sowCardReportBean.setFirstServiceDate(rs.getDate("firstServiceDate"));
			sowCardReportBean.setLastServiceDate(rs.getDate("LastServDate"));
			
			return sowCardReportBean;
		}
	}

}
