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

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dto.OriginDto;
import com.pigtrax.master.service.interfaces.OriginService;
import com.pigtrax.pigevents.dto.CompanyTargetDto;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/origin")
public class OriginRestController {
	
	private static final Logger logger = Logger.getLogger(OriginRestController.class);
	
	@Autowired
	OriginService originService;
	
	/**
	 * Service to retrive the list of origin values
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getOriginList", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getOriginList(HttpServletRequest request)
	{
		logger.info("Inside getOriginList method" );
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		List<OriginDto> originList = originService.getOriginList(activeUser.getUsername());
		dto.setPayload(originList);
		dto.setStatusMessage("success");
		return dto;
	}
	
	/**
	 * Service to save the origin value
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/saveOrigin", method=RequestMethod.POST, produces="application/json", consumes="application/json") 
	@ResponseBody
	public ServiceResponseDto saveOrigin(HttpServletRequest request, @RequestBody OriginDto originDto)
	{
		logger.info("Inside saveOrigin method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		ServiceResponseDto dto = new ServiceResponseDto();
		try{
			originDto.setUserUpdated(activeUser.getUsername());
			originService.saveOrigin(originDto);
			dto.setPayload(originDto);
			dto.setStatusMessage("success");
			
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
	
	/**
	 * Service to delete origin values
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deleteOrigin", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto deleteOrigin(HttpServletRequest request, @RequestBody Integer originId)
	{
		logger.info("Inside deleteOrigin method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		originService.deleteOrigin(originId);
		dto.setStatusMessage("success");
		return dto;
	}
	
	
}
