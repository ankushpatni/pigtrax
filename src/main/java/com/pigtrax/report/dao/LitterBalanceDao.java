package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.report.bean.LitterBalanceBean;
import com.pigtrax.usermanagement.enums.PigletStatusEventType;

@Repository
@Transactional
public class LitterBalanceDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<LitterBalanceBean> getLitterBalance(final Integer premiseId, final Date startDate, final Date endDate)
	{
		List<LitterBalanceBean> litterBalance = new ArrayList<LitterBalanceBean>();
		
		String qry="SELECT distinct T.\"id_PigInfo\",T.\"pigId\", T.\"weanDate\" as \"weanDate\",T.\"liveBorns\", T.\"weanPigNum\" as \"wean\" , T.\"transferPigNum\" as \"transfer\", "
					+" T.\"deathPigNum\" as \"death\", T.\"fosterInPigNum\", T.\"id_Pen\",PEN.\"penId\", (T.\"liveBorns\"-T.\"transferPigNum\"-T.\"deathPigNum\" - T.\"weanPigNum\" + T.\"fosterInPigNum\") as \"balance\" "
					+" FROM " 
					+" ( "
					+" SELECT  PI.\"pigId\", FE.\"liveBorns\", FE.\"id_Pen\", FE.\"id\",PS.\"id_PigInfo\", COALESCE(weanData.\"weanPigNum\",0) as \"weanPigNum\", "
					+" weanData.\"eventDateTime\" as \"weanDate\", COALESCE(transferData.\"transferPigNum\",0) as \"transferPigNum\", transferData.\"eventDateTime\", "
					+" COALESCE(deathData.\"deathPigNum\",0) as \"deathPigNum\", deathData.\"eventDateTime\", COALESCE(fosterInData.\"fosterInPigNum\" ,0) as \"fosterInPigNum\"  "
					+" FROM pigtrax.\"FarrowEvent\" FE "
					+" JOIN pigtrax.\"PigInfo\" PI ON FE.\"id_PigInfo\" = PI.\"id\" "
					+" LEFT JOIN pigtrax.\"PigletStatus\" PS ON PS.\"id_PigInfo\"  = PI.\"id\" "
					+" LEFT JOIN "
					+" (select \"id_PigInfo\", \"eventDateTime\",  sum(\"numberOfPigs\") as \"deathPigNum\" from pigtrax.\"PigletStatus\"  "
					+ " WHERE  \"id_PigletStatusEventType\" =? "  
					+" GROUP BY \"id_PigInfo\",\"eventDateTime\") deathData ON PS.\"id_PigInfo\" = deathData.\"id_PigInfo\" "
					+" LEFT JOIN "
					+" (select \"id_PigInfo\", \"eventDateTime\", sum(\"numberOfPigs\") as \"transferPigNum\" from pigtrax.\"PigletStatus\"  "
					+ " WHERE  \"id_PigletStatusEventType\" =? " 
					+" GROUP BY \"id_PigInfo\",\"eventDateTime\") transferData ON PS.\"id_PigInfo\" = transferData.\"id_PigInfo\" "
					+" LEFT JOIN "
					+" (select \"id_PigInfo\", \"eventDateTime\", sum(\"numberOfPigs\") as \"weanPigNum\" from pigtrax.\"PigletStatus\"  "
					+ " WHERE  \"id_PigletStatusEventType\" =? "  
					+" GROUP BY \"id_PigInfo\",\"eventDateTime\") weanData ON PS.\"id_PigInfo\" = weanData.\"id_PigInfo\" and weanData.\"eventDateTime\"::date between ? AND ? "
					+" LEFT JOIN "
					+" (select \"id_PigInfo\", \"eventDateTime\", sum(\"numberOfPigs\") as \"fosterInPigNum\" from pigtrax.\"PigletStatus\"  "
					+ " WHERE  \"id_PigletStatusEventType\" =? " 
					+" GROUP BY \"id_PigInfo\",\"eventDateTime\") fosterInData ON PS.\"id_PigInfo\" = fosterInData.\"id_PigInfo\" "
					+" WHERE FE.\"id_Premise\" = ? AND FE.\"farrowDateTime\"::date between ? AND ? "
					+" )T "
					+" JOIN pigtrax.\"Pen\" PEN ON T.\"id_Pen\" = PEN.\"id\" order by balance desc"; 

		litterBalance = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, PigletStatusEventType.Death.getTypeCode());
				ps.setInt(2, PigletStatusEventType.FosterOut.getTypeCode());
				ps.setInt(3, PigletStatusEventType.Wean.getTypeCode());
				ps.setDate(4, new java.sql.Date(startDate.getTime()));
				ps.setDate(5, new java.sql.Date(endDate.getTime()));
				ps.setInt(6, PigletStatusEventType.FosterIn.getTypeCode());
				ps.setInt(7, premiseId);
				ps.setDate(8, new java.sql.Date(startDate.getTime()));
				ps.setDate(9, new java.sql.Date(endDate.getTime()));
			}}, new LitterBalanceMapper());
		
		return litterBalance;
	}
	
	
	private static final class LitterBalanceMapper implements RowMapper<LitterBalanceBean> {
		public LitterBalanceBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			LitterBalanceBean litterBean = new LitterBalanceBean();	
			litterBean.setPigId(rs.getString("pigId"));
			litterBean.setWeanDate(rs.getDate("weanDate"));
			litterBean.setPenId(rs.getString("penId"));
			litterBean.setLiveBorn(rs.getInt("liveBorns"));
			litterBean.setDeath(rs.getInt("death"));
			litterBean.setTransfer(rs.getInt("transfer"));
			litterBean.setWean(rs.getInt("wean"));
			litterBean.setBalance(rs.getInt("balance"));
			litterBean.setFosterInNum(rs.getInt("fosterInPigNum"));
			return litterBean;
		}
	}
}
