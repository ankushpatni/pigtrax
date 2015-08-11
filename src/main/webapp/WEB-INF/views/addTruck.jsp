<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header md-effect-9">
	<div class="md-content" ng-show="truck">
		<div class="modal-header" >
			<h3><spring:message code="label.transportTruck.addTransportTruckData" text="Add Truck Data" /></h3>
			<button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">�</button>
		</div>
		<form name="truckAddForm" novalidate angular-validator>
			<div class="modal-body form">
		        	<div class="form-group">
						<label><spring:message code="label.transportTruck.transportTruckId" text="Truck Number Plate" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.transportTruck.transportTruckId' text='Truck Number Plate' />" name="transportTruckId" ng-model="add.transportTruckId" maxlength="20" required required-message="'<spring:message code='label.transportTruck.transportTruckIdRequired' text='Truck Number plate is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.silo.siloIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
					</div>
					<div class="modal-footer">
		            	<button class="btn btn-primary btn-flat md-close"  ng-click="addTransportTruck()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
						<button class="btn btn-default btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
		        	</div>
		       </div>
	    </form>
	</div>
	<div class="md-content" ng-hide="truck">
		<div class="modal-header" >
			<h3><spring:message code="label.transportTrailer.addTransportTruckData" text="Add Trailer Data" /></h3>
			<button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">�</button>
		</div>
		<form name="trailerAddForm" novalidate angular-validator>
			<div class="modal-body form">
		        	<div class="form-group">
						<label><spring:message code="label.transportTrailer.transportTrailerId" text="Trailer Number Plate" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.transportTrailer.transportTrailerId' text='Trailer Number Plate' />" name="transportTrailerId" ng-model="add.transportTrailerId" maxlength="20" required required-message="'<spring:message code='label.transportTrailer.transportTrailerIdRequired' text='Trailer Number plate is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.silo.siloIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
					</div>
					<div class="form-group">
						<label><spring:message code="label.transportTrailer.trailerTypeId" text="Trailer Type" /><span style='color: red'>*</span></label>
						<select class="form-control" type="text" placeholder="<spring:message code='label.transportTrailer.trailerTypeId'  text='Trailer Type'/>" name="siloType" ng-model="add.trailerTypeId" required required-message="'<spring:message code='label.transportTrailer.trailerTypeRequired' text='Trailer Type is required' />'" ng-options="k as v for (k, v) in transportTrailerType"/>	
					</div>
					<div class="modal-footer">
		            	<button class="btn btn-primary btn-flat md-close"  ng-click="addTransportTrailer()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
						<button class="btn btn-default btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
		        	</div>
		       </div>
	    </form>
	</div>
</div>