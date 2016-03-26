package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.LitterBalanceBean;
import com.pigtrax.report.dao.LitterBalanceDao;
import com.pigtrax.util.DateUtil;

@Repository
public class LitterBalanceService {

	@Autowired
	LitterBalanceDao litterBalanceBean;

	private static final String seprater = ",";

	public List<String> getLitterBalance(String premise, Integer premiseId, Date startDate, Date endDate) { 
		List<LitterBalanceBean> litterBalanceList = litterBalanceBean.getLitterBalance(premiseId, startDate, endDate);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (litterBalanceList != null && litterBalanceList.size() > 0) {

			StringBuffer rowBuffer = null;
			/*returnRows
					.add("PigID, Wean date (dd/MM/yyyy), PenID,Liveborn, Deaths, PigletTransfer, Foster In, Weaned,Balance");
		*/	
			returnRows
			.add("PigID, Wean date (dd/MM/yyyy), PenID,Liveborn, Deaths, PigletTransfer, Weaned,Balance");
			returnRows.add("\n");
			for (LitterBalanceBean litterBalanceBean : litterBalanceList) {
				rowBuffer = new StringBuffer();
					rowBuffer.append(litterBalanceBean.getPigId() + seprater);
					try {
						dateStr = DateUtil.convertToFormatString(litterBalanceBean.getWeanDate(), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr);
						else
							rowBuffer.append(" ");
					} catch (ParseException e) {
						rowBuffer.append(" ");
					}	
					rowBuffer.append(seprater);
					rowBuffer.append(litterBalanceBean.getPenId() + seprater);
					rowBuffer.append(litterBalanceBean.getLiveBorn() + seprater);					
					rowBuffer.append(litterBalanceBean.getDeath() + seprater);
					rowBuffer.append(litterBalanceBean.getTransfer() + seprater);
					//rowBuffer.append(litterBalanceBean.getFosterInNum() + seprater);
					rowBuffer.append(litterBalanceBean.getWean() + seprater);
					rowBuffer.append(litterBalanceBean.getBalance());
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		else
		{
			StringBuffer rowBuffer = new StringBuffer();
			returnRows
					.add("PigID, Wean date (dd/MM/yyyy), PenID,Liveborn, Deaths, PigletTransfer, Foster In, Weaned,Balance");
			returnRows.add("\n");
			rowBuffer.append("No data found"+seprater);
			rowBuffer.append(seprater);
			rowBuffer.append(seprater);					
			rowBuffer.append(seprater);
			rowBuffer.append(seprater);
			rowBuffer.append(seprater);
			rowBuffer.append(seprater);
			rowBuffer.append(seprater);
			returnRows.add(rowBuffer.toString()+"\n");
		}
		return returnRows;
	}

}
