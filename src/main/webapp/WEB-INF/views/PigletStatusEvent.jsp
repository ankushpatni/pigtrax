<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.pigletstatuseventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="PigletStatusEventController" ng-init="loadPage(${CompanyId})" id="PigletStatusEventControllerId">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div> 
 		  <div class="col-sm-6 col-md-6">
 		  
 		  <div class="block-flat">
		   <form name="farrowEventSearchForm" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pigletstatuseventform.search.heading'  text='Search Piglet Status'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage">
            			<spring:message code='label.piginfo.pigletstatuseventform.search.errormessage' 
            			text='Please enter Pig Id / Tattoo Id and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage">
            	<spring:message code='label.piginfo.pigletstatuseventform.search.data.errormessage' 
            	text='Piglet status event information not found for the search criteria'/></p>
			<div  class="form-group">
              <select  class="form-control"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise" ng-change="loadPigInfo()" >
			  <option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
              <option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
              </select>
              </div>
          
			 <div class="options">
			<div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1" class=" pigletstatus" value="pigId" ng-model="selectGroup"> <spring:message code='label.piginfo.pigletstatuseventform.search.pigid.option'  text='Pig Id'/>
                </label>              
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad2" class=" pigletstatus" value="tattooId" ng-model="selectGroup"> <spring:message code='label.piginfo.pigletstatuseventform.search.tattooid.option'  text='Tattoo Id'/> 
                </label>				
              </div>
            </div>    
			<div  class="form-group" ng-show="selectGroup==='pigId'">
				 
				  <button type="button" class="btn btn-primary active btn-group pull-right" ng-enter="searchPigletStatusEvents()" ng-click="searchPigletStatusEvents()"><i class="fa fa-search"></i></button>
				
					 <select  class="form-control"  name="search" ng-model="searchText" style="width:85%">
								<option value="" hidden><spring:message code='label.piginfo.pigId.placeholder' text='Select PigId' /></option>
								<option ng-repeat="pigInfo in pigInfoList" value="{{pigInfo.pigId}}" ng-value="pigInfo.pigId" ng-selected="searchText == pigInfo.pigId">{{pigInfo.pigId}}</option>
								</select>
			</div>
			<div  class="form-group" ng-show="selectGroup==='tattooId'">
				 
				  <button type="button" class="btn btn-primary active btn-group pull-right" ng-enter="searchPigletStatusEvents()" ng-click="searchPigletStatusEvents()"><i class="fa fa-search"></i></button>
				
					 <select  class="form-control"  name="search" ng-model="searchText" style="width:85%">
								<option value="" hidden><spring:message code='label.piginfo.pigId.placeholder' text='Select PigId' /></option>
								<option ng-repeat="pigInfo in pigInfoList" value="{{pigInfo.tattoo}}" ng-value="pigInfo.tattoo" ng-selected="searchText == pigInfo.tattoo">{{pigInfo.tattoo}}</option>
								</select>
			</div>
          </div>
		  </form>
		  <form name="pigletStatusEventSearchResultForm"  ng-if="pigletStatusEventList != null && pigletStatusEventList.length != 0" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pigletstatuseventform.searchresults.heading'  text='Piglet Status Events'/></h3>
            <div class="table-responsive">
             <table>
				<thead>
                     <tr>                       
                       <th><spring:message code='label.piginfo.farroweventform.farrowDateTime'  text='Farrow Date'/> </th>
                       <th><spring:message code='label.piginfo.pigletstatuseventform.pigletStatusEventType'  text='Event Type'/> </th>
                       <th><spring:message code='label.piginfo.pigletstatuseventform.eventDateTime'  text='Event Date'/> </th>                             
                       <th><spring:message code='label.employeegroup.list.header.action'  text='Action'/> </th>
                     </tr>
                 </thead>   
                 <tbody>
                   <tr ng-repeat="pigletStatusEventDto in pigletStatusEventList"> 
					<td >{{DateUtils.getFormatedDate(pigletStatusEventDto.farrowEventDto.farrowDateTime)}}</td>
					<td>
						<p  ng-show="pigletStatusEventDto.id != null && pigletStatusEventDto.pigletStatusEventTypeId == 2"> <spring:message code='label.piginfo.pigletstatuseventform.foster'  text='Transfer'/> </p>
						<p  ng-show="pigletStatusEventDto.id != null && pigletStatusEventDto.pigletStatusEventTypeId == 3"> <spring:message code='label.piginfo.pigletstatuseventform.wean'  text='Wean'/> </p>
						<p  ng-show="pigletStatusEventDto.id != null && pigletStatusEventDto.pigletStatusEventTypeId == 4"> <spring:message code='label.piginfo.pigletstatuseventform.death'  text='Piglet Mortality'/> </p>
					</td>
					<td>
						<p  ng-show="pigletStatusEventDto.id != null && pigletStatusEventDto.pigletStatusEventTypeId == 2">{{DateUtils.getFormatedDate(pigletStatusEventDto.fosterEventDateTime)}}</p>
						<p  ng-show="pigletStatusEventDto.id != null && pigletStatusEventDto.pigletStatusEventTypeId == 3">{{DateUtils.getFormatedDate(pigletStatusEventDto.weanEventDateTime)}}</p>
						<p  ng-show="pigletStatusEventDto.id != null && pigletStatusEventDto.pigletStatusEventTypeId == 4">{{DateUtils.getFormatedDate(pigletStatusEventDto.deathEventDateTime)}}</p>
					</td>
                    <td><button type="button" class="btn btn-edit btn-xs" ng-click="setPigletStatusDetails(pigletStatusEventDto)">
						<span class="glyphicon glyphicon-pencil"></span>
						<spring:message code="label.employeegroup.list.edit" text="Edit" />												
						</button>
					</td>
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
                  <h3><spring:message code='label.piginfo.pigletstatuseventform.pigletStatusEvent'  text='Piglet Status'/></h3>
                  
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
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
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pigletstatuseventform.pigletstatusEventValidation_ErrCode_1' text='Wean should happen within 0-60 days of farrow event, please change the date and submit again to proceed' />
                  </div>  
                  <div class="alert alert-warning alert-white rounded" ng-show="pigletstatusEventValidation_ErrCode_2">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pigletstatuseventform.pigletstatusEventValidation_ErrCode_2' text='Transfer should happen within 0-50 days of farrow event, please change the date and submit again to proceed' />
                  </div>                         
                  <div class="alert alert-warning alert-white rounded" ng-show="pigletstatusEventValidation_ErrCode_3">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pigletstatuseventform.pigletstatusEventValidation_ErrCode_3' text='The total count of Piglets is not the same as alive Piglets at the time of Farrow. Please account for the entire litter' />
                  </div>  
                  <div class="alert alert-warning alert-white rounded" ng-show="matchingFarrowRecordNotFound">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pigletstatuseventform.matchingFarrowRecordNotFound' text='Matching farrow event not found for the given date' />
                  </div>                             
                </div>
                <div class="content">
                  <form name="pigletstatuseventform" novalidate angular-validator my-reset> 
                  <input type=hidden name="id" ng-model="pigletStatusEvent.id"/>
                   <input type=hidden name="fosterTo" ng-model="pigletStatusEvent.fosterTo"/>
				  <input type=hidden name="pigInfoId" ng-model="pigletStatusEvent.pigInfoId"/>				  
				  <input type=hidden name="fosterFarrowId" ng-model="pigletStatusEvent.fosterFarrowEventId"/>
				  <input type=hidden name="eventReason" ng-model="pigletStatusEvent.eventReason" value=""/>
				  
				  <label style='color:blue' class='control-label'>&nbsp;<spring:message code='label.piginfo.pigletstatuseventform.instruction' text='Please enter the Pig Id and select the farrow event to proceed' /></label>
				  
				  	<div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
                       <select class="form-control"  ng-change="searchFarrowEvent()"  name="premiseId" id="premiseId" ng-model="pigletStatusEvent.premiseId" required required-message="'<spring:message code='label.piginfo.farroweventform.premise.requiredmessage' text='Premise is required' />'">
                       	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="pigletStatusEvent.premiseId == premise.id">{{premise.name}}</option>
                        </select>
                    </div>	
				  
					 <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigletstatuseventform.pigId'  text='Pig Id'/><span style='color: red'>*</span></label>
                     
					  <div data-min-view="2" class="input-group col-md-7 col-xs-9"  >
                     	<input type="text" ng-model="pigletStatusEvent.pigId" id="pigId" name="pigId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigletstatuseventform.pigId.placeholder'  text='Enter Pig Id'/>" 
                      required-message="'<spring:message code='label.piginfo.pigletstatuseventform.pigId.requiredmessage' text='Pig Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.pigletstatuseventform.pigId.invalidmessage' text='Only Numeric values are allowed' />'" ng-blur="searchFarrowEvent()" />						
						</div>	
						
                    </div>
                    <a href="#" ng-click="goToFarrowEvent()" ng-show="pigletStatusEvent.farrowEventDto != null && pigletStatusEvent.farrowEventDto.id > 0"><spring:message code='label.piginfo.pigletstatuseventform.gotofarroweventtext'  text='Go to Farrow Event'/></a>
                    <div>
					<label ng-show="inValidPigIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigletstatuseventform.pigInfoId.server.invalidmessage' text='Invalid Pig Id for the company' /></label>
					<label ng-show="requiredPigIdMessage" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigletstatuseventform.pigInfoId.requiredmessage' text='Pig Info Id is required' /></label>
					<label ng-show="pigletStatusEventAlreadyAdded" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigletstatuseventform.pigInfoId.pigletStatusEventCreated' text='Piglet Status already captured for the selected Pig Id,  farrow event and the event type. Use search feature to modify it.' /></label>
					</div>
				    <div class=" block-flat bars-widget" ng-if="pigletStatusEvent.farrowEventId != null && pigletStatusEvent.farrowEventId>0 && !pigletStatusEventAlreadyAdded">
		                <div class="spk4 pull-right spk-widget"></div>  
		                <h4>;<spring:message code='label.leftmenu.managepigevents.farrowevent.link' text='Farrow' /></h4><div></div>
		                <div><spring:message code='label.piginfo.farroweventform.farrowDateTime'  text='Farrow Date'/> : {{DateUtils.getFormatedDate(pigletStatusEvent.farrowEventDto.farrowDateTime)}}</div>
		                <div><spring:message code='label.piginfo.farroweventform.liveborns'  text='Live Borns'/> : {{pigletStatusEvent.farrowEventDto.liveBorns}}</div>
		            </div>					 
					 <div class=" block-flat bars-widget" ng-if="fosterInRecords != null && fosterInRecords.length != 0">
		                <div class="spk4 pull-right spk-widget"></div>
		                <h4><spring:message code='label.piginfo.pigletstatuseventform.fosterindetails' text='Transferred Details' /></h4><div></div>
		                 <div class="table-responsive">
		                <table>
		                  <thead>
		                  <tr>
		                   <th><spring:message code='label.piginfo.pigletstatuseventform.fosterFrom' text='Transfer From' /></th>
		                   <th><spring:message code='label.piginfo.pigletstatuseventform.numberOfPigs' text='No of Piglets' /></th>
		                   <th><spring:message code='label.piginfo.pigletstatuseventform.weightInKg' text='Weight' /></th>
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
		            </div>
					 
					 <div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.pigletStatusEventType'  text='Event Type'/><span style='color: red'>*</span></label>
                     <select required class="form-control" ng-model="pigletStatusEvent.pigletStatusEventTypeId" name="eventType" id="eventType" ng-change="changeEvent()"  ng-show="pigletStatusEvent.id == null"   
                     required-message="'<spring:message code='label.piginfo.pigletstatuseventform.eventType.required' text='Event Type is required' />'">
                          <option value="4" ng-selected="pigletStatusEvent.penId == 4"><spring:message code='label.piginfo.pigletstatuseventform.death'  text='Piglet Mortality'/></option>
                          <option value="2" ng-selected="pigletStatusEvent.penId == 2"><spring:message code='label.piginfo.pigletstatuseventform.foster'  text='Transfer'/></option>
                          <option value="3" ng-selected="pigletStatusEvent.penId == 3"><spring:message code='label.piginfo.pigletstatuseventform.wean'  text='Wean'/></option>
                        </select>
						<label  ng-show="pigletStatusEvent.id != null && pigletStatusEvent.pigletStatusEventTypeId == 2"> : <spring:message code='label.piginfo.pigletstatuseventform.foster'  text='Transfer'/> </label>
						<label ng-show="pigletStatusEvent.id != null && pigletStatusEvent.pigletStatusEventTypeId == 3"> : <spring:message code='label.piginfo.pigletstatuseventform.wean'  text='Wean'/> </label>
						<label ng-show="pigletStatusEvent.id != null && pigletStatusEvent.pigletStatusEventTypeId == 4"> : <spring:message code='label.piginfo.pigletstatuseventform.death'  text='Piglet Mortality'/> </label>
                    </div>
					 
					 <div class="form-group cl-mcont"  ng-show="eventSection != null && 0<eventSection.length">   
					
 					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="fosterToPigIdrequired" ><spring:message code='label.piginfo.pigletstatuseventform.fosterToPigId.requiredMessage' text='Pig Id for transfer is required' /></label>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="eventDateTimerequired" ><spring:message code='label.piginfo.pigletstatuseventform.eventDateTime.requiredMessage' text='Event Date is required' /></label>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="invalidPigletNumbers" ><spring:message code='label.piginfo.pigletstatuseventform.invalidPigletNumbers' text='Piglet count should be in whole numeric values' /></label>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="pigletNumbersRequired" ><spring:message code='label.piginfo.pigletstatuseventform.pigletNumbersRequired' text='Please enter wean/transfer/piglet mortality information' /></label>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="invalidGroupEventId" ><spring:message code='label.piginfo.pigletstatuseventform.invalidGroupEventId' text='Invalid Group Id' /></label>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="invalidGroupEventDate" ><spring:message code='label.piginfo.pigletstatuseventform.invalidGroupEventDate' text='Group start date is later than wean date' /></label>
					</div>	
										
					
					 <div class="form-group" ng-show="eventSection=='death'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.numberOfPigs' text='No of Pigs' /><span style='color: red'>*</span></label>
                      <input type="number" min="0" step="1" ng-value="0" name="deathPigNum" ng-model="pigletStatusEvent.deathPigNum"  maxlength="3"  size="3" class="form-control">
                    </div>
                     <div class="form-group" ng-show="eventSection=='death'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.weightInKg' text='Weight' /></label>
                      <input type="number" min="0" ng-value="0" name="deathPigWt" ng-model="pigletStatusEvent.deathPigWt"  maxlength="10"  step="0.01" size="10" class="form-control"> 
                    </div>
                     <div class="form-group" ng-show="eventSection=='death'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.eventDateTime'  text='Event Date'/><span style='color: red'>*</span></label><i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      <input type="text" class="form-control" ng-model="pigletStatusEvent.deathEventDateStr" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletStatusEvent.deathEventDateStr, 'deathEventDate')"/>
                    </div>
                     <div class="form-group" ng-show="eventSection=='death'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.info' text='Info' /></label>
                      <select class="form-control"  name="mortalityReason" ng-model="pigletStatusEvent.mortalityReasonTypeId">                         
                         <option value=""><spring:message code='label.piginfo.pigletstatuseventform.mortalityreason' text='Mortality Reason' /></option>
                          <option ng-repeat="key in mortalityReasonTypeKeys" ng-value="key" ng-selected="pigletStatusEvent.mortalityReasonTypeId==key">{{mortalityReasonTypes[key]}}</option>
                        </select>   
                    </div>
                     
                    
                    
                     <div class="form-group" ng-show="eventSection=='transfer'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.numberOfPigs' text='No of Pigs' /><span style='color: red'>*</span></label>
                      <input type="number" min="0" step="1" ng-value="0" name="fosterPigNum" ng-model="pigletStatusEvent.fosterPigNum"  maxlength="3"  size="3" class="input-sm  form-control"> 
                    </div>
                     <div class="form-group" ng-show="eventSection=='transfer'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.weightInKg' text='Weight' /></label>
                      <input type="number" min="0" ng-value="0" name="fosterPigWt" ng-model="pigletStatusEvent.fosterPigWt"  maxlength="8"   step="0.01" size="8" class="input-sm  form-control">  
                    </div>
                     <div class="form-group" ng-show="eventSection=='transfer'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.eventDateTime'  text='Event Date'/><span style='color: red'>*</span></label><i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>                      
                      <input type="text" class="form-control" ng-model="pigletStatusEvent.fosterEventDateStr" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletStatusEvent.fosterEventDateStr, 'fosterEventDate')"/>
                    </div>
                     <div class="form-group" ng-show="eventSection=='transfer'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.info' text='Info' /></label>
                      <div data-min-view="2" class="input-group col-md-9 col-xs-11" >
                      	<input type="text" ng-value="0" name="fosterToPigId" readonly ng-model="pigletStatusEvent.fosterToPigId"  maxlength="30"  size="30" class="input-sm  form-control" placeholder="'<spring:message code='label.piginfo.pigletstatuseventform.fosterToPigId' text='Trasferred To' /> '">
						<span>
	                        <button type="button" class="btn btn-primary active" ng-click="getAllFosterPigs()" data-target="#searchFosters"><i class="fa fa-search"></i></button>
						</span>					
                       </div>  
                    </div>
					
					
                     <div class="form-group" ng-show="eventSection=='wean'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.eventDateTime'  text='Event Date'/><span style='color: red'>*</span></label><i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>                      
                      <input type="text" class="form-control" ng-model="pigletStatusEvent.weanEventDateStr" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletStatusEvent.weanEventDateStr, 'weanEventDate')"/>
                    </div>
					<div class="form-group" ng-show="eventSection=='wean'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.numberOfPigs' text='No of Pigs' /><span style='color: red'>*</span></label>
                      <input type="number" min="0" step="1" ng-value="0" name="weanPigNum" ng-model="pigletStatusEvent.weanPigNum"  maxlength="3"  size="3" class="input-sm form-control" ng-blur="checkOnEraseOfWean()">
                    </div>
                     <div class="form-group" ng-show="eventSection=='wean'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.weightInKg' text='Weight' /></label>
                      <input type="number" min="0" ng-value="0" name="weanPigWt" ng-model="pigletStatusEvent.weanPigWt"  maxlength="8"  size="8"   step="0.01" class="input-sm form-control"> 
                    </div>
                     <div class="form-group" ng-show="eventSection=='wean'">
                      <label><spring:message code='' text='No of Light Weight Pigs' /><span style='color: red'>*</span></label>
                      <input type="number" min="0" step="1" ng-value="0" name="numberOfPigsLW" ng-model="pigletStatusEvent.numberOfPigsLW"  maxlength="3"  size="3" class="input-sm form-control" >
                    </div>
                     <div class="form-group" ng-show="eventSection=='wean'">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.info' text='Info' /></label>
                      <input type="text" name="weanGroupEventId" ng-model="pigletStatusEvent.groupId"  maxlength="30"  size="8"  class="input-sm form-control" ng-blur="checkGroupEventId()" 
                         placeholder="'<spring:message code='label.piginfo.pigletstatuseventform.groupeventId' text='Group Id' /> '">
                    </div>
					
					
                    </div>		                   
                    <!-- <div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.weaninggroupid'  text='Weaning Group Id'/></label>
                      <input type="text" ng-model="pigletStatusEvent.weanGroupId"  name="weaningGroupId"  class="form-control" 
                       placeholder="<spring:message code='label.piginfo.pigletstatuseventform.weaninggroupid.placeholder'  
                       text='Enter Weaning group Id'/>"/>
                    </div> -->
					
					<div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.pen'  text='Pen'/></label>
                     <select class="form-control" ng-model="pigletStatusEvent.penId">
                          <option ng-repeat="pen in penInfo" value="{{pen.id}}" ng-selected="pigletStatusEvent.penId == pen.id">{{pen.penId}}</option>
                        </select>
                    </div>
                    
                   
                    
					 <div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.sowcondition'  text='Sow Condition'/></label>
                      <i>[1:  <spring:message code='label.piginfo.entryeventform.sowcondition.least.message'  text='Least Healthy'/> - 5:<spring:message code='label.piginfo.entryeventform.sowcondition.most.message'  text='Most Healthiest'/>]</i>
                      <select class="form-control" name="sowCondition" ng-model="pigletStatusEvent.sowCondition" 
                       required-message="'<spring:message code='label.piginfo.pigletstatuseventform.sowcondition.requiredmessage' text='Sow condition is required'/>'">
                           <option value="1">1</option>
                          	<option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div> 
					
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.pigletstatuseventform.remarks'  text='Remarks'/></label>
                      <textarea name="remarks" ng-model="pigletStatusEvent.remarks" class="form-control" placeholder="<spring:message code='label.piginfo.pigletstatuseventform.remarks.placeholder' text='Enter remarks'/>"></textarea>
                    </div>
                   
                    
                    <button class="btn btn-success" ng-click="addPigletStatusEvent()" type="submit" ng-disabled="inValidPigIdFromServer"><spring:message code='label.piginfo.pigletstatuseventform.submit'  text='Submit'/></button>
                    <button class="btn btn-warning" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.pigletstatuseventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="deletePigletStatusEvent()" ng-show="editBtnclicked" ng-confirm-click="<spring:message code='label.piginfo.pigletstatuseventform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.pigletstatuseventform.delete'  text='Delete'/></button>
                  </form>
                </div>
              </div>
            </div> 
             <div class="col-sm-1 col-md-1">      
            </div>
          </div>
		  
		  <!-- - Breeding Service Id search Modal -->
		  <div id="searchFarrowEvents" class="modal colored-header warning custom-width">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.piginfo.pigletstatuseventform.farrowsearchresults.heading'  text='Farrow Events'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">�</button>
                      </div>
                      <div class="modal-body form" >
                      <div class="table-responsive">
                      <table>
                       <thead>
                           <th><spring:message code='label.employeegroup.list.header.select'  text='Select'/> </th>
                           <th><spring:message code='label.piginfo.pigletstatuseventform.farrowDate'  text='Farrow Date'/> </th>                           
	                        <th><spring:message code='label.piginfo.farroweventform.liveborns'  text='Live Borns'/> </th>
                       		
                       </thead>
                       <tbody>
	                   <tr ng-repeat="farrowEventObj in farrowEventList" ng-if="farrowEventList != null && farrowEventList.length > 0">
	                    <td><input type="radio" name="farrowEventDtoId" id="farrowEventDtoId" ng-model="pigletStatusEvent.farrowEventDto" ng-value="farrowEventObj"></td>	                   
	                    <td>{{DateUtils.getFormatedDate(farrowEventObj.farrowDateTime)}}</td>
	                     <td>{{farrowEventObj.liveBorns}}</td>
	                   </tr>
	                   <tr ng-if="farrowEventList == null || farrowEventList.length == 0">
	                     <td colspan="3">
	                       <spring:message code='label.piginfo.pigletstatuseventform.list.farrowevents.noresults'  text='No Farrow events found'/>
	                     </td>
	                   </tr>
	                   
	                 </tbody>
                      </table>
                      </div>
                      </div>
                      <div class="modal-footer">
                      <button type="button" class="btn btn-success btn-flat md-close" ng-hide="farrowEventList == null || farrowEventList.length == 0" ng-click="selectFarrowEvent()"><spring:message code='label.employeegroup.list.header.select'  text='Select'/></button>
                      <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                      </div>
                      
                     </div>
            </div>
            
		  <!-- - foster search Modal -->
		  <div id="searchFosters" class="modal colored-header warning custom-width">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.piginfo.pigletstatuseventform.fostersearchresults.heading'  text='Transfer Pigs'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">�</button>
                      </div>
                      <div class="modal-body form" >
                      <div class="table-responsive">
                      <table>
                       <thead>
                           <th><spring:message code='label.employeegroup.list.header.select'  text='Select'/> </th>                     
	                        <th><spring:message code='label.piginfo.pigletstatuseventform.pigId'  text='Pig Id'/> </th>
                       		<th><spring:message code='label.piginfo.pigletstatuseventform.farrowDate'  text='Farrow Date'/> </th>
                       </thead>
                       <tbody>
	                   <tr ng-repeat="fosterObj in fosterPigList" ng-if="fosterPigList != null && fosterPigList.length > 0">
	                    <td><input type="radio" name="fosterId" id="fosterId" ng-model="pigletStatusEvent.fosterDto" ng-value="fosterObj"></td>
	                    <td>{{fosterObj.pigId}}</td>
	                    <td>{{DateUtils.getFormatedDate(fosterObj.birthDate)}}</td>
<!-- 	                    <td>{{DateUtils.getFormatedDate(fosterObj.currentFarrowEventDate)}}</td> -->
	                   </tr>
	                   <tr ng-if="fosterPigList == null || fosterPigList.length == 0">
	                     <td colspan="3">
	                       <spring:message code='label.piginfo.pigletstatuseventform.list.farrowevents.noresults'  text='No farrow events found'/>
	                     </td>
	                   </tr>
	                   
	                 </tbody>
                      </table>
                      </div>
                      </div>
                      <div class="modal-footer">
                      <button type="button" class="btn btn-success btn-flat md-close" ng-hide="fosterPigList == null || fosterPigList.length == 0" ng-click="selectFoster()"><spring:message code='label.employeegroup.list.header.select'  text='Select'/></button>
                      <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                      </div>
                      
                     </div>
            </div>            

		<div class="md-overlay"></div>
</div>
<form method="post" action="farrowEvent" id="prevFarrowEventForm">
  <input type="hidden" name="selectedCompany" id="selectedCompany">
  <input type="hidden" name="selectedFarrowEventId" id="selectedFarrowEventId">
</form>
