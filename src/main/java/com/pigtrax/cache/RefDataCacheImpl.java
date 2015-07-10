package com.pigtrax.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pigtrax.cache.dao.interfaces.RefDataDao;
import com.pigtrax.cache.dto.RefDataTranslationDto;

public class RefDataCacheImpl implements RefDataCache{
	
	RefDataDao refDataDao;
	
	/*
	 * Most of the maps has following structure
	 * <fieldLanguage, <fieldCode, fieldLable>>
	 * 
	 * <en, <Male, 1>>
	 * <en, <Female, 2>>
	 * <pr, <Male_pr, 3>>
	 * 
	 * so on so forth
	 * 
	 */
	
	private Map<String, Map<Integer, String>> roleTypeMap;
	
	private Map<String, Map<Integer, String>> sexTypeMap;
	
	private Map<String, Map<Integer, String>> phaseTypeMap;

	private Map<String, Map<Integer, String>> ventilationTypeMap;

	
	/*
	 * This map is simpler
	 * <Country, <[List of cities in this country]>>
	 * 
	 * <Spain, <[Madris, Barcelona, Alicante]>
	 */
	private Map<String, Set<String>> cityCountryMap;
	
	public void populateCaches(){
		roleTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getRoleTypeData()));
		sexTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getSexData()));
		phaseTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getPhaseTypeData()));
		ventilationTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getVentilationTypeData()));
		cityCountryMap = Collections.unmodifiableMap(refDataDao.getCountryCityData());
	}

	@Override
	public Map<Integer, String> getRoleTypeMap(String language){
		return roleTypeMap.get(language);
	}

	@Override
	public Map<Integer, String> getSexTypeMap(String language){
		return sexTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getPhaseTypeMap(String language){
		return phaseTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getVentilationTypeMap(String language){
		return ventilationTypeMap.get(language);
	}
	
	@Override
	public Set<String> getCitiesForCountry(String city){
		return cityCountryMap.get(city);
	}

	@Override
	public Set<String> getAllCountries(){
		return cityCountryMap.keySet();
	}
	
	private Map<String, Map<Integer, String>> convertToMap(List<RefDataTranslationDto> rolesList) {
		Map<String, Map<Integer, String>> tmpMap = new HashMap<String, Map<Integer, String>>();
		for(RefDataTranslationDto refDto : rolesList){
			Map<Integer, String> innerMap = tmpMap.get(refDto.getFieldLanguage());
			if(innerMap == null){
				innerMap = new HashMap<Integer, String>();
				tmpMap.put(refDto.getFieldLanguage(), innerMap);
			}
			innerMap.put(refDto.getFieldCode(), refDto.getFieldValue());
		}
		return tmpMap;
	}

	public void setRefDataDao(RefDataDao refDataDao) {
		this.refDataDao = refDataDao;
	}
	
}
