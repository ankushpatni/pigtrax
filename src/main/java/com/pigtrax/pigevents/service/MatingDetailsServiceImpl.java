package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.MatingDetails;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
import com.pigtrax.pigevents.dao.interfaces.MatingDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PregnancyEventDao;
import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.dto.MatingDetailsBuilder;
import com.pigtrax.pigevents.dto.MatingDetailsDto;
import com.pigtrax.pigevents.service.interfaces.BreedingEventService;
import com.pigtrax.pigevents.service.interfaces.MatingDetailsService;


@Repository
public class MatingDetailsServiceImpl implements MatingDetailsService {
	
	@Autowired
	MatingDetailsDao matingDetailsDao;
	
	@Autowired
	BreedingEventDao breedingEventDao;

	@Autowired
	BreedingEventService breedingEventService;
	
	@Autowired
	PregnancyEventDao pregnancyEventDao;
	
	@Autowired 
	MatingDetailsBuilder builder;
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
   @Override
	public MatingDetailsDto saveMatingDetails(MatingDetailsDto matingDetailsDto) throws PigTraxException {

	   MatingDetails matingDetails = builder.convertToBean(matingDetailsDto);

	   if(matingDetailsDto.getMatingDetailId() == null)
	   {
		   int matingDetailId = matingDetailsDao.addMatingDetails(matingDetails);
		   matingDetailsDto.setMatingDetailId(matingDetailId);
	   }
	   else
	   {
		   matingDetailsDao.updateMatingDetails(matingDetails); 
	   }
	   
	   try {
		   boolean eventMasterTag = false;
			BreedingEvent breedingEvent = breedingEventDao.getBreedingEventInformation(matingDetailsDto.getBreedingEventId());
			if(breedingEvent != null)
			{
			   //update serviceStartdate
				if(breedingEvent.getServiceStartDate() == null)
				{
				   breedingEventDao.updateServiceStartDate(matingDetailsDto.getMatingDate(), matingDetailsDto.getBreedingEventId());
				   eventMasterTag = true;
				   	PigTraxEventMaster master = new PigTraxEventMaster();
					master.setPigInfoId(breedingEvent.getPigInfoId());
					master.setUserUpdated(matingDetailsDto.getUserUpdated());
					master.setBreedingEventId(matingDetailsDto.getBreedingEventId());
					master.setEventTime(matingDetailsDto.getMatingDate());
					eventMasterDao.insertEntryEventDetails(master);
				}
				else if(breedingEvent.getServiceStartDate() != null)
				{
					
					List<MatingDetails> matingList = matingDetailsDao.getMatingDetails(breedingEvent.getId());
					
					DateTime serviceStartDate = new DateTime(breedingEvent.getServiceStartDate());
					DateTime matingDate = new DateTime(matingDetailsDto.getMatingDate());
					
					if(matingDate.isBefore(serviceStartDate))
					{
						breedingEventDao.updateServiceStartDate(matingDetailsDto.getMatingDate(), matingDetailsDto.getBreedingEventId());
						eventMasterTag = true;
					}
					else if(matingList != null)
					{
						MatingDetailsDto nextServiceMatingDetail = findServiceMateDetails(matingDetailsDto.getBreedingEventId());
						if(!serviceStartDate.toLocalDate().equals(matingDate.toLocalDate()))
						{
							if(nextServiceMatingDetail != null && nextServiceMatingDetail.getMatingDetailId() > 0)
								   breedingEventDao.updateServiceStartDate(nextServiceMatingDetail.getMatingDate(), matingDetailsDto.getBreedingEventId());
							else
								   breedingEventDao.updateServiceStartDate(null, matingDetailsDto.getBreedingEventId());
							eventMasterTag = true;
						}
					}
					
					if(eventMasterTag)
					{
						PigTraxEventMaster master = new PigTraxEventMaster();
						master.setPigInfoId(breedingEvent.getPigInfoId());
						master.setUserUpdated(matingDetailsDto.getUserUpdated());
						master.setBreedingEventId(matingDetailsDto.getBreedingEventId());
						master.setEventTime(matingDetailsDto.getMatingDate());
						eventMasterDao.updateBreedingEventMasterDetails(master);
					}
					
				}
					
			}
				
			
			
		} catch (SQLException e) {
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	   
		return matingDetailsDto;
	}
   
   @Override
	public MatingDetailsDto deleteMatingDetails(MatingDetailsDto matingDetailsDto) throws PigTraxException { 
		
	   try{
		   
		   boolean isDeletable = true;
	   
		   BreedingEvent breedingEvent = breedingEventDao.getBreedingEventInformation(matingDetailsDto.getBreedingEventId());
		   
		   DateTime serviceStartDate = null;
		   DateTime matingDate = null;
		   if(breedingEvent.getServiceStartDate() != null)
			   serviceStartDate = new DateTime(breedingEvent.getServiceStartDate());
		   if(matingDetailsDto.getMatingDate() != null )
			   matingDate = new DateTime(matingDetailsDto.getMatingDate());
		   
		   if(breedingEvent.getServiceStartDate() != null && serviceStartDate.toLocalDate().equals(matingDate.toLocalDate()))
		   {
			   
			  
			   List<PregnancyEvent> pregnancyEvents = pregnancyEventDao.getPregnancyEvents(breedingEvent.getId());
			   if(pregnancyEvents != null && pregnancyEvents.size() > 0)
			   {
				   isDeletable = false;
				   throw new PigTraxException("PREGCHECK-TRUE"); 
			   }
			   else
				   isDeletable = true;
		   }
		   else
			   isDeletable = true;
		   
		   if(isDeletable)
		   {    
			   
			   int rowsDeleted = matingDetailsDao.deleteMatingDetails(matingDetailsDto.getMatingDetailId());
			   if(rowsDeleted > 0 && serviceStartDate != null && serviceStartDate.toLocalDate().equals(matingDate.toLocalDate()))
			   {
				   MatingDetailsDto nextServiceMatingDetail = findServiceMateDetails(matingDetailsDto.getBreedingEventId());
				   if(nextServiceMatingDetail != null && nextServiceMatingDetail.getMatingDetailId() > 0)
					   breedingEventDao.updateServiceStartDate(nextServiceMatingDetail.getMatingDate(), matingDetailsDto.getBreedingEventId());
				   else
					   breedingEventDao.updateServiceStartDate(null, matingDetailsDto.getBreedingEventId());
			   }
		   }
	   
	   }
	   catch(SQLException sqlEx)
	   {
		   throw new PigTraxException(sqlEx.getMessage(), sqlEx.getSQLState());
	   }
	   
		return matingDetailsDto;
	}
   
   private MatingDetailsDto findServiceMateDetails(Integer breedingEventId) throws PigTraxException
   {
	   BreedingEventDto breedingEventDto = breedingEventService.getBreedingEventInformation(breedingEventId);
	   
	   MatingDetailsDto nextMatingDetailsDto = null;
	   DateTime nextMatingDate = null;
	   
	   List<MatingDetailsDto> matingDetailsList = breedingEventDto.getMatingDetailsList();
	   if(matingDetailsList != null && matingDetailsList.size() >0 )
	   {
		   for(MatingDetailsDto matingDetailsDto : matingDetailsList)
		   {
			   DateTime matingDate = new DateTime(matingDetailsDto.getMatingDate()); 
			   if(nextMatingDate == null || matingDate.isBefore(nextMatingDate))
			   {
				   nextMatingDate = matingDate;
				   nextMatingDetailsDto = matingDetailsDto;
			   }
		   }
	   }
	   
	   return nextMatingDetailsDto;
   }
}
