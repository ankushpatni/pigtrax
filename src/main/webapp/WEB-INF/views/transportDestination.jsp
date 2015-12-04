<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="transportDestinationContent" ng-controller="TransportDestinationController" ng-init="getTransportDestinationList(${generatedCompanyId})" class="container-fluid">
<form name="transportDestinationForm" method="post">
<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
				<div class="header">
							<h3><spring:message code="label.transportDestination.header" text="Transport Destination Data" /></h3>
						</div>
		<button type="button" ng-click="addTransportDestinationData()" class="btn btn-sm btn btn-success">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.transportDestination.addTransportDestinationData" text="Add New Transport Destination" />
		</button>
		<div class="content">
						<div class="table-responsive" style="overflow-x: hidden">
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: #f7b781">
			<tr>
				<th st-sort="name" style="width:20%"><spring:message code="label.transportDestination.name" text="Name" /></th>
				<th st-sort="location" style="width:20%"><spring:message code="label.transportDestination.address" text="Address" /></th>
				<th st-sort="location" style="width:20%"><spring:message code="label.transportDestination.city" text="City" /></th>
				<th st-sort="location" style="width:20%"><spring:message code="label.transportDestination.state" text="State" /></th>
				<th st-sort="location" style="width:20%"><spring:message code="label.transportDestination.marketType" text="Market Type" /></th>
				<th style="width:20%"><spring:message code="label.transportDestination.delete" text="Delete" /></th>
			</tr>
		 	<tr>
				<th colspan="6"><input st-search="" class="form-control" placeholder="<spring:message code='label.company.globalSearch' text='Global Search ...' />" type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection track by $index">
				<td style="width:20%">{{row.name}}</td>
				<td style="width:20%">{{row.address}}</td>
				<td style="width:20%">{{row.city}}</td>
				<td style="width:20%">{{row.state}}</td>
				<td style="width:20%">{{row.marketType}}</td>
				<td style="width: 20%">
					<button type="button" class="btn btn-edit btn-xs" ng-click="deleteTransportDestinationData(row)">
					<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.transportDestination.delete" text="Delete" /></a></button>					
				</td>
			</tr>
			</tbody>		
			<tr style="background-color: #f7b781">
				<td colspan="14">
					<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPages" ></div>
				</td>
			</tr>
		</table></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</form>
</div>