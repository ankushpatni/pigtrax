package com.pigtrax.master.controller;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pigtrax.master.service.interfaces.ReportService;
import com.pigtrax.usermanagement.beans.PigTraxUser;

@RestController
public class ReportControlller {
	
	@Autowired
	private HttpBatchPost httpBatchPost;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ReportService reportService;
	
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
		
		
		while(itr.hasNext())
		{
			Date calWeek = (Date)itr.next();
			String key = (calWeek).toString();
			dateList.add(key);
			Map valueMap = (Map)map.get(calWeek);
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
			
			totalBornList.add(totalBorn);
			totalLiveBornList.add(totalLiveBorn);
			totalDeathList.add(totalDeath);
			totalStillBornList.add(totalStillBorn);
			totalMummiesList.add(totalMummies);
			totalBirthWeightList.add(totalBirthWeight);
			litterWithAgeLessSevenList.add(litterWithAgeLessSeven);
			totalPigsWeavnedList.add(totalPigsWeavened);
			
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
			
			float percentageLitterWithAgeLessSeven = ((float)(litterWithAgeLessSeven)/totalLiveBorn)*100;
			
			float ferrowCapacity = (float)(totalFerrow)/totalActivePenAvailable;
			
			float percentageLittersWeanedWithMoreThanTwalePigs = ((float)(littersWeanedWithMoreThanTwalePigs)/litterWithAgeLessSeven)*100;
			float percentageSowsWeaningZeroPig  = ((float)(sowsWeaningZeroPig)/totalFerrow)*100;
			
			float pigsWeaningDivideLitterWeaned = (float)(totalPigsWeavened)/littersWeaned;
			float pigsWeaningDivideSowWeaned = (float)(totalPigsWeavened)/totalLitterSWeanedFosterInOut;
			
			float percentagePingsWeanedDivideByTotalPigs = ((float)(totalPigsWeavened)/totalBorn)*100;
			
			int netFoster = totalLiveBorn -totalPigsMortal- totalPigsWeavened;
			
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
			
		}
		
		int size = dateList.size();
		StringBuffer dateBuffer = new StringBuffer();
		dateBuffer.append("Calander Week,");
		ArrayList<String> rows = new ArrayList<String>();
		for(int i=0;i<size;i++)
		{
			dateBuffer.append(dateList.get(i)+",");
		}
		
		rows.add(dateBuffer.toString());
		rows.add("\n");
		
