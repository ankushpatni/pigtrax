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
import com.pigtrax.cache.dao.interfaces.RefDataDao;
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
import com.pigtrax.pigevents.dto.PregnancyEventBuilder;
import com.pigtrax.pigevents.dto.PregnancyEventDto;
import com.pigtrax.pigevents.service.interfaces.BreedingEventService;
import com.pigtrax.pigevents.service.interfaces.PregnancyEventService;
import com.pigtrax.pigevents.validation.PregnancyEventValidation;

@Repository
public class PregnancyEventServiceImpl implements PregnancyEventService {
	private static final Logger logger = Logger.getLogger(PregnancyEventServiceImpl.class);
	
	@Autowired 
	PregnancyEventDao pregnancyEventDao;
	
	@Autowired
	PregnancyEventBuilder builder;
	
	@Autowired 
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	EmployeeGroupService employeeGroupService;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	PregnancyEventValidation pregnancyEventValidation;
	
	@Autowired
	BreedingEventService breedingEventService;
	
	@Autowired
	RefDataDao refDataDao;
	
	@Autowired
	FarrowEventDao farrowEventDao;
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	
	@Override
	public int savePregnancyEventInformation(PregnancyEventDto pregnancyEventDto)
			throws PigTraxException {
		try{
			PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(pregnancyEventDto.getPigId(), pregnancyEventDto.getCompanyId());
			if(pigInfo != null)
				pregnancyEventDto.setPigInfoId(pigInfo.getId());
			
			boolean flag = setBreedingEventId(pregnancyEventDto);
			
			if(flag)
			{			
				PregnancyEvent pregnancyEvent = builder.convertToBean(pregnancyEventDto);
				
				if(pregnancyEventDto.getId() == null)
				{
				   return addPregnancyEventInformation(pregnancyEvent);
				}
				else
				{
					return pregnancyEventDao.updatePregnancyEventDetails(pregnancyEvent);  
				}
			}
			else
			{
				throw new PigTraxException("INVALID-SERVICE");
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
	
	
	
	private boolean setBreedingEventId(PregnancyEventDto pregnancyEventDto) {
		try {
			if(pregnancyEventDto.getResultDate() != null)
			{
				DateTime pregnancyResultDate = new DateTime(pregnancyEventDto.getResultDate());
				Integer eventTypeId = pregnancyEventDto.getPregnancyEventTypeId();
				
				List<BreedingEvent> breedingEventList = breedingEventDao.getOpenServiceRecords(pregnancyEventDto.getPigInfoId());
				
				if(breedingEventList != null && 0<breedingEventList.size())
				{
					for(BreedingEvent breedingEvent : breedingEventList)
					{
						DateTime serviceDate = new DateTime(breedingEvent.getServiceStartDate());
						int durationDays = Days.daysBetween(serviceDate, pregnancyResultDate).getDays();
						
						
						if (eventTypeId == 1 && durationDays >= 18 && durationDays <= 60) {
							pregnancyEventDto.setBreedingEventId(breedingEvent.getId());
							break;
						}
						else if (eventTypeId == 2 && durationDays >= 18 && durationDays >= 110) {
							pregnancyEventDto.setBreedingEventId(breedingEvent.getId());
							break;
						}
						else if (eventTypeId == 3 && durationDays >= 105 && durationDays <= 125) {
							pregnancyEventDto.setBreedingEventId(breedingEvent.getId());
							break;
						}						
							
					}
				}
				if(pregnancyEventDto.getBreedingEventId() != 0)
					return true;
				else
					return false;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
	
	
	@Transactional("ptxJTransactionManager")
	private int addPregnancyEventInformation(PregnancyEvent pregnancyEvent) throws SQLException
	{
		int pergnancyEventId = pregnancyEventDao.addPregnancyEventdDetails(pregnancyEvent);
		
		PigTraxEventMaster master = new PigTraxEventMaster();
		master.setPigInfoId(pregnancyEvent.getPigInfoId());
		master.setUserUpdated(pregnancyEvent.getUserUpdated());
		master.setEventTime(pregnancyEvent.getResultDate());
		master.setPregnancyEventId(pergnancyEventId);
		eventMasterDao.insertEntryEventDetails(master);
		
		return pergnancyEventId;		
	}
	
	
	@Override
	public List<PregnancyEventDto> getPregnancyEvents(PregnancyEventDto pregnancyEventDto) throws PigTraxException { 
		 
		List<PregnancyEvent> pregnancyEvents = null;
		List<PregnancyEventDto> pregnancyEventDtoList = new ArrayList<PregnancyEventDto>();
		try {
			pregnancyEvents = pregnancyEventDao.getPregnancyEvents(pregnancyEventDto.getSearchText(), pregnancyEventDto.getSearchOption(), pregnancyEventDto.getCompanyId(), pregnancyEventDto.getSelectedPremise());
			pregnancyEventDtoList =  builder.convertToDtos(pregnancyEvents);
			
			for(PregnancyEventDto dto : pregnancyEventDtoList)
			{
				
				dto.setBreedingEventDto(breedingEventService.getBreedingEventInformation(dto.getBreedingEventId()));
				
				//Get the employee group details
				EmployeeGroupDto empGrpDto = employeeGroupService.getEmployeeGroup(dto.getEmployeeGroupId());
				dto.setEmployeeGroup(empGrpDto);
				
				//Get the pig id for a given pigIdInfo
				PigInfo pigInfo = pigInfoDao.getPigInformationById(dto.getPigInfoId());
				dto.setPigId(pigInfo.getPigId());		
				
				dto.setPregnancyEventType(refDataCache.getPregnancyEventTypeMap(pregnancyEventDto.getLanguage()).get(dto.getPregnancyEventTypeId()));
				
			}
			
			if(pregnancyEventDto.getPregnancyEventType()!= null && pregnancyEventDto.getPregnancyEventType().length() > 0)
				pregnancyEventDtoList = filterByPregnancyEventType(pregnancyEventDto.getPregnancyEventType(), pregnancyEventDtoList);
			
			return pregnancyEventDtoList;
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
	
	/**
	 * Filter the list of events for the given pregnancy event type
	 * @param pregnancyEventType
	 * @param pregnancyEventList
	 * @return
	 */
	private List<PregnancyEventDto> filterByPregnancyEventType(String pregnancyEventType, List<PregnancyEventDto> pregnancyEventList)
	{
		
		
		List<PregnancyEventDto> filteredList = null;
		if(pregnancyEventList != null)
		{
			filteredList = new ArrayList<PregnancyEventDto>();
			for(PregnancyEventDto dto : pregnancyEventList)
			{
				FarrowEvent farrowEvent = farrowEventDao.getFarrowEventByPregancyEvent(dto.getId());
				
				Integer fieldCode = refDataDao.getFieldCodeForId(dto.getPregnancyEventTypeId(), "PregnancyEventType");
				
				Integer resultFieldCode = refDataDao.getFieldCodeForId(dto.getPregnancyExamResultTypeId(), "PregnancyExamResultType");
				
				if(pregnancyEventType != null &&  fieldCode == 1 && resultFieldCode == 1 && farrowEvent == null)
					filteredList.add(dto);					
			}
		}
		return filteredList;
	}
	
	
	/**
	 * Delete a pregnancy event based on the primary key ID
	 */
	@Override
	public void deletePregnancyEvent(Integer pregnancyEventId)
			throws PigTraxException {
		try{
			eventMasterDao.deletePregnancyEvent(pregnancyEventId);
			pregnancyEventDao.deletePregnancyEvent(pregnancyEventId);
		}
		catch(SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
	/**
	 * To validate Pregnancy Event Dto
	 */
	public int validatePregnancyEvent(PregnancyEventDto pregnancyEventDto) {		
		return pregnancyEventValidation.validate(pregnancyEventDto);		
	}
	
	@Override
	public PregnancyEventDto getPregnancyEventInformation( 
			Integer pregnancyEventId, String language) throws PigTraxException {
		PregnancyEvent pregnancyEvent = pregnancyEventDao.getPregnancyEvent(pregnancyEventId);
		PregnancyEventDto eventDto =  builder.convertToDto(pregnancyEvent);
		PigInfo info = null;
		if(eventDto != null)
		{
		try {
			info = pigInfoDao.getPigInformationById(eventDto.getPigInfoId());
			eventDto.setPigId(info.getPigId());
		} catch (SQLException e) {
			info = null;
		}
		
		eventDto.setBreedingEventDto(breedingEventService.getBreedingEventInformation(eventDto.getBreedingEventId()));
		eventDto.setPregnancyEventType(refDataCache.getPregnancyEventTypeMap(language).get(eventDto.getPregnancyEventTypeId()));
		}
		return eventDto;
	}
	
	@Override
	public PregnancyEventDto getPregnancyEvent(Integer breedingEventId, String language)
			throws PigTraxException {
		List<PregnancyEvent> pregnancyEvents = null;
		List<PregnancyEventDto> pregnancyEventDtoList = new ArrayList<PregnancyEventDto>(); 
		try {
			pregnancyEvents = pregnancyEventDao.getPregnancyEvents(breedingEventId);
			pregnancyEventDtoList =  builder.convertToDtos(pregnancyEvents);
			
			for(PregnancyEventDto dto : pregnancyEventDtoList)
			{
				
				dto.setBreedingEventDto(breedingEventService.getBreedingEventInformation(dto.getBreedingEventId()));
				
				//Get the employee group details
				EmployeeGroupDto empGrpDto = employeeGroupService.getEmployeeGroup(dto.getEmployeeGroupId());
				dto.setEmployeeGroup(empGrpDto);
				
				//Get the pig id for a given pigIdInfo
				PigInfo pigInfo = pigInfoDao.getPigInformationById(dto.getPigInfoId());
				dto.setPigId(pigInfo.getPigId());		
				
				dto.setPregnancyEventType(refDataCache.getPregnancyEventTypeMap(language).get(dto.getPregnancyEventTypeId()));
				
			}
			
			pregnancyEventDtoList = filterByPregnancyEventType("PregnancyEvent", pregnancyEventDtoList);
			
			if(pregnancyEventDtoList != null && 0< pregnancyEventDtoList.size())
				return pregnancyEventDtoList.get(0);
			
			return null;
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
}
