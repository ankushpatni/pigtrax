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
				<label ng-show="edit">{{add.barnIdEdit}}</label>
				<label ng-hide="edit">{{add.premisesId}}</label>
				<input ng-hide="edit" class="form-control" type="text" placeholder="<spring:message code='label.barn.barnID' text='Barn ID' />" name="barnId" ng-model="add.barnId" maxlength="4" required required-message="'<spring:message code='label.barn.barnIDRequired' text='Barn Id is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.barn.barnIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.phaseTypeId" text="Phase Type ID" /><span style='color: red'>*</span></label>
				<select class="form-control" type="text" placeholder="<spring:message code='label.barn.phaseTypeId'  text='Phase Type ID'/>" name="phaseTypId" ng-model="add.phaseTypeId" required required-message="'<spring:message code='label.barn.phaseTypeIdRequired' text='Phase Type is required' />'" ng-options="k as v for (k, v) in phaseType"/>	
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.location" text="Location" /></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.barn.location' text='Location' />" name="location" ng-model="add.location" maxlength="30" required-message="'<spring:message code='label.barn.barnLocationRequired' text='Location is required' />'" />
			</div>
			<div class="form-group">
				<label ><spring:message code="label.barn.area" text="Area" /></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.barn.area' text='Area' />" name="area" ng-model="add.area" maxlength="18" required-message="'<spring:message code='label.barn.barnAreaRequired' text='Area is required' />'" ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>
			</div>			
			<div class="form-group">
				<label ><spring:message code="label.barn.feederCount" text="Feeder Count" /></label>
				<input class="form-control" placeholder="<spring:message code='label.barn.feederCount' text='Feeder Count' />" name="feederCount" ng-model="add.feederCount" maxlength="8" required-message="'<spring:message code='label.barn.feederCountRequired' text='Feeder Count is required' />'" ng-pattern="/^\d{1,8}?$/i" invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
			</div>
			<div class="form-group">
				<label ><spring:message code="label.barn.holesPerFeeder" text="Holes Per Feeder" /></label>
				<input class="form-control" placeholder="<spring:message code='label.barn.holesPerFeeder' text='Holes Per Feeder' />" name="holesPerFeeder" ng-model="add.holesPerFeeder" maxlength="8" ng-pattern="/^\d{1,8}?$/i" invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.waterAccessCount" text="Water Access Count" /></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.barn.waterAccessCount' text='Water Access Count' />" name="waterAccessCount" ng-model="add.waterAccessCount" maxlength="8" required-message="'<spring:message code='label.premise.waterAccessCountRequired' text='Water Access Count is required' />'" ng-pattern="/^\d{1,8}?$/i"  invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.ventilationTypeId" text="Ventilation Type ID" /></label>
				<select class="form-control" type="text" placeholder="<spring:message code='label.barn.ventilationTypeId'  text='Ventilation Type ID'/>" name="ventilationTypeId" ng-model="add.ventilationTypeId" required-message="'<spring:message code='label.barn.ventilationTypeIdRequired' text='Ventilation Type is required' />'" ng-options="k as v for (k, v) in validationType"/>	
			</div>	
			<div class="form-group">
				<label><spring:message code="label.barn.barnOrientationId" text="Barn Orientation Id" /></label>
				<select class="form-control" type="text"  name="barnOrientationId" ng-model="add.barnOrientationId" >
				<option value = 1><spring:message code="label.barn.barnOrientationId.northSouth" text="North - South" /></option>
				<option value = 2><spring:message code="label.barn.barnOrientationId.eastWest" text="East - West" /></option>
				</select>	
			</div>	
			
			<div class="form-group">
                      <label><spring:message code='label.barn.barnLocationId'  text='Barn Location'/></label>                      
					    <select class="form-control"  name="barnLocationId" ng-model="add.barnLocationId" >
                      	<option ng-repeat="key in barnLocationKeys" ng-value="key" ng-selected="add.barnLocationId==key">{{barnLocationKeyValues[key]}}</option>        
                        </select>
                    </div>
                    
                    
           <div class="form-group">
                  <label><spring:message code='label.barn.waterTypeId'  text='Water Type'/></label>                      
	    <select class="form-control"  name="waterTypeId" ng-model="add.waterTypeId" >
                  	<option ng-repeat="key in waterTypeKeys" ng-value="key" ng-selected="add.waterTypeId==key">{{waterTypeKeyValues[key]}}</option>        
                    </select>
             </div>
             
             
            <div class="form-group">
                  <label><spring:message code='label.barn.barnPositionId'  text='Barn Position'/></label>                      
	    			<select class="form-control"  name="barnPositionId" ng-model="add.barnPositionId" >
                  	<option ng-repeat="key in barnPositionKeys" ng-value="key" ng-selected="add.barnPositionId==key">{{barnPositionKeyValues[key]}}</option>        
                    </select>
             </div>
             
            <div class="form-group">
                  <label><spring:message code='label.barn.feederTypeId'  text='Feeder Type'/></label>                      
	    			<select class="form-control"  name="feederTypeId" ng-model="add.feederTypeId" >
                  	<option ng-repeat="key in feederTypeKeys" ng-value="key" ng-selected="add.feederTypeId==key">{{feederTypeKeyValues[key]}}</option>        
                    </select>
             </div>
			
			<div class="form-group">
                 <label><spring:message code='label.piginfo.entryeventform.remarks'  text='Remarks'/></label>
                 <textarea name="remarks" ng-model="add.remarks" class="form-control" placeholder="<spring:message code='label.piginfo.entryeventform.remarks' text='Remarks'/>" ></textarea>
               </div>
         <div class="modal-footer">

            <button class="btn btn-primary btn-flat md-close"  ng-click="addBarn()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-primary btn-flat md-close"  ng-click="addBarn()" ng-show="edit"><spring:message code="label.premise.save" text="Save" /></button>
            <button class="btn btn-default btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
       </div>
    </form>
</div>
