package com.pigtrax.master.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.dao.interfaces.RoomDao;
import com.pigtrax.master.dto.Room;
import com.pigtrax.master.service.interfaces.RoomService;

/**
 * @author Ankush
 *
 */
@Repository
public class RoomServiceImpl implements RoomService{

	@Autowired
	private RoomDao roomDao;

	@Override
	public List<Room> getRoomList(int generatedPremisesId) {
		return roomDao.getRoomList( generatedPremisesId );
	}

	@Override
	public int updateRoomStatus(String roomID, Boolean roomStatus)
			throws SQLException {
		return roomDao.updateRoomStatus(roomID, roomStatus);
	}
	
	@Override
	public int insertRoomRecord(Room room) throws SQLException
	{
		return roomDao.insertRoomRecord(room);
	}
	
	@Override
	public int updateRoomRecord(Room room) throws SQLException
	{
		return roomDao.updateRoomRecord(room);
	}

}
