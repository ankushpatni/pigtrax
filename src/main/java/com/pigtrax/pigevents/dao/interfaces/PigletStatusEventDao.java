package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.PigletStatusEvent;

public interface PigletStatusEventDao {
	
	int addPigletStatusEvent(PigletStatusEvent pigletStatusEvent) throws SQLException, DuplicateKeyException;
	
	int updatePigletStatusEvent(PigletStatusEvent pigletStatusEvent) throws SQLException, DuplicateKeyException;
	
	PigletStatusEvent getPigletStatusEventInformation(Integer pigletStatusEventId);
	
	List<PigletStatusEvent> getPigletStatusEvents(String searchText, String searchOption, Integer companyId);
	
	void deletePigletStatusEvent(Integer id) throws SQLException;
}
