package com.pigtrax.pigevents.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.Pen;
import com.pigtrax.pigevents.dao.interfaces.PenDao;

@Repository
@Transactional
public class PenDaoImpl implements PenDao{

	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	  public List<Pen> getPenList(final Integer barnId) throws SQLException {
		  String Qry = "Select \"id\", \"penId\", \"id_Room\", \"location\", \"isActive\" from pigtrax.\"Pen\" where  \"isActive\" is true and "
		  		+ "\"id_Room\" in (Select \"id\" from pigtrax.\"Room\" where \"id_Barn\" = ? and  \"isActive\" is true)";
		  List<Pen> penList = jdbcTemplate.query(Qry, new PreparedStatementSetter(){
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, barnId);
				}}, new PenMapper());

		  return penList;
	}
	  
	  
	  private static final class PenMapper implements RowMapper<Pen> {
			public Pen mapRow(ResultSet rs, int rowNum) throws SQLException {
				Pen pen = new Pen();
				pen.setId(rs.getInt("id"));
				pen.setPenId(rs.getString("penId"));
				pen.setRoomId(rs.getInt("id_Room"));
				pen.setLocation(rs.getString("location"));
				pen.setActive(rs.getBoolean("isActive"));
				return pen;
			}
		}
}
