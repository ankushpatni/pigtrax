<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.generateTargetReport.heading'
								text='Generate Target Report' /></h2>
</div>
<div class="cl-mcont" id="TargetReportControllerId" ng-controller="TargetReportController" class="container-fluid" ng-init="loadPremises('${CompanyId}','${REPORT_NO_DATA}')">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateTargetReport" name="generateTargetReportForm">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.generateTargetReport.heading'
								text='Generate Target Report' />
						</h3>
						<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.report.search.data.errormessage' text='No data found for the given criteria'/></p>
						<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
								{%>
								<div  class="form-group">
								<select  class="form-control"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"   ng-change="loadPremises()">
								<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
                      			 	<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
                      		  </select>
								</div>
							<%} else{ %>
							<input type="hidden" name="selectedCompany" id="selectedCompany" value="${CompanyId}"/>
							<%} %>		
							<div  class="form-group">
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  >
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>					
						<div  class="form-group">
						<label><spring:message code='label.generateLactationLengthReport.startDate'  text='Start Date'/></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"/>
								</div>
								
								
								
								</div>
						<button type="button" value="report" ng-click="generateTargetReport()">
							<spring:message code='label.piginfo.generateReport.button'
								text='Generate Report' />
						</button>
					</div>
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>