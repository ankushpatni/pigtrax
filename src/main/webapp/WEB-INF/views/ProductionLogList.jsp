<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="companyContent" ng-controller="ProductionLogController"
	ng-init="setCompanyId(${CompanyId}, '${loggedInUser}')" class="container-fluid">
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
                  <div class="alert alert-success alert-white rounded"  ng-show="productionLogDeleted">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.productionlogform.deletemessage' text='Production log deleted successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="productionLogError">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.productionlogform.errormessage' text='An exception occurred while saving, please try again'/>
                  </div>
                  
						</div>
					<div class="row">
					<!-- <button type="button" data-toggle="modal" data-target="#addProductionLogModal"  class="btn btn-success btn-flat md-trigger">
						<i class="glyphicon glyphicon-plus"> </i>
						<spring:message code="label.productionlogform.addproductionlog"
							text="Add Production Log" />
					</button> -->
					 <div class="content">
					<div class="form-group">
                      <label class="col-sm-10 control-label"><spring:message code='label.productionlogform.selectdaterange' text='Select Date Range'/></label>
                      <div class="col-sm-10">
                        <div class="options">
                          <div class="control-group">						  
                            <div class="controls">
                              <div class="input-prepend input-group"><span class="add-on input-group-addon primary"><span class="glyphicon glyphicon-th"></span></span>
                                <input id="reservation" type="text" style="width: 200px" name="reservation"  class="form-control">
								<button type="button" class="btn btn-primary active" ng-click="searchProductionLog()"><i class="fa fa-search"></i></button>
								  <button type="button" data-toggle="modal" data-target="#addProductionLogModal"  class="btn btn-success btn-flat md-trigger pull-right" ng-click="AddProductionLog()">
						<i class="glyphicon glyphicon-plus"> </i>
						<spring:message code="label.productionlogform.addproductionlog"
							text="Add Production Log" />
					</button>
                              </div>
                            </div>
							
                          </div>
						
                        </div>
						
                      </div>
                    </div>
					
					</div>
					</div>
					</div>
					 <div class="row">
					<div class="content">
						<div class="table-responsive" style="overflow-x: hidden">
						 	<table st-table="displayedCollection" st-safe-src="rowCollection"
								class="table table-striped" style="background-color: LightGray">
								<thead style="background-color: #3399CC">
									<tr>
									   <th st-sort="roomId" ><spring:message
												code="label.productionlogform.roomId" text="Room Id" /></th>
												<th st-sort="groupId" ><spring:message
												code="label.productionlogform.groupId" text="Group Id" /></th>
										<th st-sort="eventType" ><spring:message
												code="label.productionlogform.logeventType" text="Log Event Type" /></th>
										<th st-sort="logeventType" ><spring:message
												code="label.productionlogform.observation" text="Observation" /></th>
										<th st-sort="date" ><spring:message
												code="label.productionlogform.observationDate" text="Date" /></th>		
										<th st-sort="date"><spring:message
												code="label.productionlogform.loggedon" text="Logged On" /></th>
										<th st-sort="date"><spring:message
												code="label.productionlogform.loggedby" text="Logged By" /></th>
										
										<th style="width:20px"><spring:message code="label.productionlogform.acion"
												text="Action"/></th>
									</tr>
									
								</thead>
								<tbody>
									<tr ng-repeat="row in displayedCollection track by $index">
										<td>{{row.room}}</td>										
										<td>{{row.groupId}}</td>
										<td>{{row.logEventType}}</td>
										<td><span ng-bind-html="row.observation | newline"></span></td>
										<td>{{row.observationDate}}</td>
										<td>{{row.lastUpdated}}</td>
										<td>{{row.userUpdated}}</td>
										<td>
											<button type="button" class="btn btn-edit btn-xs" data-toggle="modal" data-target="#addProductionLogModal"  ng-click="selectProductionLogForEdit(row)" ng-show="loggedInUser == row.userUpdated">
												<span class="glyphicon glyphicon-pencil"></span>
												<spring:message code="label.company.edit" text="Edit" />
												</a>
											</button>
										&nbsp; &nbsp;
										<button type="button" class="btn btn-danger btn-xs" ng-click="deleteProductionLog(row.id)" ng-show="loggedInUser == row.userUpdated" ng-confirm-click="<spring:message code='label.matingdetailsform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>">
							<spring:message code="label.piginfo.breedingeventform.delete" text="Delete" /></a></button>
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
                      <label><spring:message code='label.productionlogform.roomId'  text='Room Id'/></label>                      
                      <select class="form-control" type="text" name="roomId" ng-model="productionLog.roomId"  ng-options="k as v for (k, v) in roomMap"></select>
                    </div>                   
                     <div class="form-group">
                      <label><spring:message code='label.productionlogform.groupId'  text='Group Id'/></label>                      
                      <input name="groupId" ng-model="productionLog.groupId" class="form-control" 
                        placeholder="<spring:message code='label.productionlogform.groupId.placeholder' text='Enter group id'/>" maxlength = "30"/>                        
                    </div>
                    
                    <div class="form-group">
                      <label><spring:message code='label.productionlogform.observationDate'  text='Date'/></label>
                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7" id="observationDateDiv" >
                          <input size="16" type="date" id="observationDate" name="observationDate" ng-model="productionLog.observationDate" readonly="" class="form-control"   format-date><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div> 
                        <label ng-show="birthDateRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.piginfo.entryeventform.birthdate.requiredmessage' text='Birth Date is required' /></label>
                    </div>
                    
                    <div class="form-group">
                      <label><spring:message code='label.productionlogform.logeventType'  text='Log Event Type'/></label>
                       <select class="form-control"  name="logEventType" ng-model="productionLog.logEventTypeId" >
                      	<option ng-repeat="key in logEventKeys" ng-value="key" ng-selected="productionLog.logEventTypeId==key">{{logEventTypes[key]}}</option>        
                        </select>
                        
                    </div>
                    
                    
                     <div class="form-group">
                      <label><spring:message code='label.productionlogform.observation'  text='Observation'/><span style='color: red'>*</span></label>                      
                      <textarea name="remarks" ng-model="productionLog.observation" class="form-control" rows="10" cols="10"
                        placeholder="<spring:message code='label.productionlogform.observation.placeholder' text='Enter observation'/>" required 
                        required-message="'<spring:message code='label.productionlogform.observation.requiredmessage' text='Observation is required'/>'" maxlength = "500"></textarea>
                        
                    </div>
                    
                     </div>
                      <div class="modal-footer">
                      <button type="button" class="btn btn-success btn-flat md-close" data-dismiss="modal" ng-click="saveProductionLog()"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
                      <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                      </div>
                      
                     </div>
                     
                     <script>
$(document).ready(function(){
 var currDate = new Date();
 var dateVal = currDate.getFullYear()+"-"+(currDate.getMonth()+1)+"-"+currDate.getDate();
	  $("#observationDateDiv").attr('data-date-enddate',dateVal);
});  
</script>
            </div>

		

		
		
	</div>
	</form>
	
</div>
  