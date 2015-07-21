package com.pigtrax.master.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;

@Repository
public class EmployeeGroupServiceImpl implements EmployeeGroupService {
	
	@Autowired
	private EmployeeGroupDao employeeGroupDao;
	
	@Override
	public List<EmployeeGroupDto> getEmployeeGroups(Integer companyId) throws PigTraxException{
		try {
			return employeeGroupDao.getEmployeeGroups(companyId);
		} catch (SQLException e) {
			throw new PigTraxException("Sql error occurred", e.getSQLState());
		}
	}
}
