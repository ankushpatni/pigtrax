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
						<label><spring:message code='label.premise.sowSource'  text='Sow Source'/><span style='color: red'>*</span></label>
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.sowSourceYes.requiredMessage' text='Sow source is required' />'"  name="selectedSowSource" id="selectedSowSource" ng-model="selectedSowSource" >
								<option value="" hidden>----</option>
                      			 	<option ng-repeat="premise in sowSourceList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedSowSource == premise.id">{{premise.name}}</option>
                      			 	<option value="-1"><spring:message code='label.pigtrax.all.txt'  text='All'/></option>
                      		  </select>
                      		  	
						</div>	
							
						<div  class="row">
						<div class="form-group col-md-3">
						<label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
<%-- 						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  ng-change="selectGroups()"  > --%>
<%-- 								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option> --%>
<!--                       			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option> -->
<!--                       		  </select> -->
                      		  <div ng-dropdown-multiselect="" options="premiseValues" selected-model="selectedPremises"  extra-settings="multiselectdropdownsettings"></div>	
						</div>					
							  
						<div class="form-group col-md-3">
							  
							  						<label><spring:message   text='Group Status'/><span style='color: red'>*</span></label>
						<select  class="form-control"  value="Open Group" required required-message="'<spring:message text='Group Status is required' />'"  name="groupStatus" id="groupStatus" ng-model="groupStatus" >
									<option value="" hidden>----</option>
                      			 	<option value="open"><spring:message   text='Open Group'/></option>
                      			 	<option value="closed"><spring:message   text='Closed Group'/></option>
                      		  </select>

						</div>					
						</div>					
						
<!-- 						<div  class="form-group"> -->
<%-- 						<label><spring:message code='label.piginfo.generateReport.startDate'  text='Start Date'/><span style='color: red'>*</span></label>  --%>
<%-- 							<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i> --%>
<%--                       	<input type="text" required required-message="'<spring:message code='label.report.startdate.required' text='Start date is required' />'" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"/> --%>
<!-- 								</div> -->
								<div  class="form-group">
                    	<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/><span style='color: red'>*</span></label> 
							<i><spring:message code='label.piginfo.input.dateformat'  text='(in dd/mm/yyyy format)'/></i>
                      	<input type="text" required required-message="'<spring:message code='label.report.enddate.required' text='End date is required' />'" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true' name="endDate"/>
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
						
								
								<div  class="form-group">  	
					   		 <select  class="form-control"  name="reportType" id="reportType" ng-model="reportType"  style="width:90%;display:inline" >
									<!--<option value="" hidden><spring:message code='label.piginfo.groupEventForm.search.placeholder' text='Search by Group Id ...' /></option>-->
									<option value="current" ng-selected=true><spring:message code='label.piginfo.groupEventForm.currentInventory' text='Current Inventory' /></option>
									<option value="mortality" ><spring:message code='label.leftmenu.groupStatusReport.mortality' text='Mortality' /></option>
					             </select>
							 </div>
								
						<button type="submit" value="report" ng-click="generateProdEventLog()">
							<spring:message code='label.piginfo.generateReport.button'
								text='Generate Report' />
						</button>
					</div>
					<input type="hidden" name="companyId1" id="companyId1"/>
					<input type="hidden" name="selectedPremise" id="selectedPremise"/>
					
				</form>
			</div>
			<p class="text-center" ng-show="clicked">
    			<i class="fa fa-spinner fa-spin fa-3x"></i>
			</p>
			
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>