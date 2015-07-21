package com.pigtrax.master.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.master.dto.Premises;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;
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
}
