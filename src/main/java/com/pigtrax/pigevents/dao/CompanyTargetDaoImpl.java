package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.pigtrax.pigevents.dao.interfaces.CompanyTargetDao;


@Repository
@Transactional
public class CompanyTargetDaoImpl implements CompanyTargetDao {

	private static final Logger logger = Logger.getLogger(BreedingEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int addCompanyTarget(final CompanyTarget companyTarget)  throws SQLException{
		final String Qry = "insert into pigtrax.\"CompanyTarget\"(\"id_TargetType\", \"targetValue\", "
				+ "\"id_Company\", \"completionDate\", \"remarks\", \"lastUpdated\", \"userUpdated\") "
				+ "values(?,?,?,?,?,current_timestamp,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            
	    				ps.setObject(1, companyTarget.getTargetId(), java.sql.Types.INTEGER);
	    				ps.setString(2, companyTarget.getTargetValue());
	    				if(companyTarget.getCompanyId() != null && companyTarget.getCompanyId() != 0){
	    					ps.setInt(3, companyTarget.getCompanyId());
	    				}
	    				else{
	    	            	ps.setNull(3, java.sql.Types.INTEGER);
	    				}
	    				ps.setObject(4, companyTarget.getCompletionDate(), java.sql.Types.DATE); 	    				
	    				ps.setString(5, companyTarget.getRemarks());
	    				ps.setString(6, companyTarget.getUserUpdated()); 
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}
	
	@Override
	public List<CompanyTarget> getCompanyTargets(final Integer companyId)  throws SQLException {
		String sql = "select \"id\", \"id_TargetType\", \"targetValue\", \"completionDate\", \"remarks\", \"id_Company\" "
				+ "from pigtrax.\"CompanyTarget\" where \"id_Company\" = ?";
		
		List<CompanyTarget> companyTargetList = jdbcTemplate.query(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {				
				ps.setInt(1, companyId);
			}}, new CompanyTargetMapper());
		
		return companyTargetList;
	}
	
	private static final class CompanyTargetMapper implements RowMapper<CompanyTarget> {
		public CompanyTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
			CompanyTarget companyTarget = new CompanyTarget();
			companyTarget.setId(rs.getInt("id")); 
			companyTarget.setTargetId(rs.getInt("id_TargetType"));
			companyTarget.setTargetValue(rs.getString("targetValue"));
			companyTarget.setCompletionDate(rs.getDate("completionDate"));
			companyTarget.setRemarks(rs.getString("remarks"));
			companyTarget.setCompanyId(rs.getInt("id_Company"));
			return companyTarget;
		}
	}
	
	
	@Override
	public int updateCompanyTarget(final CompanyTarget companyTarget)  throws SQLException {
		final String Qry = "update pigtrax.\"CompanyTarget\" set \"targetValue\" = ?, "
				+ " \"completionDate\" = ?, \"remarks\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ? "
				+ " where \"id\" = ?";
		

		this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
	    				ps.setString(1, companyTarget.getTargetValue());
	    				ps.setObject(2, companyTarget.getCompletionDate(), java.sql.Types.DATE); 	    				
	    				ps.setString(3, companyTarget.getRemarks());
	    				ps.setString(4, companyTarget.getUserUpdated());
	    				ps.setInt(5, companyTarget.getId());
	    	        }
	    	    });
		
		
		return companyTarget.getId();
	}
	
	@Override
	public int deleteTargetDetails(final Integer companyTargetId) throws SQLException {
		final String Qry = "delete from pigtrax.\"CompanyTarget\" where \"id\" = ?";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
    				ps.setInt(1, companyTargetId);
    	       }
    	 });
	}
}
