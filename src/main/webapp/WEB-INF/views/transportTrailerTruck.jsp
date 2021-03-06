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
				<div class="alert alert-success alert-white rounded"  ng-show="truckSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.transportTruck.success.message' text='Truck added successfully'/>
                  </div>	
				<div class="alert alert-success alert-white rounded"  ng-show="truckDeleteMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.transportTruck.delete.message' text='Truck deleted successfully'/>
                  </div>                  			
				<button type="button" ng-click="addTransportTruckData()" class="btn btn-sm btn btn-success">
					<i class="glyphicon glyphicon-plus">
					</i> <spring:message code="label.transportTruck.addTransportTruckData" text="Add New Truck" />
				</button>
				<div class="content">
					<div class="table-responsive" style="overflow-x: hidden">
						<table st-table="displayedCollection1" st-safe-src="rowCollectionTruck" class="table table-striped" style="background-color: LightGray">  
							<thead style="background-color: #f7b781">
							<tr>
								<th st-sort="siloId" style="width:20%"><spring:message code="label.transportTruck.transportTruckId" text="Truck Number Plate" /></th>
								<th st-sort="siloId" style="width:20%"><spring:message code="label.transportTruck.purchaseYear" text="Purchase Year" /></th>
								<th st-sort="siloId" style="width:20%"><spring:message code="label.transportTruck.make" text="Make" /></th>
								<%-- <th style="width:20%"><spring:message code="label.transportTruck.deleteTruckData" text="Delete Truck" /></th>
								 --%><th style="width:20%"><spring:message code="label.companytargetform.action" text="Action" /></th>
							</tr>
						 	</thead>
							<tbody>
							<tr ng-repeat="row in displayedCollection1 track by $index">
								<td style="width:20%">{{row.transportTruckId}}</td>
								<td style="width:20%">{{row.purchaseYear}}</td>
								<td style="width:20%">{{row.make}}</td>
								<td style="width: 20%">
									<button type="button" class="btn btn-edit btn-xs" ng-click="deleteTransportTruckData(row)">
										<span class="glyphicon glyphicon-remove" ></span><spring:message code="label.transportTruck.deleteTruck" text="Delete" /></a></button>					
								</td>				
							</tr>
							</tbody>		
							<tr style="background-color: #f7b781">
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
				<div class="alert alert-success alert-white rounded"  ng-show="trailerSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.transportTrailer.success.message' text='Trailer added successfully'/>
                  </div>	
				<div class="alert alert-success alert-white rounded"  ng-show="trailerDeleteMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.transportTrailer.delete.message' text='Trailer deleted successfully'/>
                  </div>                  			
				<button type="button" ng-click="addTransportTrailerData()" class="btn btn-sm btn btn-success">
					<i class="glyphicon glyphicon-plus">
					</i> <spring:message code="label.transportTrailer.addTransportTruckData" text="Add New Trailer" />
				</button>
				<div class="content">
					<div class="table-responsive" style="overflow-x: hidden">
						<table st-table="displayedCollection" st-safe-src="rowCollectionTrailer" class="table table-striped" style="background-color: LightGray">  
							<thead style="background-color: #f7b781">
							<tr>
								<th st-sort="siloId" style="width:20%"><spring:message code="label.transportTrailer.transportTrailerId" text="Trailer Number Plate" /></th>
								<th style="width:30%"><spring:message code="label.transportTrailer.transportTrailerTypeId" text="Trailer Type" /></th>
								<th style="width:30%"><spring:message code="label.transportTrailer.trailerFunction" text="Trailer Function" /></th>
								<th style="width:30%"><spring:message code="label.transportTrailer.trailerYear" text="Year" /></th>
								<th style="width:30%"><spring:message code="label.transportTrailer.trailerMake" text="Make" /></th>
								<%-- <th style="width:20%"><spring:message code="label.transportTrailer.deleteTrailerData" text="Delete Trailer" /></th>
								 --%>
								 <th style="width:20%"><spring:message code="label.companytargetform.action" text="Action" /></th>
							</tr>
						 	</thead>
							<tbody>
							<tr ng-repeat="row in displayedCollection track by $index">
								<td style="width:20%">{{row.transportTrailerId}}</td>
								<td style="width:30%">{{transportTrailerType[row.trailerTypeId]}}</td>
								<td style="width:30%">{{row.trailerFunction}}</td>
								<td style="width:30%">{{row.trailerYear}}</td>
								<td style="width:30%">{{row.trailerMake}}</td>
								<td style="width: 20%">
									<button type="button" class="btn btn-edit btn-xs" ng-click="deleteTransportTrailerData(row)">
										<span class="glyphicon glyphicon-remove" ></span><spring:message code="label.transportTrailer.deleteTrailer" text="Delete" /></a></button>					
								</td>				
							</tr>
							</tbody>		
							<tr style="background-color: #f7b781">
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