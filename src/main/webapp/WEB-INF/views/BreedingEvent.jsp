<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.breedingeventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="BreedingEventController" ng-init="setCompanyId(${CompanyId})" id="BreedingEventControllerId">
 
  <div class="row">
 		  <div class="col-sm-2 col-md-2"></div>
 		  <div class="col-sm-8 col-md-8">
 		  <div class="block-flat">
		   <form name="breedingEventSearchForm" >
 		     <div class="head">
 		     
 		     <h3> <spring:message code='label.piginfo.breedingeventform.search.heading'  text='Search'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.breedingeventform.search.errormessage' text='Please enter Pig Id/ Tattoo/ Service Id and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.breedingeventform.search.data.errormessage' text='Breeding event information not found for the search criteria'/></p>
			
            <input type="text" name="search" ng-model="searchText" placeholder="<spring:message code='label.piginfo.breedingeventform.search.placeholder'  text='Search by service id ...'/>" class="form-control">
 		    
            <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="getBreedingEventInformation()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1" class="icheck breedingevent" value="pigId" > <spring:message code='label.piginfo.breedingeventform.search.pigid.option'  text='Pig Id'/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad2" class="icheck breedingevent" value="tattoo"> <spring:message code='label.piginfo.breedingeventform.search.tattoo.option'  text='Tattoo'/> 
                </label>
				<label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad3" class="icheck breedingevent" value="serviceId"> <spring:message code='label.piginfo.breedingeventform.search.serviceId.option'  text='Service Id'/> 
                </label>
              </div>
            </div>
          </div>
		  </form>
		  <form name="breedingEventSearchResultForm"  ng-if="breedingEventList != null && breedingEventList.length != 0" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.breedingeventform.searchresults.heading'  text='Breeding Events'/></h3>
             <table>
				<thead>
                     <tr>
					 <th><spring:message code='label.piginfo.breedingeventform.pigInfoId'  text='Pig Id'/> </th>
                       <th><spring:message code='label.piginfo.breedingeventform.serviceId'  text='Service Id'/> </th>
                       <th><spring:message code='label.piginfo.breedingeventform.breedingServiceType'  text='Breeding Service Type'/> </th>
                       <th><spring:message code='label.piginfo.breedingeventform.breedingDate'  text='Breeding Date'/> </th>
                       <th><spring:message code='label.employeegroup.list.header.action'  text='Action'/> </th>
                     </tr>
                 </thead>
                 <tbody>
                   <tr ng-repeat="breedingEventDto in breedingEventList">
				   <td>{{breedingEventDto.pigInfoId}}</td>
                    <td>{{breedingEventDto.serviceId}}</td>
                    <td>{{breedingEventDto.breedingServiceType}}</td>
                    <td>{{DateUtils.getFormatedDate(breedingEventDto.breedingDate)}}</td>
                    <td><button type="button" class="btn btn-edit btn-xs"
												ng-click="getBreedingEventDetails(breedingEventDto)">
												<span class="glyphicon glyphicon-pencil"></span>
												<spring:message code="label.employeegroup.list.edit" text="Edit" />												
											</button></td>
                   </tr>
                 </tbody>
             </table>
          </div>
		  </form>
          </div>
 		  </div>
 		  <div class="col-sm-2 col-md-2"></div>
 		</div>
 
 
          <div class="row" >
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.piginfo.breedingeventform.breedingevent'  text='Breeding Event'/></h3>
                  
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.breedingeventform.submit.success.message' text='Breeding information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                  <div class="alert alert-success alert-white rounded" ng-show="entryEventDeleteMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.breedingeventform.delete.message'  text='Breeding event information deleted'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded"  ng-show="entryEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.duplicate.error.message' text='A breeding event record already exists with the same service id'/>
                  </div>  
                  <div class="alert alert-warning alert-white rounded" ng-show="breedingEventValidation_WarnCode_1">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-warning"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_WarnCode_1' text='Breeding happen within first 5 days, please click submit again to proceed' />
                  </div>
                  <div class="alert alert-warning alert-white rounded" ng-show="breedingEventValidation_WarnCode_2">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-warning"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_WarnCode_2'  text='Breeding happens between 19 to 60 days and the pig is not pregnant' />
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="breedingEventValidation_ErrCode_BirthDate">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_BirthDate' text='Birth date of the pig can not be later than breeding date' />
                  </div>
                   <div class="alert alert-danger alert-white rounded" ng-show="breedingEventValidation_ErrCode_EntryDate">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_EntryDate' text='Entry date of the pig can not be later than breeding date' />
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="breedingEventValidation_ErrCode_1">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_1' text='Breeding happens between 6 to 18 days' />
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="breedingEventValidation_ErrCode_2"> 
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_2' text='Breeding happens between 19 to 60 days and the pig is pregnant' />
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="breedingEventValidation_ErrCode_3"> 
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_3' text='Breeding happens between 19 to 60 days and no pregnacy check done' />
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="breedingEventValidation_ErrCode_4"> 
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_4' text='Breeding date earlier than gestation window' />
                  </div>
                </div>
                <div class="content">
                  <form name="breedingeventform" novalidate angular-validator my-reset>
                  <input type=hidden name="id" ng-model="breedingEvent.id"/>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.serviceId'  text='Service Id'/><span style='color: red'>*</span></label>
                      <input ng-hide="breedingEvent.id != null && breedingEvent.id != 0" required="true" type="text" id="serviceId" name="serviceId" ng-model="breedingEvent.serviceId"  maxlength="30" 
                      placeholder="<spring:message code='label.piginfo.breedingeventform.serviceId.placeholder'  text='Enter Service Id'/>" 
                      required-message="'<spring:message code='label.piginfo.breedingeventform.serviceId.requiredmessage' text='Service Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.breedingeventform.serviceId.invalidmessage' text='Only Alpha Numeric values are allowed' />'"  class="form-control">
				      <p ng-show="breedingEvent.id != null && breedingEvent.id != 0">{{breedingEvent.serviceId}}</p>
                    </div>
                     <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.employeegroup'  text='Employee Group'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span class="btn btn-success" ng-click="viewEmployeeGroup()" data-toggle="modal" data-target="#selectEmployeeGroupModal"><span class="glyphicon glyphicon-user"></span></span>	
                      <input type="hidden" ng-model="breedingEvent.employeeGroupId" id="employeeGroupId" name="employeeGroupId"  class="form-control" readonly = ""/>
					  <div ng-show="breedingEvent.employeeGroup != null && breedingEvent.employeeGroup.id > 0">
							<p>Group Id : <small>{{breedingEvent.employeeGroup.groupId}}</small></p>
							<p ng-repeat="employee in breedingEvent.employeeGroup.employeeList">{{employee.name}} ( Id : {{employee.id}})</p>
						</div>
                      </div>
                    </div>					
					<div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.pigInfoId'  text='Pig Id'/></label>
                     <input type="text" ng-model="breedingEvent.pigInfoId" id="pigInfoId" name="pigInfoId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.breedingeventform.pigInfoId.placeholder'  text='Enter Piginfo Id'/>" 
                      required-message="'<spring:message code='label.piginfo.breedingeventform.pigInfoId.requiredmessage' text='Pig Info Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.breedingeventform.pigInfoId.invalidmessage' text='Only Numeric values are allowed' />'" ng-blur="checkForPigId()" ng-focus="clearMessages()"/>
						<label ng-show="!inValidPigIdFromServer && breedingEvent.pigInfoId != null && !malePigIdentified && breedingEvent.gestationRecordDate != null">Gestation window started on {{DateUtils.getFormatedDate(breedingEvent.gestationRecordDate)}}</label>
                    </div>
					<label ng-show="inValidPigIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.breedingeventform.pigInfoId.server.invalidmessage' text='Invalid Pig Id for the company' /></label>
					<label ng-show="malePigIdentified" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.breedingeventform.pigInfoId.server.malePigIdentified' text='The selected Pig Id is a boar.  Please select a Sow' /></label>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.breedingServiceType'  text='Breeding Service Type'/><span style='color: red'>*</span></label>
                       <select class="form-control"  id="sexType" name="sexType" ng-model="breedingEvent.breedingServiceTypeId"  required 
                      required-message="'<spring:message code='label.piginfo.breedingeventform.breedingServiceType.requiredmessage' text='Breeding Service Type is required' />'"
                       ng-options="k as v for (k, v) in breedingServiceTypes">
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.breedinggroupId'  text='Breeding Group Id'/></label>
                      <input type="text" ng-model="breedingEvent.breedingGroupId" id="breedingGroupId" name="breedingGroupId"  class="form-control"  placeholder="<spring:message code='label.piginfo.breedingeventform.breedinggroupId.placeholder'  text='Enter Breeding group Id'/>"/>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.breedingDate'  text='Breeding Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7" >
                          <input size="16" type="date" id="breedingDate" name="breedingDate" ng-model="breedingEvent.breedingDate" readonly="" class="form-control" ng-blur="validateBreedingDate()"  format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th" id="popupcal"></span></span>
                        </div>
                    </div>
                    <label ng-show="breedingDateRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.breedingeventform.breedingDate.requiredmessage' text='Breeding Date is required' /></label>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.semenId'  text='Semen Id'/></label>
                       <input type="text" class="form-control" name="dame" id="semenId" ng-model="breedingEvent.semenId" maxlength="30" placeholder="<spring:message code='label.piginfo.breedingeventform.semenId.placeholder' text='Enter Semen Id'/>" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.mateQuality'  text='Mate Quality'/></label>
                      <select ng-model="breedingEvent.mateQuality" name="mateQuality" id="mateQuality" class="form-control">
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      </select>
                    </div>
                    
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.remarks'  text='Remarks'/><span style='color: red'>*</span></label>
                      <textarea name="remarks" ng-model="breedingEvent.remarks" id="remarks" class="form-control" placeholder="<spring:message code='label.piginfo.breedingeventform.remarks.placeholder' text='Enter remarks'/>" required required-message="'<spring:message code='label.piginfo.breedingeventform.remarks.requiredmessage' text='Remarks is required'/>'"></textarea>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.sowcondition'  text='Sow Condition'/><span style='color: red'>*</span></label>
                      <i>[1:  <spring:message code='label.piginfo.entryeventform.sowcondition.least.message'  text='Least Healthy'/> - 5:<spring:message code='label.piginfo.entryeventform.sowcondition.most.message'  text='Most Healthiest'/>]</i>
                      <select class="form-control" id="sowCondition" name="sowCondition" ng-model="breedingEvent.sowCondition" required required-message="'<spring:message code='label.piginfo.breedingeventform.sowcondition.requiredmessage' text='Sow condition is required'/>'">
                           <option value="1">1</option>
                          <option value="2">2</option>
                          <option value="3">3</option>
                          <option value="4">4</option>
                          <option value="5">5</option>
                        </select>
                    </div>
                    <button class="btn btn-success" ng-click="addBreedingEvent()" type="submit" ng-disabled="inValidPigIdFromServer || malePigIdentified"><spring:message code='label.piginfo.breedingeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-warning" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.breedingeventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="deleteBreedingEventInfo()" ng-show="breedingEvent.id != null && breedingEvent.id > 0" ng-confirm-click="<spring:message code='label.piginfo.breedingeventform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.breedingeventform.delete'  text='Delete'/></button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		  
		  <div id="selectEmployeeGroupModal" class="modal colored-header warning custom-width" ng-controller="EmployeeGroupController" ng-init="getEmployeeGroups()">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.employeegroup.heading'  text='Employee Groups'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">�</button>
                      </div>
                      <div class="modal-body form" ng-hide="viewAddForm">
					     <p class="color-primary"><spring:message code='label.employeegroup.selection.message'  text='Please select a group and proceed'/></p>
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
	                          <select class="form-control" ng-model="employeeGrp.employeeJobFunction">
	                            <option value="Any"><spring:message code='label.employeegroup.add.functions.any'  text='Any'/></option>
	                          </select>
	                          <p class="color-danger" ng-show="employeeGrpJobFunctionInvalid"><spring:message code='label.employeegroup.message.jobfunction.invalid'  text='Job function is required'/></p>
	                        </div>
	                        <div class="row">
	                        <p class="color-danger" ng-show="employeeGrpEmployeeInvalid"><spring:message code='label.employeegroup.message.employeeselection.invalid'  text='Please select atleast one employee'/></p>
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
                      <div class="modal-footer">
					    <div class="pull-left">
						<button type="button" class="btn btn-success btn-flat" ng-click="showAddGroupForm()"  ng-hide="viewAddForm"><spring:message code='label.employeegroup.button.addgroup'  text='New Group'/></button>
						</div>
                        <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                        <button type="button" data-dismiss="modal" class="btn btn-success btn-flat md-close" ng-hide="viewAddForm || employeeGroups == null || employeeGroups.length == 0" ng-click="selectEmployeeGroup(breedingEvent)"><spring:message code='label.employeegroup.button.proceed'  text='Proceed'/></button>
                        <button type="button"  class="btn btn-success btn-flat" ng-show="viewAddForm && employeeList.length > 0" ng-click="addEmployeeGroup()"><spring:message code='label.employeegroup.button.save'  text='Save'/></button>
                      </div>
                    </div>
                  </div>
                  
                  
                  
             <button type="button" ng-click="addMatingDetailData()" class="btn btn-sm btn btn-success" ng-show="(breedingEvent.id != null && breedingEvent.id > 0) || entryEventSuccessMessage">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.breedingeventform.addMatingDetails" text="Add Mating Details" />
		</button>
	<form name="matingdetailsform" method="post">	
		<div class="content" ng-show="(breedingEvent.id != null && breedingEvent.id > 0) || entryEventSuccessMessage">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection" st-safe-src="breedingEvent.matingDetailsList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>
						<th style="width:10%"><spring:message code="label.matingdetailsform.matingDate" text="Mating Date" /></th>
						<th style="width:10%"><spring:message code="label.matingdetailsform.employeeGroupId" text="Group Id" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.dateOfEntry" text="Date Of Entry" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.numberOfPigs" text="Number Of Pigs" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.weightInKgs" text="Weight In Kgs" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.inventoryAdjustment" text="Inventory Adjustment" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.roomId" text="Room" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.remarks" text="Remarks" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.edit" text="Edit" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:10%">{{$index+1}}</td>
					<td style="width:10%">{{row.origin}}</td>
					<td style="width:25%">{{DateUtils.getFormatedDate(row.dateOfEntry)}}</td>
					<td style="width:25%">{{row.numberOfPigs}}</td>
					<td style="width:25%">{{row.weightInKgs}}</td>
					<td style="width:10%">{{row.inventoryAdjustment}}</td>
					<td style="width:10%">{{roomList[row.roomId]}}</td>
					<td style="width:10%">{{row.remarks}}</td>
					<td style="width: 8%">
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
		<input type="hidden" name="searchedGroupid" id="searchedGroupid"/>
		<input type="hidden" name="groupEventId" id="groupEventId"/>			
		<input type="hidden" name="groupGeneratedIdSeq" id="groupGeneratedIdSeq"/>
		<input type="hidden" name="companyId" id="companyId"/>
		<input type="hidden" name="groupStartDateTimeAdd" id="groupStartDateTimeAdd"/>
		
	</form>	  	
                  
                  
		<div class="md-overlay"></div>
</div>

  
		  

	
