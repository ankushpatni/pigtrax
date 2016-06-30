package com.pigtrax.master.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigtrax.master.beans.MasterRation;
import com.pigtrax.master.dao.interfaces.MasterRationDao;
import com.pigtrax.master.dto.MasterRationDto;
import com.pigtrax.master.service.interfaces.MasterRationService;

@Service
public class MasterRationServiceImpl implements MasterRationService {
	@Autowired
	MasterRationDao rationDao;
	
  
	  
  @Override
	public void saveRation(MasterRationDto rationDto) {
	  if(rationDto != null)
	  {
		  if(rationDto.getId() != null && rationDto.getId() > 0 )
		  {
			MasterRation ration = new MasterRation();
			ration.setId(rationDto.getId());
			ration.setRationValue(rationDto.getRationValue());
			ration.setFeedTypeId(rationDto.getFeedTypeId());;
			ration.setUserUpdated(rationDto.getUserUpdated());
			ration.setRationDescription(rationDto.getRationDescription());
			ration.setRationTypeId(rationDto.getRationTypeId());
			rationDao.updateRation(ration);
		  }
		  else
		  {
			MasterRation ration = new MasterRation();
			ration.setRationValue(rationDto.getRationValue());
			ration.setFeedTypeId(rationDto.getFeedTypeId());;
			ration.setUserUpdated(rationDto.getUserUpdated());
			ration.setRationDescription(rationDto.getRationDescription());
			ration.setRationTypeId(rationDto.getRationTypeId());
			rationDao.saveRation(ration);
		  }
	  }
	}
  
  @Override
	public void deleteRation(Integer rationId) {
	  rationDao.deleteRation(rationId);	
	}
  
  @Override
public List<MasterRationDto> getRationList(String language) {
	  return rationDao.getRationList(language);
}
  
  @Override
	public Map<Integer, String> getRationListAsMap(String language) {
		List<MasterRationDto> rationDtoList = getRationList(language);
		Map<Integer, String> rationMap = new LinkedHashMap<Integer, String>();
		if(rationDtoList != null && 0 <rationDtoList.size())
		{
			for(MasterRationDto dto : rationDtoList)
			{
				rationMap.put(dto.getId(), dto.getRationValue());
			}
		}
		return rationMap;
	}
}
