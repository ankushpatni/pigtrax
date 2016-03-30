package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.DataIntegrityLog;
import com.pigtrax.report.bean.PigletMortalityReportBean;
import com.pigtrax.report.dao.DataIntegrityLogDao;
import com.pigtrax.util.DateUtil;

@Repository
public class DataIntegrityLogService {
	
	@Autowired
	DataIntegrityLogDao logDao;
	
	private static final String seprater = ",";
	
	public List<String> getLog(Date startDate, Date endDate)
	{
		List<DataIntegrityLog> logList = logDao.getLog(startDate, endDate);
		
		ArrayList<String> returnRows = new ArrayList<String>();

		if (logList != null && logList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Data Table, Error Type, Event Date, Error Description");
			returnRows.add("\n");
			for (DataIntegrityLog log : logList) {
				rowBuffer = new StringBuffer();
					rowBuffer.append(log.getEventType() + seprater);
					rowBuffer.append(log.getErrorType() + seprater);
					try {
						rowBuffer.append(DateUtil.convertToFormatString(log.getEventDate(),"dd/MM/yyyy") + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					rowBuffer.append(log.getErrorDescription());
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}
}
