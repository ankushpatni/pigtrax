<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.generateGroupStatusReport.heading'
								text='Generate Group Status Report' /></h2>
</div>
<div class="cl-mcont" id="groupStatusReportControllerId" ng-controller="GroupStatusReportController" ng-init="loadPremises('${CompanyId}','${REPORT_NO_DATA}')" class="container-fluid">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateGroupStatusReport" name="generateGroupStatusReportForm">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.generateGroupStatusReport.heading'
								text='Generate Group Status Report' />
						</h3>
						<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.groupReport.generateReport.search.data.errormessage' text='Group Information not found for the search criteria'/></p>
						
						<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
								{%>
								<div  class="form-group">
								<label><spring:message code="label.employee.company" text="Name" /><span style='color: red'>*</span></label>
								<select  class="form-control"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"  ng-change="loadPremises()">
								<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
                      			 	<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
                      		  </select>
								</div>
							<%} else {%>
							
							<input type="hidden" name="selectedCompany" id="selectedCompany"/>
							<%} %>
						<div  class="form-group">
						<label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  ng-change="selectGroups()"  >
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
						
						<div  class="form-group">
						<label><spring:message code='label.leftmenu.managepigevents.groupEvent.link'  text='Group Event'/><span style='color: red'>*</span></label>
						<select  class="form-control"  required required-message="'<spring:message code='label.piginfo.groupEventForm.groupId.requiredMessage' text='Group Id is required' />'"  name="selectedGroup" id="selectedGroup" ng-model="selectedGroup"  >								
                      			 	<option ng-repeat="group in groupList" value="{{group.id}}" ng-value="group.id" ng-selected="selectedGroup == group.id">{{group.groupId}}</option>
                      		  </select>
						</div>
						
						
						<div  class="form-group">
						<label><spring:message code='label.piginfo.generateReport.startDate'  text='Start Date'/><span style='color: red'>*</span></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" required required-message="'<spring:message code='label.report.startdate.required' text='Start date is required' />'" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"/>
								</div>
                    	<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/><span style='color: red'>*</span></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in dd/mm/yyyy format)'/></i>
                      	<input type="text" required required-message="'<spring:message code='label.report.enddate.required' text='End date is required' />'" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true' name="endDate"/>
								</div>
						<button type="submit" value="report" ng-click="generateProdEventLog()">
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