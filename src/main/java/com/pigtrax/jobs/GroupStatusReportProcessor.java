package com.pigtrax.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.jobs.dao.interfaces.GroupStatusReportDataDao;
import com.pigtrax.jobs.dto.GroupStatusReportDataDto;
import com.pigtrax.master.dao.interfaces.PremisesDao;
import com.pigtrax.master.dto.Premises;
import com.pigtrax.pigevents.beans.CompanyTarget;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.dao.interfaces.CompanyTargetDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventRoomDao;
import com.pigtrax.pigevents.dao.interfaces.RemovalEventExceptSalesDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.SalesEventDetailsDao;
import com.pigtrax.util.DateUtil;


@Component("groupStatusProcessor")
public class GroupStatusReportProcessor{
	
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
	
	
	public void process() throws Exception
	{
		List<Premises> premiseList = premisesDao.getAllPremises();
		if(premiseList != null && 0 < premiseList.size())
		{
			for(Premises premise : premiseList)
			{
				CompanyTarget target = companyTargetDao.getCompanyTargetByType(premise.getId(), 122); // Target type id for DOF
				
				List<GroupEvent> groupList = groupEventDao.getAllGroups(premise.getId());   
				if(groupList != null && 0 < groupList.size())
				{
					for(GroupEvent group : groupList)
					{
						try{						
						GroupStatusReportDataDto groupDto = new GroupStatusReportDataDto();
						groupDto.setGroupStartDate(group.getGroupStartDateTime());
						groupDto.setGroupEventId(group.getId());
						groupDto.setGroupId(group.getGroupId());
						groupDto.setGroupCloseDate(group.getGroupCloseDateTime()); 

						Calendar cal = Calendar.getInstance();
						cal.setTime(group.getGroupStartDateTime());
						int week = cal.get(Calendar.WEEK_OF_YEAR);
						groupDto.setCalendarWk(week);
						
						
						List<GroupEventDetails> groupEventDetailsList = groupEventDetailsDao.groupEventDetailsListByGroupId(group.getId());
							if(groupEventDetailsList != null && 0 <groupEventDetailsList.size())
							{
								for(GroupEventDetails eventDetails : groupEventDetailsList)
								{
									if(eventDetails.getSowSourceId() != null && eventDetails.getSowSourceId() > 0)
									{	
										groupDto.setSowSourceId(eventDetails.getSowSourceId());
										Premises sowSourcePremise = premisesDao.findByPremisesByAutoGeneratedId(groupDto.getSowSourceId());
										groupDto.setSowSource(sowSourcePremise != null ? sowSourcePremise.getPermiseId() : "");										
										break;
									}
								} 
							}
						
						
						GroupEvent barnRoomDetails = groupEventRoomDao.getGroupRoomAndBarnDetails(group.getId());
						if(barnRoomDetails == null)
							barnRoomDetails = new GroupEvent();
						
						groupDto.setPremiseId(group.getPremiseIdStr()); 
						groupDto.setBarnId(barnRoomDetails.getBarnId());
						groupDto.setRoomId(barnRoomDetails.getRoomId());
						groupDto.setPremiseIdPk(group.getPremiseId());
						
						Integer pigSpaces = barnRoomDetails.getRoomSpace();
						
						Map<String, Object> startWtAndHeadMap = groupEventDao.getStartWtAndHead(group.getId());
						groupDto.setStartWt((Double)startWtAndHeadMap.get("StartWt"));
						Long startHead  = 0L;
						startHead = (Long)startWtAndHeadMap.get("StartHd");
						groupDto.setStartHd(startHead); 
						 
						Integer phaseId = groupEventDao.getPhaseOfProduction(group.getId());
						groupDto.setPhaseTypeId(phaseId);
//						
//						if(phaseId != null && phaseId > 0)
//							record.put("PhaseType",refDataCache.getPhaseOfProductionTypeMap(language).get(phaseId));
//						else
//							record.put("PhaseType", " ");
						
						Integer deadCount = removalEventDao.getDeadsCount(DateUtil.getToday(), group.getId());
						groupDto.setDeads(deadCount); 
						
						Integer inventoryCnt = groupEventDao.getInventoryCount(DateUtil.getToday(), group.getId()); 
						if(inventoryCnt == null ) inventoryCnt = 0;
						groupDto.setInventory(inventoryCnt);
						
						Double density = 0D;
						
						if(inventoryCnt >0 && pigSpaces >0)
							density = Math.round(inventoryCnt*100.0)/(pigSpaces*100.0);
						groupDto.setDensity(density);
						
						Integer salesCount = saleEventDetailsDao.getSalesCount(DateUtil.getToday(), group.getId());
						if(salesCount == null ) salesCount = 0;
						groupDto.setSales(salesCount);
						
						Double mortalityPercentage = 0D;
						if(deadCount >0 && startHead > 0)
							mortalityPercentage =  (double) (deadCount*100.0)/(startHead); 
						groupDto.setMortalityPercentage(mortalityPercentage);
						
						Date projectedSalesDate = null;
						Integer dofDays = 0;
						
						if(target != null)
						{
							try{
								dofDays = Integer.parseInt(target.getTargetValue());
							}catch(NumberFormatException nfEx)
							{
								dofDays = 0;
							}
						}
						
						projectedSalesDate = DateUtil.addDays(group.getGroupStartDateTime(),dofDays);
						groupDto.setProjectedSaleDate(projectedSalesDate);
						
						cal = Calendar.getInstance();
						cal.setTime(projectedSalesDate);
						week = cal.get(Calendar.WEEK_OF_YEAR);
						groupDto.setProjectedSaleWk(week);
						
						Date ServDateSTART = groupDto.getGroupStartDate();				
						cal.add(Calendar.DAY_OF_MONTH, 6);
						
						Date ServDateEND = cal.getTime();
						
						Map<Integer, Integer> inventoryCntMap = groupEventDetailsDao.getInventoryCntByWeek(group.getId(),ServDateSTART, ServDateEND);
						groupDto.setInventoryCntMap(inventoryCntMap);
						
						Map<Integer, Integer> mortalityCntMap = removalEventDao.getMortalityCntByWeek(group.getId(),ServDateSTART, ServDateEND);
						groupDto.setMortalityCntMap(mortalityCntMap);
						
						//Delete the existing data
						//reportDataDao.delete(groupDto.getGroupEventId());
						
						//Insert new data
						reportDataDao.insert(groupDto);
						
						
					}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
					
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
