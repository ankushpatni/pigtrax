<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header warning md-effect-9">
	<div class="md-content" ng-show="truck">
		<div class="modal-header" >
			<h3><spring:message code="label.transportTruck.addTransportTruckData" text="Add Truck Data" /></h3>
			<button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
		</div>
		<form name="truckAddForm" novalidate angular-validator>
			<div class="modal-body form">
				<div class="alert alert-danger alert-white rounded"  ng-show="duplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.transportTruck.duplicate.error.message' text='Truck with same number plate already exist'/>
                  </div>
		        	<div class="form-group">
						<label><spring:message code="label.transportTruck.transportTruckId" text="Truck Number Plate" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.transportTruck.transportTruckId' text='Truck Number Plate' />" name="transportTruckId" ng-model="add.transportTruckId" maxlength="20" required required-message="'<spring:message code='label.transportTruck.transportTruckIdRequired' text='Truck Number plate is required' />'" ng-pattern="/^[a-z\0-9\s]+$/i" invalid-message="'<spring:message code='label.silo.siloIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
					</div>
					<div class="form-group">
						<label><spring:message code="label.transportTruck.make" text="Make" /></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.transportTruck.make' text='Make' />" name="make" ng-model="add.make" maxlength="30"  ng-pattern="/^[a-z\0-9\s]+$/i" invalid-message="'<spring:message code='label.silo.siloIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
					</div>
					<div class="form-group">
                      <label><spring:message code='label.transportTruck.purchaseYear'  text='Purchase Year'/></label>                      
                      <select class="form-control" name="purchaseYear" ng-model="add.purchaseYear" >
                            <option ng-repeat="i in purchaseYearArr" ng-value="i">{{i}}</option>                            
                        </select>
                    </div>
					<div class="modal-footer">
		            	<button class="btn btn-success btn-flat md-close"  ng-click="addTransportTruck()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
						<button class="btn btn-warning btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
		        	</div>
		       </div>
	    </form>
	</div>
	<div class="md-content" ng-hide="truck">
		<div class="modal-header" >
			<h3><spring:message code="label.transportTrailer.addTransportTruckData" text="Add Trailer Data" /></h3>
			<button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
		</div>
		<form name="trailerAddForm" novalidate angular-validator>
			<div class="modal-body form">
		        	<div class="form-group">
						<label><spring:message code="label.transportTrailer.transportTrailerId" text="Trailer Number Plate" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.transportTrailer.transportTrailerId' text='Trailer Number Plate' />" name="transportTrailerId" ng-model="add.transportTrailerId" maxlength="20" required required-message="'<spring:message code='label.transportTrailer.transportTrailerIdRequired' text='Trailer Number plate is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.silo.siloIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
					</div>
					<div class="form-group">
						<label><spring:message code="label.transportTrailer.trailerTypeId" text="Trailer Type" /></label>
						<select class="form-control" type="text" placeholder="<spring:message code='label.transportTrailer.trailerTypeId'  text='Trailer Type'/>" name="trailerTypeId" ng-model="add.trailerTypeId">
							<option ng-repeat="key in transportTrailerTypeKeySet" ng-value="key" ng-selected="add.trailerTypeId==key">{{transportTrailerType[key]}}</option>
						</select>							
					</div>
					<div class="form-group">
						<label><spring:message code="label.transportTrailer.trailerFunction" text="Trailer Function" /></label>
						<select class="form-control"  name="glineType" ng-model="add.trailerFunctionId" >
                      	<option ng-repeat="key in trailerFunctionKeys" ng-value="key" ng-selected="add.trailerFunctionId==key">{{trailerFunctionKeyTypes[key]}}</option>        
                        </select>	
					</div>
					<div class="form-group">
                      <label><spring:message code='label.transportTrailer.trailerYear'  text='Year'/></label>                      
                      <select class="form-control" name="purchaseYear" ng-model="add.trailerYear" >
                            <option ng-repeat="i in purchaseYearArr" ng-value="i">{{i}}</option>                            
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.transportTrailer.trailerMake'  text='Make'/></label>                      
                      <input class="form-control" type="text" placeholder="<spring:message code='label.transportTrailer.trailerMake' text='Trailer Make' />" name="trailerMake" ng-model="add.trailerMake" maxlength="50"/ >
                    </div>
					<div class="modal-footer">
		            	<button class="btn btn-success btn-flat md-close"  ng-click="addTransportTrailer()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
						<button class="btn btn-warning btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
		        	</div>
		       </div>
	    </form>
	</div>