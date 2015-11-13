package com.pigtrax.master.service;

import java.util.List;

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
			MasterRation ration = new MasterRation();
			ration.setRationValue(rationDto.getRationValue());
			ration.setFeedTypeId(rationDto.getFeedTypeId());;
			ration.setUserUpdated(rationDto.getUserUpdated());
			rationDao.saveRation(ration);
		
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
}
