package com.pigtrax.cache;

import java.util.Map;
import java.util.Set;

public interface RefDataCache {

	Map<Integer, String> getRoleTypeMap(String language);

	Map<Integer, String> getSexTypeMap(String language);

	Set<String> getCitiesForCountry(String city);

	Set<String> getAllCountries();

}
