package com.pigtrax.report.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.pigtrax.report.dao.DataExtractionDao;
import com.pigtrax.util.DateUtil;

@Repository
public class DataExtractionService {
	
	private static final String seprater = ",";
	
	@Autowired
	DataExtractionDao extractionDao;
	
	@Autowired
	MessageSource messageSource;
	
	public List<String> getData(Integer premiseId, Integer pigId, Integer groupId, String reportOption, Integer eventTypeId, Date startDate, Date endDate, Locale locale)
	{		
		
		List<String> returnRows = new ArrayList<String>();
		
		List<String> dataValues = new ArrayList<String>();
		
		String rowHeader = populateReportHeader(eventTypeId, locale);
		
		returnRows.add(rowHeader+"\n");
		
		
		dataValues = getDataValues(premiseId,pigId, groupId,  reportOption, eventTypeId, startDate, endDate, locale.getLanguage());
		if(dataValues != null)
			returnRows.addAll(dataValues)	;
		

		return returnRows;
	}
	
	private String populateReportHeader(Integer eventTypeId, Locale locale)
	{
		String header = "";
		if(eventTypeId != null)
		{
			switch(eventTypeId)
			{
			case 1 : header = messageSource.getMessage("label.barn.room", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","
						+messageSource.getMessage("label.piginfo.entryeventform.tattoo", null, "", locale)+","+ messageSource.getMessage("label.piginfo.entryeventform.alternateTattoo", null, "", locale)+","
						+ messageSource.getMessage("label.piginfo.entryeventform.entryDate", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.sex", null, "", locale)+","
						+ messageSource.getMessage("label.leftmenu.managemasterdata.origin.link", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.gcompany", null, "", locale)+","
						+ messageSource.getMessage("label.piginfo.entryeventform.gline", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.gfunction", null, "", locale)+","
						+ messageSource.getMessage("label.piginfo.entryeventform.birthdate", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.sire", null, "", locale)+","
						+messageSource.getMessage("label.piginfo.entryeventform.dam", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.parity", null, "", locale);
							break;
			case 2 : header = messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","+messageSource.getMessage("label.piginfo.breedingeventform.serviceStartDate", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.breedingeventform.breedingServiceType", null, "", locale)+"," +messageSource.getMessage("label.piginfo.breedingeventform.breedinggroupId", null, "", locale)+","
							 +messageSource.getMessage("label.piginfo.entryeventform.sowcondition", null, "", locale)+","+messageSource.getMessage("label.piginfo.breedingeventform.weightInKgs", null, "", locale);
							break;	
			case 3 : header = messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","+messageSource.getMessage("label.piginfo.breedingeventform.employeegroup", null, "", locale)+","+messageSource.getMessage("label.piginfo.pregnancyeventform.pregnancyEventType", null, "", locale)+", "
							+  messageSource.getMessage("label.piginfo.pregnancyeventform.pregnancyExamResultType", null, "", locale)+","+  messageSource.getMessage("label.piginfo.pregnancyeventform.resultDate", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.sowcondition", null, "", locale);
							break;
			case 4 : header =messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.pen", null, "", locale)+"," +messageSource.getMessage("label.piginfo.farroweventform.farrowDateTime", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.farroweventform.liveborns", null, "", locale)+","+messageSource.getMessage("label.piginfo.farroweventform.stillborns", null, "", locale)+","+messageSource.getMessage("label.piginfo.farroweventform.mummies", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.farroweventform.weakborns", null, "", locale)+","+messageSource.getMessage("label.piginfo.farroweventform.maleborns", null, "", locale)+","+messageSource.getMessage("label.piginfo.farroweventform.femaleborns", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.farroweventform.weightInKgs", null, "", locale)+","+messageSource.getMessage("label.piginfo.farroweventform.typeOfBirth", null, "", locale)+","+messageSource.getMessage("label.piginfo.breedingeventform.employeegroup", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.entryeventform.sowcondition", null, "", locale)+","+messageSource.getMessage("label.piginfo.farroweventform.teats", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale);					
							break;	
			case 5 : header = messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","+messageSource.getMessage("label.piginfo.pigletstatuseventform.pigletStatusEventType", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.pigletstatuseventform.eventDateTime", null, "", locale)+","+messageSource.getMessage("label.groupEventDetail.numberOfPigs", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.breedingeventform.weightInKgs", null, "", locale)+","+messageSource.getMessage("label.piginfo.pigletstatuseventform.weaninggroupid", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.pigletstatuseventform.fosterToPigId", null, "", locale)+","+messageSource.getMessage("label.piginfo.pigletstatuseventform.mortalityreason", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.entryeventform.pen", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.entryeventform.sowcondition", null, "", locale);							
							break;	
			case 6 : header =  messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.removalExceptSales.removalDateTime", null, "", locale)+","+messageSource.getMessage("label.piginfo.removalEventform.removalTypeId", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.pigletstatuseventform.mortalityreason", null, "", locale)+","+messageSource.getMessage("label.groupEventDetail.numberOfPigs", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.removalExceptSales.weightInKgs", null, "", locale)+","+messageSource.getMessage("label.piginfo.removalExceptSales.revenueUsd", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale);
							break;
			case 7 : header =messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.removalExceptSales.salesDateTime", null, "", locale)+","+messageSource.getMessage("label.groupEventDetail.numberOfPigs", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.removalEventform.salesType", null, "", locale)+","+messageSource.getMessage("label.piginfo.removalEventform.salesReason", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.feedEventForm.ticketNumber", null, "", locale)+","+messageSource.getMessage("label.piginfo.removalExceptSales.invoiceId", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.removalExceptSales.soldTo", null, "", locale)+","+messageSource.getMessage("label.transportJourney.transportTruckId", null, "", locale)+","
							+messageSource.getMessage("label.transportJourney.transportTrailerId", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale);
							break;
			case 8 : header =messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.removalExceptSales.transferDateTime", null, "", locale)+","+messageSource.getMessage("label.groupEventDetail.numberOfPigs", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.removalExceptSales.weightInKgs", null, "", locale)+","+messageSource.getMessage("label.piginfo.removalExceptSales.premiseIdFrom", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.removalExceptSales.premiseIdTo", null, "", locale)+","+messageSource.getMessage("label.piginfo.removalExceptSales.toRoomId", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.removalExceptSales.revenueUsd", null, "", locale)+","+messageSource.getMessage("label.transportJourney.transportTruckId", null, "", locale)+","
							+messageSource.getMessage("label.transportJourney.transportTrailerId", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale);
							break;
			case 9 : header = messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","+messageSource.getMessage("label.feedEventDetail.feedEventDate", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.feedEventForm.ticketNumber", null, "", locale)+","+messageSource.getMessage("label.piginfo.feedEventForm.batchId", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.feedEventForm.feedMedication", null, "", locale)+","
							+messageSource.getMessage("label.transportJourney.transportTruckId", null, "", locale)+","+messageSource.getMessage("label.transportJourney.transportTrailerId", null, "", locale)+","
							+messageSource.getMessage("label.feedEventDetail.feedmill", null, "", locale)+","+messageSource.getMessage("label.feedEventDetail.feedEventTypeId", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.breedingeventform.weightInKgs", null, "", locale)+","+messageSource.getMessage("label.piginfo.feedEventForm.feedCost", null, "", locale)+","
							+messageSource.getMessage("label.feedEventDetail.siloId", null, "", locale)+","+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale);
							break;
			case 10 : header =messageSource.getMessage("label.barn.room", null, "", locale)+","+ messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","
							+ messageSource.getMessage("label.productionlogform.observationDate", null, "", locale)+","+ messageSource.getMessage("label.productionlogform.logeventType", null, "", locale)+","
							+messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale);
									break;
			case 11 : header = messageSource.getMessage("label.piginfo.groupEventForm.groupId", null, "", locale)+","+ messageSource.getMessage("label.barn.room", null, "", locale)+","
							+ messageSource.getMessage("label.piginfo.groupEventForm.groupStartDateTime", null, "", locale)+","+ messageSource.getMessage("label.piginfo.groupEventForm.groupCloseDateTime", null, "", locale)+","
							+ messageSource.getMessage("label.groupEventDetail.phaseOfProductionTypeId", null, "", locale)+","+ messageSource.getMessage("label.piginfo.entryeventform.remarks", null, "", locale);
								break;
			case 12 : header =  messageSource.getMessage("label.piginfo.entryeventform.pigid", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.litterId", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.tattooId", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.weightAtBirth", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.weightAtWeaning", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.weight1", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.date1", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.weight2", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.date1", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.weight3", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.date1", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.weight4", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.date1", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.weight5", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.date1", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.weight6", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.date1", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.weight7", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.date1", null, "", locale)+","+ messageSource.getMessage("label.piginfo.pigleteventform.weight8", null, "", locale)+","
								+messageSource.getMessage("label.piginfo.pigleteventform.date1", null, "", locale);
								break;
			}
		}
		return header;
	}
	
	
	
	List<String> getDataValues(Integer premiseId, Integer pigId, Integer groupId, String reportOption, Integer eventType, Date startDate, Date endDate, String language)
	{
		String query = "";
		List<String> returnRows = new ArrayList<String>();
		java.sql.Date start = new java.sql.Date(startDate.getTime());
		java.sql.Date end = new java.sql.Date(endDate.getTime());
		List<Map<String, Object>> resultList = null;
		
		if(reportOption != null)
		{
			switch(eventType)
			{
			case 1 : query = " select PI.\"pigId\", PI.\"sireId\", PI.\"damId\", PI.\"entryDate\", GL.\"fieldValue\" as \"gline\", GC.\"fieldValue\" as \"gcompany\", PI.\"birthDate\", PI.\"tattoo\", PI.\"alternateTattoo\", "
					+ "		R.\"roomId\", ST.\"fieldValue\" as \"sexType\", GF.\"fieldValue\" as \"gfunction\", O.\"name\" as \"origin\", PI.\"parity\", PI.\"remarks\" "
					+ "    FROM pigtrax.\"PigInfo\" PI left join  pigtraxrefdata.\"GlineTypeTranslation\" GL ON PI.\"gline\"=GL.\"id_GlineType\" and GL.\"fieldLanguage\" = '"+language+"' "
					+ "		left join  pigtraxrefdata.\"GcompanyTypeTranslation\" GC  ON PI.\"gcompany\"=GC.\"id_GcompanyType\" and GC.\"fieldLanguage\" = '"+language+"' "
					+ "		left join  pigtraxrefdata.\"GfunctionTypeTranslation\" GF ON PI.\"id_GfunctionType\"=GF.\"id_GfunctionType\" and GF.\"fieldLanguage\" = '"+language+"'  "
					+ "		left join  pigtraxrefdata.\"SexTypeTranslation\" ST ON PI.\"id_SexType\"=ST.\"id_SexType\" and ST.\"fieldLanguage\" = '"+language+"' "
					+ "	    left join pigtrax.\"Room\" R on  PI.\"id_Room\" = R.\"id\" "
					+ " 	LEFT JOIN pigtrax.\"Origin\" O ON PI.\"id_Origin\" = O.\"id\" "  
					+ "		Where PI.\"id_Premise\" = "+premiseId+" and PI.\"entryDate\" between '"+start+"' and '"+end+"' and PI.\"isActive\" is true ";
					if(reportOption.equalsIgnoreCase("pigId") && pigId != null)
							query += " and PI.\"id\" = "+pigId;
						
						resultList = extractionDao.getPigInfoData(query);
						returnRows = populateRows(resultList, eventType);
							
							break;
			case 2 : query = "select PI.\"pigId\", BST.\"fieldValue\" as \"serviceType\",BE.\"serviceGroupId\", BE.\"serviceStartDate\", BE.\"weightInKgs\", BE.\"sowCondition\"  "
									+ "	from pigtrax.\"BreedingEvent\" BE Join pigtrax.\"PigInfo\" PI ON BE.\"id_PigInfo\" = PI.\"id\""
									+ " left join pigtraxrefdata.\"BreedingServiceTypeTranslation\" BST on BE.\"id_BreedingServiceType\" = BST.\"id_BreedingServiceType\" and 	BST.\"fieldLanguage\" = '"+language+"'	"
									+ "  where  PI.\"id_Premise\" = "+premiseId+" and BE.\"serviceStartDate\" 	between '"+start+"' and '"+end+"' and PI.\"isActive\" is true ";
			
								if(reportOption.equalsIgnoreCase("pigId") && pigId != null)
									query += " and PI.\"id\" = "+pigId;
			
							 resultList = extractionDao.getBreedingData(query);
								returnRows = populateRows(resultList, eventType);
										break;	
			case 3 : query = " select PI.\"pigId\", EG.\"groupId\", PET.\"fieldValue\" as \"pregnancyEventType\",PERT.\"fieldValue\" as \"pregnancyResultType\", PE.\"resultDate\", PE.\"sowCondition\" "
					+ "  from pigtrax.\"PregnancyEvent\" PE join pigtrax.\"PigInfo\" PI ON PE.\"id_PigInfo\" = PI.\"id\" "
					+ "	 Left join pigtrax.\"EmployeeGroup\" EG ON EG.\"id\" = PE.\"id_EmployeeGroup\" "
					+ "left join pigtraxrefdata.\"PregnancyEventTypeTranslation\" PET on PE.\"id_PregnancyEventType\"  = PET.\"id_PregnancyEventType\" and PET.\"fieldLanguage\" = '"+language+"' "
					+ " left join pigtraxrefdata.\"PregnancyExamResultTypeTranslation\" PERT ON PE.\"id_PregnancyExamResultType\" = PERT.\"id_PregnancyExamResultType\" and PERT.\"fieldLanguage\" = '"+language+"' "
					+ "  where  PI.\"id_Premise\" = "+premiseId+" and PE.\"resultDate\" between '"+start+"' and '"+end+"' and PI.\"isActive\" is true ";
			if(reportOption.equalsIgnoreCase("pigId") && pigId != null)
				query += " and PI.\"id\" = "+pigId;
			 resultList = extractionDao.getPregnancyData(query);
				returnRows = populateRows(resultList, eventType);
							break;
			case 4 : query = "select PI.\"pigId\", PEN.\"penId\",FE.\"farrowDateTime\",FE.\"liveBorns\", FE.\"stillBorns\", FE.\"mummies\", FE.\"weakBorns\", FE.\"maleBorns\", FE.\"femaleBorns\",\"weightInKgs\", FE.\"inducedBirth\", FE.\"assistedBirth\","
					+ " EG.\"groupId\", FE.\"sowCondition\",FE.\"teats\", FE.\"remarks\" from pigtrax.\"FarrowEvent\" FE Join pigtrax.\"PigInfo\" PI ON FE.\"id_PigInfo\" = PI.\"id\" "
					+ "Join pigtrax.\"Pen\" PEN ON PEN.\"id\" = FE.\"id_Pen\" left join pigtrax.\"EmployeeGroup\" EG ON FE.\"id_EmployeeGroup\" = EG.\"id\""
					+ "  where  PI.\"id_Premise\" = "+premiseId+" and FE.\"farrowDateTime\" between '"+start+"' and '"+end+"' and PI.\"isActive\" is true ";
			if(reportOption.equalsIgnoreCase("pigId") && pigId != null)
				query += " and PI.\"id\" = "+pigId;
			
			 resultList = extractionDao.getFarrowData(query);
				returnRows = populateRows(resultList, eventType);
							break;	
			case 5 : query = "select PI.\"pigId\", PSET.\"fieldValue\" as \"eventType\", PS.\"numberOfPigs\", PS.\"weightInKgs\", PS.\"eventDateTime\", GE.\"groupId\", TTP.\"pigId\" as \"transferToPig\", "
					+ "MRTT.\"fieldValue\" as \"mortalityReason\" ,PEN.\"penId\",PS.\"remarks\",PS.\"sowCondition\" "
								+ " from pigtrax.\"PigletStatus\" PS join pigtrax.\"PigInfo\" PI ON PS.\"id_PigInfo\" = PI.\"id\"	"
								+ " left join pigtraxrefdata.\"PigletStatusEventTypeTranslation\" PSET ON PS.\"id_PigletStatusEventType\" = PSET.\"id_PigletStatusEventType\" and PSET.\"fieldLanguage\" = '"+language+"'"
								+ " left join pigtrax.\"GroupEvent\" GE ON PS.\"id_GroupEvent\" = GE.\"id\" "
								+ " left join pigtrax.\"PigInfo\" TTP ON TTP.\"id\" = PS.\"fosterTo\" "
								+" Left join pigtrax.\"Pen\" PEN ON PEN.\"id\" = PS.\"id_Pen\" "
								+ " left join pigtraxrefdata.\"MortalityReasonTypeTranslation\" MRTT ON PS.\"id_MortalityReasonType\" = MRTT.\"id_MortalityReasonType\" and MRTT.\"fieldLanguage\" = '"+language+"' "
								+ " where  PI.\"id_Premise\" = "+premiseId+" and PS.\"eventDateTime\" between '"+start+"' and '"+end+"' and PI.\"isActive\" is true ";
			if(reportOption.equalsIgnoreCase("pigId") && pigId != null)
				query += " and PI.\"id\" = "+pigId;
			
			 resultList = extractionDao.getPigletStatusData(query);
				returnRows = populateRows(resultList, eventType);
							break;	
			case 6 : query = "select GE.\"groupId\", PI.\"pigId\",RES.\"removalDateTime\",RT.\"fieldValue\" as \"removalType\" ,MRTT.\"fieldValue\" as \"mortalityReason\" ,RES.\"numberOfPigs\",RES.\"weightInKgs\",RES.\"revenueUsd\" as \"revenue\",RES.\"remarks\" "
													+ "	 From pigtrax.\"RemovalEventExceptSalesDetails\" RES Left join pigtrax.\"GroupEvent\" GE ON RES.\"id_GroupEvent\" = GE.\"id\" "
													+ "  left join pigtrax.\"PigInfo\" PI ON RES.\"id_PigInfo\" = PI.\"id\" "
													+ " join pigtraxrefdata.\"RemovalEventTypeTranslation\" RT ON RES.\"id_RemovalEvent\" = RT.\"id_RemovalType\" and RT.\"fieldLanguage\" = '"+language+"' "
													+ " left join pigtraxrefdata.\"MortalityReasonTypeTranslation\" MRTT ON RES.\"id_MortalityReason\" = MRTT.\"id_MortalityReasonType\" and MRTT.\"fieldLanguage\" = '"+language+"' "
													+ "  where RES.\"id_Premise\" = "+premiseId+" and RES.\"removalDateTime\" between '"+start+"' and '"+end+"'  and RES.\"id_RemovalEvent\" != 9 ";
													if(reportOption.equalsIgnoreCase("pigId"))
													{
														query += " and RES.\"id_GroupEvent\" IS NULL ";
														 if(pigId != null)
															 query += " and RES.\"id_PigInfo\" = "+pigId;
													}
													else if(reportOption.equalsIgnoreCase("groupId"))
													{
														query += " and RES.\"id_PigInfo\" IS NULL ";
														if(groupId != null)
															query += " and RES.\"id_GroupEvent\" = "+groupId;
													}
													 resultList = extractionDao.getMortalityAdjustmentDate(query);
														returnRows = populateRows(resultList, eventType);
													break;
			case 7 : query = "select GE.\"groupId\", PI.\"pigId\", SE.\"salesDateTime\",  SE.\"numberOfPigs\", STT.\"fieldValue\" as \"salesType\", SRT.\"fieldValue\" as \"salesReason\",SE.\"ticketNumber\",SE.\"invoiceId\",SE.\"revenueUsd\", SE.\"soldTo\", TTK.\"truckId\", TTR.\"trailerId\",SE.\"remarks\" "
									+ "	from pigtrax.\"SalesEventDetails\" SE Left join pigtrax.\"GroupEvent\" GE ON SE.\"id_GroupEvent\" = GE.\"id\" and GE.\"id_Premise\" = "+premiseId
									+ " left join pigtrax.\"PigInfo\" PI ON SE.\"id_PigInfo\" = PI.\"id\"  and PI.\"id_Premise\" = "+premiseId 
									+ " left join pigtraxrefdata.\"SalesTypeTranslation\" STT ON SE.\"salesTypes\" = STT.\"id_SalesType\"::text and STT.\"fieldLanguage\" = '"+language+"' "
									+ " left join pigtraxrefdata.\"SalesReasonsTranslation\" SRT ON SE.\"salesReasons\" = SRT.\"id_SalesReason\"::text and SRT.\"fieldLanguage\" = '"+language+"' "
									+" left join pigtrax.\"TransportJourney\" TJ ON SE.\"id_TransportJourney\" = TJ.\"id\" "
									+ " left join pigtrax.\"TransportTruck\" TTK ON TJ.\"id_TransportTruck\" = TTK.\"id\" "
									+ " left join pigtrax.\"TransportTrailer\" TTR ON TJ.\"id_TransportTrailer\" = TTR.\"id\"  "
									+ " where SE.\"salesDateTime\"  between '"+start+"' and '"+end+"' and (GE.\"groupId\" IS NOT NULL OR PI.\"pigId\" IS NOT NULL) ";
									if(reportOption.equalsIgnoreCase("pigId"))
									{
										query += " and SE.\"id_GroupEvent\" IS NULL ";
										 if(pigId != null)
											 query += " and SE.\"id_PigInfo\" = "+pigId;
									}
									else if(reportOption.equalsIgnoreCase("groupId"))
									{
										query += " and SE.\"id_PigInfo\" IS NULL ";
										if(groupId != null)
											query += " and SE.\"id_GroupEvent\" = "+groupId;
									}
									 resultList = extractionDao.getSalesData(query);
										returnRows = populateRows(resultList, eventType);
											break;
			case 8 : 	query = "select GE.\"groupId\", PI.\"pigId\",RES.\"removalDateTime\",RES.\"numberOfPigs\",RES.\"weightInKgs\", PRE.\"permiseId\" as \"fromPremise\", "
					+ "TPRE.\"permiseId\" as \"toPremise\", TR.\"roomId\", RES.\"revenueUsd\" as \"revenue\",TTK.\"truckId\", TTR.\"trailerId\", RES.\"remarks\" "
					+ "	 From pigtrax.\"RemovalEventExceptSalesDetails\" RES Left join pigtrax.\"GroupEvent\" GE ON RES.\"id_GroupEvent\" = GE.\"id\" "
					+ "  left join pigtrax.\"PigInfo\" PI ON RES.\"id_PigInfo\" = PI.\"id\" "
					+ " left join pigtrax.\"Premise\" PRE ON RES.\"id_Premise\" = PRE.\"id\" "
					+ " left join pigtrax.\"Premise\" TPRE ON RES.\"id_DestPremise\" = TPRE.\"id\" "
					+ " left join pigtrax.\"Room\" TR ON RES.\"id_Room\" = TR.\"id\" "
					+" left join pigtrax.\"TransportJourney\" TJ ON RES.\"id_TransportJourney\" = TJ.\"id\" "
					+ " left join pigtrax.\"TransportTruck\" TTK ON TJ.\"id_TransportTruck\" = TTK.\"id\" "
					+ " left join pigtrax.\"TransportTrailer\" TTR ON TJ.\"id_TransportTrailer\" = TTR.\"id\"  "
					+ "  where RES.\"id_Premise\" = "+premiseId+" and RES.\"removalDateTime\" between '"+start+"' and '"+end+"' and RES.\"id_RemovalEvent\" = 9";
			
						if(reportOption.equalsIgnoreCase("pigId"))
						{
							query += " and RES.\"id_GroupEvent\" IS NULL ";
							 if(pigId != null)
								 query += " and RES.\"id_PigInfo\" = "+pigId;
						}
						else if(reportOption.equalsIgnoreCase("groupId"))
						{
							query += " and RES.\"id_PigInfo\" IS NULL ";
							if(groupId != null)
								query += " and RES.\"id_GroupEvent\" = "+groupId;
						}
						 resultList = extractionDao.getTransferData(query);
							returnRows = populateRows(resultList, eventType);
								break;
			case 9 : query = "  select GE.\"groupId\", FED.\"feedEventDate\",FE.\"ticketNumber\", MR.\"rationValue\", FE.\"feedMedication\", TTK.\"truckId\", " 
						+" TTR.\"trailerId\", FED.\"feedMill\", FET.\"fieldValue\" as \"feedEventType\", FED.\"weightInKgs\", FED.\"feedCost\", SI.\"siloId\", FED.\"remarks\" "
						+"	from pigtrax.\"FeedEventDetails\" FED JOIN pigtrax.\"FeedEvent\" FE ON FED.\"id_FeedEvent\" = FE.\"id\" "
						+"		JOIN pigtrax.\"GroupEvent\" GE ON FED.\"id_GroupEvent\" = GE.\"id\" "
						+"		JOIN pigtrax.\"Silo\" SI on FED.\"id_Silo\" = SI.\"id\" "
						+"		JOIN pigtrax.\"MasterRation\" MR on FE.\"batchId\" = MR.\"id\" "
						+"		JOIN pigtraxrefdata.\"FeedEventTypeTranslation\" FET ON FET.\"id_FeedEventType\" = FED.\"id_FeedEventType\" and FET.\"fieldLanguage\" = '"+language+"'"
						+"		LEFT JOIN pigtrax.\"TransportJourney\" TJ ON FE.\"id_TransportJourney\"= TJ.\"id\"  "
						+"	LEFT Join pigtrax.\"TransportTruck\" TTK ON TJ.\"id_TransportTruck\" = TTK.\"id\" "
						+"		LEFT Join pigtrax.\"TransportTrailer\" TTR ON TJ.\"id_TransportTruck\" = TTR.\"id\" WHERE FE.\"id_Premise\" = "+premiseId+" and FED.\"feedEventDate\" between '"+start+"' and '"+end+"' ";
			 		if(reportOption.equalsIgnoreCase("groupId") && groupId != null)
			 			query += " and FED.\"id_GroupEvent\" = "+groupId;
			 		 resultList = extractionDao.getFeedData(query);
						returnRows = populateRows(resultList, eventType);
								break;
			case 10 : query = " select R.\"roomId\", PL.\"groupId\", PL.\"observationDate\", LET.\"fieldValue\" as \"logEventType\", PL.\"observation\" from pigtrax.\"ProductionLog\" PL "
							+ " left join pigtrax.\"Room\" R on PL.\"id_Room\" = R.\"id\" JOIN pigtraxrefdata.\"LogEventTypeTranslation\" LET ON LET.\"id_LogEventType\" = PL.\"id_LogEventType\" "
							+ "and LET.\"fieldLanguage\" = '"+language+"' "
							+" where PL.\"id_Premise\" = "+premiseId+" and PL.\"observationDate\" between '"+start+ "' and '"+end+"'";
			 resultList = extractionDao.getProductionLogData(query);
				returnRows = populateRows(resultList, eventType);
								break;
			case 11 : query = " select GE.\"groupId\", R.\"roomId\", GE.\"groupStartDateTime\", GE.\"groupCloseDateTime\", PPT.\"fieldValue\" as \"phaseOfProductionType\", GE.\"remarks\" "
							+" from pigtrax.\"GroupEvent\" GE " 
							+" Join pigtrax.\"GroupEventPhaseChange\" GEPC ON GEPC.\"id_GroupEvent\" = GE.\"id\" and GEPC.\"phaseEndDate\" is NULL "
							+" Join pigtrax.\"GroupEventRoom\" GER ON GER.\"id_GroupEventPhaseChange\" = GEPC.\"id\" " 
							+" LEFT Join pigtraxrefdata.\"PhaseOfProductionTypeTranslation\" PPT ON PPT.\"id_PhaseOfProductionType\" = GEPC.\"id_PhaseOfProductionType\" and PPT.\"fieldLanguage\" = 'en' "
							+" Join pigtrax.\"Room\" R ON R.\"id\" = GER.\"id_Room\" "
							+" JOIN pigtrax.\"Barn\" B ON B.\"id\" = R.\"id_Barn\" " 
							+" where B.\"id_Premise\" = "+premiseId+" and  GE.\"groupStartDateTime\" between '"+start+"' and '"+end+"' "; 
						if(reportOption.equalsIgnoreCase("groupId") && groupId != null)
								query += " and GE.\"id_GroupEvent\" = "+groupId;
						 resultList = extractionDao.getGroupEventData(query);
							returnRows = populateRows(resultList, eventType);
								break;
			case 12 : query = " select IP.\"pigId\", IP.\"litterId\", IP.\"tattooId\", IP.\"weightAtBirth\", IP.\"weightAtWeaning\", IP.\"weight1\", "
					+ "			IP.\"date1\", IP.\"weight2\", IP.\"date2\", IP.\"weight3\", IP.\"date3\", "
					+ " 		IP.\"weight4\", IP.\"date4\", IP.\"weight5\", IP.\"date5\", IP.\"weight6\", IP.\"date6\" , IP.\"weight7\", IP.\"date7\" , IP.\"weight8\", IP.\"date8\" " 
					+" from pigtrax.\"IndividualPigletStatus\" IP  "
					+" where IP.\"id_Premise\" = "+premiseId+" and (IP.\"date1\" between '"+start+"' and '"+end+"'  ) ";
/*			case 12 : query = " select PI.\"pigId\", IP.\"litterId\", IP.\"tattooId\", IP.\"weightAtBirth\", IP.\"weightAtWeaning\", IP.\"weight1\", "
					+ "			IP.\"date1\", IP.\"weight2\", IP.\"date2\", IP.\"weight3\", IP.\"date3\", "
					+ " 		IP.\"weight4\", IP.\"date4\", IP.\"weight5\", IP.\"date5\", IP.\"weight6\", IP.\"date6\" " 
					+" from pigtrax.\"IndividualPigletStatus\" IP JOIN pigtrax.\"PigInfo\" PI ON IP.\"id_PigInfo\" = PI.\"id\" "
					+" where PI.\"id_Premise\" = "+premiseId+" and (IP.\"date1\" between '"+start+"' and '"+end+"' OR IP.\"date2\" between '"+start+"' and '"+end+"' OR IP.\"date3\" between '"+start+"' and '"+end+"' "
					+ "OR IP.\"date4\" between '"+start+"' and '"+end+"' OR IP.\"date5\" between '"+start+"' and '"+end+"' OR IP.\"date6\" between '"+start+"' and '"+end+"' ) ";
*/			
			//if(reportOption.equalsIgnoreCase("pigId") && pigId != null)
				//query += " and PI.\"id\" = "+pigId;
			 resultList = extractionDao.getPigletEventData(query);
				returnRows = populateRows(resultList, eventType);
								break;
			}
		}
		return returnRows; 
	}
	
	
	private List<String> populateRows(List<Map<String, Object>> resultList, Integer eventType)
	{
		ArrayList<String> returnRows = new ArrayList<String>();
		StringBuffer rowBuffer = null;
		if(resultList != null)
		{
			switch(eventType)
			{
			case 1 : for(Map<String, Object> rowMap : resultList)
							{
								rowBuffer = new StringBuffer();
								rowBuffer.append(rowMap.get("roomId") + seprater);
								rowBuffer.append(rowMap.get("pigId") + seprater);
								rowBuffer.append(rowMap.get("tattoo") + seprater);
								rowBuffer.append(rowMap.get("alternateTattoo") + seprater);
								try {
									if(rowMap.get("entryDate") != null)
										rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("entryDate"),"dd/MM/yyyy") + seprater);
									else 
										rowBuffer.append("" + seprater);
								} catch (ParseException e) {
									rowBuffer.append("" + seprater);
								}
								rowBuffer.append(rowMap.get("sexType") + seprater);
								rowBuffer.append(rowMap.get("origin") + seprater);
								rowBuffer.append(rowMap.get("gcompany") + seprater);
								rowBuffer.append(rowMap.get("gline") + seprater);
								rowBuffer.append(rowMap.get("gfunction") + seprater);
								try {
									if(rowMap.get("birthDate") != null)
										rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("birthDate"),"dd/MM/yyyy") + seprater);
									else 
										rowBuffer.append("" + seprater);
								} catch (ParseException e) {
									rowBuffer.append("" + seprater);
								}
								rowBuffer.append(rowMap.get("sireId") + seprater);
								rowBuffer.append(rowMap.get("damId") + seprater);
								rowBuffer.append(rowMap.get("remarks"));
								returnRows.add(rowBuffer.toString()+"\n");
							}
							break;
			case 2 : for(Map<String, Object> rowMap : resultList)
					{
						rowBuffer = new StringBuffer();				
						rowBuffer.append(rowMap.get("pigId") + seprater);
						try {
							if(rowMap.get("serviceStartDate") != null)
								rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("serviceStartDate"),"dd/MM/yyyy") + seprater);
							else
								rowBuffer.append("" + seprater);
						} catch (ParseException e) {
							rowBuffer.append("" + seprater);
						}
						rowBuffer.append(rowMap.get("serviceType") + seprater);
						rowBuffer.append(rowMap.get("serviceGroupId") + seprater);				
						rowBuffer.append(rowMap.get("sowCondition")+ seprater);
						rowBuffer.append(rowMap.get("weightInKgs") + seprater);
						
						returnRows.add(rowBuffer.toString()+"\n");
					}
					break;
					
			case 3 : for(Map<String, Object> rowMap : resultList)
							{
								rowBuffer = new StringBuffer();				
								rowBuffer.append(rowMap.get("pigId") + seprater);
								rowBuffer.append(rowMap.get("groupId") + seprater);
								rowBuffer.append(rowMap.get("pregnancyEventType") + seprater);	
								rowBuffer.append(rowMap.get("pregnancyResultType") + seprater);
								try {
									if(rowMap.get("resultDate") != null)
									rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("resultDate"),"dd/MM/yyyy") + seprater);
									else rowBuffer.append("" + seprater);
								} catch (ParseException e) {
									rowBuffer.append("" + seprater);
								}
								rowBuffer.append(rowMap.get("sowCondition"));
								returnRows.add(rowBuffer.toString()+"\n");
							}
							break;
			
							
			case 4 : for(Map<String, Object> rowMap : resultList)
							{
								rowBuffer = new StringBuffer();				
								rowBuffer.append(rowMap.get("pigId") + seprater);
								rowBuffer.append(rowMap.get("penId") + seprater);
								try {
									if(rowMap.get("farrowDateTime") != null)
									rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("farrowDateTime"),"dd/MM/yyyy") + seprater);
									else
										rowBuffer.append("" + seprater);
								} catch (ParseException e) {
									rowBuffer.append("" + seprater);
								}
								rowBuffer.append(rowMap.get("liveBorns"));
					
