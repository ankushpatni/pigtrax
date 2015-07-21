package com.pigtrax.master.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.master.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.usermanagement.dto.EmployeeDto;


@Repository
@Transactional
public class EmployeeGroupDaoImpl implements EmployeeGroupDao {
	private static Logger logger = Logger.getLogger(EmployeeGroupDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<EmployeeGroupDto> getEmployeeGroups(final Integer companyId) throws SQLException{
		String qry = "Select EG.\"id\", EG.\"groupId\", EG.\"id_EmployeeJobFunction\", EJF.\"id_Employee\", EJF.\"functionName\", E.\"employeeId\", E.\"name\" "
				+ "from pigtrax.\"EmployeeGroup\" EG "
				+ "JOIN pigtrax.\"EmployeeJobFunction\" EJF on EG.\"id_EmployeeJobFunction\" = EJF.\"id\" "
				+ "JOIN pigtrax.\"Employee\" E on EJF.\"id_Employee\" = E.\"id\" "
				+ "WHERE  EG.\"isActive\" is true and EJF.\"functionTo\" is NULL and E.\"id_Company\" = ? Order by EG.\"groupId\"";
		
		List<EmployeeGroupDto> employeeGroupList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, companyId);
			}}, new EmployeeGroupMapper());

		logger.info("employeeGroupList in Dao impl : "+employeeGroupList.size());
		
		if(employeeGroupList != null && employeeGroupList.size() > 0){
			return employeeGroupList;
		}
		return null;
	}
	
	
	private static final class EmployeeGroupMapper implements ResultSetExtractor<List<EmployeeGroupDto>> {
		
		@Override
		public List<EmployeeGroupDto> extractData(ResultSet rs)
				throws SQLException, DataAccessException {
			List<EmployeeGroupDto> employeeGroupList = new ArrayList<EmployeeGroupDto>();
			EmployeeGroupDto grp = new EmployeeGroupDto();
			String groupId = "";
			List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
			while(rs.next())
			{
				EmployeeDto employeeDto = new EmployeeDto();				
				if(!groupId.equals(grp.getGroupId()))
				{
					groupId = rs.getString("groupId");
					grp = new EmployeeGroupDto();
					employeeList = new ArrayList<EmployeeDto>();
					grp.setEmployeeList(employeeList);
					employeeGroupList.add(grp);
				}
				grp.setId(rs.getInt("id"));
				grp.setGroupId(groupId);
				grp.setEmployeeJobFunctionId(rs.getInt("id_EmployeeJobFunction"));
				
				employeeDto.setId(rs.getInt("id_Employee"));
				employeeDto.setEmployeeId(rs.getString("employeeId"));
				employeeDto.setName(rs.getString("name"));
				employeeList.add(employeeDto);
				
			}
			return employeeGroupList;
		}
	}
}
