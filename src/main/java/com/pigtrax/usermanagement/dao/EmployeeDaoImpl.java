package com.pigtrax.usermanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.notification.BlockingQueueEmail;
import com.pigtrax.notification.NotificationManager;
import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.beans.Employee;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dao.interfaces.CompanyDao;
import com.pigtrax.usermanagement.dao.interfaces.EmployeeDao;
import com.pigtrax.usermanagement.dto.EmployeeDto;



@Repository
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private NotificationManager notificationManager;
	
	@Autowired
	MessageSource messageSource;
	

private static final Logger logger = Logger.getLogger(EmployeeDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private List<String> bCC = new ArrayList<String>();
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	public void setDataSource(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * To retrieve all the employee details
	 * @return List<EmployeeDto>
	 */
	public List<EmployeeDto> getEmployeeList() {
		List<EmployeeDto> empList = new ArrayList<EmployeeDto>();
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(activeUser.getUserRole()==1 ||activeUser.getUserRole()==2){
		String Qry = "SELECT E.\"id\",E.\"employeeId\", E.\"name\", E.\"email\" , E.\"isActive\",E.\"id_Company\",C.\"companyId\", C.\"name\" as companyName "
				+ "from pigtrax.\"Employee\" E JOIN pigtrax.\"Company\" C ON E.\"id_Company\" = C.\"id\" order by \"id\" desc" ;
		List<Map<String,Object>> empRows = jdbcTemplate.queryForList(Qry);
		for(Map<String,Object> empRow : empRows){
	            EmployeeDto emp = new EmployeeDto();
	            emp.setId(Integer.parseInt(empRow.get("id").toString()));
	            emp.setEmployeeId(empRow.get("employeeId").toString());
	            emp.setName(String.valueOf(empRow.get("name")));
	            emp.setEmail(String.valueOf(empRow.get("email") != null ? (empRow.get("email") ) : ""));
	            emp.setActiveFlag(empRow.get("isActive").toString());
	            emp.setCompanyId(Integer.parseInt(empRow.get("id_Company").toString()));
	            emp.setCompanyName(String.valueOf(empRow.get("companyName")));
	            empList.add(emp);
	            
	    }
		
	    return empList;
	}else if(activeUser.getUserRole()==3){
			String QrySub = "SELECT \"id\",\"employeeId\", \"name\", \"email\" , \"isActive\" from pigtrax.\"Employee\" where \"id_Company\"='"+activeUser.getCompanyId()+"' order by \"id\" desc ";
			List<Map<String,Object>> empRowsSub = jdbcTemplate.queryForList(QrySub);
			for(Map<String,Object> empRow : empRowsSub){
		            EmployeeDto empSub = new EmployeeDto();
		            empSub.setId(Integer.parseInt(empRow.get("id").toString()));
		            empSub.setEmployeeId(empRow.get("employeeId").toString());
		            empSub.setName(String.valueOf(empRow.get("name")));
		            empSub.setEmail(String.valueOf(empRow.get("email") != null ? (empRow.get("email") ) : ""));
		            empSub.setActiveFlag(empRow.get("isActive").toString());
		            empList.add(empSub);     
		    }
		
		
	    return empList;	
	}
		else if(activeUser.getUserRole()==4){
				String QrySub = "SELECT \"id\",\"employeeId\", \"name\", \"email\" , \"isActive\" from pigtrax.\"Employee\" where \"employeeId\"='"+request.getRemoteUser()+"' order by \"id\" desc ";
				List<Map<String,Object>> empRowsSub = jdbcTemplate.queryForList(QrySub);
				for(Map<String,Object> empRow : empRowsSub){
			            EmployeeDto empSub = new EmployeeDto();
			            empSub.setId(Integer.parseInt(empRow.get("id").toString()));
			            empSub.setEmployeeId(empRow.get("employeeId").toString());
			            empSub.setName(String.valueOf(empRow.get("name")));
			            empSub.setEmail(String.valueOf(empRow.get("email") != null ? (empRow.get("email") ) : ""));
			            empSub.setActiveFlag(empRow.get("isActive").toString());
			            empList.add(empSub);
			            
			    }
		    return empList;
			
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
		String Qry = "SELECT pigtrax.\"Employee\".\"id\", \"employeeId\",\"id_Company\", \"name\", \"ptPassword\", "
				+ "\"isActive\", \"isPortalUser\", \"id_RoleType\", \"fieldCode\" from pigtrax.\"Employee\", "
				+ "pigtraxrefdata.\"RoleType\" WHERE \"employeeId\"=? and \"id_RoleType\"=pigtraxrefdata.\"RoleType\".\"id\" and \"isPortalUser\" is true";
		
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

	
	@Override
	public int insertEmployeeRecord(final Employee employee, Locale locale) throws SQLException {
		String password = "pigtrax";
		final String hashedPassword = passwordEncoder.encode(password);
		
		final String query = "INSERT INTO pigtrax.\"Employee\"(\"employeeId\", \"id_Company\", name, \"ptPassword\",\"isActive\",  \"isPortalUser\", \"lastUpdated\", \"userUpdated\", \"id_RoleType\", \"email\")"+
				 "VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)";
	
		KeyHolder holder = new GeneratedKeyHolder();
		
		 int result = jdbcTemplate.update(new PreparedStatementCreator() { 
		        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        	 PreparedStatement ps =
		    	                con.prepareStatement(query, new String[] {"id"});
		        	 ps.setString(1, employee.getEmployeeId());
		        	 ps.setLong(2, employee.getCompanyId());
		        	 ps.setString(3, employee.getName());
		        	 ps.setString(4, hashedPassword);
		        	 ps.setBoolean(5, employee.isActive());
		        	 ps.setBoolean(6, employee.isPortalUser());
		        	 ps.setDate(7, new java.sql.Date(System.currentTimeMillis()));
		        	 ps.setString(8, employee.getUserUpdated());
		        	 ps.setLong(9, employee.getUserRoleId());
		        	 ps.setString(10, employee.getEmail());
		        	 return ps;
				//preparedStatement.setInt(11, employee.getPortalId());
			}
		}, holder);
	 
		 int keyVal = holder.getKey().intValue();
		 logger.info("Key generated = "+keyVal);
		 employee.setId(keyVal);
		 
		 //Insert into Employee Job function
		 insertEmployeeJobFunction(employee);
	 
		 if(result!=0 && employee.isActive()){
			 
			 Company companyDetails = companyDao.findByCompanyByAutoGeneratedId(employee.getCompanyId());
			 
			 BlockingQueueEmail blocking  =  getSuperEmailIdByCompany(employee.getCompanyId(), employee.getEmployeeId() , employee.getEmail() , employee.getName(), companyDetails.getCompanyId(), locale);
			// blocking.populateEmployeeCreationMessage(messageResource);
			 blocking.setPassword(password);	
			 blocking.setLocale(locale);
			 notificationManager.mailThread(blocking);
		 }
		 return result;
	}
	
	private void insertEmployeeJobFunction(final Employee employee){
		String query = "INSERT INTO pigtrax.\"EmployeeJobFunction\"(\"functionName\", \"functionFrom\", \"functionTo\", \"lastUpdated\",\"userUpdated\",  \"id_Employee\")"+
				 "VALUES (?, current_timestamp, null, current_timestamp, ?, ?)";
	
		 int result = jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, "Any");
				preparedStatement.setLong(2, employee.getUserRoleId());
				preparedStatement.setInt(3, employee.getId());
			}
		});
	}
	
	
	@Override
	public Employee getEmployeeById(int empId) {
		List<Employee> empList = null;
		String Qry = "SELECT \"id\", \"employeeId\", \"id_Company\", \"name\", \"ptPassword\", \"isActive\",  \"isPortalUser\", \"lastUpdated\", \"userUpdated\", \"id_RoleType\", \"email\" FROM pigtrax.\"Employee\" where \"id\"="+empId+"";
		List<Map<String,Object>> empRows = jdbcTemplate.queryForList(Qry);
		for(Map<String,Object> empRow : empRows){
			empList  =	new ArrayList<Employee>();
	            Employee emp = new Employee();
	            emp.setId(Integer.parseInt(empRow.get("id").toString()));
	            emp.setEmployeeId(empRow.get("employeeId").toString());
	            emp.setName(String.valueOf(empRow.get("name")));
	            emp.setEmail(String.valueOf(empRow.get("email")));
	            emp.setPortalUser(Boolean.parseBoolean(empRow.get("isPortalUser").toString()));
	            emp.setPtPassword(empRow.get("ptPassword").toString());
	            emp.setActive(Boolean.parseBoolean(empRow.get("isActive").toString()));
	            emp.setCompanyId(Integer.parseInt(empRow.get("id_Company").toString()));
	            emp.setUserRoleId(Integer.parseInt(empRow.get("id_RoleType").toString()));
	          //  emp.setPortalId(Integer.parseInt(empRow.get("portalId").toString()));
	            
	            empList.add(emp);   
	            
	    }
		if(empList != null && 0 < empList.size())
			return empList.get(0);
	    return null;
	}
	@Override
	public int editEmployeeRecord(final Employee employee) {
		 int result = 0;
		try{
			String query = "UPDATE pigtrax.\"Employee\" SET  \"employeeId\"=?, \"id_Company\"=?, \"name\"=?, \"isActive\"=?, \"isPortalUser\"=?, \"lastUpdated\"=?, \"userUpdated\"=?, \"id_RoleType\"=?, \"email\"=?  WHERE \"id\"=?";

			logger.info("in edit employee method -->" + employee.getId());
			result =jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1,employee.getEmployeeId());
					ps.setInt(2,employee.getCompanyId());
					ps.setString(3, employee.getName());
					ps.setBoolean(4, employee.isActive());					
					ps.setBoolean(5, employee.isPortalUser());
					ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
					ps.setString(7, request.getRemoteUser().toString());
					ps.setInt(8, employee.getUserRoleId());
					ps.setString(9, employee.getEmail());
					ps.setInt(10, employee.getId());
				}
			});			
	         return result;
		}catch(Exception e){
			e.printStackTrace();
		}
	return result;
	}
	
	/**
	 * Generates the Email object using the email ids to be sent
	 * @param comanyId
	 * @param employeeId
	 * @param email
	 * @param name
	 * @param companyIdValue
	 * @return
	 */
	public BlockingQueueEmail getSuperEmailIdByCompany(int comanyId , String employeeId , String email , String name, String companyIdValue, Locale locale ){
		
		logger.info("In Blocking Mathod :-  "+comanyId+"    "+employeeId+"   "+email);
		BlockingQueueEmail blockingEmailObj = new BlockingQueueEmail();
		try {
			String Qry ="SELECT  \"id_Company\", \"name\", \"ptPassword\", \"id_RoleType\",  \"email\" FROM "
					+ "pigtrax.\"Employee\" where  ( \"id_Company\" ="+comanyId+" AND \"id_RoleType\"=3) or ( \"id_RoleType\"=1 OR \"id_RoleType\"=2)";
			List<Map<String,Object>> empRows = jdbcTemplate.queryForList(Qry);
			for(Map<String,Object> empRow : empRows){   
					if(empRow.get("email") != null)
					{
		            	bCC.add(empRow.get("email").toString());
		            	blockingEmailObj.setbCC(bCC);
					}
		    }
			blockingEmailObj.setName(name.toString());
			blockingEmailObj.setEmailId(email);
			blockingEmailObj.setCompanyId(companyIdValue);
			blockingEmailObj.setLocale(locale);
			blockingEmailObj.populateEmployeeCreationMessage(messageSource); 
			return blockingEmailObj;
		} catch (Exception e) {
			logger.info(" Exception in blocking queue email");
		}
		return blockingEmailObj;
	}
	@Override
	public int activateEmployeeStatus(final int employeeId) {
		String query = "UPDATE pigtrax.\"Employee\" SET \"isActive\"=?  WHERE \"id\"=?";

		logger.info("in activate Id-->" + employeeId);
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, true);
				ps.setInt(2, employeeId);
			}
		});
	}
	@Override
	public int deActivateEmployeeStatus(final int employeeId) {
		String query = "UPDATE pigtrax.\"Employee\" SET \"isActive\"=?  WHERE \"id\"=?";

		logger.info("in deActivate Id-->" + employeeId);
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, false);
				ps.setInt(2, employeeId);
			}
		});
	}
	@Override
	public String resetPassword(String oldPassword , String newPassword) {
		String result=null;
		String hashedOldPassword = passwordEncoder.encode(oldPassword);
		final String hashedNewPassword = passwordEncoder.encode(newPassword);
		String Qry = "SELECT \"ptPassword\" from pigtrax.\"Employee\" where \"employeeId\"='"+request.getRemoteUser().toString()+"'";
		List<Map<String, Object>> emp = jdbcTemplate.queryForList(Qry);
		for(Map<String,Object> empRow : emp){
		String encPass = empRow.get("ptPassword").toString();
		 if(passwordEncoder.matches(oldPassword, encPass)){
			 String query = "UPDATE pigtrax.\"Employee\" SET \"ptPassword\"=?  WHERE \"employeeId\"=?";

				logger.info("in in update Password-->");
				jdbcTemplate.update(query, new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, hashedNewPassword);
						ps.setString(2, request.getRemoteUser().toString());
					}
				});
				result="label.employee.resetSuccessMess";
			}
		 else{
				result="label.employee.wroungOldPassword";
			}
		}
		
		return result;

}
	@Override
	public String forgetPassword(final String employeeId) {
		String status=null;
		String Qry = "SELECT \"ptPassword\" , \"email\" from pigtrax.\"Employee\" where \"employeeId\"='"+employeeId+"'";
		List<Map<String, Object>> emp = jdbcTemplate.queryForList(Qry);
		if(emp.size()>=1){
			for(Map<String,Object> empRow : emp){      
			logger.info("email address --------->   "+empRow.get("email").toString());
			String password = String.valueOf(employeeId);
			final String hashedPassword = passwordEncoder.encode(password);
			logger.info("Your Forget Password Url Is ------------>"+"localhost:8087/PigTrax/changePassword?otp="+hashedPassword);
			logger.info("in deActivate Id-->" + employeeId);
			}
			status= "success";
		}else{
			status="error";
		}
		return status;
	}
	
	public String changePassword(final String employeeId, final String newPassword) {
		String query = "UPDATE pigtrax.\"Employee\" SET \"ptPassword\"=?  WHERE \"employeeId\"=?";

		logger.info("in in update Password-->");
		jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, passwordEncoder.encode(newPassword));
				ps.setString(2, employeeId);
			}
		});
		return "success";	
	}
	
	
	@Override
	public Employee getEmployeeByEmployeeId(String employeeId) {
		List<Employee> empList = null;
		String Qry = "SELECT \"id\", \"employeeId\", \"id_Company\", \"name\", \"ptPassword\", \"isActive\",  \"isPortalUser\", \"lastUpdated\","
				+ " \"userUpdated\", \"id_RoleType\", \"email\" FROM pigtrax.\"Employee\" where \"employeeId\"='"+employeeId+"'";
		List<Map<String,Object>> empRows = jdbcTemplate.queryForList(Qry);
		for(Map<String,Object> empRow : empRows){
			empList  =	new ArrayList<Employee>();
	            Employee emp = new Employee();
	            emp.setId(Integer.parseInt(empRow.get("id").toString()));
	            emp.setEmployeeId(empRow.get("employeeId").toString());
	            emp.setName(String.valueOf(empRow.get("name")));
	            emp.setEmail(String.valueOf(empRow.get("email")));
	            emp.setPortalUser(Boolean.parseBoolean(empRow.get("isPortalUser").toString()));
	            emp.setPtPassword(empRow.get("ptPassword").toString());
	            emp.setActive(Boolean.parseBoolean(empRow.get("isActive").toString()));
	            emp.setCompanyId(Integer.parseInt(empRow.get("id_Company").toString()));
	            emp.setUserRoleId(Integer.parseInt(empRow.get("id_RoleType").toString()));
	          //  emp.setPortalId(Integer.parseInt(empRow.get("portalId").toString()));
	            
	            empList.add(emp);   
	            
	    }
		if(empList != null && 0 < empList.size())
			return empList.get(0);
	    return null;
	}
}
