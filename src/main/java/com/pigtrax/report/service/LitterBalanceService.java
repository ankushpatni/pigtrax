package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.LitterBalanceBean;
import com.pigtrax.report.dao.LitterBalanceDao;
import com.pigtrax.util.DateUtil;

@Repository
public class LitterBalanceService {

	@Autowired
	LitterBalanceDao litterBalanceBean;
	
	@Autowired
	MessageSource messageSource;

	private static final String seprater = ",";

	public List<String> getLitterBalance(String premise, Integer premiseId, Date startDate, Date endDate, Locale locale) { 
		List<LitterBalanceBean> litterBalanceList = litterBalanceBean.getLitterBalance(premiseId, startDate, endDate);

		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (litterBalanceList != null && litterBalanceList.size() > 0) {

			StringBuffer rowBuffer = null;
			/*returnRows
					.add("PigID, Wean date (dd/MM/yyyy), PenID,Liveborn, Deaths, PigletTransfer, Foster In, Weaned,Balance");
		*/				
			
			returnRows.add(messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","+messageSource.getMessage("label.reports.litterbalance.weandate", null, "", locale)
					+messageSource.getMessage("label.piginfo.input.dateformat", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.entryeventform.pen", null, "", locale)+","+messageSource.getMessage("label.piginfo.farroweventform.liveborns", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.pigletstatuseventform.death", null, "", locale)+","+messageSource.getMessage("label.piginfo.pigletstatuseventform.foster", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.pigletstatuseventform.wean", null, "", locale)+","+messageSource.getMessage("label.leftmenu.reports.litterBalance", null, "", locale)+"\n");
			
			ArrayList<String> returnRows1 = new ArrayList<String>();
			int liveBornTotal = 0;
			int deathTotal = 0 ;
			int transferTotal = 0;
			int weanTotal = 0 ;
			int banalceTotal = 0;
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
					returnRows1.add(rowBuffer.toString()+"\n");
					liveBornTotal  = liveBornTotal + litterBalanceBean.getLiveBorn();
					deathTotal = deathTotal + litterBalanceBean.getDeath() ;
					transferTotal = transferTotal + litterBalanceBean.getTransfer() ;
					weanTotal = weanTotal + litterBalanceBean.getWean() ;
					banalceTotal = banalceTotal + litterBalanceBean.getBalance();
			}
			returnRows.add(messageSource.getMessage("label.piginfo.litterbalanceReport.netLitterBalance", null, "", locale)+",,,"+liveBornTotal+","+deathTotal+","+
					transferTotal+","+weanTotal+","+banalceTotal+"\n");
			returnRows.addAll(returnRows1);
		}	
		
		return returnRows;
	}

}
