package com.pigtrax.notification;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BlockingQueueEmail {
	
	private String emailId;
	private String password;
	private List<String> bCC;
	/*private List<String> cC;*/
	private String name;
	private String message;
	private String companyId;
	
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getbCC() {
		return bCC;
	}
	public void setbCC(List<String> bCC) {
		this.bCC = bCC;
	}
	
	public void populateEmployeeCreationMessage()
	{
		this.message = "Employee ["+this.name+"] added in the company "+this.companyId;
	}
	
}
