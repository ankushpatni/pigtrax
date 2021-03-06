package com.pigtrax.master.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.master.dto.Barn;
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
	
	/**
	 * Load the Room information based on auto generated generatedBarnId
	 * @param Barn
	 * @return
	 * @throws SQLException
	 */
	
	public Room findByRoomByAutoGeneratedId(final int generatedRoomId) throws SQLException;
	
	/**
	 * To get the list of Room BIdased on company
	 * @return List<Room>
	 * @throws SQLException 
	 */
	public List<Room> getRoomListBasedOnCompanyId( int generatedCompanyId ) throws SQLException;
	
	/**
	 * To get the list of Room BIdased on premise
	 * @return List<Room>
	 * @throws SQLException 
	 */
	public List<Room> getRoomListBasedOnPremise( int premiseId ) throws SQLException;
	
	/**
	 * To get the list of Room BIdased on premise and barn type
	 * @return List<Room>
	 * @throws SQLException 
	 */
	public List<Room> getRoomListBasedOnPremise( int premiseId, String barnType) throws SQLException;


	Room getRoomListBasedOnPen(int penId) throws SQLException;


	int getCountOfSpacesBasedOnPremisesId(int premiseId) throws SQLException;


	int getCountOfSpacesBasedOnCompanyId(int generatedCompanyId) throws SQLException;
	

}
