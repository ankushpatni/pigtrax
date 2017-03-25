package com.pigtrax.jobs;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.pigtrax.jobs.dao.interfaces.GroupStatusReportDataDao;
import com.pigtrax.jobs.dto.GroupPerformanceAttribute;
import com.pigtrax.jobs.dto.GroupPerformanceReportDataDto;
import com.pigtrax.master.dao.interfaces.PremisesDao;
import com.pigtrax.master.dto.Premises;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.CompanyTargetDao;
import com.pigtrax.pigevents.dao.interfaces.FeedEventDetailDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventRoomDao;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.SalesEventDetailsDao;
import com.pigtrax.report.bean.RationReportBean;
import com.pigtrax.report.bean.RationReportFeedCostBean;
import com.pigtrax.report.dao.RationReportDao;
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

	@Autowired
	RationReportDao rationReportDao;
	
	private JdbcTemplate jdbcTemplate; 

	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
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
//							 Double netTransferWeight = performanceAttribute.getTransferInWtTotal() - performanceAttribute.getTransferOutWtTotal();
							 performanceAttribute.setNetTransferWeight(performanceAttribute.getTransferOutWtTotal()- performanceAttribute.getTransferInWtTotal() );
							 Integer netTransferHead = performanceAttribute.getTransferIn()-performanceAttribute.getTransferOut();
							 performanceAttribute.setNetTransferHead(netTransferHead);
//							 Double netTransferWtPerHead = Math.round(performanceAttribute.getNetTransferWeight()*100.0)/(performanceAttribute.getNetTransferHead()*100.0);
							 performanceAttribute.setNetTransferWeightPerHead(performanceAttribute.getTransferOutWtHd()-performanceAttribute.getTransferInWtHd());
							 
							 
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
						 
						 performanceAttribute.setTransferStartWtTotal(performanceAttribute.getStartWtTotal()+performanceAttribute.getTransferInWtTotal());
						 performanceAttribute.setTransferEndWtTotal(performanceAttribute.getEndWtTotal()+performanceAttribute.getTransferOutWtTotal()); 
						 performanceAttribute.setTotalGainWithoutTransfer(performanceAttribute.getEndWtTotal()-performanceAttribute.getStartWtTotal());
						 performanceAttribute.setTotalGainWithTransfer(performanceAttribute.getTransferEndWtTotal()-performanceAttribute.getTransferStartWtTotal());
						 performanceAttribute.setTotalGainHDWithoutTransfer(performanceAttribute.getTotalGainWithoutTransfer()/(performanceAttribute.getWeanSales()+performanceAttribute.getFeederSales()+performanceAttribute.getPreMarketSales()+performanceAttribute.getMarketSales()));
						 performanceAttribute.setTotalGainHDTransfer(performanceAttribute.getTotalGainWithTransfer()/(performanceAttribute.getWeanSales()+performanceAttribute.getFeederSales()+performanceAttribute.getPreMarketSales()+performanceAttribute.getMarketSales()+performanceAttribute.getTransferOut()));
						 
						 Double mortalityPct = Math.round(performanceAttribute.getPigDeaths()*100.0)/((performanceAttribute.getStartHd()+performanceAttribute.getTransferIn())*100.0);
						 performanceAttribute.setMortalityPct(((mortalityPct*100)*100.0)/100.0);
						 Double preMarketPct = Math.round(performanceAttribute.getPreMarketSales()*100.0)/((performanceAttribute.getStartHd()+performanceAttribute.getTransferIn())*100.0);
						 performanceAttribute.setPreMarketPct(((preMarketPct*100)*100.0)/100.0);
						 
						 performanceAttribute.setPigDays(groupEventDetailsDao.calculatePigDays(group.getId()));
						 
						 //DOF to calculate
//						 performanceAttribute.setDof(groupEventDetailsDao.calculateDaysOnFeed(group.getId()));
						 performanceAttribute.setDof(performanceAttribute.getPigDays()/(performanceAttribute.getFeederSales()+performanceAttribute.getPreMarketSales()+performanceAttribute.getMarketSales()+performanceAttribute.getTransferOut()));
						 
						 
						 performanceAttribute.setAdgWithoutTFR(performanceAttribute.getTotalGainHDWithoutTransfer()/performanceAttribute.getDof());
						 performanceAttribute.setAdgWithTFR(performanceAttribute.getTotalGainHDTransfer()/performanceAttribute.getDof());
						 
						 performanceAttribute.setTotalFeedUsed(feedDetailDao.getTotalFeedUsed(group.getId()));
						 //Get new calcualtion for feed budget
						 DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
						 Date endDateP = df.parse("01-01-2100"); // for example, today's date
						 Date startDatep = df.parse("01-01-1990"); // use your own dates, of course	
						 FeedBudgetParamBean bean = getFeedBudgetParams(group.getId(), premise.getId(), startDatep,endDateP);
