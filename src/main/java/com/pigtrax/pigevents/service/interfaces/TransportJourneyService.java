package com.pigtrax.pigevents.service.interfaces;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.TransportJourney;

public interface TransportJourneyService {

	TransportJourney getTransportJourneyById(final int id) throws PigTraxException;

	public int addTransportJourney(TransportJourney transportJourney)
			throws PigTraxException;

	public int updateTransportJourney(TransportJourney transportJourney)
			throws PigTraxException;

}
