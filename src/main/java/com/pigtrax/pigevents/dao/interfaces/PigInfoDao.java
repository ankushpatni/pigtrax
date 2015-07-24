package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.PigInfo;

public interface PigInfoDao {
   public int addPigInformation(PigInfo pigInfo) throws SQLException, DuplicateKeyException;
   
   public int updatePigInformation(final PigInfo pigInfo) throws SQLException, DuplicateKeyException ;
   
   public PigInfo getPigInformationByPigId(String pigId, Integer companyId) throws SQLException;
   
   public PigInfo getPigInformationByTattoo(String tattoo, Integer companyId) throws SQLException;
   
   public void deletePigInfo(Integer id) throws SQLException;
   
   PigInfo getPigInformationById(final Integer pigInfoId) throws SQLException;
}
