<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.breedingeventform.piginformation'  text='Pig Information'/></h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="BreedingEventController" ng-init="setCompanyId(${CompanyId})">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div>
 		  <div class="col-sm-6 col-md-6">
 		  <div class="block-flat">
		   <form name="breedingEventSearchForm" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.breedingeventform.search.heading'  text='Search'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.breedingeventform.search.errormessage' text='Please enter Pig Id/ Tattoo and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.breedingeventform.search.data.errormessage' text='Pig Information not found for the search criteria'/></p>
			<div class="input-group">
            <input type="text" name="search" ng-model="searchText" placeholder="<spring:message code='label.piginfo.breedingeventform.search.placeholder'  text='Search by service id ...'/>" class="form-control">

			 <span class="input-group-btn">
          <button type="button" class="btn btn-primary" ng-click="getBreedingEventInformation()"><i class="fa fa-search"></i></button></span>
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
                  <h3><spring:message code='label.piginfo.breedingeventform.breedingevent'  text='Breeding Event'/></h3>
                  <p class="color-success" ng-show="entryEventSuccessMessage"><spring:message code='label.piginfo.breedingeventform.submit.success.message' text='Breeding information saved successfully'/></p>
				  <p class="color-danger" ng-show="entryEventErrorMessage"><spring:message code='label.piginfo.breedingeventform.submit.error.message' text='An exception occurred. Please check the values entered'/></p>
				  <p class="color-success" ng-show="entryEventDeleteMessage"><spring:message code='label.piginfo.breedingeventform.delete.message'  text='Breeding event information deleted'/></p>
                </div>
                <div class="content">
                  <form name="breedingeventform" novalidate angular-validator>
                  <input type=hidden name="id" ng-model="breedingEvent.id"/>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.serviceId'  text='Service Id'/><span style='color: red'>*</span></label>
                      <input ng-hide="breedingEvent.id != null && breedingEvent.id != 0" required="true" type="text" name="serviceId" ng-model="breedingEvent.serviceId"  maxlength="30" 
                      placeholder="<spring:message code='label.piginfo.breedingeventform.serviceId.placeholder'  text='Enter Service Id'/>" 
                      required-message="'<spring:message code='label.piginfo.breedingeventform.serviceId.requiredmessage' text='Service Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.breedingeventform.serviceId.invalidmessage' text='Only Alpha Numeric values are allowed' />'"  class="form-control">
				      <p ng-show="breedingEvent.id != null && breedingEvent.id != 0">{{breedingEvent.id.serviceId}}</p>
                    </div>
                     <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.employeegroup'  text='Employee Group'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
                      <input type="text" ng-model="breedingEvent.employeeGroupId"  name="employeeGroupId"  class="form-control" readonly = ""/><span class="input-group-addon btn btn-primary md-trigger " ng-click="viewEmployeeGroup()"  data-modal="colored-primary"><span class="glyphicon glyphicon-th"></span></span>					   
                      </div>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.pigInfoId'  text='Pig Info Id'/></label>
                     <input type="text" ng-model="breedingEvent.pigInfoId"  name="pigInfoId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.breedingeventform.pigInfoId.placeholder'  text='Enter Piginfo Id'/>" 
                      required-message="'<spring:message code='label.piginfo.breedingeventform.pigInfoId.requiredmessage' text='Pig Info Id is required' />'"
						ng-pattern="/^[0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.breedingeventform.pigInfoId.invalidmessage' text='Only Numeric values are allowed' />'"/>
                    </div>
                   
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.breedingServiceType'  text='Breeding Service Type'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="sexType" ng-model="breedingEvent.breedingServiceTypeId"  required 
                      required-message="'<spring:message code='label.piginfo.breedingeventform.breedingServiceType.requiredmessage' text='Breeding Service Type is required' />'"
                       ng-options="k as v for (k, v) in breedingServiceTypes">
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.breedinggroupId'  text='Breeding Group Id'/></label>
                      <input type="text" ng-model="breedingEvent.breedingGroupId"  name="breedingGroupId"  class="form-control"/>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.breedingDate'  text='Breeding Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="breedingDate" name="breedingDate" ng-model="breedingEvent.breedingDate" readonly="" class="form-control"><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.semenId'  text='Semen Id'/></label>
                       <input type="text" class="form-control" name="dame" ng-model="breedingEvent.semenId" maxlength="30" placeholder="<spring:message code='label.piginfo.breedingeventform.semenId.placeholder' text='Enter Semen Id'/>" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.mateQuality'  text='Mate Quality'/></label>
                      <select ng-model="breedingEvent.mateQuality" name="mateQuality" class="form-control">
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      </select>
                    </div>
                    
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.remarks'  text='Remarks'/><span style='color: red'>*</span></label>
                      <textarea name="remarks" ng-model="breedingEvent.remarks" class="form-control" placeholder="<spring:message code='label.piginfo.breedingeventform.remarks.placeholder' text='Enter remarks'/>" required required-message="'<spring:message code='label.piginfo.breedingeventform.remarks.requiredmessage' text='Remarks is required'/>'"></textarea>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.sowcondition'  text='Sow Condition'/><span style='color: red'>*</span></label>
                      <select class="form-control" name="sowCondition" ng-model="breedingEvent.sowCondition" required required-message="'<spring:message code='label.piginfo.breedingeventform.sowcondition.requiredmessage' text='Sow condition is required'/>'">
                          <option value=1>1 </option>
                          <option value=2>2</option>
                          <option value=3>3</option>
                        </select>
                    </div>
                    <button class="btn btn-primary" ng-click="addBreedingEvent()" type="submit"><spring:message code='label.piginfo.breedingeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-default" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.breedingeventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="deleteBreedingEventInfo()" ng-show="breedingEvent.id != null && breedingEvent.id > 0" ng-confirm-click="<spring:message code='label.piginfo.breedingeventform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.breedingeventform.delete'  text='Delete'/></button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
