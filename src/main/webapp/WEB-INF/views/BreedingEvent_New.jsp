<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2>${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="BreedingEventController" ng-init="setCompanyId(${CompanyId},'${BreedingEventId}')" id="BreedingEventControllerId">
 
  <div class="row" ng-show="!AddMatingDetailsForm">
 		  <div class="col-sm-2 col-md-2"></div>
 		  <div class="col-sm-8 col-md-8">
 		  <div class="block-flat">
		   <form name="breedingEventSearchForm">
 		     <div class="head">
 		     
 		     <h3> <spring:message code='label.piginfo.breedingeventform.search.heading'  text='Search Breeding Events'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.breedingeventform.search.errormessage' text='Please enter Pig Id/ Tattoo and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.breedingeventform.search.data.errormessage' text='Breeding event information not found for the search criteria'/></p>
			<div  class="form-group">
              <select  class="form-control"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise" ng-change="loadPigInfo()" >
			  <option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
              <option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
              </select>
			</div>
			
            <div class="options">
			  <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1" class="breedingevent" value="pigId" ng-model="selectGroup"> <spring:message code='label.piginfo.breedingeventform.search.pigid.option'  text='Pig Id'/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad2" class="breedingevent" value="tattoo" ng-model="selectGroup"> <spring:message code='label.piginfo.breedingeventform.search.tattoo.option'  text='Tattoo'/> 
                </label>				
              </div>
            </div>
			<div  class="form-group" ng-show="selectGroup==='pigId'">
				 
				  <button type="button" class="btn btn-primary active btn-group pull-right" ng-enter="getBreedingEventInformation()" ng-click="getBreedingEventInformation()"><i class="fa fa-search"></i></button>
				
					 <select  class="form-control"  name="search" ng-model="searchText" style="width:90%">
								<option value="" hidden><spring:message code='label.piginfo.pigId.placeholder' text='Select PigId' /></option>
								<option ng-repeat="pigInfo in pigInfoList" value="{{pigInfo.pigId}}" ng-value="pigInfo.pigId" ng-selected="searchText == pigInfo.pigId">{{pigInfo.pigId}}</option>
								</select>
			</div>
			<div  class="form-group" ng-show="selectGroup==='tattoo'">
				 
				  <button type="button" class="btn btn-primary active btn-group pull-right" ng-enter="getBreedingEventInformation()" ng-click="getBreedingEventInformation()"><i class="fa fa-search"></i></button>
				
					 <select  class="form-control"  name="search" ng-model="searchText" style="width:90%">
								<option value="" hidden><spring:message code='label.piginfo.tatto.placeholder' text='Select Tattoo' /></option>
								<option ng-repeat="pigInfo in pigInfoList" value="{{pigInfo.tattoo}}" ng-value="pigInfo.tattoo" ng-selected="searchText == pigInfo.tattoo">{{pigInfo.tattoo}}</option>
								</select>
			</div>
          </div>
		  </form>
		  <form name="breedingEventSearchResultForm"  ng-if="breedingEventList != null && breedingEventList.length != 0" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.breedingeventform.searchresults.heading'  text='Breeding Events'/></h3>
            <div class="table-responsive">
             <table>
				<thead>
                     <tr>
					 <th><spring:message code='label.piginfo.breedingeventform.pigInfoId'  text='Pig Id'/> </th>
                       <th><spring:message code='label.piginfo.breedingeventform.breedingServiceType'  text='Breeding Service Type'/> </th>
                       <th><spring:message code='label.piginfo.breedingeventform.serviceStartDate'  text='Service Start Date'/> </th>
                       <th><spring:message code='label.employeegroup.list.header.action'  text='Action'/> </th>
                     </tr>
                 </thead>
                 <tbody>
                   <tr ng-repeat="breedingEventDto in breedingEventList">
				   <td>{{breedingEventDto.pigInfoId}}</td>
                    <td>{{breedingEventDto.breedingServiceType}}</td>
                    <td>{{DateUtils.getFormatedDate(breedingEventDto.serviceStartDate)}}</td>
                    <td><button type="button" class="btn btn-edit btn-xs"
												ng-click="getBreedingEventDetailsObj(breedingEventDto)">
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
 		  <div class="col-sm-2 col-md-2"></div>
 		</div>
 
 
          <div class="row"  ng-show="!AddMatingDetailsForm">
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
                  <div class="alert alert-danger alert-white rounded"  ng-show="matingDetailsDeleteErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.matingdetails.delete.error' text='Pregnancy checks are already associated for this event, hence can not delete'/>
                  </div>  
                  <div class="alert alert-danger alert-white rounded"  ng-show="entryEventDeleteErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.matingdetails.delete.error' text='Pregnancy checks are already associated for this event, hence can not delete'/>
                  </div> 
                   <div class="alert alert-danger alert-white rounded" ng-show="breedingEventIncompleteCycle"> 
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_IncompleteCycle' text='There is an incomplete breeding cycle. Please complete that before starting another cycle' />
                  </div>
                  <div class="alert alert-success alert-white rounded"  ng-show="matingDetailsSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.matingdetailsform.submit.success.message' text='Mating details saved successfully'/>
                  </div>
                </div>
                <div class="content">
                  <form name="breedingeventform" novalidate angular-validator my-reset>
                  <input type=hidden name="id" ng-model="breedingEvent.id"/>
                  <input type=hidden name="id" ng-model="breedingEvent.penId"/> 
                    <!--  <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.employeegroup'  text='Employee Group'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span class="btn btn-success" ng-click="viewEmployeeGroup()" data-toggle="modal" data-target="#selectEmployeeGroupModal"><span class="glyphicon glyphicon-user"></span></span>	
                      <input type="hidden" ng-model="breedingEvent.employeeGroupId" id="employeeGroupId" name="employeeGroupId"  class="form-control" readonly = ""/>
					  <div ng-show="breedingEvent.employeeGroup != null && breedingEvent.employeeGroup.id > 0">
							<p>Group Id : <small>{{breedingEvent.employeeGroup.groupId}}</small></p>
							<p ng-repeat="employee in breedingEvent.employeeGroup.employeeList">{{employee.name}} ( Id : {{employee.id}})</p>
						</div>
                      </div>
                    </div>	 -->	
                    
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
                       <select class="form-control" ng-change="checkForPigId()"  name="premiseId" id="premiseId" ng-model="breedingEvent.premiseId" required required-message="'<spring:message code='label.piginfo.farroweventform.premise.requiredmessage' text='Premise is required' />'">
                       	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="breedingEvent.premiseId == premise.id">{{premise.name}}</option>
                        </select>
                    </div>	
				  
				  
                    			
					<div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.pigInfoId'  text='Pig Id'/><span style='color: red'>*</span></label>
                      <label  ng-show="breedingEvent.id != null && breedingEvent.id > 0" >{{breedingEvent.pigInfoId}}</label>
                     <input type="text" ng-show="breedingEvent.id == null" ng-model="breedingEvent.pigInfoId" id="pigInfoId" name="pigInfoId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.breedingeventform.pigInfoId.placeholder'  text='Enter Piginfo Id'/>" 
                      required required-message="'<spring:message code='label.piginfo.breedingeventform.pigInfoId.requiredmessage' text='Pig Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.breedingeventform.pigInfoId.invalidmessage' text='Only Numeric values are allowed' />'" ng-blur="checkForPigId()" ng-focus="clearMessages()"/>	
                    </div>
					<div>
					<label ng-show="malePigIdentified" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.breedingeventform.pigInfoId.server.malePigIdentified' text='The selected Pig Id is a boar.  Please select a Sow' /></label>
					<label ng-show="inValidPigIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.breedingeventform.pigInfoId.server.invalidmessage' text='Invalid Pig Id for the company' /></label>
					</div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.breedingServiceType'  text='Breeding Service Type'/></label>
                       <select class="form-control"  id="sexType" name="sexType" ng-model="breedingEvent.breedingServiceTypeId"  ng-options="k as v for (k, v) in breedingServiceTypes">
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.breedinggroupId'  text='Service Group Id'/></label>
                      <input type="text" ng-model="breedingEvent.breedingGroupId" id="breedingGroupId" name="breedingGroupId"  class="form-control"  placeholder="<spring:message code='label.piginfo.breedingeventform.breedinggroupId.placeholder'  text='Enter Breeding group Id'/>"/>
                    </div>  
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.sowcondition'  text='Sow Condition'/></label>
                      <i>[1:  <spring:message code='label.piginfo.entryeventform.sowcondition.least.message'  text='Least Healthy'/> - 5:<spring:message code='label.piginfo.entryeventform.sowcondition.most.message'  text='Most Healthiest'/>]</i>
                      <select class="form-control" id="sowCondition" name="sowCondition" ng-model="breedingEvent.sowCondition" >
                           <option value="1">1</option>
                          <option value="2">2</option>
                          <option value="3">3</option>
                          <option value="4">4</option>
                          <option value="5">5</option>
                        </select>
                    </div>
                    
                     <div class="form-group">                    
                    	<label><spring:message code='label.piginfo.breedingeventform.weightInKgs'  text='Weight'/></label>
                    	<input type="number" name="weightInKgs" min="0" step="0.01" ng-model="breedingEvent.weightInKgs"  maxlength="5"  size="3" class="form-control">
                    </div>
                    
                    <div class="form-group">                    
                    	<label><spring:message code='label.piginfo.breedingeventform.serviceStartDate'  text='Service Start Date'/></label>
                    	<label ng-if="breedingEvent.serviceStartDate == null"><spring:message code='label.piginfo.breedingeventform.serviceStartDatedefault'  text='-:-'/></label>
                    	<label ng-if="breedingEvent.serviceStartDate != null"> -  {{DateUtils.getFormatedDate(breedingEvent.serviceStartDate)}}</label>
                    </div>
                    
                    <button class="btn btn-success" ng-click="addBreedingEvent()" type="submit" ng-disabled="inValidPigIdFromServer || malePigIdentified"><spring:message code='label.piginfo.breedingeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-warning" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.breedingeventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="deleteBreedingEventInfo()" ng-show="breedingEvent.id != null && breedingEvent.id > 0" ng-confirm-click="<spring:message code='label.piginfo.breedingeventform.delete.confirmmessage'  text='Are you sure you want to delete the entry and delete all the associated mating details?'/>"><spring:message code='label.piginfo.breedingeventform.delete'  text='Delete'/></button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
          
          
          <!--  Add Mating details form -->
          
          
          <div class="row"  ng-show="AddMatingDetailsForm">
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.piginfo.matingdetailsform.matingDetails.heading'  text='Mating Details'/></h3>
                  
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.breedingeventform.submit.success.message' text='Breeding information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
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
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_EntryDate' text='Breeding date can not be earlier than entry date' />
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
                <div class="alert alert-danger alert-white rounded" ng-show="breedingEventValidation_ErrCode_DuplicateMatingDate"> 
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_DuplicateMatingDate' text='Mating detail record already exists for the selected date' />
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="breedingEventValidation_ErrCode_PregCheckAdded"> 
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_PregCheckAdded' text='Pregnancy Events are already tracked for this event' />
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="breedingEventValidation_ErrCode_NextEvent"> 
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.breedingeventform.breedingEventValidation_ErrCode_NextEvent' text='Please enter the next cycle of service as another event' />
                  </div>                  
                <div class="content">
                  <form name="matingdetailsform" novalidate angular-validator my-reset>                  
                                       
                     <div class="form-group">
                      <label><spring:message code='label.piginfo.matingdetailsform.matingdate'  text='Mating Date'/><span style='color: red'>*</span></label> <i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="matingDetails.matingDateStr" mask="39/19/2999 24:60" mask-validate='true' ng-blur="dateCheck(matingDetails.matingDateStr, 'matingDate')" />
                    </div>
                     <div class="form-group">
                      <label><spring:message code=''  text='Mating Time'/><span style='color: red'>*</span></label> <i><spring:message code=''  text='(in hh:mm format)'/></i>
                      	<input type="text" class="form-control"   />
                    </div>
                    <div>
                    <label ng-show="matingDateRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.matingdetailsform.matingdate.requiredmessage' text='Mating Date is required' /></label>
                    </div>
                   
                   
                      <div class="form-group">
                      <label><spring:message code='label.piginfo.matingdetailsform.employeegroup'  text='Employee Group'/></label>
                      <div data-min-view="2"  class="input-group col-md-5 col-xs-7"  >
					  <span class="btn btn-success" ng-click="viewEmployeeGroup()" data-toggle="modal" data-target="#selectEmployeeGroupModal"><span class="glyphicon glyphicon-user"></span></span>	
                      <input type="hidden" ng-model="matingDetails.employeeGroupId" id="employeeGroupId" name="employeeGroupId"  class="form-control" readonly = ""/>
					  <div ng-show="matingDetails.employeeGroup != null && matingDetails.employeeGroup.id > 0">
							<p>Group Id : <small>{{matingDetails.employeeGroup.groupId}}</small></p>
							<p ng-repeat="employee in matingDetails.employeeGroup.employeeList">{{employee.name}} ( Id : {{employee.id}})</p>
						</div>
                      </div>
                    </div>	 				
					<div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.semenId'  text='Semen Id'/></label>
                     <input type="text" ng-model="matingDetails.semenId" id="semenId" name="semenId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.breedingeventform.semenId.placeholder'  text='Enter Semen Id'/>" 
                      required-message="'<spring:message code='label.piginfo.breedingeventform.semenId.requiredmessage' text='Semen Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.breedingeventform.semenId.invalidmessage' text='Only Alpha Numeric values are allowed' />'"/>
                    </div>
                    
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.matingdetailsform.semendate'  text='Semen Date'/></label> <i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="matingDetails.semenDateStr" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(matingDetails.semenDateStr, 'semenDate')"/>                      	
                    </div>                    
                    
					<div class="form-group">
                      <label><spring:message code='label.piginfo.breedingeventform.mateQuality'  text='Mating Quality'/></label>
                      <select ng-model="matingDetails.matingQuality" name="matingQuality" id="matingQuality" class="form-control">
                      <option value="1"><spring:message code='label.piginfo.breedingeventform.mateQuality.good'  text='Good'/></option>
                      <option value="2"><spring:message code='label.piginfo.breedingeventform.mateQuality.ok'  text='OK'/></option>
                      <option value="3"><spring:message code='label.piginfo.breedingeventform.mateQuality.poor'  text='Poor'/></option>
                      </select>
                    </div>                   
                    
                    <button class="btn btn-success" ng-click="saveMatingDetails()" type="submit"><spring:message code='label.piginfo.breedingeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-warning" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.breedingeventform.cancel'  text='Clear Form'/></button>                    
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
          
          <!-- - End of mating details form -->
          
          
           <!-- -- MAting details -->
             <div class="row"  ng-show="!AddMatingDetailsForm">
		 
            <div class="col-sm-12 col-md-12">
              <div class="block-flat">
            <button type="button" ng-click="addMatingDetailData()" class="btn btn-sm btn btn-success" ng-show="(breedingEvent.id != null && breedingEvent.id > 0) || entryEventSuccessMessage">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.breedingeventform.addMatingDetails" text="Add Mating Details" />
		</button>
	<form name="matingdetailsform" method="post">	
		<div class="content" ng-show="(breedingEvent.id != null && breedingEvent.id > 0) || entryEventSuccessMessage">
			<div class="table-responsive">
			<table st-table="displayedCollection" st-safe-src="breedingEvent.matingDetailsList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>
						<th style="width:20%"><spring:message code="label.piginfo.matingdetailsform.matingdate" text="Mating Date" /></th>
						<th style="width:20%"><spring:message code="label.matingdetailsform.employeeGroupId" text="Group Id" /></th>
						<th style="width:20%"><spring:message code="label.matingdetailsform.semenId" text="Semen Id" /></th>
						<th style="width:20%"><spring:message code="label.matingdetailsform.semendate" text="Semen Date" /></th>
						<th style="width:20%"><spring:message code="label.matingdetailsform.matingQuality" text="Mating Quality" /></th>
						<th style="width:5%"><spring:message code="label.groupEventDetail.edit" text="Edit" /></th>
						<th style="width:20%"><spring:message code="label.piginfo.breedingeventform.delete" text="Delete" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:20%">{{DateUtils.getFormatedDate(row.matingDate)}}</td>
					<td style="width:20%">{{row.employeeGroup.groupId}}</td>					
					<td style="width:20%">{{row.semenId}}</td>
					<td style="width:20%">{{DateUtils.getFormatedDate(row.semenDate)}}</td>
					<td style="width:20%">
					<p ng-show="row.matingQuality == 1"><spring:message code='label.piginfo.breedingeventform.mateQuality.good'  text='Good'/></p>
                    <p ng-show="row.matingQuality == 2"><spring:message code='label.piginfo.breedingeventform.mateQuality.ok'  text='OK'/></p>
                    <p ng-show="row.matingQuality == 3"><spring:message code='label.piginfo.breedingeventform.mateQuality.poor'  text='Poor'/></p>
                    &nbsp;
					</td>
					<td style="width: 5%">
						<button type="button" class="btn btn-edit btn-xs" ng-click="editMatingDetails(row)">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
					</td>	
					<td style="width:20%">
						<button type="button" class="btn btn-danger btn-xs" ng-click="deleteMatingDetails(row)" ng-confirm-click="<spring:message code='label.matingdetailsform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>">
							<spring:message code="label.piginfo.breedingeventform.delete" text="Delete" /></a></button>	
											
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
	</form>	 
     </div>             
      </div>        
      </div>          
           <!-- -- MAting details -->
          
		  
		  <div id="selectEmployeeGroupModal" class="modal colored-header warning custom-width" ng-controller="EmployeeGroupController" ng-init="getEmployeeGroups()">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.employeegroup.heading'  text='Employee Groups'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">�</button>
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
	                          <input type="text" placeholder="<spring:message code='label.employeegroup.add.groupId.placeholder'  text='Enter Group Id'/>" class="form-control" ng-model="employeeGrp.groupId" ng-readonly="employeeGrp.id > 0">
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
                        <button type="button" data-dismiss="modal" class="btn btn-success btn-flat md-close" ng-hide="viewAddForm || employeeGroups == null || employeeGroups.length == 0" ng-click="selectEmployeeGroup(matingDetails)"><spring:message code='label.employeegroup.button.proceed'  text='Proceed'/></button>
                        <button type="button"  class="btn btn-success btn-flat" ng-show="viewAddForm && employeeList.length > 0" ng-click="addEmployeeGroup()"><spring:message code='label.employeegroup.button.save'  text='Save'/></button>
                      </div>
                    </div>
                  </div>      
                  
		<div class="md-overlay"></div>
</div>

		  

	
