package com.pigtrax.usermanagement.dao;

import java.math.BigDecimal;
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

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.dao.interfaces.CompanyDao;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class CompanyDaoImpl implements CompanyDao{
	
private static final Logger logger = Logger.getLogger(CompanyDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * To get the list of Company
	 * @return List<Company>
	 * 
	 * "companyId", name, address, city, "registrationNumber", email, 
            phone, "contactName", payment, "paymentDate", "isActive", "lastUpdated", 
            "userUpdated")
	 */
	public List<Company> getCompanyList() {
		String query = "SELECT \"id\",\"companyId\", \"name\", \"address\", \"city\", \"registrationNumber\", \"email\", \"phone\", \"contactName\", \"payment\", \"paymentDate\", \"isActive\" from pigtrax.\"Company\" order by \"id\" desc ";
		return jdbcTemplate.query(query, new CompanyMapper());
	}
	
	public int updateCompanyStatus(final String companyID, final Boolean companyStatus)
	{
		String query = "update pigtrax.\"Company\" SET \"isActive\"=?  WHERE \"companyId\"=?";

		logger.info("Status-->" + companyStatus);
		logger.info("Id-->" + companyID);
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, !companyStatus);
				ps.setString(2, companyID.toUpperCase());
			}
		});
	
	}

	@Override
	public Company findByCompanyID(final String companyID) {
		String query = "SELECT \"id\",\"companyId\", \"name\", \"address\", \"city\", \"registrationNumber\", \"email\", \"phone\", \"contactName\", \"payment\", \"paymentDate\", \"isActive\" from pigtrax.\"Company\"  WHERE \"companyId\"=?";
		List<Company> companyList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, companyID.toUpperCase());
			}}, new CompanyMapper());

		if(companyList != null && companyList.size() > 0){
			return companyList.get(0);
		}
		return null;
	}

	@Override
	public int insertCompanyRecord(final Company company) {
		String query = "INSERT INTO pigtrax.\"Company\"(  \"companyId\", name, address, city, \"registrationNumber\", email, phone,"
					 +" \"contactName\", payment, \"paymentDate\", \"isActive\", \"lastUpdated\",\"userUpdated\")"+
					 "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, company.getCompanyId().toUpperCase());
				ps.setString(2, company.getName());
				ps.setString(3, company.getAddress());
				ps.setString(4, company.getCity());
				ps.setString(5, company.getRegistrationNumber());
				ps.setString(6, company.getEmail());
				ps.setString(7, company.getPhone());
				ps.setString(8, company.getContactName());
				ps.setBigDecimal(9, company.getPayment());
				if (company.getPayment() != null && company.getPayment() != BigDecimal.ZERO) {
					ps.setDate(10, new java.sql.Date(System.currentTimeMillis()));
				} else {
					ps.setDate(10, null);
				}
				ps.setBoolean(11, company.isActive());
				ps.setDate(12, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(13, UserUtil.getLoggedInUser());
			}
		});
	}
	
	public int updateCompanyRecord(final Company company) {
		String query = "update pigtrax.\"Company\" SET name=?, address=?, city =?, \"registrationNumber\" =?, email=?, phone=?, \"contactName\"=?, payment=?, \"paymentDate\"=?, \"lastUpdated\"=?, \"userUpdated\"=?  WHERE \"companyId\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, company.getName());
				ps.setString(2, company.getAddress());
				ps.setString(3, company.getCity());
				ps.setString(4, company.getRegistrationNumber());
				ps.setString(5, company.getEmail());
				ps.setString(6, company.getPhone());
				ps.setString(7, company.getContactName());
				ps.setBigDecimal(8, company.getPayment());
				if (company.getPayment() != null && company.getPayment() != BigDecimal.ZERO) {
					ps.setDate(9, new java.sql.Date(System.currentTimeMillis()));
				} else {
					ps.setDate(9, null);
				}
				ps.setDate(10, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(11, UserUtil.getLoggedInUser());
				ps.setString(12,company.getCompanyId());

			}
		});
	}

	
	private static final class CompanyMapper implements RowMapper<Company> {
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			Company company = new Company();
			company.setId(rs.getInt("id"));
			company.setCompanyId(rs.getString("companyId"));
			company.setName(rs.getString("name"));
			company.setAddress(rs.getString("address"));
			company.setCity(rs.getString("city"));
			company.setRegistrationNumber(rs.getString("registrationNumber"));
			company.setEmail(rs.getString("email"));
			company.setPhone(rs.getString("phone"));
			company.setContactName(rs.getString("contactName"));
			company.setPayment(rs.getBigDecimal("payment"));
			company.setPaymentDate(rs.getDate("paymentDate"));
			company.setActive(rs.getBoolean("isActive"));
			return company;
		}
	}
}
