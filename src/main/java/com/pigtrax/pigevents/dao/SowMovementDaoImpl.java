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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.SowMovement;
import com.pigtrax.pigevents.dao.interfaces.SowMovementDao;

@Repository
@Transactional
public class SowMovementDaoImpl implements SowMovementDao{
	
private static final Logger logger = Logger.getLogger(SowMovementDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	@Override
	public int addSowMovement(final SowMovement sowMovement) throws SQLException {
		final String Qry = "insert into pigtrax.\"SowMovement\"(\"id_PigInfo\", \"id_Room\", \"id_Premise\", \"movementDate\","
				+ "  \"lastUpdated\",\"userUpdated\",\"id_Company\") "
				+ "values(?,?,?,current_timestamp,current_timestamp,?,?)";	
		

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				ps.setInt(1, sowMovement.getPigInfoId());
				ps.setInt(2, sowMovement.getRoomId());
				ps.setInt(3, sowMovement.getPremiseId());
				ps.setString(4, sowMovement.getUserUpdated());
				ps.setInt(5, sowMovement.getCompanyId());
				return ps;
			
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}
	
	
	/**
	 * To delete the given information
	 * @param id
	 */
	
	@Override
	public void deleteSowMovement(final Integer id) throws SQLException {
		
		final String qry = "delete from pigtrax.\"SowMovement\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
	}
	
	@Override
	public int updateSowMovement(final SowMovement sowMovement) throws SQLException {
		final String Qry = "update pigtrax.\"SowMovement\" set  \"id_Room\"=?, \"id_Premise\"=?, "
				+ "  \"lastUpdated\"= current_timestamp ,\"userUpdated\"=?  where \"id\" = ? and \"id_Company\" = ?";	
		
		logger.info("Updating moment in dao impl = "+sowMovement.getPigInfoId());
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, sowMovement.getRoomId());
				ps.setInt(2, sowMovement.getPremiseId());
				ps.setString(3, sowMovement.getUserUpdated());
				ps.setInt(4, sowMovement.getId());
				ps.setInt(5, sowMovement.getCompanyId());
			}
		});
	}
	
	/**
	 * Get the pig information based on companyId
	 */
	@Override
	public List<SowMovement> getSowMovementListByCompanyId( final Integer companyId) throws SQLException {
		String qry = "Select \"id_PigInfo\", \"id_Room\", \"id_Premise\", \"movementDate\", \"id_Company\" "
				+ "from pigtrax.\"SowMovement\" where \"id_PigInfo\" in ( Select \"id\" from pigtrax.\"PigInfo\" where \"id_Company\" = ?) and \"id_Company\" = ? order by \"lastUpdated\" desc";
		List<SowMovement> sowMovementList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			
			public void setValues(PreparedStatement ps) throws SQLException {				
				ps.setInt(1, companyId);
				ps.setInt(2, companyId);
			}}, new SowMovementMapper());

		if(sowMovementList != null && sowMovementList.size() > 0){
			return sowMovementList;
		}
		return null;
	}
	
	/**
	 * Get the pig information based on pigInfoId
	 */
	@Override
	public List<SowMovement> getSowMovementListByPigInfoId( final String pigInfoId, final Integer companyId, final Integer premisesId) throws SQLException {
		String qry = "Select \"id\", \"id_PigInfo\", \"id_Room\", \"id_Premise\", \"movementDate\", \"id_Company\" "
				+ "from pigtrax.\"SowMovement\" where \"id_PigInfo\" in ( Select \"id\" from pigtrax.\"PigInfo\" where \"pigId\" = ? and \"id_Company\" = ? and \"id_Premise\" = ? ) and \"id_Company\" = ? order by \"lastUpdated\" desc";
		List<SowMovement> sowMovementList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			
			public void setValues(PreparedStatement ps) throws SQLException {				
				ps.setString(1, pigInfoId);
				ps.setInt(2, companyId);
				ps.setInt(3, premisesId);
				ps.setInt(4, companyId);
			}}, new SowMovementMapper());

		if(sowMovementList != null && sowMovementList.size() > 0){
			return sowMovementList;
		}
		return null;
	}
	
	private static final class SowMovementMapper implements RowMapper<SowMovement> {
		public SowMovement mapRow(ResultSet rs, int rowNum) throws SQLException {
			SowMovement sowMovement = new SowMovement();
			sowMovement.setId(rs.getInt("id"));
			sowMovement.setPigInfoId(rs.getInt("id_PigInfo"));
			sowMovement.setRoomId(rs.getInt("id_Room"));
			sowMovement.setPremiseId(rs.getInt("id_Premise"));
			sowMovement.setMovementDate(rs.getDate("movementDate"));
			sowMovement.setCompanyId(rs.getInt("id_Company"));
			return sowMovement;
		}
	}
}
