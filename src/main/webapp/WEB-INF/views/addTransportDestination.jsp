<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header md-effect-9">
	<div class="md-content">
		<div class="modal-header" >
			<h3><spring:message code="label.transportDestination.addTransportDestination" text="Add Transport Destination Data" /></h3>
			<button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
		</div>
		<form name="transportDestinationAddForm" novalidate angular-validator>
			<div class="modal-body form">
		        	<div class="form-group">
						<label><spring:message code="label.transportDestination.name" text="Name" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.transportDestination.name' text='Name' />" name="name1" ng-model="add.name" maxlength="30"  required required-message="'<spring:message code='label.transportDestination.nameRequired' text='Name is required' />'" />
					</div>
					<div class="form-group">
						<label><spring:message code="label.transportDestination.city" text="City" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.transportDestination.city' text='City' />" name="city" ng-model="add.city" maxlength="100"  required required-message="'<spring:message code='label.transportDestination.cityRequired' text='City is required' />'" />
					</div>
					<div class="form-group">
						<label><spring:message code="label.transportDestination.state" text="State" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='state' text='State' />" name="state" ng-model="add.state" maxlength="100"  required required-message="'<spring:message code='label.transportDestination.stateRequired' text='State is required' />'" />
					</div>
					<div class="form-group">
						<label><spring:message code="label.transportDestination.address" text="Address" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.transportDestination.address' text='Address' />" name="address" ng-model="add.address" maxlength="30"  required required-message="'<spring:message code='label.transportDestination.addressRequired' text='Address is required' />'" />
					</div>
					<div class="form-group">
						<label><spring:message code="label.transportDestination.marketType" text="Market Type" /><span style='color: red'>*</span></label>
						<select class="form-control"  name="marketType" ng-model="add.marketTypeId" >
                      	<option ng-repeat="key in marketTypeKeys" ng-value="key" ng-selected="add.marketType==key">{{marketTypeKeyValues[key]}}</option>        
                        </select>						
					</div>
					<div class="modal-footer">
		            	<button class="btn btn-primary btn-flat md-close"  ng-click="addTransportDestination()"><spring:message code="label.premise.add" text="Add" /></button>
						<button class="btn btn-default btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
		        	</div>
		       </div>
	    </form>
	</div>
</div>