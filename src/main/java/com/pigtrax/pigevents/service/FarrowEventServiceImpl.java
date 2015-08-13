package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;
import com.pigtrax.pigevents.beans.FarrowEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.dao.interfaces.FarrowEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dto.FarrowEventBuilder;
import com.pigtrax.pigevents.dto.FarrowEventDto;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.pigevents.service.interfaces.PregnancyEventService;
import com.pigtrax.pigevents.validation.FarrowEventValidation;

@Repository
public class FarrowEventServiceImpl implements FarrowEventService {
	private static final Logger logger = Logger.getLogger(PregnancyEventServiceImpl.class);
	
	@Autowired 
	FarrowEventDao farrowEventDao;
	
	@Autowired
	FarrowEventBuilder builder;
	
	@Autowired 
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	EmployeeGroupService employeeGroupService;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	FarrowEventValidation farrowEventValidation;
	
	@Autowired
	PregnancyEventService pregnancyEventService;
	
	
	@Override
	public int saveFarrowEventInformation(FarrowEventDto farrowEventDto)
			throws PigTraxException {
		try{
			PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(farrowEventDto.getPigId(), farrowEventDto.getCompanyId());
			if(pigInfo != null)
				farrowEventDto.setPigInfoId(pigInfo.getId());
			
			FarrowEvent farrowEvent = builder.convertToBean(farrowEventDto);
			 
			if(farrowEventDto.getId() == null)
			{
			   return addFarrowEventInformation(farrowEvent);
			}
			else
			{
				return farrowEventDao.updateFarrowEventDetails(farrowEvent); 
			}
		}catch(SQLException sqlEx)
		{
			if("23505".equals(sqlEx.getSQLState()))
			{
				throw new PigTraxException("ServiceId already exists", sqlEx.getSQLState());
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
	
	
	@Transactional("ptxJTransactionManager")
	private int addFarrowEventInformation(FarrowEvent farrowEvent) throws SQLException
	{
		int farrowEventId = farrowEventDao.addFarrowEventDetails(farrowEvent);
		
		PigTraxEventMaster master = new PigTraxEventMaster();
		master.setPigInfoId(farrowEvent.getPigInfoId());
		master.setUserUpdated(farrowEvent.getUserUpdated());
		master.setEventTime(farrowEvent.getFarrowDateTime());
		master.setFarrowEventId(farrowEventId);
		eventMasterDao.insertEntryEventDetails(master);
		
		return farrowEventId;		
	}
	
		
	/**
	 * To validate Pregnancy Event Dto
	 */
	public int validateFarrowEvent(FarrowEventDto farrowEventDto) {		
		return farrowEventValidation.validate(farrowEventDto);		
	}
	
	
	@Override
	public void deleteFarrowEvent(Integer farrowEventId)
			throws PigTraxException {
		 try {
			farrowEventDao.deleteFarrowEvent(farrowEventId);
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
	
	@Override
	public List<FarrowEventDto> getFarrowEvents(FarrowEventDto farrowEventDto)
			throws PigTraxException {
		List<FarrowEvent> farrowEvents = null;
		List<FarrowEventDto> farrowEventDtoList = new ArrayList<FarrowEventDto>();
		try {
			farrowEvents = farrowEventDao.getFarrowEvents(farrowEventDto.getSearchText(), farrowEventDto.getSearchOption(), farrowEventDto.getCompanyId());
			farrowEventDtoList =  builder.convertToDtos(farrowEvents);
			
			for(FarrowEventDto dto : farrowEventDtoList)
			{
				//Get pregnancyEvent details
				dto.setPregnancyEventDto(pregnancyEventService.getPregnancyEventInformation(dto.getPregnancyEventId(), farrowEventDto.getLanguage()));  
				 
				//Get the employee group details
				EmployeeGroupDto empGrpDto = employeeGroupService.getEmployeeGroup(dto.getEmployeeGroupId());
				dto.setEmployeeGroup(empGrpDto);
				
				//Get the pig id for a given pigIdInfo
				PigInfo pigInfo = pigInfoDao.getPigInformationById(dto.getPigInfoId());
				dto.setPigId(pigInfo.getPigId());
							
			}
			
			
			return farrowEventDtoList;
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
}