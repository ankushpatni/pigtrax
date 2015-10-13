package com.pigtrax.usermanagement.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.beans.Employee;
import com.pigtrax.usermanagement.dao.interfaces.EmployeeDao;
import com.pigtrax.usermanagement.dto.EmployeeDto;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;
import com.pigtrax.usermanagement.service.interfaces.EmployeeService;

@RestController
@RequestMapping("rest/employee")
public class EmployeeRestController {
	
	private static final Logger logger = Logger.getLogger(EmployeeRestController.class);
	
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getEmployeeListIndi", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getEmployeeListIndi()
	{
		logger.info("Inside getEmployeeList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<EmployeeDto> employeeList  = employeeService.getEmployeeList();
		dto.setPayload(employeeList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value="/activateEmployeeStatus", method=RequestMethod.POST , produces="application/json")
	public ServiceResponseDto activateEmployeeSubmit(@RequestParam int employeeId ){
		logger.info("Inside activateEmployeeStatus()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord;
		try {
		
		updatedRecord = employeeService.activateEmployeeStatus(employeeId);
		dto.setStatusMessage("SUCCESS");
		dto.setPayload(updatedRecord);		
		return dto;
		}catch(Exception e){
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside activateEmployeeStatus()" +((SQLException) e).getErrorCode() + e.getMessage());
			e.printStackTrace();
		}
		return dto;
	}
	@RequestMapping(value="/deActivateEmployeeStatus", method=RequestMethod.POST , produces="application/json")
	public ServiceResponseDto deActivateEmployeeSubmit(@RequestParam int employeeId ){
		logger.info("Inside DeActivateEmployeeStatus()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord;
		try {
		
		updatedRecord = employeeService.deActivateEmployeeStatus(employeeId);
		dto.setStatusMessage("SUCCESS");
		dto.setPayload(updatedRecord);		
		return dto;
		}catch(Exception e){
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside deActivateEmployeeStatus()" +((SQLException) e).getErrorCode() + e.getMessage());
			e.printStackTrace();
		}
		return dto;
	}
	@RequestMapping(value = "/insertEmployeeRecordSubmit", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertEmployeeRecordSubmit( @RequestBody Employee employee)
	{
		logger.debug("Inside insertEmployeeRecord()  ");
		ServiceResponseDto dto = new ServiceResponseDto();
		logger.info("String ---->"+employee.toString());
		int updatedRecord = 0;
		try 
		{
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			
				updatedRecord = employeeService.insertEmployeeRecord(employee);
			
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(updatedRecord);
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside insertCompanyStatus()" +e.getErrorCode() + e.getMessage());
			e.printStackTrace();
		}
		
		catch (Exception e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setPayload("Employee with ID : "+employee.getEmployeeId().toUpperCase() + " already present.");
				dto.setDuplicateRecord(true);
			}
			
			logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
			
		
		
		return dto;
	}
	
	
	@RequestMapping(value = "/getEmployeeDetails", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto getEmployeeDetails( @RequestBody Integer employeeId)
	{
		logger.debug("Inside getEmployeeDetails()  ");
		ServiceResponseDto dto = new ServiceResponseDto();
		Employee emp = employeeService.getEmployeeById(employeeId);
		if(emp != null)
		{
			dto.setStatusMessage("success");
			dto.setPayload(emp);
		}
		else
		{
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
	
	
	@RequestMapping(value = "/editEmployeeRecord", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto editEmployeeRecord( @RequestBody Employee employee)
	{
		logger.debug("Inside edittEmployeeRecord()  ");
		ServiceResponseDto dto = new ServiceResponseDto();
		logger.info("String ---->"+employee.toString());
		int editRecord = 0;
		try 
		{
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			
				editRecord = employeeService.editEmployeeRecord(employee);
			
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(editRecord);
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
			editRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setPayload("Company with ID : "+employee.getEmployeeId().toUpperCase() + " already present.");
			}
			
			logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
	return dto;
}
}
