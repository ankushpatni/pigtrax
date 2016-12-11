package com.pigtrax.jobs.dao.interfaces;

import com.pigtrax.jobs.dto.GroupPerformanceReportDataDto;
import com.pigtrax.jobs.dto.GroupStatusReportDataDto;

public interface GroupStatusReportDataDao {
   int delete(Integer groupEventId);
   int delete(Integer groupEventId, String type);
   void insert(GroupStatusReportDataDto data);
   void insert(GroupPerformanceReportDataDto data);
   void cleanUpOldData();
}
