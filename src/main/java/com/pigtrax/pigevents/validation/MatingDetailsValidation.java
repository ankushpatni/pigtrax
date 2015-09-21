package com.pigtrax.pigevents.validation;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PregnancyEventDao;
import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.dto.MatingDetailsDto;
import com.pigtrax.pigevents.service.interfaces.BreedingEventService;

@Component
//@PropertySource("file:src/main/resources/PigTraxValidation.properties")
//@PropertySource("/WEB-INF/classes/PigTraxValidation.properties")
@PropertySource("classpath:PigTraxValidation.properties")
public class MatingDetailsValidation {
	
	private static final Logger logger = Logger.getLogger(MatingDetailsValidation.class);
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	PregnancyEventDao pregnancyEventDao;
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	BreedingEventService breedingEventService;
	
	@Autowired
	private Environment env;
	
	private int BREEDING_EVENT_TIME_PERIOD1;
	private int BREEDING_EVENT_TIME_PERIOD2;
	private int BREEDING_EVENT_TIME_PERIOD3;
	private int BREEDING_EVENT_TIME_PERIOD4;
	
	private String SUCCESS_CODE = "SUCCESS-00";
	private String ERR_CODE_NO_EVENT_MASTER = "ERR+01";
	private String WARN_CODE_01 = "WARN-01";
	private String WARN_CODE_02 = "WARN-02";
	private String ERR_CODE_01 = "ERR-01";
	private String ERR_CODE_02 = "ERR-02";
	private String ERR_CODE_03 = "ERR-03";
	private String ERR_CODE_04 = "ERR-04";
	private String ERR_CODE_BIRTH_DATE = "ERR_BIRTHDATE_NOT_MATCHING";
	private String ERR_CODE_ENTRY_DATE = "ERR_ENTRYDATE_NOT_MATCHING";
	private String ERR_CODE_DUPLICATE_DATE = "ERR_CODE_DUPLICATE_DATE";
	private String ERR_CODE_PREG_CHECK_ADDED = "ERR_CODE_PREG_CHECK_ADDED";
	
	/**
	 * Load the property values
	 */
	private void init()
	{
		String period = env.getProperty("BREEDING_EVENT_TIME_PERIOD1");		
		BREEDING_EVENT_TIME_PERIOD1 = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("BREEDING_EVENT_TIME_PERIOD2");		
		BREEDING_EVENT_TIME_PERIOD2 = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("BREEDING_EVENT_TIME_PERIOD3");		
		BREEDING_EVENT_TIME_PERIOD3 = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("BREEDING_EVENT_TIME_PERIOD4");		
		BREEDING_EVENT_TIME_PERIOD4 = Integer.parseInt((period != null) ? period.trim() : "0");
		
		logger.info(BREEDING_EVENT_TIME_PERIOD1+"--"+BREEDING_EVENT_TIME_PERIOD2+"--"+BREEDING_EVENT_TIME_PERIOD3+"--"+BREEDING_EVENT_TIME_PERIOD4);
	} 
	
