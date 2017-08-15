package com.pigtrax.master.controller;

import java.util.List;

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

import com.pigtrax.master.dto.MasterRationDto;
import com.pigtrax.master.service.interfaces.MasterRationService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/ration")
public class RationRestController {
	
	private static final Logger logger = Logger.getLogger(RationRestController.class);
	
	@Autowired
	MasterRationService rationService;
	
	/**
	 * Service to retrive the list of origin values
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getRationList", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getRationList(HttpServletRequest request)
	{
		logger.info("Inside getRationList method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		ServiceResponseDto dto = new ServiceResponseDto();
		List<MasterRationDto> rationDtoList = rationService.getRationList(language,activeUser.getUsername());
		dto.setPayload(rationDtoList);
		dto.setStatusMessage("success");
		return dto;
	}
	
	/**
	 * Service to save the origin value
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/saveRation", method=RequestMethod.POST, produces="application/json", consumes="application/json") 
	@ResponseBody
	public ServiceResponseDto saveRation(HttpServletRequest request, @RequestBody MasterRationDto rationDto)
	{
		logger.info("Inside saveRation method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		ServiceResponseDto dto = new ServiceResponseDto();
		rationDto.setUserUpdated(activeUser.getUsername());
			rationService.saveRation(rationDto);
			dto.setPayload(rationDto);
			dto.setStatusMessage("success");
		return dto;
	}
	
	
	/**
	 * Service to delete origin values
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deleteRation", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto deleteRation(HttpServletRequest request, @RequestBody Integer rationId)
	{
		logger.info("Inside deleteRation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		rationService.deleteRation(rationId);
		dto.setStatusMessage("success");
		return dto;
	}
	
	
}
