package com.pigtrax.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pigtrax.cache.dao.interfaces.RefDataDao;
import com.pigtrax.cache.dto.RefDataTranslationDto;

@Repository
public class RefDataCacheImpl implements RefDataCache {

	RefDataDao refDataDao;

	/*
	 * Most of the maps has following structure <fieldLanguage, <fieldCode,
	 * fieldLable>>
	 * 
	 * <en, <Male, 1>> <en, <Female, 2>> <pr, <Male_pr, 3>>
	 * 
	 * so on so forth
	 * 
	 */

	private Map<String, Map<Integer, String>> roleTypeMap;

	private Map<String, Map<Integer, String>> sexTypeMap;

	private Map<String, Map<Integer, String>> phaseTypeMap;

	private Map<String, Map<Integer, String>> ventilationTypeMap;

	private Map<String, Map<Integer, String>> breedingServiceTypeMap;

	private Map<String, Map<Integer, String>> pregnancyEventTypeMap;

	private Map<String, Map<Integer, String>> pregnancyExamResultTypeMap;

	private Map<String, Map<Integer, String>> siloTypeMap;

	private Map<String, Map<Integer, String>> pigletStatusEventTypeMap;
	
	private Map<String, Map<Integer, String>> phaseOfProductionTypeMap;

	private Map<String, Map<Integer, String>> feedEventTypeMap;
	
	private Map<String, Map<Integer, String>> transportTrailerTypeMap;
	
	private Map<String, Map<Integer, String>> removalEventTypeMap;
	
	private Map<String, Map<Integer, String>> gfunctionTypeMap;
	
	private Map<String, Map<Integer, String>> mortalityReasonTypeMap;
	
	private Map<String, Map<Integer, String>> targetTypeMap;
	
	private Map<Integer, String> rationTypeMap;
		
	private Map<String, Map<Integer, String>> gcompanyTypeMap;
	
	private Map<String, Map<Integer, String>> glineTypeMap;
	
	private Map<String, Map<Integer, String>> logEventTypeMap;
	
	private Map<String, Map<Integer, String>> pigletConditionMap;
	
	private Map<String, Map<Integer, String>> saleReasonsMap;
	
	private Map<String, Map<Integer, String>> saleTypesMap;
	
	private Map<String, Map<Integer, String>> premiseTypeMap;
	
	private Map<String, Map<Integer, String>> barnLocationMap;
	
	private Map<String, Map<Integer, String>> waterTypeMap;
	
	private Map<String, Map<Integer, String>> barnPositionMap;
	
	private Map<String, Map<Integer, String>> feederTypeMap;
	
	private Map<String, Map<Integer, String>> functionTypeMap;
	
	private Map<String, Map<Integer, String>> jobFunctionRoleMap;
	
	private Map<String, Map<Integer, String>> trailerFunctionMap;
	
	/*
	 * This map is simpler <Country, <[List of cities in this country]>>
	 * 
	 * <Spain, <[Madris, Barcelona, Alicante]>
	 */
	private List<Map<String, List<Map<String, String>>>> cityCountryMap;

	private List<Map<String, String>> countryMapList;

