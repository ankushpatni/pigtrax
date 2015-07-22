package com.pigtrax.master.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.usermanagement.dto.EmployeeDto;

public interface EmployeeGroupService {
	 List<EmployeeGroupDto> getEmployeeGroups(Integer companyId) throws PigTraxException;
	 
	 List<EmployeeDto> getEmployeeList(Integer companyId, String jobFunction) throws PigTraxException;
	 
	 void saveEmployeeGroup(EmployeeGroupDto dto) throws PigTraxException;
}
