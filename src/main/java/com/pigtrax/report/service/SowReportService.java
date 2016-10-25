package com.pigtrax.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dao.interfaces.BarnDao;
import com.pigtrax.master.dao.interfaces.RoomDao;
import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.dto.Room;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.master.service.interfaces.PenService;
import com.pigtrax.master.service.interfaces.PremisesService;
import com.pigtrax.master.service.interfaces.RoomService;
import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigletStatusEvent;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.SalesEventDetails;
import com.pigtrax.pigevents.dao.interfaces.BreedingEventDao;
import com.pigtrax.pigevents.dao.interfaces.FarrowEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigletStatusEventDao;
import com.pigtrax.pigevents.dto.FarrowEventDto;
import com.pigtrax.pigevents.dto.PregnancyEventDto;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.pigevents.service.interfaces.PigletStatusEventService;
import com.pigtrax.pigevents.service.interfaces.PregnancyEventService;
import com.pigtrax.pigevents.service.interfaces.RemovalEventExceptSalesService;
import com.pigtrax.pigevents.service.interfaces.SalesEventDetailsService;
import com.pigtrax.report.bean.SowReportBean;
import com.pigtrax.report.dao.SowReportDao;
import com.pigtrax.util.DateUtil;

/**
 * @author Ankush
 *
 */
@Repository
public class SowReportService {
	
	@Autowired
	private SowReportDao sowReportDao;
	
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
	FarrowEventDao farrowDao;
	
	@Autowired
	PigletStatusEventService pigletStatusEventService; 
	
	@Autowired
	PigletStatusEventDao pigletStatusEventdao; 
	
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
	GroupEventService groupEventService;
	
	@Autowired
	BreedingEventDao breedingEventDao;
	
	@Autowired
	MessageSource messageSource;
	
	
	private static final String seprater = ",";

