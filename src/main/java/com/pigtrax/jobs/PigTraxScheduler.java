package com.pigtrax.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mySchedulerBean")
public class PigTraxScheduler {
	
	@Autowired
	GroupStatusReportProcessor groupStatusProcessor;
	/**
	 * Backend jobs 
	 */
	public void processGroupStatusReport() {
		try{
		groupStatusProcessor.process();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
