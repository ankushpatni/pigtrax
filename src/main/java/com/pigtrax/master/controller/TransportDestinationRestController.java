package com.pigtrax.master.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.master.dto.TransportDestination;
import com.pigtrax.master.service.interfaces.TransportDestinationService;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/transportDestination")
public class TransportDestinationRestController {
	
	private static final Logger logger = Logger.getLogger(TransportDestinationRestController.class);

	@Autowired
	TransportDestinationService transportDestinationServiceImpl;
	
	/**
	 * Service to retrive the list of TransportDestination
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getTransportDestination", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getTransportDestination(@RequestParam int generatedCompanyId, HttpServletRequest request)
	{
		logger.info("Inside getTransportDestination" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List phaseType = new ArrayList();
		phaseType.add(transportDestinationServiceImpl.getTransportDestinationList(generatedCompanyId));
		dto.setPayload(phaseType);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	@RequestMapping(value = "/insertTransportDestination", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertTransportDestination( @RequestBody TransportDestination transportDestination)
	{
		logger.debug("Inside insertTransportDestination()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord = 0;
		try 
		{
			if( 0 == transportDestination.getId() )
			{
				updatedRecord = transportDestinationServiceImpl.insertTransportDestinationRecord(transportDestination);
			}
			else
			{
				updatedRecord = transportDestinationServiceImpl.updateTransportDestinationRecord(transportDestination);
			}
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(updatedRecord);
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside insertTransportTruckRecord/updateTransportTruckRecord" +((SQLException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		catch (Exception e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setPayload("TransportTruck with ID : "+transportDestination.getId() + " already present.");
				logger.error("Inside insertTransportTruckRecord()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			}
			logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}


}
