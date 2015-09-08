<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.farroweventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="FarrowEventController" ng-init="loadPage(${CompanyId})">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div> 
 		  <div class="col-sm-6 col-md-6">
 		  <div class="block-flat">
		   <form name="farrowEventSearchForm" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.farroweventform.search.heading'  text='Search'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.farroweventform.search.errormessage' text='Please enter Pig Id/ Tattoo / Farrow Id and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.farroweventform.search.data.errormessage' text='Farrow event information not found for the search criteria'/></p>
			
            <input type="text" name="search" ng-model="searchText" placeholder="<spring:message code='label.piginfo.farroweventform.search.placeholder'  text='Search by Pig Id / Tattoo / Farrow Id ...'/>" class="form-control">

			 <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="getFarrowEventInformation()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1" class="icheck" value="pigId" > <spring:message code='label.piginfo.farroweventform.search.pigid.option'  text='Pig Id'/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad2" class="icheck" value="tattoo"> <spring:message code='label.piginfo.farroweventform.search.tattoo.option'  text='Tattoo'/> 
                </label>	
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad3" class="icheck" value="farrowId"> <spring:message code='label.piginfo.farroweventform.search.farrowid.option'  text='Farrow Id'/> 
                </label>				
              </div>
            </div>            
          </div>
		  </form>
		  <form name="farrowEventSearchResultForm"  ng-if="farrowEventList != null && farrowEventList.length != 0" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.farroweventform.searchresults.heading'  text='Farrow Events'/></h3>
             <table>
				<thead>
                     <tr>                       
                       <th><spring:message code='label.piginfo.farroweventform.farrowId'  text='Farrow Id'/> </th>
                       <th><spring:message code='label.piginfo.farroweventform.farrowDateTime'  text='Farrow Date'/> </th>
                       <th><spring:message code='label.employeegroup.list.header.action'  text='Action'/> </th>
                     </tr>
                 </thead>
                 <tbody>
                   <tr ng-repeat="farrowEventDto in farrowEventList">
                    <td>{{farrowEventDto.farrowId}}</td>
                    <td>{{farrowEventDto.farrowDateTime | date : 'yyyy-MM-dd'}}</td>
                    <td><button type="button" class="btn btn-edit btn-xs"
												ng-click="getFarrowEventDetails(farrowEventDto)">
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
 		  <div class="col-sm-3 col-md-3"></div>
 		</div>	
 		
          <div class="row" >
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.piginfo.farroweventform.farrowEvent'  text='Farrow Event'/></h3>
                  
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.farroweventform.submit.success.message' text='Farrow Event information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.farroweventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                  <div class="alert alert-success alert-white rounded" ng-show="entryEventDeleteMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.farroweventform.delete.message'  text='Farrow event information deleted'/>
                  </div>     
                  <div class="alert alert-danger alert-white rounded" ng-show="farrowEventValidation_ErrCode_1">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.farroweventform.farrowEventValidation_ErrCode1.message' text='Farrow event doesn\'t fall between the acceptated duration of the service date'/>
                  </div>   
                  <div class="alert alert-danger alert-white rounded" ng-show="farrowEventValidation_ErrCode_2">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.farroweventform.farrowEventValidation_ErrCode2.message' text='Farrow event details already captured for the selected pregnancy event'/>
                  </div>        
                  <div class="alert alert-danger alert-white rounded"  ng-show="entryEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.farroweventform.duplicate.error.message' text='A farrow record already exists with the same farrow id'/>
                  </div>    
                </div>
                <div class="content">
                  <form name="farroweventform" novalidate angular-validator my-reset>
                  <input type=hidden name="id" ng-model="farrowEvent.id"/>
				  <input type=hidden name="pregnancyEventId" ng-model="farrowEvent.pregnancyEventId"/>
				  
				  
					 <div class="form-group"> 
                      <label><spring:message code='label.piginfo.farroweventform.pigInfoId'  text='Pig Id'/><span style='color: red'>*</span></label>
                     <input type="text" ng-model="farrowEvent.pigId" id="pigId" name="pigId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.farroweventform.pigInfoId.placeholder'  text='Enter Piginfo Id'/>" 
                      required required-message="'<spring:message code='label.piginfo.farroweventform.pigInfoId.requiredmessage' text='Pig Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.farroweventform.pigInfoId.invalidmessage' text='Only Numeric values are allowed' />'" ng-blur="checkForPigId()" ng-focus="clearMessages()"/>
                    </div>
					<label ng-show="inValidPigIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.pigInfoId.server.invalidmessage' text='Invalid Pig Id for the company' /></label>
					<label ng-show="requiredPigIdMessage" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.pigInfoId.requiredmessage' text='Pig Info Id is required' /></label>
					<label ng-show="malePigIdentified" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.pigInfoId.server.malePigIdentified' text='The selected Pig Id is a boar.  Please select a Sow' /></label>
					
					<div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.pregnancyEventId'  text='Pregnancy Event'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" class="input-group col-md-7 col-xs-9"  >
                     	<input type="text" ng-model="farrowEvent.pregnancyEventType" id="pregnancyEventType" name="pregnancyEventType"  class="form-control" maxlength="30" readonly=""/>
						<span class="input-group-addon btn btn-primary" ng-click="searchPregnancyService(farrowEvent.pigId, farrowEvent.companyId)"  data-target="#searchPregnancyService"><span class="fa fa-search"></span></span>
						</div>
                    </div>
                    <label ng-show="pregnancyEventRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.pregnancyeventRequired' text='Select a pregnancy event' /></label>
					<label ng-show="inValidServiceIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.serviceId.server.invalidmessage' text='Invalid Pregnancy event for the selected Pig Id' /></label>					
					
                     <div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.employeegroup'  text='Employee Group'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span class="btn btn-primary" ng-click="viewEmployeeGroup()" data-toggle="modal" data-target="#selectEmployeeGroupModal"><span class="glyphicon glyphicon-user"></span></span>	
                      <input type="hidden" ng-model="farrowEvent.employeeGroupId" id="employeeGroupId" name="employeeGroupId"  class="form-control" readonly = ""/>
					  <div ng-show="farrowEvent.employeeGroup != null && farrowEvent.employeeGroup.id > 0">
							<p>Group Id : <small>{{farrowEvent.employeeGroup.groupId}}</small></p>
							<p ng-repeat="employee in farrowEvent.employeeGroup.employeeList">{{employee.name}} ( Id : {{employee.id}})</p>
						</div>
                      </div>
                    </div>
                    
                    
				  	<div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.farrowId'  text='Farrow Id'/><span style='color: red'>*</span></label>
                      <input ng-hide="farrowEvent.id != null && farrowEvent.id != 0"  type="text" name="farrowId" ng-model="farrowEvent.farrowId"  maxlength="30" 
                      placeholder="<spring:message code='label.piginfo.farroweventform.farrowId.placeholder'  text='Enter Farrow Id'/>" required
                      required-message="'<spring:message code='label.piginfo.farroweventform.farrowId.requiredmessage' text='Farrow Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.farroweventform.farrowId.invalidmessage' text='Only Alpha Numeric values are allowed' />'"  class="form-control">
				      <p ng-show="farrowEvent.id != null && farrowEvent.id != 0">{{farrowEvent.farrowId}}</p>
                    </div>
                    
                    
                    <div class="form-group">   
					
                    <label><spring:message code='label.piginfo.farroweventform.pigletinfo'  text='Piglet Information'/></label>
                    <label ng-show="invalidFarrowValue" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.invalidFarrowValue' text='Piglet information should be in whole numeric values' /></label>
                    <label ng-show="invalidFarrowCount" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.invalidFarrowCount' text='Farrow count doesn\'t match. Please check' /></label>
                    
                    <table class="table">
                     <tr>
                       <td> <label><spring:message code='label.piginfo.farroweventform.liveborns'  text='Live Borns'/> </label></td><td><input type="number" ng-value="0" name="farrowId" ng-model="farrowEvent.liveBorns"  maxlength="3"  size="3" class="form-control"></td>
                       <td><label><spring:message code='label.piginfo.farroweventform.stillborns'  text='Still Borns'/></label> </td><td><input type="number" ng-value = "0" name="farrowId" ng-model="farrowEvent.stillBorns"  maxlength="3"  size="3" class="form-control"></td>
                     </tr>
                     <tr>
                       <td><label><spring:message code='label.piginfo.farroweventform.maleborns'  text='Male Borns'/></label> </td><td><input type="number" ng-value="0" name="farrowId" ng-model="farrowEvent.maleBorns"  maxlength="3"  size="3" class="form-control"></td>
                       <td><label><spring:message code='label.piginfo.farroweventform.femaleborns'  text='Female Borns'/></label> </td><td><input type="number" ng-value="0" name="farrowId" ng-model="farrowEvent.femaleBorns"  maxlength="3"  size="3" class="form-control"></td>
                     </tr>
                     <tr>
                       <td><label><spring:message code='label.piginfo.farroweventform.mummies'  text='Mummies'/></label> </td><td> <input type="number" ng-value="0"  name="farrowId" ng-model="farrowEvent.mummies"  maxlength="3"  size="3" class="form-control"></td>
                       <td>&nbsp;</td>
					   <td>&nbsp;</td>
                     </tr>
                    </table>                    
                    </div>
                    
                    
                     <div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.pen'  text='Pen'/></label>
                     <select class="form-control" ng-model="farrowEvent.penId">
                          <option ng-repeat="pen in penInfo" value="{{pen.id}}">{{pen.penId}}</option>
                        </select>
                    </div>
                   
                    
                    <div class="form-group">
                    
                    <label>Weight(kgs)</label>
                    <input type="number" name="farrowId" ng-model="farrowEvent.weightInKgs"  maxlength="5"  size="3" class="form-control">
                    </div>
                    <div class="form-group">
                    	<label>Type of Birth</label>
                		<label class="radio-inline">
                  			<input type="radio" name="rad1" id="birthType" class="icheck" value="induced" ng-model="farrowEvent.inducedBirth"> <spring:message code='label.piginfo.farroweventform.inducedBirth'  text='Induced Birth'/>
                		</label>
                		<label class="radio-inline">
                  			<input type="radio" name="rad1"  id="birthType" class="icheck" value="assisted" ng-model="farrowEvent.assistedBirth"> <spring:message code='label.piginfo.farroweventform.assistedBirth'  text='Assisted Birth'/> 
                		</label>		
              		</div>
                    
                    
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.farrowDateTime'  text='Farrow Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="farrowDate" name="farrowDate" ng-model="farrowEvent.farrowDateTime" readonly="" class="form-control" format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                    <label ng-show="farrowDateRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.farrowDate.requiredmessage' text='Farrow Date is required' /></label>      
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.sowcondition'  text='Sow Condition'/><span style='color: red'>*</span></label>
                      <select class="form-control" name="sowCondition" ng-model="farrowEvent.sowCondition" required required-message="'<spring:message code='label.piginfo.farroweventform.sowcondition.requiredmessage' text='Sow condition is required'/>'">
                           <option value="1">1 - <spring:message code='label.piginfo.entryeventform.sowcondition.least.message'  text='Least Healthy'/></option>
                          <option value="2">2 - <spring:message code='label.piginfo.entryeventform.sowcondition.normal.message'  text='Healthy'/></option>
                          <option value="3">3 - <spring:message code='label.piginfo.entryeventform.sowcondition.most.message'  text='Most Healthiest'/></option>
                        </select>
                    </div>
                    
                     <div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.remarks'  text='Remarks'/><span style='color: red'>*</span></label>
                      <textarea name="remarks" ng-model="farrowEvent.remarks" class="form-control" placeholder="<spring:message code='label.piginfo.farroweventform.remarks.placeholder' text='Enter remarks'/>" required required-message="'<spring:message code='label.piginfo.farroweventform.remarks.requiredmessage' text='Remarks is required'/>'"></textarea>
                    </div>
                    
                    <button class="btn btn-primary" ng-click="addFarrowEvent()" type="submit" ng-disabled="inValidPigIdFromServer || malePigIdentified"><spring:message code='label.piginfo.farroweventform.submit'  text='Submit'/></button>
                    <button class="btn btn-default" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.farroweventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="deleteFarrowEvent()" ng-show="farrowEvent.id != null && farrowEvent.id > 0" ng-confirm-click="<spring:message code='label.piginfo.farroweventform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.farroweventform.delete'  text='Delete'/></button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		  
		  <!-- - Breeding Service Id search Modal -->
		  <div id="searchPregnancyService" class="modal colored-header custom-width">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.piginfo.pregnancyeventform.searchresults.heading'  text='Pregnancy Events'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
                      </div>
                      <div class="modal-body form" >
                      <table>
                       <thead>
                           <th><spring:message code='label.employeegroup.list.header.select'  text='Select'/> </th>                           
	                        <th><spring:message code='label.piginfo.pregnancyeventform.examDate'  text='Exam Date'/> </th>
                       		<th><spring:message code='label.piginfo.pregnancyeventform.resultDate'  text='Result Date'/> </th>
                       </thead>
                       <tbody>
	                   <tr ng-repeat="pregnancyEventObj in pregnancyEventList" ng-if="pregnancyEventList != null && pregnancyEventList.length > 0">
	                    <td><input type="radio" name="pregnancyEventDtoId" id="pregnancyEventDtoId" ng-model="farrowEvent.pregnancyEventDto" ng-value="pregnancyEventObj"></td>
	                    <td>{{pregnancyEventObj.examDate | date : 'yyyy-MM-dd'}}</td>
	                    <td>{{pregnancyEventObj.resultDate | date : 'yyyy-MM-dd'}}</td>
	                   </tr>
	                   <tr ng-if="pregnancyEventList == null || pregnancyEventList.length == 0">
	                     <td colspan="3">
	                       <spring:message code='label.piginfo.farroweventform.list.pregnancyevents.noresults'  text='No pregnancy events found'/>
	                     </td>
	                   </tr>
	                   
	                 </tbody>
                      </table>
                      </div>
                      <div class="modal-footer">
                      <button type="button" class="btn btn-primary btn-flat md-close" ng-hide="pregnancyEventList == null || pregnancyEventList.length == 0" ng-click="selectPergnancyEventService()"><spring:message code='label.employeegroup.list.header.select'  text='Select'/></button>
                      <button type="button" data-dismiss="modal" class="btn btn-default btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
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
                        <button type="button" data-dismiss="modal" class="btn btn-primary btn-flat md-close" ng-hide="viewAddForm || employeeGroups == null || employeeGroups.length == 0" ng-click="selectEmployeeGroup(farrowEvent)"><spring:message code='label.employeegroup.button.proceed'  text='Proceed'/></button>
                        <button type="button"  class="btn btn-primary btn-flat" ng-show="viewAddForm && employeeList.length > 0" ng-click="addEmployeeGroup()"><spring:message code='label.employeegroup.button.save'  text='Save'/></button>
                      </div>
                    </div>
                  </div>


		<div class="md-overlay"></div>
</div>

  
		  

	
