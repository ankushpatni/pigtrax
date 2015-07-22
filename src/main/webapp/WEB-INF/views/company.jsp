<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="companyContent" ng-controller="CompanyController"
	ng-init="getCompanyList()" class="container-fluid">

	<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
						<div class="header">
							<h3>Company Data</h3>
						</div>
					<button type="button" ng-click="addCompanyData()" data-modal="form-primary"  class="btn btn-primary btn-flat md-trigger">
						<i class="glyphicon glyphicon-plus"> </i>
						<spring:message code="label.company.addNewCompany"
							text="Add New company" />
					</button>
					<div class="content">
						<div class="table-responsive" style="overflow-x: hidden">
						  <form id="companyForm" method="POST">
							<table st-table="displayedCollection" st-safe-src="rowCollection"
								class="table table-striped" style="background-color: LightGray">
								<thead style="background-color: #3399CC">
									<tr>
										<th st-sort="CompanyID" size="5%"><spring:message
												code="label.company.companyID" text="Company ID" /></th>
										<th st-sort="name" size="10%"><spring:message
												code="label.company.name" text="Name" /></th>
										<th st-sort="address" size="15%"><spring:message
												code="label.company.address" text="Address" /></th>
										<th st-sort="city" size="8%"><spring:message
												code="label.company.city" text="City" /></th>
										<th st-sort="registrationNumber" size="5%"><spring:message
												code="label.company.registrationNumber"
												text="Registration Number" /></th>
										<th st-sort="email" size="8%"><spring:message
												code="label.company.email" text="Email" /></th>
										<th st-sort="phone" size="8%"><spring:message
												code="label.company.phone" text="Phone" /></th>
										<th st-sort="contactName" size="8%"><spring:message
												code="label.company.contactName" text="Contact Name" /></th>
										<th st-sort="payment" size="5%"><spring:message
												code="label.company.payment" text="Payment" /></th>
										<th st-sort="paymentDate" size="10%"><spring:message
												code="label.company.paymentDate" text="Payment Date" /></th>
										<th size="5%"><spring:message code="label.company.edit"
												text="Edit" /></th>
										<th size="15%"><spring:message code="label.company.goto"
												text="Go To" /></th>
										<th size="5%"></th>
									</tr>
									<tr>
										<th colspan="14"><input st-search="" class="form-control"
											placeholder="global search ..." type="text" /></th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="row in displayedCollection track by $index">
										<td size="5%">{{row.companyId}}</td>
										<td size="10%">{{row.name}}</td>
										<td size="15%">{{row.address}}</td>
										<td size="8%">{{row.city}}</td>
										<td size="5%">{{row.registrationNumber}}</td>
										<td size="8%">{{row.email}}</td>
										<td size="8%">{{row.phone}}</td>
										<td size="8%">{{row.contactName}}</td>
										<td size="5%">{{row.payment}}</td>
										<td size="10%">{{row.paymentDate}}</td>
										<td size="5%">
											<button type="button" class="btn btn-edit btn-xs"
												ng-click="editCompanyData(row)">
												<span class="glyphicon glyphicon-pencil"></span>
												<spring:message code="label.company.edit" text="Edit" />
												</a>
											</button>
										</td>
										<td size="15%">
											<button type="button" class="btn btn btn-info btn-sm"
												style="margin-bottom: 5px"
												ng-repeat="rt in differentPages track by $index"
												ng-click="gotToPage($index,row)">{{rt.name}}</button>
												<% if(request.isUserInRole(String.valueOf(RoleType.PigTraxSuperAdmin.getIntegerValue()))) { %>
												 <br> <br>
												<div class="btn-group">								 				
												  <button type="button" data-toggle="dropdown" class="btn-sm btn-info dropdown-toggle">Pig Events <span class="caret"></span></button>
												  <ul role="menu" class="dropdown-menu">
													<li><a href="#" ng-click="goToPigEvent('EntryEvent',row.id)">Entry Event</a></li>
													<li><a href="#" ng-click="goToPigEvent('BreedingEvent',row.id)">Breeding Event</a></li>
													<li><a ng-href="entryEvent?companyId={{row.companyId}}">Pregnancy Event</a></li>
													<li><a ng-href="entryEvent?companyId={{row.companyId}}">Farrow Event</a></li>
												  </ul>
												</div>
												<% }  %>
										</td>
										<td size="5%">
											<button ng-if="row.active" type="button"
												ng-click="removeItem(row)" class="btn btn-sm btn-danger"
												ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()">
												<a style="color: black" ng-show="hoverEdit"><spring:message
														code="label.company.deactivate" text="De-Activate" /></a> <i
													class="glyphicon glyphicon-remove-circle"> </i>
											</button>
											<button ng-hide="row.active" type="button"
												ng-click="removeItem(row)" class="btn btn-sm btn-success"
												ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()">
												<a style="color: black" ng-show="hoverEdit"><spring:message
														code="label.company.activate" text="Activate" /></a> <i
													class="glyphicon glyphicon glyphicon-ok"> </i>
											</button>
										</td>
										
									</tr>
								</tbody>
								<tr style="background-color: #3399CC">
									<td colspan="14">
										<div st-pagination="" st-items-by-page="itemsByPage"
											st-displayed-pages="totalPages"></div>
									</td>
								</tr>
							</table>
							<input type="hidden" id="selectedCompany" name="selectedCompany"/>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="resources/assets/lib/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="resources/assets/lib/jquery.nanoscroller/javascripts/jquery.nanoscroller.js"></script>
    <script type="text/javascript" src="resources/assets/js/cleanzone.js"></script>
    <script src="resources/assets/lib/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
      $(document).ready(function(){
      	//initialize the javascript
      	App.init();
      });
      
    </script>