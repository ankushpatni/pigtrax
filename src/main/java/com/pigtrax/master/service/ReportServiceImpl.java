package com.pigtrax.master.service;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pigtrax.master.service.interfaces.ReportService;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;

@Repository
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	private static final String DEFAULT_FORMATTER = "yyyy-MM-dd";
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Map<String, String> getFerrowEventReport(String startDate,
			String endDate) {
		
		Date startDateD;
		Date endDateD;
		try {
			startDateD = new Date( sdf.parse(startDate).getTime());

			endDateD = new Date( sdf.parse(endDate).getTime());
			
			eventMasterDao.selectFerrowEvents(startDateD, endDateD);
		} catch (ParseException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}		
		
		return null;
	}

}
