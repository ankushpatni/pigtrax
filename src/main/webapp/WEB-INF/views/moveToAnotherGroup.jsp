<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<div id="form-primary" 	class="md-modal colored-header warning md-effect-9">
	<div class="md-content">
<div class="modal-header">
	<h3 ng-hide="edit"><spring:message code="label.groupEventDetail.moveGroupEvent" text="Move Group Event" /></h3>
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
</div>
<form name="moveGroupeventAddForm" novalidate angular-validator>
<div class="modal-body form">
				 <div class="alert alert-danger alert-white rounded"  ng-show="groupEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.groupEventform..duplicate.error.message' text='A Group event record already exists with the same Group Id'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded"  ng-show="closeGroupParentError">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.groupEventform.closeGroupParentError' text='Pigs can not be transformed to close group.'/>
                  </div>
				  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
				  <div>
						<h5 style="display:inline"> <spring:message code="label.groupEventDetail.addToExistingGroup" text="Move to an existing Group" /></h5>
						<input style="margin-left: 10px;display:inline;size:10px" type="checkbox" name="searchExisting" class="icheck" ng-model="groupEvent.searchExisting" ng-click="clearForm()"></input>						
					</div>
					<div class="col-sm-12 col-md-12" ng-show="groupEvent.searchExisting">
					 <div style="margin-bottom: 30px;">
						<div class="head">
								<h5> <spring:message code='label.piginfo.groupEventForm.groupId.search'  text='Search Existing Group'/></h5>
								<div  class="form-group">
									<select class="form-control" name="premiseId" ng-model="premiseId"  ng-options="k as v for (k, v) in premisesMap"></select>		             			
								</div>
								<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.groupEventForm.search.data.errormessage' text='Group event information not found for the search criteria'/></p>
								 <input type="text" name="search" ng-model="searchText" ng-pattern="/^[a-z0-9]+$/i"
							invalid-message="'<spring:message code='label.piginfo.groupEventForm.groupId.invalidMessage' text='Only Numeric values are allowed' />'" placeholder="<spring:message code='label.piginfo.groupEventForm.search.placeholder'  text='Search by Group Id ...'/>" class="form-control" style="width:90%;display:inline">
								 <button type="button" class="btn btn-primary active" ng-click="getGroupEventInformation(searchText)"><i class="fa fa-search"></i></button>
							</div>					
						</div>
					</div>
					<div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.groupId'  text='Group Id'/></label>
                      <input ng-hide="(moveGroupevent.id != null && moveGroupevent.id > 0)" type="text" ng-model="moveGroupevent.groupId" id="groupId" name="groupId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.groupEventForm.groupId.placeholder'  text='Enter Group Id'/>" required
                      required-message="'<spring:message code='label.piginfo.groupEventForm.groupId.requiredMessage' text='Group Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.groupEventForm.groupId.invalidMessage' text='Only Numeric values are allowed' />'"  ng-focus="clearMessages()"/>
						<label ng-show="(moveGroupevent.id != null && moveGroupevent.id > 0)"> :  {{moveGroupevent.groupId}}</label>
                   </div>
				   <div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="groupIdMatches" ><spring:message code='label.piginfo.groupEventForm.groupIdMatches' text='Moved to group can not be parent group' /></label>
					</div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.groupStartDateTime'  text='Group Start Date'/><span style='color: red'>*</span></label>
                      <div ng-hide="(moveGroupevent.id != null && moveGroupevent.id > 0)" data-min-view="2" data-date-format="yyyy-MM-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                         <!-- <input size="16" type="date" id="groupStartDateTime" name="groupStartDateTime" ng-model="moveGroupevent.groupStartDateTime" readonly="" class="form-control" format-date required-message="'<spring:message code='label.piginfo.groupEventForm.groupStartDateTime.requiredMessage' text='Group Start Date is required' />'"/><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>-->
						 <input type="text" datepicker-popup="yyyy-MM-dd" class="form-control" datepicker-popup="shortDate" id="groupStartDateTimeAnother" name="groupStartDateTimeAnother" ng-model="moveGroupevent.groupStartDateTimeAnother" is-open="opened" required-message="'<spring:message code='label.piginfo.groupEventForm.groupStartDateTime.requiredMessage' text='Group Start Date is required' />'" />
							<span class="input-group-btn">
							<button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
						</span>
						</div>
						<label ng-show="(moveGroupevent.id != null && moveGroupevent.id > 0)"> :{{ DateUtils.getFormatedDate(moveGroupevent.groupStartDateTimeAnother)}}</label>	
                    </div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="groupdaterequiredMove" ><spring:message code='label.piginfo.groupEventForm.groupStartDate.requiredMessage' text='Group Start Date is required' /></label>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="groupDateError" ><spring:message code='label.piginfo.groupEventForm.groupStartDate.errorMessage' text='Group Start Date cant not be less than Parent Start required' /></label>
					</div>
                     <div class="form-group">
                      <label><spring:message code='label.groupEventDetail.phaseOfProductionTypeId'  text='Phase Of Production'/><span style='color: red'>*</span></label>
                       <select ng-hide="(moveGroupevent.id != null && moveGroupevent.id > 0)" class="form-control"  required required-message="'<spring:message code='label.groupEventDetail.phaseOfProduction.required' text='Phase Of Production is required' />'"  name="phaseOfProductionTypeId" id="phaseOfProductionTypeId" ng-model="moveGroupevent.phaseOfProductionTypeId"   
                         ng-options="k as v for (k, v) in phaseOfProductionType"> </select>
						<label ng-show="(moveGroupevent.id != null && moveGroupevent.id > 0)"> :  {{phaseOfProductionType[moveGroupevent.phaseOfProductionTypeId]}}</label>						 
                    </div>
					<div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.numberOfPigsToBeMoved'  text='Number of pigs to be moved'/><span style='color: red'>*</span></label>
                      <input type="text" ng-model="moveGroupevent.currentInventory" id="currentInventory" name="currentInventory"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.numberOfPigsToBeMoved'  text='Enter Remark'/>" required required-message="'<spring:message code='label.piginfo.groupEventForm.numberOfPigsToBeMoved.required' text='Number of pigs to be moved are required' />'" " 
                       ng-focus="clearMessages()" ng-pattern="/^\d{1,8}?$/i"  invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
                   </div>
				   <div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="noOfPigsCanBeTransfered" ><spring:message code='label.piginfo.groupEventForm.numberOfPigsToBeMoved.errorMessage' text='No. Of Pigs transfer can not be more than no.of Pigs available in parent Group Event' /></label>
					</div>
                   <div class="form-group">
						<label><spring:message code="label.groupEventDetail.weightInKgs" text="Weight" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.groupEventDetail.weightInKgs'  text='Weight'/>" name="weightInKgs" ng-model="moveGroupevent.weightInKgs" required required-message="'<spring:message code='label.groupEventDetail.weightInKgs.required' text='Weight is required' />'" ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>	
					</div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></label>
                      <input type="text" ng-model="moveGroupevent.remarks" id="remarks" name="remarks"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/>
                   </div>                  
         <div class="modal-footer">

            <button class="btn btn-success btn-flat md-close"  ng-click="addMoveGroupEvent()"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-warning btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
        </div>
       </div>
    </form>
</div>
</head>
</html>
