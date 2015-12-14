package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.FarrowEvent;

public interface FarrowEventDao {
	
	FarrowEvent getFarrowEvent(Integer farrowEventId);
   
   int updateFarrowEventDetails(FarrowEvent farrowEvent) throws SQLException;
   
   int addFarrowEventDetails(FarrowEvent farrowEvent) throws SQLException;
   
   List<FarrowEvent> getFarrowEvents(String pigId, String searchOption, Integer companyId) throws SQLException;
   
   List<FarrowEvent> getFarrowEvents(String pigId, String searchOption, Integer companyId, Integer premiseId) throws SQLException;
   
   //FarrowEvent getFarrowEvent(String farrowId, Integer companyId);
    
   void deleteFarrowEvent(Integer farrowEventId) throws SQLException;
   
   FarrowEvent getFarrowEventByPregancyEvent(final Integer pregnancyEventId) ;
   
   void updateLitterId(Integer farrowEventId, Integer companyId);
   
   boolean checkFarrowEventByBreedingEvent(Integer breedingEventId);
   
   FarrowEvent getFarrowEventIdByLitterId(Integer pigInfoId, Integer litterId);
}
