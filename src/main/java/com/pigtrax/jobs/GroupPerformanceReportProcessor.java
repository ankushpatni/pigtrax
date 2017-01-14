package com.pigtrax.jobs;

import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.jobs.dao.interfaces.GroupStatusReportDataDao;
import com.pigtrax.jobs.dto.GroupPerformanceAttribute;
import com.pigtrax.jobs.dto.GroupPerformanceReportDataDto;
import com.pigtrax.master.dao.interfaces.PremisesDao;
import com.pigtrax.master.dto.Premises;
import com.pigtrax.pigevents.beans.CompanyTarget;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.CompanyTargetDao;
import com.pigtrax.pigevents.dao.interfaces.FeedEventDetailDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventRoomDao;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.SalesEventDetailsDao;
import com.pigtrax.usermanagement.enums.RemovalEventType;
import com.pigtrax.util.DateUtil;


@Component("groupPerformanceProcessor")
public class GroupPerformanceReportProcessor{
	
	@Autowired
	PremisesDao premisesDao;
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Autowired
	GroupEventDetailsDao groupEventDetailsDao;
	
	@Autowired
	GroupEventRoomDao groupEventRoomDao;
	
	@Autowired
	RemovalEventExceptSalesDetailsDao removalEventDao;
	
	@Autowired
	SalesEventDetailsDao saleEventDetailsDao;
	
	@Autowired
	CompanyTargetDao companyTargetDao;
	
	@Autowired
	GroupStatusReportDataDao reportDataDao;	
	
	
	@Autowired
	FeedEventDetailDao feedDetailDao;
	
