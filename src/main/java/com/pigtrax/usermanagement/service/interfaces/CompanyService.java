package com.pigtrax.usermanagement.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.usermanagement.beans.Company;

public interface CompanyService {
	
	/**
	 * To get the list of Company
	 * @return List<Company>
	 */
	public List<Company> getCompanyList();
	
	/**
	 * Load the Company information based on companyId
	 * @param Company
	 * @return
	 * @throws SQLException
	 */
	public Company findByCompanyID(String companyID)  throws SQLException;
	
	/**
	 * To update Company Status
	 * @return List<Company>
	 */
	public int updateCompanyStatus(final String companyID, final Boolean companyStatus) throws SQLException;
	
	/**
	 * To Insert Company Status
	 * @return int (Success with int>0)
	 */
	
	public int insertCompanyRecord(Company company) throws SQLException;
	
	/**
	 * To update Company record
	 * @return Company
	 */
	
	public int updateCompanyRecord(Company company) throws SQLException;
	
}
