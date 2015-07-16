package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.PigInfo;

public interface PigInfoDao {
   public int addPigInformation(PigInfo pigInfo) throws SQLException, DuplicateKeyException;
   
   public int updatePigInformation(final PigInfo pigInfo) throws SQLException, DuplicateKeyException ;
   
   public PigInfo getPigInformationByPigId(String pigId) throws SQLException;
   
   public PigInfo getPigInformationByTattoo(String tattoo) throws SQLException;
}
