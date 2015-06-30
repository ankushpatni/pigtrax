<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="companyContent" ng-controller="CompanyController" ng-init="getCompanyList()">
	<button type="button" ng-click="addCompanyData()" class="btn btn-sm btn-success">
			<i class="glyphicon glyphicon-plus">
			</i> Add New company
		</button>
<br> <br>
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: darkgrey">
			<tr>
				<th st-sort="CompanyID" size="5%">Company ID</th>
				<th st-sort="name" size="10%">Name</th>
				<th st-sort="address" size="15%">Address</th>
				<th st-sort="city" size="8%">City</th>
				<th st-sort="registrationNumber" size="5%">Registration Number</th>
				<th st-sort="email" size="8%">Email</th>
				<th st-sort="phone" size="8%">Phone</th>
				<th st-sort="contactName" size="8%">Contact Name</th>
				<th st-sort="payment" size="5%">Payment</th>
				<th st-sort="paymentDate" size="10%">Payment Date</th>
				<th st-sort="isActive" size="5%">Active</th>
				<th size="5%"></th>
			</tr>
		 	<tr>
				<th colspan="5"><input st-search="" class="form-control" placeholder="global search ..." type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection">
				<td size="5%">{{row.companyId}}</td>
				<td size="10%">{{row.name}}</td>
				<td size="15%">{{row.address}}</td>
				<td size="8%">{{row.city}}</td>
				<td size="5%">{{row.registrationNumber}}</td>
				<td size="8%">{{row.email}}</td>
				<td size="8%">{{row.phone}}</td>
				<td size="8%">{{row.contactName}}</td>
				<td size="5%">{{row.payment}}</td>
				<td size="10%">{{row.paymentDate}}</td>
				<td ng-if="row.active" size="5%">Active</td>
				<td ng-hide="row.active" size="5%">In Active</td>
				<td size="5%">
				<button type="button" ng-click="removeItem(row)" class="btn btn-sm btn-danger">
					<i class="glyphicon glyphicon-remove-circle">
					</i>
				</button>
				</td>
			</tr>
			</tbody>
		</table>
</div>