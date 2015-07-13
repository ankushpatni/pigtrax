<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="modal-header">
    <div class="cp-modal-close" ng-click="cancel()">X</div>
    <h3 class="modal-title" style="text-align: center; font-weight: bold;" ng-hide="edit"><spring:message code="label.barn.addBarnData" text="Add Barn Data" /></h3>
	<h3 class="modal-title" style="text-align: center; font-weight: bold;" ng-show="edit"><spring:message code="label.barn.editBarnData" text="Edit Barn Data" /></h3>
</div>
<div class="modal-body">
    <form name="barnAddForm" novalidate angular-validator>
        <div class="cp-modal-body-cont">
              <div>
				<label class="cp-add-row-labal"><spring:message code="label.barn.barnID" text="Barn ID" /><span style='color: red'>*</span></label>
				<label class="cp-add-row" ng-show="edit">{{add.barnId}}</label>
				<label class="cp-add-row-barn-premise-companyId" ng-hide="edit">{{add.premisesId}}</label>
				<input ng-hide="edit" class="cp-add-row-barn" type="text" placeholder="<spring:message code='label.barn.barnID' text='Barn ID' />" name="barnId" ng-model="add.barnId" maxlength="10" required required-message="'<spring:message code='label.barn.barnIDRequired' text='Barn Id is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.barn.barnIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
			</div>
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.barn.phaseTypeId" text="Phase Type ID" /><span style='color: red'>*</span></label>
				<select class="cp-add-row" type="text" placeholder="<spring:message code='label.barn.phaseTypeId'  text='Phase Type ID'/>" name="phaseTypId" ng-model="add.phaseTypeId" required required-message="'<spring:message code='label.barn.phaseTypeIdRequired' text='Phase Type is required' />'" ng-options="k as v for (k, v) in phaseType"/>	
			</div>
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.barn.location" text="Location" /><span style='color: red'>*</span></label>
				<input class="cp-add-row" type="text" placeholder="<spring:message code='label.barn.location' text='Location' />" name="location" ng-model="add.location" maxlength="30" required required-message="'<spring:message code='label.barn.barnLocationRequired' text='Location is required' />'" />
			</div>
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.barn.area" text="Area" /><span style='color: red'>*</span></label>
				<input class="cp-add-row" type="text" placeholder="<spring:message code='label.barn.area' text='Area' />" name="area" ng-model="add.area" maxlength="8" required required-message="'<spring:message code='label.barn.barnAreaRequired' text='Area is required' />'" />
			</div>			
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.barn.feederCount" text="Feeder Count" /><span style='color: red'>*</span></label>
				<input class="cp-add-row" placeholder="<spring:message code='label.barn.feederCount' text='Feeder Count' />" name="feederCount" ng-model="add.feederCount" maxlength="8" required required-message="'<spring:message code='label.barn.feederCountRequired' text='Feeder Count is required' />'"/>
			</div>
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.barn.waterAccessCount" text="Water Access Count" /><span style='color: red'>*</span></label>
				<input class="cp-add-row" type="text" placeholder="<spring:message code='label.barn.waterAccessCount' text='Water Access Count' />" name="waterAccessCount" ng-model="add.waterAccessCount" maxlength="8" required required-message="'<spring:message code='label.premise.waterAccessCountRequired' text='Water Access Count is required' />'" />
			</div>
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.barn.ventilationTypeId" text="Ventilation Type ID" /><span style='color: red'>*</span></label>
				<select class="cp-add-row" type="text" placeholder="<spring:message code='label.barn.ventilationTypeId'  text='Ventilation Type ID'/>" name="ventilationTypeId" ng-model="add.ventilationTypeId" required required-message="'<spring:message code='label.barn.ventilationTypeIdRequired' text='Ventilation Type is required' />'" ng-options="k as v for (k, v) in validationType"/>	
			</div>			
        </div>
        <div class="cp-modal-body-btns">

            <button class="btn cp-btn-warning" ng-click="addBarn()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn" ng-click="addBarn()" ng-show="edit"><spring:message code="label.premise.edit" text="Edit" /></button>
            <button class="btn" ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
        <!-- <div class="cp-modal-body-bottom">
            <input type="checkbox" style="margin: 0 6px 0 0; float: left;">Remember me</input>
            <a style="float: right;" ng-click="forgotPass()">Forgot Your Password?</a>
        </div> -->
    </form>
</div>
<div class="modal-footer" ng-show="alertVisible">
    <p style="color:red">{{alertMessage}}</p>
</div>