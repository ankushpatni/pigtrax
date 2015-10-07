package com.pigtrax.pigevents.service.interfaces;

import java.util.List;
import java.util.Map;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.PigInfoDto;

public interface PigInfoService {
    int savePigInformation(PigInfoDto dto) throws Exception;
    
    PigInfoDto getPigInformation(PigInfoDto dto) throws Exception;
    
    int deletePigInfo(Integer id) throws Exception;
    
    List<PigInfoDto> getAllFosterPigs(PigInfoDto pigInfo) throws Exception; 
    
    Map<Integer,Object> getPigInformationByCompany(int companyId) throws Exception;
    
    int changePigId(Integer pigInfoId, String newPigId) throws PigTraxException; 
    
    PigInfoDto getInactivePigInformation(PigInfoDto dto) throws Exception;
}  
