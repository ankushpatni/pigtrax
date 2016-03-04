<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.piginfo.generateSaleReport.heading'
								text='Generate Sale Report' /></h2>
</div>
<div class="cl-mcont" id="removalSaleGeneraterControllerId" ng-controller="saleReportController" ng-init="loadPremises('${CompanyId}')" class="container-fluid">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateSaleReport" name="generateReportSale">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.piginfo.generateReport.heading'
								text='Generate Report' />
						</h3>
						<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.entryeventform.search.data.errormessage' text='Pig Information not found for the search criteria'/></p>
						<p class="color-danger" ng-show="searchDataErrorMessageGroup"><spring:message code='label.groupReport.generateReport.search.data.errormessage' text='Group Information not found for the search criteria'/></p>
						
						<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
								{%>
								<div  class="form-group">
								<select  class="form-control"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"  ng-change="loadPremises()">
								<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
                      			 	<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
                      		  </select>
						</div>
							<%}%>
						<div  class="form-group">
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise" ng-change="getBarnList()" >
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
						<div  class="form-group">
						<select  class="form-control"  name="selectedBarn" id="selectedBarn" ng-model="selectedBarn"  >
								<option value="" hidden><spring:message code='label.piginfo.barn.placeholder' text='Select Barn' /></option>
                      			 	<option ng-repeat="barn in barnList" value="{{barn.id}}" ng-value="barn.id" ng-selected="selectedBarn == barn.id">{{barn.barnId}}</option>
                      		  </select>
						</div>
						<div  class="form-group">			
 		   				 <input type="text" name="ticketNumber" id="ticketNumber" class="form-control" ng-model="ticketNumber" placeholder="<spring:message code='label.saleReport.search.ticketNumber'  text='Search by Ticket Number ...'/>"/>
						</div>
						
						<div  class="form-group">			
 		   				 <input type="text" name="groupId" id="groupId" class="form-control" ng-model="groupId" placeholder="<spring:message code='label.groupReport.search.group'  text='Search by Group Id ...'/>"/>
						</div>
						
						<div  class="form-group">
						<label><spring:message code='label.generateLactationLengthReport.startDate'  text='Start Date'/></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="startDate" mask="19/39/2999" mask-validate='true' name="startDate"/>
								</div>
                    	<label><spring:message code='label.generateLactationLengthReport.endDate'  text='End Date'/></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      	<input type="text" class="form-control" ng-model="endDate" mask="19/39/2999" mask-validate='true' name="endDate"/>
								</div>
					
                    	
						<button type="button" value="report" ng-click="searchPigInfo()">
							<spring:message code='label.piginfo.generateReport.button'
								text='Generate Report' />
						</button>
					</div>
					<input type="hidden" name="companyId1" id="companyId1"/>
					<input type="hidden" name="selectedPremise" id="selectedPremise"/>
					<input type="hidden" name="selectedBarn" id="selectedBarn"/>
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>