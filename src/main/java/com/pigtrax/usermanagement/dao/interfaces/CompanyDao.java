package com.pigtrax.usermanagement.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.usermanagement.beans.Company;

public interface CompanyDao {
	
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

}
