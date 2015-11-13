<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.rationform.topheading'  text='Rations'/> </h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="RationController" ng-init="getFeedTypes()">
 
          <div class="row">
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.rationform.heading'  text='Manage Ration'/></h3>
                  
                   <div class="alert alert-success alert-white rounded"  ng-show="rationSaved">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.rationform.submit.success' text='Ration details saved successfully'/>
                  </div>   
                  <div class="alert alert-success alert-white rounded"  ng-show="duplicateOrigin">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.rationform.duplicateorigin' text='Origin already exists'/>
                  </div>               
                   <div class="alert alert-success alert-white rounded"  ng-show="rationValueDeleted">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.rationform.delete.success' text='Ration details deleted successfully'/>
                  </div>
                  
                </div>
                <div class="content">
                  <form name="rationform">
                                        
                     
                    <div class="form-group">
                      <label><spring:message code='label.rationform.ration'  text='Ration'/><span style='color: red'>*</span></label>
                      <input type="text" maxlength="30" name="origin" ng-model="masterRation.rationValue" class="form-control" placeholder="<spring:message code='label.rationform.ration.placegolder' text='Enter Ration'/>"
                      ></input>
                      <label ng-show="rationRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.rationform.rationRequiredMessage' text='Ration is required' /></label>
                    </div>
                    
                    <div class="form-group">
                      <label><spring:message code='label.rationform.feedType'  text='Feed Type'/></label>
                      
                      <select class="form-control"   required required-message="'<spring:message code='label.rationform.feedType.required' text='Feed Type is required' />'" name="feedTypeId" id="feedTypeId" ng-model="masterRation.feedTypeId">
                      	<option ng-repeat="key in feedTypeKeys" ng-value="key" ng-selected="masterRation.feedTypeId==key">{{feedTypeKeyValues[key]}}</option>        
                        </select>             
                    </div> 
                    
                    <button class="btn btn-primary" ng-click="saveRation()" type="submit"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
                    <button class="btn btn-default" ng-click="resetForm()" type="button"><spring:message code='label.piginfo.entryeventform.cancel'  text='Clear Form'/></button>                    
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
    
	<form name="origindetailsform" method="post">	
		<div class="content" ng-show="rationList != null && rationList.length > 0 ">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection" st-safe-src="rationList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #3399CC">
					<tr>
						<th style="width:20%"><spring:message code="label.rationform.ration" text="Ration" /></th>
						<th style="width:20%"><spring:message code="label.rationform.feedType" text="Feed Type" /></th>
						<th style="width:20%"><spring:message code="label.rationform.createdOn" text="Created On" /></th>
						<th style="width:20%"><spring:message code="label.rationform.createdBy" text="Created By" /></th>
						<th style="width:20%"><spring:message code="label.companytargetform.action" text="Action" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:20%">{{row.rationValue}}</td>
					<td style="width:20%">{{row.feedType}}</td>
					<td style="width:20%">{{row.lastUpdated|  date : 'yyyy-MM-dd'}}</td>
					<td style="width:20%">{{row.userUpdated}}</td>										
					<td style="width:20%">						
					<button type="button" class="btn btn-edit btn-xs"
							ng-click="deleteRation(row.id)" ng-confirm-click="<spring:message code='label.rationform.delete.confirmmessage'  text='Are you sure you want to delete the ration? On delete this value will be removed from feed events'/>">
							<span class="glyphicon glyphicon-remove"></span>												
						</button>
					</td>				
				</tr>
				</tbody>		
				<tr style="background-color: #3399CC">
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


