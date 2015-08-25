package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.PigletStatusEventDto;

public interface PigletStatusEventService {
	
	int savePigletStatusEvent(PigletStatusEventDto pigletStatusEventDto) throws PigTraxException;
	  
	List<PigletStatusEventDto> getPigletStatusEvents(PigletStatusEventDto pigletEventStatusDto) throws PigTraxException;
	
	void deletePigletStatusEvent(PigletStatusEventDto pigletStatusEventDto) throws PigTraxException;
	
	int validatePigletStatusEvent(PigletStatusEventDto pigletStatusEventDto);
}
