package com.pigtrax.usermanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		dto.setPayload(refDataCache.getPregnancyEventTypeMap(language));
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
		dto.setPayload(refDataCache.getPregnancyExamResultTypeMap(language));
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
		outDataList.add(refDataCache.getRemovalEventTypeMap(language));
		try {
			outDataList.add(pigInfoService.getPigInformationByCompany(companyId));
			outDataList.add(barnService.getBarnListBasedOnCompanyId(companyId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outDataList.add(groupeventService.getGroupEventByCompanyId(companyId));
		
		dto.setPayload(outDataList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	
	

}
