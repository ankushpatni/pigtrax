<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
 <div class="navbar-static-top">
        <!--Header upper region-->
        <div class="header-upper">
          <div class="header-upper-inner container">
            <div class="row">
              <div class="col-xs-8 col-xs-push-4">
               
              </div>
              <div >
                
                <!--user menu-->
                <div class="btn-group user-menu pull-right">
						<table width="100%">
							<tr>
								<td align="right">
								<%if (request.getRemoteUser() == null) { %>
									<a href="?locale=en"
									class="btn btn-link login">English</a>|<a href="?locale=pt"
									class="btn btn-link login">Portugese</a>|<a href="?locale=es"
									class="btn btn-link login">Spanish</a></td>
									<% } %>
									<td >
									<%
										if (request.getRemoteUser() != null) {
									%> <spring:message code="label.welcome" text="Welcome" /> <%=request.getRemoteUser()%>,
									<%} %>
									</td>
									<td align="right">
									<%
										if (request.getRemoteUser() != null) {
									%>
									<a href="j_spring_security_logout" class="btn btn-link login"><spring:message
											code="label.logout" text="Logout" /></a> 
									<%	}%>

								</td>
							</tr>
						</table>



					</div>
            </div>
          </div>
        </div>
      </div>
      
      <!--Header & Branding region-->
      <div class="header" data-toggle="clingify">
        <div class="header-inner container">
          <div class="navbar">
            <div class="pull-left">
              <!--branding/logo-->
              <a class="navbar-brand" href="home" title="Home">
                <h1>
                  <span><spring:message code="label.application.title"  text="PigTrax"/></span>&nbsp;<spring:message code="label.application.system.title"  text="System"/><span></span>
                </h1>
              </a>
              
            </div>
            <div class="pull-right">
            <%
				if (request.getRemoteUser() != null) {
			%>
              <a href="home" title="<spring:message code='label.homeicon.tooltip'  text='Home'/>">
		          <span class="glyphicon glyphicon-home"></span>
		        </a>
              &nbsp;&nbsp;
              <a href="#"  title="<spring:message code='label.reportsicon.tooltip'  text='Reports'/>">
		          <span class="glyphicon glyphicon-stats"></span>
		        </a>
              &nbsp;&nbsp;
             <a href="#"  title="<spring:message code='label.settingsicon.tooltip'  text='Settings'/>">
	          <span class="glyphicon glyphicon-cog"></span>
	        </a>
              &nbsp;&nbsp;
              <a href="#"  title="<spring:message code='label.notificationsicon.tooltip'  text='Notifications'/>">
	          <span class="glyphicon glyphicon-info-sign"></span>	          
	        </a><span class="badge badge-notify">3</span>
              
              <%} %>
            </div>
            
          
          <!--/.navbar-collapse -->
        </div>
      </div>
    </div>
  </div>
  
 