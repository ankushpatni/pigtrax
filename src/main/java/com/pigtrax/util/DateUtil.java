package com.pigtrax.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class DateUtil {

	private static final Date NO_EXPIRE = new Date(16725218400000l);

	public static Date getEndOfTime() {
		return NO_EXPIRE;
		// return TODAY;
	}

	public static Date getToday() {
		return new Date();
	}

	public static Date addDays(Date dt,int days){
		Calendar cal  = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}	
	
	public static long calculateDays(Date futureDate){
		long days = 0;
		try {
			Date today = convertToFormat(getToday(),"yyyy-MM-dd");
			days = futureDate.getTime() - today.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS);
	}
	
	public static Date convertToFormat(Date dateVal, String pattern) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String dateStr = formatter.format(dateVal);
		return formatter.parse(dateStr);
	}
	
	
	public static String convertToFormatString(Date dateVal, String pattern) throws ParseException
	{
		if(dateVal != null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			String dateStr = formatter.format(dateVal);
			return dateStr;
		}
		return null;
	}
	
	
	public static Date convertToFormat(String dateVal, String pattern)
			{
		System.out.println("date val to parse == "+dateVal);
		if (dateVal == null)
			return null;
		else {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			Date convertedDate = null;
			try{
				convertedDate = formatter.parse(dateVal);
			}catch(ParseException pEx)
			{
				convertedDate = null;
			}
			return convertedDate;
		}
	}
	
	
	public static Date parseDateToYYYYMMDD(Date dateObj) throws ParseException
	{
		if(dateObj == null)
			return null;
		SimpleDateFormat inputformatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = inputformatter.format(dateObj);
		Date convertedDate = inputformatter.parse(dateStr);
		return convertedDate;
	}
	
	
	public static Map<String, String> getMonthMap()
	{
		Map<String, String> monthMap = new HashMap<String, String>();
		monthMap.put("Jan","01");
    	monthMap.put("Feb","02");
    	monthMap.put("Mar","03");
    	monthMap.put("Apr","04");    	
    	monthMap.put("May","05");
    	monthMap.put("Jun","06");
    	monthMap.put("Jul","07");
    	monthMap.put("Aug","08");
    	monthMap.put("Sep","09");
    	monthMap.put("Oct","10");
    	monthMap.put("Nov","11");
    	monthMap.put("Dec","12");
    	return monthMap;
	}
	
	 public  static String getTimePart(Date dateVal)
	 {
		 String retVal = "";
		 DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
		 if(dateVal != null)
		 {
			 retVal=dateFormat.format(dateVal);
		 }
		 return retVal;
	 }
	
	
	public static void main(String[] args) {
		String hashed = BCrypt.hashpw("Textbookvalet#123", BCrypt.gensalt());
		System.out.println("hashed : "+hashed);
		//$10$CTS7fdvFGYq55db3fWDJ5u7U1hLcupkA42tc4Qx5kRnl9q09reJcK
	}
	
	
	
}
