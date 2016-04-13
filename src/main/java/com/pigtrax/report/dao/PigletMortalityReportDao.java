package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
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
		
		String qry=" SELECT R.\"id\", BN.\"barnId\", R.\"roomId\", coalesce(SUM(FE.\"liveBorns\"),0) as \"inventoryCount\",(?-FE.\"farrowDateTime\":: date) as \"lactationDays\", "
				 +"	COALESCE(SUM(PS.\"numberOfPigs\"),0) as \"Number of Deaths\",PS.\"id_Pen\", PS.\"eventDateTime\" as \"deathDate\", FE.\"farrowDateTime\" as \"farrowDate\",PS1.\"eventDateTime\"as \"weanDate\" " 
				 +" FROM pigtrax.\"FarrowEvent\" FE "
				 +" LEFT JOIN pigtrax.\"PigletStatus\" PS On PS.\"id_FarrowEvent\" = FE.\"id\" and PS.\"id_PigletStatusEventType\" = 4"
				 +" LEFT JOIN pigtrax.\"Pen\" PEN ON FE.\"id_Pen\" = PEN.\"id\" "
				 +" LEFT JOIN pigtrax.\"Room\" R ON PEN.\"id_Room\" = R.\"id\" "
				 +" LEFT JOIN pigtrax.\"Barn\" BN ON BN.\"id\" = R.\"id_Barn\" "
				 +" LEFT JOIN pigtrax.\"PigletStatus\" PS1 On PS1.\"id_FarrowEvent\" = FE.\"id\" and PS1.\"id_PigletStatusEventType\" = 3"
				 +" WHERE  FE.\"id_Premise\" = ? "
				 +" AND FE.\"farrowDateTime\"::date between ? and ? "
				 +" GROUP BY PS.\"id_Pen\" , \"lactationDays\", R.\"roomId\",R.\"id\", BN.\"barnId\", FE.\"farrowDateTime\", PS.\"eventDateTime\", PS1.\"eventDateTime\" ORDER BY  BN.\"barnId\",  R.\"roomId\", PS.\"eventDateTime\",\"lactationDays\" ";

		pigletMortalityList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setDate(1, new java.sql.Date(endDate.getTime()));
				ps.setInt(2, premiseId);
				ps.setDate(3, new java.sql.Date(startDate.getTime()));
				ps.setDate(4, new java.sql.Date(endDate.getTime()));
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
			pigletMortalityReportBean.setInventoryCount(rs.getInt("inventoryCount"));
			pigletMortalityReportBean.setDeathDate((rs.getObject("deathDate") != null) ? rs.getDate("deathDate") : null);
			pigletMortalityReportBean.setFarrowDate((rs.getObject("farrowDate") != null) ? rs.getDate("farrowDate") : null);
			pigletMortalityReportBean.setWeanDate((rs.getObject("weanDate") != null) ? rs.getDate("weanDate") : null);
			pigletMortalityReportBean.setRoomPkId(rs.getObject("id") != null ? rs.getInt("id") : null);
			
			return pigletMortalityReportBean;
		}
	}
	
	
	public Integer getStartHead(Date startDate,Date endDate, Integer roomId, Integer premisesId)
	{
		java.sql.Date qryDate = null;
		if(startDate != null)
			qryDate = new java.sql.Date(startDate.getTime());
		
		java.sql.Date qryEndDate = null;
		if(endDate != null)
			qryEndDate = new java.sql.Date(endDate.getTime());
		
		String qry = "SELECT T.\"id\", COALESCE(T.\"cnt\") as net from "
					 +" (SELECT R.\"id\", SUM(FE.\"liveBorns\") as \"cnt\"  from pigtrax.\"FarrowEvent\" FE " 
					// +" LEFT JOIN pigtrax.\"PigletStatus\" PS ON FE.\"id\" = PS.\"id_FarrowEvent\" AND PS.\"id_PigletStatusEventType\" IN (4,2) "
					// +" LEFT JOIN pigtrax.\"PigletStatus\" PS1 ON FE.\"id\" = PS1.\"id_FarrowEvent\" AND PS1.\"id_PigletStatusEventType\" IN (1) "
					 +" LEFT JOIN pigtrax.\"Pen\" P ON P.\"id\" = FE.\"id_Pen\" "
					 +" LEFT JOIN pigtrax.\"Room\" R ON P.\"id_Room\" = R.\"id\" "
					 +" LEFT JOIN pigtrax.\"PigInfo\" PI ON PI.\"id\" = FE.\"id_PigInfo\" where " 
					 + "PI.\"entryDate\" <= '"+qryEndDate+"' and PI.\"id_Premise\" ="+premisesId+" ";
		if(roomId != null)
				qry += " AND R.\"id\" = "+roomId;
					 
					 qry += " GROUP BY R.\"id\") T" ;
		
		Long returnValue = null;
		returnValue = jdbcTemplate.query(qry, new ResultSetExtractor<Long>() {
			public Long extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				if (resultSet.next()) {
					return resultSet.getLong(2);
				}
				return null;
			}
		});			 
		
		logger.debug("retVal is :" + returnValue);
		if (returnValue != null) {
			return Integer.decode(returnValue.toString());
		}
	
	return 0;
		
	}

}
