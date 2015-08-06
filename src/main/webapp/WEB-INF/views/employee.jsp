<div id="employeeContent" ng-controller="EmployeeController"
	ng-init="getEmployeeList()">
	<div class="container">
		<br> <br>
		<table class="table">
			<tr>
				<th>Emp_ID</th>
				<th>Name</th>
				<th>Email</th>
			</tr>
			<tr ng-repeat="employee in employeeList"
				ng-if="employeeList != null && 0 < employeeList.length">
				<td>{{employee.empId}}</td>
				<td>{{employee.name}}</td>
				<td>{{employee.email}}</td>
			</tr>
			<tr ng-if="employeeList == null || 0 == employeeList.length">
				<td colspan="3">No employee records available</td>
			</tr>
		</table>
	</div>
</div>
