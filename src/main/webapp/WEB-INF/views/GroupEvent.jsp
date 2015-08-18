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
			            	<h3> <spring:message code='label.piginfo.pregnancyeventform.search.heading'  text='Search'/></h3>
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
                  
                   <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
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
                </div>
                <div class="content">
                  <form name="groupEventForm" novalidate angular-validator>
                  <input type=hidden name="id" ng-model="groupEvent.id"/>
				  <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.groupId'  text='Group Id'/></label>
                      <input type="text" ng-model="groupEvent.groupId" id="groupId" name="groupId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.groupEventForm.groupId.placeholder'  text='Enter Group Id'/>" required
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
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.groupCloseDateTime'  text='Group Close Date'/></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="groupCloseDateTime" name="groupCloseDateTime" ng-model="groupEvent.groupCloseDateTime" readonly="" class="form-control" format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div> 
					
					 <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></label>
                      <input type="text" ng-model="groupEvent.remarks" id="remarks" name="remarks"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/>
                   </div>

					<button class="btn btn-primary" ng-click="addGroupEvent()" type="submit" ng-disabled="inValidPigIdFromServer"><spring:message code='label.piginfo.groupEventform.submit'  text='Submit'/></button>
                    <button class="btn btn-default" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.pregnancyeventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="changeGroupEventStatus()" ng-show="groupEvent.id != null && groupEvent.id > 0 && groupEvent.active" ><spring:message code='label.piginfo.groupEventform.deActivate'  text='De-Activate'/></button> <button type="button" class="btn btn-success pull-right" ng-click="changeGroupEventStatus()" ng-show="groupEvent.id != null && groupEvent.id > 0 && !groupEvent.active" ><spring:message code='label.piginfo.groupEventform.Activate'  text='Activate'/></button>					
				  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		  
		<button type="button" ng-click="addGroupEventDetailData(groupEvent.groupId)" class="btn btn-sm btn btn-primary" ng-show="groupEvent.id != null && groupEvent.id > 0 && groupEvent.active">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.groupEventDetail.addNewGroupEventDetail" text="Add New Event Detail" />
		</button>
	<form name="groupEventFormAdd" method="post">	
		<div class="content" ng-show="groupEvent.id != null && groupEvent.id > 0 && groupEvent.active">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="groupEventDetailList" st-safe-src="groupEventDetailList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #3399CC">
					<tr>
						<th style="width:10%"><spring:message code="label.groupEventDetail.number" text="Number" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.origin" text="Origin" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.dateOfEntry" text="Date Of Entry" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.numberOfPigs" text="Number Of Pigs" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.weightInKgs" text="Weight In Kgs" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.inventoryAdjustment" text="Inventory Adjustment" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.roomId" text="Room" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.employeeGroupId" text="Employee Group" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.phaseOfProductionTypeId" text="Phase Of Production" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.remarks" text="Remarks" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.edit" text="Edit" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in groupEventDetailList track by $index">
					<td style="width:10%">{{$index}}</td>
					<td style="width:10%">{{origin}}</td>
					<td style="width:25%">{{row.dateOfEntry}}</td>
					<td style="width:25%">{{row.numberOfPigs}}</td>
					<td style="width:25%">{{row.weightInKgs}}</td>
					<td style="width:10%">{{row.inventoryAdjustment}}</td>
					<td style="width:10%">{{roomList[row.roomId]}}</td>
					<td style="width:10%">{{row.employeeGroupId}}</td>
					<td style="width:10%">{{phaseOfProductionType[row.phaseOfProductionTypeId]}}</td>
					<td style="width:10%">{{row.remarks}}</td>
					<td style="width: 8%">
						<button type="button" class="btn btn-edit btn-xs" ng-click="editGroupEventDetailsData(row)">
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
	</form>	  		
		<div class="md-overlay"></div>
</div>	