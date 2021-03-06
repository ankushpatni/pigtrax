<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2>${CompanyName}</h2>
        </div>
		 
 <div class="cl-mcont" ng-controller="CompanyTargetController" ng-init="setCompanyId(${CompanyId})" id="EntryEventControllerId">
 
          <div class="row">
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.companytargetform.heading'  text='Targets'/></h3>
                  
                   <div class="alert alert-success alert-white rounded"  ng-show="companyTargetSaved">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.companytargetform.submit.success' text='Target details saved successfully'/>
                  </div>
                  <div class="alert alert-danger alert-white rounded" ng-show="duplicateCompanyTarget">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-times-circle"></i></div><spring:message code='label.companytargetform.submit.duplicate' text='Details already added for the selected target for the selected year'/>
                  </div>
                   <div class="alert alert-success alert-white rounded"  ng-show="companyTargetDeleted">
                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">�</button>
                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.companytargetform.delete.success' text='Target details deleted successfully'/>
                  </div>
                  
                </div>
                <div class="content">
                  <form name="companytargetform">
                  <input type=hidden name="companyId" ng-model="companyTarget.companyId" value="${CompanyId}"/>
                  
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/><span style='color: red'>*</span></label>
                       <select class="form-control"   ng-change="getCompanyTargets()" name="premiseId" id="premiseId" ng-model="companyTarget.premiseId"  required required-message="'<spring:message code='label.piginfo.farroweventform.premise.requiredmessage' text='Premise is required' />'">
                       	<option ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="companyTarget.premiseId == premise.id">{{premise.name}}</option>
                        </select>
                    </div>	
                  
                     <div class="form-group">
                      <label><spring:message code='label.companytargetform.targetname'  text='Target'/><span style='color: red'>*</span></label>                      
                      <select class="form-control" id="targetId"  name="targetName" ng-model="companyTarget.targetId" ng-if="companyTarget.id == null" ng-change="checkTargetType()" >
                      <option ng-repeat="key in usedTargetKeys" ng-value="key">{{targetTypes[key]}}</option>                          
                        </select>
                        <label ng-if="companyTarget.id != null && companyTarget.id > 0">{{companyTarget.targetName}}</label>
                        <label ng-show="targetIdRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.companytargetform.targetIdRequiredMessage' text='Target Id is required' /></label>
                    </div>
                    
                     
                    <div class="form-group">
                      <label><spring:message code='label.companytargetform.targetvalue'  text='Value'/><span style='color: red'>*</span></label>
                      <input type="text" maxlength="30" name="targetValue" ng-model="companyTarget.targetValue" class="form-control" placeholder="<spring:message code='labelcompanytargetform.targetvalue.placegolder' text='Enter target value'/>"
                      ></input>
                      <label ng-show="targetValueRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.companytargetform.targetValueRequiredMessage' text='Target value is required' /></label>
                    </div>
                    
                    
                    <div class="form-group" ng-show="ShowRationOption">
                      <label><spring:message code='label.piginfo.feedEventForm.rationId'  text='Ration Id'/><span style='color: red'>*</span></label>
                     <!-- <input type="text" ng-model="feedEvent.rationId" id="rationId" name="rationId"  class="form-control" maxlength="255" placeholder="<spring:message code='label.piginfo.feedEventForm.batchId.placeholder'  text='Enter Batch Id'/>" 
                       required required-message="'<spring:message code='label.piginfo.feedEventForm.batchId.requiredMessage' text='Batch Id is required' />'" ng-focus="clearMessages()"/>-->                        
                        <select class="form-control" id="rationId" name="rationId" ng-model="companyTarget.rationId"  required required required-message="'<spring:message code='label.piginfo.feedEventForm.rationId.requiredMessage' text='Ration Id is required' />'">
                      	<option ng-repeat="key in rationListKeys" ng-value="key" ng-selected="companyTarget.rationId==key">{{rationListKeyValues[key]}}</option>        
                        </select> 
                   </div>
                    
                    
                    <div class="form-group"> 
                      <label><spring:message code='label.companytargetform.completiondate'  text='Start Date'/><span style='color: red'>*</span></label><i><spring:message code='label.piginfo.input.dateformat'  text='(in dd/mm/yyyy format)'/></i>                     
                      <input type="text" class="form-control" ng-model="companyTarget.completionDateStr" mask="39/19/2999" mask-validate='true' ng-blur="dateCheck(companyTarget.completionDateStr, 'completionDate')"/>
                        <label ng-show="completionDateRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.companytargetform.completionDateRequiredMessage' text='Completion date is required' /></label> 
                    </div>
					
					                    
                    <div class="form-group">
                      <label><spring:message code='label.companytargetform.remarks'  text='Remarks'/></label>
                      <textarea name="remarks" ng-model="companyTarget.remarks" class="form-control" placeholder="<spring:message code='label.companytargetform.remarks.placeholder' text='Enter remarks'/>"></textarea>
                      <label ng-show="remarksRequired" style='color:red' class='control-label has-error validationMessage'>&nbsp;<spring:message code='label.companytargetform.remarksRequiredMessage' text='Remarks is required' /></label>
                    </div>
                    
                    <button class="btn btn-success" ng-click="saveCompanyTarget()" type="submit"><spring:message code='label.piginfo.entryeventform.submit'  text='Submit'/></button>
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
						<th style="width:20%"><spring:message code='label.piginfo.farroweventform.premise'  text='Premise'/></th>
						<th style="width:20%"><spring:message code="label.companytargetform.targetname" text="Target" /></th>
						<th style="width:20%"><spring:message code="label.companytargetform.targetvalue" text="Value" /></th>
						<th style="width:20%"><spring:message code='label.piginfo.feedEventForm.rationId'  text='Ration Id'/></th>
						<th style="width:20%"><spring:message code="label.companytargetform.completiondate" text="Start Date" /></th>
						<th style="width:20%"><spring:message code="label.companytargetform.remarks" text="Remarks" /></th>
						<th style="width:20%"><spring:message code="label.companytargetform.action" text="Action" /></th>
					</tr>
	 			</thead>
				<tbody>
				<tr ng-repeat="row in displayedCollection track by $index">
					<td style="width:20%">{{row.premise}}</td>
					<td style="width:20%">{{row.targetName}}</td>
					<td style="width:20%">{{row.targetValue}}</td>		
					<td style="width:20%">{{row.ration}}</td>			
					<td style="width:20%">{{DateUtils.getFormatedDate(row.completionDate)}}</td>
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


