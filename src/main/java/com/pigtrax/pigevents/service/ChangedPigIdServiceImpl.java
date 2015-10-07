package com.pigtrax.pigevents.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.ChangedPigId;
import com.pigtrax.pigevents.dao.interfaces.ChangedPigIdDao;
import com.pigtrax.pigevents.dto.PigInfoDto;
import com.pigtrax.pigevents.service.interfaces.ChangePigIdService;

@Repository
public class ChangedPigIdServiceImpl implements ChangePigIdService {

	@Autowired
	ChangedPigIdDao changedPigIdDao;
	
	 @Override
	public int storeChangedPigId(PigInfoDto pigInfoDto) throws PigTraxException {
		 try{
			if(pigInfoDto != null)
			{
				ChangedPigId changedPigIdObj = new ChangedPigId();
				changedPigIdObj.setPigInfoId(pigInfoDto.getId());
				changedPigIdObj.setOldSowId(pigInfoDto.getPigId());
				changedPigIdObj.setChangedSowId(pigInfoDto.getNewPigId());
				changedPigIdObj.setChangeDateTime(pigInfoDto.getChangePigIdDate());
				changedPigIdObj.setUserUpdated(pigInfoDto.getUserUpdated());
				changedPigIdObj.setCompanyId(pigInfoDto.getCompanyId());					
				return changedPigIdDao.storeChangedPigId(changedPigIdObj);				
			}
		 }catch(SQLException e)
			{
				throw new PigTraxException(e.getMessage());
			}
		 catch(DuplicateKeyException Ex)
			{
				  throw new PigTraxException("Duplicate Key Exception occured. Please check Pig Id/ Tattoo", "", true);
			} 
		return 0;
	}
}
