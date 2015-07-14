package com.pigtrax.pigevents.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pigtrax.master.dao.interfaces.BarnDao;
import com.pigtrax.pigevents.dto.BarnDto;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dao.interfaces.EmployeeDao;
import com.pigtrax.usermanagement.dto.EmployeeDto;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/entryEvent")
public class EntryEventRestController {
	
	private static final Logger logger = Logger.getLogger(EntryEventRestController.class);
	
	@Autowired
	BarnDao barnDao;
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getBarns", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getBarns()
	{
		logger.info("Inside getBarns method" );
		Integer companyId = null;
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(activeUser != null)
			companyId = activeUser.getCompanyId();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			List<BarnDto> barnList = barnDao.getBarns(companyId);
			logger.info("Barn List = "+barnList);
			dto.setStatusMessage("Success");
			dto.setPayload(barnList);
		} catch (SQLException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
    
}
