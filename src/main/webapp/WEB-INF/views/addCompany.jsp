<div class="modal-header">
    <div class="cp-modal-close" ng-click="cancel()">X</div>
    <h3 class="modal-title" style="text-align: center; font-weight: bold;">Add Company</h3>
</div>
<div class="modal-body">
    <form name="loginForm" novalidate>
        <div class="cp-modal-body-cont">
            <input type="text" placeholder="Company ID" ng-model="add.companyId" value="C123"><br/>
            <br>
            <input type="text" placeholder="Name" ng-model="add.name" size=4 value="C123"/><br/>
            <br>
            <input type="text" placeholder="Address" ng-model="add.address" value="C123"/><br/>
            <br>
            <input type="text" placeholder="City" ng-model="add.city" value="C123"/><br/>
            <br>
            <input type="text" placeholder="Registration Number" ng-model="add.registrationNumber" value="987123" /><br/>
            <br>
            <input type="text" placeholder="Email" ng-model="add.email" value="C123@gmail.com"><br/>
            <br>
            <input type="text" placeholder="Phone" ng-model="add.phone" value="1234123123" /><br/>
            <br>
            <input type="text" placeholder="Contact Name" ng-model="add.contactName" value="Ankush" /><br/>
            <br>
            <input type="text" placeholder="Payment" ng-model="add.payment" value="12123"><br/>
        </div>
        <div class="cp-modal-body-btns">

            <button class="btn" ng-click="addCompany()">Add</button>
            <button class="btn" ng-click="cancel()">Cancel</button>
        </div>
        <!-- <div class="cp-modal-body-bottom">
            <input type="checkbox" style="margin: 0 6px 0 0; float: left;">Remember me</input>
            <a style="float: right;" ng-click="forgotPass()">Forgot Your Password?</a>
        </div> -->
    </form>
</div>
<div class="modal-footer" ng-show="alertVisible">
    <p>{{alertMessage}}</p>
</div>