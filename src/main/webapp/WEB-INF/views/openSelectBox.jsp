<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="form-primary" 	class="md-modal colored-header md-effect-9">
	<div class="md-content">
		<div class="modal-header" >
			<h3><spring:message code="label.removalEvent.selectRemovalEvent" text="Select Removal Event" /></h3>
			<button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
		</div>
		<form name="openSelectForm" novalidate angular-validator>
			<div class="modal-body form">
					<div class="form-group">
						<label><spring:message code="label.piginfo.removalEventform.feedEvent" text="Removal Event" /></label>
						
						<label ng-repeat="n in removalElementList">
							<div class="form-group" style="width:100%">{{n.removalId}}
        					 <input type="radio" name="removalIdSelected" ng-model="removalIdSelected" class="icheck removal" ng-value="{{n.id}}" id="{{n.id}}"/>
							 </div>
						</label>
					</div>
					<div class="modal-footer">
		            	<button class="btn btn-warning btn-flat md-close"  ng-click="cancel()"><spring:message code="label.premise.cancel" text="Cancel" /></button>
		        	</div>
		       </div>
	    </form>
	</div>
</div>