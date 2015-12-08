<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2>${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="ChangePigIdController" ng-init="setCompanyId(${CompanyId})"  id="ChangePigIdControllerId">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div>
 		  <div class="col-sm-6 col-md-6">
 		  <div class="block-flat">
		   <form name="entryEventSearchForm" >
 		     <div class="head">
 		     	<div class="alert alert-success alert-white rounded"  ng-show="changedPigIdSuccess">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.changepigidform.submit.success.message' text='Pig Id changed successfully'/>
                  </div>
 		     <h3> <spring:message code='label.changeidform.search.heading'  text='Search for a Pig to change Id'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.entryeventform.search.errormessage' text='Please enter Pig Id/ Tattoo and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.entryeventform.search.data.errormessage' text='Pig Information not found for the search criteria'/></p>
			
            <input type="text" name="search"  ng-enter="getPigInformationForChangeId()" ng-model="searchText" placeholder="<spring:message code='label.piginfo.entryeventform.search.placeholder'  text='Search by Pig Id/ Tattoo ...'/>" class="form-control">
 		    
            <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="getPigInformationForChangeId()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1" class="icheck changepigId" value="pigId"  > <spring:message code='label.piginfo.entryeventform.search.pigid.option'  text='Pig Id'/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad2" class="icheck changepigId" value="tattoo" > <spring:message code='label.piginfo.entryeventform.search.tattoo.option'  text='Tattoo'/> 
                </label>
              </div>
            </div>
          </div>
		  </form>
          </div>
 		  </div>
 		  <div class="col-sm-3 col-md-3"></div>
 		</div>
          <div class="row" ng-show="pigInfo.id != null">
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat" >
                <div class="header">
                <h3><spring:message code='label.changeidform.heading'  text='Change ID'/></h3>
                <label ng-show="!pigInfo.active"  style='color:red' class='control-label has-error validationMessage'><spring:message code='label.changeidform.disablechangeid'  text='This pig is not active in the system hence its id can not be changed'/> </label>
                
                
                  <div class="alert alert-danger alert-white rounded"  ng-show="changedPigIdError">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.changepigidform.submit.error.message' text='An exception occured. Please check the values'/>
                  </div> 
                  <div class="alert alert-danger alert-white rounded"  ng-show="duplicatePigIdErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.changepigidform.duplicate.error.message' text='A piginfo record already exists with the same Pig Id'/>
                  </div>
                </div>
                <div class="content">
                  <form name="changeIdEventForm" novalidate angular-validator my-reset>                  
                  <input type=hidden name="companyId" ng-model="pigInfo.companyId" value="${CompanyId}"/>
                  <input type=hidden name="id" ng-model="pigInfo.id"/>
                    <div class="form-group">
                      <label><spring:message code='label.changepigidform.newpigid'  text='New Pig Id'/><span style='color: red'>*</span></label>
                      <input  required="true" type="text" name="pigId" ng-model="pigInfo.newPigId"  maxlength="30" 
                      placeholder="<spring:message code='label.changepigidform.newpigid.placeholder'  text='Enter new Pig Id'/>" 
                      required-message="'<spring:message code='label.piginfo.entryeventform.pigid.requiredmessage' text='Pig Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.entryeventform.pigid.invalidmessage' text='Only Alpha Numeric values are allowed' />'"  
						class="form-control" ng-blur="checkNewPigIdStatus()">
						
                    </div>
                    <label ng-show="newPigIdActiveError"  style='color:red' class='control-label has-error validationMessage'><spring:message code='label.changeidform.newPigIdActiveError'  text='Entered Pig Id is active in the system. Please choose another Pig Id'/> </label>
                    <div class="form-group">
                      <label><spring:message code='label.changepigidform.changedate'  text='Change Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7" id="changeDateDiv" >
                          <input size="16" type="date" id="changeDate" name="changeDate" ng-model="pigInfo.changePigIdDate" readonly="" class="form-control"   format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div> 
                        <label ng-show="changeDateRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.changepigidform.changeDate.requiredmessage' text='Change Date is required' /></label>
                    </div>
                    <button class="btn btn-success" ng-disabled="!pigInfo.active || newPigIdActiveError" ng-click="changePigId()" type="submit"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-warning" ng-click="resetForm()" type="button"><spring:message code='label.piginfo.entryeventform.cancel'  text='Clear Form'/></button>                   
                  </form>
                </div> 
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
</div>
<script> 
$(document).ready(function(){
 var currDate = new Date();
 var dateVal = currDate.getFullYear()+"-"+(currDate.getMonth()+1)+"-"+currDate.getDate();
	  $("#changeDateDiv").attr('data-date-enddate',dateVal);
});  
</script>