package com.pigtrax.pigevents.dao.interfaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PregnancyEvent;

public interface PigTraxEventMasterDao {
	/**
	 * Insert the entry event details of a pig info
	 * @param master
	 * @return
	 * @throws SQLException
	 */
	int insertEntryEventDetails(PigTraxEventMaster master) throws SQLException;
	
	
	/**
	 * Insert the entry event details of a pig info
	 * @param master
	 * @return
	 * @throws SQLException
	 */
	int updatePigletStatusEventMasterDetails(PigTraxEventMaster master) throws SQLException;
	
	
	/**
	 * update the breeding event details of a given pigInfoId
	 * @param breedingEvent
	 * @return
	 * @throws SQLException
	 */
	int updateBreedingEventDetails(BreedingEvent breedingEvent) throws SQLException;
	
	/**
	 * To fetch the PigTraxEventMaster record for a given pigInfo Key
	 * @param pigInfoKey
	 * @return
	 * @throws Exception
	 */
	List<PigTraxEventMaster>  getEventMasterRecords(final Integer pigInfoKey) throws Exception;
	
	/**
	 * Delete the piglet status event entries for a given farrow event id
	 * @param farrowEventId
	 * @return
	 * @throws SQLException
	 */
	int deletePigletStatusEvents(Integer farrowEventId) throws SQLException;
	
	/**
	 * Delete farrow event entries based on farrow event id
	 * @param farrowEventId
	 * @throws SQLException
	 */
	 void deleteFarrowEvent(final Integer farrowEventId) throws SQLException;
	 
 	/**
	 * Delete pregnancy event entries based on pregnancy event id
	 * @param farrowEventId
	 * @throws SQLException
	 */
	 void deletePregnancyEvent(final Integer pregnancyEventId) throws SQLException;
	 
	 /**
	 * Delete breeding event entries based on breeding event id
	 * @param farrowEventId
	 * @throws SQLException
	 */
	 void deleteBreedingEvent(final Integer breedingEventId) throws SQLException;

	 /**
		 * Select ferrow event entries date
		 * @param farrowEventId
		 * @throws SQLException
		 */
	List<Integer> selectFerrowEvents(Date startDate, Date endDate)
			throws SQLException;
	
}
