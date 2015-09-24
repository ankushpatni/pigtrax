<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="transportTrailerTruckContent" ng-controller="TransportTrailerTruckController" ng-init="getTransportTrailerTruck(${generatedCompanyId})" class="container-fluid">
<form name="transportTrailerTruckForm" method="post">
<div class="cl-mcont">
	<div class="row">
		<div class="col-md-6">
			<div class="block-flat">
				<div class="header">
					<h3><spring:message code="label.transportTruck.header" text="Truck Data" /></h3>
				</div>
				<button type="button" ng-click="addTransportTruckData()" class="btn btn-sm btn btn-primary">
					<i class="glyphicon glyphicon-plus">
					</i> <spring:message code="label.transportTruck.addTransportTruckData" text="Add New Truck" />
				</button>
				<div class="content">
					<div class="table-responsive" style="overflow-x: hidden">
						<table st-table="displayedCollection1" st-safe-src="rowCollectionTruck" class="table table-striped" style="background-color: LightGray">  
							<thead style="background-color: #3399CC">
							<tr>
								<th st-sort="siloId" style="width:20%"><spring:message code="label.transportTruck.transportTruckId" text="Truck Number Plate" /></th>
								<th style="width:20%"><spring:message code="label.transportTruck.deleteTruckData" text="Delete Truck" /></th>
							</tr>
						 	</thead>
							<tbody>
							<tr ng-repeat="row in displayedCollection1 track by $index">
								<td style="width:20%">{{row.transportTruckId}}</td>
								<td style="width: 20%">
									<button type="button" class="btn btn-edit btn-xs" ng-click="deleteTransportTruckData(row)">
										<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.transportTruck.deleteTruck" text="Delete" /></a></button>					
								</td>				
							</tr>
							</tbody>		
							<tr style="background-color: #3399CC">
								<td colspan="14">
									<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPagesTruck" ></div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="block-flat">
				<div class="header">
					<h3><spring:message code="label.transportTrailer.header" text="Trailer Data" /></h3>
				</div>
				<button type="button" ng-click="addTransportTrailerData()" class="btn btn-sm btn btn-primary">
					<i class="glyphicon glyphicon-plus">
					</i> <spring:message code="label.transportTrailer.addTransportTruckData" text="Add New Trailer" />
				</button>
				<div class="content">
					<div class="table-responsive" style="overflow-x: hidden">
						<table st-table="displayedCollection" st-safe-src="rowCollectionTrailer" class="table table-striped" style="background-color: LightGray">  
							<thead style="background-color: #3399CC">
							<tr>
								<th st-sort="siloId" style="width:20%"><spring:message code="label.transportTrailer.transportTrailerId" text="Trailer Number Plate" /></th>
								<th style="width:30%"><spring:message code="label.transportTrailer.transportTrailerTypeId" text="Trailer Type" /></th>
								<th style="width:20%"><spring:message code="label.transportTrailer.deleteTrailerData" text="Delete Trailer" /></th>
							</tr>
						 	</thead>
							<tbody>
							<tr ng-repeat="row in displayedCollection track by $index">
								<td style="width:20%">{{row.transportTrailerId}}</td>
								<td style="width:30%">{{transportTrailerType[row.trailerTypeId]}}</td>
								<td style="width: 20%">
									<button type="button" class="btn btn-edit btn-xs" ng-click="deleteTransportTrailerData(row)">
										<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.transportTrailer.deleteTrailer" text="Delete" /></a></button>					
								</td>				
							</tr>
							</tbody>		
							<tr style="background-color: #3399CC">
								<td colspan="14">
									<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPagesTrailer" ></div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>		
	<input type="hidden" name="generatedSiloId" id="generatedSiloId"/>
	</form>
</div>