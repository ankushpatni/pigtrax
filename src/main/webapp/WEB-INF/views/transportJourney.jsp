<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="transportJourneyModal" class="modal colored-header custom-width" ng-init="init()" ng-controller="addTransportJourneyCtrl">
	<div class="md-content">
<div class="modal-header">
	<h3 ng-hide="edit"><spring:message code="label.transportJourney.add" text="Add Transport Journey" /></h3>
	<h3 ng-show="edit"><spring:message code="label.transportJourney.edit" text="Edit Transport Journey" /></h3>
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
    
</div>
<form name="transportJourneyForm" novalidate angular-validator> 
<div class="modal-body form">
    
              <div class="form-group">
				<label><spring:message code="label.transportJourney.trailerFunction" text="Trailer Function" /></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.transportJourney.trailerFunction' text='Trailer Function' />" name="trailerFunction" ng-model="transportJourney.trailerFunction" maxlength="20"  >
			</div>
			<div class="form-group">
                 <label><spring:message code='label.transportJourney.journeyStartTime'  text='Journey StartTime'/></label>
                 <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7">
                     <input size="16" type="date" id="journeyStartTime" name="journeyStartTime" ng-model="transportJourney.journeyStartTime" readonly="" class="form-control" format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
				</div>
            </div>
             <div class="form-group">
               <label><spring:message code='label.transportJourney.journeyEndTime'  text='Journey EndTime'/></label>
               <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7">
                   <input size="16" type="date" id="journeyEndTime" name="journeyEndTime" ng-model="transportJourney.journeyEndTime" readonly="" class="form-control" format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                 </div>
             </div> 
			<div class="form-group">
              <label><spring:message code='label.transportJourney.transportDestinationId'  text='Transport Destination'/></label>
               <select class="form-control"  name="transportDestinationId" id="transportDestinationId" ng-model="transportJourney.transportDestinationId"   
                 ng-options="k as v for (k, v) in transportDestination">
                </select>
            </div>
            <div class="form-group">
              <label><spring:message code='label.transportJourney.transportTruckId'  text='Transport Truck'/></label>
               <select class="form-control"  name="transportTruckId" id="transportTruckId" ng-model="transportJourney.transportTruckId"   
                 ng-options="k as v for (k, v) in transportTruck">
                </select>
            </div>
            <div class="form-group">
              <label><spring:message code='label.transportJourney.transportTrailerId'  text='Room'/></label>
               <select class="form-control"  name="transportTrailerId" id="transportTrailerId" ng-model="transportJourney.transportTrailerId"   
                 ng-options="k as v for (k, v) in transportTrailer">
                </select>
            </div>
			<div class="modal-footer">

            <button class="btn btn-primary btn-flat md-close" data-dismiss="modal" ng-click="addTransportJourney()"><spring:message code="label.premise.add" text="Add" /></button>
			<!--<button class="btn btn-primary btn-flat md-close" data-dismiss="modal" ng-click="addTransportJourney()" ng-show="edit"><spring:message code="label.premise.edit" text="Edit" /></button>-->
            <button class="btn btn-default btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
       </div>
    </form>
</div>
</div>
