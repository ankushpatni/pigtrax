<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2>Mass Upload - Events</h2>
</div>
<div class="cl-mcont" id="massuploadControllerId">
	<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" action="uploadFile" name="batchupload"
					enctype="multipart/form-data">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.piginfo.entryeventform.batchupload.heading'
								text='Batch Upload' />
						</h3>
						<input type="file" name="file" value="upload" class="form-control">
						<br /> Event Type: <select class="form-control" name="eventType">
							<option value="PIGINFO">PIGINFO</option>
							<option value="PREGNANCYINFO">PREGNANCY INFO</option>
							<option value="PIGLETSTATUSINFO">PIGLET STATUS INFO</option>
							<option value="FARROWEVENT">FARROW</option>
							<option value="GROUPEVENT">GROUP</option>
							<option value="FEEDEVENT">FEED</option>
							<option value="GROUPEVENTDETAILEVENT">GROUP DETAIL</option>
						</select> <br />CSV HEADER: <select class="form-control" name="header">
							<option value="false">WITHOUT HEADER</option>
							<option value=true>WITH HEADE</option>
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