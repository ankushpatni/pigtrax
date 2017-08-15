<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="penContent" ng-controller="PenController" ng-init="getPenList('${roomId}',${generatedRoomId})" class="container-fluid">
<form name="penForm" method="post">
<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
				<div class="header">
							<h3><spring:message code="label.pen.header" text="Pen Data" /></h3>
						</div>
		<button type="button" ng-click="addPenData()" class="btn btn-sm btn btn-success">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.pen.addPenDataButton" text="Add New Pen" />
		</button>
		<div class="content">
						<div class="table-responsive" >
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: #f7b781">
			<tr>
				<th st-sort="roomId" style="width:20%"><spring:message code="label.pen.penID" text="Pen ID"/></th>
				<th st-sort="location" style="width:30%"><spring:message code="label.barn.location" text="Location" /></th>
				<th style="width:20%"><spring:message code="label.premise.edit" text="Edit" /></th>
				<th style="width:20px"><spring:message code="label.company.activateDeactivate"
												text="Status: Active/ Inactive"/></th>
			</tr>
		 	<tr>
				<th colspan="4"><input st-search="" class="form-control" placeholder="global search ..." type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection track by $index">
				<td style="width:20%">{{row.penId}}</td>
				<td style="width:30%">
				 <p ng-show="row.location=='doorway'"><spring:message code="label.pen.location.doorway" text="Doorway" /></p>
				 <p ng-show="row.location=='interior'"><spring:message code="label.pen.location.doorway" text="Interior" /></p>
				 <p ng-show="row.location=='exterior'"><spring:message code="label.pen.location.doorway" text="Exterior" /></p>
				 &nbsp;
				</td>
				<td style="width: 20%">
					<button type="button" class="btn btn-edit btn-xs" ng-click="editPenData(row)">
						<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
				</td>
				<td style="width:20px">
				<button ng-hide="row.active" type="button" ng-click="removeItem(row)" class="btn btn-sm btn-danger" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()"><a style="color:black" ng-show="hoverEdit"><spring:message code="label.company.deactivate" text="De-Activate" /></a>
					<i class="glyphicon glyphicon-remove-circle">
					</i>
				</button>
				<button ng-show="row.active" type="button" ng-click="removeItem(row)" class="btn btn-sm btn-success" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()"><a style="color:black" ng-show="hoverEdit"><spring:message code="label.company.activate" text="Activate" /></a>
					<i class="glyphicon glyphicon glyphicon-ok">
					</i>
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
	<input type="hidden" name="generatedRoomId" id="generatedRoomId"/>
	</form>
</div>