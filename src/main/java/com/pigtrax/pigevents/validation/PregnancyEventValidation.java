package com.pigtrax.pigevents.validation;

import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.PregnancyEventDto;

@Component
//@PropertySource("file:src/main/resources/PigTraxValidation.properties")
//@PropertySource("/WEB-INF/classes/PigTraxValidation.properties")
@PropertySource("classpath:PigTraxValidation.properties")
public class PregnancyEventValidation {
	
	private static final Logger logger = Logger.getLogger(PregnancyEventValidation.class);
	
		
	@Autowired
	private Environment env;
	
	private int PREGNANCY_EVENT_PREG_EXAM_EVENT_START_DURATION;
	private int PREGNANCY_EVENT_PREG_EXAM_EVENT_END_DURATION;
	private int PREGNANCY_EVENT_ABORTION_START_DURATION;
	private int PREGNANCY_EVENT_ABORTION_END_DURATION;
	private int PREGNANCY_EVENT_NOT_IN_PIG_START_DURATION;
	private int PREGNANCY_EVENT_NOT_IN_PIG_END_DURATION;
	
	private int SUCCESS_CODE = 0;
	private int ERR_CODE_01 = 1;
	private int ERR_CODE_02 = 2;
	private int ERR_CODE_03 = 3;
	
	/**
	 * Load the property values
	 */
	private void init()
	{
		
		String period = env.getProperty("PREGNANCY_EVENT_PREG_EXAM_EVENT_START_DURATION");		
		PREGNANCY_EVENT_PREG_EXAM_EVENT_START_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("PREGNANCY_EVENT_PREG_EXAM_EVENT_END_DURATION");		
		PREGNANCY_EVENT_PREG_EXAM_EVENT_END_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("PREGNANCY_EVENT_ABORTION_START_DURATION");		
		PREGNANCY_EVENT_ABORTION_START_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("PREGNANCY_EVENT_ABORTION_END_DURATION");		
		PREGNANCY_EVENT_ABORTION_END_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("PREGNANCY_EVENT_NOT_IN_PIG_START_DURATION");		
		PREGNANCY_EVENT_NOT_IN_PIG_START_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		period = env.getProperty("PREGNANCY_EVENT_NOT_IN_PIG_END_DURATION");		
		PREGNANCY_EVENT_NOT_IN_PIG_END_DURATION = Integer.parseInt((period != null) ? period.trim() : "0");
		
		logger.info(PREGNANCY_EVENT_PREG_EXAM_EVENT_START_DURATION+"--"+PREGNANCY_EVENT_PREG_EXAM_EVENT_END_DURATION+"--"+PREGNANCY_EVENT_ABORTION_START_DURATION+"--"+PREGNANCY_EVENT_ABORTION_END_DURATION+"--"+PREGNANCY_EVENT_NOT_IN_PIG_START_DURATION+"--"+PREGNANCY_EVENT_NOT_IN_PIG_END_DURATION);
	} 
	
  /**
   * Business validation method
   * @param pregnancyEventDto
   * @return
   */
  public int validate(PregnancyEventDto pregnancyEventDto)
  {
	  
	  init();
	  
	  int pregnancyEventType = pregnancyEventDto.getPregnancyEventTypeId();
	  DateTime resultDate = new DateTime(pregnancyEventDto.getResultDate());
	  DateTime serviceDate = new DateTime(pregnancyEventDto.getBreedingEventDto().getBreedingDate());
	  
	  int duration = Days.daysBetween(serviceDate, resultDate).getDays();
	  
	  //In case of pregnancy event
	  if(pregnancyEventType == 1 && (duration < PREGNANCY_EVENT_PREG_EXAM_EVENT_START_DURATION || duration >  PREGNANCY_EVENT_PREG_EXAM_EVENT_END_DURATION))
	  {
		  return ERR_CODE_01;
	  }
	  else if(pregnancyEventType == 2 && (duration < PREGNANCY_EVENT_ABORTION_START_DURATION || duration > PREGNANCY_EVENT_ABORTION_END_DURATION))
	  {
		  return ERR_CODE_02;
	  }
	  else if(pregnancyEventType == 3 && (duration < PREGNANCY_EVENT_NOT_IN_PIG_START_DURATION || duration > PREGNANCY_EVENT_NOT_IN_PIG_END_DURATION))
	  {
		  return ERR_CODE_03;
	  }
	  
	  return SUCCESS_CODE;
	 
  }
  
  
  
}
