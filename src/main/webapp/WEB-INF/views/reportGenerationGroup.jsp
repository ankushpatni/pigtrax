<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.groupReport.generateReport.heading'
								text='Generate Group Report' /></h2>
</div>
<div class="cl-mcont" id="groupReposrtGeneraterControllerId" ng-controller="groupReportController" ng-init="loadPremises('${CompanyId}','${REPORT_NO_DATA}')" class="container-fluid">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateReportGroup" name="generateReportGroup">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.groupReport.generateReport.heading'
								text='Generate Group Report' />
						</h3>
						<p class="color-danger" ng-show="searchDataErrorMessageGroup"><spring:message code='label.groupReport.generateReport.search.data.errormessage' text='Group Information not found for the search criteria'/></p>
						<p class="color-danger" ng-show="mentaoryField"><spring:message code='label.piginfo.entryeventform.search.data.mandatory' text='Please enter mandatory fields.'/></p>
						<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.report.search.data.errormessage' text='No data found for the given criteria'/></p>
						
						<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
								{%>
								<div  class="form-group">
								<label><spring:message code="label.employee.company" text="Name" /><span style='color: red'>*</span></label>
								
								<select  class="form-control"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"  ng-change="loadPremises()">
								<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
                      			 	<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
                      		  </select>
						</div>
							<%}%>
						<div  class="form-group">
						<label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise" ng-change="loadGroupInfo()" >
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
						<div  class="form-group">			
 		   				 <!---<input type="text" name="search" id="search" required required-message="'<spring:message code='label.piginfo.entryeventform.pigid.requiredmessage' text='Pig Id is required' />'" class="form-control" ng-model="searchText" placeholder="<spring:message code='label.groupReport.search.group'  text='Search by Group Id ...'/>"/>-->
						 <label><spring:message code='label.group.entryeventform.name'  text='Group Name'/><span style='color: red'>*</span></label>
						 <select  class="form-control"  name="groupId" id="groupId" ng-enter="getPigInformation()" ng-model="groupId" >
								<option value="" hidden><spring:message code='label.groupReport.search.group' text='Search by Group Id ...' /></option>
								<option ng-repeat="groupEventSearch in groupEventListSearch" value="{{groupEventSearch.groupId}}" ng-value="groupEventSearch.groupId" ng-selected="groupId == groupEventSearch.groupId">{{groupEventSearch.groupId}}</option>
								</select>
						</div>
                    	
						<button type="button" value="report" ng-click="searchGroupInfo()">
							<spring:message code='label.piginfo.generateReport.button'
								text='Generate Report' />
						</button>
					</div>
					<input type="hidden" name="companyId1" id="companyId1"/>
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>