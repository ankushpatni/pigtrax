<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header md-effect-9">
	<div class="md-content">
<div class="modal-header">
<h3  ng-hide="edit"><spring:message code="label.premise.addPremiseData" text="Add Premise Data" /></h3>
	<h3  ng-show="edit"><spring:message code="label.premise.editPremiseData" text="Edit Premise Data" /></h3>
   <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
    
</div>
<form name="premisesAddForm" novalidate angular-validator>
	<div class="modal-body form">
    		<div class="form-group">
				<label><spring:message code="label.premise.premiseID" text="Premise ID" /><span style='color: red'>*</span></label>
				<label ng-show="edit">{{add.permiseIdEdit}}</label>
				<label ng-hide="edit">{{add.companyId}}</label>
				<input ng-hide="edit" class="form-control" type="text" placeholder="<spring:message code='label.premise.premiseID' text='Premise ID' />" name="premiseId" ng-model="add.permiseId" maxlength="2" required required-message="'<spring:message code='label.premise.premiseIDRequired' text='Premise Id is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.company.premiseIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
			</div>
			 <div class="form-group">
				<label><spring:message code="label.premise.name" text="Name" /><span style='color: red'>*</span></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.premise.name'  text='Name'/>" name="premiseName" ng-model="add.name" maxlength="50" required  required-message="'<spring:message code='label.premise.premiseNameRequired' text='Premise Name is required' />'"/>	
			</div>
			<div class="form-group">
				<label><spring:message code="label.premise.address" text="Address" /><span style='color: red'>*</span></label>
				<textarea class="form-control" type="text" placeholder="<spring:message code='label.premise.address' text='Address' />" name="address" ng-model="add.address" maxlength="255" required required-message="'<spring:message code='label.premise.premiseAddressRequired' text='Premise address is required' />'" />
			</div>
			<div class="form-group">
				<label><spring:message code="label.premise.state" text="State" /><span style='color: red'>*</span></label>
				<Select class="form-control" placeholder="<spring:message code='label.premise.state' text='State' />" name="state" ng-model="add.state" required required-message="'<spring:message code='label.premise.stateRequired' text='State is required' />'" 
				       ng-change="changeCity()"  ng-options="state.value as state.name for state in country"/>
			</div>			
			<div class="form-group">
				<label><spring:message code="label.premise.city" text="City" /><span style='color: red'>*</span></label>
				<select class="form-control" placeholder="<spring:message code='label.premise.city' text='City' />" name="city" ng-model="add.city" maxlength="30" required required-message="'<spring:message code='label.premise.cityRequired' text='City is required' />'" 
					ng-options="city1.value as city1.name for city1 in city"/>
			</div>
			<div class="form-group">
				<label><spring:message code="label.premise.zipcode" text="Zipcode" /><span style='color: red'>*</span></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.premise.zipcode' text='Zipcode' />" name="zipcode" ng-model="add.zipcode" maxlength="9" required required-message="'<spring:message code='label.premise.zipcodeRequired' text='Zipcode address is required' />'" />
			</div>  
        <div class="modal-footer">

            <button class="btn btn-primary btn-flat md-close" ng-click="addPremise()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-primary btn-flat md-close" ng-click="addPremise()" ng-show="edit"><spring:message code="label.premise.edit" text="Edit" /></button>
            <button class="btn btn-default btn-flat md-close" ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
            <p style="color:red">{{alertMessage}}</p>
        </div>
       </div>
    </form>
</div>
</div>