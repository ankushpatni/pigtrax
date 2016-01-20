package com.pigtrax.master.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pigtrax.master.dto.Premises;
import com.pigtrax.master.service.interfaces.PremisesService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

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
	public ServiceResponseDto getPremisesList(HttpServletRequest request, @RequestParam int generatedCompanyId, @RequestParam(required=false) String premisesType)
	{
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		logger.info("Inside getPremisesList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Premises> premisesList  = premisesService.getPremisesList(generatedCompanyId, language, premisesType);
		dto.setPayload(premisesList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPremisesListNotIn", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPremisesListNotIn(HttpServletRequest request, @RequestParam int generatedCompanyId, @RequestParam String premisesType)
	{
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		logger.info("Inside getPremisesList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Premises> premisesList  = premisesService.getPremisesListPremisisTypeNotInFilter(generatedCompanyId, language, premisesType);
		dto.setPayload(premisesList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPremisesListBySowSource", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPremisesListBySowSource(HttpServletRequest request, @RequestParam int generatedCompanyId)
	{
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		logger.info("Inside getPremisesList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Premises> premisesList  = premisesService.getPremisesListBySowSource(generatedCompanyId); 
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
		int updatedRecord = 0;
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try 
		{
			premises.setUserUpdated(activeUser.getUsername());
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
				dto.setPayload("Permise with ID : "+premises.getPermiseId().toUpperCase() + " already present.");
				logger.error("Inside insertPremisesRecord()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			}
			
		//	logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}
	
	
	/**String premisesID, Boolean premisesStatus
	 * Service to update employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deletePremise", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto deletePremiseData( @RequestBody Integer premiseId)
	{
		logger.info("Inside deletePremiseData()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int deletedRecords;
		try 
		{
			deletedRecords = premisesService.deletePremise(premiseId);
			if(deletedRecords > 0)
			{
				dto.setPayload(deletedRecords);
				dto.setStatusMessage("SUCCESS");
			}
			else
			dto.setStatusMessage("ERROR");
		} 
		catch (Exception e) {
			deletedRecords = 0;
			dto.setStatusMessage("ERROR");
		}
		dto.setPayload(deletedRecords);		
		return dto;
	}


}
