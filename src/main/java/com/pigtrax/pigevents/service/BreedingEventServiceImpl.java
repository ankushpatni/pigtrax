package com.pigtrax.pigevents.service;

import java.sql.SQLException;
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
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dto.BreedingEventBuilder;
import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.service.interfaces.BreedingEventService;
import com.pigtrax.pigevents.validation.BreedingEventValidation;

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
	BreedingEventValidation validationObj;
	
	public int saveBreedingEventInformation(BreedingEventDto dto)
			throws Exception {

			try{
				PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(dto.getPigInfoId(), dto.getCompanyId());
				if(pigInfo != null)
					dto.setPigInfoKey(pigInfo.getId());
				
				BreedingEvent breedingEvent = builder.convertToBean(dto);
				
				if(dto.getId() == null)
				{
				logger.info("Breeding Event Dtoo : "+dto.toString());
				   return addBreedingEventInformation(breedingEvent);
				}
				else
				{
					return breedingEventDao.updateBreedingEventInformation(breedingEvent);
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
	private int addBreedingEventInformation(BreedingEvent breedingEvent) throws SQLException
	{
		int breedingEventId = breedingEventDao.addBreedingEventInformation(breedingEvent);
				
		PigTraxEventMaster master = new PigTraxEventMaster();
		master.setPigInfoId(breedingEvent.getPigInfoKey());
		master.setUserUpdated(breedingEvent.getUserUpdated());
		master.setEventTime(breedingEvent.getBreedingDate());
		master.setBreedingEventId(breedingEventId);
		eventMasterDao.insertEntryEventDetails(master);
		
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
			else  
			{
				breedingEventList =  breedingEventDao.getBreedingEventInformationByServiceId(breedingEventDto.getSearchText(), breedingEventDto.getCompanyId());
			}
			breedingEventDtoList = builder.convertToDtos(breedingEventList);
			
			for(BreedingEventDto breedingEvent_Dto : breedingEventDtoList)
			{
				EmployeeGroupDto employeeGroup = employeeGroupDao.getEmployeeGroup(breedingEvent_Dto.getEmployeeGroupId());
				breedingEvent_Dto.setEmployeeGroup(employeeGroup);
				
				PigInfo pigInfo = pigInfoDao.getPigInformationById(breedingEvent_Dto.getPigInfoKey());
				breedingEvent_Dto.setPigInfoId(pigInfo.getPigId());
								
				breedingEvent_Dto.setBreedingServiceType(refDataCache.getBreedingServiceTypeMap(breedingEventDto.getLanguage()).get(breedingEvent_Dto.getBreedingServiceTypeId()));
				
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
			breedingEventDao.deleteBreedingEventInfo(id);
		}
	}
	
	@Override
	public int validateBreedingEvent(BreedingEventDto breedingEventDto)
	{
		try {
			logger.info("values : "+breedingEventDto.getPigInfoId()+"/"+breedingEventDto.getCompanyId());
			PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(breedingEventDto.getPigInfoId(), breedingEventDto.getCompanyId());
			if(pigInfo != null)
				breedingEventDto.setPigInfoKey(pigInfo.getId());
			
			return validationObj.validate(breedingEventDto);
		}
		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		catch (PigTraxException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public BreedingEventDto getBreedingEventInformation(Integer breedingEventId) throws PigTraxException {
		BreedingEvent breedingEvent;
		try {
			breedingEvent = breedingEventDao.getBreedingEventInformation(breedingEventId);
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		} 
		return builder.convertToDto(breedingEvent);
	}
	
	@Override
	public BreedingEventDto checkForBreedingServiceId(String pigId, String serviceId,
			int companyId) throws PigTraxException {
		try {
			BreedingEvent breedingEvent =  breedingEventDao.checkForBreedingServiceId(pigId, serviceId, companyId);
			if(breedingEvent != null)
				return builder.convertToDto(breedingEvent);
			return null;
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
	
}
