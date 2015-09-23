package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.CompanyTargetDto;

public interface CompanyTargetService {
   List<CompanyTargetDto> getCompanyTargets(CompanyTargetDto companyTargetDto) throws PigTraxException;
   
   CompanyTargetDto saveCompanyTarget(CompanyTargetDto companyTargetDto) throws PigTraxException;
   
   List<CompanyTargetDto> deleteTargetDetails(CompanyTargetDto companyTargetDto) throws PigTraxException;
   
}
