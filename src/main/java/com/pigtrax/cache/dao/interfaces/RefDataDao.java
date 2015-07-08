package com.pigtrax.cache.dao.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pigtrax.dto.RefDataTranslationDto;

public interface RefDataDao {

	List<RefDataTranslationDto> getRoleTypeData();

	List<RefDataTranslationDto> getSexData();

	Map<String, Set<String>> getCountryCityData();

}
