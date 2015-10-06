package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.pigtrax.pigevents.beans.ProductionLog;

public interface ProductionLogDao {
   int addProductionLog(ProductionLog productionLog) throws SQLException;
   
   int updateProductionLog(ProductionLog productionLog) throws SQLException;
   
   List<ProductionLog> getProductLogList(final Integer companyId, final Date startDate, final Date endDate) throws SQLException;
   
   int deleteProductionLog(Integer id) throws SQLException;
}
