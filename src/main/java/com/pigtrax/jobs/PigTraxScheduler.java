package com.pigtrax.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mySchedulerBean")
public class PigTraxScheduler {
	
	@Autowired
	GroupStatusReportProcessor groupStatusProcessor;
	
	
	@Autowired
	GroupPerformanceReportProcessor groupPerformanceProcessor;
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
	
	
	
	/**
	 * Backend jobs 
	 */
	public void cleanUpGroupStatusReport() {
		try{
		groupStatusProcessor.cleanup();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Backend jobs 
	 */
	public void processPerformanceReport() {
		try{
			groupPerformanceProcessor.process();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
