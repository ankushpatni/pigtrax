package com.pigtrax.usermanagement.dao.interfaces;

import java.util.List;

import com.pigtrax.usermanagement.dto.EmployeeDto;

public interface EmployeeDao {
	/**
	 * To get the list of employees
	 * @return List<EmployeeDto>
	 */
	public List<EmployeeDto> getEmployeeList();
}
