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

@Repository
@Transactional
public class RoomDaoImpl implements RoomDao {

	private static final Logger logger = Logger.getLogger(RoomDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Room> getRoomList(final int generatedBarnId) {
		String query = "SELECT \"id\",\"roomId\", \"location\",\"id_Barn\", \"isActive\",\"id_roomPosition\",\"pigSpaces\" "
				+ "from pigtrax.\"Room\" where \"id_Barn\" = ? order by \"id\" desc ";

		List<Room> roomList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedBarnId);
					}
				}, new RoomMapper());

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
	public Room findByRoomByAutoGeneratedId(final int generatedRoomId) throws SQLException
	{
		String query = "SELECT \"id\",\"roomId\", \"location\",\"id_Barn\", \"isActive\", \"id_roomPosition\",\"pigSpaces\""
				+ " from pigtrax.\"Room\" where \"id\" = ?";
		
		List<Room> roomList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedRoomId);
					}
				}, new RoomMapper());

		if (roomList != null && roomList.size() > 0) {
			return roomList.get(0);
		}

		return null;
	}
	

	@Override
	public int insertRoomRecord(final Room room) throws SQLException {
		String query = "INSERT INTO pigtrax.\"Room\"(  \"roomId\", \"id_Barn\", location, "
				+ " \"isActive\", \"lastUpdated\",\"userUpdated\", \"id_roomPosition\",\"pigSpaces\",\"floorTypeId\")"
				+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, room.getRoomId().toUpperCase());
				ps.setInt(2, room.getBarnId());
				ps.setString(3, room.getLocation());
				ps.setBoolean(4, room.isActive());
				ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(6, room.getUserUpdated());
				ps.setObject(7, room.getRoomPositionId(), java.sql.Types.INTEGER);
				ps.setObject(8, room.getPigSpaces(), java.sql.Types.INTEGER);
				ps.setObject(9, room.getFloorTypeId(), java.sql.Types.INTEGER);
			}
		});
	}

	@Override
	public int updateRoomRecord(final Room room) throws SQLException {
		String query = "update pigtrax.\"Room\" SET location=?, \"lastUpdated\"=?,"
				+ " \"userUpdated\"=?, \"id_roomPosition\"=?,\"pigSpaces\" = ?  WHERE \"roomId\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setString(1, room.getLocation());
				ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(3, room.getUserUpdated());
				ps.setObject(4, room.getRoomPositionId(), java.sql.Types.INTEGER);
				ps.setObject(5, room.getPigSpaces(), java.sql.Types.INTEGER);
				ps.setString(6, room.getRoomId());

			}
		});
	}
	
	public List<Room> getRoomListBasedOnCompanyId( final int generatedCompanyId ) throws SQLException
	{
		String query = "SELECT \"roomserrialid\" as \"id\",\"roomId\" from pigtrax.\"CompPremBarnRoomPenVw\" where \"roomId\" != '' and companyserialid = ?";
	//CompPremBarnRoomPenVw
		List<Room> roomList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedCompanyId);
					}
				}, new RoomMapperList());
	
		if (roomList != null && roomList.size() > 0) {
			return  roomList;
		}
		return null;
	}
	
	public int getCountOfSpacesBasedOnCompanyId( final int generatedCompanyId ) throws SQLException
	{
		String query = "select sum(\"pigSpaces\") from pigtrax.\"Room\" where \"id\" in  (SELECT \"roomserrialid\" as \"id\" from pigtrax.\"CompPremBarnRoomPenVw\" where \"roomId\" != '' and companyserialid = ?)";
	//CompPremBarnRoomPenVw
		 List<Integer> penList = jdbcTemplate.query(query,
					new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, generatedCompanyId);
						}
					}, new RowMapper<Integer>() {
						public Integer mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getInt(1);
						}
					});
			
		 return penList.get(0);
	}
	
	@Override
	public List<Room> getRoomListBasedOnPremise( final int premiseId ) throws SQLException
	{
		String query = "SELECT \"roomserrialid\" as \"id\",\"roomId\" from pigtrax.\"CompPremBarnRoomPenVw\" where \"roomId\" != '' and premiseserialid = ?";
	//CompPremBarnRoomPenVw
		List<Room> roomList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, premiseId);
					}
				}, new RoomMapperList());
	
		if (roomList != null && roomList.size() > 0) {
			return  roomList;
		}
		return null;
	}
	
	@Override
	public int getCountOfSpacesBasedOnPremisesId( final int premiseId ) throws SQLException
	{
		String query = "select sum(\"pigSpaces\") from pigtrax.\"Room\" where \"id\" in  (SELECT \"roomserrialid\" as \"id\" from pigtrax.\"CompPremBarnRoomPenVw\" where \"roomId\" != '' and premiseserialid = ?)";
	//CompPremBarnRoomPenVw
		 List<Integer> penList = jdbcTemplate.query(query,
					new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, premiseId);
						}
					}, new RowMapper<Integer>() {
						public Integer mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getInt(1);
						}
					});
			
		 return penList.get(0);
	}
	
	
	@Override
	public Room getRoomListBasedOnPen( final int penId ) throws SQLException
	{
		String query = "SELECT \"roomserrialid\" as \"id\",\"roomId\" from pigtrax.\"CompPremBarnRoomPenVw\" where \"roomId\" != '' and penserialid = ?";
	//CompPremBarnRoomPenVw
		List<Room> roomList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, penId);
					}
				}, new RoomMapperList());
	
		if (roomList != null && roomList.size() > 0) {
			return  roomList.get(0);
		}
		return null;
	}

	private static final class RoomMapper implements RowMapper<Room> {
		public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
			Room room = new Room();
			room.setId(rs.getInt("id"));
			room.setRoomId(rs.getString("roomId"));
			room.setBarnId(rs.getInt("id_Barn"));
			room.setLocation(rs.getString("location"));
			room.setActive(rs.getBoolean("isActive"));
			room.setRoomPositionId(rs.getInt("id_roomPosition"));
			room.setPigSpaces(rs.getInt("pigSpaces"));
			return room;
		}
	}
	
	private static final class RoomMapperList implements RowMapper<Room> {
		public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
			Room room = new Room();
			room.setId(rs.getInt("id"));
			room.setRoomId(rs.getString("roomId"));
			return room;
		}
	}
	
	
	@Override
	public List<Room> getRoomListBasedOnPremise(final int premiseId,
			final String barnType) throws SQLException {
		String query = "";
		if(barnType != null && "farrow".equalsIgnoreCase(barnType))
			query = "SELECT Vw.\"roomserrialid\" as \"id\",Vw.\"roomId\" from pigtrax.\"CompPremBarnRoomPenVw\" Vw JOIN pigtrax.\"Barn\" B ON Vw.\"barnserialid\" = B.\"id\" "
				+" JOIN pigtraxrefdata.\"PhaseType\" PT ON B.\"id_PhaseType\" = PT.\"id\" "
				+" where Vw.\"roomId\" != '' and Vw.premiseserialid = ? and PT.\"fieldDescription\" = 'Farrow'";
		else
			query = "SELECT Vw.\"roomserrialid\" as \"id\",Vw.\"roomId\" from pigtrax.\"CompPremBarnRoomPenVw\" Vw JOIN pigtrax.\"Barn\" B ON Vw.\"barnserialid\" = B.\"id\" "
					+" JOIN pigtraxrefdata.\"PhaseType\" PT ON B.\"id_PhaseType\" = PT.\"id\" "
					+" where Vw.\"roomId\" != '' and Vw.premiseserialid = ? and PT.\"fieldDescription\" in ('Nursery','Finishing','Wean to finish')";
			
		//CompPremBarnRoomPenVw
			List<Room> roomList = jdbcTemplate.query(query,
					new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, premiseId);
						}
					}, new RoomMapperList());
		
			if (roomList != null && roomList.size() > 0) {
				return  roomList;
			}
			return null;
	}

}
