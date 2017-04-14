<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.leftmenu.reports.performanceMonitoring'
								text='Generate Report' /></h2>
</div>
<div class="cl-mcont" id="reposrtGeneraterControllerId" ng-controller="GroupPerformanceReportController" ng-init="loadPremises('${CompanyId}')">
	<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" action="generateGroupPerformanceReport" name="generateGroupPerformanceReport">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.leftmenu.reports.groupperformanceMonitoring'
								text='Generate Group Performance Report' />
						</h3>
						<p class="color-danger" ng-show="targetListEmpty"><spring:message  text='No Farms associated with sow source'/></p>
						
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
						<label><spring:message code='label.premise.sowSource'  text='Sow Source'/><span style='color: red'>*</span></label>
						<select  class="form-control"  ng-change="getPremisesFromSowSource('${CompanyId}')"  required required-message="'<spring:message code='label.premise.sowSourceYes.requiredMessage' text='Sow source is required' />'"  name="selectedSowSource" id="selectedSowSource" ng-model="selectedSowSource" >
								<option value="" hidden>----</option>
                      			 	<option ng-repeat="premise in sowSourceList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedSowSource == premise.id">{{premise.name}}</option>
                      			 	<option value="-1"><spring:message code='label.pigtrax.all.txt'  text='All'/></option>
                      		  </select>
                      		  	
						</div>	
						
<!-- 						<div class="form-group col-md-3"> -->
<%-- 						<label><spring:message code='label.premise.sowSource'  text='Sow Source'/><span style='color: red'>*</span></label> --%>
<!--                       		  <div ng-dropdown-multiselect="" options="premiseValues" selected-model="selectedPremises"  extra-settings="multiselectdropdownsettings"></div>	 -->
<!-- 						</div>					 -->
													
						<div  class="form-group">
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  >
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
                    	<div class="form-group">
                      		<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/><span style='color: red'>*</span></label>
                      		<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
                      			<input type="text" name="endDate" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true'/>
							
		                      <!-- <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
		                          <input size="16" type="date" name="endDate" readonly="" class="form-control" format-date/><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
								</div> -->
                    	</div>
                    	
<!--                     	<div class="form-group"> -->
<%--                       		<label><spring:message code='label.piginfo.generateReport.numberOfWeeks'  text='Number of Weeks'/><span style='color: red'>*</span></label> --%>
<!--                       			<input type="number" name="numberOfWeeks" class="form-control" ng-model="numberOfWeeks" step="1"/> -->
<!-- 								<input type="hidden" name="startDate" class="form-control" ng-model="startDate"/> -->
<!-- 		                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
<!-- 		                          <input size="16" type="date" name="startDate" readonly="" class="form-control" format-date/><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span> -->
<!-- 								</div> --> 
<!--                     	</div> -->
                    	
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
                    	
                    	
                    	 <div  class="form-group">  	
					   		 <select  class="form-control"  name="status" id="status" ng-model="status" >
									<!--<option value="" hidden><spring:message code='label.piginfo.groupEventForm.search.placeholder' text='Search by Group Id ...' /></option>-->
									<option value="active" ng-selected=true><spring:message code='label.piginfo.groupEventForm.search.active' text='Active groups' /></option>
									<option value="closed" ><spring:message code='label.piginfo.groupEventForm.search.close' text='Closed groups' /></option>
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