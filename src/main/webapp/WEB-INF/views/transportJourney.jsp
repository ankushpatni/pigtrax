<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="transportJourneyModal" class="md-modal colored-header warning md-effect-9" ng-init="init()">
	<div class="md-content">
<div class="modal-header">
	<h3 ng-hide="edit"><spring:message code="label.transportJourney.add" text="Add Transport Journey" /></h3>
	<h3 ng-show="edit"><spring:message code="label.transportJourney.edit" text="Edit Transport Journey" /></h3>
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
    
</div>
<form name="transportJourneyForm" novalidate angular-validator> 
<div class="modal-body form">
    		<input class="form-control" type="hidden" placeholder="<spring:message code='label.transportJourney.trailerFunction' text='Trailer Function' />" name="trailerFunction" ng-model="transportJourney.trailerFunction" maxlength="20"  >            
			<input type="hidden"  id="journeyStartTime" name="journeyStartTime" ng-model="transportJourney.journeyStartTime"  />
            <input type="hidden"  id="journeyEndTime" name="journeyEndTime" ng-model="transportJourney.journeyEndTime"  />
            <input type="hidden"  id="journeyEndTime" name="journeyEndTime" ng-model="transportJourney.transportDestinationId"  />			
            <div class="form-group">
              <label><spring:message code='label.transportJourney.transportTruckId'  text='Transport Truck'/></label>
               <select class="form-control"  name="transportTruckId" id="transportTruckId" ng-model="transportJourney.transportTruckId"   
                 ng-options="k as v for (k, v) in transportTruck">
                </select>
            </div>
            <div class="form-group">
              <label><spring:message code='label.transportJourney.transportTrailerId'  text='Transport Trailer'/></label>
               <select class="form-control"  name="transportTrailerId" id="transportTrailerId" ng-model="transportJourney.transportTrailerId"   
                 ng-options="k as v for (k, v) in transportTrailer">
                </select>
            </div>
			<div class="modal-footer">

            <button class="btn btn-success btn-flat md-close" data-dismiss="modal" ng-click="addTransportJourney()"><spring:message code="label.premise.add" text="Add" /></button>
			<!--<button class="btn btn-success btn-flat md-close" data-dismiss="modal" ng-click="addTransportJourney()" ng-show="edit"><spring:message code="label.premise.edit" text="Edit" /></button>-->
            <button class="btn btn-warning btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
       </div>
    </form>
</div>
</div>
