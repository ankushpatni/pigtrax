package com.pigtrax.master.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pigtrax.master.service.interfaces.ReportService;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.report.service.ActionListReportService;
import com.pigtrax.report.service.GroupReportService;
import com.pigtrax.report.service.InventoryStatusReportService;
import com.pigtrax.report.service.SowReportService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.util.DateUtil;

@RestController
public class ReportControlller {
	
	@Autowired
	private HttpBatchPost httpBatchPost;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	SowReportService sowReportService;
	
	@Autowired
	GroupReportService groupReportService;
	
	@Autowired
	GroupEventService groupEventService;
	
	@Autowired
	ActionListReportService actionListReportService;
	
	@Autowired
	InventoryStatusReportService inventoryStatusReportService;
	
	@RequestMapping(value = "/generateReport", method = RequestMethod.POST)
	public void generateReportHandler(HttpServletRequest request, HttpServletResponse response) {
			try {
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				
				System.out.println("startDate = " + startDate);
				System.out.println("startDate = " + endDate);
				
				PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Integer companyId = activeUser.getCompanyId();
				System.out.println(companyId);
				
				response.setContentType("text/csv");
				String reportName = "CSV_Ferrow_Name.csv";
				response.setHeader("Content-disposition", "attachment;filename="+reportName);
		    
				
				ArrayList<String> rows = getFerrowReports(startDate, endDate, companyId);
				
				Iterator<String> iter = rows.iterator();
				while (iter.hasNext()) {
					String outputString = (String) iter.next();
					response.getOutputStream().print(outputString);
				}
		 
				response.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//return new ModelAndView("redirect:" + "reportGeneration?token=success");
	}

	private ArrayList<String> getFerrowReports(String startDate, String endDate, Integer companyId) {
	
		Map map = reportService.getFerrowEventReport(startDate, endDate, companyId);
		System.out.println(map);
		
		int totalActivePenAvailable = reportService.getActivedPenCount(companyId);
		
		//{2015-11-08={totalFerrow=2, valueList=[50, 20, 30, 15, 15, 35]}}
		Set setKey = map.keySet();
		Iterator itr = setKey.iterator();
		
		List<String> dateList= new LinkedList<String>();
		List<Integer> totalBornList = new LinkedList<Integer>();
		List<Integer> totalLiveBornList = new LinkedList<Integer>();
		List<Integer> totalDeathList = new LinkedList<Integer>();
		List<Integer> totalStillBornList = new LinkedList<Integer>();
		List<Integer> totalMummiesList = new LinkedList<Integer>();
		List<Float> totalBirthWeightList = new LinkedList<Float>();
		
		
		List<Float> averageTotalBornList = new LinkedList<Float>();
		List<Float> averageLiveBornList = new LinkedList<Float>();
		List<Float> averageDeadBornList = new LinkedList<Float>();
		List<Float> percentageDeadBornList = new LinkedList<Float>();
		List<Float> averageStillBornList = new LinkedList<Float>();
		List<Float> percentageStillBornList = new LinkedList<Float>();
		List<Float> averageMummiesList = new LinkedList<Float>();
		List<Float> percentageMummiesList = new LinkedList<Float>();
		List<Float> averageBirthWeightList = new LinkedList<Float>();
		List<Float> averageLiveBirthWeightList = new LinkedList<Float>();
		List<Integer> litterWithAgeLessSevenList = new LinkedList<Integer>();
		List<Float> percentageLitterWithAgeLessSevenList = new LinkedList<Float>();
				
		//Farrow Capacity (Farrows / crate / year)
		List<Float> ferrowCapacityList = new LinkedList<Float>();
		
		//Litterweavend
		List<Integer> littersWeanedList = new LinkedList<Integer>();
		
		//LitterweavendWithMoreThan12Pigs
		List<Integer> littersWeanedWithMoreThanTwalePigsList = new LinkedList<Integer>();
		List<Float> percentageLittersWeanedWithMoreThanTwalePigsList = new LinkedList<Float>();
		List<Integer> sowsWeaningZeroPigList = new LinkedList<Integer>();
		List<Float> percentageSowsWeaningZeroPigList = new LinkedList<Float>();		
		List<Integer> totalPigsWeavnedList = new LinkedList<Integer>();
		List<Float> pigsWeaningDivideLitterWeanedList = new LinkedList<Float>();
		List<Float> pigsWeaningDivideSowWeanedList = new LinkedList<Float>();
		List<Float> percentagePingsWeanedDivideByTotalPigsList = new LinkedList<Float>();
		List<Integer> netFosterList = new LinkedList<Integer>();
		
		//Pre-weaning Mortality
		List<Float> percentagePreWeaningMortalityList = new LinkedList<Float>();
		
		//Litters with weaning weight
		List<Integer> littersWithWeaingWeightList = new LinkedList<Integer>();
		//Piglets with weaning weight
		List<Integer> pigletsWithWeainngWeightList = new LinkedList<Integer>();
		//total pigs weave weight
		List<Integer> weainngWeightList = new LinkedList<Integer>();
		List<Float> weaningWeightWithLitterList = new LinkedList<Float>();
		List<Float> weaningWeightWithPigletsList = new LinkedList<Float>();
		List<Integer> littersWeanedLessThan17DaysList = new LinkedList<Integer>();
		List<Float> percentageLittersWeanedLessThan17DaysList = new LinkedList<Float>();
		
		List<Integer> giltsFarrowedList = new LinkedList<Integer>();
		List<Float> avgGestlenList = new LinkedList<Float>();
		List<Float> avgParityList = new LinkedList<Float>();
		List<Integer> littersFarrowedList = new LinkedList<Integer>();
		List<Float> farrowingRateList = new LinkedList<Float>();
		
		//Total services 1st services

		List<Integer> totalServicesList = new LinkedList<Integer>();
		List<Integer> firstServiceList = new LinkedList<Integer>();
		List<Integer> repeateServiceList = new LinkedList<Integer>();
		List<Float> percentageFirstServiceList = new LinkedList<Float>();
		List<Float> percentageRepeateServiceList = new LinkedList<Float>();
		List<Float> percentageFirstServiceGiltList = new LinkedList<Float>();//keep it blank at present
		List<Float> percentageFirstServiceSowsList = new LinkedList<Float>();// keep it balnk at present
		List<Integer> breedingEventWithMatingMoreThanOneList = new LinkedList<Integer>();
		List<Float> percentageBreedingEventWithMatingMoreThanOneList = new LinkedList<Float>();
		List<Float> countOfMatingPerServiceList = new LinkedList<Float>();
		
		List<Integer> weanSowsBredBy7DaysList = new LinkedList<Integer>();
		List<Float> percentageWeanSowsBredBy7DaysList = new LinkedList<Float>();
		List<Float> weanTo1stServiceIntervalList = new LinkedList<Float>();
		List<Float> conceptionRateAt30dPresumedPregnantList = new LinkedList<Float>();
		List<Float> conceptionRateAtDay42List = new LinkedList<Float>();
		List<Float> avgParityOfServedFemalesList = new LinkedList<Float>();
		List<Integer> serviceToFalloutIntervalList = new LinkedList<Integer>();
		List<Float> serviceCapacityList = new LinkedList<Float>();
		List<Float> arrivalTo1stServIntervalList = new LinkedList<Float>();
		
		List<Integer> totalViablebornList = new LinkedList<Integer>();
		List<Float> averageViablebornList = new LinkedList<Float>();
		
		List<Integer> littersWithBirthWeightList = new LinkedList<Integer>();
				
		while(itr.hasNext())
		{
			Date calWeek = (Date)itr.next();
			String key = (calWeek).toString();
			dateList.add(key);
			Map valueMap = (Map)map.get(calWeek);
			if(valueMap != null && !valueMap.isEmpty())
			{
				int totalFerrow = (Integer)valueMap.get("totalFerrow");
				List valueList = (List)valueMap.get("valueList");
				int totalBorn = (Integer)valueList.get(0);
				int totalLiveBorn = (Integer)valueList.get(1);
				int totalDeath = (Integer)valueList.get(2);
				int totalStillBorn = (Integer)valueList.get(3);
				int totalMummies = (Integer)valueList.get(4);
				float totalBirthWeight = (Float)valueList.get(5);
				int litterWithAgeLessSeven = (Integer)valueList.get(6);
				int littersWeaned = (Integer)valueList.get(7);
				int littersWeanedWithMoreThanTwalePigs = (Integer)valueList.get(8);
				int sowsWeaningZeroPig = totalFerrow - littersWeaned ;
				int totalPigsWeavened = (Integer)valueList.get(9);
				int totalLitterSWeanedFosterInOut = (Integer)valueList.get(10);
				int totalPigsMortal = (Integer)valueList.get(11);
				int littersWithWeaingWeight = (Integer)valueList.get(12);
				int pigletsWithWeainngWeight = (Integer)valueList.get(13);
				int weainngWeight = (Integer)valueList.get(14);
				int littersWeanedLessThan17Days = (Integer)valueList.get(15);
				int countPifIngoIdFromFarrowWithParityOneInPigInfo = (Integer)valueList.get(16);
				int countParityOfPigIngoIdFromFarrow = (Integer)valueList.get(17);
				int sumOfDiffOfFerrowAndBreedingDate = (Integer)valueList.get(18);
				int piGIdFromFerrow = (Integer)valueList.get(19);
				int piGIdFromBreeding = (Integer)valueList.get(20);
				int firstServiceCount = (Integer)valueList.get(21);
				int repeateServiceCount = (Integer)valueList.get(22);
				int breedingEventWithMatingMoreThanOne = (Integer)valueList.get(23);
				int countOfMating = (Integer)valueList.get(24);
				int countOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding = (Integer)valueList.get(25);
				int pigletStatusEventsPigIdCountForWeavnAndDateRangeWithMoreThanTwalePig = (Integer)valueList.get(26);
				int numberOfDaysBetweenWeanAndServiceDate = (Integer)valueList.get(27);
				int pairtyOfServedFemals = (Integer)valueList.get(28);
				int countPifIngoIdWithParityOneInPigInfo = (Integer)valueList.get(29);
				int arrivalTo1stServInterval = (Integer)valueList.get(30);
				int totalWeakBorn = (Integer)valueList.get(31);
				int litterWithWeight = (Integer)valueList.get(32);
				int getConceptionRateAtPresumedPregnantFor30 = (Integer)valueList.get(33);
				int getConceptionRateAtPresumedPregnantFor42 = (Integer)valueList.get(34);
				
				totalBornList.add(totalBorn);
				totalLiveBornList.add(totalLiveBorn);
				totalDeathList.add(totalDeath);
				totalStillBornList.add(totalStillBorn);
				totalMummiesList.add(totalMummies);
				totalBirthWeightList.add(totalBirthWeight);
				litterWithAgeLessSevenList.add(litterWithAgeLessSeven);
				totalPigsWeavnedList.add(totalPigsWeavened);
				totalServicesList.add(piGIdFromBreeding);
				firstServiceList.add(firstServiceCount);
				repeateServiceList.add(repeateServiceCount);
				
				
				float averageTotalBorn = (float)(totalBorn)/totalFerrow;
				float averageLiveBorn = (float)(totalLiveBorn)/totalFerrow;
				
				float averageDeadBorn = (float)(totalDeath)/totalFerrow;
				float percentageDeadBorn = ((float)(totalDeath)/totalBorn)*100;
				
				float averageStillBorn = (float)(totalStillBorn)/totalFerrow;
				float percentageStillBorn = ((float)(totalStillBorn)/totalBorn)*100;
				
				float averageMummies = (float)(totalMummies)/totalFerrow;
				float percentageMummies = ((float)(totalMummies)/totalBorn)*100;
				
				float averageBirthWeight = (float)(totalBirthWeight)/totalBorn;
				float averageLiveBirthWeight = ((float)(totalBirthWeight)/totalLiveBorn)*100;
				
				float percentageLitterWithAgeLessSeven = ((float)(litterWithAgeLessSeven)/littersWeaned)*100;
				
				float ferrowCapacity = (float)(totalFerrow)/totalActivePenAvailable;
				
				float percentageLittersWeanedWithMoreThanTwalePigs = ((float)(littersWeanedWithMoreThanTwalePigs)/totalPigsWeavened)*100;
				float percentageSowsWeaningZeroPig  = ((float)(sowsWeaningZeroPig)/totalFerrow)*100;
				
				float pigsWeaningDivideLitterWeaned = (float)(totalPigsWeavened)/littersWeaned;
				float pigsWeaningDivideSowWeaned = (float)(totalPigsWeavened)/totalLitterSWeanedFosterInOut;
				
				float percentagePingsWeanedDivideByTotalPigs = ((float)(totalPigsWeavened)/totalBorn)*100;
				
				int netFoster = totalLiveBorn -totalPigsMortal- totalPigsWeavened;
				
				float percentagePreWeaningMortality = ((float)(totalPigsMortal)/totalLiveBorn)*100;
				
				float weaningWeightWithLitter = (float)(weainngWeight)/littersWeaned;
				float weaningWeightWithPiglets = (float)(weainngWeight)/totalPigsWeavened;
				
				float percentageLittersWeanedLessThan17Days = ((float)(littersWeanedLessThan17Days)/littersWeaned)*100;
				
				averageTotalBornList.add(averageTotalBorn);
				averageLiveBornList.add(averageLiveBorn);
				averageDeadBornList.add(averageDeadBorn);
				percentageDeadBornList.add(percentageDeadBorn);
				averageStillBornList.add(averageStillBorn);
				percentageStillBornList.add(percentageStillBorn);
				averageMummiesList.add(averageMummies);
				percentageMummiesList.add(percentageMummies);
				averageBirthWeightList.add(averageBirthWeight);
				averageLiveBirthWeightList.add(averageLiveBirthWeight);
				percentageLitterWithAgeLessSevenList.add(percentageLitterWithAgeLessSeven);
				ferrowCapacityList.add(ferrowCapacity);
				littersWeanedList.add(littersWeaned);
				littersWeanedWithMoreThanTwalePigsList.add(littersWeanedWithMoreThanTwalePigs);
				percentageLittersWeanedWithMoreThanTwalePigsList.add(percentageLittersWeanedWithMoreThanTwalePigs);
				sowsWeaningZeroPigList.add(sowsWeaningZeroPig);
				percentageSowsWeaningZeroPigList.add(percentageSowsWeaningZeroPig);
				pigsWeaningDivideLitterWeanedList.add(pigsWeaningDivideLitterWeaned);
				pigsWeaningDivideSowWeanedList.add(pigsWeaningDivideSowWeaned);
				percentagePingsWeanedDivideByTotalPigsList.add(percentagePingsWeanedDivideByTotalPigs);
				netFosterList.add(netFoster);
				percentagePreWeaningMortalityList.add(percentagePreWeaningMortality);
				littersWithWeaingWeightList.add(littersWithWeaingWeight);
				pigletsWithWeainngWeightList.add(pigletsWithWeainngWeight);
				weainngWeightList.add(weainngWeight);
				weaningWeightWithLitterList.add(weaningWeightWithLitter);
				weaningWeightWithPigletsList.add(weaningWeightWithPiglets);
				littersWeanedLessThan17DaysList.add(littersWeanedLessThan17Days);
				percentageLittersWeanedLessThan17DaysList.add(percentageLittersWeanedLessThan17Days);
				
				littersFarrowedList.add(piGIdFromFerrow);
				avgParityList.add((float)countParityOfPigIngoIdFromFarrow/piGIdFromFerrow);	
				avgGestlenList.add((float)sumOfDiffOfFerrowAndBreedingDate/piGIdFromFerrow);
				giltsFarrowedList.add(countPifIngoIdFromFarrowWithParityOneInPigInfo);
				farrowingRateList.add((float)piGIdFromFerrow/piGIdFromBreeding);
				
				percentageFirstServiceList.add(((float)firstServiceCount/piGIdFromBreeding)*100);
				percentageRepeateServiceList.add(((float)repeateServiceCount/piGIdFromBreeding)*100);
				breedingEventWithMatingMoreThanOneList.add(breedingEventWithMatingMoreThanOne);
				percentageBreedingEventWithMatingMoreThanOneList.add(((float)breedingEventWithMatingMoreThanOne/piGIdFromBreeding)*100);
				countOfMatingPerServiceList.add((float)countOfMating/piGIdFromBreeding);
				
				weanSowsBredBy7DaysList.add(countOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding);
				percentageWeanSowsBredBy7DaysList.add((float)countOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding/pigletStatusEventsPigIdCountForWeavnAndDateRangeWithMoreThanTwalePig);
				weanTo1stServiceIntervalList.add((float)numberOfDaysBetweenWeanAndServiceDate/countPifIngoIdWithParityOneInPigInfo);
				avgParityOfServedFemalesList.add((float)pairtyOfServedFemals/piGIdFromBreeding);
				serviceCapacityList.add((float)piGIdFromBreeding/totalActivePenAvailable);
				arrivalTo1stServIntervalList.add((float)arrivalTo1stServInterval/piGIdFromBreeding);
				
				totalViablebornList.add(totalLiveBorn-totalWeakBorn);
				averageViablebornList.add((float)(totalLiveBorn-totalWeakBorn)/totalFerrow);
				littersWithBirthWeightList.add(litterWithWeight);
				
				conceptionRateAt30dPresumedPregnantList.add((float)getConceptionRateAtPresumedPregnantFor30/totalFerrow);
				conceptionRateAtDay42List.add((float)getConceptionRateAtPresumedPregnantFor42/totalFerrow);
				
			}
			else
			{
				
				totalBornList.add(0);
				totalLiveBornList.add(0);
				totalDeathList.add(0);
				totalStillBornList.add(0);
				totalMummiesList.add(0);
				totalBirthWeightList.add(0f);
				litterWithAgeLessSevenList.add(0);
				totalPigsWeavnedList.add(0);
				totalServicesList.add(0);
				firstServiceList.add(0);
				repeateServiceList.add(0);
				
				
				averageTotalBornList.add(0f);
				averageLiveBornList.add(0f);
				averageDeadBornList.add(0f);
				percentageDeadBornList.add(0f);
				averageStillBornList.add(0f);
				percentageStillBornList.add(0f);
				averageMummiesList.add(0f);
				percentageMummiesList.add(0f);
				averageBirthWeightList.add(0f);
				averageLiveBirthWeightList.add(0f);
				percentageLitterWithAgeLessSevenList.add(0f);
				ferrowCapacityList.add(0f);
				littersWeanedList.add(0);
				littersWeanedWithMoreThanTwalePigsList.add(0);
				percentageLittersWeanedWithMoreThanTwalePigsList.add(0f);
				sowsWeaningZeroPigList.add(0);
				percentageSowsWeaningZeroPigList.add(0f);
				pigsWeaningDivideLitterWeanedList.add(0f);
				pigsWeaningDivideSowWeanedList.add(0f);
				percentagePingsWeanedDivideByTotalPigsList.add(0f);
				netFosterList.add(0);
				percentagePreWeaningMortalityList.add(0f);
				littersWithWeaingWeightList.add(0);
				pigletsWithWeainngWeightList.add(0);
				weainngWeightList.add(0);
				weaningWeightWithLitterList.add(0f);
				weaningWeightWithPigletsList.add(0f);
				littersWeanedLessThan17DaysList.add(0);
				percentageLittersWeanedLessThan17DaysList.add(0f);
				
				littersFarrowedList.add(0);
				avgParityList.add(0f);	
				avgGestlenList.add(0f);
				giltsFarrowedList.add(0);
				farrowingRateList.add(0f);
				
				percentageFirstServiceList.add(0f);
				percentageRepeateServiceList.add(0f);
				breedingEventWithMatingMoreThanOneList.add(0);
				percentageBreedingEventWithMatingMoreThanOneList.add(0f);
				countOfMatingPerServiceList.add(0f);
				
				weanSowsBredBy7DaysList.add(0);
				percentageWeanSowsBredBy7DaysList.add(0f);
				weanTo1stServiceIntervalList.add(0f);
				avgParityOfServedFemalesList.add(0f);
				serviceCapacityList.add(0f);
				arrivalTo1stServIntervalList.add(0f);
				
				totalViablebornList.add(0);
				averageViablebornList.add(0f);
				littersWithBirthWeightList.add(0);
				conceptionRateAt30dPresumedPregnantList.add(0f);
				conceptionRateAtDay42List.add(0f);
				
				
			}
			
		}
		
		ArrayList<String> rows = new ArrayList<String>();
		int size = dateList.size();		

		StringBuffer dateBuffer = new StringBuffer();
		dateBuffer.append("Calander Week,");
		for(int i=0;i<size;i++)
		{
			dateBuffer.append(dateList.get(i)).append(",");
		}
		
		rows.add(dateBuffer.toString());
		rows.add("\n");
		
		rows.add("Breeding Performance");
		rows.add("\n");
		rows.add("Gilts and Sows");
		rows.add("\n");
		
		StringBuffer totalServicesBuffer = new StringBuffer();
		totalServicesBuffer.append("Total services,");
		for(int i=0;i<size;i++)
		{
			totalServicesBuffer.append(totalServicesList.get(i)).append(",");
		}
		
		rows.add(totalServicesBuffer.toString());
		rows.add("\n");
		rows.add("\n");
		
		rows.add("% Services");
		rows.add("\n");
		
		
		StringBuffer firstServiceBuffer = new StringBuffer();
		firstServiceBuffer.append("First services,");
		for(int i=0;i<size;i++)
		{
			firstServiceBuffer.append(firstServiceList.get(i)).append(",");
		}
		
		rows.add(firstServiceBuffer.toString());
		rows.add("\n");
		
		StringBuffer repeateServiceBuffer = new StringBuffer();
		repeateServiceBuffer.append("Repeat services,");
		for(int i=0;i<size;i++)
		{
			repeateServiceBuffer.append(repeateServiceList.get(i)).append(",");
		}
		
		rows.add(repeateServiceBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageFirstServiceBuffer = new StringBuffer();
		percentageFirstServiceBuffer.append("% First services,");
		for(int i=0;i<size;i++)
		{
			percentageFirstServiceBuffer.append(percentageFirstServiceList.get(i)).append(",");
		}
		
		rows.add(percentageFirstServiceBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageFirstServiceGiltBuffer = new StringBuffer(); //percentageFirstServiceGiltList
		percentageFirstServiceGiltBuffer.append("% 1st services: gilts,");
		for(int i=0;i<size;i++)
		{
			percentageFirstServiceGiltBuffer.append(",");
		}		
		rows.add(percentageFirstServiceGiltBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageFirstServiceSowsBuffer = new StringBuffer(); //percentageFirstServiceSowsList
		percentageFirstServiceSowsBuffer.append("% 1st services: sows,");
		for(int i=0;i<size;i++)
		{
			percentageFirstServiceSowsBuffer.append(",");
		}		
		rows.add(percentageFirstServiceSowsBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageRepeateServiceBuffer = new StringBuffer();
		percentageRepeateServiceBuffer.append("% Repeat services,");
		for(int i=0;i<size;i++)
		{
			percentageRepeateServiceBuffer.append(percentageRepeateServiceList.get(i)).append(",");
		}		
		rows.add(percentageRepeateServiceBuffer.toString());
		rows.add("\n");
		
		StringBuffer breedingEventWithMatingMoreThanOneBuffer = new StringBuffer();
		breedingEventWithMatingMoreThanOneBuffer.append("Services with >1 matings,");
		for(int i=0;i<size;i++)
		{
			breedingEventWithMatingMoreThanOneBuffer.append(breedingEventWithMatingMoreThanOneList.get(i)).append(",");
		}		
		rows.add(breedingEventWithMatingMoreThanOneBuffer.toString());
		rows.add("\n");
		
			
		StringBuffer percentageBreedingEventWithMatingMoreThanOneBuffer = new StringBuffer();
		percentageBreedingEventWithMatingMoreThanOneBuffer.append("% Services with 1+ matings,");
		for(int i=0;i<size;i++)
		{
			percentageBreedingEventWithMatingMoreThanOneBuffer.append(percentageBreedingEventWithMatingMoreThanOneList.get(i)).append(",");
		}		
		rows.add(percentageBreedingEventWithMatingMoreThanOneBuffer.toString());
		rows.add("\n");
		
		StringBuffer countOfMatingPerServiceListBuffer = new StringBuffer();
		countOfMatingPerServiceListBuffer.append("Matings per Service,");
		for(int i=0;i<size;i++)
		{
			countOfMatingPerServiceListBuffer.append(countOfMatingPerServiceList.get(i)).append(",");
		}		
		rows.add(countOfMatingPerServiceListBuffer.toString());
		rows.add("\n");
		rows.add("\n");
		
		rows.add("Gilts");
		rows.add("\n");
		
		StringBuffer arrivalTo1stServIntervalListBuffer = new StringBuffer();
		arrivalTo1stServIntervalListBuffer.append("Arrival to 1st serv interval,");
		for(int i=0;i<size;i++)
		{
			arrivalTo1stServIntervalListBuffer.append(arrivalTo1stServIntervalList.get(i)).append(",");
		}		
		rows.add(arrivalTo1stServIntervalListBuffer.toString());
		rows.add("\n");
		rows.add("\n");
		
		rows.add("Wean Sows");
		rows.add("\n");
		
		StringBuffer weanSowsBredBy7DaysListBuffer = new StringBuffer();
		weanSowsBredBy7DaysListBuffer.append("Wean sows bred by 7 days,");
		for(int i=0;i<size;i++)
		{
			weanSowsBredBy7DaysListBuffer.append(weanSowsBredBy7DaysList.get(i)).append(",");
		}		
		rows.add(weanSowsBredBy7DaysListBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageWeanSowsBredBy7DaysListBuffer = new StringBuffer();
		percentageWeanSowsBredBy7DaysListBuffer.append("% Wean sows bred by 7 days,");
		for(int i=0;i<size;i++)
		{
			percentageWeanSowsBredBy7DaysListBuffer.append(percentageWeanSowsBredBy7DaysList.get(i)).append(",");
		}		
		rows.add(percentageWeanSowsBredBy7DaysListBuffer.toString());
		rows.add("\n");
				
		//Wean to 1st service interval
		
		StringBuffer weanTo1stServiceIntervalListBuffer = new StringBuffer();
		weanTo1stServiceIntervalListBuffer.append("Wean to 1st service interval,");
		for(int i=0;i<size;i++)
		{
			weanTo1stServiceIntervalListBuffer.append(weanTo1stServiceIntervalList.get(i)).append(",");
		}		
		rows.add(weanTo1stServiceIntervalListBuffer.toString());
		rows.add("\n");

		StringBuffer conceptionRateAt30dPresumedPregnantListBuffer = new StringBuffer(); // no calculation
		conceptionRateAt30dPresumedPregnantListBuffer.append("Conception rate at 30d Presumed Pregnant,");
		for(int i=0;i<size;i++)
		{
			conceptionRateAt30dPresumedPregnantListBuffer.append(conceptionRateAt30dPresumedPregnantList.get(i)).append(",");
		}		
		rows.add(conceptionRateAt30dPresumedPregnantListBuffer.toString());
		rows.add("\n");
		
		StringBuffer conceptionRateAtDay42ListBuffer = new StringBuffer(); // no calculation
		conceptionRateAtDay42ListBuffer.append("Conception rate at day42,");
		for(int i=0;i<size;i++)
		{
			conceptionRateAtDay42ListBuffer.append(conceptionRateAtDay42List.get(i)).append(",");
		}		
		rows.add(conceptionRateAtDay42ListBuffer.toString());
		rows.add("\n");
		
		StringBuffer avgParityOfServedFemalesListBuffer = new StringBuffer(); // no calculation
		avgParityOfServedFemalesListBuffer.append("Avg parity of served females").append(",");
		for(int i=0;i<size;i++)
		{
			avgParityOfServedFemalesListBuffer.append(avgParityOfServedFemalesList.get(i)).append(",");
		}		
		rows.add(avgParityOfServedFemalesListBuffer.toString());
		rows.add("\n");
		
		StringBuffer serviceToFalloutIntervalListBuffer = new StringBuffer();
		serviceToFalloutIntervalListBuffer.append("Service to fallout interval,");
		for(int i=0;i<size;i++)
		{
			if(!serviceToFalloutIntervalList.isEmpty())
				serviceToFalloutIntervalListBuffer.append(serviceToFalloutIntervalList.get(i)).append(",");
			else
				serviceToFalloutIntervalListBuffer.append(",");
		}		
		rows.add(serviceToFalloutIntervalListBuffer.toString());
		rows.add("\n");
		
		StringBuffer serviceCapacityListBuffer = new StringBuffer();
		serviceCapacityListBuffer.append("Service Capacity (Services/crate/year),");
		for(int i=0;i<size;i++)
		{
			serviceCapacityListBuffer.append(serviceCapacityList.get(i)).append(",");
		}		
		rows.add(serviceCapacityListBuffer.toString());
		rows.add("\n");
		
		rows.add("\n");
		rows.add("Farrowing Performance");
		rows.add("\n");
		rows.add("Gilts and Sows");
		rows.add("\n");
		
		StringBuffer littersFarrowedBuffer = new StringBuffer();
		littersFarrowedBuffer.append("Litters farrowed,");
		for(int i=0;i<size;i++)
		{
			littersFarrowedBuffer.append(littersFarrowedList.get(i)).append(",");
		}
		rows.add(littersFarrowedBuffer.toString());
		rows.add("\n");
		
		StringBuffer avgParityBuffer = new StringBuffer();
		avgParityBuffer.append("Avg parity,");
		for(int i=0;i<size;i++)
		{
			avgParityBuffer.append(avgParityList.get(i)).append(",");
		}
		
		rows.add(avgParityBuffer.toString());
		rows.add("\n");
		
		StringBuffer avgGestlenBuffer = new StringBuffer();
		avgGestlenBuffer.append("Avg gestlen,");
		for(int i=0;i<size;i++)
		{
			avgGestlenBuffer.append(avgGestlenList.get(i)).append(",");
		}
		
		rows.add(avgGestlenBuffer.toString());
		rows.add("\n");
		
		StringBuffer giltsFarrowedBuffer = new StringBuffer();
		giltsFarrowedBuffer.append("Gilts farrowed,");
		for(int i=0;i<size;i++)
		{
			giltsFarrowedBuffer.append(giltsFarrowedList.get(i)).append(",");
		}
		
		rows.add(giltsFarrowedBuffer.toString());
		rows.add("\n");
		
		StringBuffer farrowingRateBuffer = new StringBuffer();
		farrowingRateBuffer.append("Farrowing rate,");
		for(int i=0;i<size;i++)
		{
			farrowingRateBuffer.append(farrowingRateList.get(i)).append(",");
		}
		
		rows.add(farrowingRateBuffer.toString());
		rows.add("\n");
		
		StringBuffer adjFarrowingRateListRateBuffer = new StringBuffer();
		adjFarrowingRateListRateBuffer.append("Adj farrowing rate,");
		for(int i=0;i<size;i++)
		{
			//adjFarrowingRateListRateBuffer.append(farrowingRateList.get(i)).append(",");
			adjFarrowingRateListRateBuffer.append(",");
		}
		
		rows.add(adjFarrowingRateListRateBuffer.toString());
		rows.add("\n");
		rows.add("\n");		
		rows.add("Piglets");
		rows.add("\n");
		
		
		StringBuffer totalBornBuffer = new StringBuffer();
		totalBornBuffer.append("Total Born,");
		for (int i = 0; i < size; i++) {
			totalBornBuffer.append(totalBornList.get(i)).append(",");					
		}
		rows.add(totalBornBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageTotalBornBuffer = new StringBuffer();
		averageTotalBornBuffer.append("Average Total Born,");
		for (int i = 0; i < size; i++) {
			averageTotalBornBuffer.append(averageTotalBornList.get(i)).append(",");					
		}
		rows.add(averageTotalBornBuffer.toString());
		rows.add("\n");	
		
		
		StringBuffer totalViablebornBuffer = new StringBuffer();
		totalViablebornBuffer.append("Total Viable Born,");
		for (int i = 0; i < size; i++) {
			totalViablebornBuffer.append(totalViablebornList.get(i)).append(",");					
		}
		rows.add(totalViablebornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer averageViablebornBuffer = new StringBuffer();
		averageViablebornBuffer.append("Average Viable Born,");
		for (int i = 0; i < size; i++) {
			averageViablebornBuffer.append(averageViablebornList.get(i)).append(",");					
		}
		rows.add(averageViablebornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer totalLiveBornBuffer = new StringBuffer();
		totalLiveBornBuffer.append("Total Live Born,");
		for (int i = 0; i < size; i++) {
			totalLiveBornBuffer.append(totalLiveBornList.get(i)).append(",");					
		}
		rows.add(totalLiveBornBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageLiveBornBuffer = new StringBuffer();
		averageLiveBornBuffer.append("Average Live Born,");
		for (int i = 0; i < size; i++) {
			averageLiveBornBuffer.append(averageLiveBornList.get(i)).append(",");					
		}
		rows.add(averageLiveBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer totalDeathBuffer = new StringBuffer();
		totalDeathBuffer.append("Total Death Born,");
		for (int i = 0; i < size; i++) {
			totalDeathBuffer.append(totalDeathList.get(i)).append(",");					
		}
		rows.add(totalDeathBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageDeathBornBuffer = new StringBuffer();
		averageDeathBornBuffer.append("Average Death Born,");
		for (int i = 0; i < size; i++) {
			averageDeathBornBuffer.append(averageDeadBornList.get(i)).append(",");					
		}
		rows.add(averageDeathBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer percentageDeathBornBuffer = new StringBuffer();
		percentageDeathBornBuffer.append("Percentage Death Born,");
		for (int i = 0; i < size; i++) {
			percentageDeathBornBuffer.append(percentageDeadBornList.get(i)).append(",");					
		}
		rows.add(percentageDeathBornBuffer.toString());
		rows.add("\n");				
		
		StringBuffer totalStillBornBuffer = new StringBuffer();
		totalStillBornBuffer.append("Total Still Born,");
		for (int i = 0; i < size; i++) {
			totalStillBornBuffer.append(totalStillBornList.get(i)).append(",");					
		}
		rows.add(totalStillBornBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageStillBornBuffer = new StringBuffer();
		averageStillBornBuffer.append("Average Still Born,");
		for (int i = 0; i < size; i++) {
			averageStillBornBuffer.append(averageStillBornList.get(i)).append(",");					
		}
		rows.add(averageStillBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer percentageStillBornBuffer = new StringBuffer();
		percentageStillBornBuffer.append("Percentage Still Born,");
		for (int i = 0; i < size; i++) {
			percentageStillBornBuffer.append(percentageStillBornList.get(i)).append(",");					
		}
		rows.add(percentageStillBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer totalMummiesBuffer = new StringBuffer();
		totalMummiesBuffer.append("Total Mummies Born,");
		for (int i = 0; i < size; i++) {
			totalMummiesBuffer.append(totalMummiesList.get(i)).append(",");					
		}
		rows.add(totalMummiesBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageMummiesBornBuffer = new StringBuffer();
		averageMummiesBornBuffer.append("Average Mummies Born,");
		for (int i = 0; i < size; i++) {
			averageMummiesBornBuffer.append(averageMummiesList.get(i)).append(",");					
		}
		rows.add(averageMummiesBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer percentageMummiesBornBuffer = new StringBuffer();
		percentageMummiesBornBuffer.append("Percentage Mummies Born,");
		for (int i = 0; i < size; i++) {
			percentageMummiesBornBuffer.append(percentageMummiesList.get(i)).append(",");					
		}
		rows.add(percentageMummiesBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer litterWithAgeLessSevenBuffer = new StringBuffer();
		litterWithAgeLessSevenBuffer.append("Litter with age less 7,");
		for (int i = 0; i < size; i++) {
			litterWithAgeLessSevenBuffer.append(litterWithAgeLessSevenList.get(i)).append(",");					
		}
		rows.add(litterWithAgeLessSevenBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageLitterWithAgeLessSevenBuffer = new StringBuffer();
		percentageLitterWithAgeLessSevenBuffer.append("Percentage Litter with age less 7, ");
		for (int i = 0; i < size; i++) {
			percentageLitterWithAgeLessSevenBuffer.append(percentageLitterWithAgeLessSevenList.get(i)).append(",");					
		}
		rows.add(percentageLitterWithAgeLessSevenBuffer.toString());
		rows.add("\n");	
		
		StringBuffer totalBirthWeightBuffer = new StringBuffer();
		totalBirthWeightBuffer.append("Birth weight,");
		for (int i = 0; i < size; i++) {
			totalBirthWeightBuffer.append(totalBirthWeightList.get(i)).append(",");					
		}
		rows.add(totalBirthWeightBuffer.toString());
		rows.add("\n");
		
		StringBuffer littersWithBirthWeightBuffer = new StringBuffer();
		littersWithBirthWeightBuffer.append("Litters with birth weight,");
		for (int i = 0; i < size; i++) {
			littersWithBirthWeightBuffer.append(littersWithBirthWeightList.get(i)).append(",");					
		}
		rows.add(littersWithBirthWeightBuffer.toString());
		rows.add("\n");
 
		StringBuffer averageBirthWeightBuffer = new StringBuffer();
		averageBirthWeightBuffer.append("Average Birth weight,");
		for (int i = 0; i < size; i++) {
			averageBirthWeightBuffer.append(averageBirthWeightList.get(i)).append(",");					
		}
		rows.add(averageBirthWeightBuffer.toString());
		rows.add("\n");
		
		rows.add("\n");
		rows.add("Litters with farrow & wean");
		rows.add("\n");
		
		StringBuffer ferrowCapacityBuffer = new StringBuffer();
		ferrowCapacityBuffer.append("Ferrow Capacity,");
		for (int i = 0; i < size; i++) {
			ferrowCapacityBuffer.append(ferrowCapacityList.get(i)).append(",");					
		}
		rows.add(ferrowCapacityBuffer.toString());
		rows.add("\n");
		
		rows.add("\n");
		rows.add("Weaning Performance");
		rows.add("\n");
		rows.add("Gilts and Sows Weaned");
		rows.add("\n");
		
		StringBuffer littersWeanedBuffer = new StringBuffer();
		littersWeanedBuffer.append("Litters weaned,");
		for (int i = 0; i < size; i++) {
			littersWeanedBuffer.append(littersWeanedList.get(i)).append(",");					
		}
		rows.add(littersWeanedBuffer.toString());
		rows.add("\n");
		
		StringBuffer littersWeanedWithMoreThanTwalePigsBuffer = new StringBuffer();
		littersWeanedWithMoreThanTwalePigsBuffer.append("Litters weaning 12+,");
		for (int i = 0; i < size; i++) {
			littersWeanedWithMoreThanTwalePigsBuffer.append(littersWeanedWithMoreThanTwalePigsList.get(i)).append(",");					
		}
		rows.add(littersWeanedWithMoreThanTwalePigsBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageLittersWeanedWithMoreThanTwalePigsBuffer = new StringBuffer();
		percentageLittersWeanedWithMoreThanTwalePigsBuffer.append("% Litters weaning 12+,");
		for (int i = 0; i < size; i++) {
			percentageLittersWeanedWithMoreThanTwalePigsBuffer.append(percentageLittersWeanedWithMoreThanTwalePigsList.get(i)).append(",");					
		}
		rows.add(percentageLittersWeanedWithMoreThanTwalePigsBuffer.toString());
		rows.add("\n");
		
		StringBuffer sowsWeaningZeroPigBuffer = new StringBuffer();
		sowsWeaningZeroPigBuffer.append("Sows weaning 0 pig,");
		for (int i = 0; i < size; i++) {
			sowsWeaningZeroPigBuffer.append(sowsWeaningZeroPigList.get(i)).append(",");					
		}
		rows.add(sowsWeaningZeroPigBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageSowsWeaningZeroPigBuffer = new StringBuffer();
		percentageSowsWeaningZeroPigBuffer.append("% Sows weaning 0 pigs,");
		for (int i = 0; i < size; i++) {
			percentageSowsWeaningZeroPigBuffer.append(percentageSowsWeaningZeroPigList.get(i)).append(",");					
		}
		rows.add(percentageSowsWeaningZeroPigBuffer.toString());
		rows.add("\n");
		
		StringBuffer pigsWeaningDivideLitterWeanedBuffer = new StringBuffer();
		pigsWeaningDivideLitterWeanedBuffer.append("Pigs weaned/litter weaned,");
		for (int i = 0; i < size; i++) {
			pigsWeaningDivideLitterWeanedBuffer.append(pigsWeaningDivideLitterWeanedList.get(i)).append(",");					
		}
		rows.add(pigsWeaningDivideLitterWeanedBuffer.toString());
		rows.add("\n");
		
		StringBuffer totalPigsWeavnedBuffer = new StringBuffer();
		totalPigsWeavnedBuffer.append("Total pigs weaned,");
		for (int i = 0; i < size; i++) {
			totalPigsWeavnedBuffer.append(totalPigsWeavnedList.get(i)).append(",");					
		}
		rows.add(totalPigsWeavnedBuffer.toString());
		rows.add("\n");
		
		
		StringBuffer pigsWeaningDivideSowWeanedBuffer = new StringBuffer();
		pigsWeaningDivideSowWeanedBuffer.append("Pigs weaned/sow weaned,");
		for (int i = 0; i < size; i++) {
			pigsWeaningDivideSowWeanedBuffer.append(pigsWeaningDivideSowWeanedList.get(i)).append(",");					
		}
		rows.add(pigsWeaningDivideSowWeanedBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentagePingsWeanedDivideByTotalPigsBuffer = new StringBuffer();
		percentagePingsWeanedDivideByTotalPigsBuffer.append("Pigs weaned/totalborn(%),");
		for (int i = 0; i < size; i++) {
			percentagePingsWeanedDivideByTotalPigsBuffer.append(percentagePingsWeanedDivideByTotalPigsList.get(i)).append(",");					
		}
		rows.add(percentagePingsWeanedDivideByTotalPigsBuffer.toString());
		rows.add("\n");
		
		//Net foster
		StringBuffer netFosterBuffer = new StringBuffer();
		netFosterBuffer.append("Net foster,");
		for (int i = 0; i < size; i++) {
			netFosterBuffer.append(netFosterList.get(i)).append(",");					
		}
		rows.add(netFosterBuffer.toString());
		rows.add("\n");
		
		
		rows.add("\n");
		rows.add("Pre-weaning Mortality");
		rows.add("\n");
		//Pre-weaning Mortality
		StringBuffer percentagePreWeaningMortalityBuffer = new StringBuffer();
		percentagePreWeaningMortalityBuffer.append("PWM(%),");
		for (int i = 0; i < size; i++) {
			percentagePreWeaningMortalityBuffer.append(percentagePreWeaningMortalityList.get(i)).append(",");					
		}
		rows.add(percentagePreWeaningMortalityBuffer.toString());
		rows.add("\n");

		rows.add("\n");
		rows.add("Piglet Weight and Age");
		rows.add("\n");
		//Pre-weaning Mortality
		StringBuffer littersWithWeaingWeightBuffer = new StringBuffer();
		littersWithWeaingWeightBuffer.append("Litters with weaning weight,");
		for (int i = 0; i < size; i++) {
			littersWithWeaingWeightBuffer.append(littersWithWeaingWeightList.get(i)).append(",");					
		}
		rows.add(littersWithWeaingWeightBuffer.toString());
		rows.add("\n");
		
		
		StringBuffer pigletsWithWeainngWeightBuffer = new StringBuffer();
		pigletsWithWeainngWeightBuffer.append("Piglets with weaning weight,");
		for (int i = 0; i < size; i++) {
			pigletsWithWeainngWeightBuffer.append(pigletsWithWeainngWeightList.get(i)).append(",");					
		}
		rows.add(pigletsWithWeainngWeightBuffer.toString());
		rows.add("\n");
		
		
		StringBuffer weainngWeightBuffer = new StringBuffer();
		weainngWeightBuffer.append("Total weaning weight,");
		for (int i = 0; i < size; i++) {
			weainngWeightBuffer.append(weainngWeightList.get(i)).append(",");					
		}
		rows.add(weainngWeightBuffer.toString());
		rows.add("\n");
		
		StringBuffer weaningWeightWithLitterBuffer = new StringBuffer();
		weaningWeightWithLitterBuffer.append("Weaning weight/litter,");
		for (int i = 0; i < size; i++) {
			weaningWeightWithLitterBuffer.append(weaningWeightWithLitterList.get(i)).append(",");					
		}
		rows.add(weaningWeightWithLitterBuffer.toString());
		rows.add("\n");
		
		StringBuffer weaningWeightWithPigletsBuffer = new StringBuffer();
		weaningWeightWithPigletsBuffer.append("Weaning weight/piglet,");
		for (int i = 0; i < size; i++) {
			weaningWeightWithPigletsBuffer.append(weaningWeightWithPigletsList.get(i)).append(",");					
		}
		rows.add(weaningWeightWithPigletsBuffer.toString());
		rows.add("\n");
		
		StringBuffer littersWeanedLessThan17DaysBuffer = new StringBuffer();
		littersWeanedLessThan17DaysBuffer.append("Litters weaned less than 17 days,");
		for (int i = 0; i < size; i++) {
			littersWeanedLessThan17DaysBuffer.append(littersWeanedLessThan17DaysList.get(i)).append(",");					
		}
		rows.add(littersWeanedLessThan17DaysBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageLittersWeanedLessThan17DaysBuffer = new StringBuffer();
		percentageLittersWeanedLessThan17DaysBuffer.append("% Litters weaned less than 17 days,");
		for (int i = 0; i < size; i++) {
			percentageLittersWeanedLessThan17DaysBuffer.append(percentageLittersWeanedLessThan17DaysList.get(i)).append(",");					
		}
		rows.add(percentageLittersWeanedLessThan17DaysBuffer.toString());
		rows.add("\n");		
		return rows;
	}
	
	@RequestMapping(value = "/reportGeneration", method = RequestMethod.GET)
	public ModelAndView reportGeneration(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportGeneration.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		return new ModelAndView("template", model);
	}
	
	@RequestMapping(value = "/reportGenerationSow", method = RequestMethod.GET)
	public ModelAndView reportGenerationSow(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportGenerationSow.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}
	
	@RequestMapping(value = "/reportGenerationGroup", method = RequestMethod.GET)
	public ModelAndView reportGenerationGroup(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportGenerationGroup.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}
	
	@RequestMapping(value = "/generateReportSow", method = RequestMethod.POST)
	public void generateReportSow(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				String search = request.getParameter("search");
				String companyString = request.getParameter("companyId1");
				Integer companyId ;
				
				System.out.println("selectedPremise = " + selectedPremise);
				System.out.println("search = " + search);
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				
				PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(companyString != null && !StringUtils.isEmpty(companyString))
				{
					companyId = Integer.parseInt(companyString);
				}
				else
				{
					companyId = activeUser.getCompanyId();
				}
				System.out.println(companyId);
				
				response.setContentType("text/csv");
				//String reportName = "CSV_Sow_"+search+".csv";
				String date = DateUtil.convertToFormatString(new java.util.Date(System.currentTimeMillis()),"mm-dd-yyyy");
				String reportName = "SowHistory_"+selectedPremise+"_"+search+"_"+(new java.util.Date(System.currentTimeMillis())).toString()+".csv";
				response.setHeader("Content-disposition", "attachment;filename="+reportName);
		    
				List<String> rows =new ArrayList<String>();
						
				try {
					int premisesId = Integer.parseInt(selectedPremise);
					PigInfo pigInformation = pigInfoDao.getPigInformationByPigIdWithOutStatus(search, companyId, premisesId);
					if(null != pigInformation && pigInformation.getId() != null && pigInformation.getId() != 0)
					{
						rows = sowReportService.getSowReport(search,pigInformation.getId(), companyId, language);
						Iterator<String> iter = rows.iterator();
						while (iter.hasNext()) {
							String outputString = (String) iter.next();
							response.getOutputStream().print(outputString);
						}
					}
					else
					{
						rows.add("Can not find pig by given Id");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					rows.add("There is some error please contact Admin");
				}
				
				response.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//return new ModelAndView("redirect:" + "reportGeneration?token=success");
	}

	@RequestMapping(value = "/generateReportGroup", method = RequestMethod.POST)
	public void generateReportGroup(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				String search = request.getParameter("search");
				String companyString = request.getParameter("companyId1");
				Integer companyId ;
				
				System.out.println("selectedPremise = " + selectedPremise);
				System.out.println("search = " + search);
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				
				PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(companyString != null && !StringUtils.isEmpty(companyString))
				{
					companyId = Integer.parseInt(companyString);
				}
				else
				{
					companyId = activeUser.getCompanyId();
				}
				System.out.println(companyId);
				
				response.setContentType("text/csv");
				String date = DateUtil.convertToFormatString(new java.util.Date(System.currentTimeMillis()),"mm-dd-yyyy");
				//String reportName = "GroupHistory_"+selectedPremise+"_"+search+"_"+date+".csv";
				String reportName = "GroupHistory_"+selectedPremise+"_"+search+"_"+(new java.util.Date(System.currentTimeMillis())).toString()+".csv";
				response.setHeader("Content-disposition", "attachment;filename="+reportName);
		    
				List<String> rows =new ArrayList<String>();
						
				try {
					int premisesId = Integer.parseInt(selectedPremise);
					GroupEvent groupEventByGroupId = groupEventService.getGroupEventByGroupId(search, companyId, premisesId);
					if(null != groupEventByGroupId && groupEventByGroupId.getId() != null && groupEventByGroupId.getId() != 0)
					{
						//rows = groupReportService.getSowReport(search,groupEventByGroupId.getId(), companyId, language);
						rows = groupReportService.getGroupReportwithPhase(search,groupEventByGroupId, companyId, language);
						Iterator<String> iter = rows.iterator();
						while (iter.hasNext()) {
							String outputString = (String) iter.next();
							response.getOutputStream().print(outputString);
						}
					}
					else
					{
						rows.add("Can not find Group by given Id");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					rows.add("There is some error please contact Admin");
				}
				
				response.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//return new ModelAndView("redirect:" + "reportGeneration?token=success");
	}
	
	
	
	@RequestMapping(value = "/generateActionListReport", method = RequestMethod.POST)
	public void generateActionListReport(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				
				PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				response.setContentType("text/csv");
				String reportName = "CSV_Report_ActionListReport_"+selectedPremise+".csv";
				response.setHeader("Content-disposition", "attachment;filename="+reportName);
		    
				List<String> rows =new ArrayList<String>();
						
				try {
					Integer premiseId = Integer.parseInt(selectedPremise);
					
					if(premiseId > 0)
					{
						//rows = groupReportService.getSowReport(search,groupEventByGroupId.getId(), companyId, language);
						rows = actionListReportService.getActionList(premiseId);
						Iterator<String> iter = rows.iterator();
						while (iter.hasNext()) {
							String outputString = (String) iter.next();
							response.getOutputStream().print(outputString);
						}
					}
					else
					{
						rows.add("Can not find Group by given Id");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					rows.add("There is some error please contact Admin");
				}
				
				response.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@RequestMapping(value = "/reportGenerationActionList", method = RequestMethod.GET)
	public ModelAndView reportGenerationActionList(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportGenerationActionList.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}

	@RequestMapping(value = "/reportInventoryStatus", method = RequestMethod.GET)
	public ModelAndView reportInventoryStatus(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportInventoryStatus.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}

	
	@RequestMapping(value = "/generateInventoryStatusReport", method = RequestMethod.POST)
	public void generateInventoryStatusReport(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				
				PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				response.setContentType("text/csv");
				String reportName = "CSV_Report_InventoryStatusReport_"+DateUtil.getToday().getTime()+"_"+selectedPremise+".csv";
				response.setHeader("Content-disposition", "attachment;filename="+reportName);
		    
				List<String> rows =new ArrayList<String>();
						
				try {
					Integer premiseId = Integer.parseInt(selectedPremise);
					
					if(premiseId > 0)
					{
						rows = inventoryStatusReportService.getInventoryStatusList(premiseId);
						Iterator<String> iter = rows.iterator();
						while (iter.hasNext()) {
							String outputString = (String) iter.next();
							response.getOutputStream().print(outputString);
						}
					}
					else
					{
						rows.add("Can not find Group by given Id");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					rows.add("There is some error please contact Admin");
				}
				
				response.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
}
