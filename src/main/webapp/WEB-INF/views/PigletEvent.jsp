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
			<div  class="form-group">
              <select  class="form-control"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  >
			  <option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
              <option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
              </select>
             </div>
           <div  class="form-group">  	
            <input type="text" name="search"  ng-enter="getPigletEventInformation()" ng-model="searchText" placeholder="<spring:message code='label.piginfo.pigletEventform.search.placeholder'  text='Search by Farrow Id / Tattoo ...'/>" class="form-control">
			</div>
			 <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="getPigletEventInformation()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="pigId" class="icheck pigletevent" value="pigId" > <spring:message code='label.piginfo.pigleteventform.pigInfoId'  text='Pig Id'/>
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
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pigleteventform.submit.success.message' text='Piglet Event information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pigleteventform.submit.error.message' text='An exception occurred. Please check the values entered'/>
                  </div>
                  <div class="alert alert-success alert-white rounded" ng-show="entryEventDeleteMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pigleteventform.delete.message'  text='Piglet event information deleted'/>
                  </div>    
                  <div class="alert alert-danger alert-white rounded" ng-show="entryEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pigleteventform.error.duplicate' text='A piglet entry already exists with the same tattoo'/>
                  </div>
                  
                </div>
                <div class="content">
                  <form novalidate angular-validator angular-validator-submit="addPigletEvent(pigleteventform)" name="pigleteventform">
                  <input type=hidden name="id" ng-model="pigletEvent.pigletId"/>
				  	
				  	<div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="premiseId" id="premiseId" ng-model="pigletEvent.premiseId" required required-message="'<spring:message code='label.piginfo.farroweventform.premise.requiredmessage' text='Premise is required' />'">
                       	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="pigletEvent.premiseId == premise.id">{{premise.name}}</option>
                        </select>
                    </div>	
				  
					 <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.pigId'  text='Piglet Id'/><span style='color: red'>*</span></label>
                      <label ng-show="pigletEvent.pigletId != null && pigletEvent.pigletId > 0">{{pigletEvent.pigId}}</label>                      
                     <input ng-show="!(pigletEvent.pigletId != null && pigletEvent.pigletId > 0)" type="text" ng-model="pigletEvent.pigId" id="pigId" name="pigId" required  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.pigletId.placeholder'  text='Enter Piglet Id'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.pigId.requiredmessage' text='Piglet Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.pigleteventform.pigId.invalidmessage' text='Only Alpha numeric values are allowed' />'" />
					
                    </div>
                    <div>
                    <label ng-show="malePigIdentified" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.breedingeventform.pigInfoId.server.malePigIdentified' text='The selected Pig Id is a boar.  Please select a Sow' /></label>
					<label ng-show="inValidPigIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.farrowId.server.invalidmessage' text='Invalid Pig Id for the company' /></label>
					<label ng-show="pigletsAdded" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigleteventform.farrowId.server.pigletsaddedmessage' text='All piglets information added for the selected farrow event.' /></label>
					<label ng-show="requiredPigIdMessage" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.pigleteventform.farrowId.requiredmessage' text='Farrow Id is required' /></label>
					</div>
					<div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.litterId'  text='Litter Id'/></label>
                     <input type="number" min="0" ng-value="0" maxlength="10"  step="1" size="10"   ng-model="pigletEvent.litterId" id="litterId" name="litterId"  class="form-control"
                       placeholder="<spring:message code='label.piginfo.pigleteventform.litterId.placeholder'  text='Enter Litter Id of the piglet'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.litterId.requiredmessage' text='Litter Id is required' />'"						
						invalid-message="'<spring:message code='label.numeric.errormessage' text='Only Aplha numeric values are allowed' />'"  />
                    </div>	
                    <label ng-show="invalidLitterId" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.breedingeventform.pigInfoId.server.invalidLitterId' text='Invalid litter Id for the selected pig' /></label>		
					 <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.tattooId'  text='Tattoo Id'/></label>
                     <input type="text" ng-model="pigletEvent.tattooId" id="tattooId" name="tattooId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.tattooId.placeholder'  text='Enter Tattoo Id of the piglet'/>"
                      	ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.numeric.errormessage' text='Only Aplha numeric values are allowed' />'" ng-blur="checkForTattooId()" />
                    </div>					
