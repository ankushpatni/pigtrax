<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.pigletstatuseventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="PigletStatusEventController" ng-init="loadPage(${CompanyId})">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div> 
 		  <div class="col-sm-6 col-md-6">
 		  <div class="block-flat">
		   <form name="farrowEventSearchForm" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pigletstatuseventform.search.heading'  text='Search'/> Piglet Status</h3>
            <p class="color-danger" ng-show="searchErrorMessage">
            			<spring:message code='label.piginfo.pigletstatuseventform.search.errormessage' 
            			text='Please enter Pig Id / Tattoo Id and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage">
            	<spring:message code='label.piginfo.pigletstatuseventform.search.data.errormessage' 
            	text='Piglet status event information not found for the search criteria'/></p>
			
            <input type="text" name="search" ng-model="searchText" 
            placeholder="<spring:message code='label.piginfo.pigletstatuseventform.search.placeholder'  text='Search by Pig Id / Tattoo ...'/>" 
            class="form-control">

			 <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="searchPigletStatusEvents()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1" class="icheck" value="pigId" > <spring:message code='label.piginfo.pigletstatuseventform.search.pigid.option'  text='Pig Id'/>
                </label>              
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad2" class="icheck" value="tattooId"> <spring:message code='label.piginfo.pigletstatuseventform.search.tattooid.option'  text='Tattoo Id'/> 
                </label>				
              </div>
            </div>            
          </div>
		  </form>
		  <form name="pigletStatusEventSearchResultForm"  ng-if="pigletStatusEventList != null && pigletStatusEventList.length != 0" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pigletstatuseventform.searchresults.heading'  text='Piglet Status Events'/></h3>
             <table>
				<thead>
                     <tr>                       
                       <th><spring:message code='label.piginfo.pigletstatuseventform.eventDateTime'  text='Farrow Id'/> </th>
                       <th><spring:message code='label.piginfo.pigletstatuseventform.weaninggroupid'  text='Wean Group Id'/> </th>
                       <th><spring:message code='label.employeegroup.list.header.action'  text='Action'/> </th>
                     </tr>
                 </thead>   
                 <tbody>
                   <tr ng-repeat="pigletStatusEventDto in pigletStatusEventList"> 
					<td >{{pigletStatusEventDto.farrowEventDto.farrowId}}</td>                   
                    <td >{{pigletStatusEventDto.weanGroupId}}</td>
                    <td><button type="button" class="btn btn-edit btn-xs" ng-click="setPigletStatusDetails(pigletStatusEventDto)">
						<span class="glyphicon glyphicon-pencil"></span>
						<spring:message code="label.employeegroup.list.edit" text="Edit" />												
						</button>
					</td>
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
                  <h3><spring:message code='label.piginfo.pigletstatuseventform.pigletStatusEvent'  text='Piglet Status'/></h3>
                  
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pigletstatuseventform.submit.success.message' text='Piglet Status Event information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pigletstatuseventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                  <div class="alert alert-success alert-white rounded" ng-show="entryEventDeleteMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pigletstatuseventform.delete.message'  text='Piglet Status event information deleted'/>
                  </div>     
                  <div class="alert alert-warning alert-white rounded" ng-show="pigletstatusEventValidation_ErrCode_1">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-warning"></i></div><spring:message code='label.piginfo.pigletstatuseventform.pigletstatusEventValidation_ErrCode_1' text='Wean should happen within 0-60 days of farrow event, please change the date and submit again to proceed' />
                  </div>  
                  <div class="alert alert-warning alert-white rounded" ng-show="pigletstatusEventValidation_ErrCode_2">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-warning"></i></div><spring:message code='label.piginfo.pigletstatuseventform.pigletstatusEventValidation_ErrCode_2' text='Foster should happen within 0-50 days of farrow event, please change the date and submit again to proceed' />
                  </div>                         
                  <div class="alert alert-warning alert-white rounded" ng-show="pigletstatusEventValidation_ErrCode_3">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-warning"></i></div><spring:message code='label.piginfo.pigletstatuseventform.pigletstatusEventValidation_ErrCode_3' text='The total count of Piglets is not the same as alive Piglets at the time of Farrow. Please account for the entire litter' />
                  </div>                               
                </div>
                <div class="content">
                  <form name="pigletstatuseventform" novalidate angular-validator my-reset>
                  <input type=hidden name="id" ng-model="pigletStatusEvent.id"/>
                   <input type=hidden name="fosterTo" ng-model="pigletStatusEvent.fosterTo"/>
				  <input type=hidden name="pigInfoId" ng-model="pigletStatusEvent.pigInfoId"/>				  
				  <input type=hidden name="fosterFarrowId" ng-model="pigletStatusEvent.fosterFarrowEventId"/>
				  
				  
					 <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigletstatuseventform.pigId'  text='Pig Id'/></label>
                     
					  <div data-min-view="2" class="input-group col-md-7 col-xs-9"  >
                     	<input type="text" ng-model="pigletStatusEvent.pigId" id="pigId" name="pigId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigletstatuseventform.pigId.placeholder'  text='Enter Pig Id'/>" 
                      required-message="'<spring:message code='label.piginfo.pigletstatuseventform.pigId.requiredmessage' text='Pig Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.pigletstatuseventform.pigId.invalidmessage' text='Only Numeric values are allowed' />'" />
						
						<span class="input-group-addon btn btn-primary" ng-click="searchFarrowEvent(pigletStatusEvent.pigId, pigletStatusEvent.companyId)"  data-target="#searchFarrowEvents" ><a class="btn-primary">Farrow Event</a></span>
						</div>	
						
                    </div>
					<label ng-show="inValidPigIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigletstatuseventform.pigInfoId.server.invalidmessage' text='Invalid Pig Id for the company' /></label>
					<label ng-show="requiredPigIdMessage" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigletstatuseventform.pigInfoId.requiredmessage' text='Pig Info Id is required' /></label>
					<label ng-show="pigletStatusEventAlreadyAdded" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigletstatuseventform.pigInfoId.pigletStatusEventCreated' text='Piglet Status already captured for the selected Pig Id ana farrow event. Use search feature to modify it.' /></label>
				    <div class=" block-flat bars-widget" ng-if="pigletStatusEvent.farrowEventId != null && pigletStatusEvent.farrowEventId>0 && !pigletStatusEventAlreadyAdded">
		                <div class="spk4 pull-right spk-widget"></div>  
		                <h4>Farrow Details</h4><div></div>
		                <div>Farrow Event Date : {{pigletStatusEvent.farrowEventDto.farrowDateTime | date : 'yyyy-MM-dd'}}</div>
		                <div>Live Borns : {{pigletStatusEvent.farrowEventDto.liveBorns}}</div>
		                <div>Male Borns : {{pigletStatusEvent.farrowEventDto.maleBorns}}</div>
		                <div>Female Borns : {{pigletStatusEvent.farrowEventDto.femaleBorns}}</div>
		            </div>					 
					 <div class=" block-flat bars-widget" ng-if="fosterInRecords != null && fosterInRecords.length != 0">
		                <div class="spk4 pull-right spk-widget"></div>
		                <h4>Foster In Details</h4><div></div>
		                <table>
		                  <thead>
		                  <tr>
		                   <th><spring:message code='label.piginfo.pigletstatuseventform.fosterFrom' text='Foster From' /></th>
		                   <th><spring:message code='label.piginfo.pigletstatuseventform.numberOfPigs' text='No of Pigs' /></th>
		                   <th><spring:message code='label.piginfo.pigletstatuseventform.weightInKg' text='Weight (Kg)' /></th>
		                   </tr>
		                  </thead>
		                  <tbody>
		                    <tr ng-repeat="fosterInObj in fosterInRecords" ng-if="fosterInRecords != null && fosterInRecords.length != 0">
		                      <td>{{fosterInObj.fosterFromPigId}}</td>
		                      <td>{{fosterInObj.numberOfPigs}}</td>
		                      <td>{{fosterInObj.weightInKgs}}</td>
		                   </tr>		                  
		                  </tbody>
		                </table>
		            </div>
					 
					 
					 <div class="form-group">   
					
                    <label><spring:message code='label.piginfo.pigletstatuseventform.pigletStatusEventText'  text='Piglet Status Event'/></label>
                    <div>&nbsp;</div>
 					<!-- <div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="eventDateTimerequired" ><spring:message code='label.piginfo.pigletstatuseventform.fosterToPigId.requiredMessage' text='Event Date is required' /></label>
					</div>    -->
 					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="fosterToPigIdrequired" ><spring:message code='label.piginfo.pigletstatuseventform.fosterToPigId.requiredMessage' text='Pig Id for foster is required' /></label>
					</div>					                    
                    <table class="table">
                     <thead>
                       <tr>
                         <td>&nbsp; </td>
                         <td><spring:message code='label.piginfo.pigletstatuseventform.numberOfPigs' text='No of Pigs' /> </td>
                         <td><spring:message code='label.piginfo.pigletstatuseventform.weightInKg' text='Weight (Kg)' /> </td>
                         <td><spring:message code='label.piginfo.pigletstatuseventform.fosterToPigId' text='Foster To - Pig Id' /></td>
                        </tr>
                     </thead>
                     <tbody>
                       <tr>
                         <td><spring:message code='label.piginfo.pigletstatuseventform.wean' text='Wean' /> </td>
                         <td><input type="number" min="1" ng-value="0" name="weanPigNum" ng-model="pigletStatusEvent.weanPigNum"  maxlength="3"  size="3" class="input-sm form-control"> </td>
                         <td><input type="number" min="0" ng-value="0" name="weanPigWt" ng-model="pigletStatusEvent.weanPigWt"  maxlength="8"  size="8"   step="0.01" class="input-sm form-control"> </td>
                         <td><spring:message code='label.piginfo.pigletstatuseventform.notApplicable' text='N/A' /> </td>
                       </tr>
                       <tr>
                        <td><spring:message code='label.piginfo.pigletstatuseventform.foster' text='Foster' /> </td>
                         <td><input type="number" min="1" ng-value="0" name="fosterPigNum" ng-model="pigletStatusEvent.fosterPigNum"  maxlength="3"  size="3" class="input-sm  form-control"> </td>
                         <td><input type="number" min="0" ng-value="0" name="fosterPigWt" ng-model="pigletStatusEvent.fosterPigWt"  maxlength="8"   step="0.01" size="8" class="input-sm  form-control"> </td>
                         <td><div data-min-view="2" class="input-group col-md-9 col-xs-11" >
                            <input type="text" ng-value="0" name="fosterToPigId" readonly ng-model="pigletStatusEvent.fosterToPigId"  maxlength="30"  size="30" class="input-sm  form-control">
							<span>
                            <button type="button" class="btn btn-primary active" ng-click="getAllFosterPigs()" data-target="#searchFosters"><i class="fa fa-search"></i></button>
									</span>					
                            </div>
                            </td>   
                        </tr>
                        <tr>
                        <td><spring:message code='label.piginfo.pigletstatuseventform.death' text='Death' /> </td>
                         <td><input type="number" min="1" ng-value="0" name="deathPigNum" ng-model="pigletStatusEvent.deathPigNum"  maxlength="3"  size="3" class="form-control"> </td>
                         <td><input type="number" min="1" ng-value="0" name="deathPigWt" ng-model="pigletStatusEvent.deathPigWt"  maxlength="10"  step="0.01" size="10" class="form-control"> </td>
                         <td>&nbsp; </td>                         
                        </tr>
                     </tbody>
                    
                    </table>                    
                    </div>                    
                     <div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.eventDateTime'  text='Event Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="eventDateTime" name="eventDateTime" ng-model="pigletStatusEvent.eventDateTime" readonly
                          class="form-control" format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
 					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="eventDateTimerequired" ><spring:message code='label.piginfo.pigletstatuseventform.eventDateTime.requiredMessage' text='Event Date is required' /></label>
					</div>                   
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.weaninggroupid'  text='Weaning Group Id'/></label>
                      <input type="text" ng-model="pigletStatusEvent.weanGroupId"  name="weaningGroupId"  class="form-control" 
                       placeholder="<spring:message code='label.piginfo.pigletstatuseventform.weaninggroupid.placeholder'  
                       text='Enter Weaning group Id'/>"/>
                    </div>
					<div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.eventReason'  text='Event Reason'/></label>
                      <textarea name="eventReason" ng-model="pigletStatusEvent.eventReason" class="form-control" 
                       placeholder="<spring:message code='label.piginfo.pigletstatuseventform.eventreason.placeholder' text='Enter event reason'/>"></textarea>
                    </div>
                    
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.remarks'  text='Remarks'/><span style='color: red'>*</span></label>
                      <textarea name="remarks" ng-model="pigletStatusEvent.remarks" ng-required="true"  required-message="'<spring:message code='label.piginfo.pigletstatuseventform.remarks.requiredmessage' text='Remarks is required'/>'" 
                      class="form-control" placeholder="<spring:message code='label.piginfo.pigletstatuseventform.remarks.placeholder' text='Enter remarks'/>"></textarea>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.sowcondition'  text='Sow Condition'/></label>
                      <select class="form-control" name="sowCondition" ng-model="pigletStatusEvent.sowCondition" 
                      ng-required="true" required-message="'<spring:message code='label.piginfo.pigletstatuseventform.sowcondition.requiredmessage' text='Sow condition is required'/>'">
                           <option value="1">1 - <spring:message code='label.piginfo.entryeventform.sowcondition.least.message'  text='Least Healthy'/></option>
                          <option value="2">2 - <spring:message code='label.piginfo.entryeventform.sowcondition.normal.message'  text='Healthy'/></option>
                          <option value="3">3 - <spring:message code='label.piginfo.entryeventform.sowcondition.most.message'  text='Most Healthiest'/></option>
                        </select>
                    </div> 
                    
                    <button class="btn btn-primary" ng-click="addPigletStatusEvent()" type="submit" ng-disabled="inValidPigIdFromServer || pigletStatusEvent.farrowEventId == null"><spring:message code='label.piginfo.pigletstatuseventform.submit'  text='Submit'/></button>
                    <button class="btn btn-default" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.pigletstatuseventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="deletePigletEvent()" ng-show="editBtnclicked" ng-confirm-click="<spring:message code='label.piginfo.pigletstatuseventform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.pigletstatuseventform.delete'  text='Delete'/></button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">         
            </div>
          </div>
		  
		  <!-- - Breeding Service Id search Modal -->
		  <div id="searchFarrowEvents" class="modal colored-header custom-width">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.piginfo.pigletstatuseventform.farrowsearchresults.heading'  text='Farrow Events'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">�</button>
                      </div>
                      <div class="modal-body form" >
                      <table>
                       <thead>
                           <th><spring:message code='label.employeegroup.list.header.select'  text='Select'/> </th>                           
	                        <th><spring:message code='label.piginfo.pigletstatuseventform.farrowId'  text='Farrow Id'/> </th>
                       		<th><spring:message code='label.piginfo.pigletstatuseventform.farrowDate'  text='Farrow Date'/> </th>
                       </thead>
                       <tbody>
	                   <tr ng-repeat="farrowEventObj in farrowEventList" ng-if="farrowEventList != null && farrowEventList.length > 0">
	                    <td><input type="radio" name="farrowEventDtoId" id="farrowEventDtoId" ng-model="pigletStatusEvent.farrowEventDto" ng-value="farrowEventObj"></td>
	                    <td>{{farrowEventObj.farrowId}}</td>
	                    <td>{{farrowEventObj.farrowDateTime | date : 'yyyy-MM-dd'}}</td>
	                   </tr>
	                   <tr ng-if="farrowEventList == null || farrowEventList.length == 0">
	                     <td colspan="3">
	                       <spring:message code='label.piginfo.pigletstatuseventform.list.farrowevents.noresults'  text='No Farrow events found'/>
	                     </td>
	                   </tr>
	                   
	                 </tbody>
                      </table>
                      </div>
                      <div class="modal-footer">
                      <button type="button" class="btn btn-primary btn-flat md-close" ng-hide="farrowEventList == null || farrowEventList.length == 0" ng-click="selectFarrowEvent()"><spring:message code='label.employeegroup.list.header.select'  text='Select'/></button>
                      <button type="button" data-dismiss="modal" class="btn btn-default btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                      </div>
                      
                     </div>
            </div>
            
		  <!-- - foster search Modal -->
		  <div id="searchFosters" class="modal colored-header custom-width">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.piginfo.pigletstatuseventform.fostersearchresults.heading'  text='Foster Pigs'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">�</button>
                      </div>
                      <div class="modal-body form" >
                      <table>
                       <thead>
                           <th><spring:message code='label.employeegroup.list.header.select'  text='Select'/> </th>                           
	                        <th><spring:message code='label.piginfo.pigletstatuseventform.farrowId'  text='Farrow Id'/> </th>
                       		<th><spring:message code='label.piginfo.pigletstatuseventform.farrowDate'  text='Farrow Date'/> </th>
                       </thead>
                       <tbody>
	                   <tr ng-repeat="fosterObj in fosterPigList" ng-if="fosterPigList != null && fosterPigList.length > 0">
	                    <td><input type="radio" name="fosterId" id="fosterId" ng-model="pigletStatusEvent.fosterDto" ng-value="fosterObj"></td>
	                    <td>{{fosterObj.pigId}}</td>
	                    <td>{{fosterObj.currentFarrowEventDate | date : 'yyyy-MM-dd'}}</td>
	                   </tr>
	                   <tr ng-if="fosterPigList == null || fosterPigList.length == 0">
	                     <td colspan="3">
	                       <spring:message code='label.piginfo.pigletstatuseventform.list.farrowevents.noresults'  text='No Forsters found'/>
	                     </td>
	                   </tr>
	                   
	                 </tbody>
                      </table>
                      </div>
                      <div class="modal-footer">
                      <button type="button" class="btn btn-primary btn-flat md-close" ng-hide="fosterPigList == null || fosterPigList.length == 0" ng-click="selectFoster()"><spring:message code='label.employeegroup.list.header.select'  text='Select'/></button>
                      <button type="button" data-dismiss="modal" class="btn btn-default btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                      </div>
                      
                     </div>
            </div>            

		<div class="md-overlay"></div>
</div>