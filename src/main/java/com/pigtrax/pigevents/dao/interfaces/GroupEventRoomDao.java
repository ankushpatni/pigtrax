package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.RoomPK;
import com.pigtrax.pigevents.beans.GroupEventPhaseChange;

public interface GroupEventRoomDao {
     void addGroupEventRooms(GroupEventPhaseChange groupEventPhaseChange);
     
     void deleteGroupEventRooms(Integer groupEventId);
     
     List<RoomPK> getGroupEventRooms(final Integer groupEventPhaseChangeId)	throws SQLException;

	void addSingleGroupEventRooms(Integer eventId, Integer roomId);
}
