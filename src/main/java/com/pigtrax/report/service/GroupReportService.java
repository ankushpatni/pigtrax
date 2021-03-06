package com.pigtrax.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dao.interfaces.BarnDao;
import com.pigtrax.master.dao.interfaces.RoomDao;
import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.master.service.interfaces.PenService;
import com.pigtrax.master.service.interfaces.PremisesService;
import com.pigtrax.master.service.interfaces.RoomService;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.SalesEventDetails;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.pigevents.service.interfaces.PigletStatusEventService;
import com.pigtrax.pigevents.service.interfaces.PregnancyEventService;
import com.pigtrax.pigevents.service.interfaces.RemovalEventExceptSalesService;
import com.pigtrax.pigevents.service.interfaces.SalesEventDetailsService;
import com.pigtrax.report.bean.GroupReportBean;
import com.pigtrax.report.bean.GroupReportBeanwithPhase;
import com.pigtrax.report.dao.GroupReportDao;
import com.pigtrax.util.DateUtil;

/**
 * @author Ankush
 *
 */
@Repository
public class GroupReportService {
	
	@Autowired
	private GroupReportDao groupReportDao;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	BarnService barnService;
	
	@Autowired
	PremisesService premisesService;
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	PenService penService;
	
	@Autowired
	RoomDao roomDao;
	
	@Autowired
	BarnDao barnDao;
	
	@Autowired
	PigletStatusEventService pigletStatusEventService; 
	
	@Autowired
	FarrowEventService farrowEventService;
	
	@Autowired
	PregnancyEventService pregnancyEventService;
	
	@Autowired
	RemovalEventExceptSalesService  removalEventExceptSalesService;
	
	@Autowired
	SalesEventDetailsService salesEventDetailsService;
	
	@Autowired
	RefDataCache refDataCache;
	
	@Autowired
	MessageSource messageSource;
	
	private static final String seprater = ",";
	
	private static final String dataSeprater = "::";
	
