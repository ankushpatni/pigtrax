<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="companyContent" ng-controller="CompanyController">
	<br> <br> <br>
	<button type="button" ng-click="addRandomItem(row)" class="btn btn-sm btn-success">
			<i class="glyphicon glyphicon-plus">
			</i> Add random item
		</button>

		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped">
			<thead>
			<tr>
				<th st-sort="firstName">first name</th>
				<th st-sort="lastName">last name</th>
				<th st-sort="birthDate">birth date</th>
				<th st-sort="balance">balance</th>
			</tr>
			<tr>
				<th colspan="5"><input st-search="" class="form-control" placeholder="global search ..." type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection">
				<td>{{row.firstName}}</td>
				<td>{{row.lastName}}</td>
				<td>{{row.birthDate}}</td>
				<td>{{row.balance}}</td>
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