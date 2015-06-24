<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div id="content">
  <div class="container">
    <!-- Login form -->
    <form role="form" class="form-login form-wrapper form-narrow" action="j_spring_security_check" method="post">
      <h3>
        <span><spring:message code="label.login"  text="Login"/></span>         
      </h3> 
      <div class="error">${error}</div>     
      
      <div class="form-group">
        <label class="sr-only" for="login-email-page">Username</label>
        <input type="text" id="login-email-page" name ="username" class="form-control email" placeholder="<spring:message code='label.username'  text='Username'/>" ng-model="userDto.email" required>
      </div>
      <div class="form-group">
        <label class="sr-only" for="login-password-page">Password</label>
        <input type="password" id="login-password-page" name="password" class="form-control password" placeholder="<spring:message code='label.password'  text='Password'/>"  ng-model="userDto.password" required>
      </div>
       <button type="submit" class="btn btn-primary" ><spring:message code="label.login"  text="Login"/></button>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </div>
</div>
