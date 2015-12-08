package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.dto.BreedingEventDto;

public interface BreedingEventDao {
	
   int addBreedingEventInformation(BreedingEvent breedingEvent) throws SQLException, DuplicateKeyException;   

   int updateBreedingEventInformation(final BreedingEvent breedingEvent) throws SQLException, DuplicateKeyException ; 
   
   List<BreedingEvent> getBreedingEventInformationByPigId(String searchText, Integer companyId) throws SQLException;
   
   List<BreedingEvent> getBreedingEventInformationByTattoo(String searchText, Integer companyId) throws SQLException;
   
   BreedingEvent getBreedingEventInformation(Integer breedingEventId) throws SQLException;
   
   void deleteBreedingEventInfo(Integer id) throws SQLException;
   
   int updateServiceStartDate(Date matingDate, Integer breedingEventId);
   
   BreedingEvent checkForBreedingServiceId(String pigId, String serviceId, int companyId); 
   
   BreedingEvent getLatestServiceEvent(Integer pigInfoId);
   
   List<BreedingEvent> getOpenServiceRecords(Integer pigInfoId);
   
   Date getServiceStartDate(Integer breedingEventId);
   
}
