<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 

<!-- ======== @Region: #content ======== -->
<div class="cl-mcont" ng-controller="PasswordController">
	<div class="row dash-cols">
		<div class="col-sm-1 col-md-1"></div>
	            <div class="col-sm-10 col-md-10">
			<div class="block-flat">
				<div class="header">
					<h3><spring:message code="label.employee.resetPassword"  text="Reset Password"/></h3>
					
					<div class="alert alert-success alert-white rounded"  ng-show="changePasswordSuccess">
                   	 <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    	<div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.employee.forgotpassword.changePasswordSuccess' text='Password changed successfully. Please login'/>
                  	</div>	
                  	<div class="alert alert-danger alert-white rounded" ng-show="changePasswordError">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.employee.forgotpassword.changePasswordError' text='An exception occurred. Please try again'/>
                  </div>
				</div>
				<div class="content">
					<form role="form" class="form-horizontal"
						modelAttribute="resetPassword" >
						<input type="hidden" name="otp" id="otp" value="${otp}"/>
						<input type="hidden" name="eid" id = "eid" value="${eid}"/>
						
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label"><spring:message code='label.employee.sharedPassword'  text='One Time Password'/></label>
							<div class="col-sm-10 col-xs-4">
								<input id="inputPassword3" type="password" ng-model="employee.oneTimePassword" name="oneTimePassword"minlength="7" maxlength="40" onKeyPress="demoMatchClick1(this);" ng-pattern="/^[a-z0-9]+$/i"
									placeholder="<spring:message code='label.onetimePassword'  text='One time Password'/>"
									class="form-control" required>
									<input type="text" id="message1" style="border:none;color:red ; font-weight:bold;" readonly="readonly" ng-hide="invalidOTPMessage"/>
							</div>
							<label ng-show="invalidOTPMessage" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.employee.forgotpassword.invalidOTPMessage' text='Invalid One time Password entered' /></label>
						</div>
						
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label"><spring:message code='label.employee.newPassword'  text='New Password'/></label>
							<div class="col-sm-10 col-xs-4">
								<input id="inputPassword3" type="password" ng-model="employee.newPassword"  name="newPassword"minlength="7" maxlength="40" onKeyPress="demoMatchClick1(this);" ng-pattern="/^[a-z0-9]+$/i"
									placeholder="<spring:message code='label.newPassword'  text='New Password'/>"
									class="form-control" required>
									<input type="text" id="message1" style="border:none;color:red ; font-weight:bold;" readonly="readonly"/>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label"><spring:message code='label.employee.reEnterNewPassword'  text='Re Enter Password'/></label>
							<div class="col-sm-10 col-xs-4">
								<input id="inputPassword3" type="password"  ng-model="employee.reEnterPassword" name="reEnterPassword"minlength="7" maxlength="40" onKeyPress="demoMatchClick2(this);"  ng-pattern="/^[a-z0-9]+$/i"
									placeholder="<spring:message code='label.reEnterPass'  text='Re Enter Password'/>"
									class="form-control" required>
									<input type="text" id="message2" style="border:none;color:red ; font-weight:bold;" readonly="readonly"/>
							</div>
							<label ng-show="passwordDoesnotMatch" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.employee.forgotpassword.passwordDoesnotMatch' text='The new password entered does not match with re entered ' /></label>
						</div>
						
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button  class="btn btn-success" ng-click="forgotChangePassword()"><spring:message code="label.employee.save"  text="Save"/></button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-sm-1 col-md-1"></div>
	</div>
</div>

