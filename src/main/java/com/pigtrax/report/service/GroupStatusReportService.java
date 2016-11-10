package com.pigtrax.report.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.master.dao.interfaces.BarnDao;
import com.pigtrax.master.dao.interfaces.PremisesDao;
import com.pigtrax.master.dao.interfaces.RoomDao;
import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.dto.Room;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.report.dao.GroupStatusReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class GroupStatusReportService {

	@Autowired
	GroupStatusReportDao groupStatusReportDao;
	
	@Autowired
	GroupEventDao groupDao;
	
	@Autowired
	PremisesDao premiseDao;
	
	@Autowired
	RoomDao roomDao;
	
	@Autowired
	BarnDao barnDao;
	
	@Autowired
	GroupEventService groupEventService;
	
	private static final Logger logger = Logger.getLogger(GroupStatusReportService.class);
	
	private static final String seprater = ",";
	
	@Autowired
	MessageSource messageSource;

	public List<String> getGroupStatusResult(Integer companyId, String selectedPremise,  Date startDate, Date endDate, String groupIdStr, Locale locale, String reportType) { 
		
		
		Date inputStartDate  = startDate;
		Date inputEndDate  = endDate;
		
		Integer groupId = null;
		ArrayList<String> returnRows = new ArrayList<String>();
		if(groupIdStr != null)
		   groupId = Integer.parseInt(groupIdStr);
		Room room = null;
		Barn barn = null;
		if(selectedPremise != null)
		{	
			
			List<Map<String, Object>> rangeList = new ArrayList<Map<String,Object>>();
			Map<String, Object> mp = new HashMap<String, Object>();
			Calendar cal = Calendar.getInstance();
			while(DateUtil.isDateAfter(endDate,startDate)){
				mp = getDateRange(startDate,endDate);
				rangeList.add(mp);
				startDate = (Date)mp.get("EventDateEnd");
				cal.setTime(startDate);
				cal.add(Calendar.DAY_OF_MONTH, 1);//add one day to the previous end date
				startDate = cal.getTime();
			}
			
			logger.info("Range List size"+rangeList.size());
			
			
			List<Map<String, Object>> resultList  = groupStatusReportDao.getGroupStatusList(companyId, selectedPremise, inputStartDate, inputEndDate, rangeList,  locale.getLanguage(), reportType);
	
			
			
			
			String dateStr = "";
			if (resultList != null && resultList.size() > 0) {
	
				StringBuffer rowBuffer = null;
				
				returnRows.add(messageSource.getMessage("label.reports.groupstatus.phasetype", null, "", locale)+","
						+messageSource.getMessage("label.barn.barnId", null, "", locale)+","
						+messageSource.getMessage("label.room.roomID", null, "", locale)+","
						+messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","
								+messageSource.getMessage("label.reports.groupstatus.eventstartdate", null, "", locale)+","
						+messageSource.getMessage("label.reports.groupstatus.eventdateend", null, "", locale)+","+messageSource.getMessage("label.reports.groupstatus.wk", null, "", locale)+","
						+messageSource.getMessage("label.reports.groupstatus.startwt", null, "", locale)+","+messageSource.getMessage("label.reports.groupstatus.starthd", null, "", locale)+","
						+messageSource.getMessage("label.reports.groupstatus.inventory", null, "", locale)+","+messageSource.getMessage("label.reports.groupstatus.deads", null, "", locale)+","
						+messageSource.getMessage("label.reports.groupstatus.percentagemortality", null, "", locale)+","+messageSource.getMessage("label.reports.groupstatus.wof", null, "", locale)+","
						+messageSource.getMessage("label.reports.groupstatus.density", null, "", locale)+","+messageSource.getMessage("label.reports.groupstatus.sales", null, "", locale)+","
						+"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,"+messageSource.getMessage("label.piginfo.groupstatus.projectedsaledate", null, "", locale)+","
						+messageSource.getMessage("label.reports.groupstatus.salewk", null, "", locale)+"\n");
				
				
				for (Map mpRow : resultList) {
					rowBuffer = new StringBuffer();
					rowBuffer.append(mpRow.get("PhaseType") + seprater);
					rowBuffer.append(mpRow.get("BarnId")+ seprater);
					rowBuffer.append(mpRow.get("RoomId")+ seprater);
					rowBuffer.append(mpRow.get("GroupId")+ seprater);
					try {
						dateStr = DateUtil.convertToFormatString((Date)mpRow.get("EventDateStart"), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr);
						else
							rowBuffer.append(" ");
					} catch (ParseException e) {
						rowBuffer.append(" ");
					}	
					rowBuffer.append(seprater);
					try {
						dateStr = DateUtil.convertToFormatString((Date)mpRow.get("GroupEventCloseDate"), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr);
						else
							rowBuffer.append(" ");
					} catch (ParseException e) {
						rowBuffer.append(" ");
					}	
					rowBuffer.append(seprater);					
					rowBuffer.append(mpRow.get("WK") + seprater);
					rowBuffer.append(mpRow.get("StartWt") + seprater);
					rowBuffer.append(mpRow.get("StartHd") + seprater);
					rowBuffer.append(mpRow.get("Inventory")+ seprater);
					rowBuffer.append(mpRow.get("Deads")+ seprater);
					rowBuffer.append(mpRow.get("Mortality%")+ seprater);
					rowBuffer.append(mpRow.get("WOF")+ seprater);
					rowBuffer.append(mpRow.get("Density")+ seprater);
					rowBuffer.append(mpRow.get("Sales")+ seprater);
					rowBuffer.append(mpRow.get("W1") + seprater);
					rowBuffer.append(mpRow.get("W2") + seprater);
					rowBuffer.append(mpRow.get("W3") + seprater);
					rowBuffer.append(mpRow.get("W4") + seprater);
					rowBuffer.append(mpRow.get("W5") + seprater);
					rowBuffer.append(mpRow.get("W6") + seprater);
					rowBuffer.append(mpRow.get("W7") + seprater);
					rowBuffer.append(mpRow.get("W8") + seprater);
					rowBuffer.append(mpRow.get("W9") + seprater);
					rowBuffer.append(mpRow.get("W10") + seprater);
					rowBuffer.append(mpRow.get("W11") + seprater);
					rowBuffer.append(mpRow.get("W12") + seprater);
					rowBuffer.append(mpRow.get("W13") + seprater);
					rowBuffer.append(mpRow.get("W14") + seprater);
					rowBuffer.append(mpRow.get("W15") + seprater);
					rowBuffer.append(mpRow.get("W16") + seprater);
					rowBuffer.append(mpRow.get("W17") + seprater);
					rowBuffer.append(mpRow.get("W18") + seprater);
					rowBuffer.append(mpRow.get("W19") + seprater);
					rowBuffer.append(mpRow.get("W20") + seprater);
					rowBuffer.append(mpRow.get("W21") + seprater);
					rowBuffer.append(mpRow.get("W22") + seprater);
					rowBuffer.append(mpRow.get("W23") + seprater);
					rowBuffer.append(mpRow.get("W24") + seprater);
					rowBuffer.append(mpRow.get("W25") + seprater);
					rowBuffer.append(mpRow.get("W26") + seprater);
					try {
						dateStr = DateUtil.convertToFormatString((Date)mpRow.get("ProjectedSaleDate"), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr);
						else
							rowBuffer.append(" ");
					} catch (ParseException e) {
						rowBuffer.append(" ");
					}	
					rowBuffer.append(seprater);	
					rowBuffer.append(mpRow.get("SaleWk") );
					returnRows.add(rowBuffer.toString()+"\n");
				}
			}
		}
		return returnRows;
	}

	private Map<String, Object> getDateRange(Date startDate,Date endDate){
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("EventDateStart", startDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		mp.put("WK", week);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		Date servDateEnd = cal.getTime();
		if(!DateUtil.isDateAfter(endDate,servDateEnd)){
			mp.put("EventDateEnd", endDate);//set the end date as the ServDateEND
		} else{
			mp.put("EventDateEnd", servDateEnd);//add 6 days to start date and set as ServDateEND
		}
		
		return mp;	
	}	
	
}
