package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.ProdEventLogBean;
import com.pigtrax.report.dao.ProdEventLogDao;
import com.pigtrax.util.DateUtil;

@Repository
public class ProdEventLogService {

	@Autowired
	ProdEventLogDao prodEventLogDao;
	
	@Autowired
	MessageSource messageSource;
	

	private static final String seprater = ",";

	public List<String> getProdEventLogList(String premise, Integer premiseId, Date startDate, Date endDate, Locale locale) { 
		List<ProdEventLogBean> prodEventLogList = prodEventLogDao.getProdEventLogList(premiseId, startDate, endDate);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (prodEventLogList != null && prodEventLogList.size() > 0) {

			StringBuffer rowBuffer = null;						
			
			returnRows.add(messageSource.getMessage("label.piginfo.removalExceptSales.premiseId", null, "", locale)+","+messageSource.getMessage("label.premise.barn", null, "", locale)+","
					+messageSource.getMessage("label.barn.room", null, "", locale)+","+messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","
					+messageSource.getMessage("label.productionlogform.observationDate", null, "", locale)+messageSource.getMessage("label.piginfo.input.dateformat", null, "", locale)+","
					+messageSource.getMessage("label.productionlogform.logeventType", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale)+"\n");
			
			for (ProdEventLogBean pigletMortalityReportBean : prodEventLogList) {
				rowBuffer = new StringBuffer();
					rowBuffer.append(premise + seprater);
					rowBuffer.append(pigletMortalityReportBean.getBarnId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getRoomId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getGroupId() + seprater);
					try {
						dateStr = DateUtil.convertToFormatString(pigletMortalityReportBean.getEventDate(), "dd/MM/yyyy");
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
