package com.pigtrax.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.FeedReportBean;
import com.pigtrax.report.dao.FeedReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class FeedReportService {
	
	@Autowired
	FeedReportDao feedReportDao;
	
	@Autowired
	MessageSource messageSource;

	private static final String seprater = ",";
	
	public List<String> getFeedList(String premise, int premiseId, Locale locale, Integer groupId) {
	
		List<FeedReportBean> feedReportBeanList = feedReportDao.getFeedList(premiseId, groupId);
		
		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (feedReportBeanList != null && feedReportBeanList.size() > 0) {
			
			StringBuffer rowBuffer = null;		
			
			returnRows.add(messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","+messageSource.getMessage("label.premise.barn", null, "", locale)+","
					+messageSource.getMessage("label.barn.silo", null, "", locale)+","+messageSource.getMessage("label.piginfo.feedEventForm.batchId", null, "", locale)+","+messageSource.getMessage("label.feedEventDetail.feedEventDate", null, "", locale)+","
					+messageSource.getMessage("label.feedEventDetail.feedEventTypeId", null, "", locale)+","+messageSource.getMessage("label.piginfo.breedingeventform.weightInKgs", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.feedEventForm.feedCost", null, "", locale)+","+messageSource.getMessage("label.piginfo.feedEventForm.feedMedication", null, "", locale)
					+messageSource.getMessage("label.piginfo.feedEventForm.ticketNumber", null, "", locale)+","+messageSource.getMessage("label.feedEventDetail.feedmill", null, "", locale)+","
					+messageSource.getMessage("label.transportJourney.transportTruckId", null, "", locale)+","+messageSource.getMessage("label.transportJourney.transportTrailerId", null, "", locale)
					+","+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale)+",\n");
			
			
			try{
				for (FeedReportBean feedReportBean : feedReportBeanList) {
					rowBuffer = new StringBuffer();
					if(feedReportBean.getGroupID()!=null)
						rowBuffer.append(feedReportBean.getGroupID()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getBarnID()!=null)	
						rowBuffer.append(feedReportBean.getBarnID()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getSiloID()!=null)
						rowBuffer.append(feedReportBean.getSiloID()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getRationID()!=null)
						rowBuffer.append(feedReportBean.getRationID()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getFeedEventDate()!=null)
						rowBuffer.append(DateUtil.convertToFormatString(feedReportBean.getFeedEventDate(),"dd/MM/yyyy")).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getFeedEventtype()!=null)
						rowBuffer.append(feedReportBean.getFeedEventtype()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getWeight()!=0)
						rowBuffer.append(feedReportBean.getWeight()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getFeedcost()!=0)
						rowBuffer.append(feedReportBean.getFeedcost()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getMedication()!=null)
						rowBuffer.append(feedReportBean.getMedication()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getTruckTicket()!=null)
						rowBuffer.append(feedReportBean.getTruckTicket()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getFeedMill()!=null)
						rowBuffer.append(feedReportBean.getFeedMill()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getTruck()!=null)
						rowBuffer.append(feedReportBean.getTruck()).append(seprater);
					else
						rowBuffer.append(seprater);
					
					if(feedReportBean.getTrailer()!=null)
						rowBuffer.append(feedReportBean.getTrailer()).append(seprater);
					else
						rowBuffer.append(seprater);				
					
					if(feedReportBean.getRemarks()!=null)
						rowBuffer.append(feedReportBean.getRemarks());
					else
						rowBuffer.append(seprater);
					
					returnRows.add(rowBuffer.toString()+"\n");
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnRows.add("Error occured please coontact admin.");
			}
			
		}

	return returnRows;

	}
}
