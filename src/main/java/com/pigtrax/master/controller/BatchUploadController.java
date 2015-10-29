package com.pigtrax.master.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

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
	public ModelAndView uploadFileHandler(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				String eventType = request.getParameter("eventType");
				String header = request.getParameter("header");
				if(header == null)
					header = "false";
				System.out.println("eventType = "+eventType);
				byte[] bytes = file.getBytes();
				String fileName = String.valueOf(new Random().nextInt(99999)) + "_" + file.getOriginalFilename();
				System.out.println(fileName);
				String path = env.getProperty("upload.file.path") + File.separator + fileName;
				File serverFile = new File(path);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();				
				httpBatchPost.execute(eventType, header, UserUtil.getLoggedInUser(), path);				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:" + "pigEntryEvent");
	}
}
