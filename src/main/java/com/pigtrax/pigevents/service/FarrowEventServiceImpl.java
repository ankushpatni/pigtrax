package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;
import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.FarrowEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
import com.pigtrax.pigevents.dao.interfaces.FarrowEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PregnancyEventDao;
import com.pigtrax.pigevents.dto.FarrowEventBuilder;
import com.pigtrax.pigevents.dto.FarrowEventDto;
import com.pigtrax.pigevents.dto.PigletEventDto;
import com.pigtrax.pigevents.dto.PregnancyEventDto;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.pigevents.service.interfaces.PigletEventService;
import com.pigtrax.pigevents.service.interfaces.PregnancyEventService;
import com.pigtrax.pigevents.validation.FarrowEventValidation;
import com.pigtrax.usermanagement.dao.interfaces.CompanyDao;

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
	
	@Autowired
	PigletEventService pigletEventService;
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	PregnancyEventDao pregnancyEventDao;
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	
	@Override
	@Transactional("ptxJTransactionManager")
	public int saveFarrowEventInformation(FarrowEventDto farrowEventDto)
			throws PigTraxException {
		try{
			PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(farrowEventDto.getPigId(), farrowEventDto.getCompanyId(), farrowEventDto.getPremiseId());
			if(pigInfo != null)
			{
				farrowEventDto.setPigInfoId(pigInfo.getId());
				
				boolean check = setBreedingEventId(farrowEventDto);
				if(check)
				{
					FarrowEvent farrowEvent = builder.convertToBean(farrowEventDto);
					 
					if(farrowEventDto.getId() == null)
					{			   
					   return addFarrowEventInformation(farrowEvent, farrowEventDto.getCompanyId(), farrowEventDto.getLastGestationLength());
					}
					else
					{
						int rows = farrowEventDao.updateFarrowEventDetails(farrowEvent);
						PigTraxEventMaster master = new PigTraxEventMaster();
						master.setPigInfoId(farrowEvent.getPigInfoId());
						master.setUserUpdated(farrowEvent.getUserUpdated());
						master.setFarrowEventId(farrowEvent.getId());
						master.setEventTime(farrowEvent.getFarrowDateTime());
						eventMasterDao.updateFarrowEventMasterDetails(master);
					}
				}
				else
				{
					throw new PigTraxException("INVALID-SERVICE-RECORD");
				}
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
		return -1;
	}
	
	
	
	private boolean setBreedingEventId(FarrowEventDto farrowEventDto)
	{
		if(farrowEventDto.getPigInfoId() != null && farrowEventDto.getFarrowDateTime() != null)
		{
			List<PregnancyEvent> pregnancyList = pregnancyEventDao.getOpenPregnancyRecords(farrowEventDto.getPigInfoId());
			List<BreedingEvent> breedingEventList = breedingEventDao.getPendingFarrowServiceRecords(farrowEventDto.getPigInfoId());
			if(breedingEventList != null && 0 <breedingEventList.size())
			{
				for(BreedingEvent breedingEventDetails :  breedingEventList)
				{
					if(breedingEventDetails != null) 
					{
						DateTime serviceDate = new DateTime(breedingEventDetails.getServiceStartDate());
						DateTime farrowDate = new DateTime(farrowEventDto.getFarrowDateTime());
						int duration = Days.daysBetween(serviceDate, farrowDate).getDays();
						if(duration >= 105 && duration <= 125)
						{
							farrowEventDto.setBreedingEventId(breedingEventDetails.getId());
							farrowEventDto.setLastGestationLength(duration);
							break;
						}
					}
				}
			}
			if(farrowEventDto.getBreedingEventId() != null && farrowEventDto.getBreedingEventId() != 0)
				return true;
			else
				return false;
		}
		return false;
	}
	
	
	@Transactional("ptxJTransactionManager")
	private int addFarrowEventInformation(FarrowEvent farrowEvent, Integer companyId, Integer gestationLength) throws SQLException
	{		
		int farrowEventId = farrowEventDao.addFarrowEventDetails(farrowEvent);
		
		pigInfoDao.increaseParity(farrowEvent.getPigInfoId(), gestationLength);
		
		PigInfo pigInfo = pigInfoDao.getPigInformationById(farrowEvent.getPigInfoId());
		
		breedingEventDao.updateBreedingParity(pigInfo.getParity(), farrowEvent.getBreedingEventId());
		
		farrowEventDao.updateLitterId(farrowEventId, companyId);
		
		companyDao.increaseLitterId(companyId); 
		
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
			
			 FarrowEvent event = farrowEventDao.getFarrowEvent(farrowEventId);
			 if(event != null)
			 {
				 //decrease the parity value by 1
				pigInfoDao.decreaseParity(event.getPigInfoId());
			 	eventMasterDao.deleteFarrowEvent(farrowEventId);
			 	farrowEventDao.deleteFarrowEvent(farrowEventId);
			 }
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
			farrowEvents = farrowEventDao.getFarrowEvents(farrowEventDto.getSearchText(), farrowEventDto.getSearchOption(), farrowEventDto.getCompanyId(), farrowEventDto.getSelectedPremise());
			farrowEventDtoList =  builder.convertToDtos(farrowEvents);
			
			for(FarrowEventDto dto : farrowEventDtoList)
			{
				//Get pregnancyEvent details
				//dto.setPregnancyEventDto(pregnancyEventService.getPregnancyEventInformation(dto.getPregnancyEventId(), farrowEventDto.getLanguage()));  
				 
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
	
	@Override
	public FarrowEventDto getFarrowEventDetails(Integer farrowEventId)
			throws PigTraxException {
		FarrowEvent farrowEvent = farrowEventDao.getFarrowEvent(farrowEventId);
		FarrowEventDto dto = null;
		if(farrowEvent != null)
		{
			dto = builder.convertToDto(farrowEvent);
			PigInfo info;
			try {
				
				//PregnancyEventDto pregEventDto = pregnancyEventService.getPregnancyEventInformation(dto.getPregnancyEventId(), "en");
				//if(pregEventDto != null)
				//	dto.setPregnancyEventDto(pregEventDto);
				
				info = pigInfoDao.getPigInformationById(dto.getPigInfoId());
			} catch (SQLException e) {
				throw new PigTraxException(e.getMessage());
			}
			if(info != null)
				dto.setPigId(info.getPigId());
		}
		return dto;
	}
	
	@Override
	public FarrowEventDto getFarrowEventDetails(FarrowEventDto farrowEventDto)
			throws PigTraxException {
		List<FarrowEventDto> farrowEvents = getFarrowEvents(farrowEventDto);
		if(farrowEvents != null && farrowEvents.size() > 0)
		{
		  //Check if there are 	
		  FarrowEventDto dto = farrowEvents.get(0);
		  int liveBorns = dto.getLiveBorns();		  
		  List<PigletEventDto> pigletEventDtoList = pigletEventService.getPigletEventsByFarrowId(dto.getFarrowId(), farrowEventDto.getCompanyId());
		  int cntPigletsAdded = (pigletEventDtoList != null) ? pigletEventDtoList.size() : 0;
		  if(liveBorns > cntPigletsAdded)		  
			  dto.setPigletsAdded(false);
		  else 
			  dto.setPigletsAdded(true);
		  return dto;
		}
		
		return null;
	}
	
	@Override 
	public FarrowEventDto getFarrowEventByPregancyEvent(Integer pregnancyEventId) {
		FarrowEvent event =  farrowEventDao.getFarrowEventByPregancyEvent(pregnancyEventId);
		if(event != null) 
			return builder.convertToDto(event);
		else 
			return null;
	}
	
	@Override
	public Integer getFarrowEventIdByLitterId(String pigId, Integer companyId,
			Integer premiseId, Integer litterId) throws PigTraxException{
		try{
			PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(pigId, companyId, premiseId);
			if(pigInfo != null)
			{
				FarrowEvent farrowEvent = farrowEventDao.getFarrowEventIdByLitterId(pigInfo.getId(), litterId);
				if(farrowEvent != null)
					return farrowEvent.getId();
			}
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
}
