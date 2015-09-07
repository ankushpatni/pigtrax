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

	Map<String, String> getCountryData();

	Map<String, Map<String, String>> getCityCountryData();

}