								rowBuffer.append(rowMap.get("stillBorns") + seprater);	
								rowBuffer.append(rowMap.get("mummies") + seprater);
								rowBuffer.append(rowMap.get("weakBorns") + seprater);
								rowBuffer.append(rowMap.get("maleBorns") + seprater);
								rowBuffer.append(rowMap.get("femaleBorns") + seprater);
								rowBuffer.append(rowMap.get("weight") + seprater);
								rowBuffer.append(rowMap.get("inducedBirth") + seprater);
								rowBuffer.append(rowMap.get("assistedBirth") + seprater);
								rowBuffer.append(rowMap.get("groupId") + seprater);
								rowBuffer.append(rowMap.get("sowCondition") + seprater);
								rowBuffer.append(rowMap.get("teats") + seprater);
								rowBuffer.append(rowMap.get("remarks") + seprater);
								returnRows.add(rowBuffer.toString()+"\n");
							}
							break;
				case 5 : for(Map<String, Object> rowMap : resultList)
					{
						rowBuffer = new StringBuffer();				
						rowBuffer.append(rowMap.get("pigId") + seprater);
						rowBuffer.append(rowMap.get("eventType") + seprater);
						try {
							if(rowMap.get("eventDateTime") != null)
							rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("eventDateTime"),"dd/MM/yyyy") + seprater);
							else
								rowBuffer.append("" + seprater);
						} catch (ParseException e) {
							rowBuffer.append("" + seprater);
						}
						rowBuffer.append(rowMap.get("numberOfPigs") + seprater);
			
						rowBuffer.append(rowMap.get("weightInKgs") + seprater);	
						rowBuffer.append(rowMap.get("groupId") + seprater);
						rowBuffer.append(rowMap.get("transferToPig") + seprater);
						rowBuffer.append(rowMap.get("mortalityReason") + seprater);
						rowBuffer.append(rowMap.get("penId") + seprater);
						rowBuffer.append(rowMap.get("remarks") + seprater);
						rowBuffer.append(rowMap.get("sowCondition"));						
						returnRows.add(rowBuffer.toString()+"\n");
					}
					break;
				
				case 6 : for(Map<String, Object> rowMap : resultList)
				{	
					
					rowBuffer = new StringBuffer();				
					rowBuffer.append(rowMap.get("groupId") + seprater);
					rowBuffer.append(rowMap.get("pigId") + seprater);
					try {
						if(rowMap.get("removalDateTime") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("removalDateTime"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					rowBuffer.append(rowMap.get("removalType")+seprater);
		
					rowBuffer.append(rowMap.get("mortalityReason") + seprater);	
					rowBuffer.append(rowMap.get("numberOfPigs") + seprater);
					rowBuffer.append(rowMap.get("weightInKgs") + seprater);
					rowBuffer.append(rowMap.get("revenue") + seprater);
					rowBuffer.append(rowMap.get("remarks") );
					returnRows.add(rowBuffer.toString()+"\n");
				}
				break;
				
				case 7 : for(Map<String, Object> rowMap : resultList)
				{	
					
					rowBuffer = new StringBuffer();				
					rowBuffer.append(rowMap.get("groupId") + seprater);
					rowBuffer.append(rowMap.get("pigId") + seprater);
					try {
						if(rowMap.get("salesDateTime") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("salesDateTime"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					rowBuffer.append(rowMap.get("numberOfPigs") + seprater);
					rowBuffer.append(rowMap.get("salesType") + seprater);
					rowBuffer.append(rowMap.get("salesReason") + seprater);
					rowBuffer.append(rowMap.get("ticketNumber")  + seprater);
					rowBuffer.append(rowMap.get("invoiceId")  + seprater);
					rowBuffer.append(rowMap.get("revenueUsd")  + seprater);
					rowBuffer.append(rowMap.get("soldTo")  + seprater);
					rowBuffer.append(rowMap.get("truckId")  + seprater);
					rowBuffer.append(rowMap.get("trailerId")  + seprater);
					rowBuffer.append(rowMap.get("remarks") );
					
					returnRows.add(rowBuffer.toString()+"\n");
				}
				break;
				
				case 8 : for(Map<String, Object> rowMap : resultList)
				{	
					
					rowBuffer = new StringBuffer();				
					rowBuffer.append(rowMap.get("groupId") + seprater);
					rowBuffer.append(rowMap.get("pigId") + seprater);
					try {
						if(rowMap.get("removalDateTime") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("removalDateTime"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					rowBuffer.append(rowMap.get("numberOfPigs") + seprater);
					rowBuffer.append(rowMap.get("weightInKgs") + seprater);
					rowBuffer.append(rowMap.get("fromPremise") + seprater);
					rowBuffer.append(rowMap.get("toPremise") + seprater);
					rowBuffer.append(rowMap.get("roomId") + seprater);
					rowBuffer.append(rowMap.get("revenue")  + seprater);
					rowBuffer.append(rowMap.get("truckId")  + seprater);
					rowBuffer.append(rowMap.get("trailerId")  + seprater);
					rowBuffer.append(rowMap.get("remarks")  );
					
					returnRows.add(rowBuffer.toString()+"\n");
				}
				break;
				
				case 9 : for(Map<String, Object> rowMap : resultList)
				{	
									
					rowBuffer = new StringBuffer();				
					rowBuffer.append(rowMap.get("groupId") + seprater);
					try {
						if(rowMap.get("feedEventDate") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("feedEventDate"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					rowBuffer.append(rowMap.get("ticketNumber") + seprater);
					rowBuffer.append(rowMap.get("batchId") + seprater);
					rowBuffer.append(rowMap.get("feedMedication") + seprater);
					rowBuffer.append(rowMap.get("truckId") + seprater);
					rowBuffer.append(rowMap.get("trailerId") + seprater);
					rowBuffer.append(rowMap.get("feedMill")  + seprater);
					rowBuffer.append(rowMap.get("feedEventType")  + seprater);
					rowBuffer.append(rowMap.get("weightInKgs")  + seprater);
					rowBuffer.append(rowMap.get("feedCost")  + seprater);
					rowBuffer.append(rowMap.get("siloId")  + seprater);
					rowBuffer.append(rowMap.get("remarks")  );
					
					returnRows.add(rowBuffer.toString()+"\n");
				}
				break;
				
				case 10 : for(Map<String, Object> rowMap : resultList)
				{	
									
					rowBuffer = new StringBuffer();				
					rowBuffer.append(rowMap.get("roomId") + seprater);
					rowBuffer.append(rowMap.get("groupId") + seprater);
					try {
						if(rowMap.get("observationDate") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("observationDate"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					rowBuffer.append(rowMap.get("logEventType") + seprater);
					rowBuffer.append(rowMap.get("observation")  );
					
					returnRows.add(rowBuffer.toString()+"\n");
				}
				break;
				
				case 11 : for(Map<String, Object> rowMap : resultList)
				{	
					
					rowBuffer = new StringBuffer();				
					rowBuffer.append(rowMap.get("groupId") + seprater);
					rowBuffer.append(rowMap.get("roomId") + seprater);
					try {
						if(rowMap.get("groupStartDateTime") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("groupStartDateTime"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					
					try {
						if(rowMap.get("groupCloseDateTime") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("groupCloseDateTime"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					
					rowBuffer.append(rowMap.get("phaseOfProductionType"));
					
					returnRows.add(rowBuffer.toString()+"\n");
				}
				break;
				
				
				case 12 : for(Map<String, Object> rowMap : resultList)
				{	
					
					
					rowBuffer = new StringBuffer();				
					rowBuffer.append(rowMap.get("pigId") + seprater);
					rowBuffer.append(rowMap.get("litterId") + seprater);
					rowBuffer.append(rowMap.get("tattooId") + seprater);
					rowBuffer.append(rowMap.get("weightAtBirth") + seprater);
					rowBuffer.append(rowMap.get("weightAtWeaning") + seprater);
					rowBuffer.append(rowMap.get("weight1") + seprater);
					try {
						if(rowMap.get("date1") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("date1"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					
					rowBuffer.append(rowMap.get("weight2") + seprater);
					try {
						if(rowMap.get("date2") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("date2"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					
					rowBuffer.append(rowMap.get("weight3") + seprater);
					try {
						if(rowMap.get("date3") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("date3"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					
					rowBuffer.append(rowMap.get("weight4") + seprater);
					try {
						if(rowMap.get("date4") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("date4"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					
					rowBuffer.append(rowMap.get("weight5") + seprater);
					try {
						if(rowMap.get("date5") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("date5"),"dd/MM/yyyy") + seprater);
						else
							rowBuffer.append("" + seprater);
					} catch (ParseException e) {
						rowBuffer.append("" + seprater);
					}
					
					
					rowBuffer.append(rowMap.get("weight6") + seprater);
					try {
						if(rowMap.get("date6") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("date6"),"dd/MM/yyyy")+ seprater );
						else
							rowBuffer.append(""+ seprater);
					} catch (ParseException e) {
						rowBuffer.append("" );
					}
					
					rowBuffer.append(rowMap.get("weight7") + seprater);
					try {
						if(rowMap.get("date7") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("date7"),"dd/MM/yyyy")+ seprater );
						else
							rowBuffer.append(""+ seprater);
					} catch (ParseException e) {
						rowBuffer.append("" );
					}

					rowBuffer.append(rowMap.get("weight8") + seprater);
					try {
						if(rowMap.get("date8") != null)
						rowBuffer.append(DateUtil.convertToFormatString((Date)rowMap.get("date8"),"dd/MM/yyyy") );
						else
							rowBuffer.append("");
					} catch (ParseException e) {
						rowBuffer.append("" );
					}
					
					
					
					returnRows.add(rowBuffer.toString()+"\n");
				}
				break;
				
			}
		
		}
		
		return returnRows;
	}
	
	
}

