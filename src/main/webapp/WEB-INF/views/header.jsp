<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<div id="head-nav" class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" data-toggle="collapse" data-target=".navbar-collapse" class="navbar-toggle"><span class="fa fa-gear"></span></button><a href="#" class="navbar-brand"><span><spring:message code="label.application.title"  text="PigTrax"/>&nbsp;<spring:message code="label.application.system.title"  text="System"/></span></a>
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
                <li class="divider"></li>
                <li><a href="j_spring_security_logout">Sign Out</a></li>
              </ul>
            </li>
          </ul>
          <%} %>
          <ul class="nav navbar-nav navbar-right not-nav">
          <%if (request.getRemoteUser() == null) {  	 
          %>
           <a href="?locale=en"> English </a> | <a href="?locale=pr" >Portuguese</a> | <a href="?locale=es">Spanish</a>
           <% } else { %>
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
            <li class="button dropdown"><a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle"><i class="fa fa-globe"></i><span class="bubble">2</span></a>
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
            <li class="button"><a href="javascript:;" class="speech-button"><i class="fa fa-microphone"></i></a></li>
          </ul>
        </div>
      </div>
    </div>
    <!-- -- Header Ends -->
    