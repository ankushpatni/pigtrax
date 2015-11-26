<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="form-primary" 	class="md-modal colored-header md-effect-9" ng-controller="CompanyController">
	<div class="md-content">
		<div class="modal-header">
			<h3><spring:message code="label.company.companyDetail" text="Company Detail" /></h3>
		</div>
		<form name="companyDetailForm" method="post">
			<div class="modal-body form">
				 <table class="no-border no-strip information">
                      <tbody class="no-border-x no-border-y">
                        <tr>
                          <td style="width:20%;" class="category"><strong><spring:message code="label.company.companyDetail" text="Company Detail" /></strong></td>
                          <td>
                            <table class="no-border no-strip skills">
                              <tbody class="no-border-x no-border-y">
                                <tr>
                                  <td style="width:20%;"><b><spring:message code="label.company.companyID" text="Company ID" /></b></td>
                                  <td>${CompanyId.companyId}</td>
                                </tr>
								 <tr>
                                  <td style="width:20%;"><b><spring:message code="label.company.name"
										text="Name" /></b></td>
                                  <td>${CompanyId.name}</td>
                                </tr>
								<tr>
                                  <td style="width:20%;"><b><spring:message code="label.company.address"
									text="Address" /></b></td>
                                  <td>${CompanyId.address}</td>
                                </tr>
								<tr>
                                  <td style="width:20%;"><b><spring:message code="label.company.country"
									text="Country" /></td>
                                  <td></td>
                                </tr>
								<tr>
                                  <td style="width:20%;"><b><spring:message code="label.company.city"
									text="City" /></td>
                                  <td>${CompanyId.city}</td>
                                </tr>
								<tr>
                                 <td style="width:20%;"><b><spring:message code="label.company.registrationNumber"
									text="Registration Number" /></td>
                                  <td>${CompanyId.registrationNumber}</td>
                                </tr>				
                                <tr>
                                 <td style="width:20%;"><b><spring:message code="label.company.email"
									text="Email" /></b></td>
                                  <td>${CompanyId.email}</td>
                                </tr>
                                <tr>
                                 <td style="width:20%;"><b><spring:message code="label.company.phone"
									text="Phone" /></b></td>
                                  <td>${CompanyId.phone}</td>
                                </tr>
                                <tr>
                                 <td style="width:20%;"><b><spring:message code="label.company.contactName"
									text="Contact Name" /></b></td>
                                  <td>${CompanyId.contactName}</td>
                                </tr>											
                              </tbody>
                            </table>
                          </td>
                        </tr>
						</tbody>
						</table>
				<div class="modal-footer">
					<button class="btn btn-primary btn-flat md-close" ng-click="gotToPageFromDetails('${CompanyId.id}')">
						<spring:message code="label.company.goToPremise" text="Go To Premises" />
					</button>
					
					<button class="btn btn-primary btn-flat md-close" ng-click="gotToTransportFromDetails()">
						<spring:message code="label.company.goToTransportPage" text="Go To Transport Page" />
					</button>						
				</div>
			</div>
			
			<input type="hidden" name="generatedCompanyId" id="generatedCompanyId"/>
		</form>
		<div class="modal-footer" ng-show="alertVisible"></div>
	</div>
</div>