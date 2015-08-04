package com.pigtrax.master.controller;

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

import com.pigtrax.master.dto.Silo;
import com.pigtrax.master.service.interfaces.SiloService;
import com.pigtrax.master.service.interfaces.TransportTrailerService;
import com.pigtrax.master.service.interfaces.TransportTruckService;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;


@RestController
@RequestMapping("rest/transportTrailerTruck")
public class TransportTrailerTruckRestController {
	
	private static final Logger logger = Logger.getLogger(TransportTrailerTruckRestController.class);

	@Autowired
	TransportTruckService transportTruckServiceImpl;
	
	@Autowired
	TransportTrailerService transportTrailerServiceImpl;
	
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
		phaseType.add(transportTrailerServiceImpl.getTransportTrailerList(generatedCompanyId));
		dto.setPayload(phaseType);
		dto.setStatusMessage("Success");
		return dto;
	}

}
