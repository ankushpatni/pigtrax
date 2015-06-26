package com.pigtrax.usermanagement.service.interfaces;

import java.util.List;

import com.pigtrax.usermanagement.beans.Company;

public interface CompanyService {
	
	/**
	 * To get the list of Company
	 * @return List<Company>
	 */
	public List<Company> getCompanyList();

}
