package com.pigtrax.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RefDataCache {

	Map<Integer, String> getRoleTypeMap(String language);

	Map<Integer, String> getSexTypeMap(String language);

	List<Map<String, List<Map<String, String>>>> getCitiesForCountry(String city);

	List<Map<String, String>> getAllCountries();

	Map<Integer, String> getPhaseTypeMap(String language);

	Map<Integer, String> getVentilationTypeMap(String language);
	
	Map<Integer, String> getBreedingServiceTypeMap(String language);	
	
	Map<Integer, String> getPregnancyEventTypeMap(String language);
	
	Map<Integer, String> getPregnancyExamResultTypeMap(String language);
	
	Map<Integer, String> getSiloTypeMap(String language);
	Map<Integer, String> getPigletStatusEventType(String language);
	Map<Integer, String> getPhaseOfProductionTypeMap(String language);
	
	Map<Integer, String> getFeedEventTypeMap(String language);

	Map<Integer, String> transportTrailerType(String language);
	
	Map<Integer, String> getRemovalEventTypeMap(String language) ;
	
	Map<Integer, String> getGfunctionTypeMap(String language);
	
	Map<Integer, String> getMortalityReasonTypeMap(String language);
	
	Map<Integer, String> getTargetTypeMap(String language);

}
