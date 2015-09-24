package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.pigevents.beans.CompanyTarget;
import com.pigtrax.pigevents.dao.interfaces.CompanyTargetDao;
import com.pigtrax.pigevents.dto.CompanyTargetBuilder;
import com.pigtrax.pigevents.dto.CompanyTargetDto;
import com.pigtrax.pigevents.service.interfaces.CompanyTargetService;

@Repository
public class CompanyTargetServiceImpl implements CompanyTargetService{

	@Autowired
	CompanyTargetDao companyTargetDao; 
	
	@Autowired
	CompanyTargetBuilder builder;

	@Autowired
	RefDataCache refDataCache;
	
	@Override 
	public List<CompanyTargetDto> getCompanyTargets(CompanyTargetDto companyTargetDto) throws PigTraxException {
		try{
			List<CompanyTarget> companyTargets = companyTargetDao.getCompanyTargets(companyTargetDto.getCompanyId());
			List<CompanyTargetDto> companyTargetDtoList =  builder.convertToDtos(companyTargets);
			if(companyTargetDtoList != null)
			{
				for(CompanyTargetDto dto : companyTargetDtoList)
				{
					dto.setTargetName(refDataCache.getTargetTypeMap(companyTargetDto.getLanguage()).get(dto.getTargetId()));
				}
			}
			return companyTargetDtoList;
		}catch(SQLException sqlEx)
		{
			throw new PigTraxException(sqlEx.getMessage(), sqlEx.getSQLState());
		}
		
	}
	
	@Override 
	public CompanyTargetDto saveCompanyTarget(CompanyTargetDto companyTargetDto) throws PigTraxException {
		
		CompanyTarget target = builder.convertToBean(companyTargetDto);
		
		try{
			if(companyTargetDto.getId() != null && companyTargetDto.getId() > 0)
			{
				companyTargetDao.updateCompanyTarget(target);
			}
			else
			{
				int targetKey = companyTargetDao.addCompanyTarget(target);
				companyTargetDto.setId(targetKey);
			}			
			companyTargetDto.setTargetName(refDataCache.getTargetTypeMap(companyTargetDto.getLanguage()).get(companyTargetDto.getTargetId()));
			
		}catch(SQLException sqlEx)
		{
			throw new PigTraxException(sqlEx.getMessage(), sqlEx.getSQLState());
		}
		return companyTargetDto;
	}
	
	
	@Override 
	public List<CompanyTargetDto> deleteTargetDetails(CompanyTargetDto companyTargetDto)
			throws PigTraxException {
		try{
			int rowsdeleted = companyTargetDao.deleteTargetDetails(companyTargetDto.getId());
			if(rowsdeleted > 0)
			{  
				return getCompanyTargets(companyTargetDto);
			}
			return null;
		} catch(SQLException sqlEx)
		{
			throw new PigTraxException(sqlEx.getMessage(), sqlEx.getSQLState());
		}
		
	}
}
