package com.pigtrax.pigevents.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class PigInfoDaoImpl implements PigInfoDao {
	private static final Logger logger = Logger.getLogger(PigInfoDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * To add pig information
	 * @param PigInfo
	 * @return int
	 */
	public int addPigInformation(final PigInfo pigInfo) throws SQLException, DuplicateKeyException {
		final String Qry = "insert into pigtrax.\"PigInfo\"(\"pigId\", \"sireId\", \"damId\", \"entryDate\", \"origin\", \"gline\", \"gcompany\", \"birthDate\", \"tattoo\", \"alternateTattoo\", \"remarks\", \"sowCondition\", \"lastUpdated\", \"userUpdated\", \"id_Company\", \"id_Pen\", \"id_Barn\", \"id_SexType\") "
				+ "values(?,?,?,current_timestamp,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setString(1, pigInfo.getPigId());
	    				ps.setString(2, pigInfo.getSireId());
	    				ps.setString(3, pigInfo.getDamId());
	    				ps.setString(4, pigInfo.getOrigin());
	    				ps.setString(5, pigInfo.getGline());
	    				ps.setString(6, pigInfo.getGcompany());
	    				ps.setDate(7,  new java.sql.Date(pigInfo.getBirthDate().getTime()));
	    				ps.setString(8, pigInfo.getTattoo());
	    				ps.setString(9, pigInfo.getAlternateTattoo());
	    				ps.setString(10, pigInfo.getRemarks());
	    				ps.setInt(11, pigInfo.getSowCondition());
	    				ps.setString(12, pigInfo.getUserUpdated());
	    				ps.setInt(13, pigInfo.getCompanyId());
	    				ps.setObject(14, pigInfo.getPenId());
	    				ps.setObject(15, pigInfo.getBarnId());
	    				ps.setInt(16, pigInfo.getSexTypeId());
	    			
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
		
	}
	
	
	
	
	/**
	 * To add pig information
	 * @param PigInfo
	 * @return int
	 */
	public int updatePigInformation(final PigInfo pigInfo) throws SQLException, DuplicateKeyException {
		String Qry = "update pigtrax.\"PigInfo\" set \"pigId\"=?, \"sireId\" = ?, \"damId\" = ?, \"origin\"= ?, \"gline\"= ?, \"gcompany\" = ?, \"birthDate\" = ?, \"tattoo\" = ?, \"alternateTattoo\" = ?, \"remarks\" = ?, \"sowCondition\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"id_Company\" = ?, \"id_Pen\" = ?, \"id_Barn\" = ?, \"id_SexType\" =? where \"id\" = ? ";
		
		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				logger.info("company id in dao impl = "+pigInfo.getCompanyId());
				
				ps.setString(1, pigInfo.getPigId());
				ps.setString(2, pigInfo.getSireId());
				ps.setString(3, pigInfo.getDamId());
				ps.setString(4, pigInfo.getOrigin());
				ps.setString(5, pigInfo.getGline());
				ps.setString(6, pigInfo.getGcompany());
				ps.setDate(7,  new java.sql.Date(pigInfo.getBirthDate().getTime()));
				ps.setString(8, pigInfo.getTattoo());
				ps.setString(9, pigInfo.getAlternateTattoo());
				ps.setString(10, pigInfo.getRemarks());
				ps.setInt(11, pigInfo.getSowCondition());
				ps.setString(12, pigInfo.getUserUpdated());
				ps.setInt(13, pigInfo.getCompanyId());
				ps.setObject(14, pigInfo.getPenId());
				ps.setObject(15, pigInfo.getBarnId());
				ps.setInt(16, pigInfo.getSexTypeId());				
				ps.setInt(17, pigInfo.getId());
			}
		});
		
	}
	
	
	/**
	 * Get the pig information based on pigId
	 */
	public PigInfo getPigInformationByPigId(final String pigId, final Integer companyId) throws SQLException {
		String qry = "Select \"id\", \"pigId\", \"sireId\", \"damId\",\"origin\", \"gline\", \"gcompany\", \"birthDate\",\"tattoo\",\"alternateTattoo\", \"remarks\", \"sowCondition\", \"id_Company\", \"id_Pen\", \"id_Barn\", \"id_SexType\"  from pigtrax.\"PigInfo\" where \"pigId\" = ? and \"id_Company\" = ?";
		List<PigInfo> pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, pigId.toUpperCase());
				ps.setInt(2, companyId);
			}}, new PigInfoMapper());

		if(pigInfoList != null && pigInfoList.size() > 0){
			return pigInfoList.get(0);
		}
		return null;
	}
	
	
	/**
	 * Get the pig information based on tattoo
	 */
	public PigInfo getPigInformationByTattoo(final String tattoo, final Integer companyId) throws SQLException {
		String qry = "Select \"id\", \"pigId\", \"sireId\", \"damId\",\"origin\", \"gline\", \"gcompany\", \"birthDate\",\"tattoo\",\"alternateTattoo\", \"remarks\", \"sowCondition\", \"id_Company\", \"id_Pen\", \"id_Barn\", \"id_SexType\" from pigtrax.\"PigInfo\" where \"tattoo\" = ? and \"id_Company\" = ?";
		List<PigInfo> pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tattoo.trim());
				ps.setInt(2, companyId);
			}}, new PigInfoMapper());

		if(pigInfoList != null && pigInfoList.size() > 0){
			return pigInfoList.get(0);
		}
		return null;
	}
	
	private static final class PigInfoMapper implements RowMapper<PigInfo> {
		public PigInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			PigInfo pigInfo = new PigInfo();
			pigInfo.setId(rs.getInt("id"));
			pigInfo.setPigId(rs.getString("pigId"));
			pigInfo.setSireId(rs.getString("sireId"));
			pigInfo.setDamId(rs.getString("damId"));
			pigInfo.setOrigin(rs.getString("origin"));
			pigInfo.setGline(rs.getString("gline"));
			pigInfo.setGcompany(rs.getString("gcompany"));
			pigInfo.setBirthDate(rs.getDate("birthDate"));
			pigInfo.setTattoo(rs.getString("tattoo"));
			pigInfo.setAlternateTattoo(rs.getString("alternateTattoo"));
			pigInfo.setRemarks(rs.getString("remarks"));
			pigInfo.setSowCondition(rs.getInt("sowCondition"));
			pigInfo.setCompanyId(rs.getInt("id_Company"));
			pigInfo.setBarnId((rs.getObject("id_Barn")!=null)?(Integer)rs.getObject("id_Barn") : null);
			pigInfo.setPenId((rs.getObject("id_Pen")!=null)?(Integer)rs.getObject("id_Pen") : null);
			pigInfo.setSexTypeId(rs.getInt("id_SexType"));
			return pigInfo;
		}
	}
	
	/**
	 * To delete the given information
	 * @param id
	 */
	
	@Override
	public void deletePigInfo(final Integer id) throws SQLException {
		
		final String qry = "delete from pigtrax.\"PigInfo\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
	}
	
	
	
	/**
	 * Get the pig information based on pigId
	 */
	@Override
	public PigInfo getPigInformationById(final Integer pigInfoId) throws SQLException {
		String qry = "Select \"id\", \"pigId\", \"sireId\", \"damId\",\"origin\", \"gline\", \"gcompany\", \"birthDate\",\"tattoo\",\"alternateTattoo\", \"remarks\", \"sowCondition\", \"id_Company\", \"id_Pen\", \"id_Barn\", \"id_SexType\"  from pigtrax.\"PigInfo\" where \"id\" = ? ";
		List<PigInfo> pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setInt(1, pigInfoId);
			}}, new PigInfoMapper());

		if(pigInfoList != null && pigInfoList.size() > 0){
			return pigInfoList.get(0);
		}
		return null;
	}
}

