<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="premisesContent" ng-controller="PremisesController" ng-init="getPremisesList('${companyId}',${generatedCompanyId})" class="container-fluid">
<form name="premisesForm" method="post">
<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
				<div class="header">
							<h3><spring:message	code="label.premise.premiseData" text="Premises Data" /></h3>
							<div class="alert alert-danger alert-white rounded"  ng-show="premiseDeleteErrorMessage">
			                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
			                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.premise.delete.activebarns.error' text='There are active barns available in the premise. Hence can not be deleted'/>
			                  </div>
							
						</div>
	<button type="button" ng-click="addPremiseData()" class="btn btn-sm btn btn-success">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.premise.addNewPremise" text="Add New Premise" />
		</button>
<div class="content">
	<div class="table-responsive">
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: #3399CC">
			<tr>
				<th st-sort="premiseID" style="width:10%"><spring:message code="label.premise.premiseID" text="Premise ID" /></th>
				<th st-sort="name" style="width:10%"><spring:message code="label.premise.name" text="Name" /></th>
				<th st-sort="name" style="width:10%"><spring:message code="label.premise.premiseType" text="Premise Type" /></th>
				<th st-sort="address" style="width:25%"><spring:message code="label.premise.address" text="Address" /></th>
				<th st-sort="city" style="width:10%"><spring:message code="label.premise.city" text="City" /></th>
				<th st-sort="state" style="width:10%"><spring:message code="label.company.country" text="Country" /></th>
				<th st-sort="zipcode" style="width:10%"><spring:message code="label.premise.zipcode" text="Zip Code" /></th>
				<th st-sort="zipcode" style="width:10%"><spring:message code="label.premise.gpsLatittude" text="GPS Latittude" /></th>
				<th st-sort="zipcode" style="width:10%"><spring:message code="label.premise.gpsLongitude" text="GPS Longitude" /></th>
				<th style="width:8%"><spring:message code="label.premise.edit" text="Edit" /></th>
				<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue())) {%>
				<th style="width:8%"><spring:message code="label.premise.delete" text="Delete" /></th>
				<%} %>
				<th style="width:8%"><spring:message code="label.company.goto" text="Go To" /></th>
				<th style="width:20px"><spring:message code="label.company.activateDeactivate"
												text="Activate/Deactivate"/></th>
			</tr>
		 	<tr>
				<th colspan="13"><input st-search="" class="form-control" placeholder="<spring:message code='label.company.globalSearch' text='Global Search ...' />" type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection track by $index">
				<td style="width:10%">{{row.permiseId}}</td>
				<td style="width:10%">{{row.name}}</td>
				<td style="width:10%">{{row.premiseType}}</td>
				<td style="width:25%">{{row.address}}</td>
				<td style="width:10%">{{row.city}}</td>
				<td style="width:10%">{{row.state}}</td>
				<td style="width:10%">{{row.zipcode}}</td>
				<td style="width:10%">{{row.gpsLatittude}}</td>
				<td style="width:10%">{{row.gpsLongitude}}</td>
				<td style="width: 8%">
					<button type="button" class="btn btn-edit btn-xs" ng-click="editPremiseData(row)">
						<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
				</td>
				<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue())) {%>
				<td style="width: 8%">
				  <button type="button" class="btn btn-edit btn-xs"
												ng-click="deletePremise(row)" ng-confirm-click="<spring:message code='label.employeegroup.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>">
									<span class="glyphicon glyphicon-remove"></span>												
					</button>
				</td>
				<%} %>
				
				<td style="width:8%">  
					<button type="button" class="btn btn btn-info btn-sm" style="margin-bottom:5px" ng-click="gotToPage(row)">
						<spring:message code="label.premise.barn" text="Barn" /></button>	
				</td>
				<td style="width:20px">
				<button ng-hide="row.active" type="button" ng-click="removeItem(row)" class="btn btn-sm btn-danger" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()"><a style="color:black" ng-show="hoverEdit"><spring:message code="label.company.deactivate" text="De-Activate" /></a>
					<i class="glyphicon glyphicon-remove-circle">
					</i>
				</button>
				<button ng-show="row.active" type="button" ng-click="removeItem(row)" class="btn btn-sm btn-success" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()"><a style="color:black" ng-show="hoverEdit"><spring:message code="label.company.activate" text="Activate" /></a>
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