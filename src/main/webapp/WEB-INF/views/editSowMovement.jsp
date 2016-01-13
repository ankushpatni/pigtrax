<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header warning md-effect-9">
	<div class="md-content">
<div class="modal-header">
	<h3><spring:message code="label.sowMovementForm.editSowData" text="Edit Sow Movement" /></h3>
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
    
</div>
<form name="editSowMovementForm" novalidate angular-validator>
<div class="modal-body form">			
			 <div class="form-group">
				<label><spring:message code="label.sowMovementForm.Premises" text="Premises" /><span style='color: red'>*</span></label>				
				 <select class="form-control" name="roomId" ng-model="premiseId"  ng-options="k as v for (k, v) in premisesMap" ng-change="getRooms()"></select>
			</div>
			<div class="form-group">
				<label><spring:message code="label.sowMovementForm.Room" text="Room" /></label>				
				 <select class="form-control" name="roomId" ng-model="roomId"  ng-options="k as v for (k, v) in roomMap"></select>
			</div>
			
			<div class="modal-footer">

          <button class="btn btn-success btn-flat md-close"  ng-click="editSowMovement()" ><spring:message code="label.premise.save" text="Save" /></button>
            <button class="btn btn-warning btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
       </div>
    </form>
</div>
</div>