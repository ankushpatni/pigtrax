<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2>Mass Upload - Events</h2>
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
						<input type="file" name="file" value="upload" class="form-control">
						<br /> Event Type: <select class="form-control" name="eventType">
							<option value="PIGINFO" <%=eventType.equals("PIGINFO")?"selected":"" %>>PIGINFO</option>
							<option value="BREEDINGEVENT" <%=eventType.equals("BREEDINGEVENT")?"selected":"" %>>BREEDING EVENT</option>
							<option value="MATINGDETAILS" <%=eventType.equals("MATINGDETAILS")?"selected":"" %>>MATINGDETAILS</option>
							<option value="PREGNANCYINFO" <%=eventType.equals("PREGNANCY INFO")?"selected":"" %>>PREGNANCY INFO</option>
							<option value="FARROWEVENT" <%=eventType.equals("FARROWEVENT")?"selected":"" %>>FARROW</option>
							<option value="INDIVIDUALPIGLETSTATUS" <%=eventType.equals("INDIVIDUALPIGLETSTATUS")?"selected":"" %>>INDIVIDUAL PIGLET STATUS</option>	
							<option value="PIGLETSTATUSINFO" <%=eventType.equals("PIGLETSTATUSINFO")?"selected":"" %>>PIGLET STATUS INFO</option>							
							<option value="GROUPEVENT" <%=eventType.equals("GROUPEVENT")?"selected":"" %>>GROUPEVENT</option>
							<option value="GROUPEVENTDETAILEVENT" <%=eventType.equals("GROUPEVENTDETAILEVENT")?"selected":"" %>>GROUP DETAIL</option>
							<option value="FEEDEVENT" <%=eventType.equals("FEEDEVENT")?"selected":"" %>>FEED</option>
							<option value="FEEDDETAILEVENT" <%=eventType.equals("FEEDDETAILEVENT")?"selected":"" %>>FEED DETAIL</option>
							<option value="REMOVALEVENTEXCEPTSALESEVENT" <%=eventType.equals("REMOVALEVENTEXCEPTSALESEVENT")?"selected":"" %>>REMOVALEVENTEXCEPTSALESEVENT</option>
							<option value="SALESEVENTDETAILS" <%=eventType.equals("SALESEVENTDETAILS")?"selected":"" %>>SALESEVENTDETAILS</option>
													
						</select> <br />CSV HEADER: <select class="form-control" name="header">							
							<option value="true" <%=eventType.equals("true")?"selected":"" %>>WITH HEADER</option>
							<option value="false" <%=eventType.equals("false")?"selected":"" %>>WITHOUT HEADER</option>
						</select> <br />
						<button type="submit" value="upload" class="btn btn-success">
							<spring:message code='label.piginfo.entryeventform.upload.button'
								text='Upload' />
						</button> &nbsp;&nbsp;&nbsp;<a href="#" data-toggle="modal" data-target="#guideLinesModal">Guidelines</a>
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
						    7. entryDate - Entry date in mm/dd/yyyy format. Should be within 100 - 300 days of birth date. Mandatory field </br>
						    8. sexTypeId - Allowed values Male / Female. Mandatory field</br>
						    9. geneticOrigin - Need to configure in the system and enter that value. Optional field</br>
						    10. geneticCompany - Mandatory field. Refer to the entry event screen to know about the allowed values. </br>
						    11. geneticLine - Mandatory field. Refer to the entry event screen to know about the allowed values. </br>
						    12. geneticFunction - Mandatory field. Refer to the entry event screen to know about the allowed values. </br>
						    13. birthDate - Birth date in mm/dd/yyyy format.  Optional field.</br>
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