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
                      <input type="text" ng-model="breedingEvent.employeeGroupId"  name="employeeGroupId"  class="form-control" readonly = ""/><span class="input-group-addon btn btn-primary md-trigger "  ng-click="getEmployeeGroups()" data-modal="colored-primary"><span class="glyphicon glyphicon-th"></span></span>					   
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
                    <button class="btn btn-primary" ng-click="addBreedingEvent()"><spring:message code='label.piginfo.breedingeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-default"><spring:message code='label.piginfo.breedingeventform.cancel'  text='Cancel'/></button>
                    <button class="btn btn-danger pull-right" ng-click="deletePigInfo()" ng-show="breedingEvent.id != null && breedingEvent.id > 0" ng-confirm="<spring:message code='label.piginfo.breedingeventform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.breedingeventform.delete'  text='Delete'/></button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		  
		  
		  <div id="colored-primary" class="md-modal colored-header custom-width md-effect-9">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3>Employee Groups</h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
                      </div>
                      <div class="modal-body form">
					     <p class="color-primary">Please select a group and click to proceed</p>
                        <table>
						<thead>
                           <tr>
                           	 <th>Select </th>
                             <th> Group ID </th>
                             <th> Members </th>
                           </tr>
						   </thead>
						   <tbody>
                           <tr ng-repeat="employeeGroup in employeeGroups" ng-if="employeeGroups != null && employeeGroups.length > 0">
						   <td><input type="radio" ng-model="selected"></td>
                              <td> {{employeeGroup.groupId}} </td>
                              <td>
                                 <p ng-repeat="employee in employeeGroup.employeeList">  {{employee.name}} ({{employee.employeeId}}) </p>
                               </td>
                           </tr>
                           <tr ng-if="employeeGroups == null || employeeGroups.length == 0">
                             <td colspan="3">
                               No groups defined
                             </td>
                           </tr>
						   </tbody>
                        </table>
                       
                      </div>
                      <div class="modal-footer">
					    <div class="pull-left">
						<button type="button" data-dismiss="modal" class="btn btn-primary btn-flat md-close">Add New Group</button>
						</div>
                        <button type="button" data-dismiss="modal" class="btn btn-default btn-flat md-close">Cancel</button>
                        <button type="button" data-dismiss="modal" class="btn btn-primary btn-flat md-close">Proceed</button>
                      </div>
                    </div>
                  </div>

</div>


		

	
