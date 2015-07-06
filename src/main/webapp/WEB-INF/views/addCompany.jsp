<div class="modal-header">
    <div class="cp-modal-close" ng-click="cancel()">X</div>
    <h3 class="modal-title" style="text-align: center; font-weight: bold;" ng-hide="edit">Add Company Data</h3>
	<h3 class="modal-title" style="text-align: center; font-weight: bold;" ng-show="edit">Edit Company Data</h3>
</div>
<div class="modal-body">
    <form name="companyAddForm" novalidate angular-validator>
        <div class="cp-modal-body-cont">
             <div>
				<label class="cp-add-row-labal">Company ID<span style='color: red'>*</span></label>
				<label class="cp-add-row" ng-show="edit">{{add.companyId}}</label>
				<input ng-hide="edit" class="cp-add-row" type="text" placeholder="Company ID" name="companyId" ng-model="add.companyId" maxlength="4" size="5" required required-message="'Company Id is required'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'Only Alpha Numeric values are allowed.'"/ >
			</div>
			 <div>
				<label class="cp-add-row-labal">Company Name<span style='color: red'>*</span></label>
				<input class="cp-add-row" type="text" placeholder="Company Name" name="companyName" ng-model="add.name" maxlength="50" required required-message="'Company Name is required'"/>	
			</div>
			<div>
				<label class="cp-add-row-labal">Address<span style='color: red'>*</span></label>
				<textarea class="cp-add-row" type="text" placeholder="Company Address" name="address" ng-model="add.address" maxlength="255" required required-message="'Address is required'" />
			</div>
			<div>
				<label class="cp-add-row-labal">Country<span style='color: red'>*</span></label>
				<Select class="cp-add-row" placeholder="Country" name="country" ng-model="add.country" required required-message="'Country is required'" 
				       ng-change="changeCity()"  ng-options="state.value as state.name for state in country"/>
			</div>
			<div>
				<label class="cp-add-row-labal">City<span style='color: red'>*</span></label>
				<select class="cp-add-row" placeholder="City" name="city" ng-model="add.city" maxlength="30" required required-message="'City is required'" 
					ng-options="city1.value as city1.name for city1 in city"/>
			</div>
            <div>
				<label class="cp-add-row-labal">Registration Number<span style='color: red'>*</span></label>
				<input class="cp-add-row" type="text" placeholder="Registration Number" name="registrationNumber" ng-model="add.registrationNumber" maxlength="20" required required-message="'Registration Number is required'" ng-pattern="/^[a-z0-9]+$/i" invalid-message="'Only Alpha Numeric values Allowed.'"/>
			</div>
            <div>
				<label class="cp-add-row-labal">Email<span style='color: red'>*</span></label>
				<input class="cp-add-row" type="email" placeholder="Email" name="uEmail" ng-model="add.email" maxlength="50" required required-message="'Email is required'" invalid-message="'Not a valid email id'"/>
			</div>
            <div>
				<label class="cp-add-row-labal">Phone<span style='color: red'>*</span></label>
				<input class="cp-add-row" type="text" placeholder="+1-111-111-1111" name="phone" ng-model="add.phone" maxlength="15" required required-message="'Phone is required'" ng-pattern="/^[\0-9-+]+$/" invalid-message="'Please input correct number in US format.'"/>
			</div>
            <div>
				<label class="cp-add-row-labal">Contact Name<span style='color: red'>*</span></label>
				<input class="cp-add-row" type="text" placeholder="Contact Name" name="contactName" ng-model="add.contactName" maxlength="50" required required-message="'Contact Name is required'"/>
			</div>
			<div>
				<label class="cp-add-row-labal">Payment</label>
				<input class="cp-add-row" type="text" placeholder="Payment" name="payment" ng-model="add.payment" maxlength="15" ng-pattern="/^[0-9]{1,15}(\.[0-9]+)?$/i" invalid-message="'Only Numeric values Allowed.'"/>
			</div>
        </div>
        <div class="cp-modal-body-btns">

            <button class="btn cp-btn-warning" ng-click="addCompany()" ng-hide="edit">Add</button>
			<button class="btn" ng-click="addCompany()" ng-show="edit">Edit</button>
            <button class="btn" ng-click="cancel()">Cancel</button>
        </div>
        <!-- <div class="cp-modal-body-bottom">
            <input type="checkbox" style="margin: 0 6px 0 0; float: left;">Remember me</input>
            <a style="float: right;" ng-click="forgotPass()">Forgot Your Password?</a>
        </div> -->
    </form>
</div>
<div class="modal-footer" ng-show="alertVisible">
    <p style="color:red">{{alertMessage}}</p>
</div>