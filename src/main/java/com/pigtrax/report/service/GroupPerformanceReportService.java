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

	public List<String> getGroupPerformanceReportList(String selectedPremise, Date endDate, String status, Date startDate, Locale locale)  throws Exception{ 
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<String> returnRows = new ArrayList<String>();
		
//		Date inputEndDate  = DateUtil.convertToFormat(endDate, "dd/MM/yyyy");
//		Date inputStartDate  = DateUtil.addDays(inputEndDate, numberOfWeeks*-7);
		
		boolean isActive = status!=null&&status.equalsIgnoreCase("active")?true:false;
	    List<GroupPerformanceReportDataDto> resultList  = groupStatusReportDao.getPerformanceReportList(startDate, endDate, Integer.parseInt(selectedPremise),isActive);		
	
		StringBuffer rowBuffer = null;			
			
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {						
		   rowBuffer.append(groupRecord.getGroupId()+seprater);
		}					
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupEventForm.groupStartDateTime", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {
		   GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
		   rowBuffer.append(performanceAttribute.getStartDate()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.groupenddate", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndDate()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.startdatevariance", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartDaysVariance()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.enddatevariance", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndDaysVariance()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.starthd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.endhd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.pigdeaths", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPigDeaths()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.pigeuth", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPigsEuth()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.pigadj", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPigAdjust()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferin", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferIn()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferout", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferOut()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");

		//Net Transfer
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.nettransfer", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferNet()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();

		//TransfersNETPCT
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferNETPCT", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferNetPct()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B15
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.weansales", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getWeanSales()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		//B16 Feeder Sales
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedersales", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getFeederSales()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B17
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.premarketsales", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPreMarketSales()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B18
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.marketsales", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getMarketSales()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B19
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.startwt", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartWt()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");

		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.startwthdvary", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartWtHdVary()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B21
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.startwttotal", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getStartWtTotal()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.endwt", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndWt()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.endwthdvary", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndWtHdVary()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B24
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.endwttotal", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getEndWtTotal()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		//b25
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferinwthd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferInWtHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B26
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferinwttotal", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferInWtTotal()+seprater);
		}
		returnRows.add(rowBuffer.toString()+"\n");
		
/*		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferinhd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferInWtTotal()+seprater);
		}		

		returnRows.add(rowBuffer.toString()+"\n");
*/		//b27
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferoutwthd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferOutWtHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B28
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferoutwttotal", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferOutWtTotal()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		

		
		
		
		//b29=b27-b25
//		returnRows.add(rowBuffer.toString()+"\n");
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.nettransferwthd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getNetTransferWeightPerHead()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//b30=b28-b26
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.netrransferwt", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getNetTransferWeight()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B31
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferstartwttotal", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferStartWtTotal()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B32
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.transferendwttotal", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTransferEndWtTotal()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		//B33
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalgainwithouttransfer", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalGainWithoutTransfer()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalgainwithtransfer", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalGainWithTransfer()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalgainhdwithouttransfer", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalGainHDWithoutTransfer()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalgainhdtransfer", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalGainHDTransfer()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
/*		returnRows.add(rowBuffer.toString()+"\n");
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.nettransferhd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getNetTransferHead()+seprater);
		}		
*/

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
/*		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalgain", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalGainWt()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalwtgainwithouttransfer", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalGainWtPerTranfer()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalgainhd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getGainHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.gainhdwithouttransfer", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getGainHdPerTransfer()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
*/		
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.growthperformance", null, "", locale)+seprater);			
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.mortalitypct", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getMortalityPct()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.premktpct", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPreMarketPct()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.pigdays", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getPigDays()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.dof", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getDof()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.adgwithouttfr", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getAdgWithoutTFR()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");

		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.adgwithtfr", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getAdgWithTFR()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedperformance", null, "", locale)+seprater);			
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalfeedused", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalFeedUsed()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalfeedbudget", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalFeedBudget()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedbudgetvariance", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getBudgetVariance()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedbudgetvariancepct", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getBudgetVariancePct()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.adfi", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getAdfi()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedhd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getFeedHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.feedefficiency", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getFeedEfficiency()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.gainefficiency", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getGainEfficiency()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.costperformance", null, "", locale)+seprater);			
		returnRows.add(rowBuffer.toString()+"\n");
		
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.totalfeedcost", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTotalFeedCost()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcbudgeted", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcBudgeted()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcvariance", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcVariance()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfchd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcbudgetedhd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcBudgetedHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcvariance", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcVariance()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcvariancehd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcVarianceHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.tfcgain", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getTfcGain()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.mof", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getMof()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.mofhd", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getMofHd()+seprater);
		}		
		returnRows.add(rowBuffer.toString()+"\n");
		
		rowBuffer = new StringBuffer();
		rowBuffer.append(messageSource.getMessage("label.piginfo.groupperformancereport.mofpct", null, "", locale)+seprater);
		for (GroupPerformanceReportDataDto groupRecord : resultList) {		
			 GroupPerformanceAttribute performanceAttribute = groupRecord.getPerformanceAttribute();
			 rowBuffer.append(performanceAttribute.getMofPct()+seprater);
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
