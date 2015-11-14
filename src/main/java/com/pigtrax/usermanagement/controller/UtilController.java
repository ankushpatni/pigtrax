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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.master.service.interfaces.MasterRationService;
import com.pigtrax.master.service.interfaces.PremisesService;
import com.pigtrax.master.service.interfaces.RoomService;
import com.pigtrax.master.service.interfaces.SiloService;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.pigevents.service.interfaces.PigInfoService;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/util")
public class UtilController {
	
	private static final Logger logger = Logger.getLogger(UtilController.class);
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	SiloService siloService;
	
	@Autowired
	GroupEventService groupeventService;
	
	@Autowired
	PigInfoService pigInfoService;
	
	@Autowired
	BarnService barnService;
	
	@Autowired
	PremisesService premisesService;
	
	@Autowired
	MasterRationService rationService;
	
	@RequestMapping(value = "/getCityCountryList", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getCityCountryList(HttpServletRequest request	)
	{
		logger.info("Inside getCompanyList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List cityCountryList = new ArrayList();
		List<Map<String, String>> countryList = refDataCache.getAllCountries();
		cityCountryList.add(countryList);
		cityCountryList.add(refDataCache.getCitiesForCountry(null));
		dto.setPayload(cityCountryList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getValidationType", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getValidationType(HttpServletRequest request)
	{
		logger.info("Inside getValidationType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		dto.setPayload( refDataCache.getVentilationTypeMap(language));
		dto.setStatusMessage("Success");
		return dto;
	}
	
	
	@RequestMapping(value = "/getBreedingServiceType", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getBreedingServiceType(HttpServletRequest request)
	{
		logger.info("Inside getBreedingServiceType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
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
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		phaseType.add(refDataCache.getPhaseTypeMap(language));
		phaseType.add(refDataCache.getVentilationTypeMap(language));
		dto.setPayload(phaseType);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getPregnancyEventTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPregnancyEventTypes(HttpServletRequest request)
	{
		logger.info("Inside getPregnancyEventTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		
		Map<Integer, String> refDataMap = refDataCache.getPregnancyEventTypeMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		
		dto.setPayload(responseList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getPregnancyExamResultTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPregnancyExamResultTypes(HttpServletRequest request)
	{
		logger.info("Inside getPregnancyExamResultTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		
		Map<Integer, String> refDataMap = refDataCache.getPregnancyExamResultTypeMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		
		dto.setPayload(responseList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getSiloType", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getSiloType(HttpServletRequest request)
	{
		logger.info("Inside getPhaseType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Map<Integer,String>> siloType = new ArrayList<Map<Integer,String>>();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		siloType.add(refDataCache.getSiloTypeMap(language));
		dto.setPayload(siloType);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getPhaseOfProductionType", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPhaseOfProductionType(HttpServletRequest request,  @RequestParam Integer companyId)
	{
		logger.info("Inside getPhaseType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Map<Integer,String>> phaseOfProductionType = new ArrayList<Map<Integer,String>>();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		phaseOfProductionType.add(refDataCache.getPhaseOfProductionTypeMap(language));
		phaseOfProductionType.add(roomService.getRoomListBasedOnCompanyId(companyId));
		phaseOfProductionType.add(barnService.getBarnListBasedOnCompanyId(companyId));
		
		dto.setPayload(phaseOfProductionType);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getFeedEventDetailMasterData", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getFeedEventDetailMasterData(HttpServletRequest request,  @RequestParam Integer companyId)
	{
		logger.info("Inside getPhaseType" );		
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Map> phaseOfProductionType = new ArrayList<Map>();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		phaseOfProductionType.add(refDataCache.getFeedEventTypeMap(language));
		phaseOfProductionType.add(siloService.getSiloListBasedOnCompanyId(companyId));
		phaseOfProductionType.add(groupeventService.getGroupEventByCompanyId(companyId));
		phaseOfProductionType.add(rationService.getRationListAsMap(language));
		dto.setPayload(phaseOfProductionType);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getRemovalEventDetailMasterData", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getRemovalEventDetailMasterData(HttpServletRequest request,  @RequestParam Integer companyId)
	{
		logger.info("Inside getPhaseType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Map> outDataList = new ArrayList<Map>();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		outDataList.add(refDataCache.getRemovalEventTypeMap(language));
		//outDataList.add(siloService.getSiloListBasedOnCompanyId(companyId));
		outDataList.add(groupeventService.getGroupEventByCompanyId(companyId));
		dto.setPayload(outDataList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getRemovalExceptSalesMasterData", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getRemovalExceptSalesMasterData(HttpServletRequest request,  @RequestParam Integer companyId)
	{
		logger.info("Inside getPhaseType" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Map> outDataList = new ArrayList<Map>();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		
		Map<Integer, String> refDataMap = refDataCache.getRemovalEventTypeMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		Map refDataKeyValueMap = new HashMap<String, Object>();
		refDataKeyValueMap.put("RemovalEventKey", keySet);
		refDataKeyValueMap.put("RemovalEventValue", refDataMap);
		outDataList.add(refDataKeyValueMap);
		
		//outDataList.add(refDataCache.getRemovalEventTypeMap(language));
		try {
			outDataList.add(pigInfoService.getPigInformationByCompany(companyId));
			outDataList.add(premisesService.getPremisesListBasedOnCompanyId(companyId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outDataList.add(groupeventService.getGroupEventByCompanyId(companyId));
		
		
		Map<Integer, String> mortalityRefDataMap = refDataCache.getMortalityReasonTypeMap(language);
		Set<Integer> mortalityKeySet = null;
		if(refDataMap != null)
			mortalityKeySet  = mortalityRefDataMap.keySet();
		Map mortalityRefDataKeyValueMap = new HashMap<String, Object>();
		mortalityRefDataKeyValueMap.put("MortalityReasonKey", mortalityKeySet);
		mortalityRefDataKeyValueMap.put("MortalityReasonValue", mortalityRefDataMap);
		outDataList.add(mortalityRefDataKeyValueMap);
		
		//outDataList.add(refDataCache.getMortalityReasonTypeMap(language));
		dto.setPayload(outDataList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getGfunctionTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getGfunctionTypes(HttpServletRequest request)
	{
		logger.info("Inside getGfunctionTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> refDataMap = refDataCache.getGfunctionTypeMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/getMortalityReasonTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getMortalityReasonTypes(HttpServletRequest request)
	{
		logger.info("Inside getMortalityReasonTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		
		Map<Integer, String> refDataMap = refDataCache.getMortalityReasonTypeMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto;
	}
	
	
	@RequestMapping(value = "/getTargetTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getTargetTypes(HttpServletRequest request)
	{
		logger.info("Inside getTargetTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> mapval = refDataCache.getTargetTypeMap(language);
		
		Set<Integer> keySet = mapval.keySet();
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(mapval);
		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto;
	}
	
	
	@RequestMapping(value = "/getRoleTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getRoleTypes(HttpServletRequest request)
	{
		logger.info("Inside getRoleTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> mapval = refDataCache.getRoleTypeMap(language);
		
		Set<Integer> keySet = mapval.keySet();
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(mapval);
		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto;
	}
	
	
	@RequestMapping(value = "/getGcompanyTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getGcompanyTypes(HttpServletRequest request)
	{
		logger.info("Inside getGcompanyTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> refDataMap = refDataCache.getGcompanyTypeMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto; 
	}
	
	@RequestMapping(value = "/getGlineTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getGlineTypes(HttpServletRequest request)
	{
		logger.info("Inside getGlineTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> refDataMap = refDataCache.getGlineTypeMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto; 
	}
	
	@RequestMapping(value = "/getLogEventTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getLogEventTypes(HttpServletRequest request)
	{
		logger.info("Inside getLogEventTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> refDataMap = refDataCache.getLogEventTypeMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto; 
	}
	
	
	@RequestMapping(value = "/getFeedEventTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getFeedEventTypes(HttpServletRequest request)
	{
		logger.info("Inside getFeedTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> refDataMap = refDataCache.getFeedEventTypeMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto;
	}
	
	
	@RequestMapping(value = "/getPigletConditions", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPigletConditions(HttpServletRequest request)
	{
		logger.info("Inside getPigletConditions" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> refDataMap = refDataCache.getPigletConditionMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto; 
	}
	
	
	@RequestMapping(value = "/getSaleTypes", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getSaleTypes(HttpServletRequest request)
	{
		logger.info("Inside getSaleTypes" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> refDataMap = refDataCache.getSaleTypesMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto; 
	}
	
	@RequestMapping(value = "/getSaleReasons", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getSaleReasons(HttpServletRequest request)
	{
		logger.info("Inside getSaleReasons" );
		ServiceResponseDto dto = new ServiceResponseDto();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		Map<Integer, String> refDataMap = refDataCache.getSaleReasonsMap(language);
		Set<Integer> keySet = null;
		if(refDataMap != null)
			keySet  = refDataMap.keySet();
		
		List<Object> responseList = new ArrayList<Object>();
		responseList.add(keySet);
		responseList.add(refDataMap);		
		dto.setPayload(responseList); 
		dto.setStatusMessage("Success");
		return dto; 
	}
}