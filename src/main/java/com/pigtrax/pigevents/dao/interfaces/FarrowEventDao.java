package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.FarrowEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;

public interface FarrowEventDao {
	
	FarrowEvent getFarrowEvent(Integer farrowEventId);
   
   int updateFarrowEventDetails(FarrowEvent farrowEvent) throws SQLException;
   
   int addFarrowEventDetails(FarrowEvent farrowEvent) throws SQLException;
   
   List<FarrowEvent> getFarrowEvents(String pigId, String searchOption, Integer companyId) throws SQLException;
    
   void deleteFarrowEvent(Integer farrowEventId) throws SQLException;
   
}
