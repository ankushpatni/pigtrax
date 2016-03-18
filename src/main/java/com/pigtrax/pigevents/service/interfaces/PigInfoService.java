package com.pigtrax.pigevents.service.interfaces;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.dto.PigInfoDto;

public interface PigInfoService {
    int savePigInformation(PigInfoDto dto) throws Exception;
    
    PigInfoDto getPigInformation(PigInfoDto dto) throws Exception;
    
    int deletePigInfo(Integer id) throws Exception;
    
    List<PigInfoDto> getAllFosterPigs(PigInfoDto pigInfo) throws Exception; 
    
    Map<Integer,Object> getPigInformationByCompany(int companyId) throws Exception;
    
    int changePigId(Integer pigInfoId, String newPigId) throws PigTraxException; 
    
    PigInfoDto getInactivePigInformation(PigInfoDto dto) throws Exception;
    
    List<String> getAvailablePigIds(Integer companyId);
    
    PigInfoDto getPigInformationById(final Integer pigInfoId) throws PigTraxException, ParseException;

	Integer updatePigInfoRecordForSowMovement(PigInfoDto pigInfoDto)
			throws Exception;

	PigInfoDto getPigInformationWithOutStatus(PigInfoDto dto) throws Exception;
	
	public List<PigInfoDto> getActivePigInformationListByPremises(Integer companyId, Integer premiseId) throws PigTraxException, ParseException ;
}  
