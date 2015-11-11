package com.pigtrax.cache.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pigtrax.cache.dao.interfaces.RefDataDao;
import com.pigtrax.cache.dto.RefDataTranslationDto;

@Repository
public class RefDataDaoImpl implements RefDataDao {
 
	JdbcTemplate jdbcTemplate;
 
	@Override
	public List<RefDataTranslationDto> getRoleTypeData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_RoleType\" FROM pigtraxrefdata.\"RoleTypeTranslation\" order by \"fieldLanguage\", \"id_RoleType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}

	@Override
	public List<RefDataTranslationDto> getSexData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_SexType\" FROM pigtraxrefdata.\"SexTypeTranslation\" order by \"fieldLanguage\", \"id_SexType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getPhaseTypeData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PhaseType\" FROM pigtraxrefdata.\"PhaseTypeTranslation\" order by \"fieldLanguage\", \"id_PhaseType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getVentilationTypeData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_VentilationType\" FROM pigtraxrefdata.\"VentilationTypeTranslation\" order by \"fieldLanguage\", \"id_VentilationType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getBreedingServiceTypeData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_BreedingServiceType\" FROM pigtraxrefdata.\"BreedingServiceTypeTranslation\" order by \"fieldLanguage\", \"id_BreedingServiceType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getPregnancyEventTypeData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PregnancyEventType\" FROM pigtraxrefdata.\"PregnancyEventTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getPregnancyExamResultTypeData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PregnancyExamResultType\" FROM pigtraxrefdata.\"PregnancyExamResultTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getSiloTypeData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_SiloType\" FROM pigtraxrefdata.\"SiloTypeTranslation\" order by \"fieldLanguage\", \"id_SiloType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getPhaseOfProductionType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PhaseOfProductionType\" FROM pigtraxrefdata.\"PhaseOfProductionTypeTranslation\" order by \"fieldLanguage\", \"id_PhaseOfProductionType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}


	@Override
	public List<RefDataTranslationDto> getPigletStatusEventType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PigletStatusEventType\" FROM pigtraxrefdata.\"PigletStatusEventTypeTranslation\" order by \"fieldLanguage\", \"id_PigletStatusEventType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getFeedEventType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_FeedEventType\" FROM pigtraxrefdata.\"FeedEventTypeTranslation\" order by \"fieldLanguage\", \"id_FeedEventType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> transportTrailerType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_TrailerType\" FROM pigtraxrefdata.\"TrailerTypeTranslation\" order by \"fieldLanguage\", \"id_TrailerType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> removalEventType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_RemovalType\" FROM pigtraxrefdata.\"RemovalEventTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getGfunctionType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_GfunctionType\" FROM pigtraxrefdata.\"GfunctionTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getMortalityReasonTypes() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_MortalityReasonType\" FROM pigtraxrefdata.\"MortalityReasonTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	
	@Override
	public List<RefDataTranslationDto> getTargetTypes() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_TargetType\" FROM pigtraxrefdata.\"TargetTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getGcompanyType() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_GcompanyType\" FROM pigtraxrefdata.\"GcompanyTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	
	@Override
	public List<RefDataTranslationDto> getGlineTypes() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_GlineType\" FROM pigtraxrefdata.\"GlineTypeTranslation\" order by \"fieldLanguage\", \"fieldValue\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<Map<String, String>> getCountryData() {
		String query = "select distinct country.name from pigtraxrefdata.\"Country\" country;";
		return jdbcTemplate.query(query, new ResultSetExtractor<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Map<String, String>> resultMapList = new LinkedList<Map<String, String>>();
				Map<String, String> resultMap = new HashMap<String,String>();
				while (rs.next()) {
					String country = rs.getString(1);
					resultMap = new HashMap<String,String>();
					resultMap.put("name",  country);
					resultMap.put("value",  country);
					resultMapList.add(resultMap);
				}
				return resultMapList;
			}
		});
	}

	@Override
	public List<Map<String, List<Map<String, String>>>> getCityCountryData() {
		String query = "select city.name, country.name from pigtraxrefdata.\"Country\" country, pigtraxrefdata.\"City\" city where city.\"id_Country\"=country.id order by city.\"id_Country\", city.name;";
		return jdbcTemplate.query(query, new ResultSetExtractor<List<Map<String, List<Map<String, String>>>>>() {
			@Override
			public List<Map<String, List<Map<String, String>>>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
				List<Map<String, String>> resultMapList = new LinkedList<Map<String, String>>();
				Map<String, String> cityMap = new HashMap<String, String>();
				List<Map<String, List<Map<String, String>>>> finalList = new LinkedList<Map<String, List<Map<String, String>>>>();
				
				while (rs.next()) {
					String city = rs.getString(1);
					String country = rs.getString(2);

					List<Map<String, String>> cityMapList = resultMap.get(country);
					if (cityMapList == null) {
						cityMapList = new LinkedList<Map<String, String>>();						
					}
					
					cityMap = new HashMap<String, String>();
					cityMapList.add(cityMap);
					resultMap.put(country, cityMapList);
					cityMap.put("name", city);
					cityMap.put("value", city);
				}
				finalList.add(resultMap);
				return finalList;
			}
		});
	}

	private final static class CacheRefDataRowMaper implements RowMapper<RefDataTranslationDto> {
		@Override
		public RefDataTranslationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			String fieldValue = rs.getString(1);
			String fieldLanguage = rs.getString(2);
			Integer fieldCode = rs.getInt(3);
			return new RefDataTranslationDto(fieldLanguage, fieldValue, fieldCode);
		}
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * Get the fieldCode for a given Id
	 * @param id
	 * @param referenceDataTable
	 * @return
	 */
	public Integer getFieldCodeForId(final Integer id, String referenceDataTable)
	{
		String query = "SELECT \"fieldCode\" FROM pigtraxrefdata.\""+referenceDataTable+"\" where \"id\" = ?";		
		
		List<Integer> fieldCodes = jdbcTemplate.query(query, new PreparedStatementSetter(){
 			@Override
 			public void setValues(PreparedStatement ps) throws SQLException {
 				ps.setInt(1, id);
 			}}, new FieldMapper());	
		
		if(fieldCodes != null && fieldCodes.size() > 0)
			return fieldCodes.get(0);
		return null;
	}
	
	private static final class FieldMapper implements RowMapper<Integer>
	{

		@Override
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			return rs.getInt(1);
		}
		
	}
	
	public Map<Integer,String> getRationType()
	{
		String query = "select \"id\", \"rationValue\" from pigtrax.\"MasterRation\" order by \"rationValue\" ";
		return jdbcTemplate.query(query, new ResultSetExtractor<Map<Integer, String>>() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> rationTypeMap = new HashMap<Integer, String>();
				
				while (rs.next()) {
					rationTypeMap.put(rs.getInt(1), rs.getString(2));
				}
					
				return rationTypeMap;
			}
		});
	}
	
}