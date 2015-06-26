package com.pigtrax.usermanagement.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@RestController
@RequestMapping("rest/company")
public class CompanyRestController {

	private static final Logger logger = Logger
			.getLogger(CompanyRestController.class);

	@Autowired
	CompanyService companyService;
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getCompanyList", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getCompanyList()
	{
		logger.info("Inside getCompanyList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Company> companyList  = companyService.getCompanyList();
		dto.setPayload(companyList);
		dto.setStatusMessage("Success");
		return dto;
	}

}
