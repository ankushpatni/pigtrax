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
					<div class="head">
						<div class="alert alert-success alert-white rounded"  ng-show="uploadStatus == 'success'">
		                    <button type="button" data-dismiss="alert" aria-hidden="true" class="close">×</button>
		                    <div class="icon"><i class="fa fa-check"></i></div><spring:message code='label.uploadprocess.success.message' text='Upload process completed successfully'/>
                 		 </div>
                 		 
						<%
						String reportName = "";
						if(session.getAttribute("REPORT_FILE") != null)
							reportName = (String)session.getAttribute("REPORT_FILE");
						
						%>
						<a href="<%=request.getContextPath()%>/downloadReport?file=<%=reportName%>"   ng-show="uploadStatus == 'success'"><spring:message code='label.uploadprocess.viewreport.link' text='Download Report'/></a>
						
						<h3>
							<spring:message
								code='label.piginfo.entryeventform.batchupload.heading'
								text='Batch Upload' />
						</h3>
						<input type="file" name="file" value="upload" class="form-control">
						<br /> Event Type: <select class="form-control" name="eventType">
							<option value="PIGINFO">PIGINFO</option>
							<option value="BREEDINGEVENT">BREEDING EVENT</option>
							<option value="MATINGDETAILS">MATINGDETAILS</option>
							<option value="PREGNANCYINFO">PREGNANCY INFO</option>
							<option value="FARROWEVENT">FARROW</option>
							<option value="PIGLETSTATUSINFO">PIGLET STATUS INFO</option>							
							<option value="GROUPEVENT">GROUP</option>
							<option value="GROUPEVENTDETAILEVENT">GROUP DETAIL</option>
							<option value="FEEDEVENT">FEED</option>
							<option value="FEEDEVENT">INDIVIDUAL PIGLET STATUS</option>							
						</select> <br />CSV HEADER: <select class="form-control" name="header">
							<option value="false">WITHOUT HEADER</option>
							<option value=true>WITH HEADER</option>
						</select> <br />
						<button type="submit" value="upload">
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