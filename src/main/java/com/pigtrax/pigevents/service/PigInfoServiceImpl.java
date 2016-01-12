package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.text.ParseException;
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
import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.SowMovement;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.SowMovementDao;
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
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired 
	SowMovementDao sowMovementDao;
	
	
	public int savePigInformation(PigInfoDto dto) throws Exception {
		
		logger.info("adding Pig info  : "+dto.toString());
		
		PigInfo pigInfo = builder.convertToBean(dto);
		logger.info("company id in service = "+pigInfo.getCompanyId());
		
			try{
				int returnValue;
				if(dto.getId() == null)
				{
					
				   returnValue =  addPigInformation(pigInfo);
				   
				   SowMovement sowMovement = new SowMovement();
				   sowMovement.setPigInfoId(returnValue);
				   sowMovement.setPremiseId(pigInfo.getPremiseId());
				   sowMovement.setRoomId(pigInfo.getRoomId());
				   sowMovement.setUserUpdated(pigInfo.getUserUpdated());
				   sowMovementDao.addSowMovement(sowMovement);
				}
				else
				{
					PigInfo pigInfoExist = pigInfoDao.getPigInformationById(pigInfo.getId());
					if(!(pigInfoExist.getPremiseId() == pigInfo.getPremiseId() && 
							pigInfoExist.getRoomId() == pigInfo.getRoomId()))
					{
							SowMovement sowMovement = new SowMovement();
						   sowMovement.setPigInfoId(pigInfo.getId());
						   sowMovement.setPremiseId(pigInfo.getPremiseId());
						   sowMovement.setRoomId(pigInfo.getRoomId());
						   sowMovement.setUserUpdated(pigInfo.getUserUpdated());
						   sowMovementDao.addSowMovement(sowMovement);
					}
					returnValue = pigInfoDao.updatePigInformation(pigInfo);
				}
				
				return returnValue;
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
				info =  pigInfoDao.getPigInformationByPigId(dto.getSearchText(), dto.getCompanyId(), dto.getSelectedPremise());
		}
		else 
		{
			info =  pigInfoDao.getPigInformationByTattoo(dto.getSearchText(), dto.getCompanyId(), dto.getSelectedPremise());
		}
		return builder.convertToDto(info);
	}
	   
	/**
	 * To delete the given information
	 */

	public int deletePigInfo(Integer id) throws Exception {
		if(id != null)
		{
			PigInfo pigInfo = pigInfoDao.getPigInformationById(id);
			if(pigInfo != null)
			{
				List<BreedingEvent> breedingEvents = breedingEventDao.getBreedingEventInformationByPigId(pigInfo.getPigId(), pigInfo.getCompanyId());
				if(breedingEvents != null && 0 < breedingEvents.size())
				{
					return -1;
				}
				else
				{
					pigInfoDao.deletePigInfo(id);
					return 1;
				}
			}
			
		}
		return 0;
		
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
	public Map<Integer,Object> getPigInformationByCompany(int companyId) throws Exception {
		
		List<PigInfo> pigInfoList =  pigInfoDao.getPigInformationByCompanyId(companyId);
		Map<Integer,Object> pigInfoMap = new LinkedHashMap<Integer,Object>();
					
		if(null != pigInfoList && pigInfoList.size()>0)
		{
			for(PigInfo pigInfo : pigInfoList)
			{
				pigInfoMap.put(pigInfo.getId(),pigInfo);
			}
		}		
		
		return pigInfoMap;
	}
  
	@Override
	public int changePigId(Integer pigInfoId, String newPigId) throws PigTraxException {  
		try{
			return pigInfoDao.changePigId(pigInfoId, newPigId);
		}
		 catch(DuplicateKeyException Ex)
		{
			  throw new PigTraxException("Duplicate Key Exception occured. Please check Pig Id/ Tattoo", "", true);
		}
	}
	
	@Override
	public PigInfoDto getInactivePigInformation(PigInfoDto dto)
			throws Exception {

		PigInfo info = null;
		if(dto != null && dto.getSearchOption().equals("pigId"))
		{
				info =  pigInfoDao.getInactivePigInformationByPigId(dto.getSearchText(), dto.getCompanyId(), dto.getPremiseId());
		}
		else 
		{
			info =  pigInfoDao.getInactivePigInformationByTattoo(dto.getSearchText(), dto.getCompanyId(), dto.getPremiseId()); 
		}
		return builder.convertToDto(info);
	}
	
	@Override
	public List<String> getAvailablePigIds(Integer companyId) {
		return pigInfoDao.getAvailablePigIds(companyId); 
	}
	
	@Override
	public PigInfoDto getPigInformationById(final Integer pigInfoId) throws PigTraxException, ParseException {
		PigInfo info = null;
		if(pigInfoId != null)
		{ 
			try {
				info = pigInfoDao.getPigInformationById(pigInfoId);
			} catch (SQLException e) {
				throw new PigTraxException(e.getMessage());
			} 
		}
		return builder.convertToDto(info);
	}
	
	/**
	 * Get app 
	 */
	@Override
	@Transactional("ptxJTransactionManager")
	public Integer updatePigInfoRecordForSowMovement(PigInfoDto pigInfoDto) throws Exception {
		
		return  pigInfoDao.updatePigInformation(builder.convertToBean(pigInfoDto));
	}
	
}
