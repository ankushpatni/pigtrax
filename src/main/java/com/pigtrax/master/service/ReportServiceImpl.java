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
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.service.interfaces.ReportService;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;

@Repository
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	private static final String DEFAULT_FORMATTER = "yyyy-MM-dd";
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	Map<Date,Map> dateMapDate = new LinkedHashMap<Date,Map>();

	@Override
	public Map<Date,Map> getFerrowEventReport(String startDate,
			String endDate) {
		
		Date startDateD;
		Date endDateD;
		try {
			startDateD = new Date( sdf.parse(startDate).getTime());
			endDateD = new Date( sdf.parse(endDate).getTime());
			dateMapDate = new LinkedHashMap<Date,Map>();
			monthsBetween(startDateD, endDateD);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		} 	
		
		return dateMapDate;
	}
	
	public int monthsBetween(Date a, Date b) {
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
	        
	        if(end.before(b))
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
				listValues.add(litterLess7);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
