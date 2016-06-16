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
			
			/*Map<String, LactationLengthBean> dataMap = new HashMap<String, LactationLengthBean>();*/
			Map<Integer, LactationLengthBean> dataMap = new TreeMap<Integer, LactationLengthBean>();
			
			for(LactationLengthBean bean : lactationLengthList)
			{
				
				/*
				if(bean.getLactationLength() == 0)
				{
					LactationLengthBean rowBean = dataMap.get("0");
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setDayLabel("0 days");
					dataMap.put("0", rowBean);
				}				
				else if(bean.getLactationLength() == 1)
				{
					LactationLengthBean rowBean = dataMap.get("1");
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setDayLabel("1 day");
					dataMap.put("1", rowBean);
				}
				else if(bean.getLactationLength() == 2)
				{
					LactationLengthBean rowBean = dataMap.get("2");
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setDayLabel("2 days");
					dataMap.put("2", rowBean);
				}
				else if(bean.getLactationLength() == 3)
				{
					LactationLengthBean rowBean = dataMap.get("3");
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setDayLabel("3 days");
					dataMap.put("3", rowBean);
				}
				else if(bean.getLactationLength() >= 4 && bean.getLactationLength() <= 7)
				{
					LactationLengthBean rowBean = dataMap.get("4-7");
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setDayLabel("4-7 days");
					dataMap.put("4-7", rowBean);
				}
				else if(bean.getLactationLength() >= 8 && bean.getLactationLength() <= 10)
				{
					LactationLengthBean rowBean = dataMap.get("8-10");
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setDayLabel("8-10 days");
					dataMap.put("8-10", rowBean);
				}
				else if(bean.getLactationLength() >= 11 && bean.getLactationLength() <= 15)
				{
					LactationLengthBean rowBean = dataMap.get("11-15");
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setDayLabel("11-15 days");
					dataMap.put("11-15", rowBean);
				}
				else if(bean.getLactationLength() >= 16 && bean.getLactationLength() <= 20)
				{
					LactationLengthBean rowBean = dataMap.get("16-20");
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setDayLabel("16-20 days");
					dataMap.put("16-20", rowBean);
				}
				else if(bean.getLactationLength() >= 21)
				{
					LactationLengthBean rowBean = dataMap.get(">21");
					if(rowBean == null)
					{
						rowBean = new LactationLengthBean();
					}
					rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
					rowBean.setTotalPigCount(bean.getTotalPigCount());
					rowBean.setDayLabel("21+ days");
					dataMap.put(">21", rowBean);
				}*/
				
				LactationLengthBean rowBean = dataMap.get(bean.getLactationLength());
				if(rowBean == null)
				{
					rowBean = new LactationLengthBean();
				}
				rowBean.setNumberOfPigs(rowBean.getNumberOfPigs()+bean.getNumberOfPigs());
				rowBean.setTotalPigCount(bean.getTotalPigCount());
				rowBean.setLactationLength(bean.getLactationLength());
				rowBean.setPercentage(bean.getPercentage());
				count = count + rowBean.getNumberOfPigs();
				dataMap.put(bean.getLactationLength(), rowBean);
			}
			
			
			StringBuffer rowBuffer = null;
			double totalPercentage = 0;
			double weightedAvgOfLacationDays = 0;
			double averageLactationDays = 0D;
			double totalLactationDays = 0D;
			int totalSowCount = 0;
			
			returnRows.add(messageSource.getMessage("label.reports.lactation.lactationdays", null, "", locale)+","+messageSource.getMessage("label.reports.lactation.numberofsows", null, "", locale)+","
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
						rowBuffer = new StringBuffer();				
						rowBuffer.append(lactationLengthBean.getLactationLength()+ seprater);
						rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
						totalLactationDays+=lactationLengthBean.getLactationLength();
						
						totalPercentage = totalPercentage+((double)(100*lactationLengthBean.getNumberOfPigs()))/count;
						weightedAvgOfLacationDays = weightedAvgOfLacationDays + (double)(lactationLengthBean.getNumberOfPigs()*lactationLengthBean.getLactationLength()/count);
						rowBuffer.append(lactationLengthBean.getPercentage());
						//rowBuffer.append(((double)(100*lactationLengthBean.getNumberOfPigs())/count) );
						returnRows.add(rowBuffer.toString()+"\n");
						totalSowCount = lactationLengthBean.getTotalPigCount();
					}
				}
			}
			averageLactationDays = totalLactationDays/numberOfRows;
			returnRows.add("\n");
			returnRows.add(averageLactationDays +","+count+","+totalPercentage);
			
			/*LactationLengthBean lactationLengthBean = dataMap.get("0");
			if(lactationLengthBean != null)
			{   
				rowBuffer = new StringBuffer();				
				rowBuffer.append(lactationLengthBean.getDayLabel() + seprater);
				rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
				rowBuffer.append(lactationLengthBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			else
			{
				rowBuffer = new StringBuffer();				
				rowBuffer.append("0 days" + seprater);
				rowBuffer.append(emptyBean.getNumberOfPigs() + seprater);
				rowBuffer.append(emptyBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			lactationLengthBean = dataMap.get("1");
			if(lactationLengthBean != null)
			{   
				rowBuffer = new StringBuffer();				
				rowBuffer.append(lactationLengthBean.getDayLabel() + seprater);
				rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
				rowBuffer.append(lactationLengthBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			else
			{
				rowBuffer = new StringBuffer();				
				rowBuffer.append("1 day" + seprater);
				rowBuffer.append(emptyBean.getNumberOfPigs() + seprater);
				rowBuffer.append(emptyBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			lactationLengthBean = dataMap.get("2");
			if(lactationLengthBean != null)
			{   
				rowBuffer = new StringBuffer();				
				rowBuffer.append(lactationLengthBean.getDayLabel() + seprater);
				rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
				rowBuffer.append(lactationLengthBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			else
			{
				rowBuffer = new StringBuffer();				
				rowBuffer.append("2 days" + seprater);
				rowBuffer.append(emptyBean.getNumberOfPigs() + seprater);
				rowBuffer.append(emptyBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			lactationLengthBean = dataMap.get("3");
			if(lactationLengthBean != null)
			{   
				rowBuffer = new StringBuffer();				
				rowBuffer.append(lactationLengthBean.getDayLabel() + seprater);
				rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
				rowBuffer.append(lactationLengthBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			else
			{
				rowBuffer = new StringBuffer();				
				rowBuffer.append("3 days" + seprater);
				rowBuffer.append(emptyBean.getNumberOfPigs() + seprater);
				rowBuffer.append(emptyBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			lactationLengthBean = dataMap.get("4-7");
			if(lactationLengthBean != null)
			{   
				rowBuffer = new StringBuffer();				
				rowBuffer.append(lactationLengthBean.getDayLabel() + seprater);
				rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
				rowBuffer.append(lactationLengthBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			else
			{
				rowBuffer = new StringBuffer();				
				rowBuffer.append("4-7 days" + seprater);
				rowBuffer.append(emptyBean.getNumberOfPigs() + seprater);
				rowBuffer.append(emptyBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			lactationLengthBean = dataMap.get("8-10");
			if(lactationLengthBean != null)
			{   
				rowBuffer = new StringBuffer();				
				rowBuffer.append(lactationLengthBean.getDayLabel() + seprater);
				rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
				rowBuffer.append(lactationLengthBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			else
			{
				rowBuffer = new StringBuffer();				
				rowBuffer.append("8-10 days" + seprater);
				rowBuffer.append(emptyBean.getNumberOfPigs() + seprater);
				rowBuffer.append(emptyBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			lactationLengthBean = dataMap.get("11-15");
			if(lactationLengthBean != null)
			{   
				rowBuffer = new StringBuffer();				
				rowBuffer.append(lactationLengthBean.getDayLabel() + seprater);
				rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
				rowBuffer.append(lactationLengthBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			else
			{
				rowBuffer = new StringBuffer();				
				rowBuffer.append("11-15 days" + seprater);
				rowBuffer.append(emptyBean.getNumberOfPigs() + seprater);
				rowBuffer.append(emptyBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			lactationLengthBean = dataMap.get("16-20");
			if(lactationLengthBean != null)
			{   
				rowBuffer = new StringBuffer();				
				rowBuffer.append(lactationLengthBean.getDayLabel() + seprater);
				rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
				rowBuffer.append(lactationLengthBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			else
			{
				rowBuffer = new StringBuffer();				
				rowBuffer.append("16-20 days" + seprater);
				rowBuffer.append(emptyBean.getNumberOfPigs() + seprater);
				rowBuffer.append(emptyBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			lactationLengthBean = dataMap.get(">21");
			if(lactationLengthBean != null)
			{   
				rowBuffer = new StringBuffer();				
				rowBuffer.append(lactationLengthBean.getDayLabel() + seprater);
				rowBuffer.append(lactationLengthBean.getNumberOfPigs() + seprater);
				rowBuffer.append(lactationLengthBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}
			else
			{
				rowBuffer = new StringBuffer();				
				rowBuffer.append("21+ days" + seprater);
				rowBuffer.append(emptyBean.getNumberOfPigs() + seprater);
				rowBuffer.append(emptyBean.getPercentage() );
				returnRows.add(rowBuffer.toString()+"\n");
			}*/
		
		return returnRows;
	}

}
