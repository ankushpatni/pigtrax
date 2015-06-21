package com.pigtrax.usermanagement.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.usermanagement.dao.interfaces.EmployeeDao;
import com.pigtrax.usermanagement.dto.EmployeeDto;

@Repository
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {

private static final Logger logger = Logger.getLogger(EmployeeDaoImpl.class);
	
	private DataSource dataSource;
	
	@Autowired
	public void setDataSource(JdbcTemplate jdbcTemplate) {
		this.dataSource = jdbcTemplate.getDataSource();
	}
	/**
	 * To retrieve all the employee details
	 * @return List<EmployeeDto>
	 */
	public List<EmployeeDto> getEmployeeList() {
		List<EmployeeDto> empList = new ArrayList<EmployeeDto>();
		String Qry = "SELECT \"Emp_ID\", \"Name\", \"Email\" from \"Employee\"";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String,Object>> empRows = jdbcTemplate.queryForList(Qry);
		for(Map<String,Object> empRow : empRows){
	            EmployeeDto emp = new EmployeeDto();
	            emp.setEmpId((Integer)empRow.get("Emp_ID"));
	            emp.setName(String.valueOf(empRow.get("Name")));
	            emp.setEmail(String.valueOf(empRow.get("Email")));
	            empList.add(emp);
	    }
	    return empList;
	}

}
