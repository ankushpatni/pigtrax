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

@Repository
@Transactional
public class LactationLengthDao {

	private static final Logger logger = Logger.getLogger(LactationLengthDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<LactationLengthBean> getLactationLength(final Integer premiseId, final Date startDate, final Date endDate)
	{
		List<LactationLengthBean> lactationLengthList = new ArrayList<LactationLengthBean>();
		
		/*String qry=" SELECT  T.\"cnt\", T.\"totalCnt\", T.\"LactLength\", (100*T.\"cnt\"::double precision)/T.\"totalCnt\"::double precision as \"percentage\" FROM  ( "+
				 " select count(PS.\"id_PigInfo\") as cnt, (select PS.\"eventDateTime\"::date  from pigtrax.\"PigletStatus\" PS   JOIN pigtrax.\"FarrowEvent\" FE ON  PS.\"id_FarrowEvent\" = FE.\"id\" "+
				 " AND PS.\"eventDateTime\"::date between ? AND ? AND PS.\"id_PigletStatusEventType\" = 3 " +   
				 " WHERE PS.\"id_Premise\" = ?)-FE.\"farrowDateTime\"::date as \"LactLength\", (SELECT count(\"id\") from pigtrax.\"PigInfo\" WHERE \"id_SexType\" = 2 and \"isActive\" is true aND \"id_Premise\" = ?) as \"totalCnt\"    from pigtrax.\"FarrowEvent\" FE "+
				 " JOIN pigtrax.\"PigletStatus\" PS ON FE.\"id\" = PS.\"id_FarrowEvent\" AND PS.\"eventDateTime\"::date between ? AND ?  AND PS.\"id_PigletStatusEventType\" = 3   "+
				 " WHERE PS.\"id_Premise\" = ? "+
				 " GROUP BY  FE.\"farrowDateTime\"::date) T order by T.\"LactLength\" ";*/
				/*"SELECT "
				+" T.\"cnt\", T.\"totalCnt\", T.\"LactLength\", (100*T.\"cnt\"::double precision)/T.\"totalCnt\"::double precision as \"percentage\", T.\"eventTime\" FROM " 
				+" ( "
				+" SELECT count(PEM.\"id\") as cnt, PEM.\"eventTime\"::date, current_date-PEM.\"eventTime\"::date as \"LactLength\", "
				+" (SELECT count(\"id\") from pigtrax.\"PigInfo\" WHERE \"id_SexType\" = 2 and \"isActive\" is true aND \"id_Premise\" = ?) as \"totalCnt\"  "
				+" FROM pigtrax.\"PigTraxEventMaster\" PEM  "
				+" JOIN pigtrax.\"FarrowEvent\" FE ON PEM.\"id_FarrowEvent\" = FE.\"id\" "
				+" WHERE PEM.\"id\" in ( "
				+" SELECT max(PEM1.\"id\") " 
				+" FROM pigtrax.\"PigTraxEventMaster\" PEM1  "
				+" JOIN pigtrax.\"PigInfo\" PI ON PEM1.\"id_PigInfo\" = PI.\"id\" and PI.\"isActive\" is TRUE AND PI.\"id_Premise\" =? "
				+" GROUP BY PEM1.\"id_PigInfo\") "
				+ " AND PEM.\"id_FarrowEvent\" IS NOT NULL  AND FE.\"farrowDateTime\"::date between ? and ? "
				+" GROUP BY PEM.\"eventTime\"::date,\"LactLength\" ) T order by T.\"LactLength\" ";*/
		
		String qry=" SELECT  T.\"cnt\", T.\"totalCnt\", T.\"LactLength\", (100*T.\"cnt\"::double precision)/T.\"totalCnt\"::double precision as \"percentage\" FROM  ( "+
				 " select count(PS.\"id_PigInfo\") as cnt,  PS.\"eventDateTime\"::date -FE.\"farrowDateTime\"::date as \"LactLength\", (SELECT count(\"id\") from pigtrax.\"PigInfo\" WHERE \"id_SexType\" = 2 and \"isActive\" is true aND \"id_Premise\" = ?) as \"totalCnt\"  "
				 + "  from pigtrax.\"FarrowEvent\" FE JOIN  pigtrax.\"PigInfo\" PI ON PI.\"id\" = FE.\"id_PigInfo\" "+
				 " JOIN pigtrax.\"PigletStatus\" PS ON FE.\"id\" = PS.\"id_FarrowEvent\" AND PS.\"eventDateTime\"::date between ? AND ?  AND PS.\"id_PigletStatusEventType\" = 3   "+
				 " WHERE PS.\"id_Premise\" = ? "+
				 " GROUP BY  FE.\"farrowDateTime\"::date,  PS.\"eventDateTime\"::date) T order by T.\"LactLength\" ";

		lactationLengthList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
								
				ps.setInt(1, premiseId);				
				ps.setDate(2, new java.sql.Date(startDate.getTime()));
				ps.setDate(3, new java.sql.Date(endDate.getTime()));
				ps.setInt(4, premiseId);
			}}, new LactationLengthMapper());
		
		return lactationLengthList;
	}
	
	
	public Integer getTotalCount(final Integer premiseId)
	{
	
		String qry="SELECT count(\"id\") from pigtrax.\"PigInfo\" WHERE \"id_SexType\" = 2 and \"isActive\" is true aND \"id_Premise\" = ?";

		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, premiseId);
				}
			},
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getInt(1);
		            }
		            return null;
		          }
		        });
		
		return cnt;
	}
	
	
	
	private static final class LactationLengthMapper implements RowMapper<LactationLengthBean> {
		public LactationLengthBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			LactationLengthBean lactationLengthBean = new LactationLengthBean();
			
			lactationLengthBean.setNumberOfPigs(rs.getInt("cnt"));
			lactationLengthBean.setTotalPigCount(rs.getInt("totalCnt"));
			lactationLengthBean.setLactationLength(rs.getInt("LactLength"));
			lactationLengthBean.setPercentage(rs.getDouble("percentage"));
			//lactationLengthBean.setEventTime(rs.getDate("eventTime"));
			
			return lactationLengthBean;
		}
	}
}
