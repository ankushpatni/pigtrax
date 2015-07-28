package com.pigtrax.usermanagement.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/util")
public class UtilController {
	
	private static final Logger logger = Logger.getLogger(UtilController.class);
	
	@Autowired
	RefDataCache refDataCache;
	
	@RequestMapping(value = "/getCityCountryList", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getCityCountryList(HttpServletRequest request	)
	{
		logger.info("Inside getCompanyList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		Map<String, Set<String>> cityCountryMap = null;
		dto.setPayload(cityCountryMap);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getValidationType", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getValidationType(HttpServletRequest request)
	{
		logger.info("Inside getValidationType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		Locale locale = request.getLocale();
		String language = locale.getLanguage();
		dto.setPayload( refDataCache.getVentilationTypeMap(language));
		dto.setStatusMessage("Success");
		return dto;
	}
	
	
	@RequestMapping(value = "/getBreedingServiceType", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getBreedingServiceType(HttpServletRequest request)
	{
		logger.info("Inside getBreedingServiceType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		Locale locale = request.getLocale();
		String language = locale.getLanguage();
		dto.setPayload( refDataCache.getBreedingServiceTypeMap(language));
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getPhaseType", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPhaseType(HttpServletRequest request)
	{
		logger.info("Inside getPhaseType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Map<Integer,String>> phaseType = new ArrayList<Map<Integer,String>>();
		Locale locale = request.getLocale();
		String language = locale.getLanguage();
		phaseType.add(refDataCache.getPhaseTypeMap(language));
		phaseType.add(refDataCache.getVentilationTypeMap(language));
		dto.setPayload(phaseType);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	

}
