package com.pigtrax.usermanagement.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pigtrax.usermanagement.beans.Employee;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.EmployeeDto;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;
import com.pigtrax.usermanagement.service.interfaces.EmployeeService;

@RestController
@RequestMapping("rest/employee")
public class EmployeeRestController {
	
	private static final Logger logger = Logger.getLogger(EmployeeRestController.class);
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	
	
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
	public ServiceResponseDto insertEmployeeRecordSubmit(HttpServletRequest request,  @RequestBody Employee employee)
	{
		logger.debug("Inside insertEmployeeRecord()  ");
		ServiceResponseDto dto = new ServiceResponseDto();
		logger.info("String ---->"+employee.toString());
		int updatedRecord = 0;
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		Locale locale = localeResolver.resolveLocale(request);
		try 
		{
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			employee.setUserUpdated(activeUser.getUsername());
			updatedRecord = employeeService.insertEmployeeRecord(employee, locale);
			 
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
	
	@RequestMapping(value="/forgotPassword", method=RequestMethod.POST, produces="application/json")	
	public ServiceResponseDto forgotPassword(HttpServletRequest request,@RequestBody String employeeId){
	 ServiceResponseDto dto = new ServiceResponseDto();
	 LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
	 Locale locale = localeResolver.resolveLocale(request);
	 String status = employeeService.forgetPassword(employeeId, locale);
	 dto.setStatusMessage("success");
	 return dto;
	}
	
	
	@RequestMapping(value = "/validateOneTimePassword", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public ServiceResponseDto validateOneTimePassword(HttpServletRequest request, HttpServletResponse response, 
    		@RequestBody EmployeeDto empDto) {
    	
		logger.info("Inside validateOneTimePassword method" );
    	ServiceResponseDto rc = new ServiceResponseDto();    	
    	
    	
    	Employee employee = employeeService.getEmployeeByEmployeeId(empDto.getEmployeeId());
    	
    	String encodedPassword = employee.getPtPassword(); 
    	
    	if(BCrypt.checkpw(empDto.getOtPassword(), encodedPassword))
    		rc.setStatusMessage("success");
    	else
    		rc.setStatusMessage("ERROR");
    	return rc;
    }
	
	
	@RequestMapping(value="/changePassword",  method = RequestMethod.POST,produces = "application/json", consumes="application/json")
	public ServiceResponseDto changePassword(@RequestBody Employee employee){
		String status = null;
		ServiceResponseDto dto = new ServiceResponseDto();
		status = 	employeeService.changePassword(employee.getEmployeeId(), employee.getPtPassword()); 
		dto.setStatusMessage("success");
		return dto;		
	}
	
	
	@RequestMapping(value = "/getEmployeeRoles", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto getEmployeeRoles(HttpServletRequest request, @RequestBody Integer employeeId)
	{
		logger.debug("Inside getEmployeeRoles()  ");
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		List<Employee> empList = employeeService.getEmployeeRoles(employeeId, language);
		dto.setStatusMessage("success");
		dto.setPayload(empList);
		
		return dto;
	}
	
	
}
