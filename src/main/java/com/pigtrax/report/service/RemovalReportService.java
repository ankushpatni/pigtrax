package com.pigtrax.report.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.bean.RemovalReportBean;
import com.pigtrax.report.dao.RemovalReportDao;

@Repository
public class RemovalReportService {
	
	@Autowired
	RemovalReportDao removalReportDao;

	private static final String seprater = ",";
	
	public List<String> getRemovalList(String premise, int premiseId, int pigId,int groupId, java.util.Date startDate, java.util.Date endDate) {
		Date startDateSql = null;
		Date endDateSql = null;
		if(null != startDate)
		{
			startDateSql = new Date(startDate.getTime());
		}
		
		if(null != endDate)
		{
			endDateSql = new Date(endDate.getTime());
		}
		List<RemovalReportBean> removalReportBeanList = removalReportDao.getRemovalList(premiseId, pigId, groupId, startDateSql, endDateSql);
		
		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (removalReportBeanList != null && removalReportBeanList.size() > 0) {

			StringBuffer rowBuffer = null;
			returnRows
					.add("Premise,BarnID,RoomID,PenID,Group ID,PigID,Pstatus,RemovalDate,Number of pigs removed,AveWt,Parity,AveDOF,AveWks,	Mortality Reason");
			returnRows.add("\n");
			for (RemovalReportBean removalReportBean : removalReportBeanList) {
				rowBuffer = new StringBuffer();
				rowBuffer.append(removalReportBean.getPremise()).append(seprater);
				
				if(removalReportBean.getBarnID() != null)
					rowBuffer.append(removalReportBean.getBarnID()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(removalReportBean.getRoomID() != null)
					rowBuffer.append(removalReportBean.getRoomID()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(removalReportBean.getPenID() != null)
					rowBuffer.append(removalReportBean.getPenID()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(removalReportBean.getGroupID() != null)
					rowBuffer.append(removalReportBean.getGroupID()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(removalReportBean.getPigID() != null)
					rowBuffer.append(removalReportBean.getPigID()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(removalReportBean.getpStatus() != null)
					rowBuffer.append(removalReportBean.getpStatus()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				rowBuffer.append(removalReportBean.getRemovalDate()).append(seprater);
				rowBuffer.append(removalReportBean.getNumberPigsRemoved()).append(seprater);
				rowBuffer.append(removalReportBean.getAveWeight()).append(seprater);
				rowBuffer.append(removalReportBean.getParity()).append(seprater);
				rowBuffer.append(removalReportBean.getAveDOF()).append(seprater);
				rowBuffer.append(removalReportBean.getAveWOF()).append(seprater);
				rowBuffer.append(removalReportBean.getMortalityReason()).append(seprater);
				returnRows.add(rowBuffer.toString()+"\n");
				
			}
		}
		
		return returnRows;

	}

}
