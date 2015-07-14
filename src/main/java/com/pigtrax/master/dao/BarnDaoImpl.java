package com.pigtrax.master.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.master.dao.interfaces.BarnDao;
import com.pigtrax.master.dto.Barn;
import com.pigtrax.pigevents.dto.BarnDto;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class BarnDaoImpl implements BarnDao {

	private static final Logger logger = Logger.getLogger(BarnDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Barn> getBarnList(final int generatedPremisesId) {
		String query = "SELECT \"id\",\"barnId\", \"id_Premise\", \"id_PhaseType\", \"location\", \"area\", \"feederCount\","+
						" \"waterAccessCount\", \"isActive\", \"id_VentilationType\" from pigtrax.\"Barn\" where \"id_Premise\" = ? order by \"id\" desc ";

		List<Barn> barnList = jdbcTemplate.query(query, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, generatedPremisesId);
			}}, new BarnMapper());
		
		return barnList;
	}

	@Override
	public int updateBarnStatus(final String barnID, final Boolean barnStatus)
			throws SQLException {
		String query = "update pigtrax.\"Barn\" SET \"isActive\"=?  WHERE \"barnId\"=?";

		logger.info("Status-->" + barnStatus);
		logger.info("barnID-->" + barnID);
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, !barnStatus);
				ps.setString(2, barnID.toUpperCase());
			}
		});
	}
	

	@Override
	public int insertBarnRecord(final Barn barn) throws SQLException {
		String query = "INSERT INTO pigtrax.\"Barn\"(  \"barnId\", \"id_Premise\", \"id_PhaseType\", location, area, \"feederCount\", \"waterAccessCount\", "
				 +" \"isActive\", \"lastUpdated\",\"userUpdated\",\"id_VentilationType\")"+
				 "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, barn.getBarnId().toUpperCase());
				ps.setInt(2, barn.getPremiseId());
				ps.setInt(3, barn.getPhaseTypeId());
				ps.setString(4, barn.getLocation());
				ps.setInt(5, barn.getArea());
				ps.setInt(6, barn.getFeederCount());
				ps.setInt(7, barn.getWaterAccessCount());
				ps.setBoolean(8, barn.isActive());
				ps.setDate(9, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(10, UserUtil.getLoggedInUser());
				ps.setInt(11, barn.getVentilationTypeId());
			}
		});	
	}

	@Override
	public int updateBarnRecord(final Barn barn) throws SQLException {
		String query = "update pigtrax.\"Barn\" SET \"id_PhaseType\"=?, location=?, area=?, \"feederCount\"=?, \"waterAccessCount\"=?, \"lastUpdated\"=?,"+
						" \"userUpdated\"=?,\"id_VentilationType\"=?  WHERE \"barnId\"=?";
		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, barn.getPhaseTypeId());
				ps.setString(2, barn.getLocation());
				ps.setInt(3, barn.getArea());
				ps.setInt(4, barn.getFeederCount());
				ps.setInt(5, barn.getWaterAccessCount());
				ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(7, UserUtil.getLoggedInUser());
				ps.setInt(8, barn.getVentilationTypeId());
				ps.setString(9, barn.getBarnId().toUpperCase());
			}
		});
	}
		
	private static final class BarnMapper implements RowMapper<Barn> {
		public Barn mapRow(ResultSet rs, int rowNum) throws SQLException {
			Barn barn = new Barn();
			barn.setId(rs.getInt("id"));
			barn.setBarnId(rs.getString("barnId"));
			barn.setPremiseId(rs.getInt("id_Premise"));
			barn.setPhaseTypeId(rs.getInt("id_PhaseType"));
			barn.setLocation(rs.getString("location"));
			barn.setArea(rs.getInt("area"));
			barn.setFeederCount(rs.getInt("feederCount"));
			barn.setWaterAccessCount(rs.getInt("waterAccessCount"));
			barn.setActive(rs.getBoolean("isActive"));
			barn.setVentilationTypeId(rs.getInt("id_VentilationType"));
			return barn;
		}
	}
	
	
	/**
	 * To retrieve the list of Barns in a company
	 * 
	 * @throws SQLException
	 */
	public List<BarnDto> getBarns(Integer companyId) throws SQLException {
		BarnDto dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<BarnDto> barnList = new ArrayList<BarnDto>();
		String Qry = "SELECT \"id\", \"barnId\",\"id_Premise\",\"id_PhaseType\", \"location\", \"area\",\"feederCount\",\"waterAccessCount\",\"id_VentilationType\" from pigtrax.\"Barn\" where \"id_Premise\" in ( Select \"id\" from pigtrax.\"Premise\" where \"id_Company\" = ?::smallint)";
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			pstmt = conn.prepareStatement(Qry);
			pstmt.setString(1, String.valueOf(companyId));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new BarnDto();
				dto.setId(rs.getInt(1));
				dto.setBarnId(rs.getString(2));
				dto.setPremiseId(rs.getInt(3));
				dto.setPhaseTypeId(rs.getInt(4));
				dto.setLocation(rs.getString(5));
				dto.setArea(rs.getInt(6));
				dto.setFeederCount(rs.getInt(7));
				dto.setWaterAccessCount(rs.getInt(8));
				//dto.setActive(rs.getBoolean(9));
				//dto.setLastUpdated(rs.getDate(9));
				//dto.setUserUpdated(rs.getString(10));
				dto.setVentilationTypeId(rs.getInt(9));
				barnList.add(dto);
			}
		} finally {
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			conn.close();
		}
		return barnList;
	}

}
