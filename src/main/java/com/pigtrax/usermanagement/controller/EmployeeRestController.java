package com.pigtrax.usermanagement.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.usermanagement.dao.interfaces.EmployeeDao;
import com.pigtrax.usermanagement.dto.EmployeeDto;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/employee")
public class EmployeeRestController {
	
	private static final Logger logger = Logger.getLogger(EmployeeRestController.class);
	
	@Autowired
	EmployeeDao employeeDao;
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getEmployeeList", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getEmployeeList()
	{
		logger.info("Inside getEmployeeList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<EmployeeDto> employeeList  = employeeDao.getEmployeeList();
		dto.setPayload(employeeList);
		dto.setStatusMessage("Success");
		return dto;
	}
    
}