  /**
   * Business validation method
   * @param breedingEventDto
   * @return
   */
  public String validate(MatingDetailsDto matingDetailsDto) throws PigTraxException
  {
	  
	  init();
	  
	  BreedingEventDto breedingEventDto = breedingEventService.getBreedingEventInformation(matingDetailsDto.getBreedingEventId());
	  
	  List<MatingDetailsDto> matingDetailsList = null;
	  
	  if(breedingEventDto != null)
		  matingDetailsList = breedingEventDto.getMatingDetailsList();
	  
	  Integer pigInfoKey = breedingEventDto.getPigInfoKey();
	  Integer breedingEventId = matingDetailsDto.getBreedingEventId();
	  
	  int durationDays = 0;
	  
	  PigInfo pigInfo = null;
	  
	  DateTime currentBreedingEventDate = null;
	  
	  if(breedingEventDto.getServiceStartDate() != null) currentBreedingEventDate = new DateTime(breedingEventDto.getServiceStartDate());
	  DateTime currentMatingDate = new DateTime(matingDetailsDto.getMatingDate());
	  List<PregnancyEvent> events = pregnancyEventDao.getPregnancyEvents(breedingEventId);
	  
	  
	  
	  DateTime pigBirthDate = null;
	  DateTime pigEntryDate = null;
	  
	  List<PigTraxEventMaster> eventMasterList = null;
		try {
			eventMasterList = eventMasterDao.getEventMasterRecords(pigInfoKey); 
			pigInfo = pigInfoDao.getPigInformationById(pigInfoKey);
			if(pigInfo.getBirthDate() != null) 
				pigBirthDate = new DateTime(pigInfo.getBirthDate());
			pigEntryDate = new DateTime(pigInfo.getEntryDate());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PigTraxException(e.getMessage(),"");
		}
	 
		
		 if(pigBirthDate != null && currentMatingDate.toLocalDate().compareTo(pigBirthDate.toLocalDate()) != 1) 
		  {
			  return ERR_CODE_BIRTH_DATE;
		  }
		  else if(pigEntryDate != null && currentMatingDate.toLocalDate().compareTo(pigEntryDate.toLocalDate()) != 1)
		  {
			  return ERR_CODE_ENTRY_DATE;
		  }	  
		
		  else if(currentBreedingEventDate == null)
		  {  
				 return SUCCESS_CODE;
		  }
	  else if(currentMatingDate.toLocalDate().equals(currentBreedingEventDate.toLocalDate()))
	  {
		  return ERR_CODE_DUPLICATE_DATE;
	  }
	  else if(currentMatingDate.toLocalDate().isBefore(currentBreedingEventDate.toLocalDate()) && events != null)
	  {
		  return ERR_CODE_PREG_CHECK_ADDED;
	  }	 
	 
	  
	  if(matingDetailsList != null && matingDetailsList.size() > 0)
	  {
		  for(MatingDetailsDto matingDto : matingDetailsList)
		  {
			  DateTime serviceDate = new DateTime(matingDto.getMatingDate());
			  
			  if(currentMatingDate.toLocalDate().equals(serviceDate.toLocalDate()))
				  return ERR_CODE_DUPLICATE_DATE;
		  }
	  }
	  
	   if(eventMasterList == null)
	  {
		  logger.info("Event master record not found");
		  return ERR_CODE_NO_EVENT_MASTER;
	  }
	  else
	  {		 
		 durationDays = Days.daysBetween(currentBreedingEventDate, currentMatingDate).getDays();
			 if(durationDays > BREEDING_EVENT_TIME_PERIOD4)
				 return SUCCESS_CODE;
			 else if(durationDays > BREEDING_EVENT_TIME_PERIOD3 && durationDays <= BREEDING_EVENT_TIME_PERIOD4)
				 {					 
					  
					  if(events != null && events.size() > 0)
					  {
						boolean pregnancyStatus =   checkPregnancyStatus(events);
						if(pregnancyStatus)
							return ERR_CODE_02;
						else
							return SUCCESS_CODE;
					  }		
					  else
						 return ERR_CODE_03;
				 }
			 else if(durationDays > BREEDING_EVENT_TIME_PERIOD2 && durationDays < BREEDING_EVENT_TIME_PERIOD3)
			 {
				 events = pregnancyEventDao.getPregnancyEvents(breedingEventId);
				 
				  if(events != null && events.size() > 0)
				  {
					boolean pregnancyStatus =   checkPregnancyStatus(events);
					if(pregnancyStatus)
						return ERR_CODE_02;
					else
						return SUCCESS_CODE;
				  }				 
				  else
				  {
					  return ERR_CODE_03;
				  }
			 }			 
			 else if(durationDays >= BREEDING_EVENT_TIME_PERIOD1 && durationDays <= BREEDING_EVENT_TIME_PERIOD2)
			  {			
					logger.info("Breeding happens between 6 to 18 days");
					return ERR_CODE_01;					
			  }
			 else if(durationDays >= 1 && durationDays < BREEDING_EVENT_TIME_PERIOD1)
			  {
				  logger.info("Breeding happens within first 5 days");
				  return WARN_CODE_01;
			  }
			 else
				 return SUCCESS_CODE;
	  }  
	 
  }
  
  
  private boolean checkPregnancyStatus(List<PregnancyEvent> events)
  {
	  if(events != null)
	  {
		  for(PregnancyEvent event : events)
		  {
			  if(event.getPregnancyEventTypeId() == 1 && event.getPregnancyExamResultTypeId() == 1)
				  return true;
			  else if(event.getPregnancyEventTypeId() == 1 &&  (event.getPregnancyExamResultTypeId() == 2 || event.getPregnancyExamResultTypeId()  == 3))
				  return false;
		  }
	  }
	  return false;
  }
  
  
  
}
