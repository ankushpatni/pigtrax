<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="addFeedEventDetailModal" class="modal colored-header custom-width" ng-init="init()" ng-controller="AddFeedEventDetailController">
	<div class="md-content">
<div class="modal-header">
	<h3 ng-hide="edit"><spring:message code="label.feedEventDetail.add" text="Add Feed Event Detail" /></h3>
	<h3 ng-show="edit"><spring:message code="label.feedEventDetail.edit" text="Edit Feed Event Detail" /></h3>
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>    
</div>
<form name="feedEventDetailAddForm" novalidate angular-validator method="post">
<div class="modal-body form">
					<input type=hidden name="id" ng-model="feedEventDetail.id"/>
    				<div class="form-group">
						<label><spring:message code="label.feedEventDetail.feedEventId" text="Feed Event" /></label>
						<label>{{$rootScope.feedContentId}}</label>
					 </div>
					<div class="form-group">
						<label ><spring:message code="label.feedEventDetail.feedEventDate" text="Feed Event Date" /><span style='color: red'>*</span></label>
						<div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
							 <input size="16" type="date" id="feedEventDate" name="feedEventDate" ng-model="feedEventDetail.feedEventDate" readonly="" class="form-control" format-date required-message="'<spring:message code='label.feedEventDetail.feedEventDate.required' text='Feed Event Date is required' />'"/><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
						</div>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="feedEventDateFlag" ><spring:message code='label.feedEventDetail.feedEventDate.required' text='Feed Event Date is required' /></label>
					</div>
					<div class="form-group">
						<label><spring:message code="label.feedEventDetail.weightInKgs" text="Weight In Kgs" /></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.feedEventDetail.weightInKgs.placeHolder'  text='Enter Weight in KG'/>" name="weightInKgs" ng-model="feedEventDetail.weightInKgs" ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>	
					</div>
					<div class="form-group">
						<label><spring:message code="label.feedEventDetail.remarks" text="Remarks" /></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.feedEventDetail.remarks.placeHolder' text='Enter Remarks' />" name="remarks" ng-model="feedEventDetail.remarks" maxlength="255"/>
					</div>					
					
					<div class="form-group">
                      <label><spring:message code='label.feedEventDetail.siloId'  text='Silo'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="siloId" id="siloId" ng-model="feedEventDetail.siloId" required required-message="'<spring:message code='label.feedEventDetail.siloId.required' text='Silo is required' />'" 
                         ng-options="k as v for (k, v) in siloList">
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.feedEventDetail.feedEventTypeId'  text='Feed Event Type'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="feedEventTypeId" id="feedEventTypeId" ng-model="feedEventDetail.feedEventTypeId"  required required-message="'<spring:message code='label.feedEventDetail.feedEventTypeId.required' text='Feed Event Type is required' />'"  
                         ng-options="k as v for (k, v) in feedEventType">
                        </select>
                    </div>
                  
			<button class="btn btn-primary btn-flat md-close"  ng-click="addFeedEventDetail()" ng-hide="feedEventDetail.id>0"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-primary btn-flat md-close"  ng-click="addFeedEventDetail()" ng-show="feedEventDetail.id>0"><spring:message code="label.premise.edit" text="Edit" /></button>
            <button class="btn btn-default btn-flat md-close"  ng-click="gotoFeedEvent()"><spring:message code="label.premise.cancel" text="Cancel" /></button> 
         </div>
    </form>
</div>
</div>
