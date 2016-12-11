package com.pigtrax.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.jobs.dto.GroupPerformanceAttribute;
import com.pigtrax.jobs.dto.GroupPerformanceReportDataDto;
import com.pigtrax.jobs.dto.GroupStatusReportDataDto;
import com.pigtrax.master.dao.interfaces.BarnDao;
import com.pigtrax.master.dao.interfaces.PremisesDao;
import com.pigtrax.master.dao.interfaces.RoomDao;
import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.dto.Room;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.report.dao.GroupStatusReportDao;
import com.pigtrax.util.DateUtil;

@Repository
public class GroupPerformanceReportService {

	@Autowired
	GroupStatusReportDao groupStatusReportDao;
	
	
	private static final Logger logger = Logger.getLogger(GroupPerformanceReportService.class);
	
	private static final String seprater = ",";
	
	@Autowired
	MessageSource messageSource;

	public List<String> getGroupPerformanceReportList(String selectedPremise, String endDate, String status, Integer numberOfWeeks, Locale locale)  throws Exception{ 
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<String> returnRows = new ArrayList<String>();
		
		Date inputEndDate  = DateUtil.convertToFormat(endDate, "dd/MM/yyyy");
		Date inputStartDate  = DateUtil.addDays(inputEndDate, numberOfWeeks*-7);
		
		boolean isActive = status!=null&&status.equalsIgnoreCase("active")?true:false;
	    List<GroupPerformanceReportDataDto> resultList  = groupStatusReportDao.getPerformanceReportList(inputStartDate, inputEndDate, Integer.parseInt(selectedPremise),isActive);		
	
		StringBuffer rowBuffer = null;			
			
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {						
		   rowBuffer.append(groupRecord.getGroupId()+seprater+seprater);
		}					
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupEventForm.groupStartDateTime", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {
		   GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
		   rowBuffer.append(performanceAttribute.getStartDate()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.groupenddate", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndDate()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.startdatevariance", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartDaysVariance()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.enddatevariance", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndDaysVariance()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.starthd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.endhd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.pigdeaths", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPigDeaths()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.pigeuth", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPigsEuth()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.pigadj", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPigAdjust()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferin", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferIn()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferout", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferOut()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.nettransfer", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferNet()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.weansales", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getWeanSales()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedersales", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getFeederSales()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.premarketsales", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPreMarketSales()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.marketsales", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getFeederSales()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.startwttotal", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartWtTotal()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.startwt", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartWt()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.startwthdvary", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartWtHdVary()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.endwttotal", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndWtTotal()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.endwt", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndWt()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.endwthdvary", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndWtHdVary()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferinwttotal", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferInWtTotal()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferinwthd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferInWtHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferoutwttotal", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferOutWtTotal()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferoutwthd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferOutWtHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalgain", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalGainWt()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalwtgainwithouttransfer", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalGainWtPerTranfer()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalgainhd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getGainHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.gainhdwithouttransfer", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getGainHdPerTransfer()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.growthperformance", null, "", locale)+seprater+seprater);			
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.mortalitypct", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getMortalityPct()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.premktpct", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPreMarketPct()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.pigdays", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPigDays()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.dof", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getDof()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.adg", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getAdg()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedperformance", null, "", locale)+seprater+seprater);			
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalfeedused", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalFeedUsed()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalfeedbudget", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalFeedBudget()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedbudgetvariance", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getBudgetVariance()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.adfi", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getAdfi()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedhd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getFeedHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedefficiency", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getFeedEfficiency()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.gainefficiency", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getGainEfficiency()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.costperformance", null, "", locale)+seprater+seprater);			
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalfeedcost", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalFeedCost()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcbudgeted", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcBudgeted()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcvariance", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcVariance()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfchd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcbudgetedhd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcBudgetedHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcvariance", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcVariance()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcvariancehd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcVarianceHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcgain", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcGain()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.mof", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getMof()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.mofhd", null, "", locale)+seprater+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getMofHd()+seprater+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		return returnRows;
	}

	private Map<String, Object> getDateRange(Date startDate,Date endDate){
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("EventDateStart", startDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		mp.put("WK", week);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		Date servDateEnd = cal.getTime();
		if(!DateUtil.isDateAfter(endDate,servDateEnd)){
			mp.put("EventDateEnd", endDate);//set the end date as the ServDateEND
		} else{
			mp.put("EventDateEnd", servDateEnd);//add 6 days to start date and set as ServDateEND
		}
		
		return mp;	
	}	
	
}
