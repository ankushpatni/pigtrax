<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head header">
          <h2><spring:message code='label.piginfo.overView.report.overView'  text='OverView Report'/></h2>
</div>
 <div class="block-flat" ng-controller="overViewController" ng-init="loadPremises(${CompanyId},'${REPORT_NO_DATA}')"  id="OverViewControllerId">
	<form method="POST" name="overViewForm" novalidate angular-validator my-reset>
	<div class="content" class="block-flat">
	<p class="color-danger" ng-show="mentaoryField"><spring:message code='label.report.search.criteria.errormessage' text='Please enter mandatory fields.'/></p>
	<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.report.search.data.errormessage' text='No data found for the given criteria'/></p>
						
		<div class="row">
			  <div>
				
					<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()))
								{%>
								
					<div class="form-group col-sm-2 col-md-2">
							<label><spring:message code='label.piginfo.overView.report.overView'  text='Company'/><span style='color: red'>*</span></label>
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
					</div>
					<div class="form-group col-sm-2 col-md-2">
						<label><spring:message code='label.piginfo.entryeventform.name'  text='Pig Name'/></label>
						 <select  class="form-control"  name="pigId" id="pigId" ng-enter="getPigInformation()" ng-model="pigId" >
							<option value="" hidden><spring:message code='label.sowReport.search.pig' text='Search by Pig Id ...' /></option>
							<option ng-repeat="pigInfo in pigInfoList" value="{{pigInfo.pigId}}" ng-value="pigInfo.pigId" ng-selected="pigId == pigInfo.pigId">{{pigInfo.pigId}}</option>
						</select>	
					</div>
					
					<div  class="form-group col-sm-2 col-md-2">			
 		   				<label><spring:message code='label.group.entryeventform.name'  text='Group Name'/></label>
						 <select  class="form-control"  name="groupId" id="groupId" ng-model="groupId" >
							<option value="" hidden><spring:message code='label.groupReport.search.group' text='Search by Group Id ...' /></option>
							<option ng-repeat="groupEventSearch in groupEventListSearch" value="{{groupEventSearch.groupId}}" ng-value="groupEventSearch.groupId" ng-selected="groupId == groupEventSearch.groupId">{{groupEventSearch.groupId}}</option>
						</select>
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
						<label><spring:message code='label.piginfo.generateReport.startDate'  text='Start Date'/><span style='color: red'>*</span></label>
						<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
						<input type="text" name="startDate" class="form-control" ng-model="startDate" mask="39/19/2999" mask-validate='true' name="startDate"/>
								   
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
					
						<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/><span style='color: red'>*</span></label>
						<i><spring:message code='label.piginfo.input.dateformat'  text='(in mm/dd/yyyy format)'/></i>
						<input type="text" name="endDate" class="form-control" ng-model="endDate" mask="39/19/2999" mask-validate='true'/>				
					</div>	

					
			  </div>
			</div>
			
			<div class="row">
			<div class="text-center"><h3>Select Any One to Generate report</h3></div>
			</div>

		<div class="row">
			  <div>			  
					<div class="form-group col-sm-2 col-md-2">				
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchSowHistory('SowHistory')" type="button"><spring:message code='label.piginfo.overView.report.SowHistory'  text='Sow History'/><br><p style="font-size:10px">Pig Name Mandatory*<p></button>
					</div>
					<div class="form-group col-sm-2 col-md-2">
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchGroupHistory('GroupHistory')" type="button"><spring:message code='label.piginfo.overView.report.GroupHistory'  text='Group History'/><br><p style="font-size:10px">Group Name Mandatory*<p></button>	
					</div>
					
					<div  class="form-group col-sm-2 col-md-2">			
 		   				<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.overView.report.Actionlists'  text='Action lists'/><br><p style="font-size:10px">Group Name Mandatory*<p></button>
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.entryeventform.InventoryStatus'  text='Inventory Status'/></button>		   
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
					<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.entryeventform.LacationLength'  text='Lacation Length'/></button>			
					</div>	

					<div class="form-group col-sm-2 col-md-2">
					<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.entryeventform.Litterbalance'  text='Litter balance'/></button>	
					</div>
			  </div>
			</div>

		<div class="row">
			  <div>			  
					<div class="form-group col-sm-2 col-md-2">				
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.overView.report.SowMovement'  text='Sow Movement'/><br><p style="font-size:10px">Pig Name Mandatory*<p></button>
					</div>
					<div class="form-group col-sm-2 col-md-2">
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.overView.report.Feed'  text='Feed'/></button>	
					</div>
					
					<div  class="form-group col-sm-2 col-md-2">			
 		   				<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.overView.report.Removal'  text='Removal'/></button>
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
						<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.entryeventform.Sales'  text='Sales'/></button>		   
					</div>
					
					<div class="form-group col-sm-2 col-md-2">
					<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.entryeventform.Targets'  text='Targets'/></button>			
					</div>	

					<div class="form-group col-sm-2 col-md-2">
					<button style="height:60px;width:150px" class="btn btn-warning btn-lg" ng-click="searchPigInfo()" type="button"><spring:message code='label.piginfo.entryeventform.ProductionLog'  text='Production Log'/></button>	
					</div>
			  </div>
			</div>

			<div class="row">
			  <div>
		  
							
			  </div>

			<input type="hidden" name="companyId1" id="companyId1"/>		  
			<input type="hidden" name="fromOverView" id="fromOverView" value="true"/>		  
		</div>
	</div>
	</form>
</div>
