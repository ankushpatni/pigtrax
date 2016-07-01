package com.pigtrax.master.service.interfaces;

import java.sql.Date;
import java.util.Map;

public interface ReportService {
	
	int getActivedPenCount(int companyId);

	Map<Date, Map> getFerrowEventReport(String startDate, String endDate,
			Integer companyId, Integer premisesId, Integer numberOfWeeks);

}
