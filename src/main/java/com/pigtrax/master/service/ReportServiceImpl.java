package com.pigtrax.master.service;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.dao.interfaces.PenDao;
import com.pigtrax.master.dao.interfaces.RoomDao;
import com.pigtrax.master.service.interfaces.ReportService;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PigletStatusEventDao;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;

@Repository
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	PenDao penDao;
	
	@Autowired
	RoomDao roomDao;
	
	@Autowired
	PigletStatusEventDao pigletStatusEventDao;
	
	@Autowired
	RemovalEventExceptSalesDetailsDao removalEventExceptSalesDetailsDao;
	
	private static final String DEFAULT_FORMATTER = "dd/MM/yyyy";
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	Map<Date,Map> dateMapDate = new LinkedHashMap<Date,Map>();

	@Override
	public Map<Date,Map> getFerrowEventReport(String startDate,
			String endDate, Integer companyId, Integer premisesId) {
		
		Date startDateD;
		Date endDateD;
		try {
			startDateD = new Date( sdf.parse(startDate).getTime());
			endDateD = new Date( sdf.parse(endDate).getTime());
			dateMapDate = new LinkedHashMap<Date,Map>();
			monthsBetween(startDateD, endDateD, companyId, premisesId);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		} 	
		
		return dateMapDate;
	}
	
	public int monthsBetween(Date a, Date b,  Integer companyId, Integer premisesId) {
	    Calendar cal = Calendar.getInstance();
	    if (a.before(b)) {
	        cal.setTime(a);
	    } else {
	        cal.setTime(b);
	        b = a;
	    }
	    int c = 0;
	    while (cal.getTime().before(b)) 
	    {
	    	Date start = new Date(cal.getTime().getTime());
	    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	    	if(dayOfWeek-1 ==1)
	    	{
	    		cal.add(Calendar.DAY_OF_YEAR, 6);
	    	}
	    	else
	    	{
	    		cal.add(Calendar.DAY_OF_YEAR, 8-dayOfWeek);
	    	}
	    	// cal.add(Calendar.WEEK_OF_YEAR, 1);
	        Date end = new Date(cal.getTime().getTime());
	        
	        if(b.before(end))
	        {
	        	end = b;
	        }
	        cal.add(Calendar.DAY_OF_YEAR, 1);
	        
	        List<Integer> listFerrowId = getFerrowEventListId(start,end,companyId, premisesId);
	        
	      //  if(listFerrowId!=null && !listFerrowId.isEmpty())
	        {
	        	Integer totalFerrowEvents = new HashSet<Integer>(listFerrowId).size();
	        	List listValues = new LinkedList();
	        	 if(listFerrowId!=null && !listFerrowId.isEmpty())
	 	        {
				listValues = eventMasterDao
						.getFerrowReportParams(listFerrowId,companyId, premisesId);
	 	        }
	        	 else
	        	 {
	        		 listValues.add(0);
	        		 listValues.add(0);
	        		 listValues.add(0);
	        		 listValues.add(0);
	        		 listValues.add(0);
	        		 listValues.add(0f);
	        	 }
				
				int litterLess7 = eventMasterDao.getLitterForGivenrange(start,end,companyId, premisesId);
				listValues.add(litterLess7);//6
				listValues.add(getPigletStatusEventsFerrowIdCountForWeavnAndDateRange(start,end,companyId, premisesId));//7
				listValues.add(getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(start,end,companyId,premisesId));//8
				listValues.add(getTotalPigsWeavend(start,end,companyId,premisesId));//9
				listValues.add(getPigletStatusEventsFerrowIdWeavnAndFosterInAndOut(start,end,companyId,premisesId));//10
				listValues.add(getTotalPigsMortal(start,end,companyId,premisesId));//11
				listValues.add(getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithWeight(start,end,companyId,premisesId));//12
				listValues.add(getTotalPigsWeavendWithWeight(start,end,companyId,premisesId));//13
				listValues.add(getTotalPigsWeight(start,end,companyId,premisesId));//14
				listValues.add(getPigletStatusEventsFerrowIdForWeavnAndDateRange(start,end,companyId,premisesId));//15 error here
				listValues.add(getCountPifIngoIdFromFarrowWithParityOneInPigInfo(start,end,companyId,premisesId));//16
				listValues.add(getCountParityOfPigIngoIdFromFarrow(start,end,companyId,premisesId));//17
				listValues.add(getSumOfDiffOfFerrowAndBreedingDate(start,end,companyId,premisesId));//18
				listValues.add(getPiGIdFromFerrow(start,end,companyId,premisesId));//19
				listValues.add(getPiGIdFromBreeding(start,end,companyId,premisesId));//20
				listValues.add(getCountOfFirstService(start, end, companyId,premisesId));//21
				listValues.add(getCountOfRepeateService(start, end, companyId,premisesId));//22
				listValues.add(getCountOfServiceWithMatingGreaterThanOne(start, end, companyId,premisesId));//23
				listValues.add(getCountOfMating(start, end, companyId,premisesId));//24
				listValues.add(getCountOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding(start, end, companyId,premisesId));//25
				listValues.add(getPigletStatusEventsPigIdCountForWeavnAndDateRangeWithMoreThanTwalePig(start, end, companyId,premisesId));//26
				listValues.add(getNumberOfDaysBetweenWeanAndServiceDate(start, end, companyId,premisesId));//27
				listValues.add(getPairtyOfServedFemals(start, end, companyId,premisesId));//28
				listValues.add(getCountPifIngoIdWithParityOneInPigInfo(start, end, companyId,premisesId));//29
				listValues.add(getSumOfDateDiffBetweenServiceAndEntryDate(start, end, companyId,premisesId));//30
				listValues.add(pigletStatusEventDao.getTotalWeekBornPiglet(start, end, companyId, premisesId));//31
				listValues.add(pigletStatusEventDao.getLittersWithWeightOfLiveBorn(start, end, companyId, premisesId));//32
				listValues.add(pigletStatusEventDao.getConceptionRateAtPresumedPregnant(start, end, companyId,30, premisesId));//33
				listValues.add(pigletStatusEventDao.getConceptionRateAtPresumedPregnant(start, end, companyId,42, premisesId));//34
				listValues.add(pigletStatusEventDao.getSumOfDiffOfFerrowAndWeanDate(start, end, companyId, premisesId));//35
				
				
				listValues.add(pigletStatusEventDao.getEndFemaleInventory(start, end, companyId, premisesId));//36  End Female Inventory
				listValues.add(pigletStatusEventDao.getEndFemaleInventory(start, end, companyId, premisesId));//37  End Female Inventory
				listValues.add(pigletStatusEventDao.getEndLactationInventory(start, end, companyId, premisesId)); // 38     End Lactation Inventory
				listValues.add(pigletStatusEventDao.getEndGestationInventory(start, end, companyId, premisesId)); // 39 End Gestation Inventory
				
				try {
					if(premisesId !=null && premisesId !=0)
					{
						
						listValues.add(penDao.getCountOfPenByPremiseId(premisesId)); 		// 40     %Space Capacity
						listValues.add(roomDao.getCountOfSpacesBasedOnPremisesId(premisesId)) ; // 41     %Space Capacity
						
					}
					else
					{
						listValues.add(penDao.getTotalPenActive(companyId)); 		// 40     %Space Capacity
						listValues.add(roomDao.getCountOfSpacesBasedOnCompanyId(companyId)) ; // 41     %Space Capacity
					}
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				listValues.add(pigletStatusEventDao.getAveParityofEndInventory(start, end, companyId, premisesId)) ; // 42     Ave Parity of End Inventory
				listValues.add(pigletStatusEventDao.getFemaleEntered(start, end, companyId, premisesId)) ; // 43        Female Entered
				
				listValues.add(pigletStatusEventDao.getTotalFemalesCulled(start, end, companyId, premisesId)); //  44   Total Females Culled
				listValues.add(pigletStatusEventDao.getAveParityofCulls(start, end, companyId, premisesId)); //  45   Ave Parity of Culls
				listValues.add(pigletStatusEventDao.getSowCulled(start, end, companyId, premisesId)); // 46 Sows Culled
				listValues.add(pigletStatusEventDao.getGiltsCulled(start, end, companyId, premisesId));//  47   Gilts Culled
				
				listValues.add(pigletStatusEventDao.getTotalFemaleDeathsandDestroyed(start, end, companyId, premisesId));//  48  Total Female Deaths and Destroyed
				listValues.add(pigletStatusEventDao.getAveParityofMortality(start, end, companyId, premisesId)); //   49  Ave Parity of Mortality
				listValues.add(pigletStatusEventDao.getGiltDeaths(start, end, companyId, premisesId));//  50  Gilt Deaths
				listValues.add(pigletStatusEventDao.getSowDeaths(start, end, companyId, premisesId)); //  51  Sow Deaths
				listValues.add(pigletStatusEventDao.getTotalFemalesDestroyed(start, end, companyId, premisesId)); //  52  Total Females Destroyed
				
				listValues.add(pigletStatusEventDao.getBoarEntered(start, end, companyId, premisesId));//  53  Boar Entered
				listValues.add(pigletStatusEventDao.getBoarCulled(start, end, companyId, premisesId)); //  54 Boar Culled
				listValues.add(pigletStatusEventDao.getBoarDeathsandDestroyed(start, end, companyId, premisesId));// 55 Boar Deaths and Destroyed
				
				listValues.add(pigletStatusEventDao.getTotalAbortions(start, end, companyId, premisesId));// 56 Total Abortions
				listValues.add(pigletStatusEventDao.getAbortionsNatural(start, end, companyId, premisesId));// 57 Abortions - Natural
				listValues.add(pigletStatusEventDao.getAbortionsInduced(start, end, companyId, premisesId));// 58 Abortions - Induced
				listValues.add(pigletStatusEventDao.getAveAbortionParity(start, end, companyId, premisesId));// 59 Ave Abortion Parity
				
				if(premisesId !=null && premisesId !=0)
				{
					listValues.add(pigletStatusEventDao.getSowsorGiltsTransferredIN(start, end, companyId, premisesId));// 60 sows or gilts Transferred IN
					listValues.add(pigletStatusEventDao.getSowsorGiltsTransferredOut(start, end, companyId, premisesId));// 61 sows or gilts Transferred out
				}
				else
				{
					listValues.add(0); //60
					listValues.add(0); //61
				}
				listValues.add(pigletStatusEventDao.getGiltEntered(start, end, companyId, premisesId));// 62 number of gilt entered during the time period
					
				Map mapOfValues = new LinkedHashMap();
				mapOfValues.put("totalFerrow", totalFerrowEvents);
				mapOfValues.put("valueList", listValues);
				dateMapDate.put(start, mapOfValues);
				
			}
	       /* else
	        {
	        	dateMapDate.put(start, null);
	        }*/
	        c++;
	    }
	    return c - 1;
	}
	
	public List<Integer> getFerrowEventListId(Date start, Date end,int companyId, Integer premisesId)
	{
		try {
			return eventMasterDao.selectFerrowEvents(start, end, companyId, premisesId);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int getActivedPenCount(int companyId)
	{
		try {
			return penDao.getTotalPenActive(companyId);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}
	
	private int getPigletStatusEventsFerrowIdCountForWeavnAndDateRange(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdCountForWeavnAndDateRange(start, end, companyId,premisesId);
	}
	
	private int getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(start, end, companyId,premisesId);
	}
	
	private int getTotalPigsWeavend(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getTotalPigsWeavend(start, end, companyId,premisesId);
	}
	
	private int getPigletStatusEventsFerrowIdWeavnAndFosterInAndOut(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdWeavnAndFosterInAndOut(start, end, companyId,premisesId);
	}
	
	private int getTotalPigsMortal(Date start, Date end, int companyId,Integer premisesId)
	{
		return removalEventExceptSalesDetailsDao.getTotalPigsMortal(start, end, companyId,premisesId);
	}
	
	private int getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithWeight(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithWeight( start,  end, companyId,premisesId);
	}
	
	private int getTotalPigsWeavendWithWeight(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getTotalPigsWeavendWithWeight( start,  end, companyId,premisesId);
	}
	
	private int getTotalPigsWeight(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getTotalPigsWeight(start,  end, companyId,premisesId);
	}
	
	private int getPigletStatusEventsFerrowIdForWeavnAndDateRange(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdForWeavnAndDateRange(start, end, companyId,premisesId);
	}
	
	private int getCountPifIngoIdFromFarrowWithParityOneInPigInfo(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getCountPifIngoIdFromFarrowWithParityOneInPigInfo(start, end, companyId,premisesId);
	}
	
	private int getCountParityOfPigIngoIdFromFarrow(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getCountParityOfPigIngoIdFromFarrow(start, end, companyId,premisesId);
	}
	
	private int getSumOfDiffOfFerrowAndBreedingDate(Date start, Date end, int companyId,Integer premisesId)// doubt here how to put premises
	{
		return pigletStatusEventDao.getSumOfDiffOfFerrowAndBreedingDate(start, end, companyId,premisesId);
	}
	
	private int getPiGIdFromFerrow(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getPiGIdFromFerrow(start, end, companyId,premisesId);
	}
	
	private int getPiGIdFromBreeding(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getPiGIdFromBreeding(start, end, companyId,premisesId);
	}
	
	private int getCountOfFirstService(Date start, Date end, int companyId,Integer premisesId) // Doubt here how to put premises ?
	{
		return pigletStatusEventDao.getCountOfFirstService(start, end, companyId,premisesId);
	}
	
	private int getCountOfRepeateService(Date start, Date end, int companyId,Integer premisesId) // Doubt here how to take premises ?
	{
		return pigletStatusEventDao.getCountOfRepeateService(start, end, companyId,premisesId);
	}
	
	private int getCountOfServiceWithMatingGreaterThanOne(Date start, Date end, int companyId,Integer premisesId) // doubt
	{
		return pigletStatusEventDao.getCountOfServiceWithMatingGreaterThanOne(start, end, companyId,premisesId);
	}
	
	private int getCountOfMating(Date start, Date end, int companyId,Integer premisesId) // doubt
	{
		return pigletStatusEventDao.getCountOfMating(start, end, companyId,premisesId);
	}
	
	private int getCountOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding(Date start, Date end, int companyId,Integer premisesId) // doubt
	{
		return pigletStatusEventDao.getCountOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding(start, end, companyId,premisesId);
	}
	
	private int getPigletStatusEventsPigIdCountForWeavnAndDateRangeWithMoreThanTwalePig(Date start, Date end, int companyId,Integer premisesId) // doubt
	{
		return pigletStatusEventDao.getPigletStatusEventsPigIdCountForWeavnAndDateRange(start, end, companyId,premisesId);
	}
	
	private int getNumberOfDaysBetweenWeanAndServiceDate(Date start, Date end, int companyId,Integer premisesId) // doubt
	{
		return pigletStatusEventDao.getNumberOfDaysBetweenWeanAndServiceDate(start, end, companyId,premisesId);
	}
	
	private int getPairtyOfServedFemals(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getPairtyOfServedFemals(start, end, companyId,premisesId);
	}
	
	private int getCountPifIngoIdWithParityOneInPigInfo(Date start, Date end, int companyId,Integer premisesId)
	{
		return pigletStatusEventDao.getCountPifIngoIdWithParityOneInPigInfo(start, end, companyId,premisesId);
	}
	
	private int getSumOfDateDiffBetweenServiceAndEntryDate(Date start, Date end, int companyId,Integer premisesId)  // doubt
	{
		return pigletStatusEventDao.getSumOfDateDiffBetweenServiceAndEntryDate(start, end, companyId,premisesId);
	}
	
	
}
