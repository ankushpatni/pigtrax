<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header warning md-effect-9">
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
				<label ng-show="edit">{{add.roomIdEdit}}</label>
				<label ng-hide="edit">{{add.barnId}}</label>
				<input ng-hide="edit" class="form-control" type="text" placeholder="<spring:message code='label.room.roomID' text='Room ID' />" 
				name="roomId" ng-model="add.roomId" maxlength="4" required required-message="'<spring:message code='label.room.roomIDRequired' text='Room Id is required' />'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'<spring:message code='label.room.roomIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
			</div>			
			<div class="form-group">
				<label><spring:message code="label.barn.roomPosition" text="Room Position" /></label>
				<select class="form-control" type="text"  name="roomPosition" ng-model="add.roomPositionId" >
				<option value="1"><spring:message code="label.barn.roomPosition.center" text="Center" /></option>
				<option value="2"><spring:message code="label.barn.roomPosition.inside" text="Inside" /></option>
				<option value="3"><spring:message code="label.barn.roomPosition.outside" text="Outside" /></option>
				</select>
			</div>
			<div class="form-group">
				<label><spring:message code="label.barn.floorType" text="Floor Type" /></label>
				<select class="form-control" type="text"  name="floorType" ng-model="add.floorTypeId" >
				<option value="1"><spring:message code="" text="Cast Plastic" /></option>
				<option value="2"><spring:message code="" text="Full Slat" /></option>
				<option value="3"><spring:message code="" text="Narrow Slat" /></option>
				<option value="4"><spring:message code="" text="Open Pit" /></option>
				<option value="5"><spring:message code="" text="Outside Dirt" /></option>
				<option value="6"><spring:message code="" text="Partial Slat" /></option>
				<option value="7"><spring:message code="" text="Plastic" /></option>
				<option value="8"><spring:message code="" text="Solid Cement" /></option>
				<option value="9"><spring:message code="" text="Straw" /></option>
				<option value="10"><spring:message code="" text="Tenderfoot" /></option>
				<option value="11"><spring:message code="" text="Tribar" /></option>
				<option value="12"><spring:message code="" text="Woven Wire" /></option>
				</select>
			</div>
			 <div class="form-group">
				<label><spring:message code="label.room.pigSpaces" text="Pig Spaces" /><span style='color: red'>*</span></label>				
				<input class="form-control" type="text" placeholder="<spring:message code='label.room.pigSpaces' text='Pig Spaces' />" 
				name="pigSpaces" ng-model="add.pigSpaces" maxlength="6"   ng-pattern="/^[0-9]{1,6}?$/i" 
				invalid-message="'<spring:message code='label.room.pigSpaceInvalid' text='Only whole numbers are accepted'/>'" 
				 required required-message="'<spring:message code='label.room.pigSpaces.requiredMessage' text='Pig spaces is required' />'"/>
			</div>
			<div class="modal-footer">

            <button class="btn btn-success btn-flat md-close"  ng-click="addRoom()" ng-hide="edit"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-success btn-flat md-close"  ng-click="addRoom()" ng-show="edit"><spring:message code="label.premise.save" text="Save" /></button>
            <button class="btn btn-warning btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
       </div>
    </form>
</div>
</div>