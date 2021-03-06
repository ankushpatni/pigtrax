package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.RemovalEvent;

public interface RemovalEventDao {
	
	public RemovalEvent getRemovalEventById(final int id)
			throws SQLException ;
	
	RemovalEvent getRemovalEventByRemovalId(final String removalId) throws SQLException;
	
	RemovalEvent getRemovalEventByGroupId(final String groupId) throws SQLException;
	
	int addRemovalEvent(final RemovalEvent removalEvent) throws SQLException;
	
	int updateRemovalEvent(final RemovalEvent removalEvent) throws SQLException;
	
	public List<RemovalEvent> getRemovalEventByPigId(final int pigId)
			throws SQLException;
	
	public List<RemovalEvent> getRemovalEventByGroupId(final int groupId)
			throws SQLException;

	public List<RemovalEvent> getRemovalEventByGroupIdForSale(Integer id);

	public List<RemovalEvent> getRemovalEventByPigIdFoelsale(Integer id);

}
