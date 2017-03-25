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
				+ "\"id_Company\", \"completionDate\", \"remarks\", \"lastUpdated\", \"userUpdated\",\"id_Premise\",\"id_Ration\") "
				+ "values(?,?,?,?,?,current_timestamp,?,?,?)";
		
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
	    				ps.setInt(7, companyTarget.getPremiseId());
	    				ps.setObject(8, companyTarget.getRationId(), java.sql.Types.INTEGER);
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}
	
	@Override
	public List<CompanyTarget> getCompanyTargets(final Integer companyId, final Integer premiseId)  throws SQLException {
		String sql = "select \"id\", \"id_TargetType\", \"targetValue\", \"completionDate\", \"remarks\", \"id_Company\", \"id_Premise\",\"id_Ration\" "
				+ "from pigtrax.\"CompanyTarget\" where \"id_Company\" = ? and \"id_Premise\" = ? order by \"id_Premise\" ";
		
		List<CompanyTarget> companyTargetList = jdbcTemplate.query(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {				
				ps.setInt(1, companyId);
				ps.setInt(2, premiseId);
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
			companyTarget.setPremiseId(rs.getInt("id_Premise"));
			companyTarget.setRationId(rs.getInt("id_Ration"));
			return companyTarget;
		}
	}
	
	
	@Override
	public int updateCompanyTarget(final CompanyTarget companyTarget)  throws SQLException {
		final String Qry = "update pigtrax.\"CompanyTarget\" set \"targetValue\" = ?, "
				+ " \"completionDate\" = ?, \"remarks\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"id_Premise\"=?,\"id_Ration\" = ? "
				+ " where \"id\" = ?";
		

		this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
	    				ps.setString(1, companyTarget.getTargetValue());
	    				ps.setObject(2, companyTarget.getCompletionDate(), java.sql.Types.DATE); 	    				
	    				ps.setString(3, companyTarget.getRemarks());
	    				ps.setString(4, companyTarget.getUserUpdated());
	    				ps.setInt(5, companyTarget.getPremiseId());
	    				ps.setObject(6, companyTarget.getRationId(), java.sql.Types.INTEGER);
	    				ps.setInt(7, companyTarget.getId());
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
	
	@Override
	public CompanyTarget getCompanyTargetsByPremises(final Integer premisesId)  throws SQLException {
		String sql = "select \"id\", \"id_TargetType\", \"targetValue\", \"completionDate\", \"remarks\", \"id_Company\", \"id_Premise\",\"id_Ration\" "
				+ "from pigtrax.\"CompanyTarget\" where \"id_Premise\" = ? and \"id_TargetType\" = 1 order by \"id_Premise\" ";
		
		List<CompanyTarget> companyTargetList = jdbcTemplate.query(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {				
				ps.setInt(1, premisesId);
			}}, new CompanyTargetMapper());
		
		if(companyTargetList != null && companyTargetList.size()>0)
			return companyTargetList.get(0);
		else
			return null;
		
	}
	
	public CompanyTarget getCompanyTargetByType(final Integer premiseId, final Integer parameterId)
			throws Exception {
		String sql = "select \"id\", \"id_TargetType\", \"targetValue\", \"completionDate\", \"remarks\", \"id_Company\", \"id_Premise\",\"id_Ration\" "
				+ "from pigtrax.\"CompanyTarget\" where \"id_Premise\" = ? and \"id_TargetType\" = ? order by \"id_Premise\" ";
		
		List<CompanyTarget> companyTargetList = jdbcTemplate.query(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {		
				ps.setInt(1, premiseId);
				ps.setInt(2, parameterId);
			}}, new CompanyTargetMapper());
		
		if(companyTargetList != null && companyTargetList.size()>0)
			return companyTargetList.get(0);
		else
			return null;
	}
}
