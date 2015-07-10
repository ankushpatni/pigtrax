<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="modal-header">
    <div class="cp-modal-close" ng-click="cancel()">X</div>
    <h3 class="modal-title" style="text-align: center; font-weight: bold;" ng-hide="edit"><spring:message code="label.premise.addPremiseData" text="Add Premise Data" /></h3>
	<h3 class="modal-title" style="text-align: center; font-weight: bold;" ng-show="edit"><spring:message code="label.premise.editPremiseData" text="Edit Premise Data" /></h3>
</div>
<div class="modal-body">
    <form name="premisesAddForm" novalidate angular-validator>
        <div class="cp-modal-body-cont">
              <div>
				<label class="cp-add-row-labal"><spring:message code="label.premise.premiseID" text="Premise ID" /><span style='color: red'>*</span></label>
				<label class="cp-add-row" ng-show="edit">{{add.permiseId}}</label>
				<label class=".cp-add-row-premise-companyId" ng-hide="edit">{{add.companyId}}</label>
				<input ng-hide="edit" class="cp-add-row-premise" type="text" placeholder="<spring:message code='label.premise.premiseID' text='Premise ID' />" name="premiseId" ng-model="add.permiseId" maxlength="6" size="5" required required-message="'<spring:message code='label.premise.premiseIDRequired' text='Premise Id is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.company.premiseIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
			</div>
			 <div>
				<label class="cp-add-row-labal"><spring:message code="label.premise.name" text="Name" /><span style='color: red'>*</span></label>
				<input class="cp-add-row" type="text" placeholder="<spring:message code='label.premise.name'  text='Name'/>" name="premiseName" ng-model="add.name" maxlength="50" required  required-message="'<spring:message code='label.premise.premiseNameRequired' text='Premise Name is required' />'"/>	
			</div>
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.premise.address" text="Address" /><span style='color: red'>*</span></label>
				<textarea class="cp-add-row" type="text" placeholder="<spring:message code='label.premise.address' text='Address' />" name="address" ng-model="add.address" maxlength="255" required required-message="'<spring:message code='label.premise.premiseAddressRequired' text='Premise address is required' />'" />
			</div>
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.premise.state" text="State" /><span style='color: red'>*</span></label>
				<Select class="cp-add-row" placeholder="<spring:message code='label.premise.state' text='State' />" name="state" ng-model="add.state" required required-message="'<spring:message code='label.premise.stateRequired' text='State is required' />'" 
				       ng-change="changeCity()"  ng-options="state.value as state.name for state in country"/>
			</div>			
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.premise.city" text="City" /><span style='color: red'>*</span></label>
				<select class="cp-add-row" placeholder="<spring:message code='label.premise.city' text='City' />" name="city" ng-model="add.city" maxlength="30" required required-message="'<spring:message code='label.premise.cityRequired' text='City is required' />'" 
					ng-options="city1.value as city1.name for city1 in city"/>
			</div>
			<div>
				<label class="cp-add-row-labal"><spring:message code="label.premise.zipcode" text="Zipcode" /><span style='color: red'>*</span></label>
				<input class="cp-add-row" type="text" placeholder="<spring:message code='label.premise.zipcode' text='Zipcode' />" name="zipcode" ng-model="add.zipcode" maxlength="9" required required-message="'<spring:message code='label.premise.zipcodeRequired' text='Zipcode address is required' />'" />
			</div>           
        </div>
        <div class="cp-modal-body-btns">

            <button class="btn cp-btn-warning" ng-click="addPremise()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn" ng-click="addPremise()" ng-show="edit"><spring:message code="label.premise.edit" text="Edit" /></button>
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