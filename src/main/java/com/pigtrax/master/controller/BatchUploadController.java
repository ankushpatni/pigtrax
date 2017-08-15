package com.pigtrax.master.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pigtrax.util.UserUtil;

@RestController
public class BatchUploadController {

	@Autowired
	private HttpBatchPost httpBatchPost;

	@Autowired
	private Environment env;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				String eventType = request.getParameter("eventType");
				String header = request.getParameter("header");
				if (header == null)
					header = "false";
				String companyId = request.getParameter("companyId");
				String premiseId = request.getParameter("premiseId");
				System.out.println("eventType = " + eventType);
				byte[] bytes = file.getBytes();
				String fileName = String.valueOf(new Random().nextInt(99999)) + "_" + file.getOriginalFilename();
				System.out.println(fileName);
				String path = env.getProperty("upload.file.path") + File.separator + fileName;
				File serverFile = new File(path);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				httpBatchPost.execute(eventType, header, UserUtil.getLoggedInUser(), path, companyId, premiseId);
				
				HttpSession session = request.getSession(true);
				String reportFileName = fileName.toLowerCase().replaceAll(".csv", "_report.txt");
				session.setAttribute("REPORT_FILE", reportFileName);
				session.setAttribute("eventType", eventType);
				session.setAttribute("header", header);
				
				
				String tempPath = (null == env.getProperty("upload.file.path")) ? "." : env.getProperty("upload.file.path");
			    String fullFilename = tempPath + "/" + reportFileName;
		    	response.setContentType("text/plain");
				response.setHeader("Content-disposition", "attachment;filename="+reportFileName);
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
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:" + "massupload?token=success");
	}

	@RequestMapping(value = "/massupload", method = RequestMethod.GET)
	public ModelAndView massUpload(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("contentUrl", "massUpload.jsp");
		model.put("token", request.getParameter("token") != null ? request.getParameter("token") : "");
		HttpSession session = request.getSession(true);
		
		if(request.getParameter("token") == null)
		{
		session.removeAttribute("REPORT_FILE");
		session.removeAttribute("eventType");
		session.removeAttribute("header");
		}
		return new ModelAndView("template", model);
	}
}
