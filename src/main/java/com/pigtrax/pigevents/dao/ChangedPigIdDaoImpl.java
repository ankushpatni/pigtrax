package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.pigevents.beans.ChangedPigId;
import com.pigtrax.pigevents.dao.interfaces.ChangedPigIdDao;


@Repository
@Transactional
public class ChangedPigIdDaoImpl implements ChangedPigIdDao {

private static final Logger logger = Logger.getLogger(BreedingEventDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	} 
	
	@Override
	public int storeChangedPigId(final ChangedPigId changedPigId) throws SQLException {
		final String Qry = "insert into pigtrax.\"ChangedPigId\"(\"oldSowId\", \"changedSowId\", "
				+ "\"id_Company\", \"changeDateTime\", \"lastUpdated\", \"userUpdated\",\"id_PigInfo\") "
				+ "values(?,?,?,?,current_timestamp,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            
	    				ps.setString(1, changedPigId.getOldSowId());
	    				ps.setString(2, changedPigId.getChangedSowId());
	    				if(changedPigId.getCompanyId() != null && changedPigId.getCompanyId() != 0){
	    					ps.setInt(3, changedPigId.getCompanyId());
	    				}
	    				else{
	    	            	ps.setNull(3, java.sql.Types.INTEGER);
	    				}
	    				ps.setObject(4, changedPigId.getChangeDateTime(), java.sql.Types.DATE); 	
	    				ps.setString(5, changedPigId.getUserUpdated()); 
	    				ps.setInt(6, changedPigId.getPigInfoId()); 
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}
	 
	 
}
