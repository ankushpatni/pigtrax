<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.piginfo.generateRemovalReport.heading'
								text='Generate Report' /></h2>
</div>
<div class="cl-mcont" id="removalReposrtGeneraterControllerId" ng-controller="removalReportController" ng-init="loadPremises('${CompanyId}','${REPORT_NO_DATA}')" class="container-fluid">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateRemovalReport" name="generateReportRemoval">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.piginfo.generateReport.heading'
								text='Generate Report' />
						</h3>
						<p class="color-danger" ng-show="searchDataErrorMessagePig"><spring:message code='label.piginfo.entryeventform.search.data.errormessage' text='Pig Information not found for the search criteria'/></p>
						<p class="color-danger" ng-show="searchDataErrorMessageGroup"><spring:message code='label.groupReport.generateReport.search.data.errormessage' text='Group Information not found for the search criteria'/></p>
						<p class="color-danger" ng-show="mentaoryField"><spring:message code='label.report.search.criteria.errormessage' text='Please enter mandatory fields.'/></p>
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
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise" ng-change="loadPigAndGroupInfo()" >
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
					<%-- 	<div  class="form-group">			
 		   				<!-- <input type="text" name="pigId" id="pigId" class="form-control" ng-model="pigId" placeholder="<spring:message code='label.sowReport.search.pig'  text='Search by Pig Id ...'/>"/>-->
						 <select  class="form-control"  name="search" id="search" ng-model="pigId" >
								<option value="" hidden><spring:message code='label.sowReport.search.pig' text='Search by Pig Id ...' /></option>
								<option ng-repeat="pigInfo in pigInfoListSearch" value="{{pigInfo.pigId}}" ng-value="pigInfo.pigId" ng-selected="pigId == pigInfo.pigId">{{pigInfo.pigId}}</option>
								</select>
						</div>
						
						<div  class="form-group">			
 		   				 <!--<input type="text" name="groupId" id="groupId" class="form-control" ng-model="groupId" placeholder="<spring:message code='label.groupReport.search.group'  text='Search by Group Id ...'/>"/>-->
						 <select  class="form-control"  name="search" id="search" ng-model="groupId" >
								<option value="" hidden><spring:message code='label.groupReport.search.group' text='Search by Group Id ...' /></option>
								<option ng-repeat="groupEventSearch in groupEventListSearch" value="{{groupEventSearch.groupId}}" ng-value="groupEventSearch.groupId" ng-selected="searchText == groupEventSearch.groupId">{{groupEventSearch.groupId}}</option>
								</select>
						</div> --%>
						
						<div  class="form-group">			
 		   				 <!--<input type="text" name="groupId" id="groupId" class="form-control" ng-model="groupId" placeholder="<spring:message code='label.groupReport.search.group'  text='Search by Group Id ...'/>"/>-->
						 <label><spring:message code='label.groupReport.search.animal' text='Animal Type' /><span style='color: red'>*</span></label>
						 <select  class="form-control"  name="animalType" id="animalType" ng-model="animalType" >
								<option value="" hidden><spring:message code='label.groupReport.search.animal' text='Animal Type' /></option>
								<option value="group" ><spring:message code='label.groupReport.search.Grover' text='Grover' /></option>
								<option value="pig" ><spring:message code='label.groupReport.search.Pig' text='Pig' /></option>
								<option value="piglet" ><spring:message code='label.groupReport.search.PigletStatus' text='Piglet Status' /></option>
								</select>
						</div>
						
						<div  class="form-group">
						<label><spring:message code='label.generateLactationLengthReport.startDate'  text='Start Date'/><span style='color: red'>*</span></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"/>
								</div>
                    	<label><spring:message code='label.generateLactationLengthReport.endDate'  text='End Date'/><span style='color: red'>*</span></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true' name="endDate"/>
								</div>
					
                    	
						<button type="button" value="report" ng-click="searchPigInfo()">
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