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
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
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
	private Environment env;
	
	private int BREEDING_EVENT_TIME_PERIOD1;
	private int BREEDING_EVENT_TIME_PERIOD2;
	private int BREEDING_EVENT_TIME_PERIOD3;
	private int BREEDING_EVENT_TIME_PERIOD4;
	
	private int SUCCESS_CODE = 0;
	private int ERR_CODE_NO_EVENT_MASTER = -1;
	private int ERR_CODE_01 = 1;
	private int ERR_CODE_02 = 2;
	private int ERR_CODE_03 = 3;
	private int ERR_CODE_04 = 4;
	
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
  public int validate(BreedingEventDto breedingEventDto) throws PigTraxException
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
	  
	  DateTime currentBreedingEventDate = new DateTime(breedingEventDto.getBreedingDate());
	  
	  
	  List<PigTraxEventMaster> eventMasterList = null;
		try {
			eventMasterList = eventMasterDao.getEventMasterRecords(pigInfoKey); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new PigTraxException(e.getMessage(),"");
		}
	  if(eventMasterList == null)
	  {
		  logger.info("Event master record not found");
		  return ERR_CODE_NO_EVENT_MASTER;
	  }
	  else
	  {
		  pregnancyEventId = findLastPregnancyEventId(eventMasterList);		  
		  breedingEventId = findLastBreedingEventId(eventMasterList);
		  
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
				  lastBreedingEventDate = new DateTime(breedingEvent.getBreedingDate());			  
			  logger.info("last breeding event date : "+lastBreedingEventDate);
			  
			  if(pregnancyEventId == -1)
			  {
				  
				  fromDateToCompare = lastBreedingEventDate;
				  durationDays = Days.daysBetween(fromDateToCompare.withTimeAtStartOfDay() , currentBreedingEventDate.withTimeAtStartOfDay() ).getDays() ;	
				  if(durationDays <= BREEDING_EVENT_TIME_PERIOD1)
				  {
					  logger.info("Breeding happens within first 5 days");
					  return ERR_CODE_01;
				  }
				  else if(durationDays >= BREEDING_EVENT_TIME_PERIOD1 && durationDays <= BREEDING_EVENT_TIME_PERIOD2)
				  {
					  logger.info("Breeding happens between 6 to 14 days");
					  return ERR_CODE_02;
				  }
				  else if(durationDays > BREEDING_EVENT_TIME_PERIOD2 && durationDays <= BREEDING_EVENT_TIME_PERIOD3)
				  {
					  logger.info("Breeding happens between 14 to 60 days");
					  return ERR_CODE_03;
				  }
			  }
			  //Checking if there is a pregnancy Event
			  else if(pregnancyEventId != -1)
			  {
				  
				  PregnancyEvent pregnancyEvent = pregnancyEventDao.getPregnancyEvent(pregnancyEventId);
				  lastPregnancyExamDate = new DateTime(pregnancyEvent.getExamDate());
				  lastPregnancyResultDate = new DateTime(pregnancyEvent.getResultDate());
				  logger.info("pregnancyEventId not null : "+lastPregnancyExamDate+"//"+pregnancyEventId);
				  
				  
				  if(lastPregnancyExamDate.isBefore(lastBreedingEventDate))
				  {
					  fromDateToCompare = lastBreedingEventDate;
					  durationDays = Days.daysBetween(fromDateToCompare.withTimeAtStartOfDay() , currentBreedingEventDate.withTimeAtStartOfDay() ).getDays() ;	
					  if(durationDays <= BREEDING_EVENT_TIME_PERIOD1)
					  {
						  logger.info("Breeding happens within first 5 days");
						  return ERR_CODE_01;
					  }
					  else if(durationDays >= BREEDING_EVENT_TIME_PERIOD1 && durationDays <= BREEDING_EVENT_TIME_PERIOD2)
					  {
						  logger.info("Breeding happens between 6 to 14 days");
						  return ERR_CODE_02;
					  }
					  else if(durationDays > BREEDING_EVENT_TIME_PERIOD2 && durationDays <= BREEDING_EVENT_TIME_PERIOD3)
					  {
						  logger.info("Breeding happens between 14 to 60 days");
						  return ERR_CODE_03;
					  }
				  }
				  else 
				  {
					  fromDateToCompare = lastPregnancyExamDate;
					  logger.info("Comparing with pregnancy date");
					  durationDays = Days.daysBetween(fromDateToCompare.withTimeAtStartOfDay() , currentBreedingEventDate.withTimeAtStartOfDay() ).getDays() ;		  
					  if(durationDays <= BREEDING_EVENT_TIME_PERIOD3)
					  {
						  logger.info("Breeding happens btween 130 days of the pregnancy");
						   return ERR_CODE_04;
					  }
					  else
					  {
						  logger.info("Perfect time");
						  return SUCCESS_CODE;
					  }
				  }	  
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
  private int findLastPregnancyEventId(List<PigTraxEventMaster> eventMasterList)
  {
	  if(eventMasterList != null)
	  {
		  for(PigTraxEventMaster eventMaster : eventMasterList)
		  {
			  if(eventMaster.getPregnancyEventId() != null && eventMaster.getPregnancyEventId() > 0)
			  {
				  return eventMaster.getPregnancyEventId();
			  }
		  }
	  }
	  
	  return -1;
  }
  
  /**
   * Find the last breeding event Id for a given 
   * list of events of a specific pig
   * @param eventMasterList
   * @return
   */
  private int findLastBreedingEventId(List<PigTraxEventMaster> eventMasterList)
  {
	  if(eventMasterList != null)
	  {
		  for(PigTraxEventMaster eventMaster : eventMasterList)
		  {
			  if(eventMaster.getBreedingEventId() != null && eventMaster.getBreedingEventId() > 0)
			  {
				  return eventMaster.getBreedingEventId();
			  }
		  }
	  }
	  
	  return -1;
  }
}
