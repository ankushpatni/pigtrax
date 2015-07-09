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

import com.pigtrax.master.location.Premises;
import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;
import com.pigtrax.usermanagement.service.interfaces.PremisesService;

@RestController
@RequestMapping("rest/premises")
public class PremisesRestController {
	
	
	private static final Logger logger = Logger.getLogger(PremisesRestController.class);

	@Autowired
	PremisesService premisesService;
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPremisesList", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPremisesList()
	{
		logger.info("Inside getPremisesList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Premises> premisesList  = premisesService.getPremisesList();
		dto.setPayload(premisesList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	/**String premisesID, Boolean premisesStatus
	 * Service to update employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/updatePremisesStatus", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto updatePremisesStatus( @RequestParam String premisesID, @RequestParam String isActive)
	{
		logger.info("Inside updatePremisesStatus()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord;
		try 
		{
			updatedRecord = premisesService.updatePremisesStatus(premisesID.toUpperCase(), new Boolean(isActive));
			dto.setStatusMessage("SUCCESS");
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside updatePremisesStatus()" +e.getErrorCode() + e.getMessage());
			e.printStackTrace();
		}
		dto.setPayload(updatedRecord);		
		return dto;
	}
	
	/**
	 * Service to insert  the premises record
	 * @return Company
	 */
	@RequestMapping(value = "/insertPremisesRecord", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertPremisesRecord( @RequestBody Premises premises)
	{
		logger.debug("Inside insertPremisesRecord()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		logger.info("String ---->"+premises.toString());
		int updatedRecord = 0;
		try 
		{
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			if( 0 == premises.getId() )
			{
				updatedRecord = premisesService.insertPremisesRecord(premises);
			}
			else
			{
				updatedRecord = premisesService.updatePremisesRecord(premises);
			}
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(updatedRecord);
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside updateCompanyStatus()" +((SQLException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		catch (Exception e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setPayload("Company with ID : "+premises.getPermiseId().toUpperCase() + " already present.");
				logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			}
			
		//	logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}


}
