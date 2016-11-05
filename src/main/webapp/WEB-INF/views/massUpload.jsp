<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2><spring:message code='label.piginfo.entryeventform.batchupload.heading'	text='Batch Upload' /></h2>
</div>
<div class="cl-mcont" id="massuploadControllerId" ng-controller="MassUploadController" ng-init="setStatus('${token}')">
	<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" action="uploadFile" name="batchupload"
					enctype="multipart/form-data">
					<%
						String reportName = "";
						String eventType = "";
						String header = "";
						if(session.getAttribute("REPORT_FILE") != null)
							reportName = (String)session.getAttribute("REPORT_FILE");
						if(session.getAttribute("eventType") != null)
							eventType = (String)session.getAttribute("eventType");
						if(session.getAttribute("header") != null)
							header = (String)session.getAttribute("header");
						
						%>
					<div class="head">
						  <b  ng-show="uploadStatus == 'success'"  class="color-primary"><spring:message code='label.uploadprocess.success.message' text='Upload process completed.'/>&nbsp;
						  <a href="<%=request.getContextPath()%>/downloadReport?file=<%=reportName%>"><spring:message code='label.uploadprocess.success.message.clickhere' text='Click here'/></a>&nbsp; <spring:message code='label.uploadprocess.success.download.text' text='to download the report.'/></b>
                 		
						<h3>
							<spring:message
								code='label.piginfo.entryeventform.batchupload.heading'
								text='Batch Upload' />
						</h3>
						

					<div class="form-group">
                      <label><spring:message code='label.massuploadForm.Company'  text='Company'/><span style='color: red'>*</span></label>
                       <select class="form-control"  ng-change="getPremises()" name="companyId" id="companyId"  ng-model="upload.companyId" required required-message="'<spring:message code='label.piginfo.farroweventform.premise.requiredmessage' text='Premise is required' />'" ng-change="getRooms()">
                       	<option  ng-repeat="company in companyList" value="{{company.id}}" ng-value="company.id" ng-selected="upload.companyId == company.id">{{company.name}}</option>
                        </select>
                    </div>
					
					
					<div class="form-group">
                      <label><spring:message code='label.company.premises'  text='Premise'/><span style='color: red'>*</span></label>
                       <select class="form-control"  name="premiseId" id="premiseId"  ng-model="upload.premiseId" required required-message="'<spring:message code='label.piginfo.farroweventform.premise.requiredmessage' text='Premise is required' />'" ng-change="getRooms()">
                       	<option  ng-repeat="premise in premiseList" value="{{premise.id}}" ng-value="premise.id" ng-selected="upload.premiseId == premise.id">{{premise.name}}</option>
                        </select>
                    </div>
						
						<div class="form-group">
                      <label><spring:message code='label.massuploadForm.eventtype'  text='Event Type'/></label>
                      <select class="form-control" name="eventType">
							<option value="ENTRYEVENT" <%=eventType.equals("ENTRYEVENT")?"selected":"" %>><spring:message code="label.leftmenu.managepigevents.entryevent.link"  text="Entry Event"/></option>
							<option value="BREEDINGEVENT" <%=eventType.equals("BREEDINGEVENT")?"selected":"" %>><spring:message code="label.leftmenu.managepigevents.breedingevent.link"  text="Breeding Event"/></option>
							<option value="MATINGDETAILS" <%=eventType.equals("MATINGDETAILS")?"selected":"" %>><spring:message code="label.piginfo.matingdetailsform.matingDetails.heading" text="Mating Details"/></option>
							<option value="PREGNANCYEVENT" <%=eventType.equals("PREGNANCYEVENT")?"selected":"" %>><spring:message code="label.leftmenu.managepigevents.pregnancyevent.link"  text="Pregnancy Event"/></option>
							<option value="FARROWEVENT" <%=eventType.equals("FARROWEVENT")?"selected":"" %>><spring:message code="label.leftmenu.managepigevents.farrowevent.link"  text="Farrow Event"/></option>
							<option value="PIGLETSTATUSEVENT" <%=eventType.equals("PIGLETSTATUSEVENT")?"selected":"" %>><spring:message code="label.leftmenu.managepigevents.pigletstatus.link"  text="Piglet Status Event"/></option>
							<option value="GROUPEVENT" <%=eventType.equals("GROUPEVENT")?"selected":"" %>><spring:message code="label.leftmenu.managepigevents.groupEvent.link"  text="Group Event"/></option>
							<option value="GROUPEVENTDETAILS" <%=eventType.equals("GROUPEVENTDETAILS")?"selected":"" %>><spring:message code="label.massupload.groupeventdetails" text="Group Event Details"/></option>
							<option value="INDIVIDUALPIGLET" <%=eventType.equals("INDIVIDUALPIGLET")?"selected":"" %>><spring:message code="label.leftmenu.managepigevents.individualpiglet.link"  text="Individual Piglet"/></option>
							<option value="FEEDEVENTDETAILS" <%=eventType.equals("FEEDEVENTDETAILS")?"selected":"" %>><spring:message code="label.massuploadForm.feedeventdetails"  text="Feed Event Details"/></option>
							<option value="MORTALITYANDADJUSTMENT" <%=eventType.equals("MORTALITYANDADJUSTMENT")?"selected":"" %>><spring:message code='label.piginfo.removalExceptSales.removalExceptSales.option'  text='Removal Except Sales'/></option>
							<option value="SALES" <%=eventType.equals("SALES")?"selected":"" %>><spring:message code='label.piginfo.removalExceptSales.sales.option'  text='Removal Event Id'/> </option>
													
						</select> 
						</div>
						
						<div class="form-group">
                      		<label><spring:message code='label.massuploadForm.csvheader'  text='CSV Header'/></label>
                       		<select class="form-control" name="header">							
								<option value="true" <%=eventType.equals("true")?"selected":"" %>><spring:message code='label.massuploadForm.withHeader'  text='WITH HEADER'/></option>
								<option value="false" <%=eventType.equals("false")?"selected":"" %>><spring:message code='label.massuploadForm.withoutHeader'  text='WITHOUT HEADER'/></option>
							</select> 
						</div>
						
						<div class="form-group">
						<input type="file" name="file" value="upload" class="form-control">
						</div>
						
						<button type="submit" value="upload" class="btn btn-success">
							<spring:message code='label.piginfo.entryeventform.upload.button'
								text='Upload' />
						</button> 
						
						<div class="form-group">
						
							<button type="button"  class="btn btn-edit btn-xs" ng-click="showCSVData()">
							<span class="glyphicon" ></span><spring:message code="label.leftmenu.managepigevents.csv.link" text="Show CSV Template" /></a></button>
							
							<button type="button"  class="btn btn-edit btn-xs" ng-click="showXLSData()">
							<span class="glyphicon" ></span><spring:message code="label.leftmenu.managepigevents.xls.link" text="Show XLS Template" /></a></button>
						</div>
						
						
						<div class="form-group" ng-show="showCSV">
						
						<h4><spring:message code="label.massupload.downloadtemplates.text" text="Download sample CSV templates" /></h4>
						
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=EntryEvent&fileType=csv"><spring:message code="label.leftmenu.managepigevents.entryevent.link"  text="Entry Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=BreedingEvent&fileType=csv"><spring:message code="label.leftmenu.managepigevents.breedingevent.link"  text="Breeding Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=MatingDetails&fileType=csv"><spring:message code="label.piginfo.matingdetailsform.matingDetails.heading" text="Mating Details"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=PregnancyEvent&fileType=csv"><spring:message code="label.leftmenu.managepigevents.pregnancyevent.link"  text="Pregnancy Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=FarrowEvent&fileType=csv"><spring:message code="label.leftmenu.managepigevents.farrowevent.link"  text="Farrow Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=PigletStatusEvent&fileType=csv"><spring:message code="label.leftmenu.managepigevents.pigletstatus.link"  text="Piglet Status Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=GroupEvent&fileType=csv"><spring:message code="label.leftmenu.managepigevents.groupEvent.link"  text="Group Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=GroupEventDetails&fileType=csv"><spring:message code="label.massupload.groupeventdetails" text="Group Event Details"/></a><br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=IndividualPigletEvent&fileType=csv"><spring:message code="label.leftmenu.managepigevents.individualpiglet.link"  text="Individual Piglet"/></a></br>						
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=FeedEventDetails&fileType=csv"><spring:message code="label.leftmenu.managepigevents.feedEventDetails.link"  text="Feed Event Details"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=RemovalEventExceptSalesEventGroup&fileType=csv"><spring:message code="label.leftmenu.managepigevents.removalEventExceptSalesEventGroup.link"  text="Mortality & adjustment - Group"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=RemovalEventExceptSalesEventPig&fileType=csv"><spring:message code="label.leftmenu.managepigevents.removalEventExceptSalesEventPig.link"  text="Mortality & adjustment - Pig"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=SalesEventGroup&fileType=csv"><spring:message code="label.leftmenu.managepigevents.SalesEventGroup.link"  text="Sales- Group"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=SalesEventPig&fileType=csv"><spring:message code="label.leftmenu.managepigevents.SalesEventPig.link"  text="Sales- Pig"/></a></br>
						</div>
						
						<div class="form-group" ng-show="showXLS">
						
						<h4>Download sample XLS templates</h4>
						
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=EntryEvent&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.entryevent.link"  text="Entry Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=BreedingEvent&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.breedingevent.link"  text="Breeding Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=MatingDetails&fileType=xlsx"><spring:message code="label.piginfo.matingdetailsform.matingDetails.heading" text="Mating Details"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=PregnancyEvent&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.pregnancyevent.link"  text="Pregnancy Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=FarrowEvent&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.farrowevent.link"  text="Farrow Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=PigletStatusEvent&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.pigletstatus.link"  text="Piglet Status Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=GroupEvent&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.groupEvent.link"  text="Group Event"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=GroupEventDetails&fileType=xlsx"><spring:message code="label.massupload.groupeventdetails" text="Group Event Details"/></a><br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=IndividualPigletEvent&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.individualpiglet.link"  text="Individual Piglet"/></a></br>						
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=FeedEventDetails&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.feedEventDetails.link"  text="Feed Event Details"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=RemovalEventExceptSalesEventGroup&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.removalEventExceptSalesEventGroup.link"  text="Mortality & adjustment - Group"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=RemovalEventExceptSalesEventPig&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.removalEventExceptSalesEventPig.link"  text="Mortality & adjustment - Pig"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=SalesEventGroup&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.SalesEventGroup.link"  text="Sales- Group"/></a></br>
						<a href="<%=request.getContextPath()%>/downloadTemplate?type=SalesEventPig&fileType=xlsx"><spring:message code="label.leftmenu.managepigevents.SalesEventPig.link"  text="Sales- Pig"/></a></br>
						</div>
						
					</div>					
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
	
	
			<!-- What is available Pig Id -->			 
		  <div id="guideLinesModal" class="modal colored-header warning custom-width">
                    <div class="md-content">
                      <div class="modal-header">
                        <h3><spring:message code='label.massupload.guidelines.heading'  text='Mass Upload Guidelines'/> </h3>
                        <button type="button" data-dismiss="modal" aria-hidden="true" class="close md-close">×</button>
                      </div>
                      <div class="modal-body form" >
                      <div class="table-responsive">
                        <h4>
                            Entry Event
                        </h4>
						 <p>
						    1. companyId - The unique company id value. Mandatory field <br>
						    2. farmName - The unique premise id value. Mandatory field <br>
						    3. roomId - The unique room id within the specified farm. Mandatory field </br>
						    4. pigId - unique pig id value. Mandatory field </br>
						    5. tattoo - Unique value. Optional field </br>
						    6. alternateTattoo - Alternate value. Optional field </br>
						    7. entryDate - Entry date in dd/mm/yyyy format. Should be within 100 - 300 days of birth date. Mandatory field </br>
						    8. sexTypeId - Allowed values Male / Female. Mandatory field</br>
						    9. geneticOrigin - Need to configure in the system and enter that value. Optional field</br>
						    10. geneticCompany - Mandatory field. Refer to the entry event screen to know about the allowed values. </br>
						    11. geneticLine - Mandatory field. Refer to the entry event screen to know about the allowed values. </br>
						    12. geneticFunction - Mandatory field. Refer to the entry event screen to know about the allowed values. </br>
						    13. birthDate - Birth date in dd/mm/yyyy format.  Optional field.</br>
						    14. sireId -  Optional field.</br>
						    15. damId -  Optional field.</br>
						    16. remarks -  Optional field.</br>
						    17. parity -  Numeric value. Optional field.</br>
						 </p>
                      </div>
                      <div class="modal-footer">                                           
                      <button type="button" data-dismiss="modal" class="btn btn-warning btn-flat md-close"><spring:message code='label.employeegroup.button.cancel'  text='Cancel'/></button>
                      </div>
                      
                     </div>
            </div>
            
            
            
          </div>
	
</div>