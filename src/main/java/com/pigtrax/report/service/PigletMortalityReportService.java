package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.PigletMortalityReportBean;
import com.pigtrax.report.dao.PigletMortalityReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class PigletMortalityReportService {

	@Autowired
	PigletMortalityReportDao pigletMortalityReportDao;
	
	@Autowired
	MessageSource messageSource;

	private static final String seprater = ",";

	public List<String> getPigletMortalityList(String premise, Integer premiseId, Date startDate, Date endDate, Locale locale) { 
		List<PigletMortalityReportBean> pigletMortalityList = pigletMortalityReportDao.getPigletMortalityList(premiseId, startDate, endDate);

		List<PigletMortalityReportBean> arrangedList = reArrange(pigletMortalityList);
		
		
		ArrayList<String> returnRows = new ArrayList<String>();

		if (arrangedList != null && arrangedList.size() > 0) {

			StringBuffer rowBuffer = null;
			
			returnRows.add(messageSource.getMessage("label.piginfo.removalExceptSales.premiseId", null, "", locale)+","+messageSource.getMessage("label.premise.barn", null, "", locale)+","
					+messageSource.getMessage("label.barn.room", null, "", locale)+","+messageSource.getMessage("label.reports.pigletmortality.starthead", null, "", locale)+","
					+messageSource.getMessage("label.reports.pigletmortality.invencount", null, "", locale)+","+messageSource.getMessage("label.reports.lactation.lactationdays", null, "", locale)+","
					+messageSource.getMessage("label.reports.pigletmortality.0d", null, "", locale)+","+messageSource.getMessage("label.reports.pigletmortality.1d", null, "", locale)+","
					+messageSource.getMessage("label.reports.pigletmortality.2d", null, "", locale)+","+messageSource.getMessage("label.reports.pigletmortality.3d", null, "", locale)+","
					+messageSource.getMessage("label.reports.pigletmortality.4d", null, "", locale)+","+messageSource.getMessage("label.reports.pigletmortality.5d", null, "", locale)+","
					+messageSource.getMessage("label.reports.pigletmortality.6d", null, "", locale)+","+messageSource.getMessage("label.reports.pigletmortality.7d", null, "", locale)+","
					+messageSource.getMessage("label.reports.pigletmortality.8d", null, "", locale)+","//+messageSource.getMessage("label.reports.pigletmortality.9d", null, "", locale)+","
					+messageSource.getMessage("label.reports.pigletmortality.deathnum", null, "", locale)+","+messageSource.getMessage("label.reports.pigletmortality.mortality", null, "", locale)+","
			//		+messageSource.getMessage("label.employee.functionEndDate", null, "", locale)
					+"\n");
			
			
			for (PigletMortalityReportBean pigletMortalityReportBean : arrangedList) {
				rowBuffer = new StringBuffer();
				    
					Integer startHead = pigletMortalityReportDao.getStartHead(startDate, endDate,pigletMortalityReportBean.getRoomPkId(),premiseId);
				
					rowBuffer.append(premise + seprater);
					rowBuffer.append(pigletMortalityReportBean.getBarnId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getRoomId() + seprater);
					rowBuffer.append(startHead + seprater);
					rowBuffer.append((startHead-pigletMortalityReportBean.getNumberOfDeaths() ) + seprater);
					rowBuffer.append(pigletMortalityReportBean.getLactationDays() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getCount1() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getCount2() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getCount3() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getCount4() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getCount5() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getCount6() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getCount7() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getCount8() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getCount9() + seprater);
					
					double percentageMortality = 0;
					if(pigletMortalityReportBean.getNumberOfDeaths() != null && pigletMortalityReportBean.getNumberOfDeaths() > 0 
							&& startHead != null && startHead  > 0)
					{
						percentageMortality = (double)(pigletMortalityReportBean.getNumberOfDeaths()*100)/startHead ;
					}
					
					rowBuffer.append(pigletMortalityReportBean.getNumberOfDeaths() + seprater);
					rowBuffer.append(percentageMortality + seprater);
					
					/*try {
						String dateStr = DateUtil.convertToFormatString(pigletMortalityReportBean.getWeanDate(), "dd/MM/yyyy");
						if(dateStr != null)
							rowBuffer.append(dateStr);
						else
							rowBuffer.append(" ");
					} catch (ParseException e) {
						rowBuffer.append(" ");
					}*/
					
					returnRows.add(rowBuffer.toString()+"\n");
			}
		}
		return returnRows;
	}
	
	private List<PigletMortalityReportBean> reArrange(List<PigletMortalityReportBean> pigletMortalityList)
	{
		List<PigletMortalityReportBean> mortalityList = new ArrayList<PigletMortalityReportBean>();
		PigletMortalityReportBean bean = null;
		int deathCnt = 0;
		int duration = 0;
		if(pigletMortalityList != null)
		{
			for(PigletMortalityReportBean mortalityBean : pigletMortalityList)
			{
				DateTime farrowDate = null;
				DateTime deathDate = null;
				
				
				if(bean == null || !bean.getRoomId().equals(mortalityBean.getRoomId()))
				{
					deathCnt = 0;
					duration = 0;
					bean = new PigletMortalityReportBean();
					mortalityList.add(bean);
				}
				bean.setBarnId(mortalityBean.getBarnId());
				bean.setRoomId(mortalityBean.getRoomId());
				bean.setInventoryCount(mortalityBean.getInventoryCount());
				bean.setLactationDays(mortalityBean.getLactationDays());
				
				
				if(mortalityBean.getFarrowDate() != null && mortalityBean.getDeathDate() != null)
				{
					farrowDate = new DateTime(mortalityBean.getFarrowDate());
					deathDate = new DateTime(mortalityBean.getDeathDate());
					duration = Days.daysBetween(farrowDate, deathDate).getDays();
				}
				
				if(duration == 0)
				{
					//bean.setCount1(deathCnt+mortalityBean.getNumberOfDeaths() );
					//bean.setCount1(mortalityBean.getNumberOfDeaths() );
					if(bean.getCount1() == null)
						bean.setCount1(mortalityBean.getNumberOfDeaths() );
					else
						bean.setCount1(bean.getCount1() + mortalityBean.getNumberOfDeaths() );
				}
				else if(duration == 1)
				{
					if(bean.getCount2() == null)
						bean.setCount2(mortalityBean.getNumberOfDeaths() );
					else
						bean.setCount2(bean.getCount2() + mortalityBean.getNumberOfDeaths() );
				}
				else if(duration == 2)
				{
				//	bean.setCount3(mortalityBean.getNumberOfDeaths() );
					if(bean.getCount3() == null)
						bean.setCount3(mortalityBean.getNumberOfDeaths() );
					else
						bean.setCount3(bean.getCount3() + mortalityBean.getNumberOfDeaths() );
				}
				else if(duration == 3)
				{
				//	bean.setCount4(mortalityBean.getNumberOfDeaths() );
					if(bean.getCount4() == null)
						bean.setCount4(mortalityBean.getNumberOfDeaths() );
					else
						bean.setCount4(bean.getCount4() + mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 4 && duration <= 7)
				{
				//	bean.setCount5(mortalityBean.getNumberOfDeaths() );
					if(bean.getCount5() == null)
						bean.setCount5(mortalityBean.getNumberOfDeaths() );
					else
						bean.setCount5(bean.getCount5() + mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 8 && duration <= 10)
				{
					//bean.setCount6(mortalityBean.getNumberOfDeaths() );
					if(bean.getCount6() == null)
						bean.setCount6(mortalityBean.getNumberOfDeaths() );
					else
						bean.setCount6(bean.getCount6() + mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 11 && duration <= 15)
				{
					//bean.setCount7(mortalityBean.getNumberOfDeaths() );
					if(bean.getCount7() == null)
						bean.setCount7(mortalityBean.getNumberOfDeaths() );
					else
						bean.setCount7(bean.getCount7() + mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 16 && duration <= 20)
				{
				//	bean.setCount8(mortalityBean.getNumberOfDeaths() );
					if(bean.getCount8() == null)
						bean.setCount8(mortalityBean.getNumberOfDeaths() );
					else
						bean.setCount8(bean.getCount8() + mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 21)
				{
					//bean.setCount9(mortalityBean.getNumberOfDeaths() );
					if(bean.getCount9() == null)
						bean.setCount9(mortalityBean.getNumberOfDeaths() );
					else
						bean.setCount9(bean.getCount9() + mortalityBean.getNumberOfDeaths() );
				}
				deathCnt += mortalityBean.getNumberOfDeaths();
				bean.setNumberOfDeaths(deathCnt);
			}
		}
		return mortalityList;
	}
	
	
	
}
