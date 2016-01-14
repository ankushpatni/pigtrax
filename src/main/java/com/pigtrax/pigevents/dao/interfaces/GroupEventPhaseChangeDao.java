package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.GroupEventPhaseChange;

public interface GroupEventPhaseChangeDao {
	
   Integer addGroupPhaseChange(GroupEventPhaseChange groupEventPhaseChange) throws SQLException;
   
   void endDateGroupEventPhase(Integer groupEventId) throws SQLException;
   
   List<GroupEventPhaseChange> getPhaseChangeDetails(final Integer groupEventId) throws SQLException;
   
   GroupEventPhaseChange getCurrentPhase(Integer groupEventId)  throws SQLException;
   
   Integer getCurrentPhaseRecordId(Integer groupEventId);
   
   void updatePhaseDetails(GroupEventPhaseChange currentPhase);
   
   void deleteCurrentPhase(Integer groupEventPhaseId);
   
   void activatePhase(Integer groupEventId, Integer phaseOfProductionTypeId);
   
}
