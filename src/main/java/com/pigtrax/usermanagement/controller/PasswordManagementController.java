package com.pigtrax.usermanagement.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pigtrax.usermanagement.beans.ResetPassword;
import com.pigtrax.usermanagement.dao.interfaces.EmployeeDao;
import com.pigtrax.usermanagement.service.interfaces.EmployeeService;

 

@Controller
public class PasswordManagementController {
	@Autowired
	HttpServletRequest request;
	@Autowired
	EmployeeService employeeService;
	
	private static Logger logger = Logger.getLogger(PasswordManagementController.class);
	
	@RequestMapping(value="/resetPassword")
	public String resetPassword(Model model){
		logger.info("in reset password controller--->");
		model.addAttribute("contentUrl", "resetPassword.jsp");
		return "template";
	}
	
	@RequestMapping(value="/resetPasswordCheck")
	public String resetPasswordCheck(@ModelAttribute("resetPassword") ResetPassword resetPassword,Model model){
		logger.info("in reset password change controller--->"+resetPassword.getReEnterPassword());
		String result=null;
		if(resetPassword.getNewPassword().equals(resetPassword.getReEnterPassword())){
			try{
				result= employeeService.resetPassword(resetPassword.getOldPassword(),resetPassword.getNewPassword());
				request.setAttribute("resetMessage", result);
			}catch(Exception s){
				logger.error("Inside resetPasswordChange()" +s.getMessage());
				s.printStackTrace();
			}
		}else{
			request.setAttribute("resetMessage", "label.employee.reEnterNotMatch");
		}
		
		model.addAttribute("contentUrl", "resetPassword.jsp");
		return "template";
	}
	
	@RequestMapping(value="/forgetPassword")
	public String forgetPassword(Model model){
		logger.info("in forget password controller--->");
		model.addAttribute("contentUrl", "forgetPassword.jsp");
		return "template";
	}
	
	@RequestMapping(value="/forgetPasswordAction")
	public String forgetPasswordAction(Model model){
		String status = null;
		logger.info("in forget password controller--->"+request.getParameter("employeeId"));
		try {
			
			 status = employeeService.forgetPassword(request.getParameter("employeeId"));
			 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			request.setAttribute("message",status );
		}
		model.addAttribute("contentUrl", "forgetPassword.jsp");
		return "template";
	}
	@RequestMapping(value="/changePassword")
	public String ChangePasswordFromUrl(Model model){
		String status = null;
		try{
		logger.info("in change password controller--->"+request.getParameter("otp"));
		request.setAttribute("otp", request.getParameter("otp"));
		model.addAttribute("contentUrl", "changePassword.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "template";
	}
	@RequestMapping(value="/changePasswordAction")
	public String ChangePasswordFromUrl(@ModelAttribute("resetPassword") ResetPassword resetPassword,Model model){
		String status = null;
		logger.info("in change password controller--->"+resetPassword.getNewPassword()+"   "+resetPassword.getReEnterPassword());
		if(resetPassword.getNewPassword().equals(resetPassword.getReEnterPassword()) && !request.getParameter("otp").equalsIgnoreCase(null)){
			try{
			 status = 	employeeService.changePassword(resetPassword.getNewPassword() , resetPassword.getReEnterPassword() , request.getParameter("otp"));
				model.addAttribute("contentUrl", "UserLogin.jsp");
				request.setAttribute("message",status );
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message",status );
				model.addAttribute("contentUrl", "changePassword.jsp");
			}
			}
		return "template";
	}
	

}
