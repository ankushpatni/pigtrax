<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style type="text/css">
.item{
    background: #33A1DE;    
    text-align: center;
    height: 150px !important;
}
.carousel{
    margin-top: 20px;
} 
</style> 
<div class="cl-mcont">
          <div class="stats_bar">
            <div data-step="2" data-intro="&lt;strong&gt;Beautiful Elements&lt;/strong&gt; &lt;br/&gt; If you are looking for a different UI, this is for you!." class="butpro butstyle">
              <div class="sub">
                <h2>CLIENTS</h2><span id="total_clientes">170</span>
              </div>
              <div class="stat"><span class="spk1">
                  <canvas style="display: inline-block; width: 74px; height: 16px; vertical-align: top;" width="74" height="16"></canvas></span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2>Sales</h2><span>$951,611</span>
              </div>
              <div class="stat"><span class="up"> 13,5%</span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2>VISITS</h2><span>125</span>
              </div>
              <div class="stat"><span class="down"> 20,7%</span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2>NEW USERS</h2><span>18</span>
              </div>
              <div class="stat"><span class="equal"> 0%</span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2>AVERAGE</h2><span>3%</span>
              </div>
              <div class="stat"><span class="spk2"></span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2>Downloads</h2><span>184</span>
              </div>
              <div class="stat"><span class="spk3"></span></div>
            </div>
          </div>
          <div class="row dash-cols">
            <div class="col-sm-6 col-md-6">
              <div class="block">
                <div class="header no-border">
                  <h2>Sales</h2>
                </div>
                <div data-step="3" class="content blue-chart">
                  <div id="site_statistics" style="height:125px;"></div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-md-6">
              <div class="block">
                <div class="header no-border">
                  <h2>Monitor</h2>
                </div>
                <div class="content red-chart">
                  <div id="site_statistics2" style="height:125px;"></div>
                </div>
              </div>
            </div>
          </div>
          <div class="row dash-cols">
            <div class="col-sm-12 col-md-12">
              <div class="block">
                  <div id="mainCarousel" class="carousel"  data-ride="carousel">
			    	<!-- Carousel indicators -->
			        <ol class="carousel-indicators">
			            <li data-target="#mainCarousel" data-slide-to="0" class="active"></li>
			            <li data-target="#mainCarousel" data-slide-to="1"></li>
			            <li data-target="#mainCarousel" data-slide-to="2"></li>
			        </ol>   
			       <!-- Wrapper for carousel items -->
			        <div class="carousel-inner">
			            <div class="active item">
			                <button class="btn btn-lg" id="masterDataBtn"><span><spring:message code="label.leftmenu.managemasterdata.link"  text="Manage Master Data"/></button>
			                
			            </div>
				            <div class="item">
				                 <button class="btn btn-lg" id="pigEventsBtn"><span><spring:message code="label.leftmenu.managepigevents.link"  text="Manage Pig Events"/></button>
				            </div>
				            <div class="item">
				                <button class="btn btn-lg"><span><spring:message code="label.leftmenu.analyticreports.link"  text="Analytic Reports"/></button>
				            </div>
				        </div>
				        <!-- Carousel controls -->
				        <a class="carousel-control left" href="#mainCarousel" data-slide="prev">
				            <span class="glyphicon glyphicon-chevron-left"></span>
				        </a>
				        <a class="carousel-control right" href="#mainCarousel" data-slide="next">
				            <span class="glyphicon glyphicon-chevron-right"></span>
				        </a>
				    </div>
				    </div>
				     <div class="block">
				    <div id="masterDataOptions" class="carousel slide"  data-ride="carousel">
				    	<!-- Carousel indicators -->
				        <ol class="carousel-indicators">
				            <li data-target="#masterDataOptions" data-slide-to="0" class="active"></li>
				            <li data-target="#masterDataOptions" data-slide-to="1"></li>
				            <li data-target="#masterDataOptions" data-slide-to="2"></li>
				        </ol>   
				       <!-- Wrapper for carousel items -->
				        <div class="carousel-inner">
				            <div class="active item">
				                <button class="btn btn-lg" ><spring:message code="label.leftmenu.managemasterdata.companydata.link"  text="Company Data"/></button>
				                
				            </div>
					            <div class="item">
					                 <button class="btn btn-lg"><spring:message code="label.leftmenu.managemasterdata.locationdata.link"  text="Location Data"/></button>
					            </div>
					            <div class="item">
					                <button class="btn btn-lg"><spring:message code="label.leftmenu.managemasterdata.employeedata.link"  text="Employee Data/Roles"/></button>
					            </div>
				        </div>
				        <!-- Carousel controls -->
				        <a class="carousel-control left" href="#masterDataOptions" data-slide="prev">
				            <span class="glyphicon glyphicon-chevron-left"></span>
				        </a>
				        <a class="carousel-control right" href="#masterDataOptions" data-slide="next">
				            <span class="glyphicon glyphicon-chevron-right"></span>
				        </a>
				    </div>
				    </div>
				     <div class="block">
				    <div id="pigEventsOptions" class="carousel slide"  data-ride="carousel">
			    	<!-- Carousel indicators -->
			        <ol class="carousel-indicators">
			            <li data-target="#pigEventsOptions" data-slide-to="0" class="active"></li>
			            <li data-target="#pigEventsOptions" data-slide-to="1"></li>
			            <li data-target="#pigEventsOptions" data-slide-to="2"></li>
			        </ol>   
			       <!-- Wrapper for carousel items --> 
			        <div class="carousel-inner">
			            <div class="active item">
			                <button class="btn btn-primary" ><spring:message code="label.leftmenu.managepigevents.entryevent.link"  text="Entry Event"/></button>
			                
			            </div>
				            <div class="item">
				                 <button class="btn btn-primary"><spring:message code="label.leftmenu.managepigevents.breedingevent.link"  text="Breeding Event"/></button>
				            </div>
				            <div class="item">
				                <button class="btn btn-primary"><spring:message code="label.leftmenu.managepigevents.pregnancyevent.link"  text="Pregnancy Event"/></button>
				            </div>
				            <div class="item">
				                <button class="btn btn-primary"><spring:message code="label.leftmenu.managepigevents.farrowevent.link"  text="Farrow Event"/></button>
				            </div>
				        </div>
				        <!-- Carousel controls -->
				        <a class="carousel-control left" href="#pigEventsOptions" data-slide="prev">
				            <span class="glyphicon glyphicon-chevron-left"></span>
				        </a>
				        <a class="carousel-control right" href="#pigEventsOptions" data-slide="next">
				            <span class="glyphicon glyphicon-chevron-right"></span>
				        </a>
				    </div>
				    </div>
				    
	<script>
	$('#masterDataOptions').css('display', 'none');
	$('#pigEventsOptions').css('display', 'none');
	$('#masterDataBtn').click(function(e) {
		alert("Master Data clicked");
		  e.preventDefault();
		  $('#pigEventsOptions').css('display', 'none');
		  //$('#masterDataBtn').css('background', '#28597a');
		  $('#masterDataOptions').css('display', 'block');
		});
	$('#pigEventsBtn').click(function(e) {
		  e.preventDefault();
		  $('#masterDataOptions').css('display', 'none');
		  //$('#pigEventsBtn').css('background', '#28597a');
		  $('#pigEventsOptions').css('display', 'block');
		});
	</script>
				    
              
            </div>
          </div>
        </div>