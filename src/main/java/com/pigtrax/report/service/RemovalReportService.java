package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.report.bean.RemovalReportBean;
import com.pigtrax.report.dao.RemovalReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class RemovalReportService {
	
	@Autowired
	RemovalReportDao removalReportDao;
	
	@Autowired
	RefDataCache refDataCache;

	private static final String seprater = ",";
	
	public List<String> getRemovalList(String premise, int premiseId, java.util.Date startDate, java.util.Date endDate,String language, String animalType) {
		java.sql.Date startDateSql = null;
		java.sql.Date endDateSql = null;
		
		Map<Integer, String> mortalityReasonTypeMap = refDataCache.getMortalityReasonTypeMap(language);
		
		Map< String, StringBuffer> mortalityReasonCountMap = new LinkedHashMap<String, StringBuffer>();
		Map< String, StringBuffer> mortalityReasonPercentageCountMap = new LinkedHashMap<String, StringBuffer>();
		
		mortalityReasonCountMap.put("WeekStart", new StringBuffer("Week Start").append(seprater));
		mortalityReasonCountMap.put("WeekEnd", new StringBuffer("Week End").append(seprater));
		mortalityReasonCountMap.put("Unclassified", new StringBuffer("Unclassified").append(seprater));
		mortalityReasonPercentageCountMap.put("Unclassified", new StringBuffer("Unclassified").append(seprater));
		for (Map.Entry<Integer, String> entry : mortalityReasonTypeMap.entrySet())
		{
			mortalityReasonCountMap.put(entry.getValue(),new StringBuffer(entry.getValue()).append(seprater));
			mortalityReasonPercentageCountMap.put(entry.getValue(),new StringBuffer(entry.getValue()).append(seprater));
		}
		mortalityReasonCountMap.put("Total", new StringBuffer("Total").append(seprater));
		
		List<Map<String, Object>> rangeList = new ArrayList<Map<String,Object>>();
		Map<String, Object> mp = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		while(DateUtil.isDateAfter(endDate,startDate)){
			mp = getDateRange(startDate,endDate);
			rangeList.add(mp);
			startDate = (Date)mp.get("ServDateEND");
			cal.setTime(startDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);//add one day to the previous end date
			startDate = cal.getTime();
		}
		
		for(Map<String, Object> map : rangeList)
		{
			//map.put("mortalityReasonCount", mortalityReasonCountMap);
			int total = 0;
			if(null != startDate)
			{
				startDateSql = new java.sql.Date(((Date)map.get("ServDateSTART")).getTime());
			}
			
			if(null != endDate) 
			{
				endDateSql = new java.sql.Date(((Date)map.get("ServDateEND")).getTime());
			}
			
			List<RemovalReportBean> removalReportBeanList = null;
			
			StringBuffer weekReason = mortalityReasonCountMap.get("WeekStart");
			StringBuffer WeekEnd = mortalityReasonCountMap.get("WeekEnd");
			Set<String> selectedMortality = new HashSet<String>();
			selectedMortality.add("WeekStart");
			selectedMortality.add("WeekEnd");
			try 
			{
				weekReason.append(DateUtil.convertToFormatString((Date)map.get("ServDateSTART"), "dd/MM/yyyy")).append(seprater);
				WeekEnd.append(DateUtil.convertToFormatString((Date)map.get("ServDateEND"), "dd/MM/yyyy")).append(seprater);
				mortalityReasonCountMap.put("WeekStart",weekReason);
				mortalityReasonCountMap.put("WeekEnd",WeekEnd);
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
			
			//if(animalType.equalsIgnoreCase("piglet"))
			{
				removalReportBeanList = removalReportDao.getRemovalMortalityReasonList(premiseId, startDateSql, endDateSql, animalType);
			}
			
			if(removalReportBeanList != null && removalReportBeanList.size() >0)
			{
				for(RemovalReportBean removalReportBean : removalReportBeanList)
				{
					selectedMortality.add(removalReportBean.getMortalityReason());
					StringBuffer mortalityReason = mortalityReasonCountMap.get(removalReportBean.getMortalityReason());
					mortalityReason.append(removalReportBean.getNumberPigsRemoved()).append(seprater);
					mortalityReasonCountMap.put(removalReportBean.getMortalityReason(), mortalityReason);
					total = total + removalReportBean.getNumberPigsRemoved();
				}
				
				StringBuffer mortalityReason = mortalityReasonCountMap.get("Total");
				mortalityReason.append(total).append(seprater);
				mortalityReasonCountMap.put("Total", mortalityReason);
				selectedMortality.add("Total");
				
				for (Map.Entry< String, StringBuffer> entry : mortalityReasonCountMap.entrySet())
				{
					if(!selectedMortality.contains(entry.getKey()))
					{
						StringBuffer mortalityReasonNon = mortalityReasonCountMap.get(entry.getKey());
						mortalityReasonNon.append(seprater);
						mortalityReasonCountMap.put(entry.getKey(), mortalityReasonNon);
					}
				}
			}
			else
			{
				for (Map.Entry< String, StringBuffer> entry : mortalityReasonCountMap.entrySet())
				{
					if(!selectedMortality.contains(entry.getKey()))
					{
						StringBuffer mortalityReason = mortalityReasonCountMap.get(entry.getKey());
						mortalityReason.append(seprater);
						mortalityReasonCountMap.put(entry.getKey(), mortalityReason);
					}
				}
			}
			
			selectedMortality = new HashSet<String>();
			if(removalReportBeanList != null && removalReportBeanList.size() >0)
			{
				for(RemovalReportBean removalReportBean : removalReportBeanList)
				{
					selectedMortality.add(removalReportBean.getMortalityReason());
					StringBuffer mortalityReason = mortalityReasonPercentageCountMap.get(removalReportBean.getMortalityReason());
					mortalityReason.append(((float)removalReportBean.getNumberPigsRemoved()/total)*100).append(seprater);
					mortalityReasonPercentageCountMap.put(removalReportBean.getMortalityReason(), mortalityReason);
				}
				
				for (Map.Entry< String, StringBuffer> entry : mortalityReasonPercentageCountMap.entrySet())
				{
					if(!selectedMortality.contains(entry.getKey()))
					{
						StringBuffer mortalityReason = mortalityReasonPercentageCountMap.get(entry.getKey());
						mortalityReason.append(seprater);
						mortalityReasonPercentageCountMap.put(entry.getKey(), mortalityReason);
					}
				}
				
			}
			else
			{
				for (Map.Entry< String, StringBuffer> entry : mortalityReasonPercentageCountMap.entrySet())
				{
					if(!selectedMortality.contains(entry.getKey()))
					{
						StringBuffer mortalityReason = mortalityReasonPercentageCountMap.get(entry.getKey());
						mortalityReason.append(seprater);
						mortalityReasonPercentageCountMap.put(entry.getKey(), mortalityReason);
					}
				}
			}
			
		}

		ArrayList<String> returnRows = new ArrayList<String>();
		int i = 0;
		for (Map.Entry< String, StringBuffer> entry : mortalityReasonCountMap.entrySet())
		{
			returnRows.add(entry.getValue().toString()+"\n");
			i++;
			if(i==2)
			{
				returnRows.add("Piglet Deaths \n");
			}
		}
		
		returnRows.add("\n");
		returnRows.add("Percentage Piglet Deaths \n");
		for (Map.Entry< String, StringBuffer> entry : mortalityReasonPercentageCountMap.entrySet())
		{
			returnRows.add(entry.getValue().toString()+"\n");
		}
		
		/*if (removalReportBeanList != null && removalReportBeanList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Premise,BarnID,RoomID,PenID,Group ID,PigID,Pstatus,RemovalDate,Number of pigs removed,AveWt,Parity,AveDOF,AveWks,	Mortality Reason");
			returnRows.add("\n");
			try{
				for (RemovalReportBean removalReportBean : removalReportBeanList) {
					rowBuffer = new StringBuffer();
					rowBuffer.append(removalReportBean.getPremise()).append(seprater);
					
					if(removalReportBean.getBarnID() != null)
						rowBuffer.append(removalReportBean.getBarnID()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(removalReportBean.getRoomID() != null)
						rowBuffer.append(removalReportBean.getRoomID()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(removalReportBean.getPenID() != null)
						rowBuffer.append(removalReportBean.getPenID()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(removalReportBean.getGroupID() != null)
						rowBuffer.append(removalReportBean.getGroupID()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(removalReportBean.getPigID() != null)
						rowBuffer.append(removalReportBean.getPigID()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(removalReportBean.getpStatus() != null)
						rowBuffer.append(removalReportBean.getpStatus()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					//rowBuffer.append(removalReportBean.getRemovalDate()).append(seprater);
					rowBuffer.append(DateUtil.convertToFormatString(removalReportBean.getRemovalDate(),"dd/MM/yyyy")).append(seprater);
					rowBuffer.append(removalReportBean.getNumberPigsRemoved()).append(seprater);
					rowBuffer.append(removalReportBean.getAveWeight()).append(seprater);
					rowBuffer.append(removalReportBean.getParity()).append(seprater);
					rowBuffer.append(removalReportBean.getAveDOF()).append(seprater);
					rowBuffer.append(removalReportBean.getAveWOF()).append(seprater);
					rowBuffer.append(removalReportBean.getMortalityReason()).append(seprater);
					returnRows.add(rowBuffer.toString()+"\n");
					
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnRows.add("Error occured please coontact admin.");
			}
		}*/
		
		return returnRows;

	}
	
private Map<String, Object> getDateRange(Date startDate,Date endDate){
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("ServDateSTART", startDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		mp.put("ServeWk", week);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		Date servDateEnd = cal.getTime();
		if(!DateUtil.isDateAfter(endDate,servDateEnd)){
			mp.put("ServDateEND", endDate);//set the end date as the ServDateEND
		} else{
			mp.put("ServDateEND", servDateEnd);//add 6 days to start date and set as ServDateEND
		}
		
		return mp;	
	}	

}
