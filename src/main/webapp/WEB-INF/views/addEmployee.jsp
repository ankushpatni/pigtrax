<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.pigtrax.usermanagement.beans.PigTraxUser"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="form-primary" 	class="md-modal colored-header md-effect-9">
	<div class="md-content">
		<div class="modal-header">
			<h3><spring:message code="label.employee.addEmployeeData" text="Add Employee Data" /></h3> 
			 <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
			
		</div>
		<form name="employeeAddForm" novalidate angular-validator>
			<div class="modal-body form">
					<%PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();%>
						<%if(activeUser.getUserRole()==1 || activeUser.getUserRole() == 2){%>
				
				<div class="form-group">
					<label><spring:message code="label.employee.companyId" text="Company" /><span style='color: red'>*</span></label> 
					<!--<label ng-show="edit">{{add.employeeId}}</label> -->
						<select class="form-control" id="companyId" name="companyId" onchange="setCompanyId();" >
						<option value=""></option>
                        <option ng-repeat="company in companyList" ng-value="company.id" ng-selected="add.companyId == company.id">{{company.companyId}}</option>
                        </select>
				</div>
				<%}else{ %>
					<div class="form-group">
					<label><%=activeUser.getUserRole()%><spring:message code="label.employee.companyId" text="Company" /><span style='color: red'>*</span></label> 
					<input type="text" readonly="readonly"class="form-control"  maxlength="20" value="<%=activeUser.getCompanyId()%>" />
					<input type="hidden" id="companyIdfarm" value="<%=activeUser.getCompanyId()%>">
				</div>
				<%} %>
				<input type="hidden" id="setCompany" />
				<div class="form-group">
					<label><spring:message code="label.employee.name"
							text="Name" /><span style='color: red'>*</span></label> <input
						type="text"
						class="form-control" 
						placeholder="<spring:message code='label.employee.name'  text='Name'/>"
						name="employeeName" ng-model="add.name" maxlength="30" required
						required-message="'<spring:message code='label.employee.employeeNameRequired' text='Employee Name is required' />'" onclick="setCompanyIdFarm();"/>
				</div>
				<div class="form-group">
					<label><spring:message code="label.employee.employeeId"
							text="Employee Id" /><span style='color: red'>*</span></label> <input
						type="text"
						class="form-control" 
						placeholder="<spring:message code='label.employee.employeeId'  text='Employee Id'/>"
						name="employeeId" ng-model="add.employeeId" maxlength="20" required
						ng-pattern="/^[a-z0-9]+$/i"
						required-message="'<spring:message code='label.employee.employeeIdRequired' text='Employee Id is required' />'" />
				</div>
				<div class="form-group">
					<label><spring:message code="label.employee.email"
							text="Email" /><span style='color: red'>*</span></label> <input
						class="form-control" type="text"
						placeholder="<spring:message code='label.employee.email' text='Email' />"
						name="email" ng-model="add.email" 
						ng-pattern="/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/" required maxlength="30"
						required-message="'<spring:message code='label.employee.emailRequired' text='Email is required' />'"/>
				</div>
				<div class="form-group">
					<label ><spring:message
							code="label.employee.role"
							text="Role" /><span style='color: red'>*</span></label>
						<select class="form-control" id="userRoles"  name="userRoleId" ng-model="add.userRoleId"  required onchange="userRolesSubmit();">
                        <option ng-repeat="key in roleTypeKeys" ng-value="key" ng-selected="add.userRoleId==key">{{roleTypes[key]}}</option>
                        </select>
				</div>
				<input type="hidden" id="userRole">				
				 <div class="form-group">
					<div class="input-group">
					  <span class="input-group-addon">
						<input type="checkbox"  ng-model="add.active" id="isActiveR" name="isActiveR" ></input>
					  </span>
					  <input type="text" class="form-control" value="<spring:message code="label.employee.active" text="Active"/>" readonly="readonly">
					</div>
				  </div>
				  <div class="form-group">
					<div class="input-group">
					  <span class="input-group-addon">
						<input type="checkbox" ng-model="add.portalUser" id="isPortR" name="isPortR"></input>
					  </span>
					  <input type="text" class="form-control" value="<spring:message code="label.employee.portalUser" text="Portal User"/>"readonly="readonly">
					</div>
				  </div> 

				<div class="modal-footer">

					<button class="btn btn-primary btn-flat md-close" ng-click="addEmployee()"
						ng-hide="edit" >
						<spring:message code="label.employee.add" text="Add" />
					</button>
					<button class="btn btn-default btn-flat md-close" ng-click="cancel()">
						<spring:message code="label.employee.cancel" text="Cancel" />
					</button>
					<p style="color: red">{{alertMessage}}</p>
				</div>


			</div>
		</form>
		<div class="modal-footer" ng-show="alertVisible"></div>
	</div>
	<script type="text/javascript">
			function setCompanyId(){
			var f = document.getElementById("companyId").value;
			 document.getElementById("setCompany").value=f;
			}
	</script>
	<script type="text/javascript">
			function setCompanyIdFarm(){
			var f = document.getElementById("companyIdfarm").value;
			 document.getElementById("setCompany").value=f;
			}
	</script>
	<script type="text/javascript">
			function userRolesSubmit(){
			var f = document.getElementById("userRoles").value;
			 document.getElementById("userRole").value=f;
			}
	</script>

</div>
