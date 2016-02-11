package com.pigtrax.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.dao.interfaces.BarnDao;
import com.pigtrax.master.dao.interfaces.RoomDao;
import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.dto.Room;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.master.service.interfaces.PenService;
import com.pigtrax.master.service.interfaces.PremisesService;
import com.pigtrax.master.service.interfaces.RoomService;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.report.bean.SowReportBean;
import com.pigtrax.report.dao.SowReportDao;

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
	
	private static final String seprater = ",";

	public ArrayList<String> getSowReport(String pidId, int sowId, int companyId)
	{
		ArrayList<String> returnRows = new ArrayList<String>();
		try {
			List<SowReportBean> sowList = sowReportDao.getSowList(sowId);

			if (sowList != null && sowList.size() > 0) {
				
				Map<Integer, String> premisesNameMap = premisesService.getPremisesNameMapBasedOnCompanyId(companyId);
				Map<Integer,String> barnIdMap  = barnService.getBarnListBasedOnCompanyId(companyId);
				Map<Integer,String> roomIdMap = roomService.getRoomListBasedOnCompanyId(companyId);
				Map<Integer,String> penIdMap = penService.getPenIdMapByCompanyId(companyId);
				
				StringBuffer rowBuffer = null;
				returnRows.add("Sow ID, Event Date, Event Name, Barn, Room, Pen, Parity, Event Data");
				returnRows.add("\n");
				int parityInt = 0;
				for (SowReportBean SowReportBean : sowList) {
					rowBuffer = new StringBuffer();
						if(SowReportBean.getPigInfoId() != null)
						{
							rowBuffer.append(pidId + seprater);
							rowBuffer.append(SowReportBean.getEventDate() + seprater);
							if(SowReportBean.getBreedingEventId() != null && SowReportBean.getBreedingEventId()!=0 )
							{
								rowBuffer.append("Breading,");
								
								if(SowReportBean.getBreedingEventPenId() != null && SowReportBean.getBreedingEventPenId() !=0)
								{
									Barn barn = barnDao.getBarnBasedOnPenId(SowReportBean.getBreedingEventPenId());
									rowBuffer.append(barn.getBarnId() + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								
								if(SowReportBean.getBreedingEventPenId() != null && SowReportBean.getBreedingEventPenId()!=0)
								{
									Room room = roomDao.getRoomListBasedOnPen(SowReportBean.getBreedingEventPenId());
									rowBuffer.append(room.getRoomId() + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								rowBuffer.append(penIdMap.get(SowReportBean.getBreedingEventPenId())+seprater);
								rowBuffer.append(parityInt+seprater); // no remark for Brredingevent
							}
							if(SowReportBean.getPregnancyEventId() != null  && SowReportBean.getPregnancyEventId() !=0)
							{
								rowBuffer.append("Pregnancy"+seprater);
								
								rowBuffer.append(seprater);
								rowBuffer.append(seprater);
								rowBuffer.append(seprater);
								rowBuffer.append(parityInt+seprater); // no remark for Pregnancy
							}
							else if(SowReportBean.getFarrowEventId() != null  && SowReportBean.getFarrowEventId() !=0)
							{
								parityInt++;
								rowBuffer.append("Farrow"+seprater);
								
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
								rowBuffer.append(SowReportBean.getFarrowEventRemarks() !=null?SowReportBean.getFarrowEventRemarks():"");
								
							}
							else if(SowReportBean.getPigletStatusId()!= null  && SowReportBean.getPigletStatusId()!=0)
							{
								rowBuffer.append("Piglet Status"+seprater);
								
								if(SowReportBean.getPigletStatusPenId() != null && SowReportBean.getPigletStatusPenId()!=0)
								{
									Barn barn = barnDao.getBarnBasedOnPenId(SowReportBean.getPigletStatusPenId());
									rowBuffer.append(barn.getBarnId() + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								
								if(SowReportBean.getPigletStatusPenId() != null && SowReportBean.getPigletStatusPenId()!=0)
									{
									Room room = roomDao.getRoomListBasedOnPen(SowReportBean.getPigletStatusPenId());
									rowBuffer.append(room.getRoomId() + seprater);
								}
								else
									
								{
									rowBuffer.append(seprater);
								}
								rowBuffer.append(penIdMap.get(SowReportBean.getPigletStatusPenId())+seprater);	
								rowBuffer.append(parityInt+seprater);
								rowBuffer.append(SowReportBean.getPigletStatusRemarks()!=null?SowReportBean.getPigletStatusRemarks():"");
							}
							
							else if(SowReportBean.getRemovalEventExceptSalesDetailsId()!= null && SowReportBean.getRemovalEventExceptSalesDetailsId()!=0 )
							{
								rowBuffer.append("Removal"+seprater);
								
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
								rowBuffer.append(SowReportBean.getRemovalRemarks()!=null?SowReportBean.getRemovalRemarks():"");
							}
							
							else if(SowReportBean.getSalesEventDetailsId() != null  && SowReportBean.getSalesEventDetailsId() !=0)
							{
								rowBuffer.append("Sales"+seprater);
								
								rowBuffer.append(seprater);
								rowBuffer.append(seprater);
								rowBuffer.append(seprater);
								rowBuffer.append(parityInt+seprater);
								rowBuffer.append(SowReportBean.getSalesRemarks()!=null?SowReportBean.getSalesRemarks():"");
								
							}
							else
							{
								rowBuffer.append("Entry"+seprater);
								
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
