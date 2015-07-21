<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.entryeventform.piginformation'  text='Pig Information'/></h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="EntryEventController" ng-init="populateBarns(${CompanyId})">
 <div class="row">
 		  <div class="col-sm-3 col-md-3"></div>
 		  <div class="col-sm-6 col-md-6">
 		  <div class="block-flat">
		   <form name="entryEventSearchForm" >
 		     <div class="head">
            <h3><spring:message code='label.piginfo.entryeventform.search.heading'  text='Search'/></h3>
            <p class="color-danger">{{searchErrorMessage}}</p>
            <input type="text" name="search" ng-model="searchText" placeholder="<spring:message code='label.piginfo.entryeventform.search.placeholder'  text='Search by Pig Id / Tattoo ...'/>" class="form-control">
            <div class="options">
			 <div class="btn-group pull-right">
                <button type="button" class="btn btn-primary active" ng-click="getPigInformation()"><i class="fa fa-search"></i></button>
              </div>
              <div class="form-group">
                <label class="radio-inline">
                  <input type="radio" name="rad1" id="rad1" class="icheck" value="pigId" > <spring:message code='label.piginfo.entryeventform.search.pigid.option'  text='Pig Id'/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="rad1"  id="rad1" class="icheck" value="tattoo"> <spring:message code='label.piginfo.entryeventform.search.tattoo.option'  text='Tattoo'/> 
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
                  <p class="color-success">{{alertMessage}}</p>
				  <p class="color-danger">{{errorMessage}}</p>
                </div>
                <div class="content">
                  <form name="entryEventForm" novalidate angular-validator>
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
					   ng-pattern="/^[a-z0-9]+$/i" 
					   invalid-message="'<spring:message code='label.piginfo.entryeventform.sire.invalidmessage' text='Only Alpha Numeric values are allowed' />'"
					  />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.dam'  text='Dame'/></label>
                       <input type="text" class="form-control" name="dame" ng-model="pigInfo.damId" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.dam.placeholder' text='Enter Dame'/>" 
					   ng-pattern="/^[a-z0-9]+$/i" 
					   invalid-message="'<spring:message code='label.piginfo.entryeventform.dam.invalidmessage' text='Only Alpha Numeric values are allowed' />'"
					  />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.origin'  text='Origin'/></label>
                      <input type="text" class="form-control" name="origin" ng-model="pigInfo.origin" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.origin.placeholder' text='Enter origin'/>" 
					   ng-pattern="/^[a-z0-9]+$/i" 
					   invalid-message="'<spring:message code='label.piginfo.entryeventform.origin.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.gline'  text='Gline'/></label>
                      <input type="text" class="form-control" name="gline" ng-model="pigInfo.gline" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.gline.placeholder' text='Enter gline'/>" 
					   ng-pattern="/^[a-z0-9]+$/i" 
					   invalid-message="'<spring:message code='label.piginfo.entryeventform.gline.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.gcompany'  text='GCompany'/></label>
                      <input type="text" class="form-control" name="gcompany" ng-model="pigInfo.gcompany" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.gcompany.placeholder' text='Enter gcompany'/>" 
					   ng-pattern="/^[a-z0-9]+$/i" 
					   invalid-message="'<spring:message code='label.piginfo.entryeventform.gcompany.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.birthdate'  text='Birth Date'/><span style='color: red'>*</span></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
                          <input size="16" type="date" id="birthDate" name="birthDate" ng-model="pigInfo.birthDate" readonly="" class="form-control"><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.tattoo'  text='Tattoo'/></label>
                      <input type="text" class="form-control" name="tattoo" ng-model="pigInfo.tattoo" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.tattoo.placeholder' text='Enter tattoo'/>" 
					   ng-pattern="/^[a-z0-9]+$/i" 
					   invalid-message="'<spring:message code='label.piginfo.entryeventform.tattoo.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.alternatetattoo'  text='Alternate Tattoo'/></label>
                      <input type="text" class="form-control" name="alternateTattoo" ng-model="pigInfo.alternateTattoo" maxlength="30" placeholder="<spring:message code='label.piginfo.entryeventform.alternateTattoo.placeholder' text='Enter tattoo'/>" 
					   ng-pattern="/^[a-z0-9]+$/i" 
					   invalid-message="'<spring:message code='label.piginfo.entryeventform.alternateTattoo.invalidmessage' text='Only Alpha Numeric values are allowed' />'" />
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.remarks'  text='Remarks'/><span style='color: red'>*</span></label>
                      <textarea name="remarks" ng-model="pigInfo.remarks" class="form-control" placeholder="<spring:message code='label.piginfo.entryeventform.remarks.placeholder' text='Enter remarks'/>" required required-message="'<spring:message code='label.piginfo.entryeventform.remarks.requiredmessage' text='Remarks is required'/>'"></textarea>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.sowcondition'  text='Sow Condition'/><span style='color: red'>*</span></label>
                      <select class="form-control" name="sowCondition" ng-model="pigInfo.sowCondition" required required-message="'<spring:message code='label.piginfo.entryeventform.sowcondition.requiredmessage' text='Sow condition is required'/>'">
                          <option>1</option>
                          <option>2</option>
                          <option>3</option>
                        </select>
                    </div>
                    <button class="btn btn-primary" ng-click="addEntryEvent()"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-default"><spring:message code='label.piginfo.entryeventform.cancel'  text='Cancel'/></button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
</div>

