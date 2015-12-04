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
                <div data-step="3" class="content red-chart">
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
         
        </div>