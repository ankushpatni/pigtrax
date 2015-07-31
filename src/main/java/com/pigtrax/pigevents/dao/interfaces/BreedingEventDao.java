package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.PigInfo;

public interface BreedingEventDao {
   public int addBreedingEventInformation(BreedingEvent breedingEvent) throws SQLException, DuplicateKeyException;   

   public int updateBreedingEventInformation(final BreedingEvent breedingEvent) throws SQLException, DuplicateKeyException ;
  
   public BreedingEvent getBreedingEventInformation(String serviceId, Integer companyId) throws SQLException;
   
   BreedingEvent getBreedingEventInformation(final Integer breedingEventId) throws SQLException;
   
   public void deleteBreedingEventInfo(Integer id) throws SQLException;
}
