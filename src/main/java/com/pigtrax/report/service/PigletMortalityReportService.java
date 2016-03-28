package com.pigtrax.report.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.PigletMortalityReportBean;
import com.pigtrax.report.dao.PigletMortalityReportDao;

@Repository
public class PigletMortalityReportService {

	@Autowired
	PigletMortalityReportDao pigletMortalityReportDao;

	private static final String seprater = ",";

	public List<String> getPigletMortalityList(String premise, Integer premiseId, Date startDate, Date endDate) { 
		List<PigletMortalityReportBean> pigletMortalityList = pigletMortalityReportDao.getPigletMortalityList(premiseId, startDate, endDate);

		List<PigletMortalityReportBean> arrangedList = reArrange(pigletMortalityList);
		
		
		ArrayList<String> returnRows = new ArrayList<String>();

		if (arrangedList != null && arrangedList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Premise, Barn, Room, StartHd, Inven, Lactation Days, 0d, 1d, 2d, 3d, 4-7d, 8-10d, 11-15d, 16-20d, 21+d, Number of Deaths, Mortality, End Date");
			returnRows.add("\n");
			for (PigletMortalityReportBean pigletMortalityReportBean : arrangedList) {
				rowBuffer = new StringBuffer();
				    
					Integer startHead = pigletMortalityReportDao.getStartHead(startDate, pigletMortalityReportBean.getRoomPkId());
				
					rowBuffer.append(premise + seprater);
					rowBuffer.append(pigletMortalityReportBean.getBarnId() + seprater);
					rowBuffer.append(pigletMortalityReportBean.getRoomId() + seprater);
					rowBuffer.append(startHead + seprater);
					rowBuffer.append(pigletMortalityReportBean.getInventoryCount() + seprater);
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
							&& pigletMortalityReportBean.getLactationDays() != null && pigletMortalityReportBean.getLactationDays() > 0)
					{
						percentageMortality = (double)(pigletMortalityReportBean.getNumberOfDeaths()*100)/pigletMortalityReportBean.getLactationDays();
					}
					
					rowBuffer.append(pigletMortalityReportBean.getNumberOfDeaths() + seprater);
					rowBuffer.append(percentageMortality + seprater);
					rowBuffer.append(" ");
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
				
				
				if(mortalityBean.getFarrowDate() != null && mortalityBean.getFarrowDate() != null)
				{
					farrowDate = new DateTime(mortalityBean.getFarrowDate());
					deathDate = new DateTime(mortalityBean.getDeathDate());
					duration = Days.daysBetween(farrowDate, deathDate).getDays();
				}
				
				if(duration == 0)
				{
					bean.setCount1(deathCnt+mortalityBean.getNumberOfDeaths() );
				}
				else if(duration == 1)
				{
					bean.setCount2(mortalityBean.getNumberOfDeaths() );
				}
				else if(duration == 2)
				{
					bean.setCount3(mortalityBean.getNumberOfDeaths() );
				}
				else if(duration == 3)
				{
					bean.setCount4(mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 4 && duration <= 7)
				{
					bean.setCount5(mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 8 && duration <= 10)
				{
					bean.setCount6(mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 11 && duration <= 15)
				{
					bean.setCount7(mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 16 && duration <= 20)
				{
					bean.setCount8(mortalityBean.getNumberOfDeaths() );
				}
				else if(duration >= 21)
				{
					bean.setCount9(mortalityBean.getNumberOfDeaths() );
				}
				deathCnt += mortalityBean.getNumberOfDeaths();
				bean.setNumberOfDeaths(deathCnt);
			}
		}
		return mortalityList;
	}
	
	
	
}
