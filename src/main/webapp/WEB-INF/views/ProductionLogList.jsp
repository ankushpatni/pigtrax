<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="companyContent" ng-controller="ProductionLogController"
	ng-init="setCompanyId(${CompanyId})" class="container-fluid">
<form name="productionLogListForm" method="post" id="productionLogListForm">
	<div class="cl-mcont">
		<div class="row">
			<div class="col-md-12">
				<div class="block-flat">
						<div class="header">
							<h3><spring:message	code="label.productionlogform.productionloglist" text="Production Log" /></h3>
							
							  <div class="alert alert-success alert-white rounded"  ng-show="productionLogSaved">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.productionlogform.successmessage' text='Production log saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="productionLogError">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.productionlogform.errormessage' text='An exception occurred while saving, please try again'/>
                  </div>
                  
						</div>
					<button type="button" data-toggle="modal" data-target="#addProductionLogModal"  class="btn btn-primary btn-flat md-trigger">
						<i class="glyphicon glyphicon-plus"> </i>
						<spring:message code="label.productionlogform.addproductionlog"
							text="Add Production Log" />
					</button>
					<div class="content">
						<div class="table-responsive" style="overflow-x: hidden">
						 	<table st-table="displayedCollection" st-safe-src="rowCollection"
								class="table table-striped" style="background-color: LightGray">
								<thead style="background-color: #3399CC">
									<tr>
										<th st-sort="observation" size="5%"><spring:message
												code="label.productionlogform.observation" text="Observation" /></th>
										<th st-sort="date" size="10%"><spring:message
												code="label.productionlogform.loggedon" text="Logged On" /></th>
										
										<th style="width:20px"><spring:message code="label.productionlogform.acion"
												text="Action"/></th>
									</tr>
									
								</thead>
								<tbody>
									<tr ng-repeat="row in displayedCollection track by $index">
										<td size="5%">{{row.observation}}</td>
										<td size="10%">{{row.lastUpdated}}</td>
										<td size="10%">
											<button type="button" class="btn btn-edit btn-xs"
												>
												<span class="glyphicon glyphicon-pencil"></span>
												<spring:message code="label.company.edit" text="Edit" />
												</a>
											</button>
										&nbsp; &nbsp;
										<button type="button" class="btn btn-danger btn-xs"  ng-confirm-click="<spring:message code='label.matingdetailsform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>">
							<spring:message code="label.piginfo.breedingeventform.delete" text="Delete" /></a></button>
										</td>
										
										<td style="width:20px">
											
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
							
						</div>
					</div>
				</div>
			</div>
		</div>
		

		 <!-- - Add Production Log modal-->
		  <div id="addProductionLogModal" class="modal colored-header custom-width">
                    <div class="md-content">
                      <div class="modal-header">
                         <h3><spring:message code='label.productionlogform.add.heading'  text='Add Production Log'/></h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
                      </div>
                      <div class="modal-body form" >
                     
                  <input type=hidden name="companyId" ng-model="productionLog.companyId" value="${CompanyId}"/>
                    
                     <div class="form-group">
                      <label><spring:message code='label.productionlogform.observation'  text='Observation'/><span style='color: red'>*</span></label>                      
                      <textarea name="remarks" ng-model="productionLog.observation" class="form-control" rows="10" cols="10"
                        placeholder="<spring:message code='label.productionlogform.observation.placeholder' text='Enter observation'/>" required 
                        required-message="'<spring:message code='label.productionlogform.observation.requiredmessage' text='Observation is required'/>'"></textarea>
                        
                    </div>
                    
                     </div>
                      <div class="modal-footer">
                      <button type="button" class="btn btn-primary btn-flat md-close" data-dismiss="modal" ng-click="saveProductionLog()"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
                      <button type="button" data-dismiss="modal" class="btn btn-default btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                      </div>
                      
                     </div>
            </div>

		

		
		
	</div>
	</form>
	
</div>
  