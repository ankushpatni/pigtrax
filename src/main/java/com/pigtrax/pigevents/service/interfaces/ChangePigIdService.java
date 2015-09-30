package com.pigtrax.pigevents.service.interfaces;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.PigInfoDto;

public interface ChangePigIdService {
   int storeChangedPigId(PigInfoDto pigInfoDto) throws PigTraxException;
}
