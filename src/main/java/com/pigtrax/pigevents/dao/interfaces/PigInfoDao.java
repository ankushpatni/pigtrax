package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.dto.PigInfoDto;

public interface PigInfoDao {
   public int addPigInformation(PigInfo pigInfo) throws SQLException, DuplicateKeyException;
   
   public int updatePigInformation(final PigInfo pigInfo) throws SQLException, DuplicateKeyException ;
   
   public PigInfo getPigInformationByPigId(String pigId, Integer companyId) throws SQLException;
   
   public PigInfo getPigInformationByPigId(String pigId, Integer companyId, Integer premiseId) throws SQLException;
   
   public PigInfo getPigInformationByTattoo(String tattoo, Integer companyId) throws SQLException;
   
   public PigInfo getPigInformationByTattoo(String tattoo, Integer companyId, Integer premiseId) throws SQLException;
   
   public void deletePigInfo(Integer id) throws SQLException;
   
   PigInfo getPigInformationById(final Integer pigInfoId) throws SQLException;
   
   List<PigInfo> getAllFosterPigs(PigInfoDto pigInfo) throws SQLException;
   
   public List<PigInfo> getPigInformationByCompanyId( final Integer companyId) throws SQLException;
   
   public int updatePigInfoStatus(final Integer id, final Boolean pigStatus);
   
   int increaseParity(Integer pigInfoId, Integer gestationLength); 
   
   int decreaseParity(Integer pigInfoId);
   
   int changePigId(Integer pigInfoId, String newPigId);
   
   PigInfo getInactivePigInformationByPigId(String pigId, Integer companyId, Integer premiseId) throws SQLException;
   
   PigInfo getInactivePigInformationByPigId(String pigId, Integer companyId) throws SQLException;
   
   PigInfo getInactivePigInformationByTattoo(String tattoo, Integer companyId, Integer premiseId) throws SQLException;
   
   List<String> getAvailablePigIds(Integer companyId);

PigInfo getPigInformationByPigIdWithOutStatus(String pigId, Integer companyId,
		Integer premiseId) throws SQLException;
		
		public List<PigInfo> getActivePigInformationList(final Integer companyId, final Integer premiseId) throws SQLException ;
}
