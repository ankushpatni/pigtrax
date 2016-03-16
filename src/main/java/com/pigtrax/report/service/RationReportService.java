package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.RationReportBean;
import com.pigtrax.report.dao.RationReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class RationReportService {

	@Autowired
	RationReportDao rationReportDao;

	private static final String seprater = ",";

	public List<String> getRationReportList(String premise, Integer premiseId, Date startDate, Date endDate, Integer groupId) { 
		List<RationReportBean> rationReportList = rationReportDao.getRationReportList(premiseId, startDate, endDate, groupId);

		ArrayList<String> returnRows = new ArrayList<String>();
		if (rationReportList != null && rationReportList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Ration ID, Actual_Tons used, Target_Tons used, Deviation_Tons used,Actual_kg/pig/day, Target_kg/pig/day, "
							+ "Deviation_kg/pig/day,Actual_feed cost/pig,Target_feed cost/pig,Deviation_feed cost/pig");
			returnRows.add("\n");
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
