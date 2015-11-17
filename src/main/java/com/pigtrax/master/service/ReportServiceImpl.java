package com.pigtrax.master.service;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.dao.interfaces.PenDao;
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
	PigletStatusEventDao pigletStatusEventDao;
	
	@Autowired
	RemovalEventExceptSalesDetailsDao removalEventExceptSalesDetailsDao;
	
	private static final String DEFAULT_FORMATTER = "yyyy-MM-dd";
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	Map<Date,Map> dateMapDate = new LinkedHashMap<Date,Map>();

	@Override
	public Map<Date,Map> getFerrowEventReport(String startDate,
			String endDate, Integer companyId) {
		
		Date startDateD;
		Date endDateD;
		try {
			startDateD = new Date( sdf.parse(startDate).getTime());
			endDateD = new Date( sdf.parse(endDate).getTime());
			dateMapDate = new LinkedHashMap<Date,Map>();
			monthsBetween(startDateD, endDateD, companyId);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		} 	
		
		return dateMapDate;
	}
	
	public int monthsBetween(Date a, Date b,  Integer companyId) {
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
	    		cal.add(Calendar.WEEK_OF_YEAR, 1);
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
	        
	        List<Integer> listFerrowId = getFerrowEventListId(start,end);
	        
	        if(listFerrowId!=null && !listFerrowId.isEmpty())
	        {
	        	Integer totalFerrowEvents = new HashSet<Integer>(listFerrowId).size();
	        	
				List listValues = eventMasterDao
						.getFerrowReportParams(listFerrowId);
				System.out.println("listFerrowId----->" + listFerrowId);
				System.out.println("listValues----->" + listValues);
				
				int litterLess7 = eventMasterDao.getLitterForGivenrange(end);
				listValues.add(litterLess7);//6
				listValues.add(getPigletStatusEventsFerrowIdCountForWeavnAndDateRange(start,end,companyId));//7
				listValues.add(getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(start,end,companyId));//8
				listValues.add(getTotalPigsWeavend(start,end,companyId));//9
				listValues.add(getPigletStatusEventsFerrowIdWeavnAndFosterInAndOut(start,end,companyId));//10
				listValues.add(getTotalPigsMortal(start,end,companyId));//11
				listValues.add(getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithWeight(start,end,companyId));//12
				listValues.add(getTotalPigsWeavendWithWeight(start,end,companyId));//13
				listValues.add(getTotalPigsWeight(start,end,companyId));//14
				listValues.add(getPigletStatusEventsFerrowIdForWeavnAndDateRange(start,end,companyId));//15
				Map mapOfValues = new LinkedHashMap();
				mapOfValues.put("totalFerrow", totalFerrowEvents);
				mapOfValues.put("valueList", listValues);
				dateMapDate.put(start, mapOfValues);
				
			}
	        c++;
	    }
	    return c - 1;
	}
	
	public List<Integer> getFerrowEventListId(Date start, Date end)
	{
		try {
			return eventMasterDao.selectFerrowEvents(start, end);
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
	
	private int getPigletStatusEventsFerrowIdCountForWeavnAndDateRange(Date start, Date end, int companyId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdCountForWeavnAndDateRange(start, end, companyId);
	}
	
	private int getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(Date start, Date end, int companyId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithMoreThanTwalePig(start, end, companyId);
	}
	
	private int getTotalPigsWeavend(Date start, Date end, int companyId)
	{
		return pigletStatusEventDao.getTotalPigsWeavend(start, end, companyId);
	}
	
	private int getPigletStatusEventsFerrowIdWeavnAndFosterInAndOut(Date start, Date end, int companyId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdWeavnAndFosterInAndOut(start, end, companyId);
	}
	
	private int getTotalPigsMortal(Date start, Date end, int companyId)
	{
		return removalEventExceptSalesDetailsDao.getTotalPigsMortal(start, end, companyId);
	}
	
	private int getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithWeight(Date start, Date end, int companyId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdCountForWeavnAndDateRangeWithWeight( start,  end, companyId);
	}
	
	private int getTotalPigsWeavendWithWeight(Date start, Date end, int companyId)
	{
		return pigletStatusEventDao.getTotalPigsWeavendWithWeight( start,  end, companyId);
	}
	
	private int getTotalPigsWeight(Date start, Date end, int companyId)
	{
		return pigletStatusEventDao.getTotalPigsWeight(start,  end, companyId);
	}
	
	private int getPigletStatusEventsFerrowIdForWeavnAndDateRange(Date start, Date end, int companyId)
	{
		return pigletStatusEventDao.getPigletStatusEventsFerrowIdForWeavnAndDateRange(start, end, companyId);
	}

}
