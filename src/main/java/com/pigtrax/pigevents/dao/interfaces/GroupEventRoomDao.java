package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.RoomPK;
import com.pigtrax.pigevents.beans.GroupEvent;

public interface GroupEventRoomDao {
     void addGroupEventRooms(GroupEvent event);
     
     void deleteGroupEventRooms(Integer groupEventId);
     
     List<RoomPK> getGroupEventRooms(final Integer groupEventId)	throws SQLException;
}
