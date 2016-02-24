package com.pigtrax.master.controller;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.TransportTrailer;
import com.pigtrax.master.dto.TransportTruck;
import com.pigtrax.master.service.interfaces.TransportTrailerService;
import com.pigtrax.master.service.interfaces.TransportTruckService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;


@RestController
@RequestMapping("rest/transportTrailerTruck")
public class TransportTrailerTruckRestController {
	
	private static final Logger logger = Logger.getLogger(TransportTrailerTruckRestController.class);

	@Autowired
	TransportTruckService transportTruckServiceImpl;
	
	@Autowired
	TransportTrailerService transportTrailerServiceImpl;
	
	@Autowired
	RefDataCache refDataCache;
	
	/**
	 * Service to retrive the list of Silo
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getTransportTrailerTruck", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getTransportTrailerTruck(@RequestParam int generatedCompanyId, HttpServletRequest request)
	{
		logger.info("Inside getPhaseType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List phaseType = new ArrayList();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		phaseType.add(transportTruckServiceImpl.getTransportTruckList(generatedCompanyId));
		phaseType.add(transportTrailerServiceImpl.getTransportTrailerList(generatedCompanyId, language));
		
		Map<Integer, String> trailerTypeMap =  refDataCache.transportTrailerType(language);		
		phaseType.add(trailerTypeMap);
		Set<Integer> trailerTypeKeySet = null;
		if(trailerTypeMap != null)
			trailerTypeKeySet  = trailerTypeMap.keySet();
		phaseType.add(trailerTypeKeySet);
		
		dto.setPayload(phaseType);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	/**
	 * Service to insert  the premises record
	 * @return Company
	 */
	@RequestMapping(value = "/insertTransportTruckRecord", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertTransportTruckRecord( @RequestBody TransportTruck transportTruck)
	{
		logger.debug("Inside insertTransportTruckRecord()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord = 0;
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try 
		{
			transportTruck.setUserUpdated(activeUser.getUsername());
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			if( 0 == transportTruck.getId() )
			{
				updatedRecord = transportTruckServiceImpl.insertTransportTruck(transportTruck);
			}
			else
			{
				updatedRecord = transportTruckServiceImpl.updateTransportTruck(transportTruck);
			}
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(updatedRecord);
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside insertTransportTruckRecord/updateTransportTruckRecord" +((SQLException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		catch (Exception e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setDuplicateRecord(true);
				dto.setPayload("TransportTruck with ID : "+transportTruck.getTransportTruckId().toUpperCase() + " already present.");
				logger.error("Inside insertTransportTruckRecord()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			}
			logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}

	/**
	 * Service to insert  the premises record
	 * @return Company
	 */
	@RequestMapping(value = "/insertTransportTrailorRecord", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertTransportTrailorRecord( @RequestBody TransportTrailer transportTrailer)
	{
		logger.debug("Inside insertTransportTruckRecord()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord = 0;
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try 
		{
			transportTrailer.setUserUpdated(activeUser.getUsername());
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			if( 0 == transportTrailer.getId() )
			{
				updatedRecord = transportTrailerServiceImpl.insertTransportTrailer(transportTrailer);
			}
			else
			{
				updatedRecord = transportTrailerServiceImpl.deleteTransportTrailer(transportTrailer);
			}
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(updatedRecord);
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside insertTransportTruckRecord/updateTransportTruckRecord" +((SQLException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		catch (Exception e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setDuplicateRecord(true);
				dto.setPayload("TransportTruck with ID : "+transportTrailer.getTransportTrailerId().toUpperCase() + " already present.");
				logger.error("Inside insertTransportTruckRecord()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			}
			logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}


}