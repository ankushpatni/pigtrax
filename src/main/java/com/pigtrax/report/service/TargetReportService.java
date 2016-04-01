package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.TargetReportBean;
import com.pigtrax.report.dao.TargetReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class TargetReportService {

	@Autowired
	TargetReportDao targetReportDao;

	@Autowired
	MessageSource messageSource;
	
	private static final String seprater = ",";

	public List<String> getTargetList(Integer companyId,Integer premiseId, Date startDate, Locale locale) { 
		List<TargetReportBean> prodEventLogList = targetReportDao.getTargetList(companyId,premiseId,startDate);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (prodEventLogList != null && prodEventLogList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows.add(messageSource.getMessage("label.companytargetform.targetname", null, "", locale)+","+messageSource.getMessage("label.companytargetform.completiondate", null, "", locale)
					+messageSource.getMessage("label.piginfo.input.dateformat", null, "", locale)+","
					+messageSource.getMessage("label.companytargetform.targetvalue", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale)+"\n");
			
			for (TargetReportBean targetReportBean : prodEventLogList) {
				rowBuffer = new StringBuffer();
					rowBuffer.append(targetReportBean.getParameter() + seprater);
					try {
						dateStr = DateUtil.convertToFormatString(targetReportBean.getStartDate(), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr);
						else
							rowBuffer.append(" ");
					} catch (ParseException e) {
						rowBuffer.append(" ");
					}	
					rowBuffer.append(seprater);
					rowBuffer.append(targetReportBean.getTargetValue() + seprater);
					rowBuffer.append((targetReportBean.getRemark()  != null)?targetReportBean.getRemark() : "" + seprater);
					rowBuffer.append(" ");
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}

}
