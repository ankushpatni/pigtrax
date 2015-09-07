package com.pigtrax.cache.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PregnancyEventType\" FROM pigtraxrefdata.\"PregnancyEventTypeTranslation\" order by \"fieldLanguage\", \"id_PregnancyEventType\"; ";
		return jdbcTemplate.query(query, new CacheRefDataRowMaper());
	}
	
	@Override
	public List<RefDataTranslationDto> getPregnancyExamResultTypeData() {
		String query = "SELECT \"fieldValue\", \"fieldLanguage\", \"id_PregnancyExamResultType\" FROM pigtraxrefdata.\"PregnancyExamResultTypeTranslation\" order by \"fieldLanguage\", \"id_PregnancyExamResultType\"; ";
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
	public Map<String, String> getCountryData() {
		String query = "select distinct country.name from pigtraxrefdata.\"Country\" country;";
		return jdbcTemplate.query(query, new ResultSetExtractor<Map<String, String>>() {
			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> resultMap = new HashMap<String, String>();
				while (rs.next()) {
					String country = rs.getString(1);
					resultMap.put(country,  country);
				}
				return resultMap;
			}
		});
	}

	@Override
	public Map<String, Map<String, String>> getCityCountryData() {
		String query = "select city.name, country.name from pigtraxrefdata.\"Country\" country, pigtraxrefdata.\"City\" city where city.\"id_Country\"=country.id order by city.\"id_Country\", city.name;";
		return jdbcTemplate.query(query, new ResultSetExtractor<Map<String, Map<String, String>>>() {
			@Override
			public Map<String, Map<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();
				while (rs.next()) {
					String city = rs.getString(1);
					String country = rs.getString(2);

					Map<String, String> cityMap = resultMap.get(country);
					if (cityMap == null) {
						cityMap = new HashMap<String, String>();
						resultMap.put(country, cityMap);
					}
					cityMap.put(city, city.substring(0, 2));
				}
				return resultMap;
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
	
}
