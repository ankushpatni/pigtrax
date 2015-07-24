package com.pigtrax.master.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;
import com.pigtrax.usermanagement.dto.EmployeeDto;

@Repository
public class EmployeeGroupServiceImpl implements EmployeeGroupService {
	
	@Autowired
	private EmployeeGroupDao employeeGroupDao;
	
	/**
	 * Get the list of employee groups in a company
	 */
	@Override
	public List<EmployeeGroupDto> getEmployeeGroups(Integer companyId) throws PigTraxException{
		try {
			return employeeGroupDao.getEmployeeGroups(companyId);
		} catch (SQLException e) {
			throw new PigTraxException("Sql error occurred", e.getSQLState());
		}
	}
	
	/**
	 * Get the list of employees based on company Id
	 */
	@Override
	public List<EmployeeDto> getEmployeeList(Integer companyId,
			String jobFunction) throws PigTraxException {
		try {
			return employeeGroupDao.getEmployeeList(companyId, jobFunction);
		} catch (SQLException e) {
			throw new PigTraxException("Sql error occurred", e.getSQLState());
		}
	}
	
	public String saveEmployeeGroup(EmployeeGroupDto dto) throws PigTraxException {
		
		try {
			if(dto != null && dto.getId() > 0)
			{
				employeeGroupDao.deleteEmployeeGroup(dto.getGroupId(), dto.getCompanyId());
				employeeGroupDao.addEmployeeGroup(dto);
				return "success";
			}
			else
			{
				if(!isGroupExistsWithId(dto.getGroupId(), dto.getCompanyId()))	
				{
					employeeGroupDao.addEmployeeGroup(dto);
					return "success";
				}
				else
				{
					return "duplicate";
				}
			}
		
		} catch (SQLException e) {
			throw new PigTraxException("Sql error occurred", e.getSQLState());
		}
	}
	
	private boolean isGroupExistsWithId(String groupId, Integer companyId)
			throws PigTraxException {
		try {
		return employeeGroupDao.isGroupExistsWithId(groupId, companyId);
		}
		catch(SQLException sqlEx)
		{
			throw new PigTraxException("Exception occurred", "");
		}
		
	}
	
	/**
	 * Find the existing group with the given list of employees
	 */
	public EmployeeGroupDto getGroupWithSameEmployees(
			EmployeeGroupDto employeeGroupDto) throws PigTraxException {
		boolean flag = false;
		EmployeeGroupDto matchingGroup = null;
		try {
			List<EmployeeGroupDto> employeeGroups = employeeGroupDao.getEmployeeGroups(employeeGroupDto.getCompanyId());
			if(employeeGroups == null)
				return null;
			
			else if(employeeGroups != null && employeeGroupDto.getEmployeeList() != null)
			{
				for(EmployeeGroupDto employeeGroup : employeeGroups)
				{
					if(employeeGroup.getEmployeeList().size() == employeeGroupDto.getEmployeeList().size())
					{
						List<EmployeeDto> dbEmployeeList = employeeGroup.getEmployeeList();
						for(EmployeeDto employeeDto : employeeGroupDto.getEmployeeList())
						{
							if(dbEmployeeList.contains(employeeDto))
							{
								flag = true;
							}
							else
							{
								flag = false;
								break;
							}
						}
					}
					if(flag){
						matchingGroup = employeeGroup;
						break;
					}
				}
			}
			else
				return null;
		} catch (SQLException e) {
			throw new PigTraxException("Exception occurred", e.getSQLState());
		}
		
		return matchingGroup;
	}
	
	
	/**
	 * Delete employee group based on group Id and company
	 */
	public int inactivateGroup(EmployeeGroupDto employeeGroupDto)
			throws PigTraxException {
		try{
			
			return employeeGroupDao.inactivateGroup(employeeGroupDto.getGroupId(), employeeGroupDto.getCompanyId());			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			throw new PigTraxException("Execption occurred", ex.getSQLState());
		}		
	}
}
