 <!-- - Add Production Log modal-->
		  <div id="addProductionLogModal" class="modal colored-header custom-width">
		   <form name="productionlogform">
                    <div class="md-content">
                      <div class="modal-header">
                         <h3><spring:message code='label.productionlogform.add.heading'  text='Add Production Log'/></h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
                      </div>
                      <div class="modal-body form" >
                     
                  <input type=hidden name="companyId" ng-model="productionLog.companyId" value="${CompanyId}"/>
                    
                     <div class="form-group">
                      <label><spring:message code='label.productionlogform.observation'  text='Observation'/><span style='color: red'>*</span></label>                      
                      <textarea name="remarks" ng-model="productionLog.observation" class="form-control" placeholder="<spring:message code='label.productionlogform.observation.placeholder' text='Enter remarks'/>" required required-message="'<spring:message code='label.piginfo.entryeventform.remarks.requiredmessage' text='Remarks is required'/>'"></textarea>
                        <label ng-show="targetIdRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.companytargetform.targetIdRequiredMessage' text='Target Id is required' /></label>
                    </div>
                        </div>
                      <div class="modal-footer">
                      <button type="button" class="btn btn-success btn-flat md-close"  ng-click="saveProductionLog()"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
                      <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                      </div>
                      
                     </div>
                     </form>
            </div>




<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.companytargetform.heading'  text='Targets'/> - ${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="ProductionLogController">
 
          <div class="row">
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.productionlogform.heading'  text='Production Log'/></h3>
                  
                   <div class="alert alert-success alert-white rounded"  ng-show="productionLogSaved">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.productionlogform.successmessage' text='Production log saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="productionLogError">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.productionlogform.errormessage' text='An exception occurred while saving, please try again'/>
                  </div>
                  
                </div>
                <div class="content">
                  <form name="productionlogform">
                  <input type=hidden name="companyId" ng-model="productionLog.companyId" value="${CompanyId}"/>
                    
                     <div class="form-group">
                      <label><spring:message code='label.productionlogform.observation'  text='Observation'/><span style='color: red'>*</span></label>                      
                      <textarea name="remarks" ng-model="productionLog.observation" class="form-control" placeholder="<spring:message code='label.productionlogform.observation.placeholder' text='Enter remarks'/>" required required-message="'<spring:message code='label.piginfo.entryeventform.remarks.requiredmessage' text='Remarks is required'/>'"></textarea>
                        <label ng-show="targetIdRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.companytargetform.targetIdRequiredMessage' text='Target Id is required' /></label>
                    </div>
                    
                    <button class="btn btn-success" ng-click="saveProductionLog()" type="submit"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-warning" ng-click="resetForm()" type="button"><spring:message code='label.piginfo.entryeventform.cancel'  text='Clear Form'/></button>                    
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
          
          
          <!-- -- MAting details -->
             <div class="row" >
		 
            <div class="col-sm-12 col-md-12">
              <div class="block-flat">
    
	<form name="matingdetailsform" method="post">	
		<div class="content" ng-show="(companyTargets != null && companyTargets.length > 0) ">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection" st-safe-src="companyTargets" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>
						<th style="width:20%"><spring:message code="label.companytargetform.targetname" text="Target" /></th>
						<th style="width:20%"><spring:message code="label.companytargetform.targetvalue" text="Value" /></th>
						<th style="width:20%"><spring:message code="label.companytargetform.completiondate" text="Completion Date" /></th>
						<th style="width:20%"><spring:message code="label.companytargetform.remarks" text="Remarks" /></th>
						<th style="width:20%"><spring:message code="label.companytargetform.action" text="Action" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:20%">{{row.targetName}}</td>
					<td style="width:20%">{{row.targetValue}}</td>					
					<td style="width:20%">{{row.completionDate|  date : 'yyyy-MM-dd'}}</td>
					<td style="width:20%">{{row.remarks}}</td>
					<td style="width:20%">
					<button type="button" class="btn btn-edit btn-xs"
							ng-click="editCompanyTarget(row)">
							<span class="glyphicon glyphicon-pencil"></span>
							<spring:message code="label.employeegroup.list.edit" text="Edit" />												
						</button>
							&nbsp;				
					<button type="button" class="btn btn-edit btn-xs"
							ng-click="deleteTargetDetails(row)" ng-confirm-click="<spring:message code='label.matingdetailsform.delete.confirmmessage'  text='Are you sure you want to delete the entry?'/>">
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
			</table>
			</div>
		</div>
	</form>	 
     </div>             
      </div>        
      </div>          
	  
</div>


