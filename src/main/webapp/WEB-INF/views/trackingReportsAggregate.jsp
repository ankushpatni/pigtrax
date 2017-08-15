<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head header">
          <h2><spring:message code='label.piginfo.overView.report.trackingreportaggregate'  text='Tracking Reports'/></h2>
</div>
 <div class="block-flat" ng-controller="trackingAggregatorController" ng-init="loadPremises(${CompanyId},'${REPORT_NO_DATA}')" >
	<form method="POST" name="overViewForm" novalidate angular-validator my-reset>
	<div class="content" class="block-flat">
						
		<div class="row">
			  <div>
				
					<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
								{%>
								
					<div class="form-group col-sm-2 col-md-2">
							<label><spring:message code='label.employee.company'  text='Company'/><span style='color: red'>*</span></label>
							<select  class="form-control"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"  ng-change="loadPremises()">
							<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
								<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
						  </select>
					</div>
							<%}%>
<!-- 					<div class="form-group col-sm-2 col-md-2">				 -->
<%-- 						<label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label> --%>
<%-- 						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise" ng-change="loadPigInfo()" > --%>
<%-- 								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option> --%>
<!-- 									<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option> -->
<!-- 						</select> -->
<%-- 						<p class="color-danger" ng-show="prmisesSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p> --%>
	
<!-- 					</div> -->
<!-- 					<div class="form-group col-sm-2 col-md-2"> -->
<%-- 						<label><spring:message code='label.piginfo.entryeventform.name'  text='Pig Name'/></label> --%>
<!-- 						 <select  class="form-control"  name="pigId" id="pigId" ng-enter="getPigInformation()" ng-model="pigId" > -->
<%-- 							<option value="" hidden><spring:message code='label.sowReport.search.pig' text='Search by Pig Id ...' /></option> --%>
<!-- 							<option ng-repeat="pigInfo in pigInfoList" value="{{pigInfo.id}}" ng-value="pigInfo.id" ng-selected="pigId == pigInfo.id">{{pigInfo.pigId}}</option> -->
<!-- 						</select> -->
<%-- 						<p class="color-danger" ng-show="pigSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>	 --%>
<!-- 					</div> -->
					
<!-- 					<div  class="form-group col-sm-2 col-md-2">			 -->
<%--  		   				<label><spring:message code='label.group.entryeventform.name'  text='Group Name'/></label> --%>
<!-- 						 <select  class="form-control"  name="groupId" id="groupId" ng-model="groupId" > -->
<%-- 							<option value="" hidden><spring:message code='label.groupReport.search.group' text='Search by Group Id ...' /></option> --%>
<!-- 							<option ng-repeat="groupEventSearch in groupEventListSearch" value="{{groupEventSearch.id}}" ng-value="groupEventSearch.id" ng-selected="id == groupEventSearch.groupId">{{groupEventSearch.groupId}}</option> -->
<!-- 						</select> -->
<%-- 						<p class="color-danger" ng-show="groupSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p> --%>
<!-- 					</div> -->
					
<!-- 					<div class="form-group col-sm-2 col-md-2"> -->
<%-- 						<label><spring:message code='label.piginfo.generateReport.startDate'  text='Start Date'/></label><br> --%>
<%-- 						<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i> --%>
<!-- 						<input type="text" name="startDate" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"/> -->
<%-- 						<p class="color-danger" ng-show="startSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>		    --%>
<!-- 					</div> -->
					
<!-- 					<div class="form-group col-sm-2 col-md-2"> -->
					
<%-- 						<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/></label><br> --%>
<%-- 						<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i> --%>
<!-- 						<input type="text" name="endDate" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true'/> -->
<%-- 						<p class="color-danger" ng-show="endSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>				 --%>
<!-- 					</div>	 -->
<!-- 					<div  class="form-group col-sm-2 col-md-2">			 -->
<!--  		   				 <input type="text" name="groupId" id="groupId" class="form-control" ng-model="groupId" placeholder="<spring:message code='label.groupReport.search.group'  text='Search by Group Id ...'/>"/> -->
<%-- 						 <label><spring:message code='label.groupReport.search.animal' text='Animal Type' /></label> --%>
<!-- 						 <select  class="form-control"  name="animalType" id="animalType" ng-model="animalType" > -->
<%-- 								<option value="" hidden><spring:message code='label.groupReport.search.animal' text='Animal Type' /></option> --%>
<%-- 								<option value="group" ><spring:message code='label.groupReport.search.Grover' text='Grower' /></option> --%>
<%-- 								<option value="pig" ><spring:message code='label.groupReport.search.Pig' text='Sow' /></option> --%>
<%-- 								<option value="piglet" ><spring:message code='label.groupReport.search.PigletStatus' text='Piglet Status' /></option> --%>
<!-- 								</select> -->
<%-- 								<p class="color-danger" ng-show="animalSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p> --%>
<!-- 						</div> -->
					
			  </div>
			</div>
			
			<div class="row">
			<div class="text-center"><h3><spring:message code='label.groupReport.selection.msg' text='Select Any One to Generate report' /></h3></div>
			</div>

		<div class="row">
			  <div>			  
					<div class="form-group col-sm-2 col-md-3">				
						<button style="height:60px;width:220px" class="btn btn-warning btn-lg" ng-click="redirectMortality()" type="button"><spring:message code="label.leftmenu.Charts.pigletMortalityReport"  text="Piglet Mortality Tracking Report"/></button>
					</div>
					<div class="form-group col-sm-2 col-md-3">
						<button style="height:60px;width:220px" class="btn btn-warning btn-lg" ng-click="redirectGestation()" type="button"><spring:message code="label.leftmenu.reports.gestationReport"  text="Gestation Tracking Report"/></button>	
					</div>
					
					<div  class="form-group col-sm-2 col-md-3">			
 		   				<button style="height:60px;width:220px" class="btn btn-warning btn-lg" ng-click="redirectGroupStatus()" type="button"><spring:message code="label.leftmenu.reports.groupStatusReport"  text="Group Status Report"/></button>
					</div>
					
					<div class="form-group col-sm-2 col-md-3">
						<button style="height:60px;width:220px" class="btn btn-warning btn-lg" ng-click="redirectRation()" type="button"><spring:message code="label.leftmenu.reports.rationReport"  text="Feed Budget Tracking Report"/></button>		   
					</div>
					
			  </div>
			</div>



			<div class="row">
			  <div>	
			  </div>

			<input type="hidden" name="companyId1" id="companyId1"/>		  
			<input type="hidden" name="fromOverView" id="fromOverView" value="true"/>
			<input type="hidden" name="reportType" id="reportType" />		  
		</div>
	</div>
	</form>
</div>