	public void process() throws Exception
	{
		List<Premises> premiseList = premisesDao.getAllPremises();
		if(premiseList != null && 0 < premiseList.size())
		{
			for(Premises premise : premiseList)
			{	
								
				List<GroupEvent> groupList = groupEventDao.getAllGroups(premise.getId());   
				if(groupList != null && 0 < groupList.size())
				{
					for(GroupEvent group : groupList)
					{
					
						//if(group.getId() == 141)
						//{
						
						try{						
						 GroupPerformanceReportDataDto performanceDto = new GroupPerformanceReportDataDto();
						 performanceDto.setActive(group.isActive());
						 performanceDto.setGroupEventId(group.getId());
						 performanceDto.setGroupId(group.getGroupId());
						 performanceDto.setPremise(premise.getName());
						 performanceDto.setPremiseId(premise.getId());
						 performanceDto.setGroupStartDate(group.getGroupStartDateTime());
						 performanceDto.setGroupEndDate(group.getGroupCloseDateTime());
						
						 GroupPerformanceAttribute performanceAttribute = new GroupPerformanceAttribute();
						 Date averageStartDate = groupEventDetailsDao.getAverageStartDate(group.getId());
						 performanceAttribute.setStartDate(averageStartDate != null ? DateUtil.convertToFormatString(averageStartDate, "dd/MM/yyyy"):"");
						 
						 Date averageEndDate = groupEventDetailsDao.getAverageEndDate(group.getId());
						 performanceAttribute.setEndDate(averageEndDate != null ? DateUtil.convertToFormatString(averageEndDate, "dd/MM/yyyy"):"");
						 
						 performanceAttribute.setStartDaysVariance(groupEventDetailsDao.getStartDateVariance(group.getId()));
						 performanceAttribute.setEndDaysVariance(groupEventDetailsDao.getEndDateVariance(group.getId()));
						 
						 Map<String, Object> startWtAndHeadMap = groupEventDao.getStartWtAndHead(group.getId());
						 performanceAttribute.setStartWtTotal((Double)startWtAndHeadMap.get("StartWt"));
						 Long startHead  = 0L;
						 startHead = (Long)startWtAndHeadMap.get("StartHd");
						 performanceAttribute.setStartHd(startHead.intValue());
						 
						 performanceAttribute.setStartWtHdVary(groupEventDao.getMaxStartWtPerHeadVariance(group.getId()));
						 
						 
						 performanceAttribute.setEndHd(saleEventDetailsDao.getSalesCount(DateUtil.addDays(DateUtil.getToday(),1), group.getId()));
						 
						 performanceAttribute.setEndWtTotal(saleEventDetailsDao.getSalesWt(DateUtil.addDays(DateUtil.getToday(),1), group.getId()));
						 
						 performanceAttribute.setEndWt(performanceAttribute.getEndWtTotal()/performanceAttribute.getEndHd());
						 
						 performanceAttribute.setEndWtHdVary(saleEventDetailsDao.getMaxSaleWtPerHeadVariance(group.getId()));
						 
						 performanceAttribute.setPigDeaths(removalEventDao.getDeadsCount(DateUtil.addDays(DateUtil.getToday(),1), group.getId()));
						 
						 performanceAttribute.setPigsEuth(removalEventDao.getDeadsCount(DateUtil.addDays(DateUtil.getToday(),1), group.getId(), 7)); //7 - is for Euthanized						 
						 
						 Integer adjCount = removalEventDao.getCount(DateUtil.addDays(DateUtil.getToday(),1), group.getId(), RemovalEventType.AdjustInventory.getTypeCode());
						 
						 performanceAttribute.setPigAdjust(adjCount != null ? -1*adjCount :0);
						 
						 Map<String, Object> transferDetails = groupEventDetailsDao.getTransferInOutCountAndWt(group.getId());
						 if(transferDetails != null)
						 {
							 performanceAttribute.setTransferIn(transferDetails.get("transferInCnt") != null ? (Integer)transferDetails.get("transferInCnt"): 0);
							 performanceAttribute.setTransferInWtTotal(transferDetails.get("transferInWt") != null ? (Double)transferDetails.get("transferInWt"): 0);
							 performanceAttribute.setTransferInWtHd(performanceAttribute.getTransferInWtTotal()/performanceAttribute.getTransferIn());
							 performanceAttribute.setTransferOut(transferDetails.get("transferOutCnt") != null ? (Integer)transferDetails.get("transferOutCnt"): 0);
							 performanceAttribute.setTransferOutWtTotal(transferDetails.get("transferOutWt") != null ? (Double)transferDetails.get("transferOutWt"): 0);
							 performanceAttribute.setTransferNet(performanceAttribute.getTransferIn()  - performanceAttribute.getTransferOut());		
							 performanceAttribute.setTransferNetPct(performanceAttribute.getTransferNet() * 100.00/ performanceAttribute.getStartHd()); 
							 performanceAttribute.setTransferOutWtHd(performanceAttribute.getTransferOutWtTotal()/performanceAttribute.getTransferOut());
							 Double netTransferWeight = performanceAttribute.getTransferInWtTotal() - performanceAttribute.getTransferOutWtTotal();
							 performanceAttribute.setNetTransferWeight(netTransferWeight);
							 Integer netTransferHead = performanceAttribute.getTransferIn()-performanceAttribute.getTransferOut();
							 performanceAttribute.setNetTransferHead(netTransferHead);
							 Double netTransferWtPerHead = Math.round(performanceAttribute.getNetTransferWeight()*100.0)/(performanceAttribute.getNetTransferHead()*100.0);
							 performanceAttribute.setNetTransferWeightPerHead(netTransferWtPerHead);
							 
							 
						 }
						 
						 performanceAttribute.setWeanSales(saleEventDetailsDao.getSalesCount(DateUtil.addDays(DateUtil.getToday(), 1), group.getId(), 4)); //Weaner sales count
						 performanceAttribute.setPreMarketSales(saleEventDetailsDao.getSalesCount(DateUtil.addDays(DateUtil.getToday(), 1), group.getId(), 2)); //Premarket sales count
						 performanceAttribute.setFeederSales(saleEventDetailsDao.getSalesCount(DateUtil.addDays(DateUtil.getToday(), 1), group.getId(), 5)); //Feeder sales count
						 performanceAttribute.setMarketSales(saleEventDetailsDao.getSalesCount(DateUtil.addDays(DateUtil.getToday(), 1), group.getId(), 3)); //Market sales count
						 
						 performanceAttribute.setStartWt(performanceAttribute.getStartWtTotal()/performanceAttribute.getStartHd());
						 performanceAttribute.setStartWt(performanceAttribute.getStartWtTotal()/performanceAttribute.getStartHd());
						 
						 performanceAttribute.setTotalGainWt(performanceAttribute.getEndWtTotal() - performanceAttribute.getStartWtTotal());
						 
						 performanceAttribute.setTotalGainWtPerTranfer(performanceAttribute.getEndWtTotal()+performanceAttribute.getTransferOutWtTotal()-performanceAttribute.getStartWtTotal()-performanceAttribute.getTransferInWtTotal());
						 
						 performanceAttribute.setGainHd(performanceAttribute.getTotalGainWt()/(performanceAttribute.getWeanSales()+performanceAttribute.getPreMarketSales()+performanceAttribute.getFeederSales()+performanceAttribute.getMarketSales()));
						 
						 Double gainHdPerTransfer = Math.round(performanceAttribute.getTotalGainWtPerTranfer()*100.0)/((performanceAttribute.getWeanSales()+performanceAttribute.getPreMarketSales()+performanceAttribute.getFeederSales()+performanceAttribute.getMarketSales()+performanceAttribute.getTransferOut())*100.0);
						 performanceAttribute.setGainHdPerTransfer(gainHdPerTransfer);
						 
						 Double mortalityPct = Math.round(performanceAttribute.getPigDeaths()*100.0)/((performanceAttribute.getStartHd()+performanceAttribute.getTransferIn())*100.0);
						 performanceAttribute.setMortalityPct(((mortalityPct*100)*100.0)/100.0);
						 Double preMarketPct = Math.round(performanceAttribute.getPreMarketSales()*100.0)/((performanceAttribute.getStartHd()+performanceAttribute.getTransferIn())*100.0);
						 performanceAttribute.setPreMarketPct(((preMarketPct*100)*100.0)/100.0);
						 
						 performanceAttribute.setPigDays(groupEventDetailsDao.calculatePigDays(group.getId()));
						 
						 //DOF to calculate
//						 performanceAttribute.setDof(groupEventDetailsDao.calculateDaysOnFeed(group.getId()));
						 performanceAttribute.setDof(performanceAttribute.getPigDays()/(performanceAttribute.getEndHd()+performanceAttribute.getTransferOut()));
						 
						 
						 performanceAttribute.setAdg(performanceAttribute.getTotalGainWt()/(performanceAttribute.getEndHd())/performanceAttribute.getPigDays());
						 
						 performanceAttribute.setTotalFeedUsed(feedDetailDao.getTotalFeedUsed(group.getId()));
						 performanceAttribute.setTotalFeedBudget(feedDetailDao.getTotalFeedBudgeted(group.getId()));
						 
						 performanceAttribute.setBudgetVariance(performanceAttribute.getTotalFeedUsed() - performanceAttribute.getTotalFeedBudget());
						 performanceAttribute.setBudgetVariancePct(Math.round(performanceAttribute.getBudgetVariance()*100.0)*100/(performanceAttribute.getTotalFeedBudget()*100.0));
						 
						 //Feed Performance
						 
						 performanceAttribute.setAdfi(Math.round(performanceAttribute.getTotalFeedUsed()*100.0)/(performanceAttribute.getPigDays()*100.0));
						 performanceAttribute.setFeedHd(Math.round(performanceAttribute.getTotalFeedUsed()*100.0)/((performanceAttribute.getEndHd()+performanceAttribute.getTransferOut())*100.0));
						 performanceAttribute.setFeedEfficiency(Math.round(performanceAttribute.getTotalFeedUsed()*100.0)/(performanceAttribute.getTotalGainWtPerTranfer()*100.0));
						 performanceAttribute.setGainEfficiency((performanceAttribute.getTotalGainWtPerTranfer()*100.0)/Math.round(performanceAttribute.getTotalFeedUsed()*100.0));
						 
						 //Feed Cost performance						 
						 performanceAttribute.setTotalFeedCost(feedDetailDao.getTotalFeedCost(group.getId()));
						 performanceAttribute.setTfcBudgeted(feedDetailDao.getTotalFeedBudgetedCost(group.getId())); 
						 performanceAttribute.setTfcVariance(performanceAttribute.getTotalFeedCost() - performanceAttribute.getTfcBudgeted());
						 performanceAttribute.setTfcHd(Math.round(performanceAttribute.getTotalFeedCost()*100.0)/(performanceAttribute.getEndHd()*100.0));
						 performanceAttribute.setTfcBudgetedHd(Math.round(performanceAttribute.getTfcBudgeted()*100.0)/(performanceAttribute.getEndHd()*100.0));
						 performanceAttribute.setTfcVarianceHd(performanceAttribute.getTfcHd() - performanceAttribute.getTfcBudgetedHd());
						 performanceAttribute.setTfcGain(Math.round(performanceAttribute.getTotalFeedCost()*100.0)/(performanceAttribute.getTotalGainWtPerTranfer()*100.0));
						 
						 performanceAttribute.setMof(saleEventDetailsDao.getSalesRevenue(group.getId()) - performanceAttribute.getTotalFeedCost());
						 performanceAttribute.setMofHd(Math.round(performanceAttribute.getMof()*100.0)/(performanceAttribute.getEndHd()*100.0));
						 
						 Double salesRevenue = saleEventDetailsDao.getSalesRevenue(group.getId());
						 performanceAttribute.setMofPct(Math.round(performanceAttribute.getMofHd()*100.0)*100/(salesRevenue*100.0));
						 
						 String objXml = "";
							if (performanceAttribute != null) {
								JAXBContext jaxbContext = JAXBContext
										.newInstance(GroupPerformanceAttribute.class);
								Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
								// output pretty printed
								jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
								StringWriter stringWriter = new StringWriter();

								jaxbMarshaller.marshal(performanceAttribute, stringWriter);
								objXml = stringWriter.toString();
							}
							performanceDto.setPerformanceData(objXml);
							
							
						 
							reportDataDao.insert(performanceDto);
							
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
					//}
						
				}
					
				}
				
			}
		}
	}
	
	
	/**
	 * To clean up all historic data
	 * @throws Exception
	 */
	public void cleanup() throws Exception
	{
		reportDataDao.cleanUpOldData();
	}
	
}
