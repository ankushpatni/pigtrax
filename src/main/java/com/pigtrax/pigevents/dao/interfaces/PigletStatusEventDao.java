package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.PigletStatusEvent;
import com.pigtrax.pigevents.dto.PigletStatusEventDto;

public interface PigletStatusEventDao {
	
	int addPigletStatusEvent(PigletStatusEvent pigletStatusEvent) throws SQLException, DuplicateKeyException;
	
	PigletStatusEvent getPigletStatusEventInformation(Integer pigletStatusEventId);
	
	List<PigletStatusEvent> getPigletStatusEvents(String searchText, String searchOption, Integer companyId);
	
	void deletePigletStatusEvent(Integer id) throws SQLException;
	
	List<PigletStatusEvent> getFosterInRecords(String pigId, Integer companyId, Integer farrowEventId);
	
	List<PigletStatusEvent> getPigletStatusEventsByFarrowEventId(Integer farrowEventId);
	
	void deletePigletStatusEventsByFarrowId(Integer pigInfoId, Integer farrowEventId) throws SQLException;
	
	PigletStatusEvent getFosterInRecord(Integer farrowEventId) throws SQLException;
	
}
