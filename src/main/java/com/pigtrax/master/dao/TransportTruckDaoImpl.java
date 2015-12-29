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

import com.pigtrax.master.dao.interfaces.TransportTruckDao;
import com.pigtrax.master.dto.TransportTruck;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class TransportTruckDaoImpl implements TransportTruckDao{
	
	private static final Logger logger = Logger.getLogger(TransportTruckDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<TransportTruck> getTransportTruckList(final int generatedCompanyId) {
		String query = "SELECT \"id\",\"truckId\", \"id_Company\",\"purchaseYear\",\"make\" from pigtrax.\"TransportTruck\" where \"id_Company\" = ? order by \"id\" desc ";

		List<TransportTruck> transportTruckList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedCompanyId);
					}
				}, new TransportTruckMapper());

		return transportTruckList;
	}
	
	@Override	
	public TransportTruck findByTransportTruckByAutoGeneratedId(final int generatedTransportTruckId) throws SQLException
	{
		String query = "SELECT \"id\",\"truckId\", \"id_Company\",\"purchaseYear\",\"make\" from pigtrax.\"TransportTruck\" where \"id\" = ?";
		
		List<TransportTruck> transportTruckList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedTransportTruckId);
					}
				}, new TransportTruckMapper());

		if (transportTruckList != null && transportTruckList.size() > 0) {
			return transportTruckList.get(0);
		}

		return null;
	}

	
	@Override
	public int insertTransportTruck(final TransportTruck transportTruck) throws SQLException {
		String query = "INSERT INTO pigtrax.\"TransportTruck\"(  \"truckId\", \"id_Company\", "
				+ " \"lastUpdated\",\"userUpdated\",\"purchaseYear\",\"make\")"
				+ "VALUES ( ?, ?, ?, ?, ?,?)";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, transportTruck.getTransportTruckId().toUpperCase());
				ps.setInt(2, transportTruck.getCompanyId());
				ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(4, transportTruck.getUserUpdated());
				ps.setInt(5, transportTruck.getPurchaseYear());
				ps.setString(6, transportTruck.getMake());
			}
		});
	}

	@Override
	public int updateTransportTruck(final TransportTruck transportTruck) throws SQLException {
		String query = "DELETE FROM pigtrax.\"TransportTruck\""
				+ " WHERE \"id\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, transportTruck.getId());
			}
		});
	}
			
	private static final class TransportTruckMapper implements RowMapper<TransportTruck> {
		public TransportTruck mapRow(ResultSet rs, int rowNum) throws SQLException {
			TransportTruck transportTruck = new TransportTruck();
			transportTruck.setId(rs.getInt("id"));
			transportTruck.setTransportTruckId(rs.getString("truckId"));
			transportTruck.setCompanyId(rs.getInt("id_Company"));
			transportTruck.setPurchaseYear(rs.getInt("purchaseYear"));
			transportTruck.setMake(rs.getString("make"));
			return transportTruck;
		}
	}

}