	public void populateCaches() {
		roleTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getRoleTypeData()));
		sexTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getSexData()));
		phaseTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getPhaseTypeData()));
		ventilationTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getVentilationTypeData()));
		countryMapList = refDataDao.getCountryData();
		cityCountryMap = refDataDao.getCityCountryData();
		breedingServiceTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getBreedingServiceTypeData()));
		pregnancyEventTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getPregnancyEventTypeData()));
		pregnancyExamResultTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getPregnancyExamResultTypeData()));
		siloTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getSiloTypeData()));
		pigletStatusEventTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getPigletStatusEventType()));
		phaseOfProductionTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getPhaseOfProductionType()));
		feedEventTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getFeedEventType()));
		transportTrailerTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.transportTrailerType()));
		removalEventTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.removalEventType()));
		gfunctionTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getGfunctionType())); 
		mortalityReasonTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getMortalityReasonTypes()));
		targetTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getTargetTypes()));
		rationTypeMap = Collections.unmodifiableMap(refDataDao.getRationType());
		gcompanyTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getGcompanyType()));
		glineTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getGlineTypes()));
		logEventTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getLogEventTypes()));
		pigletConditionMap = Collections.unmodifiableMap(convertToMap(refDataDao.getPigletConditions()));
		saleTypesMap = Collections.unmodifiableMap(convertToMap(refDataDao.getSaleTypes()));
		saleReasonsMap = Collections.unmodifiableMap(convertToMap(refDataDao.getSaleReasons()));
		premiseTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getPremiseTypes()));		
		barnLocationMap = Collections.unmodifiableMap(convertToMap(refDataDao.getBarnLocations()));
		waterTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getWaterTypes()));
		barnPositionMap = Collections.unmodifiableMap(convertToMap(refDataDao.getBarnPositions()));
		feederTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getFeederTypes()));
		functionTypeMap = Collections.unmodifiableMap(convertToMap(refDataDao.getFunctionTypes()));
		jobFunctionRoleMap = Collections.unmodifiableMap(convertToMap(refDataDao.getJobFunctionRole()));
		trailerFunctionMap = Collections.unmodifiableMap(convertToMap(refDataDao.getTrailerFunctions()));
	}

	@Override
	public Map<Integer, String> getRoleTypeMap(String language) {
		return roleTypeMap.get(language);
	}

	@Override
	public Map<Integer, String> getSexTypeMap(String language) {
		System.out.println("sex type map = " + sexTypeMap);
		return sexTypeMap.get(language);
	}

	@Override
	public Map<Integer, String> getPhaseTypeMap(String language) {
		return phaseTypeMap.get(language);
	}

	@Override
	public Map<Integer, String> getVentilationTypeMap(String language) {
		return ventilationTypeMap.get(language);
	}

	@Override
	public Map<Integer, String> getBreedingServiceTypeMap(String language) {
		System.out.println("breedingServiceTypeMap in Ref Data Cache imple : " + breedingServiceTypeMap.toString());
		return breedingServiceTypeMap.get(language);
	}

	public Map<Integer, String> getPregnancyEventTypeMap(String language) {
		return pregnancyEventTypeMap.get(language);
	}

	public Map<Integer, String> getPregnancyExamResultTypeMap(String language) {
		return pregnancyExamResultTypeMap.get(language);
	}

	@Override
	public List<Map<String, List<Map<String, String>>>> getCitiesForCountry(String city) {
		return cityCountryMap;
	}

	@Override
	public List<Map<String, String>> getAllCountries() {
		return countryMapList;
	}

	@Override
	public Map<Integer, String> getSiloTypeMap(String language) {
		return siloTypeMap.get(language);
	}

	@Override
	public Map<Integer, String> getPhaseOfProductionTypeMap(String language) {
		return phaseOfProductionTypeMap.get(language);
	}

	@Override
	public Map<Integer, String> getFeedEventTypeMap(String language) {
		return feedEventTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getRemovalEventTypeMap(String language) {
		return removalEventTypeMap.get(language);
	}
	
	private Map<String, Map<Integer, String>> convertToMap(List<RefDataTranslationDto> rolesList) {
		Map<String, Map<Integer, String>> tmpMap = new HashMap<String, Map<Integer, String>>();
		for (RefDataTranslationDto refDto : rolesList) {
			Map<Integer, String> innerMap = tmpMap.get(refDto.getFieldLanguage());
			if (innerMap == null) {
				innerMap = new LinkedHashMap<Integer, String>();
				tmpMap.put(refDto.getFieldLanguage(), innerMap);
			}
			innerMap.put(refDto.getFieldCode(), refDto.getFieldValue());
		}
		System.out.println("tmp Map from Ref Data Cache Impl : " + tmpMap.toString());

		return tmpMap; 
	}

	public void setRefDataDao(RefDataDao refDataDao) {
		this.refDataDao = refDataDao;
	}

	@Override 
	public Map<Integer, String> getPigletStatusEventType(String language) {
		return pigletStatusEventTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> transportTrailerType(String language) {
		return transportTrailerTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getGfunctionTypeMap(String language) {
		return gfunctionTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getMortalityReasonTypeMap(String language) {
		return mortalityReasonTypeMap.get(language);
	}

	@Override
	public Map<Integer, String> getTargetTypeMap(String language) {
		return targetTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getRationTypeMap() {
		return rationTypeMap;
	}
	
	@Override
	public Map<Integer, String> getGcompanyTypeMap(String language) {
		return gcompanyTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getGlineTypeMap(String language) {
		return glineTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getLogEventTypeMap(String language) {
		return logEventTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getPigletConditionMap(String language) {
		return pigletConditionMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getSaleReasonsMap(String language) {
		return saleReasonsMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getSaleTypesMap(String language) {
		return saleTypesMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getPremiseTypeMap(String language) {
		// TODO Auto-generated method stub
		return premiseTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getBarnLocationMap(String language) {
		return barnLocationMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getBarnPositionMap(String language) {
		return barnPositionMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getFeederTypeMap(String language) {
		return feederTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getWaterTypeMap(String language) {
		return waterTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getFunctionTypeMap(String language) {
		return functionTypeMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getJobFunctionRoleMap(String language) {
		return jobFunctionRoleMap.get(language);
	}
	
	@Override
	public Map<Integer, String> getTrailerFunctionMap(String language) {
		return trailerFunctionMap.get(language);
	}
	
}