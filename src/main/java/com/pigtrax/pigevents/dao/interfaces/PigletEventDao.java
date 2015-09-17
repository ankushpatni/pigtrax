package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.PigletEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;

public interface PigletEventDao {
	
   //PregnancyEvent getPregnancyEvent(Integer pregnancyEventId);
   
   int updatePigletEventDetails(PigletEvent pregnancyEvent) throws SQLException, DuplicateKeyException;
   
   int addPigletEventDetails(PigletEvent pigletEvent) throws SQLException, DuplicateKeyException;
   
   public List<PigletEvent> getPigletEvents(String searchText, String searchOption, Integer companyId) throws SQLException;
   
   void deletePigletEvent(Integer pigletEventId) throws SQLException;
   
}
