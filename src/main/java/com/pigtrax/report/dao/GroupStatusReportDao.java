package com.pigtrax.report.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dao.interfaces.PremisesDao;
import com.pigtrax.master.dto.Premises;
import com.pigtrax.master.dto.Room;
import com.pigtrax.pigevents.beans.CompanyTarget;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.dao.interfaces.CompanyTargetDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventRoomDao;
import com.pigtrax.usermanagement.enums.GroupEventActionType;
import com.pigtrax.usermanagement.enums.RemovalEventType;
import com.pigtrax.util.DateUtil;

@Repository
@Transactional
public class GroupStatusReportDao {

	private static final Logger logger = Logger.getLogger(GroupStatusReportDao.class);

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	GroupEventDao groupDao;
	
	@Autowired
	PremisesDao premisesDao;
	
	
	@Autowired
	CompanyTargetDao companyTargetDao;
	
	@Autowired
	RefDataCache refDataCache;	
	
	@Autowired
	GroupEventRoomDao eventRoomDao;
	
	@Autowired
	GroupEventDetailsDao groupEventDetailsDao;
	

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcTemplate.setQueryTimeout(10*60); 
	}
	
	public List<Map<String, Object>> getGroupStatusList(Integer companyId, String selectedPremise, Date inputStartDate, Date inputEndDate, 
								List<Map<String, Object>> rangeList,  String language, String reportType, String selectedSowSource) throws Exception
	{	
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		Premises sourcePremise = null;
		if(selectedSowSource != null && !selectedSowSource.equals("-1"))
			sourcePremise = premisesDao.findByPremisesByAutoGeneratedId(Integer.parseInt(selectedSowSource));
		
		String[] premiseIds = null;
		if(selectedPremise != null) premiseIds = selectedPremise.split(",");
		
		if(premiseIds != null && 0 < premiseIds.length)
		{
			for(String premiseId : premiseIds)
			{
				Map<String, Object> row = rangeList.get(0);
				CompanyTarget target = companyTargetDao.getCompanyTargetByType(Integer.parseInt(premiseId), 122); // Target type id for DOF
				List<GroupEvent> groups = getAllGroups(Integer.parseInt(premiseId), inputStartDate, inputEndDate, selectedSowSource);
				
				if(groups != null)
				{
					for(GroupEvent group : groups)
					{
						if(selectedSowSource == null || selectedSowSource.equals("-1"))
						{
						
						List<GroupEventDetails> groupEventDetailsList = groupEventDetailsDao.groupEventDetailsListByGroupId(group.getId());
							if(groupEventDetailsList != null && 0 <groupEventDetailsList.size())
							{
								for(GroupEventDetails eventDetails : groupEventDetailsList)
								{
									if(eventDetails.getSowSourceId() != null && eventDetails.getSowSourceId() > 0)
									{
										sourcePremise = premisesDao.findByPremisesByAutoGeneratedId(eventDetails.getSowSourceId());
										break;
									}
								}
							}
						}
						
						GroupEvent barnRoomDetails = eventRoomDao.getGroupRoomAndBarnDetails(group.getId());
						if(barnRoomDetails == null)
							barnRoomDetails = new GroupEvent();
						
						Map<String, Object> record = new HashMap<String, Object>();
						
						
						record.put("EventDateStart", group.getGroupStartDateTime());
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(group.getGroupStartDateTime());
						int week = cal.get(Calendar.WEEK_OF_YEAR);
						
						record.put("EventDateEnd", row.get("EventDateEnd"));
						/**
						 * Calendar week of the event start date
						 */
						record.put("WK", week);
						
						record.put("SowSource", sourcePremise.getName());  
						record.put("FarmName",group.getPremiseIdStr());
						record.put("BarnId", barnRoomDetails.getBarnId());
						record.put("RoomId", barnRoomDetails.getRoomId());						
						
						
						Date ServDateSTART = (Date)record.get("EventDateStart");				
						cal.add(Calendar.DAY_OF_MONTH, 6);
						
						Date ServDateEND = cal.getTime();
						
						Integer pigSpaces = barnRoomDetails.getRoomSpace();
						
						record.put("GroupId", group.getGroupId());
						record.put("GroupEventCloseDate", group.getGroupCloseDateTime());
						
						Map<String, Object> startWtAndHeadMap = getStartWtAndHead(ServDateSTART, ServDateEND, group.getId());
						record.put("StartWt", startWtAndHeadMap.get("StartWt"));
						Long startHead  = 0L;
						startHead = (Long)startWtAndHeadMap.get("StartHd");
						record.put("StartHd", startHead);
						Integer phaseId = getPhaseOfProduction(inputStartDate, inputEndDate, group.getId());
						if(phaseId != null && phaseId > 0)
							record.put("PhaseType",refDataCache.getPhaseOfProductionTypeMap(language).get(phaseId));
						else
							record.put("PhaseType", " ");
						Integer deadCount = getDeadsCount(inputEndDate, group.getId());
						record.put("Deads", deadCount);
						
						Integer inventoryCnt = getInventoryCount(inputEndDate, group.getId()); 
						if(inventoryCnt == null ) inventoryCnt = 0;
						
						record.put("Inventory", inventoryCnt);
						Double density = 0D;
						
						if(inventoryCnt >0 && pigSpaces >0)
							density = Math.round(inventoryCnt*100.0)/(pigSpaces*100.0);
						record.put("Density", density);
						
						Integer salesCount = getSalesCount(inputEndDate, group.getId());
						if(salesCount == null ) salesCount = 0;
						record.put("Sales", salesCount);
						
						Double mortalityPercentage = 0D;
						if(deadCount >0 && startHead > 0)
							mortalityPercentage =  (double) (deadCount*100.0)/(startHead); 
						record.put("Mortality%", mortalityPercentage);
						
						Date projectedSalesDate = null;
						Integer dofDays = 0;
						
						if(target != null)
						{
							try{
								dofDays = Integer.parseInt(target.getTargetValue());
							}catch(NumberFormatException nfEx)
							{
								dofDays = 0;
							}
						}
						
						projectedSalesDate = DateUtil.addDays(group.getGroupStartDateTime(),dofDays);
						record.put("ProjectedSaleDate", projectedSalesDate);
						
						cal = Calendar.getInstance();
						cal.setTime(projectedSalesDate);
						week = cal.get(Calendar.WEEK_OF_YEAR);
						record.put("SaleWk", week);
						
						Map<Integer, Integer> weekCntMap = null;
						if(("current").equalsIgnoreCase(reportType))
						{
							weekCntMap = getInventoryCntByWeek(group.getId(),ServDateSTART, ServDateEND); 
						}
						else 
						{
							weekCntMap = getMortalityCntByWeek(group.getId(),ServDateSTART, ServDateEND);
						}
						
						if(weekCntMap != null && weekCntMap.size() > 0)
						{ 
							
							int i = 1;
							while(i<=26)
							{
								record.put("W"+i,weekCntMap.get(i));
								i++;
							}
						}
						
						resultList.add(record);
					}
				}
				
			}
		}
		
		
		
		return resultList;
	}
	
	private Map<String, Object> getStartWtAndHead(final Date ServDateSTART, final Date ServDateEND, final Integer groupId)
	{	
		final String qry = "select SUM(coalesce(GED.\"numberOfPigs\",0)*coalesce(GED.\"weightInKgs\",0))/SUM(coalesce(GED.\"numberOfPigs\",0)) as StartWt , SUM(coalesce(GED.\"numberOfPigs\",0)) as StartHd"
				+ "  from pigtrax.\"GroupEventDetails\" GED "
				+ " where \"id_GroupEvent\" = ? and \"groupEventActionType\" = ?";
			
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> cnt  = (List<Map<String, Object>>)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setInt(2, GroupEventActionType.Add.getTypeCode());
				}
			}, new DataMapper());
		
		if(cnt != null && 0<cnt.size())
			return cnt.get(0);
		return null;
	}
	
	private Date getGroupStartDate(final Integer groupId)
	{
		final String qry = " select \"groupStartDateTime\" from pigtrax.\"GroupEvent\"  "
				+ "where \"id\" = ?";
		
			
		@SuppressWarnings("unchecked")
		Date startDate  = (Date)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
				}
			},new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getDate(1);
			            }
			            return null;
			          }
			        });
		
		return startDate ;
	}
	
	private Integer getPigSpaces(final Integer groupId)
	{
		final String qry = " select \"pigSpaces\" from pigtrax.\"Room\" R "
				+ "JOIN pigtrax.\"GroupEventRoom\" GER ON GER.\"id_Room\" = R.\"id\" "
				+ "JOIN pigtrax.\"GroupEventPhaseChange\" GEPC ON GER.\"id_GroupEventPhaseChange\" = GEPC.\"id\" "
				+ "JOIN pigtrax.\"GroupEvent\" GE ON GEPC.\"id_GroupEvent\" = GE.\"id\"  where GE.\"id\" = ?";
		
			
		@SuppressWarnings("unchecked")
		Integer pigSpaces  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
				}
			},new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return null;
			          }
			        });
		
		return pigSpaces ;
	}
	
	private Integer getPhaseOfProduction(final Date ServDateSTART, final Date ServDateEND, final Integer groupId)
	{
		final String qry = "select \"id_PhaseOfProductionType\" from pigtrax.\"GroupEventPhaseChange\" where \"id\" = (select max(\"id\") from pigtrax.\"GroupEventPhaseChange\" where " 
			+" (\"phaseStartDate\" between ?  and ? or \"phaseStartDate\" < ?)   and \"id_GroupEvent\" = ?)";
		
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {					
					ps.setDate(1, new java.sql.Date(ServDateSTART.getTime()));
					ps.setDate(2, new java.sql.Date(ServDateEND.getTime()));
					ps.setDate(3, new java.sql.Date(ServDateEND.getTime()));
					ps.setInt(4, groupId);
				}
			}, new ResultSetExtractor() {
		          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return 0;
			          }
			        });
		
		return cnt ;
	}
	
	
	/**
	 * Find out the count of mortality as of end date for the given group
	 * @param endDate
	 * @param groupId
	 * @return
	 */
	private Integer getDeadsCount(final Date endDate, final Integer groupId)
	{
		final String qry = "select sum(\"numberOfPigs\") from pigtrax.\"RemovalEventExceptSalesDetails\" where \"id_GroupEvent\" = ? and \"id_RemovalEvent\" = ? and \"removalDateTime\" <=  ?";
			
		@SuppressWarnings("unchecked")
		Integer cnt  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setInt(2, RemovalEventType.Mortality.getTypeCode());
					ps.setDate(3, new java.sql.Date(endDate.getTime()));
					
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
	 * Find the number of pigs sold from a group as of given end date
	 * @param endDate
	 * @param groupId
	 * @return
	 */
	private Integer getSalesCount(final Date endDate, final Integer groupId)
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
	
	
	
	private static final class DataMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("StartHd",rs.getLong("StartHd"));
			dataMap.put("StartWt",rs.getDouble("StartWt"));
			return dataMap;
		}
	}
	
	
	private Map<Integer, Integer> getInventoryCntByWeek(final Integer groupId, final Date ServDateSTART, final Date ServDateEND) throws Exception
	{
		Map<Integer, Integer> weekCntMap = new HashMap<Integer, Integer>();
		Integer remainingCnt = 0;
		for(int i =0 ;i <26; i++)
		{
			Thread.sleep(5*1000);
			final Date startDate = DateUtil.addDays(ServDateSTART, i*7);
			final Date endDate = DateUtil.addDays(ServDateEND, i*7);
			final int index = i+1;
			
			final String qry = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num from pigtrax.\"GroupEventDetails\" GED "
					+ "where GED.\"id_GroupEvent\" = ? and GED.\"dateOfEntry\" between ? and ? and GED.\"groupEventActionType\" = ?";
			
			try{
			
			
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
				weekCntMap.put(index, 0);
			}
		}
		
		return weekCntMap;
		
	}
	
	/**
	 * To find the inventory count of a group as of the given end date
	 * @param endDate
	 * @param groupId
	 * @return
	 */
	private Integer getInventoryCount(final Date endDate, final Integer groupId)
	{
		
		final String qry = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num from pigtrax.\"GroupEventDetails\" GED "
				+ "where GED.\"id_GroupEvent\" = ? and GED.\"dateOfEntry\" <= ?";
		
		@SuppressWarnings("unchecked")
		Integer sowCount  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
			@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, groupId);
					ps.setDate(2, new java.sql.Date(endDate.getTime()));
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
		return sowCount;
	}
	
	
	
	
	private Map<Integer, Integer> getMortalityCntByWeek(final Integer groupId, final Date ServDateSTART, final Date ServDateEND) throws Exception
	{
		Map<Integer, Integer> weekCntMap = new HashMap<Integer, Integer>();
		Integer remainingCnt = 0;
		for(int i =0 ;i <26; i++)
		{
			Thread.sleep(5*1000);
			final Date startDate = DateUtil.addDays(ServDateSTART, i*7);
			final Date endDate = DateUtil.addDays(ServDateEND, i*7);
			final int index = i+1;
			
			final String qry = " select coalesce(sum(RES.\"numberOfPigs\"),0) as Num from pigtrax.\"RemovalEventExceptSalesDetails\" RES "
					+ "where RES.\"id_GroupEvent\" = ? and RES.\"removalDateTime\" between ? and ? and RES.\"id_RemovalEvent\" = ?";
			
			
			
			@SuppressWarnings("unchecked")
			Integer sowCount  = (Integer)jdbcTemplate.query(qry,new PreparedStatementSetter() {
				@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, groupId);
						ps.setDate(2, new java.sql.Date(startDate.getTime()));
						ps.setDate(3, new java.sql.Date(endDate.getTime()));
						ps.setInt(4, RemovalEventType.Mortality.getTypeCode());
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
		
		return weekCntMap;
		
	}
	
	private List<Room> getAllRooms(String premiseIdPK)
	{
		String qry = "Select PR.\"permiseId\", B.\"id_Premise\",  B.\"barnId\",  R.* from  pigtrax.\"Room\" R JOIN pigtrax.\"Barn\" B ON R.\"id_Barn\" = B.\"id\" "
				+ " JOIN pigtrax.\"Premise\" PR ON B.\"id_Premise\" = PR.\"id\" JOIN pigtrax.\"Company\" C ON PR.\"id_Company\" = C.\"id\" where PR.\"id\" in ( "+premiseIdPK+") order by PR.\"id\", R.\"id\" ";
	    
	    
	    List<Room> roomList = jdbcTemplate.query(qry,new RoomMapper());
	    return roomList;
	}
	
	
	private List<GroupEvent> getAllGroups(final Integer premiseId, Date inputStartDate, Date inputEndDate, final String selectedSowSource)
	{
		
		final java.sql.Date startDate = new java.sql.Date(inputStartDate.getTime());
		final java.sql.Date endDate = new java.sql.Date(inputEndDate.getTime());
		
		String qry = "SELECT distinct GE.*, PR.\"name\" as farmName from pigtrax.\"GroupEvent\" GE  "					
					+ " JOIN pigtrax.\"GroupEventDetails\" GED ON GED.\"id_GroupEvent\" = GE.\"id\" "
					+ " JOIN pigtrax.\"Premise\" PR ON GE.\"id_Premise\" = PR.\"id\" "
					+ " WHERE GE.\"id_Premise\" =?  AND ( GE.\"groupStartDateTime\" between ? and ? OR GE.\"groupCloseDateTime\" between ? and ?) ";
		
		if(selectedSowSource != null && !selectedSowSource.equals("-1"))
				qry += " AND GED.\"id_SowSource\" in (?) ";
				
					qry += " order by GE.\"groupStartDateTime\" ";
		

		List<GroupEvent> groupEventList = jdbcTemplate.query(qry, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {	
				ps.setInt(1, premiseId);
				ps.setDate(2, startDate);
 				ps.setDate(3, endDate);
 				ps.setDate(4, startDate);
 				ps.setDate(5, endDate);
 				if(selectedSowSource != null && !selectedSowSource.equals("-1"))
 					ps.setInt(6,  Integer.parseInt(selectedSowSource));
			}}, new GroupEventMapper());
		
		return groupEventList;
	}
	
	private static final class GroupEventMapper implements RowMapper<GroupEvent> {
		public GroupEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			GroupEvent groupEvent = new GroupEvent();
			groupEvent.setId(rs.getInt("id"));
			groupEvent.setGroupId(rs.getString("groupId"));
			groupEvent.setGroupStartDateTime(rs.getDate("groupStartDateTime"));
			try {
				groupEvent.setGroupStartDateStr(DateUtil.convertToFormatString(groupEvent.getGroupStartDateTime(), "dd/MM/yyyy"));
			} catch (ParseException e) {
				groupEvent.setGroupStartDateStr(null);
			}
			
			groupEvent.setGroupCloseDateTime(rs.getDate("groupCloseDateTime"));
			try {
				groupEvent.setGroupCloseDateStr(DateUtil.convertToFormatString(groupEvent.getGroupCloseDateTime(), "dd/MM/yyyy"));
			} catch (ParseException e) {
				groupEvent.setGroupCloseDateStr(null);
			}
			groupEvent.setActive(rs.getBoolean("isActive"));
			groupEvent.setRemarks(rs.getString("remarks"));
			groupEvent.setLastUpdated(rs.getDate("lastUpdated"));
			groupEvent.setUserUpdated(rs.getString("userUpdated"));
			groupEvent.setCompanyId(rs.getInt("id_Company"));
			groupEvent.setCurrentInventory(rs.getInt("currentInventory"));
			groupEvent.setPreviousGroupId(rs.getString("previousGroupId"));
			groupEvent.setPhaseOfProductionTypeId(rs.getInt("id_PhaseOfProductionType"));
			groupEvent.setPremiseId(rs.getInt("id_Premise"));
			groupEvent.setPremiseIdStr(rs.getString("farmName"));
			return groupEvent;
		}
	}
	
	
	private static final class RoomMapper implements RowMapper<Room> {
		public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
			Room room = new Room();
			room.setId(rs.getInt("id"));
			room.setRoomId(rs.getString("roomId"));
			room.setBarnId(rs.getInt("id_Barn"));
			room.setLocation(rs.getString("location"));
			room.setActive(rs.getBoolean("isActive"));
			room.setRoomPositionId(rs.getInt("id_roomPosition"));
			room.setPigSpaces(rs.getInt("pigSpaces"));
			room.setPremiseIdStr(rs.getString("permiseId"));
			room.setBarnIdStr(rs.getString("barnId"));
			room.setPremiseId(rs.getInt("id_Premise"));
			return room;
		}
	}
	
	
}
