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
import com.pigtrax.master.dto.EmployeeGroupDto;
import com.pigtrax.master.service.interfaces.EmployeeGroupService;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PregnancyEventDao;
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
	EmployeeGroupService employeeGroupService;
	
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
	
	
	@Override
	public List<PregnancyEventDto> getPregnancyEvents(String pigId,
			Integer companyId) throws PigTraxException { 
		
		List<PregnancyEvent> pregnancyEvents = null;
		List<PregnancyEventDto> pregnancyEventDtoList = new ArrayList<PregnancyEventDto>();
		try {
			pregnancyEvents = pregnancyEventDao.getPregnancyEvents(pigId, companyId);
			pregnancyEventDtoList =  builder.convertToDtos(pregnancyEvents);
			
			for(PregnancyEventDto dto : pregnancyEventDtoList)
			{
				//Get the employee group details
				EmployeeGroupDto empGrpDto = employeeGroupService.getEmployeeGroup(dto.getEmployeeGroupId());
				dto.setEmployeeGroup(empGrpDto);
				
				//Get the pig id for a given pigIdInfo
				PigInfo pigInfo = pigInfoDao.getPigInformationById(dto.getPigInfoId());
				dto.setPigId(pigInfo.getPigId());				
			}
			
			return pregnancyEventDtoList;
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
	/**
	 * Delete a pregnancy event based on the primary key ID
	 */
	@Override
	public void deletePregnancyEvent(Integer pregnancyEventId)
			throws PigTraxException {
		try{
			pregnancyEventDao.deletePregnancyEvent(pregnancyEventId);
		}
		catch(SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}
	
}
