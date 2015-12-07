<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.pregnancyeventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="PregnancyEventController" ng-init="loadPage(${CompanyId},'${PregnancyEventId}')" id="PregnancyEventControllerId">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div> 
 		  <div class="col-sm-6 col-md-6">
 		  
 		  <div class="block-flat">
		   <form name="breedingEventSearchForm" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pregnancyeventform.search.heading'  text='Search Pregnancy Events'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.pregnancyeventform.search.errormessage' text='Please enter Pig Id/ Tattoo and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.pregnancyeventform.search.data.errormessage' text='Pregnancy event information not found for the search criteria'/></p>
			
            <input type="text" name="search" ng-model="searchText" ng-enter="getPregnancyEventInformation()"  placeholder="<spring:message code='label.piginfo.pregnancyeventform.search.placeholder'  text='Search by Pig Id / Tattoo ...'/>" class="form-control">

			 <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="getPregnancyEventInformation()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1" class="icheck pregnancyevent" value="pigId" > <spring:message code='label.piginfo.breedingeventform.search.pigid.option'  text='Pig Id'/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad2" class="icheck pregnancyevent" value="tattoo"> <spring:message code='label.piginfo.breedingeventform.search.tattoo.option'  text='Tattoo'/> 
                </label>				
              </div>
            </div>            
          </div>
		  </form>
		  <form name="pregnancyEventSearchResultForm"  ng-if="pregnancyEventList != null && pregnancyEventList.length != 0" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pregnancyeventform.searchresults.heading'  text='Pregnancy Events'/></h3>
            <div class="table-responsive">
             <table>
				<thead>
                     <tr>
                       <th><spring:message code='label.piginfo.pregnancyeventform.pregnancyEventType'  text='Pregnancy Event Type'/> </th>
                       <th><spring:message code='label.piginfo.pregnancyeventform.resultDate'  text='Result Date'/> </th>
                       <th><spring:message code='label.employeegroup.list.header.action'  text='Action'/> </th>
                     </tr>
                 </thead>
                 <tbody>
                   <tr ng-repeat="pregnancyEventDto in pregnancyEventList">
                    <td>{{pregnancyEventDto.pregnancyEventType}}</td>                    
                    <td>{{DateUtils.getFormatedDate(pregnancyEventDto.resultDate)}}</td>
                    <td><button type="button" class="btn btn-edit btn-xs"
												ng-click="getPregnancyEventDetails(pregnancyEventDto)">
												<span class="glyphicon glyphicon-pencil"></span>
												<spring:message code="label.employeegroup.list.edit" text="Edit" />												
											</button></td>
                   </tr>
                 </tbody>
             </table>
             </div>
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
                  <h3><spring:message code='label.piginfo.pregnancyeventform.pregnancyEvent'  text='Pregnancy Event'/></h3>
                  
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pregnancyeventform.submit.success.message' text='Pregnancy Event information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                  <div class="alert alert-success alert-white rounded" ng-show="entryEventDeleteMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pregnancyeventform.delete.message'  text='Pregnancy event information deleted'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="pregnancyEventValidation_ErrCode_1">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.pregnancyEventValidation.ErrCode1'  text='Pregnancy date doesn\'t fall in the accepted duration of the service date'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="pregnancyEventValidation_ErrCode_2">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.pregnancyEventValidation.ErrCode2'  text='Abortion date doesn\'t fall in the accepted duration of the service date'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="pregnancyEventValidation_ErrCode_3">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.pregnancyEventValidation.ErrCode3'  text='Not in pig date doesn\'t fall in the accepted duration of the service date'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="pregnancyEventValidation_ErrCode_4">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.pregnancyEventValidation.ErrCode4'  text='Pregnancy result date can not be earlier than service date'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="pregnancyEventValidation_ErrCode_5">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pregnancyeventform.pregnancyEventValidation.ErrCode5'  text='Pregnancy event record already added for the selected breed event'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded"  ng-show="entryEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pregnancyeventform.duplicate.error.message' text='A pregnancy event record already exists with the same details'/>
                  </div>  
                </div>
                <div class="content">
                  <form name="pregnancyeventform" novalidate angular-validator my-reset>
                  <input type=hidden name="id" ng-model="pregnancyEvent.id"/>
				  <input type=hidden name="breedingEventId" ng-model="pregnancyEvent.breedingEventId"/>
				  
				  	<div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="premiseId" id="premiseId" ng-model="pregnancyEvent.premiseId" required required-message="'<spring:message code='label.piginfo.farroweventform.premise.requiredmessage' text='Premise is required' />'">
                       	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="pregnancyEvent.premiseId == premise.id">{{premise.name}}</option>
                        </select>
                    </div>	
				  
				  
					 <div class="form-group">
                      <label><spring:message code='label.piginfo.pregnancyeventform.pigInfoId'  text='Pig Id'/><span style='color: red'>*</span></label>
                       <div data-min-view="2" class="input-group col-md-7 col-xs-9"> 
                     <input type="text" ng-model="pregnancyEvent.pigId" id="pigId" name="pigId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pregnancyeventform.pigInfoId.placeholder'  text='Enter Piginfo Id'/>"/>
						<span class="input-group-addon btn btn-primary" ng-click="searchBreedingService(pregnancyEvent.pigId, pregnancyEvent.companyId)"  ><a class="btn-primary"><spring:message code='label.piginfo.breedingeventform.breedingevent'  text='Breeding Event'/></a></span>
						</div>
						<a href="#" ng-click="goToBreedingEvent()" ng-show="pregnancyEvent.breedingEventId != null && pregnancyEvent.breedingEventId > 0"><spring:message code='label.piginfo.pregnancyeventform.gotobreedingeventtext'  text='Go to Breeding Event'/></a>						 
					<label ng-show="inValidPigIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pregnancyeventform.pigInfoId.server.invalidmessage' text='Invalid Pig Id for the company' /></label>
					<label ng-show="requiredPigIdMessage" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pregnancyeventform.pigInfoId.requiredmessage' text='Pig Info Id is required' /></label>
					<label ng-show="breedingEventIdRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pregnancyeventform.breedingserviceid.requiredmessage' text='Breeding Service Id is required' /></label>
					<label ng-show="malePigIdentified" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pregnancyeventform.pigInfoId.server.malePigIdentified' text='The selected Pig Id is a boar.  Please select a Sow' /></label>
					
                    </div>
                      <div class="form-group">
                      <label><spring:message code='label.piginfo.pregnancyeventform.resultDate'  text='Result Date'/><span style='color: red'>*</span></label>
                      <input rsmdatedropdowns ng-model="pregnancyEvent.resultDate" day-div-class="day-container" day-class="day-selector" starting-year="2030" num-years="30"/>
                         <label ng-show="resultDateRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pregnancyeventform.resultDate.requiredmessage' text='Result Date is required' /></label>
	                    <label ng-show="invalidResultDate" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pregnancyeventform.resultDate.invalidmessage' text='Result date can not be earlier than exam date' /></label>
                    </div>   
					
                    	
					
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.pregnancyeventform.pregnancyEventType'  text='Pregnancy Event Type'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="sexType" ng-model="pregnancyEvent.pregnancyEventTypeId"  required 
                      required-message="'<spring:message code='label.piginfo.pregnancyeventform.pregnancyeventtype.requiredmessage' text='Pregnancy Event Type is required' />'"
                        ng-change="changePregnancyEventType()">
                        <option ng-repeat="key in pregnancyEventTypeKeys" ng-value="key" ng-selected="pregnancyEvent.pregnancyEventTypeId==key">{{pregnancyEventTypes[key]}}</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.pregnancyeventform.pregnancyExamResultType'  text='Pregnancy Exam Result Type'/></label>
                       <select class="form-control"  name="examResultType" id="examResultType" ng-model="pregnancyEvent.pregnancyExamResultTypeId" >
                       <option ng-repeat="key in pregnancyExamResultTypeKeys" ng-value="key" ng-selected="pregnancyEvent.pregnancyExamResultTypeId==key">{{pregnancyExamResultTypes[key]}}</option>
                        </select>
                    </div>                    
                                  
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.pregnancyeventform.employeegroup'  text='Employee Group'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span class="btn btn-success" ng-click="viewEmployeeGroup()" data-toggle="modal" data-target="#selectEmployeeGroupModal"><span class="glyphicon glyphicon-user"></span></span>	
                      <input type="hidden" ng-model="pregnancyEvent.employeeGroupId" id="employeeGroupId" name="employeeGroupId"  class="form-control" readonly = ""/>
					  <div ng-show="pregnancyEvent.employeeGroup != null && pregnancyEvent.employeeGroup.id > 0">
							<p>Group Id : <small>{{pregnancyEvent.employeeGroup.groupId}}</small></p>
							<p ng-repeat="employee in pregnancyEvent.employeeGroup.employeeList">{{employee.name}} ( Id : {{employee.id}})</p>
						</div>
                      </div>
                    </div>				
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.pregnancyeventform.sowcondition'  text='Sow Condition'/>
                      </label>
                      <i>[1:  <spring:message code='label.piginfo.entryeventform.sowcondition.least.message'  text='Least Healthy'/> - 5:<spring:message code='label.piginfo.entryeventform.sowcondition.most.message'  text='Most Healthiest'/>]</i>
                      <select class="form-control" name="sowCondition" ng-model="pregnancyEvent.sowCondition" >
                           <option value="1">1</option>
                          <option value="2">2</option>
                          <option value="3">3</option>
                          <option value="4">4</option>
                          <option value="5">5</option>
                        </select>
                    </div>
                    <button class="btn btn-success" ng-click="addPregnancyEvent()" type="submit" ng-disabled="inValidPigIdFromServer || malePigIdentified"><spring:message code='label.piginfo.pregnancyeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-warning" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.pregnancyeventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="deletePregnancyEvent()" ng-show="pregnancyEvent.id != null && pregnancyEvent.id > 0" ng-confirm-click="<spring:message code='label.piginfo.pregnancyeventform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.pregnancyeventform.delete'  text='Delete'/></button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		  
		  <!-- - Breeding Service Id search Modal -->
		  <div id="searchBreedingService" class="modal colored-header custom-width">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.piginfo.breedingeventform.searchresults.heading'  text='Breeding Events'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
                      </div>
                      <div class="modal-body form" >
                      <div class="table-responsive">
                      <table>
                       <thead>
                           <th><spring:message code='label.employeegroup.list.header.select'  text='Select'/> </th>
	                       <th><spring:message code='label.piginfo.breedingeventform.breedingServiceType'  text='Breeding Service Type'/> </th>
	                       <th><spring:message code='label.piginfo.breedingeventform.breedingDate'  text='Service Start Date'/> </th>
                       </thead>
                       <tbody>
	                   <tr ng-repeat="breedingEventObj in breedingEventList" ng-if="breedingEventList != null && breedingEventList.length > 0">
	                    <td><input type="radio" name="breedingEventDtoId" id="breedingEventDtoId" ng-model="pregnancyEvent.breedingEventDto" ng-value="breedingEventObj"></td>	                    
	                    <td>{{breedingEventObj.breedingServiceType}}</td>
	                    <td>{{DateUtils.getFormatedDate(breedingEventObj.serviceStartDate)}}</td>
	                   </tr>
	                   <tr ng-if="breedingEventList == null || breedingEventList.length == 0">
	                     <td colspan="4">
	                       <spring:message code='label.pregnancyeventform.list.breedingevents.noresults'  text='No breeding events found'/>
	                     </td>
	                   </tr>
	                   
	                 </tbody>
                      </table>
                      </div>
                      </div>
                      <div class="modal-footer">
                      <button type="button" class="btn btn-success btn-flat md-close" ng-hide="breedingEventList == null || breedingEventList.length == 0" ng-click="selectBreedingEventService()"><spring:message code='label.employeegroup.list.header.select'  text='Select'/></button>
                      <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                      </div>
                      
                     </div>
            </div>
		  
		  <!-- -- Employee Group Modal -->
		  <div id="selectEmployeeGroupModal" class="modal colored-header custom-width" ng-controller="EmployeeGroupController" ng-init="getEmployeeGroups()">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.employeegroup.heading'  text='Employee Groups'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
                      </div>
                      <div class="modal-body form" ng-hide="viewAddForm">
					     <p class="color-primary"><spring:message code='label.employeegroup.selection.message'  text='Please select a group and proceed'/></p>
					     <div class="table-responsive">
                        <table>
						<thead>
                           <tr>
                           	 <th><spring:message code='label.employeegroup.list.header.select'  text='Select'/> </th>
                             <th> <spring:message code='label.employeegroup.list.header.groupId'  text='Group Id'/> </th>
                             <th> <spring:message code='label.employeegroup.list.header.members'  text='Members'/> </th>
                             <th colspan="2"> <spring:message code='label.employeegroup.list.header.action'  text='Action'/> </th>
                           </tr>
						   </thead>
						   <tbody>
                           <tr ng-repeat="employeeGroup in employeeGroups" ng-if="employeeGroups != null && employeeGroups.length > 0">
						   <td><input type="radio" name="employeeGrpId" id="employeeGrpId" value="{{employeeGroup.id}}"></td>
                              <td> {{employeeGroup.groupId}} </td>
                              <td>
                                 <p ng-repeat="employee in employeeGroup.employeeList">  {{employee.name}} ({{employee.employeeId}}) </p>
                               </td>
                               <td>
                               <button type="button" class="btn btn-edit btn-xs"
												ng-click="editEmployeeGroup(employeeGroup)">
												<span class="glyphicon glyphicon-pencil"></span>
												<spring:message code="label.employeegroup.list.edit" text="Edit" />												
											</button>
                               </td>
                               <td>
                               <button type="button" class="btn btn-edit btn-xs"
												ng-click="removeEmployeeGroup(employeeGroup)" ng-confirm-click="<spring:message code='label.employeegroup.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>">
												<span class="glyphicon glyphicon-remove"></span>												
											</button>
                               </td>
                           </tr>
                           <tr ng-if="employeeGroups == null || employeeGroups.length == 0">
                             <td colspan="5">
                               <spring:message code='label.employeegroup.list.nogroups'  text='No groups defined'/>
                             </td>
                           </tr>
						   </tbody>
                        </table>
                       </div>
                      </div>
                      <div class="modal-body form" ng-show="viewAddForm">
                         <h4 ng-if="!(employeeGrp.id > 0)"> <spring:message code='label.employeegroup.add.heading'  text='Add Employee Group'/></h4>
						 <h4 ng-if="employeeGrp.id > 0"> <spring:message code='label.employeegroup.edit.heading'  text='Edit Employee Group'/></h4>
					     <p class="color-primary"><spring:message code='label.employeegroup.add.message'  text='Please enter group Id, job function and then select the employees'/></p>
					     <p class="color-danger" ng-show="employeeGrpAddError"><spring:message code='label.employeegroup.message.add.error'  text='Exception occurred while adding the group'/></p>
					     <p class="color-danger" ng-show="employeeGrpAddDuplicateError"><spring:message code='label.employeegroup.message.add.error'  text='A group with the same group Id / employees already exist. Please check'/></p>
					     <p class="color-success" ng-show="employeeGrpAddSuccess"><spring:message code='label.employeegroup.message.add.success'  text='Employee group added successfully'/></p>
					     <p class="color-success" ng-show="employeeGrpDeleteSuccess"><spring:message code='label.employeegroup.message.delete.success'  text='Employee group deleted'/></p>
						 <input type="hidden" ng-model="employeeGrp.id"/>
                          <div class="form-group">
	                          <label><spring:message code='label.employeegroup.add.groupId'  text='Group Id'/></label>
	                          <input type="text" placeholder="Enter Group Id" class="form-control" ng-model="employeeGrp.groupId" ng-readonly="employeeGrp.id > 0">
	                          <p class="color-danger" ng-show="employeeGrpGroupIdInvalid"><spring:message code='label.employeegroup.message.groupid.invalid'  text='Group Id is required'/></p>
	                        </div>
	                        <div class="form-group">
	                          <label><spring:message code='label.employeegroup.add.jobFunction'  text='Job Function'/></label>
	                           <select class="form-control" id="jobFunctionRoleId"  name="jobFunctionRoleId" ng-model="employeeGrp.jobFunctionRoleId"  ng-change="getEmployeeList()">
                        	    <option ng-repeat="key in jobFunctionRoleKeys" ng-value="key" ng-selected="employeeGrp.jobFunctionRoleId==key">{{jobFunctionRoleKeyValues[key]}}</option>
                        	  </select>	     
	                          <p class="color-danger" ng-show="employeeGrpJobFunctionInvalid"><spring:message code='label.employeegroup.message.jobfunction.invalid'  text='Job function is required'/></p>
	                        </div>
	                        <div class="row">
	                        <p class="color-danger" ng-show="employeeGrpEmployeeInvalid"><spring:message code='label.employeegroup.message.employeeselection.invalid'  text='Please select atleast one employee'/></p>
	                        <div class="table-responsive">
			                   <table>
								<thead>
		                           <tr>
		                           	 <th><spring:message code='label.employeegroup.employeelist.header.select'  text='Select'/> </th>
		                             <th> <spring:message code='label.employeegroup.employeelist.header.employeeId'  text='Employee Id'/> </th>
		                             <th> <spring:message code='label.employeegroup.employeelist.header.name'  text='Name'/> </th>
		                           </tr>
								   </thead>
								   <tbody>
		                           <tr ng-repeat="employee in employeeList" ng-if="employeeList != null && employeeList.length > 0">
								   <td><input type="checkbox" ng-model="employee.selected"></td>
		                              <td> {{employee.employeeId}}</td>
		                              <td>
		                                 {{employee.name}}
		                               </td>
		                           </tr>
		                           <tr ng-if="employeeList == null || employeeList.length == 0">
		                             <td colspan="3">
		                               <spring:message code='label.employeegroup.employeelist.noemployees'  text='No Employees available'/>
		                             </td>
		                           </tr>
								   </tbody>
		                        </table>
		                        </div>
	                        </div>
                       
                      </div>
                      <div class="modal-footer">
					    <div class="pull-left">
						<button type="button" class="btn btn-success btn-flat" ng-click="showAddGroupForm()"  ng-hide="viewAddForm"><spring:message code='label.employeegroup.button.addgroup'  text='New Group'/></button>
						</div>
                        <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                        <button type="button" data-dismiss="modal" class="btn btn-success btn-flat md-close" ng-hide="viewAddForm || employeeGroups == null || employeeGroups.length == 0" ng-click="selectEmployeeGroup(pregnancyEvent)"><spring:message code='label.employeegroup.button.proceed'  text='Proceed'/></button>
                        <button type="button"  class="btn btn-success btn-flat" ng-show="viewAddForm && employeeList.length > 0" ng-click="addEmployeeGroup()"><spring:message code='label.employeegroup.button.save'  text='Save'/></button>
                      </div>
                    </div>
                  </div>


		<div class="md-overlay"></div>
</div>

<form method="post" action="breedingEvent" id="prevBreedingEventForm">
  <input type="hidden" name="selectedCompany" id="selectedCompany">
  <input type="hidden" name="selectedBreedingEventId" id="selectedBreedingEventId">
</form>

		  

	
