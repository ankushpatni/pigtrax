package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dto.GroupEventBuilder;
import com.pigtrax.pigevents.dto.GroupEventDto;
import com.pigtrax.pigevents.service.interfaces.GroupEventDetailsService;

@Repository
public class GroupEventDetailsServiceImpl implements GroupEventDetailsService{
	
private static final Logger logger = Logger.getLogger(GroupEventDetailsServiceImpl.class);
	
	@Autowired 
	GroupEventDetailsDao groupEventDetailsDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	EmployeeGroupService employeeGroupService;

	@Override
	public List<GroupEventDetails> groupEventDetailsListByGroupId(int groupId) {
		return groupEventDetailsDao.groupEventDetailsListByGroupId(groupId);
	}

	@Override
	public GroupEventDto groupEventDetailsListById(Integer id) throws PigTraxException {
		GroupEventDto groupEventDto = null;
		GroupEventDetails groupEventDetails = groupEventDetailsDao
				.groupEventDetailsListById(id);

		if (null != groupEventDetails) {
			groupEventDto = GroupEventBuilder.convertToDto(groupEventDetails);
			// Get the employee group details
			EmployeeGroupDto empGrpDto;

			empGrpDto = employeeGroupService.getEmployeeGroup(groupEventDto
					.getEmployeeGroupId());

			groupEventDto.setEmployeeGroup(empGrpDto);
		}

		return groupEventDto;
	}

	@Override
	public int updateGroupEventDetails(GroupEventDto groupEventDetails)
			throws PigTraxException {
		try {
			return groupEventDetailsDao.updateGroupEventDetails(GroupEventBuilder.convertToBean(groupEventDetails));
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

	@Override
	public int addGroupEventDetails(final GroupEventDto groupEventDto) throws PigTraxException {
		try
		{
			return groupEventDetailsDao.addGroupEventDetails(GroupEventBuilder.convertToBean(groupEventDto));
		}
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		
	}

	@Override
	public void deleteGroupEventDetailsByGroupId(Integer id) throws SQLException {
		groupEventDetailsDao.deleteGroupEventDetailsByGroupId(id);
		
	}

}
