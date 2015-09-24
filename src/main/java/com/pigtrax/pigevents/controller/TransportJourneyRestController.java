package com.pigtrax.pigevents.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.master.dto.TransportDestination;
import com.pigtrax.master.dto.TransportTrailer;
import com.pigtrax.master.dto.TransportTruck;
import com.pigtrax.master.service.interfaces.TransportDestinationService;
import com.pigtrax.master.service.interfaces.TransportTrailerService;
import com.pigtrax.master.service.interfaces.TransportTruckService;
import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.service.interfaces.TransportJourneyService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/transportJourney")
public class TransportJourneyRestController {
	
	private static final Logger logger = Logger.getLogger(TransportJourneyRestController.class);
	
	@Autowired
	TransportDestinationService transportDestinationServiceImpl;
	
	@Autowired
	TransportTruckService transportTruckServiceImpl;
	
	@Autowired
	TransportTrailerService transportTrailerServiceImpl;
	
	@Autowired
	TransportJourneyService transportJourneyServiceImpl;
		
	/**
	 * Service to save the TransportJourneyMaterData information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getTransportJourneyMasterData", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getTransportJourneyMasterData(@RequestParam int generatedCompanyId)
	{
		logger.info("Inside getTransportJourneyMasterData method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			 if( generatedCompanyId == 0) 
			   {
				   generatedCompanyId = activeUser.getCompanyId();
			   }
			List<Map<Integer, String>> phaseType = new ArrayList<Map<Integer, String>>();
			phaseType.add(getTransportDestinationMap(transportDestinationServiceImpl.getTransportDestinationList(generatedCompanyId)));
			phaseType.add(getTransportTruckMap(transportTruckServiceImpl.getTransportTruckList(generatedCompanyId)));
			phaseType.add(getTransportTrailerMap(transportTrailerServiceImpl.getTransportTrailerList(generatedCompanyId)));
			dto.setPayload(phaseType);
			dto.setStatusMessage("Success");
		}
		catch (Exception e)
		{			
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}		
		return dto; 
	}
	private Map<Integer, String> getTransportDestinationMap(List<TransportDestination> transportDestinationList)
	{
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(TransportDestination transportDestination : transportDestinationList)
		{
			returnMap.put(transportDestination.getId(),transportDestination.getName());
		}
		return returnMap;
		
	}
	
	private Map<Integer, String> getTransportTruckMap(List<TransportTruck> transportTruckList)
	{
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(TransportTruck transportTruck : transportTruckList)
		{
			returnMap.put(transportTruck.getId(),transportTruck.getTransportTruckId());
		}
		return returnMap;
		
	}
	
	private Map<Integer, String> getTransportTrailerMap(List<TransportTrailer> transportTrailerList)
	{
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(TransportTrailer transportTrailer : transportTrailerList)
		{
			returnMap.put(transportTrailer.getId(),transportTrailer.getTransportTrailerId());
		}
		return returnMap;
		
	}
	
	
	/**
	 * Service to save the TransportJourney information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/addTransportJourney", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto saveTransportJourney(@RequestBody TransportJourney transportJourney)
	{
		logger.info("Inside addTransportJourney method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			 
			transportJourney.setUserUpdated(activeUser.getUsername());
			transportJourneyServiceImpl.addTransportJourney(transportJourney);
			dto.setStatusMessage("Success");
		}
		catch (Exception e)
		{			
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}		
		return dto; 
	}
}
