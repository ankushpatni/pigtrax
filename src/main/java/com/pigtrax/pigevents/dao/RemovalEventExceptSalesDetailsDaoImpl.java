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

import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;
import com.pigtrax.util.UserUtil;

public class RemovalEventExceptSalesDetailsDaoImpl implements RemovalEventExceptSalesDetailsDao
{
	
private static final Logger logger = Logger.getLogger(RemovalEventExceptSalesDetailsDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public RemovalEventExceptSalesDetails getRemovalEventExceptSalesDetailsById( final int id) throws SQLException
	{

		String qry = "SELECT \"id\", \"numberOfPigs\", \"removalDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
	    "\"weightInKgs\", \"id_RemovalEvent\", \"id_Barn\", \"lastUpdated\", \"userUpdated\" FROM pigtrax.\"RemovalEventExceptSalesDetails\" where \"id\" = ?" ;
					
		List<RemovalEventExceptSalesDetails> removalEventExceptSalesDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {					
				ps.setInt(1, id);
			}}, new RemovalEventExceptSalesDetailsMapper());

		if(removalEventExceptSalesDetailsList != null && removalEventExceptSalesDetailsList.size() > 0)
		{
			return removalEventExceptSalesDetailsList.get(0);
		}
		return null;
	}

	@Override
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsListByRemovalId(
			final String removalId) throws SQLException {
		String qry = "SELECT \"id\", \"numberOfPigs\", \"removalDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			    "\"weightInKgs\", \"id_RemovalEvent\", \"id_Barn\", \"lastUpdated\", \"userUpdated\" FROM pigtrax.\"RemovalEventExceptSalesDetails\" where \"id_RemovalEvent\" = ?" ;
							
		List<RemovalEventExceptSalesDetails> removalEventExceptSalesDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {					
				ps.setString(1, removalId);
			}}, new RemovalEventExceptSalesDetailsMapper());

		if(removalEventExceptSalesDetailsList != null && removalEventExceptSalesDetailsList.size() > 0)
		{
			return removalEventExceptSalesDetailsList;
		}
		return null;
	}

	@Override
	public int addRemovalEventExceptSalesDetails(
			final RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
			throws SQLException {
		final String Qry = "insert into pigtrax.\"RemovalEventExceptSalesDetails\"(\"numberOfPigs\", \"removalDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			    "\"weightInKgs\", \"id_RemovalEvent\", \"id_Barn\", \"lastUpdated\", \"userUpdated\") " + 
			       "values(?,?,?,?,?,?,?,current_timestamp,?)";	
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				
				if(removalEventExceptSalesDetails.getNumberOfPigs() != null )
				{
					ps.setInt(1, removalEventExceptSalesDetails.getNumberOfPigs());
				}
				else
				{
					ps.setNull(1, java.sql.Types.INTEGER);
				}
				if(null != removalEventExceptSalesDetails.getRemovalDateTime())
				{
				ps.setDate(2, new java.sql.Date(removalEventExceptSalesDetails
						.getRemovalDateTime().getTime()));
				}
				else
				{
					ps.setNull(2, java.sql.Types.DATE);
				}
				
				if(removalEventExceptSalesDetails.getPigInfoId() != null )
				{
					ps.setInt(3, removalEventExceptSalesDetails.getPigInfoId());
				}
				else
				{
					ps.setNull(3, java.sql.Types.INTEGER);
				}
				
				if(removalEventExceptSalesDetails.getGroupEventId() != null )
				{
					ps.setInt(4, removalEventExceptSalesDetails.getGroupEventId());
				}
				else
				{
					ps.setNull(4, java.sql.Types.INTEGER);
				}
				
				if(removalEventExceptSalesDetails.getWeightInKgs() != null )
				{
					ps.setBigDecimal(5, removalEventExceptSalesDetails.getWeightInKgs());
				}
				else
				{
					ps.setNull(5, java.sql.Types.INTEGER);
				}
				
				if(removalEventExceptSalesDetails.getRemovalEventId() != null )
				{
					ps.setInt(6, removalEventExceptSalesDetails.getRemovalEventId());
				}
				else
				{
					ps.setNull(6, java.sql.Types.INTEGER);
				}	
				
				if(removalEventExceptSalesDetails.getBarnId() != null )
				{
					ps.setInt(7, removalEventExceptSalesDetails.getBarnId());
				}
				else
				{
					ps.setNull(7, java.sql.Types.INTEGER);
				}				
				
				ps.setString(8, UserUtil.getLoggedInUser());				
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;

	}

	@Override
	public int updateRemovalEventExceptSalesDetails(
			RemovalEventExceptSalesDetails salesEventDetails)
			throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private static final class RemovalEventExceptSalesDetailsMapper implements RowMapper<RemovalEventExceptSalesDetails> {
		public RemovalEventExceptSalesDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			RemovalEventExceptSalesDetails removalEventExceptSalesDetails = new RemovalEventExceptSalesDetails();
			removalEventExceptSalesDetails.setId(rs.getInt("id"));
			removalEventExceptSalesDetails.setNumberOfPigs(rs.getInt("numberOfPigs"));
			removalEventExceptSalesDetails.setRemovalDateTime(rs.getDate("removalDateTime"));
			removalEventExceptSalesDetails.setPigInfoId(rs.getInt("id_PigInfo"));
			removalEventExceptSalesDetails.setGroupEventId(rs.getInt("id_GroupEvent"));
			removalEventExceptSalesDetails.setWeightInKgs(rs.getBigDecimal("weightInKgs"));
			removalEventExceptSalesDetails.setRemovalEventId(rs.getInt("id_RemovalEvent"));
			removalEventExceptSalesDetails.setBarnId(rs.getInt("soldTo"));
			removalEventExceptSalesDetails.setLastUpdated(rs.getDate("id_Barn"));
			removalEventExceptSalesDetails.setUserUpdated(rs.getString("userUpdated"));
			return removalEventExceptSalesDetails;
		}		
	}

}
