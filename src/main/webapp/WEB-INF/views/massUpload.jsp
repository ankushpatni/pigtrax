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
							<option value="BREEDINGEVENT" <%=eventType.equals("BREEDING EVENT")?"selected":"" %>>BREEDING EVENT</option>
							<option value="MATINGDETAILS" <%=eventType.equals("MATINGDETAILS")?"selected":"" %>>MATINGDETAILS</option>
							<option value="PREGNANCYINFO" <%=eventType.equals("PREGNANCY INFO")?"selected":"" %>>PREGNANCY INFO</option>
							<option value="FARROWEVENT" <%=eventType.equals("FARROW")?"selected":"" %>>FARROW</option>
							<option value="INDIVIDUALPIGLETSTATUS" <%=eventType.equals("INDIVIDUAL PIGLET STATUS")?"selected":"" %>>INDIVIDUAL PIGLET STATUS</option>	
							<option value="PIGLETSTATUSINFO" <%=eventType.equals("PIGLET STATUS INFO")?"selected":"" %>>PIGLET STATUS INFO</option>							
							<option value="GROUPEVENT" <%=eventType.equals("GROUP")?"selected":"" %>>GROUP</option>
							<option value="GROUPEVENTDETAILEVENT" <%=eventType.equals("GROUP DETAIL")?"selected":"" %>>GROUP DETAIL</option>
							<option value="FEEDEVENT" <%=eventType.equals("FEED")?"selected":"" %>>FEED</option>
							<option value="REMOVALEVENTEXCEPTSALESEVENT" <%=eventType.equals("REMOVALEVENTEXCEPTSALESEVENT")?"selected":"" %>>REMOVALEVENTEXCEPTSALESEVENT</option>
							<option value="SALESEVENTDETAILS" <%=eventType.equals("SALESEVENTDETAILS")?"selected":"" %>>SALESEVENTDETAILS</option>
													
						</select> <br />CSV HEADER: <select class="form-control" name="header">							
							<option value="true" <%=eventType.equals("true")?"selected":"" %>>WITH HEADER</option>
							<option value="false" <%=eventType.equals("false")?"selected":"" %>>WITHOUT HEADER</option>
						</select> <br />
						<button type="submit" value="upload" class="btn btn-success">
							<spring:message code='label.piginfo.entryeventform.upload.button'
								text='Upload' />
						</button>
					</div>
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>