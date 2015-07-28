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
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PregnancyEventDao;
import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.dto.PregnancyEventBuilder;
import com.pigtrax.pigevents.dto.PregnancyEventDto;
import com.pigtrax.pigevents.service.interfaces.PregnancyEventService;

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
	EmployeeGroupDao employeeGroupDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	
	@Override
	public int savePregnancyEventInformation(PregnancyEventDto pregnancyEventDto)
			throws PigTraxException {
		try{
			PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(pregnancyEventDto.getPigId(), pregnancyEventDto.getCompanyId());
			if(pigInfo != null)
				pregnancyEventDto.setPigInfoId(pigInfo.getId());
			
			PregnancyEvent pregnancyEvent = builder.convertToBean(pregnancyEventDto);
			
			if(pregnancyEventDto.getId() == null)
			{
			   return addPregnancyEventInformation(pregnancyEvent);
			}
			else
			{
				return pregnancyEventDao.updatePregnancyEventDetails(pregnancyEvent); 
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
	private int addPregnancyEventInformation(PregnancyEvent pregnancyEvent) throws SQLException
	{
		int pergnancyEventId = pregnancyEventDao.addPregnancyEventdDetails(pregnancyEvent);
				
		PigTraxEventMaster master = new PigTraxEventMaster();
		pregnancyEvent.setId(pergnancyEventId);
		eventMasterDao.updatePregnancyEventDetails(pregnancyEvent); 
		return pergnancyEventId;		
	}
	
	
}
