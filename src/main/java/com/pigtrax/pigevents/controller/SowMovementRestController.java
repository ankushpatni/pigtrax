package com.pigtrax.pigevents.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.service.interfaces.PremisesService;
import com.pigtrax.pigevents.beans.SowMovement;
import com.pigtrax.pigevents.dto.PigInfoDto;
import com.pigtrax.pigevents.service.interfaces.PigInfoService;
import com.pigtrax.pigevents.service.interfaces.SowMovementService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/sowMomentEvent")
public class SowMovementRestController {
	
 private static final Logger logger = Logger.getLogger(SowMovementController.class);
	 
	 @Autowired
	 SowMovementService sowMovementService;
	 
	 @Autowired
		PremisesService premisesService;
	 
	 @Autowired
	 PigInfoService pigInfoService;
	 
	 /**
		 * Service to save the Sow Moment Information
		 * @return ServiceResponseDto
		 */
		@RequestMapping(value = "/addSowEvent", method=RequestMethod.POST, produces="application/json")
		@ResponseBody
		public ServiceResponseDto addSowMovement(HttpServletRequest request, @RequestBody SowMovement sowMovement)
		{
			logger.info("Inside addSowEvent RestController method" ); 
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ServiceResponseDto dto = new ServiceResponseDto();
			try 
			{
				sowMovement.setUserUpdated(activeUser.getUsername());
				int rowsInserted  = 0;
				
				 rowsInserted = sowMovementService.addSowMovement(sowMovement);
				 
				 PigInfoDto pigInfoDto = pigInfoService.getPigInformationById(sowMovement.getPigInfoId());
				 pigInfoDto.setPremiseId(sowMovement.getPremiseId());
				 pigInfoDto.setRoomId(sowMovement.getRoomId());
				 pigInfoDto.setUserUpdated(activeUser.getUsername());
				 pigInfoDto.setCompanyId(sowMovement.getCompanyId());
				 pigInfoService.updatePigInfoRecordForSowMovement(pigInfoDto);
				
				dto.setStatusMessage("Success");
			} 
			catch (PigTraxException e)
			{
				if(e.isDuplicateStatus())
				{
					dto.setDuplicateRecord(true);
				}
				dto.setStatusMessage("ERROR : "+e.getMessage());
			} 
			catch (Exception e)
			{			
				dto.setStatusMessage("ERROR : "+e.getMessage());
			}		
			return dto; 
		}		
		
		/**
		 * Service to delete the pig information
		 * @return ServiceResponseDto
		 */
		@RequestMapping(value = "/deleteSowMovement", method=RequestMethod.POST, produces="application/json")
		@ResponseBody
		public ServiceResponseDto deleteSowMovement(HttpServletRequest request, @RequestBody Integer id)
		{
			logger.info("Inside deleteSowMovement method" );
			ServiceResponseDto dto = new ServiceResponseDto();
			try {
				int rowsDeleted = sowMovementService.deleteSawMoment(id);
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
		 * Service to retrive the list of get SowMoment List
		 * @return ServiceResponseDto
		 */
		@RequestMapping(value = "/getSowMomentList", method=RequestMethod.POST, produces="application/json")
		@ResponseBody
		public ServiceResponseDto getSowMoementList(HttpServletRequest request, @RequestBody Map<String, String> params )
		{
			logger.info("Inside getSowMomentList method" );
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole());
			ServiceResponseDto dto = new ServiceResponseDto();
			   
			try {
				List<SowMovement> SowMomentList = sowMovementService.getSowMovementListByPigInfoId(params.get("pigInfo"), Integer.parseInt(params.get("companyId")), Integer.parseInt(params.get("premiseId")));
				dto.setPayload(SowMomentList);
			} catch (PigTraxException e) {
				e.printStackTrace();
				dto.setStatusMessage("ERROR");
			} catch (Exception e) {
				e.printStackTrace();
				dto.setStatusMessage("ERROR");
			}
			return dto;
		}
		
		/**
		 * Service to retrive the list of get SowMoment List
		 * @return ServiceResponseDto
		 */
		@RequestMapping(value = "/getPremisesMapList", method=RequestMethod.GET, produces="application/json")
		@ResponseBody
		public ServiceResponseDto getPremisesMapList(HttpServletRequest request,@RequestParam Integer companyId)
		{
			logger.info("Inside getSowMomentList method" );
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole());
			ServiceResponseDto dto = new ServiceResponseDto();
			   
			try {
				dto.setPayload(premisesService.getPremisesListBasedOnCompanyId(companyId));
			} catch (Exception e) {
				e.printStackTrace();
				dto.setStatusMessage("ERROR");
			}
			return dto;
		}


}
