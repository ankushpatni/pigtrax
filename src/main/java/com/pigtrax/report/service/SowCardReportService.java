package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.SowCardReportBean;
import com.pigtrax.report.dao.SowCardReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class SowCardReportService {
	
	@Autowired
	SowCardReportDao sowCardReportDao;
	
	private static final String seprater = ",";
	
	public List<String> getSowCardList(String premise, int premiseId, String language, int pigId) {
		
		Map< String, StringBuffer> sowCardServiceMap = new LinkedHashMap<String, StringBuffer>();
		List< StringBuffer> sowCardServiceList = new LinkedList<StringBuffer>();
		List<SowCardReportBean> sowCardList = sowCardReportDao.getSowCardList(pigId);
		
		List< String> sowCardServiceListReturn = new LinkedList<String>();
		
		if(sowCardList == null || sowCardList.size() == 0)
			return sowCardServiceListReturn;
		
		//sowCardServiceMap.put("Parity", value);
		sowCardServiceList.add(new StringBuffer("Parity,"));
		sowCardServiceList.add(new StringBuffer("FarrowDate,"));
		sowCardServiceList.add(new StringBuffer("TotalBorn,"));
		sowCardServiceList.add(new StringBuffer("LiveBorn,"));
		sowCardServiceList.add(new StringBuffer("StillBorn,"));
		sowCardServiceList.add(new StringBuffer("Mummies,"));
		sowCardServiceList.add(new StringBuffer("GestLength,"));
		sowCardServiceList.add(new StringBuffer("Farrow Interval,"));
		sowCardServiceList.add(new StringBuffer("Birth Weight,"));
		sowCardServiceList.add(new StringBuffer("Wean Date,"));
		sowCardServiceList.add(new StringBuffer("Pigs Weaned,"));
		sowCardServiceList.add(new StringBuffer("Wean Age,"));
		sowCardServiceList.add(new StringBuffer("Average Weaning Weight,"));
		sowCardServiceList.add(new StringBuffer("Number Of Services,"));
		sowCardServiceList.add(new StringBuffer("First Service Date,"));
		sowCardServiceList.add(new StringBuffer("Last Service Date,"));
		
		int counter = 0;
		for(SowCardReportBean sowCardReportBean : sowCardList)
		{
			try {
			sowCardServiceList.get(0).append(sowCardReportBean.getCurrentParity()).append(",");
			sowCardServiceList.get(1).append(DateUtil.convertToFormatString(sowCardReportBean.getFarrowDateTime(),"dd/MM/yyyy")).append(",");
			sowCardServiceList.get(2).append(sowCardReportBean.getTotalBorn()).append(",");
			sowCardServiceList.get(3).append(sowCardReportBean.getLiveBorns()).append(",");
			sowCardServiceList.get(4).append(sowCardReportBean.getStillBorns()).append(",");
			sowCardServiceList.get(5).append(sowCardReportBean.getMummies()).append(",");
			sowCardServiceList.get(6).append(sowCardReportBean.getGenLength()).append(",");
			if(counter != 0)
				{
				SowCardReportBean sowCardReportBeanDate = sowCardList.get(counter-1);
				long diff = sowCardReportBean.getFarrowDateTime().getTime() - sowCardReportBeanDate.getFarrowDateTime().getTime();
				long dateDifference  = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				sowCardServiceList.get(7).append(dateDifference).append(",");
				//int dateDifference = DateUtil.sowCardReportBean.getFarrowDateTime() - sowCardReportBeanDate.getFarrowDateTime();
				}
			else
			{
				sowCardServiceList.get(7).append(",");
			}
			sowCardServiceList.get(8).append(sowCardReportBean.getBirthWeight()).append(",");
			sowCardServiceList.get(9).append(DateUtil.convertToFormatString(sowCardReportBean.getWeanDate(),"dd/MM/yyyy")).append(",");
			sowCardServiceList.get(10).append(sowCardReportBean.getNumberOfPigs()).append(",");
			sowCardServiceList.get(11).append(sowCardReportBean.getWeanAge()).append(",");
			sowCardServiceList.get(12).append(sowCardReportBean.getAvgWeanWeight()).append(",");
			sowCardServiceList.get(13).append(sowCardReportBean.getTotalService()).append(",");
			sowCardServiceList.get(14).append(DateUtil.convertToFormatString(sowCardReportBean.getFirstServiceDate(),"dd/MM/yyyy")).append(",");
			sowCardServiceList.get(15).append(DateUtil.convertToFormatString(sowCardReportBean.getLastServiceDate(),"dd/MM/yyyy")).append(",");
			counter++;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(StringBuffer stringBuffer : sowCardServiceList)
		{
			sowCardServiceListReturn.add(stringBuffer.toString()+"\n");
		}
		
		return sowCardServiceListReturn;
		
	}

}
