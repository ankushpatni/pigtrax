package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.Date;
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

import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;
import com.pigtrax.usermanagement.enums.PigletStatusEventType;
import com.pigtrax.usermanagement.enums.RemovalEventType;
import com.pigtrax.util.UserUtil;

@Repository
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
	    "\"weightInKgs\", \"id_RemovalEvent\", \"id_Premise\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\",\"id_DestPremise\",\"remarks\",\"id_MortalityReason\",\"revenueUsd\" FROM pigtrax.\"RemovalEventExceptSalesDetails\" where \"id\" = ?" ;
					
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
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsByGroupId( final int id) throws SQLException
	{

		String qry = "SELECT \"id\", \"numberOfPigs\", \"removalDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
	    "\"weightInKgs\", \"id_RemovalEvent\", \"id_Premise\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\",\"id_DestPremise\",\"remarks\", \"id_MortalityReason\",\"revenueUsd\" FROM pigtrax.\"RemovalEventExceptSalesDetails\" where \"id_GroupEvent\" = ?" ;
					
		List<RemovalEventExceptSalesDetails> removalEventExceptSalesDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {					
				ps.setInt(1, id);
			}}, new RemovalEventExceptSalesDetailsMapper());

		if(removalEventExceptSalesDetailsList != null && removalEventExceptSalesDetailsList.size() > 0)
		{
			return removalEventExceptSalesDetailsList;
		}
		return null;
	}
	
	@Override
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsByPigInfoId( final int id) throws SQLException
	{

		String qry = "SELECT \"id\", \"numberOfPigs\", \"removalDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
	    "\"weightInKgs\", \"id_RemovalEvent\", \"id_Premise\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\",\"id_DestPremise\",\"remarks\",\"id_MortalityReason\",\"revenueUsd\" FROM pigtrax.\"RemovalEventExceptSalesDetails\" where \"id_PigInfo\" = ?" ;
					
		List<RemovalEventExceptSalesDetails> removalEventExceptSalesDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {					
				ps.setInt(1, id);
			}}, new RemovalEventExceptSalesDetailsMapper());

		if(removalEventExceptSalesDetailsList != null && removalEventExceptSalesDetailsList.size() > 0)
		{
			return removalEventExceptSalesDetailsList;
		}
		return null;
	}


	@Override
	public List<RemovalEventExceptSalesDetails> getRemovalEventExceptSalesDetailsListByRemovalId(
			final int removalId) throws SQLException {
		String qry = "SELECT \"id\", \"numberOfPigs\", \"removalDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			    "\"weightInKgs\", \"id_RemovalEvent\", \"id_Premise\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\",\"id_DestPremise\",\"remarks\",\"id_MortalityReason\",\"revenueUsd\" FROM pigtrax.\"RemovalEventExceptSalesDetails\" where \"id_RemovalEvent\" = ?" ;
							
		List<RemovalEventExceptSalesDetails> removalEventExceptSalesDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {					
				ps.setInt(1, removalId);
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
			    "\"weightInKgs\", \"id_RemovalEvent\", \"id_Premise\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\",\"id_DestPremise\",\"remarks\",\"id_MortalityReason\", \"revenueUsd\") " + 
			       "values(?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?)";	
		
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
				
				if(removalEventExceptSalesDetails.getPigInfoId() != null && removalEventExceptSalesDetails.getPigInfoId() !=0 )
				{
					ps.setInt(3, removalEventExceptSalesDetails.getPigInfoId());
				}
				else
				{
					ps.setNull(3, java.sql.Types.INTEGER);
				}
				
				if(removalEventExceptSalesDetails.getGroupEventId() != null && removalEventExceptSalesDetails.getGroupEventId() !=0)
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
				
				if(removalEventExceptSalesDetails.getRemovalEventId() != null && removalEventExceptSalesDetails.getRemovalEventId() !=0)
				{
					ps.setInt(6, removalEventExceptSalesDetails.getRemovalEventId());
				}
				else
				{
					ps.setNull(6, java.sql.Types.INTEGER);
				}	
				
				if(removalEventExceptSalesDetails.getPremiseId() != null && removalEventExceptSalesDetails.getPremiseId() !=0)
				{
					ps.setInt(7, removalEventExceptSalesDetails.getPremiseId());
				}
				else
				{
					ps.setNull(7, java.sql.Types.INTEGER);
				}				
				
				ps.setString(8, removalEventExceptSalesDetails.getUserUpdated());	
				

				if(removalEventExceptSalesDetails.getTransportJourneyId() != null && removalEventExceptSalesDetails.getTransportJourneyId()!=0)
				{
					ps.setInt(9, removalEventExceptSalesDetails.getTransportJourneyId());
				}
				else
				{
					ps.setNull(9, java.sql.Types.INTEGER);
				}
				if(removalEventExceptSalesDetails.getDestPremiseId() != null )
				{
					ps.setInt(10, removalEventExceptSalesDetails.getDestPremiseId());
				}
				else
				{
					ps.setNull(10, java.sql.Types.INTEGER);
				}
				ps.setString(11, removalEventExceptSalesDetails.getRemarks());
				
				if(removalEventExceptSalesDetails.getMortalityReasonId() != null && removalEventExceptSalesDetails.getMortalityReasonId() !=0)
				{
					ps.setInt(12, removalEventExceptSalesDetails.getMortalityReasonId());
				}
				else
				{
					ps.setNull(12, java.sql.Types.INTEGER);
				}
				if(removalEventExceptSalesDetails.getRevenueUsd() != null)
				{
					ps.setObject(13, removalEventExceptSalesDetails.getRevenueUsd(),java.sql.Types.DECIMAL);
				}
				else
				{
					ps.setNull(13, java.sql.Types.DECIMAL);
				}
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;

	}

	@Override
	public int updateRemovalEventExceptSalesDetails(
			final RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
			throws SQLException {
		String query = "update pigtrax.\"RemovalEventExceptSalesDetails\" SET \"numberOfPigs\"=?, \"removalDateTime\"=?, \"id_PigInfo\"=?,"
				+" \"id_GroupEvent\"=? ,\"weightInKgs\" =? , \"id_Premise\"=?, \"lastUpdated\"=current_timestamp,"+
				" \"userUpdated\"=?,\"id_TransportJourney\"=?,\"id_DestPremise\"=?,\"id_MortalityReason\"=?,\"revenueUsd\"=?  where \"id\" = ? ";
		
			return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
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
					
					if(removalEventExceptSalesDetails.getPremiseId() != null )
					{
						ps.setInt(6, removalEventExceptSalesDetails.getPremiseId());
					}
					else
					{
						ps.setNull(6, java.sql.Types.INTEGER);
					}				
					
					ps.setString(7, UserUtil.getLoggedInUser());
					if(removalEventExceptSalesDetails.getTransportJourneyId() != null && removalEventExceptSalesDetails.getTransportJourneyId()!=0)
					{
						ps.setInt(8, removalEventExceptSalesDetails.getTransportJourneyId());
					}
					else
					{
						ps.setNull(8, java.sql.Types.INTEGER);
					}
					
					if(removalEventExceptSalesDetails.getDestPremiseId() != null )
					{
						ps.setInt(9, removalEventExceptSalesDetails.getDestPremiseId());
					}
					else
					{
						ps.setNull(9, java.sql.Types.INTEGER);
					}
					if(removalEventExceptSalesDetails.getMortalityReasonId() != null && removalEventExceptSalesDetails.getMortalityReasonId()!=0 )
					{
						ps.setInt(10, removalEventExceptSalesDetails.getMortalityReasonId());
					}
					else
					{
						ps.setNull(10, java.sql.Types.INTEGER);
					}	
					
					if(removalEventExceptSalesDetails.getRevenueUsd() != null)
					{
						ps.setObject(11, removalEventExceptSalesDetails.getRevenueUsd(), java.sql.Types.DECIMAL);
					}
					else
					{
						ps.setNull(11, java.sql.Types.DECIMAL);
					}	
					ps.setInt(12, removalEventExceptSalesDetails.getId());
				}
			});	
	}
	
	/**
	 * To delete the given RemovalEventExceptSalesDetails
	 * @param id
	 */
	
	@Override
	public void deleteRemovalEventExceptSalesDetails(final Integer id) throws SQLException {
		
		final String qry = "delete from pigtrax.\"RemovalEventExceptSalesDetails\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
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
			removalEventExceptSalesDetails.setPremiseId(rs.getInt("id_Premise"));
			removalEventExceptSalesDetails.setDestPremiseId(rs.getInt("id_DestPremise"));			
			removalEventExceptSalesDetails.setTransportJourneyId(rs.getInt("id_TransportJourney"));			
			removalEventExceptSalesDetails.setUserUpdated(rs.getString("userUpdated"));
			removalEventExceptSalesDetails.setRemarks(rs.getString("remarks"));
			removalEventExceptSalesDetails.setMortalityReasonId(rs.getInt("id_MortalityReason"));
			removalEventExceptSalesDetails.setRevenueUsd(rs.getBigDecimal("revenueUsd"));			
			return removalEventExceptSalesDetails;
		}		
	}
	
	/**
	 * To get the total pigs mortal count companyId
	 */
	@Override
	public Integer getTotalPigsMortal(final Date start,final Date end,
			final Integer companyId) {
		
		//select sum("numberOfPigs") from pigtrax."RemovalEventExceptSalesDetails" where "id_GroupEvent" in(select "id" from pigtrax."GroupEvent" where "id_Company" = 1);
		String qry = "select sum(\"numberOfPigs\") from pigtrax.\"RemovalEventExceptSalesDetails\" "+
					" where \"id_GroupEvent\" in (select \"id\" from pigtrax.\"GroupEvent\" where \"id_Company\" = ?) "+
					"and \"id_RemovalEvent\" = ? and \"removalDateTime\" >= ? and \"removalDateTime\" <= ?  ";

 		List<Integer> pigletStatusEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, companyId);
 				ps.setInt(2, RemovalEventType.Mortality.getTypeCode());
 				ps.setDate(3, start);
 				ps.setDate(4, end);
 			}}, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getInt(1);
				}
			});

		return pigletStatusEventList.get(0);
	}


}
