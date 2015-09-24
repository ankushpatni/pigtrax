package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.dto.GroupEventDto;

public interface GroupEventDetailsService {

	List<GroupEventDetails> groupEventDetailsListByGroupId(final int groupId);
	
	GroupEventDto groupEventDetailsListById(final Integer id) throws PigTraxException;
	   
	int updateGroupEventDetails(final GroupEventDto groupEventDto) throws PigTraxException;
	   
	int addGroupEventDetails(final GroupEventDto groupEventDto) throws PigTraxException;
	 
	void deleteGroupEventDetailsByGroupId(final Integer id) throws SQLException;
}
