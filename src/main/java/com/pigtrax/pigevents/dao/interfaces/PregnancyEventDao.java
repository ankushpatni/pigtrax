package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.PregnancyEvent;

public interface PregnancyEventDao {
	
   PregnancyEvent getPregnancyEvent(Integer pregnancyEventId);
   
   int updatePregnancyEventDetails(PregnancyEvent pregnancyEvent) throws SQLException;
   
   int addPregnancyEventdDetails(PregnancyEvent pregnancyEvent) throws SQLException;
   
   List<PregnancyEvent> getPregnancyEvents(final String pigId, final Integer companyId) throws SQLException;
   
   void deletePregnancyEvent(Integer pregnancyEventId) throws SQLException;
   
}