<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.originform.topheading'  text='Genetic Origin'/> </h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="OriginController" ng-init="getOriginList()">
 
          <div class="row">
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.originform.heading'  text='Manage Origin'/></h3>
                  
                   <div class="alert alert-success alert-white rounded"  ng-show="originSaved">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.originform.submit.success' text='Origin value saved successfully'/>
                  </div>   
                  <div class="alert alert-success alert-white rounded"  ng-show="duplicateOrigin">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.originform.duplicateorigin' text='Origin already exists'/>
                  </div>               
                   <div class="alert alert-success alert-white rounded"  ng-show="originValueDeleted">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.originform.delete.success' text='Origin value deleted successfully'/>
                  </div>
                  
                </div>
                <div class="content">
                  <form name="originform">
                                        
                     
                    <div class="form-group">
                      <label><spring:message code='label.originform.origin'  text='Origin'/><span style='color: red'>*</span></label>
                      <input type="text" maxlength="30" name="origin" ng-model="origin.name" class="form-control" placeholder="<spring:message code='label.originform.origin.placegolder' text='Enter origin'/>"
                      ></input>
                      <label ng-show="originRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.originform.originValueRequiredMessage' text='Origin is required' /></label>
                    </div>
                    
                    <button class="btn btn-success" ng-click="saveOrigin()" type="submit"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
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
    
	<form name="origindetailsform" method="post">	
		<div class="content" ng-show="originList != null && originList.length > 0) ">
			<div class="table-responsive" style="overflow-x: hidden">
			<table st-table="displayedCollection" st-safe-src="originList" class="table table-striped" style="background-color: LightGray">  
				<thead style="background-color: #f7b781">
					<tr>
						<th style="width:20%"><spring:message code="label.originform.origin" text="Origin" /></th>
						<th style="width:20%"><spring:message code="label.originform.createdOn" text="Created On" /></th>
						<th style="width:20%"><spring:message code="label.originform.createdBy" text="Created By" /></th>
						<th style="width:20%"><spring:message code="label.companytargetform.action" text="Action" /></th>
					</tr>
					<tr>
						<th colspan="4"><input st-search="" class="form-control"
							placeholder="<spring:message code='label.company.globalSearch' text='Global Search ...' />" type="text" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:20%">{{row.name}}</td>
					<td style="width:20%">{{DateUtils.getFormatedDate(row.lastUpdated)}}</td>
					<td style="width:20%">{{row.userUpdated}}</td>										
					<td style="width:20%">						
					<button type="button" class="btn btn-edit btn-xs"
							ng-click="deleteOrigin(row.id)" ng-confirm-click="<spring:message code='label.originform.delete.confirmmessage'  text='Are you sure you want to delete the origin? On delete this value will be removed from entry events'/>">
							<span class="glyphicon glyphicon-remove"></span><spring:message code="label.transportDestination.delete" text="Delete" /></span>												
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


