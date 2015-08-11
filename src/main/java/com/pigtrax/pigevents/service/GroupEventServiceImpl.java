package com.pigtrax.pigevents.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;

@Repository
public class GroupEventServiceImpl implements GroupEventService{
	
private static final Logger logger = Logger.getLogger(GroupEventServiceImpl.class);
	
	@Autowired 
	GroupEventDao groupEventDao;
	
	@Autowired
	RefDataCache refDataCache;

	@Override
	public GroupEvent getGroupEventByGroupId(String groupId) {
		return groupEventDao.getGroupEventByGroupId(groupId);
	}

	@Override
	public GroupEvent getGroupEventById(Integer id) {
		return groupEventDao.getGroupEventById(id);
	}

	@Override
	public int updateGroupEventDetails(GroupEvent groupEvent)
			throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addGroupEventDetails(GroupEvent groupEvent) throws SQLException {
		return groupEventDao.addGroupEventDetails(groupEvent);
	}

	@Override
	public void deleteGroupEventByGroupId(Integer id) throws SQLException {
		groupEventDao.deleteGroupEventByGroupId(id);
		
	}

}
