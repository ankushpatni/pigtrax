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

import com.pigtrax.master.dao.interfaces.TransportTrailerDao;
import com.pigtrax.master.dto.TransportTrailer;
import com.pigtrax.master.dto.TransportTruck;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class TransportTrailerDaoImpl implements TransportTrailerDao{
	
	private static final Logger logger = Logger.getLogger(TransportTrailerDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private static final class TransportTrailerMapper implements RowMapper<TransportTrailer> {
		public TransportTrailer mapRow(ResultSet rs, int rowNum) throws SQLException {
			TransportTrailer transportTrailer = new TransportTrailer();
			transportTrailer.setId(rs.getInt("id"));
			transportTrailer.setTransportTrailerId(rs.getString("trailerId"));
			transportTrailer.setCompanyId(rs.getInt("id_Company"));
			transportTrailer.setTrailerTypeId(rs.getInt("id_TrailerType"));
			
			return transportTrailer;
		}
	}
	
	@Override
	public List<TransportTrailer> getTransportTrailerList(final int generatedCompanyId) {
		String query = "SELECT \"id\",\"trailerId\", \"id_Company\", \"id_TrailerType\" from pigtrax.\"TransportTrailer\" where \"id_Company\" = ? order by \"id\" desc ";

		List<TransportTrailer> transportTrailerList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedCompanyId);
					}
				}, new TransportTrailerMapper());

		return transportTrailerList;
	}
	
	@Override	
	public TransportTrailer findByTransportTrailerByAutoGeneratedId(final int generatedTransportTrailerId) throws SQLException
	{
		String query = "SELECT \"id\",\"trailerId\", \"id_Company\", \"id_TrailerType\" from pigtrax.\"TransportTrailer\" where \"id\" = ?";
		
		List<TransportTrailer> transportTrailerList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedTransportTrailerId);
					}
				}, new TransportTrailerMapper());

		if (transportTrailerList != null && transportTrailerList.size() > 0) {
			return transportTrailerList.get(0);
		}

		return null;
	}

	@Override
	public int insertTransportTrailer(final TransportTrailer transportTrailer) throws SQLException {
		String query = "INSERT INTO pigtrax.\"TransportTrailer\"(  \"trailerId\",  "
				+ " \"lastUpdated\",\"userUpdated\", \"id_Company\", \"id_TrailerType\")"
				+ "VALUES ( ?, ?, ?, ?, ?)";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, transportTrailer.getTransportTrailerId().toUpperCase());
				ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(3, UserUtil.getLoggedInUser());
				ps.setInt(4, transportTrailer.getCompanyId());
				ps.setInt(5, transportTrailer.getTrailerTypeId());
			}
		});
	}
	
	@Override
	public int updateTransportTrailer(final TransportTrailer transportTrailer) throws SQLException {
		String query = "update pigtrax.\"TransportTrailer\" SET id_TrailerType=?, \"lastUpdated\"=?,"
				+ " \"userUpdated\"=?  WHERE \"id\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setInt(1, transportTrailer.getTrailerTypeId());
				ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(3, UserUtil.getLoggedInUser());
				ps.setInt(4, transportTrailer.getId());

			}
		});
	}
			


}