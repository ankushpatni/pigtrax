package com.pigtrax.master.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.beans.Origin;
import com.pigtrax.master.dao.interfaces.OriginDao;
import com.pigtrax.master.dto.OriginDto;
import com.pigtrax.master.service.interfaces.OriginService;

@Service
public class OriginServiceImpl implements OriginService {
	@Autowired
	OriginDao originDao;
	
  @Override
	public List<OriginDto> getOriginList() {
		return originDao.getOriginList();
	}
	  
  @Override
	public void saveOrigin(OriginDto originDto) throws PigTraxException {
	  if(originDto != null)
	  {
		if(!originDao.checkIfExists(originDto.getName()))
		{
			Origin origin = new Origin();
			origin.setName(originDto.getName());
			origin.setUserUpdated(originDto.getUserUpdated());;
			originDao.saveOrigin(origin);
		}
		else
		{
			throw new PigTraxException("Duplicate Record ","",true );
		}
	  }
	}
  
  @Override
	public void deleteOrigin(Integer originId) {
		originDao.deleteOrigin(originId);	
	}
}
