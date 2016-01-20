<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.groupEventForm.pigletinformation'  text='Piglet Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="GroupEventController" ng-init="setCompanyId('${CompanyId}','${searchedGroupid}', '${searchPremiseId}')">
   <div class="row">
	 		  <div class="col-sm-3 col-md-3"></div> 
	 		  <div class="col-sm-6 col-md-6">
		 		  <div class="block-flat">
					  <form name="groupEventSearchForm" >
			 		     <div class="head">
			            	<h3> <spring:message code='label.piginfo.groupEventForm.search.heading'  text='Search Group Events'/></h3>
			               	<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.groupEventForm.search.data.errormessage' text='Group event information not found for the search criteria'/></p>
			               	<div  class="form-group">
				              <select  class="form-control"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  >
							  <option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
				              <option ng-repeat="premise in farmList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
				              </select>
				             </div>
           					<div  class="form-group">  	
					   		 <input type="text" name="search"  ng-enter="getGroupEventInformation(searchText, selectedPremise)" ng-model="searchText" ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.groupEventForm.groupId.invalidMessage' text='Only Numeric values are allowed' />'" placeholder="<spring:message code='label.piginfo.groupEventForm.search.placeholder'  text='Search by Group Id ...'/>" class="form-control" style="width:90%;display:inline">
							 <button type="button" class="btn btn-primary active" ng-click="getGroupEventInformation(searchText, selectedPremise)"><i class="fa fa-search"></i></button>
							 </div>
			          	</div>
					  </form>	
				 </div>
	          </div>
	 		  <div class="col-sm-3 col-md-3"></div>
	  </div>			
 		
          <div class="row" >
		  <div class="col-sm-2 col-md-2"></div>
            <div class="col-sm-8 col-md-8">
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
                      <label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="premiseId" id="premiseId" ng-change="getRooms(true)" ng-model="groupEvent.premiseId" required required-message="'<spring:message code='label.piginfo.farroweventform.premise.requiredmessage' text='Premise is required' />'">
                       	<option ng-repeat="farm in farmList" value="{{farm.id}}" ng-value="farm.id" ng-selected="groupEvent.premiseId == farm.id">{{farm.name}}</option>
                        </select>
                    </div>
                  
                  <div class="form-group">
                      <label><spring:message code='label.groupEventDetail.roomId'  text='Room'/><span style='color: red'>*</span></label>
                      <div ng-dropdown-multiselect="" options="roomValues" selected-model="groupEvent.roomIds" ></div>			
                    </div>
                  
				  <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.groupId'  text='Group Id'/><span style='color: red'>*</span></label>
                      <label ng-show="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage">{{groupEvent.groupId}}</label>
                      <input type="text" ng-hide="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage" ng-model="groupEvent.groupId" id="groupId" name="groupId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.groupEventForm.groupId.placeholder'  text='Enter Group Id'/>" required
                      required-message="'<spring:message code='label.piginfo.groupEventForm.groupId.requiredMessage' text='Group Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.groupEventForm.groupId.invalidMessage' text='Only Numeric values are allowed' />'"  ng-focus="clearMessages()"/>
                   </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.groupStartDateTime'  text='Group Start Date'/><span style='color: red'>*</span></label>	<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>					                      
                      <input type="text" class="form-control" ng-model="groupEvent.groupStartDateStr" mask="19/39/2999" mask-validate='true' ng-blur="dateCheck(groupEvent.groupStartDateStr, 'groupStartDate')"/>
                                            
                    </div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="groupdaterequired" ><spring:message code='label.piginfo.groupEventForm.groupStartDate.requiredMessage' text='Group Start Date is required' /></label>
					</div>					
                    <div class="form-group" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.currentInventory == 0)">
                      <label><spring:message code='label.piginfo.groupEventForm.groupCloseDateTime'  text='Group Close Date'/></label><i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>                      
                      <input type="text" class="form-control" ng-model="groupEvent.groupCloseDateStr" mask="19/39/2999" mask-validate='true' ng-blur="dateCheck(groupEvent.groupCloseDateStr, 'groupCloseDate')"/>                      
                    </div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="groupStartEndDateError" ><spring:message code='label.piginfo.groupEventForm.groupStartEndDateError.requiredMessage' text='Group Close Date can not be less than Group Start date ' /></label>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="groupenddaterequired" ><spring:message code='label.piginfo.groupEventForm.groupEndDate.requiredMessage' text='Group Close Date is required' /></label>
					</div>					
					 <div class="form-group">
                      <label><spring:message code='label.groupEventDetail.phaseOfProductionTypeId'  text='Phase Of Production'/><span style='color: red'>*</span></label>
                       <select ng-hide="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage" class="form-control"  required required-message="'<spring:message code='label.groupEventDetail.phaseOfProduction.required' text='Phase Of Production is required' />'" name="phaseOfProductionTypeId" id="phaseOfProductionTypeId" ng-model="groupEvent.phaseOfProductionTypeId"   
                         ng-options="k as v for (k, v) in phaseOfProductionType"> </select>
                         <label ng-show="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage"> :  {{phaseOfProductionType[groupEvent.phaseOfProductionTypeId]}}</label>                       
                    </div>
                    
                    
                     <div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.remark'  text='Remark'/></label>
                      <input type="text" ng-model="groupEvent.remarks" id="remarks" name="remarks"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/>
                   </div> 
					<div class="form-group">
                      <label><spring:message code='label.piginfo.groupEventForm.currentInventory'  text='Current Inventory'/></label>
                      <label ng-show="(groupEvent.currentInventory >= 0)"> : {{groupEvent.currentInventory}}</label>
                      <%-- <input type="text" ng-model="groupEvent.remarks" id="currentInventory" name="currentInventory"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.groupEventForm.remark.placeholder'  text='Enter Remark'/>" 
                       ng-focus="clearMessages()"/> --%>
					   <label ng-hide="(groupEvent.currentInventory >= 0)"> : 0</label>
                   </div>                   
				  
					<button class="btn btn-success" ng-click="addGroupEvent()" type="submit" ng-hide="(groupEvent.id != null && groupEvent.id > 0) || entryEventSuccessMessage "><spring:message code='label.piginfo.groupEventform.add'  text='Add'/></button>
					<button class="btn btn-success" ng-click="addGroupEvent()" type="submit" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.active) || entryEventSuccessMessage"><spring:message code='label.piginfo.groupEventform.edit'  text='Edit'/></button>
					<button type="button" ng-click="addGroupEventDetailData()" class="btn btn-success" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.active) || entryEventSuccessMessage">
						<i class="glyphicon glyphicon-plus">
						</i> <spring:message code="label.groupEventDetail.addAddPigstoGroup" text="Add Pigs to Group" />
					</button>
					<button class="btn btn-success" ng-click="promoteToFinish()" type="submit" ng-confirm-click="<spring:message code='label.piginfo.groupEventform.promoteToFinish.confirmmessage'  text='Are you sure you want to promote this group to finish phase?'/>" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.phaseOfProductionTypeId == 1 && groupEvent.active && groupEvent.currentInventory>0) "><spring:message code='label.piginfo.groupEventform.promoteToFinish'  text='Promote to Finish'/></button>
					<button class="btn btn-success" ng-click="moveBackToNursery()" type="submit" ng-confirm-click="<spring:message code='label.piginfo.groupEventform.moveToNursery.confirmmessage'  text='Are you sure you want to move this group back to to nursery phase?'/>" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.phaseOfProductionTypeId == 2 && groupEvent.active && groupEvent.currentInventory>0) "><spring:message code='label.piginfo.groupEventform.moveToNursery'  text='Move back to Nursery'/></button>
					<button class="btn btn-success" data-toggle="modal" ng-click="transferToGroupInit()" data-target="#transferToGroupModal"  type="submit" ng-confirm-click="<spring:message code='label.piginfo.groupEventform.transferToNursery.confirmmessage'  text='Are you sure you want to transfer?'/>" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.phaseOfProductionTypeId == 1 && groupEvent.active && groupEvent.currentInventory>0) "><spring:message code='label.piginfo.groupEventform.transferToNursery'  text='Transfer to Nursery'/></button>
					<button class="btn btn-success" data-toggle="modal" ng-click="transferToGroupInit()" data-target="#transferToGroupModal"   type="submit" ng-confirm-click="<spring:message code='label.piginfo.groupEventform.transferToFinish.confirmmessage'  text='Are you sure you want to transfer?'/>" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.phaseOfProductionTypeId == 2 && groupEvent.active && groupEvent.currentInventory>0) "><spring:message code='label.piginfo.groupEventform.transferToFinish'  text='Transfer to Finish'/></button>
					<button class="btn btn-success" data-toggle="modal" ng-click="transferToGroupInit()" data-target="#transferToGroupModal"   type="submit" ng-confirm-click="<spring:message code='label.piginfo.groupEventform.transferToFinish.confirmmessage'  text='Are you sure you want to transfer?'/>" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.phaseOfProductionTypeId == 3 && groupEvent.active && groupEvent.currentInventory>0) "><spring:message code='label.piginfo.groupEventform.transferToPhase1'  text='Transfer to Wean to Finish Phase1'/></button>
					<button class="btn btn-success" ng-click= "promoteToPhase2()" data-target="#phase2GroupModal" data-toggle="modal"  type="submit" ng-confirm-click="<spring:message code='label.piginfo.groupEventform.promoteToPhase2.confirmmessage'  text='Are you sure you want to promote this group to phase 2?'/>" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.phaseOfProductionTypeId == 3 && groupEvent.active && groupEvent.currentInventory>0) "><spring:message code='label.piginfo.groupEventform.promoteToPhase2'  text='Promote to Phase 2'/></button>
					<button class="btn btn-success" ng-click= "undoWeanToFinishPhase2()"  type="submit" ng-confirm-click="<spring:message code='label.piginfo.groupEventform.undoPhase2Movement.confirmmessage'  text='Are you sure you want undo the Phase 2 promotion?'/>" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.phaseOfProductionTypeId == 4 && groupEvent.active && groupEvent.currentInventory>0) "><spring:message code='label.piginfo.groupEventform.undoPhase2Movement'  text='Undo Phase2 Promotion'/></button>
					
					
                    <button class="btn btn-warning" type="button" ng-click="resetForm()" data-toggle="modal" data-target="#transportJourneyModal"><spring:message code='label.piginfo.pregnancyeventform.cancel'  text='Clear Form'/></button> 
					
                    <button type="button" class="btn btn-danger pull-right" ng-click="changeGroupEventStatus(false)" ng-show="groupEvent.id != null && groupEvent.id > 0 && groupEvent.currentInventory==0 && groupEvent.active" ><spring:message code='label.piginfo.groupEventform.deActivate'  text='De-Activate'/></button>
					
                    <button type="button" class="btn btn-success pull-right" ng-click="changeGroupEventStatus(true)" ng-show="groupEvent.id != null && groupEvent.id > 0 && groupEvent.currentInventory==0 && !groupEvent.active" ><spring:message code='label.piginfo.groupEventform.Activate'  text='Activate'/></button>					
				  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-2 col-md-2">        
            </div>
          </div>
		  
		
	<form name="groupEventFormAdd" method="post">	
		<div class="content" ng-show="(groupEvent.id != null && groupEvent.id > 0 && groupEvent.active) || entryEventSuccessMessage">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection" st-safe-src="groupEventDetailList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>
						<th style="width:5%"><spring:message code="label.groupEventDetail.number" text="Number" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.dateOfEntry" text="Date Of Entry" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.numberOfPigs" text="Number Of Pigs" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.weightInKgs" text="Weight In Kgs" /></th>
						<th style="width:35%"><spring:message code="label.groupEventDetail.remarks" text="Remarks" /></th>
						<th style="width:35%"><spring:message code="label.groupEventDetail.transferredFromGroup" text="From Group" /></th>
						<th style="width:5%"><spring:message code="label.groupEventDetail.edit" text="Edit" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:5%">{{$index+1}}</td>
					<td style="width:10%">{{DateUtils.getFormatedDate(row.dateOfEntry)}}</td>
					<td style="width:7%">{{row.numberOfPigs}}</td>
					<td style="width:7%">{{row.weightInKgs}}</td>
					<td style="width:35%">{{row.remarks}}</td>
					<td style="width:35%">{{row.fromGroupIdStr}}</td>
					<td style="width: 5%">
						<button type="button" class="btn btn-edit btn-xs" ng-click="addGroupEventDetailData(row.id)">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
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
		
		
		
		<div class="content" ng-show="(groupEvent.id != null && groupEvent.id > 0)">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedPhaseCollection" st-safe-src="groupEvent.phaseChangeList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>
						<th ><spring:message code="label.groupPhaseChange.PhaseOfProductionType" text="Phase of Production Type" /></th>
						<th ><spring:message code="label.groupPhaseChange.phaseStartDate" text="Phase Start Date" /></th>
						<th ><spring:message code="label.groupPhaseChange.phaseEndDate" text="Phase End Date" /></th>
						<th ><spring:message code="label.groupPhaseChange.rooms" text="Room(s)" /></th>
						<th ><spring:message code="label.groupPhaseChange.premiseId" text="Premise Id" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedPhaseCollection track by $index">					
					<td>{{phaseOfProductionType[row.phaseOfProductionTypeId]}}</td>
					<td>{{DateUtils.getFormatedDate(row.phaseStartDate)}}&nbsp;{{row.phaseStartTimeStr}}</td>
					<td>{{DateUtils.getFormatedDate(row.phaseEndDate)}}&nbsp;{{row.phaseEndTimeStr}}</td>
					<td><p ng-repeat="room in row.rooms">{{room}}</p></td>
					<td>{{row.premise}}</td>
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
		
		
		
		<input type="hidden" name="searchedGroupid" id="searchedGroupid"/>
		<input type="hidden" name="searchPremiseId" id="searchPremiseId"/>		
		<input type="hidden" name="groupEventId" id="groupEventId"/>			
		<input type="hidden" name="groupGeneratedIdSeq" id="groupGeneratedIdSeq"/>
		<input type="hidden" name="companyId" id="companyId"/>		
		<input type="hidden" name="groupStartDateTimeAdd" id="groupStartDateTimeAdd"/>
		
	</form>
	
	
	<!-- -- Transfer to group modal -->
	 <div id="transferToGroupModal" class="modal colored-header warning custom-width" ng-init="transferToGroupInit()">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.groupEvent.transferToGroup.heading'  text='Transfer to Group'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
                      </div>
                      
                      <div class="modal-body form" >
                      	 <p class="color-danger" ng-show="transferToGroupSearchDataError"><spring:message code='label.groupEvent.search.data.errormessage' text='Group event information not found for the search criteria'/></p>
                         <p class="color-danger" ng-show="transferToGroupSearchError"><spring:message code='label.groupEvent.transferToGroupSearchError.message' text='Not possible to transfer to the same group'/></p>
                         <p class="color-danger" ng-show="invalidTransferPigNum"><spring:message code='label.groupEvent.invalidTransferPigNum.message' text='Number of pigs can not be more than the inventory count'/></p>
                         <h4> <spring:message code='label.groupEvent.transferToGroup.searchtext'  text='Search a group to transfer'/></h4>
                         
                         <div  class="form-group">
			             <label><spring:message code='label.groupEventDetail.phaseOfProductionTypeId'  text='Phase Of Production'/></label>
			             <label>{{phaseOfProductionType[groupEvent.phaseOfProductionTypeId]}}</label>
			             </div>
                         
                         <div  class="form-group">
			              <select  class="form-control"  name="transferToPremise" id="transferToPremise" ng-model="transferToPremise"  >
						  <option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
			              <option ng-repeat="premise in farmList" value="{{premise.id}}" ng-value="premise.id" ng-selected="transferToPremise == premise.id">{{premise.name}}</option>
			              </select>
			             </div>
          					<div  class="form-group">  	
				   		 <input type="text" name="search"  ng-enter="getGroupEventInformation(transferToGroup, transferToPremise)" ng-model="transferToGroupTxt" ng-pattern="/^[a-z0-9]+$/i"
								invalid-message="'<spring:message code='label.piginfo.groupEventForm.groupId.invalidMessage' text='Only Numeric values are allowed' />'" 
								placeholder="<spring:message code='label.piginfo.groupEventForm.search.placeholder'  text='Search by Group Id ...'/>" 
								class="form-control" ng-enter="getTransferToGroupEventInformation(transferToGroupTxt, transferToPremise)">
						 <button type="button" class="btn btn-primary active" ng-click="getTransferToGroupEventInformation(transferToGroupTxt, transferToPremise)"><i class="fa fa-search"></i></button>
						 </div>
	                    
	                    <form name="groupEventTransferForm" novalidate angular-validator>
	                     <div ng-if="transferGroupEventData != null && transferGroupEventData.id > 0">
	                        <div class="form-group">
	                           <label><spring:message code='label.piginfo.groupEventForm.currentInventory'  text='Current Inventory'/></label>
	                           <label ng-show="(groupEvent.currentInventory > 0)"> : {{transferGroupEventData.currentInventory}}</label>
	                           <label ng-hide="(groupEvent.currentInventory > 0)"> : ---</label>
	                        </div>
	                        <div class="form-group">	
	                        	<label><spring:message code='label.piginfo.groupEventForm.transferPigNum.message'  text='Number of pigs to be transferred'/><span style='color: red'>*</span></label>
	                        	 <input class="form-control" type="text" placeholder="<spring:message code='label.groupEventDetail.numberOfPigs' text='Number of Pigs' />" 
	                        	 name="transferredPigNum" ng-model="transferGroupEventData.transferredPigNum" maxlength="8" required 
	                        	 required-message="'<spring:message code='label.groupEventDetail.numberOfPigs.required' text='Number Of Pigs required' />'"
	                        	  ng-pattern="/^\d{1,8}?$/i"  invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
	                        </div>
	                        
	                        <div class="form-group">	
	                        	<label><spring:message code='label.piginfo.groupEventForm.transferPigWt.message'  text='Weight of Pigs (kg)'/><span style='color: red'>*</span></label>
	                        	 <input class="form-control" type="text" placeholder="<spring:message code='label.groupEventDetail.weightInKgs'  text='Weight In Kgs'/>"
	                        	 name="transferredPigWt" ng-model="transferGroupEventData.transferredPigWt" maxlength="8"  required 
	                        	 required-message="'<spring:message code='label.groupEventDetail.weightInKgs.required' text='Weight In Kgs is required' />'" 
	                        	 ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>
	                        </div>
	                     </div>
                       </form>
                      </div>
                      <div class="modal-footer">
						<button type="button" class="btn btn-success btn-flat" data-dismiss="modal" ng-click="transferToGroup()" ng-if="transferGroupEventData != null && transferGroupEventData.id > 0"><spring:message code='label.groupEvent.transferToGroup.Transfer'  text='Transfer'/></button>
						
                        <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>                        
                      </div>
                    </div>
                  </div>
	<!-- -- End of Transfer to group modal -->
	
	
	
	<!-- -- Transfer to group modal -->
	 <div id="phase2GroupModal" class="modal colored-header warning custom-width" style="{height:300px}">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.groupEvent.promoteToPhase2.heading'  text='Wean to Finish Phase 2'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
                      </div>
                      
                      <div class="modal-body form" >
                        
                        <table >
                         <tr><td>
	                      <label><spring:message code='label.groupEvent.promoteToPhase2.selectRooms'  text='Select Rooms'/><span style='color: red'>*</span></label>
	                      </td>
	                      <td>
	                      <div ng-dropdown-multiselect="" options="allRoomValues" selected-model="updatedRooms" ></div>
	                      </td>
	                      </tr>	
                       </table>
                      </div>
                      <div class="modal-footer">
						<button class="btn btn-success" ng-click="confirmPromoteToPhase2()" type="submit" ><spring:message code='label.piginfo.groupEventform.edit'  text='Edit'/></button>
						
                        <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>                        
                      </div>
                    </div>
                  </div>
	<!-- -- End of Transfer to group modal -->
	
	
	
		  		
		<div class="md-overlay"></div>
</div>	

