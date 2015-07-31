package com.pigtrax.usermanagement.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@RestController
@RequestMapping("rest/company")
public class CompanyRestController {

	private static final Logger logger = Logger.getLogger(CompanyRestController.class);

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
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/updateCompanyStatus", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto updateCompanyStatus( @RequestParam String companyId, @RequestParam String isActive)
	{
		logger.info("Inside updateCompanyStatus()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord;
		try 
		{
			updatedRecord = companyService.updateCompanyStatus(companyId.toUpperCase(), new Boolean(isActive));
			dto.setStatusMessage("SUCCESS");
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside updateCompanyStatus()" +e.getErrorCode() + e.getMessage());
			e.printStackTrace();
		}
		dto.setPayload(updatedRecord);		
		return dto;
	}
	
	
	/**
	 * Service to insert  the company record
	 * @return Company
	 */
	@RequestMapping(value = "/insertCompanyRecord", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertCompanyRecord( @RequestBody Company company)
	{
		logger.debug("Inside insertCompanyRecord()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		logger.info("String ---->"+company.toString());
		int updatedRecord = 0;
		try 
		{
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			if( 0 == company.getId() )
			{
				updatedRecord = companyService.insertCompanyRecord(company);
			}
			else
			{
				updatedRecord = companyService.updateCompanyRecord(company);
			}
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(updatedRecord);
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside updateCompanyStatus()" +e.getErrorCode() + e.getMessage());
			e.printStackTrace();
		}
		
		catch (Exception e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setPayload("Company with ID : "+company.getCompanyId().toUpperCase() + " already present.");
			}
			
			logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}


}
