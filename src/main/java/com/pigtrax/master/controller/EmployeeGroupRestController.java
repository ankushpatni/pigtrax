package com.pigtrax.master.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.EmployeeDto;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;


@RestController
@RequestMapping("rest/employeeGroup")
public class EmployeeGroupRestController {
	private static final Logger logger = Logger.getLogger(EmployeeGroupRestController.class);
	
	
	@Autowired
	EmployeeGroupService employeeGroupService;
	
	/**
	 * Service to retrive the list of employee groups
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getEmployeeGroups", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto getEmployeeGroups(@RequestBody int companyId)
	{
		logger.info("Inside getEmployeeGroups" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<EmployeeGroupDto> employeeGroupDtoList;
		try {
			employeeGroupDtoList = employeeGroupService.getEmployeeGroups(companyId);
			dto.setPayload(employeeGroupDtoList);
			dto.setStatusMessage("Success");
		} catch (PigTraxException e) {
			dto.setStatusMessage("Error : Exception occurred ");
		}
		
		return dto;
	}
	
	
	/**
	 * Service to retrive the list of employees in a company
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getEmployeeList", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto getEmployeeList(@RequestBody EmployeeGroupDto employeeGroupDto)
	{
		logger.info("Inside getEmployeeGroups" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<EmployeeDto> employeeDtoList = null;
		try {
			employeeDtoList = employeeGroupService.getEmployeeList(employeeGroupDto.getCompanyId(), employeeGroupDto.getEmployeeJobFunction());
			dto.setPayload(employeeDtoList);
			dto.setStatusMessage("Success");
		} catch (PigTraxException e) {
			dto.setStatusMessage("Error : Exception occurred ");
		}
		
		return dto;
	}
	
	
	/**
	 * Service to save the employee group information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/saveEmployeeGroup", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto saveEmployeeGroup(HttpServletRequest request, @RequestBody EmployeeGroupDto employeeGroupDto)
	{
		logger.info("Inside saveEmployeeGroup" );
		ServiceResponseDto dto = new ServiceResponseDto();
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			employeeGroupDto.setUserUpdated(activeUser.getUsername());
			employeeGroupService.saveEmployeeGroup(employeeGroupDto);
			dto.setStatusMessage("Success");
		} catch (PigTraxException e) {
			dto.setStatusMessage("Error : Exception occurred ");
		}

		return dto;
	}
}
