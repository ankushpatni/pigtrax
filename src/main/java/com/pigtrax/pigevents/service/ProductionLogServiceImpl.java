package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.ProductionLog;
import com.pigtrax.pigevents.dao.interfaces.ProductionLogDao;
import com.pigtrax.pigevents.dto.ProductionLogDto;
import com.pigtrax.pigevents.service.interfaces.ProductionLogService;

@Repository
public class ProductionLogServiceImpl implements ProductionLogService {
	
	@Autowired
	ProductionLogDao productionLogDao;
	
    @Override
    public int storeProductionLog(ProductionLogDto productionLogDto)
    		throws PigTraxException {
    	if(productionLogDto != null)
    	{    	
    		ProductionLog productionLog = new ProductionLog();
    		productionLog.setObservation(productionLogDto.getObservation());
    		productionLog.setCompanyId(productionLogDto.getCompanyId());
    		productionLog.setUserUpdated(productionLogDto.getUserUpdated());
    		
    		try {
				return productionLogDao.storeProductionLog(productionLog);
			} catch (SQLException e) {
				throw new PigTraxException(e.getMessage());
			}
    	}
    	else
    		return -1;
    }
    
    
    @Override
    public List<ProductionLogDto> getProductLogList(
    		ProductionLogDto productionLogDto) throws PigTraxException {
    	List<ProductionLog> productionLogs = null;
    	List<ProductionLogDto> productionLogDtoList = new ArrayList<ProductionLogDto>();
    	try{
	    	productionLogs = productionLogDao.getProductLogList(productionLogDto.getCompanyId(), 
	    			productionLogDto.getStartDate(), productionLogDto.getEndDate());  
	    	
	    	if(productionLogs != null)
	    	{
	    		for(ProductionLog productionLog : productionLogs)
	    		{
	    			ProductionLogDto dto = new ProductionLogDto();
	    			dto.setId(productionLog.getId());
	    			dto.setObservation(productionLog.getObservation());
	    			dto.setCompanyId(productionLog.getCompanyId());
	    			dto.setLastUpdated(productionLog.getLastUpdated());
	    			dto.setUserUpdated(productionLog.getUserUpdated());
	    			productionLogDtoList.add(dto);
	    		}
	    	}
    	}
    	catch(SQLException ex)
    	{
    		throw new PigTraxException(ex.getMessage());
    	}
    	return productionLogDtoList;
    }
}