//						 performanceAttribute.setTotalFeedBudget(feedDetailDao.getTotalFeedBudgeted(group.getId()));
						 performanceAttribute.setTotalFeedBudget(bean.getTotatFeedBudgeted());
						 performanceAttribute.setBudgetVariance(performanceAttribute.getTotalFeedUsed()-performanceAttribute.getTotalFeedBudget());
						 
						 performanceAttribute.setBudgetVariancePct(Math.round(performanceAttribute.getBudgetVariance()*100.0)*100/(performanceAttribute.getTotalFeedBudget()*100.0));
						 
						 //Feed Performance
						 
						 performanceAttribute.setAdfi(Math.round(performanceAttribute.getTotalFeedUsed()*100.0)/(performanceAttribute.getPigDays()*100.0));
						 performanceAttribute.setFeedHd(Math.round(performanceAttribute.getTotalFeedUsed()*100.0)/((performanceAttribute.getEndHd()+performanceAttribute.getTransferOut())*100.0));
						 performanceAttribute.setFeedEfficiency(Math.round(performanceAttribute.getTotalFeedUsed()*100.0)/(performanceAttribute.getTotalGainWtPerTranfer()*100.0));
						 performanceAttribute.setGainEfficiency((performanceAttribute.getTotalGainWtPerTranfer()*100.0)/Math.round(performanceAttribute.getTotalFeedUsed()*100.0));
						 
						 //Feed Cost performance						 
						 performanceAttribute.setTotalFeedCost(feedDetailDao.getTotalFeedCost(group.getId()));
						 performanceAttribute.setTfcBudgeted(bean.getTfcBudgeted()); 
//						 performanceAttribute.setTfcBudgeted(feedDetailDao.getTotalFeedBudgetedCost(group.getId())); 
						 performanceAttribute.setTfcVariance(performanceAttribute.getTotalFeedCost() - performanceAttribute.getTfcBudgeted());
						 performanceAttribute.setTfcHd(bean.getTfcHD());
//						 performanceAttribute.setTfcHd(Math.round(performanceAttribute.getTotalFeedCost()*100.0)/(performanceAttribute.getEndHd()*100.0));
						 performanceAttribute.setTfcBudgetedHd(bean.getTfcBudgetedHD());
