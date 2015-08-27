package com.pigtrax.pigevents.validation;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PregnancyEventDao;
import com.pigtrax.pigevents.dto.PigletStatusEventDto;

@Component
@PropertySource("classpath:PigTraxValidation.properties")
public class PigletStatusEventValidation {
	
	private static final Logger logger = Logger.getLogger(PigletStatusEventValidation.class);
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	PregnancyEventDao pregnancyEventDao;
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	private Environment env;
	
	private int PIGLET_STATUS_FARROW_START_DURATION;
	private int PIGLET_STATUS_FARROW_END_DURATION;
	private int PIGLET_STATUS_FOSTERIN_START_DURATION;
	private int PIGLET_STATUS_FOSTERIN_END_DURATION;
	
	private int SUCCESS_CODE = 0;
	//private int ERR_CODE_NO_EVENT_MASTER = -1;
	private int ERR_CODE_01 = 1;
	private int ERR_CODE_02 = 2;
	private int ERR_CODE_03 = 3;
//	private int ERR_CODE_04 = 4;
	
	/**
	 * Load the property values
	 */
	private void init()
	{
		String period = env.getProperty("PIGLET_STATUS_FARROW_START_DURATION");		
		PIGLET_STATUS_FARROW_START_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("PIGLET_STATUS_FARROW_END_DURATION");		
		PIGLET_STATUS_FARROW_END_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("PIGLET_STATUS_FOSTERIN_START_DURATION");		
		PIGLET_STATUS_FOSTERIN_START_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("PIGLET_STATUS_FOSTERIN_END_DURATION");		
		PIGLET_STATUS_FOSTERIN_END_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		logger.info(PIGLET_STATUS_FARROW_START_DURATION+"--"+PIGLET_STATUS_FARROW_END_DURATION+"--"+PIGLET_STATUS_FOSTERIN_START_DURATION+"--"+PIGLET_STATUS_FOSTERIN_END_DURATION);
	} 
	
  /**
   * Business validation method
   * @param pigletStatusEventDto
   * @return SUCCESS_CODE
   */
  public int validate(PigletStatusEventDto pigletStatusEventDto) throws PigTraxException
  {
	  
	  init();
	  
	  int liveBorn = pigletStatusEventDto.getFarrowEventDto().getLiveBorns();
	  int weanPigNum = pigletStatusEventDto.getWeanPigNum() !=null?pigletStatusEventDto.getWeanPigNum().intValue():0;
	  int fosterPigNum = pigletStatusEventDto.getFosterPigNum() !=null?pigletStatusEventDto.getFosterPigNum().intValue():0;
	  int deathPigNum = pigletStatusEventDto.getDeathPigNum() !=null?pigletStatusEventDto.getDeathPigNum().intValue():0;
	  int totalpigs = weanPigNum + fosterPigNum + deathPigNum;
	  
	  if(totalpigs != liveBorn) {
		  logger.info("Pig count does not match with the farrow event");
		  return ERR_CODE_03;
	  }
	  
	  int durationDays = 0;	  
	  DateTime farrowEventDate = new DateTime(pigletStatusEventDto.getFarrowEventDto().getFarrowDateTime());
	  DateTime weanEventDate = new DateTime(pigletStatusEventDto.getEventDateTime());
	  durationDays = Days.daysBetween(farrowEventDate,weanEventDate).getDays() ;	
	  
	  if(weanEventDate.isBefore(farrowEventDate) || durationDays > PIGLET_STATUS_FARROW_END_DURATION || durationDays< PIGLET_STATUS_FARROW_START_DURATION)
	  {
		  logger.info("Weaning should happen within 0-60 days of farrow event");
		  return ERR_CODE_01;
	  } 
	  
	  if(pigletStatusEventDto.getFosterToDateTime() != null){
			DateTime fosterinEventDate = new DateTime(pigletStatusEventDto.getFosterToDateTime());
			durationDays = Days.daysBetween(fosterinEventDate,weanEventDate).getDays() ;
			if(weanEventDate.isBefore(fosterinEventDate) || durationDays > PIGLET_STATUS_FOSTERIN_END_DURATION || durationDays< PIGLET_STATUS_FOSTERIN_START_DURATION){
				logger.info("Weaning should happen within 0-50 days of farrow event");
			return ERR_CODE_02;
		 }
		  
	  }
	  return SUCCESS_CODE;	 
  }
  
}
