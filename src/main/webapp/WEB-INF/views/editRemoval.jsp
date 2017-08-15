<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header warning md-effect-9">
	<div class="md-content">
<div class="modal-header">
<h3  ng-hide="edit"><spring:message code="" text="Edit Pig Movement" /></h3>
<%-- 	<h3  ng-show="edit"><spring:message code="label.premise.editPremiseData" text="Edit Premise Data" /></h3> --%>
   <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
    
</div>
<form name="removalDataForm" novalidate angular-validator>
	<div class="modal-body form">
		
		                    
                   
				   <div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.numberOfPigs'  text='Number Of Pigs'/></label>
                      <label ng-show="selectGroup==='pigInfo'">1</label>
                      <input type="text" ng-model="removalExceptSales.numberOfPigs" id="numberOfPigs" name="numberOfPigs"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.removalExceptSales.numberOfPigs.placeholder'  text='Enter Number Of Pigs'/>" 
                      required-message="'<spring:message code='label.piginfo.removalExceptSales.numberOfPigs.requiredMessage' text='Number Of Pigs are required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.groupEventForm.groupId.invalidMessage' text='Only Numeric values are allowed' />'"  ng-focus="clearMessages()"/>
				   </div>
                   <div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="noOfPigsrequired" ><spring:message code='label.piginfo.removalExceptSales.numberOfPigs.requiredMessage' text='No. Of pigs are required.' /></label>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="noOfPigWrongCount" ><spring:message code='label.piginfo.removalExceptSales.numberOfPigs.wrongCount' text='No. Of pigs can not be greater than Group event Pigs.' /></label>
					</div>
                    
	 				<div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.weightInKgs'  text='Weight'/><span style='color: red'>*</span></label>
                     <input class="form-control" type="text"  placeholder="<spring:message code='label.piginfo.removalExceptSales.weightInKgs.placeHolder' text='Enter Weight' />" 
                     	name="weightInKgs" ng-model="removalExceptSales.weightInKgs" maxlength="20" required required-message="'<spring:message code='label.piginfo.removalExceptSales.weightInKgs.requiredMessage' text='Weight is required' />'" 
                     	 ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>
                   </div>
                 
                 
<!-- 				<div class="form-group"> -->
<%--                       <label ng-show="removalTypeId != 9"><spring:message code='label.piginfo.removalExceptSales.removalDateTime'  text='Removal Date'/><span style='color: red'>*</span></label> --%>
<%--                       <label ng-show="removalTypeId == 9"><spring:message code='label.piginfo.removalExceptSales.transferDateTime'  text='Transfer Date'/><span style='color: red'>*</span></label> --%>
<%-- 	                      <i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i> --%>
<!-- 	                      <input rsmdatedropdowns ng-model="removalExceptSales.removalDateTime" day-div-class="day-container" day-class="day-selector" starting-year="2030" num-years="30"/>  -->
<!-- 	                     <input type="text" class="form-control" ng-model="entryDateStr" mask="39/19/2999" ng-blur="dateCheck(entryDateStr)"/> -->
	                      
<!-- 			              </div> -->
                  
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></label>
                      <input type="text" ng-model="removalExceptSales.remarks" id="remarks" name="remarks"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/>
                   </div>                    				
			
        <div class="modal-footer">

            <button class="btn btn-success btn-flat md-close" ng-click="updateRomvalData()" ><spring:message code="" text="Save" /></button>
            <button class="btn btn-warning btn-flat md-close" ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
            <p style="color:red">{{alertMessage}}</p>
        </div>
       </div>
    </form>
</div>
</div>