package com.pigtrax.master.controller;

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

import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.dto.Premises;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/barn")
public class BarnRestController {
	
	private static final Logger logger = Logger.getLogger(BarnRestController.class);

	@Autowired
	BarnService barnService;
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getBarnList", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getBarnList(@RequestParam int generatedPremisesId)
	{
		logger.info("Inside getBarnList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Barn> barnList  = barnService.getBarnList(generatedPremisesId);
		dto.setPayload(barnList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	/**String barnID, Boolean barnStatus
	 * Service to update employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/updateBarnStatus", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto updateBarnStatus( @RequestParam String barnID, @RequestParam String isActive)
	{
		logger.info("Inside updateBarnStatus()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord;
		try 
		{
			updatedRecord = barnService.updateBarnStatus(barnID.toUpperCase(), new Boolean(isActive));
			dto.setStatusMessage("SUCCESS");
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside updateBarnStatus()" +e.getErrorCode() + e.getMessage());
			e.printStackTrace();
		}
		dto.setPayload(updatedRecord);		
		return dto;
	}

	/**
	 * Service to insert  the premises record
	 * @return Company
	 */
	@RequestMapping(value = "/insertBarnRecord", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertBarnRecord( @RequestBody Barn barn)
	{
		logger.debug("Inside insertBarnRecord()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord = 0;
		try 
		{
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			if( 0 == barn.getId() )
			{
				updatedRecord = barnService.insertBarnRecord(barn);
			}
			else
			{
				updatedRecord = barnService.updateBarnRecord(barn);
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
				dto.setPayload("Barn with ID : "+barn.getBarnId().toUpperCase() + " already present.");
				logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			}
			
		//	logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}


}
