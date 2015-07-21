package com.pigtrax.master.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.Room;

public interface RoomDao {
	
	/**
	 * To get the list of Room
	 * @return List<Room>
	 */
	public List<Room> getRoomList( int generatedBarnId );
	
	
	/**
	 * To update Room Status
	 * @return int
	 */
	
	public int updateRoomStatus(final String roomID, final Boolean roomStatus) throws SQLException;
	
	/**
	 * To Insert Room record
	 * @return int (Success with int>0)
	 */
	
	public int insertRoomRecord(Room room) throws SQLException;
	
	/**
	 * To update Room record
	 * @return Room 
	 */
	
	public int updateRoomRecord(Room room) throws SQLException;

}