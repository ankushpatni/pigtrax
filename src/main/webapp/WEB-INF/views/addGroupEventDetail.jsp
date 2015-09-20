<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h3 ng-hide="edit"><spring:message code="label.groupEventDetail.add.detail" text="Add Group Event Detail" /></h3>
			<h3 ng-show="edit"><spring:message code="label.groupEventDetail.edit.detail" text="Edit Group Event Detail" /></h3>
</div>
		 
 <div class="cl-mcont" ng-controller="AddGroupEventDetailController" ng-init="setCompanyId('${companyId}','${groupId}','${groupEventId}','${groupGeneratedId}','${groupStartDateTime}')">
 
        <div class="row" >
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">            
                <div class="content">
                  <form name="groupEventDetailAddForm" novalidate angular-validator method="post">
				  <input type=hidden name="groupId" ng-model="groupEvent.groupId"/>
                	 <div class="form-group">
						<label><spring:message code="label.piginfo.groupEventForm.groupId" text="Group ID" /></label>
						<label>{{groupAlphaId}}</label>
					 </div>
					 <div class="form-group">
						<label ><spring:message code="label.groupEventDetail.barn" text="Barn" /><span style='color: red'>*</span></label>
						<!-- <input class="form-control" type="text" placeholder="<spring:message code='label.groupEventDetail.barn' text='Origin' />" name="area" ng-model="groupEvent.origin" maxlength="20" />-->
						<select class="form-control"  name="barnId" id="barnId" ng-model="groupEvent.barnId"   required required-message="'<spring:message code='label.groupEventDetail.barn.required' text='Barn is required' />'"
                         ng-options="k as v for (k, v) in barnList">
                        </select>
					</div>			
					<div class="form-group">
						<label ><spring:message code="label.groupEventDetail.dateOfEntry" text="Date Of Entry" /><span style='color: red'>*</span></label>
						<div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
							 <input size="16" type="date" id="dateOfEntry" name="groupEvent.dateOfEntry" ng-model="groupEvent.dateOfEntry" readonly="" class="form-control" format-date required-message="'<spring:message code='label.groupEventDetail.dateOfEntry.required' text='Date of Entry is required' />'"/><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
						</div>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="dateOfEntryFlag" ><spring:message code='label.groupEventDetail.dateOfEntry.conditionMaessag' text='Date Of Entry should be greater than Group Start Date.' /></label>
					</div>
					<div class="form-group">
						<label><spring:message code="label.groupEventDetail.numberOfPigs" text="Number Of Pigs" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.groupEventDetail.numberOfPigs' text='Number of Alive Pigs' />" name="numberOfPigs" ng-model="groupEvent.numberOfPigs" maxlength="8" required required-message="'<spring:message code='label.groupEventDetail.numberOfPigs.required' text='Number Of Pigs required' />'" ng-pattern="/^\d{1,8}?$/i"  invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
					</div>
					<div class="form-group">
						<label><spring:message code="label.groupEventDetail.weightInKgs" text="Weight In Kgs" /><span style='color: red'>*</span></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.groupEventDetail.weightInKgs'  text='Weight In Kgs'/>" name="weightInKgs" ng-model="groupEvent.weightInKgs" required required-message="'<spring:message code='label.groupEventDetail.weightInKgs.required' text='Weight In Kgs is required' />'" ng-pattern="/^[0-9]{1,15}(\.[0-9]{1,2})?$/i" invalid-message="'<spring:message code='label.barn.areaInvalid' text='Only values like xxx.xx are Allowed.'/>'"/>	
					</div>
					<div class="form-group">
						<label><spring:message code="label.groupEventDetail.inventoryAdjustment" text="Inventory Adjustment" /></label>
						<input class="form-control" type="text" placeholder="<spring:message code='label.groupEventDetail.inventoryAdjustment' text='Inventory Adjustment' />" name="inventoryAdjustment" ng-model="groupEvent.inventoryAdjustment" maxlength="8" ng-pattern="/^\d{1,8}?$/i"  invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'"/>
					</div>					
					
					<div class="form-group">
                      <label><spring:message code='label.groupEventDetail.roomId'  text='Room'/></label>
                       <select class="form-control"  name="roomId" id="roomId" ng-model="groupEvent.roomId"   
                         ng-options="k as v for (k, v) in roomType">
                        </select>
                    </div>
					
					 <div class="form-group">
                      <label><spring:message code='label.piginfo.pregnancyeventform.employeegroup'  text='Employee Group'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span class="btn btn-primary" ng-click="viewEmployeeGroup()" data-toggle="modal" data-target="#selectEmployeeGroupModal"><span class="glyphicon glyphicon-user"></span></span>	
                      <input type="hidden" ng-model="groupEvent.employeeGroupId" id="employeeGroupId" name="employeeGroupId"  class="form-control" readonly = ""/>
					  <div ng-show="groupEvent.employeeGroup != null && groupEvent.employeeGroup.id > 0">
							<p>Group Id : <small>{{groupEvent.employeeGroup.groupId}}</small></p>
							<p ng-repeat="employee in groupEvent.employeeGroup.employeeList">{{employee.name}} ( Id : {{employee.id}})</p>
						</div>
                      </div>
                    </div>
					<div class="form-group">
				<label><spring:message code="label.groupEventDetail.remarks" text="Remarks" /></label>
				<input class="form-control" type="text" placeholder="<spring:message code='label.groupEventDetail.remarks' text='Remarks' />" name="remarks" ng-model="groupEvent.remarks" maxlength="255" />
			</div>
			
                   
                  
			<button class="btn btn-primary btn-flat md-close"  ng-click="addGroupEventDetail()" ng-hide="groupEvent.id>0"><spring:message code="label.premise.add" text="Add" /></button>
			<button class="btn btn-primary btn-flat md-close"  ng-click="addGroupEventDetail()" ng-show="groupEvent.id>0"><spring:message code="label.premise.edit" text="Edit" /></button>
            <button class="btn btn-default btn-flat md-close"  ng-click="gotoGroupEvent()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
			<input type="hidden" name="searchedGroupid" id="searchedGroupid"/>
			<input type="hidden" name="selectedCompany" id="selectedCompany"/>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3"></div>
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
						<button type="button" class="btn btn-primary btn-flat" ng-click="showAddGroupForm()"  ng-hide="viewAddForm"><spring:message code='label.employeegroup.button.addgroup'  text='New Group'/></button>
						</div>
                        <button type="button" data-dismiss="modal" class="btn btn-default btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                        <button type="button" data-dismiss="modal" class="btn btn-primary btn-flat md-close" ng-hide="viewAddForm || employeeGroups == null || employeeGroups.length == 0" ng-click="selectEmployeeGroup(groupEvent)"><spring:message code='label.employeegroup.button.proceed'  text='Proceed'/></button>
                        <button type="button"  class="btn btn-primary btn-flat" ng-show="viewAddForm && employeeList.length > 0" ng-click="addEmployeeGroup()"><spring:message code='label.employeegroup.button.save'  text='Save'/></button>
                      </div>
                    </div>
                  </div>
		<div class="md-overlay"></div>
</div>
