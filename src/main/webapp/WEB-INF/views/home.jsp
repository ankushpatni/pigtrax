<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
 <link href="resources/css/custom-style.css" rel="stylesheet"> 
 <link rel="stylesheet" href="resources/lib/slick/slick.css">
 <link rel="stylesheet" href="resources/lib/slick/slick-theme.css">

<div id="homeContent"> 
<div class="slider" id="main">
      <div>
        <button class="btn btn-super" id="masterDataBtn"><span><spring:message code="label.home.slider.masterdata"  text="Manage Master Data"/></button>
      </div>
      <div>
        <button class="btn btn-super"><span><spring:message code="label.home.slider.pigevents"  text="Manage Pig Events"/></button>
      </div>
      <div>
        <button class="btn btn-super"><span><spring:message code="label.home.slider.analyticsreport"  text="Analytics Report"/></button>
      </div>
    </div>
    
    <div class="slider" id="sub">
      <div>
        <button class="btn btn-medium"><span><spring:message code="label.home.slider.masterdata.companydata"  text="Company Data"/></button>
      </div>
      <div>
        <button class="btn btn-medium"><span><spring:message code="label.home.slider.masterdata.locationdata"  text="Location Data"/></button>
      </div>
      <div>
        <button class="btn btn-medium "><span><spring:message code="label.home.slider.masterdata.employeedata"  text="Employee Data/Roles"/></button>
      </div>
    </div>
<script>
	$(document).ready(function(){
        $('#main').slick({
            centerMode: true,
            centerPadding: '60px',
            dots: true,
            infinite: true,
            speed: 300,
            slidesToShow: 2,
            slidesToScroll: 1,
			arrows: true
        });
        
        $('#sub').slick({
            centerMode: true,
            centerPadding: '60px',
            dots: true,
            infinite: true,
            speed: 300,
            slidesToShow: 2,
            slidesToScroll: 1,
			arrows: true,
        	visibility:'hidden'
        });
      });	
	$('#sub').css('visibility', 'hidden');
	</script>
	
	<script>
	$('#masterDataBtn').click(function(e) {
		  e.preventDefault();
		  $('#masterDataBean').css('background', '#28597a');
		  $('#sub').css('visibility', 'visible');
		});
	</script>

</div>
