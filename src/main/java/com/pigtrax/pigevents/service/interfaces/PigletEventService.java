package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.PigletEventDto;

public interface PigletEventService {
   int savePigletEventInformation(PigletEventDto pigletEventDto) throws PigTraxException;
   
   List<PigletEventDto> getPigletEvents(PigletEventDto pigletEventDto) throws PigTraxException; 
   
   List<PigletEventDto> getPigletEventsByFarrowId(String farrowId, Integer companyId) throws PigTraxException;
   
   void deletePigletEvent(Integer pigletEventId) throws PigTraxException;   
}

