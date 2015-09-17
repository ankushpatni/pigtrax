package com.pigtrax.pigevents.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.TransportJourney;
import com.pigtrax.pigevents.dao.interfaces.TransportJourneyDao;
import com.pigtrax.pigevents.service.interfaces.TransportJourneyService;

@Repository
public class TransportJourneyServiceImpl implements TransportJourneyService{

	@Autowired 
	TransportJourneyDao transportJourneyDao;
	
	@Override
	public TransportJourney getTransportJourneyById(int id) throws PigTraxException {
		try {
			return transportJourneyDao.getTransportJourneyById(id);
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

	@Override
	public int addTransportJourney(TransportJourney transportJourney)
			throws PigTraxException {
		try {
			return transportJourneyDao.addTransportJourney(transportJourney);
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

	@Override
	public int updateTransportJourney(TransportJourney transportJourney)
			throws PigTraxException {
		try {
			return transportJourneyDao.updateTransportJourney(transportJourney);
		} 
		catch (SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
	}

}
