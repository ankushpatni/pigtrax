package com.pigtrax.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.SalesEventDetails;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dto.FarrowEventDto;
import com.pigtrax.pigevents.dto.PregnancyEventDto;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.pigevents.service.interfaces.PigletStatusEventService;
import com.pigtrax.pigevents.service.interfaces.PregnancyEventService;
import com.pigtrax.pigevents.service.interfaces.RemovalEventExceptSalesService;
import com.pigtrax.pigevents.service.interfaces.SalesEventDetailsService;
import com.pigtrax.report.bean.GroupReportBean;
import com.pigtrax.report.bean.SowReportBean;
import com.pigtrax.report.dao.GroupReportDao;

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
	
	private static final String seprater = ",";
	
	public ArrayList<String> getSowReport(String groupIdStr, int groupId, int companyId, String language)
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
				returnRows.add("Group ID, Event Date, Event Name, Barn, Room, Data");
				returnRows.add("\n");
				int parityInt = 0;
				for (GroupReportBean groupReportBean : groupList) {
					rowBuffer = new StringBuffer();
						if(groupReportBean.getGroupEventId() != null)
						{
							rowBuffer.append(groupIdStr + seprater);
							rowBuffer.append(groupReportBean.getEventDate() + seprater);
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
									rowBuffer.append(barn.getBarnId() + seprater);
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
								
									rowBuffer.append("NumPigs : "+removalEventExceptSalesDetailsById.getNumberOfPigs() + " :: Weight : "+removalEventExceptSalesDetailsById.getWeightInKgs()
											+ " :: Remark : "+removalEventExceptSalesDetailsById.getRemarks());
									
									if(removalEventExceptSalesDetailsById.getRemovalEventId() !=9)
									{
										rowBuffer.append("Removal Type : "+removalEventTypeMap.get(removalEventExceptSalesDetailsById.getRemovalEventId()) +
												" :: NumPigs : "+removalEventExceptSalesDetailsById.getNumberOfPigs() + " :: Weight : "+removalEventExceptSalesDetailsById.getWeightInKgs()+
												" :: Mortality Reason : "+mortalityReasonTypeMap.get(removalEventExceptSalesDetailsById.getMortalityReasonId()));
									}
									else
									{
										rowBuffer.append("NumPigs : "+removalEventExceptSalesDetailsById.getNumberOfPigs() + " :: To premises : "+premisesNameMap.get(removalEventExceptSalesDetailsById.getDestPremiseId()) +
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
								rowBuffer.append("NumPigs : "+salesEventDetailsById.getNumberOfPigs() +"Sales Type : "+salesTypeStr+" :: Invoice Id : "+salesEventDetailsById.getInvoiceId());
								
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


}
