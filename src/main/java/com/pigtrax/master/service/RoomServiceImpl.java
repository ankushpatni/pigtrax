package com.pigtrax.master.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	public List<Room> getRoomList(int generatedBarnId) {
		return roomDao.getRoomList( generatedBarnId );
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
	
	public Room findByRoomByAutoGeneratedId(final int generatedRoomId) throws SQLException
	{
		return roomDao.findByRoomByAutoGeneratedId(generatedRoomId);
	}
	
	/**
	 * To get the list of Room BIdased on company
	 * @return List<Room>
	 * @throws SQLException 
	 */
	public Map<Integer,String> getRoomListBasedOnCompanyId( int generatedCompanyId )
	{
		Map<Integer,String> roomIdMap = new LinkedHashMap<Integer,String>();
		try
		{
		List<Room> roomList =  roomDao.getRoomListBasedOnCompanyId(generatedCompanyId);
			if(null != roomList && roomList.size()>0)
			{
				for(Room room : roomList)
				{
					roomIdMap.put(room.getId(),room.getRoomId());
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return roomIdMap;
	}
	
	
	/**
	 * To get the list of Room BIdased on company
	 * @return List<Room>
	 * @throws SQLException 
	 */
	public Map<Integer,String> getRoomListBasedOnPremise( int premiseId )
	{
		Map<Integer,String> roomIdMap = new LinkedHashMap<Integer,String>();
		try
		{
		List<Room> roomList =  roomDao.getRoomListBasedOnPremise(premiseId);
			if(null != roomList && roomList.size()>0)
			{
				for(Room room : roomList)
				{
					roomIdMap.put(room.getId(),room.getRoomId());
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return roomIdMap;
	}
	
	@Override
	public Map<Integer, String> getRoomListBasedOnPremise(int premiseId,
			String barnType) {
		Map<Integer,String> roomIdMap = new LinkedHashMap<Integer,String>();
		try
		{
		List<Room> roomList =  roomDao.getRoomListBasedOnPremise(premiseId, barnType);
			if(null != roomList && roomList.size()>0)
			{
				for(Room room : roomList)
				{
					roomIdMap.put(room.getId(),room.getRoomId());
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return roomIdMap;
	}

}
