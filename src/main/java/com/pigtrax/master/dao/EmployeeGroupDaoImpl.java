package com.pigtrax.master.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
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
	
	public EmployeeGroupDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public EmployeeGroupDto getEmployeeGroup(final Integer employeeGroupIdKey)
			throws SQLException {
		String qry = "Select EG.\"id\", EG.\"groupId\", EG.\"id_EmployeeJobFunction\", EJF.\"id_Employee\", EJF.\"functionName\", E.\"employeeId\", E.\"name\", EJF.\"functionName\" "
				+ "from pigtrax.\"EmployeeGroup\" EG "
				+ "JOIN pigtrax.\"EmployeeJobFunction\" EJF on EG.\"id_EmployeeJobFunction\" = EJF.\"id\" "
				+ "JOIN pigtrax.\"Employee\" E on EJF.\"id_Employee\" = E.\"id\" "
				+ "WHERE  EG.\"isActive\" is TRUE and EJF.\"functionTo\" is NULL and EG.\"groupId\" = (select \"groupId\" from pigtrax.\"EmployeeGroup\" where \"id\" = ?) Order by EG.\"groupId\"";
		List<EmployeeGroupDto> employeeGroupList = jdbcTemplate.query(qry,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, employeeGroupIdKey);
					}
				}, new EmployeeGroupMapper());

		
		if (employeeGroupList != null && employeeGroupList.size() > 0) {
			return employeeGroupList.get(0);
		}
		return null;
	}
	
	
	
	

	@Override
	public List<EmployeeGroupDto> getEmployeeGroups(final Integer companyId)
			throws SQLException {
		String qry = "Select EG.\"id\", EG.\"groupId\", EG.\"id_EmployeeJobFunction\", EJF.\"id_Employee\", EJF.\"functionName\", E.\"employeeId\", E.\"name\", EJF.\"functionName\" "
				+ "from pigtrax.\"EmployeeGroup\" EG "
				+ "JOIN pigtrax.\"EmployeeJobFunction\" EJF on EG.\"id_EmployeeJobFunction\" = EJF.\"id\" "
				+ "JOIN pigtrax.\"Employee\" E on EJF.\"id_Employee\" = E.\"id\" "
				+ "WHERE  EG.\"isActive\" is TRUE and EJF.\"functionTo\" is NULL and E.\"id_Company\" = ? Order by EG.\"groupId\"";

		List<EmployeeGroupDto> employeeGroupList = jdbcTemplate.query(qry,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, companyId);
					}
				}, new EmployeeGroupMapper());

		logger.info("employeeGroupList in Dao impl : "
				+ employeeGroupList.size());

		if (employeeGroupList != null && employeeGroupList.size() > 0) {
			return employeeGroupList;
		}
		return null;
	}

	private static final class EmployeeGroupMapper implements
			ResultSetExtractor<List<EmployeeGroupDto>> {

		@Override
		public List<EmployeeGroupDto> extractData(ResultSet rs)
				throws SQLException, DataAccessException {
			List<EmployeeGroupDto> employeeGroupList = new ArrayList<EmployeeGroupDto>();
			EmployeeGroupDto grp = new EmployeeGroupDto();
			String groupId = "";
			List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
			while (rs.next()) {
				EmployeeDto employeeDto = new EmployeeDto();
				if (!groupId.equals(rs.getString("groupId"))) {
					groupId = rs.getString("groupId");
					grp = new EmployeeGroupDto();
					employeeList = new ArrayList<EmployeeDto>();
					grp.setEmployeeList(employeeList);
					employeeGroupList.add(grp);
				}
				grp.setId(rs.getInt("id"));
				grp.setGroupId(groupId);
				grp.setEmployeeJobFunctionId(rs.getInt("id_EmployeeJobFunction"));
				grp.setEmployeeJobFunction(rs.getString("functionName"));

				employeeDto.setId(rs.getInt("id_Employee"));
				employeeDto.setEmployeeId(rs.getString("employeeId"));
				employeeDto.setName(rs.getString("name"));
				employeeDto.setSelected(true);
				employeeList.add(employeeDto);

			}
			return employeeGroupList;
		}
	}

	public List<EmployeeDto> getEmployeeList(final Integer companyId,
			final String jobFunction) throws SQLException {
		List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
		String qry = "Select E.\"id\", E.\"employeeId\", E.\"name\", EJF.\"id\" as \"employeeJobId\" "
				+ "from pigtrax.\"Employee\" E "
				+ " join pigtrax.\"EmployeeJobFunction\" EJF On E.\"id\" = EJF.\"id_Employee\" "
				+ "where E.\"id_Company\" = ? and lower(EJF.\"functionName\") = ? and E.\"isActive\" is true order by E.\"name\"";

		employeeList = jdbcTemplate.query(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, companyId);
				ps.setString(2, jobFunction.trim().toLowerCase());
			}
		}, new EmployeeMapper());

		return employeeList;
	}

	private static final class EmployeeMapper implements
			ResultSetExtractor<List<EmployeeDto>> {

		@Override
		public List<EmployeeDto> extractData(ResultSet rs) throws SQLException,
				DataAccessException {

			List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
			while (rs.next()) {
				EmployeeDto employeeDto = new EmployeeDto();
				employeeDto.setId(rs.getInt("id"));
				employeeDto.setEmployeeId(rs.getString("employeeId"));
				employeeDto.setName(rs.getString("name"));
				employeeDto.setEmployeeJobId(rs.getInt("employeeJobId"));
				employeeList.add(employeeDto);

			}
			return employeeList;
		}
	}

	public void addEmployeeGroup(final EmployeeGroupDto employeeGroupDto)
			throws SQLException, DuplicateKeyException {
		
		final String qry = "insert into pigtrax.\"EmployeeGroup\"(\"groupId\", \"isActive\", \"lastUpdated\", \"userUpdated\", \"id_EmployeeJobFunction\") "
				+ "values(?,true,current_timestamp,?,?)";
		if(employeeGroupDto != null)
		{
			List<EmployeeDto> employeeDtoList = employeeGroupDto.getEmployeeList();
			for(final EmployeeDto employeeDto : employeeDtoList)
			{
				jdbcTemplate.update(
			    	    new PreparedStatementCreator() {
			    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			    	            PreparedStatement ps =
			    	            con.prepareStatement(qry, new String[] {"id"});
			    	            ps.setString(1, employeeGroupDto.getGroupId());
			    				ps.setString(2, employeeGroupDto.getUserUpdated());
			    				ps.setInt(3, employeeDto.getEmployeeJobId());
			    			
			    	            return ps;
			    	        }
			    	  });
			}
		}

	}
	
	
	@Override
	public boolean isGroupExistsWithId(final String groupId, final Integer companyId) throws SQLException
	{
		final String qry = "Select count(EG.\"id\") from pigtrax.\"EmployeeGroup\" EG join pigtrax.\"EmployeeJobFunction\" EJF on EG.\"id_EmployeeJobFunction\" = EJF.\"id\" "
				+ " JOIN pigtrax.\"Employee\" E on EJF.\"id_Employee\" = E.\"id\" where EG.\"groupId\" = ? and E.\"id_Company\" = ?";
		
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, groupId);
					ps.setInt(2, companyId);
				}
			},
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getInt(1);
		            }
		            return null;
		          }
		        });
		
		return cnt > 0? true : false;
	}
	
	
	/**
	 * Deletes a group from a company
	 * @param groupId
	 * @param companyId
	 * @return
	 * @throws SQLException
	 */
	public int deleteEmployeeGroup(final String groupId, final int companyId)
			throws SQLException {
		final String qry = "delete from pigtrax.\"EmployeeGroup\" where \"groupId\" = ? and  \"id_EmployeeJobFunction\" in (select \"id\" from pigtrax.\"EmployeeJobFunction\" where \"id_Employee\" in (select \"id\" from pigtrax.\"Employee\" where \"id_Company\" = ?))";
		return this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setString(1, groupId);
				ps.setInt(2, companyId);
			}
		});
	}

	
	public int inactivateGroup(final String groupId, final int companyId)
			throws SQLException {
		final String qry = "update pigtrax.\"EmployeeGroup\" set \"isActive\" = FALSE where \"groupId\" = ? and  \"id_EmployeeJobFunction\" in (select \"id\" from pigtrax.\"EmployeeJobFunction\" where \"id_Employee\" in (select \"id\" from pigtrax.\"Employee\" where \"id_Company\" = ?))";
		return this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setString(1, groupId);
				ps.setInt(2, companyId);
			}
		});
	}
	
}
