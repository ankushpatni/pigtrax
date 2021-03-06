package com.pigtrax.report.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.LactationLengthBean;
import com.pigtrax.report.dao.LactationLengthDao;

@Repository
public class LactationLengthReportService {

	@Autowired
	LactationLengthDao lactationLengthDao;
	
	@Autowired
	MessageSource messageSource;

	private static final String seprater = ",";

	public List<String> getLactationLength(Integer premiseId, Date startDate, Date endDate, Locale locale) { 
		List<LactationLengthBean> lactationLengthList = lactationLengthDao.getLactationLength(premiseId, startDate, endDate);
		if(lactationLengthList == null) lactationLengthList = new ArrayList<LactationLengthBean>();
		
		ArrayList<String> returnRows = new ArrayList<String>();

		
			LactationLengthBean emptyBean = new LactationLengthBean();
			emptyBean.setTotalPigCount(lactationLengthDao.getTotalCount(premiseId));
			emptyBean.setNumberOfPigs(0);
			
			int count = 0;
			int totalSowCount = 0;
			
			Map<Integer, LactationLengthBean> dataMap = new TreeMap<Integer, LactationLengthBean>();
			
			if(lactationLengthList != null && 0 < lactationLengthList.size())
			{
			
				for(LactationLengthBean bean : lactationLengthList)
				{	
					LactationLengthBean rowBean = dataMap.get(bean.getLactationLength());
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setSowIdString((rowBean.getSowIdString()!=null?rowBean.getSowIdString():"")+"|"+(bean.getSowIdString()!=null?bean.getSowIdString():""));
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setLactationLength(bean.getLactationLength());
					rowBean.setPercentage(bean.getPercentage());
					
					totalSowCount += bean.getNumberOfPigs();
					
					dataMap.put(bean.getLactationLength(), rowBean);
				}
				
				
				StringBuffer rowBuffer = null;
				double totalPercentage = 0;
				double weightedAvgOfLacationDays = 0;
				double averageLactationDays = 0D;
				double totalLactationDays = 0D;
				
				
				returnRows.add(messageSource.getMessage("label.reports.lactation.numberofsows", null, "", locale)+","+messageSource.getMessage("label.reports.lactation.lactationdays", null, "", locale)+","
						+messageSource.getMessage("label.reports.lactation.totalpercentage", null, "", locale)+"\n");
				
				LactationLengthBean lactationLengthBean = null;
				int numberOfRows = dataMap.entrySet().size();
				for (Map.Entry<Integer, LactationLengthBean> entry : dataMap.entrySet())
				{
				    //System.out.println(entry.getKey() + "/" + entry.getValue());
					if(entry.getValue() != null)
					{
						lactationLengthBean = entry.getValue();
						if(lactationLengthBean.getNumberOfPigs() > 0)
						{
							count = count + lactationLengthBean.getNumberOfPigs();
							rowBuffer = new StringBuffer();							

							rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
							rowBuffer.append(lactationLengthBean.getLactationLength()+ seprater);
							totalLactationDays+=lactationLengthBean.getLactationLength();
							
							lactationLengthBean.setPercentage(((double)(100*lactationLengthBean.getNumberOfPigs())/totalSowCount));
							
							totalPercentage = totalPercentage+lactationLengthBean.getPercentage();
							//weightedAvgOfLacationDays = weightedAvgOfLacationDays + (double)(lactationLengthBean.getNumberOfPigs()*lactationLengthBean.getLactationLength()/count);
							rowBuffer.append(lactationLengthBean.getPercentage());							
							returnRows.add(rowBuffer.toString()+"\n");
							//totalSowCount = lactationLengthBean.getTotalPigCount();
						}
					}
				}
				if(numberOfRows > 0)
				{
					averageLactationDays = totalLactationDays/numberOfRows;
					returnRows.add("\n");
					returnRows.add(1, count +","+averageLactationDays+","+totalPercentage+"\n\n");					
				}
			
			}
		
		return returnRows;
	}

}
