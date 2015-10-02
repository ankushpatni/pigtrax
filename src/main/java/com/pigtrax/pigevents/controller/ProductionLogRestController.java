package com.pigtrax.pigevents.controller;

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
import com.pigtrax.pigevents.dto.CompanyTargetDto;
import com.pigtrax.pigevents.dto.ProductionLogDto;
import com.pigtrax.pigevents.service.interfaces.ProductionLogService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/productionLog")
public class ProductionLogRestController {
	
	private static final Logger logger = Logger.getLogger(ProductionLogRestController.class);
	
	@Autowired
	ProductionLogService productionLogService;
	
	/**
	 * Service to save the company target
	 * @return ServiceResponseDto
	 */
	@RequestMapping(method=RequestMethod.POST, produces="application/json", consumes="application/json") 
	@ResponseBody
	public ServiceResponseDto saveCompanyTarget(HttpServletRequest request, @RequestBody ProductionLogDto productionLogDto)
	{
		logger.info("Inside saveCompanyTarget method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		ServiceResponseDto dto = new ServiceResponseDto();
		try{
			productionLogDto.setUserUpdated(activeUser.getUsername());
			int key = productionLogService.storeProductionLog(productionLogDto);
			productionLogDto.setId(key);
			dto.setPayload(productionLogDto);
			dto.setStatusMessage("success");
			
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
	
	/**
	 * Service to save the company target
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value="/getProductionLogList", method=RequestMethod.POST, produces="application/json", consumes="application/json") 
	@ResponseBody
	public ServiceResponseDto getProductionLogList(HttpServletRequest request, @RequestBody ProductionLogDto productionLogDto)
	{
		logger.info("Inside getProductionLogList method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try{
			productionLogDto.setUserUpdated(activeUser.getUsername());
			List<ProductionLogDto> productionLogDtoList  = productionLogService.getProductLogList(productionLogDto);			
			dto.setPayload(productionLogDtoList);
			dto.setStatusMessage("success");
			
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
}
