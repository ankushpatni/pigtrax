package com.pigtrax.usermanagement.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public Company findByCompanyID(String companyID) throws SQLException {
		// TODO Auto-generated method stub
		return companyDao.findByCompanyID(companyID);
	}

	
	@Override
	public int updateCompanyStatus(final String companyID, final Boolean companyStatus) throws SQLException
	{
		return companyDao.updateCompanyStatus(companyID, companyStatus);
	}
	
	@Override
	public int insertCompanyRecord(Company company) throws SQLException
	{
		return companyDao.insertCompanyRecord(company);
	}
	
	public int updateCompanyRecord(Company company) throws SQLException
	{
		return companyDao.updateCompanyRecord(company);
	}

	@Override
	public Company findByCompanyByAutoGeneratedId(int generatedCompanyId) throws SQLException 
	{
		return companyDao.findByCompanyByAutoGeneratedId(generatedCompanyId);
	}
	
	@Override
	public Map<String,Integer> getCompanyListNameMap() {
		Map<String,Integer> nameMap = new LinkedHashMap<String,Integer>();
		List<Company> listCompany =  companyDao.getCompanyList();
		
		for(Company company : listCompany)
		{
			nameMap.put(company.getName() +"-"+company.getCompanyId(), company.getId());
		}
		return nameMap;
	}
	
	public void cleanupCompanyData(Integer companyId) {
		companyDao.cleanupCompany(companyId);
	}

	
}
