<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2>${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="RemovalEventController" ng-init="setCompanyId('${CompanyId}','${removalEventTicketNumber}','${fromExcept}','${actionResult}')">
   <div class="row">
	 		  <div class="col-sm-3 col-md-3"></div> 
	 		  <div class="col-sm-6 col-md-6">
		 		  <div class="block-flat">
					  <%-- <form name="feedEventSearchForm" >
			 		     <div class="head">
			            	<h3> <spring:message code='label.piginfo.pregnancyeventform.search.heading'  text='Search'/></h3>
			               	<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.removalEventform.search.data.errormessage' text='Removal event information not found for the search criteria'/></p>
					   		 <input type="text" name="search" ng-model="searchText" ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.feedEventForm.feedContentId.invalidMessage' text='Only Numeric values are allowed' />'" placeholder="<spring:message code='label.piginfo.feedEventForm.search.placeholder'  text='Search by Ticket Number ...'/>" class="form-control" style="width:90%;display:inline">
							 <button type="button" class="btn btn-primary active" ng-click="getRemovalEvent(searchText)"><i class="fa fa-search"></i></button>
			          	</div>
					  </form>	 --%>
					   <form name="removalExceptSalesSearchForm" method="post">
				 		     <div class="head">
				            <h3> <spring:message code='label.piginfo.removalExceptSales.search.heading'  text='Search Removal Events'/></h3>
				            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.removalExceptSales.search.errormessage' text='Please enter Group Id/Pig Id/Removal Event Id  and select the corresponding option'/></p>
				            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.removalExceptSales.search.data.errormessage' text='Removal/Sales information not found for the search criteria'/></p>
							<!--<div  class="form-group">
		             			<select class="form-control" name="premiseId" ng-model="premiseId"  ng-options="k as v for (k, v) in premiseNameMap" ng-change="loadPigAndGroupInfo()"></select>		             			
							</div>-->
						<div  class="form-group">
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="premiseId" id="premiseId" ng-model="premiseId"  ng-change="loadPigAndGroupInfo()">
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="premiseId == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
				            <div class="options">
							  <div class="form-group">
				              <label class="radio-inline">
				                  <input type="radio" name="rad1"  id="rad1" class="removal" value="groupId" ng-model="selectGroup" ng-change="loadGroupEvents()"> <spring:message code='label.piginfo.removalExceptSales.search.groupId.option'  text='Group Id'/> 
				                </label>
				                <label class="radio-inline">
				                  <input type="radio" name="rad1" id="rad2" class="removal" value="pigId" ng-model="selectGroup" ng-change="loadPigInfo()"> <spring:message code='label.piginfo.removalExceptSales.search.pigid.option'  text='Pig Id'/>
				                </label>
				                 <!-- <label class="radio-inline">
				                  <input type="radio" name="rad1"  id="rad3" class="icheck removal" value="removalId"> <spring:message code='label.piginfo.removalExceptSales.search.removalId.option'  text='Removal Event Id'/> 
				                </label> -->				
				              </div>
							  
							  <div  class="form-group" ng-show="selectGroup==='pigId'">
				 
								  <button type="button" class="btn btn-primary active btn-group pull-right" ng-enter="searchRemovalEvent(searchText)" ng-click="searchRemovalEvent(searchText)"><i class="fa fa-search"></i></button>
								
									 <select  class="form-control"  name="search" ng-model="searchText" style="width:85%">
												<option value="" hidden><spring:message code='label.piginfo.pigId.placeholder' text='Select PigId' /></option>
												<option ng-repeat="pigInfoSearch in pigInfoListSearch" value="{{pigInfoSearch.pigId}}" ng-value="pigInfoSearch.pigId" ng-selected="searchText == pigInfoSearch.pigId">{{pigInfoSearch.pigId}}</option>
												</select>
							</div>
							<div  class="form-group" ng-show="selectGroup==='groupId'">
								 
								  <button type="button" class="btn btn-primary active btn-group pull-right" ng-enter="searchRemovalEvent(searchText)" ng-click="searchRemovalEvent(searchText)"><i class="fa fa-search"></i></button>
								
									 <select  class="form-control"  name="search" ng-model="searchText" style="width:85%">
												<option value="" hidden><spring:message code='label.piginfo.groupId.placeholder' text='Select GroupId' /></option>
												<option ng-repeat="groupEventSearch in groupEventListSearch" value="{{groupEventSearch.groupId}}" ng-value="groupEventSearch.groupId" ng-selected="searchText == groupEventSearch.groupId">{{groupEventSearch.groupId}}</option>
												</select>
							</div>
				            </div>            
				          </div>
		  			</form>	
				 </div>
	          </div>
	 		  <div class="col-sm-3 col-md-3"></div>
	  </div>	
		<div class="row">
	 		  <div class="col-sm-3 col-md-3"></div> 
	 		  <div class="col-sm-6 col-md-6">
		 		  <div class="block-flat">
		 		   <div class="header">
		 		   
				            <h3><spring:message code='label.piginfo.removalEventform.removalEventType'  text='Add Removal Event Type'/></h3>
		 		   <div class="alert alert-success alert-white rounded"  ng-show="actionResult">
									<button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
									<div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalEventform.ExceptSalesDetailsSalesEvent.submit.success.message' text='Removal Except/Sales Event Details information saved successfully'/>
							  </div>	
		 		   </div>
					<div class="content">
					   <form name="removalEventForm" method="post">
				 		     
				           	<div class="head">						
				            <div class="options">
							 <div class="btn-group pull-right">
				                <button type="button" class="btn btn-success active" ng-click="addDifferentRemovalEvent()"><i class="glyphicon glyphicon-plus"></i></button>
				              </div>
				              <div class="form-group">
				              <label class="radio-inline">
				                  <input type="radio" name="rad1"  id="radAdd1" class="icheck removal" value="removalExcept"> <spring:message code='label.piginfo.removalExceptSales.removalExceptSales.option'  text='Removal Except Sales'/>
				              </label>
				              <label class="radio-inline">
				                  <input type="radio" name="rad1" id="radAdd2" class="icheck removal" value="transfer" > <spring:message code='label.piginfo.removalExceptSales.transfer.option'  text='Pig Id'/>
				              </label>
				              <label class="radio-inline">
				                  <input type="radio" name="rad1"  id="radAdd3" class="icheck removal" value="sales"> <spring:message code='label.piginfo.removalExceptSales.sales.option'  text='Removal Event Id'/> 
				              </label>				
				              </div>
				            </div>  
								<div class="alert alert-success alert-white rounded"  ng-show="entrySalesDetailSuccessMessage">
									<button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
									<div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalEventform.salesdetails.submit.success.message' text='Sales Event Details information saved successfully'/>
								</div>
														
				          </div>
							<input type="hidden" name="companyId" id="companyId"/>
							<input type="hidden" name="removalTypeId1" id="removalTypeId1"/>
						</form>
						</div>	
				 </div>
	          </div>
	 		  <div class="col-sm-3 col-md-3"></div>
	  </div>	
 		
         <!-- <div class="row" >
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.piginfo.removalEventform.feedEvent'  text='Removal Event'/></h3>
                   <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalEventform.submit.success.message' text='Removal Event information saved successfully'/>
                  </div>
                  <div class="alert alert-success alert-white rounded"  ng-show="entrySalesDetailSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalEventform.salesdetails.submit.success.message' text='Sales Event Details information saved successfully'/>
                  </div>
                  <div class="alert alert-success alert-white rounded"  ng-show="entryExceptSalesDetailsSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.removalEventform.ExceptSalesDetails.submit.success.message' text='Except Sales Details information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                   <div class="alert alert-danger alert-white rounded"  ng-show="removalEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
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
                         <label ng-show="(removalEvent.id != null && removalEvent.id > 0) || entryEventSuccessMessage"> :  {{removalEventType[removalEvent.removalTypeId]}}</label>                       
                    </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></label>
                      <input type="text" ng-model="removalEvent.remarks" id="remarks" name="remarks"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/>
                   </div>
	 				
                    <%-- <div class="form-group">
                      <label><spring:message code='label.piginfo.feedEventForm.transportJourneyId'  text='Transport Journey'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span ng-hide="(removalEvent.transportJourney.id != null && removalEvent.transportJourney.trailerFunction != 0)" class="btn btn-success" ng-click="addTransportJourney()"><span class="glyphicon glyphicon-user"></span></span>	
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
                    					
                  	<button class="btn btn-success" ng-click="addRemovalEvent()" type="submit" ng-hide="(removalEvent.id != null && removalEvent.id > 0) || entryEventSuccessMessage"><spring:message code='label.piginfo.groupEventform.add'  text='Add'/></button>
					<button class="btn btn-success" ng-click="addRemovalEvent()" type="submit" ng-show="(removalEvent.id != null && removalEvent.id > 0) || entryEventSuccessMessage"><spring:message code='label.piginfo.groupEventform.edit'  text='Edit'/></button>
					<button class="btn btn-warning" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.pregnancyeventform.cancel'  text='Clear Form'/></button>
                   </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>-->

       <p class="text-center" ng-hide="dataLoaded">
    <i class="fa fa-spinner fa-spin fa-3x"></i>
</p>

	<form name="removalExceptSalesDisplayForm" method="post">	
		<div class="content" ng-show="exceptSalesFlag && dataLoaded" >
		<h3><spring:message code='label.piginfo.removalExceptSales.removalExcept.header'  text='Removal Except Sales'/></h3>
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection2" st-safe-src="removalExceptSalesList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>
						<!--  <th style="width:10%"><spring:message code="label.groupEventDetail.number" text="Number" /></th>-->
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.groupEventId" text="Group Event" /></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.removalDateTime" text="Removal Date" /></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.numberOfPigs" text="Number Of Pigs" /></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.weightInKgs" text="Weight" /></th>
						<th style="width:25%"><spring:message code="label.piginfo.removalExceptSales.search.option.premisesIdTo" text="Premises To" /></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.pigInfoId" text="Pig Info" /></th>
						<th style="width:25%"><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.delete" text="Delete" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection2 track by $index">
					<!-- <td style="width:10%">{{$index+1}}</td>-->
					<td style="width:25%">{{groupEventList[row.groupEventId].groupId}}</td>
					<td style="width:10%">{{DateUtils.getFormatedDate(row.removalDateTime)}}</td>
					<td style="width:10%">{{row.numberOfPigs}}</td>
					<td style="width:25%">{{row.weightInKgs}}</td>
					<td style="width:10%">{{premiseNameMap[row.premiseId]}}</td>
					<td style="width:25%">{{pigInfoList[row.pigInfoId].pigId}}</td>
					<td style="width:25%">{{row.remarks}}</td>
					<td style="width: 8%">
						<button type="button" ng-show="row.pigInfoId==0" class="btn btn-edit btn-xs" ng-click="deleteRemovalExceptSalesData(row,'<spring:message code="label.piginfo.removalExceptSales.delete.groupEvent" text="GroupDelete" />')">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.piginfo.removalExceptSales.delete" text="Delete" /></a></button>
						<button type="button" ng-show="row.groupEventId==0" class="btn btn-edit btn-xs" ng-click="deleteRemovalExceptSalesData(row,'<spring:message code="label.piginfo.removalExceptSales.delete.pigInfo" text="PigInfo Delete" />')">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.piginfo.removalExceptSales.delete" text="Delete" /></a></button>
						<button type="button" ng-show="row.pigInfoId==0" class="btn btn-edit btn-xs" ng-click="editRemovalData(row)">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a>
						</button>
						<button type="button" ng-show="row.groupEventId==0" class="btn btn-edit btn-xs" ng-click="editRemovalData(row)">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a>
						</button>
					</td>				
				</tr>
				</tbody>		
				<tr style="background-color: #f7b781">
					<td colspan="14">
						<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPages" ></div>
					</td>
				</tr>
			</table>
			</div>
		</div>
		
		<input type="hidden" name="removalIdEntered" id="removalIdEntered"/>
		<input type="hidden" name="removalGeneratedId" id="removalGeneratedId"/>
		<input type="hidden" name="removalExceptSalesId" id="removalExceptSalesId"/>			
		<input type="hidden" name="companyId" id="companyId"/>
		<input type="hidden" name="removalTypeId1" id="removalTypeId1"/>
		<input type="hidden" name="removalSalesEventId" id="removalSalesEventId"/>
		
		
		
		
		<div class="content" ng-show="transferEventFlag && dataLoaded">
		<h3><spring:message code='label.removalExceptSales.add.transferdetail'  text='Transfer Details'/></h3>
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection3" st-safe-src="transferList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>
						<!--  <th style="width:10%"><spring:message code="label.groupEventDetail.number" text="Number" /></th>-->
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.groupEventId" text="Group Event" /></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.removalDateTime" text="Removal Date" /></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.numberOfPigs" text="Number Of Pigs" /></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.weightInKgs" text="Weight" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.transferredFromGroup" text="Received From Group / Transferred To Group" /></th>
						<th style="width:25%"><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection3 track by $index">
					<!-- <td style="width:10%">{{$index+1}}</td>-->
					<td style="width:25%">{{row.groupIdStr}}</td>
					<td style="width:10%">{{DateUtils.getFormatedDate(row.removalDateTime)}}</td>
					<td style="width:10%">{{row.numberOfPigs}}</td>
					<td style="width:25%">{{row.weightInKgs}}</td>
					<td style="width:25%">{{row.fromGroupIdStr}}</td>
					<td style="width:10%">{{row.remarks}}</td>
				</tr>
				</tbody>		
				<tr style="background-color: #f7b781">
					<td colspan="14">
						<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPages" ></div>
					</td>
				</tr>
			</table>
			</div>
		</div>
		
		
		
		<div ng-show="salesEventFlag && dataLoaded">
		<h3><spring:message code='label.piginfo.removalExceptSales.salesEvent.header'  text='Removal Sales Event'/></h3>
		<div class="content">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection1" st-safe-src="salesEventList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>						
						<!--  <th style="width:10%"><spring:message code="label.groupEventDetail.number" text="Number" /></th>-->
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.groupEventId" text="Group Event" /></th>
						<th style="width:10%"><spring:message code='label.piginfo.removalExceptSales.salesDateTime'  text='Sales Date'/></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.numberOfPigs" text="Number Of Pigs" /></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.weightInKgs" text="Weight" /></th>
						<th style="width:10%"><spring:message code='label.piginfo.removalExceptSales.revenueUsd'  text='Revenue($)'/></th>
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.pigInfoId" text="Pig Info" /></th>
						<th style="width:25%"><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></th>	
						<th style="width:10%"><spring:message code='label.piginfo.removalExceptSales.ticketNumber'  text='Ticket Number' /></th>
						<th style="width:10%"><spring:message code='label.piginfo.removalExceptSales.invoiceId'  text='Invoice Number'/></th>
						<th style="width:10%"><spring:message code='label.piginfo.removalExceptSales.soldTo'  text='Sold To'/></th>					
						<th style="width:10%"><spring:message code="label.piginfo.removalExceptSales.delete" text="Delete" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection1 track by $index">
					<!--  <td style="width:10%">{{$index+1}}</td>-->
					<td style="width:25%">{{groupEventList[row.groupEventId].groupId}}</td>
					<td style="width:10%">{{DateUtils.getFormatedDate(row.salesDateTime)}}</td>
					<td style="width:10%">{{row.numberOfPigs}}</td>
					<td style="width:25%">{{row.weightInKgs}}</td>
					<td style="width:25%">{{row.revenueUsd}}</td>
					<td style="width:25%">{{pigInfoList[row.pigInfoId].pigId}}</td>
					<td style="width:25%">{{row.remarks}}</td>
					<td style="width:10%">{{row.ticketNumber}}</td>
					<td style="width:10%">{{row.invoiceId}}</td>					
					<td style="width:25%">{{row.soldTo}}</td>
					

					<td style="width: 8%">
						<button type="button" ng-show="row.pigInfoId==0" class="btn btn-edit btn-xs" ng-click="deleteSalesEventDetailsData(row,'<spring:message code="label.piginfo.removalExceptSales.delete.groupEvent" text="GroupDelete" />')">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.piginfo.removalExceptSales.delete" text="Delete" /></a></button>
						<button type="button" ng-show="row.groupEventId==0" class="btn btn-edit btn-xs" ng-click="deleteSalesEventDetailsData(row,'<spring:message code="label.piginfo.removalExceptSales.delete.pigInfo" text="PigInfo Delete" />')">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.piginfo.removalExceptSales.delete" text="Delete" /></a></button>
						<button type="button" ng-show="row.pigInfoId==0" class="btn btn-edit btn-xs" ng-click="editRemovalData(row)">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a>
						</button>
						<button type="button" ng-show="row.groupEventId==0" class="btn btn-edit btn-xs" ng-click="editRemovalData(row)">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a>
						</button>
						<button type="button" ng-show="row.groupEventId==0" class="btn btn-edit btn-xs" ng-click="editRemovalData(row)">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a>
						</button>
					</td>				
				</tr>
				</tbody>		
				<tr style="background-color: #f7b781">
					<td colspan="14">
						<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPages" ></div>
					</td>
				</tr>
			</table>
			</div>
		</div>
	</div>
	</form>
		
		<div class="md-overlay"></div>
	</div>	
	 