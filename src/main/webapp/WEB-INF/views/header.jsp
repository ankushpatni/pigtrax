<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="head-nav" class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" data-toggle="collapse" data-target=".navbar-collapse" class="navbar-toggle"><span class="fa fa-gear"></span></button>
          <a href="#" class="navbar-brand"><span></span></a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#"><spring:message code="label.header.home.link"  text="Home"/></a></li>
            <li><a href="#about"><spring:message code="label.header.about.link"  text="About"/></a></li>
            <li class="dropdown"><a href="#" data-toggle="dropdown" class="dropdown-toggle"><spring:message code="label.header.contact.link"  text="Contact"/><b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#"><spring:message code="label.header.contact.location.link"  text="Our Location"/></a></li>
                <li><a href="#"><spring:message code="label.header.contact.address.link"  text="Contact Address"/></a></li>               
              </ul>
            </li>
            
          </ul>
           <%if (request.getRemoteUser() != null) {
        	   %>
          <ul class="nav navbar-nav navbar-right user-nav">
            <li class="dropdown profile_menu"><a href="#" data-toggle="dropdown" class="dropdown-toggle"><img alt="Avatar" src="resources/assets/img/avatar2.jpg"><span><%=request.getRemoteUser()%></span><b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">My Account</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="#">Messages</a></li>
                <li><a href="resetPassword">Reset Password</a></li>
                <li class="divider"></li>
                <li><a href="j_spring_security_logout"><spring:message code="label.header.home.signOut"  text="Sign Out"/></a></li>
              </ul>
            </li>
          </ul>
          <%} %>
          <%if (request.getRemoteUser() == null) {  	 
          %>           
          <div class="polyglot-language-switcher pull-right active">
			    <ul style="display: none">
			        <li><a href="?locale=en" title="English (US)"  data-lang-id="en" ><img src="resources/assets/img/flags/us.png" alt="United States"> English (US)</a></li>			        
			        <li><a href="?locale=pr" title="Portuguese" data-lang-id="pr"><img src="resources/assets/img/flags/br.png" alt="Portuguese"> Portuguese</a></li>
			        <li><a href="?locale=es" title="Spanish" data-lang-id="es"><img src="resources/assets/img/flags/es.png" alt="Spain"> Español</a></li>
			    </ul>
			</div>
			<% } else { %>
          <ul class="nav navbar-nav navbar-right not-nav">
           
           <li class="button dropdown"><a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle"><i class="fa fa-comments"></i></a>
              <ul class="dropdown-menu messages">
                <li>
                  <div class="nano nscroller">
                    <div class="content">
                      <ul>
                        <li><a href="#"><img src="resources/assets/img/avatar2.jpg" alt="avatar"><span class="date pull-right">13 Sept.</span><span class="name">Daniel</span> I'm following you, and I want your money!</a></li>
                        <li><a href="#"><img src="resources/assets/img/avatar_50.jpg" alt="avatar"><span class="date pull-right">20 Oct.</span><span class="name">Adam</span> is now following you</a></li>
                        <li><a href="#"><img src="resources/assets/img/avatar4_50.jpg" alt="avatar"><span class="date pull-right">2 Nov.</span><span class="name">Michael</span> is now following you</a></li>
                        <li><a href="#"><img src="resources/assets/img/avatar3_50.jpg" alt="avatar"><span class="date pull-right">2 Nov.</span><span class="name">Lucy</span> is now following you</a></li>
                      </ul>
                    </div>
                  </div>
                  <ul class="foot">
                    <li><a href="#">View all messages </a></li>
                  </ul>
                </li>
              </ul>
            </li>
            <li class="button dropdown"><a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle"><i class="fa fa-globe"></i></a>
              <ul class="dropdown-menu">
                <li>
                  <div class="nano nscroller">
                    <div class="content">
                      <ul>
                        <li><a href="#"><i class="fa fa-cloud-upload info"></i><b>Daniel</b> is now following you <span class="date">2 minutes ago.</span></a></li>
                        <li><a href="#"><i class="fa fa-male success"></i><b>Michael</b> is now following you <span class="date">15 minutes ago.</span></a></li>
                        <li><a href="#"><i class="fa fa-bug warning"></i><b>Mia</b> commented on post <span class="date">30 minutes ago.</span></a></li>
                        <li><a href="#"><i class="fa fa-credit-card danger"></i><b>Andrew</b> killed someone <span class="date">1 hour ago.</span></a></li>
                      </ul>
                    </div>
                  </div>
                  <ul class="foot">
                    <li><a href="#">View all activity </a></li>
                  </ul>
                </li>
              </ul>
            </li>
            <%} %>
          </ul>
        </div>
      </div>
    </div>
    <!-- -- Header Ends -->
    <script>
    jQuery(document).ready(function ($) {
        $('.polyglot-language-switcher').polyglotLanguageSwitcher();
    });
</script> 
    