 <div class="navbar-static-top">
        <!--Header upper region-->
        <div class="header-upper">
          <div class="header-upper-inner container">
            <div class="row">
              <div class="col-xs-8 col-xs-push-4">
               
              </div>
              <div class="col-xs-4 col-xs-pull-8">
                
                <!--user menu-->
                <div class="btn-group user-menu">
              
             <%
			if (request.getRemoteUser()== null) {%>  
                	<a href="login" class="btn btn-link login" >Login</a> 
                	
          <%
          } if (request.getRemoteUser()!= null) {
          %>
             	 
             	Welcome <%=request.getRemoteUser() %>, <a href="login?logout" class="btn btn-link logout navbar-right" >Logout</a>
              <%}%>
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
                  <span>PigTrax</span>&nbsp;System<span></span>
                </h1>
              </a>
              
            </div>
            
          
          <!--/.navbar-collapse -->
        </div>
      </div>
    </div>
  </div>
  
 