	public ArrayList<String> getSowReport(String pidId, int sowId, int companyId, String language, Locale locale)
	{
		ArrayList<String> returnRows = new ArrayList<String>();
		try {
			List<SowReportBean> sowList = sowReportDao.getSowList(sowId);

			if (sowList != null && sowList.size() > 0) {
				
				Map<Integer, String> premisesNameMap = premisesService.getPremisesNameMapBasedOnCompanyId(companyId);
				Map<Integer,String> barnIdMap  = barnService.getBarnListBasedOnCompanyId(companyId);
				Map<Integer,String> roomIdMap = roomService.getRoomListBasedOnCompanyId(companyId);
				Map<Integer,String> penIdMap = penService.getPenIdMapByCompanyId(companyId);
				Map<Integer, String> removalEventTypeMap = refDataCache.getRemovalEventTypeMap(language);
				Map<Integer, String> mortalityReasonTypeMap = refDataCache.getMortalityReasonTypeMap(language);
				Map<Integer, String> saleTypesMap = refDataCache.getSaleTypesMap(language);
				Map<Integer, String> pregenancyExamResultTypesMap = refDataCache.getPregnancyExamResultTypeMap(language);
				Map<Integer, String> pigletStatusEventType = refDataCache.getPigletStatusEventType(language);
				Map<Integer, GroupEvent> groupEventMap=  groupEventService.getGroupEventByCompanyId(companyId);
				
				StringBuffer rowBuffer = null;
				returnRows.add(messageSource.getMessage("label.reports.sowhistory.sowid", null, "", locale)+","+messageSource.getMessage("label.piginfo.pigletstatuseventform.eventDateTime", null, "", locale)+","
							+messageSource.getMessage("label.reports.sowhistory.eventname", null, "", locale)+","+messageSource.getMessage("label.premise.barn", null, "", locale)+","
							+messageSource.getMessage("label.barn.room", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.pen", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.entryeventform.parity", null, "", locale)+","+messageSource.getMessage("label.reports.sowhistory.eventdata", null, "", locale)+"\n");
				
				int parityInt = 0;
				for (SowReportBean SowReportBean : sowList) {
					rowBuffer = new StringBuffer();
						if(SowReportBean.getPigInfoId() != null)
						{
							rowBuffer.append(SowReportBean.getPigId() + seprater);
							
							if(SowReportBean.getBreedingEventId() != null && SowReportBean.getBreedingEventId()!=0 )
							{
								
								BreedingEvent breedingEventInformation = breedingEventDao.getBreedingEventInformation(SowReportBean.getBreedingEventId());
								rowBuffer.append(DateUtil.convertToFormatString(SowReportBean.getEventDate(),"dd/MM/yyyy") + seprater);
								parityInt = breedingEventInformation.getCurrentParity();
								parityInt = parityInt - 1;
								
								rowBuffer.append(messageSource.getMessage("label.leftmenu.managepigevents.breedingevent.link", null, "", locale)+"/"+messageSource.getMessage("label.piginfo.matingdetailsform.matingDetails.heading", null, "", locale)+seprater);
								
								if(SowReportBean.getBreedingEventPenId() != null && SowReportBean.getBreedingEventPenId() !=0)
								{
									Barn barn = barnDao.getBarnBasedOnPenId(SowReportBean.getBreedingEventPenId());
									rowBuffer.append(barn.getBarnId() + seprater);
								
									Room room = roomDao.getRoomListBasedOnPen(SowReportBean.getBreedingEventPenId());
									rowBuffer.append(room.getRoomId() + seprater);
									
									rowBuffer.append(penIdMap.get(SowReportBean.getBreedingEventPenId())+seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
									rowBuffer.append(seprater);
									rowBuffer.append(seprater);
								}
								
								rowBuffer.append(parityInt+seprater); // no remark for Brredingevent
							}
							else if(SowReportBean.getPregnancyEventId() != null  && SowReportBean.getPregnancyEventId() !=0)
							{
								rowBuffer.append(DateUtil.convertToFormatString(SowReportBean.getEventDate(),"dd/MM/yyyy") + seprater);
								PregnancyEventDto pregEvent = pregnancyEventService.getPregnancyEventInformation(SowReportBean.getPregnancyEventId(), language);
								if(pregEvent != null)
								{
									parityInt = breedingEventDao.getParity(pregEvent.getBreedingEventId());
									parityInt = parityInt - 1;
								}
								
								rowBuffer.append(messageSource.getMessage("label.leftmenu.managepigevents.pregnancyevent.link", null, "", locale)+seprater);
								
								rowBuffer.append(seprater);
								rowBuffer.append(seprater);
								rowBuffer.append(seprater);
								rowBuffer.append(parityInt+seprater); // no remark for Pregnancy
								PregnancyEventDto pregnancyEventInformation = pregnancyEventService.getPregnancyEventInformation(SowReportBean.getPregnancyEventId(), language);
								rowBuffer.append(messageSource.getMessage("label.piginfo.pregnancyeventform.pregnancyEventType", null, "", locale)+" : "+pregnancyEventInformation.getPregnancyEventType()+" :: "+messageSource.getMessage("label.piginfo.pregnancyeventform.pregnancyExamResultType", null, "", locale)+" : " +pregenancyExamResultTypesMap.get(pregnancyEventInformation.getPregnancyExamResultTypeId()));
							}
							
							else if(SowReportBean.getPigletStatusId()!= null  && SowReportBean.getPigletStatusId()!=0)
							{
								rowBuffer.append(DateUtil.convertToFormatString(SowReportBean.getEventDate(),"dd/MM/yyyy") + seprater);
								PigletStatusEvent pigletStatusEventInformation = pigletStatusEventdao.getPigletStatusEventInformation(SowReportBean.getPigletStatusId());
								
								FarrowEventDto farrowEvent = farrowEventService.getFarrowEventDetails(pigletStatusEventInformation.getFarrowEventId())	;
								if(farrowEvent != null)
								{
									parityInt = breedingEventDao.getParity(farrowEvent.getBreedingEventId());
								}
								
								
								rowBuffer.append(pigletStatusEventType.get(pigletStatusEventInformation.getPigletStatusEventTypeId())+seprater);
								
								if(SowReportBean.getPigletStatusPenId() != null && SowReportBean.getPigletStatusPenId()!=0)
								{
									Barn barn = barnDao.getBarnBasedOnPenId(SowReportBean.getPigletStatusPenId());
									rowBuffer.append(barn.getBarnId() + seprater);
									
									Room room = roomDao.getRoomListBasedOnPen(SowReportBean.getPigletStatusPenId());
									rowBuffer.append(room.getRoomId() + seprater);
									
									rowBuffer.append(penIdMap.get(SowReportBean.getPigletStatusPenId())+seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
									rowBuffer.append(seprater);
									rowBuffer.append(seprater);
								}
									
								rowBuffer.append(parityInt+seprater);
								String info="";
								if(pigletStatusEventInformation.getMortalityReasonTypeId() != null && pigletStatusEventInformation.getMortalityReasonTypeId() != 0)
								{
									info =  " "+messageSource.getMessage("label.piginfo.pigletstatuseventform.mortalityreason", null, "", locale)+" : "+mortalityReasonTypeMap.get(pigletStatusEventInformation.getMortalityReasonTypeId());
								}
								if(pigletStatusEventInformation.getGroupEventId() != null && pigletStatusEventInformation.getGroupEventId() !=0)
								{
									info =   " "+messageSource.getMessage("label.piginfo.pigletstatuseventform.groupeventId", null, "", locale)+" : " + groupEventMap.get(pigletStatusEventInformation.getGroupEventId()).getGroupId();
								}
								if(pigletStatusEventInformation.getFosterTo() != null && pigletStatusEventInformation.getFosterTo() !=0)
								{
									PigInfo pigInformationById = pigInfoDao.getPigInformationById(pigletStatusEventInformation.getFosterTo());
									info = " "+messageSource.getMessage("label.piginfo.pigletstatuseventform.fosterToPigId", null, "", locale)+" : " + pigInformationById.getPigId();
								}
								//else if(pigletStatusEventInformation.getP)
								rowBuffer.append(messageSource.getMessage("label.groupEventDetail.numberOfPigs", null, "", locale)+" : "+pigletStatusEventInformation.getNumberOfPigs() + " :: "+ info);
							}
							else if(SowReportBean.getFarrowEventId() != null  && SowReportBean.getFarrowEventId() !=0)
							{
								rowBuffer.append(DateUtil.convertToFormatString(SowReportBean.getEventDate(),"dd/MM/yyyy") + seprater);
								FarrowEventDto farrowEvent = farrowEventService.getFarrowEventDetails(SowReportBean.getFarrowEventId())	;
								if(farrowEvent != null)
								{
									parityInt = breedingEventDao.getParity(farrowEvent.getBreedingEventId());
								}								
								
								rowBuffer.append(messageSource.getMessage("label.leftmenu.managepigevents.farrowevent.link", null, "", locale)+seprater);
								
								if(SowReportBean.getFarrowEventPenId() != null && SowReportBean.getFarrowEventPenId()!=0)
								{
									Barn barn = barnDao.getBarnBasedOnPenId(SowReportBean.getFarrowEventPenId());
									rowBuffer.append(barn.getBarnId() + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								
								if(SowReportBean.getFarrowEventPenId() != null && SowReportBean.getFarrowEventPenId()!=0)
								{
									Room room = roomDao.getRoomListBasedOnPen(SowReportBean.getFarrowEventPenId());
									rowBuffer.append(room.getRoomId() + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								rowBuffer.append(penIdMap.get(SowReportBean.getFarrowEventPenId())+seprater);
								rowBuffer.append(parityInt+seprater);
								
								FarrowEventDto farrowEventDetails = farrowEventService.getFarrowEventDetails(SowReportBean.getFarrowEventId());
								rowBuffer.append(messageSource.getMessage("label.piginfo.farroweventform.liveborns", null, "", locale)+": "+farrowEventDetails.getLiveBorns() + " :: "+messageSource.getMessage("label.piginfo.farroweventform.stillborns", null, "", locale)+" : "+farrowEventDetails.getStillBorns() 
										+" :: "+messageSource.getMessage("label.piginfo.farroweventform.mummies", null, "", locale)+" : "+farrowEventDetails.getMummies());
								
							}
							
							
							else if(SowReportBean.getRemovalEventExceptSalesDetailsId()!= null && SowReportBean.getRemovalEventExceptSalesDetailsId()!=0 )
							{
								rowBuffer.append(DateUtil.convertToFormatString(SowReportBean.getEventDate(),"dd/MM/yyyy") + seprater);
								parityInt = farrowDao.getFarrowCount(SowReportBean.getPigInfoId());
								
								RemovalEventExceptSalesDetails removalEventExceptSalesDetailsById = removalEventExceptSalesService.getRemovalEventExceptSalesDetailsById(SowReportBean.getRemovalEventExceptSalesDetailsId());
								if(removalEventExceptSalesDetailsById.getRemovalEventId() !=9)
								{
									rowBuffer.append(messageSource.getMessage("label.leftmenu.managepigevents.removalevent.link", null, "", locale)+seprater);
								}
								else
									rowBuffer.append(messageSource.getMessage("label.piginfo.removalExceptSales.transfer.option", null, "", locale)+seprater);
								
								if(SowReportBean.getRemovalEventExceptSalesDetailsRoomId() != null && SowReportBean.getRemovalEventExceptSalesDetailsRoomId()!=0)
								{
									Barn barn = barnDao.getBarnBasedOnRoomId(SowReportBean.getRemovalEventExceptSalesDetailsRoomId());
									rowBuffer.append(barn.getBarnId() + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								
								if(SowReportBean.getRemovalEventExceptSalesDetailsRoomId() != null && SowReportBean.getRemovalEventExceptSalesDetailsRoomId()!=0)
								{
									rowBuffer.append(roomIdMap.get(SowReportBean.getRemovalEventExceptSalesDetailsRoomId()) + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								rowBuffer.append(seprater);	
								rowBuffer.append(parityInt+seprater);
								//RemovalEventExceptSalesDetails removalEventExceptSalesDetailsById = removalEventExceptSalesService.getRemovalEventExceptSalesDetailsById(SowReportBean.getRemovalEventExceptSalesDetailsRoomId());
								if(removalEventExceptSalesDetailsById.getRemovalEventId() !=9)
								{
									rowBuffer.append(messageSource.getMessage("label.piginfo.removalEventform.removalTypeId", null, "", locale)+": "+removalEventTypeMap.get(removalEventExceptSalesDetailsById.getRemovalEventId()) + " :: Mortality Reason : "+mortalityReasonTypeMap.get(removalEventExceptSalesDetailsById.getMortalityReasonId()));
								}
								else
								{
									rowBuffer.append(messageSource.getMessage("label.piginfo.removalExceptSales.premiseIdTo", null, "", locale)+": "+premisesNameMap.get(removalEventExceptSalesDetailsById.getDestPremiseId()));
								}
								
								
							}
							
							else if(SowReportBean.getSalesEventDetailsId() != null  && SowReportBean.getSalesEventDetailsId() !=0)
							{
								rowBuffer.append(DateUtil.convertToFormatString(SowReportBean.getEventDate(),"dd/MM/yyyy") + seprater);
								parityInt = farrowDao.getFarrowCount(SowReportBean.getPigInfoId());
								
								rowBuffer.append(messageSource.getMessage("label.piginfo.removalExceptSales.sales.option", null, "", locale)+seprater);
								
								rowBuffer.append(seprater);
								rowBuffer.append(seprater);
								rowBuffer.append(seprater);
								rowBuffer.append(parityInt+seprater);
								SalesEventDetails salesEventDetailsById = salesEventDetailsService.getSalesEventDetailsById(SowReportBean.getSalesEventDetailsId());
								StringBuffer salesTypeStr = new StringBuffer();
								if(salesEventDetailsById.getSalesTypes() != null)
								{
									Integer [] salesType = salesEventDetailsById.getSalesTypes();
									for(Integer i : salesType)
									{
										salesTypeStr.append(saleTypesMap.get(i) +":");
									}
								}
								rowBuffer.append("Sales Type : "+salesTypeStr+" :: Ticket Number : "+salesEventDetailsById.getTicketNumber());
								
							}
							else
							{
								rowBuffer.append(DateUtil.convertToFormatString(SowReportBean.getEventDate(),"dd/MM/yyyy") + seprater);
								rowBuffer.append(messageSource.getMessage("label.piginfo.entryeventform.entryevent", null, "", locale)+seprater);
								
								if(SowReportBean.getPigInfoRoom() != null && SowReportBean.getPigInfoRoom()!=0)
								{
									Barn barn = barnDao.getBarnBasedOnRoomId(SowReportBean.getPigInfoRoom());
									rowBuffer.append(barn.getBarnId() + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}								
								if(SowReportBean.getPigInfoRoom() != null && SowReportBean.getPigInfoRoom() !=0)
								{
									rowBuffer.append(roomIdMap.get(SowReportBean.getPigInfoRoom()) + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								rowBuffer.append(seprater);
								rowBuffer.append(parityInt+seprater);
								rowBuffer.append(SowReportBean.getPigInfoRemarks()!=null?SowReportBean.getPigInfoRemarks():"");
							}
						}
						returnRows.add(rowBuffer.toString()+"\n");
				}
				
			} else {
				returnRows.add("Can not find pig by given Id");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnRows.add("Error occured please coontact admin.");
		}
		return returnRows;
	}
}
