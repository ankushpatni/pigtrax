<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.pigtrax.usermanagement.beans.PigTraxUser, com.pigtrax.usermanagement.enums.RoleType"%>
<div id="employeeContent" ng-controller="EmployeeController"
	ng-init="getEmployeeListIndi()">
	
	
	<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
						<div class="header">
							<h3><spring:message	code="label.employee.employeedata" text="Employee Data" /></h3>
						</div>
						
						<%PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();%>
						<%if(activeUser.getUserRole()==1 || activeUser.getUserRole() == 2){%>
						
					<button type="button" ng-click="addEmployeeData()" data-modal="form-primary"  class="btn btn-success btn-flat md-trigger">
						<i class="glyphicon glyphicon-plus"> </i>
						<spring:message code="label.employee.addNewEmployee"
							text="Add New Employee" />
					</button>
					<%}else{}%>
					
				<div class="table-responsive">
		 <table st-table="employeeList" st-safe-src="rowCollection"
								class="table table-striped" style="background-color: LightGray">
			<thead style="background-color: #3399CC">
			<tr>
				<th><label><spring:message code="label.employee.employeeId" text="Employee Id" /></label></th>
				<th><label><spring:message code="label.employee.name" text="Name" /></label></th>
				<th><label><spring:message code="label.employee.email" text="Email" /></label></th>
				<%if(activeUser.getUserRole()==1 || activeUser.getUserRole() == 2){%>
				<th><label><spring:message code="label.employee.company" text="Company" /></label></th>
				<% } %>
				<th size="5%"><label><spring:message code="label.employee.edit" text="Edit" /><label></th>
				<th size="1%" style="width:10px;"><label><spring:message code="label.employee.action" text="Action" /></label></th>
			</tr>
			<tr>
										<th colspan="6"><input st-search="" class="form-control"
											placeholder="<spring:message code='label.company.globalSearch' text='Global Search ...' />" type="text" /></th>
									</tr>
			</thead>
			<tbody>
			<tr ng-repeat="employee in employeeList track by $index"
				ng-if="employeeList != null && 0 < employeeList.length"> 
				<td>{{employee.employeeId}}</td>
				<td>{{employee.name}}</td>   
				<td>{{employee.email}}</td>
				<%if(activeUser.getUserRole()==1 || activeUser.getUserRole() == 2){%>
				<td>{{employee.companyName}}</td>
				<% } %>
				<td>
					<button type="button" class="btn btn-edit btn-xs"ng-click="editEmployeeData(employee.id)">
						<span class="glyphicon glyphicon-pencil"></span>
							<spring:message code="label.employee.edit" text="Edit" />
					</button>
				</td>
				<td>
											<button ng-if="employee.activeFlag == 'true' " type="button"
												ng-click="deActivateEmployee(employee.id)" class="btn btn-sm btn-danger"
												ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()">
												<a style="color: black" ng-show="hoverEdit"><spring:message
														code="label.employee.deactivate" text="De-Activate" /></a> <i
													class="glyphicon glyphicon-remove-circle"> </i>
											</button>
											<button ng-if="employee.activeFlag == 'false' " type="button"
												ng-click="activateEmployee(employee.id)"class="btn btn-sm btn-success"
												ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()">
												<a style="color: black" ng-show="hoverEdit"><spring:message
														code="label.employee.activate" text="Activate" /></a> <i
													class="glyphicon glyphicon glyphicon-ok"> </i>
											</button>
										</td>
			</tr>
			<tr ng-if="employeeList == null || 0 == employeeList.length">
				<td colspan="3">No employee records available</td>
			</tr>
			</tbody>
				<tfoot>
				<tr >
					<td colspan="14"  class="text-center">
						<div st-pagination="" st-items-by-page="itemsByPage"
							st-displayed-pages="totalPages"></div>
					</td>
				</tr>
				</tfoot>
		</table>
	</div>
					
				</div>
			</div>
		</div>
	</div>
	</div>