<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.entryeventform.piginformation'  text='Pig Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="EntryEventController" ng-init="populateBarns(${CompanyId})"  id="EntryEventControllerId">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div>
 		  <div class="col-sm-6 col-md-6">
 		  <div class="block-flat">
		   <form name="entryEventSearchForm" >
 		     <div class="head">
 		     
 		     <h3> <spring:message code='label.piginfo.entryeventform.search.heading'  text='Search'/></h3>
            <p class="color-danger" ng-show="searchErrorMessage"><spring:message code='label.piginfo.entryeventform.search.errormessage' text='Please enter Pig Id/ Tattoo and select the corresponding option'/></p>
            <p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.entryeventform.search.data.errormessage' text='Pig Information not found for the search criteria'/></p>
			
            <input type="text" name="search" ng-model="searchText" placeholder="<spring:message code='label.piginfo.entryeventform.search.placeholder'  text='Search by Pig Id/ Tattoo ...'/>" class="form-control">
 		    
            <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="getPigInformation()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1" class="icheck" value="pigId"  > <spring:message code='label.piginfo.entryeventform.search.pigid.option'  text='Pig Id'/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad2" class="icheck" value="tattoo" > <spring:message code='label.piginfo.entryeventform.search.tattoo.option'  text='Tattoo'/> 
                </label>
              </div>
            </div>
          </div>
		  </form>
          </div>
 		  </div>
 		  <div class="col-sm-3 col-md-3"></div>
 		</div>
          <div class="row">
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.piginfo.entryeventform.entryevent'  text='Entry Event'/></h3>
                  
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventSuccessMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.entryeventform.submit.success.message' text='Pig information saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded"  ng-show="entryEventErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.entryeventform.submit.error.message' text='An exception occured. Please check the values'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded"  ng-show="entryEventDuplicateErrorMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.piginfo.entryeventform.duplicate.error.message' text='A piginfo record already exists with the same Pig Id/ Tattoo'/>
                  </div>
                  <div class="alert alert-success alert-white rounded"  ng-show="entryEventDeleteMessage">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.piginfo.entryeventform.delete.message'  text='Pig information deleted'/>
                  </div>                  
                </div>
                <div class="content">
                  <form name="entryEventForm" novalidate angular-validator my-reset>
                  <input type=hidden name="companyId" ng-model="pigInfo.companyId" value="${CompanyId}"/>
                  <input type=hidden name="id" ng-model="pigInfo.id"/>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.pigid'  text='Pig Id'/><span style='color: red'>*</span></label>
                      <input ng-hide="pigInfo.id != null && pigInfo.id != 0" required="true" type="text" name="pigId" ng-model="pigInfo.pigId"  maxlength="30" 
                      placeholder="<spring:message code='label.piginfo.entryeventform.pigid.placeholder'  text='Enter Pig Id'/>" 
                      required-message="'<spring:message code='label.piginfo.entryeventform.pigid.requiredmessage' text='Pig Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.piginfo.entryeventform.pigid.invalidmessage' text='Only Alpha Numeric values are allowed' />'"  class="form-control">
				      <p ng-show="pigInfo.id != null && pigInfo.id != 0">{{pigInfo.pigId}}</p>
                    </div>
                     <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.barn'  text='Barn'/></label>
                      <select class="form-control" ng-model="pigInfo.barnId"                      
                      ng-change="getPenList()">
                          <option ng-repeat="barnDto in barns" value="{{barnDto.id}}">{{barnDto.barnId}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.pen'  text='Pen'/></label>
                     <select class="form-control" ng-model="pigInfo.penId">
                          <option ng-repeat="pen in penInfo" value="{{pen.id}}">{{pen.penId}}</option>
                        </select>
                    </div>
                   
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.sex'  text='Sex'/><span style='color: red'>*</span></label>
                      <select class="form-control"  name="sexType" ng-model="pigInfo.sexTypeId"  required 
                      required-message="'<spring:message code='label.piginfo.entryeventform.sex.requiredmessage' text='Sex Information is required' />'"
                       ng-options="k as v for (k, v) in sexTypes">
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.sire'  text='Sire'/></label>
                      <input type="text" class="form-control" name="sire" ng-model="pigInfo.sireId" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.sire.placeholder' text='Enter sire'/>" 
					     invalid-message="'<spring:message code='label.piginfo.entryeventform.sire.invalidmessage' text='Only Alpha Numeric values are allowed' />'"
					  />   
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.dam'  text='Dame'/></label>
                       <input type="text" class="form-control" name="dame" ng-model="pigInfo.damId" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.dam.placeholder' text='Enter Dame'/>" 
					     invalid-message="'<spring:message code='label.piginfo.entryeventform.dam.invalidmessage' text='Only Alpha Numeric values are allowed' />'"
					  />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.origin'  text='Origin'/></label>
                      <input type="text" class="form-control" name="origin" ng-model="pigInfo.origin" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.origin.placeholder' text='Enter origin'/>" 			   
					   invalid-message="'<spring:message code='label.piginfo.entryeventform.origin.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.gline'  text='Gline'/></label>
                      <input type="text" class="form-control" name="gline" ng-model="pigInfo.gline" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.gline.placeholder' text='Enter gline'/>" 
					      invalid-message="'<spring:message code='label.piginfo.entryeventform.gline.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.gcompany'  text='GCompany'/></label>
                      <input type="text" class="form-control" name="gcompany" ng-model="pigInfo.gcompany" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.gcompany.placeholder' text='Enter gcompany'/>" 
					     invalid-message="'<spring:message code='label.piginfo.entryeventform.gcompany.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.birthdate'  text='Birth Date'/></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="birthDate" name="birthDate" ng-model="pigInfo.birthDate" readonly="" class="form-control"   format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div> 
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.entryDate'  text='Entry Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="entryDate" name="entryDate" ng-model="pigInfo.entryDate" readonly="" class="form-control"  format-date required-message="'<spring:message code='label.piginfo.entryeventform.entryDate.requiredmessage' text='Entry Date is required' />'"><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div> 
                        <label ng-show="entryDateRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.entryeventform.entryDate.requiredmessage' text='Entry Date is required' /></label>
                        <label ng-show="invalidEntryDate" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.entryeventform.entryDate.invalidmessage' text='Entry date can not be earlier than birth date' /></label>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.tattoo'  text='Tattoo'/></label>
                      <input type="text" class="form-control" name="tattoo" ng-model="pigInfo.tattoo" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.tattoo.placeholder' text='Enter tattoo'/>" 
					      invalid-message="'<spring:message code='label.piginfo.entryeventform.tattoo.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.alternateTattoo'  text='Alternate Tattoo'/></label>
                      <input type="text" class="form-control" name="alternateTattoo" ng-model="pigInfo.alternateTattoo" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.alternateTattoo.placeholder' text='Enter tattoo'/>" 
					    invalid-message="'<spring:message code='label.piginfo.entryeventform.alternateTattoo.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.remarks'  text='Remarks'/><span style='color: red'>*</span></label>
                      <textarea name="remarks" ng-model="pigInfo.remarks" class="form-control" placeholder="<spring:message code='label.piginfo.entryeventform.remarks.placeholder' text='Enter remarks'/>" required required-message="'<spring:message code='label.piginfo.entryeventform.remarks.requiredmessage' text='Remarks is required'/>'"></textarea>
                    </div>
                    <button class="btn btn-primary" ng-click="addEntryEvent()" type="submit"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-default" ng-click="resetForm()" type="button"><spring:message code='label.piginfo.entryeventform.cancel'  text='Clear Form'/></button>
                    <button class="btn btn-danger pull-right" ng-click="deletePigInfo()" type="button" ng-show="pigInfo.id != null && pigInfo.id > 0" ng-confirm-click="<spring:message code='label.piginfo.entryeventform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>"><spring:message code='label.piginfo.entryeventform.delete'  text='Delete'/></button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
</div>


