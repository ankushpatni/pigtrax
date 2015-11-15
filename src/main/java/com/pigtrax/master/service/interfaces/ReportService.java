package com.pigtrax.master.service.interfaces;

import java.sql.Date;
import java.util.Map;

public interface ReportService {
	
	public Map<Date, Map> getFerrowEventReport(String startDate, String enddate,Integer companyId);

	int getActivedPenCount(int companyId);

}
