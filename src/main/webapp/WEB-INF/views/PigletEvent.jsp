<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.farroweventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="PigletEventController" ng-init="loadPage(${CompanyId})">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div> 
 		  <div class="col-sm-6 col-md-6">
 		  <div class="block-flat">
		   <form name="pigletEventSearchForm" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pigletEventform.search.heading'  text='Search'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.pigletEventform.search.errormessage' text='Please enter Farrow Id/ Piglet Tattoo Id and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.pigletEventform.search.data.errormessage' text='Piglet event information not found for the search criteria'/></p>
			
            <input type="text" name="search" ng-model="searchText" placeholder="<spring:message code='label.piginfo.pigleteventform.search.placeholder'  text='Search by Farrow Id / Tattoo ...'/>" class="form-control">

			 <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="getPigletEventInformation()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="farrowId" class="icheck" value="farrowId" > <spring:message code='label.piginfo.pigleteventform.farrowId'  text='Farrow Id'/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="pigletTattooId" class="icheck" value="pigletTattooId"> <spring:message code='label.piginfo.pigleteventform.tattooId'  text='Tattoo'/> 
                </label>			
              </div>
            </div>            
          </div>
		  </form>
		  <form name="pigletEventSearchResultForm"  ng-if="pigletEventList != null && pigletEventList.length != 0" >
 		     <div class="head">
            <h3> <spring:message code='label.piginfo.pigletEventSearchResultForm.searchresults.heading'  text='Piglet Events'/></h3>
             <table>
				<thead>
                     <tr>                       
                       <th><spring:message code='label.piginfo.pigleteventform.tattooId'  text='Farrow Id'/> </th>
                       <th><spring:message code='label.piginfo.pigleteventform.weightAtBirth'  text='Weight at Birth'/> </th>
                       <th><spring:message code='label.piginfo.pigleteventform.weightAtWeaning'  text='Weight at Weaning'/> </th>
                       <th><spring:message code='label.employeegroup.list.header.action'  text='Action'/> </th>
                     </tr>
                 </thead>
                 <tbody>
                   <tr ng-repeat="pigletEventDto in pigletEventList">
                    <td>{{pigletEventDto.tattooId}}</td>
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
                  <h3><spring:message code='label.piginfo.pigleteventform.pigletEvent'  text='Piglet Event'/></h3>
                  
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
                  <div class="alert alert-danger alert-white rounded" ng-show="farrowEventValidation_ErrCode_1">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.pigleteventform.farrowEventValidation_ErrCode1.message' text='Piglet event doesn\'t fall between the acceptated duration of the service date'/>
                  </div>        
                  <div class="alert alert-danger alert-white rounded"  ng-show="entryEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.pigleteventform.duplicate.error.message' text='A piglet record already exists with the same farrow id'/>
                  </div>    
                </div>
                <div class="content">
                  <form name="pigleteventform" novalidate angular-validator>
                  <input type=hidden name="id" ng-model="pigletEvent.pigletId"/>  
				  <input type=hidden name="farrowEventId" ng-model="pigletEvent.farrowEventId"/>
					 <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.farrowId'  text='Farrow Id'/><span style='color: red'>*</span></label>
                     <input type="text" ng-model="pigletEvent.farrowId" id="farrowId" name="farrowId" required  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.farrowId.placeholder'  text='Enter Farrow Id'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.farrowId.requiredmessage' text='Farrow Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.pigleteventform.farrowId.invalidmessage' text='Only Alpha numeric values are allowed' />'" ng-blur="checkForFarrowId()" ng-focus="clearMessages()"/>
                    </div>
					<label ng-show="inValidPigIdFromServer" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.farrowId.server.invalidmessage' text='Invalid Farrow Id for the company' /></label>
					<label ng-show="pigletsAdded" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.farrowId.server.pigletsaddedmessage' text='All piglets information added for the selected farrow event.' /></label>
					<label ng-show="requiredPigIdMessage" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.farroweventform.farrowId.requiredmessage' text='Farrow Id is required' /></label>	
					 <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.tattooId'  text='Tattoo Id'/><span style='color: red'>*</span></label>
                     <input type="text" required ng-model="pigletEvent.tattooId" id="tattooId" name="tattooId"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.tattooId.placeholder'  text='Enter Tatto Id of the piglet'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.tattooId.requiredmessage' text='Tattoo Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.numeric.errormessage' text='Only Aplha numeric values are allowed' />'" ng-blur="checkForTattooId()" ng-focus="clearMessages()"/>
                    </div>					
					<div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.weightAtBirth'  text='Weight at Birth'/><span style='color: red'>*</span></label>
                     <input type="text" required  ng-model="pigletEvent.weightAtBirth" id="weightAtBirth" name="weightAtBirth"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weightAtBirth.placeholder'  text='Enter weigth at birth of the piglet'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.weightAtBirth.requiredmessage' text='Weight at birth information is required' />'"
						ng-pattern="/^[0-9]+$/i"
						invalid-message="'<spring:message code='label.numeric.errormessage' text='Only numeric values are allowed' />'"/>
                    </div>	
                    
                    <div class="form-group"> 
                      <label><spring:message code='label.piginfo.pigleteventform.weightAtWeaning'  text='Weight at Weaning'/><span style='color: red'>*</span></label>
                     <input type="text" required ng-model="pigletEvent.weightAtWeaning" id="weightAtWeaning" name="weightAtWeaning"  class="form-control" maxlength="30" placeholder="<spring:message code='label.piginfo.pigleteventform.weightAtWeaning.placeholder'  text='Enter weigth at weaning of the piglet'/>" 
                      required-message="'<spring:message code='label.piginfo.pigleteventform.weightAtWeaning.requiredmessage' text='Weight at weaning information is required' />'"
						ng-pattern="/^[0-9]+$/i"
						invalid-message="'<spring:message code='label.numeric.errormessage' text='Only numeric values are allowed' />'"/>
                    </div>						
                    <button class="btn btn-primary" ng-click="addPigletEvent()" type="submit" ng-disabled="inValidPigIdFromServer || pigletsAdded"><spring:message code='label.piginfo.farroweventform.submit'  text='Submit'/></button>
                    <button class="btn btn-default" type="button" ng-click="resetForm()"><spring:message code='label.piginfo.farroweventform.cancel'  text='Clear Form'/></button>
                    <button type="button" class="btn btn-danger pull-right" ng-click="deletePigletEvent()" ng-show="pigletEvent.pigletId != null && pigletEvent.pigletId > 0" ng-confirm-click="<spring:message code='label.piginfo.pigleteventformform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.pigleteventform.delete'  text='Delete'/></button>
                  </form>
                </div> 
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
		<div class="md-overlay"></div>
</div>

  
		  

	
