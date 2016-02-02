<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="siloContent" ng-controller="SiloController" ng-init="getSiloList('${barnId}',${generatedBarnId},true)" class="container-fluid">
<form name="siloForm" method="post">
<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
				<div class="header">
							<h3><spring:message code="label.silo.header" text="Silo Data" /></h3>
						</div>
		<button type="button" ng-click="addSiloData()" class="btn btn-sm btn btn-success">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.silo.addSiloData" text="Add New Silo" />
		</button>
		<div class="content">
						<div class="table-responsive" >
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: #f7b781">
			<tr>
				<th st-sort="siloId" style="width:20%"><spring:message code="label.silo.siloID" text="Silo ID" /></th>
				<th st-sort="location" style="width:30%"><spring:message code="label.silo.feedline" text="Feed Line" /></th>
				<th st-sort="location" style="width:30%"><spring:message code="label.silo.siloTypeId" text="Silo Type" /></th>
				<th style="width:20%"><spring:message code="label.premise.edit" text="Edit" /></th>
				<th style="width:8%"><spring:message code="label.premise.delete" text="Delete" /></th>
			</tr>
		 	<tr>
				<th colspan="5"><input st-search="" class="form-control" placeholder="<spring:message code='label.company.globalSearch' text='Global Search ...' />" type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection track by $index">
				<td style="width:20%">{{row.siloId}}</td>
				<td style="width:30%">
				<p ng-show="row.location==1"><spring:message code='label.silo.feedline.automatic' text='Automatic' /></p>
				<p ng-show="row.location==2"><spring:message code='label.silo.feedline.individdrop' text='IndividDrop' /></p>
				<p ng-show="row.location==3"><spring:message code='label.silo.feedline.manual' text='Manual' /></p>
				</td>
				<td style="width:30%">{{siloType[row.siloTypeId]}}</td>
				<td style="width: 20%">
					<button type="button" class="btn btn-edit btn-xs" ng-click="editSiloData(row)">
						<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
				</td>		
				
				<td style="width: 8%">
				  <button type="button" class="btn btn-edit btn-xs"
												ng-click="deleteSilo(row)" ng-confirm-click="<spring:message code='label.employeegroup.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>">
									<span class="glyphicon glyphicon-remove"></span>												
					</button>
				</td>
						
			</tr>
			</tbody>		
			<tr style="background-color: #f7b781">
				<td colspan="14">
					<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPages" ></div>
				</td>
			</tr>
		</table></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="generatedSiloId" id="generatedSiloId"/>
	</form>
</div>