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
	
	void deletePigletStatusEvent(Integer id) throws SQLException;
	
	List<PigletStatusEvent> getFosterInRecords(String pigId, Integer companyId, Integer farrowEventId);
	
	List<PigletStatusEvent> getPigletStatusEventsByFarrowEventId(Integer farrowEventId);
	
	List<PigletStatusEvent> getPigletStatusEventsByFarrowEventId(Integer farrowEventId, Integer pigletStatusEventTypeId);
	
	
	void deletePigletStatusEventsByFarrowId(Integer pigInfoId, Integer farrowEventId) throws SQLException;
	
	PigletStatusEvent getFosterInRecord(Integer farrowEventId) throws SQLException;

	Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRange(
			Date start, Date end, Integer companyId);

	Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(
			Date start, Date end, Integer companyId);

	Integer getTotalPigsWeavend(Date start, Date end, Integer companyId);

	Integer getPigletStatusEventsFerrowIdWeavnAndFosterInAndOut(Date start,
			Date end, Integer companyId);

	Integer getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithWeight(
			Date start, Date end, Integer companyId);

	Integer getTotalPigsWeavendWithWeight(Date start, Date end,
			Integer companyId);

	Integer getTotalPigsWeight(Date start, Date end, Integer companyId);

	Integer getPigletStatusEventsFerrowIdForWeavnAndDateRange(Date start,
			Date end, Integer companyId);

	Integer getCountPifIngoIdFromFarrowWithParityOneInPigInfo(Date start,
			Date end, Integer companyId);

	Integer getCountParityOfPigIngoIdFromFarrow(Date start, Date end,
			Integer companyId);

	Integer getSumOfDiffOfFerrowAndBreedingDate(Date start, Date end,
			Integer companyId);

	Integer getPiGIdFromFerrow(Date start, Date end, Integer companyId);

	Integer getPiGIdFromBreeding(Date start, Date end, Integer companyId);

	Integer getCountOfFirstService(Date start, Date end, Integer companyId);

	Integer getCountOfRepeateService(Date start, Date end, Integer companyId);

	Integer getCountOfServiceWithMatingGreaterThanOne(Date start, Date end,
			Integer companyId);

	Integer getCountOfMating(Date start, Date end, Integer companyId);

	Integer getCountOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding(
			Date start, Date end, Integer companyId);

	Integer getPigletStatusEventsPigIdCountForWeavnAndDateRange(
			Date start, Date end, Integer companyId);

	Integer getNumberOfDaysBetweenWeanAndServiceDate(Date start, Date end,
			Integer companyId);

	Integer getPairtyOfServedFemals(Date start, Date end, Integer companyId);

	Integer getCountPifIngoIdWithParityOneInPigInfo(Date start, Date end,
			Integer companyId);

	Integer getSumOfDateDiffBetweenServiceAndEntryDate(Date start, Date end,
			Integer companyId);
	
	void deletePigletStatusEventsByFarrowId(final Integer pigInfoId, final Integer farrowEventId, final Integer pigletStatusEventType)
			throws SQLException;
	
}
