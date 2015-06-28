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
	 * To update Company Status
	 * @return List<Company>
	 */
	public int updateCompanyStatus(final String companyID, final Boolean companyStatus) throws SQLException;
	
}
