package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.TargetReportBean;
import com.pigtrax.report.dao.TargetReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class TargetReportService {

	@Autowired
	TargetReportDao targetReportDao;

	private static final String seprater = ",";

	public List<String> getTargetList(Integer companyId,Date startDate) { 
		List<TargetReportBean> prodEventLogList = targetReportDao.getTargetList(companyId,startDate);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (prodEventLogList != null && prodEventLogList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Parameter, Start date (dd/MM/yyyy), Target value, Remark");
			returnRows.add("\n");
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
					rowBuffer.append(targetReportBean.getRemark() + seprater);
					rowBuffer.append(" ");
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}

}
