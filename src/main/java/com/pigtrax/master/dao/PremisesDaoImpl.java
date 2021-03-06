package com.pigtrax.master.dao;

import java.awt.datatransfer.StringSelection;
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
import org.springframework.util.StringUtils;

import com.pigtrax.master.dao.interfaces.PremisesDao;
import com.pigtrax.master.dto.Premises;

@Repository
@Transactional
public class PremisesDaoImpl implements PremisesDao {

	private static final Logger logger = Logger.getLogger(PremisesDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Premises> getPremisesList(final int generatedCompanyId, String premisesType) {
		String query = "SELECT \"id\",\"permiseId\", \"id_Company\", \"name\", \"address\", \"city\", \"state\", \"zipcode\", \"isActive\",\"gpsLatittude\",\"gpsLongitude\","
				+ "\"id_PremiseType\",\"sowSource\",\"otherCity\", \"lactationLength\" "
				+ "from pigtrax.\"Premise\" where \"id_Company\" = ? ";
		if (!StringUtils.isEmpty(premisesType) && !premisesType.equalsIgnoreCase("null")) {
			query = query + " and \"id_PremiseType\" in (" + premisesType + ")";
		}

		query = query + "order by \"name\" ";

		List<Premises> premisesList = jdbcTemplate.query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedCompanyId);
			}
		}, new PremisesMapper());

		return premisesList;
	}

	@Override
	public List<Premises> getPremisesListNotInFilterPremisesType(final int generatedCompanyId, String premisesType) {
		String query = "SELECT \"id\",\"permiseId\", \"id_Company\", \"name\", \"address\", \"city\", \"state\", \"zipcode\", \"isActive\",\"gpsLatittude\",\"gpsLongitude\","
				+ "\"id_PremiseType\",\"sowSource\",\"otherCity\",\"lactationLength\" "
				+ "from pigtrax.\"Premise\" where \"id_Company\" = ? ";
		if (!StringUtils.isEmpty(premisesType) && !premisesType.equalsIgnoreCase("null")) {
			query = query + " and \"id_PremiseType\" not in (" + premisesType + ")";
		}

		query = query + "order by \"name\" ";

		List<Premises> premisesList = jdbcTemplate.query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedCompanyId);
			}
		}, new PremisesMapper());

		return premisesList;
	}

	@Override
	public List<Premises> getPremisesListBySowSource(final int generatedCompanyId, String premisesType) {
		String query = "SELECT \"id\",\"permiseId\", \"id_Company\", \"name\", \"address\", \"city\", \"state\", \"zipcode\", \"isActive\",\"gpsLatittude\",\"gpsLongitude\","
				+ "\"id_PremiseType\",\"sowSource\",\"otherCity\",\"lactationLength\" "
				+ "from pigtrax.\"Premise\" where \"id_Company\" = ? and lower(\"sowSource\") = ? ";

		if (!StringUtils.isEmpty(premisesType) && !premisesType.equalsIgnoreCase("null")) {
			query = query + " and \"id_PremiseType\" in (" + premisesType + ")";
		}

		query += " order by \"name\" ";

		List<Premises> premisesList = jdbcTemplate.query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedCompanyId);
				ps.setString(2, "yes");
			}
		}, new PremisesMapper());

		return premisesList;
	}

	@Override
	public Premises findByPremisesByAutoGeneratedId(final int generatedPremisesId) {
		String query = "SELECT \"id\",\"permiseId\", \"id_Company\", \"name\", \"address\", \"city\", \"state\", \"zipcode\", \"isActive\",\"gpsLatittude\","
				+ "\"gpsLongitude\",\"id_PremiseType\",\"sowSource\",\"otherCity\",\"lactationLength\" "
				+ " from pigtrax.\"Premise\" where \"id\" = ? ";

		List<Premises> premisesList = jdbcTemplate.query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedPremisesId);
			}
		}, new PremisesMapper());

		if (premisesList != null && premisesList.size() > 0) {
			return premisesList.get(0);
		}

		return null;
	}

	@Override
	public int updatePremisesStatus(final String premisesID, final Boolean premisesStatus) throws SQLException {
		String query = "update pigtrax.\"Premise\" SET \"isActive\"=?  WHERE \"permiseId\"=?";

		logger.info("Status-->" + premisesStatus);
		logger.info("Id-->" + premisesID);
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, !premisesStatus);
				ps.setString(2, premisesID.toUpperCase());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pigtrax.usermanagement.dao.interfaces.PremisesDao#
	 * insertPremisesRecord(com.pigtrax.master.location.Premises) INSERT INTO
	 * pigtrax."Premise"( id, "permiseId", "id_Company", name, address, city,
	 * state, zipcode, "isActive", "lastUpdated", "userUpdated")
	 */
	@Override
	public int insertPremisesRecord(final Premises premises) throws SQLException {
		String query = "INSERT INTO pigtrax.\"Premise\"(  \"permiseId\", \"id_Company\", name, address, city, state, zipcode, "
				+ " \"isActive\", \"lastUpdated\",\"userUpdated\",\"gpsLatittude\",\"gpsLongitude\",\"id_PremiseType\",\"sowSource\",\"otherCity\",\"lactationLength\")"
				+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, premises.getPermiseId().toUpperCase());
				ps.setInt(2, premises.getCompanyId());
				ps.setString(3, premises.getName());
				ps.setString(4, premises.getAddress());
				ps.setString(5, premises.getCity());
				ps.setString(6, premises.getState());
				ps.setString(7, premises.getZipcode());
				ps.setBoolean(8, premises.isActive());
				ps.setDate(9, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(10, premises.getUserUpdated());
				ps.setString(11, premises.getGpsLatittude());
				ps.setString(12, premises.getGpsLongitude());
				if (premises.getPremiseTypeId() != null && premises.getPremiseTypeId() != 0)
					ps.setInt(13, premises.getPremiseTypeId());
				else
					ps.setNull(13, java.sql.Types.INTEGER);
				ps.setString(14, premises.getSowSource());
				ps.setString(15, premises.getOtherCity());
				if (premises.getLactationLengthInWeeks() != null && premises.getLactationLengthInWeeks() > 0)
					ps.setInt(16, premises.getLactationLengthInWeeks());
				else
					ps.setNull(16, java.sql.Types.INTEGER);
			}
		});
	}

	@Override
	public int updatePremisesRecord(final Premises premises) throws SQLException {
		String query = "update pigtrax.\"Premise\" SET name=?, address=?, city =?, state =?, zipcode=?, \"lastUpdated\"=?, "
				+ "\"userUpdated\"=?,\"gpsLatittude\"=?, \"gpsLongitude\"=?,\"id_PremiseType\"=?, \"sowSource\" = ?, \"otherCity\" = ?, \"lactationLength\" = ? "
				+ "  WHERE \"permiseId\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, premises.getName());
				ps.setString(2, premises.getAddress());
				ps.setString(3, premises.getCity());
				ps.setString(4, premises.getState());
				ps.setString(5, premises.getZipcode());
				ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(7, premises.getUserUpdated());
				ps.setString(8, premises.getGpsLatittude());
				ps.setString(9, premises.getGpsLongitude());
				if (premises.getPremiseTypeId() != null && premises.getPremiseTypeId() != 0)
					ps.setInt(10, premises.getPremiseTypeId());
				else
					ps.setNull(10, java.sql.Types.INTEGER);
				ps.setString(11, premises.getSowSource());
				ps.setString(12, premises.getOtherCity());
				if (premises.getLactationLengthInWeeks() != null && premises.getLactationLengthInWeeks() > 0)
					ps.setInt(13, premises.getLactationLengthInWeeks());
				else
					ps.setNull(13, java.sql.Types.INTEGER);
				ps.setString(14, premises.getPermiseId().toUpperCase());
			}
		});
	}

	private static final class PremisesMapper implements RowMapper<Premises> {
		public Premises mapRow(ResultSet rs, int rowNum) throws SQLException {
			Premises premises = new Premises();
			premises.setId(rs.getInt("id"));
			premises.setPermiseId(rs.getString("permiseId"));
			premises.setCompanyId(rs.getInt("id_Company"));
			premises.setName(rs.getString("name"));
			premises.setAddress(rs.getString("address"));
			premises.setCity(rs.getString("city"));
			premises.setState(rs.getString("state"));
			premises.setZipcode(rs.getString("zipcode"));
			premises.setActive(rs.getBoolean("isActive"));
			premises.setGpsLatittude(rs.getString("gpsLatittude"));
			premises.setGpsLongitude(rs.getString("gpsLongitude"));
			premises.setPremiseTypeId(rs.getInt("id_PremiseType"));
			premises.setSowSource(rs.getString("sowSource"));
			premises.setOtherCity(rs.getString("otherCity"));
			premises.setLactationLengthInWeeks(rs.getInt("lactationLength"));
			return premises;
		}
	}

	/*
	 * Get barn list based on companyId from View
	 * 
	 */

	public List<Premises> getPremisesListBasedOnCompanyId(final int generatedCompanyId) throws SQLException {
		String query = "SELECT \"id\",\"permiseId\",\"name\" from pigtrax.\"Premise\" where \"id_Company\" = ? order by \"name\"";
		// CompPremBarnRoomPenVw
		List<Premises> premisesList = jdbcTemplate.query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedCompanyId);
			}
		}, new PremisesMapperList());

		if (premisesList != null && premisesList.size() > 0) {
			return premisesList;
		}
		return null;
	}

	@Override
	public List<Premises> getPremisesListBasedOnCompanyIdFromView(final int generatedCompanyId) throws SQLException {
		// CompPremBarnRoomPenVw

		String query = "SELECT \"id\",\"permiseId\", \"id_Company\", \"name\", \"address\", \"city\", \"state\", \"zipcode\", \"isActive\",\"gpsLatittude\","
				+ "\"gpsLongitude\",\"id_PremiseType\",\"sowSource\",\"otherCity\", \"lactationLength\" "
				+ " from pigtrax.\"Premise\" where \"id\" in ( SELECT \"premiseserialid\" from pigtrax.\"CompPremBarnSiloVw\" where \"permiseId\" != '' and companyserialid = ? ) order by \"name\" ";

		List<Premises> premisesList = jdbcTemplate.query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedCompanyId);
			}
		}, new PremisesMapper());

		if (premisesList != null && premisesList.size() > 0) {
			return premisesList;
		}
		return null;
	}

	private static final class PremisesMapperList implements RowMapper<Premises> {
		public Premises mapRow(ResultSet rs, int rowNum) throws SQLException {
			Premises premises = new Premises();
			premises.setId(rs.getInt("id"));
			premises.setPermiseId(rs.getString("permiseId"));
			premises.setName(rs.getString("name"));
			return premises;
		}
	}

	@Override
	public int deletePremiseData(final int generatedPremisesId) {

		final String qry = "delete from pigtrax.\"Premise\" where \"id\" = ?";

		int rowsDeleted = this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedPremisesId);
			}
		});

		return rowsDeleted;
	}

	public List<Premises> getAllPremises() {
		String query = "SELECT \"id\",\"permiseId\", \"id_Company\", \"name\", \"address\", \"city\", \"state\", \"zipcode\", \"isActive\",\"gpsLatittude\",\"gpsLongitude\","
				+ "\"id_PremiseType\",\"sowSource\",\"otherCity\", \"lactationLength\" " + "from pigtrax.\"Premise\" ";
		query = query + "order by \"name\" ";

		List<Premises> premisesList = jdbcTemplate.query(query, new PremisesMapper());

		return premisesList;
	}

	public List<Premises> getPremisesListFromSowSource(final int generatedCompanyId, final int sowSource) {

		String qry="";
		if (sowSource == -1){
			 qry = "select "
					+ " \"id\",\"permiseId\", \"id_Company\", \"name\", \"address\", \"city\", \"state\", \"zipcode\", \"isActive\",\"gpsLatittude\",\"gpsLongitude\","
					+ "\"id_PremiseType\",\"sowSource\",\"otherCity\", \"lactationLength\""
					+ " from pigtrax.\"Premise\" where \"id\" in "
					+ "( "
					+ "select distinct(\"id_Premise\") from pigtrax.\"GroupEvent\" where \"groupId\" in "
					+ "("
					+ "select \"groupId\" from pigtrax.\"GroupEvent\" where \"id_Premise\" in "
					+ " ("
					+ " select \"id\" from pigtrax.\"Premise\" where \"sowSource\" like 'Yes' and \"id_Company\" = ?  "
					+ ")"
					+ ")"
					+ ")"
					+ "  order by \"name\" ";
			
		}
		else{
			 qry = "select "
					+ " \"id\",\"permiseId\", \"id_Company\", \"name\", \"address\", \"city\", \"state\", \"zipcode\", \"isActive\",\"gpsLatittude\",\"gpsLongitude\","
					+ "\"id_PremiseType\",\"sowSource\",\"otherCity\", \"lactationLength\""
					+ " from pigtrax.\"Premise\" where \"id\" in "
					+ "( select distinct(\"id_Premise\") from pigtrax.\"GroupEvent\" where \"groupId\" in "
					+ "(select \"groupId\" from pigtrax.\"GroupEvent\" where \"id_Premise\" in (?) and \"id_Company\" = ?  )) order by \"name\" ";
			
		}
		String query = "select \"name\" from pigtrax.\"Premise\" where \"id\" in "
				+ "( select \"id_Premise\" from pigtrax.\"GroupEvent\" where \"id_Premise\" in "
				+ "(select \"id\" from pigtrax.\"Premise\" where \"sowSource\" like 'Yes' and \"id\"=?) ) and \"id_Company\" = ? ";

		List<Premises> premisesList = jdbcTemplate.query(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				if (sowSource == -1){
					ps.setInt(1, generatedCompanyId);
					
				}else{
					ps.setInt(1, sowSource);
					ps.setInt(2	, generatedCompanyId);
					
				}
			}
		}, new PremisesMapper());

		return premisesList;
	}

}
