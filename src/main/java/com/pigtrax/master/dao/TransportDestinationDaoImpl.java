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

import com.pigtrax.master.dao.interfaces.TransportDestinationDao;
import com.pigtrax.master.dto.TransportDestination;


@Repository
@Transactional
public class TransportDestinationDaoImpl implements TransportDestinationDao{
	
	private static final Logger logger = Logger.getLogger(TransportDestinationDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<TransportDestination> getTransportDestinationList(final int generatedCompanyId ) {
		String query = "SELECT \"id\", \"name\", \"address\", \"city\",\"state\", "+
						" \"id_Company\",\"id_MarketType\" from pigtrax.\"TransportDestination\" where \"id_Company\" = ? order by \"id\" desc ";

		List<TransportDestination> transportDestinationList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedCompanyId);
			}}, new TransportDestinationMapper());
		
		return transportDestinationList;
	}

	@Override
	public TransportDestination findByTransportDestinationByAutoGeneratedId(final int generatedTransportDestinationId) throws SQLException {
		
		String query = "SELECT \"id\", \"name\", \"address\", \"city\",\"state\","+
				" \"id_Company\",\"id_MarketType\" from pigtrax.\"TransportDestination\" where \"id\" = ? ";

		List<TransportDestination> transportDestinationList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedTransportDestinationId);
					}
				}, new TransportDestinationMapper());

		if (transportDestinationList != null && transportDestinationList.size() > 0) {
			return transportDestinationList.get(0);
		}

		return null;
	}
	

	@Override
	public int insertTransportDestinationRecord(final TransportDestination transportDestination) throws SQLException {
		String query = "INSERT INTO pigtrax.\"TransportDestination\"(  \"name\", \"address\", \"city\", "
				 +" \"lastUpdated\",\"userUpdated\",\"id_Company\",\"state\",\"id_MarketType\")"+
				 "VALUES ( ?, ?, ?, ?, ?, ?,?,?)";
	
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, transportDestination.getName());
				ps.setString(2, transportDestination.getAddress());
				ps.setString(3, transportDestination.getCity());
				ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(5, transportDestination.getUserUpdated());
				ps.setInt(6, transportDestination.getCompanyId());
				ps.setString(7, transportDestination.getState());
				ps.setObject(8,  transportDestination.getMarketTypeId());
			}
		});	
	}

	@Override
	public int updateTransportDestinationRecord( final TransportDestination transportDestination) throws SQLException {

		String query = "DELETE FROM pigtrax.\"TransportDestination\"  WHERE \"id\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, transportDestination.getId());
			}
		});
	}
		
	private static final class TransportDestinationMapper implements RowMapper<TransportDestination> {
		public TransportDestination mapRow(ResultSet rs, int rowNum) throws SQLException {
			TransportDestination transportDestination = new TransportDestination();
			transportDestination.setId(rs.getInt("id"));
			transportDestination.setName(rs.getString("name"));
			transportDestination.setAddress(rs.getString("address"));
			transportDestination.setCity(rs.getString("city"));
			transportDestination.setCompanyId(rs.getInt("id_Company"));
			transportDestination.setState(rs.getString("state"));
			transportDestination.setMarketTypeId((rs.getObject("id_MarketType") != null) ? rs.getInt("id_MarketType") : null) ;
			return transportDestination;
		}
	}	

}
