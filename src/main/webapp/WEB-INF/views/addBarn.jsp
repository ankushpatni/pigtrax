<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header md-effect-9">
	<div class="md-content">
<div class="modal-header">
	<h3 ng-hide="edit"><spring:message code="label.barn.addBarnData" text="Add Barn Data" /></h3>
	<h3 ng-show="edit"><spring:message code="label.barn.editBarnData" text="Edit Barn Data" /></h3>
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
    
</div>
<form name="barnAddForm" novalidate angular-validator>
<div class="modal-body form">
    
              <div class="form-group">
				<label><spring:message code="label.barn.barnID" text="Barn ID" /><span style='color: red'>*</span></label>
				<label ng-show="edit">{{add.barnId}}</label>
				<label ng-hide="edit">{{add.premisesId}}</label>
				<input ng-hide="edit" class="form-control" type="text" placeholder="<spring:message code='label.barn.barnID' text='Barn ID' />" name="barnId" ng-model="add.barnId" maxlength="4" required required-message="'<spring:message code='label.barn.barnIDRequired' text='Barn Id is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.barn.barnIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.phaseTypeId" text="Phase Type ID" /><span style='color: red'>*</span></label>
				<select class="form-control" type="text" placeholder="<spring:message code='label.barn.phaseTypeId'  text='Phase Type ID'/>" name="phaseTypId" ng-model="add.phaseTypeId" required required-message="'<spring:message code='label.barn.phaseTypeIdRequired' text='Phase Type is required' />'" ng-options="k as v for (k, v) in phaseType"/>	
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.location" text="Location" /><span style='color: red'>*</span></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.barn.location' text='Location' />" name="location" ng-model="add.location" maxlength="30" required required-message="'<spring:message code='label.barn.barnLocationRequired' text='Location is required' />'" />
			</div>
			<div class="form-group">
				<label ><spring:message code="label.barn.area" text="Area" /><span style='color: red'>*</span></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.barn.area' text='Area' />" name="area" ng-model="add.area" maxlength="8" required required-message="'<spring:message code='label.barn.barnAreaRequired' text='Area is required' />'" ng-pattern="/^[0-9]{1,15}(\.[0-9]+)?$/i" invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
			</div>			
			<div class="form-group">
				<label ><spring:message code="label.barn.feederCount" text="Feeder Count" /><span style='color: red'>*</span></label>
				<input class="form-control" placeholder="<spring:message code='label.barn.feederCount' text='Feeder Count' />" name="feederCount" ng-model="add.feederCount" maxlength="8" required required-message="'<spring:message code='label.barn.feederCountRequired' text='Feeder Count is required' />'" ng-pattern="/^\d{1,8}?$/i" invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.waterAccessCount" text="Water Access Count" /><span style='color: red'>*</span></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.barn.waterAccessCount' text='Water Access Count' />" name="waterAccessCount" ng-model="add.waterAccessCount" maxlength="8" required required-message="'<spring:message code='label.premise.waterAccessCountRequired' text='Water Access Count is required' />'" ng-pattern="/^\d{1,8}?$/i"  invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.ventilationTypeId" text="Ventilation Type ID" /><span style='color: red'>*</span></label>
				<select class="form-control" type="text" placeholder="<spring:message code='label.barn.ventilationTypeId'  text='Ventilation Type ID'/>" name="ventilationTypeId" ng-model="add.ventilationTypeId" required required-message="'<spring:message code='label.barn.ventilationTypeIdRequired' text='Ventilation Type is required' />'" ng-options="k as v for (k, v) in validationType"/>	
			</div>	
         <div class="modal-footer">

            <button class="btn btn-primary btn-flat md-close"  ng-click="addBarn()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-primary btn-flat md-close"  ng-click="addBarn()" ng-show="edit"><spring:message code="label.premise.edit" text="Edit" /></button>
            <button class="btn btn-default btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
       </div>
    </form>
</div>
</div>