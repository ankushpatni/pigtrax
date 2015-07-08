package com.pigtrax.cache.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.pigtrax.cache.dao.interfaces.RefDataDao;
import com.pigtrax.dto.RefDataTranslationDto;

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
	public Map<String, Set<String>> getCountryCityData() {
		String query = "select city.name, country.name from pigtraxrefdata.\"Country\" country, pigtraxrefdata.\"City\" city where city.\"id_Country\"=country.id order by city.\"id_Country\", city.name;";
		return jdbcTemplate.query(query, new ResultSetExtractor<Map<String, Set<String>>>() {
			@Override
			public Map<String, Set<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Set<String>> resultMap = new HashMap<String, Set<String>>();
				while (rs.next()) {
					String city = rs.getString(1);
					String country = rs.getString(2);

					Set<String> cityList = resultMap.get(country);
					if (cityList == null) {
						cityList = new HashSet<String>();
						resultMap.put(country, cityList);
					}
					cityList.add(city);
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
}
