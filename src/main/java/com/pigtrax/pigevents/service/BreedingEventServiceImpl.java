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
import com.pigtrax.master.dao.interfaces.EmployeeGroupDao;
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.MatingDetails;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
import com.pigtrax.pigevents.dao.interfaces.FarrowEventDao;
import com.pigtrax.pigevents.dao.interfaces.MatingDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PregnancyEventDao;
import com.pigtrax.pigevents.dto.BreedingEventBuilder;
import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.dto.MatingDetailsBuilder;
import com.pigtrax.pigevents.dto.MatingDetailsDto;
import com.pigtrax.pigevents.service.interfaces.BreedingEventService;
import com.pigtrax.pigevents.validation.MatingDetailsValidation;

@Repository
public class BreedingEventServiceImpl implements BreedingEventService {
	private static final Logger logger = Logger.getLogger(BreedingEventServiceImpl.class);
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	BreedingEventBuilder builder;
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	EmployeeGroupDao employeeGroupDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	MatingDetailsValidation validationObj;
	
	@Autowired
	PregnancyEventDao pregnancyEventDao;
	
	@Autowired
	MatingDetailsDao matingDetailsDao;
	
	@Autowired
	MatingDetailsBuilder matingDetailsBuilder;
	
	@Autowired
	FarrowEventDao farrowEventDao;
	
