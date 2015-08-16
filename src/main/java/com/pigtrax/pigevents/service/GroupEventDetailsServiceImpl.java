package com.pigtrax.pigevents.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.service.interfaces.GroupEventDetailsService;

@Repository
public class GroupEventDetailsServiceImpl implements GroupEventDetailsService{
	
private static final Logger logger = Logger.getLogger(GroupEventDetailsServiceImpl.class);
	
	@Autowired 
	GroupEventDetailsDao groupEventDetailsDao;
	
	@Autowired
	RefDataCache refDataCache;

	@Override
	public GroupEventDetails groupEventDetailsListByGroupId(String groupId) {
		return groupEventDetailsDao.groupEventDetailsListByGroupId(groupId);
	}

	@Override
	public GroupEventDetails groupEventDetailsListById(Integer id) {
		return groupEventDetailsDao.groupEventDetailsListById(id);
	}

	@Override
	public int updateGroupEventDetails(GroupEventDetails groupEventDetails)
			throws SQLException {
		return groupEventDetailsDao.updateGroupEventDetails(groupEventDetails);
	}

	@Override
	public int addGroupEventDetails(GroupEventDetails groupEventDetails) throws SQLException {
		return groupEventDetailsDao.addGroupEventDetails(groupEventDetails);
	}

	@Override
	public void deleteGroupEventDetailsByGroupId(Integer id) throws SQLException {
		groupEventDetailsDao.deleteGroupEventDetailsByGroupId(id);
		
	}

}
