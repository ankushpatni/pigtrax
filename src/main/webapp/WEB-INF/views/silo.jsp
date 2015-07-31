<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="siloContent" ng-controller="SiloController" ng-init="getSiloList('${barnId}',${generatedBarnId})" class="container-fluid">
<form name="siloForm" method="post">
<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
				<div class="header">
							<h3><spring:message code="label.silo.header" text="Silo Data" /></h3>
						</div>
		<button type="button" ng-click="addSiloData()" class="btn btn-sm btn btn-primary">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.silo.addSiloData" text="Add New Silo" />
		</button>
		<div class="content">
						<div class="table-responsive" style="overflow-x: hidden">
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: #3399CC">
			<tr>
				<th st-sort="siloId" style="width:20%"><spring:message code="label.silo.siloID" text="Silo ID" /></th>
				<th st-sort="location" style="width:30%"><spring:message code="label.barn.location" text="Location" /></th>
				<th st-sort="location" style="width:30%"><spring:message code="label.silo.siloTypeId" text="Silo Type" /></th>
				<th style="width:20%"><spring:message code="label.premise.edit" text="Edit" /></th>
			</tr>
		 	<tr>
				<th colspan="4"><input st-search="" class="form-control" placeholder="global search ..." type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection track by $index">
				<td style="width:20%">{{row.siloId}}</td>
				<td style="width:30%">{row.location}}</td>
				<td style="width:30%">{siloType[row.siloType]}</td>
				<td style="width: 20%">
					<button type="button" class="btn btn-edit btn-xs" ng-click="editSiloData(row)">
						<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
				</td>				
			</tr>
			</tbody>		
			<tr style="background-color: #3399CC">
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