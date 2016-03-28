package com.pigtrax.pigevents.dao.interfaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.pigtrax.pigevents.beans.PigletStatusEvent;
import com.pigtrax.pigevents.dto.PigletStatusEventDto;

public interface PigletStatusEventDao {
	
	int addPigletStatusEvent(PigletStatusEvent pigletStatusEvent) throws SQLException, DuplicateKeyException;
	
	PigletStatusEvent getPigletStatusEventInformation(Integer pigletStatusEventId);
	
	List<PigletStatusEvent> getPigletStatusEvents(String searchText, String searchOption, Integer companyId);
	
	List<PigletStatusEvent> getPigletStatusEvents(String searchText, String searchOption, Integer companyId, Integer premiseId);
	
	void deletePigletStatusEvent(Integer id) throws SQLException;
	
	List<PigletStatusEvent> getFosterInRecords(String pigId, Integer companyId, Integer farrowEventId);
	
	List<PigletStatusEvent> getPigletStatusEventsByFarrowEventId(Integer farrowEventId);
	
	List<PigletStatusEvent> getPigletStatusEventsByFarrowEventId(Integer farrowEventId, Integer pigletStatusEventTypeId);
	
	
	void deletePigletStatusEventsByFarrowId(Integer pigInfoId, Integer farrowEventId) throws SQLException;
	
	PigletStatusEvent getFosterInRecord(Integer farrowEventId) throws SQLException;

	Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRange(
			Date start, Date end, Integer companyId,Integer premisesId);

	Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(
			Date start, Date end, Integer companyId,Integer premisesId);

	Integer getTotalPigsWeavend(Date start, Date end, Integer companyId,Integer premisesId);

	Integer getPigletStatusEventsFerrowIdWeavnAndFosterInAndOut(Date start,
			Date end, Integer companyId,Integer premisesId);

	Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithWeight(
			Date start, Date end, Integer companyId,Integer premisesId);

	Integer getTotalPigsWeavendWithWeight(Date start, Date end,
			Integer companyId,Integer premisesId);

	Integer getTotalPigsWeight(Date start, Date end, Integer companyId,Integer premisesId);

	Integer getPigletStatusEventsFerrowIdForWeavnAndDateRange(Date start,
			Date end, Integer companyId,Integer premisesId);

	Integer getCountPifIngoIdFromFarrowWithParityOneInPigInfo(Date start,
			Date end, Integer companyId,Integer premisesId);

	Integer getCountParityOfPigIngoIdFromFarrow(Date start, Date end,
			Integer companyId,Integer premisesId);

	Integer getSumOfDiffOfFerrowAndBreedingDate(Date start, Date end,
			Integer companyId,Integer premisesId);

	Integer getPiGIdFromFerrow(Date start, Date end, Integer companyId,Integer premisesId);

	Integer getPiGIdFromBreeding(Date start, Date end, Integer companyId,Integer premisesId);

	Integer getCountOfFirstService(Date start, Date end, Integer companyId,Integer premisesId);

	Integer getCountOfRepeateService(Date start, Date end, Integer companyId,Integer premisesId);

	Integer getCountOfServiceWithMatingGreaterThanOne(Date start, Date end,
			Integer companyId,Integer premisesId);

	Integer getCountOfMating(Date start, Date end, Integer companyId,Integer premisesId);

	Integer getCountOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding(
			Date start, Date end, Integer companyId,Integer premisesId);

	Integer getPigletStatusEventsPigIdCountForWeavnAndDateRange(
			Date start, Date end, Integer companyId,Integer premisesId);

	Integer getNumberOfDaysBetweenWeanAndServiceDate(Date start, Date end,
			Integer companyId,Integer premisesId);

	Integer getPairtyOfServedFemals(Date start, Date end, Integer companyId,Integer premisesId);

	Integer getCountPifIngoIdWithParityOneInPigInfo(Date start, Date end,
			Integer companyId,Integer premisesId);

	Integer getSumOfDateDiffBetweenServiceAndEntryDate(Date start, Date end,
			Integer companyId,Integer premisesId);
	
	void deletePigletStatusEventsByFarrowId(final Integer pigInfoId, final Integer farrowEventId, final Integer pigletStatusEventType)
			throws SQLException;

	Integer getTotalWeekBornPiglet(Date start, Date end, Integer companyId,Integer premisesId);

	Integer getLittersWithWeightOfLiveBorn(Date startDate, Date endDate,
			Integer companyId,Integer premisesId) ;

	int getConceptionRateAtPresumedPregnant(Date startDate, Date endDate,
			Integer companyId, Integer difference,Integer premisesId);

	Integer getSumOfDiffOfFerrowAndWeanDate(Date start, Date end,
			Integer companyId,Integer premisesId);

	Integer getTotalFemaleDeathsandDestroyed(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getGiltDeaths(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getSowDeaths(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getTotalFemalesDestroyed(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getBoarEntered(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getBoarCulled(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getBoarDeathsandDestroyed(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getAveParityofMortality(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getSowCulled(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getGiltsCulled(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getAveParityofCulls(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getTotalFemalesCulled(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getEndFemaleInventory(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getEndLactationInventory(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getEndGestationInventory(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getEndBoarInventory(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getAveParityofEndInventory(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getFemaleEntered(Date start, Date end, Integer companyId, Integer premisesId);

	Integer getTotalAbortions(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getAbortionsNatural(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getAbortionsInduced(Date startDate, Date endDate, Integer companyId, Integer premisesId);

	Integer getAveAbortionParity(Date startDate, Date endDate, Integer companyId, Integer premisesId);
	
}