<!-- 					<div class="form-group">  -->
<%--                       <label><spring:message code='label.piginfo.pigleteventform.weightAtBirth'  text='Weight at Birth'/></label> --%>
<%--                      <input type="number" min="0" step="0.01"  ng-model="pigletEvent.weightAtBirth" id="weightAtBirth" name="weightAtBirth"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weightAtBirth.placeholder'  text='Enter weigth at birth of the piglet'/>"  --%>
<%--                       required-message="'<spring:message code='label.piginfo.pigleteventform.weightAtBirth.requiredmessage' text='Weight at birth information is required' />'"/> --%>
<!--                     </div>	 -->
                    
<!--                     <div class="form-group">  -->
<%--                       <label><spring:message code='label.piginfo.pigleteventform.weightAtWeaning'  text='Weight at Weaning'/></label> --%>
<%--                      <input type="number" min="0" step="0.01" ng-model="pigletEvent.weightAtWeaning" id="weightAtWeaning" name="weightAtWeaning"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weightAtWeaning.placeholder'  text='Enter weigth at weaning of the piglet'/>"  --%>
<%--                       required-message="'<spring:message code='label.piginfo.pigleteventform.weightAtWeaning.requiredmessage' text='Weight at weaning information is required' />'"/> --%>
<!--                     </div>	 -->
                    
                   <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.groupId'  text='Group Id'/></label>
                     <input type="text" ng-model="pigletEvent.groupId" id="groupId" name="groupId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.groupId.placeholder'  text='Enter Group Id of the piglet'/>"
                      	ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.numeric.errormessage' text='Only Aplha numeric values are allowed' />'"  />
                    </div>
                    
                    <div class="form-group"> 
					 <table class="no-border">
					 	<tbody class="no-border-x no-border-y">
					 	<tr>
					 		<td>
                      			<label><spring:message code='label.piginfo.pigleteventform.weight1'  text='Weight (1st month)'/></label>
					  		</td>
					  		<td>
                     			<input type="number" min="0" step="0.01"  ng-model="pigletEvent.weight1" id="weight1" name="weight1"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weight1.placeholder'  text='Enter weight at 1st month'/>"/>
					 		</td>
					 		<td>
                     			<label><spring:message code='label.piginfo.pigleteventform.date1'  text='Date'/></label>
					 		</td>
					 		<td>
                     			<input type="text" class="form-control" ng-model="pigletEvent.dateStr1" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletEvent.dateStr1, 'date1')"/>
					 		</td>
					 	</tr>
					 </tbody>
					 </table>
                    </div>	
                    <div class="form-group"> 
					 <table class="no-border">
					 	<tbody class="no-border-x no-border-y">
					 	<tr>
                    		<td> 
                      			<label><spring:message code='label.piginfo.pigleteventform.weight2'  text='Weight (2nd month)'/></label>
                      		</td>
                      		<td>
                     			<input type="number" min="0" step="0.01"  ng-model="pigletEvent.weight2" id="weight2" name="weight2"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weight2.placeholder'  text='Enter weight at 2nd month'/>"/>
                    		</td>
					 		<td>
                     			<label><spring:message code='label.piginfo.pigleteventform.date1'  text='Date'/></label>
					 		</td>
					 		<td>
                     			<input type="text" class="form-control" ng-model="pigletEvent.dateStr2" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletEvent.dateStr2, 'date2')"/>
					 		</td>
					 	</tr>
					 </tbody>
					 </table>
                    </div>	
                    <div class="form-group"> 
                     <table class="no-border">
					 	<tbody class="no-border-x no-border-y">
					 	<tr>
                    		<td> 
                      			<label><spring:message code='label.piginfo.pigleteventform.weight3'  text='Weight (3rd month)'/></label>
                      		</td>
                      		<td>
                     			<input type="number" min="0" step="0.01"  ng-model="pigletEvent.weight3" id="weight3" name="weight3"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weight3.placeholder'  text='Enter weight at 3rd month'/>"/>
							</td>
					 		<td>
                     			<label><spring:message code='label.piginfo.pigleteventform.date1'  text='Date'/></label>
					 		</td>
					 		<td>
                     			<input type="text" class="form-control" ng-model="pigletEvent.dateStr3" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletEvent.dateStr3, 'date3')"/>
					 		</td>
					 	</tr>
					 </tbody>
					 </table>
                    </div>	
                    <div class="form-group">
                    <table class="no-border">
					 	<tbody class="no-border-x no-border-y">
					 	<tr>
                    		<td> 
                      			<label><spring:message code='label.piginfo.pigleteventform.weight4'  text='Weight (4th month)'/></label>
                      		</td>
                      		<td>
                     			<input type="number" min="0" step="0.01"  ng-model="pigletEvent.weight4" id="weight4" name="weight4"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weight4.placeholder'  text='Enter weight at 4th month'/>"/>
                     		</td>
					 		<td>
                     			<label><spring:message code='label.piginfo.pigleteventform.date1'  text='Date'/></label>
					 		</td>
					 		<td>
                     			<input type="text" class="form-control" ng-model="pigletEvent.dateStr4" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletEvent.dateStr4, 'date4')"/>
					 		</td>
					 	</tr>
					 </tbody>
					 </table>
                    </div>	
                    <div class="form-group">
                    <table class="no-border">
					  <tbody class="no-border-x no-border-y">
					 	<tr>
                    		<td> 
                      			<label><spring:message code='label.piginfo.pigleteventform.weight5'  text='Weight (5th month)'/></label>
                      		</td>
                      		<td>
                     			<input type="number" min="0" step="0.01"  ng-model="pigletEvent.weight5" id="weight5" name="weight5"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weight5.placeholder'  text='Enter weight at 5th month'/>"/>
                    		</td>
					 		<td>
                     			<label><spring:message code='label.piginfo.pigleteventform.date1'  text='Date'/></label>
					 		</td>
					 		<td>
                     			<input type="text" class="form-control" ng-model="pigletEvent.dateStr5" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletEvent.dateStr5, 'date5')"/>
					 		</td>
					 	</tr>
					 </tbody>
					 </table>
                    </div>	
                    <div class="form-group"> 
                     <table class="no-border">
					  <tbody class="no-border-x no-border-y">
					 	<tr>
                    		<td>
                       			 <label><spring:message code='label.piginfo.pigleteventform.weight6'  text='Weight (6th month)'/></label>
                       		</td>
                       		<td>
                     			<input type="number" min="0" step="0.01"  ng-model="pigletEvent.weight6" id="weight6" name="weight6"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weight6.placeholder'  text='Enter weight at 6th month'/>"/>
                     		</td>
					 		<td>
                     			<label><spring:message code='label.piginfo.pigleteventform.date1'  text='Date'/></label>
					 		</td>
					 		<td>
                     			<input type="text" class="form-control" ng-model="pigletEvent.dateStr6" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletEvent.dateStr6, 'date6')"/>
					 		</td>
					 	</tr>
					 </tbody>
					 </table>
                    </div>	
                    <div class="form-group"> 
                     <table class="no-border">
					  <tbody class="no-border-x no-border-y">
					 	<tr>
                    		<td>
                       			 <label><spring:message code='label.piginfo.pigleteventform.weight7'  text='Weight (7th month)'/></label>
                       		</td>
                       		<td>
                     			<input type="number" min="0" step="0.01"  ng-model="pigletEvent.weight7" id="weight7" name="weight7"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weight7.placeholder'  text='Enter weight at 7th month'/>"/>
                     		</td>
					 		<td>
                     			<label><spring:message code='label.piginfo.pigleteventform.date1'  text='Date'/></label>
					 		</td>
					 		<td>
                     			<input type="text" class="form-control" ng-model="pigletEvent.dateStr7" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletEvent.dateStr7, 'date7')"/>
					 		</td>
					 	</tr>
					 </tbody>
					 </table>
                    </div>	
                    <div class="form-group"> 
                     <table class="no-border">
					  <tbody class="no-border-x no-border-y">
					 	<tr>
                    		<td>
                       			 <label><spring:message code='label.piginfo.pigleteventform.weight8'  text='Weight (8th month)'/></label>
                       		</td>
                       		<td>
                     			<input type="number" min="0" step="0.01"  ng-model="pigletEvent.weight8" id="weight8" name="weight8"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weight8.placeholder'  text='Enter weight at 8th month'/>"/>
                     		</td>
					 		<td>
                     			<label><spring:message code='label.piginfo.pigleteventform.date1'  text='Date'/></label>
					 		</td>
					 		<td>
                     			<input type="text" class="form-control" ng-model="pigletEvent.dateStr8" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(pigletEvent.dateStr8, 'date8')"/>
					 		</td>
					 	</tr>
					 </tbody>
					 </table>
                    </div>	
                    <div class="form-group"> 
                    <span style='color: red'>*</span><span style='color: red'>*</span><label><spring:message code='label.piginfo.pigleteventform.dateformat.messsage'  text='All dates to be entered in dd/mm/yyyy format'/></label>
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
		  <div id="searchFarrowEvents" class="modal colored-header warning custom-width">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.piginfo.pigletstatuseventform.farrowsearchresults.heading'  text='Farrow Events'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
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

  
		  

	
