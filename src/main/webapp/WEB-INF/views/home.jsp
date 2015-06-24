<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
 <link href="resources/css/custom-style.css" rel="stylesheet"> 
 <link rel="stylesheet" href="resources/lib/slick/slick.css">
 <link rel="stylesheet" href="resources/lib/slick/slick-theme.css">
<script type="text/javascript" src="resources/lib/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="resources/lib/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="resources/lib/slick/slick.min.js"></script>
<div id="homeContent">
<div class="slider" id="main">
      <div>
        <button class="btn btn-super button:hover"><span><spring:message code="label.home.slider.masterdata"  text="Manage Master Data"/></button>
      </div>
      <div>
        <button class="btn btn-super button:hover"><span><spring:message code="label.home.slider.pigevents"  text="Manage Pig Events"/></button>
      </div>
      <div>
        <button class="btn btn-super button:hover"><span><spring:message code="label.home.slider.analyticsreport"  text="Analytics Report"/></button>
      </div>
    </div>
    
    <div class="slider" id="sub">
      <div>
        <button class="btn btn-medium button:hover"><span><spring:message code="label.home.slider.masterdata.companydata"  text="Company Data"/></button>
      </div>
      <div>
        <button class="btn btn-medium button:hover"><span><spring:message code="label.home.slider.masterdata.locationdata"  text="Location Data"/></button>
      </div>
      <div>
        <button class="btn btn-medium button:hover"><span><spring:message code="label.home.slider.masterdata.employeedata"  text="Employee Data/Roles"/></button>
      </div>
    </div>
<script>
	$(document).ready(function(){
        $('#main').slick({
            centerMode: true,
            centerPadding: '60px',
            dots: false,
            infinite: true,
            speed: 300,
            slidesToShow: 2,
            slidesToScroll: 1,
			arrows: true
        });
        
        $('#sub').slick({
            centerMode: true,
            centerPadding: '60px',
            dots: false,
            infinite: true,
            speed: 300,
            slidesToShow: 2,
            slidesToScroll: 1,
			arrows: true
        });
      });	
	</script>

</div>
