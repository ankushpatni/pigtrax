package com.pigtrax.pigevents.dao.interfaces;

import java.sql.SQLException;

import com.pigtrax.pigevents.beans.TransportJourney;

public interface TransportJourneyDao {
	
	TransportJourney getTransportJourneyById(final int id) throws SQLException;	
	
	public int addTransportJourney(TransportJourney transportJourney)
			throws SQLException;
	
	public int updateTransportJourney(TransportJourney transportJourney)
			throws SQLException;

}