</div>

  
		  <div id="colored-primary" class="md-modal colored-header custom-width md-effect-9" ng-controller="EmployeeGroupController" ng-init="getEmployeeGroups()">
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
                           </tr>
						   </thead>
						   <tbody>
                           <tr ng-repeat="employeeGroup in employeeGroups" ng-if="employeeGroups != null && employeeGroups.length > 0">
						   <td><input type="radio" ng-model="employeeGroup.selected"></td>
                              <td> {{employeeGroup.groupId}} </td>
                              <td>
                                 <p ng-repeat="employee in employeeGroup.employeeList">  {{employee.name}} ({{employee.employeeId}}) </p>
                               </td>
                           </tr>
                           <tr ng-if="employeeGroups == null || employeeGroups.length == 0">
                             <td colspan="3">
                               <spring:message code='label.employeegroup.list.nogroups'  text='No groups defined'/>
                             </td>
                           </tr>
						   </tbody>
                        </table>
                       
                      </div>
                      <div class="modal-body form" ng-show="viewAddForm">
                         <h4> <spring:message code='label.employeegroup.add.heading'  text='Add Employee Group'/></h4>
					     <p class="color-primary"><spring:message code='label.employeegroup.add.message'  text='Please enter group Id, job function and then select the employees'/></p>
					     <p class="color-danger" ng-show="employeeGrpAddError"><spring:message code='label.employeegroup.message.add.error'  text='Exception occurred while adding the group'/></p>
					     <p class="color-success" ng-show="employeeGrpAddSuccess"><spring:message code='label.employeegroup.message.add.success'  text='Employee group added successfully'/></p>
                          <div class="form-group">
	                          <label><spring:message code='label.employeegroup.add.groupId'  text='Group Id'/></label>
	                          <input type="text" placeholder="Enter Group Id" class="form-control" ng-model="employeeGrp.groupId">
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
		                              <td> {{employee.employeeId}} </td>
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
						<button type="button" class="btn btn-primary btn-flat" ng-click="showAddGroupForm()"  ng-hide="viewAddForm"><spring:message code='label.employeegroup.button.addgroup'  text='New Group'/></button>
						</div>
                        <button type="button" data-dismiss="modal" class="btn btn-default btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                        <button type="button" data-dismiss="modal" class="btn btn-primary btn-flat md-close" ng-hide="viewAddForm" ng-click="selectEmployeeGroup()"><spring:message code='label.employeegroup.button.proceed'  text='Proceed'/></button>
                        <button type="button" data-dismiss="modal" class="btn btn-primary btn-flat" ng-show="viewAddForm" ng-click="addEmployeeGroup()"><spring:message code='label.employeegroup.button.save'  text='Save'/></button>
                      </div>
                    </div>
                  </div>


		

	
