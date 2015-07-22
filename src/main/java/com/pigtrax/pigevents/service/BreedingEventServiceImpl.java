package com.pigtrax.pigevents.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dto.BreedingEventBuilder;
import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.dto.PigInfoBuilder;
import com.pigtrax.pigevents.dto.PigInfoDto;
import com.pigtrax.pigevents.service.interfaces.BreedingEventService;
import com.pigtrax.pigevents.service.interfaces.PigInfoService;

@Repository
public class BreedingEventServiceImpl implements BreedingEventService {
	private static final Logger logger = Logger.getLogger(BreedingEventServiceImpl.class);
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	BreedingEventBuilder builder;
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	@Override
	public int saveBreedingEventInformation(BreedingEventDto dto)
			throws Exception {
		BreedingEvent breedingEvent = builder.convertToBean(dto);
		
			try{
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
		return dto;
	}
	
	public void deleteBreedingEventInfo(Integer id) throws Exception {
		if(id != null)
		{
			breedingEventDao.deleteBreedingEventInfo(id);
		}
	}
}
