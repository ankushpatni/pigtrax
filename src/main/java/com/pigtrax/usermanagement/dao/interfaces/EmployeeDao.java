package com.pigtrax.usermanagement.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.usermanagement.beans.Employee;
import com.pigtrax.usermanagement.dto.EmployeeDto;

public interface EmployeeDao {
	/**
	 * To get the list of employees
	 * @return List<EmployeeDto>
	 */
	public List<EmployeeDto> getEmployeeList();
	
	/**
	 * Load the employee information based on employeeId(username)
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public EmployeeDto findByUserName(String username)  throws SQLException;
}
