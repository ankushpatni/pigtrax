<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%String message = new String();message = (String)request.getAttribute("message");if(message==null){message=new String();}%>
<!-- ======== @Region: #content ======== -->
<div class="cl-mcont" ng-controller="PasswordController">
	<div class="row dash-cols">
		<div class="col-sm-3 col-md-3"></div>
	            <div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<div class="header">
					<h3><spring:message code="label.employee.ForgetPassword"  text="Forget Password"/></h3>
					
				<div class="alert alert-success alert-white rounded"  ng-show="successMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.employee.forgotpassword.emailsent' text='An email sent to reset the password'/>
                  </div>
				<div class="alert alert-danger alert-white rounded" ng-show="errorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.employee.forgotpassword.invalidemployeeid' text='The employee id entered is invalid'/>
                  </div>
				</div>
				<div class="content">
					<form role="form" class="form-horizontal"
						method="post">
						<div class="form-group">
							<label for="inputUserName" style="width:25%" class="col-sm-2 control-label"><spring:message code='label.employee.employeeId'  text='employee Id'/></label>
							<div class="col-sm-10 col-xs-4" style="width:55%">
								<input id="inputUserName" type="text" name="employeeId"
									placeholder="<spring:message code='label.employee.employeeId'  text='Employee Id'/>" class="form-control" required ng-model="employeeId">
							</div>
						</div>						
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button class="btn btn-success" style="margin-left:inherit;" ng-click="forgotPassword()"><spring:message code="label.employee.submit"  text="Submit"/></button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>



