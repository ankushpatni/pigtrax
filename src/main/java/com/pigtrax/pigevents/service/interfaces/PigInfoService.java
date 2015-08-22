package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.pigevents.dto.PigInfoDto;

public interface PigInfoService {
    int savePigInformation(PigInfoDto dto) throws Exception;
    
    PigInfoDto getPigInformation(PigInfoDto dto) throws Exception;
    
    void deletePigInfo(Integer id) throws Exception;
    
    List<PigInfoDto> getAllFosterPigs(PigInfoDto pigInfo) throws Exception; 
}  
