package com.pigtrax.pigevents.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.Pen;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.master.service.interfaces.PenService;
import com.pigtrax.pigevents.beans.RemovalEvent;
import com.pigtrax.pigevents.dto.BarnDto;
import com.pigtrax.pigevents.dto.PigInfoDto;
import com.pigtrax.pigevents.service.interfaces.PigInfoService;
import com.pigtrax.pigevents.service.interfaces.RemovalEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/entryEvent")
public class EntryEventRestController {
	
	private static final Logger logger = Logger.getLogger(EntryEventRestController.class);
	
	@Autowired
	BarnService barnService;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	PenService penService;
	
	@Autowired
	PigInfoService pigInfoService;
	
	@Autowired
	RemovalEventService removalEventService;
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getBarns", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getBarns(HttpServletRequest request, @RequestBody Integer companyId)
	{
		logger.info("Inside getBarns method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		if(companyId == null)
		{
			if(activeUser != null)
				companyId = activeUser.getCompanyId();
		}
		ServiceResponseDto dto = new ServiceResponseDto();
		Map<String, Object> entryEventMap = new HashMap<String, Object>();
		try {
			List<BarnDto> barnList = barnService.getBarns(companyId);
			entryEventMap.put("barnList", barnList);
			
			Map<Integer, String> sexTypeMap = refDataCache.getSexTypeMap(language);
			logger.info("Entry event map - sex type : "+ sexTypeMap);
			entryEventMap.put("sexTypeMap", sexTypeMap);
			
			logger.info("Barn List = "+barnList);
			dto.setStatusMessage("Success");
			dto.setPayload(entryEventMap);
		} catch (SQLException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
	
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPenList", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getPenInfo(HttpServletRequest request, @RequestBody Integer barnId)
	{
		logger.info("Inside getPenInfo method" );
		
		ServiceResponseDto dto = new ServiceResponseDto();
		Map<String, Object> entryEventMap = new HashMap<String, Object>();
		try {
			List<Pen> penDtoList = penService.getPenListByBarnId(barnId);
			dto.setPayload(penDtoList);
		} catch (SQLException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/saveEntryEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto saveEntryEventInformation(HttpServletRequest request, @RequestBody PigInfoDto pigInformation)
	{
		logger.info("Inside saveEntryEventInformation method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			logger.info("company id in rest controller = "+pigInformation.getCompanyId());
			pigInformation.setUserUpdated(activeUser.getUsername());
			int rowsInserted = pigInfoService.savePigInformation(pigInformation);
			dto.setStatusMessage("Success");
		} catch (PigTraxException e) {
			e.printStackTrace();
			logger.info("is duplicate : "+e.isDuplicateStatus());
			if(e.isDuplicateStatus())
			{
				dto.setDuplicateRecord(true);
			}
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
	
	
	/**
	 * Service to search the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPigInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getPigInformation(HttpServletRequest request, @RequestBody PigInfoDto pigInformation)
	{
		logger.info("Inside getPigInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			pigInformation = pigInfoService.getPigInformation(pigInformation);
			dto.setPayload(pigInformation);
			if(pigInformation != null && pigInformation.getId() != null)
				dto.setStatusMessage("Success");
			else
				dto.setStatusMessage("ERROR : Pig Information not found");
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} catch (Exception e) {
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
	
	
	/**
	 * Service to delete the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deletePigInfo", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto deletePigInfo(HttpServletRequest request, @RequestBody Integer id)
	{
		logger.info("Inside deletePigInfo method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			int rowsDeleted = pigInfoService.deletePigInfo(id);
			if(rowsDeleted == -1)
			{
				dto.setPayload(id);
				dto.setStatusMessage("Events");
			}
			else
			{
			 dto.setPayload(id);
			 dto.setStatusMessage("Success");
			}
		} catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
   
	/**
	 * Service to retrieve all foster pigs
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getAllFosterPigs", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getAllFosterPigs(HttpServletRequest request, @RequestBody PigInfoDto pigInformation)
	{
		logger.info("Inside getAllFosterPigs method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<PigInfoDto> fosterList = new ArrayList<PigInfoDto>();
		try {
			fosterList = pigInfoService.getAllFosterPigs(pigInformation);
			dto.setPayload(fosterList);
			if(fosterList != null)
				dto.setStatusMessage("Success");
			else
				dto.setStatusMessage("ERROR : No Foster Pigs found");
		}
		catch (Exception e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
	
	
	/**
	 * Service to search the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPigInformationForChangeId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getPigInformationForChangeId(HttpServletRequest request, @RequestBody PigInfoDto pigInformation)
	{
		logger.info("Inside getPigInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			String flag = "DisableChange";
			pigInformation = pigInfoService.getPigInformation(pigInformation);
			if(pigInformation != null){
				List<RemovalEvent> removalEvents = removalEventService.getRemovalEventByPigId(pigInformation.getId());
				if(removalEvents != null && 0 < removalEvents.size())
				{
					for(RemovalEvent event : removalEvents)
					{
						if(event.getRemovalTypeId() == 1 || event.getRemovalTypeId() == 2 || event.getRemovalTypeId() == 4)
						{
							flag = "EnableChange";
							break;
						}
					}
				}
				else
					flag = "DisableChange";
			}
			if("EnableChange".equals(flag))
				pigInformation.setEnableChangeId(true);
			else
				pigInformation.setEnableChangeId(false);
			
			dto.setPayload(pigInformation);
			if(pigInformation != null && pigInformation.getId() != null)
				dto.setStatusMessage("Success");
			else
				dto.setStatusMessage("ERROR : Pig Information not found");
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} catch (Exception e) {
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
	
}
