package com.pigtrax.report.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.LactationLengthBean;
import com.pigtrax.report.dao.LactationLengthDao;

@Repository
public class LactationLengthReportService {

	@Autowired
	LactationLengthDao lactationLengthDao;

	private static final String seprater = ",";

	public List<String> getLactationLength(Integer premiseId, Date startDate, Date endDate) { 
		List<LactationLengthBean> lactationLengthList = lactationLengthDao.getLactationLength(premiseId, startDate, endDate);

		ArrayList<String> returnRows = new ArrayList<String>();

		if (lactationLengthList != null && lactationLengthList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Lactation Days, Number of Sows, Percent of Total");
			returnRows.add("\n");
			for (LactationLengthBean lactationLengthBean : lactationLengthList) {
				rowBuffer = new StringBuffer();
				
					rowBuffer.append(lactationLengthBean.getLactationLength() + seprater);
					rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
					rowBuffer.append(lactationLengthBean.getPercentage() );
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}

}
