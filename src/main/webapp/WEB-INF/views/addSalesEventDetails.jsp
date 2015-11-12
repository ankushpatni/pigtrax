<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->

<div class="page-head">
          <h3 ng-hide="edit"><spring:message code="label.removalExceptSales.add.detail" text="Add Removal Except Sales" /></h3>
     		<h3 ng-show="edit"><spring:message code="label.removalExceptSales.edit.detail" text="Edit Removal Except Sales" /></h3>
</div>		 
 <div class="cl-mcont" ng-controller="SalesEventController" ng-init="setCompanyId('${companyId}','${removalTypeId}')">
        <div class="row" >
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                   <div class="alert alert-danger alert-white rounded" ng-show="eventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.removalExceptSales.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                   <div class="alert alert-danger alert-white rounded"  ng-show="eventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalExceptSales.duplicate.error.message' text='A Removal Except Sales record already exists with the '/>
                  </div>		 
                </div>
                
                <div class="content">
                  <form name="salesEventForm" novalidate angular-validator my-reset method="post">
                  <input type=hidden name="id" ng-model="removalExceptSales.id"/>
                  <input type=hidden name="removalEventId" ng-model="removalExceptSales.removalEventId"/>
				  <!-- <div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.numberOfPigs'  text='Number Of Pigs'/><span style='color: red'>*</span></label>
                      <label ng-show="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage">{{removalExceptSales.numberOfPigs}}</label>
                      <input type="text" ng-hide="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage" ng-model="removalExceptSales.numberOfPigs" id="numberOfPigs" name="numberOfPigs"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.removalExceptSales.numberOfPigs.placeholder'  text='Enter Number Of Pigs'/>" 
                       required required-message="'<spring:message code='label.piginfo.removalExceptSales.numberOfPigs.requiredMessage' text='Number Of Pigs are required' />'" ng-focus="clearMessages()"/>
                   </div>-->
                    <div >
                     	  <label><spring:message code='label.piginfo.removalExceptSales.groupPremises'  text='Select Group/Premises'/></label>
						  <div class="form-group">
				              <label >
				                  <input type="radio" name="rad1"  id="rad1" value="group" ng-model="selectGroup"> <spring:message code='label.piginfo.removalExceptSales.search.option.groupId'  text='Group'/> 
				              </label>
				              <label >
				                  <input type="radio" name="rad1" id="rad2" value="pigInfo" ng-model="selectGroup"> <spring:message code='label.piginfo.removalExceptSales.pigInfoId'  text='Premises'/>
				              </label>				
			              </div>
				      </div> 
				        <div class="form-group" ng-show="selectGroup==='pigInfo'">
                      <label><spring:message code='label.piginfo.removalExceptSales.pigInfoId'  text='Pig Info'/></label>
                       <select ng-hide="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage" class="form-control"  name="pigInfoId" id="pigInfoId" ng-model="removalExceptSales.pigInfoId"   
                         ng-options="k as v.pigId for (k, v) in pigInfoList"> </select>
                         <label ng-show="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage"> :  {{pigInfoList[removalExceptSales.pigInfoId]}}</label>                       
                    </div>
                    
                    <div class="form-group" ng-show="selectGroup==='group'">
                      <label><spring:message code='label.piginfo.removalExceptSales.groupEventId'  text='Group Event'/></label>
                       <select ng-hide="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage" class="form-control"  name="groupEventId" id="groupEventId" ng-model="removalExceptSales.groupEventId"   
                         ng-options="k as v.groupId for (k, v) in groupEventList" ng-show="v.active"> </select>
                         <label ng-show="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage"> :  {{groupEventList[removalExceptSales.groupEventId].groupId}}</label>
                         </div> 
					<div ng-show="selectGroup==='group'">
						<label style="margin-top: -15px;" class="control-label" ><spring:message code='label.piginfo.removalExceptSales.numberOfPigsInGroup' text='Group Inventory :' />{{groupEventList[removalExceptSales.groupEventId].currentInventory}}</label>
					</div>
					<div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.numberOfPigs'  text='Number Of Pigs'/></label>
                      <label ng-show="selectGroup==='pigInfo'">1</label>
                      <input type="text" ng-show="selectGroup==='group'" ng-model="removalExceptSales.numberOfPigs" id="numberOfPigs" name="numberOfPigs"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.removalExceptSales.numberOfPigs.placeholder'  text='Enter Number Of Pigs'/>" 
                      required-message="'<spring:message code='label.piginfo.removalExceptSales.numberOfPigs.requiredMessage' text='Number Of Pigs are required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.groupEventForm.groupId.invalidMessage' text='Only Numeric values are allowed' />'"  ng-focus="clearMessages()"/>
                   </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.removalEventform.removalTypeId'  text='Removal Type'/></label>                      
 						<select  ng-show="removalTypeId != 3" class="form-control"    name="removalEventId" id="removalEventId" ng-model="removalExceptSales.removalEventId" >
                      	<option ng-repeat="key in removalEventTypeKeys" ng-value="key" ng-selected="removalExceptSales.removalEventId==key" ng-hide="key==3 || key ==9">{{removalEventType[key]}}</option>        
                        </select>                      
                         <label ng-show="removalTypeId == 3"> :  {{removalEventType[removalTypeId]}}</label> 
                        
                    </div>
                   <!-- <div class="form-group">
                      <label><spring:message code='label.piginfo.removalEventform.removalId'  text='Removal Id'/><span style='color: red'>*</span></label>
                      <input type="text" ng-hide="(removalEvent.id != null && removalEvent.id > 0) || entryEventSuccessMessage" ng-model="removalExceptSales.removalEventId" id="removalEventId" name="removalEventId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.removalEventform.removalId.placeholder'  text='Enter Removal Id'/>" required
                      required-message="'<spring:message code='label.piginfo.removalEventform.removalId.requiredMessage' text='Removal Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.removalEventform.removalId.invalidMessage' text='Only Numeric values are allowed' />'"  ng-focus="clearMessages()"/>
                   </div>-->
					<div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.ticketNumber'  text='Ticket Number'/></label>
                     <input class="form-control" type="text" placeholder="<spring:message code='label.piginfo.removalExceptSales.ticketNumber.placeHolder' text='Enter Ticket  Number' />" 
                     	name="ticketNumber" ng-model="removalExceptSales.ticketNumber" maxlength="20" required required-message="'<spring:message code='label.piginfo.removalExceptSales.ticketNumber.requiredMessage' text='Ticket Number Required' />'" 
                     	 />
                   </div>
					<div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.invoiceId'  text='Invoice Number'/></label>
                     <input class="form-control" type="text" placeholder="<spring:message code='label.piginfo.removalExceptSales.invoiceId.placeHolder' text='Enter Invoice Number' />" 
                     	name="invoiceId" ng-model="removalExceptSales.invoiceId" maxlength="20" required-message="'<spring:message code='label.piginfo.removalExceptSales.invoiceId.requiredMessage' text='Invoice Number Required' />'" 
                     	 />
                   </div>
					<div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.revenueUsd'  text='Revenue($)'/></label>
                     <input class="form-control" type="text" placeholder="<spring:message code='label.piginfo.removalExceptSales.revenueUsd.placeHolder' text='Enter Revenue($)' />" 
                     	name="revenueUsd" ng-model="removalExceptSales.revenueUsd" maxlength="23" required-message="'<spring:message code='label.piginfo.removalExceptSales.revenueUsd.requiredMessage' text='Revenue required' />'" 
                     	 ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>
                   </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.soldTo'  text='Sold To'/></label>
                     <input class="form-control" type="text" placeholder="<spring:message code='label.piginfo.removalExceptSales.soldTo.placeHolder' text='Enter Sold To' />" 
                     	name="soldTo" ng-model="removalExceptSales.soldTo" maxlength="20" required-message="'<spring:message code='label.piginfo.removalExceptSales.soldTo.requiredMessage' text='Sold To Required' />'" 
                     	 />
                   </div>
                   <div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="noOfPigsrequired" ><spring:message code='label.piginfo.removalExceptSales.numberOfPigs.requiredMessage' text='No. Of pigs are required.' /></label>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="noOfPigWrongCount" ><spring:message code='label.piginfo.removalExceptSales.numberOfPigs.wrongCount' text='No. Of pigs can nto be greater than Group event Pigs.' /></label>
					</div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.salesDateTime'  text='Sales Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="salesDateTime" name="salesDateTime" ng-model="removalExceptSales.salesDateTime" readonly="" class="form-control" format-date /><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
						</div>
                    </div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="removalDateTimerequired" ><spring:message code='label.piginfo.removalExceptSales.salesDateTime.requiredMessage' text='Sales Date Time is required' /></label>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="errorRemovalDateTime" ><spring:message code='label.piginfo.removalExceptSales.salesDateTime.wrongDateMessage' text='Sales Date Time is can not be less than Event Start Date' /></label>
					</div>	
	 				<div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.weightInKgs'  text='Weight In Kgs'/><span style='color: red'>*</span></label>
                     <input class="form-control" type="text" placeholder="<spring:message code='label.piginfo.removalExceptSales.weightInKgs.placeHolder' text='Enter Weight In Kgs' />" 
                     	name="weightInKgs" ng-model="removalExceptSales.weightInKgs" maxlength="20" required required-message="'<spring:message code='label.piginfo.removalExceptSales.weightInKgs.requiredMessage' text='Weight In Kgs required' />'" 
                     	 ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>
                   </div>
                 
                  <%--   <div class="form-group">
                      <label><spring:message code='label.piginfo.removalExceptSales.premiseId'  text='Premise'/></label>
                       <select ng-hide="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage" class="form-control" name="premiseId" id="premiseId" ng-model="removalExceptSales.premiseId"   
                         ng-options="k as v for (k, v) in premiseList"> </select>
                         <label ng-show="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage"> :  {{premiseList[removalExceptSales.premiseId]}}</label>                       
                    </div>
                    <div class="form-group" ng-show="removalTypeId==3">
                      <label><spring:message code='label.piginfo.removalExceptSales.premiseId'  text='Premise'/></label>
                       <select ng-hide="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage" class="form-control" name="destPremiseId" id="destPremiseId" ng-model="removalExceptSales.destPremiseId"   
                         ng-options="k as v for (k, v) in premiseList"> </select>
                         <label ng-show="(removalExceptSales.id != null && removalExceptSales.id > 0) || entryEventSuccessMessage"> :  {{premiseList[removalExceptSales.destPremiseId]}}</label>                       
                    </div>
                    <div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="sourceAndDestinationPremisesSameError" ><spring:message code='label.piginfo.removalExceptSales.sourceAndDestinationPremisesSameError.errorMessage' text='Source and Destination Premises can not be same' /></label>
					</div> --%>
                   <!-- <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.transportJourneyId'  text='Transport Journey'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span ng-hide="(removalExceptSales.transportJourney.id != null && removalExceptSales.transportJourney.trailerFunction != 0)" class="btn btn-primary" ng-click="addTransportJourney()"><span class="glyphicon glyphicon-user"></span></span>	
                      <input type="hidden" ng-model="removalExceptSales.transportJourneyId" id="transportJourneyId" name="transportJourneyId"/>
					  <div ng-show="(removalExceptSales.transportJourney.id != 0 && removalExceptSales.transportJourney.id != null)">
							<p><spring:message code="label.transportJourney.trailerFunction" text="Trailer Function" /> : <small>{{removalExceptSales.transportJourney.trailerFunction}}</small></p>
							<p><spring:message code='label.transportJourney.transportDestinationId'  text='Transport Destination'/> : <small>{{transportDestination[removalExceptSales.transportJourney.transportDestinationId]}}</small></p>
							<p><spring:message code='label.transportJourney.transportTruckId'  text='Transport Truck'/> : <small>{{transportTruck[removalExceptSales.transportJourney.transportTruckId]}}</small></p>
							<p><spring:message code='label.transportJourney.transportTrailerId'  text='Transport Trailer'/> : <small>{{transportTrailer[removalExceptSales.transportJourney.transportTrailerId]}}</small></p>
						</div>
                      </div>
                    </div>
                     -->
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></label>
                      <input type="text" ng-model="removalExceptSales.remarks" id="remarks" name="remarks"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/>
                   </div>
                    					
                  	<button class="btn btn-primary" ng-click="addSalesEventDetails()" type="submit"><spring:message code='label.piginfo.groupEventform.add'  text='Add'/></button>
					<button class="btn btn-default" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.pregnancyeventform.cancel'  text='Clear Form'/></button>
					
					<input type="hidden" name="removalEventTicketNumber" id="removalEventTicketNumber"/>
					<input type="hidden" name="selectedCompany" id="selectedCompany"/>
					<input type="hidden" name="fromExcept" id="fromExcept"/>
                   </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		<div class="md-overlay"></div>
	</div>	