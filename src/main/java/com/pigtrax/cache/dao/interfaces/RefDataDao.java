package com.pigtrax.cache.dao.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pigtrax.cache.dto.RefDataTranslationDto;

public interface RefDataDao {

	List<RefDataTranslationDto> getRoleTypeData();

	List<RefDataTranslationDto> getSexData();

	List<RefDataTranslationDto> getPhaseTypeData();

	List<RefDataTranslationDto> getVentilationTypeData();

	List<RefDataTranslationDto> getBreedingServiceTypeData();

	List<RefDataTranslationDto> getPregnancyEventTypeData();

	List<RefDataTranslationDto> getPregnancyExamResultTypeData();

	List<RefDataTranslationDto> getSiloTypeData();

	List<RefDataTranslationDto> getPigletStatusEventType();

	Integer getFieldCodeForId(Integer id, String referenceDataTable);

	List<RefDataTranslationDto> getPhaseOfProductionType();

	public List<RefDataTranslationDto> getFeedEventType();

	List<Map<String, String>> getCountryData();

	List<Map<String, List<Map<String, String>>>> getCityCountryData();

	List<RefDataTranslationDto> transportTrailerType();
	
	List<RefDataTranslationDto> removalEventType();
	
	List<RefDataTranslationDto> getGfunctionType();
	
	List<RefDataTranslationDto> getMortalityReasonTypes();
	
	List<RefDataTranslationDto> getTargetTypes();

	Map<Integer,String> getRationType();
	
	List<RefDataTranslationDto> getGcompanyType();
	
	List<RefDataTranslationDto> getGlineTypes();

	List<RefDataTranslationDto> getLogEventTypes();
	
	List<RefDataTranslationDto> getPigletConditions();
	
	List<RefDataTranslationDto> getSaleTypes();
	
	List<RefDataTranslationDto> getSaleReasons();
	
	List<RefDataTranslationDto> getPremiseTypes();
	
	List<RefDataTranslationDto> getBarnLocations();
	
	List<RefDataTranslationDto> getWaterTypes();
	
	List<RefDataTranslationDto> getBarnPositions();
	
	List<RefDataTranslationDto> getFeederTypes();
	
	List<RefDataTranslationDto> getFunctionTypes();
	
	List<RefDataTranslationDto> getJobFunctionRole();
	
	List<RefDataTranslationDto> getTrailerFunctions();
	
	List<RefDataTranslationDto> getMarketTypes();
	
	List<RefDataTranslationDto> getRationTypes();
	
	List<RefDataTranslationDto> getEventTypes();
	
}
