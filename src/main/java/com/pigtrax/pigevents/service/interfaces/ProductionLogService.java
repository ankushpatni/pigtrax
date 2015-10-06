package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.ProductionLog;
import com.pigtrax.pigevents.dto.ProductionLogDto;

public interface ProductionLogService {
   int storeProductionLog(ProductionLogDto productionLogDto) throws PigTraxException;
   
   List<ProductionLogDto> getProductLogList(ProductionLogDto productionLogDto) throws PigTraxException;
   
   int deleteProductionLog(Integer id) throws PigTraxException;
}
