package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.master.dto.Room;
import com.pigtrax.master.dto.RoomPK;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.GroupEventRoomDao;

@Repository
@Transactional
public class GroupEventRoomDaoImpl implements GroupEventRoomDao {

	private static final Logger logger = Logger.getLogger(GroupEventRoomDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void addGroupEventRooms(final GroupEvent event) {
		
		final String sql = "Insert into pigtrax.\"GroupEventRoom\"(\"id_GroupEvent\", \"id_Room\") values (?,?)";
		if(event != null && event.getRoomIds() != null)
		{
			List<RoomPK> rooms = event.getRoomIds();
			for(final RoomPK room : rooms)
			{
				jdbcTemplate.update(new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setInt(1, event.getId());
						ps.setInt(2, Integer.parseInt(room.getId()));
						return ps;
					}
				});				
			}
		}
	}
	
	
	@Override
	public void deleteGroupEventRooms(final Integer groupEventId) {
		final String qry = "delete from pigtrax.\"GroupEventRoom\" where \"id_GroupEvent\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, groupEventId);
			}
		});
	}
	
	
	@Override
	public List<RoomPK> getGroupEventRooms(final Integer groupEventId)
			throws SQLException {
		
		String qry = "select \"id_Room\" FROM pigtrax.\"GroupEventRoom\" where \"id_GroupEvent\" = ?";
			
		List<RoomPK> roomIds = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, groupEventId);
			}}, new GroupEventRoomMapper());

		return roomIds;
	}
	 
	 
	 private static final class GroupEventRoomMapper implements RowMapper<RoomPK> {
			public RoomPK mapRow(ResultSet rs, int rowNum) throws SQLException {
				RoomPK room = new RoomPK();
				room.setId(rs.getString("id_Room"));
				return room;
			}
		}
	
	
}
