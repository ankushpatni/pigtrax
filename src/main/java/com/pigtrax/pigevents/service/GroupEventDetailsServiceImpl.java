package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dto.GroupEventBuilder;
import com.pigtrax.pigevents.dto.GroupEventDto;
import com.pigtrax.pigevents.service.interfaces.GroupEventDetailsService;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;

@Repository
public class GroupEventDetailsServiceImpl implements GroupEventDetailsService{
	
private static final Logger logger = Logger.getLogger(GroupEventDetailsServiceImpl.class);
	
	@Autowired 
	GroupEventDetailsDao groupEventDetailsDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	EmployeeGroupService employeeGroupService;
	
	@Autowired
	GroupEventService groupEventService;

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
	@Transactional("ptxJTransactionManager")
	public int updateGroupEventDetails(GroupEventDto groupEventDto)
			throws PigTraxException {
		int returnValue = 0;
		int inventoryUpdatevalue=0;
		int previousInventoryValue = 0;
		try 
		{
			GroupEvent groupEvent = groupEventService.getGroupEventByGeneratedGroupId(groupEventDto.getGroupId(),groupEventDto.getCompanyId());
			GroupEventDetails previousGroupEventDetails = groupEventDetailsDao.groupEventDetailsListById(groupEventDto.getId());
			//inventoryUpdatevalue = groupEventDto.getNumberOfPigs();
			if(null != groupEventDto && groupEventDto.getNumberOfPigs() !=0)
			{
				inventoryUpdatevalue = groupEventDto.getNumberOfPigs();
				/*if(null != groupEventDto.getInventoryAdjustment() && groupEventDto.getInventoryAdjustment()>0)
				{
					inventoryUpdatevalue = inventoryUpdatevalue-groupEventDto.getInventoryAdjustment();					
				}*/
			}
			if(null != previousGroupEventDetails && previousGroupEventDetails.getNumberOfPigs() !=0)
			{
				previousInventoryValue = previousGroupEventDetails.getNumberOfPigs();
				/*if(null != previousGroupEventDetails.getInventoryAdjustment() && previousGroupEventDetails.getInventoryAdjustment()>0)
				{
					previousInventoryValue = inventoryUpdatevalue-groupEventDto.getInventoryAdjustment();					
				}*/
			}
			returnValue = groupEventDetailsDao.updateGroupEventDetails(GroupEventBuilder.convertToBean(groupEventDto));

			if(groupEvent.getCurrentInventory() != null)
			{
				groupEvent.setCurrentInventory(groupEvent.getCurrentInventory() + inventoryUpdatevalue-previousInventoryValue);
			}
			groupEventService.updateGroupEventCurrentInventory(groupEvent);
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		return returnValue;
	}

	@Transactional("ptxJTransactionManager")
	public int addGroupEventDetails(final GroupEventDto groupEventDto) throws PigTraxException {
		int returnValue = 0;
		int inventoryUpdatevalue=0;
		try
		{
			returnValue =  groupEventDetailsDao.addGroupEventDetails(GroupEventBuilder.convertToBean(groupEventDto));
			GroupEvent groupEvent = groupEventService.getGroupEventByGeneratedGroupId(groupEventDto.getGroupId(),groupEventDto.getCompanyId());
			if(null != groupEventDto && groupEventDto.getNumberOfPigs() !=0)
			{
				inventoryUpdatevalue = groupEventDto.getNumberOfPigs();
				/*if(null != groupEventDto.getInventoryAdjustment() && groupEventDto.getInventoryAdjustment()>0)
				{
					inventoryUpdatevalue = inventoryUpdatevalue-groupEventDto.getInventoryAdjustment();					
				}*/
				if(groupEvent.getCurrentInventory() != null)
				{
					groupEvent.setCurrentInventory(groupEvent.getCurrentInventory() + inventoryUpdatevalue);
				}
				else
				{
					groupEvent.setCurrentInventory(inventoryUpdatevalue);
				}
				groupEventService.updateGroupEventCurrentInventory(groupEvent);
			}
			
		}
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		return returnValue;
		
	}

	@Override
	public void deleteGroupEventDetailsByGroupId(Integer id) throws SQLException {
		groupEventDetailsDao.deleteGroupEventDetailsByGroupId(id);
		
	}

}
