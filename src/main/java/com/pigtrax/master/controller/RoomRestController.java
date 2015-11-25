package com.pigtrax.master.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.master.dto.Room;
import com.pigtrax.master.service.interfaces.RoomService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/room")
public class RoomRestController {

	
	private static final Logger logger = Logger.getLogger(RoomRestController.class);

	@Autowired
	RoomService roomService;
	
	/**
	 * Service to retrive the list of Room
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getRoomList", method=RequestMethod.GET, produces="application/json")
	public ServiceResponseDto getRoomList(@RequestParam int generatedBarnId)
	{
		logger.info("Inside getRoomList" );
		ServiceResponseDto dto = new ServiceResponseDto();
		List<Room> roomList  = roomService.getRoomList(generatedBarnId);
		dto.setPayload(roomList);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	/**
	 * Service to retrive the list of Room
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getRoomsForCompany", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	public ServiceResponseDto getRoomsForCompany(@RequestBody int companyId)
	{
		logger.info("Inside getRoomsForCompany" );
		ServiceResponseDto dto = new ServiceResponseDto();
		Map<Integer, String> roomMap   = roomService.getRoomListBasedOnCompanyId(companyId);
		dto.setPayload(roomMap);
		dto.setStatusMessage("Success");
		return dto;
	}
	
	/**String roomId, Boolean roomStatus
	 * Service to update room
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/updateRoomStatus", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto updateRoomStatus(@RequestParam String roomId, @RequestParam String isActive)
	{
		logger.info("Inside updateBarnStatus()" );
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord;
		
		try 
		{
			updatedRecord = roomService.updateRoomStatus(roomId.toUpperCase(), new Boolean(isActive));
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
	@RequestMapping(value = "/insertRoomRecord", method=RequestMethod.POST, produces="application/json")
	public ServiceResponseDto insertRoomRecord( @RequestBody Room room)
	{
		logger.debug("Inside insertRoomRecord()" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		int updatedRecord = 0;
		try 
		{
			room.setUserUpdated(activeUser.getUsername());
		//	Company checkCompany = companyService.findByCompanyID(company.getCompanyId());
			if( 0 == room.getId() )
			{
				updatedRecord = roomService.insertRoomRecord(room);
			}
			else
			{
				updatedRecord = roomService.updateRoomRecord(room);
			}
			dto.setStatusMessage("SUCCESS");
			dto.setPayload(updatedRecord);
		} 
		catch (SQLException e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			logger.error("Inside insertRoomRecord/updateRoomRecord" +((SQLException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		catch (Exception e) {
			updatedRecord = 0;
			dto.setStatusMessage("FALSE");
			if( e instanceof DuplicateKeyException)
			{
				dto.setPayload("Room with ID : "+room.getRoomId().toUpperCase() + " already present.");
				logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			}
			
		//	logger.error("Inside updateCompanyStatus()" +((DuplicateKeyException) e).getLocalizedMessage() + e.getMessage());
			e.printStackTrace();
		}
		
		return dto;
	}



}
