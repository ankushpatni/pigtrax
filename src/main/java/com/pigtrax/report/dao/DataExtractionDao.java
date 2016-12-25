package com.pigtrax.report.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DataExtractionDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger logger = Logger.getLogger(DataExtractionDao.class);
	

	
	/**
	 * Extract data from PigInfo
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getPigInfoData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new PigInfoMapper());
		
		return logList;
	} 
	
	private static final class PigInfoMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("pigId", rs.getString("pigId")!= null?rs.getString("pigId"):"");
			rowMap.put("sireId", rs.getString("sireId")!= null?rs.getString("sireId"):"");
			rowMap.put("damId", rs.getString("damId")!= null?rs.getString("damId"):"");
			rowMap.put("entryDate", rs.getDate("entryDate"));
			rowMap.put("gline", rs.getString("gline")!= null?rs.getString("gline"):"");
			rowMap.put("gcompany", rs.getString("gcompany")!= null?rs.getString("gcompany"):"");
			rowMap.put("birthDate", rs.getDate("birthDate"));
			rowMap.put("tattoo",rs.getString("tattoo")!= null?rs.getString("tattoo"):"");
			rowMap.put("alternateTattoo",rs.getString("alternateTattoo")!= null?rs.getString("alternateTattoo"):"");
			rowMap.put("roomId",rs.getString("roomId")!= null?rs.getString("roomId"):"");
			rowMap.put("sexType",rs.getString("sexType")!= null?rs.getString("sexType"):"");
			rowMap.put("gfunction",rs.getString("gfunction")!= null?rs.getString("gfunction"):"");
			rowMap.put("origin",rs.getString("origin")!= null?rs.getString("origin"):"");
			rowMap.put("parity",rs.getString("parity")!= null?rs.getString("parity"):"0");
			rowMap.put("remarks",rs.getString("remarks")!= null?rs.getString("remarks"):"");
			return rowMap;
		} 
	}
	
	/**
	 * Extract data from Breeding
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getBreedingData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new BreedingMapper());
		
		return logList;
	} 
	
	private static final class BreedingMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("pigId", rs.getString("pigId")!= null?rs.getString("pigId"):"");
			rowMap.put("serviceType", rs.getString("serviceType")!= null?rs.getString("serviceType"):"");
			rowMap.put("serviceGroupId", rs.getString("serviceGroupId")!= null?rs.getString("serviceGroupId"):"");
			rowMap.put("serviceStartDate", rs.getDate("serviceStartDate"));
			rowMap.put("weightInKgs", rs.getString("weightInKgs")!= null?rs.getString("weightInKgs"):"");
			rowMap.put("sowCondition", rs.getString("sowCondition")!= null?rs.getString("sowCondition"):"");
			return rowMap;
		}
	}
	
	
	/**
	 * Extract data from Pregnancy
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getPregnancyData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new PregnancyMapper());
		
		return logList;
	} 
	
	private static final class PregnancyMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("pigId", rs.getString("pigId")!= null?rs.getString("pigId"):"");
			rowMap.put("groupId", rs.getString("groupId")!= null?rs.getString("groupId"):"");
			rowMap.put("pregnancyEventType", rs.getString("pregnancyEventType")!= null?rs.getString("pregnancyEventType"):"");
			rowMap.put("pregnancyResultType", rs.getString("pregnancyResultType")!= null?rs.getString("pregnancyResultType"):"");
			rowMap.put("resultDate", rs.getDate("resultDate"));
			rowMap.put("sowCondition", rs.getString("sowCondition")!= null?rs.getString("sowCondition"):"");
			return rowMap;
		}
	}
	
	
	
	/**
	 * Extract data from Farrow
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getFarrowData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new FarrowMapper());
		
		return logList;
	} 
	
	private static final class FarrowMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {	
			
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("pigId", rs.getString("pigId")!= null?rs.getString("pigId"):"");
			rowMap.put("penId", rs.getString("penId")!= null?rs.getString("penId"):"");
			rowMap.put("farrowDateTime", rs.getDate("farrowDateTime"));
			rowMap.put("liveBorns", rs.getString("liveBorns")!= null?rs.getString("liveBorns"):"");
			rowMap.put("stillBorns", rs.getString("stillBorns")!= null?rs.getString("stillBorns"):"");
			rowMap.put("mummies", rs.getString("mummies")!= null?rs.getString("mummies"):"");
			rowMap.put("weakBorns", rs.getString("weakBorns")!= null?rs.getString("weakBorns"):"");
			rowMap.put("maleBorns", rs.getString("maleBorns")!= null?rs.getString("maleBorns"):"");
			rowMap.put("femaleBorns", rs.getString("femaleBorns")!= null?rs.getString("femaleBorns"):"");
			rowMap.put("weightInKgs", rs.getString("weightInKgs")!= null?rs.getString("weightInKgs"):"");
			rowMap.put("inducedBirth", rs.getString("inducedBirth")!= null?rs.getString("inducedBirth"):"");
			rowMap.put("assistedBirth", rs.getString("assistedBirth")!= null?rs.getString("assistedBirth"):"");
			rowMap.put("groupId", rs.getString("groupId")!= null?rs.getString("groupId"):"");
			rowMap.put("sowCondition", rs.getString("sowCondition")!= null?rs.getString("sowCondition"):"");
			rowMap.put("teats", rs.getString("teats")!= null?rs.getString("teats"):"");
			rowMap.put("remarks", rs.getString("remarks")!= null?rs.getString("remarks"):"");
			return rowMap;
		}
	}
	
	/**
	 * Extract data from PigletSTatus
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getPigletStatusData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new PigletStatusMapper());
		
		return logList;
	} 
	
	private static final class PigletStatusMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {		
						
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("pigId", rs.getString("pigId")!= null?rs.getString("pigId"):"");
			rowMap.put("eventType", rs.getString("eventType")!= null?rs.getString("eventType"):"");
			rowMap.put("eventDateTime", rs.getDate("eventDateTime"));
			rowMap.put("numberOfPigs", rs.getString("numberOfPigs")!= null?rs.getString("numberOfPigs"):"");
			rowMap.put("weightInKgs", rs.getString("weightInKgs")!= null?rs.getString("weightInKgs"):"");
			rowMap.put("groupId", rs.getString("groupId")!= null?rs.getString("groupId"):"");
			rowMap.put("transferToPig", rs.getString("transferToPig")!= null?rs.getString("transferToPig"):"");
			rowMap.put("mortalityReason", rs.getString("mortalityReason")!= null?rs.getString("mortalityReason"):"");
			rowMap.put("penId", rs.getString("penId")!= null?rs.getString("penId"):"");
			rowMap.put("sowCondition", rs.getString("sowCondition")!= null?rs.getString("sowCondition"):"");
			rowMap.put("remarks", rs.getString("remarks")!= null?rs.getString("remarks"):"");
			return rowMap;
		}
	}
	

	/**
	 * Extract data from RemovalEVent
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getMortalityAdjustmentDate(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new MortalityAdjMapper());
		
		return logList;
	} 
	
	private static final class MortalityAdjMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {		
						
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("groupId", rs.getString("groupId")!= null?rs.getString("groupId"):"");
			rowMap.put("pigId", rs.getString("pigId")!= null?rs.getString("pigId"):"");
			rowMap.put("removalDateTime", rs.getDate("removalDateTime"));
			rowMap.put("removalType", rs.getString("removalType") !=null?rs.getString("removalType") : "");
			rowMap.put("mortalityReason", rs.getString("mortalityReason")!= null?rs.getString("mortalityReason"):"");
			rowMap.put("numberOfPigs", rs.getString("numberOfPigs")!= null?rs.getString("numberOfPigs"):"");
			rowMap.put("weightInKgs", rs.getString("weightInKgs")!= null?rs.getString("weightInKgs"):"");
			rowMap.put("revenue", rs.getString("revenue")!= null?rs.getString("revenue"):"");
			rowMap.put("remarks", rs.getString("remarks")!= null?rs.getString("remarks"):"");
			return rowMap;
		}
	}	
	
	/**
	 * Extract data from Sales
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getSalesData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new SalesMapper());
		
		return logList;
	} 
	
	private static final class SalesMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {		
						
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("groupId", rs.getString("groupId")!= null?rs.getString("groupId"):"");
			rowMap.put("pigId", rs.getString("pigId")!= null?rs.getString("pigId"):"");
			rowMap.put("salesDateTime", rs.getDate("salesDateTime"));
			rowMap.put("numberOfPigs", rs.getString("numberOfPigs")!= null?rs.getString("numberOfPigs"):"");
			rowMap.put("salesType", rs.getString("salesType")!= null?rs.getString("salesType"):"");
			rowMap.put("salesReason", rs.getString("salesReason")!= null?rs.getString("salesReason"):"");
			rowMap.put("ticketNumber", rs.getString("ticketNumber")!= null?rs.getString("ticketNumber"):"");
			rowMap.put("invoiceId", rs.getString("invoiceId")!= null?rs.getString("invoiceId"):"");
			rowMap.put("revenueUsd", rs.getString("revenueUsd")!= null?rs.getString("revenueUsd"):"");
			rowMap.put("soldTo", rs.getString("soldTo")!= null?rs.getString("soldTo"):"");
			rowMap.put("truckId", rs.getString("truckId")!= null?rs.getString("truckId"):"");
			rowMap.put("trailerId", rs.getString("trailerId")!= null?rs.getString("trailerId"):"");
			rowMap.put("remarks", rs.getString("remarks")!= null?rs.getString("remarks"):"");
			return rowMap;
		}
	}
	
	/**
	 * Extract data from TRansfer
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getTransferData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new TransferMapper());
		
		return logList;
	} 
	
	private static final class TransferMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {		
						
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("groupId", rs.getString("groupId")!= null?rs.getString("groupId"):"");
			rowMap.put("pigId", rs.getString("pigId")!= null?rs.getString("pigId"):"");
			rowMap.put("removalDateTime", rs.getDate("removalDateTime"));
			rowMap.put("numberOfPigs", rs.getString("numberOfPigs")!= null?rs.getString("numberOfPigs"):"");
			rowMap.put("weightInKgs", rs.getString("weightInKgs")!= null?rs.getString("weightInKgs"):"");
			rowMap.put("fromPremise", rs.getString("fromPremise")!= null?rs.getString("fromPremise"):"");
			rowMap.put("toPremise", rs.getString("toPremise")!= null?rs.getString("toPremise"):"");
			rowMap.put("roomId", rs.getString("roomId")!= null?rs.getString("roomId"):"");
			rowMap.put("revenue", rs.getString("revenue")!= null?rs.getString("revenue"):"");
			rowMap.put("truckId", rs.getString("truckId")!= null?rs.getString("truckId"):"");
			rowMap.put("trailerId", rs.getString("trailerId")!= null?rs.getString("trailerId"):"");
			rowMap.put("remarks", rs.getString("remarks")!= null?rs.getString("remarks"):"");
			return rowMap;
		}
	}
	
	/**
	 * Extract data from Feed
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getFeedData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new FeedMapper());
		
		return logList;
	} 
	
	private static final class FeedMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {		
						
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("groupId", rs.getString("groupId")!= null?rs.getString("groupId"):"");
			rowMap.put("feedEventDate", rs.getDate("feedEventDate"));
			rowMap.put("ticketNumber", rs.getString("ticketNumber")!= null?rs.getString("ticketNumber"):"");
			rowMap.put("batchId", rs.getString("rationValue")!= null?rs.getString("rationValue"):"");
			rowMap.put("feedMedication", rs.getString("feedMedication")!= null?rs.getString("feedMedication"):"");
			rowMap.put("truckId", rs.getString("truckId")!= null?rs.getString("truckId"):"");
			rowMap.put("trailerId", rs.getString("trailerId")!= null?rs.getString("trailerId"):"");
			rowMap.put("feedMill", rs.getString("feedMill")!= null?rs.getString("feedMill"):"");
			rowMap.put("feedEventType", rs.getString("feedEventType")!= null?rs.getString("feedEventType"):"");
			rowMap.put("weightInKgs", rs.getString("weightInKgs")!= null?rs.getString("weightInKgs"):"");
			rowMap.put("feedCost", rs.getString("feedCost")!= null?rs.getString("feedCost"):"");
			rowMap.put("siloId", rs.getString("siloId")!= null?rs.getString("siloId"):"");
			rowMap.put("remarks", rs.getString("remarks")!= null?rs.getString("remarks"):"");
			return rowMap;
		}
	}
	
	
	
	/**
	 * Extract data from ProductionLog
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getProductionLogData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new ProductionLogMapper());
		
		return logList;
	} 
	
	private static final class ProductionLogMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {		
						
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("roomId", rs.getString("roomId")!= null?rs.getString("roomId"):"");
			rowMap.put("observationDate", rs.getDate("observationDate"));
			rowMap.put("groupId", rs.getString("groupId")!= null?rs.getString("groupId"):"");
			rowMap.put("logEventType", rs.getString("logEventType")!= null?rs.getString("logEventType"):"");
			rowMap.put("observation", rs.getString("observation")!= null?rs.getString("observation"):"");
			return rowMap;
		}
	}
	
	
	/**
	 * Extract data from GroupEVent
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getGroupEventData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new GroupEventMapper());
		
		return logList;
	} 
	
	private static final class GroupEventMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {		
						
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("groupId", rs.getString("groupId")!= null?rs.getString("groupId"):"");
			rowMap.put("roomId", rs.getString("roomId")!= null?rs.getString("roomId"):"");
			rowMap.put("groupStartDateTime", rs.getDate("groupStartDateTime"));
			rowMap.put("groupCloseDateTime", rs.getDate("groupCloseDateTime"));
			rowMap.put("phaseOfProductionType", rs.getString("phaseOfProductionType")!= null?rs.getString("phaseOfProductionType"):"");
			return rowMap;
		}
	}
	
	
	/**
	 * Extract data from PigletData
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getPigletEventData(String query) {
		
		final String qry = query;
		
		List<Map<String, Object>> logList = jdbcTemplate.query(qry,  new PigletEventMapper());
		
		return logList;
	} 
	
	private static final class PigletEventMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {		
						
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("pigId", rs.getString("pigId")!= null?rs.getString("pigId"):"");
			rowMap.put("litterId", rs.getString("litterId")!= null?rs.getString("litterId"):"");
			rowMap.put("tattooId", rs.getString("tattooId")!= null?rs.getString("tattooId"):"");
			rowMap.put("weightAtBirth", rs.getString("weightAtBirth")!= null?rs.getString("weightAtBirth"):"");
			rowMap.put("weightAtWeaning", rs.getString("weightAtWeaning")!= null?rs.getString("weightAtWeaning"):"");
			rowMap.put("weight1", rs.getString("weight1")!= null?rs.getString("weight1"):"");
			rowMap.put("weight2", rs.getString("weight2")!= null?rs.getString("weight2"):"");
			rowMap.put("weight3", rs.getString("weight3")!= null?rs.getString("weight3"):"");
			rowMap.put("weight4", rs.getString("weight4")!= null?rs.getString("weight4"):"");
			rowMap.put("weight5", rs.getString("weight5")!= null?rs.getString("weight5"):"");
			rowMap.put("weight6", rs.getString("weight6")!= null?rs.getString("weight6"):"");			
			rowMap.put("weight7", rs.getString("weight7")!= null?rs.getString("weight7"):"");			
			rowMap.put("weight8", rs.getString("weight8")!= null?rs.getString("weight8"):"");			
			rowMap.put("date1", rs.getDate("date1"));
			rowMap.put("date2", rs.getDate("date2"));
			rowMap.put("date3", rs.getDate("date3"));
			rowMap.put("date4", rs.getDate("date4"));
			rowMap.put("date5", rs.getDate("date5"));
			rowMap.put("date6", rs.getDate("date6"));
			rowMap.put("date7", rs.getDate("date7"));
			rowMap.put("date8", rs.getDate("date8"));
			return rowMap;
		}
	}
	
}
