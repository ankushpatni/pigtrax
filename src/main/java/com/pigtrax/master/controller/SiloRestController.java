package com.pigtrax.master.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.master.dto.Silo;
import com.pigtrax.master.service.interfaces.SiloService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/silo")
public class SiloRestController {
	
	private static final Logger logger = Logger.getLogger(SiloRestController.class);

	@Autowired
	SiloService siloService;
	
	/**
	 * Service to retrive the list of Silo
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getSiloList", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getSiloList(@RequestParam int generatedBarnId)
	{
		logger.info("Inside getSiloList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Silo> siloList  = siloService.getSiloList(generatedBarnId);
		dto.setPayload(siloList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	/**
	 * Service to retrive the list of Silo
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getSiloListForPremise", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto getSiloListForPremise(@RequestBody Integer premiseId)
	{
		logger.info("Inside getSiloListForPremise" );
		ServiceResponseDto dto = new ServiceResponseDto();
		Map<Integer, String> siloMap  = siloService.getSiloListBasedOnPremiseId(premiseId);
		dto.setPayload(siloMap);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	
	/**String siloId, Boolean siloStatus
	 * Service to update silo
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/updateSiloStatus", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto updateSiloStatus( @RequestParam String siloId, @RequestParam String isActive)
	{
		logger.info("Inside updateBarnStatus()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord;
		try 
		{
			updatedRecord = siloService.updateSiloStatus(siloId.toUpperCase(), new Boolean(isActive));
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
	@RequestMapping(value = "/insertSiloRecord", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertSiloRecord( @RequestBody Silo silo)
	{
		logger.debug("Inside insertSiloRecord()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord = 0;
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try 
		{
			silo.setUserUpdated(activeUser.getUsername());
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			if( 0 == silo.getId() )
			{
				updatedRecord = siloService.insertSiloRecord(silo);
			}
			else
			{
				updatedRecord = siloService.updateSiloRecord(silo);
			}
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(updatedRecord);
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside insertSiloRecord/updateSiloRecord" +((SQLException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		catch (Exception e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setPayload("Silo with ID : "+silo.getSiloId().toUpperCase() + " already present.");
				logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			}
			
		//	logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}



}
