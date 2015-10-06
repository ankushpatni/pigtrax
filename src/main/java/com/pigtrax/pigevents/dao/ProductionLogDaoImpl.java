package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.CompanyTarget;
import com.pigtrax.pigevents.beans.ProductionLog;
import com.pigtrax.pigevents.dao.interfaces.ProductionLogDao;

@Repository
@Transactional
public class ProductionLogDaoImpl implements ProductionLogDao {
	
private static final Logger logger = Logger.getLogger(ProductionLogDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	@Override
	public int addProductionLog(final ProductionLog productionLog)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"ProductionLog\"(\"observation\", \"id_Company\", \"lastUpdated\", \"userUpdated\") "
				+ "values(?,?,current_timestamp,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            
	    				ps.setString(1, productionLog.getObservation());
	    				if(productionLog.getCompanyId() != null && productionLog.getCompanyId() != 0){
	    					ps.setInt(2, productionLog.getCompanyId());
	    				}
	    				else{
	    	            	ps.setNull(2, java.sql.Types.INTEGER);
	    				}
	    				ps.setString(3, productionLog.getUserUpdated()); 
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}
	
	
	
	@Override
	public int updateProductionLog(final ProductionLog productionLog)
			throws SQLException {
		String query = "update pigtrax.\"ProductionLog\" SET \"observation\"= ?  WHERE \"id\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, productionLog.getObservation());
				ps.setInt(2, productionLog.getId());
			}
		});
	}
	
	
	public List<ProductionLog> getProductLogList(final Integer companyId, final Date startDate, final Date endDate, final String username) throws SQLException
	{
		String sql = "select \"id\", \"observation\", \"id_Company\", \"lastUpdated\", \"userUpdated\" " 
				+ "from pigtrax.\"ProductionLog\" where \"id_Company\" = ?";
		
		if(startDate != null && endDate != null)
			sql+= " and \"lastUpdated\"::date >= ?  and \"lastUpdated\"::date <= ? ";
		else
			sql+= " and \"userUpdated\" = ? ";
		
		
		List<ProductionLog> productionLogList = jdbcTemplate.query(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {				
				ps.setInt(1, companyId);
				if(startDate != null && endDate != null)
				{
					ps.setDate(2, new java.sql.Date(startDate.getTime()));				
					ps.setDate(3, new java.sql.Date(endDate.getTime()));
				}
				else
				{
					ps.setString(2, username);
				}
			}}, new ProductionLogMapper());
		
		return productionLogList;
	}
	
	private static final class ProductionLogMapper implements RowMapper<ProductionLog> {
		public ProductionLog mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductionLog productionLog = new ProductionLog();
			productionLog.setId(rs.getInt("id"));
			productionLog.setCompanyId(rs.getInt("id_Company"));
			productionLog.setObservation(rs.getString("observation"));
			productionLog.setLastUpdated(rs.getDate("lastUpdated"));
			productionLog.setUserUpdated(rs.getString("userUpdated"));
			return productionLog;
		}
	}
	
	
	@Override
	public int deleteProductionLog(final Integer id) throws SQLException {
		String query = "delete from pigtrax.\"ProductionLog\" WHERE \"id\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
	}
}
