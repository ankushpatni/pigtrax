<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.leftmenu.managemasterdata.cleanupcompanydata.link'
								text='Clean Up Data' /></h2>
</div>
<div class="cl-mcont" id="cleanupControllerFormId" ng-controller="CompanyCleanupController" ng-init="getCompanyList()" class="container-fluid">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateDataExtraction" name="cleanUpCompanyDataForm">
					<div class="head">
						<p class="color-danger" ng-show="criteriaMessage"><spring:message code='label.report.search.criteria.errormessage' text='Please provide the mandatory criteria to generate the report'/></p>
						<p class="color-success" ng-show="successMessage"><spring:message code='label.company.cleanupsuccess' text='Company data cleaned up successfully.'/></p>
						
						<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
							{%>
							<div  class="form-group">
							<label><spring:message code="label.employee.company" text="Name" /><span style='color: red'>*</span></label>
							<select  class="form-control" required required-message="'<spring:message code='label.company.companyNameRequired' text='Company Name is required' />'"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"  ng-change="loadPremises()">
							<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
                     			 	<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
                     		  </select>
							</div>
						<%} %>
						
						<div  class="form-group">
						<label>Note : </label>
						 <p>When user clicks on the clean up button, all the live data (Eg : Pig, Group information) will be removed for the selected company from the system. You can not undo the operation onced one.</p>
						</div>
						
						
						<button type="button" value="report" ng-click="cleanUp()">
							<spring:message code='label.leftmenu.managemasterdata.performcleanupaction'
								text='Perform Cleanup' />
						</button>
					</div>
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>