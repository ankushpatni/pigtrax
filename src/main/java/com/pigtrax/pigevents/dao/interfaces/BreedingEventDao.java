package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.BreedingEvent;

public interface BreedingEventDao {
	
   int addBreedingEventInformation(BreedingEvent breedingEvent) throws SQLException, DuplicateKeyException;   

   int updateBreedingEventInformation(final BreedingEvent breedingEvent) throws SQLException, DuplicateKeyException ;
  
   BreedingEvent getBreedingEventInformation(String serviceId, Integer companyId) throws SQLException;
   
   List<BreedingEvent> getBreedingEventInformationByPigId(String searchText, Integer companyId) throws SQLException;
   
   List<BreedingEvent> getBreedingEventInformationByTattoo(String searchText, Integer companyId) throws SQLException;
   
   List<BreedingEvent> getBreedingEventInformationByServiceId(String searchText, Integer companyId) throws SQLException;
   
   BreedingEvent getBreedingEventInformation(Integer breedingEventId) throws SQLException;
   
   void deleteBreedingEventInfo(Integer id) throws SQLException;
   
   BreedingEvent checkForBreedingServiceId(String pigId, String serviceId, int companyId) throws SQLException; 
}
