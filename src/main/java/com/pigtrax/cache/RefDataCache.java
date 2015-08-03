package com.pigtrax.cache;

import java.util.Map;
import java.util.Set;

public interface RefDataCache {

	Map<Integer, String> getRoleTypeMap(String language);

	Map<Integer, String> getSexTypeMap(String language);

	Set<String> getCitiesForCountry(String city);

	Set<String> getAllCountries();

	Map<Integer, String> getPhaseTypeMap(String language);

	Map<Integer, String> getVentilationTypeMap(String language);
	
	Map<Integer, String> getBreedingServiceTypeMap(String language);	
	
	Map<Integer, String> getPregnancyEventTypeMap(String language);
	
	Map<Integer, String> getPregnancyExamResultTypeMap(String language);
	
	Map<Integer, String> getSiloTypeMap(String language);

}
