<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.removalEventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="RemovalEventController" ng-init="setCompanyId('${CompanyId}','${removalEventTicketNumber}')">
   <div class="row">
	 		  <div class="col-sm-3 col-md-3"></div> 
	 		  <div class="col-sm-6 col-md-6">
		 		  <div class="block-flat">
					  <form name="feedEventSearchForm" >
			 		     <div class="head">
			            	<h3> <spring:message code='label.piginfo.pregnancyeventform.search.heading'  text='Search'/></h3>
			               	<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.removalEventform.search.data.errormessage' text='Removal event information not found for the search criteria'/></p>
					   		 <input type="text" name="search" ng-model="searchText" ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.feedEventForm.feedContentId.invalidMessage' text='Only Numeric values are allowed' />'" placeholder="<spring:message code='label.piginfo.feedEventForm.search.placeholder'  text='Search by Ticket Number ...'/>" class="form-control" style="width:90%;display:inline">
							 <button type="button" class="btn btn-primary active" ng-click="getRemovalEvent(searchText)"><i class="fa fa-search"></i></button>
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
                  <h3><spring:message code='label.piginfo.removalEventform.feedEvent'  text='Removal Event'/></h3>
                   <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalEventform.submit.success.message' text='Removal Event information saved successfully'/>
                  </div>
                  <div class="alert alert-success alert-white rounded"  ng-show="entrySalesDetailSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalEventform.salesdetails.submit.success.message' text='Sales Event Details information saved successfully'/>
                  </div>
                  <div class="alert alert-success alert-white rounded"  ng-show="entryExceptSalesDetailsSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalEventform.ExceptSalesDetails.submit.success.message' text='Except Sales Details information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                   <div class="alert alert-danger alert-white rounded"  ng-show="removalEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalEventform.duplicate.error.message' text='A Removal event record already exists with the same Ticket Number'/>
                  </div>		 
                </div>
                <div class="content">
                  <form name="removalEventForm" novalidate angular-validator my-reset>
                  <input type=hidden name="id" ng-model="removalEvent.id"/>
				  <div class="form-group">
                      <label><spring:message code='label.piginfo.removalEventform.removalId'  text='Removal Id'/><span style='color: red'>*</span></label>
                      <label ng-show="(removalEvent.id != null && removalEvent.id > 0) || entryEventSuccessMessage">{{removalEvent.removalId}}</label>
                      <input type="text" ng-hide="(removalEvent.id != null && removalEvent.id > 0) || entryEventSuccessMessage" ng-model="removalEvent.removalId" id="removalId" name="removalId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.removalEventform.removalId.placeholder'  text='Enter Removal Id'/>" required
                      required-message="'<spring:message code='label.piginfo.removalEventform.removalId.requiredMessage' text='Removal Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.removalEventform.removalId.invalidMessage' text='Only Numeric values are allowed' />'"  ng-focus="clearMessages()"/>
                   </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.removalEventform.removalTypeId'  text='Removal Type'/></label>
                       <select ng-hide="(removalEvent.id != null && removalEvent.id > 0) || entryEventSuccessMessage" class="form-control"  required required-message="'<spring:message code='label.piginfo.removalEventform.removalTypeId.required' text='Removal Type is required' />'" name="removalTypeId" id="removalTypeId" ng-model="removalEvent.removalTypeId"   
                         ng-options="k as v for (k, v) in removalEventType"> </select>
                         <label ng-show="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage"> :  {{removalEventType[removalEvent.removalTypeId]}}</label>                       
                    </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></label>
                      <input type="text" ng-model="removalEvent.remarks" id="remarks" name="remarks"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/>
                   </div>
	 				
                    <%-- <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.transportJourneyId'  text='Transport Journey'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span ng-hide="(removalEvent.transportJourney.id != null && removalEvent.transportJourney.trailerFunction != 0)" class="btn btn-primary" ng-click="addTransportJourney()"><span class="glyphicon glyphicon-user"></span></span>	
                      <input type="hidden" ng-model="removalEvent.transportJourneyId" id="transportJourneyId" name="transportJourneyId"/>
					  <div ng-show="(removalEvent.transportJourney.trailerFunction != null && removalEvent.transportJourney.trailerFunction != '') ||
							(removalEvent.transportJourney.id != 0 && removalEvent.transportJourney.id != null)">
							<p><spring:message code="label.transportJourney.trailerFunction" text="Trailer Function" /> : <small>{{removalEvent.transportJourney.trailerFunction}}</small></p>
							<p><spring:message code='label.transportJourney.transportDestinationId'  text='Transport Destination'/> : <small>{{transportDestination[removalEvent.transportJourney.transportDestinationId]}}</small></p>
							<p><spring:message code='label.transportJourney.transportTruckId'  text='Transport Truck'/> : <small>{{transportTruck[removalEvent.transportJourney.transportTruckId]}}</small></p>
							<p><spring:message code='label.transportJourney.transportTrailerId'  text='Transport Trailer'/> : <small>{{transportTrailer[removalEvent.transportJourney.transportTrailerId]}}</small></p>
						</div>
                      </div>
                    </div> --%>
                    					
                  	<button class="btn btn-primary" ng-click="addRemovalEvent()" type="submit" ng-hide="(removalEvent.id != null && removalEvent.id > 0) || entryEventSuccessMessage"><spring:message code='label.piginfo.groupEventform.add'  text='Add'/></button>
					<button class="btn btn-primary" ng-click="addRemovalEvent()" type="submit" ng-show="(removalEvent.id != null && removalEvent.id > 0) || entryEventSuccessMessage"><spring:message code='label.piginfo.groupEventform.edit'  text='Edit'/></button>
					<button class="btn btn-default" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.pregnancyeventform.cancel'  text='Clear Form'/></button>
                   </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		  
		<%-- <button type="button" ng-click="addFeedEventDetail()" class="btn btn-sm btn btn-primary" ng-show="(feedEvent.id != null && feedEvent.id > 0) || entryEventSuccessMessage">
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
		
	</form>	  		--%>
		<div class="md-overlay"></div>
	</div>	
	 