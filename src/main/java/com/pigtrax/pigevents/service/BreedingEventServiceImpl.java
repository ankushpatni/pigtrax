package com.pigtrax.pigevents.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
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
					throw new PigTraxException("Duplicate Key Exception occured. Please check Service Id", "");
			}
	}
	
	@Transactional("ptxJTransactionManager")
	private int addBreedingEventInformation(BreedingEvent breedingEvent) throws SQLException
	{
		int breedingEventId = breedingEventDao.addBreedingEventInformation(breedingEvent);
				
		PigTraxEventMaster master = new PigTraxEventMaster();
		breedingEvent.setId(breedingEventId);
		eventMasterDao.updateBreedingEventDetails(breedingEvent);
		return breedingEventId;		
	}
	
	@Override
	public BreedingEventDto getBreedingEventInformation(String serviceId, Integer companyId)
			throws Exception {
		BreedingEvent breedingEvent = breedingEventDao.getBreedingEventInformation(serviceId, companyId);
		BreedingEventDto dto = builder.convertToDto(breedingEvent);
		
		if(dto != null)
		{
			EmployeeGroupDto employeeGroup = employeeGroupDao.getEmployeeGroup(breedingEvent.getEmployeeGroupId());
			dto.setEmployeeGroup(employeeGroup);
			
			PigInfo pigInfo = pigInfoDao.getPigInformationById(dto.getPigInfoKey());
			dto.setPigInfoId(pigInfo.getPigId());
			
		}
		
		return dto;
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
			return validationObj.validate(breedingEventDto);
		} catch (PigTraxException e) {
			return 1;
		}
	}
}
