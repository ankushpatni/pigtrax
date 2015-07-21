package com.pigtrax.master.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dto.EmployeeGroupDto;

public interface EmployeeGroupService {
	 List<EmployeeGroupDto> getEmployeeGroups(Integer companyId) throws PigTraxException;
}