	public ArrayList<String> getGroupReport(String groupIdStr, int groupId, int companyId, String language, Locale locale)
	{
		ArrayList<String> returnRows = new ArrayList<String>();
		try {
			List<GroupReportBean> groupList = groupReportDao.getGroupList(groupId);

			if (groupList != null && groupList.size() > 0) {
				
				Map<Integer, String> premisesNameMap = premisesService.getPremisesNameMapBasedOnCompanyId(companyId);
				Map<Integer,String> barnIdMap  = barnService.getBarnListBasedOnCompanyId(companyId);
				Map<Integer,String> roomIdMap = roomService.getRoomListBasedOnCompanyId(companyId);
				Map<Integer, String> removalEventTypeMap = refDataCache.getRemovalEventTypeMap(language);
				Map<Integer, String> saleTypesMap = refDataCache.getSaleTypesMap(language);
				Map<Integer, String> mortalityReasonTypeMap = refDataCache.getMortalityReasonTypeMap(language);
				
				
				StringBuffer rowBuffer = null;
				returnRows.add(messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","+messageSource.getMessage("label.piginfo.pigletstatuseventform.eventDateTime", null, "", locale)+","
						+messageSource.getMessage("label.reports.sowhistory.eventname", null, "", locale)+","+messageSource.getMessage("label.premise.barn", null, "", locale)+","
						+messageSource.getMessage("label.barn.room", null, "", locale)+","+messageSource.getMessage("label.reports.sowhistory.eventdata", null, "", locale)+"\n");
				int parityInt = 0;
				for (GroupReportBean groupReportBean : groupList) {
					rowBuffer = new StringBuffer();
						if(groupReportBean.getGroupEventId() != null)
						{
							rowBuffer.append(groupIdStr + seprater);
							rowBuffer.append(DateUtil.convertToFormat(groupReportBean.getEventDate(),"dd/MM/yyyy") + seprater);
							if(groupReportBean.getRemovalEventExceptSalesDetailsId() != null && groupReportBean.getRemovalEventExceptSalesDetailsId()!=0 )
							{
								RemovalEventExceptSalesDetails removalEventExceptSalesDetailsById = removalEventExceptSalesService.getRemovalEventExceptSalesDetailsById(groupReportBean.getRemovalEventExceptSalesDetailsRoomId());
								if(removalEventExceptSalesDetailsById.getRemovalEventId() !=9)
								{
									rowBuffer.append("Removal"+seprater);
								}
								else
									rowBuffer.append("Transfer"+seprater);
								
								if(groupReportBean.getRemovalEventExceptSalesDetailsRoomId() != null && groupReportBean.getRemovalEventExceptSalesDetailsRoomId()!=0)
								{
									Barn barn = barnDao.getBarnBasedOnRoomId(groupReportBean.getRemovalEventExceptSalesDetailsRoomId());
									rowBuffer.append(barnIdMap.get(barn.getBarnId()) + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								
								if(groupReportBean.getRemovalEventExceptSalesDetailsRoomId() != null && groupReportBean.getRemovalEventExceptSalesDetailsRoomId()!=0)
								{
									rowBuffer.append(roomIdMap.get(groupReportBean.getRemovalEventExceptSalesDetailsRoomId()) + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								//NumPigs: 50 Weight: 1350.0000 Value:  :  Reason: 02 Remark: 
								
									/*rowBuffer.append("NumPigs : "+removalEventExceptSalesDetailsById.getNumberOfPigs() + " :: Weight : "+removalEventExceptSalesDetailsById.getWeightInKgs()
											+ " :: Remark : "+removalEventExceptSalesDetailsById.getRemarks());
								*/	
									if(removalEventExceptSalesDetailsById.getRemovalEventId() !=9)
									{
										rowBuffer.append("Removal Type : "+removalEventTypeMap.get(removalEventExceptSalesDetailsById.getRemovalEventId()) +
												" :: Pigs : "+removalEventExceptSalesDetailsById.getNumberOfPigs() + " :: Weight : "+removalEventExceptSalesDetailsById.getWeightInKgs()+
												" :: Mortality Reason : "+mortalityReasonTypeMap.get(removalEventExceptSalesDetailsById.getMortalityReasonId()));
									}
									else
									{
										rowBuffer.append("Pigs : "+removalEventExceptSalesDetailsById.getNumberOfPigs() + " :: To premises : "+premisesNameMap.get(removalEventExceptSalesDetailsById.getDestPremiseId()) +
												" :: To Room : "+roomIdMap.get(removalEventExceptSalesDetailsById.getRoomId()));
									}
								
							}
							
							else if(groupReportBean.getSalesEventDetailsId() != null  && groupReportBean.getSalesEventDetailsId() !=0)
							{
								rowBuffer.append("Sales"+seprater);
								
								rowBuffer.append(seprater);
								rowBuffer.append(parityInt+seprater);
								SalesEventDetails salesEventDetailsById = salesEventDetailsService.getSalesEventDetailsById(groupReportBean.getSalesEventDetailsId());
								StringBuffer salesTypeStr = new StringBuffer();
								if(salesEventDetailsById.getSalesTypes() != null)
								{
									Integer [] salesType = salesEventDetailsById.getSalesTypes();
									for(Integer i : salesType)
									{
										salesTypeStr.append(saleTypesMap.get(i) +":");
									}
								}
								rowBuffer.append("Pigs : "+salesEventDetailsById.getNumberOfPigs() +"Sales Type : "+salesTypeStr+" :: Invoice Id : "+salesEventDetailsById.getInvoiceId());
								
							}
							else
							{
								rowBuffer.append("Entry"+seprater);
								rowBuffer.append(seprater);
								rowBuffer.append(seprater);
								//rowBuffer.append(groupReportBean.getPigInfoRemarks()!=null?SowReportBean.getPigInfoRemarks():"");
							}
							returnRows.add(rowBuffer.toString()+"\n");
								
							}
						
						 else {
								returnRows.add("Can not find pig by given Id");
							}
						
						}						
				}
				
			}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnRows.add("Error occured please coontact admin.");
		}
		
		return returnRows;
	}

	public ArrayList<String> getGroupReportNew(String groupIdStr, GroupEvent groupEvent, int companyId, String language)
	{
		ArrayList<String> returnRows = new ArrayList<String>();
		try {
			List<GroupReportBean> groupList = groupReportDao.getNewGroupList(groupEvent.getId());

			if (groupList != null && groupList.size() > 0) {
				
				Map<Integer, String> premisesNameMap = premisesService.getPremisesNameMapBasedOnCompanyId(companyId);
				Map<Integer,String> barnIdMap  = barnService.getBarnListBasedOnCompanyId(companyId);
				Map<Integer,String> roomIdMap = roomService.getRoomListBasedOnCompanyId(companyId);
				Map<Integer, String> removalEventTypeMap = refDataCache.getRemovalEventTypeMap(language);
				Map<Integer, String> saleTypesMap = refDataCache.getSaleTypesMap(language);
				Map<Integer, String> mortalityReasonTypeMap = refDataCache.getMortalityReasonTypeMap(language);
				
				
				StringBuffer rowBuffer = new StringBuffer();
				returnRows.add("Group ID, Event Date, Event Name, Barn, Room, Data");
				returnRows.add("\n");				
				rowBuffer.append(groupIdStr).append(seprater).append(groupEvent.getGroupStartDateTime()).append(seprater).append("Group Start").append(seprater).append(seprater).append(seprater);
				returnRows.add(rowBuffer.toString());
				returnRows.add("\n");
				
				for (GroupReportBean groupReportBean : groupList) {
					rowBuffer = new StringBuffer();
						//if(groupReportBean.getGroupEventId() != null)
						{
							rowBuffer.append(groupIdStr + seprater);
							rowBuffer.append(groupReportBean.getDateOfEntry() + seprater);
							rowBuffer.append(groupReportBean.getRemarks());
							rowBuffer.append(seprater).append(seprater).append(seprater);
							if(groupReportBean.getRemovalId() != null && groupReportBean.getRemovalId()!=0 )
							{
								/*RemovalEventExceptSalesDetails removalEventExceptSalesDetailsById = removalEventExceptSalesService.getRemovalEventExceptSalesDetailsById(groupReportBean.getRemovalEventExceptSalesDetailsRoomId());
								
								if(groupReportBean.getRemovalEventExceptSalesDetailsRoomId() != null && groupReportBean.getRemovalEventExceptSalesDetailsRoomId()!=0)
								{
									Barn barn = barnDao.getBarnBasedOnRoomId(groupReportBean.getRemovalEventExceptSalesDetailsRoomId());
									rowBuffer.append(barnIdMap.get(barn.getBarnId()) + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								
								if(groupReportBean.getRemovalEventExceptSalesDetailsRoomId() != null && groupReportBean.getRemovalEventExceptSalesDetailsRoomId()!=0)
								{
									rowBuffer.append(roomIdMap.get(groupReportBean.getRemovalEventExceptSalesDetailsRoomId()) + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}*/
								//NumPigs: 50 Weight: 1350.0000 Value:  :  Reason: 02 Remark: 
								
									/*rowBuffer.append("NumPigs : "+removalEventExceptSalesDetailsById.getNumberOfPigs() + " :: Weight : "+removalEventExceptSalesDetailsById.getWeightInKgs()
											+ " :: Remark : "+removalEventExceptSalesDetailsById.getRemarks());
								*/	
									if(groupReportBean.getRemovalTypeId() !=9)
									{
										rowBuffer.append("NumPigs : "+groupReportBean.getNumberOfPigs() +
												" :: Removal Type : "+removalEventTypeMap.get(groupReportBean.getRemovalTypeId()) + " :: Weight : "+groupReportBean.getWeightInKgs()+
												" :: Mortality Reason : "+mortalityReasonTypeMap.get(groupReportBean.getMortalityReasonId()));
									}
									/*else
									{
										rowBuffer.append("NumPigs : "+removalEventExceptSalesDetailsById.getNumberOfPigs() + " :: To premises : "+premisesNameMap.get(removalEventExceptSalesDetailsById.getDestPremiseId()) +
												" :: To Room : "+roomIdMap.get(removalEventExceptSalesDetailsById.getRoomId()));
									}*/
								
							}
							
							else if(groupReportBean.getSalesId() != null  && groupReportBean.getSalesId() !=0)
							{
								SalesEventDetails salesEventDetailsById = salesEventDetailsService.getSalesEventDetailsById(groupReportBean.getSalesId());
								StringBuffer salesTypeStr = new StringBuffer();
								if(salesEventDetailsById.getSalesTypes() != null)
								{
									Integer [] salesType = getSalesTypesAsString(groupReportBean.getSalesTypes());
									for(Integer i : salesType)
									{
										salesTypeStr.append(saleTypesMap.get(i) +":");
									}
								}
								rowBuffer.append("NumPigs : "+groupReportBean.getNumberOfPigs() + " :: Weight : "+groupReportBean.getWeightInKgs() + " :: Sales Type : "+salesTypeStr+" :: Ticket Number : "+groupReportBean.getTicketNumber());
								
							}
							else
							{
								rowBuffer.append("NumPigs : "+groupReportBean.getNumberOfPigs() +" :: Weight : "+groupReportBean.getWeightInKgs() );
							}
							returnRows.add(rowBuffer.toString()+"\n");
								
							}
						
						/* else {
								returnRows.add("Can not find pig by given Id");
							}*/
						
						}						
				}
			if(groupEvent.getGroupCloseDateTime() != null)
			{
				StringBuffer rowBuffer = new StringBuffer();
				rowBuffer.append(groupIdStr).append(seprater).append(groupEvent.getGroupStartDateTime()).append(seprater).append("Group Start").append(seprater).append(seprater).append(seprater);
				returnRows.add(rowBuffer.toString());
			}
			
			}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnRows.add("Error occured please coontact admin.");
		}
		
		return returnRows;
	}
	
	public ArrayList<String> getGroupReportwithPhase(String groupIdStr, GroupEvent groupEvent, int companyId, String language, Locale locale)
	{
		ArrayList<String> returnRows = new ArrayList<String>();
		try {
			List<GroupReportBeanwithPhase> groupList = groupReportDao.getGroupListWithPhaseDetails(groupEvent.getId());

			if (groupList != null && groupList.size() > 0) {
				
				Map<Integer, String> premisesNameMap = premisesService.getPremisesNameMapBasedOnCompanyId(companyId);
				Map<Integer,String> barnIdMap  = barnService.getBarnListBasedOnCompanyId(companyId);
				Map<Integer,String> roomIdMap = roomService.getRoomListBasedOnCompanyId(companyId);
				Map<Integer, String> removalEventTypeMap = refDataCache.getRemovalEventTypeMap(language);
				Map<Integer, String> saleTypesMap = refDataCache.getSaleTypesMap(language);
				Map<Integer, String> mortalityReasonTypeMap = refDataCache.getMortalityReasonTypeMap(language);
				int weekPlaced = 0;
				int weekPlacedPrev = 0;
				
				
				
				StringBuffer rowBuffer = new StringBuffer();
				
				returnRows.add(messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","
				+messageSource.getMessage("label.piginfo.pigletstatuseventform.eventDateTime", null, "", locale) + messageSource.getMessage("label.piginfo.input.dateformat", null, "", locale)+","
				+"Week"+","+"Placed"+","+"Inventory"+","
				+messageSource.getMessage("label.reports.sowhistory.eventname", null, "", locale)+","
				+"Movement,"+"Pigs,"+"Weight,"+"MortalityReason,"+"SalesType,"+ "NurseryTicketNumber,"+ "CurrentPhase,"+"Group,"+"Premise,"+"Room" + "\n");
//				+messageSource.getMessage("label.reports.sowhistory.eventdata", null, "", locale)+"\n");
				int count = 0;
				int pigs = 0;
				int prevPlaceval = 0;
				double weight = 0;
				int inventory = 0;
				String movement = "";
				String mortalityReason = "";
				String salesType="";
				String nurseryTicketNumber= "";
				String currentPhase	= "";
				String transfer = "";

				for (GroupReportBeanwithPhase groupReportBeanwithPhase : groupList) {
					rowBuffer = new StringBuffer();
					movement="";
					rowBuffer.append(groupReportBeanwithPhase.getGroupEventId()+seprater);
					
					rowBuffer.append(DateUtil.convertToFormatString(groupReportBeanwithPhase.getEventDate(),"dd/MM/yyyy")+seprater);
					int weekOfYear = DateUtil.getWeekOfTheYear(DateUtil.convertToFormatString(groupReportBeanwithPhase.getEventDate(),"dd/MM/yyyy"));
					rowBuffer.append(weekOfYear + seprater);
					weekPlaced = weekOfYear;
					if (count ==0){
						prevPlaceval = 1;
						rowBuffer.append( prevPlaceval +seprater);
					}
					else{
						rowBuffer.append((weekPlaced - weekPlacedPrev)+prevPlaceval + seprater);
						prevPlaceval = (weekPlaced - weekPlacedPrev)+prevPlaceval;
					}
					weekPlacedPrev = weekPlaced;
					count++;
					
					String data = groupReportBeanwithPhase.getData();
					if (data != null){
						
						String [] arr = data.split("::");
						if (arr!=null){
							String [] pigArr = arr[0].split(":");
							if (pigArr !=null){
								pigs = Integer.parseInt(pigArr[1].trim());
							}
							String[] weightArr = arr[1].split(":");
							if (weightArr!=null){
								weight = Double.parseDouble(weightArr[1].trim());
							}
						}
					}
					
					inventory +=pigs;
					rowBuffer.append(inventory+seprater);

							
					rowBuffer.append(groupReportBeanwithPhase.getEventName()+seprater);
					if(groupReportBeanwithPhase.getRemovalType() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getRemovalType()))
					{
						movement= groupReportBeanwithPhase.getRemovalType();
					}
					if(groupReportBeanwithPhase.getTicketnumber() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getTicketnumber()))
					{
						movement = "Sales";
					}
					rowBuffer.append(movement+seprater);
					rowBuffer.append(pigs+seprater);
					rowBuffer.append(weight+seprater);
					rowBuffer.append(groupReportBeanwithPhase.getMortalityReason()+seprater);
					rowBuffer.append(getSalesTypes(groupReportBeanwithPhase,saleTypesMap)+seprater);
					rowBuffer.append(groupReportBeanwithPhase.getTicketnumber()+seprater);
					rowBuffer.append(groupReportBeanwithPhase.getPhaseChange()+seprater);
					
					String additionalData = groupReportBeanwithPhase.getAdditionalData();
					if (additionalData!=null && !StringUtils.isEmpty(additionalData)){
						String [] arr1 = additionalData.split("::");
						if (arr1 != null){
							if (arr1[1]!=null){
								String[] groups = arr1[1].split(":");
								if (groups!=null){
									rowBuffer.append(groups[1]+seprater);
								}
							}
							if (arr1[2]!=null){
								String[] premises = arr1[2].split(":");
								if (premises!=null){
									rowBuffer.append(premises[1]+seprater);
								}
							}
							if (arr1[4]!=null){
								String[] rooms = arr1[4].split(":") ;
								if (rooms!=null){
									rowBuffer.append(rooms[1]+seprater);
								}
							}
						}
						
					}
//					rowBuffer.append(groupReportBeanwithPhase.getAdditionalData());
					
					


				/*	if(groupReportBeanwithPhase.getData() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getData()))
					{
						rowBuffer.append(groupReportBeanwithPhase.getData());
						if(groupReportBeanwithPhase.getAdditionalData() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getAdditionalData()))
							rowBuffer.append(dataSeprater+" "+groupReportBeanwithPhase.getAdditionalData());
						rowBuffer.append(dataSeprater).append(" Current Phase : "+messageSource.getMessage("label.piginfo.pigEvent.Nursery", null, "", locale));
					}
					
					if(groupReportBeanwithPhase.getRemovalType() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getRemovalType()))
					{
						rowBuffer.append(messageSource.getMessage("label.piginfo.removalEventform.removalTypeId", null, "", locale)+" : ").append(groupReportBeanwithPhase.getRemovalType()+dataSeprater);
					}
					
					if(groupReportBeanwithPhase.getMortalityReason() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getMortalityReason()))
					{
						rowBuffer.append(messageSource.getMessage("label.piginfo.pigletstatuseventform.mortalityreason", null, "", locale)+" : ").append(groupReportBeanwithPhase.getMortalityReason()+dataSeprater);
					}
					if(groupReportBeanwithPhase.getTicketnumber() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getTicketnumber()))
					{
						rowBuffer.append(messageSource.getMessage("label.piginfo.feedEventForm.ticketNumber", null, "", locale)+" : ").append(groupReportBeanwithPhase.getTicketnumber()+dataSeprater);
					}
					if(groupReportBeanwithPhase.getSalesTypes() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getSalesTypes()))
					{
						Integer [] salesType = getSalesTypesAsString(groupReportBeanwithPhase.getSalesTypes());
						rowBuffer.append(messageSource.getMessage("label.piginfo.removalEventform.salesType", null, "", locale)+" : ");
						for(Integer i : salesType)
						{
							rowBuffer.append(saleTypesMap.get(i) +":");
						}
					}	
					if(groupReportBeanwithPhase.getPhaseChange() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getPhaseChange()))
					{
						rowBuffer.append(messageSource.getMessage("label.reports.grouphistory.phasechange", null, "", locale)+" : ").append(groupReportBeanwithPhase.getPhaseChange());
					}*/
					returnRows.add(rowBuffer.toString()+"\n");
						
					}						
				}
			if(groupEvent.getGroupCloseDateTime() != null)
			{
				StringBuffer rowBuffer = new StringBuffer();
				rowBuffer.append(groupIdStr).append(seprater).append(groupEvent.getGroupStartDateTime()).append(seprater).append("Group close").append(seprater);
				returnRows.add(rowBuffer.toString());
			}
			
			}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnRows.add("Error occured please coontact admin.");
		}
		
		return returnRows;
	}
	
	private String getSalesTypes(GroupReportBeanwithPhase groupReportBeanwithPhase, Map<Integer, String> saleTypesMap) {
		StringBuffer salesTypeStr=new StringBuffer();
		if(groupReportBeanwithPhase.getSalesTypes() != null && !StringUtils.isEmpty(groupReportBeanwithPhase.getSalesTypes()))
		{
			Integer [] salesType = getSalesTypesAsString(groupReportBeanwithPhase.getSalesTypes());
			for(Integer i : salesType)
			{
				salesTypeStr.append(saleTypesMap.get(i) +":");
			}
		}	
		// TODO Auto-generated method stub
		return salesTypeStr.toString();
	}

	

	public Integer[] getSalesTypesAsString(String salesTypesAsString) {
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
