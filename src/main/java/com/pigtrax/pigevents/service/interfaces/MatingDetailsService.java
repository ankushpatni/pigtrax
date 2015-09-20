package com.pigtrax.pigevents.service.interfaces;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.MatingDetailsDto;

public interface MatingDetailsService {
	MatingDetailsDto saveMatingDetails(MatingDetailsDto matingDetailsDto) throws PigTraxException;
	
	MatingDetailsDto deleteMatingDetails(MatingDetailsDto matingDetailsDto) throws PigTraxException;
}
