<!-- ======== @Region: #content ======== -->
<div id="content">
  <div class="container">
    <!-- Login form -->
    <form role="form" class="form-login form-wrapper form-narrow" action="j_spring_security_check" method="post">
      <h3>
        <span>Login</span>         
      </h3> 
      <div class="error">${error}</div>     
      
      <div class="form-group">
        <label class="sr-only" for="login-email-page">Username</label>
        <input type="text" id="login-email-page" name ="username" class="form-control email" placeholder="Email" ng-model="userDto.email" required>
      </div>
      <div class="form-group">
        <label class="sr-only" for="login-password-page">Password</label>
        <input type="password" id="login-password-page" name="password" class="form-control password" placeholder="Password"  ng-model="userDto.password" required>
      </div>
       <button type="submit" class="btn btn-primary" >Login</button>
      | <small>Not signed up? <a href="signup">Sign up here</a>.</small>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </div>
</div>
