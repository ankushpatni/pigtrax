<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message	code='label.piginfo.generateReport.heading'
								text='Generate Report' /></h2>
</div>
<div class="cl-mcont" id="sowReposrtGeneraterControllerId" ng-controller="sowReportController" ng-init="loadPremises('${CompanyId}')" class="container-fluid">
<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" novalidate angular-validator  action="generateReportSow" name="generateReportSow">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.piginfo.generateReport.heading'
								text='Generate Report' />
						</h3>
						<div  class="form-group">
						<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.entryeventform.search.data.errormessage' text='Pig Information not found for the search criteria'/></p>
						<select  class="form-control"  required required-message="'<spring:message code='label.premise.premiseNameRequired' text='label.premise.premiseNameRequired' />'"  name="selectedPremise" id="selectedPremise" ng-model="selectedPremise"  >
								<option value="" hidden><spring:message code='label.piginfo.premise.placeholder' text='Select premise' /></option>
                      			 	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="selectedPremise == premise.id">{{premise.name}}</option>
                      		  </select>
						</div>
						<div  class="form-group">			
 		   				 <input type="text" name="search" id="search" ng-enter="getPigInformation()" required required-message="'<spring:message code='label.piginfo.entryeventform.pigid.requiredmessage' text='Pig Id is required' />'" class="form-control" ng-model="searchText" placeholder="<spring:message code='label.sowReport.search.pig'  text='Search by Pig Id ...'/>"/>
						</div>
                    	
						<button type="button" value="report" ng-click="searchPigInfo()">
							<spring:message code='label.piginfo.generateReport.button'
								text='Generate Report' />
						</button>
					</div>
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>