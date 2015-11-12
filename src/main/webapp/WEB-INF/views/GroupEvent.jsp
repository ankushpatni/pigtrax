<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.pregnancyeventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="GroupEventController" ng-init="setCompanyId('${CompanyId}','${searchedGroupid}')">
   <div class="row">
	 		  <div class="col-sm-3 col-md-3"></div> 
	 		  <div class="col-sm-6 col-md-6">
		 		  <div class="block-flat">
					  <form name="groupEventSearchForm" >
			 		     <div class="head">
			            	<h3> <spring:message code='label.piginfo.groupEventForm.search.heading'  text='Search Group Events'/></h3>
			               	<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.groupEventForm.search.data.errormessage' text='Group event information not found for the search criteria'/></p>
					   		 <input type="text" name="search" ng-model="searchText" ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.groupEventForm.groupId.invalidMessage' text='Only Numeric values are allowed' />'" placeholder="<spring:message code='label.piginfo.groupEventForm.search.placeholder'  text='Search by Group Id ...'/>" class="form-control" style="width:90%;display:inline">
							 <button type="button" class="btn btn-primary active" ng-click="getGroupEventInformation(searchText)"><i class="fa fa-search"></i></button>
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
                  <h3><spring:message code='label.piginfo.groupEventForm.groupEvent'  text='Group Event'/></h3>
                   <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage || entryEventStatusChangeSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.groupEventform.submit.success.message' text='Group Event information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                   <div class="alert alert-danger alert-white rounded"  ng-show="groupEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.groupEventform..duplicate.error.message' text='A Group event record already exists with the same Group Id'/>
                  </div>
				  <div class="alert alert-success alert-white rounded"  ng-show="moveEntryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.groupEventform.submit.success.messageMove' text='Group Event information has been moved successfully to new Group Event'/>
                  </div>				 
                </div>
                <div class="content">
                  <form name="groupEventForm" novalidate angular-validator my-reset>
                  <input type=hidden name="id" ng-model="groupEvent.id"/>
				  <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.groupId'  text='Group Id'/></label>
                      <label ng-show="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage">{{groupEvent.groupId}}</label>
                      <input type="text" ng-hide="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage" ng-model="groupEvent.groupId" id="groupId" name="groupId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.groupEventForm.groupId.placeholder'  text='Enter Group Id'/>" required
                      required-message="'<spring:message code='label.piginfo.groupEventForm.groupId.requiredMessage' text='Group Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.groupEventForm.groupId.invalidMessage' text='Only Numeric values are allowed' />'"  ng-focus="clearMessages()"/>
                   </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.groupStartDateTime'  text='Group Start Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="groupStartDateTime" name="groupStartDateTime" ng-model="groupEvent.groupStartDateTime" readonly="" class="form-control" format-date required-message="'<spring:message code='label.piginfo.groupEventForm.groupStartDateTime.requiredMessage' text='Group Start Date is required' />'"/><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
						</div>
                    </div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="groupdaterequired" ><spring:message code='label.piginfo.groupEventForm.groupStartDate.requiredMessage' text='Group Start Date is required' /></label>
					</div>					
                    <div class="form-group" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.currentInventory == 0)">
                      <label><spring:message code='label.piginfo.groupEventForm.groupCloseDateTime'  text='Group Close Date'/></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="groupCloseDateTime" name="groupCloseDateTime" ng-model="groupEvent.groupCloseDateTime" readonly="" class="form-control" format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="groupStartEndDateError" ><spring:message code='label.piginfo.groupEventForm.groupStartEndDateError.requiredMessage' text='Group Close Date can not be less than Group Start date ' /></label>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="groupenddaterequired" ><spring:message code='label.piginfo.groupEventForm.groupEndDate.requiredMessage' text='Group Close Date is required' /></label>
					</div>					
					 <div class="form-group">
                      <label><spring:message code='label.groupEventDetail.phaseOfProductionTypeId'  text='Phase Of Production'/></label>
                       <select ng-hide="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage" class="form-control"  required required-message="'<spring:message code='label.groupEventDetail.phaseOfProduction.required' text='Phase Of Production is required' />'" name="phaseOfProductionTypeId" id="phaseOfProductionTypeId" ng-model="groupEvent.phaseOfProductionTypeId"   
                         ng-options="k as v for (k, v) in phaseOfProductionType"> </select>
                         <label ng-show="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage"> :  {{phaseOfProductionType[groupEvent.phaseOfProductionTypeId]}}</label>                       
                    </div>
					<div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.currentInventory'  text='Current Inventory'/></label>
                      <label ng-show="(groupEvent.currentInventory >= 0)"> : {{groupEvent.currentInventory}}</label>
                      <%-- <input type="text" ng-model="groupEvent.remarks" id="currentInventory" name="currentInventory"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/> --%>
					   <label ng-hide="(groupEvent.currentInventory >= 0)"> : 0</label>
                   </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.previousGroupId'  text='Previous Group'/></label>
                     <%--  <input type="text" ng-model="groupEvent.previousGroupId" id="previousGroupId" name="previousGroupId"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/> --%>
                       <label ng-show="(groupEvent.previousGroupId != null)"> : {{groupEvent.previousGroupId}}</label>
					   <label ng-hide="(groupEvent.previousGroupId != null)"> -- </label>
                   </div>
				    <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.groupEventDerived'  text='Group Event Derived'/></label>
                        <label> : {{followedGroupIdString}}</label>
                   </div>
                   <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></label>
                      <input type="text" ng-model="groupEvent.remarks" id="remarks" name="remarks"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/>
                   </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.saleEventId'  text='Sale Event'/></label>
                     <%--  <input type="text" ng-model="groupEvent.previousGroupId" id="previousGroupId" name="previousGroupId"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/> --%>
                       <label ng-show="(groupEvent.saleEventId != null)"> : {{groupEvent.saleEventId}}</label>
					   <label ng-hide="(groupEvent.saleEventId != null)"> -- </label>
                   </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.removalEventId'  text='Removal Event'/></label>
                     <%--  <input type="text" ng-model="groupEvent.previousGroupId" id="previousGroupId" name="previousGroupId"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/> --%>
                       <label ng-show="(groupEvent.removalEventId != null)"> : {{groupEvent.removalEventId}}</label>
					   <label ng-hide="(groupEvent.removalEventId != null)"> -- </label>
                   </div>
                   
                   <div class="form-group" ng-show="editGroupEventInventory">
                      <label><spring:message code='label.piginfo.groupEventForm.inventoryAdjustment'  text='Adjustment Inventory'/></label>
                     <input type="text" ng-model="groupEvent.inventoryAdjustment" id="inventoryAdjustment" name="inventoryAdjustment"  class="form-control" maxlength="10" placeholder="<spring:message code='label.piginfo.groupEventForm.inventoryAdjustment.placeholder'  text='Enter Inventory Adjustment'/>" 
                       ng-focus="clearMessages()"/>
                   </div>
                   <div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="inventoryAdjustmentError" ><spring:message code='label.piginfo.groupEventform.inventoryAdjustmentError' text='Inventory Adjustment can not be greater than no of pigs in group.' /></label>
					</div>
					
					<button class="btn btn-primary" ng-click="addGroupEvent()" type="submit" ng-hide="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage "><spring:message code='label.piginfo.groupEventform.add'  text='Add'/></button>
					<button class="btn btn-primary" ng-click="addGroupEvent()" type="submit" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.active) || entryEventSuccessMessage"><spring:message code='label.piginfo.groupEventform.edit'  text='Edit'/></button>
					<button class="btn btn-primary" ng-click="moveToAnotherGroup()" type="submit" ng-show="groupEvent.id != null && groupEvent.id > 0 && groupEvent.currentInventory != 0"><spring:message code='label.piginfo.groupEventform.moveToAnotherGroup'  text='Move To Another Group'/></button>
					<!-- <button class="btn btn-primary" ng-click="editGroupEventInventoryAmount()" type="button" ng-show="groupEvent.id != null && groupEvent.id > 0 && groupEvent.currentInventory != 0 && groupEvent.active"><spring:message code='label.piginfo.groupEventform.adjustInventory'  text='Adjust Inventory'/></button> -->
                    <button class="btn btn-default" type="button" ng-click="resetForm()" data-toggle="modal" data-target="#transportJourneyModal"><spring:message code='label.piginfo.pregnancyeventform.cancel'  text='Clear Form'/></button> 
					
                    <button type="button" class="btn btn-danger pull-right" ng-click="changeGroupEventStatus(false)" ng-show="groupEvent.id != null && groupEvent.id > 0 && groupEvent.currentInventory==0 && groupEvent.active" ><spring:message code='label.piginfo.groupEventform.deActivate'  text='De-Activate'/></button>
					
                    <button type="button" class="btn btn-success pull-right" ng-click="changeGroupEventStatus(true)" ng-show="groupEvent.id != null && groupEvent.id > 0 && groupEvent.currentInventory==0 && !groupEvent.active" ><spring:message code='label.piginfo.groupEventform.Activate'  text='Activate'/></button>					
				  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		  
		<button type="button" ng-click="addGroupEventDetailData()" class="btn btn-sm btn btn-primary" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.active) || entryEventSuccessMessage">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.groupEventDetail.addAddPigstoGroup" text="Add Pigs to Group" />
		</button>
	<form name="groupEventFormAdd" method="post">	
		<div class="content" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.active) || entryEventSuccessMessage">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection" st-safe-src="groupEventDetailList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #3399CC">
					<tr>
						<th style="width:5%"><spring:message code="label.groupEventDetail.number" text="Number" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.barn" text="Barn" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.dateOfEntry" text="Date Of Entry" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.numberOfPigs" text="Number Of Pigs" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.weightInKgs" text="Weight In Kgs" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.inventoryAdjustment" text="Inventory Adjustment" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.roomId" text="Room" /></th>
						<th style="width:35%"><spring:message code="label.groupEventDetail.remarks" text="Remarks" /></th>
						<th style="width:5%"><spring:message code="label.groupEventDetail.edit" text="Edit" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:5%">{{$index+1}}</td>
					<td style="width:7%">{{barnList[row.barnId]}}</td>
					<td style="width:10%">{{row.dateOfEntry}}</td>
					<td style="width:7%">{{row.numberOfPigs}}</td>
					<td style="width:7%">{{row.weightInKgs}}</td>
					<td style="width:7%">{{row.inventoryAdjustment}}</td>
					<td style="width:7%">{{roomList[row.roomId]}}</td>
					<td style="width:35%">{{row.remarks}}</td>
					<td style="width: 5%">
						<button type="button" class="btn btn-edit btn-xs" ng-click="addGroupEventDetailData(row.id)">
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