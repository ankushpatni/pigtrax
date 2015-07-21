<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header md-effect-9">
	<div class="md-content">
<div class="modal-header">
	<h3 ng-hide="edit"><spring:message code="label.room.addRoomData" text="Add Room Data" /></h3>
	<h3 ng-show="edit"><spring:message code="label.room.editRoomData" text="Edit Room Data" /></h3>
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">�</button>
    
</div>
<form name="roomAddForm" novalidate angular-validator>
<div class="modal-body form">
    
              <div class="form-group">
				<label><spring:message code="label.room.roomID" text="Barn ID" /><span style='color: red'>*</span></label>
				<label ng-show="edit">{{add.roomId}}</label>
				<label ng-hide="edit">{{add.barnId}}</label>
				<input ng-hide="edit" class="form-control" type="text" placeholder="<spring:message code='label.room.roomID' text='Room ID' />" name="roomId" ng-model="add.roomId" maxlength="10" required required-message="'<spring:message code='label.room.roomIDRequired' text='Room Id is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.room.roomIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.location" text="Location" /><span style='color: red'>*</span></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.barn.location' text='Location' />" name="location" ng-model="add.location" maxlength="30" required required-message="'<spring:message code='label.barn.barnLocationRequired' text='Location is required' />'" />
			</div>
			<div class="modal-footer">

            <button class="btn btn-primary btn-flat md-close"  ng-click="addRoom()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-primary btn-flat md-close"  ng-click="addRoom()" ng-show="edit"><spring:message code="label.premise.edit" text="Edit" /></button>
            <button class="btn btn-default btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
       </div>
    </form>
</div>
</div>