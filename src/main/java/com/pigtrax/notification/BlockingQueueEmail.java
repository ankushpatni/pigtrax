package com.pigtrax.notification;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
	private String subjectLine;
	private Locale locale;
	private String hashPassword;
	private String employeeId;
	
	
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getSubjectLine() {
		return subjectLine;
	}
	public void setSubjectLine(String subjectLine) {
		this.subjectLine = subjectLine;
	}
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
	
	
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getHashPassword() {
		return hashPassword;
	}
	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}
	public void populateEmployeeCreationMessage(MessageSource messageSource)
	{
		Object[] params = new Object[1];
		params[0] = this.name;
		
		this.subjectLine = messageSource.getMessage("label.employee.employeecreation.email.subject", null, "", locale);
		this.message= messageSource.getMessage("label.employee.employeecreation.email.content", params, "", locale);
		
	}
	
	public void populateForgotPasswordMessage(MessageSource messageSource)
	{
		Object[] params = new Object[4];
		params[0] = this.name;
		params[1] = this.hashPassword;
		params[2] = this.employeeId;
		params[3] = this.password;
		
		this.subjectLine = messageSource.getMessage("label.employee.forgotpassword.email.subject", null, "", locale);
		this.message= messageSource.getMessage("label.employee.forgotpassword.email.content", params, "", locale);
		
	}
	
	
}
