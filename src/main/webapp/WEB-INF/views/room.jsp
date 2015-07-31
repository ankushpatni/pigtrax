<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="roomContent" ng-controller="RoomController" ng-init="getRoomList('${barnId}',${generatedBarnId})" class="container-fluid">
<form name="roomForm" method="post">
<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
				<div class="header">
							<h3><spring:message code="label.room.header" text="Room Data" /></h3>
						</div>
		<button type="button" ng-click="addRoomData()" class="btn btn-sm btn btn-primary">
			<i class="glyphicon glyphicon-plus">
			</i> <spring:message code="label.room.addRoomData" text="Add New Room" />
		</button>
		<div class="content">
						<div class="table-responsive" style="overflow-x: hidden">
		<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped" style="background-color: LightGray">  
			<thead style="background-color: #3399CC">
			<tr>
				<th st-sort="roomId" style="width:20%"><spring:message code="label.room.roomID" text="Room ID" /></th>
				<th st-sort="location" style="width:30%"><spring:message code="label.barn.location" text="Location" /></th>
				<th style="width:20%"><spring:message code="label.premise.edit" text="Edit" /></th>
				<th style="width:20%"><spring:message code="label.company.goto" text="Go To" /></th>
				<th style="width:20px"></th>
			</tr>
		 	<tr>
				<th colspan="5"><input st-search="" class="form-control" placeholder="global search ..." type="text"/></th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="row in displayedCollection track by $index">
				<td style="width:20%">{{row.roomId}}</td>
				<td style="width:30%">{{row.location}}</td>
				<td style="width: 20%">
					<button type="button" class="btn btn-edit btn-xs" ng-click="editRoomData(row)">
						<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
				</td>
				<td style="width:20%">  
					<button type="button" class="btn btn btn-info btn-sm" style="margin-bottom:5px" ng-repeat ="rt in differentPages track by $index" ng-click="gotToPage($index,row)">
						{{rt.name}}</button>	
				</td>
				<td style="width:20px">
				<button ng-if="row.active" type="button" ng-click="removeItem(row)" class="btn btn-sm btn-danger" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()"><a style="color:black" ng-show="hoverEdit"><spring:message code="label.company.deactivate" text="De-Activate" /></a>
					<i class="glyphicon glyphicon-remove-circle">
					</i>
				</button>
				<button ng-hide="row.active" type="button" ng-click="removeItem(row)" class="btn btn-sm btn-success" ng-mouseover="hoverIn()" ng-mouseleave="hoverOut()"><a style="color:black" ng-show="hoverEdit"><spring:message code="label.company.activate" text="Activate" /></a>
					<i class="glyphicon glyphicon glyphicon-ok">
					</i>
				</button>
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
	<input type="hidden" name="generatedRoomId" id="generatedRoomId"/>
	</form>
</div>