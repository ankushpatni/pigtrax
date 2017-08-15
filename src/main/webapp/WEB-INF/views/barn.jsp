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
		<button type="button" ng-click="addBarnData()" class="btn btn-sm btn btn-success">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.barn.addNewBarn" text="Add New Barn" />
		</button>
		<div class="content">
						<div class="table-responsive">
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: #f7b781">
			<tr>
				<th st-sort="barnId" style="width:10%"><spring:message code="label.barn.barnId" text="Barn ID" /></th>
				<th st-sort="area" style="width:10%"><spring:message code='label.barn.area'  text='Area'/></th>
				<th st-sort="ventilationType" style="width:10%"><spring:message code="label.barn.ventilationType" text="Ventilation Type" /></th>
				<th st-sort="feederCount" style="width:10%"><spring:message code="label.barn.feederTypeId" text="Feeder Type" /></th>
				<th st-sort="feederCount" style="width:10%"><spring:message code="label.barn.feederCount" text="Feeder Count" /></th>
				<th st-sort="feederCount" style="width:10%"><spring:message code="label.barn.holesPerFeeder" text="Holes Per Feeder" /></th>
				<th st-sort="waterAccessCount" style="width:10%"><spring:message code="label.barn.waterTypeId" text="Water Type" /></th>
				<th st-sort="waterAccessCount" style="width:10%"><spring:message code="label.barn.waterAccessCount" text="Water Access Count" /></th>
				<th st-sort="phaseType" style="width:10%"><spring:message code="label.barn.phaseType" text="Phase Type" /></th>
				<th st-sort="phaseType" style="width:10%"><spring:message code="label.barn.year" text="Year" /></th>
				<th st-sort="phaseType" style="width:10%"><spring:message code="label.barn.barnOrientationId" text="Barn Orientation Id" /></th>
				<th st-sort="location" style="width:25%"><spring:message code="label.barn.barnLocationId" text="Barn Location" /></th>
				<th st-sort="area" style="width:10%"><spring:message code='label.barn.barnPositionId'  text='Barn Position'/></th>
				<th style="width:8%"><spring:message code="label.premise.edit" text="Edit" /></th>
				<th style="width:8%"><spring:message code="label.company.goto" text="Go To" /></th>
				<th style="width:20px"><spring:message code="label.company.activateDeactivate"
												text="Status: Active/ Inactive"/></th>
			</tr>
		 	<tr>
				<th colspan="16"><input st-search="" class="form-control" placeholder="<spring:message code='label.company.globalSearch' text='Global Search ...' />" type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection track by $index">
				<td style="width:10%">{{row.barnId}}</td>				
				<td style="width:10%">{{row.area}}</td>
				<td style="width:10%">{{validationTypeKeyValues[row.ventilationTypeId]}}</td>
<!-- 				<td style="width:10%">{{validationType[row.ventilationTypeId]}}</td> -->
				<td style="width:10%">{{feederTypeKeyValues[row.feederTypeId]}}</td>
				<td style="width:10%">{{row.feederCount}}</td>
				<td style="width:10%">{{row.holesPerFeeder}}</td>
				<td style="width:10%">{{waterTypeKeyValues[row.waterTypeId]}}</td>
				<td style="width:10%">{{row.waterAccesCount}}</td>
				<td style="width:10%">{{phaseType[row.phaseTypeId]}}</td>
				<td style="width:10%">{{row.year}}</td>
				<td style="width:10%">
				<p ng-show="row.barnOrientationId == 1"><spring:message code="label.barn.barnOrientationId.northSouth" text="North - South" /></p>
				<p ng-show="row.barnOrientationId == 2"><spring:message code="label.barn.barnOrientationId.eastWest" text="East - West" /></p>
				</td>
				<td style="width:25%">{{barnLocationKeyValues[row.barnLocationId]}}</td>
				<td style="width:10%">{{barnPositionKeyValues[row.barnPositionId]}}</td>
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
	<input type="hidden" name="generatedBarnId" id="generatedBarnId"/>
	</form>
</div>