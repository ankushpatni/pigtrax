package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.DataIntegrityLog;
import com.pigtrax.report.dao.DataIntegrityLogDao;
import com.pigtrax.util.DateUtil;

@Repository
public class DataIntegrityLogService {
	
	@Autowired
	DataIntegrityLogDao logDao;
	
	@Autowired
	MessageSource messageSource;
	
	private static final String seprater = ",";
	
	public List<String> getLog(Date startDate, Date endDate, Locale locale, Integer companyId)
	{
		List<DataIntegrityLog> logList = null;
		
		if(companyId != null)
			logList = logDao.getLog(startDate, endDate, companyId);
		else
			logList = logDao.getLog(startDate, endDate);
		
		ArrayList<String> returnRows = new ArrayList<String>();

		if (logList != null && logList.size() > 0) {

			StringBuffer rowBuffer = null;			
			returnRows.add(messageSource.getMessage("label.reports.dataintegrity.eventdate", null, "", locale)+","+messageSource.getMessage("label.reports.dataintegrity.datatable", null, "", locale)+","+messageSource.getMessage("label.reports.dataintegrity.errortype", null, "", locale)+","
					+messageSource.getMessage("label.reports.dataintegrity.errordescription", null, "", locale)+","+messageSource.getMessage("label.reports.dataintegrity.userresponsible", null, "", locale)+"\n");
			
			for (DataIntegrityLog log : logList) {
				rowBuffer = new StringBuffer();
				try {
					rowBuffer.append(DateUtil.convertToFormatString(log.getEventDate(),"dd/MM/yyyy") + seprater);
				} catch (ParseException e) {
					rowBuffer.append("" + seprater);
				}
					rowBuffer.append(log.getEventType() + seprater);
					rowBuffer.append(log.getErrorType() + seprater);
					
					rowBuffer.append(log.getErrorDescription()+ seprater);
					rowBuffer.append(log.getUserId() != null ?log.getUserId():"" ); 
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}
}
