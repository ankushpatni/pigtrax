package com.pigtrax.report.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.PigletMortalityReportBean;
import com.pigtrax.report.dao.PigletMortalityReportDao;

@Repository
public class PigletMortalityReportService {

	@Autowired
	PigletMortalityReportDao pigletMortalityReportDao;

	private static final String seprater = ",";

	public List<String> getPigletMortalityList(String premise, Integer premiseId, Date startDate, Date endDate) { 
		List<PigletMortalityReportBean> pigletMortalityList = pigletMortalityReportDao.getPigletMortalityList(premiseId, startDate, endDate);

		ArrayList<String> returnRows = new ArrayList<String>();

		if (pigletMortalityList != null && pigletMortalityList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Premise, Barn, Room, StartHd, Inven, Lactation Days, Number of Deaths, Mortality Reason, End Date");
			returnRows.add("\n");
			for (PigletMortalityReportBean pigletMortalityReportBean : pigletMortalityList) {
				rowBuffer = new StringBuffer();
				
					rowBuffer.append(premise + seprater);
					rowBuffer.append(pigletMortalityReportBean.getBarnId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getRoomId() + seprater);
					rowBuffer.append(" " + seprater);
					rowBuffer.append(" " + seprater);
					rowBuffer.append(pigletMortalityReportBean.getLactationDays() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getNumberOfDeaths() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getMortalityReason() + seprater);
					rowBuffer.append(" ");
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}

}
