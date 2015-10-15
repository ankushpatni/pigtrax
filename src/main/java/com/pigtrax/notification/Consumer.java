package com.pigtrax.notification;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer implements Runnable{

	private static final Logger logger = Logger.getLogger(Consumer.class);
	
    private  LinkedBlockingQueue<BlockingQueueEmail> sharedQueue;  

    @Autowired
    Mailer mailer;

    public LinkedBlockingQueue<BlockingQueueEmail> getSharedQueue() {
		return sharedQueue;
	}


    @Autowired
	public void setSharedQueue(LinkedBlockingQueue<BlockingQueueEmail> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}



	@Override
    public void run() {
        while(true){
            try {
            	
                BlockingQueueEmail emailObj= sharedQueue.take();
                
                logger.info("mailer = "+mailer);
                
                mailer.setToAddress(emailObj.getEmailId());
                mailer.setBccList(emailObj.getbCC());
                mailer.setSubject(emailObj.getSubjectLine());
                mailer.setMessage(emailObj.getMessage());
                mailer.sendEmail();
                
            } catch (InterruptedException ex) {
            	ex.printStackTrace();            	
            }
        }
    }
  
  
}


