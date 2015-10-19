<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="barnContent" ng-controller="BarnController" ng-init="getBarnList('${premisesId}',${generatedPremisesId},true)" class="container-fluid">
<form name="barnForm" method="post">
<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
				<div class="header">
							<h3><spring:message	code="label.barn.barnData" text="Barn Data" /></h3>
						</div>
		<button type="button" ng-click="addBarnData()" class="btn btn-sm btn btn-primary">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.barn.addNewBarn" text="Add New Barn" />
		</button>
		<div class="content">
						<div class="table-responsive">
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: #3399CC">
			<tr>
				<th st-sort="barnId" style="width:10%"><spring:message code="label.barn.barnId" text="Barn ID" /></th>
				<th st-sort="phaseType" style="width:10%"><spring:message code="label.barn.phaseType" text="Phase Type" /></th>
				<th st-sort="location" style="width:25%"><spring:message code="label.barn.location" text="Location" /></th>
				<th st-sort="area" style="width:10%"><spring:message code="label.barn.area" text="Area" /></th>
				<th st-sort="feederCount" style="width:10%"><spring:message code="label.barn.feederCount" text="Feeder Count" /></th>
				<th st-sort="waterAccessCount" style="width:10%"><spring:message code="label.barn.waterAccessCount" text="Water Access Count" /></th>
				<th st-sort="ventilationType" style="width:10%"><spring:message code="label.barn.ventilationType" text="Ventilation Type" /></th>
				<th style="width:8%"><spring:message code="label.premise.edit" text="Edit" /></th>
				<th style="width:8%"><spring:message code="label.company.goto" text="Go To" /></th>
				<th style="width:20px"><spring:message code="label.company.activateDeactivate"
												text="Activate/Deactivate"/></th>
			</tr>
		 	<tr>
				<th colspan="10"><input st-search="" class="form-control" placeholder="<spring:message code='label.company.globalSearch' text='Global Search ...' />" type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection track by $index">
				<td style="width:10%">{{row.barnId}}</td>
				<td style="width:10%">{{phaseType[row.phaseTypeId]}}</td>
				<td style="width:25%">{{row.location}}</td>
				<td style="width:10%">{{row.area}}</td>
				<td style="width:10%">{{row.feederCount}}</td>
				<td style="width:10%">{{row.waterAccessCount}}</td>
				<td style="width:10%">{{validationType[row.ventilationTypeId]}}</td>
				<td style="width: 8%">
					<button type="button" class="btn btn-edit btn-xs" ng-click="editBarnData(row)">
						<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
				</td>
				<td style="width:15%">  
					<button type="button" class="btn btn btn-info btn-sm" style="margin-bottom:5px" ng-click="gotToPage('room',row)">
						<spring:message code="label.barn.room" text="Room" /></button>
					<br>
					<br>
					<button type="button" class="btn btn btn-info btn-sm" style="margin-bottom:5px" ng-click="gotToPage('silo',row)">
						<spring:message code="label.barn.silo" text="Silo" /></button>	
							<%-- <div class="btn-group">								 				
							  <button type="button" data-toggle="dropdown" class="btn-sm btn-info dropdown-toggle"><spring:message code="label.company.goto" text="Go To" /> <span class="caret"></span></button>
							  <ul role="menu" class="dropdown-menu">
								<li><a href="#" ng-click="gotToPage('room',row)"><spring:message code="label.barn.room" text="Room" /></a></li>
								<li><a href="#" ng-click="gotToPage('silo',row)"><spring:message code="label.barn.silo" text="Silo" /></a></li>
							  </ul>
							</div>	 --%>
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
		</table></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="generatedBarnId" id="generatedBarnId"/>
	</form>
</div>