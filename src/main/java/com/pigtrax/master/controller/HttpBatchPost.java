package com.pigtrax.master.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pigtrax.usermanagement.beans.PigTraxUser;

@Component
public class HttpBatchPost {

	@Autowired
	private Environment env;

	public void execute(final String eventType, String header, final String userName, final String filePath) {
		doPost(eventType, header, ",", "csv", userName, filePath);
	}

	private void doPost(final String eventType, final String header, final String seperator, final String fileType, final String userName, final String filePath) {
		try {
			
			PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(env.getProperty("batch.process.url"));
			List<BasicNameValuePair> urlParameters = new ArrayList<BasicNameValuePair>();
			urlParameters.add(new BasicNameValuePair("eventType", eventType));
			urlParameters.add(new BasicNameValuePair("header", header));
			urlParameters.add(new BasicNameValuePair("seperator", seperator));
			urlParameters.add(new BasicNameValuePair("fileType", fileType));
			urlParameters.add(new BasicNameValuePair("userName", activeUser.getUsername()));
			urlParameters.add(new BasicNameValuePair("data", filePath));
			postRequest.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse response = httpClient.execute(postRequest);
			System.out.println("Response code " + response.getStatusLine().getStatusCode());
			httpClient.getConnectionManager().shutdown();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}