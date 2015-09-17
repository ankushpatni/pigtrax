package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.FarrowEvent;
import com.pigtrax.pigevents.dto.FarrowEventDto;

public interface FarrowEventService {
   int saveFarrowEventInformation(FarrowEventDto farrowEventDto) throws PigTraxException;
   
   List<FarrowEventDto> getFarrowEvents(FarrowEventDto farrowEventDto) throws PigTraxException; 
   
   void deleteFarrowEvent(Integer farrowEventId) throws PigTraxException;
   
   int validateFarrowEvent(FarrowEventDto farrowEventDto);
   
   FarrowEventDto getFarrowEventDetails(Integer farrowEventId) throws PigTraxException;
   
   FarrowEventDto getFarrowEventDetails(FarrowEventDto farrowEventDto) throws PigTraxException;
   
   FarrowEventDto getFarrowEventByPregancyEvent(final Integer pregnancyEventId);
}

