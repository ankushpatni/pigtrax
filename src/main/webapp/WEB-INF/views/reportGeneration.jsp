<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- ======== @Region: #content ======== -->
<div class="page-head">
	<h2>Mass Upload - Events</h2>
</div>
<div class="cl-mcont" id="reposrtGeneraterControllerId">
	<div class="row">
		<div class="col-sm-3 col-md-3"></div>
		<div class="col-sm-6 col-md-6">
			<div class="block-flat">
				<form method="POST" action="generateReport" name="generateReport">
					<div class="head">
						<h3 style="color:green">${token}</h3>
						<h3>
							<spring:message
								code='label.piginfo.generateReport.heading'
								text='Generate Report' />
						</h3>
						<div class="form-group">
                      		<label><spring:message code='label.piginfo.generateReport.startDate'  text='Start Date'/><span style='color: red'>*</span></label>
		                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
		                          <input size="16" type="date" name="startDate" readonly="" class="form-control" format-date/><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
								</div>
                    	</div>
                    	<div class="form-group">
                      		<label><spring:message code='label.piginfo.generateReport.endDate'  text='End Date'/><span style='color: red'>*</span></label>
		                      <div data-min-view="2" data-date-format="yyyy-mm-dd" class="input-group date datetime col-md-5 col-xs-7"  >
		                          <input size="16" type="date" name="endDate" readonly="" class="form-control" format-date/><span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
								</div>
                    	</div>
						<button type="submit" value="report">
							<spring:message code='label.piginfo.generateReport.button'
								text='Generate Report' />
						</button>
					</div>
				</form>
			</div>
		</div>
		<div class="col-sm-3 col-md-3"></div>
	</div>
</div>