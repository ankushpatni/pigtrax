package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dao.interfaces.RoomDao;
import com.pigtrax.master.dto.Room;
import com.pigtrax.pigevents.beans.ProductionLog;
import com.pigtrax.pigevents.dao.interfaces.ProductionLogDao;
import com.pigtrax.pigevents.dto.ProductionLogDto;
import com.pigtrax.pigevents.service.interfaces.ProductionLogService;

@Repository
public class ProductionLogServiceImpl implements ProductionLogService {
	
	@Autowired
	ProductionLogDao productionLogDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	RoomDao roomDao;
	
	/**
	 * stores the production log details
	 */
    @Override
    public int storeProductionLog(ProductionLogDto productionLogDto)
    		throws PigTraxException {
    	if(productionLogDto != null)
    	{    	
    		ProductionLog productionLog = new ProductionLog();
    		productionLog.setObservation(productionLogDto.getObservation());
    		productionLog.setEventId(productionLogDto.getEventId());
    		productionLog.setRoomId(productionLogDto.getRoomId());
    		productionLog.setLogEventTypeId(productionLogDto.getLogEventTypeId());
    		productionLog.setObservationDate(productionLogDto.getObservationDate());
    		productionLog.setCompanyId(productionLogDto.getCompanyId());
    		productionLog.setUserUpdated(productionLogDto.getUserUpdated());
    		productionLog.setId(productionLogDto.getId());
    		productionLog.setGroupId(productionLogDto.getGroupId());
    		try {
    			if(productionLog.getId() == null)
    				return productionLogDao.addProductionLog(productionLog);
    			else
    				return productionLogDao.updateProductionLog(productionLog);
			} catch (SQLException e) {
				throw new PigTraxException(e.getMessage());
			}
    	}
    	else
    		return -1;
    }
    
    /**
     * selects the list of production logs for a company
     */
    @Override
    public List<ProductionLogDto> getProductLogList(
    		ProductionLogDto productionLogDto) throws PigTraxException {
    	List<ProductionLog> productionLogs = null;
    	List<ProductionLogDto> productionLogDtoList = new ArrayList<ProductionLogDto>();
    	try{
	    	productionLogs = productionLogDao.getProductLogList(productionLogDto.getCompanyId(), 
	    			productionLogDto.getStartDate(), productionLogDto.getEndDate(), productionLogDto.getUserUpdated());   
	    	
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
	    			dto.setEventId(productionLog.getEventId());
	    			dto.setGroupId(productionLog.getGroupId());
	    			if(productionLog.getRoomId() != null && productionLog.getRoomId() !=0)
	    				dto.setRoomId(productionLog.getRoomId());
	    			if(productionLog.getLogEventTypeId() != null && productionLog.getLogEventTypeId() !=0)
	    				dto.setLogEventTypeId(productionLog.getLogEventTypeId());
	    			
	    			dto.setLogEventType(refDataCache.getLogEventTypeMap(productionLogDto.getLanguage()).get(dto.getLogEventTypeId()));
	    			
	    			Room room = roomDao.findByRoomByAutoGeneratedId(dto.getRoomId());
	    			if(room != null)
	    				dto.setRoom(room.getRoomId());
	    			
	    			dto.setObservationDate(productionLog.getObservationDate());
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
    
    /**
     * Deletes the production Log for a given id
     */
    @Override
    public int deleteProductionLog(Integer id) throws PigTraxException {
    	try{
    		return productionLogDao.deleteProductionLog(id);
    	}
    	catch(SQLException sqlEx)
    	{
    		throw new PigTraxException(sqlEx.getMessage());
    	}
    }
}
