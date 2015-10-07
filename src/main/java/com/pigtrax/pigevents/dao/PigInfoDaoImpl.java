package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
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
import com.pigtrax.pigevents.dto.PigInfoDto;

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
	@Override
	public int addPigInformation(final PigInfo pigInfo) throws SQLException, DuplicateKeyException {
		final String Qry = "insert into pigtrax.\"PigInfo\"(\"pigId\", \"sireId\", \"damId\", \"entryDate\", \"origin\", \"gline\", \"gcompany\", "
				+ "\"birthDate\", \"tattoo\", \"alternateTattoo\", \"remarks\", \"lastUpdated\", \"userUpdated\", \"id_Company\", \"id_Pen\", "
				+ "\"id_Barn\", \"id_SexType\", \"isActive\",\"id_GfunctionType\") "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() { 
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setString(1, pigInfo.getPigId());
	    				ps.setString(2, pigInfo.getSireId());
	    				ps.setString(3, pigInfo.getDamId());
	    				ps.setDate(4,  new java.sql.Date(pigInfo.getEntryDate().getTime()));
	    				ps.setString(5, pigInfo.getOrigin());
	    				ps.setString(6, pigInfo.getGline());
	    				ps.setString(7, pigInfo.getGcompany());
	    				if(pigInfo.getBirthDate() != null)
	    					ps.setDate(8,  new java.sql.Date(new DateTime(pigInfo.getBirthDate()).toLocalDate().toDateMidnight().getMillis()));
	    				else
	    					ps.setNull(8, java.sql.Types.DATE);
	    				
	    				ps.setString(9, pigInfo.getTattoo());
	    				ps.setString(10, pigInfo.getAlternateTattoo());
	    				ps.setString(11, pigInfo.getRemarks());	    				
	    				ps.setString(12, pigInfo.getUserUpdated());
	    				
	    				ps.setInt(13, pigInfo.getCompanyId());
	    				
	    				if(pigInfo.getPenId() != null && pigInfo.getPenId() != 0)
	    					ps.setObject(14, pigInfo.getPenId());
	    				else
	    					ps.setNull(14,  java.sql.Types.INTEGER);
	    				
	    				ps.setObject(15, pigInfo.getBarnId(), java.sql.Types.INTEGER);
	    				ps.setObject(16, pigInfo.getSexTypeId(), java.sql.Types.INTEGER);
	    				ps.setBoolean(17, true);
	    				ps.setObject(18, pigInfo.getGfunctionTypeId(), java.sql.Types.INTEGER);
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
		String Qry = "update pigtrax.\"PigInfo\" set \"pigId\"=?, \"sireId\" = ?, \"damId\" = ?, \"origin\"= ?, \"gline\"= ?, "
				+ "\"gcompany\" = ?, \"birthDate\" = ?, \"tattoo\" = ?, \"alternateTattoo\" = ?, "
				+ "\"remarks\" = ?, \"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"id_Company\" = ?,"
				+ " \"id_Pen\" = ?, \"id_Barn\" = ?, \"id_SexType\" =?, \"entryDate\" = ?, \"isActive\" = ?,\"id_GfunctionType\"=?  where \"id\" = ? ";
		
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
				if(pigInfo.getBirthDate() != null)
					ps.setDate(7,  new java.sql.Date(pigInfo.getBirthDate().getTime()));
				else
					ps.setNull(7, java.sql.Types.DATE);
				ps.setString(8, pigInfo.getTattoo());
				ps.setString(9, pigInfo.getAlternateTattoo());
				ps.setString(10, pigInfo.getRemarks());				
				ps.setString(11, pigInfo.getUserUpdated());
				ps.setInt(12, pigInfo.getCompanyId());
				if(pigInfo.getPenId() != null && pigInfo.getPenId() != 0)
					ps.setObject(13, pigInfo.getPenId());
				else
					ps.setNull(13, java.sql.Types.INTEGER);
				ps.setObject(14, pigInfo.getBarnId(), java.sql.Types.INTEGER);
				ps.setObject(15, pigInfo.getSexTypeId(), java.sql.Types.INTEGER);		
				ps.setDate(16,  new java.sql.Date(pigInfo.getEntryDate().getTime()));
				ps.setBoolean(17,pigInfo.isActive());
				ps.setObject(18, pigInfo.getGfunctionTypeId(), java.sql.Types.INTEGER);	
				ps.setInt(19, pigInfo.getId());
			}
		});
		
	}
	
	
	/**
	 * Get the pig information based on pigId
	 */
	public PigInfo getPigInformationByPigId(final String pigId, final Integer companyId) throws SQLException {
		String qry = "Select \"id\", \"pigId\", \"sireId\", \"damId\",\"origin\", \"gline\", \"gcompany\", \"birthDate\","
				+ "\"tattoo\",\"alternateTattoo\", \"remarks\",\"id_Company\", \"id_Pen\", \"id_Barn\", \"id_SexType\", \"entryDate\",\"isActive\",\"id_GfunctionType\" "
				+ " from pigtrax.\"PigInfo\" where \"pigId\" = ? and \"id_Company\" = ? and \"isActive\" is true";
		List<PigInfo> pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, pigId.trim());
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
		String qry = "Select \"id\", \"pigId\", \"sireId\", \"damId\",\"origin\", \"gline\", \"gcompany\", "
				+ "\"birthDate\",\"tattoo\",\"alternateTattoo\", \"remarks\", "
				+ "\"id_Company\", \"id_Pen\", \"id_Barn\", \"id_SexType\", \"entryDate\",\"isActive\",\"id_GfunctionType\" "
				+ "from pigtrax.\"PigInfo\" where \"tattoo\" = ? and \"id_Company\" = ? and \"isActive\" is true";
		List<PigInfo> pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			
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
			pigInfo.setCompanyId(rs.getInt("id_Company"));
			pigInfo.setBarnId((rs.getObject("id_Barn")!=null)?(Integer)rs.getObject("id_Barn") : null);
			pigInfo.setPenId((rs.getObject("id_Pen")!=null)?(Integer)rs.getObject("id_Pen") : null);
			pigInfo.setSexTypeId(rs.getInt("id_SexType"));
			pigInfo.setEntryDate(rs.getDate("entryDate"));
			pigInfo.setActive(rs.getBoolean("isActive"));
			pigInfo.setGfunctionTypeId((rs.getObject("id_GfunctionType")!=null)?(Integer)rs.getObject("id_GfunctionType") : null);
			return pigInfo;
		}
	}
	
	private static final class FosterInfoMapper implements RowMapper<PigInfo> {
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
			pigInfo.setEntryDate(rs.getDate("entryDate"));
			pigInfo.setTattoo(rs.getString("tattoo"));
			pigInfo.setAlternateTattoo(rs.getString("alternateTattoo"));
			pigInfo.setRemarks(rs.getString("remarks"));
			pigInfo.setCompanyId(rs.getInt("id_Company"));
			pigInfo.setBarnId((rs.getObject("id_Barn")!=null)?(Integer)rs.getObject("id_Barn") : null);
			pigInfo.setPenId((rs.getObject("id_Pen")!=null)?(Integer)rs.getObject("id_Pen") : null);
			pigInfo.setSexTypeId(rs.getInt("id_SexType"));
			pigInfo.setCurrentFarrowEventDate(rs.getDate("eventTime")!= null?rs.getDate("eventTime"):null);
			pigInfo.setFarrowId((rs.getObject("id_FarrowEvent")!=null)?(Integer)rs.getObject("id_FarrowEvent") : null);
			pigInfo.setGfunctionTypeId((rs.getObject("id_GfunctionType")!=null)?(Integer)rs.getObject("id_GfunctionType") : null);
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
		String qry = "Select \"id\", \"pigId\", \"sireId\", \"damId\",\"origin\", \"gline\", \"gcompany\", "
				+ "\"birthDate\",\"tattoo\",\"alternateTattoo\", \"remarks\", \"id_Company\","
				+ " \"id_Pen\", \"id_Barn\", \"id_SexType\", \"entryDate\", \"isActive\",\"id_GfunctionType\"  from pigtrax.\"PigInfo\" where \"id\" = ? ";
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
	
	
	/**
	 * Get all foster pigs
	 */
	@Override
	public List<PigInfo> getAllFosterPigs(final PigInfoDto pigInfo) throws SQLException {
		String qry = "SELECT PI.*,PEM.\"eventTime\",PEM.\"id_FarrowEvent\" FROM pigtrax.\"PigTraxEventMaster\" PEM INNER JOIN (SELECT \"id_PigInfo\", MAX(\"id\") AS maxid FROM pigtrax.\"PigTraxEventMaster\" "
					+" GROUP BY \"id_PigInfo\") PEM_SUB ON PEM.\"id_PigInfo\" = PEM_SUB.\"id_PigInfo\" AND PEM.\"id\" = PEM_SUB.maxid and \"id_FarrowEvent\" is not null  "
					+ "JOIN pigtrax.\"PigInfo\" PI on PEM.\"id_PigInfo\" = PI.\"id\" and PI.\"id_Company\" = ? and PI.\"pigId\" <> ?"	;
		
		
		List<PigInfo> pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter(){		
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfo.getCompanyId());
				ps.setString(2, pigInfo.getSearchText());
			}}, new FosterInfoMapper()); 
		
		return pigInfoList;
	}
	
	/**
	 * Get the pig information based on companyId
	 */
	@Override
	public List<PigInfo> getPigInformationByCompanyId( final Integer companyId) throws SQLException {
		String qry = "Select \"id\", \"pigId\", \"sireId\", \"damId\",\"origin\", \"gline\", \"gcompany\", "
				+ "\"birthDate\",\"tattoo\",\"alternateTattoo\", \"remarks\", "
				+ "\"id_Company\", \"id_Pen\", \"id_Barn\", \"id_SexType\", \"entryDate\", \"isActive\",\"id_GfunctionType\" "
				+ "from pigtrax.\"PigInfo\" where \"id_Company\" = ?";
		List<PigInfo> pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			
			public void setValues(PreparedStatement ps) throws SQLException {				
				ps.setInt(1, companyId);
			}}, new PigInfoMapper());

		if(pigInfoList != null && pigInfoList.size() > 0){
			return pigInfoList;
		}
		return null;
	}
	
	/**
	 * Update the pig status based on 
	 */
	
	public int updatePigInfoStatus(final Integer id, final Boolean pigStatus)
	{
		String query = "update pigtrax.\"PigInfo\" SET \"isActive\"=?  WHERE \"id\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, pigStatus);
				ps.setInt(2, id);
			}
		});
	
	}
	
	@Override 
	public int increaseParity(final Integer pigInfoId) {
		String query = "update pigtrax.\"PigInfo\" SET \"parity\"=\"parity\"+1  WHERE \"id\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoId);
			}
		});
	}
	
	
	@Override
	public int decreaseParity(final Integer pigInfoId) {
		String query = "update pigtrax.\"PigInfo\" SET \"parity\"=\"parity\"-1  WHERE \"id\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, pigInfoId);
			}
		});
	} 
	
	@Override
	public int changePigId(final Integer pigInfoId, final String newPigId) {
		String query = "update pigtrax.\"PigInfo\" SET \"pigId\"= ?  WHERE \"id\" = ?";

		return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, newPigId);
				ps.setInt(2, pigInfoId);
			}
		});
	}
	
	
	@Override
	public PigInfo getInactivePigInformationByPigId(final String pigId,
			final Integer companyId) throws SQLException {
		String qry = "Select \"id\", \"pigId\", \"sireId\", \"damId\",\"origin\", \"gline\", \"gcompany\", \"birthDate\","
				+ "\"tattoo\",\"alternateTattoo\", \"remarks\",\"id_Company\", \"id_Pen\", \"id_Barn\", \"id_SexType\", \"entryDate\",\"isActive\",\"id_GfunctionType\" "
				+ " from pigtrax.\"PigInfo\" where \"pigId\" = ? and \"id_Company\" = ? and \"isActive\" is false";
		List<PigInfo> pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, pigId.trim());
				ps.setInt(2, companyId);
			}}, new PigInfoMapper());

		if(pigInfoList != null && pigInfoList.size() > 0){
			return pigInfoList.get(0);
		}
		return null;
	}
	
	
	@Override
	public PigInfo getInactivePigInformationByTattoo(final String tattoo,
			final Integer companyId) throws SQLException {
		String qry = "Select \"id\", \"pigId\", \"sireId\", \"damId\",\"origin\", \"gline\", \"gcompany\", "
				+ "\"birthDate\",\"tattoo\",\"alternateTattoo\", \"remarks\", "
				+ "\"id_Company\", \"id_Pen\", \"id_Barn\", \"id_SexType\", \"entryDate\",\"isActive\",\"id_GfunctionType\" "
				+ "from pigtrax.\"PigInfo\" where \"tattoo\" = ? and \"id_Company\" = ? and \"isActive\" is false";
		List<PigInfo> pigInfoList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tattoo.trim());
				ps.setInt(2, companyId);
			}}, new PigInfoMapper());

		if(pigInfoList != null && pigInfoList.size() > 0){
			return pigInfoList.get(0);
		}
		return null;
	}

}

