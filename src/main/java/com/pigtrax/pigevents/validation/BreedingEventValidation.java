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

@Component
//@PropertySource("file:src/main/resources/PigTraxValidation.properties")
//@PropertySource("/WEB-INF/classes/PigTraxValidation.properties")
@PropertySource("classpath:PigTraxValidation.properties")
public class BreedingEventValidation {
	
	private static final Logger logger = Logger.getLogger(BreedingEventValidation.class);
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	PregnancyEventDao pregnancyEventDao;
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
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
  public String validate(BreedingEventDto breedingEventDto) throws PigTraxException
  {
	  
	  init();
	  
	  Integer pigInfoKey = breedingEventDto.getPigInfoKey();
	  Integer entryEventId = null;
	  Integer breedingEventId = null;
	  Integer pregnancyEventId = null;
	  
	  DateTime lastPregnancyExamDate = null;
	  DateTime lastPregnancyResultDate = null;
	  DateTime lastBreedingEventDate = null;
	  DateTime fromDateToCompare = null;
	  int durationDays = 0;
	  
	  PigInfo pigInfo = null;
	  
	  DateTime currentBreedingEventDate = new DateTime(breedingEventDto.getServiceStartDate());
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
	 
		
		
	  if(pigBirthDate != null && currentBreedingEventDate.toLocalDate().compareTo(pigBirthDate.toLocalDate()) != 1) 
	  {
		  return ERR_CODE_BIRTH_DATE;
	  }
	  if(pigEntryDate != null && currentBreedingEventDate.toLocalDate().compareTo(pigEntryDate.toLocalDate()) != 1)
	  {
		  return ERR_CODE_ENTRY_DATE;
	  }
	   if(eventMasterList == null)
	  {
		  logger.info("Event master record not found");
		  return ERR_CODE_NO_EVENT_MASTER;
	  }
	  else
	  {
		  breedingEventId = findLastBreedingEventId(pigInfoKey);
		  
		  List<PregnancyEvent> pregnancyEvents =  pregnancyEventDao.getPregnancyEvents(breedingEventId);
		  
		  boolean flag = checkIfPregnant(pregnancyEvents);
		  
		  
		  
		  pregnancyEventId = findLastPregnancyEventId(pregnancyEvents);		  
		  
		  
		  logger.info("pregnancyEventId/breedingEventId " +pregnancyEventId+"/"+breedingEventId);
		  
		  if(breedingEventId == -1)
		  {
			  logger.info("This is the first breeding event");
			  return SUCCESS_CODE; 
		  }
		  else
		  {
			  entryEventId = pigInfoKey;
			  //Find out the last breeding Date
			  BreedingEvent breedingEvent = null;
				try {
					breedingEvent = breedingEventDao.getBreedingEventInformation(breedingEventId);
				} catch (SQLException e) {
					throw new PigTraxException(e.getMessage(), "");
				}
			  if(breedingEvent != null)
				  lastBreedingEventDate = new DateTime(breedingEvent.getServiceStartDate());			  
			  logger.info("last breeding event date : "+lastBreedingEventDate);
			  
			 
				  fromDateToCompare = lastBreedingEventDate;
				  durationDays = Days.daysBetween(fromDateToCompare.withTimeAtStartOfDay() , currentBreedingEventDate.withTimeAtStartOfDay() ).getDays() ;
				  if(durationDays <= 0)
				  {
					  logger.info("Breeding date earlier than gestation record");
					  return ERR_CODE_04;
				  }
				  else if(durationDays > 1 && durationDays <= BREEDING_EVENT_TIME_PERIOD1)
				  {
					  logger.info("Breeding happens within first 5 days");
					  return WARN_CODE_01;
				  }
				  else if(durationDays >= BREEDING_EVENT_TIME_PERIOD1 && durationDays <= BREEDING_EVENT_TIME_PERIOD2)
				  {
				
						logger.info("Breeding happens between 6 to 18 days");
						return ERR_CODE_01;
						
				  }
				  else if(durationDays > BREEDING_EVENT_TIME_PERIOD2 && durationDays <= BREEDING_EVENT_TIME_PERIOD3)
				  {
					  
					  if(flag)
					  {
						  logger.info("Breeding happens between 19 to 60 days and the pig is pregnant");
						  return ERR_CODE_02;						
					  }
					  else 
					  {
						  if(pregnancyEvents != null && pregnancyEvents.size() > 0)
						  {
							  logger.info("Breeding happens between 19 to 60 days and the pig is not pregnant");
							  return WARN_CODE_02;
						  }
						  else
						  {
					  
							  logger.info("Breeding happens between 19 to 60 days and no pregnacy check done");
							  return ERR_CODE_03;
						  }
				  }			   
			    }
		      else   if(durationDays > BREEDING_EVENT_TIME_PERIOD4)
			  {
					  return SUCCESS_CODE; 
			  }
				  
		  }
	  }
	  
	  return SUCCESS_CODE;
	 
  }
  
  
  /**
   * Find the last pregnancy event Id for a given 
   * list of events of a specific pig
   * @param eventMasterList
   * @return
   */
  private int findLastPregnancyEventId(List<PregnancyEvent> pregnancyEvents )
  {
	  if(pregnancyEvents != null  && pregnancyEvents.size() > 0)
	  {
		  return pregnancyEvents.get(0).getId();
	  }
	  
	  return -1;
  }
  
  /**
   * Check the status of pregnacy event
   * @param events
   * @return
   */
  private boolean checkIfPregnant(List<PregnancyEvent> events)
  {  
	  if(events != null)
	  {
		  for(PregnancyEvent event : events)
		  {
			  if(1 == event.getPregnancyEventTypeId() && 1 == event.getPregnancyExamResultTypeId())
				  return true;				  
		  }
	  }
	  return false;
  }
  
  /**
   * Find the last breeding event Id for a given 
   * list of events of a specific pig
   * @param eventMasterList
   * @return
   */
  private int findLastBreedingEventId(Integer pigInfoId)
  {
	 
	  return -1;
  }
}
