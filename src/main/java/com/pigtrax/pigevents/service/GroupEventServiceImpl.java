package com.pigtrax.pigevents.service;


import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.util.UserUtil;

@Repository
public class GroupEventServiceImpl implements GroupEventService{
	
	private static final Logger logger = Logger.getLogger(PregnancyEventServiceImpl.class);
		
	@Autowired 
	GroupEventDao groupEventDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired 
	PigTraxEventMasterDao eventMasterDao;
	

	@Override
	public GroupEvent getGroupEventByGroupId(String groupId)
			throws SQLException {		
		return groupEventDao.getGroupEventByGroupId(groupId);
	}

	@Override
	@Transactional("ptxJTransactionManager")
	public int addGroupEvent(GroupEvent groupEvent) throws PigTraxException {
		try {
			System.out.println("ankush-------1111------>");
			int generatedId = groupEventDao.addGroupEvent(groupEvent);
			System.out.println("ankush------------->"+ generatedId);
			/*PigTraxEventMaster master = new PigTraxEventMaster();
			master.setUserUpdated(UserUtil.getLoggedInUser());
			master.setEventTime(groupEvent.getGroupStartDateTime());
			master.setGroupEventId(generatedId);
			eventMasterDao.insertEntryEventDetails(master);*/
			
			return generatedId;
		} 
		catch (SQLException sqlEx) {
			if ("23505".equals(sqlEx.getSQLState()))
			{
				throw new PigTraxException("GroupId already exists",
						sqlEx.getSQLState(), true);
			} 
			else
			{
				throw new PigTraxException("SqlException occured",
						sqlEx.getSQLState());
			}
		} 
		catch (DuplicateKeyException sqlEx)
		{
			logger.info("DuplicateKeyException : " + sqlEx.getRootCause() + "/"
					+ sqlEx.getCause());
			throw new PigTraxException(
					"Group Id already Exist. Please check Group Id",
					"", true);
		}
	}

	@Override
	public int updateGroupEvent(GroupEvent groupEvent) throws SQLException {
		
		return groupEventDao.updateGroupEvent(groupEvent);
	}

	@Override
	public int updateGroupEventStatus(String groupId, Boolean groupStatus)
			throws SQLException {
		return groupEventDao.updateGroupEventStatus(groupId, groupStatus);
	}

	
}
