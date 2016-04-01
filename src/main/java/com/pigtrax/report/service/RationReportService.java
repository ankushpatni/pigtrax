package com.pigtrax.report.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.RationReportBean;
import com.pigtrax.report.dao.RationReportDao;

@Repository
public class RationReportService {

	@Autowired
	RationReportDao rationReportDao;
	
	@Autowired
	MessageSource messageSource;

	private static final String seprater = ",";

	public List<String> getRationReportList(String premise, Integer premiseId, Date startDate, Date endDate, Integer groupId, Locale locale) { 
		List<RationReportBean> rationReportList = rationReportDao.getRationReportList(premiseId, startDate, endDate, groupId);

		ArrayList<String> returnRows = new ArrayList<String>();
		if (rationReportList != null && rationReportList.size() > 0) {

			StringBuffer rowBuffer = null;			
			
			returnRows.add(messageSource.getMessage("label.piginfo.feedEventForm.batchId", null, "", locale)+","+messageSource.getMessage("label.reports.rationreport.actualtonsused", null, "", locale)+","
					+messageSource.getMessage("label.reports.rationreport.targettonsused", null, "", locale)+","+messageSource.getMessage("label.reports.rationreport.deviationtonsused", null, "", locale)+","
					+messageSource.getMessage("label.reports.rationreport.actualkgperpig", null, "", locale)+","+messageSource.getMessage("label.reports.rationreport.targetkgperpig", null, "", locale)+","
					+messageSource.getMessage("label.reports.rationreport.deviationkgperpig", null, "", locale)+","+messageSource.getMessage("label.reports.rationreport.actualcostperpig", null, "", locale)
					+messageSource.getMessage("label.reports.rationreport.targetcostperpig", null, "", locale)+","+messageSource.getMessage("label.reports.rationreport.deviationcostperpig", null, "", locale)+","
					+messageSource.getMessage("label.reports.salesreport.weightperpig", null, "", locale)+","+messageSource.getMessage("label.piginfo.feedEventForm.ticketNumber", null, "", locale)+",\n");
			
			for (RationReportBean rationReportBean : rationReportList) {
				rowBuffer = new StringBuffer();
					rowBuffer.append(rationReportBean.getRationId() + seprater);
					rowBuffer.append(rationReportBean.getActualTonsUsed() + seprater);
					rowBuffer.append(rationReportBean.getTargetTonsUsed() + seprater);
					rowBuffer.append(rationReportBean.getDeviationTonsUsed() + seprater);
					rowBuffer.append(rationReportBean.getActualKg() + seprater);
					rowBuffer.append(rationReportBean.getTargetKg() + seprater);
					rowBuffer.append(rationReportBean.getDeviationKg() + seprater);
					rowBuffer.append(rationReportBean.getActualFeedCost() + seprater);
					rowBuffer.append(rationReportBean.getTargetFeedCost() + seprater);
					rowBuffer.append(rationReportBean.getDeviationFeedCost() + seprater);
					rowBuffer.append(" ");
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}

}
