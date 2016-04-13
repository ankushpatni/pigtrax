<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head header">
          <h2><spring:message code='label.piginfo.overView.report.overView'  text='OverView Report'/></h2>
</div>
 <div class="block-flat" ng-controller="overViewController" ng-init="loadPremises(${CompanyId},'${REPORT_NO_DATA}')"  id="OverViewControllerId">
	<form method="POST" name="overViewForm" novalidate angular-validator my-reset>
	<div class="content" class="block-flat">
	<p class="color-danger" ng-show="mentaoryField"><spring:message code='label.report.search.criteria.errormessage.overView' text='Please enter mandatory fields.'/></p>
	<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.report.search.data.errormessage' text='No data found for the given criteria'/></p>
						
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
					<div class="form-group col-sm-2 col-md-2">				
						<label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise" ng-change="loadPigInfo()" >
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
									<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
						</select>
						<p class="color-danger" ng-show="prmisesSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>
	
					</div>
					<div class="form-group col-sm-2 col-md-2">
						<label><spring:message code='label.piginfo.entryeventform.name'  text='Pig Name'/></label>
						 <select  class="form-control"  name="pigId" id="pigId" ng-enter="getPigInformation()" ng-model="pigId" >
							<option value="" hidden><spring:message code='label.sowReport.search.pig' text='Search by Pig Id ...' /></option>
							<option ng-repeat="pigInfo in pigInfoList" value="{{pigInfo.id}}" ng-value="pigInfo.id" ng-selected="pigId == pigInfo.id">{{pigInfo.pigId}}</option>
						</select>
						<p class="color-danger" ng-show="pigSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>	
					</div>
					
					<div  class="form-group col-sm-2 col-md-2">			
 		   				<label><spring:message code='label.group.entryeventform.name'  text='Group Name'/></label>
						 <select  class="form-control"  name="groupId" id="groupId" ng-model="groupId" >
							<option value="" hidden><spring:message code='label.groupReport.search.group' text='Search by Group Id ...' /></option>
							<option ng-repeat="groupEventSearch in groupEventListSearch" value="{{groupEventSearch.id}}" ng-value="groupEventSearch.id" ng-selected="id == groupEventSearch.groupId">{{groupEventSearch.groupId}}</option>
						</select>
						<p class="color-danger" ng-show="groupSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
						<label><spring:message code='label.piginfo.generateReport.startDate'  text='Start Date'/></label>
						<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
						<input type="text" name="startDate" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"/>
						<p class="color-danger" ng-show="startSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>		   
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
					
						<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/></label>
						<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
						<input type="text" name="endDate" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true'/>
						<p class="color-danger" ng-show="endSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>				
					</div>	
					<div  class="form-group col-sm-2 col-md-2">			
 		   				 <!--<input type="text" name="groupId" id="groupId" class="form-control" ng-model="groupId" placeholder="<spring:message code='label.groupReport.search.group'  text='Search by Group Id ...'/>"/>-->
						 <label><spring:message code='label.groupReport.search.animal' text='Animal Type' /></label>
						 <select  class="form-control"  name="animalType" id="animalType" ng-model="animalType" >
								<option value="" hidden><spring:message code='label.groupReport.search.animal' text='Animal Type' /></option>
								<option value="group" ><spring:message code='label.groupReport.search.Grover' text='Grower' /></option>
								<option value="pig" ><spring:message code='label.groupReport.search.Pig' text='Sow' /></option>
								<option value="piglet" ><spring:message code='label.groupReport.search.PigletStatus' text='Piglet Status' /></option>
								</select>
								<p class="color-danger" ng-show="animalSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>
						</div>
					
			  </div>
			</div>
			
			<div class="row">
			<div class="text-center"><h3>Select Any One to Generate report</h3></div>
			</div>

		<div class="row">
			  <div>			  
					<div class="form-group col-sm-2 col-md-2">				
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('SowReport')" type="button"><spring:message code='label.piginfo.overView.report.SowHistory'  text='Sow History'/><br><p style="font-size:10px">Pig Name Mandatory*<p></button>
					</div>
					<div class="form-group col-sm-2 col-md-2">
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('GroupReport')" type="button"><spring:message code='label.piginfo.overView.report.GroupHistory'  text='Group History'/><br><p style="font-size:10px">Group Name Mandatory*<p></button>	
					</div>
					
					<div  class="form-group col-sm-2 col-md-2">			
 		   				<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('ActionListReport')" type="button"><spring:message code='label.piginfo.overView.report.Actionlists'  text='Action lists'/><br><p style="font-size:10px">Group Name Mandatory*<p></button>
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('InventoryStatusListReport')" type="button"><spring:message code='label.piginfo.entryeventform.InventoryStatus'  text='Inventory Status'/></button>		   
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
					<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('LacationReport')" type="button"><spring:message code='label.piginfo.entryeventform.LacationLength'  text='Lacation Length'/></button>			
					</div>	

					<div class="form-group col-sm-2 col-md-2">
					<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('Litterbalance')" type="button"><spring:message code='label.piginfo.entryeventform.Litterbalance'  text='Litter balance'/></button>	
					</div>
			  </div>
			</div>

		<div class="row">
			  <div>			  
					<div class="form-group col-sm-2 col-md-2">				
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('')" type="button"><spring:message code='label.piginfo.overView.report.SowMovement'  text='Sow Movement'/><br><p style="font-size:10px">Pig Name Mandatory*<p></button>
					</div>
					<div class="form-group col-sm-2 col-md-2">
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('FeedReport')" type="button"><spring:message code='label.piginfo.overView.report.Feed'  text='Feed'/></button>	
					</div>
					
					<div  class="form-group col-sm-2 col-md-2">			
 		   				<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('RemovalReport')" type="button"><spring:message code='label.piginfo.overView.report.Removal'  text='Removal'/></button>
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('SaleReport')" type="button"><spring:message code='label.piginfo.entryeventform.Sales'  text='Sales'/></button>		   
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
					<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('TargetReport')" type="button"><spring:message code='label.piginfo.entryeventform.Targets'  text='Targets'/></button>			
					</div>	

					<div class="form-group col-sm-2 col-md-2">
					<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="generateReport('ProductionLogReport')" type="button"><spring:message code='label.piginfo.entryeventform.ProductionLog'  text='Production Log'/></button>	
					</div>
			  </div>
			</div>

			<%-- <div class="row block-flat" >
				<div class="text-center"><h3>Performance Monitor</h3></div>
				  <div>
				
					<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
								{%>
								
					<div class="form-group col-sm-3 col-md-3">
							<label><spring:message code='label.piginfo.overView.report.overView'  text='Company'/><span style='color: red'>*</span></label>
							<select  class="form-control"  name="selectedCompany" id="selectedCompany" ng-model="selectedCompany"  ng-change="loadPremises()">
							<option value="" hidden><spring:message code='label.piginfo.Company.placeholder' text='Select Company' /></option>
								<option ng-repeat="company in companyMapList" value="{{company.id}}" ng-value="company.id" ng-selected="selectedCompany == company.id" >{{company.name}}</option>
						  </select>
					</div>
							<%}%>
					<div class="form-group col-sm-3 col-md-3">				
						<label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise" ng-change="loadPigInfo()" >
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
									<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
						</select>
						<p class="color-danger" ng-show="prmisesSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>
	
					</div>
										
					
					<div class="form-group col-sm-3 col-md-3">
						<label><spring:message code='label.piginfo.generateReport.startDate'  text='Start Date'/><span style='color: red'>*</span></label>
						<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
						<input type="text" name="startDate" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"/>
						<p class="color-danger" ng-show="startSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>		   
					</div>
					
					<div class="form-group col-sm-3 col-md-3">
					
						<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/><span style='color: red'>*</span></label>
						<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
						<input type="text" name="endDate" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true'/>
						<p class="color-danger" ng-show="endSelect"><spring:message code='label.report.search.criteria.errormessage.select' text='Please Select.'/></p>				
					</div>						
			  </div>
					<div class="form-group col-sm-12" >
						<button style="height:60px;align:center" class="btn btn-warning btn-lg" ng-click="generateReport('PerformanceMonitor')" type="button"><spring:message code='label.piginfo.entryeventform.PerformanceMonitor'  text='Performance Monitor'/></button>	
					</div>
					
			</div>
 --%>
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