	public BreedingEventDto saveBreedingEventInformation(BreedingEventDto dto) 
			throws Exception {

			Integer breedingEventId = null;
		
			try{
				PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(dto.getPigInfoId(), dto.getCompanyId());
				if(pigInfo != null)
				{
					if(pigInfo != null)
						dto.setPigInfoKey(pigInfo.getId());
					
					BreedingEvent breedingEvent = builder.convertToBean(dto);
					
					if(dto.getId() == null)
					{
						boolean check = checkIfPreviousCycleCompleted(dto.getPigInfoKey());
						if(check)
						{
							logger.info("Breeding Event Dtoo : "+dto.toString());
							breedingEventId =  addBreedingEventInformation(breedingEvent);
						}
						else
						{
							throw new PigTraxException("INCOMPLETE_SERVICE_CYCLE");
						}
						
					}
					else
					{
						breedingEventDao.updateBreedingEventInformation(breedingEvent);
						breedingEventId =  breedingEvent.getId();
						
					}
					dto.setId(breedingEventId);
					List<MatingDetails>  matingDetailsList = matingDetailsDao.getMatingDetails(breedingEventId); 
					dto.setMatingDetailsList(matingDetailsBuilder.convertToDtos(matingDetailsList)); 
					return dto;
				}
				else
					return null;
				
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
	
	private boolean checkIfPreviousCycleCompleted(Integer pigInfoId)
	{
		BreedingEvent latestBreedingEvent = breedingEventDao.getLatestServiceEvent(pigInfoId);
		if(latestBreedingEvent == null)
			return true;
		else
		{			
			boolean check = farrowEventDao.checkFarrowEventByBreedingEvent(latestBreedingEvent.getId());	
			if(check)
				return true;
			else
				return false;
		}
	}
	
	@Transactional("ptxJTransactionManager")
	private int addBreedingEventInformation(BreedingEvent breedingEvent) throws SQLException
	{
		int breedingEventId = breedingEventDao.addBreedingEventInformation(breedingEvent);
				
		/*PigTraxEventMaster master = new PigTraxEventMaster();
		master.setPigInfoId(breedingEvent.getPigInfoId());
		master.setUserUpdated(breedingEvent.getUserUpdated());
		master.setBreedingEventId(breedingEventId);
		eventMasterDao.insertEntryEventDetails(master);*/
		
		return breedingEventId;		
	} 
	
	
	@Override
	public List<BreedingEventDto> getBreedingEventInformationList(BreedingEventDto breedingEventDto)
			throws PigTraxException {
		List<BreedingEvent> breedingEventList = null;//breedingEventDao.getBreedingEventInformation(breedingEventDto.getServiceId(), breedingEventDto.getCompanyId());
		List<BreedingEventDto> breedingEventDtoList = null;
		try{
			if(breedingEventDto != null && breedingEventDto.getSearchOption().equals("pigId"))
			{
				breedingEventList =  breedingEventDao.getBreedingEventInformationByPigId(breedingEventDto.getSearchText(), breedingEventDto.getCompanyId());
			}
			else if(breedingEventDto != null && breedingEventDto.getSearchOption().equals("tattoo"))
			{
				breedingEventList =  breedingEventDao.getBreedingEventInformationByTattoo(breedingEventDto.getSearchText(), breedingEventDto.getCompanyId());
			}
			
			breedingEventDtoList = builder.convertToDtos(breedingEventList);
			
			for(BreedingEventDto breedingEvent_Dto : breedingEventDtoList)
			{
				//EmployeeGroupDto employeeGroup = employeeGroupDao.getEmployeeGroup(breedingEvent_Dto.getEmployeeGroupId());
				//breedingEvent_Dto.setEmployeeGroup(employeeGroup);
				
				PigInfo pigInfo = pigInfoDao.getPigInformationById(breedingEvent_Dto.getPigInfoKey());
				breedingEvent_Dto.setPigInfoId(pigInfo.getPigId());
								
				breedingEvent_Dto.setBreedingServiceType(refDataCache.getBreedingServiceTypeMap(breedingEventDto.getLanguage()).get(breedingEvent_Dto.getBreedingServiceTypeId()));
				
				
				List<MatingDetails> matingDetails = matingDetailsDao.getMatingDetails(breedingEvent_Dto.getId());
				
				List<MatingDetailsDto> matingDetailsDtoList = matingDetailsBuilder.convertToDtos(matingDetails);
				
				if(matingDetailsDtoList != null && matingDetailsDtoList.size() > 0)
				{ 
					for(MatingDetailsDto matingDetailsDto : matingDetailsDtoList)
					{
						EmployeeGroupDto employeeGroup = employeeGroupDao.getEmployeeGroup(matingDetailsDto.getEmployeeGroupId());
						matingDetailsDto.setEmployeeGroup(employeeGroup);
					}
					
				}
				
				breedingEvent_Dto.setMatingDetailsList(matingDetailsDtoList);
				
			}
		}catch(SQLException sqlEx)
		{
			throw new PigTraxException(sqlEx.getMessage(), sqlEx.getSQLState());
		}
		
		return breedingEventDtoList;
	}	
	

	
	@Override
	public void deleteBreedingEventInfo(Integer id) throws Exception {
		if(id != null)
		{
			List<PregnancyEvent> events = pregnancyEventDao.getPregnancyEvents(id);
			if(events != null && events.size() > 0)
			{
				throw new PigTraxException("PREGCHECK-TRUE");
			}
			else {
				
				matingDetailsDao.deleteMatingDetailsForBreedingEvent(id);				
				eventMasterDao.deleteBreedingEvent(id);
				breedingEventDao.deleteBreedingEventInfo(id);
			}
		}
	}
	
	@Override
	public String validateBreedingEvent(MatingDetailsDto matingDetailsDto)
	{
		try {
			return validationObj.validate(matingDetailsDto);
		}		
		catch (PigTraxException e) {
			e.printStackTrace();
			return "ERR_GENERAL";
		}
	}
	
	@Override
	public BreedingEventDto getBreedingEventInformation(Integer breedingEventId) throws PigTraxException {
		BreedingEvent breedingEvent;
		BreedingEventDto dto  = null;
		try {
			breedingEvent = breedingEventDao.getBreedingEventInformation(breedingEventId);
			
			dto = builder.convertToDto(breedingEvent);
			
			PigInfo pigInfo = pigInfoDao.getPigInformationById(dto.getPigInfoKey());
			
			dto.setPigInfoId(pigInfo.getPigId());
			
			List<MatingDetails> matingDetails = matingDetailsDao.getMatingDetails(breedingEventId);
			
			List<MatingDetailsDto> matingDetailsDtoList = matingDetailsBuilder.convertToDtos(matingDetails);
			
			if(matingDetailsDtoList != null && matingDetailsDtoList.size() > 0)
			{
				for(MatingDetailsDto matingDetailsDto : matingDetailsDtoList)
				{
					EmployeeGroupDto employeeGroup = employeeGroupDao.getEmployeeGroup(matingDetailsDto.getEmployeeGroupId());
					matingDetailsDto.setEmployeeGroup(employeeGroup);
				}
				
			}
			
			dto.setMatingDetailsList(matingDetailsDtoList);
			
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		} 
		
		
		return dto;
	}
	
	
	@Override 
	public BreedingEventDto checkForBreedingServiceId(String pigId, int companyId) throws PigTraxException {
		try {
			BreedingEvent breedingEvent =  breedingEventDao.checkForBreedingServiceId(pigId, "", companyId);
			return builder.convertToDto(breedingEvent);
		} catch (Exception e) {   
			throw new PigTraxException(e.getMessage());
		}
	}
	 
	@Override
	public List<BreedingEventDto> getActiveBreedingServices(
			BreedingEventDto breedingEventDto) throws PigTraxException {
		List<BreedingEventDto> breedingEventDtoList = getBreedingEventInformationList(breedingEventDto);
		//Need to remove the entries which has pregnancy event captured
		List<BreedingEventDto> filteredList = new ArrayList<BreedingEventDto>();
		
		if(breedingEventDtoList != null)
		{
			for(BreedingEventDto breedingEvent_Dto : breedingEventDtoList)
			{
				List<PregnancyEvent> events = pregnancyEventDao.getPregnancyEvents(breedingEvent_Dto.getId());
				if(breedingEvent_Dto.getServiceStartDate() != null && (events == null || events.size() == 0))
					filteredList.add(breedingEvent_Dto);
			}
		}		
		return filteredList;
	}
	
}
