<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.leftmenu.reports.dataExtraction'
								text='Generate Data Extraction Report' /></h2>
</div>
<div class="cl-mcont" id="dataIntegrityControllerId" ng-controller="DataExtractionReportController" ng-init = "loadPremises('${CompanyId}','${REPORT_NO_DATA}')" class="container-fluid">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateDataExtraction" name="generateDataExtractionForm">
					<div class="head">						
						<h3>
							<spring:message
								code='label.leftmenu.reports.dataExtraction'
								text='Generate Data Extraction Report' />
						</h3>
						<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.report.search.data.errormessage' text='No data found for the given criteria'/></p>
						<p class="color-danger" ng-show="criteriaMessage"><spring:message code='label.report.search.criteria.errormessage' text='Please provide the mandatory criteria to generate the report'/></p>
						
						<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
							{%>
							<div  class="form-group">
							<label><spring:message code="label.employee.company" text="Name" /><span style='color: red'>*</span></label>
							<select  class="form-control"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"  ng-change="loadPremises()">
							<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
                     			 	<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
                     		  </select>
							</div>
						<%} else
						{
							 %>
							 <input type="hidden" name="selectedCompany" value="${CompanyId}"/>
							 <%
						}%>
						<div  class="form-group">
						<label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  ng-change="loadData()">
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
						
						<div  class="form-group">						
						  <select class="form-control" id="selectedEvent"  name="selectedEvent" ng-model="selectedEvent" ng-change="disableRadio()" >
						  <option value="" hidden><spring:message code='label.dataExtractionReport.event' text='Select Event' /></option>
                      		<option ng-repeat="key in usedEventKeys" ng-value="key" >{{eventTypeValues[key]}}</option>                          
                        	</select>
						</div>
						
						<div  class="form-group" ng-hide="disableRadioOnIndivPigletEvent">
							<label class="radio-inline">
			                  <input type="radio" name="rad1" id="rad1" class="farrowevent" value="pigId" ng-model="reportOption"  ng-change="changeSelection()" > <spring:message code='label.piginfo.farroweventform.search.pigid.option'  text='Pig Id'/>
			                </label>
			                <label class="radio-inline">
			                  <input type="radio" name="rad2" id="rad2" class="farrowevent" value="groupId" ng-model="reportOption"  ng-change="changeSelection()" > <spring:message code='label.piginfo.removalExceptSales.search.option.groupId'  text='Group'/> 
			                </label>
						</div>
						<div  class="form-group" ng-show="reportOption==='pigId'">						
						  
						 <select  class="form-control"  name="selectedPig" id="selectedPig" ng-model="parent.selectedPig" style="width:85%">
							<option value="" hidden><spring:message code='label.piginfo.pigId.placeholder' text='Select PigId' /></option>
							<option ng-repeat="pigInfo in pigInfoList" value="{{pigInfo.id}}" ng-value="pigInfo.id" ng-selected="selectedPig == pigInfo.id">{{pigInfo.pigId}}</option>
							</select>
						
						</div>
						<div  class="form-group" ng-show="reportOption==='groupId'">						
						  
						 <select  class="form-control"   id="selectedGroup" name="selectedGroup"  ng-model="selectedGroup" style="width:85%">
							<option value="" hidden><spring:message code='label.piginfo.groupEventForm.search.placeholder' text='Search by Group Id ...' /></option>
							<option ng-repeat="group in groupList" value="{{group.id}}" ng-value="group.id" ng-selected="selectedGroup == group.id">{{group.groupId}}</option>
							</select>
						
						</div>
						
						<div  class="form-group">
						<label><spring:message code=''  text='Duration'/><span style='color: red'>*</span></label> 
							<!-- i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i-->
							<input class="form-control" type="text"
								placeholder="<spring:message code='' text='Duration' />"
								name="Duration" ng-model="duration" maxlength="8"
								required
								required-message="'<spring:message code='' text='Duration required' />'"
								ng-pattern="/^\d{1,8}?$/i"
								invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'" />

						</div>
						<div  class="form-group" >
							<label class="radio-inline">
			                  <input type="radio" name="days" id="days" class="farrowevent" value="days" ng-model="selectedDurationOption"   > <spring:message code=''  text='Days'/>
			                </label>
			                <label class="radio-inline">
			                  <input type="radio" name="weeks" id="weeks" class="farrowevent" value="weeks" ng-model="selectedDurationOption"  > <spring:message code=''  text='Weeks'/> 
			                </label>
			                <label class="radio-inline">
			                  <input type="radio" name="months" id="months" class="farrowevent" value="months" ng-model="selectedDurationOption" > <spring:message code=''  text='Months'/> 
			                </label>
			                <label class="radio-inline">
			                  <input type="radio" name="years" id="years" class="farrowevent" value="years" ng-model="selectedDurationOption" > <spring:message code=''  text='Years'/> 
			                </label>
						</div>
								
						
								
								
                    	<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/><span style='color: red'>*</span></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true' name="endDate"/>
								</div>
								
						
						
								
						
						<button type="button" value="report" ng-click="generate()">
							<spring:message code='label.piginfo.generateReport.button'
								text='Generate Report' />
						</button>
					</div>
					<input type="hidden" name="reportOption" id="reportOption"/>
					<input type="hidden" name="companyId1" id="companyId1"/>
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>