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

import org.springframework.stereotype.Component;

@Component
public class Mailer {
   	 

    // Sender's email ID needs to be mentioned
    String from = "vidyar2002@gmail.com";
    String host = "localhost";	
	Session session = null;
    
    private String toAddress = "vidyar2002@yahoo.com";
    private List<String> bccList;
	
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


	void loadProperties()
	{
		// Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      session = Session.getDefaultInstance(properties);

	}
	
	
	void sendEmail()
	{
		loadProperties();
		
		try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
	         
	         if(bccList != null && 0<bccList.size())
	         {
	        	 for(String addr : bccList)
	        	 {
	        		 message.addRecipient(Message.RecipientType.BCC, new InternetAddress(addr));
	        	 }
	         }

	         // Set Subject: header field
	         message.setSubject("This is the Subject Line!");

	         // Send the actual HTML message, as big as you like
	         message.setContent("<h1>This is actual message</h1>", "text/html" );

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}	
		
}
