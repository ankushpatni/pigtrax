package com.pigtrax.usermanagement.dao;

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

import com.pigtrax.master.location.Premises;
import com.pigtrax.usermanagement.dao.interfaces.PremisesDao;
import com.pigtrax.util.UserUtil;



@Repository
@Transactional
public class PremisesDaoImpl implements PremisesDao{

	private static final Logger logger = Logger.getLogger(PremisesDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Premises> getPremisesList(final int generatedCompanyId) {
		String query = "SELECT \"id\",\"permiseId\", \"id_Company\", \"name\", \"address\", \"city\", \"state\", \"zipcode\", \"isActive\" from pigtrax.\"Premise\" where \"id_Company\" = ? order by \"id\" desc ";
		
		List<Premises> premisesList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedCompanyId);
			}}, new PremisesMapper());
		
		return premisesList;
	}


	@Override
	public int updatePremisesStatus(final String premisesID,final Boolean premisesStatus)
			throws SQLException {
		String query = "update pigtrax.\"Premise\" SET \"isActive\"=?  WHERE \"permiseId\"=?";

		logger.info("Status-->" + premisesStatus);
		logger.info("Id-->" + premisesID);
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, !premisesStatus);
				ps.setString(2, premisesID.toUpperCase());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.pigtrax.usermanagement.dao.interfaces.PremisesDao#insertPremisesRecord(com.pigtrax.master.location.Premises)
	 * INSERT INTO pigtrax."Premise"(
            id, "permiseId", "id_Company", name, address, city, state, zipcode, 
            "isActive", "lastUpdated", "userUpdated")
	 */
	@Override
	public int insertPremisesRecord(final Premises premises) throws SQLException {
		String query = "INSERT INTO pigtrax.\"Premise\"(  \"permiseId\", \"id_Company\", name, address, city, state, zipcode, "
				 +" \"isActive\", \"lastUpdated\",\"userUpdated\")"+
				 "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, premises.getPermiseId().toUpperCase());
				ps.setInt(2, premises.getCompanyId());
				ps.setString(3, premises.getName());
				ps.setString(4, premises.getAddress());
				ps.setString(5, premises.getCity());
				ps.setString(6, premises.getState());
				ps.setString(7, premises.getZipcode());
				ps.setBoolean(8, premises.isActive());
				ps.setDate(9, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(10, UserUtil.getLoggedInUser());
			}
		});		
	}

	@Override
	public int updatePremisesRecord(final Premises premises) throws SQLException {
		String query = "update pigtrax.\"Premise\" SET name=?, address=?, city =?, state =?, zipcode=?, \"lastUpdated\"=?, \"userUpdated\"=?  WHERE \"permiseId\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, premises.getName());
				ps.setString(2, premises.getAddress());
				ps.setString(3, premises.getCity());
				ps.setString(4, premises.getState());
				ps.setString(5, premises.getZipcode());
				ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(7, UserUtil.getLoggedInUser());
				ps.setString(8, premises.getPermiseId().toUpperCase());
			}
		});
	}
	
	private static final class PremisesMapper implements RowMapper<Premises> {
		public Premises mapRow(ResultSet rs, int rowNum) throws SQLException {
			Premises premises = new Premises();
			premises.setId(rs.getInt("id"));
			premises.setPermiseId(rs.getString("permiseId"));
			premises.setCompanyId(rs.getInt("id_Company"));
			premises.setName(rs.getString("name"));
			premises.setAddress(rs.getString("address"));
			premises.setCity(rs.getString("city"));
			premises.setState(rs.getString("state"));
			premises.setZipcode(rs.getString("zipcode"));
			premises.setActive(rs.getBoolean("isActive"));
			return premises;
		}
	}

}
