package com.pigtrax.report.service;

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
import com.pigtrax.pigevents.beans.CompanyTarget;
import com.pigtrax.pigevents.service.interfaces.CompanyTargetService;
import com.pigtrax.report.dao.GestationReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class GestationReportService {

	@Autowired
	GestationReportDao gestationReportDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	CompanyTargetService companyTargetService;
	
	private static final Logger logger = Logger.getLogger(GestationReportService.class);
	
	private static final String seprater = ",";

	public List<String> getGestationResult(String premise, Integer premiseId, Date startDate, Date endDate, Locale locale, String reportOption) { 
		
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
		CompanyTarget companyTargetsByPremises = null;
		try {
			companyTargetsByPremises = companyTargetService.getCompanyTargetsByPremises(premiseId);
		} catch (PigTraxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		logger.info("Range List size"+rangeList.size());
		gestationReportDao.getGestationResultList(premiseId, rangeList, reportOption);
		
		Integer gestationTarget = gestationReportDao.getAverageGestationTarget(premiseId);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (rangeList != null && rangeList.size() > 0) {

			StringBuffer rowBuffer = null;
			
			returnRows.add(messageSource.getMessage("label.reports.gestation.servdatestart", null, "", locale)+","+messageSource.getMessage("label.reports.gestation.servdateend", null, "", locale)+","
					+messageSource.getMessage("label.reports.gestation.servwk", null, "", locale)+","+messageSource.getMessage("label.reports.gestation.numberserv", null, "", locale)+","
					+messageSource.getMessage("label.reports.gestation.servtarget", null, "", locale)+","+messageSource.getMessage("label.reports.gestation.servadj", null, "", locale)+","
					+"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,"+messageSource.getMessage("label.piginfo.farroweventform.farrowEvent", null, "", locale)+","
					+messageSource.getMessage("label.reports.gestation.percetfr", null, "", locale)+","+messageSource.getMessage("label.reports.gestation.farrowdatestart", null, "", locale)+","
					+messageSource.getMessage("label.reports.gestation.farrowwk", null, "", locale)+","+messageSource.getMessage("label.reports.gestation.facapacity", null, "", locale)+"\n");
			
			
			
			for (Map mpRow : rangeList) {
				rowBuffer = new StringBuffer();
				try {
					dateStr = DateUtil.convertToFormatString((Date)mpRow.get("ServDateSTART"), "dd/MM/yyyy");
					if(dateStr != null)
						rowBuffer.append(dateStr);
					else
						rowBuffer.append(" ");
				} catch (ParseException e) {
					rowBuffer.append(" ");
				}	
				rowBuffer.append(seprater);
				try {
					dateStr = DateUtil.convertToFormatString((Date)mpRow.get("ServDateEND"), "dd/MM/yyyy");
					if(dateStr != null)
						rowBuffer.append(dateStr);
					else
						rowBuffer.append(" ");
				} catch (ParseException e) {
					rowBuffer.append(" ");
				}	
				
				rowBuffer.append(seprater);				
				rowBuffer.append(mpRow.get("ServeWk") + seprater);
				rowBuffer.append(mpRow.get("NumberServ") + seprater);
				//rowBuffer.append(gestationTarget + seprater);
				if(companyTargetsByPremises != null && DateUtil.isDateAfter((Date)mpRow.get("ServDateEND"),companyTargetsByPremises.getCompletionDate()))
				{
					rowBuffer.append(companyTargetsByPremises.getTargetValue()).append(seprater);	
				}
				else
				{
					rowBuffer.append(seprater);	
				}
				rowBuffer.append(((Integer)mpRow.get("NumberServ")- gestationTarget)+ seprater);
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
				rowBuffer.append(mpRow.get("Farrows") + seprater);
				rowBuffer.append(mpRow.get("%FR") + seprater);
				rowBuffer.append(mpRow.get("FarrowDate") + seprater);
				rowBuffer.append(mpRow.get("FarrowWK") + seprater);
				rowBuffer.append(mpRow.get("FACapacity"));
				returnRows.add(rowBuffer.toString()+"\n");
			}
		}
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
