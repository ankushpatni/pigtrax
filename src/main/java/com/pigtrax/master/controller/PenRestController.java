package com.pigtrax.master.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.master.dto.Pen;
import com.pigtrax.master.service.interfaces.PenService;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/pen")
public class PenRestController {
	
	private static final Logger logger = Logger.getLogger(PenRestController.class);

	@Autowired
	PenService penService;
	
	/**
	 * Service to retrive the list of Pen
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPenList", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getPenList(@RequestParam int generatedRoomId)
	{
		logger.info("Inside getRoomList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Pen> penList  = penService.getPenList(generatedRoomId);
		dto.setPayload(penList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	/**String roomId, Boolean roomStatus
	 * Service to update room
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/updatePenStatus", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto updatePenStatus( @RequestParam String penId, @RequestParam String isActive)
	{
		logger.info("Inside updateBarnStatus()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord;
		try 
		{
			updatedRecord = penService.updatePenStatus(penId.toUpperCase(), new Boolean(isActive));
			dto.setStatusMessage("SUCCESS");
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside updateBarnStatus()" +e.getErrorCode() + e.getMessage());
			e.printStackTrace();
		}
		dto.setPayload(updatedRecord);		
		return dto;
	}
	
	/**
	 * Service to insert  the premises record
	 * @return Company
	 */
	@RequestMapping(value = "/insertPenRecord", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertPenRecord( @RequestBody Pen pen)
	{
		logger.debug("Inside insertRoomRecord()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord = 0;
		try 
		{
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			if( 0 == pen.getId() )
			{
				updatedRecord = penService.insertPenRecord(pen);
			}
			else
			{
				updatedRecord = penService.updatePenRecord(pen);
			}
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(updatedRecord);
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside insertpenRecord/updatePenRecord" +((SQLException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		catch (Exception e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setPayload("Pen with ID : "+pen.getPenId().toUpperCase() + " already present.");
				logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			}
			
		//	logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}


}
