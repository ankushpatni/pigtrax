<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.feedEventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="FeedEventController" ng-init="setCompanyId('${CompanyId}','${feedEventTicketNumber}')">
   <div class="row">
	 		  <div class="col-sm-3 col-md-3"></div> 
	 		  <div class="col-sm-6 col-md-6">
		 		  <div class="block-flat">
					  <form name="feedEventSearchForm" >
			 		     <div class="head">
			            	<h3> <spring:message code='label.piginfo.pregnancyeventform.search.heading'  text='Search'/></h3>
			               	<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.feedEventForm.search.data.errormessage' text='Feed event information not found for the search criteria'/></p>
					   		 <input type="text" name="search" ng-model="searchText" ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.feedEventForm.feedContentId.invalidMessage' text='Only Numeric values are allowed' />'" placeholder="<spring:message code='label.piginfo.feedEventForm.search.placeholder'  text='Search by Ticket Number ...'/>" class="form-control" style="width:90%;display:inline">
							 <button type="button" class="btn btn-primary active" ng-click="getFeedEvent(searchText)"><i class="fa fa-search"></i></button>
			          	</div>
					  </form>	
				 </div>
	          </div>
	 		  <div class="col-sm-3 col-md-3"></div>
	  </div>			
 		
          <div class="row" >
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.piginfo.feedEventForm.feedEvent'  text='Feed Event'/></h3>
                   <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.feedEventform.submit.success.message' text='Feed Event information saved successfully'/>
                  </div>
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventDetailSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.feedEventform.details.submit.success.message' text='Feed Event detail information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                   <div class="alert alert-danger alert-white rounded"  ng-show="groupEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.feedEventform..duplicate.error.message' text='A Feed event record already exists with the same Ticket Number'/>
                  </div>		 
                </div>
                <div class="content">
                  <form name="feedEventForm" novalidate angular-validator my-reset>
                  <input type=hidden name="id" ng-model="feedEvent.id"/>
				  <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.feedContentId'  text='Feed Content Id'/><span style='color: red'>*</span></label>
                      <label ng-show="(feedEvent.id != null && feedEvent.id > 0) || entryEventSuccessMessage">{{feedEvent.feedContentId}}</label>
                      <input type="text" ng-hide="(feedEvent.id != null && feedEvent.id > 0) || entryEventSuccessMessage" ng-model="feedEvent.feedContentId" id="feedContentId" name="feedContentId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.feedEventForm.feedContentId.placeholder'  text='Enter Feed Content Id'/>" required
                      required-message="'<spring:message code='label.piginfo.feedEventForm.feedContentId.requiredMessage' text='Feed Content Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.feedEventForm.feedContentId.invalidMessage' text='Only Numeric values are allowed' />'"  ng-focus="clearMessages()"/>
                   </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.ticketNumber'  text='Ticket Number'/><span style='color: red'>*</span></label>
                      <label ng-show="(feedEvent.id != null && feedEvent.id > 0) || entryEventSuccessMessage">{{feedEvent.ticketNumber}}</label>
                      <input type="text" ng-hide="(feedEvent.id != null && feedEvent.id > 0) || entryEventSuccessMessage" ng-model="feedEvent.ticketNumber" id="ticketNumber" name="ticketNumber"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.feedEventForm.ticketNumber.placeholder'  text='Enter Ticket Number'/>" 
                       required required-message="'<spring:message code='label.piginfo.feedEventForm.ticketNumber.requiredMessage' text='Ticket Number is required' />'" ng-focus="clearMessages()"/>
                   </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.initialFeedEntryDateTime'  text='Initial Feed Entry Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="initialFeedEntryDateTime" name="initialFeedEntryDateTime" ng-model="feedEvent.initialFeedEntryDateTime" readonly="" class="form-control" format-date required-message="'<spring:message code='label.piginfo.feedEventForm.initialFeedEntryDateTime.requiredMessage' text='Initial Feed Entry Date Time is required' />'"/><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
						</div>
                    </div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="initialFeedEntryDateTimerequired" ><spring:message code='label.piginfo.feedEventForm.initialFeedEntryDateTime.requiredMessage' text='Initial Feed Entry Date Time is required' /></label>
					</div>	
	 				<div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.batchId'  text='Batch Id'/><span style='color: red'>*</span></label>
                      <input type="text" ng-model="feedEvent.batchId" id="batchId" name="batchId"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.feedEventForm.batchId.placeholder'  text='Enter Batch Id'/>" 
                       required required-message="'<spring:message code='label.piginfo.feedEventForm.batchId.requiredMessage' text='Batch Id is required' />'" ng-focus="clearMessages()"/>
                   </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.initialFeedQuantityKgs'  text='Initial Feed Quantity (In Kgs)'/><span style='color: red'>*</span></label>
                     <input class="form-control" type="text" placeholder="<spring:message code='label.piginfo.feedEventForm.initialFeedQuantityKgs.placeHolder' text='Enter Initial Feed Quantity (In Kgs)' />" 
                     	name="initialFeedQuantityKgs" ng-model="feedEvent.initialFeedQuantityKgs" maxlength="20" required required-message="'<spring:message code='label.piginfo.feedEventForm.initialFeedQuantityKgs.requiredMessage' text='Initial Feed Quantity required' />'" 
                     	 ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>
                   </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.feedCost'  text='Feed Cost'/><span style='color: red'>*</span></label>
                     <input class="form-control" type="text" placeholder="<spring:message code='label.piginfo.feedEventForm.feedCost.placeHolder' text='Enter Feed Cost' />" 
                     	name="feedCost" ng-model="feedEvent.feedCost" maxlength="20" required required-message="'<spring:message code='label.piginfo.feedCost.feedCost.requiredMessage' text='Feed Cost required' />'" 
                     	 ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>
                   </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.feedMedication'  text='Feed Medication'/><span style='color: red'>*</span></label>
                      <input type="text" ng-model="feedEvent.feedMedication" id="feedMedication" name="feedMedication"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.feedEventForm.feedMedication.placeholder'  text='Enter Feed Medication'/>" 
                       required required-message="'<spring:message code='label.piginfo.feedEventForm.feedMedication.requiredMessage' text='Feed Medication is required' />'" ng-focus="clearMessages()"/>
                   </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.transportJourneyId'  text='Transport Journey'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span ng-hide="(feedEvent.transportJourney.id != null && feedEvent.transportJourney.trailerFunction != 0)" class="btn btn-primary" ng-click="addTransportJourney()"><span class="glyphicon glyphicon-user"></span></span>	
                      <input type="hidden" ng-model="feedEvent.transportJourneyId" id="transportJourneyId" name="transportJourneyId"/>
					  <div ng-show="(feedEvent.transportJourney.trailerFunction != null && feedEvent.transportJourney.trailerFunction != '') ||
							(feedEvent.transportJourney.id != 0 && feedEvent.transportJourney.id != null)">
							<p><spring:message code="label.transportJourney.trailerFunction" text="Trailer Function" /> : <small>{{feedEvent.transportJourney.trailerFunction}}</small></p>
							<p><spring:message code='label.transportJourney.transportDestinationId'  text='Transport Destination'/> : <small>{{transportDestination[feedEvent.transportJourney.transportDestinationId]}}</small></p>
							<p><spring:message code='label.transportJourney.transportTruckId'  text='Transport Truck'/> : <small>{{transportTruck[feedEvent.transportJourney.transportTruckId]}}</small></p>
							<p><spring:message code='label.transportJourney.transportTrailerId'  text='Transport Trailer'/> : <small>{{transportTrailer[feedEvent.transportJourney.transportTrailerId]}}</small></p>
						</div>
                      </div>
                    </div>
                    					
                  	<button class="btn btn-primary" ng-click="addFeedEvent()" type="submit" ng-hide="(feedEvent.id != null && feedEvent.id > 0) || entryEventSuccessMessage"><spring:message code='label.piginfo.groupEventform.add'  text='Add'/></button>
					<button class="btn btn-primary" ng-click="addFeedEvent()" type="submit" ng-show="(feedEvent.id != null && feedEvent.id > 0) || entryEventSuccessMessage"><spring:message code='label.piginfo.groupEventform.edit'  text='Edit'/></button>
					<button class="btn btn-default" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.pregnancyeventform.cancel'  text='Clear Form'/></button>
                   </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		  
		<button type="button" ng-click="addFeedEventDetail()" class="btn btn-sm btn btn-primary" ng-show="(feedEvent.id != null && feedEvent.id > 0) || entryEventSuccessMessage">
			<i class="glyphicon glyphicon-plus"></i> 
			<spring:message code="label.feedEventDetail.addFeedEventDetail" text="Add Feed Event" />
		</button>
		<!-- <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  > 
			<span class="btn btn-primary" ng-click="addFeedEventDetailData()"><spring:message code="label.feedEventDetail.addFeedEventDetail" text="Add Feed Event" /><span class="glyphicon glyphicon-plus" style="margin-left:5px"></span></span>	
          </div>-->
		 
	<form name="groupEventFormAdd" method="post">	
		<div class="content" ng-show="(feedEvent.id != null && feedEvent.id > 0) || entryEventSuccessMessage">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection" st-safe-src="feedEventDetailList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #3399CC">
					<tr>
						<th style="width:10%"><spring:message code="label.groupEventDetail.number" text="Number" /></th>
						<th style="width:10%"><spring:message code="label.feedEventDetail.feedEventDate" text="Feed Event Date" /></th>
						<th style="width:10%"><spring:message code="label.feedEventDetail.weightInKgs" text="Weight In Kgs" /></th>
						<th style="width:10%"><spring:message code="label.feedEventDetail.remarks" text="Remarks" /></th>
						<th style="width:10%"><spring:message code="label.feedEventDetail.feedEventId" text="Feed Event" /></th>
						<th style="width:25%"><spring:message code="label.feedEventDetail.siloId" text="Silo" /></th>
						<th style="width:10%"><spring:message code="label.feedEventDetail.groupEventId" text="Group Event" /></th>
						<th style="width:25%"><spring:message code="label.feedEventDetail.feedEventTypeId" text="Feed Event Type" /></th>
						<th style="width:10%"><spring:message code="label.feedEventDetail.edit" text="Edit" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:10%">{{$index+1}}</td>
					<td style="width:10%">{{row.feedEventDate}}</td>
					<td style="width:10%">{{row.weightInKgs}}</td>
					<td style="width:25%">{{row.remarks}}</td>
					<td style="width:25%">{{row.feedEventId}}</td>
					<td style="width:25%">{{siloList[row.siloId]}}</td>
					<td style="width:10%">{{row.groupEventId}}</td>
					<td style="width:25%">{{feedEventType[row.feedEventTypeId]}}</td>
					<td style="width: 8%">
						<button type="button" class="btn btn-edit btn-xs" ng-click="addFeedEventDetail(row.id)">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
					</td>				
				</tr>
				</tbody>		
				<tr style="background-color: #3399CC">
					<td colspan="14">
						<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPages" ></div>
					</td>
				</tr>
			</table>
			</div>
		</div>
		<input type="hidden" name="searchedGroupid" id="searchedGroupid"/>
		<input type="hidden" name="groupEventId" id="groupEventId"/>			
		<input type="hidden" name="groupGeneratedIdSeq" id="groupGeneratedIdSeq"/>
		<input type="hidden" name="companyId" id="companyId"/>
		<input type="hidden" name="groupStartDateTimeAdd" id="groupStartDateTimeAdd"/>
		
	</form>	  		
		<div class="md-overlay"></div>
	</div>	
	