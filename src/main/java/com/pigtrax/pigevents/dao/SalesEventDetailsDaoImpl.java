package com.pigtrax.pigevents.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
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
	       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\", \"id_TransportJourney\",\"remarks\",\"salesTypes\",\"salesReasons\" FROM pigtrax.\"SalesEventDetails\" where \"id\" = ?";
			
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
	public List<SalesEventDetails> getSalesEventDetailsListByGroupId(final int groupId) throws SQLException
	{
		String qry = "SELECT \"id\", \"invoiceId\", \"ticketNumber\", \"numberOfPigs\", \"revenueUsd\","+ 
			       "\"weightInKgs\", \"salesDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\", \"id_TransportJourney\",\"remarks\",\"salesTypes\",\"salesReasons\" FROM pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\" = ?";
					
					List<SalesEventDetails> salesEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {					
							ps.setInt(1, groupId);
						}}, new SalesEventDetailsMapper());

					if(salesEventDetailsList != null && salesEventDetailsList.size() > 0)
					{
						return salesEventDetailsList;
					}
					return null;
	}
	
	@Override
	public List<SalesEventDetails> getSalesEventDetailsListByPigId(final int pigInfoIdId) throws SQLException
	{
		String qry = "SELECT \"id\", \"invoiceId\", \"ticketNumber\", \"numberOfPigs\", \"revenueUsd\","+ 
			       "\"weightInKgs\", \"salesDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\", \"id_TransportJourney\",\"remarks\",\"salesTypes\",\"salesReasons\" FROM pigtrax.\"SalesEventDetails\" where \"id_PigInfo\" = ?";
					
					List<SalesEventDetails> salesEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {					
							ps.setInt(1, pigInfoIdId);
						}}, new SalesEventDetailsMapper());

					if(salesEventDetailsList != null && salesEventDetailsList.size() > 0)
					{
						return salesEventDetailsList;
					}
					return null;
	}

	@Override
	public List<SalesEventDetails> getSalesEventDetailsListByRemovalId(
			final int removalId) throws SQLException
	{
		String qry = "SELECT \"id\", \"invoiceId\", \"ticketNumber\", \"numberOfPigs\", \"revenueUsd\","+ 
			       "\"weightInKgs\", \"salesDateTime\", \"id_PigInfo\", \"id_GroupEvent\","+ 
			       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\",\"remarks\",\"salesTypes\",\"salesReasons\" FROM pigtrax.\"SalesEventDetails\" where \"id_RemovalEvent\" = ?";
					
		List<SalesEventDetails> salesEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {					
				ps.setInt(1, removalId);
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
			       "\"soldTo\", \"id_RemovalEvent\", \"lastUpdated\", \"userUpdated\",\"id_TransportJourney\",\"remarks\",\"salesTypes\",\"salesReasons\") " + 
			       "values(?,?,?,?,?,?,?,?,?,?,current_timestamp,?,?,?,?,?)";		

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
				
				if(salesEventDetails.getPigInfoId() != null && salesEventDetails.getPigInfoId() !=0)
				{
					ps.setInt(7, salesEventDetails.getPigInfoId());
				}
				else
				{
					ps.setNull(7, java.sql.Types.INTEGER);
				}
				
				if(salesEventDetails.getGroupEventId() != null && salesEventDetails.getGroupEventId() !=0 )
				{
					ps.setInt(8, salesEventDetails.getGroupEventId());
				}
				else
				{
					ps.setNull(8, java.sql.Types.INTEGER);
				}
				
				ps.setString(9, salesEventDetails.getSoldTo());
				
				if(salesEventDetails.getRemovalEventId() != null && salesEventDetails.getRemovalEventId() !=0)
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
				
				ps.setString(13, salesEventDetails.getRemarks());
				ps.setString(14, salesEventDetails.getSalesTypesAsString());
				ps.setString(15, salesEventDetails.getSalesReasonsAsString());
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
				" \"userUpdated\"=?,\"id_TransportJourney\"=?,\"salesTypes\"=?, \"salesReasons\" = ?, \"remarks\"=?  where \"id\" = ? ";
		
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
					
					if(salesEventDetails.getPigInfoId() != null && salesEventDetails.getPigInfoId() != 0)
					{
						ps.setInt(7, salesEventDetails.getPigInfoId());
					}
					else
					{
						ps.setNull(7, java.sql.Types.INTEGER);
					}
					
					if(salesEventDetails.getGroupEventId() != null && salesEventDetails.getGroupEventId() != 0 )
					{
						ps.setInt(8, salesEventDetails.getGroupEventId());
					}
					else
					{
						ps.setNull(8, java.sql.Types.INTEGER);
					}
					
					ps.setString(9, salesEventDetails.getSoldTo());
					
					ps.setString(10, UserUtil.getLoggedInUser());
					
					if(salesEventDetails.getTransportJourneyId() != null && salesEventDetails.getTransportJourneyId() != 0)
					{
						ps.setInt(11, salesEventDetails.getTransportJourneyId());
					}
					else
					{
						ps.setNull(11, java.sql.Types.INTEGER);
					}
					ps.setString(12, salesEventDetails.getSalesTypesAsString());
					ps.setString(13, salesEventDetails.getSalesReasonsAsString());
					
					ps.setString(14, salesEventDetails.getRemarks());
					ps.setInt(15, salesEventDetails.getId());
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
			salesEventDetails.setRemarks(rs.getString("remarks"));
			salesEventDetails.setSalesTypesAsString(rs.getString("salesTypes"));
			salesEventDetails.setSalesReasonsAsString(rs.getString("salesReasons"));
						
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
	
	
	/**
	 * Find the number of pigs sold from a group as of given end date
	 * @param endDate
	 * @param groupId
	 * @return
	 */
	public Integer getSalesCount(final Date endDate, final Integer groupId)
	{
		final String qry = "select sum(\"numberOfPigs\") from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\" = ?  and \"salesDateTime\" <=  ?";
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setDate(2, new java.sql.Date(endDate.getTime()));
					
				}
			}, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return 0;
			          }
			        });
		
		return (cnt != null)?cnt : 0 ;
	}
	
	
	/**
	 * Find the total wt of pigs sold from a group as of given end date
	 * @param endDate
	 * @param groupId
	 * @return
	 */
	public Double getSalesWt(final Date endDate, final Integer groupId)
	{
		final String qry = "select sum(\"weightInKgs\") from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\" = ?  and \"salesDateTime\" <=  ?";
			
		@SuppressWarnings("unchecked")
		Double cnt  = (Double)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setDate(2, new java.sql.Date(endDate.getTime()));
					
				}
			}, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDouble(1);
			            }
			            return 0;
			          }
			        });
		
		return (cnt != null)?cnt : 0 ;
	}
	
	
	public Integer getSalesCount(final Date endDate, final Integer groupId,
			Integer marketType) {
		final String qry = "select sum(\"numberOfPigs\") from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\" = ?  and \"salesDateTime\" <=  ? and \"salesTypes\"='"+marketType+"'";
		
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setDate(2, new java.sql.Date(endDate.getTime()));
					
				}
			}, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return 0;
			          }
			        });
		
		return (cnt != null)?cnt : 0 ;
	}
	
	public Double getSalesRevenue(Integer groupId) {
		final String qry = "select sum(\"revenueUsd\") from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\" = "+groupId;
		
		@SuppressWarnings("unchecked")
		Double revenue  = (Double)jdbcTemplate.query(qry, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDouble(1);
			            }
			            return 0;
			          }
			        });
		
		return revenue;
	}
	
	
	
	

	/**
	 * Get Maximum sale wt per head
	 * @param groupId
	 * @return
	 */
	public Double getMaxSaleWtPerHeadVariance(final Integer groupId)
	{	
		Double maxSalesWtPerHead = 0D;
		Double minSalesWtPerHead = 0D;
		
		final String qry = "select \"weightInKgs\" as SaleWt , \"numberOfPigs\" as SaleHd "
					 +" from pigtrax.\"SalesEventDetails\" " 
					 +" where \"id_GroupEvent\" = ?  order by SaleWt desc";
			
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> map  = (List<Map<String, Object>>)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
				}
			}, new SaleDataMapper());
		
		if(map != null && 0<map.size())
		{
			Map<String, Object> maxStartWtMap =  map.get(0);
			Double maxWt = maxStartWtMap.get("SaleWt") != null ? (Double)maxStartWtMap.get("SaleWt") : 0D;
			Long head = maxStartWtMap.get("SaleHd") != null ? (Long)maxStartWtMap.get("SaleHd") : 0;
			if(maxWt > 0 && head > 0)
			{
				maxSalesWtPerHead =  (Math.round(maxWt*100.0)/(head*100.0));
			}
		}
		
		
		final String sql = "select \"weightInKgs\" as SaleWt , \"numberOfPigs\" as SaleHd "
				 +" from pigtrax.\"SalesEventDetails\" " 
				 +" where \"id_GroupEvent\" = ?  order by SaleWt asc";
		
		@SuppressWarnings("unchecked") 
		 List<Map<String, Object>> minMap  = (List<Map<String, Object>>)jdbcTemplate.query(sql,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
				}
			}, new SaleDataMapper());
		
			if(map != null && 0<map.size())
			{
				Map<String, Object> maxStartWtMap =  minMap.get(0);
				Double maxWt = maxStartWtMap.get("SaleWt") != null ? (Double)maxStartWtMap.get("SaleWt") : 0D;
				Long head = maxStartWtMap.get("SaleHd") != null ? (Long)maxStartWtMap.get("SaleHd") : 0;
				if(maxWt > 0 && head > 0)
				{
					minSalesWtPerHead =  (Math.round(maxWt*100.0)/(head*100.0));
				}
			}
		
		
		return maxSalesWtPerHead - minSalesWtPerHead;
	}
	
	private static final class SaleDataMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("SaleHd",rs.getLong("SaleHd"));
			dataMap.put("SaleWt",rs.getDouble("SaleWt"));
			return dataMap;
		}
	}
	
}
