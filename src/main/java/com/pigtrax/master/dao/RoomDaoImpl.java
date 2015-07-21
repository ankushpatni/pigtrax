package com.pigtrax.master.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.master.dao.interfaces.RoomDao;
import com.pigtrax.master.dto.Room;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class RoomDaoImpl implements RoomDao{

	private static final Logger logger = Logger.getLogger(RoomDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Room> getRoomList(final int generatedBarnId) {
		String query = "SELECT \"id\",\"roomId\", \"location\",\"id_Barn\", \"isActive\" from pigtrax.\"Room\" where \"id_Barn\" = ? order by \"id\" desc ";

		List<Room> roomList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedBarnId);
			}}, new RoomMapper());
		
		return roomList;
	}

	@Override
	public int updateRoomStatus(final String roomID, final Boolean roomStatus)
			throws SQLException {
		String query = "update pigtrax.\"Room\" SET \"isActive\"=?  WHERE \"roomId\"=?";

		logger.info("roomStatus-->" + roomStatus);
		logger.info("roomID-->" + roomID);
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, !roomStatus);
				ps.setString(2, roomID.toUpperCase());
			}
		});
	}

	
	@Override
	public int insertRoomRecord(final Room room) throws SQLException {
		String query = "INSERT INTO pigtrax.\"Room\"(  \"roomId\", \"id_Barn\", location, "
				 +" \"isActive\", \"lastUpdated\",\"userUpdated\")"+
				 "VALUES ( ?, ?, ?, ?, ?, ?)";
	
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, room.getRoomId().toUpperCase());
				ps.setInt(2, room.getBarnId());
				ps.setString(3, room.getLocation());
				ps.setBoolean(4, room.isActive());
				ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(6, UserUtil.getLoggedInUser());
			}
		});	
	}

	@Override
	public int updateRoomRecord(final Room room) throws SQLException {
		String query = "update pigtrax.\"Room\" SET location=?, \"lastUpdated\"=?,"+
				" \"userUpdated\"=?  WHERE \"roomId\"=?";
return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
	@Override
	public void setValues(PreparedStatement ps) throws SQLException {
		
		ps.setString(1, room.getLocation());
		ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
		ps.setString(3, UserUtil.getLoggedInUser());
		ps.setString(4, room.getRoomId());
				
	}
});
	}
	
	private static final class RoomMapper implements RowMapper<Room> {
		public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
			Room room = new Room();
			room.setId(rs.getInt("id"));
			room.setRoomId(rs.getString("roomId"));
			room.setBarnId(rs.getInt("id_Barn"));
			room.setLocation(rs.getString("location"));
			room.setActive(rs.getBoolean("isActive"));
			return room;
		}
	}
	

}
