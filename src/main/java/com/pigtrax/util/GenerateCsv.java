package com.pigtrax.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class GenerateCsv {

	 static String penInfo = "";
	 static String roomInfo = "";
	 static String rationId = "";
	 static String transportTruck = "";
	 static String transportTrailer = "";
	 static String silo = "";
	 static String removalEvent = "";
	 static String mortalityReason = "";
	 static String toPremise = "";
	 static String toRoom = "";
	 
	 
	public static void main(String [] args)
	   {
		  System.out.println("CSV Generation Instructions");
		  System.out.println("#################################");
		  System.out.println("");
		  System.out.println("STEP1 : Select Event");
		  System.out.println("		1 : Entry Event");
		  System.out.println("		2 : Breeding Event");
		  System.out.println("		3 : Mating Details");
		  System.out.println("		4 : Pregnancy Event");
		  System.out.println("		5 : Farrow Event");
		  System.out.println("		6 : Piglet Status Event");
		  System.out.println("		7 : Group Event");
		  System.out.println("		8 : Group Event Details");
		  System.out.println("		9 : Feed Event");
		  System.out.println("		10 : Feed Event Details");
		  System.out.println("		11 : Individual Piglet");
		  System.out.println("		12 : Removal & Transfer - Group");
		  Scanner scanIn = new Scanner(System.in);
	      String event = scanIn.nextLine();
	      System.out.println("Number of Records to be created : ");	      
	      String numRecords = scanIn.nextLine();
	      
	     
	      
	      System.out.println("Location where CSV file to be created : ");	      
	      String fileLocation = scanIn.nextLine();
	      
	      if(event != null && "5".equalsIgnoreCase(event))
	      {
	    	  System.out.println("Specify the Pen location to be populated in the CSV ");	      
		      penInfo = scanIn.nextLine();
	      }
	      else  if(event != null && "7".equalsIgnoreCase(event))
	      {
	    	  System.out.println("Specify the Room location to be populated in the CSV (Provide multiple room Ids | seperated)");	      
	    	  roomInfo = scanIn.nextLine();
	      }
	      else  if(event != null && "9".equalsIgnoreCase(event))
	      {
	    	  System.out.println("Specify the Ration Id");	      
	    	  rationId = scanIn.nextLine();
	    	  
	    	  System.out.println("Specify the Transport Truck");	      
	    	  transportTruck = scanIn.nextLine();
	    	  
	    	  System.out.println("Specify the Transport Trailer");	      
	    	  transportTrailer = scanIn.nextLine();
	      }
	      else  if(event != null && "10".equalsIgnoreCase(event))
	      {
	    	  System.out.println("Specify silo");	      
	    	  silo = scanIn.nextLine();
	      }
	      else  if(event != null && "12".equalsIgnoreCase(event))
	      {
	    	  System.out.println("Specify removal event");	      
	    	  removalEvent = scanIn.nextLine();
	    	  if(removalEvent != null && !("Transferred".equalsIgnoreCase(removalEvent.trim())))
	    	  {
	    		  System.out.println("Specify mortality reason");	      
		    	  mortalityReason = scanIn.nextLine();
	    	  }
	    	  else if("Transferred".equalsIgnoreCase(removalEvent.trim()))
	    	  {
	    		  System.out.println("Specify the destination premise");	      
		    	  toPremise = scanIn.nextLine();
		    	  
		    	  System.out.println("Specify the destination room");	      
		    	  toRoom = scanIn.nextLine();
	    	  }
	    	  
	    	  System.out.println("Specify the Transport Truck");	      
	    	  transportTruck = scanIn.nextLine();
	    	  
	    	  System.out.println("Specify the Transport Trailer");	      
	    	  transportTrailer = scanIn.nextLine();
	      }
	      
	      
	      int recordNum = 0;
	      try{
	    	  recordNum = Integer.parseInt(numRecords);
	      }catch(NumberFormatException nfEx)
	      {
	    	  recordNum = 0;
	      }
	      if(recordNum > 0 && event != null && event.trim().length() > 0 && fileLocation!= null && fileLocation.trim().length() > 0)
	      {
		      try{
		    	  generateCsvFile(recordNum, event, fileLocation+File.separator+event+".csv");
		      }
		      catch(Exception ex)
		      {
		    	  ex.printStackTrace();
		      }
	      }
	      else
	      {
	    	  System.out.println("Event, Number of Records and File location information are mandatory");
	      }
		  
		  
	   }
	   
	   private static void generateCsvFile(Integer recordNum, String event, String sFileName) throws Exception
	   {
			try
			{
			    FileWriter writer = new FileWriter(sFileName);
	
			    //Add Header
			    addHeader(writer, event);
			    
			    for(int i =0; i< recordNum; i++)
			    {			    	
			    	addEntry(event, i, writer, penInfo, roomInfo);
			    }	
			    writer.flush();
			    writer.close();
			    System.out.println("CSV generated");
			}
			catch(IOException e)
			{
			     e.printStackTrace();
			} 
	    }
	
	   
	   public static void addEntry(String event, int i, FileWriter writer, String penInfo, String roomInfo) throws Exception
	   {
		   if("1".equalsIgnoreCase(event.trim()))
		   {
			   String entryDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365-(i+1)), "MM/dd/yyyy");
		    	
		    	StringBuffer headerString = new StringBuffer();
		    	headerString.append(",");
		    	headerString.append("MPG0"+(i+1)+",");
		    	headerString.append("MPG0"+(i+1)+",");
		    	headerString.append("MPG0"+(i+1)+",");
		    	headerString.append(entryDate+",");
		    	headerString.append("Female,");
		    	headerString.append(",");
		    	headerString.append("choice,");
		    	headerString.append("10,");
		    	headerString.append("GGP,");
		    	headerString.append(",");
		    	headerString.append("sireval,");
		    	headerString.append("dam val,");
		    	headerString.append("Remarks");
		    	writer.append(headerString);
		    	writer.append('\n');
		   } 
		   else if("2".equalsIgnoreCase(event.trim()))
		   {
			   String entryDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365-(i+1)), "MM/dd/yyyy");
		    	
		    	StringBuffer headerString = new StringBuffer();		    	
		    	headerString.append("MPG0"+(i+1)+",");
		    	headerString.append("AI,");
		    	headerString.append(",");		    	
		    	headerString.append("5,");
		    	headerString.append("24.5");
		    	writer.append(headerString);
		    	writer.append('\n');
		   } 
		   else if("3".equalsIgnoreCase(event.trim()))
		   {
			   for(int j=0; j<3; j++)
			   {
				String matingDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365+(i+1+j)), "MM/dd/yyyy");
		    	
		    	StringBuffer headerString = new StringBuffer();		    	
		    	headerString.append("MPG0"+(i+1)+",");
		    	headerString.append(matingDate+",");
		    	headerString.append(",");		    	
		    	headerString.append("semen"+(i+1)+",");
		    	headerString.append(matingDate+",");
		    	headerString.append(j==0?"Good":((j==1)?"OK":"Poor"));
		    	writer.append(headerString);
		    	writer.append('\n');
			   }
		   } 
		   else if("4".equalsIgnoreCase(event.trim()))
		   {
			   
				String resultDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365+(i+1+20)), "MM/dd/yyyy");
		    	
		    	StringBuffer headerString = new StringBuffer();		    	
		    	headerString.append("MPG0"+(i+1)+",");
		    	headerString.append(resultDate+",");
		    	headerString.append("Pregnancy Exam,");		    	
		    	headerString.append("positive,");
		    	headerString.append(",");
		    	headerString.append("4");
		    	writer.append(headerString);
		    	writer.append('\n');
			   
		   } 
		   else if("5".equalsIgnoreCase(event.trim()))
		   {
			   
				String farrowDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365+(i+1+108)), "MM/dd/yyyy");
		    	
		    	StringBuffer headerString = new StringBuffer();	
		    	headerString.append(penInfo+",");
		    	headerString.append("MPG0"+(i+1)+",");
		    	headerString.append(farrowDate+",");
		    	headerString.append((i+40)+",");	
		    	headerString.append((i)+",");
		    	headerString.append((i+1)+",");
		    	headerString.append((i+2)+",");
		    	headerString.append((i+3)+",");
		    	headerString.append((i+4)+",");
		    	headerString.append("23.5,");
		    	headerString.append((i%2==0)?"induced,":"assisted,");
		    	headerString.append(",");
		    	headerString.append("4,");
		    	headerString.append("");
		    	writer.append(headerString);
		    	writer.append('\n');
			   
		   } 
		   
		   else if("6".equalsIgnoreCase(event.trim()))
		   {
			   
				
		    	for(int j = 0; j<3; j++)
		    	{	
		    		String weanDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365+(i+1+128+j)), "MM/dd/yyyy");
			    	StringBuffer headerString = new StringBuffer();	
			    	headerString.append("MPG0"+(i+1)+",");
			    	headerString.append((j==0)?"wean,":((j==1)?"Transfer,":"Piglet Mortality,"));
			    	headerString.append((i+3)+",");	
			    	headerString.append("203.45,");
			    	headerString.append(weanDate+",");
			    	headerString.append((j==0)?("MGROUP00"+(i+2)+","):",");
			    	headerString.append((j==1)?("MPG0"+(i+j+1)+","):",");
			    	headerString.append((j==2)?("deformed,"):",");
			    	headerString.append(",");
			    	headerString.append(",");
			    	headerString.append((j+1));
			    	writer.append(headerString);
			    	writer.append('\n');
		    	}
			   
		   } 
		   else if("7".equalsIgnoreCase(event.trim()))
		   {   
		    		String eventStartDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365-(i+1)), "MM/dd/yyyy");
			    	StringBuffer headerString = new StringBuffer();	
			    	headerString.append(roomInfo+",");
			    	headerString.append("MGROUP00"+(i+1)+",");
			    	headerString.append(eventStartDate+",");	
			    	headerString.append((i%10 ==0)?"Nursery,":((i%5==0 || i%3==0)?"Wean to Finish,":"GDU,"));
			    	headerString.append(",");
			    	headerString.append("Group event created,");
			    	headerString.append(",");
			    	writer.append(headerString);
			    	writer.append('\n');
			   
		   } 
		   else if("8".equalsIgnoreCase(event.trim()))
		   {   
		    		String entryDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365-(i-2)), "MM/dd/yyyy");
			    	StringBuffer headerString = new StringBuffer();	
			    	headerString.append("MGROUP00"+(i+1)+",");
			    	headerString.append("T123PRE1,");
			    	headerString.append(entryDate+",");
			    	headerString.append((i+5)+",");
			    	headerString.append(((i+1)*12.5)+",");
			    	headerString.append("");
			    	writer.append(headerString);
			    	writer.append('\n');
			   
		   }
		   else if("9".equalsIgnoreCase(event.trim()))
		   {   
		    		String entryDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365-(i-2)), "MM/dd/yyyy");
			    	StringBuffer headerString = new StringBuffer();	
			    	headerString.append("T00"+(i+1)+",");
			    	headerString.append(rationId+",");
			    	headerString.append("Feed Medication"+(i+1)+",");
			    	headerString.append(transportTruck+",");
			    	headerString.append(transportTrailer);
			    	writer.append(headerString);
			    	writer.append('\n');
		   }
		   else if("10".equalsIgnoreCase(event.trim()))
		   {   
			   //Ticket Number(*),Group Id(*),Feed Event Date(*),Feed Mill,Feed Event Type(*),Weight (Kg),Feed Cost,Silo (*),Remarks
			   for(int j = 0; j<3; j++)
		    	{	
		    		String feedEventDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365-(i-1)), "MM/dd/yyyy");
			    	StringBuffer headerString = new StringBuffer();	
			    	headerString.append("T00"+(i+1)+",");
			    	headerString.append("MGROUP00"+(i+1)+",");
			    	headerString.append(feedEventDate+",");
			    	headerString.append("Feed Mill"+(i+1)+",");
			    	headerString.append((j==0)?"Feed In,":((j==1)?"Feed Out,":"Feed Invenadj,"));
			    	headerString.append(((i+1+j)*12.1)+",");
			    	headerString.append(((i+1+j)*2)+",");
			    	headerString.append(silo+",");
			    	headerString.append("remarks"+(i+1)+",");
			    	writer.append(headerString);
			    	writer.append('\n');
		    	}
		   }
		   else if("11".equalsIgnoreCase(event.trim()))
		   {   		    		
		    	StringBuffer headerString = new StringBuffer();	
		    	headerString.append("MPG0"+(i+1)+",");
		    	headerString.append((i%2==0)?(i*3)+",":",");
		    	headerString.append(",");
		    	headerString.append(((i+1)*2.3)+",");
		    	headerString.append(((i+2)*2.3)+",");
		    	headerString.append(((i+3)*2.3)+",");
		    	headerString.append(((i+4)*2.3)+",");
		    	headerString.append(((i+5)*2.3)+",");
		    	headerString.append(((i+6)*2.3)+",");
		    	headerString.append(((i+7)*2.3)+",");
		    	headerString.append(((i+8)*2.3));
		    	writer.append(headerString);
		    	writer.append('\n');
		   }
		   else if("12".equalsIgnoreCase(event.trim()))
		   {   		    	
			   String removalDate = DateUtil.convertToFormatString(DateUtil.addDays(new Date(),-365+i), "MM/dd/yyyy");
		    	StringBuffer headerString = new StringBuffer();	
		    	headerString.append("MGROUP00"+(i+1)+",");
		    	headerString.append(",");
		    	headerString.append((i+1)+",");
		    	headerString.append(removalDate+",");
		    	headerString.append(((i+2)*2.3)+",");
		    	headerString.append(removalEvent+",");
		    	headerString.append(",");
		    	headerString.append(",");
		    	headerString.append("Remarks ,");
		    	headerString.append(mortalityReason+",");
		    	headerString.append(((i+8)*2.3)+",");
		    	headerString.append(transportTruck+",");
		    	headerString.append(transportTrailer);
		    	writer.append(headerString);
		    	writer.append('\n');
		   }
		   
	   }
	   
	   public static void addHeader(FileWriter writer, String event) throws Exception
	   {
		   StringBuffer headerString = new StringBuffer();
		   if(event == null)
			   throw new Exception("Invalid event");
		   else
		   {
			   if("1".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Room Id,Pig Id(*),Tattoo,Alternate Tattoo,Entry Date(*) in mm/dd/yyyy,Sex (*),Genetic Origin,Genetic Company(*),Genetic Line(*),Genetic Function(*),Birth Date,Sire,Dam,Remarks");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("2".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Pig Id(*),Service Type,Service Group Id,Sow Condition,Weight (Kg)");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("3".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Pig Id(*),Mating Date(*),Employee Group,Semen Id,Semen Date,Mate Quality");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("4".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Pig Id(*),Result Date(*),Pregnancy Event Type(*),Pregnancy Exam Result Type,Employee Group,Sow Condition");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("5".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Pen (*),Pig Id(*),Farrow Date(*),Live Borns,Still Borns,Mummies,Weak Borns,Male Borns,Female Borns,Weight (Kg),Type of Birth,Employee Group,Sow Condition,Teats");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("6".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Pig Id(*),Event Type(*),Number of Pigs(*),Weight (Kg),Event Date(*),Wean Group Id,Transferred to Pig,Mortality Reason,Pen,Remarks,Sow Condition");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("7".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Rooms(*)(Give rooms | separated),Group Id(*),Group Start Date(*),Phase of Production(*),Group Close Date,Remarks,Current Inventory");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("8".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Group Id (*),Sow Source(*),Date of Entry (*),Number of Pigs (*),Weight (Kg) (*),Employee Group");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("9".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Ticket Number(*),Ration Id(*),Feed Medication,Transport Truck,Transport Trailer");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("10".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Ticket Number(*),Group Id(*),Feed Event Date(*),Feed Mill,Feed Event Type(*),Weight (Kg),Feed Cost,Silo (*),Remarks");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("11".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Pig Id(*),Litter Id,Tattoo Id,Weight at Birth,Weight at Weaning,Weight at 1st Month,Weight At 2nd Month,Weight at 3rd Month,Weight at 4th Month,Weight at 5th Month,Weight at 6th Month");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   else if("12".equalsIgnoreCase(event.trim()))
			   {
				   headerString.append("Group Event,Pig Info Id,Number Of Pigs(*),Removal DateTime(*),WeightInKgs(*),Removal EventTypeId(*),To PremiseId (In Transfer *),RoomId (In Transfer *),Remarks,Mortality ReasonId,Revenue,Transport Truck,Transport Trailer");
				   writer.append(headerString);
				   writer.append('\n');
			   }
			   
		   }
			   
	   }
}
