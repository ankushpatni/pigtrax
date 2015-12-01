<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header md-effect-9">
	<div class="md-content">
<div class="modal-header">
	<h3 ng-hide="edit"><spring:message code="label.pen.addpenData" text="Add Pen Data" /></h3>
	<h3 ng-show="edit"><spring:message code="label.pen.editpenData" text="Edit Room Data" /></h3>
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
    
</div>
<form name="penAddForm" novalidate angular-validator>
<div class="modal-body form">
    
              <div class="form-group">
				<label><spring:message code="label.pen.penID" text="Pen ID" /><span style='color: red'>*</span></label>
				<label ng-show="edit">{{add.penIdEdit}}</label>
				<label ng-hide="edit">{{add.roomId}}</label>
				<input ng-hide="edit" class="form-control" type="text" placeholder="<spring:message code='label.pen.penID' text='Pen ID' />" name="penId" ng-model="add.penId" maxlength="4"  required required-message="'<spring:message code='label.pen.penIDRequired' text='Pen Id is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.room.roomIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
			</div>
			<div class="form-group">
				<label><spring:message code="label.pen.location" text="Pen Position" /></label>				
				<select name="location" ng-model="add.location" class="form-control" >
				<option value="doorway"><spring:message code="label.pen.location.doorway" text="Doorway" /></option>
				<option value="interior"><spring:message code="label.pen.location.interior" text="Interior" /></option>
				<option value="exterior"><spring:message code="label.pen.location.exterior" text="Exterior" /></option>
				</select>
			</div>
			<div class="modal-footer">

            <button class="btn btn-success btn-flat md-close"  ng-click="addPen()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-success btn-flat md-close"  ng-click="addPen()" ng-show="edit"><spring:message code="label.premise.save" text="Save" /></button>
            <button class="btn btn-warning btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
       </div>
    </form>
</div>
</div>