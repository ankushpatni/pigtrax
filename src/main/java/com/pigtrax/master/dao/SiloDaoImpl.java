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

import com.pigtrax.master.dao.interfaces.SiloDao;
import com.pigtrax.master.dto.Silo;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class SiloDaoImpl implements SiloDao {

	private static final Logger logger = Logger.getLogger(SiloDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Silo> getSiloList(final int generatedBarnId) {
		String query = "SELECT \"id\",\"siloId\", \"location\",\"id_Barn\", \"id_SiloType\" from pigtrax.\"Silo\" where \"id_Barn\" = ? order by \"id\" desc ";

		List<Silo> siloList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedBarnId);
					}
				}, new SiloMapper());

		return siloList;
	}

	@Override
	public int updateSiloStatus(final String siloID, final Boolean siloStatus)
			throws SQLException {
		String query = "update pigtrax.\"Silo\" SET \"isActive\"=?  WHERE \"siloId\"=?";

		logger.info("siloStatus-->" + siloStatus);
		logger.info("siloID-->" + siloID);
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, !siloStatus);
				ps.setString(2, siloID.toUpperCase());
			}
		});
	}
	
	@Override	
	public Silo findBySiloByAutoGeneratedId(final int generatedSiloId) throws SQLException
	{
		String query = "SELECT \"id\",\"siloId\", \"location\",\"id_Barn\", \"id_SiloType\" from pigtrax.\"Silo\" where \"id\" = ?";
		
		List<Silo> siloList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedSiloId);
					}
				}, new SiloMapper());

		if (siloList != null && siloList.size() > 0) {
			return siloList.get(0);
		}

		return null;
	}
	

	@Override
	public int insertSiloRecord(final Silo silo) throws SQLException {
		String query = "INSERT INTO pigtrax.\"Silo\"(  \"siloId\", \"id_Barn\", location, "
				+ " \"lastUpdated\",\"userUpdated\",\"id_SiloType\")"
				+ "VALUES ( ?, ?, ?, ?, ?, ?)";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, silo.getSiloId().toUpperCase());
				ps.setInt(2, silo.getBarnId());
				ps.setString(3, silo.getLocation());
				ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(5, UserUtil.getLoggedInUser());
				if(silo.getSiloTypeId() != 0)
				{
					ps.setInt(6, silo.getSiloTypeId());
				}
				else
				{
					ps.setInt(6, java.sql.Types.INTEGER);
				}
			}
		});
	}

	@Override
	public int updateSiloRecord(final Silo silo) throws SQLException {
		String query = "update pigtrax.\"Silo\" SET location=?, \"lastUpdated\"=?,"
				+ " \"userUpdated\"=?, \"id_SiloType\"=?  WHERE \"siloId\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setString(1, silo.getLocation());
				ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(3, UserUtil.getLoggedInUser());
				if(silo.getSiloTypeId() != 0)
				{
					ps.setInt(4, silo.getSiloTypeId());
				}
				else
				{
					ps.setInt(4, java.sql.Types.INTEGER);
				}
				ps.setString(5, silo.getSiloId());

			}
		});
	}
	
	public List<Silo> getSiloListBasedOnCompanyId( final int generatedCompanyId ) throws SQLException
	{
		String query = "SELECT \"siloserrialid\" as \"id\",\"siloId\" from pigtrax.\"CompPremBarnSiloVw\" where \"siloId\" != '' and companyserialid = ?";
	//CompPremBarnRoomPenVw
		List<Silo> siloList = jdbcTemplate.query(query,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, generatedCompanyId);
					}
				}, new SiloMapperList());
	
		if (siloList != null && siloList.size() > 0) {
			return  siloList;
		}
		return null;
	}

	private static final class SiloMapper implements RowMapper<Silo> {
		public Silo mapRow(ResultSet rs, int rowNum) throws SQLException {
			Silo silo = new Silo();
			silo.setId(rs.getInt("id"));
			silo.setSiloId(rs.getString("siloId"));
			silo.setBarnId(rs.getInt("id_Barn"));
			silo.setLocation(rs.getString("location"));
			silo.setSiloTypeId(rs.getInt("id_SiloType"));
			return silo;
		}
	}
	
	private static final class SiloMapperList implements RowMapper<Silo> {
		public Silo mapRow(ResultSet rs, int rowNum) throws SQLException {
			Silo silo = new Silo();
			silo.setId(rs.getInt("id"));
			silo.setSiloId(rs.getString("siloId"));
			return silo;
		}
	}

}
