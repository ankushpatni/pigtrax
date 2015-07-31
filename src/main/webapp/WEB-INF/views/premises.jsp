<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="premisesContent" ng-controller="PremisesController" ng-init="getPremisesList('${companyId}',${generatedCompanyId})" class="container-fluid">
<form name="premisesForm" method="post">
<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
				<div class="header">
							<h3>Premises Data</h3>
						</div>
	<button type="button" ng-click="addPremiseData()" class="btn btn-sm btn btn-primary">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.premise.addNewPremise" text="Add New Premise" />
		</button>
<div class="content">
	<div class="table-responsive" style="overflow-x: hidden">
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: #3399CC">
			<tr>
				<th st-sort="premiseID" style="width:10%"><spring:message code="label.premise.premiseID" text="Premise ID" /></th>
				<th st-sort="name" style="width:10%"><spring:message code="label.premise.name" text="Name" /></th>
				<th st-sort="address" style="width:25%"><spring:message code="label.premise.address" text="Address" /></th>
				<th st-sort="city" style="width:10%"><spring:message code="label.premise.city" text="City" /></th>
				<th st-sort="state" style="width:10%"><spring:message code="label.premise.state" text="State" /></th>
				<th st-sort="zipcode" style="width:10%"><spring:message code="label.premise.zipcode" text="Zip Code" /></th>
				<th style="width:8%"><spring:message code="label.premise.edit" text="Edit" /></th>
				<th style="width:8%"><spring:message code="label.company.goto" text="Go To" /></th>
				<th style="width:20px"></th>
			</tr>
		 	<tr>
				<th colspan="9"><input st-search="" class="form-control" placeholder="global search ..." type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection track by $index">
				<td style="width:10%">{{row.permiseId}}</td>
				<td style="width:10%">{{row.name}}</td>
				<td style="width:25%">{{row.address}}</td>
				<td style="width:10%">{{row.city}}</td>
				<td style="width:10%">{{row.state}}</td>
				<td style="width:10%">{{row.zipcode}}</td>
				<td style="width: 8%">
					<button type="button" class="btn btn-edit btn-xs" ng-click="editPremiseData(row)">
						<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
				</td>
				<td style="width:8%">  
					<button type="button" class="btn btn btn-info btn-sm" style="margin-bottom:5px" ng-repeat ="rt in differentPages track by $index" ng-click="gotToPage($index,row)">
						{{rt.name}}</button>	
				</td>
				<td style="width:20px">
				<button ng-if="row.active" type="button" ng-click="removeItem(row)" class="btn btn-sm btn-danger" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()"><a style="color:black" ng-show="hoverEdit"><spring:message code="label.company.deactivate" text="De-Activate" /></a>
					<i class="glyphicon glyphicon-remove-circle">
					</i>
				</button>
				<button ng-hide="row.active" type="button" ng-click="removeItem(row)" class="btn btn-sm btn-success" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()"><a style="color:black" ng-show="hoverEdit"><spring:message code="label.company.activate" text="Activate" /></a>
					<i class="glyphicon glyphicon glyphicon-ok">
					</i>
				</button>
				</td>
			</tr>
			</tbody>		
			<tr style="background-color: #3399CC">
				<td colspan="14">
					<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPages" ></div>
				</td>
			</tr>
		</table>
		</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="generatedPremisesId" id="generatedPremisesId"/>
	</form>
</div>