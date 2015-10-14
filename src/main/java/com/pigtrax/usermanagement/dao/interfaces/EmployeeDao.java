package com.pigtrax.usermanagement.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.beans.Employee;
import com.pigtrax.usermanagement.dto.EmployeeDto;

public interface EmployeeDao {
	/**
	 * To get the list of employees
	 * @return List<EmployeeDto>
	 */
	public List<EmployeeDto> getEmployeeList();
	
	public int insertEmployeeRecord(Employee employee) throws SQLException;
	
	public EmployeeDto findByUserName(String username)  throws SQLException;
	
	public Employee getEmployeeById(int empId);
	
	public int editEmployeeRecord(Employee employee);

	public int activateEmployeeStatus(int employeeId);

	public int deActivateEmployeeStatus(int employeeId);

	public String resetPassword(String oldPassword , String newPassword);
	
	public String forgetPassword(String emailId); 
	
	public String  changePassword(String employeeId, String newPassword);
	
	public Employee  getEmployeeByEmployeeId(String employeeId);
}
