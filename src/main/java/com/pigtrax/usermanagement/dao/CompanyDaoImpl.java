package com.pigtrax.usermanagement.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.dao.interfaces.CompanyDao;

@Repository
@Transactional
public class CompanyDaoImpl implements CompanyDao{
	
private static final Logger logger = Logger.getLogger(CompanyDaoImpl.class);
	
	private DataSource dataSource;
	
	@Autowired
	public void setDataSource(JdbcTemplate jdbcTemplate) {
		this.dataSource = jdbcTemplate.getDataSource();
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
		List<Company> companyList = new ArrayList<Company>();
		String Qry = "SELECT \"companyId\", \"name\", \"address\", \"city\", \"registrationNumber\", \"email\", \"phone\", \"contactName\", \"payment\", \"paymentDate\", \"isActive\" from pigtrax.\"Company\"";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> compRows = jdbcTemplate.queryForList(Qry);
		for (Map<String, Object> compRow : compRows) {
			Company comp = new Company();
			
			comp.setCompanyId(String.valueOf(compRow.get("companyId")));
			comp.setName(String.valueOf(compRow.get("Name")));
			comp.setAddress(String.valueOf(compRow.get("address")));
			comp.setCity(String.valueOf(compRow.get("city")));
			comp.setRegistrationNumber(String.valueOf(compRow.get("registrationNumber")));
			comp.setEmail(String.valueOf(compRow.get("email")));
			comp.setPhone((Integer) compRow.get("phone"));
			comp.setContactName(String.valueOf(compRow.get("contactName")));
			comp.setPayment((BigDecimal) compRow.get("payment"));
			comp.setPaymentDate((Date)compRow.get("paymentDate"));
			comp.setActive((Boolean)compRow.get("isActive"));
			
			companyList.add(comp);
		}
		return companyList;
	}

	@Override
	public Company findByCompanyID(String companyID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
