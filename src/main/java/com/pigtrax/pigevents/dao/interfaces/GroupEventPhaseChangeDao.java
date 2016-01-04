package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.GroupEventPhaseChange;

public interface GroupEventPhaseChangeDao {
	
   Integer addGroupPhaseChange(GroupEventPhaseChange groupEventPhaseChange) throws SQLException;
   
   void endDateGroupEventPhase(Integer groupEventId) throws SQLException;
   
   List<GroupEventPhaseChange> getPhaseChangeDetails(final Integer groupEventId) throws SQLException;
   
   Integer getCurrentPhase(Integer groupEventId);
}
