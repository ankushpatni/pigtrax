package com.pigtrax.master.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dto.OriginDto;

public interface OriginService {
    List<OriginDto> getOriginList();
    void saveOrigin(OriginDto originDto) throws PigTraxException;
    void deleteOrigin(Integer originId);
}
