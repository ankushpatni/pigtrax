package com.pigtrax.report.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.report.bean.SaleReportBean;
import com.pigtrax.report.dao.SaleReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class SaleReportService {
	
	@Autowired
	SaleReportDao saleReportDao;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	MessageSource messageSource;

	private static final String seprater = ",";
	
	public List<String> getSaleList(String premise, int premiseId, int groupId, java.util.Date startDate, java.util.Date endDate, int barnId, String ticketNumber, String language, int pigId, Locale locale)
	{
		
		Map<Integer, String> saleTypesMap = refDataCache.getSaleTypesMap(language);
		
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
		
		List<SaleReportBean> saleReportBeanList = saleReportDao.getSaleList(premiseId,barnId, groupId, startDateSql, endDateSql, ticketNumber, pigId);
		
		ArrayList<String> returnRows = new ArrayList<String>();
		String dateStr = "";
		if (saleReportBeanList != null && saleReportBeanList.size() > 0) {

			StringBuffer rowBuffer = null;
			
			returnRows.add(messageSource.getMessage("label.piginfo.removalExceptSales.premiseId", null, "", locale)+","+messageSource.getMessage("label.premise.barn", null, "", locale)+","
					+messageSource.getMessage("label.barn.room", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","+messageSource.getMessage("label.piginfo.removalExceptSales.salesDateTime", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.removalEventform.salesType", null, "", locale)+","+messageSource.getMessage("label.groupEventDetail.numberOfPigs", null, "", locale)
					+messageSource.getMessage("label.piginfo.removalExceptSales.soldTo", null, "", locale)+","+messageSource.getMessage("label.reports.salesreport.totalweight", null, "", locale)+","
					+messageSource.getMessage("label.reports.salesreport.weightperpig", null, "", locale)+","+messageSource.getMessage("label.piginfo.feedEventForm.ticketNumber", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.removalExceptSales.invoiceId", null, "", locale)+","+messageSource.getMessage("label.piginfo.removalExceptSales.revenueUsd", null, "", locale)+","
					+messageSource.getMessage("label.transportJourney.transportTruckId", null, "", locale)+","+messageSource.getMessage("label.transportJourney.transportTrailerId", null, "", locale)+","
					+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale)+","+"\n");
			
			
			try
			{
			for (SaleReportBean saleReportBean : saleReportBeanList) {
				rowBuffer = new StringBuffer();
				rowBuffer.append(saleReportBean.getPremisesId()).append(seprater);
				
				if(saleReportBean.getBarnID() != null)
					rowBuffer.append(saleReportBean.getBarnID()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getRoomID() != null)
					rowBuffer.append(saleReportBean.getRoomID()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getPigID() != null)
					rowBuffer.append(saleReportBean.getPigID()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getGroupID() != null)
					rowBuffer.append(saleReportBean.getGroupID()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getSalesEventDate() != null)
					rowBuffer.append(DateUtil.convertToFormatString(saleReportBean.getSalesEventDate(),"dd/MM/yyyy")).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getSalesType() != null)
				{
					StringBuffer salesTypeStr = new StringBuffer();
					Integer [] salesType = setSalesTypesAsString(saleReportBean.getSalesType());
						for(Integer i : salesType)
						{
							salesTypeStr.append(saleTypesMap.get(i) +":");
						}					
					rowBuffer.append(salesTypeStr).append(seprater);
				}
				else
					rowBuffer.append(seprater);
				
				
				if(saleReportBean.getNumberOfPigsSold() != null)
					rowBuffer.append(saleReportBean.getNumberOfPigsSold()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getDestination() != null)
					rowBuffer.append(saleReportBean.getDestination()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getTotalWeight() != null)
					rowBuffer.append(saleReportBean.getTotalWeight()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				rowBuffer.append(saleReportBean.getWeightPerPig()).append(seprater);
				
				if(saleReportBean.getTicketNumber() != null)
					rowBuffer.append(saleReportBean.getTicketNumber()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getInvoiceNumber() != null)
					rowBuffer.append(saleReportBean.getInvoiceNumber()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getRevenue() != 0)
					rowBuffer.append(saleReportBean.getRevenue()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getTruck() != null)
					rowBuffer.append(saleReportBean.getTruck()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getTrailer() != null)
					rowBuffer.append(saleReportBean.getTrailer()).append(seprater);
				else
					rowBuffer.append(seprater);
				
				if(saleReportBean.getRemarks() != null)
					rowBuffer.append(saleReportBean.getRemarks()).append(seprater);
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
	
	public Integer[] setSalesTypesAsString(String salesTypesAsString) {
		
		Integer[] salesTypes = null;
		if(salesTypesAsString != null)
		{
			String[] types = salesTypesAsString.split(",");
			if(types != null && 0<types.length)
				salesTypes = new Integer[types.length];
			int i = 0;
			for(String s : types)
			{
				try{
					salesTypes[i] = Integer.parseInt(s);
				}catch(Exception e)
				{
					
				}
				i++;
			}
		}
		return salesTypes;
		
	}
}
