<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%String message = new String();message = (String)request.getAttribute("resetMessage");if(message==null){message=new String();}%>
<!-- ======== @Region: #content ======== -->
<div class="cl-mcont">
	<div class="row dash-cols">
		<div class="col-sm-2 col-md-2"></div>
	            <div class="col-sm-8 col-md-8">
			<div class="block-flat">
				<div class="header">
					<h3><spring:message code="label.employee.resetPassword"  text="Reset Password"/></h3>
				</div>
				<div class="content">
				
					<form role="form" class="form-horizontal"
						action="resetPasswordCheck" modelAttribute="resetPassword" method="get">
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label"><spring:message code='label.employee.oldPassword'  text='Old Password'/></label>
							<div class="col-sm-10 col-xs-4">
								<input id="inputPassword3" type="password" name="oldPassword"  minlength="8" maxlength="16"  
									placeholder="<spring:message code='label.employee.oldPassword'  text='oldPassword'/>" class="form-control" autocomplete="off"  required onKeyPress="demoMatchClick(this);">
									<input type="text" id="message" style="border:none;color:red ; font-weight:bold;" readonly="readonly"/>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label"><spring:message code='label.employee.newPassword'  text='New Password'/></label>
							<div class="col-sm-10 col-xs-4">
								<input id="inputPassword3" type="password" name="newPassword" minlength="8" maxlength="16"  
									placeholder="<spring:message code='label.employee.newPassword'  text='New Password'/>"
									class="form-control" required onKeyPress="demoMatchClick1(this);">
								<input type="text" id="message1" style="border:none;color:red ; font-weight:bold;" readonly="readonly"/>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label"><spring:message code='label.employee.reEnterNewPassword'  text='Re Enter Password'/></label>
							<div class="col-sm-10 col-xs-4">
								<input id="inputPassword3" type="password" name="reEnterPassword"  minlength="8" maxlength="16"  
									placeholder="<spring:message code='label.employee.reEnterNewPassword'  text='Re Enter Password'/>"
									class="form-control" required onKeyPress="demoMatchClick2(this);">
									<input type="text" id="message2" style="border:none;color:red ; font-weight:bold;" readonly="readonly"/>
							</div>
						</div>
						<p style="color:red"><spring:message code="<%=message%>"  text=""/></p>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit"  class="btn btn-primary"><spring:message code="label.employee.reset"  text="Save"/></button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-sm-2 col-md-2"></div>
	</div>
	
</div>



