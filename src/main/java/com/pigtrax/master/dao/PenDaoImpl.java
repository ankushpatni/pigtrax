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

import com.pigtrax.master.dao.interfaces.PenDao;
import com.pigtrax.master.dto.Pen;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class PenDaoImpl implements PenDao {

	private static final Logger logger = Logger.getLogger(PenDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Pen> getPenList(final int generatedBarnId) {
		String query = "SELECT \"id\",\"penId\", \"location\",\"id_Room\", \"isActive\" from pigtrax.\"Pen\" where \"id_Room\" = ? order by \"id\" desc ";

		List<Pen> penList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedBarnId);
					}
				}, new PenMapper());

		return penList;
	}

	@Override
	public int updatePenStatus(final String penID, final Boolean penStatus)
			throws SQLException {
		String query = "update pigtrax.\"Pen\" SET \"isActive\"=?  WHERE \"penId\"=?";

		logger.info("penStatus-->" + penStatus);
		logger.info("penID-->" + penID);
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, !penStatus);
				ps.setString(2, penID.toUpperCase());
			}
		});
	}

	@Override
	public int insertPenRecord(final Pen pen) throws SQLException {
		String query = "INSERT INTO pigtrax.\"Pen\"(  \"penId\", \"id_Room\", location, "
				+ " \"isActive\", \"lastUpdated\",\"userUpdated\")"
				+ "VALUES ( ?, ?, ?, ?, ?, ?)";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, pen.getPenId().toUpperCase());
				ps.setInt(2, pen.getRoomId());
				ps.setString(3, pen.getLocation());
				ps.setBoolean(4, pen.isActive());
				ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(6, UserUtil.getLoggedInUser());
			}
		});
	}

	@Override
	public int updatePenRecord(final Pen pen) throws SQLException {
		String query = "update pigtrax.\"Pen\" SET location=?, \"lastUpdated\"=?,"
				+ " \"userUpdated\"=?  WHERE \"penId\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setString(1, pen.getLocation());
				ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(3, UserUtil.getLoggedInUser());
				ps.setString(4, pen.getPenId());

			}
		});
	}
	
	 public List<Pen> getPenListByBarnId(final Integer barnId) throws SQLException {
		  String Qry = "Select \"id\", \"penId\", \"id_Room\", \"location\", \"isActive\" from pigtrax.\"Pen\" where  \"isActive\" is true and "
		  		+ "\"id_Room\" in (Select \"id\" from pigtrax.\"Room\" where \"id_Barn\" = ? and  \"isActive\" is true) order by \"penId\" ";
		  List<Pen> penList = jdbcTemplate.query(Qry, new PreparedStatementSetter(){
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, barnId);
				}}, new PenMapper());

		  return penList;
	}
	 
	 
	 public List<Pen> getPenListByCompanyId(final Integer companyId) throws SQLException {
		  String Qry = "Select \"id\", \"penId\", \"id_Room\", \"location\", \"isActive\" from pigtrax.\"Pen\" where  \"isActive\" is true and "
		  		+ "\"id_Room\" in (Select \"id\" from pigtrax.\"Room\" where \"id_Barn\" = (select \"id\" from pigtrax.\"Barn\" where \"id_Premise\" = (select \"id\" from pigtrax.\"Premise\" where \"id_Company\" = ?)) and  \"isActive\" is true)";
		  List<Pen> penList = jdbcTemplate.query(Qry, new PreparedStatementSetter(){
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, companyId);
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
