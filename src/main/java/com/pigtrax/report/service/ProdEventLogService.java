package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.ProdEventLogBean;
import com.pigtrax.report.dao.ProdEventLogDao;
import com.pigtrax.util.DateUtil;

@Repository
public class ProdEventLogService {

	@Autowired
	ProdEventLogDao prodEventLogDao;

	private static final String seprater = ",";

	public List<String> getProdEventLogList(String premise, Integer premiseId, Date startDate, Date endDate) { 
		List<ProdEventLogBean> prodEventLogList = prodEventLogDao.getProdEventLogList(premiseId, startDate, endDate);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (prodEventLogList != null && prodEventLogList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Premise, Barn, Room, Group ID, Date (mm/dd/yyyy), Log Event Type, Remarks");
			returnRows.add("\n");
			for (ProdEventLogBean pigletMortalityReportBean : prodEventLogList) {
				rowBuffer = new StringBuffer();
					rowBuffer.append(premise + seprater);
					rowBuffer.append(pigletMortalityReportBean.getBarnId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getRoomId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getGroupId() + seprater);
					try {
						dateStr = DateUtil.convertToFormatString(pigletMortalityReportBean.getEventDate(), "MM/dd/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr);
						else
							rowBuffer.append(" ");
					} catch (ParseException e) {
						rowBuffer.append(" ");
					}	
					rowBuffer.append(seprater);
					rowBuffer.append(pigletMortalityReportBean.getLogEventType() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getRemark() + seprater);
					rowBuffer.append(" ");
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}

}
