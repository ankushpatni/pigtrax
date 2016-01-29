<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.pigtrax.usermanagement.beans.PigTraxUser"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="form-primary" 	class="md-modal colored-header warning md-effect-9">
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
					<label><spring:message code="label.employee.company" text="Company" /><span style='color: red'>*</span></label> 
					<!--<label ng-show="edit">{{add.employeeId}}</label> -->
						<select class="form-control" id="companyId" name="companyId" ng-model="add.companyId" required required-message="'<spring:message code='label.employee.companyRequired' text='Company is required' />'">
						<option value=""></option>
                        <option ng-repeat="company in companyList" ng-value="company.id" ng-selected="add.companyId == company.id">{{company.companyId}}</option>
                        </select>
				</div>
				<%}else{ %>
					<div class="form-group">
					<label><%=activeUser.getUserRole()%><spring:message code="label.employee.company" text="Company" /><span style='color: red'>*</span></label> 
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
				<div>
					<label><spring:message code="label.employee.phone"
							text="Phone" /><span style='color: red'>*</span></label>
							<input 	class="form-control" type="text" placeholder="+1-111-111-1111"
						name="phone" ng-model="add.phoneNumber" maxlength="15" required
						required-message="'<spring:message code='label.company.phoneRequired' text='Phone is required' />'"
						ng-pattern="/^[\0-9-+]+$/"
						invalid-message="'Please input correct number in US format.'" />
				</div>
				<div class="form-group">
					<label ><spring:message
							code="label.employee.role"
							text="User role" /><span style='color: red'>*</span></label>
						<select class="form-control" id="userRoles"  name="userRoleId" ng-model="add.userRoleId"  required required-message="'<spring:message code='label.employee.roleRequired' text='Role is required' />'">
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
					  <input type="text" class="form-control" value="<spring:message code="label.employee.portaluser" text="Portal User"/>"readonly="readonly">
					</div>
				  </div> 
				  
				  
				   <div class="form-group">
					<label ><spring:message
							code="label.employee.jobFunctionRole"
							text="Job Function" /><span style='color: red'>*</span></label>
						<select class="form-control" id="jobFunctionRoleId"  name="jobFunctionRoleId" ng-model="add.jobFunctionRoleId"  required 
						required-message="'<spring:message code='label.employee.jobFunctionRoleIdRequired' text='Job function is required' />'">
                        <option ng-repeat="key in jobFunctionRoleKeys" ng-value="key" ng-selected="add.jobFunctionRoleId==key">{{jobFunctionRoleKeyValues[key]}}</option>
                        </select>
				</div>
				  
				  <div class="form-group">
					<label ><spring:message
							code="label.employee.functionType"
							text="Function Type" /><span style='color: red'>*</span></label>
						<select class="form-control" id="functionTypeId"  name="functionTypeId" ng-model="add.functionTypeId"  required 
						required-message="'<spring:message code='label.employee.functionTypeIdRequired' text='Function type is required' />'">
                        <option ng-repeat="key in functionTypeKeys" ng-value="key" ng-selected="add.functionTypeId==key">{{functionTypeKeyValues[key]}}</option>
                        </select>
				</div>
				
				

				<div class="modal-footer">

					<button class="btn btn-success btn-flat md-close" ng-click="addEmployee()"
						ng-hide="edit" >
						<spring:message code="label.employee.add" text="Add" />
					</button>
					<button class="btn btn-warning btn-flat md-close" ng-click="cancel()">
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
	

</div>
