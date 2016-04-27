package com.pigtrax.master.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

@RestController
public class ReportDownloadController {

	@Autowired
	private HttpBatchPost httpBatchPost;

	@Autowired
	private Environment env;

	@RequestMapping(value = "/downloadReport", method = RequestMethod.GET)
	public String uploadFileHandler(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") String fileName) {
	
		try{
			String filePath = env.getProperty("upload.file.path") + File.separator;
		    String fullFilename = filePath + "/" + fileName;
	    	response.setContentType("text/plain");
			response.setHeader("Content-disposition", "attachment;filename="+fileName);
			BufferedReader  fileReader = new BufferedReader(new FileReader(fullFilename));
			ServletOutputStream out = response.getOutputStream();
			String newLine = "\r\n";
			
			String line = "";
			while((line = fileReader.readLine()) != null)
			{
				out.write(line.getBytes(), 0, line.getBytes().length);
				out.write(newLine.getBytes(), 0, newLine.getBytes().length);
			}
	
			fileReader.close();
			out.flush();
			out.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
        return null;
	}
	
	
	private enum ValidTemplates {
        EntryEvent,
        BreedingEvent,
        MatingDetails,
        PregnancyEvent,
        FarrowEvent,
        PigletStatusEvent,
        GroupEvent,
        GroupEventDetails,
        FeedEvent,
        FeedEventDetails,
        IndividualPigletEvent,
        RemovalEventExceptSalesEventGroup,
        RemovalEventExceptSalesEventPig,
        SalesEventGroup,
        SalesEventPig
    }
	
	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET)
	public String downloadTemplate(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("type") ValidTemplates templateType,@RequestParam("fileType") String fileType) {
	
		try{
			
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			
			String filePath = env.getProperty("upload.template.path") + File.separator;
		    String fullFilename = filePath  +language+ File.separator+templateType+"."+fileType;
		     
	    	if(templateType.toString().equalsIgnoreCase("RemovalEventExceptSalesEventGroup"))
	    		response.setHeader("Content-disposition", "attachment;filename=Mortality&Adjustment-Group."+fileType);
	    	else if(templateType.toString().equalsIgnoreCase("RemovalEventExceptSalesEventPig"))
	    		response.setHeader("Content-disposition", "attachment;filename=Mortality&Adjustment-Pig."+fileType);
	    	else
	    		response.setHeader("Content-disposition", "attachment;filename="+templateType+"."+fileType);
			ServletOutputStream out = response.getOutputStream();
			String newLine = "\r\n";
			
			 if(fileType.equalsIgnoreCase("CSV"))
			 {
			    	response.setContentType("text/plain");
			    	BufferedReader  fileReader = new BufferedReader(new FileReader(fullFilename));
			    	String line = "";
					while((line = fileReader.readLine()) != null)
					{
						out.write(line.getBytes(), 0, line.getBytes().length);
						out.write(newLine.getBytes(), 0, newLine.getBytes().length);
					}
					fileReader.close();
					
			 }
		    if(fileType.equalsIgnoreCase("xlsx"))
		    {
		    	response.setContentType("application/vnd.ms-excel");	
		    	FileInputStream in = new FileInputStream(new File(fullFilename));

		    	byte[] buffer= new byte[8192]; // use bigger if you want
		    	int length = 0;

		    	while ((length = in.read(buffer)) > 0){
		    	     out.write(buffer, 0, length);
		    	}
		    	in.close();
		    }
			
			
	
	
			out.flush();
			out.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
        return null;
	}

	
}
