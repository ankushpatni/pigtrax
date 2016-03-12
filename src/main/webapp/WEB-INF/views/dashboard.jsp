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
            <div data-step="2" class="butpro butstyle">
              <div class="sub">
                <h2><spring:message code="label.dashboard.clients"  text="Clients"/></h2><span id="total_clientes">170</span>
              </div>
              <div class="stat"><span class="spk1">
                  <canvas style="display: inline-block; width: 74px; height: 16px; vertical-align: top;" width="74" height="16"></canvas></span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2><spring:message code="label.dashboard.sales"  text="Sales"/></h2><span>$951,611</span>
              </div>
              <div class="stat"><span class="up"> 13,5%</span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2><spring:message code="label.dashboard.visits"  text="Visits"/></h2><span>125</span>
              </div>
              <div class="stat"><span class="down"> 20,7%</span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2><spring:message code="label.dashboard.newusers"  text="New Users"/></h2><span>18</span>
              </div>
              <div class="stat"><span class="equal"> 0%</span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2><spring:message code="label.dashboard.average"  text="Average"/></h2><span>3%</span>
              </div>
              <div class="stat"><span class="spk2"></span></div>
            </div>
            <div class="butpro butstyle">
              <div class="sub">
                <h2><spring:message code="label.dashboard.downloads"  text="Downloads"/></h2><span>184</span>
              </div>
              <div class="stat"><span class="spk3"></span></div>
            </div>
          </div>
          <div class="row dash-cols">
            <div class="col-sm-6 col-md-6">
              <div class="block">
                <div class="header no-border">
                  <h2><spring:message code="label.dashboard.sales"  text="Sales"/></h2>
                </div>
                <div data-step="3" class="content red-chart">
                  <div id="site_statistics" style="height:125px;"></div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-md-6">
              <div class="block">
                <div class="header no-border">
                  <h2><spring:message code="label.dashboard.monitor"  text="Monitor"/></h2>
                </div>
                <div class="content red-chart">
                  <div id="site_statistics2" style="height:125px;"></div>
                </div>
              </div>
            </div>
          </div>
          
          
          <div class="row dash-cols">
            <div class="col-sm-6 col-md-6 col-lg-4">
              <div class="widget-block">
                <div class="white-box padding">
                  <div class="row info">
                    <div>
                      <h3><spring:message code="label.dashboard.yourgoals"  text="Your Goals"/></h3>
                    </div>
                    <div>
                      <div id="com_stats" style="height:98px;"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-4">
              <div class="widget-block">
                <div class="white-box padding">
                  <div class="row info text-shadow">
                    <div class="col-xs-12">
                      <h3><spring:message code="label.dashboard.comments"  text="Comments"/></h3>
                    </div>
                    <div class="col-xs-12">
                      <div id="com2_stats" style="height:98px;"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-4">
              <div class="widget-block">
                <div class="white-box">
                  <div class="fact-data">
                    <div data-bar-color="#FD9C35" data-track-color="#EFEFEF" data-percent="45" class="epie-chart"><span>0%</span></div>
                  </div>
                  <div class="fact-data no-padding text-shadow">
                    <h3><spring:message code="label.dashboard.userssales"  text="Users Sales"/></h3>
                    <h2>4,522</h2>
                    <p><spring:message code="label.dashboard.monthlysalesfromusers"  text="Monthly sales from users"/></p>
                  </div>
                </div>
              </div>
            </div>
          </div>
         
        </div>