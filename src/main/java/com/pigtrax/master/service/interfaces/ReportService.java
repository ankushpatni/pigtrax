package com.pigtrax.master.service.interfaces;

import java.util.Map;

public interface ReportService {
	
	public Map<String,String> getFerrowEventReport(String startDate, String enddate);

}
