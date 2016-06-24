package com.pigtrax.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:PigTraxValidation.properties")
public class Mailer {
   	 

	private static Logger logger = Logger.getLogger(Mailer.class);
    // Sender's email ID needs to be mentioned	
	String from;
    String host = "localhost";	
	Session session = null;
    
    private String toAddress = "vidyar2002@yahoo.com";
    private List<String> bccList;
    private String subject;
    private String message;
	
    @Autowired
	private Environment env;
    
    
	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getToAddress() {
		return toAddress;
	}


	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}


	public List<String> getBccList() {
		return bccList;
	}


	public void setBccList(List<String> bccList) {
		this.bccList = bccList;
	}


	
	
	
	public String getFrom() {
		this.from = env.getProperty("email.from.address");		
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	void loadProperties()
	{
		// Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      session = Session.getDefaultInstance(properties);

	}
	
	
	public void sendEmail()
	{
		loadProperties();
		
		try{
	         // Create a default MimeMessage object.
	         MimeMessage emailMessage = new MimeMessage(session);

	         // Set From: header field of the header.
	         emailMessage.setFrom(new InternetAddress(this.getFrom()));
	         
	         emailMessage.setSubject(getSubject());

	         // Set To: header field of the header.
	         emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
	         
	         if(bccList != null && 0<bccList.size())
	         {
	        	 for(String addr : bccList)
	        	 {
	        		 emailMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(addr));
	        	 }
	         }	  
	         emailMessage.setContent(this.message,"text/html");	         
	         // Send message
	         Transport.send(emailMessage);
	         logger.info("Sent message successfully...."); 
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}	
		
}
