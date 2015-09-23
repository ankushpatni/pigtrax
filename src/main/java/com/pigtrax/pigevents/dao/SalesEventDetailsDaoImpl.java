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

import com.pigtrax.pigevents.beans.SalesEventDetails;
import com.pigtrax.pigevents.dao.interfaces.SalesEventDetailsDao;
import com.pigtrax.util.UserUtil;

@Repository
@Transactional
public class SalesEventDetailsDaoImpl implements SalesEventDetailsDao
{

	private static final Logger logger = Logger.getLogger(SalesEventDetailsDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public SalesEventDetails getSalesEventDetailsById(final int id)
			throws SQLException
	{
		String qry = "SELECT \"id\", \"invoiceId\", \"ticketNumber\", \"numberOfPigs\", \"revenueUsd\","+ 
	       "\"weightInKgs\", \"salesDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
	       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\", \"id_TransportJourney\" FROM pigtrax.\"SalesEventDetails\" where \"id\" = ?";
			
			List<SalesEventDetails> salesEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setInt(1, id);
				}}, new SalesEventDetailsMapper());

			if(salesEventDetailsList != null && salesEventDetailsList.size() > 0)
			{
				return salesEventDetailsList.get(0);
			}
			return null;
	}

	@Override
	public List<SalesEventDetails> getSalesEventDetailsListByRemovalId(
			final String removalId) throws SQLException
	{
		String qry = "SELECT \"id\", \"invoiceId\", \"ticketNumber\", \"numberOfPigs\", \"revenueUsd\","+ 
			       "\"weightInKgs\", \"salesDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\" FROM pigtrax.\"SalesEventDetails\" where \"id_RemovalEvent\" = ?";
					
		List<SalesEventDetails> salesEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {					
				ps.setString(1, removalId.toUpperCase());
			}}, new SalesEventDetailsMapper());

		if(salesEventDetailsList != null && salesEventDetailsList.size() > 0)
		{
			return salesEventDetailsList;
		}
		return null;
	}

	@Override
	public int addSalesEventDetails( final SalesEventDetails salesEventDetails)	throws SQLException 
	{
		final String Qry = "insert into pigtrax.\"SalesEventDetails\"(\"invoiceId\", \"ticketNumber\", \"numberOfPigs\", \"revenueUsd\","+ 
			       "\"weightInKgs\", \"salesDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\") " + 
			       "values(?,?,?,?,?,?,?,?,?,?,current_timestamp,?)";		

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(Qry,new String[] { "id" });
				ps.setString(1, salesEventDetails.getInvoiceId());
				ps.setString(2, salesEventDetails.getTicketNumber());
				
				if(salesEventDetails.getNumberOfPigs() != null )
				{
					ps.setInt(3, salesEventDetails.getNumberOfPigs());
				}
				else
				{
					ps.setNull(3, java.sql.Types.INTEGER);
				}
				
				if(salesEventDetails.getRevenueUsd() != null )
				{
					ps.setBigDecimal(4, salesEventDetails.getRevenueUsd());
				}
				else
				{
					ps.setNull(4, java.sql.Types.INTEGER);
				}
				
				if(salesEventDetails.getWeightInKgs() != null )
				{
					ps.setBigDecimal(5, salesEventDetails.getWeightInKgs());
				}
				else
				{
					ps.setNull(5, java.sql.Types.INTEGER);
				}
				
				
				if(null != salesEventDetails.getSalesDateTime())
				{
				ps.setDate(6, new java.sql.Date(salesEventDetails
						.getSalesDateTime().getTime()));
				}
				else
				{
					ps.setNull(6, java.sql.Types.DATE);
				}
				
				if(salesEventDetails.getPigInfoId() != null )
				{
					ps.setInt(7, salesEventDetails.getPigInfoId());
				}
				else
				{
					ps.setNull(7, java.sql.Types.INTEGER);
				}
				
				if(salesEventDetails.getGroupEventId() != null )
				{
					ps.setInt(8, salesEventDetails.getGroupEventId());
				}
				else
				{
					ps.setNull(8, java.sql.Types.INTEGER);
				}
				
				ps.setString(9, salesEventDetails.getSoldTo());
				
				if(salesEventDetails.getRemovalEventId() != null )
				{
					ps.setInt(10, salesEventDetails.getRemovalEventId());
				}
				else
				{
					ps.setNull(10, java.sql.Types.INTEGER);
				}
				
				ps.setString(11, UserUtil.getLoggedInUser());
				
				if(salesEventDetails.getTransportJourneyId() != null )
				{
					ps.setInt(12, salesEventDetails.getTransportJourneyId());
				}
				else
				{
					ps.setNull(12, java.sql.Types.INTEGER);
				}
				
				return ps;
			}
		}, holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = " + keyVal);
		return keyVal;
	}

	@Override
	public int updateSalesEventDetails(final SalesEventDetails salesEventDetails)
			throws SQLException {
		String query = "update pigtrax.\"SalesEventDetails\" SET \"invoiceId\"=?, \"ticketNumber\"=?, \"numberOfPigs\"=?,"
				+" \"revenueUsd\"=? ,\"weightInKgs\" =? , \"salesDateTime\" = ?, \"id_PigInfo\"=?, \"id_GroupEvent\"=?, \"soldTo\"=?, \"lastUpdated\"=current_timestamp,"+
				" \"userUpdated\"=?,id_TransportJourney=?  where \"id\" = ? ";
		
			return this.jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, salesEventDetails.getInvoiceId());
					ps.setString(2, salesEventDetails.getTicketNumber());
					
					if(salesEventDetails.getNumberOfPigs() != null )
					{
						ps.setInt(3, salesEventDetails.getNumberOfPigs());
					}
					else
					{
						ps.setNull(3, java.sql.Types.INTEGER);
					}
					
					if(salesEventDetails.getRevenueUsd() != null )
					{
						ps.setBigDecimal(4, salesEventDetails.getRevenueUsd());
					}
					else
					{
						ps.setNull(4, java.sql.Types.INTEGER);
					}
					
					if(salesEventDetails.getWeightInKgs() != null )
					{
						ps.setBigDecimal(5, salesEventDetails.getWeightInKgs());
					}
					else
					{
						ps.setNull(5, java.sql.Types.INTEGER);
					}
					
					
					if(null != salesEventDetails.getSalesDateTime())
					{
					ps.setDate(6, new java.sql.Date(salesEventDetails
							.getSalesDateTime().getTime()));
					}
					else
					{
						ps.setNull(6, java.sql.Types.DATE);
					}
					
					if(salesEventDetails.getPigInfoId() != null )
					{
						ps.setInt(7, salesEventDetails.getPigInfoId());
					}
					else
					{
						ps.setNull(7, java.sql.Types.INTEGER);
					}
					
					if(salesEventDetails.getGroupEventId() != null )
					{
						ps.setInt(8, salesEventDetails.getGroupEventId());
					}
					else
					{
						ps.setNull(8, java.sql.Types.INTEGER);
					}
					
					ps.setString(9, salesEventDetails.getSoldTo());
					
					ps.setString(10, UserUtil.getLoggedInUser());
					
					if(salesEventDetails.getTransportJourneyId() != null )
					{
						ps.setInt(11, salesEventDetails.getTransportJourneyId());
					}
					else
					{
						ps.setNull(11, java.sql.Types.INTEGER);
					}
					ps.setInt(12, salesEventDetails.getId());
				}
			});	
	}
	
	private static final class SalesEventDetailsMapper implements RowMapper<SalesEventDetails> {
		public SalesEventDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			SalesEventDetails salesEventDetails = new SalesEventDetails();
			salesEventDetails.setId(rs.getInt("id"));
			salesEventDetails.setInvoiceId(rs.getString("invoiceId"));
			salesEventDetails.setTicketNumber(rs.getString("ticketNumber"));
			salesEventDetails.setNumberOfPigs(rs.getInt("numberOfPigs"));
			salesEventDetails.setRevenueUsd(rs.getBigDecimal("revenueUsd"));
			salesEventDetails.setWeightInKgs(rs.getBigDecimal("weightInKgs"));
			salesEventDetails.setSalesDateTime(rs.getDate("salesDateTime"));
			salesEventDetails.setPigInfoId(rs.getInt("id_PigInfo"));
			salesEventDetails.setGroupEventId(rs.getInt("id_GroupEvent"));
			salesEventDetails.setSoldTo(rs.getString("soldTo"));
			salesEventDetails.setRemovalEventId(rs.getInt("id_RemovalEvent"));			
			salesEventDetails.setLastUpdated(rs.getDate("lastUpdated"));
			salesEventDetails.setUserUpdated(rs.getString("userUpdated"));
			salesEventDetails.setTransportJourneyId(rs.getInt("id_TransportJourney"));
			
			return salesEventDetails;
		}		
	}
	
	/**
	 * To delete the given RemovalEventExceptSalesDetails
	 * @param id
	 */
	
	@Override
	public void deleteSalesEventDetails(final Integer id) throws SQLException {
		
		final String qry = "delete from pigtrax.\"SalesEventDetails\" where \"id\" = ?";
		
		this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
	}
	
}
