package com.pigtrax.usermanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.usermanagement.beans.Employee;
import com.pigtrax.usermanagement.dao.interfaces.EmployeeDao;
import com.pigtrax.usermanagement.dto.EmployeeDto;



@Repository
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {

private static final Logger logger = Logger.getLogger(EmployeeDaoImpl.class);
	
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
	            emp.setEmployeeId(empRow.get("Emp_ID").toString());
	            emp.setName(String.valueOf(empRow.get("Name")));
	            empList.add(emp);
	    }
	    return empList;
	}
	
	/**
	 * Load the employee information based on employeeId(username)
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public EmployeeDto findByUserName(final String username) throws SQLException {
//		Employee employee = null;
		String Qry = "SELECT pigtrax.\"Employee\".\"id\", \"employeeId\",\"id_Company\", \"name\", \"ptPassword\", \"isActive\", \"isPortalUser\", \"id_RoleType\", \"fieldCode\" from pigtrax.\"Employee\", pigtraxrefdata.\"RoleType\" WHERE \"employeeId\"=? and \"id_RoleType\"=pigtraxrefdata.\"RoleType\".\"id\"";
//		Connection conn = dataSource.getConnection();		
//		PreparedStatement pstmt = conn.prepareStatement(Qry);
//		pstmt.setString(1, username);
//		ResultSet rs = pstmt.executeQuery();
//		if(rs.next())
//		{
//			employee = new Employee();
//			employee.setEmployeeId(rs.getString(2));
//			employee.setCompanyId(rs.getInt(3));
//			employee.setName(rs.getString(4));
//			employee.setPtPassword(rs.getString(5));
//			employee.setActive(rs.getBoolean(6));
//			employee.setPortalUser(rs.getBoolean(7));
//		}
//		rs.close();
//		pstmt.close();
//		conn.close();
//		return employee;
		
		List<EmployeeDto> employeeList = jdbcTemplate.query(Qry, new PreparedStatementSetter(){
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, username);
			}}, new EmployeeMapper());

		if(employeeList != null && employeeList.size() > 0){
			return employeeList.get(0);
		}
		return null;
		
		
	}
	
	
	private static final class EmployeeMapper implements RowMapper<EmployeeDto> {
		public EmployeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeDto employee = new EmployeeDto();
			employee.setId(rs.getInt("id"));
			employee.setEmployeeId(rs.getString("employeeId"));
			employee.setCompanyId(rs.getInt("id_Company"));
			employee.setName(rs.getString("name"));
			employee.setPtPassword(rs.getString("ptPassword"));
			employee.setActive(rs.getBoolean("isActive"));
			employee.setPortalUser(rs.getBoolean("isPortalUser"));
			employee.setUserRoleId(rs.getInt("id_RoleType"));
			employee.setUserRole(rs.getInt("fieldCode"));
			return employee;
		}
	}

}
