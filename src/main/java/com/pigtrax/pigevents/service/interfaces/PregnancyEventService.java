package com.pigtrax.pigevents.service.interfaces;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.PregnancyEventDto;

public interface PregnancyEventService {
   public int savePregnancyEventInformation(PregnancyEventDto pregnancyEventDto) throws PigTraxException;
}
