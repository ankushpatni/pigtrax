package com.pigtrax.report.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.ProdEventLogBean;
import com.pigtrax.report.dao.ProdEventLogDao;

@Repository
public class ProdEventLogService {

	@Autowired
	ProdEventLogDao prodEventLogDao;

	private static final String seprater = ",";

	public List<String> getProdEventLogList(String premise, Integer premiseId, Date startDate, Date endDate) { 
		List<ProdEventLogBean> prodEventLogList = prodEventLogDao.getProdEventLogList(premiseId, startDate, endDate);

		ArrayList<String> returnRows = new ArrayList<String>();

		if (prodEventLogList != null && prodEventLogList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Premise, Barn, Room, Group ID, Date, LogEventType, Remark");
			returnRows.add("\n");
			for (ProdEventLogBean pigletMortalityReportBean : prodEventLogList) {
				rowBuffer = new StringBuffer();
					rowBuffer.append(premise + seprater);
					rowBuffer.append(pigletMortalityReportBean.getBarnId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getRoomId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getGroupId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getEventDate() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getLogEventType() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getRemark() + seprater);
					rowBuffer.append(" ");
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}

}
