<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2>${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="PigletEventController" ng-init="loadPage(${CompanyId})" id="PigletEventControllerId">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div> 
 		  <div class="col-sm-6 col-md-6">
 		  <div class="block-flat">
		   <form name="pigletEventSearchForm" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pigletEventform.search.heading'  text='Search Individual Piglet Details'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.pigletEventform.search.errormessage' text='Please enter Farrow Id/ Piglet Tattoo Id and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.pigletEventform.search.data.errormessage' text='Piglet event information not found for the search criteria'/></p>
			
            <input type="text" name="search"  ng-enter="getPigletEventInformation()" ng-model="searchText" placeholder="<spring:message code='label.piginfo.pigleteventform.search.placeholder'  text='Search by Farrow Id / Tattoo ...'/>" class="form-control">

			 <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="getPigletEventInformation()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="pigId" class="icheck pigletevent" value="pigId" > <spring:message code='label.piginfo.pigleteventform.pigId'  text='Pig Id'/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="pigletTattooId" class="icheck pigletevent" value="pigletTattooId"> <spring:message code='label.piginfo.pigleteventform.tattooId'  text='Tattoo'/> 
                </label>			
              </div>
            </div>            
          </div>
		  </form>
		  <form name="pigletEventSearchResultForm"  ng-if="pigletEventList != null && pigletEventList.length != 0" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pigletEventSearchResultForm.searchresults.heading'  text='Piglet Events'/></h3>
            <div class="table-responsive">
             <table>
				<thead>
                     <tr>                       
                       <th><spring:message code='label.piginfo.pigleteventform.tattooId'  text='Farrow Id'/> </th>
                       <th><spring:message code='label.piginfo.pigleteventform.litterId'  text='Litter Id'/> </th>
                       <th><spring:message code='label.piginfo.pigleteventform.weightAtBirth'  text='Weight at Birth'/> </th>
                       <th><spring:message code='label.piginfo.pigleteventform.weightAtWeaning'  text='Weight at Weaning'/> </th>
                       <th><spring:message code='label.employeegroup.list.header.action'  text='Action'/> </th>
                     </tr>
                 </thead> 
                 <tbody>
                   <tr ng-repeat="pigletEventDto in pigletEventList">
                    <td>{{pigletEventDto.tattooId}}</td>
                    <td>{{pigletEventDto.litterId}}</td>
                    <td>{{pigletEventDto.weightAtBirth}}</td>
                    <td>{{pigletEventDto.weightAtWeaning}}</td>
                    <td><button type="button" class="btn btn-edit btn-xs"
						ng-click="getPigletEventDetails(pigletEventDto)">
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
                  <h3><spring:message code='label.piginfo.pigleteventform.pigletEvent'  text='Individual Piglet Event'/></h3>
                  
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pigleteventform.submit.success.message' text='Piglet Event information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pigleteventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                  <div class="alert alert-success alert-white rounded" ng-show="entryEventDeleteMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pigleteventform.delete.message'  text='Piglet event information deleted'/>
                  </div>    
                  
                </div>
                <div class="content">
                  <form novalidate angular-validator angular-validator-submit="addPigletEvent(pigleteventform)" name="pigleteventform">
                  <input type=hidden name="id" ng-model="pigletEvent.pigletId"/>  
				  <input type=hidden name="farrowEventId" ng-model="pigletEvent.farrowEventId"/>
				  	
				  	<div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="premiseId" id="premiseId" ng-model="pigletEvent.premiseId" required required-message="'<spring:message code='label.piginfo.farroweventform.premise.requiredmessage' text='Premise is required' />'">
                       	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="pigletEvent.premiseId == premise.id">{{premise.name}}</option>
                        </select>
                    </div>	
				  
					 <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.pigId'  text='Pig Id'/><span style='color: red'>*</span></label>
                      <label ng-show="pigletEvent.pigletId != null && pigletEvent.pigletId > 0">{{pigletEvent.pigId}}</label>                      
                     <input ng-show="!(pigletEvent.pigletId != null && pigletEvent.pigletId > 0)" type="text" ng-model="pigletEvent.pigId" id="pigId" name="pigId" required  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.pigId.placeholder'  text='Enter Pig Id'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.pigId.requiredmessage' text='Pig Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.pigleteventform.pigId.invalidmessage' text='Only Alpha numeric values are allowed' />'" ng-blur="searchFarrowEvent(pigletEvent.pigId.pigId, pigletEvent.companyId)"/>
					
                    </div>
                    <label ng-show="malePigIdentified" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.breedingeventform.pigInfoId.server.malePigIdentified' text='The selected Pig Id is a boar.  Please select a Sow' /></label>
					<label ng-show="inValidPigIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigleteventform.farrowId.server.invalidmessage' text='Invalid Farrow Id for the company' /></label>
					<label ng-show="pigletsAdded" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigleteventform.farrowId.server.pigletsaddedmessage' text='All piglets information added for the selected farrow event.' /></label>
					<label ng-show="requiredPigIdMessage" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigleteventform.farrowId.requiredmessage' text='Farrow Id is required' /></label>
					<div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.litterId'  text='Litter Id'/><span style='color: red'>*</span></label>
                      
                      <label ng-show="pigletEvent.pigletId != null && pigletEvent.pigletId > 0">{{pigletEvent.litterId}}</label> 
                      
                     <input type="text"  ng-show="pigletEvent.pigletId == null || pigletEvent.pigletId == 0" required ng-model="pigletEvent.litterId" id="litterId" name="litterId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.litterId.placeholder'  text='Enter Litter Id of the piglet'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.litterId.requiredmessage' text='Litter Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.numeric.errormessage' text='Only Aplha numeric values are allowed' />'" ng-blur="checkForLitterId()" />
                    </div>	
                    <label ng-show="invalidLitterId" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.breedingeventform.pigInfoId.server.invalidLitterId' text='Invalid litter Id for the selected pig' /></label>		
					 <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.tattooId'  text='Tattoo Id'/><span style='color: red'>*</span></label>
                     <input type="text" required ng-model="pigletEvent.tattooId" id="tattooId" name="tattooId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.tattooId.placeholder'  text='Enter Tattoo Id of the piglet'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.tattooId.requiredmessage' text='Tattoo Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.numeric.errormessage' text='Only Aplha numeric values are allowed' />'" ng-blur="checkForTattooId()" />
                    </div>					
					<div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.weightAtBirth'  text='Weight at Birth'/><span style='color: red'>*</span></label>
                     <input type="number" min="0" step="1" required  ng-model="pigletEvent.weightAtBirth" id="weightAtBirth" name="weightAtBirth"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weightAtBirth.placeholder'  text='Enter weigth at birth of the piglet'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.weightAtBirth.requiredmessage' text='Weight at birth information is required' />'"/>
                    </div>	
                    
                    <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.weightAtWeaning'  text='Weight at Weaning'/><span style='color: red'>*</span></label>
                     <input type="number" min="0" step="1" required ng-model="pigletEvent.weightAtWeaning" id="weightAtWeaning" name="weightAtWeaning"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weightAtWeaning.placeholder'  text='Enter weigth at weaning of the piglet'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.weightAtWeaning.requiredmessage' text='Weight at weaning information is required' />'"/>
                    </div>						
                    <button class="btn btn-success" type="submit" ng-disabled="inValidPigIdFromServer || pigletsAdded"><spring:message code='label.piginfo.farroweventform.submit'  text='Submit'/></button>
                    <button class="btn btn-warning" type="button" ng-click="pigleteventform.reset()"><spring:message code='label.piginfo.farroweventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="deletePigletEvent()" ng-show="pigletEvent.pigletId != null && pigletEvent.pigletId > 0" ng-confirm-click="<spring:message code='label.piginfo.pigleteventformform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.pigleteventform.delete'  text='Delete'/></button>
                  </form>
                </div> 
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		<div class="md-overlay"></div>
		
		
		<!-- - Breeding Service Id search Modal -->
		  <div id="searchFarrowEvents" class="modal colored-header custom-width">
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
	                    <td><input type="radio" name="farrowEventDtoId" id="farrowEventDtoId" ng-model="pigletEvent.farrowEventDto" ng-value="farrowEventObj"></td>	                   
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
            
		
</div>

  
		  

	
