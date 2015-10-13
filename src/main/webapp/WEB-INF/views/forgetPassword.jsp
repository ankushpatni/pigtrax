<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%String message = new String();message = (String)request.getAttribute("message");if(message==null){message=new String();}%>
<!-- ======== @Region: #content ======== -->
<div class="cl-mcont">
	<div class="row dash-cols">
		<div class="col-sm-3 col-md-3"></div>
	            <div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<div class="header">
					<h3><spring:message code="label.employee.ForgetPassword"  text="Forget Password"/></h3>
				</div>
				<div class="content">
					<form role="form" class="form-horizontal"
						action="forgetPasswordAction" method="post">
						<div class="form-group">
							<label for="inputUserName" style="width:25%" class="col-sm-2 control-label"><spring:message code='label.employee.employeeId'  text='employee Id'/></label>
							<div class="col-sm-10 col-xs-4" style="width:55%">
								<input id="inputUserName" type="text" name="employeeId"
									placeholder="<spring:message code='label.employee.employeeId'  text='Employee Id'/>" class="form-control" required>
							</div>
						</div>
						<p style="color:red"><spring:message code="<%=message%>"  text=""/></p>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary" style="margin-left:inherit;"><spring:message code="label.employee.submit"  text="Submit"/></button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>



