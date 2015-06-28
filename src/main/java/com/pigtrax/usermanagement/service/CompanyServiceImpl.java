package com.pigtrax.usermanagement.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.dao.interfaces.CompanyDao;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@Repository
public class CompanyServiceImpl implements CompanyService{
	
	@Autowired
	private CompanyDao companyDao;

	@Override
	public List<Company> getCompanyList() {
		return companyDao.getCompanyList();
	}
	
	public int updateCompanyStatus(final String companyID, final Boolean companyStatus) throws SQLException
	{
		return companyDao.updateCompanyStatus(companyID, companyStatus);
	}
	

}
