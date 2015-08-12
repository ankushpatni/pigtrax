package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.FarrowEventDto;

public interface FarrowEventService {
   public int saveFarrowEventInformation(FarrowEventDto farrowEventDto) throws PigTraxException;
   
   List<FarrowEventDto> getFarrowEvents(FarrowEventDto farrowEventDto) throws PigTraxException; 
   
   void deleteFarrowEvent(Integer farrowEventId) throws PigTraxException;
   
   int validateFarrowEvent(FarrowEventDto farrowEventDto);
}

