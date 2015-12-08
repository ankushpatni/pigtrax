<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header warning md-effect-9">
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
				<input ng-hide="edit" class="form-control" type="text" placeholder="<spring:message code='label.premise.premiseID' text='Premise ID' />" name="premiseId" ng-model="add.permiseId" maxlength="4" required required-message="'<spring:message code='label.premise.premiseIDRequired' text='Premise Id is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.company.premiseIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
			</div>
			 <div class="form-group">
				<label><spring:message code="label.premise.name" text="Name" /><span style='color: red'>*</span></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.premise.name'  text='Name'/>" name="premiseName" ng-model="add.name" maxlength="50" required  required-message="'<spring:message code='label.premise.premiseNameRequired' text='Premise Name is required' />'"/>	
			</div>
			
			<div class="form-group">
                <label><spring:message code='label.premise.premiseType'  text='Premise Type'/></label>                      
   				<select class="form-control"  name="premiseTypeId" ng-model="add.premiseTypeId" >
                	<option ng-repeat="key in premiseTypeKeys" ng-value="key" ng-selected="add.premiseTypeId==key">{{premiseTypeKeyValues[key]}}</option>        
                  </select>
             </div>
			
			<div class="form-group">
				<label><spring:message code="label.premise.address" text="Address" /></label>
				<textarea class="form-control" type="text" placeholder="<spring:message code='label.premise.address' text='Address' />" name="address" ng-model="add.address" maxlength="255" required-message="'<spring:message code='label.premise.premiseAddressRequired' text='Premise address is required' />'" />
			</div>
			<div class="form-group">
				<label><spring:message code="label.company.country" text="Country" /></label>
				<Select class="form-control" placeholder="<spring:message code='label.company.country' text='Country' />" name="state" ng-model="add.state" required-message="'<spring:message code='label.company.countryRequired' text='Country is required' />'" 
				       ng-change="changeCity()"  ng-options="state.value as state.name for state in country"/>
			</div>			
			<div class="form-group">
				<label><spring:message code="label.premise.city" text="City" /></label>
				<select class="form-control" placeholder="<spring:message code='label.premise.city' text='City' />" name="city" ng-model="add.city" maxlength="30"  required-message="'<spring:message code='label.premise.cityRequired' text='City is required' />'" 
					ng-options="city1.value as city1.name for city1 in city"/>
			</div>
			<div class="form-group">
				<label><spring:message code="label.premise.zipcode" text="Zipcode" /></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.premise.zipcode' text='Zipcode' />" name="zipcode" ng-model="add.zipcode" maxlength="9" required-message="'<spring:message code='label.premise.zipcodeRequired' text='Zipcode address is required' />'" />
			</div>  
			 <div class="form-group">
				<label><spring:message code="label.premise.gpslatittude" text="GPS Latittude" /></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.premise.gpslatittude'  text='GPS Latittude'/>" name="gpsLatittude" ng-model="add.gpsLatittude" maxlength="30"/>	
			</div>
			<div class="form-group">
				<label><spring:message code="label.premise.gpslongitude" text="GPS Longitude" /></label>
				<input class="form-control" type="text" placeholder="<spring:message code='gpslongitude'  text='GPS Longitude'/>" name="gpsLongitude" ng-model="add.gpsLongitude" maxlength="30"/>	
			</div>
			<div class="form-group">
				<label><spring:message code="label.premise.sowSource" text="Sow Source" /></label> 
				<div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1"  value="Yes" ng-model="add.sowSource" ng-checked="add.sowSource == 'Yes'"><spring:message code="label.premise.sowSourceYes" text="Yes" />
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad2"  id="rad2" value="No"  ng-model="add.sowSource"   ng-checked="add.sowSource == 'No'"> <spring:message code="label.premise.sowSourceNo" text="No" /> 
                </label>
              </div>
			</div>
			
        <div class="modal-footer">

            <button class="btn btn-success btn-flat md-close" ng-click="addPremise()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-success btn-flat md-close" ng-click="addPremise()" ng-show="edit"><spring:message code="label.premise.save" text="Save" /></button>
            <button class="btn btn-warning btn-flat md-close" ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
            <p style="color:red">{{alertMessage}}</p>
        </div>
       </div>
    </form>
</div>
</div>