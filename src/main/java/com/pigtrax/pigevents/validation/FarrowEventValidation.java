package com.pigtrax.pigevents.validation;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.dto.FarrowEventDto;

@Component
//@PropertySource("file:src/main/resources/PigTraxValidation.properties")
//@PropertySource("/WEB-INF/classes/PigTraxValidation.properties")
@PropertySource("classpath:PigTraxValidation.properties")
public class FarrowEventValidation {
	
	private static final Logger logger = Logger.getLogger(FarrowEventValidation.class);
	
		
	@Autowired
	private Environment env;
	
	private int FARROW_EVENT_START_DURATION;
	private int FARROW_EVENT_END_DURATION;
	
	private int SUCCESS_CODE = 0;
	private int ERR_CODE_01 = 1;
	
	/**
	 * Load the property values
	 */
	private void init()
	{
		
		String period = env.getProperty("FARROW_EVENT_START_DURATION");		
		FARROW_EVENT_START_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("FARROW_EVENT_END_DURATION");		
		FARROW_EVENT_END_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		logger.info(FARROW_EVENT_START_DURATION+"--"+FARROW_EVENT_END_DURATION);
	} 
	
  /**
   * Business validation method
   * @param farrowEventDto
   * @return
   */
  public int validate(FarrowEventDto farrowEventDto)
  {
	  
	  init();
	  
	  DateTime farrowDateTime = new DateTime(farrowEventDto.getFarrowDateTime());
	  DateTime breedingDate = new DateTime(farrowEventDto.getPregnancyEventDto().getBreedingEventDto().getBreedingDate());
	  int duration = Days.daysBetween(breedingDate, farrowDateTime).getDays();
	  
	  if(duration >= FARROW_EVENT_START_DURATION && duration <= FARROW_EVENT_END_DURATION)	  
		  return SUCCESS_CODE;
	  else
		  return ERR_CODE_01;
	 
  }
  
  
  
}
