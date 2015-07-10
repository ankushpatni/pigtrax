package com.pigtrax.usermanagement.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.location.Premises;
import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.dao.interfaces.PremisesDao;
import com.pigtrax.usermanagement.service.interfaces.PremisesService;

@Repository
public class PremisesServiceImpl implements PremisesService{

	@Autowired
	private PremisesDao premisesDao;

	@Override
	public List<Premises> getPremisesList(int generatedCompanyId) {
		return premisesDao.getPremisesList( generatedCompanyId );
	}

	@Override
	public int updatePremisesStatus(String premisesID, Boolean premisesStatus)
			throws SQLException {
		return premisesDao.updatePremisesStatus(premisesID, premisesStatus);
	}
	
	@Override
	public int insertPremisesRecord(Premises premises) throws SQLException
	{
		return premisesDao.insertPremisesRecord(premises);
	}
	
	@Override
	public int updatePremisesRecord(Premises premises) throws SQLException
	{
		return premisesDao.updatePremisesRecord(premises);
	}

}
