<%@page import="com.pigtrax.usermanagement.beans.Employee"%>
<%@page import="com.pigtrax.usermanagement.beans.RoleType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.pigtrax.usermanagement.beans.Company"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<div id="form-primary" 	class="md-modal colored-header warning md-effect-9" ng-init="getEmployeeDetails(${employeeId})">
	<div class="md-content">
		<div class="modal-header">
			<h3><spring:message code="label.employee.editEmployeeData" text="Edit Employee Data" /></h3>
			 <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
			
		</div>
		<form name="employeeEditForm" novalidate angular-validator>		
			<div class="modal-body form">
			<input type="hidden" name="id" ng-model="add.id" />
				<div class="form-group">
					<label><spring:message code="label.employee.companyId" text="Company" /><span style='color: red'>*</span></label> 
					<!--<label ng-show="edit">{{add.employeeId}}</label> -->
					<input type="text" readonly="readonly" class="form-control" ng-value="add.companyName" maxlength="20"/>
						<input type="hidden" name="companyId" ng-model="add.companyId" />
					
				</div>
				
				<div class="form-group">
					<label><spring:message code="label.employee.name"
							text="Name" /><span style='color: red'>*</span></label> <input
						type="text"
						class="form-control"
						placeholder="<spring:message code='label.employee.name'  text='Name'/>"
						name="employeeName" ng-model="add.name" maxlength="30" required
						required-message="'<spring:message code='label.employee.employeeNameRequired' text='Employee Name is required' />'" />
				</div>
				<div class="form-group">
					<label><spring:message code="label.employee.employeeId"
							text="Employee Id" /><span style='color: red'>*</span></label> <input
						type="text"
						class="form-control" 
						name="employeeId" ng-model="add.employeeId" ng-pattern="/^[a-z0-9]+$/i" required  maxlength="20"  required
						required-message="'<spring:message code='label.employee.employeeIdRequired' text='Employee Id is required' />'" />
				</div>
				<div class="form-group">
					<label><spring:message code="label.employee.email"
							text="Email" /><span style='color: red'>*</span></label> <input
						class="form-control" type="text"
						placeholder="<spring:message code='label.employee.email' text='Email' />"
						name="email" ng-model="add.email"  ng-pattern="/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/"  required maxlength="30"
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
							text="Role" /><span style='color: red'>*</span></label> 						
						<select class="form-control" id="userRoles"  name="userRoleId" ng-model="add.userRoleId"  required  required-message="'<spring:message code='label.employee.userRoleId' text='Role  is required' />'">
                        <option ng-repeat="key in roleTypeKeys" ng-value="key" ng-selected="add.userRoleId==key">{{roleTypes[key]}}</option>
                        </select>
						
				</div>
				<input type="hidden" id="userRole">
				 <div class="form-group">
					<div class="input-group">
					  <span class="input-group-addon">	
						<input type="checkbox"  ng-model="add.active"  id="isActiveR" name="isActiveR" ng-checked="add.active"></input>
					  </span>
					  <input type="text" class="form-control" value="<spring:message code="label.employee.active" text="Active"/>" readonly="readonly">
					</div>
				  </div>
				  <div class="form-group">
					<div class="input-group">
					  <span class="input-group-addon">				 
						<input type="checkbox" ng-model="add.portalUser"  id="isPortR" name="isPortR" ng-checked="add.portalUser"></input>
					  </span>
					  <input type="text" class="form-control" value="<spring:message code="label.employee.portalUser" text="Portal User"/>" readonly="readonly">
					</div>
				  </div> 
				
				  <div class="form-group">
					<label ><spring:message
							code="label.employee.functionType"
							text="Function Type" /><span style='color: red'>*</span></label>
						<select class="form-control" id="functionTypeId"  name="functionTypeId" ng-model="add.functionTypeId"  required >
                        <option ng-repeat="key in functionTypeKeys" ng-value="key" ng-selected="add.functionTypeId==key">{{functionTypeKeyValues[key]}}</option>
                        </select>
				</div>
				
				 <div class="form-group">
					<label ><spring:message
							code="label.employee.jobFunctionRole"
							text="Job Function" /><span style='color: red'>*</span></label>    
						<select class="form-control" id="jobFunctionRoleId"  name="jobFunctionRoleId" ng-model="add.jobFunctionRoleId"  required >
                        <option ng-repeat="key in jobFunctionRoleKeys" ng-value="key" ng-selected="add.jobFunctionRoleId==key">{{jobFunctionRoleKeyValues[key]}}</option>
                        </select>
				</div>
				 <div class="form-group">
					<label ><a href="" ng-click="getPreviousRoles(add.id)"><spring:message
							code="label.employee.previousRoles"
							text="Previous Roles" /></a></label>
					<table ng-show="onPrevRoles">
					  <tr> <th> <spring:message code="label.employee.functionName"	text="Function Name" /> </th>
						   <th><spring:message code="label.employee.functionStartDate"	text="Start Date" /></th>
						   <th><spring:message code="label.employee.functionEndDate"	text="End Date" /></th>
					 </tr>
					 <tr ng-repeat="emp in prevRoleList" ng-if="prevRoleList != null && 0<prevRoleList.length">
					   <td>{{emp.empFunctionName}}</td>
					   <td>{{DateUtils.getFormatedDate(emp.functionStartDate)}}</td>
					   <td>{{DateUtils.getFormatedDate(emp.functionEndDate)}}</td>
					 </tr>
					  <tr ng-if="prevRoleList == null || 0 == prevRoleList.length">
					    <td colspan="3"><spring:message code="label.employee.noFunctionRoles"	text="No functions assigned" /></td>
					  </tr>
					 
					</table>
				</div>
		

				<div class="modal-footer">

					<button class="btn btn-success btn-flat md-close" ng-click="editEmployee();"
						ng-hide="edit" >
						<spring:message code="label.employee.add" text="Update" />
					</button>
					<button class="btn btn-warning btn-flat md-close" ng-click="cancel()">
						<spring:message code="label.employee.cancel" text="Cancel" />
					</button>
					<p style="color: red">{{alertMessage}}</p>
				</div>
			</div>
		</form>
		<div class="modal-footer" ng-show="alertVisible"></div>
		
		
		
		
		<div id="selectEmployeeGroupModal" class="modal colored-header modal-sm" >
      
		
		
	</div>
	<script type="text/javascript">
			function userRolesSubmit(){
			var f = document.getElementById("userRoles").value;
			 document.getElementById("userRole").value=f;
			}
	</script>
</div>

