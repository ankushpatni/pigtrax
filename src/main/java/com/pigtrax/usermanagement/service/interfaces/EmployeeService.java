package com.pigtrax.usermanagement.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.usermanagement.beans.Employee;
import com.pigtrax.usermanagement.dto.EmployeeDto;

public interface EmployeeService {

	String resetPassword(String oldPassword, String newPassword);
	
public List<EmployeeDto> getEmployeeList();
	
	public int insertEmployeeRecord(Employee employee) throws SQLException;

	public Employee getEmployeeById(int empId);
	
	public int editEmployeeRecord(Employee employee);

	public int activateEmployeeStatus(int employeeId);

	public int deActivateEmployeeStatus(int employeeId);
	
	public String forgetPassword(String emailId);

	public String  changePassword(String newPassword, String reEnterPassword,String parameter); 

}
