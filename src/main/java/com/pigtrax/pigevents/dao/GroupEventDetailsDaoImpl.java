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
import org.joda.time.DateTime;
import org.joda.time.Days;
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

import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.usermanagement.enums.GroupEventActionType;
import com.pigtrax.util.DateUtil;

@Repository
@Transactional
public class GroupEventDetailsDaoImpl implements GroupEventDetailsDao{

private static final Logger logger = Logger.getLogger(GroupEventDetailsDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List<GroupEventDetails> groupEventDetailsListByGroupId(final int groupId) {
		
		 String qry = "select GED.\"id\", GED.\"id_GroupEvent\", GED.\"id_Barn\", GED.\"dateOfEntry\", GED.\"id_Room\", "
			   		+ "GED.\"id_EmployeeGroup\", GED.\"numberOfPigs\", GED.\"weightInKgs\", GED.\"indeventoryAdjustment\", "
			   		+ "GED.\"remarks\", GED.\"lastUpdated\", GED.\"userUpdated\", GED.\"id_TransportDestination\",GED.\"id_SowSource\",GED.\"id_Premise\", GED.\"id_FromGroup\",GED.\"id_RemovalEventExceptSalesDetails\",GED.\"id_SalesEventDetails\", GE.\"groupId\" "
			   		+ "from pigtrax.\"GroupEventDetails\" GED LEFT JOIN  pigtrax.\"GroupEvent\" GE ON GED.\"id_FromGroup\" = GE.\"id\" where GED.\"id_GroupEvent\" = ? order by GED.\"dateOfEntry\" desc";
		 
				List<GroupEventDetails> groupEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, groupId);
					}}, new GroupEventDetailsMapper());

				if(groupEventDetailsList != null && groupEventDetailsList.size() > 0){
					return groupEventDetailsList;
				}
				return null;
	}
	

	@Override
	public GroupEventDetails groupEventDetailsListById(final Integer id) {
		 String qry = "select GED.\"id\", GED.\"id_GroupEvent\", GED.\"id_Barn\", GED.\"dateOfEntry\", GED.\"id_Room\", "
			   		+ "GED.\"id_EmployeeGroup\", GED.\"numberOfPigs\", GED.\"weightInKgs\", GED.\"indeventoryAdjustment\", "
			   		+ "GED.\"remarks\", GED.\"lastUpdated\", GED.\"userUpdated\", GED.\"id_TransportDestination\", GED.\"id_SowSource\",GED.\"id_Premise\", GED.\"id_FromGroup\",GED.\"id_RemovalEventExceptSalesDetails\",GED.\"id_SalesEventDetails\", GE.\"groupId\" "
			   		+ "  from pigtrax.\"GroupEventDetails\" GED  LEFT JOIN  pigtrax.\"GroupEvent\" GE ON GED.\"id_FromGroup\" = GE.\"id\" where GED.\"id\" = ?";
				
				List<GroupEventDetails> groupEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, id);
					}}, new GroupEventDetailsMapper());

				if(groupEventDetailsList != null && groupEventDetailsList.size() > 0){
					return groupEventDetailsList.get(0);
				}
				return null;
	}
	
	@Override
	public GroupEventDetails groupEventDetailsListByIdAndSalesId(final Integer id, final Integer salesId) {
		/* String qry = "select GED.\"id\", GED.\"id_GroupEvent\", GED.\"id_Barn\", GED.\"dateOfEntry\", GED.\"id_Room\", "
			   		+ "GED.\"id_EmployeeGroup\", GED.\"numberOfPigs\", GED.\"weightInKgs\", GED.\"indeventoryAdjustment\", "
			   		+ "GED.\"remarks\", GED.\"lastUpdated\", GED.\"userUpdated\", GED.\"id_TransportDestination\", GED.\"id_SowSource\",GED.\"id_Premise\", GED.\"id_FromGroup\",GED.\"id_RemovalEventExceptSalesDetails\",GED.\"id_SalesEventDetails\", GE.\"groupId\" "
			   		+ "  from pigtrax.\"GroupEventDetails\" GED  LEFT JOIN  pigtrax.\"GroupEvent\" GE ON GED.\"id_FromGroup\" = GE.\"id\" where GED.\"id\" = ? and GED.\"id_SalesEventDetails\" = ?";
	*/			
		 String qry = " select  GED.\"id\" , GED.\"id_GroupEvent\", GED.\"id_Barn\", GED.\"dateOfEntry\", GED.\"id_Room\", GED.\"id_EmployeeGroup\", GED.\"numberOfPigs\", "
		 +" GED.\"weightInKgs\", GED.\"indeventoryAdjustment\", GED.\"remarks\", GED.\"lastUpdated\", GED.\"userUpdated\", GED.\"id_TransportDestination\", GED.\"id_SowSource\",GED.\"id_Premise\", GED.\"id_FromGroup\",GED.\"id_RemovalEventExceptSalesDetails\",GED.\"id_SalesEventDetails\", '' as \"groupId\" " 
		 +" from pigtrax.\"GroupEventDetails\" GED  where GED.\"id_GroupEvent\" = ? and GED.\"id_SalesEventDetails\" = ? ";
				List<GroupEventDetails> groupEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, id);
						ps.setInt(2, salesId);
					}}, new GroupEventDetailsMapper());

				if(groupEventDetailsList != null && groupEventDetailsList.size() > 0){
					return groupEventDetailsList.get(0);
				}
				return null;
	}
	
	@Override
	public GroupEventDetails groupEventDetailsListByIdAndRemovalId(final Integer id, final Integer removalId) {
		/* String qry = "select GED.\"id\", GED.\"id_GroupEvent\", GED.\"id_Barn\", GED.\"dateOfEntry\", GED.\"id_Room\", "
			   		+ "GED.\"id_EmployeeGroup\", GED.\"numberOfPigs\", GED.\"weightInKgs\", GED.\"indeventoryAdjustment\", "
			   		+ "GED.\"remarks\", GED.\"lastUpdated\", GED.\"userUpdated\", GED.\"id_TransportDestination\", GED.\"id_SowSource\",GED.\"id_Premise\", GED.\"id_FromGroup\",GED.\"id_RemovalEventExceptSalesDetails\",GED.\"id_SalesEventDetails\", GE.\"groupId\" "
			   		+ "  from pigtrax.\"GroupEventDetails\" GED  LEFT JOIN  pigtrax.\"GroupEvent\" GE ON GED.\"id_FromGroup\" = GE.\"id\" where GED.\"id\" = ? and GED.\"id_RemovalEventExceptSalesDetails\" = ?";
				
	*/	 String qry = " select  GED.\"id\" , GED.\"id_GroupEvent\", GED.\"id_Barn\", GED.\"dateOfEntry\", GED.\"id_Room\", GED.\"id_EmployeeGroup\", GED.\"numberOfPigs\", "
				 +" GED.\"weightInKgs\", GED.\"indeventoryAdjustment\", GED.\"remarks\", GED.\"lastUpdated\", GED.\"userUpdated\", GED.\"id_TransportDestination\", GED.\"id_SowSource\",GED.\"id_Premise\", GED.\"id_FromGroup\",GED.\"id_RemovalEventExceptSalesDetails\",GED.\"id_SalesEventDetails\", '' as \"groupId\" " 
				 +" from pigtrax.\"GroupEventDetails\" GED  where GED.\"id_GroupEvent\" = ? and GED.\"id_RemovalEventExceptSalesDetails\" = ? ";
				
				List<GroupEventDetails> groupEventDetailsList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, id);
						ps.setInt(2, removalId);
					}}, new GroupEventDetailsMapper());

				if(groupEventDetailsList != null && groupEventDetailsList.size() > 0){
					return groupEventDetailsList.get(0);
				}
				return null;
	}

	@Override
	public int updateGroupEventDetails(final GroupEventDetails groupEventDetails)
			throws SQLException {
		final String Qry = "update pigtrax.\"GroupEventDetails\" set \"id_Barn\" = ?, \"dateOfEntry\" = ?, \"id_Room\" = ?, \"id_EmployeeGroup\"= ?," +
			"\"numberOfPigs\"= ?, \"weightInKgs\" = ?, \"indeventoryAdjustment\" = ?, \"remarks\" = ?,  "
			+ "\"lastUpdated\" = current_timestamp, \"userUpdated\" = ?, \"id_TransportDestination\" = ?,\"id_SowSource\"=?,\"id_Premise\" = ?,\"id_RemovalEventExceptSalesDetails\" = ?,\"id_SalesEventDetails\" = ? where \"id\" = ? ";

		return this.jdbcTemplate.update(Qry, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				 if(groupEventDetails.getBarnId() != null && groupEventDetails.getBarnId() != 0)
 	            	ps.setInt(1, groupEventDetails.getBarnId());
 	            else
 	            	ps.setNull(1, java.sql.Types.INTEGER);
				
				ps.setDate(2, new java.sql.Date(groupEventDetails.getDateOfEntry()
						.getTime()));
				if(groupEventDetails.getRoomId() != null && groupEventDetails.getRoomId() != 0)
 	            	ps.setInt(3, groupEventDetails.getRoomId());
 	            else
 	            	ps.setNull(3, java.sql.Types.INTEGER);
				if(groupEventDetails.getEmployeeGroupId() != null && groupEventDetails.getEmployeeGroupId() != 0)
 	            	ps.setInt(4, groupEventDetails.getEmployeeGroupId());
 	            else
 	            	ps.setNull(4, java.sql.Types.INTEGER);
				
				ps.setInt(5, groupEventDetails.getNumberOfPigs());
				ps.setDouble(6, groupEventDetails.getWeightInKgs());
				
				if(groupEventDetails.getInventoryAdjustment() != null && groupEventDetails.getInventoryAdjustment() != 0)
 	            	ps.setInt(7, groupEventDetails.getInventoryAdjustment());
 	            else
 	            	ps.setNull(7, java.sql.Types.INTEGER);
 			
 	            ps.setString(8, groupEventDetails.getRemarks());
				ps.setString(9, groupEventDetails.getUserUpdated());
				
				if(groupEventDetails.getTransportDestination() != null && groupEventDetails.getTransportDestination() != 0)
 	            	ps.setInt(10, groupEventDetails.getTransportDestination());
 	            else
 	            	ps.setNull(10, java.sql.Types.INTEGER);
				if(groupEventDetails.getSowSourceId() != null && groupEventDetails.getSowSourceId() != 0)
 	            	ps.setInt(11, groupEventDetails.getSowSourceId());
 	            else
 	            	ps.setNull(11, java.sql.Types.INTEGER);
				
				if(groupEventDetails.getPremiseId() != null && groupEventDetails.getPremiseId() != 0)
 	            	ps.setInt(12, groupEventDetails.getPremiseId());
 	            else
 	            	ps.setNull(12, java.sql.Types.INTEGER);			
				
				if(groupEventDetails.getRemovalId() != null && groupEventDetails.getRemovalId() != 0)
 	            	ps.setInt(13, groupEventDetails.getRemovalId());
 	            else
 	            	ps.setNull(13, java.sql.Types.INTEGER);
				
				if(groupEventDetails.getSalesId() != null && groupEventDetails.getSalesId() != 0)
 	            	ps.setInt(14, groupEventDetails.getSalesId());
 	            else
 	            	ps.setNull(14, java.sql.Types.INTEGER);
				
				ps.setInt(15, groupEventDetails.getId());
			}
		});

	}

	@Override
	public int addGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException {
		final String Qry = "insert into pigtrax.\"GroupEventDetails\"(\"id_GroupEvent\", \"id_Barn\", \"dateOfEntry\", \"id_Room\", \"id_EmployeeGroup\", \"numberOfPigs\","
					+"\"weightInKgs\", \"indeventoryAdjustment\", \"remarks\", \"lastUpdated\", \"userUpdated\", "
					+ "\"id_TransportDestination\", \"id_SowSource\",\"id_Premise\", \"id_PigletStatusEvent\", \"id_FromGroup\",\"id_RemovalEventExceptSalesDetails\",\"id_SalesEventDetails\", \"groupEventActionType\") "
				+ "values(?,?,?,?,?,?,?,?,?,current_timestamp,?,?, ?,?,?, ?,?,?,?)";
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(
	    	    new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement ps =
	    	                con.prepareStatement(Qry, new String[] {"id"});
	    	            ps.setInt(1, groupEventDetails.getGroupId());
	    	            
	    	            if(groupEventDetails.getBarnId() != null && groupEventDetails.getBarnId() != 0)
	    	            	ps.setInt(2, groupEventDetails.getBarnId());
	    	            else
	    	            	ps.setNull(2, java.sql.Types.INTEGER);
	    	            
	    	            ps.setDate(3, new java.sql.Date(groupEventDetails.getDateOfEntry().getTime()));
	    	            
	    	            if(groupEventDetails.getRoomId() != null && groupEventDetails.getRoomId() != 0)
	    	            	ps.setInt(4, groupEventDetails.getRoomId());
	    	            else
	    	            	ps.setNull(4, java.sql.Types.INTEGER);
	    	            
	    	            if(groupEventDetails.getEmployeeGroupId() != null && groupEventDetails.getEmployeeGroupId() != 0)
	    	            	ps.setInt(5, groupEventDetails.getEmployeeGroupId());
	    	            else
	    	            	ps.setNull(5, java.sql.Types.INTEGER);
	    	            
	    	            ps.setObject(6, groupEventDetails.getNumberOfPigs(), java.sql.Types.INTEGER);
	    	            ps.setObject(7, groupEventDetails.getWeightInKgs(), java.sql.Types.DOUBLE );
	    	            
	    	            if(groupEventDetails.getInventoryAdjustment() != null && groupEventDetails.getInventoryAdjustment() != 0)
	    	            	ps.setInt(8, groupEventDetails.getInventoryAdjustment());
	    	            else
	    	            	ps.setNull(8, java.sql.Types.INTEGER);
	    	            
	    	            ps.setString(9, groupEventDetails.getRemarks());
	    	            ps.setString(10, groupEventDetails.getUserUpdated());
	    	            if(groupEventDetails.getTransportDestination() != null && groupEventDetails.getTransportDestination() != 0)
	     	            	ps.setInt(11, groupEventDetails.getTransportDestination());
	     	            else
	     	            	ps.setNull(11, java.sql.Types.INTEGER);
	    	            if(groupEventDetails.getSowSourceId() != null && groupEventDetails.getSowSourceId() != 0)
	     	            	ps.setInt(12, groupEventDetails.getSowSourceId());
	     	            else
	     	            	ps.setNull(12, java.sql.Types.INTEGER);
	    	            if(groupEventDetails.getPremiseId() != null && groupEventDetails.getPremiseId() != 0)
	     	            	ps.setInt(13, groupEventDetails.getPremiseId());
	     	            else
	     	            	ps.setNull(13, java.sql.Types.INTEGER);
	    	            if(groupEventDetails.getPigletStatusEventId() != null && groupEventDetails.getPigletStatusEventId() != 0)
	     	            	ps.setInt(14, groupEventDetails.getPigletStatusEventId());
	     	            else
	     	            	ps.setNull(14, java.sql.Types.INTEGER);
	    	            if(groupEventDetails.getFromGroupId() != null && groupEventDetails.getFromGroupId() != 0)
	     	            	ps.setInt(15, groupEventDetails.getFromGroupId());
	     	            else
	     	            	ps.setNull(15, java.sql.Types.INTEGER);
	    	            if(groupEventDetails.getRemovalId() != null && groupEventDetails.getRemovalId() != 0)
	     	            	ps.setInt(16, groupEventDetails.getRemovalId());
	     	            else
	     	            	ps.setNull(16, java.sql.Types.INTEGER);
	    	            if(groupEventDetails.getSalesId() != null && groupEventDetails.getSalesId() != 0)
	     	            	ps.setInt(17, groupEventDetails.getSalesId());
	     	            else
	     	            	ps.setNull(17, java.sql.Types.INTEGER);
	    	            if(groupEventDetails.getGroupEventActionType() != null && groupEventDetails.getGroupEventActionType() != 0)
	     	            	ps.setInt(18, groupEventDetails.getGroupEventActionType());
	     	            else
	     	            	ps.setNull(18, java.sql.Types.INTEGER);
	    	            return ps;
	    	        }
	    	    },
	    	    holder);
		int keyVal = holder.getKey().intValue();
		logger.info("Key generated = "+keyVal);
		
		return keyVal;
	}

	@Override
	public void deleteGroupEventDetailsByGroupId(final Integer id) throws SQLException {
		 final String qry = "delete from pigtrax.\"GroupEventDetails\" where \"id\" = ?";
			
			this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, id);
				}
			});
		
	}
	
		@Override
		public void deleteGroupEventDetailsByPigletEvent(final Integer pigletStatusEventId)
				throws SQLException {
			final String qry = "delete from pigtrax.\"GroupEventDetails\" where \"id_PigletStatusEvent\" = ?";
			
			this.jdbcTemplate.update(qry, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, pigletStatusEventId);
				}
			});
			
		}
	
	
	 private static final class GroupEventDetailsMapper implements RowMapper<GroupEventDetails> {
			public GroupEventDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
				GroupEventDetails groupEventDetails = new GroupEventDetails();
				groupEventDetails.setId(rs.getInt("id"));
				groupEventDetails.setGroupId(rs.getInt("id_GroupEvent"));
				groupEventDetails.setBarnId(rs.getInt("id_Barn"));
				groupEventDetails.setDateOfEntry(rs.getDate("dateOfEntry"));
				groupEventDetails.setRoomId(rs.getInt("id_Room"));
				groupEventDetails.setEmployeeGroupId(rs.getInt("id_EmployeeGroup"));
				groupEventDetails.setNumberOfPigs(rs.getInt("numberOfPigs"));
				groupEventDetails.setWeightInKgs(rs.getDouble("weightInKgs"));
				groupEventDetails.setInventoryAdjustment(rs.getInt("indeventoryAdjustment"));
				groupEventDetails.setRemarks(rs.getString("remarks"));
				groupEventDetails.setLastUpdated(rs.getDate("lastUpdated"));
				groupEventDetails.setUserUpdated(rs.getString("userUpdated"));
				groupEventDetails.setTransportDestination(rs.getInt("id_TransportDestination"));
				groupEventDetails.setSowSourceId(rs.getInt("id_SowSource"));
				groupEventDetails.setPremiseId(rs.getInt("id_Premise"));
				groupEventDetails.setFromGroupId(rs.getObject("id_FromGroup") != null ? rs.getInt("id_FromGroup") : null);
				groupEventDetails.setFromGroupIdStr(rs.getString("groupId"));
				groupEventDetails.setRemovalId(rs.getInt("id_RemovalEventExceptSalesDetails"));
				groupEventDetails.setSalesId(rs.getInt("id_SalesEventDetails"));
				return groupEventDetails;
			}
		}
	 
	 
	 @Override
	 public List<GroupEventDetails> getAllTransfers( final int groupEventId) throws SQLException  
		{
		 String qry = " select  GED.\"id\" , GED.\"id_GroupEvent\", GED.\"id_Barn\", GED.\"dateOfEntry\", GED.\"id_Room\", GED.\"id_EmployeeGroup\", GED.\"numberOfPigs\", "
				 +" GED.\"weightInKgs\", GED.\"indeventoryAdjustment\", GED.\"remarks\", GED.\"lastUpdated\", GED.\"userUpdated\", GED.\"id_TransportDestination\", GED.\"id_SowSource\",GED.\"id_Premise\", GED.\"id_FromGroup\",GED.\"id_RemovalEventExceptSalesDetails\",GED.\"id_SalesEventDetails\", '' as \"groupId\" " 
				 +" from pigtrax.\"GroupEventDetails\" GED  where (GED.\"id_GroupEvent\" = ? OR GED.\"id_FromGroup\" = ?) AND GED.\"id_GroupEvent\" > 0 and GED.\"id_FromGroup\" > 0  ";
				
				List<GroupEventDetails> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, groupEventId);
						ps.setInt(2, groupEventId);
					}}, new GroupEventDetailsMapper()); 

				if(groupEventList != null && groupEventList.size() > 0){
					return groupEventList;
				}
				return null;
		}
	 
	 
	 /**
	  * Get inventory count of a group at week level
	  * @param groupId
	  * @param ServDateSTART
	  * @param ServDateEND
	  * @return
	  * @throws Exception
	  */
	 public Map<Integer, Integer> getInventoryCntByWeek(final Integer groupId, final Date ServDateSTART, final Date ServDateEND) throws Exception
		{
			Map<Integer, Integer> weekCntMap = new HashMap<Integer, Integer>();
			Integer remainingCnt = 0;
			
			try{
			
				for(int i =0 ;i <26; i++)
				{		
					final int index = i+1;
					
					try{
					
					Thread.sleep(3*1000);
					final Date startDate = DateUtil.addDays(ServDateSTART, i*7);
					final Date endDate = DateUtil.addDays(ServDateEND, i*7);					
					
					final String qry = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num from pigtrax.\"GroupEventDetails\" GED "
							+ "where GED.\"id_GroupEvent\" = ? and GED.\"dateOfEntry\" between ? and ? and GED.\"groupEventActionType\" = ?";
					
					@SuppressWarnings("unchecked")
					Integer sowCount  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
						@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setInt(1, groupId);
								ps.setDate(2, new java.sql.Date(startDate.getTime()));
								ps.setDate(3, new java.sql.Date(endDate.getTime()));
								ps.setInt(4, GroupEventActionType.Add.getTypeCode());
							}
						},
				        new ResultSetExtractor() {
				          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				            if (resultSet.next()) {
				              return resultSet.getInt(1);
				            }
				            return 0;
				          }
				        });
					
					weekCntMap.put(index, sowCount);
					
					}
					catch(Exception ex)
					{
						Thread.sleep(5*1000);
						weekCntMap.put(index, 0);
					}
					
										
				}
			
			}
			catch(Exception ex)
			{
				Thread.sleep(5*1000);
			}
			
			return weekCntMap;
			
		}
	 
	 /**
	  * Get average start date of a given group
	  */
	  public Date getAverageStartDate(Integer groupId) {
			String sql = "select to_timestamp(avg(extract(epoch from T.startDt)))  from ( "
						 +" select \"groupStartDateTime\" as startDt from pigtrax.\"GroupEvent\" where \"id\" = "+groupId
						 +" union "
						 +" select \"dateOfEntry\" as startDt from pigtrax.\"GroupEventDetails\" where \"id_GroupEvent\" = "+groupId
						 +" and \"groupEventActionType\" = "+GroupEventActionType.Add.getTypeCode()+")T" ;				
			
			@SuppressWarnings("unchecked")
			Date startDate  = (Date)jdbcTemplate.query(sql,
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getDate(1);
		            }
		            return null;
		          }
		        });
			
			return startDate;
			
		}
	  
	  
	  /**
	  * Get average start date of a given group
	  */
	  public Date getAverageEndDate(Integer groupId) {
			String sql = "select to_timestamp(avg(extract(epoch from \"salesDateTime\")))::date  from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\" = "+groupId ;				
			
			@SuppressWarnings("unchecked")
			Date endDate  = (Date)jdbcTemplate.query(sql,
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getDate(1);
		            }
		            return null;
		          }
		        });
			
			return endDate;
			
		}
		 
		  
     /**
	  * Get start date variance a given group
	  */
	  public Integer getStartDateVariance(Integer groupId) {
			String sql = "select T.startDt  from ( "
						 +" select \"dateOfEntry\" as startDt from pigtrax.\"GroupEventDetails\" where \"id_GroupEvent\" = "+groupId
						 +" and \"groupEventActionType\" = "+GroupEventActionType.Add.getTypeCode()+") T " ;				
			
			@SuppressWarnings("unchecked")
			Date startDate  = (Date)jdbcTemplate.query(sql+" order by T.startDt asc limit 1 ",
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getDate(1);
		            }
		            return null;
		          }
		        });
			
			
			@SuppressWarnings("unchecked")
			Date endDate  = (Date)jdbcTemplate.query(sql+" order by T.startDt desc limit 1 ",
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getDate(1);
		            }
		            return null;
		          }
		        });
			
			int duration = 0;
			if(startDate != null && endDate != 1)
			{
				DateTime start = new DateTime(startDate.getTime());
				DateTime end = new DateTime(endDate.getTime());
				duration  = Days.daysBetween(start, end).getDays();
			}
			return duration;
			
		}
	  

			  
	  public Integer getEndDateVariance(Integer groupId) {
			
		  String sql = "select \"salesDateTime\"  from pigtrax.\"SalesEventDetails\" where \"id_GroupEvent\" = "+groupId ;				
			
			@SuppressWarnings("unchecked")
			Date startDate  = (Date)jdbcTemplate.query(sql+" order by \"salesDateTime\" asc limit 1",
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getDate(1);
		            }
		            return null;
		          }
		        });
			
			
			@SuppressWarnings("unchecked")
			Date endDate  = (Date)jdbcTemplate.query(sql+" order by \"salesDateTime\" desc limit 1",
		        new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		            if (resultSet.next()) {
		              return resultSet.getDate(1);
		            }
		            return null;
		          }
		        });
			
			int duration = 0;
			
			if(startDate != null && endDate != null)
			{
				DateTime start = new DateTime(startDate.getTime());
				DateTime end = new DateTime(endDate.getTime());
				duration  = Days.daysBetween(start, end).getDays();
			}
			
			return duration;
		}
			  
			  
		  public Map<String, Object> getTransferInOutCountAndWt(Integer groupId) {
			  try{
				  List<GroupEventDetails> groupEventList = getAllTransfers(groupId);
				  Double transferOutWt = 0D;
				  Double transferInWt = 0D;
				  Integer transferInCnt = 0;
				  Integer transferOutCnt = 0;
				  Map<String, Object> transferInOutDetails = new HashMap<String, Object>();
				  
			     if(groupEventList != null && 0 <groupEventList.size())
			     {
			    	 for(GroupEventDetails groupDetails : groupEventList)
			    	 {
			    		 if(groupDetails.getFromGroupId().intValue() == groupId)
			    		 {
			    			 if(groupDetails.getNumberOfPigs() > 0)
			    			 {
			    				 transferOutWt += groupDetails.getWeightInKgs();
			    				 transferOutCnt += groupDetails.getNumberOfPigs();
			    			 }
					     }
			    		 else if(groupDetails.getGroupId() == groupId && groupDetails.getFromGroupId() > 0)
			    		 { 
			    			 if(groupDetails.getNumberOfPigs() > 0)
			    			 {
			    				 transferInCnt += groupDetails.getNumberOfPigs();
			    			 	 transferInWt += groupDetails.getWeightInKgs();
			    			 }
			    		 }
			    	 }
			     }
			     
			     transferInOutDetails.put("transferInWt", transferInWt);
			     transferInOutDetails.put("transferInCnt", transferInCnt);
			     transferInOutDetails.put("transferOutCnt", transferOutCnt);
			     transferInOutDetails.put("transferOutWt", transferOutWt);
			     return transferInOutDetails;
			     
			  }
			  catch(Exception ex)
			  {
				  
			  }
				return null;
			}
		  
		  
		  
		  public Integer calculatePigDays(Integer groupId) {
			  Integer sowCountDays = 0;
			  String sql = "select min(\"dateOfEntry\")  from pigtrax.\"GroupEventDetails\" where \"id_GroupEvent\" = "+groupId;				
				
				@SuppressWarnings("unchecked")
				Date startDate  = (Date)jdbcTemplate.query(sql,
			        new ResultSetExtractor() {
			          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDate(1);
			            }
			            return null;
			          }
			        });
				
				sql = "select max(\"dateOfEntry\")  from pigtrax.\"GroupEventDetails\" where \"id_GroupEvent\" = "+groupId;	
				@SuppressWarnings("unchecked")
				Date endDate  = (Date)jdbcTemplate.query(sql,
			        new ResultSetExtractor() {
			          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDate(1);
			            }
			            return null;
			          }
			        });
				
				if(startDate != null && endDate != null)
				{						
					while(startDate.getTime()<= endDate.getTime())
					{
						final Date start = startDate;
						String qry = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num from pigtrax.\"GroupEventDetails\" GED "
								+ "where GED.\"id_GroupEvent\" = "+groupId+" and GED.\"dateOfEntry\" <= ?";
						
						
						sowCountDays  += (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
							@Override
								public void setValues(PreparedStatement ps) throws SQLException {
									ps.setDate(1, new java.sql.Date(start.getTime()));
								}
							},
					        new ResultSetExtractor() {
					          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
					            if (resultSet.next()) {
					              return resultSet.getInt(1);
					            }
					            return 0;
					          }
					        });
						
						startDate = DateUtil.addDays(startDate, 1);
					}
				}
				
				return sowCountDays;
			}
		  
		  
		  public Integer calculateDaysOnFeed(Integer groupId) {
			  String sql = "select \"dateOfEntry\"  from pigtrax.\"GroupEventDetails\" where \"id_GroupEvent\" = "+groupId ;				
				
				@SuppressWarnings("unchecked")
				Date startDate  = (Date)jdbcTemplate.query(sql+" order by \"dateOfEntry\" asc limit 1",
			        new ResultSetExtractor() {
			          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDate(1);
			            }
			            return null;
			          }
			        });
				
				
				@SuppressWarnings("unchecked")
				Date endDate  = (Date)jdbcTemplate.query(sql+" order by \"dateOfEntry\" desc limit 1",
			        new ResultSetExtractor() {
			          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDate(1);
			            }
			            return null;
			          }
			        });
				
				int duration = 0;
				
				if(startDate != null && endDate != null)
				{
					DateTime start = new DateTime(startDate.getTime());
					DateTime end = new DateTime(endDate.getTime());
					duration  = Days.daysBetween(start, end).getDays();
				}
				
				return duration;
			}
		  
			 
}
