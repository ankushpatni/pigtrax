package com.pigtrax.usermanagement.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		String Qry = "SELECT \"id\",\"companyId\", \"name\", \"address\", \"city\", \"registrationNumber\", \"email\", \"phone\", \"contactName\", \"payment\", \"paymentDate\", \"isActive\" from pigtrax.\"Company\" order by \"id\" desc ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> compRows = jdbcTemplate.queryForList(Qry);
		for (Map<String, Object> compRow : compRows) {
			Company comp = new Company();
			
			comp.setId((Integer)compRow.get("id"));
			comp.setCompanyId(String.valueOf(compRow.get("companyId")));
			comp.setName(String.valueOf(compRow.get("Name")));
			comp.setAddress(String.valueOf(compRow.get("address")));
			comp.setCity(String.valueOf(compRow.get("city")));
			comp.setRegistrationNumber(String.valueOf(compRow.get("registrationNumber")));
			comp.setEmail(String.valueOf(compRow.get("email")));
			comp.setPhone(String.valueOf(compRow.get("phone")));
			comp.setContactName(String.valueOf(compRow.get("contactName")));
			comp.setPayment((BigDecimal) compRow.get("payment"));
			comp.setPaymentDate((Date)compRow.get("paymentDate"));
			comp.setActive((Boolean)compRow.get("isActive"));
			
			companyList.add(comp);
		}
		return companyList;
	}
	
	public int updateCompanyStatus(final String companyID, final Boolean companyStatus) throws SQLException
	{
		String Qry = "update pigtrax.\"Company\" SET \"isActive\"=?  WHERE \"companyId\"=?";
		Connection conn = dataSource.getConnection();		
		PreparedStatement pstmt = conn.prepareStatement(Qry);
		int returnvalue = 0;
		try
		{
		pstmt.setBoolean(1, !companyStatus);
		pstmt.setString(2, companyID);
		
		System.out.println("Status-->"+companyStatus);
		System.out.println("Id-->" + companyID);
		System.out.println(Qry);

		returnvalue =  pstmt.executeUpdate();
		System.out.println("insode try"+returnvalue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//return 0;
		}
		finally
		{
			pstmt.close();
			conn.close();
		}
		System.out.println(returnvalue);
		return returnvalue;
		
		/*		UPDATE pigtrax."Company"
		   SET "isActive"=false WHERE "companyId" = '1';*/
	
	}

	@Override
	public Company findByCompanyID(String companyID) throws SQLException {
		// TODO Auto-generated method stub
		List<Company> companyList = new ArrayList<Company>();
		String Qry = "SELECT \"id\",\"companyId\", \"name\", \"address\", \"city\", \"registrationNumber\", \"email\", \"phone\", \"contactName\", \"payment\", \"paymentDate\", \"isActive\" from pigtrax.\"Company\"  WHERE \"companyId\"=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Connection conn = dataSource.getConnection();	
		Company comp = null;
		PreparedStatement pstmt = conn.prepareStatement(Qry);
		int returnvalue = 0;
		try {
			
			pstmt.setString(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			
			while ( rs.next()) {
				comp = new Company();
				comp.setCompanyId(String.valueOf(rs.getString("companyId")));
				comp.setName(String.valueOf(rs.getString("Name")));
				comp.setAddress(String.valueOf(rs.getString("address")));
				comp.setCity(String.valueOf(rs.getString("city")));
				comp.setRegistrationNumber(rs.getString("registrationNumber"));
				comp.setEmail(String.valueOf(rs.getString("email")));
				comp.setPhone(String.valueOf(rs.getString("phone")));
				comp.setContactName(String.valueOf(rs.getString("contactName")));
				comp.setPayment((BigDecimal) rs.getBigDecimal("payment"));
				comp.setPaymentDate((Date) rs.getDate("paymentDate"));
				comp.setActive((Boolean) rs.getBoolean("isActive"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// return 0;
		} finally {
			pstmt.close();
			conn.close();
		}
		System.out.println(returnvalue);
		
		return comp;
	}

	@Override
	public int insertCompanyRecord(Company company) throws SQLException {
		
		
		String Qry = "INSERT INTO pigtrax.\"Company\"(  \"companyId\", name, address, city, \"registrationNumber\", email, phone,"
					 +" \"contactName\", payment, \"paymentDate\", \"isActive\", \"lastUpdated\",\"userUpdated\")"+
					 "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		
		Connection conn = dataSource.getConnection();		
		PreparedStatement pstmt = conn.prepareStatement(Qry);
		int returnvalue = 0;
		try
		{
		pstmt.setString(1, company.getCompanyId());
		pstmt.setString(2, company.getName());
		pstmt.setString(3, company.getAddress());
		pstmt.setString(4, company.getCity());
		pstmt.setString(5, company.getRegistrationNumber());
		pstmt.setString(6, company.getEmail());
		pstmt.setString(7, company.getPhone());
		pstmt.setString(8, company.getContactName());
		pstmt.setBigDecimal(9, company.getPayment());
		//pstmt.setDate(10, new  java.sql.Date(company.getPaymentDate().getTime()));
		if(company.getPayment() == null || company.getPayment().equals("") || company.getPayment().equals("null"))
		{
			pstmt.setDate(10, null);
		}
		
		else
		{
			pstmt.setDate(10, new java.sql.Date(System.currentTimeMillis()));
		}
		pstmt.setBoolean(11, company.isActive());
		pstmt.setDate(12,new  java.sql.Date(System.currentTimeMillis()));
		pstmt.setString(13, "Ankush");
		
		System.out.println(Qry);

		returnvalue = pstmt.executeUpdate();
		 
		System.out.println("Record is inserted into Company table!"+returnvalue);

		} 
		catch (SQLException e) {
	
			System.out.println(e.getMessage());
	
		} 
		finally {
	
			if (pstmt != null) {
				pstmt.close();
			}
	
			if (conn != null) {
				conn.close();
			}
	
		}
			
		return returnvalue;
	}
	
	public int updateCompanyRecord(Company company) throws SQLException {
		String qry1 = "update pigtrax.\"Company\" SET name=?, address=?, city =?, \"registrationNumber\" =?, email=?, phone=?, \"contactName\"=?, payment=?, \"paymentDate\"=?  WHERE \"companyId\"=?";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(qry1);
		int returnvalue = 0;
		try
		{
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getAddress());
			pstmt.setString(3, company.getCity());
			pstmt.setString(4, company.getRegistrationNumber());
			pstmt.setString(5, company.getEmail());
			pstmt.setString(6, company.getPhone());
			pstmt.setString(7, company.getContactName());
			pstmt.setBigDecimal(8, company.getPayment());
			if(company.getPayment() == null || company.getPayment().equals("") || company.getPayment().equals("null"))
			{
				pstmt.setDate(9, null);
			}			
			else
			{
				pstmt.setDate(9, new java.sql.Date(System.currentTimeMillis()));
			}
			pstmt.setString(10, company.getCompanyId());

			returnvalue = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// return 0;
		} finally {
			pstmt.close();
			conn.close();
		}
		return returnvalue;
	}

}
