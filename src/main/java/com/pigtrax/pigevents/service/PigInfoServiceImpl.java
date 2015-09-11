package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dto.Silo;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dto.PigInfoBuilder;
import com.pigtrax.pigevents.dto.PigInfoDto;
import com.pigtrax.pigevents.service.interfaces.PigInfoService;

@Repository
public class PigInfoServiceImpl implements PigInfoService {
	private static final Logger logger = Logger.getLogger(PigInfoServiceImpl.class);
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	PigInfoBuilder builder;
	
	
	public int savePigInformation(PigInfoDto dto) throws Exception {
		
		logger.info("adding Pig info  : "+dto.toString());
		
		PigInfo pigInfo = builder.convertToBean(dto);
		logger.info("company id in service = "+pigInfo.getCompanyId());
		
			try{
				if(dto.getId() == null)
				{
				   return addPigInformation(pigInfo);
				}
				else
				{
					return pigInfoDao.updatePigInformation(pigInfo);
				}
			}catch(SQLException sqlEx)
			{
				
				if("23505".equals(sqlEx.getSQLState()))
				{
					throw new PigTraxException("PigId already exists", sqlEx.getSQLState(), true);
				}
				else
					throw new PigTraxException("SqlException occured", sqlEx.getSQLState());
			}
			catch(DuplicateKeyException sqlEx)
			{
				  logger.info("DuplicateKeyException : "+sqlEx.getRootCause()+"/"+sqlEx.getCause());
					throw new PigTraxException("Duplicate Key Exception occured. Please check Pig Id/ Tattoo", "", true);
			}
		
		
	}
	
	@Transactional("ptxJTransactionManager")
	private int addPigInformation(PigInfo pigInfo) throws SQLException
	{
		int id_PigInfo = pigInfoDao.addPigInformation(pigInfo);
		PigTraxEventMaster master = new PigTraxEventMaster();
		master.setPigInfoId(id_PigInfo);
		master.setUserUpdated(pigInfo.getUserUpdated());
		master.setEventTime(pigInfo.getEntryDate());
		eventMasterDao.insertEntryEventDetails(master);
		return id_PigInfo;		
	}
	
	
	/**
	 * Get Pig Information based on pigId/tattoo
	 */
	public PigInfoDto getPigInformation(PigInfoDto dto) throws Exception {
		
		PigInfo info = null;
		if(dto != null && dto.getSearchOption().equals("pigId"))
		{
				info =  pigInfoDao.getPigInformationByPigId(dto.getSearchText(), dto.getCompanyId());
		}
		else 
		{
			info =  pigInfoDao.getPigInformationByTattoo(dto.getSearchText(), dto.getCompanyId());
		}
		return builder.convertToDto(info);
	}
	   
	/**
	 * To delete the given information
	 */

	public void deletePigInfo(Integer id) throws Exception {
		if(id != null)
		{
			pigInfoDao.deletePigInfo(id);
		}
		
	}
	
	public List<PigInfoDto> getAllFosterPigs(PigInfoDto dto) throws Exception{ 
		
		List<PigInfoDto> pigInfoDtoList = new ArrayList<PigInfoDto>();

		if(dto != null && dto.getSearchOption().equals("pigId"))
		{
			List<PigInfo> pigInfoList =  pigInfoDao.getAllFosterPigs(dto);
			pigInfoDtoList =  builder.convertToDtos(pigInfoList);
		}
		
		return pigInfoDtoList;
	}
	
	/**
	 * Get Pig Information based on pigId/tattoo
	 */
	public Map<Integer,String> getPigInformationByCompany(int companyId) throws Exception {
		
		List<PigInfo> pigInfoList =  pigInfoDao.getPigInformationByCompanyId(companyId);
		Map<Integer,String> pigInfoMap = new LinkedHashMap<Integer,String>();
					
		if(null != pigInfoList && pigInfoList.size()>0)
		{
			for(PigInfo pigInfo : pigInfoList)
			{
				pigInfoMap.put(pigInfo.getId(),pigInfo.getPigId());
			}
		}		
		
		return pigInfoMap;
	}
	
	
}
