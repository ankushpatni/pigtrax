package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;
import com.pigtrax.pigevents.beans.FarrowEvent;
import com.pigtrax.pigevents.beans.PigletEvent;
import com.pigtrax.pigevents.dao.interfaces.FarrowEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PigletEventDao;
import com.pigtrax.pigevents.dto.PigletEventBuilder;
import com.pigtrax.pigevents.dto.PigletEventDto;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.pigevents.service.interfaces.PigletEventService;
import com.pigtrax.pigevents.validation.PregnancyEventValidation;

@Service
public class PigletEventServiceImpl implements PigletEventService {
	private static final Logger logger = Logger.getLogger(PigletEventServiceImpl.class);
	
	@Autowired 
	PigletEventDao pigletEventDao;
	
	@Autowired
	PigletEventBuilder builder;
	
	@Autowired 
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	EmployeeGroupService employeeGroupService;
	
	@Autowired
	FarrowEventDao farrowInfoDao;
	
	@Autowired
	RefDataCache refDataCache;
	 
	@Autowired
	FarrowEventService farrowEventService;
	
	
	@Override
	public int savePigletEventInformation(PigletEventDto pigletEventDto)
			throws PigTraxException {
		try{
			FarrowEvent farrowInfo = farrowInfoDao.getFarrowEvent(pigletEventDto.getFarrowEventId());  
			if(farrowInfo != null)
				pigletEventDto.setFarrowEventId(farrowInfo.getId());
			
			PigletEvent pigletEvent = builder.convertToBean(pigletEventDto);
			
			if(pigletEventDto.getPigletId() == null)
			{
			   return pigletEventDao.addPigletEventDetails(pigletEvent); 
			}
			else
			{
				return pigletEventDao.updatePigletEventDetails(pigletEvent)   ;
			}
		}catch(SQLException sqlEx)
		{
			if("23505".equals(sqlEx.getSQLState()))
			{
				throw new PigTraxException("ServiceId already exists", sqlEx.getSQLState(), true);
			}
			else
				throw new PigTraxException("SqlException occured", sqlEx.getSQLState());
		}
		catch(DuplicateKeyException sqlEx)
		{
			  logger.info("DuplicateKeyException : "+sqlEx.getRootCause()+"/"+sqlEx.getCause());
				throw new PigTraxException("Duplicate Key Exception occured. Please check Service Id", "", true);
		}
	}
	
	
	@Override
	public List<PigletEventDto> getPigletEvents(PigletEventDto pigletEventDto) throws PigTraxException { 
		 
		List<PigletEvent> pigletEvents = null;
		List<PigletEventDto> pigletEventDtoList = new ArrayList<PigletEventDto>();
		try {
			pigletEvents = pigletEventDao.getPigletEvents(pigletEventDto.getSearchText(), pigletEventDto.getSearchOption(), pigletEventDto.getCompanyId());
			pigletEventDtoList =  builder.convertToDtos(pigletEvents);
			
			for(PigletEventDto dto : pigletEventDtoList)
			{	
				dto.setFarrowEventDto(farrowEventService.getFarrowEventDetails(dto.getFarrowEventId()));
				
			}
			return pigletEventDtoList;
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
		
	/**
	 * Delete a pregnancy event based on the primary key ID
	 */
	@Override
	public void deletePigletEvent(Integer pigletEventId)
			throws PigTraxException {
		try{
			pigletEventDao.deletePigletEvent(pigletEventId); 
		}
		catch(SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
	/**
	 * Retrieve the list of Piglet events for a given farrowId and company Id
	 */
	@Override
	public List<PigletEventDto> getPigletEventsByFarrowId(String farrowId,
			Integer companyId) throws PigTraxException {
		try {
			List<PigletEvent> pigletEvents = pigletEventDao.getPigletEvents(farrowId, "farrowId", companyId);
			return builder.convertToDtos(pigletEvents);
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

	
}
