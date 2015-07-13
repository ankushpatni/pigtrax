package com.pigtrax.usermanagement.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	public ServiceResponseDto getValidationType()
	{
		logger.info("Inside getValidationType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		dto.setPayload( refDataCache.getVentilationTypeMap("en"));
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getPhaseType", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPhaseType()
	{
		logger.info("Inside getPhaseType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		/*List<Map<String,Object>> phaseType = new ArrayList<Map<String,Object>>();
		Map<Integer,String> phaseTypeMap = refDataCache.getPhaseTypeMap("en");
		Set<Integer> keySet = phaseTypeMap.keySet();
		Map<String,Object> inputMap;
		for(Integer id : keySet)
		{
			inputMap = new HashMap();
			inputMap.put("name", id);
			inputMap.put("value", phaseTypeMap.get(id));
			phaseType.add(inputMap);
		}
		dto.setPayload(phaseType);*/
		List<Map<Integer,String>> phaseType = new ArrayList<Map<Integer,String>>();
		phaseType.add(refDataCache.getPhaseTypeMap("en"));
		phaseType.add(refDataCache.getVentilationTypeMap("en"));
		dto.setPayload(phaseType);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	

}
