<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="form-primary" 	class="md-modal colored-header md-effect-9">
	<div class="md-content">
		<div class="modal-header">
			<h3  ng-hide="edit"><spring:message code="label.company.addCompanyData" text="Add Company Data" /></h3>
			<h3  ng-show="edit"><spring:message code="label.company.editCompanyData" text="Edit Company Data" /></h3>
			 <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close"  ng-click="cancel()">×</button>
			
		</div>
		<form name="companyAddForm" novalidate angular-validator>
			<div class="modal-body form">

				<div class="form-group">
					<label><spring:message code="label.company.companyID" text="Company ID" /><span style='color: red'>*</span></label> 
					<label ng-show="edit">{{add.companyId}}</label> 
					<input ng-hide="edit" class="form-control" type="text"
						placeholder="<spring:message code='label.company.companyID' text='Company ID' />"
						name="companyId" ng-model="add.companyId" maxlength="4" size="5"
						required
						required-message="'<spring:message code='label.company.companyIDRequired' text='Company Id is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.company.companyIDInvalid' text='Only Alpha Numeric values are allowed' />'"/ >
				</div>
				<div class="form-group">
					<label><spring:message code="label.company.name"
							text="Name" /><span style='color: red'>*</span></label> <input
						type="text"
						class="form-control" 
						placeholder="<spring:message code='label.company.name'  text='Name'/>"
						name="companyName" ng-model="add.name" maxlength="50" required
						required-message="'<spring:message code='label.company.companyNameRequired' text='Company Name is required' />'" />
				</div>
				<div class="form-group">
					<label><spring:message code="label.company.address"
							text="Address" /><span style='color: red'>*</span></label>
					<textarea class="form-control" type="text"
						placeholder="<spring:message code='label.company.address' text='Address' />"
						name="address" ng-model="add.address" maxlength="255" required
						required-message="'<spring:message code='label.company.companyAddressRequired' text='Address is required' />'" />
				</div>
				<div class="form-group">
					<label><spring:message code="label.company.country"
							text="Country" /><span style='color: red'>*</span></label> <select
						class="form-control"
						placeholder="<spring:message code='label.company.country' text='Country' />"
						name="country" ng-model="add.country" required
						required-message="'<spring:message code='label.company.countryRequired' text='Country is required' />'"
						ng-change="changeCity()"
						ng-options="state.value as state.name for state in country" />
				</div>
				<div class="form-group">
					<label><spring:message code="label.company.city"
							text="City" /><span style='color: red'>*</span></label> <select
						class="form-control"
						placeholder="<spring:message code='label.company.city' text='City' />"
						name="city" ng-model="add.city" maxlength="30" required
						required-message="'<spring:message code='label.company.cityRequired' text='City is required' />'"
						ng-options="city1.value as city1.name for city1 in city" />
				</div>
				<div class="form-group">
					<label ><spring:message
							code="label.company.registrationNumber"
							text="Registration Number" /><span style='color: red'>*</span></label> <input
						class="form-control"  type="text"
						placeholder="<spring:message code='label.company.registrationNumber' text='Registration Number' />"
						name="registrationNumber" ng-model="add.registrationNumber"
						maxlength="20" required
						required-message="'<spring:message code='label.company.registrationNumberRequired' text='Registration Number is required' />'"
						ng-pattern="/^[a-z0-9]+$/i"
						invalid-message="'<spring:message code='label.company.registrationNumberInvalid' text='Only Alpha Numeric values Allowed.'/>'" />
				</div>
				<div class="form-group">
					<label><spring:message code="label.company.email"
							text="Email" /><span style='color: red'>*</span></label> <input
						class="form-control" type="email"
						placeholder="<spring:message code='label.company.email' text='Email' />"
						name="uEmail" ng-model="add.email" maxlength="50" required
						required-message="'<spring:message code='label.company.emailRequired' text='Email is required' />'"
						invalid-message="'<spring:message code='label.company.emailInvalid' text='Not a valid email id' />'" />
				</div>
				<div>
					<label><spring:message code="label.company.phone"
							text="Phone" /><span style='color: red'>*</span></label> <input
						class="form-control" type="text" placeholder="+1-111-111-1111"
						name="phone" ng-model="add.phone" maxlength="15" required
						required-message="'<spring:message code='label.company.phoneRequired' text='Phone is required' />'"
						ng-pattern="/^[\0-9-+]+$/"
						invalid-message="'Please input correct number in US format.'" />
				</div>
				<div class="form-group">
					<label><spring:message code="label.company.contactName"
							text="Contact Name" /><span style='color: red'>*</span></label> <input
						class="form-control" type="text"
						placeholder="<spring:message code='label.company.contactName' text='Contact Name' />"
						name="contactName" ng-model="add.contactName" maxlength="50"
						required
						required-message="'<spring:message code='label.company.contactNameRequired' text='Contact Name is required' />'" />
				</div>
				<div class="form-group">
					<label><spring:message code="label.company.payment"
							text="Payment" /></label> <input class="form-control" type="text"
						placeholder="<spring:message code='label.company.payment' text='Payment' />"
						name="payment" ng-model="add.payment" maxlength="15"
						ng-pattern="/^[0-9]{1,15}(\.[0-9]+)?$/i"
						invalid-message="'<spring:message code='label.company.paymentInvalid' text='Only Numeric values Allowed.'/>'" />
				</div>
				<div class="form-group">
						<label ><spring:message code="label.company.paymentDate" text="Payment Date" /></label>
						<div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
							<input type="text" datepicker-popup="yyyy-MM-dd" class="form-control" datepicker-popup="paymentDate" id="paymentDate" name="paymentDate" ng-model="add.paymentDate" is-open="opened"/>
							<span class="input-group-btn">
							<button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
						</div>
					</div>
					<div>
						<label style="color:red;margin-top: -15px;" class="control-label" ng-show="paymentDateFlag" ><spring:message code='label.company.paymentDateRequired' text='Payment Date is required' /></label>
					</div>
				<div class="modal-footer">

					<button class="btn btn-primary btn-flat md-close" ng-click="addCompany()"
						ng-hide="edit">
						<spring:message code="label.company.add" text="Add" />
					</button>
					<button class="btn btn-primary btn-flat md-close" ng-click="addCompany()" ng-show="edit">
						<spring:message code="label.company.edit" text="Edit" />
					</button>
					<button class="btn btn-default btn-flat md-close" ng-click="cancel()">
						<spring:message code="label.company.cancel" text="Cancel" />
					</button>
					<p style="color: red">{{alertMessage}}</p>
				</div>


			</div>
		</form>
		<div class="modal-footer" ng-show="alertVisible"></div>
	</div>
</div>