//						 performanceAttribute.setTfcBudgetedHd(Math.round(performanceAttribute.getTfcBudgeted()*100.0)/(performanceAttribute.getEndHd()*100.0));
						 performanceAttribute.setTfcVarianceHd(performanceAttribute.getTfcHd() - performanceAttribute.getTfcBudgetedHd());
						 performanceAttribute.setTfcGain(Math.round(performanceAttribute.getTotalFeedCost()*100.0)/(performanceAttribute.getTotalGainWtPerTranfer()*100.0));
						 
						 performanceAttribute.setTotalRevenueSales(saleEventDetailsDao.getSalesRevenue(group.getId()) );
						 performanceAttribute.setTotalRevenueSalesHD(saleEventDetailsDao.getSalesRevenue(group.getId())/(performanceAttribute.getWeanSales()+performanceAttribute.getMarketSales()+performanceAttribute.getPreMarketSales()+performanceAttribute.getFeederSales()) );
						 
						 performanceAttribute.setMof(saleEventDetailsDao.getSalesRevenue(group.getId()) - performanceAttribute.getTotalFeedCost());
						 performanceAttribute.setMofHd(Math.round(performanceAttribute.getMof()*100.0)/(performanceAttribute.getEndHd()*100.0));
						 
						 Double salesRevenue = saleEventDetailsDao.getSalesRevenue(group.getId());
						 performanceAttribute.setMofPct(Math.round(performanceAttribute.getMofHd()*100.0)*100/(performanceAttribute.getTotalRevenueSalesHD()*100.0));
						 
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
	
	private FeedBudgetParamBean getFeedBudgetParams(int groupId, int premiseId, Date  startDate, Date endDate){
		FeedBudgetParamBean bean = new FeedBudgetParamBean();
		 
		List<RationReportBean> rationReportList = rationReportDao.getRationReportList(premiseId, startDate, endDate, groupId);
		List<RationReportFeedCostBean> feedCostList = rationReportDao.getFeedCostAndWeight(groupId); 
		List<Map<String, Date>> startAndDateList = rationReportDao.getStartEndDates(groupId);
		Date pigEntryDate = startAndDateList.get(0).get("startDate");
		Date pigEndDate = startAndDateList.get(0).get("endDate");
		double totatFeedBudgeted =0;
		double tfcBudgeted=0;
		double tfcHD=0;
		double tfcBudgetedHD=0;

		ArrayList<String> returnRows = new ArrayList<String>();
		if (rationReportList != null && rationReportList.size() > 0) {

			StringBuffer rowBuffer = null;			
			
			int count = 0;
			for (RationReportBean rationReportBean : rationReportList) {
				rowBuffer = new StringBuffer();
				
				final Date feedDate = rationReportBean.getFeedEvenDate();
				Date nextFeedDate = null;
				Date currentFeedDate = feedDate;
				if (count == 0){
					currentFeedDate = pigEntryDate;
//					currentFeedDate = startDate;
				}
				if (count == rationReportList.size()-1){
					nextFeedDate = pigEndDate;
//					nextFeedDate = endDate;
				} else{
					nextFeedDate = rationReportList.get(count+1).getFeedEvenDate();

				}
				

				String sql = " select coalesce(sum(GED.\"numberOfPigs\"),0) as Num from pigtrax.\"GroupEventDetails\" GED "
						+ "where GED.\"id_GroupEvent\" = "+groupId+" and GED.\"dateOfEntry\" <= ?";
				
				@SuppressWarnings("unchecked")
				Integer sowCount  = (Integer)jdbcTemplate.query(sql,new PreparedStatementSetter() {
					@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setDate(1, new java.sql.Date(feedDate.getTime()));
						}
					},
			        new ResultSetExtractor() {
			          public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            if (resultSet.next()) {
			              return resultSet.getInt(1);
			            }
			            return 0;
			          }
			        });
				if(sowCount == 0 )
				{
					Map<String, Object> detailsMap = groupEventDao.getStartWtAndHead(groupId);
					if(detailsMap != null && detailsMap.get("StartHd") != null)
					{
						sowCount = ((Long)detailsMap.get("StartHd")).intValue();
					}
				}
				
				//group id
			//rationid
				//inventory
				//DOF
				//tons used
				//tons target
				int duration = getDOF(currentFeedDate,nextFeedDate);
				double targetTons = rationReportBean.getTargetTonsUsed()*sowCount*rationReportBean.getTargetKg()/1000 ;
				
				//adfi
				double adfi = rationReportBean.getActualTonsUsed()*1000/duration/sowCount ;
				//Feedcost/HD
				double feedCostHD= feedCostList.get(count).getWtdAvg()*adfi;
				//FeedCost/Hd Target
				double feedCostHDTarget = feedCostList.get(count).getWtdAvg()*rationReportBean.getTargetTonsUsed();

				totatFeedBudgeted += rationReportBean.getTargetTonsUsed()*sowCount*rationReportBean.getTargetKg()/1000;
				tfcBudgeted+= feedCostList.get(count).getWtdAvg() * rationReportBean.getTargetTonsUsed()*sowCount*rationReportBean.getTargetKg();
				tfcHD += feedCostList.get(count).getWtdAvg()*adfi;
				tfcBudgetedHD += feedCostList.get(count).getWtdAvg()*rationReportBean.getTargetTonsUsed();
				
				
					
					returnRows.add(rowBuffer.toString()+"\n");
					count++;
			}
		}
		bean.setTfcBudgeted(tfcBudgeted);
		bean.setTfcBudgetedHD(tfcBudgetedHD);
		bean.setTfcHD(tfcHD);
		bean.setTotatFeedBudgeted(totatFeedBudgeted*1000);
		return bean;
	
		
	}
	
	private int getDOF(Date startDate, Date feedDate) {
		int duration = 0;
		if(startDate != null && feedDate != null)
		{
			DateTime start = new DateTime(startDate.getTime());
			DateTime end = new DateTime(feedDate.getTime());
			duration  = Days.daysBetween(start, end).getDays();
		}

		// TODO Auto-generated method stub
		return duration;
	}

}
