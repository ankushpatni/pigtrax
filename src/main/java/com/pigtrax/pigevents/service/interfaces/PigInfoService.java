package com.pigtrax.pigevents.service.interfaces;

import com.pigtrax.pigevents.dto.PigInfoDto;

public interface PigInfoService {
    public int savePigInformation(PigInfoDto dto) throws Exception;
    
    public PigInfoDto getPigInformation(PigInfoDto dto) throws Exception;
}  
