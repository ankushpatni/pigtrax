package com.pigtrax.master.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.usermanagement.dto.EmployeeDto;

public interface EmployeeGroupService {
	 List<EmployeeGroupDto> getEmployeeGroups(Integer companyId) throws PigTraxException;
	 
	 List<EmployeeDto> getEmployeeList(Integer companyId, String jobFunction) throws PigTraxException;
	 
	 String saveEmployeeGroup(EmployeeGroupDto dto) throws PigTraxException;
	 
	 EmployeeGroupDto getGroupWithSameEmployees(EmployeeGroupDto employeeGroupDto) throws PigTraxException;
	 
	 int inactivateGroup(EmployeeGroupDto employeeGroupDto) throws PigTraxException;
	 
	 EmployeeGroupDto getEmployeeGroup(Integer employeeGroupIdKey) throws PigTraxException;
}
