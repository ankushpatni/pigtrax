<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.generateRationReport.heading'
								text='Generate Ration Report' /></h2>
</div>
<div class="cl-mcont" id="RationReportControllerId" ng-controller="RationReportController" ng-init="loadPremises('${CompanyId}')" class="container-fluid">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateRationReport" name="generateRationReportForm">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.generateRationReport.heading'
								text='Generate Ration Report' />
						</h3>
						<p>
							<spring:message
								code='label.generateRationReport.subheading'
								text='Please generate the report for a given company, premise and group for the given date range' />
						</p>
						<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.groupReport.generateReport.search.data.errormessage' text='Group Information not found for the search criteria'/></p>
						<p class="color-danger" ng-show="mandatoryField"><spring:message code='label.report.search.criteria.errormessage' text='Please provide the mandatory criteria to generate the report'/></p>
						<p>
						<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
								{%>
								<div  class="form-group">
								<select  class="form-control" required required-message="'<spring:message code='label.employee.companyRequired' text='Company is required' />'"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"  ng-change="loadPremises()">
								<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
                      			 	<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
                      		  </select>
								</div>
							<%}%>
						<div  class="form-group">
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  ng-change="selectGroups()">
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
						
						
						<div  class="form-group">
						<select  class="form-control"  required required-message="'<spring:message code='label.piginfo.groupEventForm.groupId.requiredMessage' text='Group Id is required' />'"  name="selectedGroup" id="selectedGroup" ng-model="selectedGroup"  >								
                      			 	<option ng-repeat="group in groupList" value="{{group.id}}" ng-value="group.id" ng-selected="selectedGroup == group.id">{{group.groupId}}</option>
                      		  </select>
						</div>
						
<!-- 						<div  class="form-group"> -->
<%-- 						<label><spring:message code='label.piginfo.generateReport.startDate'  text='Start Date'/></label>  --%>
<%-- 							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i> --%>
<%--                       	<input type="text" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"  required required-message="'<spring:message code='label.report.startdate.required' text='Start Date is required' />'" /> --%>
<!-- 								</div> -->
                    	<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true' name="endDate"  required required-message="'<spring:message code='label.report.enddate.required' text='End Date is required' />'" />
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
								invalid-message="'<spring:message code='' text='Only Numeric values Allowed.'/>'" />

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
						
						<button type="submit" value="report" ng-click="generateRationReport()">
							<spring:message code='label.piginfo.generateReport.button'
								text='Generate Report' />
						</button>
					</div>
					<input type="hidden" name="companyId1" id="companyId1"/>
					<input type="hidden" name="selectedPremise" id="selectedPremise"/>
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>