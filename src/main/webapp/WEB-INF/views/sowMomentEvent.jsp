<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.groupEventForm.pigletinformation'  text='Piglet Information'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="SowMovementController" ng-init="setCompanyId('${CompanyId}')">
   <div class="row">
	 		  <div class="col-sm-3 col-md-3"></div> 
	 		  <div class="col-sm-6 col-md-6">
		 		  <div class="block-flat">
					  <form name="groupEventSearchForm" >
			 		     <div class="head">
			            	<h3> <spring:message code='label.piginfo.sowMovementForm.search.heading'  text='Search Sow Movement'/></h3>
			               	<p class="color-danger" ng-show="searchDataErrorMessage"><spring:message code='label.piginfo.sowMovementForm.search.data.errormessage' text='Sow Movement information not found for the search criteria'/></p>
			               
           					<div  class="form-group">  	
					   		 <input type="text" name="search"  ng-enter="getGroupEventInformation(searchText, selectedPremise)" ng-model="searchText" ng-pattern="/^[a-z0-9]+$/i"
								invalid-message="'<spring:message code='label.piginfo.sowMovementForm.invalidMessage' text='Only Alpha Numeric values are allowed' />'" placeholder="<spring:message code='label.piginfo.sowMovementForm.search.placeholder'  text='Search by Pig Info Id ...'/>" class="form-control" style="width:90%;display:inline">
							 <button type="button" class="btn btn-primary active" ng-click="getSowMovement(searchText)"><i class="fa fa-search"></i></button>
							 </div>
			          	</div>
					  </form>	
				 </div>
	          </div>
	 		  <div class="col-sm-3 col-md-3"></div>
	  </div>			
 		
 	<form name="groupEventFormAdd" method="post">	
		<div class="content" ng-show="sowMovementSuccessMessage">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection" st-safe-src="sowMovementList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>
						<th style="width:5%"><spring:message code="label.groupEventDetail.number" text="Number" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.barn" text="Barn" /></th>
						<th style="width:10%"><spring:message code="label.groupEventDetail.dateOfEntry" text="Date Of Entry" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.numberOfPigs" text="Number Of Pigs" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.weightInKgs" text="Weight In Kgs" /></th>
						<th style="width:7%"><spring:message code="label.groupEventDetail.roomId" text="Room" /></th>
						<th style="width:35%"><spring:message code="label.groupEventDetail.remarks" text="Remarks" /></th>
						<th style="width:35%"><spring:message code="label.groupEventDetail.transferredFromGroup" text="From Group" /></th>
						<th style="width:5%"><spring:message code="label.groupEventDetail.edit" text="Edit" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:5%">{{$index+1}}</td>
					<td style="width:7%">{{barnList[row.barnId]}}</td>
					<td style="width:10%">{{DateUtils.getFormatedDate(row.dateOfEntry)}}</td>
					<td style="width:7%">{{row.numberOfPigs}}</td>
					<td style="width:7%">{{row.weightInKgs}}</td>
					<td style="width:7%">{{roomList[row.roomId]}}</td>
					<td style="width:35%">{{row.remarks}}</td>
					<td style="width:35%">{{row.fromGroupIdStr}}</td>
					<td style="width: 5%">
						<button type="button" class="btn btn-edit btn-xs" ng-click="editSowMovement">
							<span class="glyphicon glyphicon-pencil" ></span><spring:message code="label.company.edit" text="Edit" /></a></button>					
					</td>				
				</tr>
				</tbody>		
				<tr style="background-color: #f7b781">
					<td colspan="14">
						<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="totalPages" ></div>
					</td>
				</tr>
			</table>
			</div>
		</div>
		
		<input type="hidden" name="companyId" id="companyId"/>	
	</form>
	
	<div class="md-overlay"></div>
</div>	

