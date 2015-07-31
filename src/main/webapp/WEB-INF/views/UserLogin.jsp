<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="cl-mcont">
	<div class="row dash-cols">
		<div class="col-sm-3 col-md-3"></div>
	            <div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<div class="header">
					<h3><spring:message code="label.login"  text="Login"/></h3>
				</div>
				<div class="content">
					<form role="form" class="form-horizontal"
						action="j_spring_security_check" method="post">
						<div class="form-group">
							<label for="inputUserName" class="col-sm-2 control-label"><spring:message code='label.username'  text='Username'/></label>
							<div class="col-sm-10 col-xs-4">
								<input id="inputUserName" type="text" name="username"
									placeholder="<spring:message code='label.username'  text='Username'/>"
									ng-model="userDto.email" class="form-control" required>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label"><spring:message code='label.password'  text='Password'/></label>
							<div class="col-sm-10 col-xs-4">
								<input id="inputPassword3" type="password" name="password"
									placeholder="<spring:message code='label.password'  text='Password'/>"
									class="form-control" ng-model="userDto.password" required>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary"><spring:message code="label.login"  text="Login"/></button>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>



