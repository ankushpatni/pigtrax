package com.pigtrax.master.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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

import com.pigtrax.master.dao.interfaces.PremisesDao;
import com.pigtrax.master.dto.Premises;
import com.pigtrax.master.service.interfaces.ReportService;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.report.service.ActionListReportService;
import com.pigtrax.report.service.DataExtractionService;
import com.pigtrax.report.service.DataIntegrityLogService;
import com.pigtrax.report.service.FeedReportService;
import com.pigtrax.report.service.GestationReportService;
import com.pigtrax.report.service.GroupReportService;
import com.pigtrax.report.service.GroupStatusReportService;
import com.pigtrax.report.service.InventoryStatusReportService;
import com.pigtrax.report.service.LactationLengthReportService;
import com.pigtrax.report.service.LitterBalanceService;
import com.pigtrax.report.service.PigletMortalityReportService;
import com.pigtrax.report.service.ProdEventLogService;
import com.pigtrax.report.service.RationReportService;
import com.pigtrax.report.service.RemovalReportService;
import com.pigtrax.report.service.SaleReportService;
import com.pigtrax.report.service.SowCardReportService;
import com.pigtrax.report.service.SowReportService;
import com.pigtrax.report.service.TargetReportService;
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
	
	@Autowired
	LactationLengthReportService lactationLengthService;
	
	@Autowired
	PigletMortalityReportService pigletMortalityReportService;
	
	@Autowired
	ProdEventLogService prodEventLogService;
	
	@Autowired
	PremisesDao premiseDao;
	
	@Autowired
	RemovalReportService removalReportService;
	
	@Autowired
	TargetReportService targetReportService;
	
	@Autowired
	LitterBalanceService litterBalanceService;
	
	@Autowired
	SaleReportService saleReportService;
	
	@Autowired
	RationReportService rationReportService;
	
	@Autowired
	FeedReportService feedReportService;
	
	@Autowired
	GestationReportService gestationReportService;
	
	@Autowired
	GroupStatusReportService groupStatusReportService;
	
	@Autowired
	DataIntegrityLogService logService;
	
	@Autowired
	DataExtractionService extractionService;
	
	@Autowired
	SowCardReportService sowCardReportService;
	
	/*@RequestMapping(value = "/generateReport", method = RequestMethod.POST)
	public void generateReportHandler(HttpServletRequest request, HttpServletResponse response) {
			try {
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String selectedPremise = request.getParameter("selectedPremise");
				String companyString = request.getParameter("companyId1");
				Integer premiseId  = 0;
				
				System.out.println("startDate = " + startDate);
				System.out.println("startDate = " + endDate);
				System.out.println("selectedPremise = " + selectedPremise);
				
				Integer companyId ;
				
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
				
				if(selectedPremise != null && !StringUtils.isEmpty(selectedPremise))
				{
					premiseId = Integer.parseInt(selectedPremise);
				}
				
				
				response.setContentType("text/csv");
				String reportName = "CSV_Performance_Report "+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+".csv";
				response.setHeader("Content-disposition", "attachment;filename="+reportName);
		    
				
				ArrayList<String> rows = getFerrowReports(startDate, endDate, companyId, premiseId);
				
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
	}*/

	private ArrayList<String> getFerrowReports(String startDate, String endDate, Integer companyId, Integer premisesId) {
	
		Map map = reportService.getFerrowEventReport(startDate, endDate, companyId, premisesId);
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
		List<Float> averageWeaingAgeList = new LinkedList<Float>();
		List<Float> pigsWeanedCrateYearList = new LinkedList<Float>();
		List<Float> weaningCapacityList = new LinkedList<Float>();
		
		List<Integer> endFemaleInventorList = new LinkedList<Integer>();
		List<Integer> endMatedFemaleInventoryList = new LinkedList<Integer>();
		List<Integer> endBoarInventoryList = new LinkedList<Integer>();
		List<Float> endLactationInventoryList = new LinkedList<Float>();
		List<Float> endGestationInventoryList = new LinkedList<Float>();
		List<Float> percentageSpaceCapacityList = new LinkedList<Float>();
		List<Float> aveFemaleInventoryPerCrateList = new LinkedList<Float>();
		List<Float> aveParityOfEndInventoryList = new LinkedList<Float>();
		List<Integer>femaleEnteredList = new LinkedList<Integer>();
		
		List<Integer> totalFemalesCulledList = new LinkedList<Integer>();
		List<Integer> aveParityofCullsList = new LinkedList<Integer>();
		List<Integer> sowCulledList = new LinkedList<Integer>();
		List<Integer> giltsCulledList = new LinkedList<Integer>();
		List<Float> percentageCullingRateList = new LinkedList<Float>();
		
		List<Integer> totalFemaleDeathsandDestroyedList = new LinkedList<Integer>();
		List<Integer> aveParityofMortalityList = new LinkedList<Integer>();
		List<Integer> giltDeathsList = new LinkedList<Integer>();
		List<Integer> sowDeathsList = new LinkedList<Integer>();
		List<Integer> totalFemalesDestroyedList = new LinkedList<Integer>();
		List<Float> percentageSowMortalityList = new LinkedList<Float>();
		
		List<Integer> boarEnteredList = new LinkedList<Integer>();
		List<Integer> boarCulledList = new LinkedList<Integer>();
		List<Integer> boarDeathsandDestroyedList = new LinkedList<Integer>();
		
		List<Integer> totalAbortionsList = new LinkedList<Integer>();
		List<Integer> abortionsNaturalList = new LinkedList<Integer>();
		List<Integer> abortionsInducedList = new LinkedList<Integer>();
		List<Integer> aveAbortionParityList = new LinkedList<Integer>();
		List<Float> percentageAbortions1000sowsList = new LinkedList<Float>();
		
		List<Integer> sowsorGiltsTransferredINList = new LinkedList<Integer>();
		List<Integer> sowsorGiltsTransferredOutList = new LinkedList<Integer>();
		List<Integer> giltEnteredList = new LinkedList<Integer>();
		List<Float> percentageGiltsSowInventoryList = new LinkedList<Float>();
		List<Float> percentageReplacementRateList = new LinkedList<Float>();
		
		List<Float> sowBoarRatioList  = new LinkedList<Float>();
		List<Float> littersFemaleYearList  = new LinkedList<Float>();
		List<Float> littersMatedFemaleYearList  = new LinkedList<Float>();
		List<Float> pigsWeanedFemaleYearList  = new LinkedList<Float>();
		List<Float> pigsWeanedMatedFemaleYearList  = new LinkedList<Float>();
		List<Float> pigsWeanedLifetimeList  = new LinkedList<Float>();
	   

	
				
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
				int countPigletWithEaventTypeWean = (Integer)valueList.get(26);
				int numberOfDaysBetweenWeanAndServiceDate = (Integer)valueList.get(27);
				int pairtyOfServedFemals = (Integer)valueList.get(28);
				int countPifIngoIdWithParityOneInPigInfo = (Integer)valueList.get(29);
				int arrivalTo1stServInterval = (Integer)valueList.get(30);
				int totalWeakBorn = (Integer)valueList.get(31);
				int litterWithWeight = (Integer)valueList.get(32);
				int getConceptionRateAtPresumedPregnantFor30 = (Integer)valueList.get(33);
				int getConceptionRateAtPresumedPregnantFor42 = (Integer)valueList.get(34);
				int weaingAge = (Integer)valueList.get(35);
				
						
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
				percentageWeanSowsBredBy7DaysList.add((float)countOfPiGIdWithDateDifferenceLess7FromPigletStatusAndBreeding/countPigletWithEaventTypeWean);
				weanTo1stServiceIntervalList.add((float)numberOfDaysBetweenWeanAndServiceDate/countPifIngoIdWithParityOneInPigInfo);
				avgParityOfServedFemalesList.add((float)pairtyOfServedFemals/piGIdFromBreeding);
				serviceCapacityList.add((float)piGIdFromBreeding/totalActivePenAvailable);
				arrivalTo1stServIntervalList.add((float)arrivalTo1stServInterval/piGIdFromBreeding);
				
				totalViablebornList.add(totalLiveBorn-totalWeakBorn);
				averageViablebornList.add((float)(totalLiveBorn-totalWeakBorn)/totalFerrow);
				littersWithBirthWeightList.add(litterWithWeight);
				
				conceptionRateAt30dPresumedPregnantList.add((float)getConceptionRateAtPresumedPregnantFor30/totalFerrow);
				conceptionRateAtDay42List.add((float)getConceptionRateAtPresumedPregnantFor42/totalFerrow);
				averageWeaingAgeList.add((float)weaingAge/totalPigsWeavened);
				pigsWeanedCrateYearList.add((float)totalPigsWeavened/totalActivePenAvailable);
				weaningCapacityList.add((float)countPigletWithEaventTypeWean/totalActivePenAvailable);
				
				
				int endFemaleInventor = (Integer)valueList.get(36);
				endFemaleInventorList.add(endFemaleInventor);
				
				int endMatedFemaleInventory = piGIdFromFerrow + piGIdFromBreeding;
				endMatedFemaleInventoryList.add(endMatedFemaleInventory);
				
				int endBoarInventory = (Integer)valueList.get(37);
				endBoarInventoryList.add(endBoarInventory);
				
				int endLactationInventory =  (Integer)valueList.get(38);
				endLactationInventoryList.add((float)endFemaleInventor/endLactationInventory);
				
				int endGestationInventory = (Integer)valueList.get(39);
				endGestationInventoryList.add((float)endFemaleInventor/endGestationInventory);
				
				int percentageSpaceCapacity = (Integer)valueList.get(40)+ (Integer)valueList.get(41);
				percentageSpaceCapacityList.add((float)endFemaleInventor/percentageSpaceCapacity);
				
				int aveFemaleInventoryPerCrate = (Integer)valueList.get(41);
				aveFemaleInventoryPerCrateList.add((float)endFemaleInventor/aveFemaleInventoryPerCrate);
				
				int aveParityOfEndInventory = (Integer)valueList.get(42);
				aveParityOfEndInventoryList.add((float)aveParityOfEndInventory/endFemaleInventor);
				
				int femaleEntered = (Integer)valueList.get(43);
				femaleEnteredList.add(femaleEntered);
				
				int totalFemalesCulled = (Integer)valueList.get(44);
				totalFemalesCulledList.add(totalFemalesCulled);
				
				percentageCullingRateList.add(((float)(totalFemalesCulled)/endFemaleInventor) * (365/7) ); // need to check
				
				// (sows and gilts culled)/(avg. female inventory) x (365/period length) x 100
				
				int aveParityofCulls = (Integer)valueList.get(45);
				aveParityofCullsList.add(aveParityofCulls);
				
				int sowCulled = (Integer)valueList.get(46);
				sowCulledList.add(sowCulled);
				
				int giltsCulled = (Integer)valueList.get(47);
				giltsCulledList.add(giltsCulled);				
				
				int  totalFemaleDeathsandDestroyed = (Integer)valueList.get(48);
				totalFemaleDeathsandDestroyedList.add(totalFemaleDeathsandDestroyed);
				
				percentageSowMortalityList.add(((float)(totalFemaleDeathsandDestroyed)/endFemaleInventor) * (365/7) ) ;
				
				//(sow and gilt deaths)/(avg. female inventory) x (365/period length) x100
				
				int  aveParityofMortality = (Integer)valueList.get(49);
				aveParityofMortalityList.add(aveParityofMortality);
				
				int  giltDeaths = (Integer)valueList.get(50);
				giltDeathsList.add(giltDeaths) ;
				
				int  sowDeaths = (Integer)valueList.get(51);
				sowDeathsList.add(sowDeaths);
				
				int  totalFemalesDestroyed = (Integer)valueList.get(52);
				totalFemalesDestroyedList.add(totalFemalesDestroyed);
				
				int  boarEntered = (Integer)valueList.get(53);
				boarEnteredList.add(boarEntered);
				
				int  boarCulled = (Integer)valueList.get(54);
				boarCulledList.add(boarCulled);
				
				int  boarDeathsandDestroyed = (Integer)valueList.get(55);
				boarDeathsandDestroyedList.add(boarDeathsandDestroyed);	
				
				int  totalAbortions = (Integer)valueList.get(56);
				totalAbortionsList.add(totalAbortions);
				
				percentageAbortions1000sowsList.add((float)totalAbortions/(endFemaleInventor/1000));
				
				int  abortionsNatural = (Integer)valueList.get(57);
				abortionsNaturalList.add(abortionsNatural);
				
				int  abortionsInduced = (Integer)valueList.get(58);
				abortionsInducedList.add(abortionsInduced);
				
				int  aveAbortionParity = (Integer)valueList.get(59);
				aveAbortionParityList.add(aveAbortionParity);
				
				
				int sowsorGiltsTransferredIN = (Integer)valueList.get(60);
				sowsorGiltsTransferredINList.add(sowsorGiltsTransferredIN);
				
				int sowsorGiltsTransferredOut = (Integer)valueList.get(61);
				sowsorGiltsTransferredOutList.add(sowsorGiltsTransferredOut);
				
				int giltEntered = (Integer)valueList.get(62);
				giltEnteredList.add(giltEntered);
				
				percentageGiltsSowInventoryList.add((float)giltEntered/endFemaleInventor);
				percentageReplacementRateList.add((float)(sowsorGiltsTransferredIN/endFemaleInventor)*(365/7));
				
				int  pigsWeanedLifetime = (Integer)valueList.get(63);	
				
				sowBoarRatioList.add((float)endFemaleInventor/endBoarInventory);
				
				float littersFemaleYear = ((float)piGIdFromBreeding/endFemaleInventor)*365;				
				littersFemaleYearList.add(littersFemaleYear);
				
				float littersMatedFemaleYear = ((float)piGIdFromBreeding/endMatedFemaleInventory)*365;
				littersMatedFemaleYearList.add(littersMatedFemaleYear);
				
				pigsWeanedFemaleYearList.add(littersFemaleYear * totalPigsWeavened);
				pigsWeanedMatedFemaleYearList.add(littersMatedFemaleYear* totalPigsWeavened);
				pigsWeanedLifetimeList.add((float)pigsWeanedLifetime/totalPigsWeavened);
					
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
				averageWeaingAgeList.add(0f);
				weaningCapacityList.add(0f);
				pigsWeanedCrateYearList.add(0f);
				
				endFemaleInventorList.add(0);
				endMatedFemaleInventoryList.add(0);
				endBoarInventoryList.add(0);
				endLactationInventoryList.add(0f);
				endGestationInventoryList.add(0f);
				percentageSpaceCapacityList.add(0f);
				aveFemaleInventoryPerCrateList.add(0f);
				aveParityOfEndInventoryList.add(0f);
				femaleEnteredList.add(0);
				
				totalAbortionsList.add(0);
				percentageAbortions1000sowsList.add(0f);
				abortionsNaturalList.add(0);
				abortionsInducedList.add(0);
				aveAbortionParityList.add(0);	
				
				totalFemalesCulledList.add(0);
				aveParityofCullsList.add(0);
				sowCulledList.add(0) ;
				giltsCulledList.add(0) ;
				percentageCullingRateList.add(0f) ;
				 
				totalFemaleDeathsandDestroyedList.add(0);
				aveParityofMortalityList.add(0);
				giltDeathsList.add(0) ;
				sowDeathsList.add(0);
				totalFemalesDestroyedList.add(0) ;
				percentageSowMortalityList.add(0f) ;
				
				boarEnteredList.add(0);
				boarCulledList.add(0);
				boarDeathsandDestroyedList.add(0);	
				
				sowsorGiltsTransferredINList.add(0);
				sowsorGiltsTransferredOutList.add(0);
				giltEnteredList.add(0);
				percentageGiltsSowInventoryList.add(0f);
				percentageReplacementRateList.add(0f);
				
				sowBoarRatioList.add(0f);
				littersFemaleYearList.add(0f);
				littersMatedFemaleYearList.add(0f);				
				pigsWeanedFemaleYearList.add(0f);
				pigsWeanedMatedFemaleYearList.add(0f);
				pigsWeanedLifetimeList.add(0f);
				
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
		
		StringBuffer averageWeaingAgeBuffer = new StringBuffer();
		averageWeaingAgeBuffer.append("Avgerage weaning age,");
		for (int i = 0; i < size; i++) {
			averageWeaingAgeBuffer.append(averageWeaingAgeList.get(i)).append(",");					
		}
		rows.add(averageWeaingAgeBuffer.toString());
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
		
		StringBuffer pigsWeanedCrateYearBuffer = new StringBuffer();
		pigsWeanedCrateYearBuffer.append("Pigs weaned/crate/year,");
		for (int i = 0; i < size; i++) {
			pigsWeanedCrateYearBuffer.append(pigsWeanedCrateYearList.get(i)).append(",");					
		}
		rows.add(pigsWeanedCrateYearBuffer.toString());
		rows.add("\n");
		
		
		StringBuffer weaningCapacityBuffer = new StringBuffer();
		weaningCapacityBuffer.append("Weaning Capacity,");
		for (int i = 0; i < size; i++) {
			weaningCapacityBuffer.append(weaningCapacityList.get(i)).append(",");					
		}
		rows.add(weaningCapacityBuffer.toString());
		rows.add("\n");
		
		rows.add("\n");
		rows.add("Inventory status");
		rows.add("\n");
		rows.add("Adult Herd Statistics");
		rows.add("\n");
		
		
		StringBuffer endFemaleInventorListBuffer = new StringBuffer();
		endFemaleInventorListBuffer.append("End Female Inventory,");
		for (int i = 0; i < size; i++) {
			endFemaleInventorListBuffer.append(endFemaleInventorList.get(i)).append(",");					
		}
		rows.add(endFemaleInventorListBuffer.toString());
		rows.add("\n");
		
		StringBuffer endMatedFemaleInventoryListBuffer = new StringBuffer();
		endMatedFemaleInventoryListBuffer.append("End Mated Female Inventory,");
		for (int i = 0; i < size; i++) {
			endMatedFemaleInventoryListBuffer.append(endMatedFemaleInventoryList.get(i)).append(",");					
		}
		rows.add(endMatedFemaleInventoryListBuffer.toString());
		rows.add("\n");
		
		StringBuffer endBoarInventoryListBuffer = new StringBuffer();
		endBoarInventoryListBuffer.append("End Boar Inventory,");
		for (int i = 0; i < size; i++) {
			endBoarInventoryListBuffer.append(endBoarInventoryList.get(i)).append(",");					
		}
		rows.add(endBoarInventoryListBuffer.toString());
		rows.add("\n");
		
		StringBuffer endLactationInventoryListBuffer = new StringBuffer();
		endLactationInventoryListBuffer.append("End Lactation Inventory,");
		for (int i = 0; i < size; i++) {
			endLactationInventoryListBuffer.append(endLactationInventoryList.get(i)).append(",");					
		}
		rows.add(endLactationInventoryListBuffer.toString());
		rows.add("\n");
		
		StringBuffer endGestationInventoryListBuffer = new StringBuffer();
		endGestationInventoryListBuffer.append(" End Gestation Inventory,");
		for (int i = 0; i < size; i++) {
			endGestationInventoryListBuffer.append(endGestationInventoryList.get(i)).append(",");					
		}
		rows.add(endGestationInventoryListBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageSpaceCapacityListBuffer = new StringBuffer();
		percentageSpaceCapacityListBuffer.append("%Space Capacity,");
		for (int i = 0; i < size; i++) {
			percentageSpaceCapacityListBuffer.append(percentageSpaceCapacityList.get(i)).append(",");					
		}
		rows.add(percentageSpaceCapacityListBuffer.toString());
		rows.add("\n");
		
		StringBuffer aveFemaleInventoryPerCrateListBuffer = new StringBuffer();
		aveFemaleInventoryPerCrateListBuffer.append(" Ave Female Inventory/Crate,");
		for (int i = 0; i < size; i++) {
			aveFemaleInventoryPerCrateListBuffer.append(aveFemaleInventoryPerCrateList.get(i)).append(",");					
		}
		rows.add(aveFemaleInventoryPerCrateListBuffer.toString());
		rows.add("\n");
		
		StringBuffer aveParityOfEndInventoryListBuffer = new StringBuffer();
		aveParityOfEndInventoryListBuffer.append("Ave Parity of End Inventory,");
		for (int i = 0; i < size; i++) {
			aveParityOfEndInventoryListBuffer.append(aveParityOfEndInventoryList.get(i)).append(",");					
		}
		rows.add(aveParityOfEndInventoryListBuffer.toString());
		rows.add("\n");
		
		StringBuffer femaleEnteredListBuffer = new StringBuffer();
		femaleEnteredListBuffer.append("Female Entered,");
		for (int i = 0; i < size; i++) {
			femaleEnteredListBuffer.append(femaleEnteredList.get(i)).append(",");					
		}
		rows.add(femaleEnteredListBuffer.toString());
		rows.add("\n");
		
			
		StringBuffer sowsorGiltsTransferredINListBuffer = new StringBuffer();
		sowsorGiltsTransferredINListBuffer.append(" Female Transfer IN,");
		for (int i = 0; i < size; i++) {
			sowsorGiltsTransferredINListBuffer.append(sowsorGiltsTransferredINList.get(i)).append(",");					
		}
		rows.add(sowsorGiltsTransferredINListBuffer.toString());
		rows.add("\n");
		
		StringBuffer sowsorGiltsTransferredOutListBuffer = new StringBuffer();
		sowsorGiltsTransferredOutListBuffer.append("Female Transfer OUT,");
		for (int i = 0; i < size; i++) {
			sowsorGiltsTransferredOutListBuffer.append(sowsorGiltsTransferredOutList.get(i)).append(",");					
		}
		rows.add(sowsorGiltsTransferredOutListBuffer.toString());
		rows.add("\n");
		
		StringBuffer giltEnteredListBuffer = new StringBuffer();
		giltEnteredListBuffer.append("Gilts Entered,");
		for (int i = 0; i < size; i++) {
			giltEnteredListBuffer.append(giltEnteredList.get(i)).append(",");					
		}
		rows.add(giltEnteredListBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageGiltsSowInventoryListBuffer = new StringBuffer();
		percentageGiltsSowInventoryListBuffer.append(" %Gilts - Sow Inventory,");
		for (int i = 0; i < size; i++) {
			percentageGiltsSowInventoryListBuffer.append(percentageGiltsSowInventoryList.get(i)).append(",");					
		}
		rows.add(percentageGiltsSowInventoryListBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageReplacementRateListBuffer = new StringBuffer();
		percentageReplacementRateListBuffer.append("%Replacement Rate,");
		for (int i = 0; i < size; i++) {
			percentageReplacementRateListBuffer.append(percentageReplacementRateList.get(i)).append(",");					
		}
		rows.add(percentageReplacementRateListBuffer.toString());
		rows.add("\n");
		
		
		StringBuffer totalAbortionsListBuffer = new StringBuffer();
		totalAbortionsListBuffer.append("Total Abortions,");
		for (int i = 0; i < size; i++) {
			totalAbortionsListBuffer.append(totalAbortionsList.get(i)).append(",");					
		}
		rows.add(totalAbortionsListBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageAbortions1000sowsListBuffer = new StringBuffer();
		percentageAbortions1000sowsListBuffer.append("%Abortions 1000sows,");
		for (int i = 0; i < size; i++) {
			percentageAbortions1000sowsListBuffer.append(percentageAbortions1000sowsList.get(i)).append(",");					
		}
		rows.add(percentageAbortions1000sowsListBuffer.toString());
		rows.add("\n");
		
		StringBuffer abortionsNaturalListListBuffer = new StringBuffer();
		abortionsNaturalListListBuffer.append("Abortions - Natural,");
		for (int i = 0; i < size; i++) {
			abortionsNaturalListListBuffer.append(abortionsNaturalList.get(i)).append(",");					
		}
		rows.add(abortionsNaturalListListBuffer.toString());
		rows.add("\n");
		
		StringBuffer abortionsInducedListBuffer = new StringBuffer();
		abortionsInducedListBuffer.append("Abortions - Induced,");
		for (int i = 0; i < size; i++) {
			abortionsInducedListBuffer.append(abortionsInducedList.get(i)).append(",");					
		}
		rows.add(abortionsInducedListBuffer.toString());
		rows.add("\n");
		
		StringBuffer aveAbortionParityListBuffer = new StringBuffer();
		aveAbortionParityListBuffer.append("Abortions - Induced,");
		for (int i = 0; i < size; i++) {
			aveAbortionParityListBuffer.append(aveAbortionParityList.get(i)).append(",");					
		}
		rows.add(aveAbortionParityListBuffer.toString());
		rows.add("\n");
		
		rows.add("\n");
		rows.add("Culls");
		rows.add("\n");
		
		StringBuffer totalFemalesCulledListBuffer = new StringBuffer();
		totalFemalesCulledListBuffer.append("Total Females Culled,");
		for (int i = 0; i < size; i++) {
			femaleEnteredListBuffer.append(totalFemalesCulledList.get(i)).append(",");					
		}
		rows.add(totalFemalesCulledListBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageCullingRateListBuffer = new StringBuffer();
		percentageCullingRateListBuffer.append("%Culling Rate,");
		for (int i = 0; i < size; i++) {
			percentageCullingRateListBuffer.append(percentageCullingRateList.get(i)).append(",");					
		}
		rows.add(percentageCullingRateListBuffer.toString());
		rows.add("\n");
		
		
		StringBuffer aveParityofCullsListBuffer = new StringBuffer();
		aveParityofCullsListBuffer.append("Ave Parity of Culls,");
		for (int i = 0; i < size; i++) {
			aveParityofCullsListBuffer.append(aveParityofCullsList.get(i)).append(",");					
		}
		rows.add(aveParityofCullsListBuffer.toString());
		rows.add("\n");
		
		StringBuffer sowCulledListBuffer = new StringBuffer();
		sowCulledListBuffer.append("Sows Culled,");
		for (int i = 0; i < size; i++) {
			sowCulledListBuffer.append(sowCulledList.get(i)).append(",");					
		}
		rows.add(sowCulledListBuffer.toString());
		rows.add("\n");
		
		StringBuffer giltsCulledListBuffer = new StringBuffer();
		giltsCulledListBuffer.append("Gilts Culled,");
		for (int i = 0; i < size; i++) {
			giltsCulledListBuffer.append(giltsCulledList.get(i)).append(",");					
		}
		rows.add(giltsCulledListBuffer.toString());
		rows.add("\n");
		
		rows.add("\n");
		rows.add("  Deaths and Destroyed");
		rows.add("\n");
		
		StringBuffer totalFemaleDeathsandDestroyedListBuffer = new StringBuffer();
		totalFemaleDeathsandDestroyedListBuffer.append("Total Female Deaths and Destroyed,");
		for (int i = 0; i < size; i++) {
			totalFemaleDeathsandDestroyedListBuffer.append(totalFemaleDeathsandDestroyedList.get(i)).append(",");					
		}
		rows.add(totalFemaleDeathsandDestroyedListBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageSowMortalityListBuffer = new StringBuffer();
		percentageSowMortalityListBuffer.append("%Sow Mortality,");
		for (int i = 0; i < size; i++) {
			percentageSowMortalityListBuffer.append(percentageSowMortalityList.get(i)).append(",");					
		}
		rows.add(percentageSowMortalityListBuffer.toString());
		rows.add("\n");
		
		StringBuffer aveParityofMortalityListBuffer = new StringBuffer();
		aveParityofMortalityListBuffer.append("Ave Parity of Mortality,");
		for (int i = 0; i < size; i++) {
			aveParityofMortalityListBuffer.append(aveParityofMortalityList.get(i)).append(",");					
		}
		rows.add(aveParityofMortalityListBuffer.toString());
		rows.add("\n");
		
		StringBuffer giltDeathsListBuffer = new StringBuffer();
		giltDeathsListBuffer.append("Gilt Deaths,");
		for (int i = 0; i < size; i++) {
			giltDeathsListBuffer.append(giltDeathsList.get(i)).append(",");					
		}
		rows.add(giltDeathsListBuffer.toString());
		rows.add("\n");
		
		StringBuffer sowDeathsListBuffer = new StringBuffer();
		sowDeathsListBuffer.append("Sow Deaths,");
		for (int i = 0; i < size; i++) {
			sowDeathsListBuffer.append(sowDeathsList.get(i)).append(",");					
		}
		rows.add(sowDeathsListBuffer.toString());
		rows.add("\n");
		
		StringBuffer totalFemalesDestroyedListBuffer = new StringBuffer();
		totalFemalesDestroyedListBuffer.append(" Total Females Destroyed,");
		for (int i = 0; i < size; i++) {
			totalFemalesDestroyedListBuffer.append(totalFemalesDestroyedList.get(i)).append(",");					
		}
		rows.add(totalFemalesDestroyedListBuffer.toString());
		rows.add("\n");
		
		rows.add("\n");
		rows.add("Boar Inventory");
		rows.add("\n");
		
		StringBuffer boarEnteredListBuffer = new StringBuffer();
		boarEnteredListBuffer.append(" Boar Entered,");
		for (int i = 0; i < size; i++) {
			boarEnteredListBuffer.append(boarEnteredList.get(i)).append(",");					
		}
		rows.add(boarEnteredListBuffer.toString());
		rows.add("\n");
		
		StringBuffer boarCulledListBuffer = new StringBuffer();
		boarCulledListBuffer.append("Boar Culled,");
		for (int i = 0; i < size; i++) {
			boarCulledListBuffer.append(boarCulledList.get(i)).append(",");					
		}
		rows.add(boarCulledListBuffer.toString());
		rows.add("\n");
		
		StringBuffer boarDeathsandDestroyedListBuffer = new StringBuffer();
		boarDeathsandDestroyedListBuffer.append("Boar Deaths and Destroyed,");
		for (int i = 0; i < size; i++) {
			boarDeathsandDestroyedListBuffer.append(boarDeathsandDestroyedList.get(i)).append(",");					
		}
		rows.add(boarDeathsandDestroyedListBuffer.toString());
		rows.add("\n");
		
		rows.add("\n");
		rows.add("Sow Days Statistics");
		rows.add("\n");
		
		
		StringBuffer sowBoarRatioListBuffer = new StringBuffer();
		sowBoarRatioListBuffer.append("Sow - Boar Ratio,");
		for (int i = 0; i < size; i++) {
			sowBoarRatioListBuffer.append(sowBoarRatioList.get(i)).append(",");					
		}
		rows.add(sowBoarRatioListBuffer.toString());
		rows.add("\n");
		
		StringBuffer littersFemaleYearListBuffer = new StringBuffer();
		littersFemaleYearListBuffer.append("Litters/Female/Year,");
		for (int i = 0; i < size; i++) {
			littersFemaleYearListBuffer.append(littersFemaleYearList.get(i)).append(",");					
		}
		rows.add(littersFemaleYearListBuffer.toString());
		rows.add("\n");
		
		StringBuffer littersMatedFemaleYearListBuffer = new StringBuffer();
		littersMatedFemaleYearListBuffer.append("Litters/Mated Female/Year,");
		for (int i = 0; i < size; i++) {
			littersMatedFemaleYearListBuffer.append(littersMatedFemaleYearList.get(i)).append(",");					
		}
		rows.add(littersMatedFemaleYearListBuffer.toString());
		rows.add("\n");
		
		StringBuffer pigsWeanedFemaleYearListBuffer = new StringBuffer();
		pigsWeanedFemaleYearListBuffer.append("Pigs Weaned/Female/Year,");
		for (int i = 0; i < size; i++) {
			pigsWeanedFemaleYearListBuffer.append(pigsWeanedFemaleYearList.get(i)).append(",");					
		}
		rows.add(pigsWeanedFemaleYearListBuffer.toString());
		rows.add("\n");	
		
		StringBuffer pigsWeanedMatedFemaleYearListBuffer = new StringBuffer();
		pigsWeanedMatedFemaleYearListBuffer.append("Pigs Weaned/Mated Female/Year,");
		for (int i = 0; i < size; i++) {
			pigsWeanedMatedFemaleYearListBuffer.append(pigsWeanedMatedFemaleYearList.get(i)).append(",");					
		}
		rows.add(pigsWeanedMatedFemaleYearListBuffer.toString());
		rows.add("\n");	
		
		StringBuffer pigsWeanedLifetimeListBuffer = new StringBuffer();
		pigsWeanedLifetimeListBuffer.append("Pigs Weaned/Lifetime,");
		for (int i = 0; i < size; i++) {
			pigsWeanedLifetimeListBuffer.append(pigsWeanedLifetimeList.get(i)).append(",");					
		}
		rows.add(pigsWeanedLifetimeListBuffer.toString());
		rows.add("\n");	
		
		return rows;
	}
	
	@RequestMapping(value = "/reportGeneration", method = RequestMethod.GET)
	public ModelAndView reportGeneration(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportGeneration.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}
	
	
	
	//Production Event Log start
	@RequestMapping(value = "/prodEventLog", method = RequestMethod.GET)
	public ModelAndView prodEventLog(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "prodEventLog.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}	
	
	
	@RequestMapping(value = "/generateProdEventLog", method = RequestMethod.POST)
	public void generateProdEventLog(HttpServletRequest request, HttpServletResponse response) {
		try {
			String selectedPremise = request.getParameter("selectedPremise");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");	    
			List<String> rows =new ArrayList<String>();			
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			try {
				Integer premiseId = Integer.parseInt(selectedPremise);
				Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
				if(premise != null)
				{
					response.setContentType("text/csv");
					String reportName = "CSV_Report_ProdEventLog_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
					response.setHeader("Content-disposition", "attachment;filename="+reportName);
					if(premiseId > 0)
					{ 
						rows = prodEventLogService.getProdEventLogList(premise.getPermiseId(), premiseId, DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"), localeResolver.resolveLocale(request)); 
						Iterator<String> iter = rows.iterator();
						while (iter.hasNext()) {
							String outputString = (String) iter.next();
							response.getOutputStream().print(outputString);
						}
					}
					else
					{
						rows.add("Cannot find premise by given Id");
					}
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
	//Production Event Log end
	
	//Target Report start
	@RequestMapping(value = "/targetReport", method = RequestMethod.GET)
	public ModelAndView goTotargetReport(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "targetReport.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}	
	
	
	@RequestMapping(value = "/generateTargetReport", method = RequestMethod.POST)
	public void generateTargetReport(HttpServletRequest request, HttpServletResponse response) {
		try {

			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String startDate = request.getParameter("startDate");   
			List<String> rows =new ArrayList<String>();			
			try {
				Integer companyId = Integer.parseInt(request.getParameter("selectedCompany"));
				Integer premiseId = Integer.parseInt(request.getParameter("selectedPremise"));
				response.setContentType("text/csv");
				String reportName = "CSV_Report_Target_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+".csv";
				response.setHeader("Content-disposition", "attachment;filename="+reportName);
				rows = targetReportService.getTargetList(companyId,premiseId, DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), localeResolver.resolveLocale(request)); 
				Iterator<String> iter = rows.iterator();
				while (iter.hasNext()) {
					String outputString = (String) iter.next();
					response.getOutputStream().print(outputString);
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
	//Target Report end	
	
	//Litter Balance start
	@RequestMapping(value = "/litterBalance", method = RequestMethod.GET)
	public ModelAndView litterBalance(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "litterBalance.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		if(request.getParameter("nodata") == null)
		{
			request.getSession().removeAttribute("REPORT_NO_DATA");
		}
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}	
	
	
	@RequestMapping(value = "/generateLitterBalance", method = RequestMethod.POST)
	public void generateLitterBalance(HttpServletRequest request, HttpServletResponse response) {
		try {
			String selectedPremise = request.getParameter("selectedPremise");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");	    
			String fromOverView = request.getParameter("fromOverView");
			List<String> rows =new ArrayList<String>();			
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			try {
				Integer premiseId = Integer.parseInt(selectedPremise);
				Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
				if(premise != null)
				{
					response.setContentType("text/csv");
					String reportName = "CSV_Report_LitterBalance_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
					response.setHeader("Content-disposition", "attachment;filename="+reportName);
					if(premiseId > 1)
					{ 
						rows = litterBalanceService.getLitterBalance(premise.getPermiseId(), premiseId, 
								DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"), localeResolver.resolveLocale(request));
						if(rows != null && rows.size() > 0)
						{
							Iterator<String> iter = rows.iterator();
							while (iter.hasNext()) {
								String outputString = (String) iter.next();
								response.getOutputStream().print(outputString);
							}
						}
					}
					else
					{
						request.getSession(true).setAttribute("REPORT_NO_DATA", true);
						if(fromOverView == null)
						{
							response.sendRedirect("litterBalance?nodata=true");
						}
						else
						{
							response.sendRedirect("overViewReport?nodata=true");
						}
					}
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
	//Litter Balance end	
	
	//Ration Report start
	@RequestMapping(value = "/rationReport", method = RequestMethod.GET)
	public ModelAndView rationReport(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportRation.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}	
	
	
	@RequestMapping(value = "/generateRationReport", method = RequestMethod.POST)
	public void generateRationReport(HttpServletRequest request, HttpServletResponse response) {
		try {
			String selectedPremise = request.getParameter("selectedPremise");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");	 
			String selectedGroup = request.getParameter("selectedGroup");
			List<String> rows =new ArrayList<String>();
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			try {
				Integer premiseId = Integer.parseInt(selectedPremise);
				Integer groupId = Integer.parseInt(selectedGroup);
				
				Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
				if(premise != null)
				{
					response.setContentType("text/csv");
					String reportName = "CSV_Report_Ration_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
					response.setHeader("Content-disposition", "attachment;filename="+reportName);
					if(premiseId > 0)
					{ 
						rows = rationReportService.getRationReportList(premise.getPermiseId(), premiseId, DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"), groupId, localeResolver.resolveLocale(request)); 
						Iterator<String> iter = rows.iterator();
						while (iter.hasNext()) {
							String outputString = (String) iter.next();
							response.getOutputStream().print(outputString);
						}
					} 
					else
					{
						rows.add("Cannot find premise by given Id");
					}
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
	//Ration Report end	
	
	
	//Gestation Report start
		@RequestMapping(value = "/gestationReport", method = RequestMethod.GET)
		public ModelAndView gestationReport(HttpServletRequest request) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("contentUrl", "gestationReport.jsp");
			model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer companyId = activeUser.getCompanyId();
			model.put("CompanyId", companyId+"");
			return new ModelAndView("template", model);
		}	
		
		
		@RequestMapping(value = "/generateGestationReport", method = RequestMethod.POST)
		public void generateGestationReport(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");	    
				List<String> rows =new ArrayList<String>();	
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				try {
					Integer premiseId = Integer.parseInt(selectedPremise);
					Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
					if(premise != null)
					{
						response.setContentType("text/csv");
						String reportName = "CSV_Report_GestationTracking_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
						response.setHeader("Content-disposition", "attachment;filename="+reportName);
						if(premiseId > 0)
						{ 
							rows = gestationReportService.getGestationResult(premise.getPermiseId(), premiseId, DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"), localeResolver.resolveLocale(request)); 
							Iterator<String> iter = rows.iterator();
							while (iter.hasNext()) {
								String outputString = (String) iter.next();
								response.getOutputStream().print(outputString);
							}
						}
						else
						{
							rows.add("Cannot find premise by given Id");
						}
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
		//Gestation Report end	
		
		// Sow report
	@RequestMapping(value = "/reportGenerationSow", method = RequestMethod.GET)
		public ModelAndView reportGenerationSow(HttpServletRequest request) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("contentUrl", "reportGenerationSow.jsp");
			model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
			
			if(request.getParameter("nodata") == null)
			{
				request.getSession().removeAttribute("REPORT_NO_DATA");
			}
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer companyId = activeUser.getCompanyId();
			model.put("CompanyId", companyId+"");
			return new ModelAndView("template", model);
		}
		
	
	@RequestMapping(value = "/generateReportSow", method = RequestMethod.POST)
	public void generateReportSow(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				String search = request.getParameter("pigId");
				String companyString = request.getParameter("companyId1");
				String fromOverView = request.getParameter("fromOverView");
				Integer companyId ;
				
				System.out.println("selectedPremise = " + selectedPremise);
				System.out.println("search = " + search);
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				Locale locale = localeResolver.resolveLocale(request);
				
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
				
				List<String> rows =new ArrayList<String>();
						
				try {
					int premisesId = Integer.parseInt(selectedPremise);
					PigInfo pigInformation = pigInfoDao.getPigInformationByPigIdWithOutStatus(search, companyId, premisesId);
					if(null != pigInformation && pigInformation.getId() != null && pigInformation.getId() != 0)
					{
						rows = sowReportService.getSowReport(search,pigInformation.getId(), companyId, language, locale);
						if(rows != null && rows.size() > 0)
						{
							
							//String reportName = "CSV_Sow_"+search+".csv";
							String date = DateUtil.convertToFormatString(new java.util.Date(System.currentTimeMillis()),"mm-dd-yyyy");
							String reportName = "SowHistory_"+selectedPremise+"_"+search+"_"+(new java.util.Date(System.currentTimeMillis())).toString()+".csv";
							response.setHeader("Content-disposition", "attachment;filename="+reportName);
					    
							
							Iterator<String> iter = rows.iterator();
							while (iter.hasNext()) {
								String outputString = (String) iter.next();
								response.getOutputStream().print(outputString);
							}
							
							request.getSession(true).setAttribute("REPORT_NO_DATA", false);
							response.getOutputStream().flush();
						}
						else
						{
							request.getSession(true).setAttribute("REPORT_NO_DATA", true);
							if(fromOverView == null)
							{
								response.sendRedirect("reportGenerationSow?nodata=true");
							}
							else
							{
								response.sendRedirect("overViewReport?nodata=true");
							}
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
	
	@RequestMapping(value = "/reportGenerationGroup", method = RequestMethod.GET)
	public ModelAndView reportGenerationGroup(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportGenerationGroup.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		if(request.getParameter("nodata") == null)
		{
			request.getSession().removeAttribute("REPORT_NO_DATA");
		}
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}
	

	@RequestMapping(value = "/generateReportGroup", method = RequestMethod.POST)
	public void generateReportGroup(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				String search = request.getParameter("groupId");
				String companyString = request.getParameter("companyId1");
				String fromOverView = request.getParameter("fromOverView");
				Integer companyId ;
				
				System.out.println("selectedPremise = " + selectedPremise);
				System.out.println("search = " + search);
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				Locale locale = localeResolver.resolveLocale(request);
				
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
				
				
				List<String> rows =new ArrayList<String>();
						
				try {
					int premisesId = Integer.parseInt(selectedPremise);
					GroupEvent groupEventByGroupId = groupEventService.getGroupEventByGroupId(search, companyId, premisesId);
					if(null != groupEventByGroupId && groupEventByGroupId.getId() != null && groupEventByGroupId.getId() != 0)
					{
						//rows = groupReportService.getSowReport(search,groupEventByGroupId.getId(), companyId, language);
						rows = groupReportService.getGroupReportwithPhase(search,groupEventByGroupId, companyId, language, locale);
						if(rows != null && rows.size() >0 )
						{
							response.setContentType("text/csv");
							String date = DateUtil.convertToFormatString(new java.util.Date(System.currentTimeMillis()),"mm-dd-yyyy");
							//String reportName = "GroupHistory_"+selectedPremise+"_"+search+"_"+date+".csv";
							String reportName = "GroupHistory_"+selectedPremise+"_"+search+"_"+(new java.util.Date(System.currentTimeMillis())).toString()+".csv";
							response.setHeader("Content-disposition", "attachment;filename="+reportName);
					    
							Iterator<String> iter = rows.iterator();
							while (iter.hasNext()) {
								String outputString = (String) iter.next();
								response.getOutputStream().print(outputString);
							}
							
							request.getSession(true).setAttribute("REPORT_NO_DATA", false);
							response.getOutputStream().flush();
						}
						else
						{
							request.getSession(true).setAttribute("REPORT_NO_DATA", true);
							if(fromOverView == null)
							{
								response.sendRedirect("reportGenerationGroup?nodata=true");
							}
							else
							{
								response.sendRedirect("overViewReport?nodata=true");
							}
							
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
						rows = actionListReportService.getActionList(premiseId, localeResolver.resolveLocale(request));
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
		
		if(request.getParameter("nodata") == null)
		{
			request.getSession().removeAttribute("REPORT_NO_DATA");
		}
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}

	
	@RequestMapping(value = "/generateInventoryStatusReport", method = RequestMethod.POST)
	public void generateInventoryStatusReport(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				String fromOverView = request.getParameter("fromOverView");
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				
				PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    
				List<String> rows =new ArrayList<String>();
						
				try {
					Integer premiseId = Integer.parseInt(selectedPremise);
					
					Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
					if(premise != null)
					{
						if(premiseId > 0)
						{
							rows = inventoryStatusReportService.getInventoryStatusList(premiseId, localeResolver.resolveLocale(request));
							if(rows != null && rows.size() > 1)
							{
								response.setContentType("text/csv");
								String reportName = "CSV_Report_InventoryStatusReport_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
								response.setHeader("Content-disposition", "attachment;filename="+reportName);
								
								
								Iterator<String> iter = rows.iterator();
								while (iter.hasNext()) {
									String outputString = (String) iter.next();
									response.getOutputStream().print(outputString);
								}
							}
							else
							{
								request.getSession(true).setAttribute("REPORT_NO_DATA", true);
								if(fromOverView == null)
								{
									response.sendRedirect("reportInventoryStatus?nodata=true");
								}
								else
								{
									response.sendRedirect("overViewReport?nodata=true");
								}
								
							}
						}
						else
						{
							rows.add("Can not find Group by given Id");
						}
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
	
	
	@RequestMapping(value = "/reportLactationLength", method = RequestMethod.GET)
	public ModelAndView reportLactationLength(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportLactationLength.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}
	
	
	@RequestMapping(value = "/generateLactationLengthReport", method = RequestMethod.POST)
	public void generateLactationLengthReport(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				
				PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    
				List<String> rows =new ArrayList<String>();
						
				try {
					Integer premiseId = Integer.parseInt(selectedPremise);
					
					Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
					if(premise != null)
					{
						response.setContentType("text/csv");
						String reportName = "CSV_Report_LactationLength_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
						response.setHeader("Content-disposition", "attachment;filename="+reportName);
						
						
						if(premiseId > 0)
						{ 
							rows = lactationLengthService.getLactationLength(premiseId, DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"), localeResolver.resolveLocale(request)); 
							Iterator<String> iter = rows.iterator();
							while (iter.hasNext()) {
								String outputString = (String) iter.next();
								response.getOutputStream().print(outputString);
							}
						}
						else
						{
							rows.add("Can not find premise by given Id");
						}
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
	
	
	
	@RequestMapping(value = "/reportPigletMortality", method = RequestMethod.GET)
	public ModelAndView reportPigletMortality(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportPigletMortality.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		
		if(request.getParameter("nodata") == null)
		{
			request.getSession().removeAttribute("REPORT_NO_DATA");
		}
		
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer companyId = activeUser.getCompanyId();
		model.put("CompanyId", companyId+"");
		return new ModelAndView("template", model);
	}
	
	
	@RequestMapping(value = "/generatePigletMortalityReport", method = RequestMethod.POST)
	public void generatePigletMortalityReport(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String fromOverView = request.getParameter("fromOverView");
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				
				PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    
				List<String> rows =new ArrayList<String>();
						
				try {
					Integer premiseId = Integer.parseInt(selectedPremise);
					
					Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
					if(premise != null)
					{
												
						if(premiseId > 0)
						{ 
							rows = pigletMortalityReportService.getPigletMortalityList(premise.getPermiseId(), premiseId, DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"), localeResolver.resolveLocale(request));
							if(rows != null && rows.size() > 1)
							{
								response.setContentType("text/csv");
								String reportName = "CSV_Report_PigletMortality_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
								response.setHeader("Content-disposition", "attachment;filename="+reportName);
								
								Iterator<String> iter = rows.iterator();
								while (iter.hasNext()) {
									String outputString = (String) iter.next();
									response.getOutputStream().print(outputString);
								}
								request.getSession(true).setAttribute("REPORT_NO_DATA", false);
								response.getOutputStream().flush();
								
							}
							else
							{
								request.getSession(true).setAttribute("REPORT_NO_DATA", true);
								if(fromOverView == null)
								{
									response.sendRedirect("reportPigletMortality?nodata=true");
								}
								else
								{
									response.sendRedirect("overViewReport?nodata=true");
								}
								
							}
						}
						else
						{
							rows.add("Can not find premise by given Id");
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					rows.add("There is some error please contact Admin");
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	
	//Removal Report Starts
		@RequestMapping(value = "/saleReport", method = RequestMethod.GET)
		public ModelAndView SaleReport(HttpServletRequest request) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("contentUrl", "reportGenerationSale.jsp");
			model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
			
			if(request.getParameter("nodata") == null)
			{
				request.getSession().removeAttribute("REPORT_NO_DATA");
			}
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer companyId = activeUser.getCompanyId();
			model.put("CompanyId", companyId+"");
			return new ModelAndView("template", model);
		}
		
		@RequestMapping(value = "/generateSaleReport", method = RequestMethod.POST)
		public void generateSaleReport(HttpServletRequest request, HttpServletResponse response) {
				try {
					String selectedPremise = request.getParameter("selectedPremise");
					String startDate = request.getParameter("startDate");
					String endDate = request.getParameter("endDate");
					String group = request.getParameter("groupId");
					String barn = request.getParameter("selectedBarn");
					String ticketNumber = request.getParameter("ticketNumber");
					String pig = request.getParameter("pigId");
					String fromOverView = request.getParameter("fromOverView");
					
					String companyString = request.getParameter("companyId1");
					Integer companyId ;
					
					
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
				
					List<String> rows =new ArrayList<String>();
							
					try {
						Integer premiseId = Integer.parseInt(selectedPremise);
						Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
						
						int groupId = 0;
						if(group != null && !StringUtils.isEmpty(group))
						{
							GroupEvent groupEventByGroupId = groupEventService.getGroupEventByGroupId(group, companyId, premiseId);
							if(null != groupEventByGroupId && groupEventByGroupId.getId() != null && groupEventByGroupId.getId() != 0)
							{
								groupId = groupEventByGroupId.getId();
							}
							
						}
						
						int barnId = 0;
						if(!StringUtils.isEmpty(barn))
						{
							barnId = Integer.parseInt(barn);
						}
						
						int pigId = 0;
						if(pig != null && !StringUtils.isEmpty(pig))
						{
							PigInfo pigInformation = pigInfoDao.getPigInformationByPigIdWithOutStatus(pig, companyId, premiseId);
							if(null != pigInformation && pigInformation.getId() != null && pigInformation.getId() != 0)
							{
								pigId = pigInformation.getId();
							}							
						}
						
						if(premise != null)
						{
							
							if(premiseId > 0)
							{ 
								java.util.Date startD = null;
								if(startDate != null && !StringUtils.isEmpty(startDate))
								{
									startD = DateUtil.convertToFormat(startDate, "dd/MM/yyyy");
								}
								java.util.Date endD =  null;
								if(endDate != null && !StringUtils.isEmpty(endDate))
								{
									endD = DateUtil.convertToFormat(endDate, "dd/MM/yyyy");
								}
								
								
								rows = saleReportService.getSaleList(selectedPremise,premiseId, groupId, startD, DateUtil.convertToFormat(endDate, "dd/MM/yyyy"),barnId,ticketNumber, language, pigId, localeResolver.resolveLocale(request));
								if(rows!= null && rows.size() > 0)
								{
									response.setContentType("text/csv");
									String reportName = "CSV_Report_Sale_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
									response.setHeader("Content-disposition", "attachment;filename="+reportName);
									
								
									Iterator<String> iter = rows.iterator();
									while (iter.hasNext()) {
										String outputString = (String) iter.next();
										response.getOutputStream().print(outputString);
									}
									request.getSession(true).setAttribute("REPORT_NO_DATA", false);
									response.getOutputStream().flush();
								}
								
								else
								{
									request.getSession(true).setAttribute("REPORT_NO_DATA", true);
									if(fromOverView == null)
									{
										response.sendRedirect("saleReport?nodata=true");
									}
									else
									{
										response.sendRedirect("overViewReport?nodata=true");
									}
									
								}
							}
							else
							{
								rows.add("Can not find premise by given Id");
							}
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
		
		//Removal Report Starts
				@RequestMapping(value = "/removalReport", method = RequestMethod.GET)
				public ModelAndView RemovalReport(HttpServletRequest request) {
					Map<String, String> model = new HashMap<String, String>();
					model.put("contentUrl", "reportGenerationRemoval.jsp");
					model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
					
					if(request.getParameter("nodata") == null)
					{
						request.getSession().removeAttribute("REPORT_NO_DATA");
					}
					
					PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					Integer companyId = activeUser.getCompanyId();
					model.put("CompanyId", companyId+"");
					return new ModelAndView("template", model);
				}
				
				@RequestMapping(value = "/generateRemovalReport", method = RequestMethod.POST)
				public void generateRemovalReport(HttpServletRequest request, HttpServletResponse response) {
						try {
							String selectedPremise = request.getParameter("selectedPremise");
							String startDate = request.getParameter("startDate");
							String endDate = request.getParameter("endDate");
							String group = request.getParameter("groupId");
							String pig = request.getParameter("pigId");
							String animalType = request.getParameter("animalType");
							String fromOverView = request.getParameter("fromOverView");
							
							String companyString = request.getParameter("companyId1");
							Integer companyId ;
							
							
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
						
							List<String> rows =new ArrayList<String>();
									
							try {
								Integer premiseId = Integer.parseInt(selectedPremise);
								Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
								
								int groupId = 0;
								if(group != null && !StringUtils.isEmpty(group))
								{
									GroupEvent groupEventByGroupId = groupEventService.getGroupEventByGroupId(group, companyId, premiseId);
									if(null != groupEventByGroupId && groupEventByGroupId.getId() != null && groupEventByGroupId.getId() != 0)
									{
										groupId = groupEventByGroupId.getId();
									}
									
								}
								
								int pigId = 0;
								if(pig != null && !StringUtils.isEmpty(pig))
								{
									PigInfo pigInformation = pigInfoDao.getPigInformationByPigIdWithOutStatus(pig, companyId, premiseId);
									if(null != pigInformation && pigInformation.getId() != null && pigInformation.getId() != 0)
									{
										pigId = pigInformation.getId();
									}							
								}
								
								if(premise != null)
								{
									
									if(premiseId > 0)
									{ 
										rows = removalReportService.getRemovalList(selectedPremise,premiseId, DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"),language, animalType);
										if(rows != null && rows.size()>0)
										{
											
											response.setContentType("text/csv");
											String reportName = "CSV_Report_Removal_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
											response.setHeader("Content-disposition", "attachment;filename="+reportName);
											
											
											Iterator<String> iter = rows.iterator();
											while (iter.hasNext()) {
												String outputString = (String) iter.next();
												response.getOutputStream().print(outputString);
											}
											
											request.getSession(true).setAttribute("REPORT_NO_DATA", false);
											response.getOutputStream().flush();
										}
										else
										{
											request.getSession(true).setAttribute("REPORT_NO_DATA", true);
											if(fromOverView == null)
											{
												response.sendRedirect("removalReport?nodata=true");
											}
											else
											{
												response.sendRedirect("overViewReport?nodata=true");
											}
											
										}
									}
									else
									{
										rows.add("Can not find premise by given Id");
									}
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
	
	
		//Removal Report Starts
		@RequestMapping(value = "/feedReport", method = RequestMethod.GET)
		public ModelAndView feedReport(HttpServletRequest request) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("contentUrl", "reportGenerationFeed.jsp");
			model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
			
			if(request.getParameter("nodata") == null)
			{
				request.getSession().removeAttribute("REPORT_NO_DATA");
			}
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer companyId = activeUser.getCompanyId();
			model.put("CompanyId", companyId+"");
			return new ModelAndView("template", model);
		}
		
		@RequestMapping(value = "/generateFeedReport", method = RequestMethod.POST)
		public void generateFeedReport(HttpServletRequest request, HttpServletResponse response) {
			
			boolean flag = true;
			Integer companyId =0 ;
				try {
					String selectedPremise = request.getParameter("selectedPremise");
					String companyString = request.getParameter("companyId1");
					String fromOverView = request.getParameter("fromOverView");
					
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
				
					List<String> rows =new ArrayList<String>();
							
					try {
						Integer premiseId = Integer.parseInt(selectedPremise);
						Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
						
						if(premise != null)
						{
							if(premiseId > 0)
							{ 
								rows = feedReportService.getFeedList(selectedPremise,premiseId, localeResolver.resolveLocale(request)); 
								if(rows == null || rows.size() > 0)
								{
									response.setContentType("text/csv");
									String reportName = "CSV_Report_Feed_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
									response.setHeader("Content-disposition", "attachment;filename="+reportName);
									
									Iterator<String> iter = rows.iterator();
									while (iter.hasNext()) {
										String outputString = (String) iter.next();
										response.getOutputStream().print(outputString);
									}
									request.getSession(true).setAttribute("REPORT_NO_DATA", false);
									response.getOutputStream().flush();
								}
								else
								{
									request.getSession(true).setAttribute("REPORT_NO_DATA", true);
									
									if(fromOverView == null)
									{
										response.sendRedirect("feedReport?nodata=true");
									}
									else
									{
										response.sendRedirect("overViewReport?nodata=true");
									}
								}
							}
							else
							{
								rows.add("Can not find premise by given Id");
							}
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
				
				
				
				
		//Group Status Report start
		@RequestMapping(value = "/groupStatusReport", method = RequestMethod.GET)
		public ModelAndView groupStatusReport(HttpServletRequest request) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("contentUrl", "groupStatusReport.jsp");
			model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer companyId = activeUser.getCompanyId();
			model.put("CompanyId", companyId+"");
			return new ModelAndView("template", model);
		}	
		
		
		@RequestMapping(value = "/generateGroupStatusReport", method = RequestMethod.POST)
		public void generateGroupStatusReport(HttpServletRequest request, HttpServletResponse response) {
			try {
				String selectedPremise = request.getParameter("selectedPremise");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");	
				String groupId = request.getParameter("selectedGroup");
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				
				List<String> rows =new ArrayList<String>();			
				try {
					Integer premiseId = Integer.parseInt(selectedPremise);
					Premises premise = premiseDao.findByPremisesByAutoGeneratedId(premiseId);
					if(premise != null)
					{
						response.setContentType("text/csv");
						String reportName = "CSV_Report_GroupStatusReport_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+"_"+premise.getPermiseId()+".csv";
						response.setHeader("Content-disposition", "attachment;filename="+reportName);
						if(premiseId > 0)
						{ 
							rows = groupStatusReportService.getGroupStatusResult(premise.getPermiseId(), premiseId, DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"), groupId, localeResolver.resolveLocale(request)); 
							Iterator<String> iter = rows.iterator();
							while (iter.hasNext()) {
								String outputString = (String) iter.next();
								response.getOutputStream().print(outputString);
							}
						}
						else
						{
							rows.add("Cannot find premise by given Id");
						}
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
				//Gestation Report end						
				
					
		//Data Integrity Report start
		@RequestMapping(value = "/dataIntegrityReport", method = RequestMethod.GET)
		public ModelAndView dataIntegrityReport(HttpServletRequest request) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("contentUrl", "dataIntegrityReport.jsp");
			model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
			
			if(request.getParameter("nodata") == null)
			{
				request.getSession().removeAttribute("REPORT_NO_DATA");
			}
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer companyId = activeUser.getCompanyId();
			model.put("CompanyId", companyId+"");
			
			return new ModelAndView("template", model);
		}	
								
		@RequestMapping(value = "/generateDataIntegrityReport", method = RequestMethod.POST)
		public void generateDataIntegrityReport(HttpServletRequest request, HttpServletResponse response) {
			try {
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				List<String> rows =new ArrayList<String>();			
				try {
						response.setContentType("text/csv");
						String reportName = "CSV_Report_DataIntegrity_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+".csv";
						response.setHeader("Content-disposition", "attachment;filename="+reportName);
						
						rows = logService.getLog(DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"), localeResolver.resolveLocale(request));
						if(rows != null && rows.size() > 1)
						{
							Iterator<String> iter = rows.iterator();
							while (iter.hasNext()) {
								String outputString = (String) iter.next();
								response.getOutputStream().print(outputString);
							}		
						}
						else
						{
							request.getSession(true).setAttribute("REPORT_NO_DATA", true);
							response.sendRedirect("dataIntegrityReport?nodata=true");
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
		
		// over view report
		@RequestMapping(value = "/overViewReport", method = RequestMethod.GET)
		public ModelAndView overViewReport(HttpServletRequest request) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("contentUrl", "overViewReport.jsp");
			model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
			
			if(request.getParameter("nodata") == null)
			{
				request.getSession().removeAttribute("REPORT_NO_DATA");
			}
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer companyId = activeUser.getCompanyId();
			model.put("CompanyId", companyId+"");
			return new ModelAndView("template", model);
		}		
	
		
		//Data Integrity Report start
		@RequestMapping(value = "/dataExtraction", method = RequestMethod.GET)
		public ModelAndView dataExtractionPig(HttpServletRequest request) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("contentUrl", "dataExtractionReport.jsp");
			model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
			
			if(request.getParameter("nodata") == null)
			{
				request.getSession().removeAttribute("REPORT_NO_DATA");
			}
			

			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer companyId = activeUser.getCompanyId();
			model.put("CompanyId", companyId+"");
			
			return new ModelAndView("template", model);
		}	
								
		@RequestMapping(value = "/generateDataExtraction", method = RequestMethod.POST)
		public void generateDataExtraction(HttpServletRequest request, HttpServletResponse response) {
			try {
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				Locale locale = localeResolver.resolveLocale(request);
				String language = localeResolver.resolveLocale(request).getLanguage();
				
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String reportOption = request.getParameter("reportOption");
				String selectedPremise = request.getParameter("selectedPremise");
				String selectedEvent = request.getParameter("selectedEvent");
				String groupIdStr = request.getParameter("selectedGroup");
				String pigIdStr = request.getParameter("selectedPig");
				
				List<String> rows =new ArrayList<String>();			
				try {
					    Integer premiseId = Integer.parseInt(selectedPremise);
					    Integer eventTypeId = null;
					    Integer groupId = null;
					    Integer pigId = null;
					    try{
					    	groupId = Integer.parseInt(groupIdStr);
					    }catch(NumberFormatException nfEx)
					    {
					    	groupId = null;
					    }
					    
					    try{
					    	pigId = Integer.parseInt(pigIdStr);
					    }catch(NumberFormatException nfEx)
					    {
					    	pigId = null;
					    }
					    
					    try{
					    	eventTypeId = Integer.parseInt(selectedEvent);
					    }catch(NumberFormatException nfEx)
					    {
					    	eventTypeId = null;
					    }
					   
					    
						response.setContentType("text/csv");
						String reportName = "CSV_Report_DataExtractionReport_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+".csv";
						response.setHeader("Content-disposition", "attachment;filename="+reportName);
						 
						rows = extractionService.getData(premiseId, pigId, groupId, reportOption, eventTypeId,  DateUtil.convertToFormat(startDate, "dd/MM/yyyy"), DateUtil.convertToFormat(endDate, "dd/MM/yyyy"), locale);
						if(rows != null && rows.size() > 1)
						{
							Iterator<String> iter = rows.iterator();
							while (iter.hasNext()) {
								String outputString = (String) iter.next();
								response.getOutputStream().print(outputString);
							}		
						}
						else
						{
							request.getSession(true).setAttribute("REPORT_NO_DATA", true);
							response.sendRedirect("dataExtraction?nodata=true");
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
		
		// Sow Card report
		@RequestMapping(value = "/sowCardReport", method = RequestMethod.GET)
		public ModelAndView sowCardReport(HttpServletRequest request) {
			Map<String, String> model = new HashMap<String, String>();
			model.put("contentUrl", "reportGenerationsowCard.jsp");
			model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
			
			if(request.getParameter("nodata") == null)
			{
				request.getSession().removeAttribute("REPORT_NO_DATA");
			}
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Integer companyId = activeUser.getCompanyId();
			model.put("CompanyId", companyId+"");
			return new ModelAndView("template", model);
		}		

		@RequestMapping(value = "/generateSowCardReport", method = RequestMethod.POST)
		public void generateOverViewReport(HttpServletRequest request, HttpServletResponse response) {
			try {
				
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				Locale locale = localeResolver.resolveLocale(request);
				String language = localeResolver.resolveLocale(request).getLanguage();

				String selectedPremise = request.getParameter("selectedPremise");
				String pigIdStr = request.getParameter("pigId");
				String companyString = request.getParameter("companyId1");
				
				PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Integer companyId = 0;
				if(companyString != null && !StringUtils.isEmpty(companyString))
				{
					companyId = Integer.parseInt(companyString);
				}
				else
				{
					companyId = activeUser.getCompanyId();
				}
				
				List<StringBuffer> rows =new ArrayList<StringBuffer>();			
				try {
					    Integer premiseId = Integer.parseInt(selectedPremise);
					    Integer pigId = null;
					    
					    if(pigIdStr != null && !StringUtils.isEmpty(pigIdStr))
						{
							PigInfo pigInformation = pigInfoDao.getPigInformationByPigIdWithOutStatus(pigIdStr, companyId, premiseId);
							if(null != pigInformation && pigInformation.getId() != null && pigInformation.getId() != 0)
							{
								pigId = pigInformation.getId();
							}							
						}
					      
					    
						response.setContentType("text/csv");
						String reportName = "CSV_Report_SowCardReport_"+DateUtil.convertToFormatString(DateUtil.getToday(),"dd/MM/yyyy")+".csv";
						response.setHeader("Content-disposition", "attachment;filename="+reportName);
						 
						rows = sowCardReportService.getSowCardList(selectedPremise, premiseId,language, pigId );
						if(rows != null && rows.size() > 1)
						{
							Iterator<StringBuffer> iter = rows.iterator();
							while (iter.hasNext()) {
								StringBuffer outputString = (StringBuffer) iter.next();
								response.getOutputStream().print(outputString.append("\n").toString());
							}		
						}
						else
						{
							request.getSession(true).setAttribute("REPORT_NO_DATA", true);
							response.sendRedirect("sowCardReport?nodata=true");
						}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//rows.add("There is some error please contact Admin");
				}
				response.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
}
