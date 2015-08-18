package com.pigtrax.pigevents.service.interfaces;

import java.sql.SQLException;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.dto.GroupEventDto;

public interface GroupEventDetailsService {

	GroupEventDetails groupEventDetailsListByGroupId(final String groupId);
	
	GroupEventDetails groupEventDetailsListById(final Integer id) ;
	   
	int updateGroupEventDetails(final GroupEventDetails groupEventDetails) throws SQLException;
	   
	int addGroupEventDetails(final GroupEventDto groupEventDto) throws PigTraxException;
	 
	void deleteGroupEventDetailsByGroupId(final Integer id) throws SQLException;
}
