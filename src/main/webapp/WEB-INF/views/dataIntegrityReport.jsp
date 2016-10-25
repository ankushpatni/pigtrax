<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.leftmenu.reports.dataIntegrityReport'
								text='Generate Data Integrity Report' /></h2>
</div>
<div class="cl-mcont" id="dataIntegrityControllerId" ng-controller="DataIntegrityReportController" ng-init = "load('${CompanyId}','${REPORT_NO_DATA}')" class="container-fluid">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateDataIntegrityReport" name="generateDataIntegrityReportForm">
					<div class="head">						
						<h3>
							<spring:message
								code='label.dataIntegrityReport.heading'
								text='Generate Data Integrity Report' />
						</h3>
						<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.report.search.data.errormessage' text='No data found for the given criteria'/></p>
						<p class="color-danger" ng-show="criteriaMessage"><spring:message code='label.report.search.criteria.errormessage' text='Please provide the mandatory criteria to generate the report'/></p>
						
						<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
								{%>
								<div  class="form-group">
								<select  class="form-control"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"  ng-change="loadPremises()">
								<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
                      			 	<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
                      		  </select>
								</div>
						<%} else {
							%>
							  <input type="hidden" name="selectedCompany" value="${CompanyId}">
							<%
						}%>
						
						
						<div  class="form-group">
						<label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/></label>
						<select  class="form-control"  required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  ng-change="loadData()">
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
						
						
						<div  class="form-group">
						<label><spring:message code='label.piginfo.generateReport.startDate'  text='Start Date'/><span style='color: red'>*</span></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"/>
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
					
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>