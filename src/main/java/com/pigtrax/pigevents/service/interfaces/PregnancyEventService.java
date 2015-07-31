package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.PregnancyEventDto;

public interface PregnancyEventService {
   public int savePregnancyEventInformation(PregnancyEventDto pregnancyEventDto) throws PigTraxException;
   
   List<PregnancyEventDto> getPregnancyEvents(final String pigId, final Integer companyId) throws PigTraxException;
   
   void deletePregnancyEvent(Integer pregnancyEventId) throws PigTraxException;
}