		StringBuffer totalBornBuffer = new StringBuffer();
		totalBornBuffer.append("Total Born,");
		for (int i = 0; i < size; i++) {
			totalBornBuffer.append(totalBornList.get(i)+",");					
		}
		rows.add(totalBornBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageTotalBornBuffer = new StringBuffer();
		averageTotalBornBuffer.append("Average Total Born,");
		for (int i = 0; i < size; i++) {
			averageTotalBornBuffer.append(averageTotalBornList.get(i)+",");					
		}
		rows.add(averageTotalBornBuffer.toString());
		rows.add("\n");				
		
		
		StringBuffer totalLiveBornBuffer = new StringBuffer();
		totalLiveBornBuffer.append("Total Live Born,");
		for (int i = 0; i < size; i++) {
			totalLiveBornBuffer.append(totalLiveBornList.get(i)+",");					
		}
		rows.add(totalLiveBornBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageLiveBornBuffer = new StringBuffer();
		averageLiveBornBuffer.append("Average Live Born,");
		for (int i = 0; i < size; i++) {
			averageLiveBornBuffer.append(averageLiveBornList.get(i)+",");					
		}
		rows.add(averageLiveBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer totalDeathBuffer = new StringBuffer();
		totalDeathBuffer.append("Total Death Born,");
		for (int i = 0; i < size; i++) {
			totalDeathBuffer.append(totalDeathList.get(i)+",");					
		}
		rows.add(totalDeathBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageDeathBornBuffer = new StringBuffer();
		averageDeathBornBuffer.append("Average Death Born,");
		for (int i = 0; i < size; i++) {
			averageDeathBornBuffer.append(averageDeadBornList.get(i)+",");					
		}
		rows.add(averageDeathBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer percentageDeathBornBuffer = new StringBuffer();
		percentageDeathBornBuffer.append("Percentage Death Born,");
		for (int i = 0; i < size; i++) {
			percentageDeathBornBuffer.append(percentageDeadBornList.get(i)+",");					
		}
		rows.add(percentageDeathBornBuffer.toString());
		rows.add("\n");				
		
		StringBuffer totalStillBornBuffer = new StringBuffer();
		totalStillBornBuffer.append("Total Still Born,");
		for (int i = 0; i < size; i++) {
			totalStillBornBuffer.append(totalStillBornList.get(i)+",");					
		}
		rows.add(totalStillBornBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageStillBornBuffer = new StringBuffer();
		averageStillBornBuffer.append("Average Still Born,");
		for (int i = 0; i < size; i++) {
			averageStillBornBuffer.append(averageStillBornList.get(i)+",");					
		}
		rows.add(averageStillBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer percentageStillBornBuffer = new StringBuffer();
		percentageStillBornBuffer.append("Percentage Still Born,");
		for (int i = 0; i < size; i++) {
			percentageStillBornBuffer.append(percentageStillBornList.get(i)+",");					
		}
		rows.add(percentageStillBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer totalMummiesBuffer = new StringBuffer();
		totalMummiesBuffer.append("Total Mummies Born,");
		for (int i = 0; i < size; i++) {
			totalMummiesBuffer.append(totalMummiesList.get(i)+",");					
		}
		rows.add(totalMummiesBuffer.toString());
		rows.add("\n");
		
		StringBuffer averageMummiesBornBuffer = new StringBuffer();
		averageMummiesBornBuffer.append("Average Mummies Born,");
		for (int i = 0; i < size; i++) {
			averageMummiesBornBuffer.append(averageMummiesList.get(i)+",");					
		}
		rows.add(averageMummiesBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer percentageMummiesBornBuffer = new StringBuffer();
		percentageMummiesBornBuffer.append("Percentage Mummies Born,");
		for (int i = 0; i < size; i++) {
			percentageMummiesBornBuffer.append(percentageMummiesList.get(i)+",");					
		}
		rows.add(percentageMummiesBornBuffer.toString());
		rows.add("\n");	
		
		StringBuffer litterWithAgeLessSevenBuffer = new StringBuffer();
		litterWithAgeLessSevenBuffer.append("Litter with age less 7,");
		for (int i = 0; i < size; i++) {
			litterWithAgeLessSevenBuffer.append(litterWithAgeLessSevenList.get(i)+",");					
		}
		rows.add(litterWithAgeLessSevenBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageLitterWithAgeLessSevenBuffer = new StringBuffer();
		percentageLitterWithAgeLessSevenBuffer.append("Percentage Litter with age less 7, ");
		for (int i = 0; i < size; i++) {
			percentageLitterWithAgeLessSevenBuffer.append(percentageLitterWithAgeLessSevenList.get(i)+",");					
		}
		rows.add(percentageLitterWithAgeLessSevenBuffer.toString());
		rows.add("\n");	
		
		StringBuffer totalBirthWeightBuffer = new StringBuffer();
		totalBirthWeightBuffer.append("Birth weight,");
		for (int i = 0; i < size; i++) {
			totalBirthWeightBuffer.append(totalBirthWeightList.get(i)+",");					
		}
		rows.add(totalBirthWeightBuffer.toString());
		rows.add("\n");
 
		StringBuffer averageBirthWeightBuffer = new StringBuffer();
		averageBirthWeightBuffer.append("Average Birth weight,");
		for (int i = 0; i < size; i++) {
			averageBirthWeightBuffer.append(averageBirthWeightList.get(i)+",");					
		}
		rows.add(averageBirthWeightBuffer.toString());
		rows.add("\n");
		
		rows.add("\n");
		rows.add("Litters with farrow & wean");
		rows.add("\n");
		
		StringBuffer ferrowCapacityBuffer = new StringBuffer();
		ferrowCapacityBuffer.append("Ferrow Capacity,");
		for (int i = 0; i < size; i++) {
			ferrowCapacityBuffer.append(ferrowCapacityList.get(i)+",");					
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
			littersWeanedBuffer.append(littersWeanedList.get(i)+",");					
		}
		rows.add(littersWeanedBuffer.toString());
		rows.add("\n");
		
		StringBuffer littersWeanedWithMoreThanTwalePigsBuffer = new StringBuffer();
		littersWeanedWithMoreThanTwalePigsBuffer.append("Litters weaning 12+,");
		for (int i = 0; i < size; i++) {
			littersWeanedWithMoreThanTwalePigsBuffer.append(littersWeanedWithMoreThanTwalePigsList.get(i)+",");					
		}
		rows.add(littersWeanedWithMoreThanTwalePigsBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageLittersWeanedWithMoreThanTwalePigsBuffer = new StringBuffer();
		percentageLittersWeanedWithMoreThanTwalePigsBuffer.append("% Litters weaning 12+,");
		for (int i = 0; i < size; i++) {
			percentageLittersWeanedWithMoreThanTwalePigsBuffer.append(percentageLittersWeanedWithMoreThanTwalePigsList.get(i)+",");					
		}
		rows.add(percentageLittersWeanedWithMoreThanTwalePigsBuffer.toString());
		rows.add("\n");
		
		StringBuffer sowsWeaningZeroPigBuffer = new StringBuffer();
		sowsWeaningZeroPigBuffer.append("Sows weaning 0 pig,");
		for (int i = 0; i < size; i++) {
			sowsWeaningZeroPigBuffer.append(sowsWeaningZeroPigList.get(i)+",");					
		}
		rows.add(sowsWeaningZeroPigBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentageSowsWeaningZeroPigBuffer = new StringBuffer();
		percentageSowsWeaningZeroPigBuffer.append("% Sows weaning 0 pigs,");
		for (int i = 0; i < size; i++) {
			percentageSowsWeaningZeroPigBuffer.append(percentageSowsWeaningZeroPigList.get(i)+",");					
		}
		rows.add(percentageSowsWeaningZeroPigBuffer.toString());
		rows.add("\n");
		
		StringBuffer pigsWeaningDivideLitterWeanedBuffer = new StringBuffer();
		pigsWeaningDivideLitterWeanedBuffer.append("Pigs weaned/litter weaned,");
		for (int i = 0; i < size; i++) {
			pigsWeaningDivideLitterWeanedBuffer.append(pigsWeaningDivideLitterWeanedList.get(i)+",");					
		}
		rows.add(pigsWeaningDivideLitterWeanedBuffer.toString());
		rows.add("\n");
		
		StringBuffer totalPigsWeavnedBuffer = new StringBuffer();
		totalPigsWeavnedBuffer.append("Total pigs weaned,");
		for (int i = 0; i < size; i++) {
			totalPigsWeavnedBuffer.append(totalPigsWeavnedList.get(i)+",");					
		}
		rows.add(totalPigsWeavnedBuffer.toString());
		rows.add("\n");
		
		
		StringBuffer pigsWeaningDivideSowWeanedBuffer = new StringBuffer();
		pigsWeaningDivideSowWeanedBuffer.append("Pigs weaned/sow weaned,");
		for (int i = 0; i < size; i++) {
			pigsWeaningDivideSowWeanedBuffer.append(pigsWeaningDivideSowWeanedList.get(i)+",");					
		}
		rows.add(pigsWeaningDivideSowWeanedBuffer.toString());
		rows.add("\n");
		
		StringBuffer percentagePingsWeanedDivideByTotalPigsBuffer = new StringBuffer();
		percentagePingsWeanedDivideByTotalPigsBuffer.append("Pigs weaned/totalborn(%),");
		for (int i = 0; i < size; i++) {
			percentagePingsWeanedDivideByTotalPigsBuffer.append(percentagePingsWeanedDivideByTotalPigsList.get(i)+",");					
		}
		rows.add(percentagePingsWeanedDivideByTotalPigsBuffer.toString());
		rows.add("\n");
		
		StringBuffer netFosterBuffer = new StringBuffer();
		netFosterBuffer.append("Net foster,");
		for (int i = 0; i < size; i++) {
			netFosterBuffer.append(netFosterList.get(i)+",");					
		}
		rows.add(netFosterBuffer.toString());
		rows.add("\n");
		//Net foster


		return rows;
	}
	
	@RequestMapping(value = "/reportGeneration", method = RequestMethod.GET)
	public ModelAndView reportGeneration(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "reportGeneration.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		return new ModelAndView("template", model);
	}

}
