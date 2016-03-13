<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="addFeedEventDetailModal" class="md-modal colored-header warning md-effect-9" ng-init="init()">
	<div class="md-content">
<div class="modal-header">
	<h3 ng-hide="edit"><spring:message code="label.feedEventDetail.add" text="Add Feed Event Detail" /></h3>
	<h3 ng-show="edit"><spring:message code="label.feedEventDetail.edit" text="Edit Feed Event Detail" /></h3>
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>    
</div>
<form name="feedEventDetailAddForm" novalidate angular-validator method="post">
<div class="modal-body form">
 				  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
				   <div class="alert alert-danger alert-white rounded"  ng-show="groupStartDateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.feedEventform.submit.groupDateError.message' text='Feed Event Date can not be less than Group Event Start Date'/>
                  </div>
				<div class="alert alert-danger alert-white rounded"  ng-show="groupEndDateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.feedEventform.submit.groupDateError.message' text='Feed Event Date can not be more than Group Event End Date'/>
                  </div>				  
					<input type=hidden name="id" ng-model="feedEventDetail.id"/>
    				<div class="form-group">
						<label><spring:message code="label.feedEventDetail.feedEventId" text="Feed Event" /></label>
						<label>{{feedEventId}}</label>
					 </div>
					 
					  <div class="form-group">
						<label><spring:message code="label.feedEventDetail.groupEventId" text="Group Event" /><span style='color: red'>*</span></label>
						 <select class="form-control"  name="groupEventId" id="groupEventId" ng-model="feedEventDetail.groupEventId"  required required-message="'<spring:message code='label.feedEventDetail.groupEventId.required' text='Group Event is required' />'"  
                         ng-options="k as v.groupId for (k, v) in groupEvent">
                        </select>
					</div>
					 
					<div class="form-group">
						<label ><spring:message code="label.feedEventDetail.feedEventDate" text="Feed Event Date" /><span style='color: red'>*</span></label><i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>						                    
						<input type="text" class="form-control" ng-model="feedEventDetail.feedEventDateStr" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(feedEventDetail.feedEventDateStr, 'feedEventDate')"/>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="feedEventDateFlag" ><spring:message code='label.feedEventDetail.feedEventDate.required' text='Feed Event Date is required' /></label>
					</div>
					
					
					<div class="form-group">
                      <label><spring:message code='label.feedEventDetail.feedmill'  text='Feed Mill'/></label>
                       <input type="text" class="form-control" ng-model="feedEventDetail.feedMill" maxlength = "30" placeholder="<spring:message code='label.feedEventDetail.feedmill.placeHolder'  text='Enter feed mill'/>"/>
                    </div>
					
					<div class="form-group">
                      <label><spring:message code='label.feedEventDetail.feedEventTypeId'  text='Feed Event Type'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="feedEventTypeId" id="feedEventTypeId" ng-model="feedEventDetail.feedEventTypeId"  required required-message="'<spring:message code='label.feedEventDetail.feedEventTypeId.required' text='Feed Event Type is required' />'"  
                         ng-options="k as v for (k, v) in feedEventType">
                        </select>
                    </div>
                   
					<div class="form-group">
						<label><spring:message code="label.feedEventDetail.weightInKgs" text="Weight" /></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.feedEventDetail.weightInKgs.placeHolder'  text='Enter Weight'/>" name="weightInKgs" ng-model="feedEventDetail.weightInKgs" ng-pattern="/^[-]?[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>	
					</div>
					
					<div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.feedCost'  text='Feed Cost'/></label>
                     <input class="form-control" type="text" placeholder="<spring:message code='label.piginfo.feedEventForm.feedCost.placeHolder' text='Enter Feed Cost' />" 
                     	name="feedCost" ng-model="feedEventDetail.feedCost" maxlength="20" required-message="'<spring:message code='label.piginfo.feedCost.feedCost.requiredMessage' text='Feed Cost required' />'" 
                     	 ng-pattern="/^[-]?[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>
                   </div>
					
					<div class="form-group">
                      <label><spring:message code='label.feedEventDetail.siloId'  text='Silo'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="siloId" id="siloId" ng-model="feedEventDetail.siloId" required required-message="'<spring:message code='label.feedEventDetail.siloId.required' text='Silo is required' />'" 
                         ng-options="k as v for (k, v) in siloList">
                        </select>
                    </div>
                    <div class="form-group">
						<label><spring:message code="label.feedEventDetail.remarks" text="Remarks" /></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.feedEventDetail.remarks.placeHolder' text='Enter Remarks' />" name="remarks" ng-model="feedEventDetail.remarks" maxlength="255"/>
					</div>					
					
                  
			<button class="btn btn-success btn-flat md-close"  ng-click="addFeedEventDetail()" ng-hide="feedEventDetail.id>0"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-success btn-flat md-close"  ng-click="addFeedEventDetail()" ng-show="feedEventDetail.id>0"><spring:message code="label.piginfo.groupEventform.edit" text="Edit" /></button>
            <button class="btn btn-warning btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button> 
         </div>
		 <input type="hidden" name="feedEventTicketNumber" id="feedEventTicketNumber"/>
		 <input type="hidden" name="selectedCompany" id="selectedCompany"/>
    </form>
</div>
</div>
