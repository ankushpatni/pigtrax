<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="companyContent" ng-controller="CompanyController" ng-init="getCompanyList()">
	<br> <br> <br>
	<button type="button" ng-click="addRandomItem(row)" class="btn btn-sm btn-success">
			<i class="glyphicon glyphicon-plus">
			</i> Add random item
		</button>

		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped">
			<thead>
			<tr>
				<th st-sort="CompanyID">Company ID</th>
				<th st-sort="name">Name</th>
				<th st-sort="address">Address</th>
				<th st-sort="city">City</th>
				<th st-sort="registrationNumber">Registration Number</th>
				<th st-sort="email">Email</th>
				<th st-sort="phone">Phone</th>
				<th st-sort="contactName">Contact Name</th>
				<th st-sort="payment">Payment</th>
				<th st-sort="paymentDate">Payment Date</th>
				<th st-sort="isActive">Active</th>
			</tr>
		<!-- 	<tr>
				<th colspan="5"><input st-search="" class="form-control" placeholder="global search ..." type="text"/></th>
			</tr> -->
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection">
				<td>{{row.companyId}}</td>
				<td>{{row.name}}</td>
				<td>{{row.address}}</td>
				<td>{{row.city}}</td>
				<td>{{row.registrationNumber}}</td>
				<td>{{row.email}}</td>
				<td>{{row.phone}}</td>
				<td>{{row.contactName}}</td>
				<td>{{row.payment}}</td>
				<td>{{row.paymentDate}}</td>
				<td>{{row.isActive}}</td>
				<td>
				<button type="button" ng-click="removeItem(row)" class="btn btn-sm btn-danger">
					<i class="glyphicon glyphicon-remove-circle">
					</i>
				</button>
				</td>
			</tr>
			</tbody>
		</table>
</div>