package com.pigtrax.pigevents.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dto.PigInfoBuilder;
import com.pigtrax.pigevents.dto.PigInfoDto;
import com.pigtrax.pigevents.service.interfaces.PigInfoService;

@Repository
public class PigInfoServiceImpl implements PigInfoService {
	private static final Logger logger = Logger.getLogger(PigInfoServiceImpl.class);
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	PigInfoBuilder builder;
	
	public int savePigInformation(PigInfoDto dto) throws Exception {
		PigInfo pigInfo = builder.convertToBean(dto);
		logger.info("company id in service = "+pigInfo.getCompanyId());
		
			try{
				if(dto.getId() == null)
				{
				return pigInfoDao.addPigInformation(pigInfo);
				}
				else
				{
					return pigInfoDao.updatePigInformation(pigInfo);
				}
			}catch(SQLException sqlEx)
			{
				if("23505".equals(sqlEx.getSQLState()))
				{
					throw new PigTraxException("PigId already exists", sqlEx.getSQLState());
				}
				else
					throw new PigTraxException("SqlException occured", sqlEx.getSQLState());
			}
			catch(DuplicateKeyException sqlEx)
			{
				  logger.info("DuplicateKeyException : "+sqlEx.getRootCause()+"/"+sqlEx.getCause());
					throw new PigTraxException("Duplicate Key Exception occured. Please check Pig Id/ Tattoo", "");
			}
		
		
	}
	
	/**
	 * Get Pig Information based on pigId/tattoo
	 */
	public PigInfoDto getPigInformation(PigInfoDto dto) throws Exception {
		
		PigInfo info = null;
		if(dto != null && dto.getSearchOption().equals("pigId"))
		{
				info =  pigInfoDao.getPigInformationByPigId(dto.getSearchText());
		}
		else
		{
			info =  pigInfoDao.getPigInformationByTattoo(dto.getSearchText());
		}
		return builder.convertToDto(info);
	}
}
