package com.pigtrax.master.service.interfaces;

import java.util.List;
import java.util.Map;

import com.pigtrax.master.dto.MasterRationDto;

public interface MasterRationService {
    void saveRation(MasterRationDto rationDto);
    
    void deleteRation(Integer rationId);
    
    List<MasterRationDto> getRationList(String language, String string);
    
    Map<Integer, String> getRationListAsMap(String language, String activeUser);
    
}
