package com.pigtrax.cache.dao.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pigtrax.cache.dto.RefDataTranslationDto;


public interface RefDataDao {

	List<RefDataTranslationDto> getRoleTypeData();

	List<RefDataTranslationDto> getSexData();

	Map<String, Set<String>> getCountryCityData();

	List<RefDataTranslationDto> getPhaseTypeData();

	List<RefDataTranslationDto> getVentilationTypeData();
	
	List<RefDataTranslationDto> getBreedingServiceTypeData();
	
	List<RefDataTranslationDto> getPregnancyEventTypeData();
	
	List<RefDataTranslationDto> getPregnancyExamResultTypeData();
	
	List<RefDataTranslationDto> getSiloTypeData();
	
<<<<<<< HEAD
	List<RefDataTranslationDto> getPigletStatusEventType();
	
	Integer getFieldCodeForId(Integer id, String referenceDataTable);
	
	
=======
	List<RefDataTranslationDto> getPhaseOfProductionType() ;

>>>>>>> branch 'master' of vidyar@s18367010.onlinehome-server.info:/opt/git/pigtrax.git
}
