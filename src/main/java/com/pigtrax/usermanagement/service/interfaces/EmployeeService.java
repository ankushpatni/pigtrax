package com.pigtrax.usermanagement.service.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import com.pigtrax.usermanagement.beans.Employee;
import com.pigtrax.usermanagement.dto.EmployeeDto;

public interface EmployeeService {

	String resetPassword(String oldPassword, String newPassword);
	
public List<EmployeeDto> getEmployeeList();
	
	public int insertEmployeeRecord(Employee employee, Locale locale) throws SQLException;

	public Employee getEmployeeById(int empId);
	
	public int editEmployeeRecord(Employee employee);

	public int activateEmployeeStatus(int employeeId);

	public int deActivateEmployeeStatus(int employeeId);
	
	public String forgetPassword(String emailId, Locale locale);

	public String  changePassword(String employeeId, String newPassword); 
	
	public Employee getEmployeeByEmployeeId(String employeeId);
	
	List<Employee> getEmployeeRoles(int empId, String language);

}
