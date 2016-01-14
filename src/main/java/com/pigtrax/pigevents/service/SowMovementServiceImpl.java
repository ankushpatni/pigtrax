package com.pigtrax.pigevents.service;

import java.sql.SQLException;
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
import com.pigtrax.pigevents.beans.SowMovement;
import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.dao.interfaces.SowMovementDao;
import com.pigtrax.pigevents.service.interfaces.SowMovementService;

@Repository
public class SowMovementServiceImpl implements SowMovementService{
	
private static final Logger logger = Logger.getLogger(FeedEventServiceImpl.class);
	
	@Autowired 
	SowMovementDao sowMovementDao;
	
	@Override
	@Transactional("ptxJTransactionManager")
	public int addSowMovement(SowMovement sowMovement) throws PigTraxException
	{
		int returnValue = 0;
		try
		{
			returnValue = sowMovementDao.addSowMovement(sowMovement);
			
		} 
		catch (SQLException sqlEx)
		{
			sqlEx.printStackTrace();
			if ("23505".equals(sqlEx.getSQLState()))
			{
				throw new PigTraxException("Sow Movement",
						sqlEx.getSQLState(), true);
			} 
			else
			{
				throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());
			}
		}
		catch (DuplicateKeyException sqlExDup)
		{
			sqlExDup.printStackTrace();
			throw new PigTraxException("Sow Movement",
						null, true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnValue;
	}
	
	@Override
	@Transactional("ptxJTransactionManager")
	public int updateSowMovement(SowMovement sowMovement) throws PigTraxException
	{
		int returnValue = 0;
		try
		{
			returnValue = sowMovementDao.updateSowMovement(sowMovement);
			
		} 
		catch (SQLException sqlEx)
		{
			sqlEx.printStackTrace();
			if ("23505".equals(sqlEx.getSQLState()))
			{
				throw new PigTraxException("Sow Movement",
						sqlEx.getSQLState(), true);
			} 
			else
			{
				throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());
			}
		}
		catch (DuplicateKeyException sqlExDup)
		{
			sqlExDup.printStackTrace();
			throw new PigTraxException("Sow Movement",
						null, true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * To delete the given information
	 */

	@Override
	@Transactional("ptxJTransactionManager")
	public int deleteSawMoment(Integer id) throws Exception {
		if(id != null)
		{
			sowMovementDao.deleteSowMovement(id);
		}
		return 0;
		
	}
	
	/**
	 * Get app 
	 */
	@Override
	@Transactional("ptxJTransactionManager")
	public List<SowMovement> getSowMomwntsListByCompany(int companyId) throws Exception {
		
		return  sowMovementDao.getSowMovementListByCompanyId(companyId);
	}

	/**
	 * Get app 
	 */
	@Override
	@Transactional("ptxJTransactionManager")
	public List<SowMovement> getSowMovementListByPigInfoId(String pigInfoId, int companyId, int premisesId) throws Exception {
		
		return  sowMovementDao.getSowMovementListByPigInfoId(pigInfoId, companyId, premisesId);
	}

}
